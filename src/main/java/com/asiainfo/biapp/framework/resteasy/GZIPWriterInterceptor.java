package com.asiainfo.biapp.framework.resteasy;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.plugins.interceptors.encoding.GZIPEncodingInterceptor;

/**
 * Dubbox对返回的结果进行gzip压缩 压缩能提高网络传输效率 但是会消耗更多的CPU资源
 * @author hjn
 *
 */
@Provider
@ServerInterceptor
public class GZIPWriterInterceptor extends GZIPEncodingInterceptor {

	public GZIPWriterInterceptor() {
		super();
	}

	@Override
	public void aroundWriteTo(WriterInterceptorContext context)
			throws IOException, WebApplicationException {
		context.getHeaders().putSingle(HttpHeaders.CONTENT_ENCODING, "gzip");
		context.getHeaders().putSingle("Access-Control-Allow-Origin","*");
		super.aroundWriteTo(context);
	}

}

