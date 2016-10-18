package com.asiainfo.biapp.mcd.common.custgroup.dao;

import java.util.BitSet;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.custgroup.vo.McdCustgroupDef;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.vo.CustInfo;
import com.asiainfo.biapp.mcd.exception.MpmException;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
public interface ICustGroupInfoDao {
	/**
	 * 点击更多按钮  关键字查询，分页方式展示我的客户群 
	 * @param currentUserId
	 * @return
	 */
	public int getMoreMyCustomCount(String currentUserId,String keyWords);
	/**
	 * 点击更多按钮  关键字查询，分页方式展示我的客户群 
	 * @param currentUserId
	 * @return
	 */
	public List<McdCustgroupDef> getMoreMyCustom(String currentUserId,String keyWords,Pager pager);
	/**
	 * 新建策略页面   选取营销人群    初始化我的客户群信息
	 * @param currentUserId
	 * @return
	 */
	public List<McdCustgroupDef> getMyCustGroup(String currentUserId);
	
	public List<Map<String,Object>> searchCustom(String contentType, Pager pager, String userId, String keywords);
	
	public String isCustomDeletable(String customGrpId, String userId);
	
	public List<Map<String,Object>> searchCustomDetail(String customGrpId);
	
	public String getExistQueueCfgId(String group_into_id, String queue_id)throws Exception;
	
	public String getMaxQueueCfgId()throws Exception;
	
	public int insertQueue(String group_into_id, String group_cycle, String queue_id,String data_date, String cfg_id,String group_table_name)throws Exception;
	
	public void deleteCustom(String customGrpId);
	
	public List<Map<String,Object>> queryQueueInfo();
	/**
	 * 获取原始客户群数量
	 * @param custGroupId
	 * @return
	 */
	public int getOriginalCustGroupNum(String custGroupId);
	/**
	 * @return
	 */
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
	 * 根据客户群ID查询是否存在该客户群任务
	 * @param customGroupId
	 * @return
	 */
	public List<Map<String,Object>> getSqlLoderISyncDataCfg(String customGroupId);
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
	void insertSqlLoderISyncDataCfg(String fileName, String fileNameVerf, String customGroupName,String mtlCuserTableName, String ftpStorePath, String filenameTemp, String customGroupId);
    /**
     * add by jinl 20150717
     * @Title: getTargetCustomerbase
     * @Description: 获取"目标客户群"信息
     * @param @param campsegId
     * @param @return
     * @param @throws Exception    
     * @return List 
     * @throws
     */
    public List<Map<String,Object>> getTargetCustomerbase(String campsegId);
    /**
     * 根据客户群ID查找客户群
     * @param custgroupId
     * @return
     */
    public McdCustgroupDef getMtlGroupInfo(String custgroupId);
    /**
     * @Title: getDataDateCustomNum
     * @Description: 最新数据日期，初始客户群规模
     * @param @param campsegId
     * @param @return    
     * @return List<Map<String,Object>> 
     * @throws
     */
    public List<Map<String, Object>> getDataDateCustomNum(String campsegId);
    /**
     * 详情页  查询原始客户群数量
     * @param custom_group_id
     * @return
     */
    public int getOriCustGroupNum(String custom_group_id);
    
    /**
     * 根据客户群编码获取客户群清单信息
     * @param customgroupid
     * @return
     */
	List<Map<String,Object>> getMtlCustomListInfo(String customgroupid); 
	/**
	 * 
	 * @param bussinessLableSql 业务标签拼装的SQL  过滤黑名单
	 * @param basicEventSql  基础标签拼装的SQL
	 * @param channelId   渠道ID
	 * @param campsegTypeId  策略类型ID 
	 * @return
	 */
	public List<Map<String,Object>> getAfterFilterCustGroupListInMem(String bussinessLableSql,String basicEventSql,String channelId, int campsegTypeId,String customgroupid,String orderProductNo,String excludeProductNo);
	
