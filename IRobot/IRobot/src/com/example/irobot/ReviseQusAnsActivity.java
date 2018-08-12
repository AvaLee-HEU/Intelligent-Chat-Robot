package com.example.irobot;

import com.example.irobot.bean.QusAnsRecord;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReviseQusAnsActivity extends Activity{
	//�޸ĶԻ�ҳ��
	SharedPreferences sharedPreferences;
	private Button mFind;
	private Button mSubmit;
	private Button mDelete;
	private QusAnsRecord QA;
	private EditText question, answer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.revise);
		newsharedPreferences();
		addAssignment();
		initmFind();
		initmSubmit();
		
	}

	
	private void addAssignment() {
		// TODO Auto-generated method stub
		question = (EditText) findViewById(R.id.revise_qus_et_id);
		answer = (EditText) findViewById(R.id.revise_ans_et_id);
		QA = new QusAnsRecord();
	}



	private void newsharedPreferences() {
		sharedPreferences = getSharedPreferences(getPackageName() + "addqa", Context.MODE_PRIVATE);
	}
	
	private void initmFind() {
		// TODO ��ѯ��ť
		mFind = (Button) findViewById(R.id.revise_find_bt_id);
		mFind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				QA.question = question.getText().toString();
				if(QA.question.equals("")) {
					Toast.makeText(ReviseQusAnsActivity.this, "�������ⲻ��Ϊ��", Toast.LENGTH_SHORT).show();
				}else {
					String localAnwser = sharedPreferences.getString(QA.question, "");
					if(!localAnwser.equals("")) {
						Toast.makeText(ReviseQusAnsActivity.this, "��ѯ���ضԻ��ɹ�", Toast.LENGTH_SHORT).show();
					    answer.setText(localAnwser);
					}else {
						Toast.makeText(ReviseQusAnsActivity.this, "δ�ҵ����ضԻ�", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	private void initmSubmit() {
		// TODO �ύ�޸İ�ť
		mSubmit= (Button) findViewById(R.id.revise_submit_bt_id);
		mSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				QA.question = question.getText().toString();
				QA.answer = answer.getText().toString();
				if (QA.question.equals("") || QA.answer.equals("")) {
					Toast.makeText(ReviseQusAnsActivity.this, "�޸ĶԻ�����Ϊ��", Toast.LENGTH_SHORT).show();
				} else {
					Editor editor = sharedPreferences.edit();
					editor.putString(QA.question, QA.answer);
					editor.commit();
					Toast.makeText(ReviseQusAnsActivity.this, "�޸ĳɹ���", Toast.LENGTH_SHORT).show();
					question.setText("");
					answer.setText("");

				}
			}
		});
		
	}
	
}
