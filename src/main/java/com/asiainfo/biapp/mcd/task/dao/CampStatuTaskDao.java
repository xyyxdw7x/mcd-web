package com.asiainfo.biapp.mcd.task.dao;

import java.util.List;
import java.util.Map;

public interface CampStatuTaskDao {
    /**
     * 更改已开始并已生成任务的策略的状态
     * @param srcStatu  原始状态
     * @param targetStatu  目标状态
     */
	public void updateStartedCampStatu(short srcStatu, short targetStatu);
	
	//查询过期的活动
	public List<Map<String, Object>> queryOutDateCamps();
	
    /**
     * 批量更新策略的状态
     * @param campIds
     * @param targetStatu
     */
	void batchupdateCampStatu(String campIds, short targetStatu);
	
	 /**
     * 批量更新任务的状态
     * @param taskIds
     * @param targetStatu
     */
	void batchupdateTaskStatu(String taskIds, short targetStatu);

	//将任务清单表中的过期任务状态拜年完成（90）
	public void updateTaskDate(String taskIds, short targetStatu);
	
	public void delOutDateScedule(String taskIds);

	void updateInMemDelCustGroupTab(String[] sqls);
   /**
    * 判断mcd_ad中某个表是否存在
    * @param tableName
    * @return
    */
	boolean queryInMemTableExists(String tableName);

	/**
	 * 将失效日期已到的客户群的状态变成失效状态
	 * @return
	 */
    void updateOutDateCustStatus();

}
