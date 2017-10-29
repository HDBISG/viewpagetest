
package com.example.viewpagetest;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
	private ArrayList<View> pageview;


	private ImageView imageView;
	private ImageView[] dotViews;
	//包裹点点的LinearLayout
	private ViewGroup group;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main);

		viewPager = (ViewPager) findViewById(R.id.viewPager);

		//查找布局文件用LayoutInflater.inflate
		LayoutInflater inflater =getLayoutInflater();
		View view1 = inflater.inflate(R.layout.item01, null);
        ImageView imageView1 = (ImageView)view1.findViewById( R.id.imageView1 );
        imageView1.setImageResource( R.drawable.view1 );

		View view2 = inflater.inflate(R.layout.item01, null);
        ImageView imageView2 = (ImageView)view2.findViewById( R.id.imageView1 );
        imageView2.setImageResource( R.drawable.view2 );

		View view3 = inflater.inflate(R.layout.item03, null);

		//将view装入数组
		pageview =new ArrayList<View>();
		pageview.add(view1);
		pageview.add(view2);
		pageview.add(view3);


		group = (ViewGroup)findViewById(R.id.viewGroup);

		//有多少张图就有多少个点点
		dotViews = new ImageView[pageview.size()];
		for(int i =0;i<pageview.size();i++){
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

			group.addView(dotViews[i]);
		}



		//绑定适配器
		viewPager.setAdapter(mPagerAdapter);
		//绑定监听事件
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
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



	//pageView监听器
	class GuidePageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		//如果切换了，就把当前的点点设置为选中背景，其他设置未选中背景
		public void onPageSelected(int arg0) {

			for(int i = 0; i< dotViews.length; i++){
				dotViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);
				if (arg0 != i) {
					dotViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
				}
			}

		}

	}
}
