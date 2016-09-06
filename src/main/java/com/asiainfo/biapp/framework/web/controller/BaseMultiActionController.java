package com.asiainfo.biapp.framework.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.asiainfo.biapp.mcd.jms.util.JmsJsonUtil;

import net.sf.json.JSONObject;

/**
 * 业务Controller的基类
 * @author hjn
 *
 */
public class BaseMultiActionController extends MultiActionController {

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
				String str = JmsJsonUtil.obj2Json(obj);
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
}
