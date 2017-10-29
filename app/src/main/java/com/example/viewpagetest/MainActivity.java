
package com.example.viewpagetest;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
/*	@descript 实现滑动切换图片带点点
 *  @Date 2014-8-4
 *  @come：http://www.cnblogs.com/tinyphp/p/3892936.html
 */

public class MainActivity extends Activity {
	private ViewPager viewPager;
	private List<View> pageview;


	private ImageView imageView;
	private ImageView[] dotViews;
	//包裹点点的LinearLayout
	private ViewGroup dotGroup;

	//最多6个点
	private byte MAX_DOTS = 6;

	//实际点数
	private byte numDots = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main);

		viewPager = (ViewPager) findViewById(R.id.viewPager);

		pageview = this.getPageViews();

		if( pageview.size() >= MAX_DOTS ) {
			numDots = 6;
		} else {
			numDots = (byte)pageview.size();
		}

		this.initDotGroup();

		//绑定适配器
		viewPager.setAdapter(mPagerAdapter);
		//绑定监听事件
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
	}

	private List<View> getPageViews() {

		//查找布局文件用LayoutInflater.inflate
		LayoutInflater inflater =getLayoutInflater();

		//将view装入数组
		pageview =new ArrayList<View>();
		pageview.add( this.getImageView( inflater, R.drawable.figure1 ) );
		pageview.add( this.getImageView( inflater, R.drawable.figure2 ) );
		pageview.add( this.getImageView( inflater, R.drawable.figure3 ) );
		pageview.add( this.getImageView( inflater, R.drawable.figure4 ) );
		pageview.add( this.getImageView( inflater, R.drawable.figure5 ) );
		pageview.add( this.getImageView( inflater, R.drawable.figure6 ) );
		pageview.add( this.getImageView( inflater, R.drawable.figure7 ) );
		pageview.add( this.getImageView( inflater, R.drawable.figure8 ) );
		pageview.add( this.getImageView( inflater, R.drawable.figure9 ) );

		return  pageview;
	}

	private View getImageView( LayoutInflater inflater, int viewId ) {

		View view1 = inflater.inflate(R.layout.item01, null);
		ImageView imageView1 = (ImageView)view1.findViewById( R.id.imageView1 );
		imageView1.setImageResource( viewId );
		return view1;
	}

	private void initDotGroup() {

		dotGroup = (ViewGroup)findViewById(R.id.viewGroup);

		//有多少张图就有多少个点点
		dotViews = new ImageView[ numDots ];
		for(int i =0;i< numDots;i++){
			imageView = new ImageView(MainActivity.this);
			imageView.setLayoutParams(new LayoutParams(20,20));
			imageView.setPadding(20, 0, 20, 0);
			dotViews[i] = imageView;

			//默认第一张图显示为选中状态
			if (i == 0) {
				dotViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				dotViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}

			if( pageview.size() > numDots && i == numDots-1) {
				dotViews[i].setBackgroundResource(R.drawable.page_indicator_more);
			}
			dotGroup.addView(dotViews[i]);
		}
	}

	//数据适配器
	PagerAdapter mPagerAdapter = new PagerAdapter(){

		@Override
		//获取当前窗体界面数
		public int getCount() {
			// TODO Auto-generated method stub
			return pageview.size();
		}

		@Override
		//断是否由对象生成界面
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		//是从ViewGroup中移出当前View
		public void destroyItem(ViewGroup container, int arg1, Object arg2) {
            container.removeView(pageview.get(arg1));
		}

		//返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
		public Object instantiateItem( ViewGroup container, int arg1){
            container.addView(pageview.get(arg1));
			return pageview.get(arg1);
		}


	};


	private boolean left = false;
	private boolean right = false;
	private boolean isScrolling = false;
	private int lastValue = -1;

	//pageView监听器
	class GuidePageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {

			//正在滑动
			if (arg0 == 1) {
				isScrolling = true;
			} else {
				isScrolling = false;
			}
			//滑动结束
			if (arg0 == 2) {
				//right = left = false;
				isScrolling = false;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

			System.out.println(  "lastValue =" + lastValue + " arg2=" + arg2);

			if (isScrolling) {
				if (lastValue > arg2) {
					// 递减，向右侧滑动
					right = false;
					left = true;
				} else if (lastValue < arg2) {
					// 递减，向右侧滑动
					right = true;
					left = false;
				} else if (lastValue == arg2) {
					//right = left = false;
				}
			}
			lastValue = arg2;

		}

		@Override
		//如果切换了，就把当前的点点设置为选中背景，其他设置未选中背景
		public void onPageSelected(int pos) {

			int numPageViews = pageview.size();

			System.out.println(  "dotViews.length =" + dotViews.length  + " numPageViews=" + numPageViews );

			//少于等于六个点
			if( dotViews.length == numPageViews ) {
				for(int i = 0; i< dotViews.length; i++){
					dotViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
				}
				dotViews[pos].setBackgroundResource(R.drawable.page_indicator_focused);
				return;
			}
			Log.i( "", "right=" + right + " left=" + left );


			//向右滑动
			if( right ) {
				if( pos < MAX_DOTS-1 ) {

					for(int i = 0; i< MAX_DOTS-1; i++){
						dotViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
					}
					dotViews[pos].setBackgroundResource(R.drawable.page_indicator_focused);
					dotViews[MAX_DOTS-1].setBackgroundResource(R.drawable.page_indicator_more);

				} else if( pos >= MAX_DOTS-1 && pos < numPageViews-2 ) {
					dotViews[0].setBackgroundResource(R.drawable.page_indicator_more);
					for(int i = 1; i< MAX_DOTS-2; i++){
						dotViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
					}
					dotViews[MAX_DOTS-2].setBackgroundResource(R.drawable.page_indicator_focused);
					dotViews[MAX_DOTS-1].setBackgroundResource(R.drawable.page_indicator_more);

				} else {
					dotViews[0].setBackgroundResource(R.drawable.page_indicator_more);
					for(int i = 1; i< MAX_DOTS; i++){
						dotViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
					}
					if( pos == numPageViews-1 ) {
						dotViews[MAX_DOTS-1].setBackgroundResource(R.drawable.page_indicator_focused);
					} else {
						dotViews[MAX_DOTS-2].setBackgroundResource(R.drawable.page_indicator_focused);
					}
				}
			} else if( left ) {

			}
		}

	}
}
