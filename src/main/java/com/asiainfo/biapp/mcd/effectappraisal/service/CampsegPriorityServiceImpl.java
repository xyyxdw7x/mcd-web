package com.asiainfo.biapp.mcd.effectappraisal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.bean.CampsegPriorityBean;
import com.asiainfo.biapp.mcd.effectappraisal.dao.IcampsegPriorityDao;
import com.asiainfo.biapp.mcd.util.jdbcPage.Pager;
import com.asiainfo.biframe.utils.string.StringUtil;

/**
 * 
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2016-3-17 上午10:11:29
 * @version 1.0
 */
@Service("campsegPriorityService")
public class CampsegPriorityServiceImpl implements IcampsegPriorityService{
	private static Logger log = LogManager.getLogger();
	
	@Autowired
	private IcampsegPriorityDao campsegPriorityDao;
	
	@Override
	public List<CampsegPriorityBean> initManualPriorityCampseg(String channelId,String adivId,String cityId){
		return campsegPriorityDao.initManualPriorityCampseg(channelId,adivId, cityId);
	}
	
	@Override
	public List<CampsegPriorityBean> initAutoPriorityCampseg(String channelId,String adivId,
			String cityId,String keyWords,Pager pager) {
		return campsegPriorityDao.initAutoPriorityCampseg(channelId,adivId,cityId,keyWords, pager);
	}
	
	@Override
	public int getAutoPriorityCampsegNum(String channelId,String adivId,String cityId,String keyWords){
		return campsegPriorityDao.getAutoPriorityCampsegNum(channelId,adivId, cityId,keyWords);
	}
	
	@Override
	public void editPriorityCampseg(String campsegId, String channelId,
			String cityId,String chnAdivId) {
		//先让之前手动优先级中的策略排序自动加1
		campsegPriorityDao.eidtManualPriority(channelId, cityId,chnAdivId);
		// 当自动超过十条的时候，将最后一条从手动改为自动,然后将待置顶的自动策略改为手动策略（状态）同时修改排序字段为1，最后调用存储过程即可
		campsegPriorityDao.changeManualToAutoPriority(campsegId,channelId, cityId,chnAdivId);
	}
	
	@Override
	public void editManualPriorityCampseg(String priOrderNumStr){
		if(StringUtil.isNotEmpty(priOrderNumStr)){
			org.json.JSONArray priOrderNumStrArray;
			try {
				priOrderNumStrArray = new org.json.JSONArray(priOrderNumStr);
				List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
				for(int i = 0;i<priOrderNumStrArray.length();i++){
					org.json.JSONObject obj = new org.json.JSONObject(priOrderNumStrArray.get(i).toString());
					String campsegId = String.valueOf(obj.get("campsegId"));
					String channelId = String.valueOf(obj.get("channelId"));
					String cityId = String.valueOf(obj.get("cityId"));
					String chnAdivId = String.valueOf(obj.get("chnAdivId")); 
//					campsegPriorityDao.editManualPriorityCampseg(campsegId,channelId,cityId,i+1,chnAdivId);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("campsegId", campsegId);
					map.put("channelId", channelId);
					map.put("cityId", cityId);
					map.put("priority", i+1);
					if(StringUtil.isNotEmpty(chnAdivId)){
						map.put("chnAdivId", chnAdivId);
						list1.add(map);
					}else{
						list2.add(map);
					}
					
				}
				campsegPriorityDao.editManualPriorityCampseg(list1, list2);
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		}
	}
	
	@Override
	public void cancleTopManualPriorityCampseg(String campsegId,String cityId,String channelId,String chnAdivId) {
		//先将手动优先级改为自动优先级
		campsegPriorityDao.cancleTopManualPriorityCampseg1(campsegId,cityId,channelId,chnAdivId);
		//将手动优先级进行手动排序，同时调用存储过程，对自动优先级进行重排序
		campsegPriorityDao.cancleTopManualPriorityCampseg2(campsegId, cityId, channelId,chnAdivId);
	}
	
	public IcampsegPriorityDao getCampsegPriorityDao() {
		return campsegPriorityDao;
	}
	public void setCampsegPriorityDao(IcampsegPriorityDao campsegPriorityDao) {
		this.campsegPriorityDao = campsegPriorityDao;
	}

	
}


