package com.asiainfo.biapp.mcd.kpi.controller;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.home.echartbean.*;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.home.echartbean.ChartInfoBase;
import com.asiainfo.biapp.mcd.home.echartbean.DataGridInfoBase;
import com.asiainfo.biapp.mcd.home.echartbean.MsmConstants;
import com.asiainfo.biapp.mcd.home.echartbean.ParseUtil;
import com.asiainfo.biapp.mcd.kpi.service.KpiService;
import com.asiainfo.biapp.mcd.kpi.service.MsmChartService;
import com.asiainfo.biapp.mcd.kpi.util.KpiUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Title: 
 * Description: 营销评估
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author zhuml 2015-12-15 
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/action/kpi")
public class KpiController  extends BaseMultiActionController{
	private static final Logger log = LogManager.getLogger(KpiController.class);
	
	@Resource(name = "demoChartService")
	private MsmChartService demoChartService;
	
	@Resource(name = "dayUseEffectService")
	private MsmChartService dayUseEffectService;
	
	@Resource(name = "monthsHisEffectService")
	private MsmChartService monthsHisEffectService;
	
	@Resource(name = "succesChanneltDisService")
	private MsmChartService succesChanneltDisService;
	
	@Resource(name = "kpiService")
	private KpiService kpiService;
	
	@Resource(name = "channelExec1Service")
	private MsmChartService channelExec1Service;
	
	@Resource(name = "channelExec2Service")
	private MsmChartService channelExec2Service;
	
	@Resource(name = "channelExec3Service")
	private MsmChartService channelExec3Service;
	
