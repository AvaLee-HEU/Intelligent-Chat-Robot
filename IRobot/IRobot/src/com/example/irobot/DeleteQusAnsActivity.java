package com.example.irobot;

import com.example.irobot.bean.QusAnsRecord;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteQusAnsActivity extends Activity{
	//ɾ���Ի�ҳ��
		SharedPreferences sharedPreferences;
		private Button mFind;
		private Button mDelete;
		private QusAnsRecord QA;
		private EditText question, answer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.delete_lo);
		newsharedPreferences();
		addAssignment();
		initmFind();
		initmDelete();
	}
	
	
	


	private void addAssignment() {
		// TODO Auto-generated method stub
		question = (EditText) findViewById(R.id.delete_qus_et_id);
		answer = (EditText) findViewById(R.id.delete_ans_et_id);
		QA = new QusAnsRecord();
	}
	
	private void newsharedPreferences() {
		sharedPreferences = getSharedPreferences(getPackageName() + "addqa", Context.MODE_PRIVATE);
	}
	
	private void initmFind() {
		// TODO ��ѯ��ť
		mFind = (Button) findViewById(R.id.delete_find_bt_id);
		mFind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				QA.question = question.getText().toString();
				if(QA.question.equals("")) {
					Toast.makeText(DeleteQusAnsActivity.this, "�������ⲻ��Ϊ��", Toast.LENGTH_SHORT).show();
				}else {
					String localAnwser = sharedPreferences.getString(QA.question, "");
					if(!localAnwser.equals("")) {
						Toast.makeText(DeleteQusAnsActivity.this, "��ѯ���ضԻ��ɹ�", Toast.LENGTH_SHORT).show();
					    answer.setText(localAnwser);
					}else {
						Toast.makeText(DeleteQusAnsActivity.this, "δ�ҵ����ضԻ�", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	private void initmDelete() {
		// TODO ɾ����ť
		mDelete = (Button) findViewById(R.id.delete_submit_bt_id);
		mDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				QA.question = question.getText().toString();
				Editor editor = sharedPreferences.edit();
				editor.remove(QA.question);
				editor.commit();
				Toast.makeText(DeleteQusAnsActivity.this, "ɾ�����ضԻ��ɹ�", Toast.LENGTH_SHORT).show();
				question.setText("");
				answer.setText("");
			}
		});
	}
}
