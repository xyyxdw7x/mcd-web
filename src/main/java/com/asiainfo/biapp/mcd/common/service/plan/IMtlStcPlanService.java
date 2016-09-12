package com.asiainfo.biapp.mcd.common.service.plan;

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

}
