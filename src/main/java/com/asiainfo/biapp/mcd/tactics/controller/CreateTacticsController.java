package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiainfo.biapp.framework.core.AppConfigService;
import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.avoid.service.IMcdMtlBotherAvoidService;
import com.asiainfo.biapp.mcd.common.channel.service.IMcdDimChannelService;
import com.asiainfo.biapp.mcd.common.channel.vo.McdDimChannel;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.common.custgroup.service.ICustGroupAttrRelService;
import com.asiainfo.biapp.mcd.common.custgroup.service.ICustGroupInfoService;
import com.asiainfo.biapp.mcd.common.custgroup.vo.McdCustgroupDef;
import com.asiainfo.biapp.mcd.common.plan.service.IMtlStcPlanService;
import com.asiainfo.biapp.mcd.common.plan.vo.DimPlanSrvType;
import com.asiainfo.biapp.mcd.common.plan.vo.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.plan.vo.McdPlanDef;
import com.asiainfo.biapp.mcd.common.plan.vo.PlanBean;
import com.asiainfo.biapp.mcd.common.service.IMpmCommonService;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;
import com.asiainfo.biapp.mcd.common.util.MpmUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.vo.McdBotherContactConfig;
import com.asiainfo.biapp.mcd.custgroup.vo.McdCustgroupAttrList;
import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.service.ChannelBossSmsTemplateService;
import com.asiainfo.biapp.mcd.tactics.service.IDimCampsegTypeService;
import com.asiainfo.biapp.mcd.tactics.service.IMcdPlanChannelListService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlChannelDefService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlStcPlanManagementService;
import com.asiainfo.biapp.mcd.tactics.service.IPlanChannelAdivResourceService;
import com.asiainfo.biapp.mcd.tactics.service.MtlCampsegCustgroupService;
import com.asiainfo.biapp.mcd.tactics.vo.ChannelBossSmsTemplate;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampChannelList;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimAdivInfo;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampType;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimSmsbossTemplateType;
import com.asiainfo.biapp.mcd.tactics.vo.McdPlanChannelList;
import com.asiainfo.biapp.mcd.tactics.vo.McdSysInterfaceDef;
import com.asiainfo.biapp.mcd.tactics.vo.McdTempletForm;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefCall;
import com.asiainfo.biapp.mcd.tactics.vo.RuleTimeTermLable;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RequestMapping("/action/tactics/createTactics")
public class CreateTacticsController extends BaseMultiActionController {
	@Resource(name = "mpmCampSegInfoService")
	private IMpmCampSegInfoService mpmCampSegInfoService; // 策略信息
	@Resource(name = "mpmCommonService")
	private IMpmCommonService mpmCommonService;
	@Resource(name = "mtlCallWsUrlService")
	private IMtlCallWsUrlService mtlCallWsUrlService;
	@Resource(name = "mtlStcPlanManagementService")
	private IMtlStcPlanManagementService mtlStcPlanManagementService;//--
	@Resource(name = "custGroupAttrRelService")
	private ICustGroupAttrRelService custGroupAttrRelService;// 客户群属性
	@Resource(name = "channelBossSmsTemplateService")
	private ChannelBossSmsTemplateService channelBossSmsTemplateService;// Boss运营位模板
	@Resource(name = "custGroupInfoService")
	private ICustGroupInfoService custGroupInfoService;// 客户群信息
	@Resource(name = "mtlStcPlanService")
	private IMtlStcPlanService mtlStcPlanService;// 产品信息
	@Resource(name = "mtlCampsegCustgroupService")
	private MtlCampsegCustgroupService mtlCampsegCustgroupService;// 客户群与渠道关系
	@Resource(name = "mtlChannelDefService")
	private IMtlChannelDefService mtlChannelDefService;// 策略和渠道关系表
	@Resource(name = "botherAvoidService")
	private IMcdMtlBotherAvoidService botherAvoidService;
	@Resource(name = "mcdDimChannelService")
	private IMcdDimChannelService mcdDimChannelService;
	
	@Resource(name = "dimCampsegTypeService")
	private IDimCampsegTypeService dimCampsegTypeService;
	
	
	@Resource(name = "mcdPlanChannelListService")
	private IMcdPlanChannelListService mcdPlanChannelListService;
	
	@Resource(name = "planChannelAdivResourceService")
	private IPlanChannelAdivResourceService planChannelAdivResourceService;

	protected final Log log = LogFactory.getLog(getClass());


	/**
	 * 保存策略基本信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveCampsegWaveInfo")
	public void saveCampsegWaveInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		JSONObject dataJson = new JSONObject();
		List<McdCampDef> campSegInfoList = new ArrayList<McdCampDef>();

		try {
			User user = this.getUser(request, response);

			/*
			 * McdTempletForm bussinessLableTemplate = new McdTempletForm();
			 * McdTempletForm basicEventTemplate = new McdTempletForm();
			 */
			// 策略包 先循环迭代出每个规则
			String test = request.getParameter("ruleList");
			org.json.JSONObject ruleList = new org.json.JSONObject(test);
			// 获取公共属性
			org.json.JSONObject commonAttr = new org.json.JSONObject(ruleList.get("commonAttr").toString());
			String campsegName = commonAttr.get("campsegName").toString();
			String putDateStart = commonAttr.get("putDateStart").toString();
			String putDateEnd = commonAttr.get("putDateEnd").toString();
			String campsegTypeId = commonAttr.get("campsegTypeId").toString();
			if (StringUtils.isEmpty(campsegTypeId)) {
				campsegTypeId = "1";
			}

			String isFilterDisturb = commonAttr.get("isFilterDisturb").toString(); // 是否需要免打扰控制
			String planId = commonAttr.get("planid").toString();
			// 审批标识
			String isApprove = commonAttr.get("isApprove").toString();
			// 先保存基本信息 父亲节点
			McdCampDef campSeginfoBasic = new McdCampDef();
			campSeginfoBasic.setCampId(MpmUtil.generateCampsegAndTaskNo());
			campSeginfoBasic.setCampName(campsegName);
			campSeginfoBasic.setStartDate(putDateStart);
			campSeginfoBasic.setEndDate(putDateEnd);
			campSeginfoBasic.setPid("0");// 父节点
			campSeginfoBasic.setCreateUserId(user.getId());
			campSeginfoBasic.setCreateUserName(user.getName());
			campSeginfoBasic.setTypeId(Short.parseShort(campsegTypeId));
			campSeginfoBasic.setPlanId(planId);
			campSeginfoBasic.setIsFatherNode(true);
			campSeginfoBasic.setCityId(user.getCityId()); // 策划人所属城市
			campSeginfoBasic.setIsFileterDisturb(Integer.parseInt(isFilterDisturb));
			/*
			 * if (user != null) { LkgStaff staff = (LkgStaff) user; String
			 * deptMsg = staff.getDepId(); if (deptMsg.indexOf("&&") > 0) {
			 * deptId = deptMsg.split("&&")[0]; deptName =
			 * deptMsg.split("&&")[1]; }
			 * campSeginfoBasic.setDeptId(Integer.parseInt(deptId)); // 策划人部门id
			 * }
			 */
			campSegInfoList.add(campSeginfoBasic);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			String planIdArray[] = planId.split(","); // 当时多产品的时候

