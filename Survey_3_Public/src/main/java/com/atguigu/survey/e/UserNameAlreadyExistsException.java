package com.atguigu.survey.e;

public class UserNameAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UserNameAlreadyExistsException() {
		
	}

	public UserNameAlreadyExistsException(String message) {
		super(message);
	}

}
