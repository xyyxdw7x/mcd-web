package com.asiainfo.biapp.mcd.custgroup.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.custgroup.vo.McdCvColDefine;

public interface McdCvColDefineDao {
	/**
	 * add by lixq10  IMCD_ZJ 新建策略页面视图预定义配置
	 * @return
	 */
	public List<McdCvColDefine> initCvColDefine(String pAttrClassId,String keyWords);
	
	
}
