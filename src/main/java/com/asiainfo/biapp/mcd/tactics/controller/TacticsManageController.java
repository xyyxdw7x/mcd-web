package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.service.IMpmCommonService;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlStcPlanManagementService;
import com.asiainfo.biapp.mcd.tactics.vo.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.tactics.vo.DimPlanSrvType;
import com.asiainfo.biapp.mcd.tactics.vo.DimPlanType;
import com.asiainfo.biapp.mcd.tactics.vo.LkgStaff;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCallwsUrl;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDef;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefCall;
import com.asiainfo.biapp.mcd.tactics.vo.MtlStcPlanBean;
import com.asiainfo.biframe.utils.spring.SystemServiceLocator;
import com.asiainfo.biframe.utils.string.StringUtil;

import net.sf.json.JSONObject;
@RequestMapping("/tactics/tacticsManage")
public class TacticsManageController extends BaseMultiActionController {
    @Resource(name="mpmCampSegInfoService")
    private IMpmCampSegInfoService mpmCampSegInfoService; 
    @Resource(name="mpmCommonService")
    private IMpmCommonService mpmCommonService; 
    @Resource(name="mtlCallWsUrlService")
    private IMtlCallWsUrlService mtlCallWsUrlService;
    @Resource(name="mtlStcPlanManagementService")
    private IMtlStcPlanManagementService mtlStcPlanManagementService;
    
