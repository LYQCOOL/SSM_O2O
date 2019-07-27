package com.swpu.o2o.exceptions;

public class LocalAuthOperationException extends RuntimeException{
	
	/**
	 * 用户操作相关异常
	 */
	private static final long serialVersionUID = 7487152110724196304L;

	public LocalAuthOperationException(String msg){
		super(msg);
	}

}
