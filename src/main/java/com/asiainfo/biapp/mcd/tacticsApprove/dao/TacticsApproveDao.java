package com.asiainfo.biapp.mcd.tacticsApprove.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author zhaokai 2016-10-21 10:00:0
 * @version 1.0
 */

public interface TacticsApproveDao {
	
	public List<Map<String,Object>> searchApproveInfo(String sql);

}
