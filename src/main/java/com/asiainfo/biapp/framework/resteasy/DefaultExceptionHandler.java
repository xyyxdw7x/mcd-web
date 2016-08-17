package com.asiainfo.biapp.framework.resteasy;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.NoLogWebApplicationException;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.asiainfo.biapp.framework.log.LoggerHelper;
import com.asiainfo.biapp.framework.ws.WebServiceMessage;

/**
 * 默认的异常处理
 * @author hjn
 *
 */
@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Exception> {

	
	@Override
	public Response toResponse(Exception e) {
		if(e instanceof NoLogWebApplicationException){
			NoLogWebApplicationException nlwae=(NoLogWebApplicationException) e;
			if(nlwae.getResponse().getStatus()==304){
				return nlwae.getResponse();
			}
		}
		e.printStackTrace();
		LoggerHelper.getInstance().logError(e.getMessage());
		WebServiceMessage wm=null;
		Response response= null;
		if(e instanceof NotFoundException){
			wm=new WebServiceMessage("404","找不到资源",null);
			return response= Response.status(Response.Status.EXPECTATION_FAILED).
					entity(wm).type(ContentType.APPLICATION_JSON_UTF_8).build();
		}else if(e instanceof Exception){
			wm=new WebServiceMessage("500","服务方法内部异常",null);
			return response= Response.status(Response.Status.EXPECTATION_FAILED).
					entity(wm).type(ContentType.APPLICATION_JSON_UTF_8).build();
		}
		//type("text/plain").build();
		return response;
	}
}
