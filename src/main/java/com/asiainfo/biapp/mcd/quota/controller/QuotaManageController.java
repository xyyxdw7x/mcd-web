package com.asiainfo.biapp.mcd.quota.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.framework.core.AppConfigService;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.util.CommonUtil;
import com.asiainfo.biapp.mcd.common.util.DateTool;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;
import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.quota.service.ICityDayQuotaServiceImp;
import com.asiainfo.biapp.mcd.quota.service.IDeptMothQuotaService;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.quota.vo.DeptMonthQuota;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/quotaManage")
public class QuotaManageController  extends BaseMultiActionController {
    private static final Logger log = LogManager.getLogger();

    @Resource(name = "quotaConfigDeptMothService")
    private IDeptMothQuotaService quotaConfigDeptMothService;
    @Resource(name = "quotaConfigCityDayService")
    private ICityDayQuotaServiceImp quotaConfigCityDayService;

    
    /**
     * 查询当前人员所在地市的所有科室的月配额
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

        List<DeptMonthQuota> deptMonConfStati = null;
        int cityMonthConfig = 0;//地市月配额

        try {
            deptMonConfStati = quotaConfigDeptMothService.getCityDeptsMonthQuota(cityid, dataDate);
            cityMonthConfig = quotaConfigDeptMothService.getCityMonthQuota(cityid);
        } catch (Exception e) {
            log.error("查询月配额或者月使用额出错",e);
            e.printStackTrace();
        }
        Map<String, Object> map= new HashMap<String, Object>();
        //地市整体月配额
       map.put("allowances", cityMonthConfig);
        
        map.put("deptMonConfStati", deptMonConfStati);
        map.put("showDate", showDate);
        map.put("couldAdjust", couldAdjust);
        map.put("newDate",DateTool.getStringDate(new Date(), "yyyyMM"));
        //add by zhuyq3 2015-10-29 15:14:57
        //Object obj = sysCampConfigDao.getProperety("SMS_CITY_NUM");
        
        try {
            map.put("smsCityNum", AppConfigService.getProperty("SMS_CITY_NUM"));
        } catch (Exception e) {
            e.printStackTrace();
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
     * 批量保存科室月配额
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/batchModifyMonConf")
    public void batchModifyMonConf(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String renFlag="";
        JSONObject dataJson = new JSONObject();
        String cityid = this.getUser(request, response).getCityId();
        String dataDate = request.getParameter("dataDate");
        if (StringUtils.isEmpty(dataDate)) {
            dataDate = QuotaUtils.getDayMonth("yyyyMM");
        }
        String jsonStr = request.getParameter("beans");
        
        @SuppressWarnings("unchecked")
        List<DeptMonthQuota> list = QuotaUtils.JsonStr2List(jsonStr,DeptMonthQuota.class);
        
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
    
    /**
     * 查看地市当前月的所有日配额（某个月的地市日配额列表）-----配额管理界面的右边部分
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
        if (StringUtils.isEmpty(showDate)) {
            dataDate = QuotaUtils.getDayMonth("yyyyMM");
            showDate = dataDate;
        } else {// 将“yyyy年MM月 ”格式转化成“yyyyMM”格式
            dataDate = showDate;
        }
        Map<String, Object> maps= new HashMap<String, Object>();
        //add by zhuyq3 2015-10-30 17:15:01
        List<Map<String, Object>> list = quotaConfigCityDayService.queryCityDayQuotas(cityId, dataDate);
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
     * 批量保存地市日配额
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/saveCityDaysQuot")
    public void saveCityDaysQuot(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String cityId=this.getUser(request, response).getCityId();
        String day = request.getParameter("day");
        String month = request.getParameter("month");
        //月剩余配额
        String quotaM= request.getParameter("monthQuota");
        //月配额
        JSONObject result = new JSONObject();
        int rows = 0;
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(day); 
            
            for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) { //先遍历整个 people 对象  
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
    
    public void saveDefault(HttpServletRequest request,HttpServletResponse response) throws Exception {
        boolean flag=false;
        JSONObject result = new JSONObject();
        String cityid = this.getUser(request, response).getCityId();
        
        String jsonStr = request.getParameter("beans");
        @SuppressWarnings("unchecked")
        List<DeptMonthQuota> list = QuotaUtils.JsonStr2List(jsonStr,DeptMonthQuota.class);
        this.ecape(list, cityid);
        flag=quotaConfigDeptMothService.saveDefault(list, cityid);
        
        if(flag){
            result.put("result", "1");
        }else{
            result.put("result", "0");
        }
        this.outJson(response, result);
        
    }
    
    private void ecape(List<DeptMonthQuota> list,String cityID){
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
