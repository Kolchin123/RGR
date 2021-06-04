package com.pkm.quizzz.exceptions;

public class ActionRefusedException extends QuizZzException {

	//различные исключения во всех классов этого пакета

	private static final long serialVersionUID = 1L;

	public ActionRefusedException() {
		super();
	}

	public ActionRefusedException(String message) {
		super(message);
	}
}