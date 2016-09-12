package com.asiainfo.biapp.mcd.tactics.service;

import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampType;

public interface IDimCampsegTypeService {
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
    String getDimCampsegTypeName(Short campsegTypeId);
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
    McdDimCampType getDimCampsegType(Short campsegTypeId);

}
