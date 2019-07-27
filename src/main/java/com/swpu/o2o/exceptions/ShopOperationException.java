package com.swpu.o2o.exceptions;

//做一层很薄的封装，意义在于更好的理解
public class ShopOperationException extends RuntimeException {
	/**
	 * 生成随机序列化id
	 */
	private static final long serialVersionUID = -323715506711251329L;

	public ShopOperationException(String msg) {
		super(msg);
	}

}