	/**
	 * 免打扰  频次过滤
	 * @param bussinessLableSql
	 * @param basicEventSql
	 * @param channelId
	 * @param campsegTypeId
	 * @param customgroupid
	 * @param orderProductNo
	 * @param excludeProductNo
	 * @param cityType:地市接触频次：15天   省公司接触频次：30天
	 * @param frequencyTime:接触次数
	 * @param updateCycle：客户群周期
	 * @return
	 */
	public List<Map<String,Object>> getAfterBotherAvoid1InMem(String bussinessLableSql,String basicEventSql,String channelId, int campsegTypeId,String customgroupid,String orderProductNo,String excludeProductNo,int avoidBotherFlag,int contactControlFlag,String cityId,String cityType,String frequencyTime,int updateCycle,String campsegId);
	List<Map<String,Object>> getAfterBotherAvoid1(String bussinessLableSql, String basicEventSql, String channelId, int campsegTypeId,
			String customgroupid, String orderProductNo, String excludeProductNo, int avoidBotherFlag,
			int contactControlFlag, String cityId, String cityType, String frequencyTime, int updateCycle,String campsegId);
	
	 /**
     * 根据代替SQLFIRE内的表在MCD里创建表的同义词
     * @param mtlCuserTableName
     */
    public void addCreateSynonymTableMcdBySqlFire(String mtlCuserTableName)  throws Exception ;
    
    /**
	 * 插入清单表新方式
	 * @param customgroupid
	 * @param bussinessLableSql
	 * @param ARPUSql
	 * @param orderProductNo
	 * @param excludeProductNo
	 * @return
     * @throws Exception 
	 */
	public void insertCustGroupNewWay(String customgroupid,String bussinessLableSql,String ARPUSql,String orderProductNo,String excludeProductNo,String tableName,boolean removeRepeatFlag) throws Exception;
	/**
     * 根据客户群id查询客户群信息
     * @param custGroupId
     * @return
     */
	McdCustgroupDef getCustGroupInfoById(String custGroupId);
	
	/**
	 * 根据客户群名称查询客户群定义信息
	 * @param custgroupName 客户群名称
	 * @author luoch
	 * @return
	 */
	public List<McdCustgroupDef> getCustgroupByName(String custgroupName);
	
	/**
	 * 根据客户群清单表名，查询出项目客户群数量信息 add by zhanghy2 at 2015-12-06 because of huge custom group
	 */
	int getCustInfoCountInMem(String sql);
	
	   public int getGroupSequence(String cityid);
	    
	    /**
	     * 根据COC传递数据，修改或新增客户群信息表
	     * @param custInfoBean  详细信息封装累
	     * @return
	     * @throws Exception
	     */
	    public void updateMtlGroupinfo(CustInfo custInfoBean);
	    /**
	     * 执行传过来的SQL语句，并注入参数
	     * @param mtlCuserTableName   清单表名
	     * @param customGroupDataDate  统计周期
	     * @param customGroupId   客户群ID
	     * @param rowNumberInt  客户数
	     * @param dataStatus 数据状态
	     * @param newDate 数据生成时间
	     * @param exceptionMessage   异常信息 
	     * @return
	     * @throws Exception
	     */
	    public void savemtlCustomListInfo(String mtlCuserTableName,String customGroupDataDate, String customGroupId, int rowNumberInt,int dataStatus, Date newDate, String exceptionMessage) throws Exception ;
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
		 * 将客户群数据插入到清单表中
		 * @param clearTable 插入前是否清空数据
		 * @param data 客户群数据
		 * @param tabName 清单表名
		 * @param date
		 * @return 插入数据条数
		 */
		public int insertInMemCustPhoneNoToTab(boolean clearTable, Byte2ObjectOpenHashMap<Short2ObjectOpenHashMap<BitSet>> data, String tabName, String date) throws Exception;
		
		/**
		 * 更新i_sync_data_cfg表的RUN_BEGIN_TIME字段
		 * @param mtlCuserTableName
		 * @param begindate
		 */
		public void updateSqlLoderISyncDataCfgBegin(String mtlCuserTableName, String begindate) throws Exception;
		
