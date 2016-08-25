package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.PrintWriter;
import java.util.List;

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
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;

import net.sf.json.JSONObject;
@RequestMapping("/tactics/campSegSearch")
public class CampSegSearchController extends BaseMultiActionController {
    private static Logger log = LogManager.getLogger();
    
    @Resource(name="mpmUserPrivilegeService")
    private IMpmUserPrivilegeService mpmUserPrivilegeService; 
    @Resource(name="mpmCampSegInfoService")
    private IMpmCampSegInfoService mpmCampSegInfoService;
    
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

                // String clickQueryFlag=request.getParameter("clickQueryFlag");
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

}
