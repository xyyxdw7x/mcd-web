package com.asiainfo.biapp.mcd.common.service.plan;

import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanType;

public interface IMtlStcPlanService {
    
    /**
     * 根据渠道类型ID获取渠道
     * @param planType
     * @return
     */
    DimPlanType getPlanTypeById(String planType);

}
