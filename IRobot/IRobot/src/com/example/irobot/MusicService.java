package com.example.irobot;

import java.io.IOException;

import com.example.irobot.MusicService;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.webkit.WebView.FindListener;
import android.widget.Spinner;

public class MusicService extends Service {

	public static final String ACTION_MUSIC_PLAY = "play";
	public static final String ACTION_MUSIC_STOP = "stop";
	private MediaPlayer player;
	private MyBinder mBinder = new MyBinder();
	private boolean mIsPlaying = false;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
        
	}

	public boolean isPlaying() {
		return mIsPlaying;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// super.onStartCommand(intent, flags, startId);
		return Service.START_NOT_STICKY;
        //START_NOT_STICKY：当Service因为内存不足而被系统kill后，接下来未来的某个时间内，即使系统内存足够可用，系统也不会尝试重新创建此Service。除非程序中Client明确再次调用startService(...)启动此Service。
		
	}

	private void play() {
		player.start();
		mIsPlaying = true;
	}

	private void createMediaPlayerAndPlayer(String path) {
		player = new MediaPlayer();
		try {
			Log.d("path", path);
			player.setDataSource(path);
			
			player.prepareAsync();
			player.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					play();

					Intent intent = new Intent();
					intent.setAction(ACTION_MUSIC_PLAY);
					MusicService.this.sendBroadcast(intent);
					// mMediaPlayer.seekTo(mMediaPlayer.getDuration() - 1000*10);
				}
			});
			player.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mIsPlaying = false;

					Intent intent = new Intent();
					intent.setAction(ACTION_MUSIC_STOP);
					MusicService.this.sendBroadcast(intent);

				}
			});

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playMusic(String path) {

		if (player != null && player.isPlaying()) {
			Log.d("playMusic", "notnull&&isP");
			return;
		} else if (player == null) {
			Log.d("playMusic", "c");
			createMediaPlayerAndPlayer(path);
		} else if (player != null) {
			Log.d("playMusic", "isP");
			play();
		}
	}

	public void stopMusic() {
		if (player != null && player.isPlaying()) {
			player.pause();
			mIsPlaying = false;
		}
	}
	
	public void releaseMusic() {
		if(player != null) {
			player.release();
			player = null;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		player.stop();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	public class MyBinder extends Binder {
		public MusicService getService() {
			return MusicService.this;
		}

	}

}
