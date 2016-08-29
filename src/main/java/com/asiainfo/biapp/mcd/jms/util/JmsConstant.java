package com.asiainfo.biapp.mcd.jms.util;

/**
 * 常量类
 * @author zhangyb5
 *
 */
public class JmsConstant {
	//缓存中KEY的类型
	public static final String CACHE_KEY_CUSTOMERS_LISTMAP = "CACHE_KEY_CUSTOMERS_LISTMAP";
	public static final String CACHE_KEY_CUSTOMERS_CONTACT_CTL_LISTMAP = "CACHE_KEY_CUSTOMERS_CONTACT_CTRL_LISTMAP";
	public static final String CACHE_KEY_LOCAL_NUMBER_SEG = "CACHE_KEY_LOCAL_NUMBER_SEG";
	public static final String CACHE_KEY_ACTIVITY_CONTAIN_OUT_PROVINCE_FLAG = "CACHE_KEY_ACTIVITY_CONTAIN_OUT_PROVINCE_FLAG";
	public static final String CACHE_KEY_ACTIVITY_SUPPORT_WIFI_FLAG = "CACHE_KEY_ACTIVITY_SUPPORT_WIFI_FLAG";
	public static final String CACHE_KEY_ACTIVITY_SUPPORT_WIFI_LISTMAP = "CACHE_KEY_ACTIVITY_SUPPORT_WIFI_LISTMAP";
	public static final String CACHE_KEY_ACTIVITY_CUSTGROUP_TYPE = "CACHE_KEY_ACTIVITY_CUSTGROUP_TYPE";
	public static final String CACHE_KEY_ACTIVITY_STATUS = "CACHE_KEY_ACTIVITY_STATUS";
	public static final String CACHE_KEY_ACTIVITY_TASKID = "CACHE_KEY_ACTIVITY_TASKID";
	public static final String CACHE_KEY_ACTIVITY_TASK_SENDODD_TAB = "CACHE_KEY_ACTIVITY_TASK_SENDODD_TAB";
	public static final String CACHE_KEY_ACTIVITY_CUST_AND_VAR = "CACHE_KEY_ACTIVITY_CUST_AND_VAR";
	public static final String CACHE_KEY_REJECT_ACTIVITY_CUSTLIST = "CACHE_KEY_REJECT_ACTIVITY_CUSTLIST";
	public static final String CACHE_KEY_REJECT_ACTIVITY_IDS = "CACHE_KEY_REJECT_ACTIVITY_IDS";
	public static final String CACHE_KEY_REJECT_ACTIVITY_BASEID = "CACHE_KEY_REJECT_ACTIVITY_BASEID";
	public static final String CACHE_KEY_ACTIVITY_CUST_LIST_TAB = "CACHE_KEY_ACTIVITY_CUST_LIST_TAB";
	public static final String CACHE_KEY_ACTIVITY_PLAN_ID = "CACHE_KEY_ACTIVITY_PLAN_ID";
	public static final String CACHE_KEY_ACTIVITY_IS_OUT_BOUND = "CACHE_KEY_ACTIVITY_IS_OUT_BOUND";
	public static final String CACHE_KEY_ACTIVITY_PLAN_CUST_LISTMAP = "CACHE_KEY_ACTIVITY_PLAN_CUST_LISTMAP";
	public static final String CACHE_KEY_ACTIVITY_CHANNEL_TYPE_ID = "CACHE_KEY_ACTIVITY_CHANNEL_TYPE_ID";
	public static final String CACHE_KEY_OPPORTUNITY_DATA = "CACHE_KEY_OPPORTUNITY_DATA";
	public static final String CACHE_KEY_EVENT_DATA = "CACHE_KEY_EVENT_DATA";
	public static final String CACHE_KEY_TABLE_VAR_DATA = "CACHE_KEY_TABLE_VAR_DATA";
	public static final String CACHE_KEY_CHANCE_MATCHING_DATA = "CACHE_KEY_CHANCE_MATCHING_DATA";
	public static final String CACHE_KEY_CAMP_CONTENT = "CACHE_KEY_CAMP_CONTENT_";
	public static final String CACHE_KEY_AVOID_BOTHER_DATA = "CACHE_KEY_AVOID_BOTHER_DATA";
	public static final String CACHE_KEY_ACT_AVOID_BOTHER_TYPE_IDS = "CACHE_KEY_ACT_AVOID_BOTHER_TYPE_IDS_";
	public static final String CHCHE_KEY_ACTIVITY_CONTAIN_STATIC_CUSTGROUP = "CHCHE_KEY_ACTIVITY_CONTAIN_STATIC_CUSTGROUP";
	public static final String CACHE_KEY_CEP_REPLACE_VARS_TAB_DATA = "CACHE_KEY_CEP_REPLACE_VARS_TAB_DATA";
	public static final String CACHE_KEY_CEP_REPLACE_VARS_MDA_DATA = "CACHE_KEY_CEP_REPLACE_VARS_MDA_DATA";
	public static final String CACHE_KEY_COMMON_MAP = "CACHE_KEY_COMMON_MAP";
	public static final String CACHE_KEY_COMMON_MAP_EXPIRE_TIME = "CACHE_KEY_COMMON_MAP_EXPIRE_TIME";
	public static final String CACHE_KEY_CEP_RECEIVE_THREAD_DATA = "CACHE_KEY_CEP_RECEIVE_THREAD_DATA";
	//接触控制相关
	public static final String CACHE_KEY_CONTACT_RULE = "CACHE_KEY_CONTACT_RULE";//用户级
	public static final String CACHE_KEY_VIP_CONTACT_RULE = "CACHE_KEY_VIP_CONTACT_RULE";//VIP用户级
	public static final String CACHE_KEY_ACTIVITY_CONTACT_RULE = "CACHE_KEY_ACTIVITY_CONTACT_RULE";//活动级
	public static final String CACHE_KEY_CONTACT_DATA = "CACHE_KEY_CONTACT_DATA";//用户级接触数据
	public static final String CACHE_KEY_ACTIVITY_CONTACT_DATA = "CACHE_KEY_ACTIVITY_CONTACT_DATA";//活动级接触数据（固定周期）
	public static final String CACHE_KEY_ACTIVITY_CONTACT_DATA_INTERVAL = "CACHE_KEY_ACTIVITY_CONTACT_DATA_INTERVAL";//活动级接触数据（间隔）
	public static final String CACHE_KEY_ACTIVITY_WAVETREE_DATA = "CACHE_KEY_ACTIVITY_WAVETREE_DATA";//活动波次树
	public static final String CACHE_KEY_VIP_CUST_DATA = "CACHE_KEY_VIP_CUST_DATA";//VIP用户数据
	public static final String CACHE_KEY_ACTIVITY_TYPE = "ACTIVITY_TYPE";//活动类型
	public static final byte CONTACT_TYPE_TIME_YEAR = 7;
	public static final byte CONTACT_TYPE_TIME_MON = 3;
	public static final byte CONTACT_TYPE_TIME_WEEK = 2;
	public static final byte CONTACT_TYPE_TIME_DAY = 1;
	public static final byte CONTACT_TYPE_TIME_HALFYEAR = 6;
	public static final byte CONTACT_TYPE_TIME_QUARTER = 5;
	public static final byte CONTACT_TYPE_TIME_TWINMON = 4;
	public static final byte CONTACT_TYPE_TIME_INTERVAL = 20;
	//接触数据类型
	public static final int CONTACT_TYPE_NONE = 0;
	public static final int CONTACT_TYPE_USER = 1;//用户级
	public static final int CONTACT_TYPE_ACTIVITY = 2;//活动级
	//接触次数常量
	public static final short UNIT_CONTACT_TIMES = 1;//接触次数1
	public static final short ZERO_CONTACT_TIMES = 0;//接触次数0
	//接触数据持久化类型
	public static final short PERSIST_TYPE_ADD = 1;//持久化：增加
	public static final short PERSIST_TYPE_UPDATE = 2;//持久化：更新
	public static final short PERSIST_TYPE_DELETE = 3;//持久化：删除
	//接触数据维护周期类型
	public static final short MAINTAIN_TYPE_YEAR = 7;
	public static final short MAINTAIN_TYPE_MON = 3;
	public static final short MAINTAIN_TYPE_WEEK = 2;
	public static final short MAINTAIN_TYPE_DAY = 1;
	public static final short MAINTAIN_TYPE_HALFYEAR = 6;
	public static final short MAINTAIN_TYPE_QUARTER = 5;
	public static final short MAINTAIN_TYPE_TWINMON = 4;
	//接触数据维护SQL
	public static final String ADD_CONTACT_DATA_TIME_SQL = "insert into MCD_CONTACT_DATA_TIME(PHONE_NO,MON_CONTACT_COUNT,WEEK_CONTACT_COUNT,DAY_CONTACT_COUNT,YEAR_CONTACT_COUNT,HALFYEAR_CONTACT_COUNT,QUARTER_CONTACT_COUNT,TWINMON_CONTACT_COUNT) values ('?1',?2,?3,?4,?5,?6,?7,?8)";
	public static final String ADD_ACTIVITY_CONTACT_DATA_TIME_SQL = "insert into MCD_ACTIVITY_CONTACT_DATA_TIME(ACTIVITY_CODE,PHONE_NO,MON_CONTACT_COUNT,WEEK_CONTACT_COUNT,DAY_CONTACT_COUNT,YEAR_CONTACT_COUNT,HALFYEAR_CONTACT_COUNT,QUARTER_CONTACT_COUNT,TWINMON_CONTACT_COUNT) values ('?0','?1',?2,?3,?4,?5,?6,?7,?8)";
	public static final String ADD_ACTIVITY_CONTACT_DATA_TIME_INTERVAL_SQL = "insert into MCD_ACTIVITY_CON_DATA_INTERVAL(ACTIVITY_CODE,PHONE_NO,LAST_CONTACT_TIME) values ('?0','?1',?2)";
	public static final String UPDATE_CONTACT_DATA_TIME_SQL = "update MCD_CONTACT_DATA_TIME set MON_CONTACT_COUNT=(MON_CONTACT_COUNT+1),WEEK_CONTACT_COUNT=(WEEK_CONTACT_COUNT+1),DAY_CONTACT_COUNT=(DAY_CONTACT_COUNT+1),YEAR_CONTACT_COUNT=(YEAR_CONTACT_COUNT+1),HALFYEAR_CONTACT_COUNT=(HALFYEAR_CONTACT_COUNT+1),QUARTER_CONTACT_COUNT=(QUARTER_CONTACT_COUNT+1),TWINMON_CONTACT_COUNT=(TWINMON_CONTACT_COUNT+1) where PHONE_NO='?1'";
	public static final String UPDATE_ACTIVITY_CONTACT_DATA_TIME_SQL = "update MCD_ACTIVITY_CONTACT_DATA_TIME set MON_CONTACT_COUNT=(MON_CONTACT_COUNT+1),WEEK_CONTACT_COUNT=(WEEK_CONTACT_COUNT+1),DAY_CONTACT_COUNT=(DAY_CONTACT_COUNT+1),YEAR_CONTACT_COUNT=(YEAR_CONTACT_COUNT+1),HALFYEAR_CONTACT_COUNT=(HALFYEAR_CONTACT_COUNT+1),QUARTER_CONTACT_COUNT=(QUARTER_CONTACT_COUNT+1),TWINMON_CONTACT_COUNT=(TWINMON_CONTACT_COUNT+1) where PHONE_NO='?1' and ACTIVITY_CODE='?0'";
	public static final String UPDATE_ACTIVITY_CONTACT_DATA_TIME_INTERVAL_SQL = "update MCD_ACTIVITY_CON_DATA_INTERVAL set LAST_CONTACT_TIME=?2 where PHONE_NO='?1' and ACTIVITY_CODE='?0'";
	public static final String QUIK_DELETE_TABLE_DATA_SQL = "ALTER TABLE ? ACTIVATE NOT LOGGED INITIALLY WITH EMPTY TABLE";
	public static final String ALTER_TABLE_NOT_LOGGED_DB2_SQL = "ALTER TABLE ? ACTIVATE NOT LOGGED INITIALLY";
	public static final String ALTER_TABLE_NOT_LOGGED_ORACLE_SQL = "ALTER TABLE ? NOLOGGING";

