package com.asiainfo.biapp.mcd.common.dao.plan;

import java.util.List;

import com.asiainfo.biapp.mcd.common.vo.plan.McdDimPlanType;
/**
 * 产品分类
 * 
 * @author wb 20160914
 *
 */

public interface McdDimPlanTypeDao {

	/**
	 * 根据父id获得所有的子类型
	 * @param pid
	 * @return
	 */
	List<McdDimPlanType> getChildrens(String pid);
	

	/**
	 * 查询所有产品类型
	 * @return
	 */
	List<McdDimPlanType> getAll();

}
