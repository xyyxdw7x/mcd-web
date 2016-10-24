package com.asiainfo.biapp.framework.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.asiainfo.biapp.framework.privilege.service.IUserPrivilege;
import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.util.SpringContextsUtil;

/**
 * 业务Controller的基类
 * @author hjn
 *
 */
public class BaseMultiActionController extends MultiActionController {

	//@Autowired
	//@Qualifier("defaultUserPrivilege")
	@SuppressWarnings("unused")
	private IUserPrivilege userPrivilege;
	
	
	 /**
	  * 获取用户ID
	  * @param request
	  * @param response
	  * @return
	  */
     public String  getUserId(HttpServletRequest request, HttpServletResponse response) {  
        String userId=(String) request.getSession().getAttribute("USER_ID");
        return userId;
     }
     
	 /**
	  * 获取用户
	  * @param request
	  * @param response
	  * @return
	  */
     public User  getUser(HttpServletRequest request, HttpServletResponse response) {  
    	 User user=(User) request.getSession().getAttribute("USER");
        return user;
     }
     
     
	 @ModelAttribute
     public void   setUserId(HttpServletRequest request, HttpServletResponse response) {  
		//request.getSession().setAttribute("USER_ID", "admin");
     }
	 
	public IUserPrivilege getUserPrivilege() {
		IUserPrivilege up=(IUserPrivilege) SpringContextsUtil.getBean("defaultUserPrivilege");
		return up;
	}

	public void setUserPrivilege(IUserPrivilege userPrivilege) {
		this.userPrivilege = userPrivilege;
	}

	
}
