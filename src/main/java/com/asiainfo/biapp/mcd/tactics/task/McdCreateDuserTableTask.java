package com.asiainfo.biapp.mcd.tactics.task;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.asiainfo.biapp.mcd.tactics.service.IMcdCreateDuserTableService;

/**
 * 
 * Title: 
 * Description:解决由于C表过大导致的创建策略速度过慢的问题，把创建D表的过程单独抽出来
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2016-5-31 上午9:56:21
 * @version 1.0
 */

public class McdCreateDuserTableTask implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3702324841539823354L;
	private static Logger log = LogManager.getLogger();
	@Resource(name="mcdCreateDuserTableService")
	private IMcdCreateDuserTableService mcdCreateDuserTableService;
	
	public void runTask() throws Exception {
		try {
			log.info("begin exec*************************************");
			List<Map<String,Object>> list = mcdCreateDuserTableService.getAll();
			log.info("******************list.size="+list.size());
			if(!CollectionUtils.isEmpty(list)){
				mcdCreateDuserTableService.doCreateDuserTable(list);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
}


