package com.asiainfo.biapp.mcd.custgroup.service;

import java.util.Date;

import com.asiainfo.biapp.mcd.custgroup.bean.CustInfoBean;

/**
 * Created on Oct 22, 2007 10:54:03 AM
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public interface IMtlCustGroupService {

	public int getGroupSequence(String cityid);
	
	/**
	 * 根据COC传递数据，修改或新增客户群信息表
	 * @param custInfoBean  详细信息封装累
	 * @return
	 * @throws Exception
	 */
	public void updateMtlGroupinfo(CustInfoBean custInfoBean);
	/**
	 * 执行传过来的SQL语句，并注入参数
	 * @param mtlCuserTableName   清单表名
	 * @param customGroupDataDate  统计周期
	 * @param customGroupId   客户群ID
	 * @param rowNumberInt  客户数
	 * @param dataStatus 数据状态
	 * @param newDate 数据生成时间
	 * @param exceptionMessage	 异常信息 
	 * @return
	 * @throws Exception
	 */
	public void savemtlCustomListInfo(String mtlCuserTableName,String customGroupDataDate, String customGroupId, int rowNumberInt,int dataStatus, Date newDate, String exceptionMessage);

	public void updateMtlGroupAttrRel(String customGroupId,String columnName,String columnCnName,String columnDataType,String columnLength,String mtlCuserTableName);
    /**
     * 
     * @param fileNameCsv  导入文件名称
     * @param fileNameVerf 验证文件民称
     * @param customGroupName  客户群名称
     * @param mtlCuserTableName 客户群所存表名称
     * @param filenameTemp 验证文件地址
     * @param ftpStorePath FTP需要导入的文件地址
     * @param customGroupId 客户群ID
     */
	public void insertSqlLoderISyncDataCfg(String fileNameCsv, String fileNameVerf, String customGroupName, String mtlCuserTableName, String ftpStorePath, String filenameTemp, String customGroupId);
    /**
     * 查看SQLLoader文件导入是否成功了
     * @param mtlCuserTableName
     * @return
     */
	public Boolean getSqlLoderISyncDataCfgEnd(String mtlCuserTableName);
    /**
     * sqlLoder导入完成后更改状态
     * @param customGroupId 客户群ID
     */
	public void updateSqlLoderISyncDataCfgStatus(String customGroupId);
    /**
     * 根据代替SQLFIRE内的表在MCD里创建表的同义词
     * @param mtlCuserTableName
     */
    public void createSynonymTableMcdBySqlFire(String mtlCuserTableName);
    
	public void addMtlGroupPushInfos(String customGroupId, String userId,
			String pushToUserId);
}
