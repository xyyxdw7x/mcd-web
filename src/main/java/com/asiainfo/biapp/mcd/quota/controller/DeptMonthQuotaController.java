package com.asiainfo.biapp.mcd.quota.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.util.DateTool;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;
import com.asiainfo.biapp.mcd.quota.dao.IMtlSysCampConfigDao;
import com.asiainfo.biapp.mcd.quota.service.IQuotaConfigDeptMothService;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.quota.vo.DeptMonQuotaDefault;
import com.asiainfo.biapp.mcd.quota.vo.DeptsQuotaStatistics;
import com.asiainfo.biapp.mcd.exception.MpmException;
import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/monthQuota")
public class DeptMonthQuotaController  extends BaseMultiActionController {
    private static final Logger log = LogManager.getLogger();

    @Resource(name = "quotaConfigDeptMothService")
    private IQuotaConfigDeptMothService quotaConfigDeptMothService;
    
    @Resource(name = "sysCampConfigDao")
    private IMtlSysCampConfigDao sysCampConfigDao;
    
    /**
     * 查询月配额
     * @param actionMapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryDeptsConfigMonth")
    public void queryDeptsConfigMonth(HttpServletRequest request,HttpServletResponse response) throws Exception {

        String cityid = this.getUser(request, response).getCityId();
        
        String dataDate;
        String showDate = request.getParameter("dataDate");
        boolean couldAdjust = true;
        if (StringUtils.isEmpty(showDate)) {
            dataDate = QuotaUtils.getDayMonth("yyyyMM");
            showDate = dataDate;
        } else {
            dataDate = showDate;
            try {
                couldAdjust = Integer.parseInt(QuotaUtils.getDayMonth("yyyyMM")) <= Integer.parseInt(showDate);
            } catch (Exception e) {
                e.printStackTrace();
                couldAdjust = false;
            }
        }

        List<DeptsQuotaStatistics> deptMonConfStati = null;
        int cityMonthConfig = 0;//地市月配额

        try {
            deptMonConfStati = quotaConfigDeptMothService.getDeptsQuotaStatistics(cityid, dataDate);
            cityMonthConfig = quotaConfigDeptMothService.getCityMonthQuota(cityid);
        } catch (Exception e) {
            log.error("查询月配额或者月使用额出错");
            e.printStackTrace();
        }
        Map<String, Object> map= new HashMap<String, Object>();
        //地市整体月配额
        if("999".equals(cityid)){
            //request.setAttribute("allowances", "无限制");
            map.put("allowances", "无限制");
        }else{
            //request.setAttribute("allowances", cityMonthConfig);
            map.put("allowances", cityMonthConfig);
        }
        map.put("deptMonConfStati", deptMonConfStati);
        map.put("showDate", showDate);
        map.put("couldAdjust", couldAdjust);
        map.put("newDate",DateTool.getStringDate(new Date(), "yyyyMM"));
        //add by zhuyq3 2015-10-29 15:14:57
        String key = "SMS_CITY_NUM";

        Object obj = sysCampConfigDao.getProperety(key);
        try {
            //request.setAttribute("smsCityNum", Integer.parseInt(String.valueOf(obj)));
            map.put("smsCityNum", Integer.parseInt(String.valueOf(obj)));
        } catch (Exception e) {
            e.printStackTrace();
            //request.setAttribute("smsCityNum", 0);
            map.put("smsCityNum", 0);
        }
        //end of zhuyq3
        
        
        JSONObject dataJson = new JSONObject();
        dataJson.put("status", "200");
        dataJson.put("data", JmsJsonUtil.obj2Json(map));
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("progma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.print(dataJson);
        out.flush();
        out.close();

    }
    /**
     * 保存月配额
     * @param actionMapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/batchModifyMonConf")
    public void batchModifyMonConf(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        String renFlag="";
        JSONObject dataJson = new JSONObject();

        String cityid = this.getUser(request, response).getCityId();
        String dataDate = request.getParameter("dataDate");
        if (StringUtils.isEmpty(dataDate)) {
            dataDate = QuotaUtils.getDayMonth("yyyyMM");
        }
        String jsonStr = request.getParameter("beans");
        
        
        @SuppressWarnings("unchecked")
        List<DeptsQuotaStatistics> list = QuotaUtils.JsonStr2List(jsonStr,DeptsQuotaStatistics.class);
        
        renFlag=quotaConfigDeptMothService.saveOrUpdate(list, cityid,dataDate);

        Map<String, String> map = new HashMap<String, String>();
        map.put("result", renFlag);

        dataJson.put("status", "200");
        dataJson.put("data", JmsJsonUtil.obj2Json(map));

        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("progma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.print(dataJson);
        out.flush();
        out.close();
    }
    
    public void saveDefault(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        boolean flag=false;
        JSONObject result = new JSONObject();
        String cityid = this.getUser(request, response).getCityId();
        
        String jsonStr = request.getParameter("beans");
        @SuppressWarnings("unchecked")
        List<DeptMonQuotaDefault> list = QuotaUtils.JsonStr2List(jsonStr,DeptMonQuotaDefault.class);
        this.ecape(list, cityid);
        flag=quotaConfigDeptMothService.saveDefault(list, cityid);
        
        if(flag){
            result.put("result", "1");
        }else{
            result.put("result", "0");
        }
        this.outJson(response, result);
        
    }
    
    private void ecape(List<DeptMonQuotaDefault> list,String cityID){
        for(int i=0;i<list.size();i++){
            list.get(i).setCityId(cityID);
        }
    }
    
	 protected void outJson(HttpServletResponse response, Object json) throws MpmException {
			log.debug("output json to response:{}", json);
			response.setContentType("text/json; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");

			// 璁剧疆娴忚鍣ㄤ笉瑕佺紦瀛 
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			try {
				response.getWriter().print(json == null ? "{}" : json.toString());
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				log.error("--out put json error", e);
				throw new MpmException("--out put json error", e);
			}
	 }
}