			for (int i = 0; i < planIdArray.length; i++) {
				String ruleIndex = "rule0";
				org.json.JSONObject rule = new org.json.JSONObject(ruleList.get(ruleIndex).toString()); // 迭代每一个规则进行计算

				// 业务标签
				/*
				 * String labelArr = rule.get("labelArr").toString(); if
				 * (StringUtils.isEmpty(labelArr) || labelArr.equals("[]")) {
				 * bussinessLableTemplate = null; } else {
				 * bussinessLableTemplate = this.createTemplateForm(labelArr,
				 * "0", true); }
				 */
				// 获取ARPU 基础标签
				/*
				 * String basicProp = rule.get("basicProp").toString(); if
				 * (StringUtils.isEmpty(basicProp) || basicProp.equals("[]")) {
				 * basicEventTemplate = null; } else { basicEventTemplate =
				 * createTemplateForm(basicProp, "1", true); }
				 */
				// 产品订购
				/*
				 * String productArr = rule.get("productArr").toString(); String
				 * productAttr[] = this.createProductAttr(productArr); String
				 * orderProductNo = productAttr[0]; // 订购产品 String
				 * excludeProductNo = productAttr[1]; // 剔除产品
				 */ // 获取渠道ID
				String channelIds = rule.get("channelIds").toString();

				// 获取客户群ID
				String customgroupid = null;
				String updateCycle = null;
				if (StringUtils.isNotEmpty(rule.get("customer").toString())) {
					customgroupid = new org.json.JSONObject(rule.get("customer").toString()).get("id").toString();
					updateCycle = new org.json.JSONObject(rule.get("customer").toString()).get("updatecycle")
							.toString();
				}
				/*
				// 获取每个rule中的基础属性
				String baseAttr = rule.get("baseAttr").toString();
				org.json.JSONArray basicPropArray = new org.json.JSONArray(baseAttr);
				String planid = "";
				String channelTypeId = "";
				for (int m = 0; m < basicPropArray.length(); m++) {
					org.json.JSONObject obj = new org.json.JSONObject(basicPropArray.get(m).toString());
					planid = obj.get("planid").toString(); // 产品ID
					if (planid.equals(planIdArray[i])) {
						channelTypeId = obj.get("campsegtypeid").toString(); // 渠道类型ID
					}
				}
				*/
				// 筛选后的客户群数量
				String afterComputCustNum = rule.get("afterComputCustNum").toString();
				// 获取渠道执行的基本信息
				String execContentStr = rule.get("execContent").toString();
				org.json.JSONArray execContent = new org.json.JSONArray(execContentStr);
				List<McdCampChannelList> mtlChannelDefList = new ArrayList<McdCampChannelList>();
				MtlChannelDefCall mtlChannelDefCall = null;

				String streamsId = "";
				//String streamName = "";
				for (int j = 0; j < execContent.length(); j++) {
					org.json.JSONObject obj = new org.json.JSONObject(execContent.get(j).toString());
					Map<String, Object> objMap = jsonToMap(execContent.get(j).toString());
					// 判断是否包含实时事件
					if (execContentStr.indexOf("cepInfo") != -1) {
						String cepInfo = obj.get("cepInfo").toString();
						org.json.JSONObject cepInfoObject = new org.json.JSONObject(cepInfo);
						org.json.JSONArray functionList = new org.json.JSONArray(
								cepInfoObject.getString("functionList"));
						for (int m = 0; m < functionList.length(); m++) {
						}
					}

					McdCampChannelList mtlChannelDef = new McdCampChannelList();
					String channelId = String.valueOf(obj.get("chanelId"));
					String content = "";
					boolean ifHasVariate = false;
					String channelCycle = "";
					String adivId = "";

					if ("901".equals(channelId)) { // 短信渠道
						channelCycle = String.valueOf(obj.get("channelCycle"));
						//String channelTrigger = String.valueOf(obj.get("channelTrigger"));
						content = String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
						// 策略级频次控制(一次性短信paramDays paramNum前台不传参数
						Object paramDaysObj = objMap.get("paramDays");
						String paramDays = "0";
						if (null != paramDaysObj) {
							paramDays = String.valueOf(paramDaysObj);
						}
						Object paramNumObj = objMap.get("paramNum");
						String paramNum = "0";
						if (null != paramNumObj) {
							paramNum = String.valueOf(paramNumObj);
						}
						// modify by zhuml end 20151203
						ifHasVariate = Boolean.parseBoolean(String.valueOf(obj.get("ifHasVariate")));
						mtlChannelDef.setUpdateCycle(Integer.parseInt(updateCycle));
						if (StringUtils.isNotEmpty(channelCycle)) {
							mtlChannelDef.setContactType(Integer.parseInt(channelCycle));
						}
						if (StringUtils.isEmpty(paramDays)) {
							paramDays = "0";
						}
						if (StringUtils.isEmpty(paramNum)) {
							paramNum = "0";
						}
						mtlChannelDef.setParamDays(Integer.parseInt(paramDays));
						mtlChannelDef.setParamNum(Integer.parseInt(paramNum));
						// 实时事件 参数 保存
						String execStr = execContent.get(j).toString();
						if (execStr.indexOf("eventInstanceDesc") != -1) { // 当选择实时的时候才会保存
							String eventInstanceDesc = String.valueOf(obj.get("eventInstanceDesc"));
							streamsId = String.valueOf(obj.get("streamsId"));
							String eventParamJson = String.valueOf(obj.get("eventParamJson"));
							//streamName = String.valueOf(obj.get("eventName"));

							Pattern p = Pattern.compile("\\s*|\t|\r|\n");

							org.json.JSONObject ruleTimeParamObj = new org.json.JSONObject(eventParamJson);
							String functionId = String.valueOf(ruleTimeParamObj.get("funcId")); // 场景id
							mtlChannelDef.setFunctionId(functionId);
							mtlChannelDef.setEventInstanceDesc(p.matcher(eventInstanceDesc).replaceAll(""));
							if ("20160309093621645766".equals(functionId)
									|| "20160309093745413830".equals(functionId)) { // 汪斌修改回显场景，特殊处理
								mtlChannelDef.setEventInstanceDesc(eventInstanceDesc);
							}
							mtlChannelDef.setEventParamJson(eventParamJson);
						}

					} else if ("902".equals(channelId) || "906".equals(channelId) || "905".equals(channelId)) { // 社会渠道和营业厅和手机crm
						content = String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
						ifHasVariate = Boolean.parseBoolean(String.valueOf(obj.get("ifHasVariate")));
					} else if ("903".equals(channelId)) { // 手机APP
						adivId = String.valueOf(obj.get("adivId"));
						mtlChannelDef.setAdivId(adivId);
					} else if ("904".equals(channelId)) { // 10086热线
						String awardMount = String.valueOf(obj.get("awardMount"));
						String editUrl = String.valueOf(obj.get("editUrl"));
						String handleUrl = String.valueOf(obj.get("handleUrl"));
						String sendSms = String.valueOf(obj.get("sendSms")); // 短信用语
						sendSms = URLDecoder.decode(URLDecoder.decode(sendSms, "UTF-8"), "UTF-8");
						content = String.valueOf(obj.get("exec_content")); // 推荐用语
						content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
						if (StringUtils.isNotEmpty(awardMount)) {
							mtlChannelDef.setAwardMount(Double.parseDouble(awardMount));
						}
						mtlChannelDef.setEditUrl(editUrl);
						mtlChannelDef.setHandleUrl(handleUrl);
						mtlChannelDef.setSendSms(sendSms);
					} else if ("907".equals(channelId)) { // toolBar +
						adivId = String.valueOf(obj.get("adivId"));
						content = String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
						mtlChannelDef.setAdivId(adivId);
					} else if ("910".equals(channelId)) { // boss运营位
						adivId = String.valueOf(obj.get("adivId"));
						content = String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
						String messageType = String.valueOf(obj.get("messageType"));
						String temp = "";
						if ("1".equals(messageType)) {
							temp += "【业务告知】" + content + "(中国移动)";
						} else {
							temp += "【服务提醒】" + content + "(中国移动)";
						}
						content = temp;
						mtlChannelDef.setAdivId(adivId);

					} else if ("911".equals(channelId) || "912".equals(channelId)) { // 微信（全省）
																						// +
																						// 微信（温州）
						adivId = String.valueOf(obj.get("adivId"));
						content = String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
						String execTitle = String.valueOf(obj.get("execTitle")); // 微信标题
						String fileName = String.valueOf(obj.get("fileName")); // 微信本地文件名称
						// //Ftp存放的文件名称
						mtlChannelDef.setAdivId(adivId);
						mtlChannelDef.setWcTitle(execTitle);
						mtlChannelDef.setWcFileName(fileName);
					}

					mtlChannelDef.setIsHasVar(!ifHasVariate ? Short.parseShort("0") : Short.parseShort("1"));

					if (StringUtils.isEmpty(adivId)) { // 当adivId为空的时候，默认为1
						mtlChannelDef.setAdivId("1");
					}
					// 保存将筛选后的客户群数量
					String afterComputeCustNum[] = afterComputCustNum.split(",");
					String targetCustNum = "0";
					if (null != afterComputeCustNum && afterComputeCustNum.length > 0) {
						for (int k = 0; k < afterComputeCustNum.length; k++) {
							String temp[] = afterComputeCustNum[k].split("_");
							if (channelId.equals(temp[0])) {
								targetCustNum = temp[1];
							}
						}
					}
					if (StringUtils.isEmpty(channelCycle) && StringUtils.isNotEmpty(updateCycle)) { // 当channelCycle为空的时候保持与updateCycle一致
						mtlChannelDef.setContactType(Integer.parseInt(updateCycle));
						mtlChannelDef.setUpdateCycle(Integer.parseInt(updateCycle));
					}

					mtlChannelDef.setTargetUserNums(Integer.parseInt(targetCustNum));
					mtlChannelDef.setChannelId(channelId);
					mtlChannelDef.setExecContent(content);
					mtlChannelDefList.add(mtlChannelDef);
				}

				McdCampDef campSeginfo = new McdCampDef();
				campSeginfo.setCampName(campsegName);
				campSeginfo.setStartDate(putDateStart);
				campSeginfo.setEndDate(putDateEnd);
				campSeginfo.setStatId(Short.parseShort(McdCONST.MPM_CAMPSEG_STAT_HDSP));
				campSeginfo.setTypeId(Short.parseShort(campsegTypeId));// 策略类型
				campSeginfo.setCreateUserId(user.getId()); // 活动策划人
				campSeginfo.setCityId(user.getCityId()); // 策划人所属城市
				campSeginfo.setCreateTime(format.parse(format.format(new Date())));
				campSeginfo.setPlanId(planIdArray[i]); // 产品编号
				campSeginfo.setCreateUserName(user.getName());
				campSeginfo.setIsFileterDisturb(Integer.parseInt(isFilterDisturb));
				// 渠道基本信息
				campSeginfo.setChannelId(channelIds); // 渠道id

				campSeginfo.setIsFatherNode(false);
				campSeginfo.setIsApprove(isApprove);
				campSeginfo.setCampNo(String.valueOf(i)); // 多规则时，规则序号

				// 添加实时事件支持
				if (execContentStr.indexOf("cepInfo") != -1) {
				}
				campSeginfo.setCepEventId(streamsId);
				// 客户群基本信息
				campSeginfo.setCustgroupId(customgroupid);

				campSeginfo.setMtlChannelDefList(mtlChannelDefList);
				campSeginfo.setMtlChannelDefCall(mtlChannelDefCall);
				// 产品订购或者剔除关系
				/*
				 * campSeginfo.setOrderPlanIds(orderProductNo);
				 * campSeginfo.setExcludePlanIds(excludeProductNo);
				 */

				// 保存业务标签
				/*
				 * if (bussinessLableTemplate != null) { String
				 * bussinessLableTemplateId =
				 * this.handleTemplet(request.getLocale(),
				 * bussinessLableTemplate); if
				 * (StringUtils.isNotEmpty(bussinessLableTemplateId)) {
				 * campSeginfo.setBussinessLableTemplateId(
				 * bussinessLableTemplateId); } }
				 */
				// 保存时机规则
				/*
				 * if (basicEventTemplate != null) { String basicEventTemplateId
				 * = this.handleTemplet(request.getLocale(),
				 * basicEventTemplate); if
				 * (StringUtils.isNotEmpty(basicEventTemplateId)) {
				 * campSeginfo.setBasicEventTemplateId(basicEventTemplateId); }
				 * }
				 */

				/*
				 * campSeginfo.setBussinessLableTemplate(bussinessLableTemplate)
				 * ; campSeginfo.setBasicEventTemplate(basicEventTemplate);
				 */
				campSegInfoList.add(campSeginfo);

			}

