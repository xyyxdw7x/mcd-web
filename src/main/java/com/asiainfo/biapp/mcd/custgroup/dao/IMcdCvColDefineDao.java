package com.asiainfo.biapp.mcd.custgroup.dao;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.model.McdCvColDefine;
import com.asiainfo.biframe.dimtable.model.DimTableDefine;

public interface IMcdCvColDefineDao {
	
	/**
	 * 初始化ARPU
	 * @param pAttrClassId
	 * @return
	 */
	public List<McdCvColDefine> initBasicLable(String attrId);
	
	/**
	 * add by lixq10  IMCD_ZJ 新建策略页面视图预定义配置
	 * @return
	 */
	public List<McdCvColDefine> initCvColDefine(String pAttrClassId,String keyWords);
	
	/**
	 * 点击更多   加载业务标签   获取总条数
	 * @param pAttrClassId
	 * @param keyWords
	 * @param pager
	 * @return
	 */
	public int getMoreCvColDefineCount(String pAttrClassId,String keyWords,String attrClassId);
	
	/**
	 * add by lixq10  点击更多   加载业务标签
	 * @param pAttrClassId
	 * @param keyWords
	 * @param pager
	 * @return
	 */
	public List<McdCvColDefine> getMoreCvColDefine(String pAttrClassId,String keyWords,Pager pager,String attrClassId);

	/**
	 * 点击业务标签更多按钮    ，初始化业务标签的子属性   如：4G发展 、终端销售等等
	 * @param pAttrClassId
	 * @return
	 */
	public List<McdCvColDefine> initBussinessLableSon(String pAttrClassId);
	
	/**
	 * 计数
	 * @param attrClassId
	 * @param keyWords
	 * @return
	 */
	public int countChangeContentBussinessLableSon(String attrClassId,String keyWords);
	
	/**
	 * 点击业务标签的子属性   如：4G发展 、终端销售等等  时，点击不同tab页切换数据
	 * @param pAttrClassId
	 * @return
	 */
	public List<McdCvColDefine> changeContentBussinessLableSon(String attrClassId,String keyWords,Pager pager);

	/**
	 * 获取dimtable表中地市信息数据数量
	 * @param define
	 * @param dimName
	 * @param cityAuthorityWhereStr
	 * @return
	 */
	public int getAllDimDataByDimTableDefineNum(
			DimTableDefine define,String dimName,
			String cityAuthorityWhereStr);
	
	/**
	 * 获取dimtable表中地市信息数据
	 * @param define
	 * @param pager
	 * @param dimName
	 * @param cityAuthorityWhereStr
	 * @return
	 */
	public List<Map<String, Object>> getAllDimDataByDimTableDefine(
			DimTableDefine define,  Pager pager, String dimName,
			String cityAuthorityWhereStr);
	
	/**
	 * 初始化添加筛选规则数据
	 * @return
	 */
	public List<McdCvColDefine> initFilterRule(String pAttrClassId,String keyWords,String frequentlyUsedLableId);

	
	
}
