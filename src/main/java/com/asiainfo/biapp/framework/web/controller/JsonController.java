package com.asiainfo.biapp.framework.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

@Controller
public class JsonController extends AbstractInvokeController {

	@Override
	protected String getViewName(HttpServletRequest request) {
		return "jsonView";
	}
}
