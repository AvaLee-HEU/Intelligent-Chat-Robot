package com.example.irobot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.irobot.bean.ChatMessage;
import com.example.irobot.bean.ChatMessage.Type;
import com.example.irobot.bean.QusAnsRecord;
import com.example.irobot.utils.HttpUtils;

import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;

import com.iflytek.cloud.speech.SpeechUser;

import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private ListView mListView;
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mDatas;
	private List<QusAnsRecord> mQusAns;
	private AddQusAnsAdapter mQAAdapter;
	private EditText mInputMsg;
	private Button mSendMsg;
	private TextView mAddMsg;
	private TextView mAddMusic;
	SharedPreferences sharedPreferences;
	
	
	private int longClickPosition;
	private PopupWindow popupWindow;
	private TextView tvDelte;
	
	private Button mbt;
	private Toast mToast;
	private RecognizerDialog iatDialog;  //识别窗口

	private  final String APP_ID = "appid=53364cbc";

	private FullScreenVideoView mVideoView;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chat_lo);

	
		initView();// 初始化空间对象
		initData();// 初始化数据
		initListener();// 初始化事件
		addMsg();// 主界面右上角添加对话点击事件
		addMusic();//主界面左上角音乐跳转按钮
		newsharedPreferences();

		
		
		mbt =(Button)findViewById(R.id.mic_bt_lo_id);
		mInputMsg = (EditText)findViewById(R.id.input_msg_lo_id);
		iatDialog = new RecognizerDialog(this);
		
		mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);
		SpeechUser.getUser().login(MainActivity.this, null, null, APP_ID,
				listener);
		mbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showIatDialog();
			}
		});

	
	
	
	
		 initView2();//加载主聊天页面动画
		
	
	
	}
	
	

	 private void initView2() {
	        //加载视频资源控件
		 mVideoView = (FullScreenVideoView) findViewById(R.id.videoView);
	        //设置播放加载路径
		 mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.moon));
	        //播放
		 mVideoView.start();
	        //循环播放
		 mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
	            @Override
	            public void onCompletion(MediaPlayer mediaPlayer) {
	            	mVideoView.start();
	            }
	        });
	    }

		

	    
	

	 //返回重启加载
	 
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		 initView2();
		super.onRestart();
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mVideoView.stopPlayback();
		super.onStop();
	}



	protected void showIatDialog() {
		// TODO Auto-generated method stub
		if (null == iatDialog) {
			// 初始化听写Dialog
			iatDialog = new RecognizerDialog(this);
		}

		// 清空Grammar_ID，防止识别后进行听写时Grammar_ID的干扰
		iatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
		// 设置听写Dialog的引擎
		iatDialog.setParameter(SpeechConstant.DOMAIN, "iat");
		iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		// 清空结果显示框.
		mInputMsg.setText(null);
		// 显示听写对话框
		iatDialog.setListener(recognizerDialogListener);
		iatDialog.show();
		showTip("请开始说话...");
	}

	RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {

		@Override
		public void onResult(RecognizerResult arg0, boolean arg1) {
			// TODO Auto-generated method stub
			String text = JsonParser.parseIatResult(arg0.getResultString());
			mInputMsg.append(text);
			mInputMsg.setSelection(mInputMsg.length());
		}

		@Override
		public void onError(SpeechError arg0) {
			// TODO Auto-generated method stub

		}
	};

	
	 // 用户登录回调监听器.
	 
	private SpeechListener listener = new SpeechListener() {

		public void onData(byte[] arg0) {
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error != null) {
				Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT)
						.show();

			}
		}

		@Override
		public void onEvent(int arg0, Bundle arg1) {
		}
	};

	// 提示框
	private void showTip(String str) {
		if (!TextUtils.isEmpty(str)) {
			mToast.setText(str);
			mToast.show();
		}
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void newsharedPreferences() {
		// TODO Auto-generated method stub
		sharedPreferences = getSharedPreferences(getPackageName() + "addqa", Context.MODE_PRIVATE);
	}



	private void addMsg() {
		// TODO 主聊天页面加号
		mAddMsg = (TextView) findViewById(R.id.title_right_lo_id);
		mAddMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, dialogBoxActivity.class);
				intent.setClass(MainActivity.this, dialogBoxActivity.class);
				intent.putExtra("name", "跳转");
				MainActivity.this.startActivity(intent);
			}
		});
	}
	
	private void addMusic() {
		// TODO 主聊天页面音乐logo点击事件
		mAddMusic = (TextView) findViewById(R.id.title_left_lo_id);
		mAddMusic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, MusicDialogActivity.class);
				intent.setClass(MainActivity.this, MusicDialogActivity.class);
				intent.putExtra("name", "跳转");
				MainActivity.this.startActivity(intent);
			}
		});
	}
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 等待接收，子线程完成数据的返回
			ChatMessage fromMessge = (ChatMessage) msg.obj;
			mDatas.add(fromMessge);
			mAdapter.notifyDataSetChanged();
			mListView.setSelection(mDatas.size() - 1);
			
			/*Toast.makeText(MainActivity.this, "网络数据请求成功！", Toast.LENGTH_SHORT).show();*/
		};

	};

	private void initListener() {
		// TODO Auto-generated method stub
		mSendMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String toMsg = mInputMsg.getText().toString();
				if (TextUtils.isEmpty(toMsg)) {
					// 进行空的判断
					Toast.makeText(MainActivity.this, "发送消息不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				ChatMessage toMessage = new ChatMessage();
				toMessage.setDate(new Date());
				toMessage.setMsg(toMsg);
				toMessage.setType(Type.OUTCOMING);
				mDatas.add(toMessage);
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(mDatas.size() - 1);

				mInputMsg.setText("");
				
				String localAnwser = sharedPreferences.getString(toMsg, "");
				Log.i("cn","localAnwser="+localAnwser);
				if (!localAnwser.equals("")) {
					/*Toast.makeText(MainActivity.this, "使用本地数据！", Toast.LENGTH_SHORT).show();*/
					ChatMessage anwserMessage = new ChatMessage();
					anwserMessage.setDate(new Date());
					anwserMessage.setMsg(localAnwser);
					anwserMessage.setType(Type.INCOMING);
					mDatas.add(anwserMessage);
					mAdapter.notifyDataSetChanged();
					mListView.setSelection(mDatas.size() - 1);
				} else {
					/*Toast.makeText(MainActivity.this, "向网络请求！", Toast.LENGTH_SHORT).show();*/
					new Thread() {
						public void run() {
							ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);
							Message m = Message.obtain();
							m.obj = fromMessage;
							mHandler.sendMessage(m);

						};
					}.start();
				}
				
				
				
				
//				QusAnsRecord toquestion = new QusAnsRecord();
//                toquestion.setQuestion(toMsg);
                
                
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		mListView = (ListView) findViewById(R.id.main_lv_chat_lo_id);
		mInputMsg = (EditText) findViewById(R.id.input_msg_lo_id);
		mSendMsg = (Button) findViewById(R.id.main_tv_send);

	}

	private void initData() {
		// TODO Auto-generated method stub

		mDatas = new ArrayList<ChatMessage>();
		mDatas.add(new ChatMessage("您好,我是智能聊天机器人IRobot,请问有什么可以帮助您的？", Type.INCOMING, new Date()));
		mAdapter = new ChatMessageAdapter(this, mDatas);
		mListView.setAdapter(mAdapter);
		
		
		 
	}
	// 再按一次退出
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}

}
