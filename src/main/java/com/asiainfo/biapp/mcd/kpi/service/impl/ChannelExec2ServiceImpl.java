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
 * 渠道执行情况第二个图
 * @author zhuml
 *
 */
@Service("channelExec2Service")
public class ChannelExec2ServiceImpl extends  msmChartServiceImpl {
	
	@Resource(name = "kpiDao")
	private IKpiDao kpiDao;

	@Override
	public List<Map<String, String>> getDataInfo(Map<String, Object> paramsMap)
			throws Exception {
		String date = String.valueOf(paramsMap.get("date"));
		@SuppressWarnings("unchecked")
		List<String> month = KpiUtil.getLast6Month(date);
		String cityId = String.valueOf(paramsMap.get("cityId"));
		String channelId = String.valueOf(paramsMap.get("channelId"));
		List<Map<String, Object>> list = kpiDao.getChannelExec2List(month, cityId, channelId);
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		Map<String,String> map1 = Maps.newLinkedHashMap();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			String OP_ID = String.valueOf( map.get("STAT_DATE"));
			String camp_succ_num = String.valueOf( map.get("SUCNUM"));//成功人数
			map1.put(OP_ID, camp_succ_num);
		}
		dataList.add(map1);
		return dataList;
	}

	@Override
	public Map<String, Object> executeDataInfo(List<Map<String, String>> dataList,
			Map<String, Object> paramsMap) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ChartInfoBase cib = new ChartInfoBase();
		resultMap.put(MsmConstants.CHART_INFO_BASE, cib);
		cib.setCaption("成功用户数趋势");
		cib.setSubcaption("");
		cib.setChartType(Constants.ECHARTS_CHARTTYPE.LINE);
		cib.setyAxisName("用户数(人)");
		List<ChartDataset> cdList = new ArrayList<ChartDataset>();
		cib.setDatasets(cdList);
		
		String[] seriesName ={"成功用户数"};
		String[] seriesType = {"line"};
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
