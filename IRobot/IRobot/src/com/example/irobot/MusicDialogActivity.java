package com.example.irobot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MusicDialogActivity extends Activity implements OnClickListener {
	private TextView tv1;
	private TextView tv2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.music_dialog_lo);
		toOnlineMusicActivity();
		toLocalMusicActivity();

	}

	private void toOnlineMusicActivity() {
		// TODO 在线音乐按钮
		tv1 = (TextView) findViewById(R.id.music_dialog_onlinemusic_tv_id);
		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MusicDialogActivity.this, MusicActivity.class);
				intent.setClass(MusicDialogActivity.this, MusicActivity.class);
				intent.putExtra("name", "跳转");
				MusicDialogActivity.this.startActivity(intent);
			}
		});
	}

	private void toLocalMusicActivity() {
		// TODO 本地音乐按钮
		tv2 = (TextView) findViewById(R.id.music_dialog_localmusic_tv_id);
		tv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MusicDialogActivity.this, LocalMusicActivity.class);
				intent.setClass(MusicDialogActivity.this, LocalMusicActivity.class);
				intent.putExtra("name", "跳转");
				MusicDialogActivity.this.startActivity(intent);
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
