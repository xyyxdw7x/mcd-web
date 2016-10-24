package com.asiainfo.biapp.mcd.content.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.content.service.IMcdContentService;
import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

import net.sf.json.JSONObject;

@RequestMapping("/content/contentManage")
public class ContentManageController extends BaseMultiActionController {
	private static Logger log = LogManager.getLogger();
	@Resource(name = "mcdContentService")
	private IMcdContentService mcdContentService;
	/**
	 * 查询内容状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public JSONObject queryContentTypes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject dataJson = new JSONObject();
		List<McdDimPlanOnlineStatus> contentStatus = mcdContentService.initDimContentStatus();// 获取状态
		dataJson.put("contentStatus", contentStatus);
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
		// 内容发布时间pub_time
		String timeId = StringUtils.isNotEmpty(request.getParameter("timeId")) ? request.getParameter("timeId"): null;
		// 状态类型：选statusId会用到
		String statusId = request.getParameter("statusId") != null ? request.getParameter("statusId") : null;
		
		try {
			pager.setPageSize(pageSize);
			pager.setPageNum(pageNum); // 当前页
			List<Map<String, Object>> list = mcdContentService.getContentByCondition(statusId, timeId, keyWords,
					pager);
			pager.setResult(list);
		} catch (Exception e) {
			log.error(e);
		}
		return pager;
	}

	/**
	 * 查询内容详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public JSONObject queryDetailContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject dataJson = new JSONObject();
		String contentId = request.getParameter("contentId");
		if(StringUtil.isEmpty(contentId)){}else{
			dataJson = mcdContentService.queryDetail(contentId);
		}
		return dataJson;
	}

	
	
	/**
	 * 详情内容保存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public String contentSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String[]> saveData = request.getParameterMap();	
		Boolean content = mcdContentService.saveContent(saveData);
		String result = "";
		if (content == false) {
			result = "保存失败！";
		} else if (content == true) {
			result = "保存成功！";
		}
		return result;

	}
}
