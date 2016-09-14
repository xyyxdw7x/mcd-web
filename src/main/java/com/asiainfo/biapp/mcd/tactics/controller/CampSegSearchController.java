package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.PrintWriter;
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
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.service.custgroup.CustGroupInfoService;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.custgroup.McdCustgroupDef;
import com.asiainfo.biapp.mcd.common.vo.plan.McdPlanDef;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlSmsSendTestTask;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.MarketApproveInfo;
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
    @Resource(name="mpmCampSegInfoService")
    private IMpmCampSegInfoService mpmCampSegInfoService;
    @Resource(name = "custGroupInfoService")
    private CustGroupInfoService custGroupInfoService;
    @Resource(name = "mtlCallWsUrlService")
    private IMtlCallWsUrlService callwsUrlService;
    @Resource(name = "mtlSmsSendTestTask")
    private IMtlSmsSendTestTask mtlSmsSendTestTask;
    
    @RequestMapping("/searchIMcdCamp")
    public ModelAndView searchIMcdCamp(HttpServletRequest request, HttpServletResponse response)throws Exception {
//                initActionAttributes(request);

                User user = this.getUser(request, response);
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
                
                
                

                // 不是省公司人员，则只可以产看本地市和省公司，省公司的则可查看全部
                String cityId = user.getCityId();
               

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
            List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
            List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
            
            for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
                JSONObject dataJson1 = new JSONObject();
                String ruleDesc = "";
                McdPlanDef stcPlan = mpmCampSegInfoService.getMtlStcPlanByPlanId(mtlCampSeginfo.getPlanId());// 查询产品信息
                dataJson1.put("planName", stcPlan != null ? stcPlan.getPlanName(): "");
                List custGroupSelectList = mpmCampSegInfoService.getCustGroupSelectList(mtlCampSeginfo.getCampId());// 取营销活动“目标群选择”步骤中选择的“目标客户群”及“对比客户群”信息
                String ruleDescShowSql = "";
                for (int i = 0; i < custGroupSelectList.size(); i++) {
                    McdCampCustgroupList mtlCampsegCustGroup = (McdCampCustgroupList) custGroupSelectList.get(i);
                    if ("CG".equals(mtlCampsegCustGroup.getCustgroupType())) {
                         //待定
                         McdCustgroupDef mtlGroupInfo = custGroupInfoService.getMtlGroupInfo(mtlCampsegCustGroup.getCustgroupId());
                         if(mtlGroupInfo != null && mtlGroupInfo.getCustomGroupName() != null){
                             ruleDesc = "客户群：" + mtlGroupInfo.getCustomGroupName();// +"<br>" + ruleDesc;
                         }
                         int originalCustGroupNum = mtlGroupInfo.getCustomNum();   //原始客户群数量
                        dataJson1.put("originalCustGroupNum", String.valueOf(originalCustGroupNum));
                    }
                    dataJson1.put("ruleDesc", ruleDesc);
                    
                }
                List mcdList = mpmCampSegInfoService.getMtlChannelDefs(mtlCampSeginfo.getCampId());
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

            if (campsegId != null && endDate != null) {

                List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
                mpmCampSegInfoService.updateCampsegEndDate(campsegId, endDate);
                for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
                    mpmCampSegInfoService.updateCampsegEndDate(mtlCampSeginfo.getCampId(),endDate);
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
    @RequestMapping("/cancelAssignment")
    public ModelAndView cancelAssignment (HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String campsegId = request.getParameter("campsegId") != null ? request
                .getParameter("campsegId") : null;
        String anceldesc = request.getParameter("anceldesc") != null ? request
                .getParameter("anceldesc") : null;
        String msg = "";
        int status = 200;

        McdCampDef segInfo = mpmCampSegInfoService.getCampSegInfo(campsegId);
        StringBuffer xml = new StringBuffer(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<MARKET_ASSIGNMENT_INFO><ASSIGNMENT_INFO>");
        xml.append("<ASSIGN_ID>" + segInfo.getApproveFlowId() + "</ASSIGN_ID>");
        xml.append("<CANCEL_DESC>" + anceldesc + "</CANCEL_DESC>");
        xml.append("</ASSIGNMENT_INFO></MARKET_ASSIGNMENT_INFO>");

        String assing_id = null;
        String approve_flag = null;
        String approve_desc = null;
        log.info("开始工单撤销 ！");
        try {
            //

            McdSysInterfaceDef url = callwsUrlService.getCallwsURL("APPREVEINFO_BYIDS");

            QName name = new QName("http://impl.biz.web.tz", "cancelAssignment");
            Service serviceWs = new Service();
            Call call = (Call) serviceWs.createCall();
            call.setTargetEndpointAddress(new java.net.URL(url.getCallwsUrl()));
            call.setOperationName(name);
            // call.setOperationName("commitApproveInfo");
            call.setTimeout(50000);// 超时时间5秒
            log.info(xml.toString());
            String childxml = call.invoke(new Object[] { xml.toString() })
                    .toString();

            log.info("撤销工单返回responed xml " + childxml);
            Document dom = DocumentHelper.parseText(childxml);
            Element root = dom.getRootElement();
            List<Element> elementList = root.elements("ASSIGNMENT_INFO");
            MarketApproveInfo marketApproveInfo = null;

            for (int i = 0; i < elementList.size(); i++) {
                Element element = (org.dom4j.Element) elementList.get(i);
                assing_id = element.element("ASSIGN_ID") != null ? element
                        .element("ASSIGN_ID").getText() : "";
                approve_flag = element.element("PROCESS_FLAG") != null ? element
                        .element("PROCESS_FLAG").getText() : "";
                approve_desc = element.element("PROCESS_DESC") != null ? element
                        .element("PROCESS_DESC").getText() : "";
            }

            // 存撤销工单状态及撤销原因）
            if ("1".equals(approve_flag)) {
                short ampsegStatId = Short
                        .valueOf(MpmCONST.MPM_CAMPSEG_STAT_HDZZ);
                mpmCampSegInfoService.cancelAssignment(campsegId, ampsegStatId, approve_desc);
                List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
                for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
                    mpmCampSegInfoService.cancelAssignment(mtlCampSeginfo.getCampId(),
                            ampsegStatId, approve_desc);
                }
            } else {
                status = 201;
                msg = "接口返回处理结果失败，原因为：" + approve_desc;
            }

        } catch (Exception e) {
            status = 201;
            e.printStackTrace();
            msg = e.getMessage();
        } finally {
            JSONObject dataJson = new JSONObject();
            if (status == 200) {
                dataJson.put("status", status);
            } else {
                dataJson.put("status", status);
                dataJson.put("result", msg);
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
        }

        return null;
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
    @RequestMapping("/campCancel")
    public ModelAndView campCancel(HttpServletRequest request,HttpServletResponse response)
            throws Exception {
        String msg = "";
        int status = 200;
        try {
            /* 获取session信息 */
            String campsegId =  request.getParameter("campsegId") != null ? request.getParameter("campsegId").toString() : null;
            String pauseComment = request.getParameter("pauseComment") != null ? request.getParameter("pauseComment").toString() : null;
            if (campsegId != null) {
                // 获取service

                List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
                mpmCampSegInfoService.updateCampStat(campsegId,
                        MpmCONST.MPM_CAMPSEG_STAT_HDZZ);
                for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
                    mpmCampSegInfoService.updateCampStat(mtlCampSeginfo.getCampId(),
                            MpmCONST.MPM_CAMPSEG_STAT_HDZZ);
                }
                //保存停止原因
                mpmCampSegInfoService.updatMtlCampSeginfoPauseComment(campsegId,pauseComment);
            }

        } catch (Exception e) {
            log.error("", e);
            status = 201;
            msg = "策略终止失败";
        } finally {
            if (StringUtils.isEmpty(msg)) {
                msg = "策略终止成功";
            }
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
     * 策略暂停
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/campPause")
    public ModelAndView campPause(HttpServletRequest request,HttpServletResponse response)
            throws Exception {
        String msg = "";
        String logDesc = "";
        int status = 200;
        try {
            /* 获取session信息 */
            String campsegId =  request.getParameter("campsegId") != null ? request.getParameter("campsegId").toString() : null;
            String pauseComment = request.getParameter("pauseComment") != null ? request.getParameter("pauseComment").toString() : null;
            
            if (campsegId != null) {
                List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
                mpmCampSegInfoService.updateCampStat(campsegId,MpmCONST.MPM_CAMPSEG_STAT_PAUSE);
                
                for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
                    mpmCampSegInfoService.updateCampStat(mtlCampSeginfo.getCampId(), MpmCONST.MPM_CAMPSEG_STAT_PAUSE);
                }
                //保存暂停原因
                mpmCampSegInfoService.updatMtlCampSeginfoPauseComment(campsegId,pauseComment);
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
     * 策略重启
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/campRestart")
    public ModelAndView campRestart(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String msg = "";
        int status = 200;
        try {

            String campsegId = request.getParameter("campsegId") != null ? request
                    .getParameter("campsegId") : null;

            if (campsegId != null) {
                List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
                mpmCampSegInfoService.updateCampStat(campsegId, MpmCONST.MPM_CAMPSEG_STAT_DDCG);
                for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
                    mpmCampSegInfoService.updateCampStat(mtlCampSeginfo.getCampId(),MpmCONST.MPM_CAMPSEG_STAT_DDCG);
                }

            }

        } catch (Exception e) {
            log.error("", e);
            msg = "重启失败，原因：" + e.getMessage();
            status = 201;
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
    @RequestMapping("/searchExecContent")
    public ModelAndView searchExecContent( HttpServletRequest request,HttpServletResponse response) throws Exception {
        int status = 200;
        String msg = "";
        JSONArray jsonArrayEnd = null;
        try {
            String campsegId = request.getParameter("campsegId") != null ? request
                    .getParameter("campsegId") : null;
            List<McdCampDef> mtlCampSeginfoList = mpmCampSegInfoService.getChildCampSeginfo(campsegId);
            List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
            // JSONObject dataJson = new JSONObject();
            for (McdCampDef mtlCampSeginfo : mtlCampSeginfoList) {
                JSONObject dataJson = new JSONObject();
//                dataJson.put("campsegName","规则" + mtlCampSeginfo.getCampsegNo());
                dataJson.put("campsegId", mtlCampSeginfo.getCampId());
                // 获取有营销用语的渠道的营销用语
                List execContentList = mpmCampSegInfoService.getExecContentList(mtlCampSeginfo.getCampId());
                JSONArray jsonArray = JSONArray.fromObject(execContentList);
                dataJson.put("channelExecContent", jsonArray);
                // 获取营销用语变量
                List execContentVariableList = mpmCampSegInfoService.getExecContentVariableList(mtlCampSeginfo.getCampId());
                JSONArray execContentVariableJsonArray = JSONArray.fromObject(execContentVariableList);
                dataJson.put("execContentVariableJsonArray",
                        execContentVariableJsonArray);
                jsonObjectList.add(dataJson);
            }
            jsonArrayEnd = JSONArray.fromObject(jsonObjectList);

        } catch (Exception e) {
            status = 201;
            e.printStackTrace();
            msg = e.getMessage();
        } finally {

            JSONObject dataJson = new JSONObject();
            dataJson.put("status", "200");
            if (!"200".equals(status)) {
                dataJson.put("result", msg);
            }
            dataJson.put("data", jsonArrayEnd);
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
     * 保存营销用语
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveExecContent")
    public ModelAndView saveExecContent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int status = 200;
        String msg = "";
        // 是否有短信渠道
        boolean isSMS = false;
        int sms = MpmCONST.CHANNEL_TYPE_SMS_INT;
        try {
            String json = request.getParameter("json") != null ? request
                    .getParameter("json") : null;
            JSONObject jsonObjectP = (JSONObject) JSONObject.fromObject(json);
            String campsegPId = jsonObjectP.get("campsegPId").toString();
            String childCampsegString = jsonObjectP.get("childCampseg")
                    .toString();
            JSONArray jsonArray = JSONArray.fromObject(childCampsegString);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String campsegId = jsonObject.get("campsegId").toString();
                String channelexecContentList = jsonObject.get(
                        "channelexecContentList").toString();
                JSONArray channelexecContentArray = JSONArray
                        .fromObject(channelexecContentList);
                for (int j = 0; j < channelexecContentArray.size(); j++) {
                    JSONObject channelexecContent = (JSONObject) channelexecContentArray
                            .get(j);
                    String channelId = channelexecContent.get("channelId")
                            .toString();
                    String execContent = channelexecContent.get("execContent")
                            .toString();
                    //是否有营销用语
                    String ifHasVariate = channelexecContent.get("ifHasVariate")
                            .toString();
                    
                    mpmCampSegInfoService.saveExecContent(campsegId, channelId, execContent,ifHasVariate);
                    if (Integer.parseInt(channelId) == sms) {
                        isSMS = true;
                        String result = mtlSmsSendTestTask.mtlSmsSendTest(campsegId, channelId);
                    }
                }
                if (isSMS) {
                    mtlSmsSendTestTask.updateCampsegInfoState(campsegId,MpmCONST.MPM_CAMPSEG_STAT_HDCS);
                }
            }
            if (isSMS) {
                mtlSmsSendTestTask.updateCampsegInfoState(campsegPId, MpmCONST.MPM_CAMPSEG_STAT_HDCS);
            }

            msg = "保存营销用语成功！";
        } catch (Exception e) {
            status = 201;
            e.printStackTrace();
            msg = e.getMessage();
        } finally {

            JSONObject dataJson = new JSONObject();
            dataJson.put("status", "200");
            if (!"200".equals(status)) {
                dataJson.put("result", msg);
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
        }

        return null;
    }


}
