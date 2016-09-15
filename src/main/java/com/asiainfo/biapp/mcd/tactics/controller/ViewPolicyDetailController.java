package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.util.DESBase64Util;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.service.custgroup.CustGroupInfoService;
import com.asiainfo.biapp.mcd.common.service.plan.IMtlStcPlanService;
import com.asiainfo.biapp.mcd.common.util.DateConvert;
import com.asiainfo.biapp.mcd.common.vo.plan.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.vo.plan.McdPlanDef;
import com.asiainfo.biapp.mcd.custgroup.vo.CustUpdateCycle;
import com.asiainfo.biapp.mcd.tactics.service.ChannelBossSmsTemplateService;
import com.asiainfo.biapp.mcd.tactics.service.IDimCampsegTypeService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlChannelDefService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlSmsSendTestTask;
import com.asiainfo.biapp.mcd.tactics.vo.ChannelBossSmsTemplate;
import com.asiainfo.biapp.mcd.tactics.vo.DataGridData;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampStatus;
import com.asiainfo.biapp.mcd.tactics.vo.McdApproveLog;
import com.asiainfo.biapp.mcd.tactics.vo.McdSysInterfaceDef;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 策略详情Controller
 * @author AsiaInfo-jie
 *
 */
@RequestMapping("/tactics/viewPolicyDetail")
public class ViewPolicyDetailController extends BaseMultiActionController  {
    private static Logger log = LogManager.getLogger(ViewPolicyDetailController.class);

    
    @Resource(name="mpmCampSegInfoService")
    private IMpmCampSegInfoService mpmCampSegInfoService;
    @Resource(name = "mtlCallWsUrlService")
    private IMtlCallWsUrlService callwsUrlService;
    @Resource(name = "mtlSmsSendTestTask")
    private IMtlSmsSendTestTask mtlSmsSendTestTask;
    @Resource(name = "dimCampsegTypeService")
    private IDimCampsegTypeService dimCampsegTypeService;
    @Resource(name = "mtlStcPlanService")
    private IMtlStcPlanService mtlStcPlanService;
    @Resource(name = "custGroupInfoService")
    private CustGroupInfoService custGroupInfoService;
    @Resource(name = "mtlChannelDefService")
    private IMtlChannelDefService mtlChannelDefService;
    @Resource(name = "channelBossSmsTemplateService")
    private ChannelBossSmsTemplateService channelBossSmsTemplateService;
    
