package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;

/**
 * 
 * Title: 
 * Description:解决由于C表过大导致的创建策略速度过慢的问题，把创建D表的过程单独抽出来
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2016-5-31 上午10:10:55
 * @version 1.0
 */

public interface IMcdCreateDuserTableService {
	/**
	 * 定时任务调度，查询出状态是xxxxx的策略
	 * @return
	 */
	public List getAll();
	
	/**
	 * 创建D表，请客户群信息
	 * @param list：满足条件的策略
	 */
	public void doCreateDuserTable(List list);
}


