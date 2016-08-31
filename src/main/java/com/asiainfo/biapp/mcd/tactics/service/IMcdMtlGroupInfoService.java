package com.asiainfo.biapp.mcd.tactics.service;

import com.asiainfo.biapp.mcd.common.vo.custgroup.MtlGroupInfo;

public interface IMcdMtlGroupInfoService {
    
    /**
     * 根据客户群ID查找客户群
     * @param custgroupId
     * @return
     */
    MtlGroupInfo getMtlGroupInfo(String custgroupId);
}
