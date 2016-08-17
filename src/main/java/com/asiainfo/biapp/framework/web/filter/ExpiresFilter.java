package com.asiainfo.biapp.framework.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExpiresFilter implements Filter {

	//log日志 
    private Log log = LogFactory.getLog(getClass());
     
    //记录客户端需要缓存的静态文件类型及缓存时间 KEY=文件类型(String型)，VALUE=缓存时间(Long型)
    private Map<String,Object> map = new HashMap<String,Object>();
    
	@SuppressWarnings("rawtypes")
	@Override
	public void init(FilterConfig config) throws ServletException {
		Enumeration en = config.getInitParameterNames();
        while(en.hasMoreElements()){
            //取得静态文件类型
            String paramName     = en.nextElement().toString();
            if(paramName == null || paramName.equals("")) continue;
             
            //取得缓存时间 。单位：秒
            String paramValue     = config.getInitParameter(paramName);
            try{
                int time = Integer.valueOf(paramValue);
                if(time > 0){
                    //存入MAP中
                    map.put(paramName, new Long(time));
                    log.info("Set " + paramName + "expires seconds: " + time);
                }
            } catch (Exception e){
                log.warn("Exception in initilazing ExpiredFilter. Set " + paramName + " error", e);
            }
        }
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		  //取得当前访问的URI
        String uri = ((HttpServletRequest)request).getRequestURI();
         
        int n = uri.lastIndexOf(".");
         
        if(n != (-1)){
            //取得访问资源的扩展名
            String ext = uri.substring(n);
 
            Long exp = (Long)map.get(ext);
             
            if(exp != null){
                HttpServletResponse resp = (HttpServletResponse) response;
                //设置缓存
                resp.setHeader("Cache-Control", "max-age=" + exp);
                //设置过期时间
                resp.setDateHeader("Expires", System.currentTimeMillis() + exp*1000);
            }
        }
         
        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
