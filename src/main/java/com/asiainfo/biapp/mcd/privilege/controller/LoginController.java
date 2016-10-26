package com.asiainfo.biapp.mcd.privilege.controller;


import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.biapp.framework.core.AppConfigService;
import com.asiainfo.biapp.framework.privilege.vo.Menu;
import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;

@RequestMapping("/action/privilege/login")
public class LoginController extends BaseMultiActionController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	
	@RequestMapping()
	@ResponseBody
	public boolean login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean suc=false;
		String userId=request.getParameter("userId");
		String userPwd=request.getParameter("userPwd");
		User user=this.getUserPrivilege().validationUserPwd(userId, userPwd);
		if(user!=null){
			request.getSession().setAttribute("USER_MENU",null);
			request.getSession().setAttribute("USER_ID", user.getId());
			request.getSession().setAttribute("USER", user);
			ServletContext application=this.getServletContext();
			String provinces=AppConfigService.PROFILE_ACTIVE;
			application.setAttribute("APP_PROVINCES",provinces);
			suc=true;
		}
		return suc;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping()
	@ResponseBody
	public List<Menu> getUserMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId=getUserId(request, response);
		List<Menu> list=null;
		list=(List<Menu>) request.getSession().getAttribute("USER_MENU");
		if(list!=null){
			return list;
		}else{
			list=this.getUserPrivilege().getUserMenuInfos(userId,true);
			request.getSession().setAttribute("USER_MENU",list);
		}
		return list;
	}	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping()
	public ModelAndView getUserMenuAll(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("common/menu");
		String userId=getUserId(request, response);
		List<Menu> list=(List<Menu>) request.getSession().getAttribute("USER_MENU");
		if(list==null){
			list=this.getUserPrivilege().getUserMenuInfos(userId,false);
			request.getSession().setAttribute("USER_MENU",list);
		}
		model.addObject("menus", list);
		return model;
	}
	
	@RequestMapping()
	@ResponseBody
	public ModelAndView loginOut(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("USER_ID",null);
		ModelAndView model = new ModelAndView("redirect:/login/login.jsp");
		return model;
	}
	
}
