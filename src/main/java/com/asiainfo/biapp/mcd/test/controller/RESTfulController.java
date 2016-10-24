package com.asiainfo.biapp.mcd.test.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.asiainfo.biapp.framework.privilege.vo.User;

@RestController
@RequestMapping("/action/test")
public class RESTfulController {

	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/test.do")
	@GET
	@ResponseBody 
	public Map<String,Object> qu2eryAllYear(@Context HttpServletRequest request) throws Exception {
		Map<String,Object> map=(Map<String, Object>) new HashMap<String,Object>();
		map.put("dasd", "1232");
		map.put("dasd2", 11);
		return map;
	}
	
	@RequestMapping("/test3.do")
	@GET
	@ResponseBody 
	public User qu2eryAllYear3(@Context HttpServletRequest request) throws Exception {
		User user=new User();
		user.setCityId("1");
		user.setName("dasdas");
		return user;
	}
	
	
	@RequestMapping("/test2.do")
	@GET
	@ResponseBody 
	public Map<String,Object> qu2eryAllYear2(@Context HttpServletRequest request) throws Exception {
		Map<String,Object> map=(Map<String, Object>) new HashMap<String,Object>();
		map.put("dasd", "1232");
		map.put("dasd2", 11);
		String url="http://localhost:8080/mcd-web/action/test/test3.do";
		Map<String,Object> ss=restTemplate.getForObject(url, Map.class);
		System.out.println(ss);
		map.put("dasd21", ss);
		return map;
	}
	
}
