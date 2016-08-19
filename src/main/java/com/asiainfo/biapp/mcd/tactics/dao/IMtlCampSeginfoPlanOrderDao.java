package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfoPlanOrder;


/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-8-8 下午03:56:45
 * @version 1.0
 */

public interface IMtlCampSeginfoPlanOrderDao {
	public void save(MtlCampSeginfoPlanOrder campSeginfoPlanOrder);
	public List<MtlCampSeginfoPlanOrder> getPlanOrderByCampsegId(String campsegId);
	public void updatePlanOrderByCampsegId(String campsegId,String orderPlanId,String excludePlanId);
}
