package com.asiainfo.biapp.mcd.tactics.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biapp.mcd.util.jdbcPage.Pager;

public interface IMpmCampSegInfoDao {
    /**
     * 策略管理查询分页
     * @param 
     * @return
     */
    public List searchIMcdCampsegInfo(MtlCampSeginfo segInfo, Pager pager);
}
