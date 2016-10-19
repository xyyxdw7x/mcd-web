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
import com.google.common.collect.Maps;

@Service("demoChartService")
public class demoChartServiceImpl extends  msmChartServiceImpl {
	
	@Resource(name = "kpiDao")
	private IKpiDao kpiDao;

	@Override
	public List<Map<String, String>> getDataInfo(Map<String, Object> paramsMap)
			throws Exception {
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		dataList.clear();
		Map<String,String> map1 = Maps.newHashMap();
		map1.put("1次", "10");
		Map<String,String> map2 = Maps.newHashMap();
		map2.put("2次", "5");
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
		cib.setCaption("近三个月内提货客户次数分析(" + String.valueOf(paramsMap.get("date")) +")");
		cib.setSubcaption("");
		cib.setChartType(Constants.ECHARTS_CHARTTYPE.LINE);
		cib.setyAxisName("(次)");
		List<ChartDataset> cdList = new ArrayList<ChartDataset>();
		cib.setDatasets(cdList);
		
		String[] seriesName ={"近三个月内提货客户次数分析" , "TestData1"};
		String[] seriesType = {"line" , "line"};
		String[] parentYAxis = {"P","P"};
		
		ChartDataset cd = new ChartDataset();
		cd.setSeriesName(seriesName[0]);
		cd.setRenderAs(seriesType[0]);
		cd.setParentYAxis(parentYAxis[0]);
		List<ChartSet> csList = new ArrayList<ChartSet>();
		for (int j = 0; j < dataList.size(); j++) {
			Map<String, String> map = dataList.get(j);
			for (String key : map.keySet()) {
				ChartSet cs = new ChartSet();
				cs.setLabel(key);
				cs.setValue(map.get(key));
				csList.add(cs);
			}
		}
		cd.setSets(csList);
		cdList.add(cd);
		return resultMap;
	}
}
