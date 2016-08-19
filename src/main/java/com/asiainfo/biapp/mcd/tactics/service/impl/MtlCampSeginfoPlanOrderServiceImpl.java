package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.dao.IMtlCampSeginfoPlanOrderDao;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCampSeginfoPlanOrderService;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfoPlanOrder;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-8-8 下午03:56:11
 * @version 1.0
 */

public class MtlCampSeginfoPlanOrderServiceImpl implements IMtlCampSeginfoPlanOrderService {
	private IMtlCampSeginfoPlanOrderDao mtlCampSeginfoPlanOrderDao;

	@Override
	public void save(MtlCampSeginfoPlanOrder campSeginfoPlanOrder) {
		mtlCampSeginfoPlanOrderDao.save(campSeginfoPlanOrder);		
	}

	public IMtlCampSeginfoPlanOrderDao getMtlCampSeginfoPlanOrderDao() {
		return mtlCampSeginfoPlanOrderDao;
	}

	public void setMtlCampSeginfoPlanOrderDao(IMtlCampSeginfoPlanOrderDao mtlCampSeginfoPlanOrderDao) {
		this.mtlCampSeginfoPlanOrderDao = mtlCampSeginfoPlanOrderDao;
	}

	@Override
	public List<MtlCampSeginfoPlanOrder> getPlanOrderByCampsegId(String campsegId) {
		return this.mtlCampSeginfoPlanOrderDao.getPlanOrderByCampsegId(campsegId);
	}

	@Override
	public void updatePlanOrderByCampsegId(String campsegId,String orderPlanId,String excludePlanId) {
		this.mtlCampSeginfoPlanOrderDao.updatePlanOrderByCampsegId(campsegId,orderPlanId,excludePlanId);
	}
}
