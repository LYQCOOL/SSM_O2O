package com.swpu.o2o.dto;

/**
 * 粉装json对象，所有返回结果都使用它
 * 
 * @author ASUS
 *
 * @param <T>
 */
public class Result<T> {
	// 是否成功标志
	private boolean success;
	// 成功返回的数据
	private T data;
	// 错误信息
	private String errorMssg;
	// 错误码
	private int errorCode;

	public Result() {

	}

	// 成功时的构造器
	public Result(boolean success, T data) {
		this.success = success;
		this.data = data;
	}
	//失败时构造器
	public Result(boolean success,int errorCode,String errorMsg){
		this.success=success;
		this.errorCode=errorCode;
		this.errorMssg=errorMsg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getErrorMssg() {
		return errorMssg;
	}

	public void setErrorMssg(String errorMssg) {
		this.errorMssg = errorMssg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
