package com.asiainfo.biapp.mcd.tactics.service;

import com.asiainfo.biapp.mcd.tactics.vo.MtlGroupInfo;

public interface IMcdMtlGroupInfoService {
    
    /**
     * 根据客户群ID查找客户群
     * @param custgroupId
     * @return
     */
    MtlGroupInfo getMtlGroupInfo(String custgroupId);
}