	@ResponseBody
	@RequestMapping
	public String queryChart(HttpServletRequest request,
			HttpServletResponse response){
		String jsonStr="";
		try {
			String paramJson  = request.getParameter("paramJson"); // 页面参数 json 形式
			String divId  = request.getParameter("divId");
			Map<String, Object> paramsMap = paramsProcess(paramJson);
			paramsMap.put("divId", divId);
			String cityId =(String) paramsMap.get("cityId");
			if(Strings.isNullOrEmpty(cityId) || "null".equals(cityId)){
				paramsMap.put("cityId", this.getUser(request, response).getCityId());
			}
			String serviceName = (String) paramsMap.get("ServiceName");
			List<Map<String, String>> dataList;
			dataList = this.getInstance(serviceName).getDataInfo(paramsMap);
			//向具体的实现类提供方法接口
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if(null != dataList && dataList.size() > 0){
				resultMap = this.getInstance(serviceName).executeDataInfo(dataList, paramsMap);
			}
			
			if(resultMap.containsKey(MsmConstants.CHART_INFO_BASE)){
				ChartInfoBase chartInfoBase= (ChartInfoBase)resultMap.get(MsmConstants.CHART_INFO_BASE);
				jsonStr = ParseUtil.parseOption(chartInfoBase);
			}else if(resultMap.containsKey(MsmConstants.GRID_INFO_BASE)){
				DataGridInfoBase dataGridInfoBase= (DataGridInfoBase)resultMap.get(MsmConstants.GRID_INFO_BASE);
				jsonStr = ParseUtil.parseDataGrid(dataGridInfoBase);
			}else{
				jsonStr="数据不存在";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonStr;
	}
	
	private MsmChartService getInstance(String serviceName){
		MsmChartService service = null;
		
		if("demoChartService".equals(serviceName)){
			return demoChartService;
		}		
		
		if("dayUseEffectService".equals(serviceName)){
			return dayUseEffectService;
		}
		
		if("monthsHisEffectService".equals(serviceName)){
			return monthsHisEffectService;
		}
		
		if("succesChanneltDisService".equals(serviceName)){
			return succesChanneltDisService;
		}
		
		if("channelExec1Service".equals(serviceName)){
			return channelExec1Service;
		}
		
		if("channelExec2Service".equals(serviceName)){
			return channelExec2Service;
		}
		
		if("channelExec3Service".equals(serviceName)){
			return channelExec3Service;
		}
		
		return service;
	}
	
	/** 
	 * 处理前台传来的参数
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> paramsProcess(String paramJson ){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap = (Map<String, Object>) JSONObject.fromObject(paramJson);
		return paramsMap;
	}
	
	/**
	 * 日使用效果标题数据
	 * @param request
	 * @param response
	 */
	public String dayUseEffectTitle(HttpServletRequest request,
			HttpServletResponse response){
		Map<String,String> map = Maps.newHashMap();
		map.put("targetCustomer", "7654321");
		map.put("successCustomer", "1234567");
		map.put("successRate", "0.6");
		String str = JsonUtil.toJSONString(map);
		return str;
	}
	
	/**
	 * 总览月使用效果表格
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getMonthUseEffectList(HttpServletRequest request,
			HttpServletResponse response){
		String date = request.getParameter("date");
		date = date.replaceAll("-", "");
		String cityId = this.getUser(request, response).getCityId();
		List<Map<String, Object>> list = kpiService.getMonthUseEffectList(date, cityId);
		List reList = Lists.newArrayList();
		List td1 = Lists.newArrayList();
		td1.add("指标");
		List td2 = Lists.newArrayList();
		td2.add("目标用户");
		List td3 = Lists.newArrayList();
		td3.add("营销用户");
		List td4 = Lists.newArrayList();
		td4.add("营销成功用户");
		List td5 = Lists.newArrayList();
		td5.add("营销成功率");
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String COUNTY_ID = map.get("AREA_NAME").toString();
			td1.add(COUNTY_ID);
			td2.add(KpiUtil.parseMoney(map.get("USER_NUM").toString(),""));
			td3.add(KpiUtil.parseMoney(map.get("VAL_CAMP").toString(),""));
			if(map.get("VAL_SUC")==null){
				td4.add("0");
			}else{
				td4.add(KpiUtil.parseMoney(map.get("VAL_SUC").toString(),""));
			}
			if(map.get("RATE")==null){
				td5.add("0");
			}else{
				td5.add(KpiUtil.parsePercent(map.get("RATE").toString()));
			}
			
		}
		reList.add(td1);
		reList.add(td2);
		reList.add(td3);
		reList.add(td4);
		reList.add(td5);
		
		return JsonUtil.toJSONString(reList);
	}
	
	/** 
	 * 营销政策效果tabList查询
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping
	public JSONObject getPolicyEffectList(HttpServletRequest request,
			HttpServletResponse response){
		JSONObject dataJson  = new JSONObject();
		String dateType = request.getParameter("dateType");
		String date = request.getParameter("date");
		String reDate =  date;
		date = date.replaceAll("-", "");
		String countryId = request.getParameter("cityId");
		String cityId = this.getUser(request, response).getCityId();
		if(countryId.length()==3){
			cityId=countryId;
			countryId = "-1";
		}
		String channelId = request.getParameter("channelId");
		if("999".equals(channelId)){
			channelId="-1";
		}
		String tactics = request.getParameter("tactics");
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		Pager pager = new Pager();
		pager.setPageSize(McdCONST.PAGE_SIZE);
		pager.setPageNum(Integer.parseInt(pageNum)); // 当前页
		if (pageNum != null) {
			pager.setPageFlag("G");
		}		
		List<Map<String, Object>> list = kpiService.getPolicyEffectList(dateType, date, cityId,countryId, channelId ,tactics, pager);
		pager.getTotalPage();
		pager = pager.pagerFlip();
		pager.setResult(list);
		
//		Map map = Maps.newHashMap();
//		map.put("data", pager);
//		map.put("status", 200);
//		map.put("date", reDate);
//		map.put("dateType", dateType);
		
		
		dataJson.put("data", pager);
		dataJson.put("status", 200);
		dataJson.put("date", reDate);
		dataJson.put("dateType", dateType);
		
		//return JsonUtil.toJSONString(map);
		
		return dataJson;
	}
	/**
	 * 营销政策效果 政策详情页 政策信息
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getPolicyInfo(HttpServletRequest request,
			HttpServletResponse response){
		String planId = request.getParameter("planId");
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		Pager pager = new Pager();
		pager.setPageSize(8);
		pager.setPageNum(Integer.parseInt(pageNum)); // 当前页
		if (pageNum != null) {
			pager.setPageFlag("G");
		}		
		String cityId = this.getUser(request, response).getCityId();
		List list = kpiService.getPolicyInfo(planId,cityId,pager);
		pager.getTotalPage();
		pager = pager.pagerFlip();
		pager.setResult(list);
		Map map = Maps.newHashMap();
		map.put("data", pager);
		map.put("status", 200);
		return JsonUtil.toJSONString(map);
	}
	
	/**
	 * 营销渠道效果tabList查询
	 * @param request
	 * @param response
	 */
	@RequestMapping
	@ResponseBody
	public JSONObject getChannelEffectList(HttpServletRequest request,
			HttpServletResponse response){
		JSONObject dataJson = new JSONObject(); 
		String dateType = request.getParameter("dateType");
		String date = request.getParameter("date");
		date = date.replaceAll("-", "");
		String cityId = this.getUser(request, response).getCityId();

		String tactics = request.getParameter("tactics");

		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		Pager pager = new Pager();
		pager.setPageSize(McdCONST.PAGE_SIZE);
		pager.setPageNum(Integer.parseInt(pageNum)); // 当前页
		if (pageNum != null) {
			pager.setPageFlag("G");
		}		
		List<Map<String, Object>> list = kpiService.getChannelEffectList(dateType, date,cityId,tactics, pager);
		pager.getTotalPage();
		pager = pager.pagerFlip();
		pager.setResult(list);
		
		//Map map = Maps.newHashMap();
		dataJson.put("data", pager);
		dataJson.put("status", 200);
		return dataJson;
	}

	/**
	 * 一线人员效果tabList查询
	 * @param request
	 * @param response
	 */
	@RequestMapping
	@ResponseBody
	public JSONObject getFrontLineEffectList(HttpServletRequest request,
			HttpServletResponse response){
		JSONObject dataJson = new JSONObject();
		String dateType = request.getParameter("dateType");
		String date = request.getParameter("date");
		String countryId = request.getParameter("cityId"); 
		String reDate = date;
		date = date.replaceAll("-", "");
		String cityId = this.getUser(request, response).getCityId();
		if(countryId.length()==3){
			cityId=countryId;
			countryId = "-1";
		}

		String channelId = request.getParameter("channelId");
		String org = request.getParameter("org");
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		Pager pager = new Pager();
		pager.setPageSize(McdCONST.PAGE_SIZE);
		pager.setPageNum(Integer.parseInt(pageNum)); // 当前页
		if (pageNum != null) {
			pager.setPageFlag("G");
		}		
		List<Map<String, Object>> list = kpiService.getFrontLineEffectList(dateType, date, cityId, countryId,channelId, org ,pager);
		pager.getTotalPage();
		pager = pager.pagerFlip();
		pager.setResult(list);
		
		//Map map = Maps.newHashMap();
		dataJson.put("data", pager);
		dataJson.put("status", 200);
		dataJson.put("date", reDate);
		dataJson.put("dateType", dateType);
		return dataJson;
	}
	/**
	 * 一线人员效果 详情页查询
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getFrontLineEffectDetailList(HttpServletRequest request,
			HttpServletResponse response){
		String dateType = request.getParameter("dateType");
		String date = request.getParameter("date");
		date = date.replaceAll("-", "");
		String channelId = request.getParameter("channelId");
		String orgId = request.getParameter("orgId");
		String userlike = request.getParameter("userlike");
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		Pager pager = new Pager();
		pager.setPageSize(McdCONST.PAGE_SIZE);
		pager.setPageNum(Integer.parseInt(pageNum)); // 当前页
		if (pageNum != null) {
			pager.setPageFlag("G");
		}		

		List list = kpiService.getFrontLineEffectDetailList( dateType, date, orgId, channelId, userlike, pager);
		pager.getTotalPage();
		pager = pager.pagerFlip();
		pager.setResult(list);
		
		Map map = Maps.newHashMap();
		map.put("data", pager);
		map.put("status", 200);
		return JsonUtil.toJSONString(map);
	}
	/**
	 * 营销政策效果列表页面 list  city
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject getCityInfos(HttpServletRequest request,
			HttpServletResponse response){
		JSONObject dataJson = new JSONObject(); 
		List relist = Lists.newArrayList();
		String cityId = this.getUser(request, response).getCityId();
		List list = kpiService.getChildCityInfosByPid(cityId);
		Map mapAll = Maps.newHashMap();
		mapAll.put("ID", KpiUtil.CITY_ALL);
		mapAll.put("NAME", "全部");
		relist.add(mapAll);
		relist.addAll(list);
		log.info("return cityinfos size" + relist.size());
		//Map map = Maps.newHashMap();
		dataJson.put("data", relist);
		dataJson.put("status", 200);
		return dataJson;
	}
	/**
	 * 营销政策效果列表页面 list channel
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping
	public JSONObject getChannelInfos(HttpServletRequest request,
			HttpServletResponse response){
		JSONObject dataJson = new JSONObject();
		
		List<Map<String,Object>> relist = Lists.newArrayList();
		String cityId = this.getUser(request, response).getCityId();
		List<Map<String, Object>> list = kpiService.getChannelInfos();
		if(!cityId.equals("577")){
			for(int i = 0 ;i<list.size(); i++){
				Map<String, Object> map = (Map<String, Object>)list.get(i);
				String channelId = map.get("ID").toString();
				if("912".equals(channelId)){
					list.remove(i);
				}
			}
		}
		Map<String,Object> mapAll = Maps.newHashMap();
		mapAll.put("ID", KpiUtil.CHANNEL_ALL);
		mapAll.put("NAME", "全部");
		relist.add(mapAll);
		relist.addAll(list);
		log.info("return getChannelInfos size" + relist.size());
		//Map<String,Object> map = Maps.newHashMap();
		dataJson.put("data", relist);
		dataJson.put("status", 200);
		return dataJson;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getKpiDefineInfos(HttpServletRequest request,
			HttpServletResponse response){
		String kpiId = request.getParameter("kpiId");
		String[] kpiIds = kpiId.split(":");
		List kpiIdsList = Arrays.asList(kpiIds);
		List list = kpiService.getKpiDefineInfos(kpiIdsList);
		return JsonUtil.toJSONString(list);
	}
	
	/**
	 * 政策详情页面 第一个processBar  目标客户精准性 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getPolicyProcessBar1(HttpServletRequest request,
			HttpServletResponse response){
		String dateType = String.valueOf(request.getParameter("dateType"));
		String date = String.valueOf(request.getParameter("date"));
		date = date.replaceAll("-", "");
		String cityId = String.valueOf(request.getParameter("cityId"));
		String planId = String.valueOf(request.getParameter("planId"));
		String countryId = KpiUtil.DEFAULT_DATABASE_ID;
		String channelId = KpiUtil.DEFAULT_DATABASE_ID;
		Map map = Maps.newHashMap();
		List list = kpiService.getPolicyExecInfo(dateType, date, planId, cityId, countryId, channelId);
		if (list.size()>0) {
			Map mapdata = (Map) list.get(0);
			String camp_succ_num = mapdata.get("camp_succ_num").toString();//营销成功用户数
			String target_user_num = mapdata.get("target_user_num").toString();//目标用户数
			map.put("has", camp_succ_num);
			map.put("total", target_user_num);
		}
		return JsonUtil.toJSONString(map);
	}
	/**
	 * 政策详情页面  第二个processBar 营销成功率情况
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getPolicyProcessBar2(HttpServletRequest request,
			HttpServletResponse response){
		String dateType = String.valueOf(request.getParameter("dateType"));
		String date = String.valueOf(request.getParameter("date"));
		date = date.replaceAll("-", "");
		String cityId = String.valueOf(request.getParameter("cityId"));
		String planId = String.valueOf(request.getParameter("planId"));
		String countryId = KpiUtil.DEFAULT_DATABASE_ID;
		String channelId = KpiUtil.DEFAULT_DATABASE_ID;
		List list = kpiService.getPolicyExecInfo(dateType, date, planId, cityId, countryId, channelId);
		Map allUsrNumMap = kpiService.getAllUserNum(dateType, date, cityId);
		Map map = Maps.newHashMap();
		if (list.size()>0) {
			Map mapdata = (Map) list.get(0);
			String total_order_num = mapdata.get("total_order_num").toString();//全网订购数
			String user_num = allUsrNumMap.get("user_num").toString();//全网用户数
			map.put("has", total_order_num);
			map.put("total", user_num);
		}
		return JsonUtil.toJSONString(map);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getPolicyProcessBar3(HttpServletRequest request,
			HttpServletResponse response){
		String dateType = String.valueOf(request.getParameter("dateType"));
		String date = String.valueOf(request.getParameter("date"));
		date = date.replaceAll("-", "");
		String cityId = String.valueOf(request.getParameter("cityId"));
		String planId = String.valueOf(request.getParameter("planId"));
		String countryId = KpiUtil.DEFAULT_DATABASE_ID;
		String channelId = KpiUtil.DEFAULT_DATABASE_ID;
		List list = kpiService.getPolicyExecInfo(dateType, date, planId, cityId, countryId, channelId);
		Map map = Maps.newHashMap();
		if(list.size()>0){
			Map mapdata = (Map) list.get(0);
			String camp_succ_num = mapdata.get("camp_succ_num").toString();//营销成功用户数
			String total_order_num = mapdata.get("total_order_num").toString();//全网订购数
			double b = Double.valueOf(camp_succ_num) / Double.valueOf(total_order_num) ;
			map.put("has",  KpiUtil.parsePercentNoUnit(String.valueOf(b)));
			map.put("total", "100");
		}
		return JsonUtil.toJSONString(map);
	}
	/**
	 * 用户订购率提升多少倍  政策详情页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getPolicyProcessBar3_1(HttpServletRequest request,
			HttpServletResponse response){
		String dateType = String.valueOf(request.getParameter("dateType"));
		String date = String.valueOf(request.getParameter("date"));
		date = date.replaceAll("-", "");
		String cityId = String.valueOf(request.getParameter("cityId"));
		String planId = String.valueOf(request.getParameter("planId"));
		String countryId = KpiUtil.DEFAULT_DATABASE_ID;
		String channelId = KpiUtil.DEFAULT_DATABASE_ID;
		List list = kpiService.getPolicyExecInfo(dateType, date, planId, cityId, countryId, channelId);
		Map allUsrNumMap = kpiService.getAllUserNum(dateType, date, cityId);
		Map map = Maps.newHashMap();
		if(list.size()>0){
			Map mapdata = (Map) list.get(0);
			String camp_succ_num = mapdata.get("camp_succ_num").toString();//营销成功用户数
			String target_user_num = mapdata.get("target_user_num").toString();//目标用户数
			//目标用户订购率:  营销成功用户数/营销用户数（频次清洗后的）
			double a = Double.valueOf(camp_succ_num)/Double.valueOf(target_user_num) ;
			String total_order_num = mapdata.get("total_order_num").toString();//全网订购数
			String user_num ="";
			if(allUsrNumMap.containsKey("user_num")){
				user_num = allUsrNumMap.get("user_num").toString();//全网用户数
				//自然订购率: 全网订购数/全网用户数
				double b = Double.valueOf(total_order_num)/Double.valueOf(user_num) ;
				//订购率提升: (目标用户订购率-自然订购率)/自然订购率
				double reValue = (a-b)/b;
				map.put("data", KpiUtil.parsePercent(String.valueOf(reValue)));
			}else {
				map.put("data", "");
			}
			
		}
		return JsonUtil.toJSONString(map);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getPolicyProcessBar4(HttpServletRequest request,
			HttpServletResponse response){
		String dateType = String.valueOf(request.getParameter("dateType"));
		String date = String.valueOf(request.getParameter("date"));
		date = date.replaceAll("-", "");
		String cityId = String.valueOf(request.getParameter("cityId"));
		String planId = String.valueOf(request.getParameter("planId"));
		String countryId = KpiUtil.DEFAULT_DATABASE_ID;
		String channelId = KpiUtil.DEFAULT_DATABASE_ID;
		Map map = Maps.newHashMap();
		List list = kpiService.getPolicyExecInfo(dateType, date, planId, cityId, countryId, channelId);
		if (list.size()>0) {
			Map mapdata = (Map) list.get(0);
			String camp_user_num = mapdata.get("camp_user_num").toString();//营销用户数
			String target_user_num = mapdata.get("target_user_num").toString();//营销目标用户数
			String uncamp_succ_num = mapdata.get("uncamp_succ_num").toString();//未营销用户成功用户数
			//未营销用户数  营销目标用户数 - 营销用户数
			int total = Integer.valueOf(target_user_num) - Integer.valueOf(camp_user_num) ;
			map.put("has", uncamp_succ_num);//未营销用户中的成功用户数
			map.put("total", String.valueOf(total));//未营销用户数
		}
		map.put("hasText", "成功用户数");
		map.put("totalText", "成功率");
		return JsonUtil.toJSONString(map);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getPolicyProcessBar5(HttpServletRequest request,
			HttpServletResponse response){
		String dateType = String.valueOf(request.getParameter("dateType"));
		String date = String.valueOf(request.getParameter("date"));
		date = date.replaceAll("-", "");
		String cityId = String.valueOf(request.getParameter("cityId"));
		String planId = String.valueOf(request.getParameter("planId"));
		String countryId = KpiUtil.DEFAULT_DATABASE_ID;
		String channelId = KpiUtil.DEFAULT_DATABASE_ID;
		Map map = Maps.newHashMap();
		List list = kpiService.getPolicyExecInfo(dateType, date, planId, cityId, countryId, channelId);
		if (list.size()>0) {
			Map mapdata = (Map) list.get(0);
			String camp_succ_num = mapdata.get("camp_succ_num").toString();//营销成功用户数
			String camp_user_num = mapdata.get("camp_user_num").toString();//营销用户数
			map.put("has", camp_succ_num);
			map.put("total", camp_user_num);
		}
		map.put("hasText", "成功用户数");
		map.put("totalText", "成功率");
		return JsonUtil.toJSONString(map);
	}

	public static  String getRandomString(int count){
		double d= Math.random()*count;
		BigDecimal   b   =   new   BigDecimal(d);  
		double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		return String.valueOf(f1);
	}

	/**
	 * 
	 * @param date
	 * @return 2015-12-12 return 20151212
	 */
	public static String parseDate(String date){
		return date.replaceAll("-", "");
	}
	public static void main(String[] args) {
		
	}
	
	/** 
	 * 获取登录者详细信息
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping
	public JSONObject getLoginDetail(HttpServletRequest request,
			HttpServletResponse response){
		JSONObject dataJson  = new JSONObject();

		Map<String,String> map = Maps.newHashMap();
		map.put("cityId", this.getUser(request, response).getCityId());
		dataJson.put("data", map);
		
		return dataJson;
	}
}
