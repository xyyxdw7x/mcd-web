package com.asiainfo.biapp.framework.resteasy;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.cache.server.SimpleServerCache;

import com.asiainfo.biapp.framework.jackson.JacksonJsonPProvider;


/**
 * resteasy总配置
 * 
 * @author hjn
 *
 */
@SuppressWarnings("deprecation")
public class DefaultApplication extends Application {

	public static int num_instantiations = 0;

	protected Set<Object> singletons = new HashSet<Object>();
	protected Set<Class<?>> clazzes = new HashSet<Class<?>>();

	public DefaultApplication() {
		num_instantiations++;
		// singletons.add(new MyResource());
		//resteasy 自带的jsonp的处理 但是如果数据压缩 则该方法失效
		//clazzes.add(JacksonJsonpInterceptor.class);
		clazzes.add(JacksonJsonPProvider.class);
		
		SimpleServerCache ssc=new SimpleServerCache();
		DefaultServerCacheInterceptor sci=new DefaultServerCacheInterceptor(ssc);
		singletons.add(sci);
		
		
		clazzes.add(CacheContainerResponseFilter.class);
		clazzes.add(GZIPWriterInterceptor.class);
		clazzes.add(DefaultExceptionHandler.class);
		clazzes.add(DefaultJacksonObjectMapper.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return clazzes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
