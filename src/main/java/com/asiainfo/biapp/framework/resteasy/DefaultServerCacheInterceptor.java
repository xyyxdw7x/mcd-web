package com.asiainfo.biapp.framework.resteasy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.jboss.resteasy.plugins.cache.server.ServerCache;
import org.jboss.resteasy.plugins.cache.server.ServerCacheHitFilter;
import org.jboss.resteasy.plugins.cache.server.ServerCacheInterceptor;
import org.jboss.resteasy.spi.NoLogWebApplicationException;

public class DefaultServerCacheInterceptor extends ServerCacheInterceptor {

	public DefaultServerCacheInterceptor(ServerCache cache) {
		super(cache);
	}

   @Override
   public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException
   {
      if (!request.getHttpMethod().equalsIgnoreCase("GET") || request.getAttribute(ServerCacheHitFilter.DO_NOT_CACHE_RESPONSE) != null)
      {
         context.proceed();
         return;
      }

      Object occ = context.getHeaders().getFirst(HttpHeaders.CACHE_CONTROL);
      if (occ == null)
      {
         context.proceed();
         return;
      }
      CacheControl cc = null;

      if (occ instanceof CacheControl) cc = (CacheControl) occ;
      else
      {
         cc = CacheControl.valueOf(occ.toString());
      }

      if (cc.isNoCache())
      {
         context.proceed();
         return;
      }

      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      OutputStream old = context.getOutputStream();
      try
      {
         context.setOutputStream(buffer);
         context.proceed();
         byte[] entity = buffer.toByteArray();
         Object etagObject = context.getHeaders().getFirst(HttpHeaders.ETAG);
         String etag = null;
         if (etagObject == null)
         {
            etag = createHash(entity);
            context.getHeaders().putSingle(HttpHeaders.ETAG, etag);
         }
         else // use application provided ETag if it exists
         {
            etag = etagObject.toString();
         }
         //cache.add(request.getUri().getRequestUri().toString(), context.getMediaType(), cc, context.getHeaders(), entity, etag);

         // check to see if ETags are the same.  If they are, we don't need to send a response back.
         Response.ResponseBuilder validatedResponse = validation.evaluatePreconditions(new EntityTag(etag));
         if (validatedResponse != null)
         {
        	 //validatedResponse.status(Response.Status.NOT_MODIFIED).cacheControl(cc).header(HttpHeaders.ETAG, etag).build();
        	 //return ;
        	 throw new NoLogWebApplicationException(validatedResponse.status(Response.Status.NOT_MODIFIED).cacheControl(cc).build());
         }
         old.write(entity);
      }
      finally
      {
         context.setOutputStream(old);
      }

   }

}
