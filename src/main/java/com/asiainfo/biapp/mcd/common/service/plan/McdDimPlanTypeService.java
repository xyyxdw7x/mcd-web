package com.asiainfo.biapp.mcd.common.service.plan;

import java.util.List;

import com.asiainfo.biapp.mcd.common.vo.plan.McdDimPlanType;

public interface McdDimPlanTypeService {

	/**
	 * 查询所有的产品类型，并将查询的产品类型集合转换成tree结构的list（每个元素的subType都被填充）
	 * 
	 * @return
	 */
	List<McdDimPlanType> getTreeList();

}
