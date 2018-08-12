package com.example.irobot;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.example.irobot.R.drawable;
import com.example.irobot.bean.MusicInfo;
import com.example.irobot.utils.HttpUtils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MusicActivity extends Activity {

	private ImageButton music_playbtn;
	private FrameLayout music_playview;
	private EditText music_searchet;
	private TextView music_name;
	private ImageButton music_searchbtn;
	private MusicInfo backmsg;
	private MediaPlayer mMediaPlayer;
	private boolean mIsPlaying = false;

	private Bitmap bitmap;
	private String mBitmapFilePath;
	private String serchinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.music_lo);

		music_searchbtn = (ImageButton) this.findViewById(R.id.music_searchbtn);
		music_searchet = (EditText) this.findViewById(R.id.music_searchet);

		music_name = (TextView) this.findViewById(R.id.music_name);
		music_playview = (FrameLayout) this.findViewById(R.id.music_playview);
		music_playbtn = (ImageButton) this.findViewById(R.id.music_playbtn);
		music_playbtn.setEnabled(false);

		setmusicsearchbtnClickListener();
		setPlayBtnClickListener();

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 等待接收，子线程完成数据的返回
			backmsg = (MusicInfo) msg.obj;
			if (backmsg.conn == true) {
				if (backmsg.infoexists == true) {
					music_name.setText(backmsg.songname + "--" + backmsg.author);
					music_playbtn.setEnabled(true);
				} else {
					music_name.setText("播放音乐");
					Toast.makeText(MusicActivity.this, "we cannot find the music!", Toast.LENGTH_LONG).show();
					music_playbtn.setEnabled(false);
				}
			} else {
				music_name.setText("播放音乐");
				music_playbtn.setEnabled(false);
				Toast.makeText(MusicActivity.this, "Cannot connection the Internet", Toast.LENGTH_SHORT).show();
			}
			music_playview = (FrameLayout) findViewById(R.id.music_playview);
			Drawable drawable = new BitmapDrawable(bitmap);
			music_playview.setBackgroundDrawable(drawable);

		};

	};

	private void setmusicsearchbtnClickListener() {
		music_searchbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (mIsPlaying) {
					music_playbtn.setBackgroundResource(R.drawable.play);
					stopMusic();
					mIsPlaying = false;
				}

				serchinfo = music_searchet.getEditableText().toString();
				mIsPlaying = false;
				if (serchinfo.length() == 0) {
					Toast.makeText(MusicActivity.this, "请输入歌曲名称", Toast.LENGTH_SHORT).show();
					music_playbtn.setEnabled(false);
					return;
				}
				new Thread() {
					public void run() {

						MusicInfo backmsg = HttpUtils.sendMusicInfo(serchinfo);
						Log.i("ir", "new thread" + backmsg);
						if (backmsg.conn == true) {
							Log.i("ir", "conn new thread" + backmsg.infoexists);
							if (backmsg.infoexists == true) {
								mBitmapFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator
										+ backmsg.songname + ".jpg";
								bitmap = HttpUtils.getBitmap(mBitmapFilePath);
								// HttpUtils.getMusic();
							} else {
								Resources res = getResources();
								bitmap = BitmapFactory.decodeResource(res, R.drawable.pic1);
							}
						} else {
							Resources res = getResources();
							bitmap = BitmapFactory.decodeResource(res, R.drawable.pic1);
						}
						Message m = new Message();
						m.obj = backmsg;
						mHandler.sendMessage(m);

					};
				}.start();

			}
		});
	}

	private void setPlayBtnClickListener() {
		// music_name.setText(R.string.musicname);

		music_playbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mIsPlaying) {
					music_playbtn.setBackgroundResource(R.drawable.play);
					stopMusic();
					mIsPlaying = false;
				} else {
					playMusic();
					music_playbtn.setBackgroundResource(R.drawable.pause);
					mIsPlaying = true;
				}

			}
		});

	}

	private void playMusic() {
		mMediaPlayer = new MediaPlayer();
		// String path =Environment.getExternalStorageDirectory().getPath() +
		// File.separator +backmsg.songname+".mp3";
		// File file = new File(path);
		// Log.v("ir","file exists="+file.exists());
		// Log.v("ir","path="+path);
		try {
			// mMediaPlayer.setDataSource(path);

			mMediaPlayer.setDataSource(backmsg.file_link);
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mMediaPlayer.start();
				}
			});
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mMediaPlayer.start();// 重新开始播放
				}
			});
			
			mMediaPlayer.setOnErrorListener(new OnErrorListener() {
				                   
				                     @Override
				                     public boolean onError(MediaPlayer mp, int what, int extra) {
				                    	 mMediaPlayer.start();
				                         return false;
				                     }
				                 });
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
		}
	}

	private void releaseMediaPlayer() {
		// TODO Auto-generated method stub

		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}

	}
	
	private void stopMusic() {
		mMediaPlayer.stop();
//		int ms=mMediaPlayer.getCurrentPosition();
//		Log.i("ir", "getCurrentPosition"+ms);
//		mMediaPlayer.seekTo(ms);
		// mMediaPlayer.seton

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		releaseMediaPlayer();
		super.onDestroy();

	}

}
