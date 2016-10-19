package com.asiainfo.biapp.mcd.kpi.service.impl;

import com.asiainfo.biapp.mcd.kpi.dao.IKpiDao;
import com.asiainfo.biapp.mcd.common.util.CommonUtil;
import com.asiainfo.biapp.mcd.home.echartbean.*;
import com.asiainfo.biapp.mcd.kpi.service.msmChartServiceImpl;
import com.asiainfo.biapp.mcd.kpi.util.KpiUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 日使用效果 overview 页面
 * @author zhuml
 *
 */
@Service("dayUseEffectService")
public class DayUseEffectServiceImpl extends  msmChartServiceImpl {
	private static final Logger log = LogManager.getLogger(DayUseEffectServiceImpl.class);
	
	@Resource(name = "kpiDao")
	private IKpiDao kpiDao;

	@Override
	public List<Map<String, String>> getDataInfo(Map<String, Object> paramsMap) throws Exception {
		List<Map<String, String>> dataList = Lists.newArrayList();
		try {
			String date = String.valueOf(paramsMap.get("date"));
			date = date.replaceAll("-", "");
			String cityId = String.valueOf(paramsMap.get("cityId"));
			List<Map<String, Object>> list = kpiDao.getDayUseEffectList(date, cityId);

			Map<String, String> map1 = Maps.newLinkedHashMap();
			Map<String, String> map2 = Maps.newLinkedHashMap();

			Integer int_user_sum = 0;
			Integer int_succ_sum = 0;
			Double d_rate_sum = 0.0;
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) list.get(i);
				String camp_user_num = String.valueOf(map.get("VAL_CAMP"));
				String camp_succ_num = String.valueOf(map.get("VAL_SUC"));// 成功用户
				String camp_succ_rate = String.valueOf(map.get("RATE"));// 成功率
				int_succ_sum += Integer.parseInt(camp_succ_num);
				int_user_sum += Integer.parseInt(camp_user_num);
				camp_succ_rate = KpiUtil.parsePercentNoUnit(camp_succ_rate);
				String city_name = String.valueOf(map.get("AREA_NAME"));
				map1.put(city_name, camp_succ_num);
				map2.put(city_name, camp_succ_rate);
			}
			String d_rate_sum_show = "";
			if (int_user_sum != 0) {
				d_rate_sum = Double.parseDouble(int_succ_sum + "") / Double.parseDouble(int_user_sum + "") * 100;
				d_rate_sum_show = String.format("%.2f", d_rate_sum);
			}
			String text = int_user_sum + ";" + int_succ_sum + ";" + d_rate_sum_show;
			paramsMap.put("subcaption", text);
			dataList.add(map1);
			dataList.add(map2);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("效果总揽KpiAction--DayUseEffectServiceImpl--getDataInfo查询失败", e);
		}

		return dataList;
	}

	@Override
	public Map<String, Object> executeDataInfo(List<Map<String, String>> dataList,
			Map<String, Object> paramsMap) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ChartInfoBase cib = new ChartInfoBase();
		resultMap.put(MsmConstants.CHART_INFO_BASE, cib);
		cib.setCaption("");
		String subcaptionText = CommonUtil.optString(paramsMap, "subcaption");
		cib.setSubcaption(subcaptionText);
		cib.setChartType(Constants.ECHARTS_CHARTTYPE.BAR);
		cib.setyAxisName("成功用户(人)");
		cib.setSyAxisName("成功率(%)");
		List<ChartDataset> cdList = new ArrayList<ChartDataset>();
		cib.setDatasets(cdList);
		
		String[] seriesName ={"成功用户(人)" , "成功率(%)"};
		String[] seriesType = {"bar" , "bar"};
		String[] parentYAxis = {"P","S"};
		
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
