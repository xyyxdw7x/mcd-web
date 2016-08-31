package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.service.plan.IMtlStcPlanService;
import com.asiainfo.biapp.mcd.common.util.DateConvert;
import com.asiainfo.biapp.mcd.common.util.MpmCache;
import com.asiainfo.biapp.mcd.common.util.MpmUtil;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanType;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlan;
import com.asiainfo.biapp.mcd.constants.MpmCONST;
import com.asiainfo.biapp.mcd.service.IMpmCommonService;
import com.asiainfo.biapp.mcd.tactics.service.IDimCampsegTypeService;
import com.asiainfo.biapp.mcd.tactics.service.IMcdMtlGroupInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlSmsSendTestTask;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampsegStat;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampsegType;
import com.asiainfo.biapp.mcd.tactics.vo.MpmCampSegInfoBean;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.utils.spring.SystemServiceLocator;
import com.asiainfo.biframe.utils.string.StringUtil;
import org.springframework.web.servlet.ModelAndView;

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
    @Resource(name="mcdMtlGroupInfoService")
    private IMcdMtlGroupInfoService iMcdMtlGroupInfoService ;
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
            segInfoBean.setCampStartDate(MpmUtil.parseOutDate(segInfo.getStartDate()));
            segInfoBean.setCampEndDate(MpmUtil.parseOutDate(segInfo.getEndDate()));
            segInfoBean.setStartDate(MpmUtil.parseOutDate(segInfo.getStartDate()));
            segInfoBean.setEndDate(MpmUtil.parseOutDate(segInfo.getEndDate()));
            
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

}
