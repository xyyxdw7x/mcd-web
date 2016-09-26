package com.asiainfo.biapp.mcd.tactics.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.custgroup.service.ICustGroupInfoService;
import com.asiainfo.biapp.mcd.common.custgroup.vo.McdCustgroupDef;
import com.asiainfo.biapp.mcd.common.plan.vo.McdPlanDef;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlSmsSendTestTask;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampStatus;
import com.asiainfo.biapp.mcd.tactics.vo.McdSysInterfaceDef;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampCustgroupList;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 策略管理Controller
 * @author AsiaInfo-jie
 *
 */
@RequestMapping("/tactics/campSegSearch")
public class CampSegSearchController extends BaseMultiActionController {
	private static Logger log = LogManager.getLogger();

	//    @Resource(name="mpmUserPrivilegeService")
	//    private IMpmUserPrivilegeService mpmUserPrivilegeService; 
	@Resource(name = "mpmCampSegInfoService")
	private IMpmCampSegInfoService mpmCampSegInfoService;
	@Resource(name = "custGroupInfoService")
	private ICustGroupInfoService custGroupInfoService;
	@Resource(name = "mtlCallWsUrlService")
	private IMtlCallWsUrlService callwsUrlService;
	@Resource(name = "mtlSmsSendTestTask")
	private IMtlSmsSendTestTask mtlSmsSendTestTask;

	@RequestMapping
	@ResponseBody
	public Map<String, Object> searchIMcdCamp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String keywords = request.getParameter("keywords") != null ? request.getParameter("keywords") : null;
		String campsegStatId = request.getParameter("campsegStatId") != null ? request.getParameter("campsegStatId")
				: null;
		String isSelectMy = request.getParameter("isSelectMy") != null ? request.getParameter("isSelectMy") : "0";
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		String channelId = request.getParameter("channelId");

		McdCampDef segInfo = new McdCampDef();
		segInfo.setIsZJ(true);
		segInfo.setKeywords(keywords);

		if (campsegStatId != null && !"".equals(campsegStatId)) {
			segInfo.setStatId(Short.parseShort(campsegStatId));
		}
		if (isSelectMy != null) {
			segInfo.setIsSelectMy(Integer.parseInt(isSelectMy));
		}
		segInfo.setChannelId(channelId);
		segInfo.setCreateUserId("chenyg");//(userId);
		String clickQueryFlag = "true";
		Pager pager = new Pager();
		pager.setPageSize(MpmCONST.PAGE_SIZE);
		pager.setPageNum(Integer.parseInt(pageNum)); // 当前页
		if (pageNum != null) {
			pager.setPageFlag("G");
		}

		List<Map<String, Object>> resultList = null;
		if ("true".equals(clickQueryFlag)) {
			resultList = mpmCampSegInfoService.searchIMcdCampsegInfo(segInfo, pager);
			pager.getTotalPage();
			pager = pager.pagerFlip();
			pager.setResult(resultList);
		} else {
			resultList = mpmCampSegInfoService.searchIMcdCampsegInfo(segInfo, pager);
			pager.getTotalPage();
			pager = pager.pagerFlip();
			pager.setResult(resultList);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "200");
		map.put("data", pager);

