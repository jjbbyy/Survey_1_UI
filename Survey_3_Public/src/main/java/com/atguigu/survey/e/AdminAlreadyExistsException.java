package com.atguigu.survey.e;

public class AdminAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AdminAlreadyExistsException(String message) {
		super(message);
	}

}