	/**
	 * 保存策略基本信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	/*public void saveCampsegWaveInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		JSONObject dataJson = new JSONObject();
		List<MtlCampSeginfo> campSegInfoList = new ArrayList<MtlCampSeginfo>();
		int whc = 0;
		int whnum = 0;
		String month = "";
		try {
			initActionAttributes(request);
			McdTempletForm bussinessLableTemplate = new McdTempletForm();
			McdTempletForm basicEventTemplate = new McdTempletForm();
			// 策略包 先循环迭代出每个规则
			String test = request.getParameter("ruleList");
			org.json.JSONObject ruleList = new org.json.JSONObject(test);
			// 获取公共属性
			org.json.JSONObject commonAttr = new org.json.JSONObject(ruleList.get("commonAttr").toString());
			String campsegName = commonAttr.get("campsegName").toString();
			String putDateStart = commonAttr.get("putDateStart").toString();
			String putDateEnd = commonAttr.get("putDateEnd").toString();
			String campsegTypeId = commonAttr.get("campsegTypeId").toString();
			if (StringUtil.isEmpty(campsegTypeId)) {
				campsegTypeId = "1";
			}

			String isFilterDisturb = commonAttr.get("isFilterDisturb").toString(); // 是否需要免打扰控制
			String planId = commonAttr.get("planid").toString();
			// 审批标识
			String isApprove = commonAttr.get("isApprove").toString();
			// 先保存基本信息 父亲节点
			MtlCampSeginfo campSeginfoBasic = new MtlCampSeginfo();
			campSeginfoBasic.setCampsegName(campsegName);
			campSeginfoBasic.setStartDate(putDateStart);
			campSeginfoBasic.setEndDate(putDateEnd);
			campSeginfoBasic.setCampsegPid("0");// 父节点
			campSeginfoBasic.setCreateUserid(user.getUserid());
			campSeginfoBasic.setCreateUserName(user.getUsername());
			campSeginfoBasic.setCampsegTypeId(Short.parseShort(campsegTypeId));
			campSeginfoBasic.setPlanId(planId);
			campSeginfoBasic.setFatherNode(true);
			campSeginfoBasic.setAreaId(user.getCityid());
			campSeginfoBasic.setCityId(user.getCityid()); // 策划人所属城市
			campSeginfoBasic.setIsFileterDisturb(Integer.parseInt(isFilterDisturb));
			String deptId = "";
			String deptName = "";
			if (user != null) {
				LkgStaff staff = (LkgStaff) user;
				String deptMsg = staff.getDepId();
				if (deptMsg.indexOf("&&") > 0) {
					deptId = deptMsg.split("&&")[0];
					deptName = deptMsg.split("&&")[1];
				}
				campSeginfoBasic.setDeptId(Integer.parseInt(deptId)); // 策划人部门id
			}
			campSegInfoList.add(campSeginfoBasic);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			String planIdArray[] = planId.split(","); // 当时多产品的时候

			for (int i = 0; i < planIdArray.length; i++) {
				String ruleIndex = "rule0";
				org.json.JSONObject rule = new org.json.JSONObject(ruleList.get(ruleIndex).toString()); // 迭代每一个规则进行计算

				// 业务标签
				String labelArr = rule.get("labelArr").toString();
				if (StringUtil.isEmpty(labelArr) || labelArr.equals("[]")) {
					bussinessLableTemplate = null;
				} else {
					bussinessLableTemplate = this.createTemplateForm(labelArr, "0", true);
				}
				// 获取ARPU 基础标签
				String basicProp = rule.get("basicProp").toString();
				if (StringUtil.isEmpty(basicProp) || basicProp.equals("[]")) {
					basicEventTemplate = null;
				} else {
					basicEventTemplate = createTemplateForm(basicProp, "1", true);
				}
				// 产品订购
				String productArr = rule.get("productArr").toString();
				String productAttr[] = this.createProductAttr(productArr);
				String orderProductNo = productAttr[0]; // 订购产品
				String excludeProductNo = productAttr[1]; // 剔除产品
				// 获取渠道ID
				String channelIds = rule.get("channelIds").toString();

				// 获取客户群ID
				String customgroupid = null;
				String updateCycle = null;
				if (StringUtil.isNotEmpty(rule.get("customer").toString())) {
					customgroupid = new org.json.JSONObject(rule.get("customer").toString()).get("id").toString();
					updateCycle = new org.json.JSONObject(rule.get("customer").toString()).get("updatecycle").toString();
				}
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
				// 筛选后的客户群数量
				String afterComputCustNum = rule.get("afterComputCustNum").toString();
				// 获取渠道执行的基本信息
				String execContentStr = rule.get("execContent").toString();
				org.json.JSONArray execContent = new org.json.JSONArray(execContentStr);
				List<MtlChannelDef> mtlChannelDefList = new ArrayList<MtlChannelDef>();
				MtlChannelDefCall mtlChannelDefCall = null;

				String cepEventId = "";
				String eventRuleDesc = "";
				String streamsId = "";
				String streamName = "";
				for (int j = 0; j < execContent.length(); j++) {
					org.json.JSONObject obj = new org.json.JSONObject(execContent.get(j).toString());
					Map<String, Object> objMap = jsonToMap(execContent.get(j).toString());
					// 判断是否包含实时事件
					if (execContentStr.indexOf("cepInfo") != -1) {
						String cepInfo = obj.get("cepInfo").toString();
						org.json.JSONObject cepInfoObject = new org.json.JSONObject(cepInfo);
						cepEventId = cepInfoObject.getString("streamsId");
						org.json.JSONArray functionList = new org.json.JSONArray(cepInfoObject.getString("functionList"));
						for (int m = 0; m < functionList.length(); m++) {
							org.json.JSONObject function = new org.json.JSONObject(functionList.get(m).toString());
							String functionCn = String.valueOf(function.get("functionCn"));
							eventRuleDesc += functionCn + "&";
						}
					}

					MtlChannelDef mtlChannelDef = new MtlChannelDef();
					String channelId = String.valueOf(obj.get("chanelId"));
					String content = "";
					boolean ifHasVariate = false;
					String channelCycle = "";
					String adivId = "";

					if ("901".equals(channelId)) { // 短信渠道
						channelCycle = String.valueOf(obj.get("channelCycle"));
						String channelTrigger = String.valueOf(obj.get("channelTrigger"));
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
						if (StringUtil.isNotEmpty(channelCycle)) {
							mtlChannelDef.setContactType(Integer.parseInt(channelCycle));
						}
						mtlChannelDef.setCepTriggerFlag(Integer.parseInt(channelTrigger));
						if (StringUtil.isEmpty(paramDays)) {
							paramDays = "0";
						}
						if (StringUtil.isEmpty(paramNum)) {
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
							mtlChannelDef.setEventActiveTempletId(streamsId);
							mtlChannelDef.setEventInstanceDesc(p.matcher(eventInstanceDesc).replaceAll(""));
							if ("20160309093621645766".equals(functionId)|| "20160309093745413830".equals(functionId)) { // 汪斌修改回显场景，特殊处理
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
						mtlChannelDef.setChannelAdivId(adivId);
					} else if ("904".equals(channelId)) { // 10086热线
						String awardMount = String.valueOf(obj.get("awardMount"));
						String editUrl = String.valueOf(obj.get("editUrl"));
						String handleUrl = String.valueOf(obj.get("handleUrl"));
						String sendSms = String.valueOf(obj.get("sendSms")); // 短信用语
						sendSms = URLDecoder.decode(URLDecoder.decode(sendSms, "UTF-8"), "UTF-8");
						content = String.valueOf(obj.get("exec_content")); // 推荐用语
						content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
						if (StringUtil.isNotEmpty(awardMount)) {
							mtlChannelDef.setAwardMount(Double.parseDouble(awardMount));
						}
						mtlChannelDef.setEditUrl(editUrl);
						mtlChannelDef.setHandleUrl(handleUrl);
						mtlChannelDef.setSendSms(sendSms);
					} else if ("907".equals(channelId)) { // toolBar +
						adivId = String.valueOf(obj.get("adivId"));
						content = String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
						mtlChannelDef.setChannelAdivId(adivId);
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
						mtlChannelDef.setChannelAdivId(adivId);

					} else if ("911".equals(channelId) || "912".equals(channelId)) { // 微信（全省）
																						// +
																						// 微信（温州）
						adivId = String.valueOf(obj.get("adivId"));
						content = String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content, "UTF-8"), "UTF-8");
						String execTitle = String.valueOf(obj.get("execTitle")); // 微信标题
						String fileName = String.valueOf(obj.get("fileName")); // 微信本地文件名称
						// //Ftp存放的文件名称
						mtlChannelDef.setChannelAdivId(adivId);
						mtlChannelDef.setExecTitle(execTitle);
						mtlChannelDef.setFileName(fileName);
					} 


					mtlChannelDef.setIfHaveVar(!ifHasVariate ? Short.parseShort("0") : Short.parseShort("1"));

					if (StringUtil.isEmpty(adivId)) { // 当adivId为空的时候，默认为1
						mtlChannelDef.setChannelAdivId("1");
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
					if (StringUtil.isEmpty(channelCycle) && StringUtil.isNotEmpty(updateCycle)) { // 当channelCycle为空的时候保持与updateCycle一致
						mtlChannelDef.setContactType(Integer.parseInt(updateCycle));
						mtlChannelDef.setUpdateCycle(Integer.parseInt(updateCycle));
					}

					mtlChannelDef.setTargetUserNums(Integer.parseInt(targetCustNum));
					mtlChannelDef.setChannelId(channelId);
					mtlChannelDef.setExecContent(content);
					mtlChannelDefList.add(mtlChannelDef);
				}

				MtlCampSeginfo campSeginfo = new MtlCampSeginfo();
				campSeginfo.setCampsegName(campsegName);
				campSeginfo.setStartDate(putDateStart);
				campSeginfo.setEndDate(putDateEnd);
				campSeginfo.setCampsegStatId(Short.parseShort(MpmCONST.MPM_CAMPSEG_STAT_HDSP));
				campSeginfo.setCampsegTypeId(Short.parseShort(campsegTypeId));// 策略类型
				campSeginfo.setCreateUserid(user.getUserid()); // 活动策划人
				campSeginfo.setCityId(user.getCityid()); // 策划人所属城市
				campSeginfo.setDeptId(Integer.parseInt(deptId)); // 策划人部门id
				campSeginfo.setCreateTime(format.parse(format.format(new Date())));
				campSeginfo.setPlanId(planIdArray[i]); // 产品编号
				campSeginfo.setCreateUserName(user.getUsername());
				campSeginfo.setIsFileterDisturb(Integer.parseInt(isFilterDisturb));
				// 渠道基本信息
				campSeginfo.setChannelId(channelIds); // 渠道id
				campSeginfo.setChannelTypeId(channelTypeId); // 渠道类型ID

				campSeginfo.setFatherNode(false);
				campSeginfo.setIsApprove(isApprove);
				campSeginfo.setCampsegNo(String.valueOf(i)); // 多规则时，规则序号

				// 添加实时事件支持
				if (execContentStr.indexOf("cepInfo") != -1) {
				}
				campSeginfo.setCepEventId(streamsId);
				campSeginfo.setEventRuleDesc(streamName);
				// 客户群基本信息
				campSeginfo.setCustgroupId(customgroupid);
				campSeginfo.setUpdatecycle(updateCycle);

				campSeginfo.setMtlChannelDefList(mtlChannelDefList);
				campSeginfo.setMtlChannelDefCall(mtlChannelDefCall);
				// 产品订购或者剔除关系
				campSeginfo.setOrderPlanIds(orderProductNo);
				campSeginfo.setExcludePlanIds(excludeProductNo);

				// 保存业务标签
				if (bussinessLableTemplate != null) {
					String bussinessLableTemplateId = this.handleTemplet(request.getLocale(), bussinessLableTemplate);
					if (StringUtil.isNotEmpty(bussinessLableTemplateId)) {
						campSeginfo.setBussinessLableTemplateId(bussinessLableTemplateId);
					}
				}
				// 保存时机规则
				if (basicEventTemplate != null) {
					String basicEventTemplateId = this.handleTemplet(request.getLocale(), basicEventTemplate);
					if (StringUtil.isNotEmpty(basicEventTemplateId)) {
						campSeginfo.setBasicEventTemplateId(basicEventTemplateId);
					}
				}

				campSeginfo.setBussinessLableTemplate(bussinessLableTemplate);
				campSeginfo.setBasicEventTemplate(basicEventTemplate);
				campSeginfo.setRequestLocal(request.getLocale());
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
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}
	*/
	/**
	 * JSON转map
	 * add by zhuml 20151203
	 * @param paramJson
	 * @return
	 */
	private Map<String, Object> jsonToMap(String paramJson ){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap = (Map<String, Object>) JSONObject.fromObject(paramJson);
		return paramsMap;
	}

