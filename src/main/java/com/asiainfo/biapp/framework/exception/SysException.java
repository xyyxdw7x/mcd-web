package com.asiainfo.biapp.framework.exception;

/**
 * 系统异常 类似数据类型转换异常 数据库sql等
 * @author hjn
 *
 */
public class SysException extends Exception {

	public SysException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6373337624907261555L;

	private String errorCode;

	private String errorMsg;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
