package com.asiainfo.biapp.mcd.kpi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.kpi.dao.IKpiDao;
import com.asiainfo.biapp.mcd.home.echartbean.DataGridInfoBase;
import com.asiainfo.biapp.mcd.home.echartbean.MsmConstants;
import com.asiainfo.biapp.mcd.home.echartbean.datagrid.GridCell;
import com.asiainfo.biapp.mcd.kpi.service.msmChartServiceImpl;

@Service("demoTableChartService")
public class demoTableChartServiceImpl extends  msmChartServiceImpl {
	
	@Resource(name = "kpiDao")
	private IKpiDao kpiDao;

	@Override
	public List<Map<String, String>> getDataInfo(Map<String, Object> paramsMap)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		return list;
	}

	@Override
	public Map<String, Object> executeDataInfo(List<Map<String, String>> dataList,
			Map<String, Object> paramsMap) throws Exception {
		String[] headCodes = {"Area","ChannelAlarmCount" , "PhoneAlarmCount" ,"TopChannelAlarmCount"};
		String[] headNames = {"省份","异常预警渠道数量","异常预警机型数量","TOP10渠道预警数量"};
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DataGridInfoBase dataGridInfoBase = new DataGridInfoBase();
		dataGridInfoBase.setId(String.valueOf(paramsMap.get("divId")));
		dataGridInfoBase.setChartType(MsmConstants.GRID_TYPE);
		dataGridInfoBase.setHeaderCodes(headCodes);
		dataGridInfoBase.setHeaderNames(headNames);
		List<Map<String, GridCell>> gridDataList = new ArrayList<Map<String, GridCell>>();
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> map = dataList.get(i);
			Map<String, GridCell> datamap = new HashMap<String, GridCell>();
			for (String key : map.keySet()) {
				GridCell gridCell = new GridCell();
				gridCell.setWarning(false);
				String value = map.get(key);
				if(key.equals("ChannelAlarmCount") || key.equals("PhoneAlarmCount") || key.equals("TopChannelAlarmCount")){
//					value =  NumberUtil.StringToInt(value);
				}
				gridCell.setValue(value);
				datamap.put(key, gridCell);
			}
			gridDataList.add(datamap);
		}
		dataGridInfoBase.setDataList(gridDataList);
		resultMap.put(MsmConstants.GRID_INFO_BASE, dataGridInfoBase);
		return resultMap;
	}



}
