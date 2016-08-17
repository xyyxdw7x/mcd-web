package com.asiainfo.biapp.framework.log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.asiainfo.biapp.framework.util.JsonUtil;


/**
 * 用户操作日志
 * @author hjn
 *
 */
public class UserOperationLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8014469496053712377L;

	/**
	 * 访问用户id
	 */
	private String userId;
	
	/**
	 * 访问用户key
	 */
	private String key;
	
	/**
	 * 浏览器标识符
	 */
	private String userAgent;
	
	/**
	 * 访问时间
	 */
	private Date date;
	
	
	/**
	 * 访问的URL
	 */
	private String url;
	
	/**
	 * 用户的IP地址
	 */
	private String ip;
	
	/**
	 * 访问的服务接口名
	 */
	private String serviceName;
	
	/**
	 * 访问的方法名
	 */
	private String methodName;
	
	/**
	 * 输入的参数
	 */
	private Object[] args;
	
	/**
	 * 请求来源
	 * @return
	 */
	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	private String referer;
	
	/**
	 * 生成一个sql片段
	 * @return
	 */
	public String toSqlString(){
		StringBuffer sb=new StringBuffer("(");
		if(this.getUserId()!=null){
			sb.append("'"+this.getUserId()+"'");
		}else{
			sb.append("''");
		}
		sb.append(",");
		if(this.getKey()!=null){
			sb.append("'"+this.getKey()+"'");
		}else{
			sb.append("''");
		}
		sb.append(",");
		sb.append("'"+this.getIp()+"'");
		sb.append(",");
		sb.append("'"+this.getUserAgent()+"'");
		sb.append(",");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(this.getDate());
		sb.append("'"+dateString+"'");
		sb.append(",");
		sb.append("'"+this.getUrl()+"'");
		sb.append(",");
		sb.append("'"+this.getServiceName()+"'");
		sb.append(",");
		sb.append("'"+this.getMethodName()+"'");
		sb.append(",");
		if(this.getArgs()!=null){
			String argsStr=JsonUtil.objectJSONString(this.getArgs());
			sb.append("'"+argsStr+"'");
		}else{
			sb.append("''");
		}
		sb.append(",");
		if(this.getReferer()!=null){
			sb.append("'"+this.getReferer()+"'");
		}else{
			sb.append("''");
		}
		sb.append(")");
		return sb.toString();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
}
