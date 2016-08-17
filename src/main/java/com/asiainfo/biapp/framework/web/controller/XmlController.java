package com.asiainfo.biapp.framework.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

@Controller
public class XmlController extends AbstractInvokeController {

	@Override
	protected String getViewName(HttpServletRequest request) {
		return "marshallingView";
	}
}
