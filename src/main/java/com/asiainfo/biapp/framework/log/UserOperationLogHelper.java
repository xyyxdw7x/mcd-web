package com.asiainfo.biapp.framework.log;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.asiainfo.biapp.framework.aop.OauthSecurityInterceptor;


/**
 * UserOperationLog助手类
 * @author hjn
 *
 */
public class UserOperationLogHelper {

	/**
	 * 用户操作日志log
	 */
	public static final String OPERATION_LOG="operationLog";
	/**
	 * 用户操作日志log的appender
	 */
	public static final String OPERATION_LOG_APPENDER="operationLogAppender";
	
	/**
	 * 创建一个UserOperationLog
	 * @param invocation
	 * @param request
	 * @param userId
	 * @param key 
	 * @return
	 */
	public static UserOperationLog createUserOperationLog(MethodInvocation invocation,HttpServletRequest request,Object[] args,String key){
		UserOperationLog uol=new UserOperationLog();
		uol.setKey(key);
		String userId=null;
		if(OauthSecurityInterceptor.keyMap.get(key)!=null){
			userId=OauthSecurityInterceptor.keyMap.get(key).split("_")[0];
		}    
		uol.setUserId(userId);
		
		Method method=invocation.getMethod();
		Class<?> clazz = method.getDeclaringClass();
		//调用的类名
		String targetClassName=clazz.getName();
		uol.setServiceName(targetClassName);
		//调用的方法名
		String methodName=method.getName();
		uol.setMethodName(methodName);
		//todo 去掉最后一个request
		uol.setArgs(ArrayUtils.subarray(args, 0, args.length-1));
		
		//日志里面只保留最原始的数据 StringTokenizer
		String userAgent=request.getHeader("User-Agent");
		uol.setUserAgent(userAgent);
		
		//获得客户端的IP地址  
		String ip=request.getRemoteAddr();
		uol.setIp(ip);
		
		String referer=request.getHeader("Referer");
		uol.setReferer(referer);
		
		String url=request.getRequestURL().toString();
		uol.setUrl(url);
		
		uol.setDate(new Date());
		return uol;
	}
	
	/**
	 * 
	 * @param uol
	 * @return
	 */
	public static JDBCLogTask createLogTask(UserOperationLog uol){
		String sql=uol.toSqlString();
		Logger logger=Logger.getLogger(UserOperationLogHelper.OPERATION_LOG);
		JDBCLogTask task=new JDBCLogTask(sql,logger);
		return task;
	}
}
