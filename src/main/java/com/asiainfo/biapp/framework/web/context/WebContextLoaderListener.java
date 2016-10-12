package com.asiainfo.biapp.framework.web.context;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.asiainfo.biapp.framework.core.AppConfigService;

/**
 * spring 加载监听器
 * 
 * @author hjn
 *
 */
public class WebContextLoaderListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String profiles = event.getServletContext().getInitParameter("spring.profiles.active");
		AppConfigService.PROFILE_ACTIVE = profiles;
		super.contextInitialized(event);
	}

}