	public static final String UPDATE_TABLE_YEAR_SQL = "update ? set YEAR_CONTACT_COUNT=0,HALFYEAR_CONTACT_COUNT=0,QUARTER_CONTACT_COUNT=0,TWINMON_CONTACT_COUNT=0,MON_CONTACT_COUNT=0,DAY_CONTACT_COUNT=0";
	public static final String UPDATE_TABLE_MON_SQL = "update ? set MON_CONTACT_COUNT =0,DAY_CONTACT_COUNT=0";
	public static final String UPDATE_TABLE_WEEK_SQL = "update ? set WEEK_CONTACT_COUNT =0";
	public static final String UPDATE_TABLE_DAY_SQL = "update ? set DAY_CONTACT_COUNT=0";
	public static final String UPDATE_TABLE_HALFYEAR_SQL = "update ? set HALFYEAR_CONTACT_COUNT=0,QUARTER_CONTACT_COUNT=0,TWINMON_CONTACT_COUNT=0,MON_CONTACT_COUNT=0,DAY_CONTACT_COUNT=0";
	public static final String UPDATE_TABLE_QUARTER_SQL = "update ? set QUARTER_CONTACT_COUNT=0,MON_CONTACT_COUNT=0,DAY_CONTACT_COUNT=0";
	public static final String UPDATE_TABLE_TWINMON_SQL = "update ? set TWINMON_CONTACT_COUNT=0,MON_CONTACT_COUNT=0,DAY_CONTACT_COUNT=0";
	public static final String UPDATE_TABLE_COMM_SQL = "update ? set CONTACT_COUNT=0";
	public static final String DELETE_ACTVITY_TABLE_DATA_SQL = "delete from MCD_ACTIVITY_CONTACT_DATA_TIME where ACTIVITY_CODE=?";
	public static final String DELETE_ACTVITY_TABLE_RULE_SQL = "delete from  MCD_ACTIVITY_CONTACT_RULE_DEF where ACTIVITY_CODE=?";
	public static final String MAINTAIN_TABLES = "MCD_CONTACT_DATA_TIME,MCD_ACTIVITY_CONTACT_DATA_TIME";//需要周期维护的表
	public static final String DELETE_ACTVITY_TABLE_DATA_INTERVAL_SQL = "delete from MCD_ACTIVITY_CON_DATA_INTERVAL where ACTIVITY_CODE=?";

