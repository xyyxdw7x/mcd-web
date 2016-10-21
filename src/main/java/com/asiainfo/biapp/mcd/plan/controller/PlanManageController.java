package com.asiainfo.biapp.mcd.plan.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.common.plan.vo.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.plan.service.IMcdPlanService;
import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

import net.sf.json.JSONObject;

@RequestMapping("/plan/planManage")
public class PlanManageController extends BaseMultiActionController {
	private static Logger log = LogManager.getLogger();
	@Resource(name = "mcdPlanService")
	private IMcdPlanService mcdPlanService;
	
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
		List<McdDimPlanType> planTypes = mcdPlanService.initDimPlanType();// 获取类型
		List<McdDimPlanOnlineStatus> planStatus = mcdPlanService.initDimPlanStatus();// 获取状态
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
			List<Map<String, Object>> list = mcdPlanService.getPlanByCondition(typeId, statusId, keyWords, pager);
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
		return mcdPlanService.queryDetail(planId);
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
	public String planSaveContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String planId = request.getParameter("planId");
		String typeId = request.getParameter("typeId");
		String statusId = request.getParameter("statusId");
		String channelId = request.getParameter("channelId");
		String cityId = request.getParameter("cityId");
		String manager = request.getParameter("managerName");
		String planDesc = request.getParameter("planDesc");
		String planComment = request.getParameter("planComment");
		String dealCode_10086 = request.getParameter("dealCode_10086");
		String dealCode_1008611 = request.getParameter("dealCode_1008611");
		String urlForAndroid = request.getParameter("urlForAndroid");
		String urlForIos = request.getParameter("urlForIos");
		String cityIds = request.getParameter("cityIds");
		String scores = request.getParameter("scores");
		String awards = request.getParameter("awards");

		Boolean plan = mcdPlanService.savePlan(planId, typeId, statusId, channelId, cityId, manager, planDesc,
				planComment, dealCode_10086, dealCode_1008611, urlForAndroid, urlForIos, cityIds, scores, awards);
		String result = "";
		
		if (plan == false) {
			result = "保存失败！";
		} else if (plan == true) {
			result = "保存成功！";
		}
		
		return result;

	}
}