			// 统一进行保存
			String flag = mpmCampSegInfoService.saveCampSegWaveInfoZJ(campSegInfoList);
			if ("2".equals(flag)) { // 当flag等于2的时候，有审批流程，并且审批失败，0和1说明状态都正常
				dataJson.put("status", "201");
			} else {
				dataJson.put("status", "200");
			}
			out.print(dataJson);
		} catch (Exception e) {
			log.error("保存异常", e);
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * IMCD_ZJ 政策修改方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void updateCampsegWaveInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		List<McdCampDef> campSegInfoList = new ArrayList<McdCampDef>();
		try {
			/*
			 * McdTempletForm bussinessLableTemplate = new McdTempletForm();
			 * McdTempletForm basicEventTemplate = new McdTempletForm();
			 */
			User user = this.getUser(request, response);

			// 策略包 先循环迭代出每个规则
			String test = request.getParameter("ruleList");
			if (StringUtils.isNotEmpty(test)) {
				org.json.JSONObject ruleList = new org.json.JSONObject(test);
				// 获取公共属性
				org.json.JSONObject commonAttr = new org.json.JSONObject(ruleList.get("commonAttr").toString());
				String campsegPid = commonAttr.get("campsegPid").toString(); // 父策略id
				if (StringUtils.isNotEmpty(campsegPid)) { // 判断为修改
					String campsegName = commonAttr.get("campsegName").toString();
					String putDateStart = commonAttr.get("putDateStart").toString();
					String putDateEnd = commonAttr.get("putDateEnd").toString();
					String campsegTypeId = commonAttr.get("campsegTypeId").toString();
					if (StringUtils.isEmpty(campsegTypeId)) {
						campsegTypeId = "1";
					}
					String planId = commonAttr.get("planid").toString();
					// 审批标识
					String isApprove = commonAttr.get("isApprove").toString();
					// 查询子策略信息
					List<McdCampDef> campsegList = mpmCampSegInfoService.getCampSeginfoListByCampsegId(campsegPid);
					// 先保存基本信息 父亲节点
					McdCampDef campSeginfoBasic = null;
					Map<String, McdCampDef> campSeginfoMap = new HashMap<String, McdCampDef>();
					for (int m = 0; m < campsegList.size(); m++) {
						McdCampDef temp = campsegList.get(m);
						campSeginfoMap.put(temp.getCampId(), temp);
						if ("0".equals(campsegList.get(m).getPid())) { // 取父策略
							campSeginfoBasic = temp;
						}
					}
					campSeginfoBasic.setCampId(campsegPid);
					campSeginfoBasic.setCampName(campsegName);
					campSeginfoBasic.setStartDate(putDateStart);
					campSeginfoBasic.setEndDate(putDateEnd);
					campSeginfoBasic.setPid("0");// 父节点
					campSeginfoBasic.setCreateUserId(user.getId());
					campSeginfoBasic.setCreateUserName(user.getName());
					campSeginfoBasic.setTypeId(Short.parseShort(campsegTypeId));
					campSeginfoBasic.setPlanId(planId);
					campSeginfoBasic.setIsFatherNode(true);
					campSeginfoBasic.setCityId(user.getCityId());
					String deptId = "";
					/*
					 * if(user != null){ LkgStaff staff = (LkgStaff)user; String
					 * deptMsg = staff.getDepId(); if(deptMsg.indexOf("&&") >
					 * 0){ deptId = deptMsg.split("&&")[0]; deptName =
					 * deptMsg.split("&&")[1]; }
					 * campSeginfoBasic.setDeptId(Integer.parseInt(deptId));
					 * //策划人部门id }
					 */

					campSegInfoList.add(campSeginfoBasic);

					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					MtlChannelDefCall mtlChannelDefCall = null;

					String planIdArray[] = planId.split(","); // 当时多产品的时候
					for (int i = 0; i < planIdArray.length; i++) {
						String campsegId = "";
						for (int j = 0; j < campsegList.size(); j++) {
							McdCampDef mtlCampSeginfo = campsegList.get(j);
							if (!("0").equals(mtlCampSeginfo.getPid())
									&& mtlCampSeginfo.getPlanId().equals(planIdArray[i])) {
								campsegId = mtlCampSeginfo.getCampId();
							}
						}
						String ruleIndex = "rule0";
						org.json.JSONObject rule = new org.json.JSONObject(ruleList.get(ruleIndex).toString()); // 迭代每一个规则进行计算
						// 业务标签
						/*
						 * String labelArr = rule.get("labelArr").toString();
						 * if(StringUtils.isEmpty(labelArr) ||
						 * labelArr.equals("[]")){ bussinessLableTemplate =
						 * null; }else{ bussinessLableTemplate =
						 * this.createTemplateForm(labelArr,"0",true); }
						 */
						// 获取ARPU 基础标签
						/*
						 * String basicProp = rule.get("basicProp").toString();
						 * if(StringUtils.isEmpty(basicProp) ||
						 * basicProp.equals("[]")){ basicEventTemplate = null;
						 * }else{ basicEventTemplate =
						 * createTemplateForm(basicProp,"1",true); }
						 */
						// 产品订购
						/*
						 * String productArr =
						 * rule.get("productArr").toString(); String
						 * productAttr[] = this.createProductAttr(productArr);
						 * String orderProductNo = productAttr[0]; //订购产品 String
						 * excludeProductNo = productAttr[1]; //剔除产品
						 */ // 获取渠道ID
						String channelIds = rule.get("channelIds").toString();
						// 获取客户群清单表名称
						String initCustListTab = rule.get("initCustListTab").toString();
						// 获取客户群ID
						String customgroupid = null;
						String updateCycle = null;
						if (StringUtils.isNotEmpty(rule.get("customer").toString())) {
							customgroupid = new org.json.JSONObject(rule.get("customer").toString()).get("id")
									.toString();
							updateCycle = new org.json.JSONObject(rule.get("customer").toString()).get("updatecycle")
									.toString();
						}
						/*
						// 获取每个rule中的基础属性
						String baseAttr = rule.get("baseAttr").toString();
						org.json.JSONArray basicPropArray = new org.json.JSONArray(baseAttr);
						String planid = "";
						String channelTypeId = "";
						for (int m = 0; m < basicPropArray.length(); m++) {
							org.json.JSONObject obj = new org.json.JSONObject(basicPropArray.get(m).toString());
							planid = obj.get("planid").toString(); // 产品ID
							if (planid.equals(planIdArray[i])) {
								channelTypeId = obj.get("campsegtypeid").toString(); // 渠道类型ID
							}
						}
						*/
						// 筛选后的客户群数量
						String afterComputCustNum = rule.get("afterComputCustNum").toString();
						// 获取渠道执行的基本信息
						String execContentStr = rule.get("execContent").toString();
						org.json.JSONArray execContent = new org.json.JSONArray(execContentStr);
						List<McdCampChannelList> mtlChannelDefList = new ArrayList<McdCampChannelList>();

						String streamsId = "";
						String streamName = "";
						for (int j = 0; j < execContent.length(); j++) {
							org.json.JSONObject obj = new org.json.JSONObject(execContent.get(j).toString());
							Map<String, Object> objMap = jsonToMap(execContent.get(j).toString());
							// 判断是否包含实时事件
							if (execContentStr.indexOf("cepInfo") != -1) {
								String cepInfo = obj.get("cepInfo").toString();
								org.json.JSONObject cepInfoObject = new org.json.JSONObject(cepInfo);
								org.json.JSONArray functionList = new org.json.JSONArray(
										cepInfoObject.getString("functionList"));
								for (int m = 0; m < functionList.length(); m++) {
								}
							}

							McdCampChannelList mtlChannelDef = new McdCampChannelList();
							String channelId = String.valueOf(obj.get("chanelId"));
							String content = "";
							boolean ifHasVariate = false;
							String channelCycle = "";
							String adivId = "";
							if ("901".equals(channelId)) { // 短信渠道
								channelCycle = String.valueOf(obj.get("channelCycle"));
								//String channelTrigger = String.valueOf(obj.get("channelTrigger"));
								content = String.valueOf(obj.get("exec_content"));
								content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
								// 策略级频次控制(一次性短信paramDays paramNum前台不传参数
								// 故paramDaysObj==null paramNumObj==null )
								// modify by zhuml 20151203
								Object paramDaysObj = objMap.get("paramDays");
								String paramDays = "0";
								if (null != paramDaysObj) {
									paramDays = String.valueOf(paramDaysObj);
								}
								Object paramNumObj = objMap.get("paramNum");
								String paramNum = "0";
								if (null != paramNumObj) {
									paramNum = String.valueOf(paramNumObj);
								}
								// modify by zhuml end 20151203

								ifHasVariate = Boolean.parseBoolean(String.valueOf(obj.get("ifHasVariate")));
								mtlChannelDef.setUpdateCycle(Integer.parseInt(updateCycle));
								if (StringUtils.isNotEmpty(channelCycle)) {
									mtlChannelDef.setContactType(Integer.parseInt(channelCycle));
								}
								if (StringUtils.isEmpty(paramDays)) {
									paramDays = "0";
								}
								if (StringUtils.isEmpty(paramNum)) {
									paramNum = "0";
								}
								mtlChannelDef.setParamDays(Integer.parseInt(paramDays));
								mtlChannelDef.setParamNum(Integer.parseInt(paramNum));

								// 实时事件 参数 保存
								String execStr = execContent.get(j).toString();
								if (execStr.indexOf("eventInstanceDesc") != -1) { // 当选择实时的时候才会保存
									String eventInstanceDesc = String.valueOf(obj.get("eventInstanceDesc"));
									streamsId = String.valueOf(obj.get("streamsId"));
									String eventParamJson = String.valueOf(obj.get("eventParamJson"));
									streamName = String.valueOf(obj.get("eventName"));
									Pattern p = Pattern.compile("\\s*|\t|\r|\n");
									org.json.JSONObject ruleTimeParamObj = new org.json.JSONObject(eventParamJson);
									String functionId = String.valueOf(ruleTimeParamObj.get("funcId")); // 场景id
									mtlChannelDef.setFunctionId(functionId);
									mtlChannelDef.setEventInstanceDesc(p.matcher(eventInstanceDesc).replaceAll(""));
									if ("20160309093621645766".equals(functionId)
											|| "20160309093745413830".equals(functionId)) { // 汪斌修改回显场景，特殊处理
										mtlChannelDef.setEventInstanceDesc(eventInstanceDesc);
									}
									mtlChannelDef.setEventParamJson(eventParamJson);
								}

							} else if ("902".equals(channelId) || "906".equals(channelId) || "905".equals(channelId)) { // 社会渠道和营业厅和手机crm
								content = String.valueOf(obj.get("exec_content"));
								content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
								ifHasVariate = Boolean.parseBoolean(String.valueOf(obj.get("ifHasVariate")));
							} else if ("903".equals(channelId)) { // 手机APP
								adivId = String.valueOf(obj.get("adivId"));
								mtlChannelDef.setAdivId(adivId);
							} else if ("904".equals(channelId)) { // 10086热线
								String awardMount = String.valueOf(obj.get("awardMount"));
								String editUrl = String.valueOf(obj.get("editUrl"));
								String handleUrl = String.valueOf(obj.get("handleUrl"));
								String sendSms = String.valueOf(obj.get("sendSms"));
								sendSms = URLDecoder.decode(URLDecoder.decode(sendSms, "UTF-8"), "UTF-8");
								content = String.valueOf(obj.get("exec_content"));
								content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
								mtlChannelDef.setAwardMount(Double.parseDouble(awardMount));
								mtlChannelDef.setEditUrl(editUrl);
								mtlChannelDef.setHandleUrl(handleUrl);
								mtlChannelDef.setSendSms(sendSms);
							} else if ("907".equals(channelId)) { // toolBar +
								adivId = String.valueOf(obj.get("adivId"));
								content = String.valueOf(obj.get("exec_content"));
								content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
								mtlChannelDef.setAdivId(adivId);
							} else if ("910".equals(channelId)) { // boss运营位
								adivId = String.valueOf(obj.get("adivId"));
								content = String.valueOf(obj.get("exec_content"));
								content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
								String messageType = String.valueOf(obj.get("messageType"));
								String temp = "";
								if ("1".equals(messageType)) {
									temp += "【业务告知】" + content + "(中国移动)";
								} else {
									temp += "【服务提醒】" + content + "(中国移动)";
								}
								content = temp;
								mtlChannelDef.setAdivId(adivId);

							} else if ("911".equals(channelId) || "912".equals(channelId)) { // 微信（全省）
																								// +
																								// 微信（温州）
								adivId = String.valueOf(obj.get("adivId"));
								content = String.valueOf(obj.get("exec_content"));
								content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
								String execTitle = String.valueOf(obj.get("execTitle")); // 微信标题
								String fileName = String.valueOf(obj.get("fileName")); // 微信本地文件名称
								mtlChannelDef.setAdivId(adivId);
								mtlChannelDef.setWcTitle(execTitle);
								mtlChannelDef.setWcFileName(fileName);
							} else if ("913".equals(channelId)) {
								String taskCode = String.valueOf(obj.get("taskCode"));// 任务编码
								String taskName = String.valueOf(obj.get("taskName"));// 任务名称
								String demand = String.valueOf(obj.get("demand"));// 需求方
								String taskClassId = String.valueOf(obj.get("taskClassId"));// 任务分类
								String tasklevel1Id = String.valueOf(obj.get("tasklevel1Id"));// 任务类型一级
								String taskLevel2Id = String.valueOf(obj.get("taskLevel2Id"));// 任务类型二级
								String taskLevel3Id = String.valueOf(obj.get("taskLevel3Id"));// 任务类型三级
								String busiLevel1Id = String.valueOf(obj.get("busiLevel1Id"));// 业务类型一级
								String busiLevel2Id = String.valueOf(obj.get("busiLevel2Id"));// 业务类型二级
								Integer inPlanFlag = obj.get("inPlanFlag") == null ? null
										: Integer.parseInt(obj.get("inPlanFlag").toString());// 是否计划内
								Integer monthPlanFlag = obj.get("monthPlanFlag") == null ? null
										: Integer.parseInt(obj.get("monthPlanFlag").toString());// 关联月度计划
								Integer callCycle = obj.get("callCycle") == null ? null
										: Integer.parseInt(obj.get("callCycle").toString());// 外呼周期
								Integer callPlanNum = obj.get("callPlanNum") == null ? null
										: Integer.parseInt(obj.get("callPlanNum").toString());// 计划外呼量
								String finishDate = String.valueOf(obj.get("finishDate"));// 要求完成时间
								String taskComment = String.valueOf(obj.get("taskComment"));// 任务描述
								String userLableInfo = String.valueOf(obj.get("userLableInfo"));// 客户标签信息
								String callQuestionUrl = String.valueOf(obj.get("callQuestionUrl"));// 外呼问卷地址
								Integer freFilterFlag = obj.get("freFilterFlag") == null ? null
										: Integer.parseInt(obj.get("freFilterFlag").toString());// 是否需要进行频次清洗
								String callForm = String.valueOf(obj.get("callForm"));// 外外呼形式问卷地址
								String callCityType = String.valueOf(obj.get("callCityType"));// 外呼属地

								mtlChannelDefCall = new MtlChannelDefCall();
								mtlChannelDefCall.setTaskCode(taskCode);
								mtlChannelDefCall.setTaskName(taskName);
								mtlChannelDefCall.setDemand(demand);
								mtlChannelDefCall.setTaskClassId(taskClassId);
								mtlChannelDefCall.setTasklevel1Id(tasklevel1Id);
								mtlChannelDefCall.setTaskLevel2Id(taskLevel2Id);
								mtlChannelDefCall.setTaskLevel3Id(taskLevel3Id);
								mtlChannelDefCall.setBusiLevel1Id(busiLevel1Id);
								mtlChannelDefCall.setBusiLevel2Id(busiLevel2Id);
								mtlChannelDefCall.setInPlanFlag(inPlanFlag);
								mtlChannelDefCall.setMonthPlanFlag(monthPlanFlag);
								mtlChannelDefCall.setCallCycle(callCycle);
								mtlChannelDefCall.setCallPlanNum(callPlanNum);
								mtlChannelDefCall.setFinishDate(finishDate);
								mtlChannelDefCall.setTaskComment(taskComment);
								mtlChannelDefCall.setUserLableInfo(userLableInfo);
								mtlChannelDefCall.setCallQuestionUrl(callQuestionUrl);
								mtlChannelDefCall.setFreFilterFlag(freFilterFlag);
								mtlChannelDefCall.setCallForm(callForm);
								mtlChannelDefCall.setCallCityType(callCityType);
							}

							if (StringUtils.isEmpty(adivId)) { // 当adivId为空的时候，默认为1
								mtlChannelDef.setAdivId("1");
							}
							mtlChannelDef.setIsHasVar(!ifHasVariate ? Short.parseShort("0") : Short.parseShort("1"));

							// 保存将筛选后的客户群数量
							String afterComputeCustNum[] = afterComputCustNum.split(",");
							String targetCustNum = "0";
							if (null != afterComputeCustNum && afterComputeCustNum.length > 0) {
								for (int k = 0; k < afterComputeCustNum.length; k++) {
									String temp[] = afterComputeCustNum[k].split("_");
									if (channelId.equals(temp[0])) {
										targetCustNum = temp[1];
									}
								}
							}

							if (StringUtils.isEmpty(channelCycle) && StringUtils.isNotEmpty(updateCycle)) { // 当channelCycle为空的时候
																											// 保持与updateCycle一致
								mtlChannelDef.setContactType(Integer.parseInt(updateCycle));
								mtlChannelDef.setUpdateCycle(Integer.parseInt(updateCycle));
							}

							mtlChannelDef.setTargetUserNums(Integer.parseInt(targetCustNum));
							mtlChannelDef.setChannelId(channelId);
							mtlChannelDef.setExecContent(content);
							mtlChannelDefList.add(mtlChannelDef);
						}

						McdCampDef campSeginfo = campSeginfoMap.get(campsegId);
						campSeginfo.setCampId(campsegId);
						campSeginfo.setCampName(campsegName);
						campSeginfo.setStartDate(putDateStart);
						campSeginfo.setEndDate(putDateEnd);
						campSeginfo.setStatId(Short.parseShort(McdCONST.MPM_CAMPSEG_STAT_CHZT));
						campSeginfo.setTypeId(Short.parseShort(campsegTypeId));// 策略类型
						campSeginfo.setCreateUserId(user.getId()); // 活动策划人
						campSeginfo.setCityId(user.getCityId()); // 策划人所属城市
						campSeginfo.setDeptId(Integer.parseInt(deptId)); // 策划人部门id
						campSeginfo.setCreateTime(format.parse(format.format(new Date())));
						campSeginfo.setPlanId(planIdArray[i]); // 产品编号
						campSeginfo.setCreateUserName(user.getName());
						// 渠道基本信息
						campSeginfo.setChannelId(channelIds); // 渠道id

						// 客户群清单表名称
						campSeginfo.setCustListTab(initCustListTab);
						campSeginfo.setIsFatherNode(false);
						campSeginfo.setIsApprove(isApprove);
						campSeginfo.setCampNo(String.valueOf(i)); // 多规则时，规则序号

						// 添加实时事件支持
						if (execContentStr.indexOf("cepInfo") != -1) {
						}

						campSeginfo.setCepEventId(streamsId);
						campSeginfo.setEventRuleDesc(streamName);

						// 客户群基本信息
						campSeginfo.setCustgroupId(customgroupid);

						campSeginfo.setMtlChannelDefList(mtlChannelDefList);
						campSeginfo.setMtlChannelDefCall(mtlChannelDefCall);
						// 产品订购或者剔除关系
						/*
						 * campSeginfo.setOrderPlanIds(orderProductNo);
						 * campSeginfo.setExcludePlanIds(excludeProductNo);
						 */

						// 先删除这条政策下的所有标签
						mpmCampSegInfoService.deleteLableByCampsegId(campsegId);
						// 保存业务标签
						/*
						 * if(bussinessLableTemplate != null){ String
						 * bussinessLableTemplateId =
						 * this.handleTemplet(request.getLocale(),
						 * bussinessLableTemplate);
						 * if(StringUtils.isNotEmpty(bussinessLableTemplateId)){
						 * campSeginfo.setBussinessLableTemplateId(
						 * bussinessLableTemplateId); } }
						 */
						// 保存时机规则
						/*
						 * if(basicEventTemplate != null){ String
						 * basicEventTemplateId =
						 * this.handleTemplet(request.getLocale(),
						 * basicEventTemplate);
						 * if(StringUtils.isNotEmpty(basicEventTemplateId)){
						 * campSeginfo.setBasicEventTemplateId(
						 * basicEventTemplateId); } }
						 */

						/*
						 * campSeginfo.setBussinessLableTemplate(
						 * bussinessLableTemplate);
						 * campSeginfo.setBasicEventTemplate(basicEventTemplate)
						 * ;
						 */
						// 保存基本信息 渠道与活动关系表 活动与客户群关系表
						campSegInfoList.add(campSeginfo);
					}
					// 统一进行保存
					String flag = mpmCampSegInfoService.updateCampSegWaveInfoZJ(campSegInfoList);
					if ("2".equals(flag)) { // 当flag等于2的时候，有审批流程，并且审批失败，0和1说明状态都正常
						dataJson.put("status", "201");
					} else {
						dataJson.put("status", "200");
					}
					out.print(dataJson);
				}
			}
		} catch (Exception e) {
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * JSON转map add by zhuml 20151203
	 * 
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> jsonToMap(String paramJson) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap = (Map<String, Object>) JSONObject.fromObject(paramJson);
		return paramsMap;
	}

	/**
	 * 新建策略页面---查询政策类别
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("initDimPlanType")
	public void initDimPlanType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		try {
			// 获取复杂事件规则地址
			McdSysInterfaceDef createCode = mtlCallWsUrlService.getCallwsURL(McdCONST.AIBI_MCD_CEP_CREATE_CODE);
			McdSysInterfaceDef createCodeCallBack = mtlCallWsUrlService
					.getCallwsURL(McdCONST.AIBI_MCD_CEP_CREATE_CODE_CALLBACK);

			List<McdDimPlanType> typeList = mtlStcPlanService.initDimPlanType();
			if (!CollectionUtils.isEmpty(typeList)) {
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(typeList));
				if (null != createCode && StringUtils.isNotEmpty(createCode.getCallwsUrl())) {
					dataJson.put("cepCreateCode", createCode.getCallwsUrl());
				} else {
					dataJson.put("cepCreateCode", "");
				}
				if (null != createCode && StringUtils.isNotEmpty(createCodeCallBack.getCallwsUrl())) {
					dataJson.put("cepCreateCodeCallBack", createCodeCallBack.getCallwsUrl());
				} else {
					dataJson.put("cepCreateCodeCallBack", "");
				}
				out.print(dataJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}

	}

	/**
	 * 新建策略页面---查询政策粒度
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("initGrade")
	public void initGrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		try {
			List<DimPlanSrvType> list = mtlStcPlanService.getGradeList();
			if (!CollectionUtils.isEmpty(list)) {
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(list));
				out.print(dataJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 新建策略页面---初始化适用渠道
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("initChannel")
	public void initChannel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		// 新建策略是否单选
		String isDoubleSelect = StringUtils.isNotEmpty(request.getParameter("isDoubleSelect"))
				? request.getParameter("isDoubleSelect") : "0";

		try {
			User user = this.getUser(request, response);
			List<McdDimChannel> list = mcdDimChannelService.getMtlChannelByCondition(isDoubleSelect);
			List<McdDimChannel> listTemp = new ArrayList<McdDimChannel>();

			String cityId = user.getCityId();
			if (!CollectionUtils.isEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					// 当不是温州的时候，不显示微信温州渠道
					if (!cityId.equals("577")) {
						String channelIdTemp = String.valueOf(list.get(i).getChannelId());
						if (!"912".equals(channelIdTemp)) {
							McdDimChannel dimMtlChannel = new McdDimChannel();
							dimMtlChannel.setTypeId(String.valueOf(list.get(i).getChannelId()));
							dimMtlChannel.setTypeName(list.get(i).getChannelName());
							listTemp.add(dimMtlChannel);
						}
					} else {
						McdDimChannel dimMtlChannel = new McdDimChannel();
						dimMtlChannel.setTypeId(String.valueOf(list.get(i).getChannelId()));
						dimMtlChannel.setTypeName(list.get(i).getChannelName());
						listTemp.add(dimMtlChannel);
					}
				}
			}
			if (!CollectionUtils.isEmpty(listTemp)) {
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(listTemp));
				out.print(dataJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 新建策略页面: 产品关系选择 按照类型和关键字查询 只查询政策力度为营销档次的产品信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchPlan")
	public void searchPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Pager pager = new Pager();
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		String pageNum = StringUtils.isNotEmpty(request.getParameter("pageNum")) ? request.getParameter("pageNum")
				: "1";
		String keyWords = StringUtils.isNotEmpty(request.getParameter("keyWords")) ? request.getParameter("keyWords")
				: null;
		String typeId = StringUtils.isNotEmpty(request.getParameter("typeid")) ? request.getParameter("typeid") : null;
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		try {
			String clickQueryFlag = "true";
			pager.setPageSize(McdCONST.SMALL_PAGE_SIZE_LABEL);
			pager.setPageNum(pageNum); // 当前页
			if (pageNum != null) {
				pager.setPageFlag("G");
			}
			pager.setTotalSize(mtlStcPlanManagementService.searchPlanCount(keyWords, typeId, cityId)); // 总记录数
			pager.getTotalPage();
			if ("true".equals(clickQueryFlag)) {
				List<PlanBean> resultList = mtlStcPlanManagementService.searchPlan(keyWords, typeId, cityId,
						pager);
				pager = pager.pagerFlip();
				pager.setResult(resultList);
			} else {
				pager = pager.pagerFlip();
				List<PlanBean> resultList = mtlStcPlanManagementService.searchPlan(keyWords, typeId, cityId,
						pager);
				pager.setResult(resultList);
			}

			dataJson.put("status", "200");
			dataJson.put("data", JmsJsonUtil.obj2Json(pager));
			out.print(dataJson);
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}

	}

	/**
	 * 新建策略页面，根据条件查询营销政策列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchByCondation")
	public void searchByCondation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Pager pager = new Pager();
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		User user = this.getUser(request, response);
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		String pageNum = StringUtils.isNotEmpty(request.getParameter("pageNum")) ? request.getParameter("pageNum")
				: "1";
		String keyWords = StringUtils.isNotEmpty(request.getParameter("keyWords")) ? request.getParameter("keyWords")
				: null;
		// 政策类别
		String typeId = StringUtils.isNotEmpty(request.getParameter("typeId")) ? request.getParameter("typeId") : null;
		// 使用渠道
		String channelTypeId = request.getParameter("channelTypeId") != null ? request.getParameter("channelTypeId")
				: null;
		// 查询粒度
		String planTypeId = request.getParameter("planTypeId") != null ? request.getParameter("planTypeId") : null;
		// 新建策略是否单选
		String isDoubleSelect = StringUtils.isNotEmpty(request.getParameter("isDoubleSelect"))
				? request.getParameter("isDoubleSelect") : "0";

		String cityId = user.getCityId();
		if (StringUtils.isNotEmpty(pageNum)) {
			pager.setPageFlag("G");
		}
		try {
			String clickQueryFlag = "true";
			pager.setPageSize(McdCONST.SMALL_PAGE_SIZE_LABEL); // 此处改为每页显示5条
			pager.setPageNum(pageNum); // 当前页
			if (pageNum != null) {
				pager.setPageFlag("G");
			}
			pager.setTotalSize(mtlStcPlanManagementService.getMtlStcPlanByCondationCount(keyWords, typeId,
					channelTypeId, planTypeId, cityId, isDoubleSelect)); // 总记录数
			pager.getTotalPage();
			if ("true".equals(clickQueryFlag)) {
				List<PlanBean> resultList = mtlStcPlanManagementService.getMtlStcPlanByCondation(keyWords, typeId,
						channelTypeId, planTypeId, cityId, isDoubleSelect, pager);
				pager = pager.pagerFlip();
				pager.setResult(resultList);
			} else {
				pager = pager.pagerFlip();
				List<PlanBean> resultList = mtlStcPlanManagementService.getMtlStcPlanByCondation(keyWords, typeId,
						channelTypeId, planTypeId, cityId, isDoubleSelect, pager);
				pager.setResult(resultList);
			}

			dataJson.put("status", "200");
			dataJson.put("data", JmsJsonUtil.obj2Json(pager));

			out.print(dataJson);
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 初始化渠道列表：短信、手机APP、营业厅、社会渠道等
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/initChannelMsg")
	public void initChannelMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();

		String isDoubleSelect = StringUtils.isNotEmpty(request.getParameter("isDoubleSelect"))? request.getParameter("isDoubleSelect") : "0";// 是否单选 单选：0 多选：1
		List<McdDimChannel> list = null;

		try {
			User user = this.getUser(request, response);
			list = mcdDimChannelService.getChannelMsg(isDoubleSelect);
			String cityId = user.getCityId();
			if (!cityId.equals("577")) {// 当不是温州的时候，不显示微信温州渠道
				for (int i = 0; i < list.size(); i++) {
					String channelId = list.get(i).getChannelId();
					if (channelId.equals("912")) {
						list.remove(i);
					}
				}
			}

			dataJson.put("status", "200");
			dataJson.put("data", JmsJsonUtil.obj2Json(list));
			out.print(dataJson);
		} catch (Exception e) {
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 选择政策：初始化短信执行情况 群发用语变量
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initTermLable")
	public void initTermLable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		String custGroupId = request.getParameter("custGroupId");
		List<McdCustgroupAttrList> list = null;
		try {
			if (StringUtils.isNotEmpty(custGroupId)) {
				list = custGroupAttrRelService.initTermLable(custGroupId);
			}
			if (!CollectionUtils.isEmpty(list)) {
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(list));
				out.print(dataJson);
			}
		} catch (Exception e) {
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 初始化Boss短信运营位模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/initMtlChannelBossSmsTemplate")
	public void initMtlChannelBossSmsTemplate(HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		List<ChannelBossSmsTemplate> list = null;
		try {
			list = channelBossSmsTemplateService.initMtlChannelBossSmsTemplate();
			if (!CollectionUtils.isEmpty(list)) {
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(list));
				out.print(dataJson);
			}
		} catch (Exception e) {
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 初始化营销类型
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/initDimCampsegType")
	public void initDimCampsegType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		List<McdDimCampType> list = null;
		try {
			list = dimCampsegTypeService.getAllDimCampsegType();
			if (!CollectionUtils.isEmpty(list)) {
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(list));
				out.print(dataJson);
			}
		} catch (Exception e) {
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 查询原始客户群数量 点击保存弹出页面，查询原始客户群、过滤后的客户群
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws MpmException
	 */
	@RequestMapping("/getOriginalCustGroupNumTemp")
	public void getOriginalCustGroupNumTemp(HttpServletRequest request, HttpServletResponse response)throws MpmException {
		long begin = new Date().getTime();
		JSONObject dataJson = new JSONObject();
		PrintWriter out = null;
		try {
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("progma", "no-cache");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			// 策略包 先循环迭代出每个规则
			String test = request.getParameter("ruleList");
			org.json.JSONObject ruleList = new org.json.JSONObject(test);
			List<List<Map<String, String>>> lists = new ArrayList<List<Map<String, String>>>(); // 存放每个rule的list
			List<Map<String, String>> listChannel = new ArrayList<Map<String, String>>(); // 存放每个rule下的渠道map
			for (int i = 0; i < ruleList.length(); i++) {
				String ruleIndex = "rule" + i;
				org.json.JSONObject rule = new org.json.JSONObject(ruleList.get(ruleIndex).toString()); // 迭代每一个规则进行计算
				// 获取渠道ID
				String channelIds = rule.get("channelIds").toString();
				// 获取客户群ID
				String customgroupid = null;
				if (StringUtils.isNotEmpty(rule.get("customer").toString())) {
					customgroupid = new org.json.JSONObject(rule.get("customer").toString()).get("id").toString();
				}
				int afterBotherAvoidNum = 0;
				int originalCustGroupNum = custGroupInfoService.getOriginalCustGroupNum(customgroupid); // 原始客户群数量

				if (StringUtils.isNotEmpty(channelIds)) {
					String channelIdTemp[] = channelIds.split(",");
					for (int j = 0; j < channelIdTemp.length; j++) {
						Map<String, String> map = new HashMap<String, String>();
						String channelId = channelIdTemp[j];
						int mdrNum = 0; // 免打扰数量
						int pcNum = 0; // 频次数量
						int satisfiedNum = 0; // 愿意数量
						int unSatisfiedNum = 0; // 不愿意数量
						int pcMdrNum = 0; // 免打扰频次数量

						map.put("ruleIndex", ruleIndex); // 记录是哪个规则
						map.put("channelId", channelId); // 记录哪个渠道
						map.put("originalCustGroupNum", String.valueOf(originalCustGroupNum));

						map.put("blackListNum", String.valueOf(mdrNum));
						map.put("avoidCustListNum", String.valueOf(pcNum));

						map.put("satisfiedNum", String.valueOf(satisfiedNum));
						map.put("unSatisfiedNum", String.valueOf(unSatisfiedNum));
						map.put("afterSatisfiedFilterNum", String.valueOf(satisfiedNum + unSatisfiedNum)); // 短信满意度过滤

						if (afterBotherAvoidNum - pcMdrNum - satisfiedNum - unSatisfiedNum < 0) {
							map.put("filterCustGroupNum", "0");
						} else {
							map.put("filterCustGroupNum",
									String.valueOf(afterBotherAvoidNum - pcMdrNum - satisfiedNum - unSatisfiedNum));
						}
						listChannel.add(map);
					}
				}
				lists.add(listChannel);
			}

			dataJson.put("status", "-1");
			dataJson.put("data", JmsJsonUtil.obj2Json(lists));
			out.print(dataJson);
		} catch (Exception e) {
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
			long end = new Date().getTime();
			System.out.println("*********************方法执行时间：" + (end - begin));
		}
	}

	/**
	 * 根据campsegId修改策略信息 回填修改参数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getCampsegInfo")
	public void getCampsegInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		try {
			// 父策略id
			String campsegPid = StringUtils.isNotEmpty(request.getParameter("campsegPid"))? request.getParameter("campsegPid") : "2016061317242609";
			List<McdCampDef> campsegList = null;
			List<McdCampDef> basicCampSeginfoList = new ArrayList<McdCampDef>(); // 父策略
			String planType = ""; // 政策类别
			Map<String, Object> map = new HashMap<String, Object>(); // 存放最终拼装的参数
			if (StringUtils.isNotEmpty(campsegPid)) {
				// 获取策略的基本信息
				campsegList = mpmCampSegInfoService.getCampSeginfoListByCampsegId(campsegPid);
				Map<String, Object> paramMap = new HashMap<String, Object>();
				for (int i = 0; i < campsegList.size(); i++) { // 区分出子策略和父策略
																// （兼容多规则）
					String ruleName = "";
					McdCampDef mtlCampSeginfo = campsegList.get(i);
					if ("0".equals(mtlCampSeginfo.getPid())) {
						basicCampSeginfoList.add(mtlCampSeginfo);
						map.put("commonAttr", basicCampSeginfoList);

						// 添加新逻辑
						String planIdArray[] = mtlCampSeginfo.getPlanId().split(",");
						List<McdPlanDef> planList = new ArrayList<McdPlanDef>();
						for (int j = 0; j < planIdArray.length; j++) {
							String planIdTemp = planIdArray[j];
							McdPlanDef mtlStcPlan = mtlStcPlanService.getMtlStcPlanByPlanID(planIdTemp);
							planList.add(mtlStcPlan);
						}
						if (CollectionUtils.isNotEmpty(planList)) {
							map.put("planList", planList);
						} else {
							map.put("planList", "");
						}
					} else if (!"0".equals(mtlCampSeginfo.getPid())) { // 子策略
						ruleName = "rule" + i;
						String campsegId = mtlCampSeginfo.getCampId();
						String initCustListTab = mtlCampSeginfo.getCustListTab();
						// 策略和标签的关系信息
						// List<MtlCampsegCustgroup> campsegCiCustgroupsListAll
						// =
						// mpmCampSegInfoService.findCustGroupListByCampsegId(campsegId);
						// 策略与客户群的关心信息
						List<McdCustgroupDef> custGroupList = mtlCampsegCustgroupService.getChoiceCustom(campsegId);
						// 基础标签
						// List<MtlCampsegCustgroup> basicLableList = new
						// ArrayList<MtlCampsegCustgroup>();
						// 业务标签
						/*
						 * List<MtlCampsegCustgroup> bussinessLableList = new
						 * ArrayList<MtlCampsegCustgroup>();
						 * 
						 * if(CollectionUtils.isNotEmpty(
						 * campsegCiCustgroupsListAll)){ for(int j =
						 * 0;j<campsegCiCustgroupsListAll.size();j++){
						 * MtlCampsegCustgroup mtlCampsegCiCustgroup =
						 * campsegCiCustgroupsListAll.get(j);
						 * if("CGT".equalsIgnoreCase(mtlCampsegCiCustgroup.
						 * getCustgroupType())){ //标签
						 * if("888".equals(mtlCampsegCiCustgroup.getAttrClassId(
						 * ))){ //基础标签
						 * basicLableList.add(mtlCampsegCiCustgroup); }else
						 * if("999".equals(mtlCampsegCiCustgroup.getAttrClassId(
						 * ))){ //业务标签
						 * bussinessLableList.add(mtlCampsegCiCustgroup); } } }
						 * }
						 */
						// 保存策略与渠道的关系
						List<McdCampChannelList> mtlChannelDefList = mtlChannelDefService.findMtlChannelDef(campsegId);
						// 所有适配的渠道
						List<McdPlanChannelList> AllChannel = mpmCampSegInfoService
								.getStcPlanChannel(mtlCampSeginfo.getPlanId());

						for (int j = 0; j < AllChannel.size(); j++) {
							McdPlanChannelList mtlStcPlanChannel = AllChannel.get(j);
							for (int k = 0; k < mtlChannelDefList.size(); k++) {
								if (mtlStcPlanChannel.getChannelId().equals(mtlChannelDefList.get(k).getChannelId())) {
									mtlStcPlanChannel.setChoose(true); // 如果之前做个修改
																		// 则设置为false
									break;
								} else {
									mtlStcPlanChannel.setChoose(false); // 如果之前做个修改
																		// 则设置为false
								}
							}
						}

						/*
						 * for(int n=0;n<mtlChannelDefList.size();n++){
						 * mtlChannelDefList.get(n).setChoose(true);
						 * //这个是已经做过处理的channel }
						 */

						for (int m = 0; m < AllChannel.size(); m++) {
							McdPlanChannelList planChannel = AllChannel.get(m);
							if (!planChannel.isChoose()) { // 如果之前没做过操作
								McdCampChannelList def = new McdCampChannelList();
								/*
								 * MtlChannelDefId mtlChannelDefId = new
								 * MtlChannelDefId();
								 * mtlChannelDefId.setCampsegId(campsegId);
								 * mtlChannelDefId.setChannelNo(m+10);
								 * mtlChannelDefId.setUsersegId((short) 0);
								 * def.setId(mtlChannelDefId);
								 */
								def.setCampId(campsegId);

								def.setChannelId(planChannel.getChannelId());
								// def.setChoose(false);
								mtlChannelDefList.add(def);
							}
						}

						// 实时事件中回显场景信息
						for (int q = 0; q < mtlChannelDefList.size(); q++) {
							McdCampChannelList channelDefTemp = mtlChannelDefList.get(q);
							String channelId = channelDefTemp.getChannelId();
							String functionId = "";
							String functionName = "";
							if ("910".equals(channelDefTemp.getChannelId())
									&& StringUtils.isNotEmpty(channelDefTemp.getExecContent())) {
								channelDefTemp.setMessageType("1");
								if (channelDefTemp.getExecContent().indexOf("服务提醒") != -1) {
									channelDefTemp.setMessageType("2");
								}
							}
							if ("901".equals(channelId)) {
								functionId = channelDefTemp.getFunctionId();
								// 获取场景名称
								if (StringUtils.isNotEmpty(functionId)) {
									List<RuleTimeTermLable> termLableList = custGroupAttrRelService
											.getFunctionNameById(functionId);
									if (termLableList.size() > 0) {
										functionName = termLableList.get(0).getFunctionNameDesc();
										channelDefTemp.setFunctionName(functionName);
									}
								}
							}

						}

						// 策略与产品订购关系
						/*
						 * List mtlCampSeginfoPlanOrderList =
						 * planOrderService.getPlanOrderByCampsegId(campsegId);
						 * MtlCampSeginfoPlanOrder mtlCampSeginfoPlanOrder =
						 * null; if(CollectionUtils.isNotEmpty(
						 * mtlCampSeginfoPlanOrderList)){ for(int
						 * n=0;n<mtlCampSeginfoPlanOrderList.size();n++){
						 * mtlCampSeginfoPlanOrder =
						 * (MtlCampSeginfoPlanOrder)mtlCampSeginfoPlanOrderList.
						 * get(n); } } List<MtlStcPlanBean> planOrderList =
						 * null; List<MtlStcPlanBean> planExcludeList = null;
						 * if(null != mtlCampSeginfoPlanOrder){
						 * if(StringUtils.isNotEmpty(mtlCampSeginfoPlanOrder.
						 * getOrderPlanIds())){ planOrderList =
						 * stcPlanService.getPlanByIds(mtlCampSeginfoPlanOrder.
						 * getOrderPlanIds()); }
						 * if(StringUtils.isNotEmpty(mtlCampSeginfoPlanOrder.
						 * getExcludePlanIds())){ planExcludeList =
						 * stcPlanService.getPlanByIds(mtlCampSeginfoPlanOrder.
						 * getExcludePlanIds()); } }
						 */
						if (CollectionUtils.isNotEmpty(custGroupList)) {
							paramMap.put("custGroupList", custGroupList);
						} else {
							paramMap.put("custGroupList", "");
						}

						/*
						 * if(CollectionUtils.isNotEmpty(basicLableList)){
						 * paramMap.put("basicLableList", basicLableList);
						 * }else{ paramMap.put("basicLableList", ""); }
						 * if(CollectionUtils.isNotEmpty(bussinessLableList)){
						 * paramMap.put("bussinessLableList",
						 * bussinessLableList); }else{
						 * paramMap.put("bussinessLableList", ""); }
						 */
						paramMap.put("mtlChannelDef", mtlChannelDefList);

						// 查询外呼渠道相关信息
						Map<String,Object> mtlChannelDefCallMap = mtlChannelDefService.getMtlChannelDefCall(campsegId,
								McdCONST.CHANNEL_TYPE_OUT_CALL);
						paramMap.put("mtlChannelDefCall", mtlChannelDefCallMap);

						/*
						 * if(CollectionUtils.isNotEmpty(planOrderList)){
						 * paramMap.put("planOrderList", planOrderList); }else{
						 * paramMap.put("planOrderList", ""); }
						 * 
						 * if(CollectionUtils.isNotEmpty(planExcludeList)){
						 * paramMap.put("planExcludeList", planExcludeList);
						 * }else{ paramMap.put("planExcludeList", ""); }
						 */
						paramMap.put("campsegId", campsegId);
						paramMap.put("planType", planType);
						paramMap.put("initCustListTab", initCustListTab);
						map.put(ruleName, paramMap);
					}
				}
			}
			if (map.size() > 0) {
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(map));
				out.print(dataJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * describute:选择营销人群，点击探索按钮，计算客户群数量
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/executeCustomGroup")
	public void executeCustomGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		try {
			User user = this.getUser(request, response);
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("progma", "no-cache");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			/*
			 * McdTempletForm bussinessLableTemplate = new McdTempletForm();
			 * McdTempletForm basicEventTemplate = new McdTempletForm();
			 */
			/*
			 * // 业务标签 String labelArr = request.getParameter("labelArr");
			 * bussinessLableTemplate =
			 * this.createTemplateForm(labelArr,"0",false); // 获取ARPU String
			 * ARPU = request.getParameter("basicProp"); basicEventTemplate =
			 * createTemplateForm(ARPU,"1",false);
			 */

			// 获取客户群ID
			String customgroupid = null;
			if (StringUtils.isNotEmpty(request.getParameter("customer"))) {
				customgroupid = new org.json.JSONObject(request.getParameter("customer")).getString("id");
			}
			// 产品订购
			/*
			 * String productArr = request.getParameter("productArr"); String
			 * productAttr[] = this.createProductAttr(productArr); String
			 * orderProductNo = productAttr[0]; //订购产品 String excludeProductNo =
			 * productAttr[1]; //剔除产品
			 */ // 计算客户群数量
			int custGroupNum = this.excuteCustGroupCount(customgroupid, null, null, request.getLocale(), null, null);
			dataJson.put("status", "200");

			// 计算渠道筛选过后的客户群
			String baseAttrStr = request.getParameter("baseAttr");
			org.json.JSONObject baseAttr = new org.json.JSONObject(baseAttrStr);
			String channelIds = baseAttr.getString("channelid");
			String campsegTypeId = baseAttr.getString("campsegtypeid");
			if (StringUtils.isEmpty(campsegTypeId) || "undefined".equals(campsegTypeId)) {
				campsegTypeId = "1";
			}
			String channelId[] = channelIds.split(",");
			if (channelId.length > 0 && StringUtils.isNotEmpty(channelIds)) {
				/*
				 * String bussinessLableSql =
				 * this.getSql(bussinessLableTemplate, request.getLocale());
				 * String basicEventSql = this.getSql(basicEventTemplate,
				 * request.getLocale());
				 */
				String cityId = user.getCityId();
				int campsegCityType = 1; // 地市类型 默认地市
				if (!cityId.isEmpty() && "999".equals(cityId)) {
					campsegCityType = 0; // 全省
				}
				StringBuffer buffer = new StringBuffer();
				buffer.append("[");
				for (int i = 0; i < channelId.length; i++) {
					// 免打扰、频次控制
					McdBotherContactConfig config = botherAvoidService.getMtlBotherContactConfig(campsegTypeId,
							channelId[i], campsegCityType);
					int mdrNum = 0;
					int pcNum = 0;
					int pcMdrNum = 0;
					// 只有短信(901)和boss运营位(910)时才做频次免打扰控制
					if (null != config && ("901".equals(channelId[i]) || "910".equals(channelId[i]))) {
						int avoidBotherFlag = config.getAvoidBotherFlag(); // 是否需要免打扰
						int contactControlFlag = config.getContactControlFlag(); // 是否需要接触控制
						List<Map<String,Object>> blackList = null;
						List<Map<String,Object>> avoidCustList = null;

						if (avoidBotherFlag == 1 && contactControlFlag == 1) { // 同时免打扰和频次控制
							// 免打扰
							blackList = custGroupInfoService.getAfterFilterCustGroupList(null, null, channelId[i],
									Integer.parseInt(campsegTypeId), customgroupid, null, null);
							avoidCustList = custGroupInfoService.getAfterBotherAvoid(null, null, channelId[i],
									Integer.parseInt(campsegTypeId), customgroupid, null, null, "999", null,
									avoidBotherFlag, 0);
							if (CollectionUtils.isNotEmpty(avoidCustList)) {
								Map<String, Object> mapT = (Map<String, Object>) avoidCustList.get(0);
								pcNum = Integer.parseInt(String.valueOf(mapT.get("pcNum")));
							}
							if (CollectionUtils.isNotEmpty(blackList)) {
								Map<String, Object> mapT = (Map<String, Object>) blackList.get(0);
								mdrNum = Integer.parseInt(String.valueOf(mapT.get("blackFilterNum")));
							}
							pcMdrNum = pcNum + mdrNum;
						} else if (avoidBotherFlag == 0 && contactControlFlag == 1) { // 不进行免打扰，只进行频次
							// 当只进行频次的时候，avoidCustList既是总数又是免打扰的数量
							// avoidCustList =
							// custGroupInfoService.getAfterBotherAvoid(null,
							// null, channelId[i],
							// Integer.parseInt(campsegTypeId), customgroupid,
							// null, null,user,null,avoidBotherFlag,0);
							avoidCustList = custGroupInfoService.getAfterBotherAvoid(null, null, channelId[i],
									Integer.parseInt(campsegTypeId), customgroupid, null, null, "999", null,
									avoidBotherFlag, 0);
							if (CollectionUtils.isNotEmpty(avoidCustList)) {
								Map<String,Object> mapT = (Map<String,Object>) avoidCustList.get(0);
								pcNum = Integer.parseInt(String.valueOf(mapT.get("pcNum")));
							}
							pcMdrNum = pcNum;
						} else if (avoidBotherFlag == 1 && contactControlFlag == 0) { // 进行免打扰，不进行频次
							blackList = custGroupInfoService.getAfterFilterCustGroupList(null, null, channelId[i],
									Integer.parseInt(campsegTypeId), customgroupid, null, null);
							if (CollectionUtils.isNotEmpty(blackList)) {
								Map<String,Object> mapT = (Map<String,Object>) blackList.get(0);
								pcMdrNum = Integer.parseInt(String.valueOf(mapT.get("blackFilterNum")));
								mdrNum = pcMdrNum;
							}
						}
					}
					if (i == channelId.length - 1) { // 最后一个
						if (custGroupNum - pcMdrNum < 0) {
							buffer.append("{channelId:" + channelId[i] + ",value:0}");
						} else {
							buffer.append("{channelId:" + channelId[i] + ",value:" + (custGroupNum - pcMdrNum) + "}");
						}
					} else {
						if (custGroupNum - pcMdrNum < 0) {
							buffer.append("{channelId:" + channelId[i] + ",value:0},");
						} else {
							buffer.append("{channelId:" + channelId[i] + ",value:" + (custGroupNum - pcMdrNum) + "},");
						}
					}
				}
				buffer.append("]");
				dataJson.put("channelIdCustNum", buffer.toString());
			}

			dataJson.put("data", "[{custGroupNum:" + custGroupNum + "}]");
			out.print(dataJson);
		} catch (Exception e) {
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 根据业务标签 基础标签 产品订购 计算我的客户群数量 add by zhanghy2 at 2015-12-06 because of huge
	 * custom group
	 * 
	 * @return
	 */
	private int excuteCustGroupCount(String customgroupid, McdTempletForm bussinessLableTemplate,McdTempletForm basicEventTemplate, Locale local, String orderProductNo, String excludeProductNo) {
		int custCnt = 0;
		try {
			/*
			 * String bussinessLableSql = null; if(null !=
			 * bussinessLableTemplate){ bussinessLableSql =
			 * this.getSql(bussinessLableTemplate, local); } String
			 * basicEventSql = null; if(null != basicEventTemplate){
			 * basicEventSql = this.getSql(basicEventTemplate, local); }
			 */
			custCnt = custGroupInfoService.getCustInfoCount(customgroupid, null, null, orderProductNo,
					excludeProductNo);
		} catch (Exception e) {
		}

		return custCnt;
	}

	// -------------------------------------------------------------新代码--------------------------------------------------------------------
	
	
	/**
	 * 创建活动页面：初始化产品类型模块
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public JSONObject queryPlanTypes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject dataJson = new JSONObject();
		List<McdDimPlanType> planTypes = mtlStcPlanService.initDimPlanType();
		List<McdDimChannel> channels = mcdDimChannelService.getAllChannels();
		dataJson.put("planTypes", planTypes);
		dataJson.put("channels", channels);
		return dataJson;
	}

	/**
	 * 创建活动页面：根据条件查询产品列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public Pager queryPlansByCondition(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Pager pager = new Pager();
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		int pageSize = request.getParameter("pageSize")==null?McdCONST.SMALL_PAGE_SIZE_LABEL:Integer.parseInt( request.getParameter("pageSize"));
		String pageNum = StringUtils.isNotEmpty(request.getParameter("pageNum")) ? request.getParameter("pageNum"): "1";
		String keyWords = StringUtils.isNotEmpty(request.getParameter("keyWords")) ? request.getParameter("keyWords"): null;
		// 产品类型
		String planTypeId = StringUtils.isNotEmpty(request.getParameter("planTypeId"))? request.getParameter("planTypeId") : null;
		// 适用渠道
		String channelId = request.getParameter("channelId") != null ? request.getParameter("channelId") : null;
		// 产品类别（1单产品？2政策？）
		String planSrvType = request.getParameter("planSrvType") != null ? request.getParameter("planSrvType") : null;

		try {
			pager.setPageSize(pageSize); 
			pager.setPageNum(pageNum); // 当前页
			List<PlanBean> list = mtlStcPlanService.getPlanByCondition(cityId, planTypeId, planSrvType, channelId, keyWords,pager);
			pager.setResult(list);
		} catch (Exception e) {
			log.error("查询产品列表失败",e);
		}
		return pager;
	}
	/**
	 * 创建活动页面：根据所选产品id返回该产品的所有适用渠道（逗号分隔）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	List<McdPlanChannelList> selectPlanBackChannels(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String planId = request.getParameter("planId");
		List<McdPlanChannelList> channels = mcdPlanChannelListService.getChannelsByPlanId(planId);
		return channels;
	}
	
	/**
	 * 创建策略界面：展示渠道列表（选渠道模块）
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping
	public List<McdDimChannel> getChannels(HttpServletRequest request, HttpServletResponse response){
		return  mcdDimChannelService.getAllChannels();
	}
	
	/**
	 * 创建产品界面：返回客户群列表。（选客户群模块）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public Pager getMoreMyCustom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		User user = this.getUser(request, response);
		List<McdCustgroupDef> resultList =null;
		int pageSize = request.getParameter("pageSize")==null?6:Integer.parseInt( request.getParameter("pageSize"));
		Pager pager=new Pager();
		String keyWords = StringUtils.isNotEmpty(request.getParameter("keyWords")) ? request.getParameter("keyWords") : null;
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		try {
			pager.setPageSize(pageSize);  
			pager.setPageNum(pageNum);  //当前页
			pager.setTotalSize(custGroupInfoService.getMoreMyCustomCount(user.getId(),keyWords));
			resultList = custGroupInfoService.getMoreMyCustom(user.getId(),keyWords,pager);
			pager.setResult(resultList);
			
		} catch (Exception e) {
			log.error("",e);
		}
        return pager;
		
	}
	
	/**
	 * 创建活动页面，选择渠道时，根据产品id和渠道id返回运营位列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public Map<String,Object> getAdivInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> res = new HashMap<String,Object>();
		String planId = request.getParameter("planId");
		String channelId = request.getParameter("channelId");
		List<McdDimAdivInfo> list = null;
		try {
			
			String adivdPictureURL = AppConfigService.getProperty("ADIVD_PICTURE_URL");
			list = planChannelAdivResourceService.getAdivByPlanChannel(planId, channelId);
			if(!CollectionUtils.isEmpty(list)){
				res.put("adivdPictureURL", adivdPictureURL);
				res.put("data", list);
			}
		} catch (Exception e) {
			log.error("",e);
		}
		return res;
	}
	
	/**
	 * 选择客户群时：根据客户群id，返回客户群的所有变量
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public List<McdCustgroupAttrList> getCustGroupVars(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		String custGroupId = request.getParameter("custGroupId");
		List<McdCustgroupAttrList> list = null;
		try {
			if (StringUtils.isNotEmpty(custGroupId)) {
				list = custGroupAttrRelService.initTermLable(custGroupId);
			}
			
		} catch (Exception e) {
			log.error("",e);
		}
		return list; 
	}
	
	/**
	 * 保存或更新策略接口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public Map<String,String> saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> rs = new HashMap<String,String>();
		List<McdCampDef> campSegInfoList = new ArrayList<McdCampDef>();//需要保存的策略列表
		String flag = "";//返回前台标记位 0:保存成功,1:提交审批成功,2:提交审批失败,3:异常
		try {
			User user = this.getUser(request, response);
			String data = request.getParameter("data");
			JSONObject campJson = JSONObject.fromObject(data);
			String commonAttr = campJson.get("campInfo").toString();// 获取公共属性
			
			// 先生成一个父节点，一个子节点
			McdCampDef campSeginfoBasic = (McdCampDef) JSONObject.toBean(JSONObject.fromObject(commonAttr), McdCampDef.class);
			McdCampDef campSeginfoSub = (McdCampDef) JSONObject.toBean(JSONObject.fromObject(commonAttr), McdCampDef.class);
			
			String campId = campSeginfoSub.getCampId(); // 父策略id
			boolean isModify = campId==null?false:true;
			if(!isModify){//创建
				campSeginfoBasic.setCampId(MpmUtil.generateCampsegAndTaskNo());//设置父节点的Id
				campSeginfoBasic.setPid("0");// 设置父节点的Pid
				campSeginfoSub.setPid(campSeginfoBasic.getCampId());//设置子节点的Pid
			}else{
				McdCampDef camp = mpmCampSegInfoService.getCampSegInfo(campId);//获取待修改的策略
				campSeginfoBasic.setCampId(camp.getPid());//设置父节点的Id
				campSeginfoBasic.setPid("0");// 设置父节点的Pid
				campSeginfoSub.setPid(camp.getPid());//设置子节点的Pid
			}
			
			campSegInfoList.add(campSeginfoBasic);
			
			// 设置子节点的渠道信息
			String channelsInfo =  campJson.get("channelsInfo").toString();// 获取公共属性
			JSONArray channelsInfoArray = JSONArray.fromObject(channelsInfo);				
			List<McdCampChannelList> mtlChannelDefList = new ArrayList<McdCampChannelList>();
			for (int j = 0; j < channelsInfoArray.size(); j++) {
					JSONObject obj = (JSONObject) channelsInfoArray.get(j);
					McdCampChannelList mtlChannelDef = (McdCampChannelList) JSONObject.toBean(obj, McdCampChannelList.class);
					if(mtlChannelDef.getAdivId()==null){//渠道默认运营位为1
						mtlChannelDef.setAdivId("1");
					}
					mtlChannelDefList.add(mtlChannelDef);
			}
			campSeginfoSub.setMtlChannelDefList(mtlChannelDefList);
			campSegInfoList.add(campSeginfoSub);

			// 统一进行保存
			flag = mpmCampSegInfoService.saveOrUpdateCampInfo(user,campSegInfoList,isModify);
			rs.put("flag", flag);
		} catch (Exception e) {
			log.error("保存异常", e);
			flag = McdCONST.TACTICS_SAVE_FAIL;
		}
		return rs; 
	}

	/**
	 * 根据campsegId修改策略信息 回填修改参数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public Map<String, Object> getCampInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		Map<String, Object> map = new HashMap<String, Object>(); // 存放最终拼装的参数
		
		try {
			// 父策略id
			String campsegPid = StringUtils.isNotEmpty(request.getParameter("pid"))? request.getParameter("pid") : "2016061317242609";
			
			// 获取策略的基本信息
			McdCampDef subCampInfo = mpmCampSegInfoService.getCampByPid(campsegPid);//获取子策略
			map.put("campInfo",subCampInfo);
			//产品信息
			McdPlanDef mtlStcPlan = mtlStcPlanService.getMtlStcPlanByPlanID(subCampInfo.getPlanId());
			map.put("planInfo",mtlStcPlan);
			// 渠道信息
			List<McdCampChannelList> mtlChannelDefList = mtlChannelDefService.findMtlChannelDef(subCampInfo.getCampId());
			map.put("channelsInfo",mtlChannelDefList);
			// 策略与客户群的关心信息
			McdCustgroupDef custGroup = mtlCampsegCustgroupService.getCustGroupByCamp(subCampInfo.getCampId());
			map.put("custGroupInfo",custGroup);

		} catch (Exception e) {
			log.error("根据策略id获取信息失败", e);
		}
		return map;
	}
	
	/**
	 * 创建策略界面：查看客户群详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping
	public Map<String,Object> viewCustGroupDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String customGrpId = request.getParameter("custGroupId");
		Map<String,Object> data = null;
		data = custGroupInfoService.queryCustGroupDetail(customGrpId);
		return data;
	}
	/**
	 * 返回BOSS运营位的所有短信模板
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public List<McdDimSmsbossTemplateType> getBossSmsTemplate(HttpServletRequest request, HttpServletResponse response)throws Exception {
		List<McdDimSmsbossTemplateType> rs = null;
		try {
			rs = channelBossSmsTemplateService.getBossSmsTemplateTypes();
		} catch (Exception e) {
			log.error("", e);
		}
		return rs; 
	}
	/**
	 * 根根产品id 返回产品信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping
	public McdPlanDef getPlanById(HttpServletRequest request, HttpServletResponse response){
		String id =  request.getParameter("planId");
		return mtlStcPlanService.getMtlStcPlanByPlanID(id);
	}
	/**
	 * 根根产品id、 获得某些渠道预览的产品酬金等相关信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping
	public Map<String,Object> getPlanRewardAndScoreInfo(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		String planId =  request.getParameter("planId");
		String channelId = request.getParameter("channelId");
		try {
			User user = this.getUser(request, response);
			String cityId = user.getCityId();
			//积分、酬金信息查询
			Map<String, String> reward = mtlStcPlanService.getPlanRewardAndScoreInfo(planId, cityId);
			
			//上月的订购数、订购率查询
			Map<String, Object> orderMount = mtlStcPlanService.getLastMonthPlanOrderRateInfo(planId, channelId);
			returnMap.put("flag", true);
			returnMap.put("msg", "");
			returnMap.put("reward", reward);
			returnMap.put("orderMount", orderMount);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
			returnMap.put("flag", false);
			returnMap.put("msg", "获取酬金信息失败！");
		}
		return returnMap;
	}
	
}
