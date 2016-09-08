package com.asiainfo.biapp.mcd.quota.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import com.asiainfo.biapp.mcd.common.util.CommonUtil;
import com.asiainfo.biapp.mcd.common.util.DateTool;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;
import com.asiainfo.biapp.mcd.quota.service.QuotaConfigCityDayService;
import com.asiainfo.biapp.mcd.quota.service.QuotaConfigDeptDayService;
import com.asiainfo.biapp.mcd.quota.service.QuotaConfigDeptMothService;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.quota.vo.CityQuotaStatisDay;
import com.asiainfo.biapp.mcd.quota.vo.QuotaConfigDeptDay;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biframe.utils.string.StringUtil;

import net.sf.json.JSONObject;

@RequestMapping("/dayQuota")
public class DeptDayQuotaController  extends BaseMultiActionController {

    //private static final Logger log = LogManager.getLogger();
    private static final Logger log = LogManager.getLogger(DeptDayQuotaController.class);

    @Resource(name = "quotaConfigCityDayService")
    private QuotaConfigCityDayService quotaConfigCityDayService;
    
    @Resource(name = "quotaConfigDeptDayService")
    private QuotaConfigDeptDayService quotaConfigDeptDayService;
    
    @Resource(name = "quotaConfigDeptMothService")
    private QuotaConfigDeptMothService quotaConfigDeptMothService;
    