    /**
     * viewPolicyDetail 查看策略详情
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/viewPolicyDetail")
    @ResponseBody
    public Map<String, Object> viewPolicyDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
        McdCampDef segInfoBean = new McdCampDef();     
        Map<String,Object> returnMap = new HashMap<String,Object>();

        String campsegId = request.getParameter("campsegId");
        McdCampDef segInfo;
        if(campsegId!=null&&!"".equals(campsegId)){
            segInfo = mpmCampSegInfoService.getCampSegInfo(campsegId);
        }else{
            segInfo = mpmCampSegInfoService.getCampSegInfo(segInfoBean.getCampId());
        }
        
        try{
            
            BeanUtils.copyProperties(segInfoBean, segInfo);
            segInfoBean.setStartDate(segInfo.getStartDate());
            segInfoBean.setEndDate(segInfo.getEndDate());
            segInfoBean.setStartDate(segInfo.getStartDate());
            segInfoBean.setEndDate(segInfo.getEndDate());
            
            //根据ID获取用户类
            User user = this.getUserPrivilege().queryUserById(segInfo.getCreateUserId());
            String userName = "";
            if(user!=null){
                userName = user.getName();
            }
            //业务类型
            String campDrvName ="";
            if(StringUtils.isNotEmpty(segInfo.getPlanId()) && segInfo.getPlanId().indexOf(",") != -1){
                String planArr[] = segInfo.getPlanId().split(",");
                for(int i = 0;i<planArr.length;i++){
                    McdPlanDef stcPlan = mpmCampSegInfoService.getMtlStcPlanByPlanId(planArr[i]);
                    if(stcPlan != null){
                        if(StringUtils.isNotEmpty(stcPlan.getPlanType())){
                            McdDimPlanType dimPlanType = mtlStcPlanService.getPlanTypeById(stcPlan.getPlanType());
                            if(null != dimPlanType){
                                campDrvName += dimPlanType.getTypeName()+",";
                            }
                        }
                    }
                }
                campDrvName = campDrvName.substring(0, campDrvName.length()-1);
            }else{
                McdPlanDef stcPlan = mpmCampSegInfoService.getMtlStcPlanByPlanId(segInfo.getPlanId());
                if(stcPlan != null){
                    if(StringUtils.isNotEmpty(stcPlan.getPlanType())){
                        McdDimPlanType dimPlanType = mtlStcPlanService.getPlanTypeById(stcPlan.getPlanType());
                        if(null != dimPlanType){
                            campDrvName = dimPlanType.getTypeName();
                        }
                    }
                }
            }
            
            //创建时间 日期格式 yyyy-MM-dd
            String createTime = DateConvert.formatLongDateTime(segInfo.getCreateTime());
            //营销活动状态名
            McdDimCampStatus dimCampsegStat = mpmCampSegInfoService.getDimCampsegStat(segInfo.getStatId().toString()); 
            String statusName = dimCampsegStat.getCampsegStatName(); //MpmCache.getInstance().getNameByTypeAndKey(MpmCONST.MPM_CAMPSEG_STAT_DEFINE,segInfo.getCampsegStatId().toString());
            //营销类型
            Short campsegTypeId = segInfo.getTypeId() == null ? 0 : segInfo.getTypeId();
            String campsegTypeName = dimCampsegTypeService.getDimCampsegTypeName(campsegTypeId);
            
            Map<String,Object> map= new HashMap<String,Object>();
            map.put("campsegId", segInfoBean.getCampId());
            map.put("campsegName", segInfoBean.getCampName());
            map.put("statusName", statusName);
            map.put("startDate", segInfoBean.getStartDate());
            map.put("endDate", segInfoBean.getEndDate());
            map.put("createUserid", segInfoBean.getCreateUserId());
            map.put("createUserName", userName);
            map.put("campDrvName", campDrvName);
            map.put("campsegTypeId", segInfoBean.getTypeId());
            map.put("campsegTypeName", campsegTypeName);
            map.put("createTime", createTime);
            List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
    
            //mtlCampSeginfoList.addAll(mtlCampSeginfoList1);
            map.put("childMtlCampSeginfo", mtlCampSeginfoList);
            
            returnMap.put("status", "200");
            returnMap.put("data", map);
            //String result = "{status : 200,data:["+jsonMap.toString()+"]}";
            System.out.println("campsegresult===="+returnMap.toString());

         
        }catch(Exception e){
            e.printStackTrace();
            returnMap.put("status", "201");
            returnMap.put("result", "fail");
            //String result = "{status : 201, result:fail}";
            System.out.println("campsegresult===="+returnMap.toString());
        }finally{
            
        }
         return returnMap;
    }
    
    /**
     * getMtlStcPlan 获取匹配的政策
     * add by jinl 20150716 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getMtlStcPlan")
    @ResponseBody
    public Map<String, Object> getMtlStcPlan(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	Map<String,Object> returnMap = new HashMap<String,Object>();

         // “匹配的政策”
        try{
            String campsegId = request.getParameter("campsegId");
            McdCampDef segInfo = mpmCampSegInfoService.getCampSegInfo(campsegId);
             String planid = segInfo.getPlanId();//获取产品编号qzd548
             JSONObject stcPlanJSON = new JSONObject();
             if(planid!=null&&!"".equals(planid)){
                McdPlanDef stcPlan = mpmCampSegInfoService.getMtlStcPlanByPlanId(planid);//查询产品信息
                McdDimPlanType dimPlanType = mtlStcPlanService.getPlanTypeById(stcPlan.getPlanType());
                stcPlanJSON.put("campDrvName", dimPlanType != null ? dimPlanType.getTypeName(): "");    //业务类型
                stcPlanJSON.put("stcPlanName", stcPlan != null ? stcPlan.getPlanName() : "");   //“政策名称”,政策即产品
                stcPlanJSON.put("stcPlanId", stcPlan != null ? stcPlan.getPlanId() : "");       //“编号”
                stcPlanJSON.put("stcPlanStartdate", stcPlan != null ? DateFormatUtils.format(stcPlan.getPlanStartdate(), "yyyy/MM/dd") : ""); //“有效期”开始时间
                stcPlanJSON.put("stcPlanEnddate", stcPlan != null ? DateFormatUtils.format(stcPlan.getPlanEnddate(), "yyyy/MM/dd") : "");     //“有效期”结束时间
                stcPlanJSON.put("stcPlanDesc", stcPlan != null ? stcPlan.getPlanDesc() : "");   //“描述”
               
             }
             request.setAttribute("stcPlanJSONStr", stcPlanJSON.toString());
             request.setAttribute("stcPlanJSON", stcPlanJSON);
             returnMap.put("status", "200");
             returnMap.put("data", stcPlanJSON);
             //String result = "{status: 200,data:["+stcPlanJSON.toString()+"]}";
             System.out.println("stcPlanresult===="+returnMap.toString());
        }catch(Exception e){
            e.printStackTrace();
            returnMap.put("status", "201");
            returnMap.put("result", "fail");
            //String result = "{status : 201, result:fail}";
            System.out.println("stcPlanresult===="+returnMap.toString());
        }

        return returnMap;
    }

    
    /**
     * getTargetCustomerbase 获取目标客户群
     * add by jinl 20150720 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getTargetCustomerbase")
    @ResponseBody
    public Map<String, Object> getTargetCustomerbase(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Map<String,Object> returnMap = new HashMap<String,Object>();

         //投放的渠道
        try{
         String custom_group_id ="";
         String custom_group_name ="";
         BigDecimal  update_cycle = null;
         String create_time ="";
         String create_user_id="";
         BigDecimal custom_num=null;
         String custom_source_id="";
         String rule_desc="";
         String campsegId = request.getParameter("campsegId");
         List<Map<String,Object>> list = mpmCampSegInfoService.getTargetCustomerbase(campsegId);
         BigDecimal custgroup_number=null;
         for(int i=0;i<list.size();i++){
             Map<String,Object> tmap = list.get(i);
             custom_group_id = (String) tmap.get("custom_group_id");
             custom_group_name = strNullToStr((String) tmap.get("custom_group_name"));
             update_cycle =  tmap.get("update_cycle") == null ? null : (BigDecimal) tmap.get("update_cycle");
             create_time = tmap.get("create_time") == null ? null : tmap.get("create_time").toString();
             create_user_id = tmap.get("create_user_id") == null ? null : strNullToStr((String) tmap.get("create_user_id"));
             custom_num =  tmap.get("custom_num") == null ? null : (BigDecimal) tmap.get("custom_num");
             custom_source_id = tmap.get("custom_source_id") == null ? null : strNullToStr((String) tmap.get("custom_source_id"));
             rule_desc = tmap.get("rule_desc") == null ? null : strNullToStr((String) tmap.get("rule_desc"));
             custgroup_number =  tmap.get("targer_user_nums") == null ? null : (BigDecimal)tmap.get("targer_user_nums");
         }
         JSONObject customDataJSON=new JSONObject();
         customDataJSON.put("custom_group_id", custom_group_id == null ? "" : custom_group_id);
         customDataJSON.put("custom_group_name", custom_group_name == null ? "" : custom_group_name);
         if(update_cycle==null){
             customDataJSON.put("update_cycle", ""); 
         }else{
//               customDataJSON.put("update_cycle", update_cycle == null ? "" : update_cycle.toString());    
                 customDataJSON.put("update_cycle", update_cycle == null ? "" : CustUpdateCycle.getName((short) Integer.parseInt(String.valueOf(update_cycle))));    
         }
         customDataJSON.put("create_time", create_time == null ? "" : create_time);
         customDataJSON.put("create_user_id", create_user_id == null ? "" : create_user_id);
         customDataJSON.put("custom_num", custom_num == null ? "" : custom_num);
         customDataJSON.put("custom_source_id", custom_source_id == null ? "" : custom_source_id);
         customDataJSON.put("rule_desc", rule_desc == null ? "" : rule_desc); 
         System.out.println("customDataJSON=========="+customDataJSON);
         //获取细分规则信息（时机）
         List<Map<String,Object>> ruleList= mpmCampSegInfoService.getrule(campsegId);
         String resultStr ="";
         for(int i=0;i<ruleList.size();i++){
             Map<String,Object> tmap = ruleList.get(i);
             String elementValue = (String) tmap.get("ELEMENT_VALUE");
             String columnCnName = (String) tmap.get("COLUMN_CN_NAME");
             if(elementValue.indexOf(",") != -1){
                 resultStr += columnCnName + "in ("+elementValue+") &";
             }else{
                 if("1".equals(elementValue)){
                     resultStr += columnCnName +" &";
                 }else if(elementValue.indexOf("-")!=-1){
//                   resultStr += " "+(elementValue.split("-")[0]+"<"+columnCnName+"<"+elementValue.split("-")[1])+"&";
                     resultStr += " "+columnCnName+":["+elementValue.split("-")[0]+","+elementValue.split("-")[1]+"]&";
                 }else{
                     resultStr += " "+columnCnName +"like "+elementValue+" &";
                 }
             }
         }
         if(StringUtils.isNotEmpty(resultStr)){
             customDataJSON.put("show_sql", resultStr.substring(0, resultStr.length()-1));
         }else{
             customDataJSON.put("show_sql", "");
         }
         if(custgroup_number==null){
             customDataJSON.put("custgroup_number", ""); 
             }else{
                 customDataJSON.put("custgroup_number", custgroup_number == null ? "" : custgroup_number.toString());    
         }
        
         System.out.println("customDataJSON=========="+customDataJSON);
         //最新数据日期，初始客户群规模
         List<Map<String,Object>> getDataDateCustomNumlist  = custGroupInfoService.getDataDateCustomNum(campsegId);//20130916114353920  
        String max_data_time=null;
        BigDecimal sum_custom_num=null;
         if(CollectionUtils.isNotEmpty(getDataDateCustomNumlist)){
             Map<String,Object> tmap = getDataDateCustomNumlist.get(0);
             max_data_time = tmap.get("max_data_time") == null ? null : (String) tmap.get("max_data_time");
             sum_custom_num = tmap.get("sum_custom_num") == null ? null : (BigDecimal) tmap.get("sum_custom_num");
         }
         customDataJSON.put("max_data_time", max_data_time == null ? "" : max_data_time);
         customDataJSON.put("sum_custom_num", sum_custom_num == null ? "" : sum_custom_num); 
         System.out.println("customDataJSON=========="+customDataJSON);
         //取 渠道信息，包含渠道成名，客户群规模。实际客户群规模
         List<Map<String,Object>> mcdList = mpmCampSegInfoService.getMtlChannelDefs(campsegId);
         
         //取原始客户群数量
         int oricustGroupNum = custGroupInfoService.getOriCustGroupNum(custom_group_id);
         
         List<Map<String,Object>> mtltlChannelDefList = new ArrayList<Map<String,Object>>(); 
         for(int i=0;i< mcdList.size();i++){
             Map<String,Object> map = (Map<String, Object>)mcdList.get(i);
             map.put("custgroup_number", custgroup_number == null ? "" : custgroup_number.toString());
             map.put("TARGER_USER_NUMS", oricustGroupNum);
             mtltlChannelDefList.add(map);
         }
         JSONArray jsonArray = JSONArray.fromObject(mtltlChannelDefList);
         customDataJSON.put("mtlChannelDefs", jsonArray);
         
         returnMap.put("status", "200");
         returnMap.put("data", customDataJSON);
         //String result = "{status : 200,data:["+customDataJSON.toString()+"]}";
         System.out.println("customDataJSONresult===="+returnMap.toString());
         
    }catch(Exception e){
        e.printStackTrace();
        returnMap.put("status","201");
        returnMap.put("result", "fail");

        System.out.println("customDataJSONresult===="+returnMap.toString());
        
    }
        return returnMap;
    }

    
    /**
     * add by jinl 20150721
     * @Title: strNullToStr
     * @Description:  对象值为null转""字符串方法
     * @param @param object
     * @param @return    
     * @return String 
     * @throws
     */
    public String strNullToStr(String object){
        if(object==null){
            object="";
         }
        return object;
    }
    