	public static final String QUERY_ALL_ACTIVITY_RULE_SQL = "SELECT A.ACTIVITY_CODE,A.TYPE_ID,A.CONTACT_COUNT FROM MCD_ACTIVITY_CONTACT_RULE_DEF A JOIN MTL_CAMP_SEGINFO B ON A.ACTIVITY_CODE = B.CAMPSEG_ID WHERE B.CAMPSEG_STAT_ID IN (50,52,53,54,59)";
	public static final String QUERY_ACTIVITY_RULE_SQL = "SELECT A.TYPE_ID,A.CONTACT_COUNT FROM MCD_ACTIVITY_CONTACT_RULE_DEF A JOIN MTL_CAMP_SEGINFO B ON A.ACTIVITY_CODE = B.CAMPSEG_ID WHERE A.ACTIVITY_CODE = ? AND B.CAMPSEG_STAT_ID in (50,52,53,54,59)";
	public static final String QUERY_USER_CONTACT_DATA_SQL = "SELECT SUBSTR(phone_no,2,2) v1,SUBSTR(phone_no,4,4) v2,SUBSTR(phone_no,8,4) v3,YEAR_CONTACT_COUNT,MON_CONTACT_COUNT,WEEK_CONTACT_COUNT,DAY_CONTACT_COUNT,HALFYEAR_CONTACT_COUNT,QUARTER_CONTACT_COUNT,TWINMON_CONTACT_COUNT FROM MCD_CONTACT_DATA_TIME";
	public static final String QUERY_ALL_ACTIVITY_CONTACT_DATA_SQL = "SELECT ACTIVITY_CODE,SUBSTR(PHONE_NO,2,2) V1,SUBSTR(PHONE_NO,4,4) V2,SUBSTR(PHONE_NO,8,4) V3,YEAR_CONTACT_COUNT,MON_CONTACT_COUNT,WEEK_CONTACT_COUNT,DAY_CONTACT_COUNT,HALFYEAR_CONTACT_COUNT,QUARTER_CONTACT_COUNT,TWINMON_CONTACT_COUNT FROM MCD_ACTIVITY_CONTACT_DATA_TIME  A JOIN MTL_CAMP_SEGINFO B ON A.ACTIVITY_CODE=B.CAMPSEG_ID WHERE B.CAMPSEG_STAT_ID IN (53,54,59)";
	public static final String QUERY_ALL_ACTIVITY_CONTACT_DATA_INTERVAL_SQL = "SELECT ACTIVITY_CODE,SUBSTR(PHONE_NO,2,2) V1,SUBSTR(PHONE_NO,4,4) V2,SUBSTR(PHONE_NO,8,4) V3,LAST_CONTACT_TIME FROM MCD_ACTIVITY_CON_DATA_INTERVAL  A JOIN MTL_CAMP_SEGINFO B ON A.ACTIVITY_CODE=B.CAMPSEG_ID WHERE B.CAMPSEG_STAT_ID IN (53,54,59)";
	public static final String QUERY_CAN_COMPLETE_ACTIVITY_SQL = "SELECT CAMPSEG_ID,CAMPSEG_TYPE_ID ,CAMPSEG_STAT_ID FROM MTL_CAMP_SEGINFO WHERE 1=1 AND CAMP_CLASS = 1 AND END_DATE < ? AND CAMPSEG_STAT_ID < 90 AND CAMPSEG_STAT_ID >= 50";
	public static final String QUERY_CAN_COMPLETE_CAMPAIGN_SQL = "SELECT B.CAMP_ID FROM MTL_CAMP_BASEINFO B WHERE 1=1 AND B.CAMP_STATUS = 1 AND B.END_DATE < ? ";
	public static final String QUERY_ACTIVITY_WAVETREE_SQL = "SELECT S.CAMPSEG_ID,S.CAMPSEG_PID  FROM MTL_CAMP_SEGINFO S";
	public static final String QUERY_ACTIVITY_PARENTID_SQL = "SELECT T.CAMPSEG_PID FROM MTL_CAMP_SEGINFO T WHERE T.CAMPSEG_ID = ?";
	public static final String UPDAT_ACTIVITY_STATUS_WC_SQL = "UPDATE MTL_CAMP_SEGINFO SET CAMPSEG_STAT_ID = 90 WHERE CAMPSEG_ID = ?";
	public static final String URL = "URL";
	public static final String ACTIVITY_CODE = "activity_code";
	public static final String QUERY_USER_DATA_TO_MAP_TMP_SQL = "SELECT SUBSTR(product_no,2,2) v1,SUBSTR(product_no,4,4) v2,SUBSTR(product_no,8,4) v3 FROM {0} tmp";
	public static final String QUERY_USER_DATA_TO_MAP_TMP_SQL_VOLTDB = "SELECT SUBSTR(product_no,2,2) v1,SUBSTR(product_no,4,4) v2,SUBSTR(product_no,8,4) v3 FROM {0}";
	public static final String QUERY_VIP_CUST_DATA_SQL = "SELECT SUBSTR(PHONE_NO,2,2) v1,SUBSTR(PHONE_NO,4,4) v2,SUBSTR(PHONE_NO,8,4) v3 FROM {0} tmp ";

