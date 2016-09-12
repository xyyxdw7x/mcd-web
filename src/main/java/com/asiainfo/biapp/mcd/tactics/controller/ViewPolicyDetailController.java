package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.service.custgroup.CustGroupInfoService;
import com.asiainfo.biapp.mcd.common.service.plan.IMtlStcPlanService;
import com.asiainfo.biapp.mcd.common.util.DateConvert;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanType;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlan;
import com.asiainfo.biapp.mcd.custgroup.vo.CustUpdateCycle;
import com.asiainfo.biapp.mcd.tactics.service.ChannelBossSmsTemplateService;
import com.asiainfo.biapp.mcd.tactics.service.IDimCampsegTypeService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlChannelDefService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlSmsSendTestTask;
import com.asiainfo.biapp.mcd.tactics.vo.ChannelBossSmsTemplate;
import com.asiainfo.biapp.mcd.tactics.vo.DataGridData;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampsegStat;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampsegType;
import com.asiainfo.biapp.mcd.tactics.vo.McdApproveLog;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCallwsUrl;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;
import com.asiainfo.biframe.utils.date.DateUtil;
import com.asiainfo.biframe.utils.string.DES;
import com.asiainfo.biframe.utils.string.StringUtil;

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
    @Resource(name = "mpmUserPrivilegeService")
    private IMpmUserPrivilegeService mpmUserPrivilegeService;
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
    public ModelAndView viewPolicyDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
        McdCampDef segInfoBean = new McdCampDef();     

        String campsegId = request.getParameter("campsegId");
        McdCampDef segInfo;
        if(campsegId!=null&&!"".equals(campsegId)){
            segInfo = mpmCampSegInfoService.getCampSegInfo(campsegId);
        }else{
            segInfo = mpmCampSegInfoService.getCampSegInfo(segInfoBean.getCampsegId());
        }
        
        try{
            
            BeanUtils.copyProperties(segInfoBean, segInfo);
            segInfoBean.setStartDate(segInfo.getStartDate());
            segInfoBean.setEndDate(segInfo.getEndDate());
            segInfoBean.setStartDate(segInfo.getStartDate());
            segInfoBean.setEndDate(segInfo.getEndDate());
            
            //根据ID获取用户类
            User user = this.getUserPrivilege().queryUserById(segInfo.getCreateUserid());//mpmUserPrivilegeService.getUser(segInfo.getCreateUserid());
            String userName = "";
            if(user!=null){
                userName = user.getName();
            }
            //业务类型
            String campDrvName ="";
            if(StringUtil.isNotEmpty(segInfo.getPlanId()) && segInfo.getPlanId().indexOf(",") != -1){
                String planArr[] = segInfo.getPlanId().split(",");
                for(int i = 0;i<planArr.length;i++){
                    MtlStcPlan stcPlan = mpmCampSegInfoService.getMtlStcPlanByPlanId(planArr[i]);
                    if(stcPlan != null){
                        if(StringUtil.isNotEmpty(stcPlan.getPlanType())){
                            DimPlanType dimPlanType = mtlStcPlanService.getPlanTypeById(stcPlan.getPlanType());
                            if(null != dimPlanType){
                                campDrvName += dimPlanType.getTypeName()+",";
                            }
                        }
                    }
                }
                campDrvName = campDrvName.substring(0, campDrvName.length()-1);
            }else{
                MtlStcPlan stcPlan = mpmCampSegInfoService.getMtlStcPlanByPlanId(segInfo.getPlanId());
                if(stcPlan != null){
                    if(StringUtil.isNotEmpty(stcPlan.getPlanType())){
                        DimPlanType dimPlanType = mtlStcPlanService.getPlanTypeById(stcPlan.getPlanType());
                        if(null != dimPlanType){
                            campDrvName = dimPlanType.getTypeName();
                        }
                    }
                }
            }
            
            //创建时间 日期格式 yyyy-MM-dd
            String createTime = DateConvert.formatLongDateTime(segInfo.getCreateTime());
            Short campsegStatId = Short.valueOf(segInfo.getCampsegStatId());
            //营销活动状态名
            DimCampsegStat dimCampsegStat = mpmCampSegInfoService.getDimCampsegStat(segInfo.getCampsegStatId().toString()); 
            String statusName = dimCampsegStat.getCampsegStatName(); //MpmCache.getInstance().getNameByTypeAndKey(MpmCONST.MPM_CAMPSEG_STAT_DEFINE,segInfo.getCampsegStatId().toString());
            //营销类型
            Short campsegTypeId = segInfo.getCampsegTypeId() == null ? 0 : segInfo.getCampsegTypeId();
            String campsegTypeName = dimCampsegTypeService.getDimCampsegTypeName(campsegTypeId);
            DimCampsegType campsegType = dimCampsegTypeService.getDimCampsegType(campsegTypeId);
            
            Map map= new HashMap();
            map.put("campsegId", segInfoBean.getCampsegId());
            map.put("campsegName", segInfoBean.getCampsegName());
            map.put("statusName", statusName);
            map.put("startDate", segInfoBean.getStartDate());
            map.put("endDate", segInfoBean.getEndDate());
            map.put("createUserid", segInfoBean.getCreateUserid());
            map.put("createUserName", userName);
            map.put("campDrvName", campDrvName);
            map.put("campsegTypeId", segInfoBean.getCampsegTypeId());
            map.put("campsegTypeName", campsegTypeName);
            map.put("createTime", createTime);
            List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
    
            //mtlCampSeginfoList.addAll(mtlCampSeginfoList1);
            map.put("childMtlCampSeginfo", mtlCampSeginfoList);
            
            JSONObject jsonMap = JSONObject.fromObject(map); 
    //      System.out.println("jsonMap========"+jsonMap.toString());  
            JSONObject result = new JSONObject();
            result.put("status", "200");
            result.put("data", jsonMap);
            //String result = "{status : 200,data:["+jsonMap.toString()+"]}";
            System.out.println("campsegresult===="+result);
            response.setContentType("application/json; charset=UTF-8");
            response.setHeader("progma", "no-cache");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();
         
        }catch(Exception e){
            e.printStackTrace();
             JSONObject result = new JSONObject();
             result.put("status", "201");
             result.put("result", "fail");
            //String result = "{status : 201, result:fail}";
            System.out.println("campsegresult===="+result);
            response.setContentType("application/json; charset=UTF-8");
            response.setHeader("progma", "no-cache");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();
        }finally{
            
        }
         return null;
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
    public ModelAndView getMtlStcPlan(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	McdCampDef segInfoBean = new McdCampDef();     
        //String planid = request.getParameter("planid");
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("progma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
         // “匹配的政策”
        try{
            String campsegId = request.getParameter("campsegId");
            McdCampDef segInfo = mpmCampSegInfoService.getCampSegInfo(campsegId);
             String planid = segInfo.getPlanId();//获取产品编号qzd548
             JSONObject stcPlanJSON = new JSONObject();
             if(planid!=null&&!"".equals(planid)){
                MtlStcPlan stcPlan = mpmCampSegInfoService.getMtlStcPlanByPlanId(planid);//查询产品信息
                DimPlanType dimPlanType = mtlStcPlanService.getPlanTypeById(stcPlan.getPlanType());
                stcPlanJSON.put("campDrvName", dimPlanType != null ? dimPlanType.getTypeName(): "");    //业务类型
                stcPlanJSON.put("stcPlanName", stcPlan != null ? stcPlan.getPlanName() : "");   //“政策名称”,政策即产品
                stcPlanJSON.put("stcPlanId", stcPlan != null ? stcPlan.getPlanId() : "");       //“编号”
                stcPlanJSON.put("stcPlanStartdate", stcPlan != null ? DateUtil.date2String(stcPlan.getPlanStartdate(), "yyyy/MM/dd") : ""); //“有效期”开始时间
                stcPlanJSON.put("stcPlanEnddate", stcPlan != null ? DateUtil.date2String(stcPlan.getPlanEnddate(), "yyyy/MM/dd") : "");     //“有效期”结束时间
                stcPlanJSON.put("stcPlanDesc", stcPlan != null ? stcPlan.getPlanDesc() : "");   //“描述”
               
             }
             request.setAttribute("stcPlanJSONStr", stcPlanJSON.toString());
             request.setAttribute("stcPlanJSON", stcPlanJSON);
             JSONObject result = new JSONObject();
             result.put("status", "200");
             result.put("data", stcPlanJSON);
             //String result = "{status: 200,data:["+stcPlanJSON.toString()+"]}";
             System.out.println("stcPlanresult===="+result);
             out.print(result);
        }catch(Exception e){
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("status", "201");
            result.put("result", "fail");
            //String result = "{status : 201, result:fail}";
            System.out.println("stcPlanresult===="+result);
            out.print(result);
        }
        out.flush();
        out.close();
        return null;
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
    public ModelAndView getTargetCustomerbase(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        McdCampDef segInfoBean = new McdCampDef();     
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("progma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
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
         String show_sql="";
         String resultStr ="";
         for(int i=0;i<ruleList.size();i++){
             Map<String,Object> tmap = ruleList.get(i);
             String elementValue = (String) tmap.get("ELEMENT_VALUE");
             String columnCnName = (String) tmap.get("COLUMN_CN_NAME");
             String ctrlTypeId = (String)tmap.get("CTRL_TYPE_ID");   //预留，可以根据标签类型进行拼接，暂时不错
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
//           if ("".equals(show_sql)) {
//              show_sql = (String) tmap.get("show_sql");
//           } else {
//              show_sql = show_sql + ";" + (String) tmap.get("show_sql");
//           }
//           custgroup_number =  tmap.get("custgroup_number") == null ? null :(BigDecimal) tmap.get("custgroup_number");
         }
         if(StringUtil.isNotEmpty(resultStr)){
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
//       for(int i=0;i<getDataDateCustomNumlist.size();i++){
         if(CollectionUtils.isNotEmpty(getDataDateCustomNumlist)){
             Map<String,Object> tmap = getDataDateCustomNumlist.get(0);
             max_data_time = tmap.get("max_data_time") == null ? null : (String) tmap.get("max_data_time");
             sum_custom_num = tmap.get("sum_custom_num") == null ? null : (BigDecimal) tmap.get("sum_custom_num");
         }
//       }
         customDataJSON.put("max_data_time", max_data_time == null ? "" : max_data_time);
         customDataJSON.put("sum_custom_num", sum_custom_num == null ? "" : sum_custom_num); 
         System.out.println("customDataJSON=========="+customDataJSON);
         //取 渠道信息，包含渠道成名，客户群规模。实际客户群规模
         List mcdList = mpmCampSegInfoService.getMtlChannelDefs(campsegId);
         
         //取原始客户群数量
         int oricustGroupNum = custGroupInfoService.getOriCustGroupNum(custom_group_id);
         
         List<Map> mtltlChannelDefList = new ArrayList<Map>(); 
         for(int i=0;i< mcdList.size();i++){
             Map map = (Map)mcdList.get(i);
             map.put("custgroup_number", custgroup_number == null ? "" : custgroup_number.toString());
             map.put("TARGER_USER_NUMS", oricustGroupNum);
             mtltlChannelDefList.add(map);
         }
         JSONArray jsonArray = JSONArray.fromObject(mtltlChannelDefList);
         customDataJSON.put("mtlChannelDefs", jsonArray);
         
         JSONObject result =new JSONObject();
         result.put("status", "200");
         result.put("data", customDataJSON);
         //String result = "{status : 200,data:["+customDataJSON.toString()+"]}";
         out.print(result);
         System.out.println("customDataJSONresult===="+result);
         
    }catch(Exception e){
        e.printStackTrace();
        JSONObject result = new JSONObject();
        result.put("status","201");
        result.put("result", "fail");
        //String result = "{status : 201, result:fail}";
        out.print(result);
        System.out.println("customDataJSONresult===="+result);
        
    }
        out.flush();
        out.close();
        return null;
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
    public ModelAndView getDeliveryChannel(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	McdCampDef segInfoBean = new McdCampDef();        
        
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("progma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
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
                    list = mtlChannelDefService.getDeliveryChannel(segInfoBean.getCampsegId());//20130916114353920  baseForm.getCampsegId()
                    list2 = mtlChannelDefService.getDeliveryChannelCall(segInfoBean.getCampsegId());
                } 
         JSONArray dChannelDataJSONArray=new JSONArray();
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
            if(StringUtil.isNotEmpty(channelAdivId)){
                String[] adivId = channelAdivId.split(",");
                for(int n = 0;n<adivId.length;n++){
                    for(int m = 0;m<bossSmsTemplatelist.size();m++){
                        if(adivId[n].equals(bossSmsTemplatelist.get(m).getTemplateId())){
                            templateName += bossSmsTemplatelist.get(m).getTemplateName()+",";
                        }
                    }
                }
                if(StringUtil.isNotEmpty(templateName)){
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
            if(StringUtil.isNotEmpty(channel_id) && "901".equals(channel_id)){  //当时短信渠道的时候
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
            
            JSONObject dChannelDataJSON=new JSONObject();
            dChannelDataJSON.put("channel_id", channel_id == null ? "" : channel_id);
            dChannelDataJSON.put("channel_name", channel_name == null ? "" : channel_name);
            dChannelDataJSON.put("exec_content", exec_content == null ? "" : exec_content);
            dChannelDataJSON.put("update_cycle", update_cycle == null ? "" : update_cycle);
            dChannelDataJSON.put("trigger_timing", trigger_timing == null ? "" : trigger_timing);
            dChannelDataJSON.put("channelAdivId", channelAdivId == null ? "" : channelAdivId);
            dChannelDataJSON.put("adivName", adivName == null ? "" : adivName);
            dChannelDataJSON.put("channelAdivName", channelAdivName == null ? "" : channelAdivName);
//            dChannelDataJSON.put("eventRuleDesc", eventRuleDesc == null ? "" : eventRuleDesc);
            dChannelDataJSON.put("paramDays", paramDays == null ? "" : paramDays);
            dChannelDataJSON.put("paramNum", paramNum == null ? "" : paramNum);
            dChannelDataJSON.put("awardMount", awardMount == 0D ? "" : awardMount);
            dChannelDataJSON.put("editUrl", editUrl == null ? "" : editUrl);
            dChannelDataJSON.put("handleUrl", handleUrl == null ? "" : handleUrl);
            dChannelDataJSON.put("sendSms", sendSms == null ? "" : sendSms);
            dChannelDataJSON.put("execTitle", execTitle == null ? "" : execTitle);
            dChannelDataJSON.put("fileName", fileName == null ? "" : fileName);
            dChannelDataJSON.put("isCall", 0);
            
            dChannelDataJSONArray.add(dChannelDataJSON);
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

            
            JSONObject dChannelDataJSON=new JSONObject();
            dChannelDataJSON.put("channelId", channelId == null ? "" : channelId);
            dChannelDataJSON.put("channelName", channelName == null ? "" : channelName);
            dChannelDataJSON.put("taskCode", taskCode == null ? "" : taskCode);
            dChannelDataJSON.put("taskName", taskName == null ? "" : taskName);
            dChannelDataJSON.put("demand", demand == null ? "" : demand);
            dChannelDataJSON.put("taskClassName", taskClassName == null ? "" : taskClassName);
            dChannelDataJSON.put("taskLevel1", taskLevel1 == null ? "" : taskLevel1);
            dChannelDataJSON.put("taskLevel2", taskLevel2 == null ? "" : taskLevel2);
            dChannelDataJSON.put("taskLevel3", taskLevel3 == null ? "" : taskLevel3);
            dChannelDataJSON.put("busiLevel1", busiLevel1 == null ? "" : busiLevel1);
            dChannelDataJSON.put("busiLevel2", busiLevel2 == null ? "" : busiLevel2);
            dChannelDataJSON.put("inPlanFlag", inPlanFlag == null ? "" : inPlanFlag);
            dChannelDataJSON.put("monthTaskName",monthTaskName == null ? "" : monthTaskName);
            dChannelDataJSON.put("callCycle", callCycle == null ? "" : callCycle);
            dChannelDataJSON.put("callPlanNum", callPlanNum == null ? "" : callPlanNum);
            dChannelDataJSON.put("finishDate", finishDate == null ? "" : finishDate);
            dChannelDataJSON.put("taskComment", taskComment == null ? "" : taskComment);
            dChannelDataJSON.put("userLableInfo", userLableInfo == null ? "" : userLableInfo);
            dChannelDataJSON.put("callQuestionUrl", callQuestionUrl == null ? "" : callQuestionUrl);
            dChannelDataJSON.put("callQuestionName", callQuestionName == null ? "" : callQuestionName);
            dChannelDataJSON.put("callNo", callNo == null ? "" : callNo);
            dChannelDataJSON.put("avoidFilterFlag", avoidFilterFlag == null ? "" : avoidFilterFlag);
            dChannelDataJSON.put("callTestFlag", callTestFlag == null ? "" : callTestFlag);
            dChannelDataJSON.put("freFilterFlag", freFilterFlag == null ? "" : freFilterFlag);
            dChannelDataJSON.put("callFormName", callFormName == null ? "" : callFormName);
            dChannelDataJSON.put("callCityTypeName", callCityTypeName == null ? "" : callCityTypeName);
            dChannelDataJSON.put("isCall", 1);
            
            dChannelDataJSONArray.add(dChannelDataJSON);
         }
         

         System.out.println("dChannelDataJSONArray=========="+dChannelDataJSONArray);
         JSONObject result =new JSONObject();
         result.put("status","200");
         result.put("data", dChannelDataJSONArray);
         //String result = "{status : 200,data:["+dChannelDataJSON.toString()+"]}";
         System.out.println("dChannelDataJSONresult===="+result);
         out.print(result);
        }catch(Exception e){
            e.printStackTrace();
            JSONObject result =new JSONObject();
            result.put("status", "201");
            result.put("result", "fail");
            //String result = "{status : 201, result:fail}";
            System.out.println("dChannelDataJSONresult===="+result);
            out.print(result);
        }
            out.flush();
            out.close();
            return null;                        
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
    public ModelAndView getLogRecord(HttpServletRequest request,HttpServletResponse response) throws Exception {

        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("progma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String campsegId = request.getParameter("campsegId");
        McdCampDef mtlCampSeginfo = mpmCampSegInfoService.getCampSegInfo(campsegId);
        JSONObject result = new JSONObject();
        try{
            //调用接口获取请求返回的XML
            log.info("=========获取XML开始===========");
            

            MtlCallwsUrl url = callwsUrlService.getCallwsURL("APPREVEINFO_BYIDS");
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
            
            if(mtlCampSeginfo.getApproveFlowid() != null){
                //先从本地库去日志信息，如果该日志信息已经存在，则直接使用，否则再次调用
                McdApproveLog mcdApproveLog = mpmCampSegInfoService.getLogByFlowId(mtlCampSeginfo.getApproveFlowid());
                String childxml = null;
               
                    
                if(mcdApproveLog != null && null != mcdApproveLog.getApproveResult()){
                    childxml = mcdApproveLog.getApproveResult();
                }else{
                    childxml = call.invoke(new Long[] { Long.parseLong(mtlCampSeginfo.getApproveFlowid()) }).toString();
                }
            
                
                
                
                log.info("=========XML解析开始===========");
                log.info("*********************日志信息："+childxml);
                 Document dom=DocumentHelper.parseText(childxml); 
                 Element root=dom.getRootElement();  
                 List<Element> elementList=root.elements("APPROVE_INFO"); 
                 List<Map> mapList = new ArrayList<Map>();
        
                 
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
                JSONArray jsonArray = JSONArray.fromObject(mapList);
                JSONObject object = new JSONObject();
                result.put("status", "200");
                result.put("data", jsonArray);
            }else{
                List<Map> mapList = new ArrayList<Map>();
                Map<String,String> map = new HashMap<String,String>();
                map.put("node_name", "草稿");
                map.put("node_no", "1");
                map.put("approve_result", mtlCampSeginfo.getCreateUserName() + "创建了策略");
                map.put("approve_view", "");
                map.put("approvaler_name",mtlCampSeginfo.getCreateUserName());
                map.put("approve_date", DateUtil.date2String(mtlCampSeginfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                map.put("if_current_node", "");
                mapList.add(map);
                JSONArray jsonArray = JSONArray.fromObject(mapList);
                JSONObject object = new JSONObject();
                result.put("status", "200");
                result.put("result", "无法获取外部审批ID,该策略没有提交审批或提交审批错误");
                result.put("data", jsonArray);
            }

        }catch(Exception e){
            log.error(e);
//          e.getStackTrace();
//          result.put("status", "201");
//          result.put("result", "fail");
            List<Map> mapList = new ArrayList<Map>();
            Map<String,String> map = new HashMap<String,String>();
            map.put("node_name", "错误");
            map.put("node_no", "1");
            map.put("approve_result", "发生错误，审批系统未返回相关日志");
            map.put("approve_view", "");
            map.put("approvaler_name",mtlCampSeginfo.getCreateUserName());
            map.put("approve_date", DateUtil.date2String(mtlCampSeginfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            map.put("if_current_node", "");
            mapList.add(map);
            JSONArray jsonArray = JSONArray.fromObject(mapList);
            JSONObject object = new JSONObject();
            result.put("status", "200");
            result.put("result", "无法获取外部审批ID,该策略没有提交审批或提交审批错误");
            result.put("data", jsonArray);
        }
        out.print(result);
        out.flush();
        out.close();
        return null;
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
    public ModelAndView tokenVerification(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String campsegId = request.getParameter("campsegId");
        String token = request.getParameter("token");
        boolean verification = false;
        if(!StringUtil.isEmpty(campsegId) && !StringUtil.isEmpty(token)){
            String tokenNew = DES.encrypt(campsegId);
            if(token.equals(tokenNew)){
                verification = true;
            }
        }
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("progma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();
        result.put("status", "200");
        JSONObject verificationJSON = new JSONObject();
        verificationJSON.put("verification", verification);
        verificationJSON.put("campsegId", campsegId);

        result.put("data", verificationJSON);
        
        out.print(result);
        out.flush();
        out.close();
        return null;
    }
    
    @RequestMapping("/getCampChannels")
    public ModelAndView getCampChannels(HttpServletRequest request,HttpServletResponse response) throws IOException  {
        String campsegIds = request.getParameter("subCampsegIds");
        try {
                List list=mpmCampSegInfoService.getChannelsByCampIds(campsegIds);
                this.outJson4Ws(response, list, "200", "");
        } catch (Exception e) {
            this.outJson4Ws(response, null, "503", "获取策略渠道异常！！！");
        }
        return null;
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
   public ModelAndView getCampChannelDetail(HttpServletRequest request,HttpServletResponse response) throws IOException {
      
       String campsegId = request.getParameter("campsegId");
       String channelId = request.getParameter("channelId");
       String startDate = request.getParameter("startDate");
       String endDate = request.getParameter("endDate");
       DataGridData dg = new DataGridData();
       try {
                List list=mpmCampSegInfoService.getCampChannelDetail(campsegId,channelId,startDate,endDate);
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
                this.outJson4Ws(response, dg, "200", "");
           } catch (Exception e) {
                this.outJson4Ws(response, null, "503", "获取渠道信息异常！！！");
          }
       return null;
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
    public ModelAndView getCampsChannelSituation(HttpServletRequest request,HttpServletResponse response) throws IOException {
          
           String campsegIds = request.getParameter("subCampsegIds");
           String channelId = request.getParameter("channelId");
           String statDate = request.getParameter("statDate");
           DataGridData dg = new DataGridData();
           try {
                    List list=mpmCampSegInfoService.getCampsChannelSituation(campsegIds,channelId,statDate);
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
                    this.outJson4Ws(response, dg, "200", "");
               } catch (Exception e) {
                   log.error("", e);
                    this.outJson4Ws(response, null, "503", "获取渠道信息异常！！！");
              }
           return null;
       }
    


}
