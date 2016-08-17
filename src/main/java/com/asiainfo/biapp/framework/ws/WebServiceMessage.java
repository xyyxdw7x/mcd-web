package com.asiainfo.biapp.framework.ws;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * RESTFul风格数据返回值
 * @author hjn
 *
 */
public class WebServiceMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7090496594560749128L;

	/**
	 * 如果jsonpCallbackName不为null则api接口返回jsonp支持跨域
	 */
	@JsonIgnore
	private String jsonpCallbackName=null;
	
	/**
	 * 返回码 例如数据权限、非法URL等
	 * 0代表成功
	 */
	private String resultcode;
	
	public String getJsonpCallbackName() {
		return jsonpCallbackName;
	}

	public void setJsonpCallbackName(String jsonpCallbackName) {
		this.jsonpCallbackName = jsonpCallbackName;
	}

	/**
	 * 返回说明 如果resultcode不为0 则reason不为0
	 */
	private String reason;
	
	/**
	 * 返回结果 包含3个key data和errCode、errMsg
	 */
	private Map<String,Object> result;
	

	public WebServiceMessage() {
		resultcode="200";
		reason="OK";
		result=new HashMap<String, Object>();
	}

	public WebServiceMessage(String resultcode,String reason,HttpServletRequest request) {
		this.resultcode=resultcode;
		this.reason=reason;
		result=new HashMap<String, Object>();
		initJsonpCallbackName(request);
	}
	
	public WebServiceMessage(Object data,HttpServletRequest request) {
		resultcode="200";
		reason="OK";
		result=new HashMap<String, Object>();
		result.put("data", data);
		result.put("errCode", null);
		result.put("errMsg", null);
		initJsonpCallbackName(request);
	}
	
	public WebServiceMessage(Object data,String errorCode,String errorMsg,HttpServletRequest request) {
		resultcode="200";
		reason="OK";
		result=new HashMap<String, Object>();
		result.put("data", data);
		result.put("errCode", errorCode);
		result.put("errMsg", errorMsg);
		initJsonpCallbackName(request);
	}
	
	/**
	 * 设置callback信息
	 * @param request
	 */
	private void initJsonpCallbackName(HttpServletRequest request){
		if(request==null){
			return ;
		}
		String callback=request.getParameter("callback");
		if(!StringUtils.isBlank(callback)){
			this.setJsonpCallbackName(callback);
		}
	}
	
	/**
	 * 成功数据处理
	 * @param data
	 */
	public void setSucResult2(Object data){
		result.put("data", data);
		result.put("errCode", null);
		result.put("errMsg", null);
	}
	/**
	 * 失败数据处理
	 * @param errCode
	 * @param errMsg
	 */
	public void setFalResult2(String errCode,String errMsg){
		result.put("data", null);
		result.put("errCode", errCode);
		result.put("errMsg", errMsg);
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}
}
