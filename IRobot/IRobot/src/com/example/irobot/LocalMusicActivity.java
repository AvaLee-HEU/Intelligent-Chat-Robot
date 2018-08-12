package com.example.irobot;

import java.util.ArrayList;
import java.util.List;

import com.example.irobot.MusicService.MyBinder;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class LocalMusicActivity extends Activity {
	private TextView mPlayTextView;
	private ImageView mPlayImageView;
	private Spinner mSpinner;
	private Button mSearchButton;
	private MusicPlayerBroadcastReceiver mMusicPlayerBroadcastReceiver;
	private Intent mMusicServiceIntent;
	private String path;
	private ArrayAdapter<String> myAdapter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.localmusic_lo);

		mSpinner = (Spinner) findViewById(R.id.spinner_song);

		Cursor mAudioCursor = this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, 
				MediaStore.Audio.AudioColumns.TITLE);// 排序方式
		// 循环输出歌曲的信息
		List<String> songList = new ArrayList<String>();
		final List<String> songPath = new ArrayList<String>();
		for (int i = 0; i < mAudioCursor.getCount(); i++) {
			mAudioCursor.moveToNext();
			// 找到歌曲标题和总时间对应的列索引
			int indexTitle = mAudioCursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);// 歌名
			String strTitle = mAudioCursor.getString(indexTitle);
			String url = mAudioCursor.getString(mAudioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
			songList.add(strTitle);
			songPath.add(url);
		}

		myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, songList);
		mSpinner.setAdapter(myAdapter);
		path = songPath.get(0);
		myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub

				path = songPath.get(arg2);
				releaseMusicWithBindService();
				mPlayImageView.setImageResource(R.drawable.play);
				Log.w("TAG", "SELECT");
			}
		});
		/*
		 * mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		 * 
		 * @Override public void onNothingSelected(AdapterView<?> parent) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onItemSelected(AdapterView<?> parent, View view, int
		 * position, long id) { // TODO Auto-generated method stub
		 * releaseMusicWithBindService();
		 * mPlayImageView.setImageResource(R.drawable.play); } });
		 */

		initView();
		registerMusicBroadcast();
		bindService();

		startService();
		
		/*
		 * //实例化组件 editText = (EditText) findViewById(R.id.edText); playButton =
		 * (Button) findViewById(R.id.btnPlay); stopButton = (Button)
		 * findViewById(R.id.btnStop);
		 * 
		 * //开始按钮事件 playButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method stub
		 * 
		 * init(); //init1();
		 * 
		 * } });
		 * 
		 * //停止按钮事件 stopButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method stub
		 * stopMusic(); } });
		 */
	}

	private void initView() {

		mPlayImageView = (ImageView) findViewById(R.id.imageview_play);
		mPlayImageView.setImageResource(R.drawable.play);
		mPlayImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mIsBind) {
					if (mService.isPlaying()) {
						stopMusicWithBindService();
						mPlayImageView.setImageResource(R.drawable.play);
					} else {
						playMusicWithBindService();
						mPlayImageView.setImageResource(R.drawable.pause);
					}
				}
			}
		});

		/*
		 * mPlayTextView = (TextView) this.findViewById(R.id.textview_play);
		 * mPlayTextView.setText(R.string.play); mPlayTextView.setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method stub if
		 * (mIsBind) { if (mService.isPlaying()) { stopMusicWithBindService();
		 * mPlayTextView.setText(R.string.play); } else { playMusicWithBindService();
		 * mPlayTextView.setText(R.string.stop); } } } });
		 */

	}

	private MusicService mService;
	private MusicService.MyBinder mBinder;
	private boolean mIsBind = false;
	private EditText editText;
	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mBinder = (MyBinder) service;
			mService = mBinder.getService();
			mIsBind = true;

			updateTextView();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mIsBind = false;
		}

	};

	private void updateTextView() {
		if (mService != null && mIsBind) {
			if (mService.isPlaying()) {
				mPlayImageView.setImageResource(R.drawable.pause);
				// mPlayTextView.setText(R.string.stop);
			} else {
				mPlayImageView.setImageResource(R.drawable.play);
				// mPlayTextView.setText(R.string.play);
			}
		}
	}

	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateTextView();
	}

	private void bindService() {
		Intent intent = new Intent(this, MusicService.class);
		bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
	}

	private void startService() {
		mMusicServiceIntent = new Intent(this, MusicService.class);
		startService(mMusicServiceIntent);
	}

	private void stopService() {
		stopService(mMusicServiceIntent);
	}

	private void playMusicWithBindService() {
		mService.playMusic(path);
		showSimpleNotification(this.getResources().getString(R.string.play));
	}

	private void stopMusicWithBindService() {
		mService.stopMusic();
		showSimpleNotification(this.getResources().getString(R.string.stop));
	}

	private void releaseMusicWithBindService() {
		if (mService != null && mIsBind) {
			mService.stopMusic();
			mService.releaseMusic();
		}
		
		showSimpleNotification(this.getResources().getString(R.string.stop));
	}

	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopService();
		unbindService(mServiceConnection);
		unregisterReceiver(mMusicPlayerBroadcastReceiver);
	}

	private void registerMusicBroadcast() {
		mMusicPlayerBroadcastReceiver = new MusicPlayerBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(MusicService.ACTION_MUSIC_PLAY);
		intentFilter.addAction(MusicService.ACTION_MUSIC_STOP);
		this.registerReceiver(mMusicPlayerBroadcastReceiver, intentFilter);
	}

	public class MusicPlayerBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action.equals(MusicService.ACTION_MUSIC_PLAY)) {
				mPlayImageView.setImageResource(R.drawable.pause);
				// mPlayTextView.setText(R.string.stop);
			} else if (action.equals(MusicService.ACTION_MUSIC_STOP)) {
				mPlayImageView.setImageResource(R.drawable.play);
				// mPlayTextView.setText(R.string.play);
			}
		}

	}

	NotificationManager mManager;

	public void createNotificationManager() {
		mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void showSimpleNotification(String status) {
		if (mManager == null) {
			createNotificationManager();
		}
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setTicker("音乐操作消息");
		builder.setContentTitle("音乐操作");
		builder.setContentText(status);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
		builder.setWhen(System.currentTimeMillis()); // 设置显示通知的时间，不设置默认获取系统时间，这个值会在Notification上面显示出来
		builder.setAutoCancel(true); // 设置为true，点击该条通知会自动删除，false时只能通过滑动来删除
		builder.setPriority(NotificationCompat.PRIORITY_MAX); // 设置优先级，级别高的排在前面
		int defaults = 0;
		defaults |= Notification.DEFAULT_SOUND;
		defaults |= Notification.DEFAULT_VIBRATE;
		defaults |= Notification.DEFAULT_LIGHTS;
		builder.setDefaults(defaults);

		mManager.notify(1, builder.build());
	}

	// 初始化MediaPlayer
	/*
	 * private void init() { if(editText.getEditableText().toString().length() == 0)
	 * { Toast.makeText(this, "请输入歌曲名称", Toast.LENGTH_SHORT).show(); } player = new
	 * MediaPlayer(); String path = "/sdcard/"+
	 * editText.getEditableText().toString() +".mp3"; try {
	 * player.setDataSource(path); player.prepare(); player.start(); } catch
	 * (IllegalArgumentException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (SecurityException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } catch (IllegalStateException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } }
	 */

	// 播放或暂停音乐
	/*
	 * private void playMusic() { isPlaying = player.isPlaying(); if(isPlaying) {
	 * player.stop(); playButton.setText("播放"); }else { player.start();
	 * playButton.setText("暂停"); } //stopButton.setEnabled(true); }
	 */
	// 停止音乐
	/*
	 * private void stopMusic() { player.stop(); //playButton.setText("播放");
	 * //stopButton.setEnabled(true); try { player.prepare(); //为下一次播放做准备 } catch
	 * (IllegalStateException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } player.seekTo(0); }
	 */
}
