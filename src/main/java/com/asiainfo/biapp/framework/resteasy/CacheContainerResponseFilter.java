package com.asiainfo.biapp.framework.resteasy;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

public class CacheContainerResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext req,
			ContainerResponseContext res) throws IOException {
		if (req.getMethod().equals("GET")) {
			String path=req.getUriInfo().getPath();
			if(!path.endsWith("/manager/flush")){
				//10个小时的缓存时间 
	            if(!res.getHeaders().containsKey("Cache-Control")){
	            	 res.getHeaders().addFirst("Cache-Control", "max-age=36000");
	            }
			}
        }
	}
}