    /**
     * getDeliveryChannel 获取投放渠道
     * add by jinl 20150716 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getDeliveryChannel")
    @ResponseBody
    public Map<String, Object> getDeliveryChannel(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	McdCampDef segInfoBean = new McdCampDef();        
    	Map<String,Object> returnMap = new HashMap<String,Object>();
         //投放的渠道
        try{
         String channel_id ="";
         String channel_name ="";
         String exec_content ="";
         String update_cycle ="";
         String trigger_timing = "";
         String channelAdivId = "";  //新增运营位id
         String adivName = "";       //运营位名称
         String channelAdivName = "";//短信模板名称
//         String eventRuleDesc = "";  //复杂事件描述信息
         String paramDays = "";
         String paramNum = "";
         
         Double awardMount = 0D;
         String editUrl = "";
         String handleUrl = "";
         String sendSms = "";
         String execTitle = "";
         String fileName = "";
         String contactType = "";
         
         String campsegId = request.getParameter("campsegId");
         List<Map<String,Object>> list ;
         List<Map<String,Object>> list2 ;
                if(campsegId!=null&&!"".equals(campsegId)){
                    list = mtlChannelDefService.getDeliveryChannel(campsegId);//20130916114353920  baseForm.getCampsegId()
                    list2 = mtlChannelDefService.getDeliveryChannelCall(campsegId);
                }else{
                    list = mtlChannelDefService.getDeliveryChannel(segInfoBean.getCampId());//20130916114353920  baseForm.getCampsegId()
                    list2 = mtlChannelDefService.getDeliveryChannelCall(segInfoBean.getCampId());
                } 
         List<Map<String,Object>> jsonList = new ArrayList<Map<String,Object>>();
         //boss运营位短信模板
         List<ChannelBossSmsTemplate> bossSmsTemplatelist = channelBossSmsTemplateService.initMtlChannelBossSmsTemplate();;
         for(int i=0;i<list.size();i++){
             Map<String,Object> tmap = list.get(i);
            channel_id = (String) tmap.get("CHANNEL_ID");
            channel_name = (String) tmap.get("CHANNEL_NAME");
            exec_content = (String) tmap.get("EXEC_CONTENT");
            update_cycle = String.valueOf(tmap.get("update_cycle"));
            channelAdivId = (String) tmap.get("channel_adiv_id");
            String templateName = "";
            if(StringUtils.isNotEmpty(channelAdivId)){
                String[] adivId = channelAdivId.split(",");
                for(int n = 0;n<adivId.length;n++){
                    for(int m = 0;m<bossSmsTemplatelist.size();m++){
                        if(adivId[n].equals(bossSmsTemplatelist.get(m).getTemplateId())){
                            templateName += bossSmsTemplatelist.get(m).getTemplateName()+",";
                        }
                    }
                }
                if(StringUtils.isNotEmpty(templateName)){
                    channelAdivName = templateName.substring(0, templateName.length()-1);
                }
            }else{
                channelAdivName = (String) tmap.get("adiv_name");
            }
            adivName = (String) tmap.get("adiv_name");
//            eventRuleDesc = (String)tmap.get("EVENT_RULE_DESC");
            
            paramDays = String.valueOf(tmap.get("PARAM_DAYS"));
            paramNum = String.valueOf(tmap.get("PARAM_NUM"));
            
            execTitle = String.valueOf(tmap.get("EXEC_TITLE"));
            fileName = String.valueOf(tmap.get("FILE_NAME"));
            
            contactType = String.valueOf(tmap.get("contact_type"));
            
            if(null != tmap.get("award_mount")){
                awardMount = Double.parseDouble(String.valueOf(tmap.get("award_mount")));
            }else{
                awardMount = Double.parseDouble(String.valueOf("0"));
            }
            editUrl = String.valueOf(tmap.get("edit_url"));
            handleUrl = String.valueOf(tmap.get("handle_url"));
            sendSms = String.valueOf(tmap.get("send_sms"));
            
            trigger_timing ="";
            if(StringUtils.isNotEmpty(channel_id) && "901".equals(channel_id)){  //当时短信渠道的时候
                if("1".equals(contactType)){
                    update_cycle = "一次性";
                }else if("2".equals(contactType)){
                    //update_cycle = "月周期";
                    update_cycle = "周期性";
                }else{
                    //update_cycle = "日周期";
                    update_cycle = "周期性";
                }
            }else{
    //          客户群生成周期:1,一次性;2:月周 3:日周期
                    if("1".equals(update_cycle)){
                        update_cycle = "一次性";
                    }else if("2".equals(update_cycle)){
                        update_cycle = "周期性";
                    }else if("3".equals(update_cycle)){
                        update_cycle = "周期性";
                    }
            }
            
            Map<String,Object> dChannelDataMap = new HashMap<String,Object>();
            dChannelDataMap.put("channel_id", channel_id == null ? "" : channel_id);
            dChannelDataMap.put("channel_name", channel_name == null ? "" : channel_name);
            dChannelDataMap.put("exec_content", exec_content == null ? "" : exec_content);
            dChannelDataMap.put("update_cycle", update_cycle == null ? "" : update_cycle);
            dChannelDataMap.put("trigger_timing", trigger_timing == null ? "" : trigger_timing);
            dChannelDataMap.put("channelAdivId", channelAdivId == null ? "" : channelAdivId);
            dChannelDataMap.put("adivName", adivName == null ? "" : adivName);
            dChannelDataMap.put("channelAdivName", channelAdivName == null ? "" : channelAdivName);
//            dChannelDataJSON.put("eventRuleDesc", eventRuleDesc == null ? "" : eventRuleDesc);
            dChannelDataMap.put("paramDays", paramDays == null ? "" : paramDays);
            dChannelDataMap.put("paramNum", paramNum == null ? "" : paramNum);
            dChannelDataMap.put("awardMount", awardMount == 0D ? "" : awardMount);
            dChannelDataMap.put("editUrl", editUrl == null ? "" : editUrl);
            dChannelDataMap.put("handleUrl", handleUrl == null ? "" : handleUrl);
            dChannelDataMap.put("sendSms", sendSms == null ? "" : sendSms);
            dChannelDataMap.put("execTitle", execTitle == null ? "" : execTitle);
            dChannelDataMap.put("fileName", fileName == null ? "" : fileName);
            dChannelDataMap.put("isCall", 0);
            
            jsonList.add(dChannelDataMap);
         }
         
         for(int i=0;i<list2.size();i++){
            Map<String,Object> tmap = list2.get(i);
            String channelId = (String) tmap.get("channelId");
            String channelName = (String) tmap.get("channelName");
            String taskCode = (String) tmap.get("taskCode");//任务编码
            String taskName = (String) tmap.get("taskName");//任务名称
            String demand = (String) tmap.get("demand");//需求方
            String taskClassName = (String) tmap.get("taskClassName");//任务分类
            String taskLevel1 = (String) tmap.get("taskLevel1");//任务类型一级
            String taskLevel2 = (String) tmap.get("taskLevel2");//任务类型2级
            String taskLevel3 = (String) tmap.get("taskLevel3");//任务类型3级
            String busiLevel1 = (String) tmap.get("busiLevel1");//业务类型一级
            String busiLevel2 = (String) tmap.get("busiLevel2");//业务类型2级
            String inPlanFlag = (String) tmap.get("inPlanFlag");//是否计划内关联月度计划
            String monthTaskName = (String) tmap.get("monthTaskName");//关联月度计划名称
            String callCycle = (String) tmap.get("callCycle");//外呼周期
            String callPlanNum = (String) tmap.get("callPlanNum").toString();//计划外呼量
            String finishDate = (String) tmap.get("finishDate");//要求完成时间
            String taskComment = (String) tmap.get("taskComment");//任务描述
            String userLableInfo = (String) tmap.get("userLableInfo");//客户标签信息
            String callQuestionUrl = (String) tmap.get("callQuestionUrl");//外呼问卷地址
            String callQuestionName = (String) tmap.get("callQuestionName");//外呼问卷名称
            String callNo = (String) tmap.get("callNo");//主叫号码
            String avoidFilterFlag = (String) tmap.get("avoidFilterFlag");//是否要清洗黑红白名单
            String callTestFlag = (String) tmap.get("callTestFlag");//是否拨测
            String freFilterFlag = (String) tmap.get("freFilterFlag");//是否需要进行频次清洗
            String callFormName = (String) tmap.get("callFormName");//外呼形式
            String callCityTypeName = (String) tmap.get("callCityTypeName");//外呼属地

            
            Map<String,Object> dChannelDataMap = new HashMap<String,Object>();
            dChannelDataMap.put("channelId", channelId == null ? "" : channelId);
            dChannelDataMap.put("channelName", channelName == null ? "" : channelName);
            dChannelDataMap.put("taskCode", taskCode == null ? "" : taskCode);
            dChannelDataMap.put("taskName", taskName == null ? "" : taskName);
            dChannelDataMap.put("demand", demand == null ? "" : demand);
            dChannelDataMap.put("taskClassName", taskClassName == null ? "" : taskClassName);
            dChannelDataMap.put("taskLevel1", taskLevel1 == null ? "" : taskLevel1);
            dChannelDataMap.put("taskLevel2", taskLevel2 == null ? "" : taskLevel2);
            dChannelDataMap.put("taskLevel3", taskLevel3 == null ? "" : taskLevel3);
            dChannelDataMap.put("busiLevel1", busiLevel1 == null ? "" : busiLevel1);
            dChannelDataMap.put("busiLevel2", busiLevel2 == null ? "" : busiLevel2);
            dChannelDataMap.put("inPlanFlag", inPlanFlag == null ? "" : inPlanFlag);
            dChannelDataMap.put("monthTaskName",monthTaskName == null ? "" : monthTaskName);
            dChannelDataMap.put("callCycle", callCycle == null ? "" : callCycle);
            dChannelDataMap.put("callPlanNum", callPlanNum == null ? "" : callPlanNum);
            dChannelDataMap.put("finishDate", finishDate == null ? "" : finishDate);
            dChannelDataMap.put("taskComment", taskComment == null ? "" : taskComment);
            dChannelDataMap.put("userLableInfo", userLableInfo == null ? "" : userLableInfo);
            dChannelDataMap.put("callQuestionUrl", callQuestionUrl == null ? "" : callQuestionUrl);
            dChannelDataMap.put("callQuestionName", callQuestionName == null ? "" : callQuestionName);
            dChannelDataMap.put("callNo", callNo == null ? "" : callNo);
            dChannelDataMap.put("avoidFilterFlag", avoidFilterFlag == null ? "" : avoidFilterFlag);
            dChannelDataMap.put("callTestFlag", callTestFlag == null ? "" : callTestFlag);
            dChannelDataMap.put("freFilterFlag", freFilterFlag == null ? "" : freFilterFlag);
            dChannelDataMap.put("callFormName", callFormName == null ? "" : callFormName);
            dChannelDataMap.put("callCityTypeName", callCityTypeName == null ? "" : callCityTypeName);
            dChannelDataMap.put("isCall", 1);
            
            jsonList.add(dChannelDataMap);
         }
         

         returnMap.put("status","200");
         returnMap.put("data", jsonList);
         //String result = "{status : 200,data:["+dChannelDataJSON.toString()+"]}";
         System.out.println("dChannelDataJSONresult===="+returnMap.toString());
        }catch(Exception e){
            e.printStackTrace();
            returnMap.put("status", "201");
            returnMap.put("result", "fail");
            //String result = "{status : 201, result:fail}";
            System.out.println("dChannelDataJSONresult===="+returnMap.toString());
        }

            return returnMap;                        
    }

    
    /**
     * @Title: getLogRecord
     * @Description: 审批日志记录
     * @param @param mapping
     * @param @param form
     * @param @param request
     * @param @param response
     * @param @return
     * @param @throws Exception    
     * @return ActionForward 
     * @throws
     */
    @RequestMapping("/getLogRecord")
    @ResponseBody
    public Map<String, Object> getLogRecord(HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();

        String campsegId = request.getParameter("campsegId");
        McdCampDef mtlCampSeginfo = mpmCampSegInfoService.getCampSegInfo(campsegId);
        try{
            //调用接口获取请求返回的XML
            log.info("=========获取XML开始===========");
            

            McdSysInterfaceDef url = callwsUrlService.getCallwsURL("APPREVEINFO_BYIDS");
//          
//          Client client = new Client(new URL(url.getCallwsUrl()));
//          
//          Object[] resultObject = client.invoke("getMarketApproveInfo", new Long[] {Long.parseLong(mtlCampSeginfo.getApproveFlowid())});//mtlCampSeginfo.getApproveFlowid()});
//          org.apache.xerces.dom.DocumentImpl resulta = (org.apache.xerces.dom.DocumentImpl) resultObject[0];
//          String childxml = resulta.getDocumentElement().getFirstChild().getNodeValue();
            
            QName name=new QName("http://impl.biz.web.tz","getMarketApproveInfo");
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(url.getCallwsUrl()));
            call.setOperationName(name);
//          call.setOperationName("commitApproveInfo");
            call.setTimeout(50000);//超时时间5秒
            
            if(mtlCampSeginfo.getApproveFlowId() != null){
                //先从本地库去日志信息，如果该日志信息已经存在，则直接使用，否则再次调用
                McdApproveLog mcdApproveLog = mpmCampSegInfoService.getLogByFlowId(mtlCampSeginfo.getApproveFlowId());
                String childxml = null;
               
                    
                if(mcdApproveLog != null && null != mcdApproveLog.getApproveResult()){
                    childxml = mcdApproveLog.getApproveResult();
                }else{
                    childxml = call.invoke(new Long[] { Long.parseLong(mtlCampSeginfo.getApproveFlowId()) }).toString();
                }
            
                
                
                
                log.info("=========XML解析开始===========");
                log.info("*********************日志信息："+childxml);
                 Document dom=DocumentHelper.parseText(childxml); 
                 Element root=dom.getRootElement();  
                 List<Element> elementList=root.elements("APPROVE_INFO"); 
                 List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
        
                 
                 for(int i=0;i<elementList.size();i++){

                    Element element= (org.dom4j.Element) elementList.get(i);
                    String node_no = element.element("NODE_NO") != null ? element.element("NODE_NO").getText() : ""; 
                    String nodname = element.element("NODE_NAME") != null ? element.element("NODE_NAME").getText() : ""; 
                    String node_name = "";
                    if("系统管理员审批".equals(nodname)){
                        node_name = "系统管理员审批";
                    }else{
                        node_name = i== 0 ? "草稿" : nodname;
                    }
                    String approve_result = element.element("APPROVE_RESULT") != null ? element.element("APPROVE_RESULT").getText() : "";
                    String approve_view = element.element("APPROVE_VIEW") != null ? element.element("APPROVE_VIEW").getText() : "";
                    String approvaler_name = element.element("APPROVALER_NAME") != null ? element.element("APPROVALER_NAME").getText() : "";
                    String approve_date = element.element("APPROVE_DATE") != null ? element.element("APPROVE_DATE").getText() : "";
                    String if_current_node = element.element("IF_CURRENT_NODE") != null ? element.element("IF_CURRENT_NODE").getText() : "";
                    Map<String,String> map = new HashMap<String,String>();


                    map.put("node_name", node_name);
                    map.put("node_no", node_no);
                    map.put("approve_result", approve_result);
                    map.put("approve_view", approve_view);
                    map.put("approvaler_name", approvaler_name);
                    map.put("approve_date", approve_date);
                    map.put("if_current_node", if_current_node);
                    mapList.add(map);
                 }
                


                log.info("=========XML解析结束===========");
                //转换JSON格式
                returnMap.put("status", "200");
                returnMap.put("data", mapList);
            }else{
                List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
                Map<String,String> map = new HashMap<String,String>();
                map.put("node_name", "草稿");
                map.put("node_no", "1");
                map.put("approve_result", mtlCampSeginfo.getCreateUserName() + "创建了策略");
                map.put("approve_view", "");
                map.put("approvaler_name",mtlCampSeginfo.getCreateUserName());
                map.put("approve_date", DateFormatUtils.format(mtlCampSeginfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                map.put("if_current_node", "");
                mapList.add(map);
                returnMap.put("status", "200");
                returnMap.put("result", "无法获取外部审批ID,该策略没有提交审批或提交审批错误");
                returnMap.put("data", mapList);
            }

        }catch(Exception e){
            log.error(e);
//          e.getStackTrace();
//          result.put("status", "201");
//          result.put("result", "fail");
            List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
            Map<String,String> map = new HashMap<String,String>();
            map.put("node_name", "错误");
            map.put("node_no", "1");
            map.put("approve_result", "发生错误，审批系统未返回相关日志");
            map.put("approve_view", "");
            map.put("approvaler_name",mtlCampSeginfo.getCreateUserName());
            map.put("approve_date", DateFormatUtils.format(mtlCampSeginfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            map.put("if_current_node", "");
            mapList.add(map);
            JSONArray jsonArray = JSONArray.fromObject(mapList);
            returnMap.put("status", "200");
            returnMap.put("result", "无法获取外部审批ID,该策略没有提交审批或提交审批错误");
            returnMap.put("data", jsonArray);
        }

        return returnMap;
    }
    
    /**
     * @Title: getLogRecord
     * @Description: 营销策略查看URL token验证
     * @param @param mapping
     * @param @param form
     * @param @param request
     * @param @param response
     * @param @return
     * @param @throws Exception    
     * @return ActionForward 
     * @throws
     */
    @RequestMapping("/tokenVerification")
    @ResponseBody
    public Map<String, Object> tokenVerification(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String campsegId = request.getParameter("campsegId");
        String token = request.getParameter("token");
        Map<String,Object> returnMap = new HashMap<String,Object>();
        boolean verification = false;
        if(!StringUtils.isEmpty(campsegId) && !StringUtils.isEmpty(token)){
            String tokenNew = DESBase64Util.encodeInfo(campsegId);
            if(token.equals(tokenNew)){
                verification = true;
            }
        }

        returnMap.put("status", "200");
        JSONObject verificationJSON = new JSONObject();
        verificationJSON.put("verification", verification);
        verificationJSON.put("campsegId", campsegId);

        returnMap.put("data", verificationJSON);
        return returnMap;
    }
    
    @RequestMapping("/getCampChannels")
    @ResponseBody
    public Map<String, Object> getCampChannels(HttpServletRequest request,HttpServletResponse response) throws IOException  {
        String campsegIds = request.getParameter("subCampsegIds");
        Map<String,Object> returnMap = new HashMap<String,Object>();
        try {
                List<Map<String,Object>> list=mpmCampSegInfoService.getChannelsByCampIds(campsegIds);
                returnMap.put("status", "200");
                returnMap.put("data", list);
                
        } catch (Exception e) {
            returnMap.put("status", "201");
            returnMap.put("data", "");
            returnMap.put("result", e.getMessage());
        } 
        return returnMap;
    }
    /**
     * 查询（父）策略某个渠道的从某天到某天的执行情况
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getCampChannelDetail")
    @ResponseBody
   public Map<String, Object> getCampChannelDetail(HttpServletRequest request,HttpServletResponse response) throws IOException {
      
       String campsegId = request.getParameter("campsegId");
       String channelId = request.getParameter("channelId");
       String startDate = request.getParameter("startDate");
       String endDate = request.getParameter("endDate");
       Map<String,Object> returnMap = new HashMap<String,Object>();
       DataGridData dg = new DataGridData();
       try {
                List<Map<String,Object>> list=mpmCampSegInfoService.getCampChannelDetail(campsegId,channelId,startDate,endDate);
                dg.setDatas(list);
                if("901".equals(channelId)){
                    dg.setHeaderTexts(new String[]{"执行时间","计划群发客户量","已发送量/过滤数量","当日成功办理量","累计接触客户量","累计成功办理量"});
                    dg.setDataFields(new String[]{"DATA_DATE","TARGET_NUMS","EXCUTE_SURPLUS","SAILSUCC_NUMS","CUMULAT_SAIL_NUMS","CUMULAT_SAILSUCC_NUMS"});
                }else if("908".equals(channelId)){//外呼
                    dg.setHeaderTexts(new String[]{"执行时间","计划外呼量","已外呼数量","当日成功办理量","累计接触客户量","累计成功办理量"});
                    dg.setDataFields(new String[]{"DATA_DATE","TARGET_NUMS","SEND_NUMS","SAILSUCC_NUMS","CUMULAT_SAIL_NUMS","CUMULAT_SAILSUCC_NUMS"});
                }else if("910".equals(channelId)){//BOSS运营位
                    dg.setHeaderTexts(new String[]{"执行时间","当日接触客户量","当日成功办理量","累计接触客户量","累计成功办理量"});
                    dg.setDataFields(new String[]{"DATA_DATE","SAIL_NUMS","SAILSUCC_NUMS","CUMULAT_SAIL_NUMS","CUMULAT_SAILSUCC_NUMS"});
                }else{
                    dg.setHeaderTexts(new String[]{"执行时间","当日展示政策次数","累计展示政策次数","当日点击政策次数","累计点击政策次数","当日成功办理量","累计成功办理量"});
                    dg.setDataFields(new String[]{"DATA_DATE","EXPOSURE_NUMS","CUMULAT_EXPOSURE_NUMS","CK_NUMS","CUMULAT_CK_NUMS","SAILSUCC_NUMS","CUMULAT_SAILSUCC_NUMS"});
                }
                dg.setDatas(list);
                returnMap.put("status", "200");
                returnMap.put("data", dg);
                
        } catch (Exception e) {
            returnMap.put("status", "503");
            returnMap.put("data", "");
            returnMap.put("result", "获取渠道信息异常！！！");
        } 
       return returnMap;
   }
    
  /**
   * 查询某策略某个指定渠道的所有子策略某天的执行情况   
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @throws IOException
   */
    @RequestMapping("/getCampsChannelSituation") 
    @ResponseBody
    public Map<String, Object> getCampsChannelSituation(HttpServletRequest request,HttpServletResponse response) throws IOException {
          
           String campsegIds = request.getParameter("subCampsegIds");
           String channelId = request.getParameter("channelId");
           String statDate = request.getParameter("statDate");
           Map<String,Object> returnMap = new HashMap<String,Object>();
           DataGridData dg = new DataGridData();
           try {
                    List<Map<String,Object>> list=mpmCampSegInfoService.getCampsChannelSituation(campsegIds,channelId,statDate);
                    dg.setDatas(list);
                    if("901".equals(channelId)){
                        dg.setHeaderTexts(new String[]{"政策名称","计划群发客户量","已发送量/过滤数量","当日成功办理量","累计接触客户量","累计成功办理量"});
                        dg.setDataFields(new String[]{"PLAN_NAME","TARGET_NUMS","EXCUTE_SURPLUS","SAILSUCC_NUMS","CUMULAT_SAIL_NUMS","CUMULAT_SAILSUCC_NUMS"});
                    }else if("908".equals(channelId)){//外呼
                        dg.setHeaderTexts(new String[]{"政策名称","计划外呼量","已外呼数量","当日成功办理量","累计接触客户量","累计成功办理量"});
                        dg.setDataFields(new String[]{"PLAN_NAME","TARGET_NUMS","SEND_NUMS","SAILSUCC_NUMS","CUMULAT_SAIL_NUMS","CUMULAT_SAILSUCC_NUMS"});
                    }else if("910".equals(channelId)){//BOSS运营位
                        dg.setHeaderTexts(new String[]{"政策名称","当日接触客户量","当日成功办理量","累计接触客户量","累计成功办理量"});
                        dg.setDataFields(new String[]{"PLAN_NAME","SAIL_NUMS","SAILSUCC_NUMS","CUMULAT_SAIL_NUMS","CUMULAT_SAILSUCC_NUMS"});
                    }else{
                        dg.setHeaderTexts(new String[]{"政策名称","当日展示政策次数","累计展示政策次数","当日点击政策次数","累计点击政策次数","当日成功办理量","累计成功办理量"});
                        dg.setDataFields(new String[]{"PLAN_NAME","EXPOSURE_NUMS","CUMULAT_EXPOSURE_NUMS","CK_NUMS","CUMULAT_CK_NUMS","SAILSUCC_NUMS","CUMULAT_SAILSUCC_NUMS"});
                    }
                    dg.setDatas(list);
                    returnMap.put("status", "200");
                    returnMap.put("data", dg);
                    
            } catch (Exception e) {
                returnMap.put("status", "503");
                returnMap.put("data", "");
                returnMap.put("result", "获取渠道信息异常！！！");
            } 
           return returnMap;
       }
    


}
