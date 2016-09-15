package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.IDimCampsegTypeDao;
import com.asiainfo.biapp.mcd.tactics.service.IDimCampsegTypeService;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampType;
/**
 * 策略类型相关Service
 * @author AsiaInfo-jie
 *
 */
@Service("dimCampsegTypeService")
public class DimCampsegTypeServiceImpl implements IDimCampsegTypeService {
    private static Logger log = LogManager.getLogger();
    
    @Resource(name = "campsegTypeDao")
    private IDimCampsegTypeDao dimCampsegTypeDao;
    
    /**
     * add by jinl 20150717
     * @Title: getDimCampsegTypeName
     * @Description: 根据营销类型ID获取营销类型名称
     * @param @param campsegTypeId
     * @param @return
     * @param @throws Exception    
     * @return String 
     * @throws
     */
    @Override
    public String getDimCampsegTypeName(Short campsegTypeId) {
        McdDimCampType obj = null;
        try {
            obj = dimCampsegTypeDao.getDimCampsegType(campsegTypeId);
        } catch (Exception e) {
            log.error("", e);
        }
        return obj != null ? obj.getCampsegTypeName() : "";
    }
    /**
     * add by jinl 20150717
     * @Title: getDimCampsegType
     * @Description: 根据营销类型ID获取营销类型类
     * @param @param campsegTypeId
     * @param @return
     * @param @throws Exception    
     * @return McdDimCampType 
     * @throws
     */
    @Override
    public McdDimCampType getDimCampsegType(Short campsegTypeId) {
        McdDimCampType obj = null;
        try {
            obj = dimCampsegTypeDao.getDimCampsegType(campsegTypeId);
        } catch (Exception e) {
            log.error("", e);
        }
        return obj;
    }
    
	@Override
	public List<McdDimCampType> getAllDimCampsegType() throws Exception {
		List<McdDimCampType> list = null;
		try {
			list = dimCampsegTypeDao.getAllDimCampsegType();
		} catch (Exception e) {
			log.error("", e);
		}
		return list;
	}

}
