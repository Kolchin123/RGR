package com.pkm.quizzz.model.support;

public class Response {
	private Long question;
	private Long selectedAnswer;

	//сущность ответов для возврата в html

	public Long getQuestion() {
		return question;
	}

	public void setQuestion(Long question) {
		this.question = question;
	}

	public Long getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(Long selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

}
