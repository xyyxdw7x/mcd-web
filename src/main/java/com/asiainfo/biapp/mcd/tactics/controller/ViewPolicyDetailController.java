package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.service.custgroup.CustGroupInfoService;
import com.asiainfo.biapp.mcd.common.service.plan.IMtlStcPlanService;
import com.asiainfo.biapp.mcd.common.util.DateConvert;
import com.asiainfo.biapp.mcd.common.util.MpmCache;
import com.asiainfo.biapp.mcd.common.util.MpmUtil;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanType;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlan;
import com.asiainfo.biapp.mcd.constants.MpmCONST;
import com.asiainfo.biapp.mcd.custgroup.vo.CustUpdateCycle;
import com.asiainfo.biapp.mcd.service.IMpmCommonService;
import com.asiainfo.biapp.mcd.tactics.service.IDimCampsegTypeService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlSmsSendTestTask;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampsegStat;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampsegType;
import com.asiainfo.biapp.mcd.tactics.vo.MpmCampSegInfoBean;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.utils.date.DateUtil;
import com.asiainfo.biframe.utils.spring.SystemServiceLocator;
import com.asiainfo.biframe.utils.string.StringUtil;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 策略详情Controller
 * @author AsiaInfo-jie
 *
 */
@RequestMapping("/tactics/viewPolicyDetail")
public class ViewPolicyDetailController extends BaseMultiActionController  {
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
        MpmCampSegInfoBean segInfoBean = new MpmCampSegInfoBean();

        String campsegId = request.getParameter("campsegId");
        MtlCampSeginfo segInfo;
        if(campsegId!=null&&!"".equals(campsegId)){
            segInfo = mpmCampSegInfoService.getCampSegInfo(campsegId);
        }else{
            segInfo = mpmCampSegInfoService.getCampSegInfo(segInfoBean.getCampsegId());
        }
        
        try{
            
            BeanUtils.copyProperties(segInfoBean, segInfo);
            segInfoBean.setCampStartDate(segInfo.getStartDate());
            segInfoBean.setCampEndDate(segInfo.getEndDate());
            segInfoBean.setStartDate(segInfo.getStartDate());
            segInfoBean.setEndDate(segInfo.getEndDate());
            
            //根据ID获取用户类
            IUser user = null;//mpmUserPrivilegeService.getUser(segInfo.getCreateUserid());
            String userName = "";
            if(user!=null){
                userName = user.getUsername();
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
            map.put("campDrvId", segInfoBean.getCampDrvId());
            map.put("campDrvName", campDrvName);
            map.put("campsegTypeId", segInfoBean.getCampsegTypeId());
            map.put("campsegTypeName", campsegTypeName);
            map.put("createTime", createTime);
            List<MtlCampSeginfo> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
    
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
        
        MpmCampSegInfoBean segInfoBean = new MpmCampSegInfoBean();
        //String planid = request.getParameter("planid");
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("progma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
         // “匹配的政策”
        try{
            String campsegId = request.getParameter("campsegId");
            MtlCampSeginfo segInfo = mpmCampSegInfoService.getCampSegInfo(campsegId);
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
        
        MpmCampSegInfoBean segInfoBean = new MpmCampSegInfoBean();        
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
         List<Map<String,Object>> ruleList=null;//mpmCampSegInfoService.getrule(campsegId);
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
         List<Map<String,Object>> getDataDateCustomNumlist  = null;//mpmCampSegInfoService.getDataDateCustomNum(campsegId);//20130916114353920  
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
         int oricustGroupNum = 0;//mcdMtlGroupService.getOriCustGroupNum(custom_group_id);
         
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

}
