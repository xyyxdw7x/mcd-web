package com.asiainfo.biapp.mcd.policy.controller;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.common.plan.vo.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;
import com.asiainfo.biapp.mcd.policy.service.IMcdPolicyService;

import net.sf.json.JSONObject;

/**
 * 政策controller层
 * 
 * @author john0723@outlook.com
 *
 */
@RequestMapping("/action/policy/policyManage")
public class PolicyManageController extends BaseMultiActionController {
	private static Logger log = LogManager.getLogger();
	@Resource(name = "mcdPolicyService")
	private IMcdPolicyService mcdPolicyService;

	
	/**
	 * 查询政策类型/状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public JSONObject queryPlanTypes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject dataJson = new JSONObject();
		List<McdDimPlanType> planTypes = mcdPolicyService.initDimPlanType();// 获取类型
		List<McdDimPlanOnlineStatus> planStatus = mcdPolicyService.initDimPolicyStatus();// 获取状态
		dataJson.put("planTypes", planTypes);
		dataJson.put("planStatus", planStatus);
		return dataJson;
	}
	
	
	
	/**
	 * 创建table列表内容页面：初始化列表数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping
	public Pager queryTableList(HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();

		// 参数中传页面参数pageSize 每一页显示多少条数据
		int pageSize = request.getParameter("pageSize") == null ? McdCONST.PAGE_SIZE: Integer.parseInt(request.getParameter("pageSize"));
		// 当前pageNum
		String pageNum = StringUtils.isNotEmpty(request.getParameter("pageNum")) ? request.getParameter("pageNum"): "1";
		// 关键字keyWords search搜索框会用到
		String keyWords = StringUtils.isNotEmpty(request.getParameter("keyWords")) ? request.getParameter("keyWords"): null;
		// 产品类型：选planTypeId会用到
		String typeId = StringUtils.isNotEmpty(request.getParameter("typeId")) ? request.getParameter("typeId") : null;
		// 状态类型：选statusId会用到
		String statusId = request.getParameter("statusId") != null ? request.getParameter("statusId") : null;
		try {
			pager.setPageSize(pageSize);
			pager.setPageNum(pageNum); // 当前页
			List<Map<String, Object>> list = mcdPolicyService.getPolicyByCondition(typeId, statusId, keyWords, pager);
			pager.setResult(list);
		} catch (Exception e) {
			log.error(e);
		}
		return pager;

	}

	
	
	/**
	 * 查询详情内容
	 * 1.根据请求的planId获取具体的详情内容数据
	 * @param planId
	 * @return
	 */
	@ResponseBody
	@RequestMapping
	public JSONObject queryDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String planId = request.getParameter("planId");
		return mcdPolicyService.queryDetail(planId);
	}
	
	
	
	
	/**
	 * 保存详情数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public String policySaveContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String[]> saveData = request.getParameterMap();
		Boolean plan = mcdPolicyService.savePolicy(saveData);
		String result = "";
		
		if (plan == false) {
			result = "保存失败！";
		} else if (plan == true) {
			result = "保存成功！";
		}
		
		return result;

	}
	
	
}
