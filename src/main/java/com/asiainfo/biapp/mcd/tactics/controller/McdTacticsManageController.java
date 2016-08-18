package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biframe.utils.string.StringUtil;

import net.sf.json.JSONObject;

public class McdTacticsManageController extends BaseMultiActionController{
	
	/**
	 * 保存策略基本信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveCampsegWaveInfo(HttpServletRequest request,HttpServletResponse response)  throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		
		IMpmCampSegInfoService service = (IMpmCampSegInfoService) SystemServiceLocator.getInstance().getService(MpmCONST.CAMPAIGN_SEG_INFO_SERVICE);
		List<MtlCampSeginfo> campSegInfoList = new ArrayList<MtlCampSeginfo>();
		int whc=0;
		int whnum=0;
		String month ="";
		try {
			initActionAttributes(request);
			McdTempletForm bussinessLableTemplate = new McdTempletForm();
			McdTempletForm basicEventTemplate = new McdTempletForm();
//			策略包    先循环迭代出每个规则
			String test = request.getParameter("ruleList");
			JSONObject ruleList = new org.json.JSONObject(test);
			//获取公共属性
			JSONObject commonAttr = new JSONObject(ruleList.get("commonAttr").toString());
			String campsegName = commonAttr.get("campsegName").toString();
			String putDateStart = commonAttr.get("putDateStart").toString();
			String putDateEnd = commonAttr.get("putDateEnd").toString();
			String campsegTypeId = commonAttr.get("campsegTypeId").toString();
			if(StringUtil.isEmpty(campsegTypeId)){
				campsegTypeId = "1";
			}
			
			String isFilterDisturb = commonAttr.get("isFilterDisturb").toString();  //是否需要免打扰控制
			String planId = commonAttr.get("planid").toString();
//			审批标识
			String isApprove = commonAttr.get("isApprove").toString(); 
			//先保存基本信息  父亲节点
			MtlCampSeginfo campSeginfoBasic = new MtlCampSeginfo();
			campSeginfoBasic.setCampsegName(campsegName);
			campSeginfoBasic.setStartDate(putDateStart);
			campSeginfoBasic.setEndDate(putDateEnd);
			campSeginfoBasic.setCampsegPid("0");//父节点
			campSeginfoBasic.setCreateUserid(user.getUserid());
			campSeginfoBasic.setCreateUserName(user.getUsername());
			campSeginfoBasic.setCampsegTypeId(Short.parseShort(campsegTypeId));
			campSeginfoBasic.setPlanId(planId);
//			String campsegPid = (String)service.saveCampSegWaveInfoZJ(campSeginfoBasic,true);
			campSeginfoBasic.setFatherNode(true);
			campSeginfoBasic.setAreaId(user.getCityid());
			campSeginfoBasic.setCityId(user.getCityid());			   //策划人所属城市
			campSeginfoBasic.setIsFileterDisturb(Integer.parseInt(isFilterDisturb));
			String deptId = "";
			String deptName = "";
			if(user != null){
				LkgStaff staff = (LkgStaff)user;
				String deptMsg = staff.getDepId();
				if(deptMsg.indexOf("&&") > 0){
					deptId = deptMsg.split("&&")[0];
					deptName = deptMsg.split("&&")[1];
				}
				campSeginfoBasic.setDeptId(Integer.parseInt(deptId));				   //策划人部门id
			}
			campSegInfoList.add(campSeginfoBasic);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			String planIdArray[] = planId.split(",");  //当时多产品的时候
			
//			for(int i = 0;i<(ruleList.length()-1);i++){
			for(int i = 0;i<planIdArray.length;i++){
				String ruleIndex = "rule0";
				org.json.JSONObject rule = new org.json.JSONObject(ruleList.get(ruleIndex).toString());  //迭代每一个规则进行计算
				
//				业务标签
				String labelArr = rule.get("labelArr").toString();
				if(StringUtil.isEmpty(labelArr) || labelArr.equals("[]")){
					bussinessLableTemplate = null;
				}else{
					bussinessLableTemplate = this.createTemplateForm(labelArr,"0",true);
				}
//				获取ARPU   基础标签
				String basicProp = rule.get("basicProp").toString();
				if(StringUtil.isEmpty(basicProp) || basicProp.equals("[]")){
					basicEventTemplate = null;
				}else{
					basicEventTemplate = createTemplateForm(basicProp,"1",true);
				}
//				产品订购
				String productArr = rule.get("productArr").toString();
				String productAttr[] = this.createProductAttr(productArr);
				String orderProductNo = productAttr[0];    //订购产品
				String excludeProductNo = productAttr[1];  //剔除产品
				//获取渠道ID
				String  channelIds = rule.get("channelIds").toString();
				//获取策略类型ID
//				String campsegTypeId = rule.get("campsegTypeId").toString();
				//运营位id
//				String adivId = rule.get("adivId").toString();
				
				//获取客户群ID
				String customgroupid = null;
				String updateCycle = null;
				if(StringUtil.isNotEmpty(rule.get("customer").toString())){
					customgroupid =  new org.json.JSONObject(rule.get("customer").toString()).get("id").toString();
					updateCycle = new org.json.JSONObject(rule.get("customer").toString()).get("updatecycle").toString();
				}
				//获取每个rule中的基础属性
				String baseAttr = rule.get("baseAttr").toString();
//				org.json.JSONObject basicPropObj = new org.json.JSONObject(baseAttr);
				org.json.JSONArray basicPropArray = new org.json.JSONArray(baseAttr);
				String planid = "";
				String channelTypeId = "";
				for(int m = 0;m < basicPropArray.length();m++){
					org.json.JSONObject obj = new org.json.JSONObject(basicPropArray.get(m).toString());
					planid = obj.get("planid").toString();  //产品ID
					if(planid.equals(planIdArray[i])){
						channelTypeId = obj.get("campsegtypeid").toString();  //渠道类型ID
					}
				}
				//筛选后的客户群数量
				String afterComputCustNum = rule.get("afterComputCustNum").toString();
				//获取渠道执行的基本信息
				String execContentStr = rule.get("execContent").toString();
				org.json.JSONArray execContent = new org.json.JSONArray(execContentStr);
				List<MtlChannelDef> mtlChannelDefList = new ArrayList<MtlChannelDef>();
				MtlChannelDefCall mtlChannelDefCall = null;

				String cepEventId = "";
				String eventRuleDesc = "";
				String streamsId = "";
				String streamName = "";
				for(int j = 0;j<execContent.length();j++){
					org.json.JSONObject obj = new org.json.JSONObject(execContent.get(j).toString());
					Map<String, Object> objMap = jsonToMap(execContent.get(j).toString());
					//判断是否包含实时事件
					if(execContentStr.indexOf("cepInfo") != -1){
						String cepInfo = obj.get("cepInfo").toString();
						org.json.JSONObject cepInfoObject = new org.json.JSONObject(cepInfo);
						cepEventId = cepInfoObject.getString("streamsId");
						org.json.JSONArray functionList = new org.json.JSONArray(cepInfoObject.getString("functionList"));
						for(int m=0;m<functionList.length();m++){
							org.json.JSONObject function = new org.json.JSONObject(functionList.get(m).toString());
							String functionCn = String.valueOf(function.get("functionCn")) ;
							eventRuleDesc += functionCn + "&";
						}
					}
					
					MtlChannelDef mtlChannelDef = new MtlChannelDef();
					String channelId = String.valueOf(obj.get("chanelId")) ;
					String content = "";
					boolean ifHasVariate = false;
					String channelCycle = "";
					String adivId = "";
					
					if("901".equals(channelId)){   //短信渠道
						channelCycle = String.valueOf(obj.get("channelCycle"));
						String channelTrigger = String.valueOf(obj.get("channelTrigger"));
						content =  String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content,"UTF-8"),"UTF-8");
						//策略级频次控制(一次性短信paramDays paramNum前台不传参数 故paramDaysObj==null paramNumObj==null ) modify by zhuml 20151203
						Object paramDaysObj = objMap.get("paramDays");
						String paramDays  =  "0";
						if(null != paramDaysObj){
							paramDays = String.valueOf(paramDaysObj);
						}
						Object paramNumObj = objMap.get("paramNum");
						String paramNum  =  "0";
						if(null != paramNumObj){
							paramNum = String.valueOf(paramNumObj);
						}
						//  modify by zhuml  end 20151203
						ifHasVariate = Boolean.parseBoolean(String.valueOf(obj.get("ifHasVariate")));
						mtlChannelDef.setUpdateCycle(Integer.parseInt(updateCycle));
						if(StringUtil.isNotEmpty(channelCycle)){
							mtlChannelDef.setContactType(Integer.parseInt(channelCycle));
						}
						mtlChannelDef.setCepTriggerFlag(Integer.parseInt(channelTrigger));
						if(StringUtil.isEmpty(paramDays)){
							paramDays = "0";
						}
						if(StringUtil.isEmpty(paramNum)){
							paramNum = "0";
						}
						mtlChannelDef.setParamDays(Integer.parseInt(paramDays));
						mtlChannelDef.setParamNum(Integer.parseInt(paramNum));
						//实时事件 参数 保存
						String execStr = execContent.get(j).toString();
						if(execStr.indexOf("eventInstanceDesc") != -1){  //当选择实时的时候才会保存
							String eventInstanceDesc = String.valueOf(obj.get("eventInstanceDesc"));
							streamsId = String.valueOf(obj.get("streamsId"));
							String eventParamJson = String.valueOf(obj.get("eventParamJson"));
							streamName = String.valueOf(obj.get("eventName")) ;
							
							Pattern p = Pattern.compile("\\s*|\t|\r|\n");
							
							org.json.JSONObject ruleTimeParamObj = new org.json.JSONObject(eventParamJson);
							String functionId = String.valueOf(ruleTimeParamObj.get("funcId")) ;  //场景id
							mtlChannelDef.setFunctionId(functionId);
							mtlChannelDef.setEventActiveTempletId(streamsId);
							mtlChannelDef.setEventInstanceDesc(p.matcher(eventInstanceDesc).replaceAll(""));
							if("20160309093621645766".equals(functionId)||"20160309093745413830".equals(functionId)){  //汪斌修改回显场景，特殊处理
								mtlChannelDef.setEventInstanceDesc(eventInstanceDesc);
							}
							mtlChannelDef.setEventParamJson(eventParamJson);
						}
						
					}else if("902".equals(channelId) || "906".equals(channelId) || "905".equals(channelId)){  //社会渠道和营业厅和手机crm
						content =  String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content,"UTF-8"),"UTF-8");
						ifHasVariate = Boolean.parseBoolean(String.valueOf(obj.get("ifHasVariate")));
					}else if("903".equals(channelId)){  //手机APP
						adivId =  String.valueOf(obj.get("adivId"));
						mtlChannelDef.setChannelAdivId(adivId);
					}else if("904".equals(channelId)){  //10086热线
						String awardMount = String.valueOf(obj.get("awardMount"));
						String editUrl =  String.valueOf(obj.get("editUrl"));
						String handleUrl =  String.valueOf(obj.get("handleUrl"));
						String sendSms =  String.valueOf(obj.get("sendSms"));  //短信用语
						sendSms = URLDecoder.decode(URLDecoder.decode(sendSms,"UTF-8"),"UTF-8");
						content =  String.valueOf(obj.get("exec_content"));   //推荐用语
						content = URLDecoder.decode(URLDecoder.decode(content,"UTF-8"),"UTF-8");
						if(StringUtil.isNotEmpty(awardMount)){
							mtlChannelDef.setAwardMount(Double.parseDouble(awardMount));
						}
						mtlChannelDef.setEditUrl(editUrl);
						mtlChannelDef.setHandleUrl(handleUrl);
						mtlChannelDef.setSendSms(sendSms);
					}else if("907".equals(channelId) ){   //toolBar + 
						adivId = String.valueOf(obj.get("adivId"));
						content =  String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content,"UTF-8"),"UTF-8");
						mtlChannelDef.setChannelAdivId(adivId);
					}else if("910".equals(channelId)){ 		//boss运营位
						adivId = String.valueOf(obj.get("adivId"));
						content =  String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content,"UTF-8"),"UTF-8");
						String messageType = String.valueOf(obj.get("messageType"));
						String temp = "";
						if("1".equals(messageType)){
							temp += "【业务告知】"+content+"(中国移动)";
						}else{
							temp += "【服务提醒】"+content+"(中国移动)";
						}
						content = temp;
						mtlChannelDef.setChannelAdivId(adivId);
						
					}else if("911".equals(channelId) || "912".equals(channelId)){   //微信（全省） + 微信（温州）
						adivId = String.valueOf(obj.get("adivId"));
						content =  String.valueOf(obj.get("exec_content"));
						content = URLDecoder.decode(URLDecoder.decode(content,"UTF-8"),"UTF-8");
						String execTitle = String.valueOf(obj.get("execTitle"));  //微信标题
						String fileName = String.valueOf(obj.get("fileName"));  //微信本地文件名称
//						String fileRemoteName = String.valueOf(obj.get("fileRemoteName"));  //Ftp存放的文件名称
						mtlChannelDef.setChannelAdivId(adivId);
						mtlChannelDef.setExecTitle(execTitle);
						mtlChannelDef.setFileName(fileName);
//						mtlChannelDef.setFileRemoteName(fileRemoteName);
					}else if("908_bak".equals(channelId)){//留给外呼渠道以后用，暂时没有用上
						if(whc==0 )
						{ 
							whnum=getWhTaskSequence(user.getCityid()) +1 ;  
							SimpleDateFormat df = new SimpleDateFormat("yyyyMM"); 
							month = df.format(new Date());   
						} 
						String code=""; 
						int num = whnum +whc; 	
						if(num < 10 && num > 0)
						{    code = "00"+ num;}
						else if(num < 100 && num > 9)
						{   code = "0"+ num;}
						else{ 
						  	code =""+ num;
						 } 
						String taskName = String.valueOf(obj.get("taskName"));//任务名称
						String demand = String.valueOf(obj.get("demand"));//需求方
						String taskClassId = String.valueOf(obj.get("taskClassId"));//任务分类
						String tasklevel1Id = String.valueOf(obj.get("tasklevel1Id"));//任务类型一级
						String taskLevel2Id = String.valueOf(obj.get("taskLevel2Id"));//任务类型二级
						String taskLevel3Id = String.valueOf(obj.get("taskLevel3Id"));//任务类型三级
						String busiLevel1Id = String.valueOf(obj.get("busiLevel1Id"));//业务类型一级
						String busiLevel2Id = String.valueOf(obj.get("busiLevel2Id"));//业务类型二级	
						
						Integer inPlanFlag =StringUtil.isEmpty( obj.get("inPlanFlag") ) ? null : Integer.parseInt(obj.get("inPlanFlag").toString());//是否计划内
						Integer monthPlanFlag = StringUtil.isEmpty( obj.get("monthPlanFlag")) ? null : Integer.parseInt(obj.get("monthPlanFlag").toString());//关联月度计划
						Integer callCycle = StringUtil.isEmpty( obj.get("callCycle") ) ? null : Integer.parseInt(obj.get("callCycle").toString());//外呼周期
						Integer callPlanNum =  StringUtil.isEmpty( obj.get("callPlanNum") ) ?null : Integer.parseInt(obj.get("callPlanNum").toString());//计划外呼量
						
						String finishDate = String.valueOf(obj.get("finishDate"));//要求完成时间
						String taskComment = String.valueOf(obj.get("taskComment"));//任务描述
						String 	userLableInfo = StringUtil.isEmpty( obj.get("userLableInfo") ) ?null :String.valueOf(obj.get("userLableInfo"));//客户标签信息
						String 	callQuestionUrl = StringUtil.isEmpty( obj.get("callQuestionUrl") ) ?null :String.valueOf(obj.get("callQuestionUrl"));//外呼问卷地址	
						String 	callQuestionName = StringUtil.isEmpty( obj.get("callQuestionName") ) ?null :String.valueOf(obj.get("callQuestionName"));//外呼问卷名称
						String callNo = String.valueOf(obj.get("callNo"));//主叫号码
						Integer avoidFilterFlag =  obj.get("avoidFilterFlag") == null ? null : Integer.parseInt(obj.get("avoidFilterFlag").toString());//是否要清洗黑红白名单
						Integer callTestFlag =  obj.get("callTestFlag") == null ? null : Integer.parseInt(obj.get("callTestFlag").toString());//是否拨测
						Integer freFilterFlag =  obj.get("freFilterFlag") == null ? null : Integer.parseInt(obj.get("freFilterFlag").toString());//是否需要进行频次清洗
						String 	callForm = String.valueOf(obj.get("callForm"));//外外呼形式问卷地址	
						String callCityType = String.valueOf(obj.get("callCityType"));//外呼属地
						String taskCode=  user.getCityid() + month+busiLevel1Id +channelId+code ; 
						
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
						mtlChannelDefCall.setCallNo(callNo);
						mtlChannelDefCall.setFinishDate(finishDate);
						mtlChannelDefCall.setTaskComment(taskComment);
						mtlChannelDefCall.setUserLableInfo(userLableInfo);
						mtlChannelDefCall.setCallQuestionUrl(callQuestionUrl);
						mtlChannelDefCall.setCallQuestionName(callQuestionName);
						mtlChannelDefCall.setAvoidFilterFlag(avoidFilterFlag);
						mtlChannelDefCall.setFreFilterFlag(freFilterFlag);
						mtlChannelDefCall.setCallTestFlag(callTestFlag);
						mtlChannelDefCall.setCallForm(callForm);
						mtlChannelDefCall.setCallCityType(callCityType);
						whc=whc+1;
					}
					
					
//					boolean ifHasVariate = Boolean.parseBoolean(String.valueOf(obj.get("ifHasVariate")));
					mtlChannelDef.setIfHaveVar(!ifHasVariate ? Short.parseShort("0"):Short.parseShort("1"));
					
					if(StringUtil.isEmpty(adivId)){  //当adivId为空的时候，默认为1
						mtlChannelDef.setChannelAdivId("1");
					}
					//保存将筛选后的客户群数量
					String afterComputeCustNum[] = afterComputCustNum.split(",");
					String targetCustNum = "0";
					if(null != afterComputeCustNum && afterComputeCustNum.length > 0){
						for(int k = 0;k<afterComputeCustNum.length;k++){
							String temp[] = afterComputeCustNum[k].split("_");
							if(channelId.equals(temp[0])){
								targetCustNum = temp[1];
							}
						}
					}
					if(StringUtil.isEmpty(channelCycle) && StringUtil.isNotEmpty(updateCycle)){  //当channelCycle为空的时候  保持与updateCycle一致
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
				campSeginfo.setCampsegTypeId(Short.parseShort(campsegTypeId));//策略类型
				campSeginfo.setCreateUserid(user.getUserid());  //活动策划人
				campSeginfo.setCityId(user.getCityid());			   //策划人所属城市
				campSeginfo.setDeptId(Integer.parseInt(deptId));				   //策划人部门id
				campSeginfo.setCreateTime(format.parse(format.format(new Date())));
				campSeginfo.setPlanId(planIdArray[i]);                //产品编号
				campSeginfo.setCreateUserName(user.getUsername());
				campSeginfo.setIsFileterDisturb(Integer.parseInt(isFilterDisturb));
				//渠道基本信息
				campSeginfo.setChannelId(channelIds);           //渠道id
				campSeginfo.setChannelTypeId(channelTypeId);		   //渠道类型ID
				
//				campSeginfo.setCampsegPid(campsegPid);
				campSeginfo.setFatherNode(false);
				campSeginfo.setIsApprove(isApprove);
				campSeginfo.setCampsegNo(String.valueOf(i));   //多规则时，规则序号
				
				//添加实时事件支持
				if(execContentStr.indexOf("cepInfo") != -1){
//					campSeginfo.setCepEventId(cepEventId);
//					campSeginfo.setEventRuleDesc(eventRuleDesc);
				}
				campSeginfo.setCepEventId(streamsId); 
				campSeginfo.setEventRuleDesc(streamName);
				//客户群基本信息
				campSeginfo.setCustgroupId(customgroupid);	
				campSeginfo.setUpdatecycle(updateCycle);
				
				campSeginfo.setMtlChannelDefList(mtlChannelDefList);
				campSeginfo.setMtlChannelDefCall(mtlChannelDefCall);
				//产品订购或者剔除关系
				campSeginfo.setOrderPlanIds(orderProductNo);
				campSeginfo.setExcludePlanIds(excludeProductNo);
				
//				保存业务标签
				if(bussinessLableTemplate != null){
					String bussinessLableTemplateId = this.handleTemplet(request.getLocale(), bussinessLableTemplate);
					if(StringUtil.isNotEmpty(bussinessLableTemplateId)){
						campSeginfo.setBussinessLableTemplateId(bussinessLableTemplateId);
					}
				}
				//保存时机规则
				if(basicEventTemplate != null){
					String basicEventTemplateId = this.handleTemplet(request.getLocale(), basicEventTemplate);
					if(StringUtil.isNotEmpty(basicEventTemplateId)){
						campSeginfo.setBasicEventTemplateId(basicEventTemplateId);
					}
				}
				
				campSeginfo.setBussinessLableTemplate(bussinessLableTemplate);
				campSeginfo.setBasicEventTemplate(basicEventTemplate);
				campSeginfo.setRequestLocal(request.getLocale());
				//保存基本信息     渠道与活动关系表     活动与客户群关系表
//				String campsegId = (String) service.saveCampSegWaveInfoZJ(campSeginfo,false);
				campSegInfoList.add(campSeginfo);
				
			}
			
			//统一进行保存
			String flag = service.saveCampSegWaveInfoZJ(campSegInfoList);
			if("2".equals(flag)){  //当flag等于2的时候，有审批流程，并且审批失败，0和1说明状态都正常
				dataJson.put("status", "201");
			}else{
				dataJson.put("status", "200");
			}
			out.print(dataJson);
		} catch (Exception e) {
			LOG.error("",e);
			dataJson.put("status", "201");
			out.print(dataJson);
		}finally{
			out.flush();
			out.close();
		}
	}

}
