package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.service.IMcdMtlGroupInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampsegCiCustgroup;
import com.asiainfo.biapp.mcd.tactics.vo.MtlGroupInfo;
import com.asiainfo.biapp.mcd.tactics.vo.MtlStcPlan;
import com.asiainfo.biframe.service.IdNameMapper;
import com.asiainfo.biframe.utils.spring.SystemServiceLocator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@RequestMapping("/tactics/campSegSearch")
public class CampSegSearchController extends BaseMultiActionController {
    private static Logger log = LogManager.getLogger();
    
//    @Resource(name="mpmUserPrivilegeService")
//    private IMpmUserPrivilegeService mpmUserPrivilegeService; 
    @Resource(name="mpmCampSegInfoService")
    private IMpmCampSegInfoService mpmCampSegInfoService;
    @Resource(name="mcdMtlGroupInfoService")
    private IMcdMtlGroupInfoService iMcdMtlGroupInfoService ;
    
    @RequestMapping("/searchIMcdCamp")
    public ModelAndView searchIMcdCamp(HttpServletRequest request, HttpServletResponse response)throws Exception {
//                initActionAttributes(request);

                // this.userId 当前登录人ID
                String keywords = request.getParameter("keywords") != null ? request
                        .getParameter("keywords") : null;
                String campDrvId = request.getParameter("campDrvId") != null ? request
                        .getParameter("campDrvId") : null;
                String campsegStatId = request.getParameter("campsegStatId") != null ? request
                        .getParameter("campsegStatId") : null;
                String isSelectMy = request.getParameter("isSelectMy") != null ? request
                        .getParameter("isSelectMy") : "0";
                String pageNum = request.getParameter("pageNum") != null ? request
                        .getParameter("pageNum") : "1";
                String channelId = request.getParameter("channelId");
                
                MtlCampSeginfo segInfo = new MtlCampSeginfo();
                segInfo.setIsZJ(true);
                segInfo.setKeywords(keywords);
                if (campDrvId != null && !"".equals(campDrvId)) {
                    segInfo.setCampDrvIds(campDrvId);
                }
                if (campsegStatId != null && !"".equals(campsegStatId)) {
                    segInfo.setCampsegStatId(Short.parseShort(campsegStatId));
                }
                if (isSelectMy != null) {
                    segInfo.setIsSelectMy(Integer.parseInt(isSelectMy));
                }
                segInfo.setChannelId(channelId);
                segInfo.setCreateUserid("chenyg");//(userId);
                
                
                

                // 不是省公司人员，则只可以产看本地市和省公司，省公司的则可查看全部
                String cityId = "999";//mpmUserPrivilegeService.getUserActualCity(this.user.getCityid()).getCityId();
                if (!"999".equals(cityId)) {
                    segInfo.setAreaId(cityId);
                }

                String channelCampCont = request.getParameter("channelCampCont");
                String clickQueryFlag = "true";
                Pager pager = new Pager();
                pager.setPageSize(MpmCONST.PAGE_SIZE);
                pager.setPageNum(Integer.parseInt(pageNum)); // 当前页
                if (pageNum != null) {
                    pager.setPageFlag("G");
                }

                List resultList = null;
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

                JSONObject dataJson = new JSONObject();
                dataJson.put("status", "200");
                dataJson.put("data", JmsJsonUtil.obj2Json(pager));
                response.setContentType("application/json; charset=UTF-8");
                response.setHeader("progma", "no-cache");
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Cache-Control", "no-cache");
                PrintWriter out = response.getWriter();
                out.print(dataJson);
                out.flush();
                out.close();

                return null;
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
    @RequestMapping("/searchCampsegStat")
    public ModelAndView searchCampsegStat( HttpServletRequest request, HttpServletResponse response) throws Exception {

        List statList = mpmCampSegInfoService.getDimCampsegStatList();
        JSONObject dataJson = new JSONObject();
        dataJson.put("status", "200");
        dataJson.put("data", JmsJsonUtil.obj2Json(statList));
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("progma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.print(dataJson);
        out.flush();
        out.close();

        return null;
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
    @RequestMapping("/searchDimCampDrvType")
    public ModelAndView searchDimCampDrvType( HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<DimCampDrvType> dimCampSceneList = mpmCampSegInfoService.getDimCampSceneList();
        JSONObject dataJson = new JSONObject();
        dataJson.put("status", "200");
        dataJson.put("data", JmsJsonUtil.obj2Json(dimCampSceneList));
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("progma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.print(dataJson);
        out.flush();
        out.close();

        return null;
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
    @RequestMapping("/searchMcdMpmCampSegChild")
    public ModelAndView searchMcdMpmCampSegChild(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        int status = 200;
        String msg = "";
        JSONArray jsonArray = null;
        try {
            String campsegId = request.getParameter("campsegId") != null ? request.getParameter("campsegId") : null;
            List<MtlCampSeginfo> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
            List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
            
            for (MtlCampSeginfo mtlCampSeginfo : mtlCampSeginfoList) {
                JSONObject dataJson1 = new JSONObject();
                String ruleDesc = "";
                MtlStcPlan stcPlan = mpmCampSegInfoService.getMtlStcPlanByPlanId(mtlCampSeginfo.getPlanId());// 查询产品信息
                dataJson1.put("planName", stcPlan != null ? stcPlan.getPlanName(): "");
                List custGroupSelectList = mpmCampSegInfoService.getCustGroupSelectList(mtlCampSeginfo.getCampsegId());// 取营销活动“目标群选择”步骤中选择的“目标客户群”及“对比客户群”信息
                String ruleDescShowSql = "";
                for (int i = 0; i < custGroupSelectList.size(); i++) {
                    MtlCampsegCiCustgroup mtlCampsegCustGroup = (MtlCampsegCiCustgroup) custGroupSelectList.get(i);
                    if ("CG".equals(mtlCampsegCustGroup.getCustgroupType())) {
                         //待定
//                        IMcdMtlGroupInfoService iMcdMtlGroupInfoService = (IMcdMtlGroupInfoService) SystemServiceLocator.getInstance().getService(MpmCONST.IMCD_MTL_GROUPINFO_SERVICE);
                         MtlGroupInfo mtlGroupInfo = iMcdMtlGroupInfoService.getMtlGroupInfo(mtlCampsegCustGroup.getCustgroupId());
                         if(mtlGroupInfo != null && mtlGroupInfo.getCustomGroupName() != null){
                             ruleDesc = "客户群：" + mtlGroupInfo.getCustomGroupName();// +"<br>" + ruleDesc;
                         }
                         int originalCustGroupNum = mtlGroupInfo.getCustomNum();   //原始客户群数量
                        dataJson1.put("originalCustGroupNum", String.valueOf(originalCustGroupNum));
                    }
                    dataJson1.put("ruleDesc", ruleDesc);
                    
                }
                List mcdList = mpmCampSegInfoService.getMtlChannelDefs(mtlCampSeginfo.getCampsegId());
                List<Map> mtltlChannelDefList = new ArrayList<Map>(); 
                
                 for(int i=0;i< mcdList.size();i++){
                     Map map = (Map)mcdList.get(i);
                     Map mapNew = new HashMap();
                     mapNew.put("TARGER_USER_NUMS", map.get("TARGER_USER_NUMS") == null ? "" : map.get("TARGER_USER_NUMS").toString());
                     mapNew.put("CHANNEL_NAME", map.get("CHANNEL_NAME") == null ? "" : map.get("CHANNEL_NAME").toString());
                     mtltlChannelDefList.add(mapNew);
                 }
                 JSONArray mtlChannelDefsJsonArray = JSONArray.fromObject(mtltlChannelDefList);
                 dataJson1.put("mtlChannelDefs", mtlChannelDefsJsonArray);
                 jsonObjectList.add(dataJson1);
            }
            
             jsonArray = JSONArray.fromObject(jsonObjectList);
        
        } catch (Exception e) {
            status = 201;
            e.printStackTrace();
            msg = e.getMessage();
        }  finally {
        
            JSONObject dataJson = new JSONObject();
            dataJson.put("status", "200");
            if(!"200".equals(status)){
                dataJson.put("result", msg);
            }
            dataJson.put("data", jsonArray);
            response.setContentType("application/json; charset=UTF-8");
            response.setHeader("progma", "no-cache");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            out.print(dataJson);
            out.flush();
            out.close();
        }
        
        return null;
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
    @RequestMapping("/submitApproval")
    public ModelAndView submitApproval(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String campsegId = request.getParameter("campsegId") != null ? request
                .getParameter("campsegId") : null;
        String message = mpmCampSegInfoService.submitApprovalXml(campsegId);
        JSONObject dataJson = new JSONObject();
        if (message.contains("失败")) {
            dataJson.put("status", 201);
            dataJson.put("result", message);
        } else {
            dataJson.put("status", 200);
        }

        dataJson.put("data", "");
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("progma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.print(dataJson);
        out.flush();
        out.close();
        return null;
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
    @RequestMapping("/delCampseg")
    public ModelAndView delCampseg( HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String msg = "";
        int status = 200;
        try {
            String campsegId = request.getParameter("campsegId") != null ? request
                    .getParameter("campsegId") : null;

            if (campsegId != null) {
                mpmCampSegInfoService.deleteCampSegInfo(campsegId);
            }

        } catch (Exception e) {
            log.error("", e);
            status = 201;
            msg = "删除策略信息失败";
        } finally {
            String jsonOut = null;
            JSONObject dataJson = new JSONObject();
            if (status != 200) {
                dataJson.put("status", status);
                dataJson.put("result", msg);
            } else {
                dataJson.put("status", status);
            }

            dataJson.put("data", "");
            response.setContentType("application/json; charset=UTF-8");
            response.setHeader("progma", "no-cache");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            out.print(dataJson);
            out.flush();
            out.close();
            return null;
        }

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
    @RequestMapping("/updateCampsegEndDate")
    public ModelAndView updateCampsegEndDate( HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String msg = "";
        int status = 200;
        try {

            String campsegId = request.getParameter("campsegId") != null ? request
                    .getParameter("campsegId") : null;
            String endDate = request.getParameter("endDate") != null ? request
                    .getParameter("endDate") : null;

            IMpmCampSegInfoService service = (IMpmCampSegInfoService) SystemServiceLocator
                    .getInstance().getService(
                            MpmCONST.CAMPAIGN_SEG_INFO_SERVICE);
            if (campsegId != null && endDate != null) {

                List<MtlCampSeginfo> mtlCampSeginfoList = service
                        .getChildCampSeginfo(campsegId);
                service.updateCampsegEndDate(campsegId, endDate);
                for (MtlCampSeginfo mtlCampSeginfo : mtlCampSeginfoList) {
                    service.updateCampsegEndDate(mtlCampSeginfo.getCampsegId(),
                            endDate);
                }

            } else {
                status = 201;
                msg = "延期日期或策略id为为空";
            }
        } catch (Exception e) {
            status = 201;
            msg = "发生未知错误，请联系管理员";
        } finally {
            JSONObject dataJson = new JSONObject();
            if (status != 200) {
                dataJson.put("status", status);
                dataJson.put("result", msg);
            } else {
                dataJson.put("status", status);
            }

            dataJson.put("data", "");
            response.setContentType("application/json; charset=UTF-8");
            response.setHeader("progma", "no-cache");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            out.print(dataJson);
            out.flush();
            out.close();
            return null;
        }
    }

}
