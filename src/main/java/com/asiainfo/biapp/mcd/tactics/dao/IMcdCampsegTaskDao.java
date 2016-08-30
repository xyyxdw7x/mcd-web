package com.asiainfo.biapp.mcd.tactics.dao;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.bean.McdCampsegTaskBean;
import com.asiainfo.biapp.mcd.model.McdCampsegTask;
import com.asiainfo.biapp.mcd.model.McdCampsegTaskHotPlanRela;
import com.asiainfo.biapp.mcd.model.MtlCampSeginfo;

public interface IMcdCampsegTaskDao {

	public MtlCampSeginfo getCampSegByTaskId(String taskId) throws Exception;

	public MtlCampSeginfo getPreCampSegPramByTaskId(String taskId) throws Exception;

	public String getWaveSql(String taskId) throws Exception;

	public Date getPseudoCustGroupUpdate() throws Exception;

	/**
	 * 获取所有任务
	 * @return
	 * @throws Exception
	 */
	public List getAll() throws Exception;

	/**
	 * 获取所有非场景营销任务
	 * @return
	 * @throws Exception
	 */
	public List<McdCampsegTask> getAllWithoutScene() throws Exception;

	public List getTasksByCampsegId(String campsegId) throws Exception;

	public void deleteTaskById(String taskId) throws Exception;

	public void deleteTask(McdCampsegTask task) throws Exception;

	public void saveTask(McdCampsegTask task) throws Exception;

	public List getTasksByCampsegIds(String campsegIds);
	/**
	 * 判断任务表中是否有该campsegId对应的数据
	 * 2013-7-5 16:19:34
	 * @author Mazh
	 * @param campSegId
	 * @return
	 */
	public Integer isHaveAtTaskByCampSegId(String campSegId) throws Exception;

	public void saveTaskByJdbc(McdCampsegTask task) throws Exception;

	/**
	 * 判断客户群是否是CM并且营销用语是否有替换符,得到替换符
	 * @author liyz
	 * @param campSegId
	 * @return
	 * */
	public Map<String, String> getCMForReplaceVar(String campsegId) throws Exception;

	public Byte2ObjectOpenHashMap<Short2ObjectOpenHashMap<Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>>>> loadCustListToMap(
			String ciCustgroupTabName, String string, String string2);

	/**
	 * 
	 * @param task
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	public Map findCampsegTaskMonitorList(McdCampsegTaskBean task, Integer curPage, Integer pageSize) throws Exception;

	public Map findSendOddPhoneList(String taskSendoddTabName, String phoneNumber, Integer curPage, Integer pageSize)
			throws Exception;

	public List<String> phoneListView(String taskSendoddTabName, String phoneNumber) throws Exception;

	/**
	 * 根据规则查询判断任务
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<McdCampsegTaskBean> findCampsegTaskByCampsegIds(List<String> ids) throws Exception;

	/**
	 * 获取单个任务
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public McdCampsegTask getCampSegTask(String taskId) throws Exception;

	/**
	 * 根据任务ID获取该任务对应的热点业务信息
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public McdCampsegTaskHotPlanRela getMcdCampsegTaskHotPlanRelaByTaskId(String taskId) throws Exception;

	/**
	 * 保存热点业务
	 * @param mcdCampsegTaskHotPlanRela
	 * @return
	 * @throws Exception
	 */
	public void saveMcdCampsegTaskHotPlanRela(McdCampsegTaskHotPlanRela mcdCampsegTaskHotPlanRela) throws Exception;

	/**
	 * 只获取今天的任务
	 * @return
	 * @throws Exception
	 */
	public List<McdCampsegTask> getAllTaskOfTodayWithoutScene() throws Exception;

	/**
	 * 根据策略ID 渠道ID获取任务
	 * @return
	 * @throws Exception
	 */
	public List<McdCampsegTask> findByCampsegIdAndChannelId(String campsegId,String channelId);
	/**
	 * 新增任务对应时间表
	 * @param taskId 任务ID
	 * dataDate 当前日期
	 * execStatus 状态
	 * @param planExecTime 计划执行时间
	 * @param tableNum 清单数据量
	 * @return
	 * @throws Exception
	 */
	public void insertMcdCampsegTaskDate(String taskId,String dataDate, short execStatus, int tableNum, java.util.Date planExecTime);
   
	public boolean checkTaskDataIsExist(String taskId, String dataDate);
	/**
     * 获取SQLFIRE内某 表的条数
     * @param custListTabName
     * @return
     */
	public int getSqlFireTableNum(String custListTabName);
    /**
     * 创建任务最终派单客户群清单表
     * @param taskSendoddTabName
     */
	public void createTaskSendoddTab(String taskSendoddTabName);
	
	/**
	 * 根据任务id获取策略信息
	 * @param taskIds
	 * @return
	 */
	public List<Map> getCampsegMsgByTaskIds(String taskIds[]);
    /**
     * 任务状态更改
     * @param campsegId
     * @param taskStatusStop
     */
	public void updateCampTaskStat(String campsegId, short type);
    /**
     * 删除免打扰过滤后生成的表
     * @param taskSendoddTabName
     */
	public void dropTaskSendoddTabName(String taskSendoddTabName);
    /**
     * 策略停止/暂停原因修改
     * @param taskSendoddTabName
     */
	public void updateTaskPauseComment(String campsegPId, String pauseComment);

	/**
	 * 根据任务状态，查询任务信息  add by lixq10 2016年5月31日10:39:20
	 * @param status
	 */
	public List getCampsegByStatus(short status);
	
	/**
	 * 根据策略id更新MCD_CAMPSEG_TASK表 add by lixq10 2016年5月31日16:00:27
	 * @param campsegId
	 * @param status
	 */
	public void updateCampsegTaskStatusById(String campsegId,String DuserName,int groupNum,short status);
	/**
	 * 根据策略id更新MTL_CAMPSEG_TASK_DATE表 add by lixq10 2016年5月31日16:00:27
	 * @param campsegId
	 * @param status
	 */
	public void updateCampsegTaskDataStatusById(String campsegId,int groupNum,short status);
	
	/**
	 * 查询Duser记录条数
	 * @param DuserName
	 * @return
	 */
	public int getDuserNum(String DuserName);
	
	/**
	 * 判断Duser表是否存在
	 * @param DuserName
	 * @return
	 */
	public int checkDuserIsExists(String DuserName);

}
