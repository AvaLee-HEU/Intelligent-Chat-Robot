package com.example.irobot;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class IntroduceActivity extends FragmentActivity {
	ViewPager introducevp;
	RadioGroup dotLayout;
	List<Fragment> introducefrags = new ArrayList<Fragment>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.introduce_viewpager_lo);
		
		initView();	
		introducevp.setOnPageChangeListener(new MyPagerChangeListener());
		
	}

	private void initView() {
		dotLayout = (RadioGroup) findViewById(R.id.introducevp_point_groupp);
		introducevp = (ViewPager) findViewById(R.id.introduce_viewpager);
		introducefrags.add(new IntroduceFragment1());
		introducefrags.add(new IntroduceFragment2());
		introducefrags.add(new IntroduceFragment3());
		introducefrags.add(new IntroduceFragment4());
		introducefrags.add(new IntroduceFragment5());
		IntroduceViewPagerAdapter introduceviewpageradpter = new IntroduceViewPagerAdapter(getSupportFragmentManager(),
				introducefrags);

		introducevp.setAdapter(introduceviewpageradpter);

	}

	public class MyPagerChangeListener implements OnPageChangeListener {

		public void onPageSelected(int position) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			((RadioButton) dotLayout.getChildAt(position)).setChecked(true);
			
	        
		
		
		}

	}

}
