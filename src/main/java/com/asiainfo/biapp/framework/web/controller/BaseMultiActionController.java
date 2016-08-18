package com.asiainfo.biapp.framework.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

/**
 * 业务Controller的基类
 * 
 * @author hjn
 *
 */
public class BaseMultiActionController extends MultiActionController {

	public String getUserId(HttpServletRequest request, HttpServletResponse response) {
		String userId = (String) request.getSession().getAttribute("USER_ID");
		return userId;
	}

	@ModelAttribute
	public void setUserId(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("USER_ID", "admin");
	}
	
	protected void outJson4Ws(HttpServletResponse response, Object obj, String status, String errorMsg)throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		
		JSONObject dataJson = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		if ("200".equals(status)) {	
			String str = mapper.writeValueAsString(obj);
			dataJson.put("data", str);
		} else {
			dataJson.put("result", errorMsg);
			dataJson.put("data", "");
		}
		dataJson.put("status", status);

		PrintWriter out = response.getWriter();
		out.print(dataJson);
		out.flush();
		out.close();
	}
}
