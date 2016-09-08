package com.asiainfo.biapp.mcd.common.constants;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.biframe.utils.config.Configure;

/**
 * Created on 2005-6-8
 *
 * <p>Title: </p>
 * <p>Description:营销管理系统常量定义�?/p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */

public class MpmCONST {

    /**
     * MCD_ZJ 分页参数
     */
    public static final int pageNum = 1;
    public final static int PAGE_SIZE = 10;  
    
    public static final String CAMPAIGN_SEG_INFO_SERVICE = "mpmCampSegInfoService";
	public static final String MPM_CAMPSEG_STAT_HDSP = "40";//活动审批�? （浙江--审批中）
	public static final String MPM_CAMPSEG_STAT_CHZT = "20";//策划状�?  （浙江--草稿）

	public static final String REALTIME_DRV_TYPE = "-8";
	public static final String NORMAL_DRV_TYPE = "-12";
	//活动场景类型
	public static final String SCENE_TYPE = "scene_type";
	public static final String SCENE_TYPE_REALTIME = "scene_type11";
	public static final String SCENE_TYPE_NORMAL = "scene_type12";
	
	public static final String MEMCACHE_TYPE_DISTRIBUTED = "distributed";//分布式
	
	/**复杂事件规则创建*/
	public static final String AIBI_MCD_CEP_CREATE_CODE = "AIBI_CEP_CREATE_CODE";
	/**复杂事件规则创建回调页面*/
	public static final String AIBI_MCD_CEP_CREATE_CODE_CALLBACK = "AIBI_CEP_CREATE_CODE_CALLBACK";

	/**
	 * 新建策略页面 选择政策翻页每页显示5条数据
	 */
	public final static int SMALL_PAGE_SIZE_LABEL = 5;
	   //活动步骤-客户属�?设置步骤
    public static final short MPM_SYS_ACTSTEP_DEF_ACTIVE_TEMPLET = 10;
	   //活动步骤-目标用户筛�?步骤
    public static final short MPM_SYS_ACTSTEP_DEF_CAMP_SELECT = 20;
    //活动步骤-活动用户剔除步骤
    public static final short MPM_SYS_ACTSTEP_DEF_CAMP_FILTER = 30;
    //活动终止（浙江--终止）
    public static final String MPM_CAMPSEG_STAT_HDZZ = "91";
    //活动暂停  （浙江--暂停）
    public static final String MPM_CAMPSEG_STAT_PAUSE = "59";
    //派单成功（浙江--执行中）
    public static final String MPM_CAMPSEG_STAT_DDCG = "54";
    //等待派单  （浙江--待执行）
    public static final String MPM_CAMPSEG_STAT_ZXZT = "50";
    //活动测试中（浙江--测试中）
    public static final String MPM_CAMPSEG_STAT_HDCS = "48";
    //活动完成  （浙江--完成）
    public static final String MPM_CAMPSEG_STAT_HDWC = "90";
    //活动测试不通过（浙江--测试不通过）
    public static final String MPM_CAMPSEG_STAT_HDCSBTG = "49";
    //审批要求整改   （浙江--审批退回）
    public static final String MPM_CAMPSEG_STAT_SPYG = "41";
    
    //任务待执�?
    public static final short TASK_STATUS_UNDO = 50;
    //任务执行�?
    public static final short TASK_STATUS_RUNNING = 51;
    //任务执行成功
    public static final short TASK_STATUS_SUCCESS = 54;
    //任务暂停
    public static final short TASK_STATUS_PAUSE = 59;
    //任务加载
    public static final short TASK_STATUS_LOADED = 70;
    //由于某种原因（如配额不足）自动暂停
    public static final short  TASK_STATUS_AUTOMATIC_PAUSE=71;
    //任务执行失败
    public static final short TASK_STATUS_FAIL = 53;
    //任务终止
    public static final short TASK_STATUS_STOP = 91;
    //任务完成
    public static final short TASK_STATUS_END = 90;
    //当天任务完成（没有可发的）
    public static final short TASK_STATUS_DAY_END = 79;
    //当为创建完成D表的时候对应任务的状态
    public static final short TASK_STATUS_INIT = 30;
    
    public static final String CHANNEL_TYPE_SMS = "901";
    //短信渠道
    public static final int CHANNEL_TYPE_SMS_INT = 901;
    //外呼
  	public static final String CHANNEL_TYPE_OUT_CALL = "913";
    
    public static final String MCD_PROPERTY_NAME = "ASIAINFO_PROPERTIES";
    
    
    //活动波次审批结果标志-通过
    public static final short MPM_SEG_APPROVE_RESULT_PASSED = 1;

    //活动波次审批结果标志-未�?�?
    public static final short MPM_SEG_APPROVE_RESULT_NOTPASSED = 2;
}
