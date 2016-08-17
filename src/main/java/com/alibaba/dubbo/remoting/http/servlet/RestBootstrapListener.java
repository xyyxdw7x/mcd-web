package com.alibaba.dubbo.remoting.http.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.plugins.server.servlet.ListenerBootstrap;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 * Resteasy和Dubbo的Listener的集成
 * @author hjn
 *
 */
public class RestBootstrapListener extends BootstrapListener {

	protected ResteasyDeployment deployment;
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		deployment.stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();

		ListenerBootstrap config = new ListenerBootstrap(event.getServletContext());
		deployment = config.createDeployment();
		deployment.start();

		servletContext.setAttribute(ResteasyProviderFactory.class.getName(),deployment.getProviderFactory());
		servletContext.setAttribute(Dispatcher.class.getName(),deployment.getDispatcher());
		servletContext.setAttribute(Registry.class.getName(),deployment.getRegistry());
		servletContext.setAttribute(ResteasyDeployment.class.getName(), deployment);
		super.contextInitialized(event);
	}

}
