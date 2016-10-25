package com.asiainfo.biapp.mcd.tacticsApprove.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.tacticsApprove.service.TacticsApproveService;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;

/**
 * Created on 2016-10-21 10:00:00
 * 
 * <p>
 * Title:策略审批/p>
 * <p>
 * Description: 策略审批
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: asiainfo.,Ltd
 * </p>
 * 
 * @author zhaokai
 * @version 1.0
 */
@Controller
@RequestMapping("/action/TacticsApprove")
public class TacticsApproveController extends BaseMultiActionController {

	private static final Logger log = LogManager.getLogger();
	
	@Resource(name="tacticsApproveService")
	private TacticsApproveService tacticsApproveService;
	
	/**
	 * 待审批策略信息查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public Pager queryApproveInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Pager pager = new Pager();
		String pageNum = StringUtils.isNotEmpty(request.getParameter("pageNum")) ? request.getParameter("pageNum"): "1";
		int pageSize = request.getParameter("pageSize")==null?McdCONST.PAGE_SIZE:Integer.parseInt( request.getParameter("pageSize"));
		try {
			pager.setPageSize(pageSize); 
			pager.setPageNum(pageNum); // 当前页
			List<Map<String, Object>> list = tacticsApproveService.getTacticsApproveInfo(pager);
			pager.setResult(list);
		} catch (Exception e) {
			log.error("查询产品列表失败",e);
		}
		return pager;
	}

}
