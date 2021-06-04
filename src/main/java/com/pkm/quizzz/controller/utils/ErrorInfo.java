package com.pkm.quizzz.controller.utils;

public class ErrorInfo {
	public final String url;
	public final String message;
	public final Integer code;

	//класс для формирования ошибок

	public ErrorInfo(String url, Exception ex, Integer code) {
		this.url = url;
		this.message = ex.getLocalizedMessage();
		this.code = code;
	}

}
