package com.asiainfo.biapp.mcd;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.quota.service.impl.DeptMothQuotaServiceImp;

@RequestMapping("/test")
public class TestController extends BaseMultiActionController {
	@Resource(name="quotaConfigDeptMothService")
	private DeptMothQuotaServiceImp quotaConfigDeptMothService;
	
	@RequestMapping("/test")
	public String test(HttpServletRequest request,HttpServletResponse response) throws Exception {
		quotaConfigDeptMothService.averageCityMonthQuota();
		quotaConfigDeptMothService.setDeptsCurrentMonthQuota();
		return "333";
	} 
	
}
