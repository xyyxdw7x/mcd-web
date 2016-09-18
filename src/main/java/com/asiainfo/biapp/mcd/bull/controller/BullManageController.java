package com.asiainfo.biapp.mcd.bull.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.bull.service.IBullMonitorService;
import com.asiainfo.biapp.mcd.bull.service.ICurrentDateQuotaService;
import com.asiainfo.biapp.mcd.bull.vo.BullMonitor;
import com.asiainfo.biapp.mcd.bull.vo.CityQuotaStatic;
import com.asiainfo.biapp.mcd.bull.vo.CurrentDateQuota;
import com.asiainfo.biapp.mcd.bull.vo.UserDept;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.constants.TasKStatus;
import com.asiainfo.biapp.mcd.quota.service.IQuotaConfigCityDayService;
import com.asiainfo.biapp.mcd.quota.vo.CityQuotaStatisDay;


@Controller
@RequestMapping("/mpm/bullManage")
public class BullManageController extends BaseMultiActionController {
	private static final Logger log = LogManager.getLogger();

	@Autowired
	private IBullMonitorService bullMonitorService;
	@Autowired
	private ICurrentDateQuotaService currentDateQuotaService;
	@Autowired
	private IQuotaConfigCityDayService quotaConfigCityDayService;

	//群发管理页面入口url
	@RequestMapping(params = "cmd=viewBull")
	public ModelAndView viewBull(HttpServletRequest request, HttpServletResponse response) {
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		List<CurrentDateQuota> list = this.currentDateQuotaService.getCurrentStatis(cityId);//科室月配额
		CityQuotaStatic cityStatis = this.currentDateQuotaService.getCityStatis(cityId);//地市月配额
		
		CityQuotaStatisDay cityQuotaStatisDay = this.quotaConfigCityDayService.getCityQuotaStatisDay(cityId);//地市日配额

		int currentSendType = this.bullMonitorService.getSendType(cityId);

		if (currentSendType == 0) {
			currentSendType = 1;// 顺序发送
			this.bullMonitorService.addCitySendType(cityId,currentSendType);
		}

		ModelAndView view = new ModelAndView("bull/bullManage"); 
		view.addObject("currentStatis", list);
		view.addObject("cityStatis", cityStatis);
		view.addObject("cityQuotaStatisDay", cityQuotaStatisDay);
		view.addObject("currentSendType", currentSendType);
		view.addObject("cityId", cityId);
		return view;
	}

	//群发管理页面打开后ajax请求列表数据
	@RequestMapping(params = "cmd=viewBullAjax")
	@ResponseBody
	public Map<String, Object> viewBullAjax(HttpServletRequest request, HttpServletResponse response) {
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		String deptId = request.getParameter("deptId");
		
		List<BullMonitor> list = null;

		try {
			list = this.bullMonitorService.getBullMonitorListByDeptId(cityId, deptId);
			this.escape(list);
		} catch (Exception e) {
			log.error("",e);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", list);
		resultMap.put("status", "200");
		
		return resultMap;
	}

	//群发管理页面下拉选择科室
	@RequestMapping(params = "cmd=allDeptes")
	@ResponseBody
	public Map<String, Object> allDeptes(HttpServletRequest request, HttpServletResponse response) {
		User user = this.getUser(request, response);
		String cityId = user.getCityId();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<UserDept> list = this.bullMonitorService.getDeptsAll(cityId);
			
			resultMap.put("data", list);
			resultMap.put("status", "200");
		} catch (Exception e) {
			resultMap.put("status", "201");
			resultMap.put("result", "查询数据异常");
		}
		return resultMap;
	}

	@RequestMapping(params = "cmd=setCampPri")
	@ResponseBody
	public Map<String, Object> setCampPri(HttpServletRequest request, HttpServletResponse response) {

		String campsegIds = request.getParameter("campsegIds");
		String[] ids = campsegIds.split(",");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			this.bullMonitorService.batchModifyCampPri(ids);
			
			resultMap.put("data", "批量更新完成");
			resultMap.put("status", "200");
		} catch (Exception e) {
			resultMap.put("status", "201");
			resultMap.put("result", "查询数据异常");
		}
		return resultMap;
	}

	@RequestMapping(params = "cmd=setSendType")
	@ResponseBody
	public Map<String, Object> setSendType(HttpServletRequest request, HttpServletResponse response) {// mcd_sms_send_city_config 各地市群发类型
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		String sendType = request.getParameter("sendType");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			this.bullMonitorService.updateSendType(cityId, sendType);
			
			resultMap.put("data", "更新发送方式成功");
			resultMap.put("status", "200");
		} catch (Exception e) {
			resultMap.put("status", "201");
			resultMap.put("result", "查询数据异常");
		}
		return resultMap;
	}

	@RequestMapping(params = "cmd=stopTask")
	@ResponseBody
	public Map<String, Object> stopTask(HttpServletRequest request, HttpServletResponse response) {

		String taskIds = request.getParameter("taskIds");
		String[] ids = taskIds.split(",");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			this.bullMonitorService.btachSetTaskStatus(ids, MpmCONST.TASK_STATUS_PAUSE);
			resultMap.put("data", "停止任务成功");
			resultMap.put("status", "200");
		} catch (Exception e) {
			resultMap.put("status", "201");
			resultMap.put("result", "查询数据异常");
		}
		return resultMap;

	}

	@RequestMapping(params = "cmd=startTask")
	@ResponseBody
	public Map<String, Object> startTask(HttpServletRequest request, HttpServletResponse response) {

		String taskIds = request.getParameter("taskIds");
		String[] ids = taskIds.split(",");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			this.bullMonitorService.btachSetTaskStatus(ids, (short) MpmCONST.TASK_STATUS_RUNNING);
			resultMap.put("data", "开始任务成功");
			resultMap.put("status", "200");
		} catch (Exception e) {
			resultMap.put("status", "201");
			resultMap.put("result", "查询数据异常");
		}
		return resultMap;
	}
	
	@RequestMapping(params = "cmd=batchUpdatePauseComment")
	@ResponseBody
	public Map<String, Object> batchUpdatePauseComment(HttpServletRequest request, HttpServletResponse response) {
		String campIds = request.getParameter("campIds");
		String pauseComment = request.getParameter("pauseComment");
		this.bullMonitorService.batchUpdatePauseComment(pauseComment, campIds);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", "修改策略暂停理由成功");
		resultMap.put("status", "200");
		return resultMap;
		
	}

	private void escape(List<BullMonitor> list) {
		for (BullMonitor monitor : list) {
			String campsegNameTemp = monitor.getCampsegName();
			String campsegNo=monitor.getCampsegNo();
			monitor.setShowCampsegName(campsegNameTemp+"_"+campsegNo);
			monitor.setExecStatusName(TasKStatus.getName(monitor.getExecStatus()));
			monitor.setCreateTime(monitor.getCreateTime().substring(0, 10));
		}
	}
}
