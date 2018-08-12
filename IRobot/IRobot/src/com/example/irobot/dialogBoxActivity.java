package com.example.irobot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class dialogBoxActivity extends Activity implements OnClickListener{
	TextView tv1;
	TextView tv2;
	TextView tv3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_lo);
		toAddQusAnsActivity();
		toReviseQusAnsActivity();
		toDeleteQusAnsActivity();
	}

	public void toAddQusAnsActivity() {
		// TODO 添加对话
		tv1 = (TextView) findViewById(R.id.dialogAdd_tv_lo_id);
		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent it = new Intent(dialogBoxActivity.this, AddQusAnsActivity.class);
				it.setClass(dialogBoxActivity.this, AddQusAnsActivity.class);
				it.putExtra("name", "跳转");
				dialogBoxActivity.this.startActivity(it);
			}
		});
	}

	private void toReviseQusAnsActivity() {
		// TODO 修改对话
		tv2 = (TextView) findViewById(R.id.dialogRevise_tv_lo_id);
		tv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent itent = new Intent(dialogBoxActivity.this, ReviseQusAnsActivity.class);
				itent.setClass(dialogBoxActivity.this, ReviseQusAnsActivity.class);
				itent.putExtra("name", "跳转");
				dialogBoxActivity.this.startActivity(itent);
			}
		});
	}

	private void toDeleteQusAnsActivity() {
		// TODO 删除对话
		tv3 = (TextView) findViewById(R.id.dialog_delete_tv_lo_id);
		tv3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent itent = new Intent(dialogBoxActivity.this, DeleteQusAnsActivity.class);
				itent.setClass(dialogBoxActivity.this, DeleteQusAnsActivity.class);
				itent.putExtra("name", "跳转");
				dialogBoxActivity.this.startActivity(itent);
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@Override
	public void onClick(View v) {
		finish();
		
	}

	
	
}
