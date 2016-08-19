package com.asiainfo.biapp.mcd.tactics.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biframe.privilege.ICity;


@Repository("mpmUserPrivilegeService")
public class MpmUserPrivilegeServiceImpl implements IMpmUserPrivilegeService {
    private static Logger log = LogManager.getLogger();
    
    @Override
    public ICity getUserActualCity(String cityId) {
        // TODO Auto-generated method stub
        return null;
    }

}
