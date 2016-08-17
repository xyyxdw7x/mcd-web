package com.asiainfo.biapp.framework.aop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.asiainfo.biapp.framework.exception.SysException;
import com.asiainfo.biapp.framework.util.MD5Util;
import com.asiainfo.biapp.framework.ws.WebServiceMessage;



/**
 * URL后面必须带有一个key 用来验证身份
 * @author hjn
 *
 */
public class OauthSecurityInterceptor implements MethodInterceptor,InitializingBean {

	/**
	 * 有效的key集合
	 */
	public static Map<String,String> keyMap=new HashMap<String, String>();
	
	private String sysNames;
	
	private boolean test;
	
	/**
	 * 排除不进行权限验证的url
	 */
	private List<String> excludePathInfos;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		//传入的参数
		Object[] args=invocation.getArguments();
		if(args.length==0){
			//方法参数中至少有一个HttpServletRequest参数
			throw new SysException("001");
		}
		if(!(args[args.length-1] instanceof HttpServletRequest)){
			throw new SysException("002");
		}
		if(!test){
			HttpServletRequest request=(HttpServletRequest) args[args.length-1];
			String key=request.getParameter("key");
			if(!verificationPathInfo(request.getPathInfo())){
				key=createDefaultKey();
				request.setAttribute("key", key);
			}
			if(!verificationKey(key)){
				WebServiceMessage wm=new WebServiceMessage("403","无权限访问",request);
				return wm;
			}
		}
		Object suc=invocation.proceed();;
		return suc;
	}
	
	public List<String> getExcludePathInfos() {
		return excludePathInfos;
	}

	public void setExcludePathInfos(List<String> excludePathInfos) {
		this.excludePathInfos = excludePathInfos;
	}

	public OauthSecurityInterceptor() {
		
	}
	
	/**
	 * 验证该url是否需要进行安全验证
	 * 如果返回true则需要进行验证 否则不需要
	 * @param pathInfo
	 * @return
	 */
	private boolean verificationPathInfo(String pathInfo){
		boolean suc=true;
		if(StringUtils.isBlank(pathInfo)){
			return suc;
		}
		for(int i=0,size=excludePathInfos.size();i<size;i++){
			String url=excludePathInfos.get(i);
			if(url.endsWith(pathInfo)){
				suc=false;
				break;
			}
		}
		return suc;
	}

	/**
	 * 验证key的有效性
	 * @param key
	 * @return
	 */
	private boolean verificationKey(String key){
		boolean suc=false;
		if(StringUtils.isBlank(key)){
			return suc;
		}
		if(OauthSecurityInterceptor.keyMap.get(key)!=null){
			suc=true;
		}
		return suc;
	}
	
	/**
	 * 每天生成新的key
	 */
	public void createKey(){
		if(getSysNames().equals("")){
			return ;
		}
		String[] keys=getSysNames().split(",");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(new Date());
		OauthSecurityInterceptor.keyMap=new HashMap<String, String>();
		for (int i = 0,size=keys.length; i < size; i++) {
			String value=keys[i]+"_"+dateString;
			String key=MD5Util.encode(value);
			keyMap.put(key, value);
		}
	}
	
	/**
	 * 生成默认的key 
	 * @return
	 */
	public String createDefaultKey(){
		String[] keys=getSysNames().split(",");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(new Date());
		String value=keys[0]+"_"+dateString;
		String key=MD5Util.encode(value);
		return key;
	}

	public String getSysNames() {
		return sysNames;
	}

	public void setSysNames(String sysNames) {
		this.sysNames = sysNames;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		createKey();
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

}
