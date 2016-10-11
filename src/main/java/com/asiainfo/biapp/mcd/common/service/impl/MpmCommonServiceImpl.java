package com.asiainfo.biapp.mcd.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.service.IMpmCommonService;
import com.asiainfo.biapp.mcd.custgroup.dao.McdCvColDefineDao;
import com.asiainfo.biapp.mcd.custgroup.vo.McdCvColDefine;

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
	
	@Resource(name="mcdCvColDefineDao")
	private McdCvColDefineDao mcdCvColDefineDao;


	@Override
	public List<McdCvColDefine> initCvColDefine(String pAttrClassId,String keyWords) {
		List<McdCvColDefine> list = null;
		try {
			list = mcdCvColDefineDao.initCvColDefine(pAttrClassId,keyWords);
		} catch (Exception e) {
		}
		return list;
	}
}
