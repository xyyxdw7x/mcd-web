/**   
 * @Title: IDimCampsegTypeDao.java
 * @Package com.asiainfo.biapp.mcd.dao
 * @Description:
 * @author A18ccms A18ccms_gmail_com   
 * @date 2015-7-17 上午10:16:41
 * @version V1.0   
 */
package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampType;

/**
 * @ClassName: IDimCampsegTypeDao
 * @Description: 营销类型Dao
 * @author jinlong
 * @date 2015-7-17 上午10:16:41
 * 
 */
public interface IDimCampsegTypeDao {
	
	/**
	 * 通过营销类型标识获取营销类型对象
	 * @param drvTypeId
	 * @return 营销类型对象
	 * @throws Exception
	 */
	public McdDimCampType getDimCampsegType(Short campsegTypeId) throws Exception;

	/**
	 * 获取所有的营销类型列表
	 * @return
	 * @throws Exception
	 */
	public List<McdDimCampType> getAllDimCampsegType() throws Exception;
}