		return map;
	}

	/**
	 * 查询状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> searchCampsegStat(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<McdDimCampStatus> statList = mpmCampSegInfoService.getDimCampsegStatList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "200");
		map.put("data", statList);
		return map;
	}

	/**
	 * 查询业务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> searchDimCampDrvType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<DimCampDrvType> dimCampSceneList = mpmCampSegInfoService.getDimCampSceneList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "200");
		map.put("data", dimCampSceneList);

		return map;
	}

	/**
	 * 查询策略包下子策略信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> searchMcdMpmCampSegChild(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int status = 200;
		String msg = "";
		Map<String, Object> returnMap = new HashMap<String, Object>();
		JSONArray jsonArray = null;
		try {
			String campsegId = request.getParameter("campsegId") != null ? request.getParameter("campsegId") : null;
			List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
			List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
			for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
				JSONObject dataJson1 = new JSONObject();
				String ruleDesc = "";
				McdPlanDef stcPlan = mpmCampSegInfoService.getMtlStcPlanByPlanId(mtlCampSeginfo.getPlanId());// 查询产品信息
				dataJson1.put("planName", stcPlan != null ? stcPlan.getPlanName() : "");
				List<McdCampCustgroupList> custGroupSelectList = mpmCampSegInfoService
						.getCustGroupSelectList(mtlCampSeginfo.getCampId());// 取营销活动“目标群选择”步骤中选择的“目标客户群”及“对比客户群”信息
				if (custGroupSelectList != null && !custGroupSelectList.isEmpty()) {
					McdCampCustgroupList mtlCampsegCustGroup = (McdCampCustgroupList) custGroupSelectList.get(0);
					McdCustgroupDef mtlGroupInfo = custGroupInfoService.getMtlGroupInfo(mtlCampsegCustGroup
							.getCustgroupId());
					if (mtlGroupInfo != null && mtlGroupInfo.getCustomGroupName() != null) {
						ruleDesc = "客户群：" + mtlGroupInfo.getCustomGroupName();
					}
					int originalCustGroupNum = mtlGroupInfo.getCustomNum(); //原始客户群数量
					dataJson1.put("originalCustGroupNum", String.valueOf(originalCustGroupNum));
					dataJson1.put("ruleDesc", ruleDesc);
				}

				List<Map<String, Object>> mcdList = mpmCampSegInfoService.getMtlChannelDefs(mtlCampSeginfo.getCampId());
				List<Map<String, String>> mtltlChannelDefList = new ArrayList<Map<String, String>>();

				for (int i = 0; i < mcdList.size(); i++) {
					Map<String, Object> map = (Map<String, Object>) mcdList.get(i);
					Map<String, String> mapNew = new HashMap<String, String>();
					mapNew.put("TARGER_USER_NUMS",
							map.get("TARGER_USER_NUMS") == null ? "" : map.get("TARGER_USER_NUMS").toString());
					mapNew.put("CHANNEL_NAME", map.get("CHANNEL_NAME") == null ? "" : map.get("CHANNEL_NAME")
							.toString());
					mtltlChannelDefList.add(mapNew);
				}
				JSONArray mtlChannelDefsJsonArray = JSONArray.fromObject(mtltlChannelDefList);
				dataJson1.put("mtlChannelDefs", mtlChannelDefsJsonArray);
				jsonObjectList.add(dataJson1);
			}

			jsonArray = JSONArray.fromObject(jsonObjectList);

		} catch (Exception e) {
			status = 201;
			log.error("", e);
			msg = e.getMessage();
		} finally {

			returnMap.put("status", "200");
			if (!"200".equals(status)) {
				returnMap.put("result", msg);
			}
			returnMap.put("data", jsonArray);
		}

		return returnMap;
	}

	/**
	 * 提交策略信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> submitApproval(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String campsegId = request.getParameter("campsegId") != null ? request.getParameter("campsegId") : null;
		String message = mpmCampSegInfoService.submitApprovalXml(campsegId);
		Map<String, Object> returnMap = new HashMap<String, Object>();

		if (message.contains("失败")) {
			returnMap.put("status", 201);
			returnMap.put("result", message);
		} else {
			returnMap.put("status", 200);
		}

		returnMap.put("data", "");

		return returnMap;
	}

	/**
	 * 删除策略信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> delCampseg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		String msg = "";
		int status = 200;
		try {
			String campsegId = request.getParameter("campsegId") != null ? request.getParameter("campsegId") : null;

			if (campsegId != null) {
				mpmCampSegInfoService.deleteCampSegInfo(campsegId);
			}

		} catch (Exception e) {
			log.error("", e);
			status = 201;
			msg = "删除策略信息失败";
		} finally {
			if (status != 200) {
				returnMap.put("status", status);
				returnMap.put("result", msg);
			} else {
				returnMap.put("status", status);
			}

			returnMap.put("data", "");
		}
		return returnMap;

	}

	/**
	 * 延期策略信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> updateCampsegEndDate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		String msg = "";
		int status = 200;
		try {

			String campsegId = request.getParameter("campsegId") != null ? request.getParameter("campsegId") : null;
			String endDate = request.getParameter("endDate") != null ? request.getParameter("endDate") : null;

			if (campsegId != null && endDate != null) {

				List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
				mpmCampSegInfoService.updateCampsegEndDate(campsegId, endDate);
				for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
					mpmCampSegInfoService.updateCampsegEndDate(mtlCampSeginfo.getCampId(), endDate);
				}

			} else {
				status = 201;
				msg = "延期日期或策略id为为空";
			}
		} catch (Exception e) {
			status = 201;
			msg = "发生未知错误，请联系管理员";
			log.error("", e);
		} finally {
			if (status != 200) {
				returnMap.put("status", status);
				returnMap.put("result", msg);
			} else {
				returnMap.put("status", status);
			}

			returnMap.put("data", "");

		}
		return returnMap;
	}

	/**
	 * 撤销工单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> cancelAssignment(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String campsegId = request.getParameter("campsegId") != null ? request.getParameter("campsegId") : null;
		String anceldesc = request.getParameter("anceldesc") != null ? request.getParameter("anceldesc") : null;
		Map<String, Object> returnMap = new HashMap<String, Object>();

		String msg = "";
		int status = 200;

		McdCampDef segInfo = mpmCampSegInfoService.getCampSegInfo(campsegId);
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<MARKET_ASSIGNMENT_INFO><ASSIGNMENT_INFO>");
		xml.append("<ASSIGN_ID>" + segInfo.getApproveFlowId() + "</ASSIGN_ID>");
		xml.append("<CANCEL_DESC>" + anceldesc + "</CANCEL_DESC>");
		xml.append("</ASSIGNMENT_INFO></MARKET_ASSIGNMENT_INFO>");

		String approve_flag = null;
		String approve_desc = null;
		log.info("开始工单撤销 ！");
		try {
			McdSysInterfaceDef url = callwsUrlService.getCallwsURL("APPREVEINFO_BYIDS");
			QName name = new QName("http://impl.biz.web.tz", "cancelAssignment");
			Service serviceWs = new Service();
			Call call = (Call) serviceWs.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url.getCallwsUrl()));
			call.setOperationName(name);
			call.setTimeout(50000);// 超时时间5秒
			log.info(xml.toString());
			String childxml = call.invoke(new Object[] { xml.toString() }).toString();

			log.info("撤销工单返回responed xml " + childxml);
			Document dom = DocumentHelper.parseText(childxml);
			Element root = dom.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> elementList = root.elements("ASSIGNMENT_INFO");
			for (int i = 0; i < elementList.size(); i++) {
				Element element = (org.dom4j.Element) elementList.get(i);
				approve_flag = element.element("PROCESS_FLAG") != null ? element.element("PROCESS_FLAG").getText() : "";
				approve_desc = element.element("PROCESS_DESC") != null ? element.element("PROCESS_DESC").getText() : "";
			}

			// 存撤销工单状态及撤销原因）
			if ("1".equals(approve_flag)) {
				short ampsegStatId = Short.valueOf(MpmCONST.MPM_CAMPSEG_STAT_HDZZ);
				mpmCampSegInfoService.cancelAssignment(campsegId, ampsegStatId, approve_desc);
				List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
				for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
					mpmCampSegInfoService.cancelAssignment(mtlCampSeginfo.getCampId(), ampsegStatId, approve_desc);
				}
			} else {
				status = 201;
				msg = "接口返回处理结果失败，原因为：" + approve_desc;
			}

		} catch (Exception e) {
			status = 201;
			log.error("", e);
			msg = e.getMessage();
		} finally {
			if (status == 200) {
				returnMap.put("status", status);
			} else {
				returnMap.put("status", status);
				returnMap.put("result", msg);
			}

			returnMap.put("data", "");
		}

		return returnMap;
	}

	/**
	 * 策略停止 Method campCancel()
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> campCancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		String msg = "";
		int status = 200;
		try {
			/* 获取session信息 */
			String campsegId = request.getParameter("campsegId") != null ? request.getParameter("campsegId").toString()
					: null;
			String pauseComment = request.getParameter("pauseComment") != null ? request.getParameter("pauseComment")
					.toString() : null;
			if (campsegId != null) {
				// 获取service

				List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
				mpmCampSegInfoService.updateCampStat(campsegId, MpmCONST.MPM_CAMPSEG_STAT_HDZZ);
				for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
					mpmCampSegInfoService.updateCampStat(mtlCampSeginfo.getCampId(), MpmCONST.MPM_CAMPSEG_STAT_HDZZ);
				}
				//保存停止原因
				mpmCampSegInfoService.updatMtlCampSeginfoPauseComment(campsegId, pauseComment);
			}

		} catch (Exception e) {
			log.error("", e);
			status = 201;
			msg = "策略终止失败";
		} finally {
			if (StringUtils.isEmpty(msg)) {
				msg = "策略终止成功";
			}
			if (status != 200) {
				returnMap.put("status", status);
				returnMap.put("result", msg);
			} else {
				returnMap.put("status", status);
			}

			returnMap.put("data", "");

		}
		return returnMap;

	}

	/**
	 * 策略暂停
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> campPause(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		String msg = "";
		int status = 200;
		try {
			/* 获取session信息 */
			String campsegId = request.getParameter("campsegId") != null ? request.getParameter("campsegId").toString()
					: null;
			String pauseComment = request.getParameter("pauseComment") != null ? request.getParameter("pauseComment")
					.toString() : null;

			if (campsegId != null) {
				List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
				mpmCampSegInfoService.updateCampStat(campsegId, MpmCONST.MPM_CAMPSEG_STAT_PAUSE);

				for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
					mpmCampSegInfoService.updateCampStat(mtlCampSeginfo.getCampId(), MpmCONST.MPM_CAMPSEG_STAT_PAUSE);
				}
				//保存暂停原因
				mpmCampSegInfoService.updatMtlCampSeginfoPauseComment(campsegId, pauseComment);
			}

		} catch (Exception e) {
			log.error("", e);
			status = 201;
		} finally {
			if (status == 201) {
				msg = "策略暂停失败";
			} else {
				msg = "策略暂停成功";
			}
			if (status != 200) {
				returnMap.put("status", status);
				returnMap.put("result", msg);
			} else {
				returnMap.put("status", status);
			}
			returnMap.put("data", "");
		}
		return returnMap;

	}

	/**
	 * 策略重启
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> campRestart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String msg = "";
		int status = 200;
		try {

			String campsegId = request.getParameter("campsegId") != null ? request.getParameter("campsegId") : null;

			if (campsegId != null) {
				List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
				mpmCampSegInfoService.updateCampStat(campsegId, MpmCONST.MPM_CAMPSEG_STAT_DDCG);
				for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
					mpmCampSegInfoService.updateCampStat(mtlCampSeginfo.getCampId(), MpmCONST.MPM_CAMPSEG_STAT_DDCG);
				}
			}
		} catch (Exception e) {
			log.error("", e);
			msg = "重启失败，原因：" + e.getMessage();
			status = 201;
		} finally {
			if (status != 200) {
				returnMap.put("status", status);
				returnMap.put("result", msg);
			} else {
				returnMap.put("status", status);
			}
			returnMap.put("data", "");
		}
		return returnMap;

	}

	/**
	 * 营销用语查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> searchExecContent(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		int status = 200;
		String msg = "";
		JSONArray jsonArrayEnd = null;
		try {
			String campsegId = request.getParameter("campsegId") != null ? request.getParameter("campsegId") : null;
			List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
			List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
			for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
				JSONObject dataJson = new JSONObject();
				dataJson.put("campsegId", mtlCampSeginfo.getCampId());
				// 获取有营销用语的渠道的营销用语
				List<Map<String, Object>> execContentList = mpmCampSegInfoService.getExecContentList(mtlCampSeginfo
						.getCampId());
				JSONArray jsonArray = JSONArray.fromObject(execContentList);
				dataJson.put("channelExecContent", jsonArray);
				// 获取营销用语变量
				List<Map<String, Object>> execContentVariableList = mpmCampSegInfoService
						.getExecContentVariableList(mtlCampSeginfo.getCampId());
				JSONArray execContentVariableJsonArray = JSONArray.fromObject(execContentVariableList);
				dataJson.put("execContentVariableJsonArray", execContentVariableJsonArray);
				jsonObjectList.add(dataJson);
			}
			jsonArrayEnd = JSONArray.fromObject(jsonObjectList);

		} catch (Exception e) {
			status = 201;
			log.error("", e);
			msg = e.getMessage();
		} finally {
			returnMap.put("status", "200");
			if (!"200".equals(status)) {
				returnMap.put("result", msg);
			}
			returnMap.put("data", jsonArrayEnd);
		}
		return returnMap;
	}

	/**
	 * 保存营销用语
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> saveExecContent(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int status = 200;
		String msg = "";
		// 是否有短信渠道
		boolean isSMS = false;
		int sms = MpmCONST.CHANNEL_TYPE_SMS_INT;
		try {
			String json = request.getParameter("json") != null ? request.getParameter("json") : null;
			JSONObject jsonObjectP = (JSONObject) JSONObject.fromObject(json);
			String campsegPId = jsonObjectP.get("campsegPId").toString();
			String childCampsegString = jsonObjectP.get("childCampseg").toString();
			JSONArray jsonArray = JSONArray.fromObject(childCampsegString);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				String campsegId = jsonObject.get("campsegId").toString();
				String channelexecContentList = jsonObject.get("channelexecContentList").toString();
				JSONArray channelexecContentArray = JSONArray.fromObject(channelexecContentList);
				for (int j = 0; j < channelexecContentArray.size(); j++) {
					JSONObject channelexecContent = (JSONObject) channelexecContentArray.get(j);
					String channelId = channelexecContent.get("channelId").toString();
					String execContent = channelexecContent.get("execContent").toString();
					//是否有营销用语
					String ifHasVariate = channelexecContent.get("ifHasVariate").toString();
					mpmCampSegInfoService.saveExecContent(campsegId, channelId, execContent, ifHasVariate);
					if (Integer.parseInt(channelId) == sms) {
						isSMS = true;
						String result = mtlSmsSendTestTask.mtlSmsSendTest(campsegId, channelId);
						log.info("发送测试短信结果:{}",result);
					}
				}
				if (isSMS) {
					mtlSmsSendTestTask.updateCampsegInfoState(campsegId, MpmCONST.MPM_CAMPSEG_STAT_HDCS);
				}
			}
			if (isSMS) {
				mtlSmsSendTestTask.updateCampsegInfoState(campsegPId, MpmCONST.MPM_CAMPSEG_STAT_HDCS);
			}

			msg = "保存营销用语成功！";
		} catch (Exception e) {
			status = 201;
			log.error("", e);
			msg = e.getMessage();
		} finally {
			returnMap.put("status", "200");
			if (!"200".equals(status)) {
				returnMap.put("result", msg);
			}
			returnMap.put("data", "");
		}

		return returnMap;
	}

}
