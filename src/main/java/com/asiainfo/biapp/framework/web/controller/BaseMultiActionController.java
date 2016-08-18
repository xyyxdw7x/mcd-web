package com.asiainfo.biapp.framework.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * 业务Controller的基类
 * @author hjn
 *
 */
public class BaseMultiActionController extends MultiActionController {

     public String  getUserId(HttpServletRequest request, HttpServletResponse response) {  
        String userId=(String) request.getSession().getAttribute("USER_ID");
        return userId;
     }
	 @ModelAttribute
     public void   setUserId(HttpServletRequest request, HttpServletResponse response) {  
		request.getSession().setAttribute("USER_ID", "admin");
     }
}
