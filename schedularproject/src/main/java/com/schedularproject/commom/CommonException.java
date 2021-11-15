package com.schedularproject.commom;

public class CommonException extends RuntimeException{

	private static final long serialVersionUID = -5226208827344858886L;

	public CommonException(String errorMessage){
		super(errorMessage);
	}
}
