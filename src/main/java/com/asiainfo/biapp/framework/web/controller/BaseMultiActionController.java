package com.asiainfo.biapp.framework.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.asiainfo.biapp.framework.core.AppConfigService;
import com.asiainfo.biapp.framework.privilege.service.IUserPrivilege;
import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.util.SpringContextsUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

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
	 * 系统配置项
	 */
	@Autowired
	private AppConfigService configService;
	
	
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
	 
	 /**
		 * 
		 * @param response
		 * @param obj
		 *            发送给客户端的对象，最终将转化成json对象的data属性
		 * @param status
		 * @param errorMsg
		 *            后台处理异常时传递给前端的信息
		 * @throws IOException
		 */
		protected void outJson4Ws(HttpServletResponse response, Object obj, String status, String errorMsg) {
			JSONObject dataJson = new JSONObject();
			if ("200".equals(status)) {
				String str = this.obj2Json(obj);
				dataJson.put("data", str);
			} else {
				dataJson.put("result", errorMsg);
				dataJson.put("data", "");
			}
			dataJson.put("status", status);
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("progma", "no-cache");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(dataJson);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		/**
		 * 对象转换为json字符串
		 * @param obj
		 * @return
		 */
		@SuppressWarnings("deprecation")
		private  String obj2Json(Object obj) {
			ObjectMapper om = new ObjectMapper();
			
			String jsonStr = "{}";
			JsonGenerator jsonGenerator = null;
			try {
				if (obj != null) {
					StringWriter sw = new StringWriter();
					jsonGenerator = om.getJsonFactory().createJsonGenerator(sw);
					jsonGenerator.writeObject(obj);
					jsonStr = sw.toString();
				}
			} catch (Exception e) {

			} finally {
				if (jsonGenerator != null) {
					try {
						jsonGenerator.close();
					} catch (IOException e) {
					}
				}
			}
			return jsonStr;
		}

		
	public IUserPrivilege getUserPrivilege() {
		IUserPrivilege up=(IUserPrivilege) SpringContextsUtil.getBean("defaultUserPrivilege");
		return up;
	}

	public void setUserPrivilege(IUserPrivilege userPrivilege) {
		this.userPrivilege = userPrivilege;
	}

	public AppConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(AppConfigService configService) {
		this.configService = configService;
	}
}
