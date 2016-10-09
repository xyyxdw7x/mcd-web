package com.asiainfo.biapp.framework.filter;

import java.io.IOException;

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
import org.apache.http.HttpStatus;

/**
 * 用户登录验证过滤器
 * @author hjn
 *
 */
public class OauthLoginFilter implements Filter {

	protected final Log log = LogFactory.getLog(getClass());
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hsr=(HttpServletRequest)request;
		HttpServletResponse hsrs=(HttpServletResponse)response;
		String url=hsr.getRequestURI().toLowerCase();
		if(url.endsWith(".jpg")||url.endsWith(".png")||url.endsWith(".ico")||url.endsWith(".gif")||url.endsWith(".js")||url.endsWith(".css")){
			chain.doFilter(request, response);
			return ;
		}
		if(url.endsWith("/action/privilege/login/login.do")||url.endsWith("/login/login.jsp")){
			chain.doFilter(request, response);
			return ;
		}
		String contextPath=hsr.getContextPath();
		if(hsr.getSession().getAttribute("USER_ID")==null){
			boolean isAjaxRequest = isAjaxRequest(hsr);  
	        if (isAjaxRequest)  
	         {  
	        	 hsr.setCharacterEncoding("UTF-8");
	        	 hsrs.sendError(HttpStatus.SC_UNAUTHORIZED, "您已经太长时间没有操作,请刷新页面");
	             return ;  
	         }else{
	        	 hsrs.sendRedirect(contextPath+"/login/login.jsp");
	         }
		}else{
			chain.doFilter(request, response);
			return ;
		}
		
	}
	
	public  boolean isAjaxRequest(HttpServletRequest request)  
    {  
        String header = request.getHeader("X-Requested-With");   
        if (header != null && "XMLHttpRequest".equals(header))   
            return true;   
        else   
            return false;    
    } 

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
