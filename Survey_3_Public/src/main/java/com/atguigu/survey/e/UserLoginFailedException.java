package com.atguigu.survey.e;

public class UserLoginFailedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserLoginFailedException(String message) {
		super(message);
	}

}
