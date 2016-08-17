package com.asiainfo.biapp.framework.core.context;

import org.springframework.core.Ordered;

/**
 * 在容器初始化完毕要执行的接口
 * @author Administrator
 *
 */
public interface IApplicationContextRefreshed extends Ordered {

	/**
	 * 要执行的方法
	 * @throws Exception
	 */
	public boolean executeAuto();
}
