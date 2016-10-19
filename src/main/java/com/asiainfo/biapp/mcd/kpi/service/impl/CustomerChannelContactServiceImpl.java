package com.asiainfo.biapp.mcd.kpi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.kpi.dao.IKpiDao;
import com.asiainfo.biapp.mcd.home.echartbean.ChartDataset;
import com.asiainfo.biapp.mcd.home.echartbean.ChartInfoBase;
import com.asiainfo.biapp.mcd.home.echartbean.ChartSet;
import com.asiainfo.biapp.mcd.home.echartbean.Constants;
import com.asiainfo.biapp.mcd.home.echartbean.MsmConstants;
import com.asiainfo.biapp.mcd.kpi.service.msmChartServiceImpl;
import com.asiainfo.biapp.mcd.kpi.util.KpiUtil;
import com.google.common.collect.Maps;

/**
 * 客户渠道接触情况
 * @author zhuml
 *
 */
@Service("customerChannelContactService")
public class CustomerChannelContactServiceImpl extends  msmChartServiceImpl {
	
	@Resource(name = "kpiDao")
	private IKpiDao kpiDao;

	@Override
	public List<Map<String, String>> getDataInfo(Map<String, Object> paramsMap)
			throws Exception {
		String dateType = String.valueOf(paramsMap.get("dateType"));
		String date = String.valueOf(paramsMap.get("date"));
		date = date.replaceAll("-", "");
		String planId = String.valueOf(paramsMap.get("planId"));
		String cityId = String.valueOf(paramsMap.get("cityId"));
		String countryId = KpiUtil.DEFAULT_DATABASE_ID;
		String channelId = KpiUtil.DEFAULT_DATABASE_ID;
		List<Map<String,Object>> list = kpiDao.getPolicyExecInfo(dateType, date, planId, cityId, countryId, channelId);
		String target_user_num = "";
		String camp_user_num = "";
		if(list.size()>0){
			Map<String,Object> map = (Map<String,Object>) list.get(0);
			target_user_num =  String.valueOf( map.get("target_user_num") );
			camp_user_num =  String.valueOf( map.get("camp_user_num") );
		} 
		
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		Map<String,String> map2 = Maps.newLinkedHashMap();
		map2.put("营销用户数", camp_user_num);
		map2.put("营销目标用户数", target_user_num);
		dataList.add(map2);
		String  caption ="营销目标用户数:" + target_user_num +"  营销用户数:" + camp_user_num;
		paramsMap.put("caption", caption);
		return dataList;
	}

	/**
	 * 前台js改成嵌套饼图
	 */
	public Map<String, Object> executeDataInfo(List<Map<String, String>> dataList,
			Map<String, Object> paramsMap) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ChartInfoBase cib = new ChartInfoBase();
		resultMap.put(MsmConstants.CHART_INFO_BASE, cib);
//		cib.setCaption("营销目标用户数：11568692        营销用户数：567890");
		cib.setCaption(String.valueOf(paramsMap.get("caption")));
		cib.setSubcaption("");
		cib.setChartType(Constants.ECHARTS_CHARTTYPE.PIE_EMPTY);
		List<ChartDataset> cdList = new ArrayList<ChartDataset>();
		cib.setDatasets(cdList);
		String[] seriesName ={"客户渠道接触情况" };
		String[] seriesType = {"pie"};
		
		for (int j = 0; j < dataList.size(); j++) {
			ChartDataset cd = new ChartDataset();
			cd.setSeriesName(seriesName[j]);
			cd.setRenderAs(seriesType[j]);
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
