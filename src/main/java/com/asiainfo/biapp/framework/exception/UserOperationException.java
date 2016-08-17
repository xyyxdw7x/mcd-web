package com.asiainfo.biapp.framework.exception;

/**
 * 用户操作异常 类型与url参数不正确等
 * @author hjn
 *
 */
public class UserOperationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3492840392057465816L;

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
