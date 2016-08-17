package com.asiainfo.biapp.framework.jackson;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.asiainfo.biapp.framework.ws.WebServiceMessage;


/**
 * 支持jsonp
 * @author hjn
 *
 */
public class JacksonJsonPProvider extends JacksonJsonProvider {

	@Override
	public void writeTo(Object value, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException {
		//支持jsonp的数据
		if(value instanceof WebServiceMessage){
			WebServiceMessage wm=(WebServiceMessage) value;
			String callbackName=wm.getJsonpCallbackName();
			if(StringUtils.isBlank(callbackName)){
				this.setJSONPFunctionName(null);
			}else{
				this.setJSONPFunctionName(callbackName);
			}
		}
		super.writeTo(value, type, genericType, annotations, mediaType, httpHeaders,
				entityStream);
	}
}