    /**
     * 调整单日配额
     * @param actionMapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView editDayQuota(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        String deptId=request.getParameter("deptId");
        String dataDate = null;
        String showDate = request.getParameter("dataDate");
        String cityId = getUser(request,response).getCityId();
        
        CityQuotaStatisDay cityDayQuota = quotaConfigCityDayService.getCityQuotaStatisDay(cityId);
        
        if (StringUtil.isEmpty(showDate)) {
            dataDate = QuotaUtils.getDayMonth("yyyyMM");
        }
        showDate = QuotaUtils.getFullDate();
        
        request.setAttribute("monthDate", dataDate);
        request.setAttribute("showDate", showDate);
        request.setAttribute("deptId", deptId);
        request.setAttribute("cityDayNum",cityDayQuota.getDayQuotaNum());
        ModelAndView model = new ModelAndView("/mcd/pages/execute/editdaysquota");
        model.addObject("monthDate", dataDate);
        model.addObject("showDate", showDate);
        model.addObject("deptId", deptId);
        model.addObject("cityDayNum",cityDayQuota.getDayQuotaNum());
        return model;
    }
    /**
     * 查看整月每日配额
     * @param actionMapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/viewDayQuota")
    public void viewDayQuota(HttpServletRequest request,HttpServletResponse response) throws Exception {

        String cityId = this.getUser(request, response).getCityId();
        String dataDate = "";
        String showDate = request.getParameter("dataDate");
        if (StringUtil.isEmpty(showDate)) {
            dataDate = QuotaUtils.getDayMonth("yyyyMM");
            showDate = dataDate;
        } else {// 将“yyyy年MM月 ”格式转化成“yyyyMM”格式
            dataDate = showDate;
        }
        Map maps= new HashMap();
        //add by zhuyq3 2015-10-30 17:15:01
//      dataDate = "201510";
        List<Map<String, Object>> list = quotaConfigCityDayService.queryCityDayQuotas(cityId, dataDate);
//      List<Object> daysConfig = new ArrayList<Object>();
        Map<Integer, Map<String, String>> result = new LinkedHashMap<Integer, Map<String, String>>();
        int days = QuotaUtils.getMonDays(dataDate);
        List<String> daysList = CommonUtil.serializeToList(days);
        int sumSurplus = 0;
        int cityMonthConfig = 0;//地市月配额
        cityMonthConfig = quotaConfigDeptMothService.getCityMonthQuota(cityId);
        int sumQuota = 0;
        if (list.isEmpty()) {
            for (int i = 0; i < days; i++) {
                Map<String, String> t1 = new LinkedHashMap<String, String>();
                t1.put("_quota", 0 + "");
                t1.put("_altered", 0 + "");
                t1.put("_surplus", 0 + "");
                result.put(i, t1);
            }
        } else {
            int newday =  Integer.parseInt(DateTool.getStringDate(new Date(), "d"));
            for (Map<String, Object> map : list) {
                Iterator<String> ite = map.keySet().iterator();
                String _val = "DAY_QUOTA_NUM";
                String _alt_key = "ALTERED";
                String _surplus_key = "SURPLUS";
                String tmp = "";
                int date = 0;
                while (ite.hasNext()) {
                    String _key = ite.next();
                    


                    if ("DATA_DATE".equals(_key)) {
                        tmp = map.get(_key).toString().substring(dataDate.length());
                        if(!"".equals(tmp) && tmp != null){
                            date = Integer.parseInt(tmp);
                        }
                        if (!daysList.contains(tmp)) {
                            Map<String, String> t1 = new LinkedHashMap<String, String>();
                            t1.put("_quota", 0 + "");
                            t1.put("_altered", 0 + "");
                            t1.put("_surplus", 0 + "");
                            result.put(Integer.parseInt(tmp), t1);
                        } else {
                            Map<String, String> t1 = new LinkedHashMap<String, String>();
                            t1.put("_quota", String.valueOf(map.get(_val)));
                            sumQuota = sumQuota + Integer.parseInt(String.valueOf(map.get(_val)));
                            t1.put("_altered", String.valueOf(map.get(_alt_key)));
                            if(newday > date){
                                t1.put("_surplus", String.valueOf(map.get(_surplus_key)));
                            }else{
                                t1.put("_surplus", "0"); 
                            }
                            result.put(Integer.parseInt(tmp), t1);
                        }

                    }

                }
                String surplus =  String.valueOf(map.get(_surplus_key));
                
                //总剩余= 本月今日之前每日剩余之和
                if(!"".equals(surplus) && surplus != null && date < newday){
                    sumSurplus = sumSurplus + Integer.parseInt(surplus);
                }
                
            }
            sumSurplus = cityMonthConfig + sumSurplus - sumQuota ; 
            log.info(sumSurplus);
        }
        String deptId=request.getParameter("deptId");
        
        maps.put("daysConfig", result);
        maps.put("monthDate", dataDate);
        maps.put("showDate", showDate);
        maps.put("deptId", deptId);
        maps.put("sumSurplus",sumSurplus);
        maps.put("newDay",DateTool.getStringDate(new Date(), "d"));
        maps.put("newDate",DateTool.getStringDate(new Date(), "yyyyMM"));
        
        JSONObject dataJson = new JSONObject();
        dataJson.put("status", "200");
        dataJson.put("data", JmsJsonUtil.obj2Json(maps));
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
     * 保存单日配额
     * @param actionMapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/saveQuota4Day")
    public void saveQuota4Day(HttpServletRequest request,HttpServletResponse response) throws Exception {
        //date="20160627";

        String cityId=this.getUser(request, response).getCityId();
        String day = request.getParameter("day");
        String month = request.getParameter("month");
        //月剩余配额
        String quotaM= request.getParameter("monthQuota");
        //月配额
        String monthQuotaSum = request.getParameter("monthQuotaSum");
        JSONObject result = new JSONObject();
        int rows = 0;
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(day); 
            
            for (Iterator iter = jsonObject.keys(); iter.hasNext();) { //先遍历整个 people 对象  
                String key = (String)iter.next(); 
                String value = jsonObject.getString(key);
                org.json.JSONObject jsonObjectValue = new org.json.JSONObject(value);  
                
                
                
                String quotaD = jsonObjectValue.getString("dayQuota");
                String quota = Double.parseDouble(quotaD)+"";
                String date = "";
    
                if(key.length() == 1){
                    date = month+"0"+key;
                }else{
                    date = month + key;
                }
    
    
                //不做配额限制
                int row = quotaConfigCityDayService.saveDayQuotas(cityId, date, quota,quotaM);
                result.put("status", "200");
                rows =rows + row;
     
            } 
        result.put("count", rows);
        this.outJson(response, result);
     } catch (Exception e) {
         e.printStackTrace();
         result.put("status", "201");
         this.outJson(response, result);
     }

    }
    
    @Deprecated
    public void saveDays(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        Boolean isSuccess=false;
        JSONObject result = new JSONObject();

        String cityid = this.getUser(request, response).getCityId();
        String dataDate;
        String showDate = request.getParameter("dataDate");
        if (StringUtil.isEmpty(showDate)) {
            dataDate = QuotaUtils.getDayMonth("yyyyMM");
        }else{//将“yyyy年MM月 ”格式转化成“yyyyMM”格式
        	
            dataDate=this.getActualDate(showDate);
        }

        String deptId=request.getParameter("deptId");
        //String deptId =this.getQuotaConfigDeptDayService().getDeptId(this.userId);
        
        String daysQuotaStr = request.getParameter("daysQuota");
        String[] daysQuota = daysQuotaStr.split("#");
        List<QuotaConfigDeptDay> list = this.createMonthQuota(daysQuota, cityid, deptId, dataDate);
        isSuccess=this.quotaConfigDeptDayService.batchUpdateDaysQuota(cityid, deptId, dataDate, list);
        result.put("result", isSuccess);
        this.outJson(response, result);
    }
    
    private List<QuotaConfigDeptDay> createMonthQuota(String[] daysQuota,String cityId,String deptId,String month){
        List<QuotaConfigDeptDay> days=new ArrayList<QuotaConfigDeptDay>();
        for(int i=0;i<daysQuota.length;i++){
            QuotaConfigDeptDay temp = new QuotaConfigDeptDay();
            temp.setCityId(cityId);
            temp.setDataDateM(month);
            temp.setDeptId(deptId);
            int tempday=i+1;
            if(tempday<10){
                temp.setDataDate(month+"0"+tempday);
            }else{
                temp.setDataDate(month+tempday);
            }
            temp.setDayQuotaNum(Integer.parseInt(daysQuota[i]));

            days.add(temp);
        }
        return days;
        
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
	 
	//将“yyyy年MM月 ”格式转化成“yyyyMM”格式。
	private String getActualDate(String monthDate) {
		String year = monthDate.substring(0, 4);
		String month = monthDate.substring(5, monthDate.length() - 1);
		String str = year + month;
		return str;
	}
}