	/**
	 * 新建策略页面---查询政策类别
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=initDimPlanType")
	public void initDimPlanType(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		try {
			//获取复杂事件规则地址
			MtlCallwsUrl createCode = mtlCallWsUrlService.getCallwsURL(MpmCONST.AIBI_MCD_CEP_CREATE_CODE);
			MtlCallwsUrl createCodeCallBack = mtlCallWsUrlService.getCallwsURL(MpmCONST.AIBI_MCD_CEP_CREATE_CODE_CALLBACK);
			
			List<DimPlanType> typeList = mpmCommonService.initDimPlanType();
			if(!CollectionUtils.isEmpty(typeList)){
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(typeList));
				if(null != createCode && StringUtil.isNotEmpty(createCode.getCallwsUrl())){
					dataJson.put("cepCreateCode", createCode.getCallwsUrl());
				}else{
					dataJson.put("cepCreateCode", "");
				}
				if(null != createCode && StringUtil.isNotEmpty(createCodeCallBack.getCallwsUrl())){
					dataJson.put("cepCreateCodeCallBack", createCodeCallBack.getCallwsUrl());
				}else{
					dataJson.put("cepCreateCodeCallBack", "");
				}
				out.print(dataJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		}finally{
			out.flush();
			out.close();
		}
		
	}
	
	/**
	 * 新建策略页面---查询政策粒度
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=initGrade")
	public void initGrade(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		try {
			List<DimPlanSrvType> list = mpmCommonService.getGradeList();
			if(!CollectionUtils.isEmpty(list)){
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(list));
				out.print(dataJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		}finally{
			out.flush();
			out.close();
		}
	}

	/**
	 * 新建策略页面---初始化适用渠道
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=initChannelType")
	public void initChannelType(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//TODO：initActionAttributes(request);
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		//新建策略是否单选
		String isDoubleSelect = StringUtil.isNotEmpty(request.getParameter("isDoubleSelect")) ? request.getParameter("isDoubleSelect") : "0";
				
		try {
			List<DimMtlChanneltype> list = mpmCommonService.getMtlChanneltypeByCondition(isDoubleSelect);
			List<DimMtlChanneltype> listTemp = new ArrayList<DimMtlChanneltype>();
			
			//TODO：String cityId = user.getCityid();
			String cityId = "999";
			if(!CollectionUtils.isEmpty(list)){
				for(int i = 0;i<list.size();i++){
					//当不是温州的时候，不显示微信温州渠道
					if(!cityId.equals("577")){
						String channelIdTemp = String.valueOf(list.get(i).getChanneltypeId());
						if(!"912".equals(channelIdTemp)){
							DimMtlChanneltype dimMtlChanneltype = new DimMtlChanneltype();
							dimMtlChanneltype.setTypeId(String.valueOf(list.get(i).getChanneltypeId()));
							dimMtlChanneltype.setTypeName(list.get(i).getChanneltypeName());
							listTemp.add(dimMtlChanneltype);
						}
					}else{
						DimMtlChanneltype dimMtlChanneltype = new DimMtlChanneltype();
						dimMtlChanneltype.setTypeId(String.valueOf(list.get(i).getChanneltypeId()));
						dimMtlChanneltype.setTypeName(list.get(i).getChanneltypeName());
						listTemp.add(dimMtlChanneltype);
					}
				}
			}
			if(!CollectionUtils.isEmpty(listTemp)){
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(listTemp));
				out.print(dataJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		}finally{
			out.flush();
			out.close();
		}
	}

	/**
	 * 新建策略页面:  产品关系选择  按照类型和关键字查询   只查询政策力度为营销档次的产品信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchPlan")
	public void searchPlan(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//TODO: initActionAttributes(request);
		Pager pager=new Pager();
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		String pageNum = StringUtil.isNotEmpty(request.getParameter("pageNum")) ? request.getParameter("pageNum") : "1";
		String keyWords = StringUtil.isNotEmpty(request.getParameter("keyWords")) ? request.getParameter("keyWords") : null;
//		政策类别
		String typeId = StringUtil.isNotEmpty(request.getParameter("typeid")) ? request.getParameter("typeid") : null;
		//TODO: String cityId = user.getCityid();
		String cityId ="999";
		try {
			String clickQueryFlag = "true";
			pager.setPageSize(MpmCONST.SMALL_PAGE_SIZE_LABEL);
			pager.setPageNum(pageNum);  //当前页
			if(pageNum != null){
				pager.setPageFlag("G");	
			}
			pager.setTotalSize(mtlStcPlanManagementService.searchPlanCount(keyWords, typeId,cityId));  // 总记录数
			pager.getTotalPage();
			if ("true".equals(clickQueryFlag)) {
				List<MtlStcPlanBean> resultList = mtlStcPlanManagementService.searchPlan(keyWords, typeId,cityId,pager);
				pager = pager.pagerFlip();
				pager.setResult(resultList);
			} else {
				pager = pager.pagerFlip();
				List<MtlStcPlanBean> resultList = mtlStcPlanManagementService.searchPlan(keyWords, typeId,cityId,pager);
				pager.setResult(resultList);
			}
			
			dataJson.put("status", "200");
			dataJson.put("data", JmsJsonUtil.obj2Json(pager));
			out.print(dataJson);
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		}finally{
			out.flush();
			out.close();
		}
	  
	}
	
	/**
	 * 新建策略页面，根据条件查询营销政策列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchByCondation")
	public void searchByCondation(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//TODO: initActionAttributes(request);
		Pager pager=new Pager();
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		String pageNum = StringUtil.isNotEmpty(request.getParameter("pageNum")) ? request.getParameter("pageNum") : "1";
		String keyWords = StringUtil.isNotEmpty(request.getParameter("keyWords")) ? request.getParameter("keyWords") : null;
		//政策类别
		String typeId = StringUtil.isNotEmpty(request.getParameter("typeId")) ? request.getParameter("typeId") : null;
		//使用渠道
		String channelTypeId = request.getParameter("channelTypeId") != null ? request.getParameter("channelTypeId") : null;
		//查询粒度
		String planTypeId = request.getParameter("planTypeId") != null ? request.getParameter("planTypeId") : null;
		//新建策略是否单选
		String isDoubleSelect = StringUtil.isNotEmpty(request.getParameter("isDoubleSelect")) ? request.getParameter("isDoubleSelect") : "0";
		
		//TODO: String cityId = user.getCityid();
		String cityId = "999";
		if(StringUtil.isNotEmpty(pageNum)){
			pager.setPageFlag("G");	
		}
		try {
			String clickQueryFlag = "true";
			pager.setPageSize(MpmCONST.SMALL_PAGE_SIZE_LABEL);  //此处改为每页显示5条
			pager.setPageNum(pageNum);  //当前页
			if(pageNum != null){
				pager.setPageFlag("G");	
			}
			pager.setTotalSize(mtlStcPlanManagementService.getMtlStcPlanByCondationCount(keyWords, typeId, channelTypeId,planTypeId,cityId,isDoubleSelect));  // 总记录数
			pager.getTotalPage();
			if ("true".equals(clickQueryFlag)) {
				List<MtlStcPlanBean> resultList = mtlStcPlanManagementService.getMtlStcPlanByCondation(keyWords, typeId, channelTypeId,planTypeId,cityId,isDoubleSelect,pager);
				pager = pager.pagerFlip();
				pager.setResult(resultList);
			} else {
				pager = pager.pagerFlip();
				List<MtlStcPlanBean> resultList = mtlStcPlanManagementService.getMtlStcPlanByCondation(keyWords, typeId, channelTypeId,planTypeId,cityId,isDoubleSelect,pager);
				pager.setResult(resultList);
			}
			
			dataJson.put("status", "200");
			dataJson.put("data", JmsJsonUtil.obj2Json(pager));
			
			out.print(dataJson);
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		}finally{
			out.flush();
			out.close();
		}
	}

}
