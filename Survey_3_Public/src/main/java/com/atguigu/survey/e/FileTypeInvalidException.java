package com.atguigu.survey.e;

public class FileTypeInvalidException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FileTypeInvalidException(String message) {
		super(message);
	}

}
