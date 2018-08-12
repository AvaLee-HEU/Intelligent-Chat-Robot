package com.example.irobot;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class IntroduceFragment5 extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.introduce_lo5, container, false);
		Button introduce_viewpager_skipbtn= (Button) view.findViewById(R.id.introduce_viewpager_skipbtn);
		introduce_viewpager_skipbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(),MainActivity.class);
				startActivity(intent);
			}
		});
		
		return view;
	}

	
}
