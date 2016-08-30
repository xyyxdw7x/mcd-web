package com.asiainfo.biapp.mcd.effectappraisal.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.bean.CampsegPriorityBean;
import com.asiainfo.biapp.mcd.effectappraisal.service.IcampsegPriorityService;
import com.asiainfo.biapp.mcd.util.jdbcPage.Pager;
import com.asiainfo.biframe.utils.string.StringUtil;

@Controller
@RequestMapping("/mpm/priorityAction")
public class ImcdCampsegPriorityController extends BaseMultiActionController { 

	private Logger log = Logger.getLogger(ImcdCampsegPriorityController.class);
	@Autowired
	private IcampsegPriorityService campsegPriorityService;

	/**
	 * 初始化手动置顶优先级
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=initManualPriorityCampseg")
	public void initManualPriorityCampseg(HttpServletRequest request, HttpServletResponse response) {
		String channelId = request.getParameter("channelId");
		String adivId =  request.getParameter("adivId");  //运营位id
		// TODO
		String cityId = "";
		try {
			List<CampsegPriorityBean> list = this.campsegPriorityService.initManualPriorityCampseg(channelId,adivId, cityId);

			this.outJson4Ws(response, list, "200", "");
		} catch (Exception e) {
			e.printStackTrace();
			this.outJson4Ws(response, new ArrayList<CampsegPriorityBean>(), "201", "");
		}
	}
	
	/**
	 * 系统自动优先级
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=initAutoPriorityCampseg")
	public void initAutoPriorityCampseg(HttpServletRequest request, HttpServletResponse response) {
		String channelId = request.getParameter("channelId");
		String adivId =  request.getParameter("adivId");  //运营位id
		// TODO
		String cityId = "";
		String pageNum = StringUtil.isNotEmpty(request.getParameter("pageNum")) ? request.getParameter("pageNum") : "1";
		String keyWords = StringUtil.isNotEmpty(request.getParameter("keyWords")) ? request.getParameter("keyWords") : null;
		Pager pager=new Pager();
		if(StringUtil.isNotEmpty(pageNum)){
			pager.setPageFlag("G");	
		}
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
			
			this.outJson4Ws(response, pager, "200", "");
		} catch (Exception e) {
			e.printStackTrace();
			this.outJson4Ws(response, null, "201", "");
		}
	}
	
	/**
	 * 修改系统优先级顺序  将系统自动优先级改为手动优先级
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=editPriorityCampseg")
	public void editPriorityCampseg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO
		String cityId = "";
		String channelId = request.getParameter("channelId");
		String campsegId = request.getParameter("campsegId");
		String chnAdivId = request.getParameter("chnAdivId");
		try {
			this.campsegPriorityService.editPriorityCampseg(campsegId, channelId, cityId, chnAdivId);
			this.outJson4Ws(response, null, "200", "");
		} catch (Exception e) {
			e.printStackTrace();
			this.outJson4Ws(response, null, "201", "");
		}
		
	}
	
	/**
	 * 修改手动优先级中的排序规则
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=editManualPriorityCampseg")
	public void editManualPriorityCampseg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String priOrderNumStr = request.getParameter("priOrderNumStr");
		try {
			this.campsegPriorityService.editManualPriorityCampseg(priOrderNumStr);
			this.outJson4Ws(response, null, "200", "");
		} catch (Exception e) {
			e.printStackTrace();
			this.outJson4Ws(response, null, "201", "");
		}
	}
	
	/**
	 * 取消置顶功能
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=cancleTopManualPriorityCampseg")
	public void cancleTopManualPriorityCampseg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String campsegId = request.getParameter("campsegId");
		String cityId = request.getParameter("cityId");
		String channelId = request.getParameter("channelId");
		String chnAdivId = request.getParameter("chnAdivId");
		PrintWriter out = response.getWriter();
		try {
			log.info("************开始执行取消置顶操作*********************");
			this.campsegPriorityService.cancleTopManualPriorityCampseg(campsegId,cityId,channelId,chnAdivId);
			log.info("************结束执行取消置顶操作*********************");
			this.outJson4Ws(response, null, "200", "");
		} catch (Exception e) {
			e.printStackTrace();
			this.outJson4Ws(response, null, "201", "");
		}
	}
}
