package com.asiainfo.biapp.mcd.priorityorder.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.priorityorder.service.ICampsegPriorityService;
import com.asiainfo.biapp.mcd.priorityorder.vo.CampsegPriorityBean;
import com.asiainfo.biapp.mcd.tactics.service.IMcdCampsegTaskService;

@Controller
@RequestMapping("/action/priorityOrder")
public class PriorityOrderController extends BaseMultiActionController { 

	private Logger log = Logger.getLogger(PriorityOrderController.class);
	@Autowired
	private ICampsegPriorityService campsegPriorityService;
	@Resource(name = "mcdCampsegTaskService")
	private IMcdCampsegTaskService mcdCampsegTaskService;

	/**
	 * 初始化手动置顶优先级
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping()
	@ResponseBody
	public Map<String, Object> initManualPriorityCampseg(HttpServletRequest request, HttpServletResponse response) {
		String channelId = request.getParameter("channelId");
		String adivId =  request.getParameter("adivId");  //运营位id
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<CampsegPriorityBean> list = this.campsegPriorityService.initManualPriorityCampseg(channelId,adivId, cityId);

			resultMap.put("data", list);
			resultMap.put("status", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "201");
			resultMap.put("result", "查询数据异常");
		}
		return resultMap;
	}
	
	/**
	 * 系统自动优先级
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping()
	@ResponseBody
	public Map<String, Object> initAutoPriorityCampseg(HttpServletRequest request, HttpServletResponse response) {
		String channelId = request.getParameter("channelId");
		String adivId =  request.getParameter("adivId");  //运营位id
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		String pageNum = StringUtils.isNotEmpty(request.getParameter("pageNum")) ? request.getParameter("pageNum") : "1";
		String keyWords = StringUtils.isNotEmpty(request.getParameter("keyWords")) ? request.getParameter("keyWords") : null;
		Pager pager=new Pager();
		if(StringUtils.isNotEmpty(pageNum)){
			pager.setPageFlag("G");	
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String clickQueryFlag = "true";
			pager.setPageSize(10);
			pager.setPageNum(pageNum);  //当前页
			if(pageNum != null){
				pager.setPageFlag("G");	
			}
			pager.setTotalSize(this.campsegPriorityService.getAutoPriorityCampsegNum(channelId,adivId,cityId,keyWords));  // 总记录数
			pager.getTotalPage();
			if ("true".equals(clickQueryFlag)) {
				List<CampsegPriorityBean> list = this.campsegPriorityService.initAutoPriorityCampseg(channelId,adivId,cityId,keyWords,pager);
				pager = pager.pagerFlip();
				pager.setResult(list);
			} else {
				pager = pager.pagerFlip();
				List<CampsegPriorityBean> list = this.campsegPriorityService.initAutoPriorityCampseg(channelId,adivId,cityId,keyWords,pager);
				pager.setResult(list);
			}
			
			resultMap.put("data", pager);
			resultMap.put("status", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "201");
			resultMap.put("result", "查询数据异常");
		}
		return resultMap;
	}
	
	/**
	 * 修改系统优先级顺序  将系统自动优先级改为手动优先级
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping()
	@ResponseBody
	public Map<String, Object> editPriorityCampseg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		String channelId = request.getParameter("channelId");
		String campsegId = request.getParameter("campsegId");
		String chnAdivId = request.getParameter("chnAdivId");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			this.campsegPriorityService.editPriorityCampseg(campsegId, channelId, cityId, chnAdivId);
			resultMap.put("data", "更新完成");
			resultMap.put("status", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "201");
			resultMap.put("result", "查询数据异常");
		}
		return resultMap;
	}
	
	/**
	 * 修改手动优先级中的排序规则
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping()
	@ResponseBody
	public Map<String, Object> editManualPriorityCampseg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String priOrderNumStr = request.getParameter("priOrderNumStr");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			this.campsegPriorityService.editManualPriorityCampseg(priOrderNumStr);
			resultMap.put("data", "更新完成");
			resultMap.put("status", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "201");
			resultMap.put("result", "查询数据异常");
		}
		return resultMap;
	}
	
	/**
	 * 取消置顶功能
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping()
	@ResponseBody
	public Map<String, Object> cancleTopManualPriorityCampseg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String campsegId = request.getParameter("campsegId");
		String cityId = request.getParameter("cityId");
		String channelId = request.getParameter("channelId");
		String chnAdivId = request.getParameter("chnAdivId");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			log.info("************开始执行取消置顶操作*********************");
			this.campsegPriorityService.cancleTopManualPriorityCampseg(campsegId,cityId,channelId,chnAdivId);
			log.info("************结束执行取消置顶操作*********************");
			resultMap.put("data", "");
			resultMap.put("status", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "201");
			resultMap.put("result", "查询数据异常");
		}
		return resultMap;
	}
	
	   /**
     * 取消置顶功能
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping()
    @ResponseBody
    public Map<String, Object> stopCampsegTask(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String taskId = request.getParameter("taskId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            log.info("************开始终止任务操作*********************");
            this.mcdCampsegTaskService.stopCampsegTask(taskId);
            log.info("************结束执行终止任务操作*********************");
            resultMap.put("data", "");
            resultMap.put("status", "200");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("status", "201");
            resultMap.put("result", "查询数据异常");
        }
        return resultMap;
    }
}