	//查询手机号，不分拆号码
	public static final String QUERY_CUST_PHONE_NO_DATA_SQL = "SELECT PHONE_NO FROM {0} tmp ";
	public static final String QUERY_CUST_PRODUCT_NO_DATA_SQL = "SELECT PRODUCT_NO FROM {0} tmp ";
	public static final String QUERY_USER_CONTACT_DATA_SQL2 = "SELECT PHONE_NO,YEAR_CONTACT_COUNT,MON_CONTACT_COUNT,WEEK_CONTACT_COUNT,DAY_CONTACT_COUNT,HALFYEAR_CONTACT_COUNT,QUARTER_CONTACT_COUNT,TWINMON_CONTACT_COUNT FROM MCD_CONTACT_DATA_TIME";
	public static final String QUERY_ALL_ACTIVITY_CONTACT_DATA_SQL2 = "SELECT ACTIVITY_CODE,PHONE_NO,YEAR_CONTACT_COUNT,MON_CONTACT_COUNT,WEEK_CONTACT_COUNT,DAY_CONTACT_COUNT,HALFYEAR_CONTACT_COUNT,QUARTER_CONTACT_COUNT,TWINMON_CONTACT_COUNT FROM MCD_ACTIVITY_CONTACT_DATA_TIME  A JOIN MTL_CAMP_SEGINFO B ON A.ACTIVITY_CODE=B.CAMPSEG_ID WHERE B.CAMPSEG_STAT_ID IN (53,54,59)";
	public static final String QUERY_ALL_ACTIVITY_CONTACT_DATA_INTERVAL_SQL2 = "SELECT ACTIVITY_CODE,PHONE_NO,LAST_CONTACT_TIME FROM MCD_ACTIVITY_CON_DATA_INTERVAL  A JOIN MTL_CAMP_SEGINFO B ON A.ACTIVITY_CODE=B.CAMPSEG_ID WHERE B.CAMPSEG_STAT_ID IN (53,54,59)";
	public static final String QUERY_ALL_SCENE_CONTACT_DATA_INTERVAL_SQL2 = "SELECT ACTIVITY_CODE,PHONE_NO,LAST_CONTACT_TIME FROM MCD_ACTIVITY_CON_DATA_INTERVAL  A JOIN DIM_CAMP_SCENE B ON A.ACTIVITY_CODE = B.SCENE_ID";
	public static final String QUERY_ALL_SCENE_CONTACT_DATA_SQL2 = "SELECT ACTIVITY_CODE,PHONE_NO,YEAR_CONTACT_COUNT,MON_CONTACT_COUNT,WEEK_CONTACT_COUNT,DAY_CONTACT_COUNT,HALFYEAR_CONTACT_COUNT,QUARTER_CONTACT_COUNT,TWINMON_CONTACT_COUNT FROM MCD_ACTIVITY_CONTACT_DATA_TIME  A  JOIN DIM_CAMP_SCENE B ON A.ACTIVITY_CODE = B.SCENE_ID";

