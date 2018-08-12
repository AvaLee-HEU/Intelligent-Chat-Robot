package com.example.irobot.bean;

import java.io.Serializable;

public class QusAnsRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	public String question;
	public String answer;

	public QusAnsRecord() {

	}

	public QusAnsRecord(String question, String answer) {
		super();
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
