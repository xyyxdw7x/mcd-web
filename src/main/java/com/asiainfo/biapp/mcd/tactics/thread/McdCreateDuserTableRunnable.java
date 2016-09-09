package com.asiainfo.biapp.mcd.tactics.thread;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.biapp.mcd.constants.MpmCONST;
import com.asiainfo.biapp.mcd.model.MtlChannelDef;
import com.asiainfo.biapp.mcd.tactics.dao.IMcdCampsegTaskDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlChannelDefDao;
import com.asiainfo.biapp.mcd.tactics.dao.MtlCampsegCustgroupDao;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.util.MpmConfigure;
import com.asiainfo.biframe.utils.spring.SystemServiceLocator;
import com.asiainfo.biframe.utils.string.StringUtil;

/**
 * 多线程创建D表
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2016-7-2 下午4:40:21
 * @version 1.0
 */

public class McdCreateDuserTableRunnable implements Runnable {
	private static Logger log = LogManager.getLogger();
	private IMpmCampSegInfoDao mpmCampSegInfoDao;
	private IMpmCampSegInfoService mpmCampSegInfoService;
	private MtlCampsegCustgroupDao mtlCampsegCustgroupDao;
	private IMcdCampsegTaskDao mcdCampsegTaskDao;
	private IMtlChannelDefDao mtlChannelDefDao;
	
	public McdCreateDuserTableRunnable() {
		try {
			log.info("begin initParam...................");
			this.mpmCampSegInfoDao = (IMpmCampSegInfoDao)SystemServiceLocator.getInstance().getService("mpmCampSegInfoDao");
			this.mpmCampSegInfoService = (IMpmCampSegInfoService)SystemServiceLocator.getInstance().getService("mpmCampSegInfoService");
			this.mtlCampsegCustgroupDao = (MtlCampsegCustgroupDao)SystemServiceLocator.getInstance().getService("mtlCampsegCustGroupDao"); 
			this.mcdCampsegTaskDao = (IMcdCampsegTaskDao)SystemServiceLocator.getInstance().getService("mcdCampsegTaskDao");
			this.mtlChannelDefDao = (IMtlChannelDefDao)SystemServiceLocator.getInstance().getService("mtlChannelDefDao");
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	@Override
	public void run() {
		String campsegId = null;
		while(true){
			campsegId = CreateDuserTaskMessageCacheQueue.getMessageQueue().poll();
			if(StringUtil.isEmpty(campsegId)){
				try {
					continue;
				} catch (Exception e) {
					log.error(e);
				}
			}
			if(mcdCampsegTaskDao.getCuserNum(campsegId) == 0){
				log.info("*********************C表数据为空，从队列出将策略id移除！！！！");
				continue;
			}
//			当策略已经存在D表的情况下，说明有任务对这个策略进行执行
			if(mcdCampsegTaskDao.checkCampsegDuserIsExists(campsegId) > 0){
				log.info("*********************D表已经更新至策略主表，无需再次执行！！！！");
				continue;
			}
			
			if(mcdCampsegTaskDao.checkTaskStatus(campsegId, MpmCONST.TASK_STATUS_UNDO)!=0){
				log.info("*********************任务状态已经变更！！！！");
				continue;
			}
//			查询策略组，针对多产品，每个子策略创建相同的D表。  每次都查询父策略的D表是否创建，如果已创建，则不往下执行，如果没创建，执行此逻辑
			List campsegCustGroupList = mtlCampsegCustgroupDao.getCustInfoByCampsegId(campsegId);
			log.info("*************客户群个数："+campsegCustGroupList.size());
			String custGroupId = "";
			for(int j=0;j<campsegCustGroupList.size();j++){
				String custGroupType = (String)((Map<String, Object>)campsegCustGroupList.get(0)).get("CUSTGROUP_TYPE");
				log.info("***********************custGroupType="+custGroupType);
				if("CG".equals(custGroupType)){
					custGroupId = (String)((Map<String, Object>)campsegCustGroupList.get(0)).get("CUSTGROUP_ID");
				}
			}
			log.info("*************custGroupId："+custGroupId);
			if(StringUtil.isNotEmpty(custGroupId)){
				//创建D表
				String tableName = mpmCampSegInfoService.createCustGroupTabAsCustTable(MpmCONST.MCD_ZD_USER_PREFIX, custGroupId);
				//给Duser表创建索引-----------创建索引的方式有待确认
				mpmCampSegInfoService.createDuserIndex(tableName,custGroupId);
				log.info("***********************tableName="+tableName);
				//向D表插入数据，由于目前系统中已经去掉了基础标签、业务标签、产品订购和剔除等信息，所以在此处直接调动，如果将来又要重新把这些信息做细分，此处应该改造
				int custCount = mpmCampSegInfoService.excuteCustGroupCount(custGroupId, null, null,null,null,null);
				log.info("*************custCount="+custCount);
//				三个场景之外客户群在插入数据的时候都要对数据进行去重
				boolean removeRepeatFlag = true;  //false:不需要去重  true：需要去重
				List list = null;
				try {
					list = mtlChannelDefDao.findMtlChannelDef(campsegId);
				} catch (Exception e1) {
					log.error("查询渠道信息异常："+e1);
				}
				//这三个场景不去重   只要包含这三个场景就不去重
				String qqwjcy = MpmConfigure.getInstance().getProperty("RULE_TIME_QQWJCY");
				String qqwzw = MpmConfigure.getInstance().getProperty("RULE_TIME_QQWZW");
				String xnwjcy = MpmConfigure.getInstance().getProperty("RULE_TIME_XNWJCY");
				String functionId = "";
				for(int j = 0;j<list.size();j++){
					MtlChannelDef mtlChannelDef = (MtlChannelDef) list.get(j);
					functionId = mtlChannelDef.getFunctionId();
					if(StringUtil.isNotEmpty(functionId)){
						if(functionId.equals(qqwjcy) || functionId.equals(qqwzw) || functionId.equals(xnwjcy)){
							removeRepeatFlag = false;
							break;
						}
					}
				}
				mpmCampSegInfoService.insertCustGroupNewWay(custGroupId, null, null, null, null, null, tableName,removeRepeatFlag);
				boolean flag = true;
				while(flag){
					//保证D表存在，同时有数据，如果没有数据然后就将状态变更，可能就会出现执行中心弹框没数据
					if(mcdCampsegTaskDao.checkDuserIsExists(tableName) >0 && mcdCampsegTaskDao.getDuserNum(tableName) > 0){
						//将目标客户群数量、D表名称和策略状态更新至策略主表
						if(StringUtil.isNotEmpty(tableName)){
							mpmCampSegInfoDao.updateCampsegById(campsegId, tableName,custCount);
						}
						//更新任务的状态之前，先判断此任务的状态是否已经变成50
						if(mcdCampsegTaskDao.checkTaskStatus(campsegId, MpmCONST.TASK_STATUS_UNDO)==0){
							mcdCampsegTaskDao.updateCampsegTaskStatusById(campsegId,tableName,custCount, MpmCONST.TASK_STATUS_UNDO);
							mcdCampsegTaskDao.updateCampsegTaskDataStatusById(campsegId,custCount, MpmCONST.TASK_STATUS_UNDO);
						}
						flag = false;
					}else{
						try {
							Thread.sleep(1000*60*5);
						} catch (InterruptedException e) {
							log.error(e);
						}
					}
				}
			}
		}
	}

}