		/**
		 * 更新i_sync_data_cfg表的RUN_END_TIME字段
		 * @param mtlCuserTableName
		 * @return
		 */
		public void updateSqlLoderISyncDataCfgEnd(String mtlCuserTableName, String enddate) throws Exception;
		
		/**
		 * 执行导入sqlldr命令
		 * @param filepath
		 * @param fineNamePrefix
		 * @return
		 */
		public void executeSqlldr(String filepath, String fineNamePrefix) throws Exception ;
		
	    /**
	     * 查看该任务执行信息
	     * @param mtlCuserTableName
	     * @return
	     */
	    public List<Map<String,Object>> getSqlLoderISyncDataCfgEnd(String mtlCuserTableName);
	    /**
	     * sqlLoder导入完成后更改状态
	     * @param customGroupId 客户群ID
	     */
	    public void updateSqlLoderISyncDataCfgStatus(String customGroupId);
	    /**
	     * 根据COC传递数据，创建客户群推送信息表 
	     * @param customGroupId  客户群ID
	     * @param userId  创建人ID
	     * @param pushToUserId 推送目标人ID
	     * @return
	     * @throws Exception
	     */
	    public void addMtlGroupPushInfos(String customGroupId, String userId,String pushToUserId);

	    /**
	     * 更新客户群状态
	     * @param tableName
	     * @param custGroupId
	     */
	    public void updateMtlGroupStatus(String tableName,String custGroupId) throws Exception;
	    
		/**
		 * 更新mcd_custgroup_tab_list表custom_num字段值
		 * @param tableName
		 */
		public void updatesavemtlCustomListNum(String tableName) throws Exception;
		
	    public void addInMemCreateCustGroupTab(String sql)throws MpmException;
	    /**
	     * 判断当前分区是否存在
	     * @param tableName 表名
	     * @param partitionName 分区名
	     * @return
	     */
        public List<Map<String, Object>> getInMemcheckPartitionIsExist(String tableName, String partitionName);
        /**
         * 判断是否是分区表
         * @param tableName
         * @return
         */
        public List<Map<String, Object>> getInMemcheckTableIsPartition(String tableName);
        /**
         * 根绝客户群ID，日期查询是否有数据
         * @param customGroupId
         * @param customGroupDataDate
         * @return
         */
        public List<Map<String, Object>> getMtlCustomListInfo(String customGroupId, String customGroupDataDate);
        /**
         * 重复传递客户群，删除之前的客户群推送信息表
         * @param customGroupId  客户群ID
         * @return
         * @throws Exception
         */
        public void deleteMtlGroupPushInfos(String customGroupId);
        /**
         * AD库里执行语句
         * @param creatMtlCuserSql
         */
        public void addInMemSql(String creatMtlCuserSql);
        /**
         * 根据代替MCD_AD内的表在MCD里创建表的同义词
         * @param mtlCuserTableName
         * @throws Exception 
         */
        public void addSynonymTableMcdBySqlFire(String mtlCuserTableName) throws Exception;
        /**
         * 批量执行语句sqlFire
         * @param inertSql  插入语句
         * @param columnTypeList 每个字段应该对应的字段类型LIST
         * @param txtList  txt文档每行数据LIST
         * @param customGroupDataDate 
         */
        public void addInMembatchExecute(String inertSql, List<String> columnTypeList, List<String> txtList,String customGroupDataDate);
        /**
         * 根据日期查询某表数量
         * @param tableName  表名
         * @param customGroupDataDate  日期
         * @return
         */
        public int getInMemTableNameNum(String tableName, String customGroupDataDate);
        /**
         * 查询客户群清单表， 某周期总条数
         * @param custListTabName
         * @param dataDate
         * @return
         */
        public int getInMemCustomListInfoNum(String custListTabName, String dataDate);
        /**
         * 执行新增SQL语句
         * @param insertSql
         * @param values 
         */
        public void addInMemExecute(String insertSql, Object[] values);
		
		/**
		 * 根据客户群id获得客户群画像
		 * @param custgroupId
		 * @return
		 * @throws Exception
		 */
		public List<String> getCustGroupPortrait(String custgroupId) throws Exception;
}
