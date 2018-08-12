package com.example.irobot;

import java.util.ArrayList;
import java.util.List;

import com.example.irobot.bean.QusAnsRecord;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddQusAnsActivity extends Activity {
	private List<QusAnsRecord> Datalist;
	private AddQusAnsAdapter MyAdapter;
	private ListView listview;
	private QusAnsRecord QA;
	private EditText question, answer;
	private Button addQAbutton;
	public SharedPreferences sharedPreferences;
	private int qus = 0, ans = 0, arg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO onCreate()
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addqa_lo);
		sharedPreferences = getSharedPreferences(getPackageName() + "addqa", Context.MODE_PRIVATE);
		newsharedPreferences();
		addAssignment();
		addQusAns();

	}

	private void addQusAns() {
		// TODO addQusAns()
		// 点击事件方法
		addQAbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				QA.question = question.getText().toString();
				QA.answer = answer.getText().toString();
				if (QA.question.equals("") || QA.answer.equals("")) {
					Toast.makeText(AddQusAnsActivity.this, "添加对话不能为空", Toast.LENGTH_SHORT).show();
				} else {
					Editor editor = sharedPreferences.edit();
					editor.putString(QA.question, QA.answer);
					editor.commit();
					Toast.makeText(AddQusAnsActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
					question.setText("");
					answer.setText("");

				}

			}

		});

	}

	private void newsharedPreferences() {
		// TODO newsharedPreferences()
		sharedPreferences = getSharedPreferences(getPackageName() + "addqa", Context.MODE_PRIVATE);

	}

	private void addAssignment() {
		// TODO Auto-generated method stub
		Datalist = new ArrayList<QusAnsRecord>();
		question = (EditText) findViewById(R.id.add_question_et_id);
		answer = (EditText) findViewById(R.id.add_answer_et_id);
		addQAbutton = (Button) findViewById(R.id.add_qa_bt_id);
		QA = new QusAnsRecord();
	}

}
