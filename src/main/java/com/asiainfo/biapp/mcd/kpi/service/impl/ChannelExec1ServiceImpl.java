package com.asiainfo.biapp.mcd.kpi.service.impl;

import com.asiainfo.biapp.mcd.home.echartbean.*;
import com.asiainfo.biapp.mcd.kpi.dao.IKpiDao;
import com.asiainfo.biapp.mcd.kpi.service.msmChartServiceImpl;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 渠道执行情况第一个图
 * @author zhuml
 *
 */
@Service("channelExec1Service")
public class ChannelExec1ServiceImpl extends  msmChartServiceImpl {
	
	@Resource(name = "kpiDao")
	private IKpiDao kpiDao;

	@Override
	public List<Map<String, String>> getDataInfo(Map<String, Object> paramsMap)
			throws Exception {
		String date = String.valueOf(paramsMap.get("date"));
		date = date.replaceAll("-", "");
		String channelId = String.valueOf(paramsMap.get("channelId"));
		String cityId = String.valueOf(paramsMap.get("cityId"));
		List<Map<String, Object>> list = kpiDao.getChannelExec1List(date, cityId, channelId);
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		Map<String,String> map1 = Maps.newLinkedHashMap();
		Map<String,String> map2 =  Maps.newLinkedHashMap();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			String camp_user_num = String.valueOf(map.get("CAMPNUM"));//营销用户数 title1
			String target_user_num = String.valueOf(map.get("TARNUM"));//总目标用户数
			String bother_avoid_user_num = String.valueOf(map.get("BOTHERAVOIDNUM"));//免打扰用户数
			String fre_ctl_user_num = String.valueOf(map.get("FRENUM"));//频次清洗用户
			String no_touch_user_name = String.valueOf(map.get("NOTOUCHNUM"));//未接触用户数
			String camp_succ_num = String.valueOf(map.get("SUCNUM"));//营销成功用户数 title2
			String camp_fail_user_num = String.valueOf(map.get("FAILNUM"));//营销失败用户数
			String user_coverage_rate = String.valueOf(map.get("CVRATE"));//用户覆盖率  title3
			String camp_succ_rate = String.valueOf(map.get("SUCRATE"));//营销用户成功率  title4
			
			if("null".equals(camp_user_num)){camp_user_num= "0";}
			if("null".equals(target_user_num)){target_user_num= "0";}
			if("null".equals(bother_avoid_user_num)){bother_avoid_user_num = "0";}
			if("null".equals(fre_ctl_user_num)){fre_ctl_user_num= "0";}
			if("null".equals(no_touch_user_name)){no_touch_user_name= "0";}
			if("null".equals(camp_succ_num)){camp_succ_num= "0";}
			if("null".equals(camp_fail_user_num)){camp_fail_user_num= "0";}
			if("null".equals(user_coverage_rate)){user_coverage_rate= "0";}
			if("null".equals(camp_succ_rate)){camp_succ_rate= "0";}
			paramsMap.put("title", camp_user_num +";" + camp_succ_num +";" +user_coverage_rate +";" +camp_succ_rate );
			
			long numbe =  Long.valueOf(target_user_num);
			map1.put("总目标用户数", "0");
			map2.put("总目标用户数", target_user_num);
			
			numbe -= Long.valueOf(bother_avoid_user_num);
			map1.put("免打扰用户数", String.valueOf(numbe));
			map2.put("免打扰用户数", bother_avoid_user_num);
			
			numbe -= Long.valueOf(fre_ctl_user_num);
			map1.put("频次清洗用户数", String.valueOf(numbe));
			map2.put("频次清洗用户数", fre_ctl_user_num);
			
			numbe -= Long.valueOf(no_touch_user_name);
			map1.put("未接触用户数", String.valueOf(numbe));
			map2.put("未接触用户数", no_touch_user_name);
			
			numbe -= Long.valueOf(camp_succ_num);
			map1.put("营销成功用户数", String.valueOf(numbe));
			map2.put("营销成功用户数", camp_succ_num);
			
			numbe -= Long.valueOf(camp_fail_user_num);
			map1.put("营销失败用户数", String.valueOf(numbe));
			map2.put("营销失败用户数", camp_fail_user_num);
			
		}
//		map1.put("总目标用户数", "0");
//		map1.put("免打扰用户数", "1700");
//		map1.put("频次清洗用户数", "1400");
//		map1.put("未接触用户数", "1200");
//		map1.put("营销成功用户数", "300");
//		map1.put("营销失败用户数", "0");
//		
//		map2.put("总目标用户数", "2900");
//		map2.put("免打扰用户数", "1200");
//		map2.put("频次清洗用户数", "300");
//		map2.put("未接触用户数", "200");
//		map2.put("营销成功用户数", "900");
//		map2.put("营销失败用户数", "300");
		
//		/**
//		 * 2016-4-11 17:12:11 注释了 
//		 */
		dataList.add(map1);
		dataList.add(map2);
		return dataList;
	}

	@Override
	public Map<String, Object> executeDataInfo(List<Map<String, String>> dataList,
			Map<String, Object> paramsMap) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ChartInfoBase cib = new ChartInfoBase();
		resultMap.put(MsmConstants.CHART_INFO_BASE, cib);
		String title = (String) paramsMap.get("title");
		cib.setCaption(title);
		cib.setSubcaption("");
		cib.setChartType(Constants.ECHARTS_CHARTTYPE.BAR);
		cib.setyAxisName("用户数(人)");
		List<ChartDataset> cdList = new ArrayList<ChartDataset>();
		cib.setDatasets(cdList);
		
		String[] seriesName ={"成功用户(人)"};
		String[] seriesType = {"bar"};
		String[] parentYAxis = {"P"};
		
		for (int j = 0; j < dataList.size(); j++) {
			ChartDataset cd = new ChartDataset();
			cd.setSeriesName(seriesName[0]);
			cd.setRenderAs(seriesType[0]);
			cd.setParentYAxis(parentYAxis[0]);
			List<ChartSet> csList = new ArrayList<ChartSet>();
			Map<String, String> map = dataList.get(j);
			for (String key : map.keySet()) {
				ChartSet cs = new ChartSet();
				cs.setLabel(key);
				cs.setValue(map.get(key));
				csList.add(cs);
			}
			cd.setSets(csList);
			cdList.add(cd);
		}
		
		return resultMap;
	}



}
