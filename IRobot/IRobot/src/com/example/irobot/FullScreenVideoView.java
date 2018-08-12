package com.example.irobot;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

public class FullScreenVideoView extends VideoView{

	public FullScreenVideoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	  public FullScreenVideoView(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	    }  
	  
	    public FullScreenVideoView(Context context, AttributeSet attrs, int defStyleAttr) {  
	        super(context, attrs, defStyleAttr);  
	  
	    }  
	    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
	    	//重新计算高度
	    	int width = getDefaultSize(0,widthMeasureSpec);
	    	int height = getDefaultSize(0,heightMeasureSpec);
	    	setMeasuredDimension(width,height);
	    }
	    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
	    	super.setOnPreparedListener(l);
	    }
	    public boolean onKeyDown(int keyCode,KeyEvent event) {
	    	return super.onKeyDown(keyCode, event);
	    }
}
