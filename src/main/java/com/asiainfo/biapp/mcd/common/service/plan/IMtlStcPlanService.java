package com.asiainfo.biapp.mcd.common.service.plan;

import java.util.List;

import com.asiainfo.biapp.mcd.common.vo.plan.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.vo.plan.McdPlanDef;

public interface IMtlStcPlanService {
    
    /**
     * 根据渠道类型ID获取渠道
     * @param planType
     * @return
     */
    McdDimPlanType getPlanTypeById(String planType);
	/**
	 * 根据plan_id查询推荐业务
	 * @param planID
	 * @return
	 */
	public McdPlanDef getMtlStcPlanByPlanID(String planID);
	
	/**
	 *  新建策略页面---初始化产品类型
	 * @return
	 */
	public  List<McdDimPlanType> initDimPlanType();
	
	/**
	 * 查询所有的产品类型，并将查询的产品类型集合转换成tree结构的list（每个元素的subType都被填充）
	 * 
	 * @return
	 */
	List<McdDimPlanType> getTreeList();

}
