package com.asiainfo.biapp.framework.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring上下文
 * @author hjn
 *
 */
public class ApplicationContextUtil implements ApplicationContextAware {

	// Spring应用上下文环境  
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		ApplicationContextUtil.applicationContext=context;
	}
	public static ApplicationContext getApplicationContext() {  
		return applicationContext;  
	}
	public static Object getBean(String name) throws BeansException {  
       return applicationContext.getBean(name);  
	}  
}
