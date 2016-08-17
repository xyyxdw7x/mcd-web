package com.asiainfo.biapp.framework.web.support;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class ApplicationContextUtil {

	
	/**
	 * 根据bean的名称查找bean实例
	 * 如果已经存在则直接返回
	 * 如果需要根据数据类型查找 找到的bean的名称为 beanName+数据库类型
	 * @param request
	 * @param beanName
	 * @param obj
	 * @param isMultipleDbImpl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getDaoBean(HttpServletRequest request,String beanName,Object obj,boolean isMultipleDbImpl)
	{
		if(obj!=null){
			return obj;
		}
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		Object suc=null;
		if(!isMultipleDbImpl){
			suc=context.getBean(beanName);
		}else{
			Hashtable<String,Object> appConfigProp=(Hashtable<String,Object>)context.getBean("applicationProps");
			String dbType=(String) appConfigProp.get("db.type");
			suc=context.getBean(beanName+dbType);
		}
		return suc;
	}
}
