package com.asiainfo.biapp.framework.filter;

import java.util.Set;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

/**
 * 跨域访问api的过滤器
 * @author hjn
 *
 */
public class ApiCorsFilter extends CorsFilter {

	@Override
	public Set<String> getAllowedOrigins() {
		return super.getAllowedOrigins();
	}

}
