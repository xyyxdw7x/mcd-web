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
 * 某个营销政策成功用户占比
 * @author zhuml
 *
 */
@Service("policySuccesChannelDisService")
public class PolicySuccesChannelDisServiceImpl extends  msmChartServiceImpl {
	
	@Resource(name = "kpiDao")
	private IKpiDao kpiDao;

	@Override
	public List<Map<String, String>> getDataInfo(Map<String, Object> paramsMap)
			throws Exception {
		String dateType = String.valueOf(paramsMap.get("dateType"));
		String date = String.valueOf(paramsMap.get("date"));
		date = date.replaceAll("-", "");
		String cityId = String.valueOf(paramsMap.get("cityId"));
		String planId = String.valueOf(paramsMap.get("planId"));
		List<Map<String, Object>> list = kpiDao.getPolicySuccessChannelList(dateType, date, planId, cityId);
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		Map<String,String> map2 = Maps.newHashMap();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			String channelName = String.valueOf(map.get("channel_name"));	
			String CAMP_SUCC_NUM = String.valueOf(map.get("CAMP_SUCC_NUM"));
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
		cib.setCaption("成功用户占比");
		cib.setSubcaption("");
		cib.setChartType(Constants.ECHARTS_CHARTTYPE.PIE_EMPTY);
		List<ChartDataset> cdList = new ArrayList<ChartDataset>();
		cib.setDatasets(cdList);
		String[] seriesName ={"成功用户占比" };
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
