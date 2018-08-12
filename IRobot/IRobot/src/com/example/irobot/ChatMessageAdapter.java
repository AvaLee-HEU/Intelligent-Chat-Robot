package com.example.irobot;

import java.text.SimpleDateFormat;
import java.util.List;

import com.example.irobot.bean.ChatMessage;
import com.example.irobot.bean.ChatMessage.Type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatMessageAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private List<ChatMessage> mDatas;

	public ChatMessageAdapter(Context context, List<ChatMessage> mDatas) {
		mLayoutInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		ChatMessage chatMessage = mDatas.get(position);
		if (chatMessage.getType() == Type.INCOMING) {
			return 0;
		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChatMessage chatMessage = mDatas.get(position);
		ViewHolder viewHolder = new ViewHolder();

		if (convertView == null) {			
			convertView = getViewWithType(convertView, viewHolder, chatMessage.getType());
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			Type currentType = chatMessage.getType();
			if (viewHolder.type != currentType) {
				convertView = getViewWithType(convertView, viewHolder, chatMessage.getType());
			}
		}
		// …Ë÷√ ˝æ›
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		viewHolder.mDate.setText(df.format(chatMessage.getDate()));
		viewHolder.mMsg.setText(chatMessage.getMsg());
		viewHolder.type = chatMessage.getType();
		
//		convertView.setTag(viewHolder);
		return convertView;
	}
	
	private View getViewWithType(View convertView, ViewHolder viewHolder, Type type) {
//		ViewHolder viewHolder = new ViewHolder();

		if (type == Type.INCOMING)
		{
			convertView = mLayoutInflater.inflate(R.layout.robot_lo, null, false);
//			viewHolder = new ViewHolder();
			viewHolder.mDate = (TextView) convertView.findViewById(R.id.left_time_lo_id);
			viewHolder.mMsg = (TextView) convertView.findViewById(R.id.robot_msg_tv_id);
		} else if (type == Type.OUTCOMING) {
			convertView = mLayoutInflater.inflate(R.layout.me_lo, null, false);
//			viewHolder = new ViewHolder();
			viewHolder.mDate = (TextView) convertView.findViewById(R.id.right_time_lo_id);
			viewHolder.mMsg = (TextView) convertView.findViewById(R.id.me_msg_tv_id);
		}
		
		convertView.setTag(viewHolder);
		
		return convertView;
	}

	private final class ViewHolder {
		TextView mDate;
		TextView mMsg;
		Type type;

	}
}
