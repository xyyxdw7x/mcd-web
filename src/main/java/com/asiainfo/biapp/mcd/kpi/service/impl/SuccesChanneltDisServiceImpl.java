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

/**
 * 营销成功用户渠道分布
 * @author zhuml
 *
 */
@Service("succesChanneltDisService")
public class SuccesChanneltDisServiceImpl extends  msmChartServiceImpl {
	
	@Resource(name = "kpiDao")
	private IKpiDao kpiDao;

	@Override
	public List<Map<String, String>> getDataInfo(Map<String, Object> paramsMap)
			throws Exception {
		String month = String.valueOf(paramsMap.get("date"));
		month = month.replaceAll("-", "");
		String cityId = String.valueOf(paramsMap.get("cityId"));
		List<Map<String, Object>> list = kpiDao.getSuccesChanneltDisList(month, cityId);
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		Map<String,String> map2 = Maps.newHashMap();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String CAMP_SUCC_NUM = map.get("VAL").toString();
			String channelName = String.valueOf(map.get("CHANNEL_NAME"));
			map2.put(channelName, CAMP_SUCC_NUM);
		}
		dataList.add(map2);
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
		cib.setCaption("营销成功用户渠道分布");
		cib.setSubcaption("");
		cib.setChartType(Constants.ECHARTS_CHARTTYPE.PIE_EMPTY);
		List<ChartDataset> cdList = new ArrayList<ChartDataset>();
		cib.setDatasets(cdList);
		String[] seriesName ={"营销成功用户渠道分布" };
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
