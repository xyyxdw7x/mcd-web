/**
 * 
 */
package com.asiainfo.biapp.mcd.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.asiainfo.biapp.mcd.tactics.thread.McdCreateDuserTableRunnable;

/**  
 * @Description: 初始化监听<br> 
 * @author zuowg <br>
 * @date 2015-9-23 上午09:55:42 <br>
 * Copyright: (C) Copyright 1993-2010 AsiaInfo Holdings, Inc<br>
 * Company: 北京亚信智慧数据科技有限公司
 */
public class InitApplicationListener implements ServletContextListener{

	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*4);
	
	/**全局缓存Map*/
	public static Map<String,Object> cacheDatas = new HashMap<String, Object>();
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		cacheDatas = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		executor.execute(new McdCreateDuserTableRunnable());
	}
}
