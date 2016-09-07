package com.asiainfo.biapp.framework.aop;

/**
 *  spring bean 获得自身的代理对象
 * 
 * @author hjn
 *
 */
public interface BeanSelfAware {

	/**
	 * 从spring中获得bean并赋值
	 * @param proxyObj
	 */
	public void setSelfProxy(Object proxyObj);
}
