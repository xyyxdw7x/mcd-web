package com.asiainfo.biapp.mcd.bull.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.bull.service.BullMonitorService;
import com.asiainfo.biapp.mcd.bull.service.CurrentDateQuotaService;
import com.asiainfo.biapp.mcd.quota.service.QuotaConfigCityDayService;
import com.asiainfo.biapp.mcd.enums.TasKStatus;
import com.asiainfo.biapp.mcd.bull.vo.BullMonitor;
import com.asiainfo.biapp.mcd.bull.vo.CityQuotaStatic;
import com.asiainfo.biapp.mcd.quota.vo.CityQuotaStatisDay;
import com.asiainfo.biapp.mcd.bull.vo.CurrentDateQuota;
import com.asiainfo.biapp.mcd.bull.vo.UserDept;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;


@Controller
@RequestMapping("/mpm/bullManage")
public class BullManageController extends BaseMultiActionController {
	private static final Logger log = LogManager.getLogger();

	@Autowired
	private BullMonitorService bullMonitorService;
	@Autowired
	private CurrentDateQuotaService currentDateQuotaService;
	@Autowired
	private QuotaConfigCityDayService quotaConfigCityDayService;

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
	public void viewBullAjax(HttpServletRequest request, HttpServletResponse response) {
		User user = this.getUser(request, response);
		String cityId = user.getCityId();

		String deptId = request.getParameter("deptId");
		String code = "200";
		String errorMsg = "";
		List<BullMonitor> list = null;

		try {
			list = this.bullMonitorService.getBullMonitorListByDeptId(cityId, deptId);
			this.escape(list);

		} catch (Exception e) {
			code = "201";
			errorMsg = "查询数据异常";
			log.error("",e);
		}

		this.outJson4Ws(response, list, code, errorMsg);
	}

	//群发管理页面下拉选择科室
	@RequestMapping(params = "cmd=allDeptes")
	public void allDeptes(HttpServletRequest request, HttpServletResponse response) {
		User user = this.getUser(request, response);
		String cityId = user.getCityId();

		try {
			List<UserDept> list = this.bullMonitorService.getDeptsAll(cityId);
			this.outJson4Ws(response, list, "200", "");
		} catch (Exception e) {
			this.outJson4Ws(response, null, "201", "查询数据异常");
		}
	}

	@RequestMapping(params = "cmd=setCampPri")
	public void setCampPri(HttpServletRequest request, HttpServletResponse response) {

		String campsegIds = request.getParameter("campsegIds");
		String[] ids = campsegIds.split(",");

		try {
			this.bullMonitorService.batchModifyCampPri(ids);
			this.outJson4Ws(response, "更新成功", "200", "");
		} catch (Exception e) {
			this.outJson4Ws(response, null, "201", "查询数据异常");
		}
	}

	@RequestMapping(params = "cmd=setSendType")
	public void setSendType(HttpServletRequest request, HttpServletResponse response) {// mcd_sms_send_city_config 各地市群发类型
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		String sendType = request.getParameter("sendType");

		try {
			this.bullMonitorService.updateSendType(cityId, sendType);
			this.outJson4Ws(response, "更新发送方式成功", "200", "");
		} catch (Exception e) {
			this.outJson4Ws(response, null, "201", "查询数据异常");
		}
	}

	@RequestMapping(params = "cmd=stopTask")
	public void stopTask(HttpServletRequest request, HttpServletResponse response) {

		String taskIds = request.getParameter("taskIds");
		String[] ids = taskIds.split(",");

		try {
			this.bullMonitorService.btachSetTaskStatus(ids, MpmCONST.TASK_STATUS_PAUSE);
			this.outJson4Ws(response, "暂停任务成功", "200", "");
		} catch (Exception e) {
			this.outJson4Ws(response, null, "201", "查询数据异常");
		}

	}

	@RequestMapping(params = "cmd=startTask")
	public void startTask(HttpServletRequest request, HttpServletResponse response) {

		String taskIds = request.getParameter("taskIds");
		String[] ids = taskIds.split(",");

		try {
			this.bullMonitorService.btachSetTaskStatus(ids, (short) MpmCONST.TASK_STATUS_RUNNING);
			this.outJson4Ws(response, "开始任务成功", "200", "");
		} catch (Exception e) {
			this.outJson4Ws(response, null, "201", "查询数据异常");
		}

	}
	
	@RequestMapping(params = "cmd=batchUpdatePauseComment")
	public void batchUpdatePauseComment(HttpServletRequest request, HttpServletResponse response) {
		String campIds = request.getParameter("campIds");
		String pauseComment = request.getParameter("pauseComment");
		this.bullMonitorService.batchUpdatePauseComment(pauseComment, campIds);
		this.outJson4Ws(response, "修改策略暂停理由成功", "200", "");
		
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