	//场景营销
	public static final String QUERY_ACTIVITY_SCENETYPE_SQL = "SELECT T.SCENE_TYPE FROM MTL_CAMP_SEGINFO T WHERE T.CAMPSEG_ID = ?";
	public static final String QUERY_ALL_SCENE_RULE_SQL = "SELECT A.ACTIVITY_CODE,A.TYPE_ID,A.CONTACT_COUNT FROM MCD_ACTIVITY_CONTACT_RULE_DEF A JOIN DIM_CAMP_SCENE B ON A.ACTIVITY_CODE = B.SCENE_ID";
	public static final String QUERY_ALL_SCENE_CONTACT_DATA_INTERVAL_SQL = "SELECT ACTIVITY_CODE,SUBSTR(PHONE_NO,2,2) V1,SUBSTR(PHONE_NO,4,4) V2,SUBSTR(PHONE_NO,8,4) V3,LAST_CONTACT_TIME FROM MCD_ACTIVITY_CON_DATA_INTERVAL  A JOIN DIM_CAMP_SCENE B ON A.ACTIVITY_CODE = B.SCENE_ID";
	public static final String QUERY_ALL_SCENE_CONTACT_DATA_SQL = "SELECT ACTIVITY_CODE,SUBSTR(PHONE_NO,2,2) V1,SUBSTR(PHONE_NO,4,4) V2,SUBSTR(PHONE_NO,8,4) V3,YEAR_CONTACT_COUNT,MON_CONTACT_COUNT,WEEK_CONTACT_COUNT,DAY_CONTACT_COUNT,HALFYEAR_CONTACT_COUNT,QUARTER_CONTACT_COUNT,TWINMON_CONTACT_COUNT FROM MCD_ACTIVITY_CONTACT_DATA_TIME  A  JOIN DIM_CAMP_SCENE B ON A.ACTIVITY_CODE = B.SCENE_ID";

