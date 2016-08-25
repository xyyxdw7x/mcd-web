package com.asiainfo.biapp.mcd.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.util.MpmLocaleUtil;
import com.asiainfo.biapp.mcd.tactics.dao.IDimMtlChanneltypeDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlStcPlanDao;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.tactics.vo.DimPlanSrvType;
import com.asiainfo.biapp.mcd.tactics.vo.DimPlanType;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 营销管理模块公用方法实现�?
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: asiainfo.,Ltd
 * </p>
 *
 * @author weilin.wu wuwl2@asiainfo.com
 * @version 1.0
 */
@Service("mpmCommonService")
public class MpmCommonServiceImpl implements IMpmCommonService {
	@Resource(name="mtlStcPlanDao")
	private IMtlStcPlanDao mtlStcPlanDao;
	@Resource(name="dimMtlChanneltypeDao")
	private IDimMtlChanneltypeDao dimMtlChanneltypeDao;
	public IMtlStcPlanDao getMtlStcPlanDao() {
		return mtlStcPlanDao;
	}
	public void setMtlStcPlanDao(IMtlStcPlanDao mtlStcPlanDao) {
		this.mtlStcPlanDao = mtlStcPlanDao;
	}
	public IDimMtlChanneltypeDao getDimMtlChanneltypeDao() {
		return dimMtlChanneltypeDao;
	}
	public void setDimMtlChanneltypeDao(IDimMtlChanneltypeDao dimMtlChanneltypeDao) {
		this.dimMtlChanneltypeDao = dimMtlChanneltypeDao;
	}
	@Override
	public List<DimPlanType> initDimPlanType() {
		try {
			return mtlStcPlanDao.initDimPlanType();
		} catch (Exception e) {
			throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.cshzclbsb"));
		}
	}
	@Override
	public List<DimPlanSrvType> getGradeList() throws MpmException {
		return dimMtlChanneltypeDao.getGradeList();
	}
	public List<DimMtlChanneltype> getMtlChanneltypeByCondition(String isDoubleSelect){
		try {
			return dimMtlChanneltypeDao.getMtlChanneltypeByCondition(isDoubleSelect);
		} catch (Exception e) {
			throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.cxqdlxdysb"));
		}
	}
}
