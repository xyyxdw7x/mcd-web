package com.asiainfo.biapp.mcd.common.service.plan;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.dao.plan.MtlStcPlanDao;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanType;

/**
 * 渠道相关Service
 * @author AsiaInfo-jie
 *
 */
@Service("mtlStcPlanService")
public class MtlStcPlanServiceImpl implements IMtlStcPlanService {
    private static Logger log = LogManager.getLogger();

    @Resource(name = "mtlStcPlanDao")
    private MtlStcPlanDao mtlStcPlanDao;
 
    /**
     * 根据渠道类型ID获取渠道
     * @param planType
     * @return
     */
    @Override
    public DimPlanType getPlanTypeById(String planTypeId) {
        return mtlStcPlanDao.getPlanTypeById(planTypeId);
    }
}
