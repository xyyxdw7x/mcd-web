package com.asiainfo.biapp.mcd.tactics.dao;

import com.asiainfo.biapp.mcd.tactics.vo.MtlGroupInfo;

public interface IMcdMtlGroupInfoDao {

    /**
     * 根据客户群ID查找客户群
     * @param custgroupId
     * @return
     */
    MtlGroupInfo getMtlGroupInfo(String custgroupId);

}
