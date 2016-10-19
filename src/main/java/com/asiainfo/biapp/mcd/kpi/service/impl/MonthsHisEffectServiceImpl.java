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
 * 月使用效果 近6个月成功用户 营销用户 成功率
 * @author zhuml
 *
 */
@Service("monthsHisEffectService")
public class MonthsHisEffectServiceImpl extends  msmChartServiceImpl {
	
	@Resource(name = "kpiDao")
	private IKpiDao kpiDao;

	@Override
	public List<Map<String, String>> getDataInfo(Map<String, Object> paramsMap)
			throws Exception {
		String date = String.valueOf(paramsMap.get("date"));
		@SuppressWarnings("unchecked")
		List<String> months = KpiUtil.getLast6Month(date);
		String cityId = String.valueOf(paramsMap.get("cityId"));
		List<Map<String, Object>> list = kpiDao.getMonthsHisEffectList(months, cityId);
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		Map<String, String> map1 = Maps.newLinkedHashMap();
		Map<String, String> map2 = Maps.newLinkedHashMap();
		Map<String, String> map3 = Maps.newLinkedHashMap();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String month = String.valueOf( map.get("STAT_DATE"));
			String camp_succ_num = String.valueOf(map.get("SUCNUM"));//成功人数
			String camp_user_num = String.valueOf(map.get("CAMPNUM"));//营销人数
			String camp_succ_rate = String.valueOf(map.get("SUCRATE"));//营销成功率
			camp_succ_rate = KpiUtil.parsePercentNoUnit(camp_succ_rate);
			map1.put(month, camp_succ_num);
			map2.put(month, camp_user_num);
			map3.put(month, camp_succ_rate);
		}
		dataList.add(map1);
		dataList.add(map2);
		dataList.add(map3);
		return dataList;
	}

	@Override
	public Map<String, Object> executeDataInfo(List<Map<String, String>> dataList,
			Map<String, Object> paramsMap) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ChartInfoBase cib = new ChartInfoBase();
		resultMap.put(MsmConstants.CHART_INFO_BASE, cib);
		cib.setCaption("近6个月营销成功用户及成功率趋势");
		cib.setSubcaption("");
		cib.setChartType(Constants.ECHARTS_CHARTTYPE.LINE);
		cib.setyAxisName("用户数(人)");
		cib.setSyAxisName("成功率(%)");
		List<ChartDataset> cdList = new ArrayList<ChartDataset>();
		cib.setDatasets(cdList);
		
		String[] seriesName ={"成功用户(人)" ,"营销用户(人)", "成功率(%)"};
		String[] seriesType = {"bar" , "bar", "line"};
		String[] parentYAxis = {"P","P","S"};
		
		for (int j = 0; j < dataList.size(); j++) {
			ChartDataset cd = new ChartDataset();
			cd.setSeriesName(seriesName[j]);
			cd.setRenderAs(seriesType[j]);
			cd.setParentYAxis(parentYAxis[j]);
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
