package com.swpu.o2o.exceptions;

public class WechatAuthOperationException extends RuntimeException {
	
	/**
	 * 微信信息相关异常
	 */
	private static final long serialVersionUID = 1068291683977176452L;

	public WechatAuthOperationException(String msg) {
		super(msg);
	}

}