	//活动类型接触控制
	public static final String QUERY_ACTIVITY_TYPE_SQL = "SELECT T.CAMP_DRV_ID FROM MTL_CAMP_SEGINFO T WHERE T.CAMPSEG_ID = ?";
	public static final String QUERY_ALL_ACTIVITY_TYPE_RULE_SQL = "SELECT A.ACTIVITY_CODE,A.TYPE_ID,A.CONTACT_COUNT FROM MCD_ACTIVITY_CONTACT_RULE_DEF A JOIN DIM_CAMP_DRV_TYPE B ON A.ACTIVITY_CODE = CAST(B.CAMP_DRV_ID AS CHAR(30))";
	public static final String QUERY_ALL_ACTIVITY_TYPE_CONTACT_DATA_INTERVAL_SQL = "SELECT ACTIVITY_CODE,SUBSTR(PHONE_NO,2,2) V1,SUBSTR(PHONE_NO,4,4) V2,SUBSTR(PHONE_NO,8,4) V3,LAST_CONTACT_TIME FROM MCD_ACTIVITY_CON_DATA_INTERVAL  A JOIN DIM_CAMP_DRV_TYPE B ON A.ACTIVITY_CODE = CAST(B.CAMP_DRV_ID AS CHAR(30))";
	public static final String QUERY_ALL_ACTIVITY_TYPE_CONTACT_DATA_SQL = "SELECT ACTIVITY_CODE,SUBSTR(PHONE_NO,2,2) V1,SUBSTR(PHONE_NO,4,4) V2,SUBSTR(PHONE_NO,8,4) V3,YEAR_CONTACT_COUNT,MON_CONTACT_COUNT,WEEK_CONTACT_COUNT,DAY_CONTACT_COUNT,HALFYEAR_CONTACT_COUNT,QUARTER_CONTACT_COUNT,TWINMON_CONTACT_COUNT FROM MCD_ACTIVITY_CONTACT_DATA_TIME  A  JOIN DIM_CAMP_DRV_TYPE B ON A.ACTIVITY_CODE = CAST(B.CAMP_DRV_ID AS CHAR(30))";
	public static final String QUERY_ALL_ACTIVITY_TYPE_CONTACT_DATA_INTERVAL_SQL2 = "SELECT ACTIVITY_CODE,PHONE_NO,LAST_CONTACT_TIME FROM MCD_ACTIVITY_CON_DATA_INTERVAL  A JOIN DIM_CAMP_DRV_TYPE B ON A.ACTIVITY_CODE = CAST(B.CAMP_DRV_ID AS CHAR(30))";
	public static final String QUERY_ALL_ACTIVITY_TYPE_CONTACT_DATA_SQL2 = "SELECT ACTIVITY_CODE,PHONE_NO,YEAR_CONTACT_COUNT,MON_CONTACT_COUNT,WEEK_CONTACT_COUNT,DAY_CONTACT_COUNT,HALFYEAR_CONTACT_COUNT,QUARTER_CONTACT_COUNT,TWINMON_CONTACT_COUNT FROM MCD_ACTIVITY_CONTACT_DATA_TIME  A  JOIN DIM_CAMP_DRV_TYPE B ON A.ACTIVITY_CODE = CAST(B.CAMP_DRV_ID AS CHAR(30))";

	//事件类型
	public static final int EVENT_TYPE_SO = 1;
	public static final int EVENT_TYPE_FO = 2;
	public static final int EVENT_TYPE_DO = 3;

	//用户行为数据来源
	public static final int EVENT_DATA_SOURCE_NBS = 1;//NBS实时数据
	public static final int EVENT_DATA_SOURCE_LOCAL = 2;//MCD延迟后的数据
	//缓存时间
	public static final long CACHE_TIME = 10 * 60 * 60;
	//缓存数据初始化完成标识，0-未完成，1-完成
	public static final String CACHE_OK = "CACHE_OK";

	/* SQL操作类型 */
	public static final byte SQL_OPTTYPE_ADD = 1;
	public static final byte SQL_OPTTYPE_UPDATE = 2;
	public static final byte SQL_OPTTYPE_DELETE = 3;

	public static final String COUNT_SQL = "select count(1) from {1}";
}
