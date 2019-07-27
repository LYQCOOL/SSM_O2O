package com.swpu.o2o.exceptions;

public class ProductCategoryOperationException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6883200811234197963L;

	//自定义商品添加异常
	public ProductCategoryOperationException(String msg){
		super(msg);
	}
}
