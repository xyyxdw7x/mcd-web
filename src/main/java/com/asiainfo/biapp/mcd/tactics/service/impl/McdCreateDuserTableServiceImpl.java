package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.tactics.dao.IMcdCampsegTaskDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.tactics.service.IMcdCreateDuserTableService;
import com.asiainfo.biapp.mcd.tactics.thread.CreateDuserTaskMessageCacheQueue;

/**
 * 
 * Title: 
 * Description:解决由于C表过大导致的创建策略速度过慢的问题，把创建D表的过程单独抽出来
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2016-5-31 上午10:14:57
 * @version 1.0
 */
@Service("mcdCreateDuserTableService")
public class McdCreateDuserTableServiceImpl implements IMcdCreateDuserTableService {
	@Resource(name="mpmCampSegInfoDao")
	private IMpmCampSegInfoDao mpmCampSegInfoDao;
	@Resource(name="mcdCampsegTaskDao")
	private IMcdCampsegTaskDao mcdCampsegTaskDao;
	

	@Override
	public List<Map<String, Object>> getAll() {
		List<Map<String, Object>> list = null;
		try {
			list = mcdCampsegTaskDao.getCampsegByStatus(McdCONST.TASK_STATUS_INIT);
		} catch (Exception e) {
			Log.error("根据状态查询任务异常："+e);
		}
		return list;
	}

	@Override
	public void doCreateDuserTable(List<Map<String,Object>> list) {
		try {
			//生产者
			for(int i=0;i<list.size();i++){
				Map<String,Object> tmap = (Map<String, Object>) list.get(i);
				String campsegId = (String)tmap.get("CAMPSEG_ID");
				ConcurrentLinkedQueue<String> messageQueue = CreateDuserTaskMessageCacheQueue.getMessageQueue();
				if(!messageQueue.contains(campsegId)){
					CreateDuserTaskMessageCacheQueue.putMessageQueue(campsegId);
				}
			}
		} catch (Exception e) {
			Log.error("创建D表异常："+e);
		}
	}
	
}


