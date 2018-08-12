package com.example.irobot;

import java.util.List;

import com.example.irobot.bean.QusAnsRecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class AddQusAnsAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private List<QusAnsRecord> mQusAns;

	public AddQusAnsAdapter(Context context, List<QusAnsRecord> mQusAns) {
		mLayoutInflater = LayoutInflater.from(context);
		this.mQusAns = mQusAns;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mQusAns.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mQusAns.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		QusAnsRecord qusansrecord = mQusAns.get(position);

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.revise, parent, false);
	        viewHolder.mQuestion = (EditText) convertView.findViewById(R.id.add_question_et_id);
			viewHolder.mAnswer = (EditText) convertView.findViewById(R.id.add_answer_et_id);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mQuestion.setText(qusansrecord.getAnswer());
		viewHolder.mAnswer.setText(qusansrecord.getQuestion());
		return convertView;
	}

	private final class ViewHolder {
		EditText mQuestion;
		EditText mAnswer;
	}
}
