package com.asiainfo.biapp.mcd.tactics.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.mcd.common.vo.custgroup.MtlGroupInfo;
import com.asiainfo.biapp.mcd.tactics.dao.IMcdMtlGroupInfoDao;
import com.asiainfo.biapp.mcd.tactics.service.IMcdMtlGroupInfoService;

@Repository("mcdMtlGroupInfoService")
public class McdMtlGroupInfoServiceImpl implements IMcdMtlGroupInfoService{
    @Resource(name="mcdMtlGroupInfoDao")
    private IMcdMtlGroupInfoDao mcdMtlGroupInfoDao;
    /**
     * 根据客户群ID查找客户群信息
     */
    @Override
    public MtlGroupInfo getMtlGroupInfo(String custgroupId) {
        return mcdMtlGroupInfoDao.getMtlGroupInfo(custgroupId);
    }

}
