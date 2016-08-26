package com.asiainfo.biapp.mcd.custgroup.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.service.CustGroupInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMcdMtlGroupInfoService;
import com.asiainfo.biapp.mcd.tactics.vo.MtlGroupInfo;
import com.asiainfo.biframe.utils.spring.SystemServiceLocator;
import com.asiainfo.biframe.utils.string.StringUtil;

import net.sf.json.JSONObject;

/**
 * 
 * Title: 新建策略页面：选择营销人群 action
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-17 下午04:15:26
 * @version 1.0
 */

public class CustGroupManagerController extends BaseMultiActionController{
	@Resource(name="custGroupInfoService")
	private CustGroupInfoService custGroupInfoService;
	public void setCustGroupInfoService(CustGroupInfoService custGroupInfoService) {
		this.custGroupInfoService = custGroupInfoService;
	}
	/**
	 * 我的客户群点击更多按钮   关键字查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void getMoreMyCustom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Pager pager=new Pager();
		//TODO: initActionAttributes(request);
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		String keyWords = StringUtil.isNotEmpty(request.getParameter("keyWords")) ? request.getParameter("keyWords") : null;
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		try {
			String clickQueryFlag = "true";
			pager.setPageSize(6);  //每页显示6条
			pager.setPageNum(pageNum);  //当前页
			if(StringUtil.isNotEmpty(pageNum)){
				pager.setPageFlag("G");	
			}
			//TODO: pager.setTotalSize(custGroupInfoService.getMoreMyCustomCount(user.getUserid(),keyWords));
			pager.setTotalSize(custGroupInfoService.getMoreMyCustomCount("chenyg",keyWords));
			pager.getTotalPage();
			if ("true".equals(clickQueryFlag)) {
				//TODO:List<MtlGroupInfo> resultList = custGroupInfoService.getMoreMyCustom(user.getUserid(),keyWords,pager);
				List<MtlGroupInfo> resultList = custGroupInfoService.getMoreMyCustom("chenyg",keyWords,pager);
				pager = pager.pagerFlip();
				pager.setResult(resultList);
			} else {
				pager = pager.pagerFlip();
				//TODO:List<MtlGroupInfo> resultList = custGroupInfoService.getMoreMyCustom(user.getUserid(),keyWords,pager);
				List<MtlGroupInfo> resultList = custGroupInfoService.getMoreMyCustom("chenyg",keyWords,pager);
				pager.setResult(resultList);
			}
			dataJson.put("status", "200");
			dataJson.put("data", JmsJsonUtil.obj2Json(pager));
			out.print(dataJson);
		} catch (Exception e) {
			dataJson.put("status", "201");
			out.print(dataJson);
		}finally{
			out.flush();
			out.close();
		}
		
	}

}
