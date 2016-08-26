package com.asiainfo.biapp.mcd.custgroup.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.custgroup.bean.CustInfoBean;


public interface IMtlCustGroupJdbcDao {

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
	/**
	 * 根据COC传递数据，修改或新增客户群与属性对应关系表
	 * @param customGroupId  客户群ID
	 * @param columnName  属性字段名
	 * @param columnCnName  属性中文名称
	 * @param columnDataType  属性字段类型
 	 * @param columnLength  属性字段长度
	 * @param mtlCuserTableName 客户群清单表名
	 * @return
	 * @throws Exception
	 */
	public void updateMtlGroupAttrRel(String customGroupId, String columnName,
			String columnCnName, String columnDataType, String columnLength, String mtlCuserTableName);
	
	/**
	 * 根据客户群ID查询是否存在该客户群任务
	 * @param customGroupId
	 * @return
	 */
	public List getSqlLoderISyncDataCfg(String customGroupId);
	/**
	 * 周期性SQLLODER任务，更新任务信息
	 * @param fileNameCsv
	 * @param fileNameVerf
	 * @param customGroupName
	 * @param mtlCuserTableName
	 * @param ftpStorePath
	 * @param filenameTemp
	 * @param customGroupId
	 */
	public void updateSqlLoderISyncDataCfg(String fileNameCsv,String fileNameVerf, String customGroupName,String mtlCuserTableName, String ftpStorePath, String filenameTemp,String customGroupId);
    /**
     * 查看该任务执行信息
     * @param mtlCuserTableName
     * @return
     */
	public List getSqlLoderISyncDataCfgEnd(String mtlCuserTableName);
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
    /**
     * 
     * @param fileNameCsv  导入文件名称
     * @param fileNameVerf 验证文件民称
     * @param customGroupName  客户群名称
     * @param mtlCuserTableName 客户群所存表名称
     * @param ftpStorePath FTP需要导入的文件地址
     * @param filenameTemp 验证文件地址
     * @param customGroupId 客户群ID
     */
	public void insertSqlLoderISyncDataCfg(String fileName, String fileNameVerf, String customGroupName, String mtlCuserTableName, String ftpStorePath, String filenameTemp, String customGroupId);
	/**
	 * 根据COC传递数据，创建客户群推送信息表 
	 * @param customGroupId  客户群ID
	 * @param userId  创建人ID
	 * @param pushToUserId 推送目标人ID
	 * @return
	 * @throws Exception
	 */
	public void addMtlGroupPushInfos(String customGroupId, String userId,String pushToUserId);

}
