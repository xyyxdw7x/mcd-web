package com.asiainfo.biapp.mcd.privilege.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiainfo.biapp.framework.privilege.service.IUserPrivilege;
import com.asiainfo.biapp.framework.privilege.vo.Menu;
import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;

@RequestMapping("/privilege/login")
public class LoginController extends BaseMultiActionController {

	protected final Log logger = LogFactory.getLog(getClass());
	//@Autowired
	@Resource(name="defaultUserPrivilege")
	private IUserPrivilege userPrivilege;
	
	@RequestMapping("/login")
	@ResponseBody
	public boolean login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean suc=false;
		String userId=request.getParameter("userId");
		String userPwd=request.getParameter("userPwd");
		User user=userPrivilege.validationUserPwd(userId, userPwd);
		if(user!=null){
			request.getSession().setAttribute("USER_ID", user.getId());
			suc=true;
		}
		return suc;
	}
	
	@RequestMapping("/getUserMenu")
	@ResponseBody
	public List<Menu> getUserMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId=getUserId(request, response);
		List<Menu> list=userPrivilege.getUserMenuInfos(userId);
		return list;
	}
	
	public IUserPrivilege getUserPrivilege() {
		return userPrivilege;
	}

	public void setUserPrivilege(IUserPrivilege userPrivilege) {
		this.userPrivilege = userPrivilege;
	}
}