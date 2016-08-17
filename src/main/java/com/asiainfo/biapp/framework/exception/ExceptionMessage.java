package com.asiainfo.biapp.framework.exception;

public class ExceptionMessage {

	private String errCode;
	
	public ExceptionMessage() {
		
	}
	
	public ExceptionMessage(Exception e) {
		
	}

	private String errMsg;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
