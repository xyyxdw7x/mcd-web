package com.asiainfo.biapp.mcd.constants;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.biapp.mcd.util.MpmLocaleUtil;
import com.asiainfo.biframe.common.SysCodes;
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

public class MpmCONST extends SysCodes {
	private static Logger log = LogManager.getLogger();

	public static ThreadLocal threadLocal = new ThreadLocal();

	public static final String LOGIC_AREA_SERVICE = "logicAreaService";

	public static final String CAMP_MANAGE_SERVICE = "campManageService";
	//短信群发测试
	public static final String SMS_SEND_TEST_TASK = "MtlSmsSendTestTask";

	//互动模块处理返回信息存储在request中的变量�?
	public static final String COMPAIGN_MODULE_PROCESS_RESULT = "compaign_module_process_result";

	//互动模块活动查询结果存储在request中的变量�?
	public static final String COMPAIGN_SEARCH_RESULT = "compaign_search_result";

	//营销管理子系统在系统公用存储目录下的子目录名
	public static final String MPM_STORE_SUB_PATH = "mpm";

	//营销管理子系统在系统公用存储目录/子目录下存储导出文件的目录名
	public static final String MPM_STORE_SEND_PATH = "send";

	//营销管理子系统在系统公用存储目录/子目录下存储导出文件的校验文件的目录�?
	public static final String MPM_STORE_CHK_PATH = "chk";

	//营销管理系统-某个资源可以被修改的标示
	public static final String MPM_SOURCE_CAN_MODIFY = "1";

	//营销管理系统-某个资源不可以被修改的标�?
	public static final String MPM_SOURCE_CAN_NOT_MODIFY = "0";

	//活动执行模式
	public static final String COMPAIGN_EXEC_MODE = "execMode";

	//活动执行模式-手动
	public static final int COMPAIGN_EXEC_MODE_BYHAND = 1;

	//活动执行模式-自动
	public static final int COMPAIGN_EXEC_MODE_AUTO = 2;

	//营销类型 mtl_camp_baseInfo中camp_type字段的含�?
	public static final String MPM_CAMP_TYPE = "campType";

	//营销类型-纯宣传类�?
	public static final short MPM_CAMP_TYPE_PASSVITY = 0;

	//营销类型-非纯宣传类型
	public static final short MPM_CAMP_TYPE_INITIATIVE = 1;

	//营销类型-精确营销类型
	public static final short MPM_CAMP_TYPE_REALTIME = 2;

	//营销类型-互动营销类型
	public static final short MPM_CAMP_TYPE_INTERACTIVE = 4;

	//营销类型-常�?营销类型
	public static final short MPM_CAMP_TYPE_NORMALCY = 3;

	//实时营销—�?动�?规则的设�?
	public static final String DIM_RULE_SERVICE_BEAN_ID = "dimRuleService";

	//活动是否�?��评估 mtl_camp_baseInfo中evaluate_flag字段的含�?
	public static final String EVALUATE_FLAG = "evaluateFlag";

	//活动是否�?��评估-不需�?
	public static final short MPM_EVALUATE_FLAG_NO = 0;

	//活动是否�?��评估-�?��
	public static final short MPM_EVALUATE_FLAG_YES = 1;

	//mtl_camp_baseInfo中camp_status字段的含�?
	//活动状�?
	public static final String COMPAIGN_STATUS = "campStatus";

	//mtl_camp_segInfo中scene_type字段的含�?
	//活动场景类型
	public static final String SCENE_TYPE = "scene_type";
	public static final String SCENE_TYPE_REALTIME = "scene_type11";
	public static final String SCENE_TYPE_NORMAL = "scene_type12";

	//活动状�? 正常状�?
	public static final short COMPAIGN_STATUS_NORMAL = 1;

	//活动状�? 完成状�?
	public static final short COMPAIGN_STATUS_COMPLETED = 2;

	//活动状�? 终止
	public static final short COMPAIGN_STATUS_TERMINALED = 3;

	//mtl_camp_baseInfo中status_chg_type字段的含�?
	//活动状�?变更方式
	public static final String COMPAIGN_STATUS_CHG_TYPE = "statusChgType";

	//手动改变状�?
	public static final short COMPAIGN_STATUS_CHG_BY_HAND = 0;

	//自动变更状�?
	public static final short COMPAIGN_STATUS_CHG_AUTO = 1;

	//mtl_camp_baseInfo中camp_replay_status字段的含�?
	//活动波次类型
	public static final String COMPAIGN_REPLAY_STATUS = "replayStatus";

	//单波次活�?
	public static final short COMPAIGN_REPLAY_STATUS_ONCE = 0;

	//多波次活�?
	public static final short COMPAIGN_REPLAY_STATUS_MULTI = 1;

	//mtl_sys_actstep_def中step_phase字段的含�?
	public static final String MPM_CAMP_STEP_PHASE = "mpm_camp_step_phase";

	//活动步骤�?��阶段-客户群筛选阶�?
	public static final short MPM_CAMP_STEP_PHASE_FILTER = 0;

	//活动步骤�?��阶段-定义阶段
	public static final short MPM_CAMP_STEP_PHASE_DEFINE = 1;

	//活动步骤�?��阶段-审批阶段
	public static final short MPM_CAMP_STEP_PHASE_AUDIT = 2;

	//活动步骤�?��阶段-执行阶段
	public static final short MPM_CAMP_STEP_PHASE_RUN = 3;

	//活动步骤�?��阶段-评估阶段
	public static final short MPM_CAMP_STEP_PHASE_ANALYSE = 4;

	//mtl_sys_actstep_def中pre_step_id字段的含�?
	//步骤依赖前续步骤编号-没有依赖活动
	public static final short MPM_CAMP_STEP_NO_PRESTEP = -1;

	//步骤依赖前续步骤编号-有需要特别处理的前续步骤
	public static final short MPM_CAMP_STEP_SPECIAL_PRESTEP = 0;

	//mtl_templet_active中atemplet_type字段的含�?
	public static final String MPM_TEMPLET_TYPE_INFO = "mpm_templet_type_info";

	//活动模版类型-公用模版
	public static final short MPM_TEMPLET_TYPE_PUBLIC = 1;

	//活动模版类型-私有模版
	public static final short MPM_TEMPLET_TYPE_PRIVATE = 2;

	//活动模版类型-活动专用模版
	public static final short MPM_TEMPLET_TYPE_SPECIAL = 3;

	public static final short MPM_TEMPLET_TYPE_OTHER = 4;

	//mtl_camp_seginfo中approve_flag字段的含�?
	//活动波次是否�?��审批标志
	public static final String MPM_SEG_APPROVE_FLAG = "segApproveFlag";

	//活动波次是否�?��审批标志-不需�?
	public static final short MPM_SEG_APPROVE_FLAG_NOTNEED = 0;

	//活动波次是否�?��审批标志-�?��
	public static final short MPM_SEG_APPROVE_FLAG_NEED = 1;

	//mtl_camp_indi_targtval中indi_resource字段的含�?
	//活动关联的指标来�?
	public static final String MPM_CAMP_INDI_RESOURCE = "campIndiResource";

	//活动关联的指标来�?来自指标库系�?
	public static final short MPM_CAMP_INDI_RESOURCE_FROM_SYS = 0;

	//活动关联的指标来�?来自营销管理系统自定�?
	public static final short MPM_CAMP_INDI_RESOURCE_FROM_SELF = 1;

	//mtl_camp_seginfo中approve_result字段的含�?
	//活动波次审批结果标志
	public static final String MPM_SEG_APPROVE_RESULT = "segApproveResult";

	//不需要提交审�?
	public static final short MPM_SEG_APPROVE_NEEDLESS = -2;

	//营销案审批结果标�?营销案就绪未提交审批(mtl_camp_baseinfo中approve_result字段的含�?
	public static final short MPM_SEG_APPROVE_RESULT_READY = -1;

	//活动波次审批结果标志-等待审批
	public static final short MPM_SEG_APPROVE_RESULT_WAITING = 0;

	//活动波次审批结果标志-通过
	public static final short MPM_SEG_APPROVE_RESULT_PASSED = 1;

	//活动波次审批结果标志-未�?�?
	public static final short MPM_SEG_APPROVE_RESULT_NOTPASSED = 2;

	//活动波次审批结果标志-要求活动波次终止
	public static final short MPM_SEG_APPROVE_RESULT_TERMINALED = 9;

	//mtl_filter_file中file_load_type的含�?
	//剔除文件入库标志
	public static final String MPM_FILTER_FILE_LOAD_TYPE = "fileLoadType";

	//剔除文件入库标志-还未入库
	public static final short MPM_FILTER_FILE_LOAD_TYPE_UNDO = 0;

	//剔除文件入库标志-入库成功
	public static final short MPM_FILTER_FILE_LOAD_TYPE_SUCCESS = 1;

	//剔除文件入库标志-入库失败
	public static final short MPM_FILTER_FILE_LOAD_TYPE_FAILURE = 2;

	//剔除文件入库标志-入库进行�?
	public static final short MPM_FILTER_FILE_LOAD_TYPE_RUNNING = 3;

	//mtl_userfilter_process中perform_result的含�?
	public static final short MPM_USERSEG_RUN_TYPE_SUCCESS = 0;

	public static final short MPM_USERSEG_RUN_TYPE_FAIL = 1;

	public static final short MPM_USERSEG_RUN_TYPE_RUNING = 2;

	//mtl_sys_actstep_def中step_id字段的含�?
	public static final String MPM_ACTIVE_STEP_ID = "mpm_active_step_id";

	//活动步骤-导入目标客户文件步骤
	public static final short MPM_SYS_ACTSTEP_DEF_IMPORT_CUST = 5;

	//活动步骤-客户群定义�?择步�?
	public static final short MPM_SYS_ACTSTEP_DEF_CUST_GROUP_DEF_SELECT = 6;

	//活动步骤-客户群定义从营销效果创建
	public static final short MPM_SYS_ACTSTEP_DEF_CUST_GROUP_DEF_FEEDBACK = 7;

	//活动步骤-客户属�?设置步骤
	public static final short MPM_SYS_ACTSTEP_DEF_ACTIVE_TEMPLET = 10;

	//活动步骤-目标用户筛�?步骤
	public static final short MPM_SYS_ACTSTEP_DEF_CAMP_SELECT = 20;

	//活动步骤-目标群运算步�?
	public static final short MPM_SYS_ACTSTEP_DEF_CAMP_AGGREGATE = 25;

	//活动步骤-活动用户剔除步骤
	public static final short MPM_SYS_ACTSTEP_DEF_CAMP_FILTER = 30;

	//活动步骤-活动信息定义步骤
	public static final short MPM_SYS_ACTSTEP_DEF_CAMPSEG = 40;

	//活动步骤-实时营销设置步骤
	public static final short MPM_SYS_ACTSTEP_DEF_REALTIME = 50;

	//活动步骤-事件营销伪实时设置步�?
	public static final short MPM_SYS_ACTSTEP_DEF_EVENT_REALTIME = -8;

	//活动步骤-提交内部审批步骤
	public static final short MPM_SYS_ACTSTEP_DEF_CAMP_AUDIT = 60;

	//活动步骤-提交渠道审批步骤
	public static final short MPM_SYS_ACTSTEP_DEF_CHANNEL_AUDIT = 70;

	//活动步骤-活动派单
	public static final short MPM_SYS_ACTSTEP_DEF_CAMP_SEND = 80;

	//活动步骤-活动执行
	public static final short MPM_SYS_ACTSTEP_DEF_CAMP_EXEC = 90;

	//活动步骤-活动跟踪分析
	public static final short MPM_SYS_ACTSTEP_DEF_CAMP_ANALYSE = 100;

	//活动步骤-活动评估
	public static final short MPM_SYS_ACTSTEP_DEF_CAMP_EVALUATE = 110;

	//mtl_data_source中source_type字段的含�?
	//数据源表类型
	public static final String SOURCE_TABLE_TYPE = "sourceTableType";

	//数据源表类型-基础信息�?
	public static final int SOURCE_TABLE_TYPE_BASE = 1;

	///数据源表类型-扩展信息�?
	public static final int SOURCE_TABLE_TYPE_EXTEND = 2;

	//数据源表类型-活动接口数据�?
	public static final int SOURCE_TABLE_TYPE_INTFACE = 3;

	//数据源表类型-活动宽表
	public static final int SOURCE_TABLE_TYPE_COMPAIGN = 4;

	///数据源表类型-剔除数据�?
	public static final int SOURCE_TABLE_TYPE_ELIMINATE = 5;

	///数据源表类型-上传文件�?
	public static final int SOURCE_TABLE_TYPE_UPLOADFILE = 6;

	//增加源表时的表类�?
	public static final String ADD_SOURCE_TABLE_TYPE = "add_source_table_type";

	public static final int ADD_SOURCE_TABLE_TYPE_INTFACE = 3;

	public static final int ADD_SOURCE_TABLE_TYPE_ELIMINATE = 5;

	//mtl_approve_confirm_list，mtl_camp_seginfo中confirm_flag字段的含�?
	public static final String MPM_CONFIRM_FLAG = "mpm_confirm_flag";

	//不需要渠道确�?
	public static final short MPM_CONFIRM_NEEDLESS = -2;

	//资源未提交确认标�?
	public static final short MPM_CONFIRM_FLAG_NO = 0;

	//资源已被确认标志
	public static final short MPM_CONFIRM_FLAG_YES = 1;

	//资源无法到位标志
	public static final short MPM_CONFIRM_FLAG_CANNOT = 2;

	//等待他人确认
	public static final short MPM_CONFIRM_FLAG_WAITING = 3;

	//确认终止营销�?活动
	public static final short MPM_CONFIRM_FLAG_TERMINATE = 9;

	//mtl_attachment_info中attachment_type字段的含�?
	//附件类型-营销案的附件
	public static final short MPM_ATTACH_TYPE_CAMP = 1;

	//附件类型-活动的附�?
	public static final short MPM_ATTACH_TYPE_CAMPSEG = 2;

	//活动宣传设计附件
	public static final short MPM_ATTACH_TYPE_DRUMBEATING = 3;

	//分公司上�?
	public static final short MPM_ATTACH_TYPE_UPLOAD = 4;

	//免打扰号码库
	public static final short MPM_ATTACH_TYPE_BOTHER = 5;

	//汇�?上报
	public static final short MPM_ATTACH_TYPE_COUNTS = 6;

	//mtl_attachment_info中attachment_content_type字段的含�?
	//附件内容类型-营销�?活动策划支持文件
	public static final short MPM_ATTACH_CONT_TYPE_SUPPORT = 1;

	//附件内容类型-营销�?活动审批意见文件
	public static final short MPM_ATTACH_CONT_TYPE_AUDIT = 2;

	// 附件内容类型-营销�?活动确认意见文件
	public static final short MPM_ATTACH_CONT_TYPE_CONFIRM = 10;

	//附件内容类型－活动宣传设计附�?
	public static final short MPM_ATTACH_CONT_TYPE_DRUMBEATING = 3;

	//附件内容类型-活动总结文档
	public static final short MPM_ATTACH_CONT_TYPE_CAMP_SUMMARIZE = 4;

	//附件内容类型-活动波次总结文档
	public static final short MPM_ATTACH_CONT_TYPE_CAMPSEG_SUMMARIZE = 5;

	//分公司上报文�?
	public static final short MPM_ATTACH_CONT_TYPE_UPLOAD = 6;

	//分公司上报有误文�?
	public static final short MPM_ATTACH_CONT_TYPE_UPLOADERR = 9;

	//免打扰号码库
	public static final short MPM_ATTACH_CONT_TYPE_BOTHER = 7;

	public static final short MPM_ATTACH_CONT_TYPE_COUNTS = 8;

	//mtl_data_source中source_status字段的含�?
	//数据源表状�?
	public static final String SOURCE_TABLE_STATUS = "sourceTableStatus";

	//数据源表状�?-无效
	public static final int SOURCE_TABLE_STATUS_INVALID = 2;

	//数据源表状�?-有效
	public static final int SOURCE_TABLE_STATUS_VALID = 1;

	//数据源表状�?-被使�?
	public static final int SOURCE_TABLE_STATUS_USED = 3;

	///数据源表状�?-暂停
	public static final int SOURCE_TABLE_STATUS_PAUSE = 4;

	//mtl_datasrc_column中cloumn_class字段的含�?
	//基础信息表字段类�?
	public static final String BASE_TABLE_COLUMN_TYPE = "baseTableColumnType";

	//基础信息表字段类�?用户属�?
	public static final int BASE_TABLE_COLUMN_TYPE_USER = 1;

	//基础信息表字段类�?客户属�?
	public static final int BASE_TABLE_COLUMN_TYPE_CUSTOMER = 2;

	//基础信息表字段类�?业务属�?
	public static final int BASE_TABLE_COLUMN_TYPE_BUSI = 3;

	//基础信息表字段类�?帐务属�?
	public static final int BASE_TABLE_COLUMN_TYPE_ACCOUNT = 4;

	//基础信息表字段类�?终端属�?
	public static final int BASE_TABLE_COLUMN_TYPE_TERMINAL = 5;

	//基础信息表字段类�?其他
	public static final int BASE_TABLE_COLUMN_TYPE_OTHER = 6;

	//mtl_datasrc_column中cloumn_type字段的含�?
	public static final String MPM_DS_COLUMN_TYPE = "mpm_ds_column_type";

	//字段数据类型-数字�?
	public static final short MPM_DS_COLUMN_TYPE_NUMBER = 1;

	//字段数据类型-字符�?
	public static final short MPM_DS_COLUMN_TYPE_STRING = 2;

	//字段数据类型-日期�?
	public static final short MPM_DS_COLUMN_TYPE_DATE = 3;

	//字段数据类型-Blob�?
	public static final short MPM_DS_COLUMN_TYPE_BLOB = 4;

	//mtl_datasrc_column中column_data_type字段
	public static final String MPM_DS_COLUMN_DATA_TYPE = "mpm_ds_column_data_type";

	public static final String MPM_DS_COLUMN_DATA_TYPE_INTEGER = "INTEGER";

	public static final String MPM_DS_COLUMN_DATA_TYPE_FLOAT = "DECIMAL()";

	public static final String MPM_DS_COLUMN_DATA_TYPE_DOUBLE = "DECIMAL(,)";

	public static final String MPM_DS_COLUMN_DATA_TYPE_CHAR = "CHAR";

	public static final String MPM_DS_COLUMN_DATA_TYPE_CHARARRAY = "CHAR()";

	public static final String MPM_DS_COLUMN_DATA_TYPE_VARCHAR = "VARCHAR()";

	public static final String MPM_DS_COLUMN_DATA_TYPE_DATE = "DATE";

	public static final String MPM_DS_COLUMN_DATA_TYPE_TIMESTAMP = "TIMESTAMP";

	//mtl_datasrc_column表中column_flag字段取�?含义
	public static final String SOURCE_TABLE_COLUMN_FLAG = "source_table_column_flag";

	//非维度�?指标字段
	public static final int COMPAIGN_COLUMN_FLAG_NOTHING = 0;

	//维度字段
	public static final int COMPAIGN_COLUMN_FLAG_DEMENSION = 1;

	//指标字段
	public static final int COMPAIGN_COLUMN_FLAG_INDI = 2;

	//mtl_campseg_progress中perform_flag字段的含�?
	public static final String MPM_CAMPSEG_STEP_PERFORM_FLAG = "campseg_step_perform_flag";

	//活动步骤执行状�?-未执�?
	public static final short MPM_CAMPSEG_STEP_PERFORM_FLAG_UNDO = 0;

	//活动步骤执行状�?-执行成功
	public static final short MPM_CAMPSEG_STEP_PERFORM_FLAG_SUCCESS = 1;

	//活动步骤执行状�?-执行失败
	public static final short MPM_CAMPSEG_STEP_PERFORM_FLAG_FAILURE = 2;

	//活动步骤执行状�?-被终�?
	public static final short MPM_CAMPSEG_STEP_PERFORM_FLAG_TERMINALED = 3;

	//活动步骤执行状�?-执行中，对于执行“目标客户圈定�?和�?目标客户群分组�?时有用：前台触发执行操作，后台程序来执行�?
	public static final short MPM_CAMPSEG_STEP_PERFORM_FLAG_RUNING = 4;

	//活动步骤执行状�?-执行�?
	public static final short MPM_CAMPSEG_STEP_PERFORM_FLAG_READYRUN = 5;

	//MTL_filter_rule_combination中rule_type字段的含�?
	//剔除方式-按文件剔除目标用�?
	public static final short MPM_FILTER_RULE_TYPE_BY_FILE = 1;

	//剔除方式-按活动剔除目标用�?
	public static final short MPM_FILTER_RULE_TYPE_BY_CAMP = 2;

	//剔除方式-按剔除模版目标用�?
	public static final short MPM_FILTER_RULE_TYPE_BY_FILTER_TEMPLET = 3;

	//剔除方式-按特定号码库提出目标用户
	public static final short MPM_FILTER_RULE_TYPE_BY_SPECIFY = 4;

	//MTL_filter_rule_combination中rule_apply_type字段的含�?
	public static final String MPM_FILTER_RULE_APPLY_TYPE = "filterRuleApplyTYpe";

	//剔除模式-按not in方式剔除
	public static final short MPM_FILTER_RULE_APPLY_TYPE_NOTIN = 1;

	//剔除模式-按in方式剔除
	public static final short MPM_FILTER_RULE_APPLY_TYPE_IN = 2;

	//活动信息导出文件格式
	public static final String COMPAIGN_EXP_FILE_EXT = "compaign_exp_file_ext";

	//活动信息导出文件格式-txt
	public static final String COMPAIGN_EXP_FILE_TXT = "2";

	//活动信息导出文件格式-cvs
	public static final String COMPAIGN_EXP_FILE_CSV = "3";

	//活动信息导出文件格式-xml
	public static final String COMPAIGN_EXP_FILE_XML = "1";

	//导出结果 MTL_CAMPSEG_EXPORT_COND
	public static final String MPM_CAMPSEG_EXPORT_RESULT = "mpm_campseg_export_result";

	public static final String MPM_CAMPSEG_EXPORT_EXPORTSUCCESS = "1";

	public static final String MPM_CAMPSEG_EXPORT_EXPORTFAILURE = "2";

	public static final String MPM_CAMPSEG_EXPORT_EXPORTING = "3";

	public static final String MPM_CAMPSEG_EXPORT_WAITEXPORT = "4";

	//实体类型编码
	public static final String RESTYPE_ENTITY = "10"; //实物�?

	public static final String RESTYPE_FAVORATE = "20"; //优惠�?

	public static final String RESTYPE_MEDIA = "30"; //媒体�?

	public static final String RESTYPE_FLACK = "40"; //宣传�?

	public static final String RESTYPE_CHANNEL = "50"; //渠道�?

	//成本实体是否有效编码 表MTL—RES—LIST res_flag字段
	public static final String RES_ENTITY_VALID = "1"; // 有效，可以被营销活动选择使用

	public static final String RES_ENTITY_STOP = "2"; // 暂停，暂时不可以被营�?��动�?择使�?

	public static final String RES_ENTITY_INVALID = "3"; //无效

	public static final String RES_ENTITY_FOREVER_VALID = "9"; //永久有效，不能删�?

	//各个service类在spring配置文件中的beanId名称
	//start added by dengshu
	public static final String CAMPSEG_RULE_SERVICE = "campsegRuleService";

	public static final String MPM_MOP_PARAM_SERVICE = "mpmMopParamService";
	//逻辑区域管理
	public static final String DIM_PUB_AREA_SERVICE = "dimPubAreaService";
	//特殊号码服务
	public static final String MTL_SPEC_SERVICES = "mtlSpecServices";
	//监控页面服务
	public static final String CAMPSEG_REALTIMELIST_SERVICE = "campsegRtimeListService";
	//end added by dengshu
	//added by caolifeng begin
	public static final String MTL_STC_PLAN_GEN_TREE_SERVICE = "mtlStcPlanGenTreeService";

	public static final String MTL_PROC_POP_DAO = "mtlProcPopDao";

	public static final String MTL_PROC_BEAT_DAO = "mtlProcBeatDao";
	//added by caolifeng end

	public static final String CAMPAIGN_BASE_INFO_SERVICE = "mpmCampBaseInfoService";

	public static final String CAMPAIGN_SEG_INFO_SERVICE = "mpmCampSegInfoService";
	
	public static final String MTL_CAMPSEG_INFO_PLAN_ORDER_SERVICE ="mtlCampsegInfoPlanOrderService";

	public static final String CALLWS_URL_SERVICE = "callWsUrlService";

	public static final String DIM_CAMP_BUSSI_CODE_SERVICE = "dimCampBussiCodeService";

	public static final String MPM_SYS_ACTFLOW_DEF_SERVICE = "mpmSysActFlowDefService";

	public static final String MPM_ATTACHMENT_SERVICE = "mpmAttachmentService";

	public static final String MPM_CAMPSEG_PROGRESS_SERVICE = "mpmCampsegProgressService";

	public static final String MPM_CAMPSEG_INDI_ANALYSE_SERVICE = "mpmCampsegIndiAnalyseService";

	public static final String MPM_CAMP_TARGET_ANALYSE_SERVICE = "mpmCampTargetAnalyseService";

	public static final String MPM_CAMPSEG_COMPARE_ANALYSE_SERVICE = "mpmCampsegCompareAnalyseService";

	public static final String MPM_USER_POLICY_SERVICE = "mpmUserPolicyService";

	public static final String MPM_CAMP_CHANNEL_SEARCH_SERVICE = "campChannelSearchService";

	public static final String MPM_CAMP_APPROVE_SEARCH_SERVICE = "campApproveSearchService";

	public static final String MPM_APPROVE_FLOW_DEF_SERVICE = "mpmApproveFlowDefService";

	public static final String MPM_APPROVE_ADVICE_SERVICE = "mtlApproveAdviceService";
	
	public static final String MPM_TEMPLET_SERVICE = "mcdTempletService";
	
	public static final String MPM_SYS_TABLE_SERVICE = "mdaSysTableService";

	public static final String MPM_APPROVE_BY_OA_SERVICE = "mpmApproveByOAServiceImpl";

	public static final String MPM_DIM_PUB_COMMON_SERVICE = "dimPubCommonService";

	public static final String MPM_CAMP_DESIGN_SERVICE = "mpmCampDesignService";

	public static final String MPM_CAMP_TEMPLET_SERVICE = "mpmTempletService";

	public static final String MPM_NB_CAMP_CHANNELS_SERVICE = "nbCampChannelsService";

	public static final String MPM_CAMPSET_COST_SERVICE = "mpmCampsegCostService";

	public static final String MPM_APPROVE_REMIND_SERVICE = "mpmApproveRemindService";

	public static final String MPM_CUST_GROUP_SERVICE = "mtlCustGroupService";
	//added by caolifeng 20100621
	public static final String MTL_EVALUATE_CHANCE_SERVICE = "mtlEvaluateChanceService";

	public static final String MPM_PUBLICIZE_COMB_SERVICE = "mtlPublicizeCombService";

	public static final String MPM_PROMOTION_TYPE_SERVICE = "mtlPromotionTypeService";

	public static final String DIM_MTL_CHANNEL_SERVICE = "dimMtlChannelService";

	public static final String MPM_CAMPSEG_AGGREGATE_SELECT_SERVICE = "mpmCampsegAggregateSelectService";

	public static final String CAMP_DATASOURCE_SERVICE_BEAN_ID = "campDatasourceService";

	public static final String SYS_ACTFLOWDEF_SERVICE_BEAN_ID = "mpmSysActFlowDefService";

	public static final String RES_LIST_SERVICE_BEAN_ID = "mpmResListService";

	public static final String IRINDI_DEFINE_SERVICE_BEAN_ID = "mpmIrIndiDefineService";

	public static final String MPM_CAMP_CHANNEL_EVALUATE_SERVICE = "campChannelEvaluateService";

	public static final String MTL_CAMP_CHANNEN_REPLY_SERVICE = "CampChannelReplySearchService";

	public static final String MTL_LOADBALANCE_PROMPT_SERVICE = "mtlLoadbalancePromptService";

	//added by happyl78
	public static final String CHANNELDISPATCH_SERVICE_BEAN_ID = "mpmCampChannelDispatchSearchService";

	public static final String DRUMBEATINGDESIGN_SERVICE_BEAN_ID = "drumbeatingDesignService";

	public static final String COMMON_SERVICE_BEAN_ID = "commonService";

	public static final String RULEDESIGN_SERVICE_BEAN_ID = "ruleDesignService";

	public static final String USERSUBSECTION_SERVICE_BEAN_ID = "userSubsectionService";

	public static final String USERSEGRUN_SERVICE_BEAN_ID = "userSegRunService";

	public static final String SENDODDDATE_SERVICE_BEAN_ID = "mpmSendOddDateSourceSvc";

	public static final String APPROVERELATION_SERVICE_BEAN_ID = "mpmApproveRelationService";

	public static final String HAND_CHGSTATUS_SERVICE_BEAN_ID = "mpmHandChgStatusService";

	public static final String DATA_EXPORT_ITEM_SERVICE_BEAN_ID = "mpmDataExportItemService";

	public static final String USERSUBSECTION_RUN_SERVICE_BEAN_ID = "mpmUserSubsectionRunService";

	public static final String CAMPSEG_APPROVE_SERVICE_BEAN_ID = "mpmCampsegApproveService";

	public static final String DIM_USER_ID_NAME_MAPPER_BEAN_ID = "dimUserId";

	public static final String DIM_COLUMN_FEATURE_SERVICE_BEAN_ID = "dimColumnFeatureService";

	public static final String MPM_CAMP_STC_PLAN_SERVICE = "mtlCampStcPlan";

	public static final String MTL_CAMP_INDI_TARGET_SERVICE = "mtlCampIndiTargetService";

	public static final String MTL_EXT_EVALUATION_URL_SERVICE = "mtlExtEvaluationUrl";

	public static final String MTL_DOWNLOAD_LOG_SERVICE = "downloadLogService";

	//public static final String MTL_APPROVE_CONFIRM_SERVICE = "mtlApproveConfirmService";
	public static final String MTL_APPROVE_AUTH_SERVICE = "mtlApproveAuthService";

	public static final String MTL_CONTACT_CONFIG_SERVICE = "mtlContactConfigService";

	public static final String CALLWS_FACTORY_SERVICE_BEAN_ID = "callwsFactoryService";
	//caihao add
	public static final String MTL_CONFIRM_SERVICE = "mtlConfirmService";

	public static final String MPM_USER_PRIVILEGE_SERVICE = "mpmUserPrivilegeService";

	public static final String MTL_APPROVE_SERVICE = "mtlApproveService";

	public static final String MTL_APPROVERESOURCETYPE_SERVICE = "mtlApproveResourceTypeService";

	public static final String DIM_MTLCHANNELTYPE_SERVICE = "dimMtlChanneltypeService";

	public static final String DIM_MTLCHANNEL_SERVICE = "dimMtlChannelService";

	/*2013�?�?7�?3:16:40 MAZH 添加端口申请�?��模块 action调用service常量*/
	public static final String MCD_PORT_CONFIG_SERVICE = "mcdPortConfigService";

	public static final String TARGET_ANALYSIS_SERVICE = "targetAnalysis";

	//陕西添加推荐业务管理service

	public static final String MTLSTCPLANMANAGEMENTSERVICE = "MtlStcPlanManagementService";

	public static final String DIM_MTL_SYSACT_FLOW = "dimActflow";

	public static final String DIM_PAGE_RELATION = "dimPageRelation";

	public static final String DIM_CAMP_DRV_TYPE = "dimCampDrvType";
	public static final String REALTIME_DRV_TYPE = "-8";
	public static final String NORMAL_DRV_TYPE = "-12";

	public static final String DIM_PLAN_EXEC_TYPE = "dimPlanExecType";

	public static final String DIM_CHANNELUSERRELATION_SERVICE = "dimChannelUserRelationService";

	public static final String DIM_CHANNEL_IDNAMEMAPPER_SERVICE = "allChannel";

	public static final String MTL_APPROVE_OA_WEB_CLIENT = "mpmApproveWsClient";

	//added by mads
	public static final String DIM_DIMDEPTFLOWRELATION_SERVICE = "dimDeptFlowRelationService";
	public static final String DIM_USERID_SERVICE = "dimUserId";
	public static final String DIM_DEPTID_SERVICE = "dimDeptId";
	public static final String DIM_CITY_SERVICE = "dimPubCity";
	public static final String DIM_AVOID_BOTHER_SERVICE = "dimAvoidBother";

	//20090618 by wt add
	public static final String MTL_APPROVE_FLOW = "approveFlow";
	public static final String DIM_MTL_CHANNELTYPE = "dimMtlChanneltype";
	public static final String ALL_CHANNEL = "allChannel";

	//added by jianglc
	public static final String MPM_CAMPSEG_DISPATCH = "mpmCampsegDispatch";
	public static final String COMMON_SENDODD_SERVICE = "commonSendodd";
	//added by caosj
	public static final String MTL_CAMP_DRV_TYPE_ID = "10";// 营销活动时话务量提升专题id
	public static final Short MTL_CUST_TYPE_ID = Short.valueOf((short) 1);// 话务量提升专题客户群类型id

	//活动任务任务服务
	public static final String MCD_CAMPSEG_TASK_SERVICE = "mcdCampsegTaskService";

	//added by caosj
	public static final String MTL_EVALUATE_ANGLE_SERVICE = "mtlEvaluateAngleService";
	public static final String MTL_CAMP_REALTIME_LIST_SERVICE = "mtlCampRealtimeListService";
	//实时营销活动状�?编码(1 启动; 2 �?��执行; 3活动暂停; 4活动失败; 5活动完成; 6活动终止)
	public static final String CAMPSEG_START = "1";
	public static final String CAMPSEG_BEGIN_EXECUTE = "2";
	public static final String CAMPSEG_PAUSE = "3";
	public static final String CAMPSEG_FAILED = "4";
	public static final String CAMPSEG_FINISH = "5";
	public static final String CAMPSEG_END = "6";
	//营销活动是否使用静�?目标�?0 不使�?1使用

	public static final String USE_STATIC_CUSTGROUP_NO = "0";
	public static final String USE_STATIC_CUSTGROUP_YES = "1";

	public static final String SAVE_SUCCESS = MpmLocaleUtil.getMessage("mcd.java.bccg1");

	public static final String SAVE_FAIL = MpmLocaleUtil.getMessage("mcd.java.bcsbqyxtgl");

	public static final String DEL_SUCCESS = MpmLocaleUtil.getMessage("mcd.java.sccg");

	public static final String DEL_FAIL = MpmLocaleUtil.getMessage("mcd.java.scsbqyxtgl");

	public static final String COSTRESOURCE_FLACK = "1"; //宣传产生的成�?

	public static final String COSTRESOURCE_CHANNEL = "2"; //营销渠道产生的成�?

	public static final String COSTRESOURCE_STCPLAN = "3"; //营销按产生的成本

	public static final String RES_MEDIA = "RES0000001"; //实体清单 媒体

	public static final String RES_CHANNEL = "RES0000002"; //实体清单 渠道

	public static final int COSTCODE_FLACK = 10012345; //成本类型编码 媒体

	public static final int COSTCODE_CHANNEL = 10012346; //成本类型编码 渠道

	//客户细分结果规则类型
	public static final String USER_SUBSECTION_TYPE = "user_subselection_type";

	public static final int USER_SUBSECTION_TEMPLET = 2; //模版细分

	public static final int USER_SUBSECTION_VIP = 1; //大客户经理细�?

	public static final int USER_SUBSECTION_DEFAUL = 0; //剩余分组

	public static final int USER_SUBSECTION_ONLY_DRUMBEATING = 3;//纯宣�?

	public static final int USER_SUBSECTION_ONLY_ALL = 9;//没有细分步骤的默认的全部用户�?

	public static final String USER_SUBSECTION_PREFIX = MpmLocaleUtil.getMessage("mcd.java.khfz");

	public static final int CHANNEL_PRIO = 1;

	//客户接触方式
	public static final String CONTACTTYPE_MODE = "contactType";

	public static final int CONTACTTYPE_MANAGER_CHANNEL = 1; //客户经理

	public static final int CONTACTTYPE_TRADITION_CHANNEL = 2; //传统渠道（营业厅，callcenter，合作伙伴）

	public static final int CONTACTTYPE_ELECTRONIC_CHANNEL = 3; //电子渠道

	public static final int CONTACTTYPE_MTL_CHANNEL = 4; //电子渠道 + 传统渠道

	//电子渠道交互类型
	public static final int REPLYTYPE_NEEDREPLY = 1; //�?��回复

	public static final int REPLYTYPE_NOTNEEDREPLY = 0; //不需要回�?

	//电子渠道类型
	public static final String SMS = "sms";

	public static final String MMS = "mms";

	public static final String WAP = "wap";

	public static final String ELCHANNEL_MODE = "elchannel";

	public static final String USER_SUBSECTION_FIELD = "field";

	public static final String VIPMANAGERID = "vip_manager_id";

	//渠道类型(电子渠道和大客户经理) added by chenlc
	public static final String CHANNEL_TYPE = "channel_type";

	public static final String CHANNEL_MANAGER_TYPE = "channel_manager_type";

	public static final String CHANNEL_TYPE_VIPMANAGER = "900";

	public static final String CHANNEL_TYPE_COMPMANAGER = "904";

	public static final String CHANNEL_ELE_TYPE = "channel_ele_type";

	public static final String CHANNEL_TYPE_SMS = "901";

	public static final String CHANNEL_TYPE_MMS = "902";

	public static final String CHANNEL_TYPE_WAP = "903";

	public static final String CHANNEL_TYPE_CALL = "905";
	//营业�?
	public static final String CHANNEL_TYPE_HALL = "906";
	//网厅
	public static final String CHANNEL_TYPE_WEB = "907";
	//圈子平台
	public static final String CHANNEL_TYPE_QZ = "908";
	//邮件渠道
	public static final String CHANNEL_TYPE_EMAIL = "909";
	//触点渠道
	public static final String CHANNEL_TYPE_TOUCH = "910";
	//掌厅渠道
	public static final String CHANNEL_TYPE_HAND = "911";
	//短搜渠道
	public static final String CHANNEL_TYPE_SMSSEARCH = "912";
	//短信挂尾
	public static final String CHANNEL_TYPE_SMS_APPEND_END = "913";
	//综合网关
	public static final String CHANNEL_TYPE_GATE_WAY = "914";
	//呼入
	public static final String CHANNEL_TYPE_CALL_IN = "915";
	//虚拟渠道
	public static final String CHANNEL_TYPE_VIRTUAL = "916";
	//自动订购渠道
	public static final String CHANNEL_TYPE_AUTO_ORDER = "916";
	//流量助手
	public static final String CHANNEL_TYPE_FLOWHELPER = "917";
	//手厅
	public static final String CHANNEL_TYPE_ST = "918";
	//外呼
	public static final String CHANNEL_TYPE_OUT_CALL = "913";

	//外部事件源渠道
	public static final String CHANNEL_TYPE_EVENTSOURCE = "999";

	public static final String CHANNEL_ID_CALL_OUT = "99991";

	public static final String CHANNEL_ID_CALL_IN = "99993";

	public static final String CHANNEL_ID_CALL_OTHER = "99992";

	//客户经理
	public static final int CHANNEL_TYPE_VIPMANAGER_INT = 900;
	//短信渠道
	public static final int CHANNEL_TYPE_SMS_INT = 901;
	//彩信渠道
	public static final int CHANNEL_TYPE_MMS_INT = 902;
	//邮件渠道
	public static final int CHANNEL_TYPE_EMAIL_INT = 909;
	//外呼渠道
	public static final int CHANNEL_TYPE_CALL_INT = 905;
	//网站渠道
	public static final int CHANNEL_TYPE_WEB_INT = 907;
	//营业�?
	public static final int CHANNEL_TYPE_SALESOFFIC_INT = 906;
	//触点渠道
	public static final int CHANNEL_TYPE_TOUCH_INT = 910;
	//掌厅
	public static final int CHANNEL_TYPE_HAND_INT = 911;

	//removed by wwl 2006-11-1 电子渠道信息定义到nb_camp_channels表中
	//	public static final String SMS_CHANNEL_CODE = "sms_channel_code";
	//	public static final String SMS_CHANNEL_CODE1 = "1860";
	//	public static final String SMS_CHANNEL_CODE2 = "1861";
	//	public static final String SMS_CHANNEL_CODE3 = "1862";
	//	public static final String MMS_CHANNEL_CODE = "mms_channel_code";
	//	public static final String MMS_CHANNEL_CODE1 = "1863";
	//	public static final String MMS_CHANNEL_CODE2 = "1864";
	//	public static final String MMS_CHANNEL_CODE3 = "1865";
	//	public static final String WAP_CHANNEL_CODE = "wap_channel_code";
	//	public static final String WAP_CHANNEL_CODE1 = "1866";
	//	public static final String WAP_CHANNEL_CODE2 = "1867";
	//	public static final String WAP_CHANNEL_CODE3 = "1868";

	//执行圈定的sql类型
	public static final int USERFILTER_SQLTYPE_TARGETUSER = 0; //目标客户

	public static final int USERFILTER_SQLTYPE_FILTERUSER = 1; //剔除客户

	//执行圈定和细分结果的默认显示字段 modified by wwl 2006-11-7 默认字段修改为只�?�?
	//public static final String COLUMN = "PRODUCT_NO,BRAND_ID,BRAND_NAME,USER_CONSUME,NEWBUSI_FEE,USER_HANDLE_FLAG,";
	public static final String COLUMN = "PRODUCT_NO,USER_HANDLE_FLAG,";

	//反馈日期类型
	public static final String FEEDBACK_MODE = "feedbackDate";

	public static final String FEEDBACKDATE_NO = "0"; //不需要提供反馈数�?

	public static final String FEEDBACKDATE_EVERYDAY = "1"; //每天反馈�?��数据

	public static final String FEEDBACKDATE_EVERYWEEK = "2"; //每星�?

	public static final String FEEDBACKDATE_EVERYMONTH = "3"; //每月

	public static final String FEEDBACKDATE_EVERYQUARTER = "4"; //每季�?

	public static final String FEEDBACKDATE_APPOINTDATE = "5"; //按照指定时间

	//审批通知发�?方式
	public static final short APPROVE_SENDTYPE_SMS = 1; //SMS

	public static final short APPROVE_SENDTYPE_EMAIL = 2; //EMAIL

	//执行通知发�?方式
	public static final short EXECUTE_SENDTYPE_SMS = 1; //SMS

	public static final short EXECUTE_SENDTYPE_EMAIL = 2; //EMAIL

	//审批通知发�?标志 mtl_approve_content_send表中send_flag字段含义
	public static final String MPM_APPROVE_CONT_SEND_FLAG = "approveSendFlag";

	//未发�?
	public static final int MPM_APPROVE_CONT_SEND_FLAG_UNDO = 0;

	//发�?成功
	public static final int MPM_APPROVE_CONT_SEND_FLAG_SUCCESS = 1;

	//发�?失败
	public static final int MPM_APPROVE_CONT_SEND_FLAG_FAILURE = 2;

	//added end

	//审批级别的定�?
	public static final String MPM_APPROVE_LEVEL_DEFINE = "mpm_approve_level_define";

	public static final String MPM_APPROVE_LEVEL_DEFINE_NO = "mpm_approve_level_define_no";

	//不需要审�?
	public static final int MPM_APPROVE_LEVEL_NOTHINE = 0;

	//�?��审批
	public static final int MPM_APPROVE_LEVEL_YIJ = 1;

	//二级审批
	public static final int MPM_APPROVE_LEVEL_ERJ = 2;

	//三级审批
	public static final int MPM_APPROVE_LEVEL_SANJ = 3;

	//四级审批
	public static final int MPM_APPROVE_LEVEL_SIJ = 4;

	//五级审批
	public static final int MPM_APPROVE_LEVEL_WUJ = 5;

	//六级审批
	public static final int MPM_APPROVE_LEVEL_LIUJ = 6;

	//七级审批
	public static final int MPM_APPROVE_LEVEL_QIJ = 7;

	//成本类型定义
	public static final String MPM_COST_TYPE_DEFINE = "mpm_cost_type_define";

	//市场类成�?
	public static final int MPM_COST_TYPE_SHICHANG = 100;

	//财务类成�?
	public static final int MPM_COST_TYPE_CAIWU = 200;

	//告警标志定义
	public static final String MPM_WARN_FLAG_DEFINE = "mpm_warn_flag_define";

	//关闭
	public static final int MPM_WARN_FLAG_GB = 0;

	//启用
	public static final int MPM_WARN_FLAG_QY = 1;

	//活动波次状�?编码
	public static final String MPM_CAMPSEG_STAT_DEFINE = "mpm_campseg_stat_define";

	//保留
	public static final String MPM_CAMPSEG_STAT_HOLD = "10";

	//策划状�?  （浙江--草稿）
	public static final String MPM_CAMPSEG_STAT_CHZT = "20";

	//活动就绪 暂时不用
	public static final String MPM_CAMPSEG_STAT_HDJX = "30";

	//活动审批�? （浙江--审批中）
	public static final String MPM_CAMPSEG_STAT_HDSP = "40";

	//审批要求整改   （浙江--审批退回）
	public static final String MPM_CAMPSEG_STAT_SPYG = "41";

	//审批要求终止
	public static final String MPM_CAMPSEG_STAT_SPYZ = "42";

	//审批通过 （程序控制）
	public static final String MPM_CAMPSEG_STAT_SPTG = "43";

	//活动确认中（新增，下迭代实现�?
	public static final String MPM_CAMPSEG_STAT_HDQR = "45";

	//确认要求整改（新增，下迭代实现）
	public static final String MPM_CAMPSEG_STAT_QRYG = "46";

	//确认通过（新增，下迭代实现）（程序控制）
	public static final String MPM_CAMPSEG_STAT_QRTG = "47";

	//等待派单  （浙江--待执行）
	public static final String MPM_CAMPSEG_STAT_ZXZT = "50";

	//执行中，用于实时活动需要一直监控执行
	public static final String MPM_CAMPSEG_STAT_DDZX = "51";

	//等待后台派单（程序控制） 
	public static final String MPM_CAMPSEG_STAT_DDPD = "52";

	//派单失败
	public static final String MPM_CAMPSEG_STAT_DDSB = "53";

	//派单成功（浙江--执行中）
	public static final String MPM_CAMPSEG_STAT_DDCG = "54";

	//后台正在派单导出（程序控制）
	public static final String MPM_CAMPSEG_STAT_ZZPD = "55";

	//活动执行（新增，下迭代实现）
	//public static final String MPM_CAMPSEG_STAT_HDZX = "54";

	//执行渠道派单中（程序控制�?
	public static final String MPM_CAMPSEG_STAT_LOADING = "56";

	//执行渠道派单失败（程序控制）
	public static final String MPM_CAMPSEG_STAT_LOADFAILURE = "57";

	//执行渠道派单成功（程序控制）
	public static final String MPM_CAMPSEG_STAT_LOADSUCCESS = "58";

	//活动暂停  （浙江--暂停）
	public static final String MPM_CAMPSEG_STAT_PAUSE = "59";

	//评估状�? -- 已弃�?
	public static final String MPM_CAMPSEG_STAT_PGZT = "60";
	
	//撤销
	public static final String MPM_CAMPSEG_STAT_CX = "61";

	//活动完成  （浙江--完成）
	public static final String MPM_CAMPSEG_STAT_HDWC = "90";

	//活动终止（浙江--终止）
	public static final String MPM_CAMPSEG_STAT_HDZZ = "91";
	
	//活动测试中（浙江--测试中）
	public static final String MPM_CAMPSEG_STAT_HDCS = "48";
	
	//活动测试不通过（浙江--测试不通过）
	public static final String MPM_CAMPSEG_STAT_HDCSBTG = "49";

	//字段是否导出中文状�?说明
	public static final String MPM_DATA_EXPORT_ITEM = "mpm_data_export_item";

	//不是维度指标或不�?��导出中文
	public static final short MPM_DATA_EXPORT_ITEM_NO = 0;

	//中文＆标识都�?��
	public static final short MPM_DATA_EXPORT_ITEM_OK = 1;

	//只需要中�?
	public static final short MPM_DATA_EXPORT_ITEM_ONLY = 3;

	//模板的种�?
	public static final String MPM_TEMPLET_CLASS_INFO = "mpm_templet_class_info";

	//活动模板
	public static final short MPM_TEMPLET_CLASS_ACT = 0;

	//筛�?模板
	public static final short MPM_TEMPLET_CLASS_SELECT = 1;

	//剔除模板
	public static final short MPM_TEMPLET_CLASS_FILTER = 2;

	//细分模板
	public static final short MPM_TEMPLET_CLASS_SUBSECTION = 3;

	//剔除规则
	public static final short MPM_FILTER_RULE_INFILE = 1;

	public static final short MPM_FILTER_RULE_INACTION = 2;

	public static final short MPM_FILTER_RULE_INTEMPLET = 3;

	//驱动类型
	//驱动类型-公用类型
	public static final short MPM_DRV_TYPE_COMMON = 0;

	//竞争对手策反类型活动专用活动模板编号
	public static final String MPM_ACTIVE_TEMPLET_CF = "11111111111111111111111111111110";

	//竞争对手策反扩展表的表名
	public static final String MPM_DATA_SOURCE_TABLE_CF = "mtl_comp_userinfo";

	//实体标志
	public static final String MPM_RES_FLAG_STATUS = "mpm_res_flag_status";

	public static final String MPM_RES_FLAG_VALID = "1";

	public static final String MPM_RES_FLAG_PAUSE = "2";

	public static final String MPM_RES_FLAG_INVALID = "3";

	public static final String MPM_RES_FLAG_NOCANDEL = "9";

	//活动步骤代码标识信息 removed by wwl 2007-11-2 重复定义�?
	//	public static final String MPM_ACTIVE_STEP_ID = "mpm_active_step_id";
	//	public static final String MPM_ACTIVE_STEP_ID_DEFINE = "0";
	//	public static final String MPM_ACTIVE_STEP_ID_PROPERTY = "10";
	//	public static final String MPM_ACTIVE_STEP_ID_SELELCT = "20";
	//    public static final String MPM_ACTIVE_STEP_ID_AGGREGATE = "25";
	//	public static final String MPM_ACTIVE_STEP_ID_FILTER = "30";
	//	public static final String MPM_ACTIVE_STEP_ID_EXCEUTE = "40";
	//	public static final String MPM_ACTIVE_STEP_ID_SUB = "50";
	//	public static final String MPM_ACTIVE_STEP_ID_EXSUB = "60";
	//	public static final String MPM_ACTIVE_STEP_ID_RULE = "70";
	//	public static final String MPM_ACTIVE_STEP_ID_FLACK = "80";
	//	public static final String MPM_ACTIVE_STEP_ID_APPROVE = "90";
	//	public static final String MPM_ACTIVE_STEP_ID_SEND = "100";
	//	public static final String MPM_ACTIVE_STEP_ID_TRACK = "110";
	//	public static final String MPM_ACTIVE_STEP_ID_EVALUATE = "120";

	//营销活动中各个资源名称类型标�?使用资源�?��的表�?
	//活动资源
	public static final String MPM_RES_NAME_TYPE_CAMP = "mtl_camp_baseinfo";

	//活动波次
	public static final String MPM_RES_NAME_TYPE_CAMP_SEG = "mtl_camp_seginfo";

	//活动模版
	public static final String MPM_RES_NAME_TYPE_ACTIVE_TEMPLET = "mtl_templet_active";

	//筛�?模版
	public static final String MPM_RES_NAME_TYPE_SELECT_TEMPLET = "mtl_templet_select";

	//剔除模版
	public static final String MPM_RES_NAME_TYPE_FILTER_TEMPLET = "mtl_templet_filter";

	//过滤组合
	public static final String MPM_RES_NAME_TYPE_FILTER_COMB = "mtl_filter_rule_combination";

	//分组模版
	public static final String MPM_RES_NAME_TYPE_SUB_TEMPLET = "mtl_templet_subsection";

	//分组组合
	public static final String MPM_RES_NAME_TYPE_SUB_COMB = "mtl_subsection_rule_comb";

	//文件剔除
	public static final String MPM_RES_NAME_TYPE_FILTER_FILE = "mtl_filter_file";

	//活动流程
	public static final String MPM_RES_NAME_TYPE_ACTIVE_FLOW = "mtl_sys_actflow_def";

	//成本类型
	public static final String MPM_RES_NAME_TYPE_COST_LIST = "mtl_cost_list";

	//指标
	public static final String MPM_RES_NAME_TYPE_IDINDI = "mtl_ir_indi_define";

	//实体
	public static final String MPM_RES_NAME_TYPE_RES_LIST = "mtl_res_list";

	//数据源表
	public static final String MPM_RES_NAME_TYPE_DATASOURCE = "mtl_camp_data_source";

	//字段
	public static final String MPM_RES_NAME_TYPE_DATASRC_COLUMN = "mtl_camp_datasrc_column";

	//维度字段信息�?
	public static final String MPM_DIM_COLUMN_FEATURE = "mtl_dim_column_feature";

	//短信-彩信的回复代�?
	public static final String MPM_SMS_MMS_CODE_DEF = "mtl_sms_mms_code_def";

	//用户上载的目标客户表
	public static final String MPM_TARGETUSER_FILE = "mtl_targetuser_file";

	//审批流程�?
	public static final String MPM_APPROVE_FLOW_DEF = "mtl_approve_flow_def";

	//确认模板
	public static final String MPM_APPROVE_CONFIRM_TEMPLET = "mtl_approve_confirm_templet";

	//客户�?
	public static final String MPM_CUST_GROUP = "mtl_cust_group";

	//宣传设计组合
	public static final String MTL_PUBLICIZE_COMB = "mtl_publicize_comb";

	//是否是对比用�?
	public static final String MPM_USER_HANDLE_FLAG = "user_handle_flag";

	public static final short MPM_USER_HANDLE_YES = 1;

	public static final short MPM_USER_HANDLE_NO = 0;

	//营销指标类型
	public static final String MPM_INDI_TYPE = "mpm_indi_type";

	//全局指标
	public static final short MPM_INDI_TYPE_GLOBAL = 1;

	//驱动类型相关指标
	public static final short MPM_INDI_TYPE_DRV = 2;

	//指标周期(mtl_ir_indi_define表中的id_period_indi字段)
	public static final String MPM_ID_PERIOD_INDI = "mpm_id_period_indi";

	//日指�?
	public static final short MPM_ID_PERIOD_INDI_DAY = 1;

	//周指�?
	public static final short MPM_ID_PERIOD_INDI_WEEK = 2;

	//月指�?
	public static final short MPM_ID_PERIOD_INDI_MONTH = 3;

	//季度指标
	public static final short MPM_ID_PERIOD_INDI_QUARTER = 4;

	//半年指标
	public static final short MPM_ID_PERIOD_INDI_HALF_YEAR = 5;

	//年度指标
	public static final short MPM_ID_PERIOD_INDI_YEAR = 6;

	//外部资源url类型
	public static final String MPM_EXT_EVALUATION_URL = "mpm_ext_evaluation_url";

	public static final short MPM_EXT_EVALUATION_URL_SEG = 2;

	public static final short MPM_EXT_EVALUATION_URL_CAMP = 1;

	//活动波次指标分析中的汇�?方式
	public static final String MPM_INDI_ANALYSE_GROUPBY = "mpm_indi_analyse_groupby";

	//汇�?方式-按活�?
	public static final short MPM_INDI_ANALYSE_GROUPBY_CAMP = 1;

	//汇�?方式-按活动波�?
	public static final short MPM_INDI_ANALYSE_GROUPBY_CAMPSEG = 2;

	//汇�?方式-按用户分�?
	public static final short MPM_INDI_ANALYSE_GROUPBY_USERSEG = 3;

	//汇�?方式-按地�?
	public static final short MPM_INDI_ANALYSE_GROUPBY_CITY = 4;

	//汇�?方式-按品�?
	public static final short MPM_INDI_ANALYSE_GROUPBY_BRAND = 5;

	//汇�?方式-按日�?
	public static final short MPM_INDI_ANALYSE_GROUPBY_DATE = 6;

	//分析展现方式
	public static final String MPM_ANALYSE_DIS_MODE = "MPM_ANALYSE_DIS_MODE";

	//分析展现方式-曲线�?
	public static final String MPM_ANALYSE_DIS_MODE_TREND = "trend";

	//分析展现方式-柱型�?
	public static final String MPM_ANALYSE_DIS_MODE_BAR = "bar";

	//分析展现方式-饼图
	public static final String MPM_ANALYSE_DIS_MODE_PIE = "pie";

	//排序属�?(字段)
	public static final String MPM_ORDER_FIELD = "mpm_order_field";

	//接触客户�?
	public static final String MPM_ORDER_FIELD_VAL1 = " tval1 ";

	//接触成功客户�?
	public static final String MPM_ORDER_FIELD_VAL2 = " tval2 ";

	//接受营销活动客户�?
	public static final String MPM_ORDER_FIELD_VAL3 = " tval3 ";

	//没接受营�?��动客户数
	public static final String MPM_ORDER_FIELD_VAL4 = " tval4 ";

	//接触成功客户�?
	public static final String MPM_ORDER_FIELD_VAL2_RATE = " val2_rate ";

	//接受营销活动客户�?
	public static final String MPM_ORDER_FIELD_VAL3_RATE = " val3_rate ";

	//没接受营�?��动客户数%
	public static final String MPM_ORDER_FIELD_VAL4_RATE = " val4_rate ";

	//完成任务%
	public static final String MPM_ORDER_FIELD_TASK_RATE = " task_rate ";
	//营销管理系统默认从活动波次开始日期前1个月生成日指标值
	public static final int MPM_DAY_INDI_ANALYSE_BEFORE_MONTH = 1;

	//营销管理系统默认在活动波次结束日期后3个月停止生成日指标值
	public static final int MPM_DAY_INDI_ANALYSE_AFTER_MONTH = 3;

	//营销管理系统默认从活动波次开始日期前1个月生成月指标值
	public static final int MPM_MONTH_INDI_ANALYSE_BEFORE_MONTH = 1;

	//营销管理系统默认在活动波次结束日期后3个月停止生成月指标值
	public static final int MPM_MONTH_INDI_ANALYSE_AFTER_MONTH = 3;
	/** ip */
	public static String HOST_ADDRESS = Configure.getInstance().getProperty("HOST_ADDRESS").trim();// NOPMD by wanglei on 13-6-11 ����3:59
	/** port */
	public static String HOST_PORT = Configure.getInstance().getProperty("HOST_PORT").trim(); // NOPMD by wanglei on 13-6-11 ����3:59
	/** context path */
	public static String CONTEXT_PATH = Configure.getInstance().getProperty("CONTEXT_PATH").trim();// NOPMD by wanglei on 13-6-11 ����3:59
	/** if language is zh */
	public static boolean IS_LANGUAGE_ZH = "zh".equalsIgnoreCase(Configure.getInstance()// NOPMD by wanglei on 13-6-11 ����3:59
			.getProperty("LOCALE_LANGUAGE_DEFAULT").toLowerCase());

	//从配置文件中读取设置
	public static void initConfigure() {
		HOST_ADDRESS = Configure.getInstance().getProperty("HOST_ADDRESS").trim();
		HOST_PORT = Configure.getInstance().getProperty("HOST_PORT").trim();
		CONTEXT_PATH = Configure.getInstance().getProperty("CONTEXT_PATH").trim();
		IS_LANGUAGE_ZH = "zh".equalsIgnoreCase(Configure.getInstance().getProperty("LOCALE_LANGUAGE_DEFAULT")
				.toLowerCase());

	}

	//定义营销评估中需要特殊处理的指标ID
	public static Map mapSpecialIndiDefines = new HashMap();
	static {
		//客户ARPU�?客户总消费额/总客户数
		//客户MOU�?客户总�?话时长（分钟�?总客户数

		//客户挽留-目标用户ARPU�?
		mapSpecialIndiDefines.put("mtl_1_10004", new String[] { "mtl_1_10002", "mtl_1_10001" });
		//客户挽留-目标用户MOU�?
		mapSpecialIndiDefines.put("mtl_1_10005", new String[] { "mtl_1_10003", "mtl_1_10001" });

		//客户关�?-目标用户ARPU�?
		mapSpecialIndiDefines.put("mtl_2_10004", new String[] { "mtl_2_10002", "mtl_2_10001" });
		//客户关�?-目标用户MOU�?
		mapSpecialIndiDefines.put("mtl_2_10005", new String[] { "mtl_2_10003", "mtl_2_10001" });

		//语音套餐-目标用户ARPU�?
		mapSpecialIndiDefines.put("mtl_5_10006", new String[] { "mtl_5_10002", "mtl_5_10001" });
		//语音套餐-目标用户MOU�?
		mapSpecialIndiDefines.put("mtl_5_10007", new String[] { "mtl_5_10005", "mtl_5_10001" });
		//语音套餐-目标用户平均通话次数
		mapSpecialIndiDefines.put("mtl_5_10008", new String[] { "mtl_5_10004", "mtl_5_10001" });

		//彩铃业务营销-目标用户ARPU�?
		mapSpecialIndiDefines.put("mtl_6_10004", new String[] { "mtl_6_10002", "mtl_6_10001" });

		//彩信业务营销-目标用户ARPU�?
		mapSpecialIndiDefines.put("mtl_7_10004", new String[] { "mtl_7_10002", "mtl_7_10001" });
	}

	//mtl_cust_group 表的operator_type字段
	//目标群运算符-�?
	public static final short MPM_CAMPSEG_AGGREGATE_OPERATOR_ADD = 1;

	//目标群运算符-�?
	public static final short MPM_CAMPSEG_AGGREGATE_OPERATOR_SUB = 2;

	//数据源字段是否有效标�?MTL_camp_datasrc_column表的column_status字段
	public static final String MPM_DATASRC_COLUMN_STATUS = "column_status";

	//数据源字�?有效
	public static final short MPM_DATASRC_COLUMN_STATUS_VALID = 1;

	//数据源字�?无效
	public static final short MPM_DATASRC_COLUMN_STATUS_UNVALID = 2;

	//审批人类�?mtl_approve_level_def表的approve_obj_type字段
	public static final String MPM_APPROVE_OBJ_TYPE = "approve_obj_type";

	//审批人类�?策划人所属部门的审批�?
	public static final short MPM_APPROVE_OBJ_TYPE_SELF_DEPT = 1;

	//审批人类�?指定部门的审批人
	public static final short MPM_APPROVE_OBJ_TYPE_APPOINT_DEPT = 2;

	//审批人类�?指定的审批人
	public static final short MPM_APPROVE_OBJ_TYPE_APPOINT_APPROVER = 3;

	//审批人类�?上N级部门的审批�?
	public static final short MPM_APPROVE_OBJ_TYPE_TOPN_DEPT = 4;

	//审批令牌 mtl_campseg_approver_list表的approve_token字段
	public static final String MPM_APPROVE_TOKEN = "approve_token";

	//不持有令�?
	public static final short MPM_APPROVE_TOKEN_NOT_HOLD = 0;

	//持有令牌
	public static final short MPM_APPROVE_TOKEN_HOLD = 1;

	//mtl_camp_baseinfo 表的camp_pri_id字段
	//活动优先�?- 紧�?
	public static final short MPM_CAMP_PRI_URGENCY = 1;

	//活动优先�?- �?
	public static final short MPM_CAMP_PRI_HIGH = 2;

	//审批建议发起方式 mtl_approve_advice表的sponsor_type字段
	public static final String MPM_APPROVE_SPONSOR_TYPE = "sponsor_type";

	//审批建议发起方式-让别人提审批意见
	public static final short MPM_APPROVE_SPONSOR_TYPE_ADVICE = 1;

	//审批建议发起方式-审批转发
	public static final short MPM_APPROVE_SPONSOR_TYPE_REDIRECT = 2;

	//审批建议参与标志 mtl_approve_advice表的act_end_flag字段
	public static final String MPM_APPROVE_ACT_END_FLAG = "act_end_flag";

	//审批建议参与标志-未结束，可参�?
	public static final short MPM_APPROVE_ACT_END_FLAG_NOT = 0;

	//审批建议参与标志-已结束，不能再参�?
	public static final short MPM_APPROVE_ACT_END_FLAG_YES = 1;

	//审批流程-通过邮件审批 mtl_approve_flow_def表的approve_flow_id字段
	public static final String MPM_APPROVE_FLOW_ID_BY_OA = "-1";

	//审批流程-不需要审�?
	public static final String MPM_APPROVE_FLOW_ID_NO_APPROVE = "0";

	//随机筛�?标识－随机筛�?mtl_templet_select表的 campseg_random_flag字段
	public static final short MPM_SELECT_RANDOM_YES = 1;

	//随机筛�?标识－不随机
	public static final short MPM_SELECT_RANDOM_NO = 0;

	//客户群类�?mtl_cust_group表的cust_group_type字段
	public static final String MPM_CUST_GROUP_TYPE = "custGroupType";

	//客户群类�?营销规则积累
	public static final short MPM_CUST_GROUP_TYPE_DEPOSIT = 0;

	//客户群类�?客户群SQL定义
	public static final short MPM_CUST_GROUP_TYPE_DEFINE = 1;

	//客户群类�?客户群清单，从客户群定义创建来的客户清单
	public static final short MPM_CUST_GROUP_TYPE_LIST_FROM_DEFINE = 2;

	//客户群类�?客户群清单，从导入文件创建来的客户清�?
	public static final short MPM_CUST_GROUP_TYPE_LIST_FROM_FILE = 3;

	//客户群类�?客户群清单，从已有客户群清单运算创建来的新客户清�?
	public static final short MPM_CUST_GROUP_TYPE_LIST_FROM_OPERATE = 4;

	//客户群类�?客户群清单，从营�?��行效果来创建
	public static final short MPM_CUST_GROUP_TYPE_LIST_FROM_EXECFEEDBACK = 5;

	//步骤流程定义 mtl_sys_actflow_def表flow_id字段
	//新增客户群定义步骤流程编�?
	public static final String MPM_FLOW_ADD_CUST_GROUP_DEFINE = "flow_def_001";

	//通过客户群定义新增客户群清单步骤流程编号
	public static final String MPM_FLOW_ADD_CUST_GROUP_FROM_DEFINE = "flow_def_002";

	//通过导入文件新增客户群清单步骤流程编�?
	public static final String MPM_FLOW_ADD_CUST_GROUP_FROM_FILE = "flow_def_003";

	//通过已有客户群清单运算新增客户群清单步骤流程编号
	public static final String MPM_FLOW_ADD_CUST_GROUP_FROM_OPERATE = "flow_def_004";

	//通过执行效果新增客户群清单步骤流程编�?
	public static final String MPM_FLOW_ADD_CUST_GROUP_FROM_EXECFEEDBACK = "flow_def_005";

	//客户群访问标�?mtl_cust_group表的cust_group_access_token字段
	public static final String MPM_CUST_GROUP_ACCESS_TOKEN = "custGroupAccessToken";

	//私有客户�?
	public static final short MPM_CUST_GROUP_ACCESS_TOKEN_PRIVATE = 0;

	//共用客户群（但还有客户群�?��地市限制，即其他用户有客户群�?��地市的访问权限�?并且客户群是共用的，别的用户才可以访问此客户群）
	public static final short MPM_CUST_GROUP_ACCESS_TOKEN_COMMON = 1;

	//客户群评估标�?-不评�?added by caolifeng 20100621
	//客户群访问标�?mtl_cust_group表的evaluate_flag字段
	public static final String MPM_CUST_GROUP_EVALUATE_FLAG = "evaluateFlag";

	public static final short MPM_CUST_GROUP_EVALUATE_FLAG_NO = 0;

	//客户群评估标�?-评估
	public static final short MPM_CUST_GROUP_EVALUATE_FLAG_YES = 1;

	//客户群状�?
	public static final String MPM_CUST_GROUP_STATUS = "cust_group_status";

	//客户群状�?不可用，还在修改�?
	public static final short MPM_CUST_GROUP_STATUS_UNVALID_MODIFING = 0;

	//客户群状�?可用
	public static final short MPM_CUST_GROUP_STATUS_VALID = 1;

	//客户群状�?不可用，等待生成物理客户清单�?
	public static final short MPM_CUST_GROUP_STATUS_UNVALID_WAITING = 2;

	//客户群状�?不可用，生成物理客户清单表失�?
	public static final short MPM_CUST_GROUP_STATUS_UNVALID_FAILURE = 3;

	//客户群状�?不可用，正在生成�?
	public static final short MPM_CUST_GROUP_STATUS_UNVALID_RUNNING = 4;

	//营销活动客户群�?择表 mtl_campseg_cust_group 中客户群用�?类型 cust_group_used_type字段
	//0-作为目标客户群使�?
	public static final short MPM_CUST_GROUP_USED_TARGET = 0;

	//1-作为对比客户群使�?
	public static final short MPM_CUST_GROUP_USED_COMPARE = 1;

	//营销案所属品�?mtl_camp_baseinfo表中brand_id字段含义（不使用维表中的品牌值，因为和维表没有啥业务关联，只是一个标识）
	public static final String MPM_CAMP_BRAND = "camp_brand";

	//�?��品牌
	public static final short MPM_CAMP_BRAND_ALL = 0;

	//全球通品�?
	public static final short MPM_CAMP_BRAND_GOTONE = 1;

	//动感地带品牌
	public static final short MPM_CAMP_BRAND_MZONE = 2;

	//神州行品�?
	public static final short MPM_CAMP_BRAND_SZX = 3;

	//接触控制的类�?
	public static final String MPM_CONTACT_CONTROL_TYPE = "contact_control_type";

	//接触控制的类�?
	public static final String MPM_CUST_CODE = "cust_code";

	//	//�?��品牌
	//	public static final short MPM_CONTACT_CONTROL_TOTAL = 1;
	//
	//	//全球通品�?
	//	public static final short MPM_CONTACT_CONTROL_SMS = 2;
	//
	//	//动感地带品牌
	//	public static final short MPM_CONTACT_CONTROL_CALL = 3;

	//mtl_approve_auth表中auth_type字段的含�?
	//审批委托授权
	public static final short MPM_AUTH_TYPE_AUDIT = 0;

	//确认委托授权
	public static final short MPM_AUTH_TYPE_CONFIRM = 1;

	//辽宁OA审批结果含义
	public static final String MPM_OA_APPROVE_RESULT_WAITING = "00";

	public static final String MPM_OA_APPROVE_RESULT_APPROVING = "01";

	public static final String MPM_OA_APPROVE_RESULT_APPROVED = "02";

	public static final String MPM_OA_APPROVE_RESULT_XMLERROR = "03";

	public static final String MPM_OA_APPROVE_RESULT_DATAERROR = "04";

	public static final String MPM_OA_APPROVE_RESULT_GUIDERROR = "05";

	public static final String MPM_OA_APPROVE_RESULT_OTHER = "06";

	//辽宁OA会签类型
	//地市会签
	public static final short MPM_OA_REQUEST_RELATION_DEPT = 0;

	//省中心会�?
	public static final short MPM_OA_REQUEST_RELATION_COMP = 1;

	//主办人员
	public static final short MPM_OA_REQUEST_RELATION_MAIN = 2;

	//协办人员
	public static final short MPM_OA_REQUEST_RELATION_NOMAIN = 3;

	//地市主管领导
	public static final short MPM_OA_REQUEST_RELATION_CITYCHARGE = 4;

	//地市总经�?
	public static final short MPM_OA_REQUEST_RELATION_CITYMANAGER = 5;

	//省主管部门文�?
	public static final short MPM_OA_REQUEST_RELATION_CHARGEWRIT = 6;

	//省主管部门领�?
	public static final short MPM_OA_REQUEST_RELATION_CHARGELEADER = 7;

	//省主管部门经办人
	public static final short MPM_OA_REQUEST_RELATION_CHARGECLERK = 8;

	//省公司领�?
	public static final short MPM_OA_REQUEST_RELATION_COMPLEADER = 9;

	//部门领导
	public static final short MPM_OA_REQUEST_RELATION_DEPTLEADER = 10;

	//统一接触平台�?��使用字段
	//接触平台给营�?��理系统分配的唯一标识
	public static final String MPM_UNIFIED_TOUCH_SYS_ID = "AI-JF";

	//系统内部子模�?
	public static final String MPM_UNIFIED_TOUCH_SUB_SYS_ID = "AI-JF-YX";

	//接触平台中具体发送的短信渠道ID
	public static final String MPM_UNIFIED_TOUCH_SMS_CHANNEL_ID = "SMS1";

	public static final String MPM_UNIFIED_TOUCH_MMS_CHANNEL_ID = "MMS1";

	public static final String MPM_UNIFIED_TOUCH_EMAIL_CHANNEL_ID = "EMAIL1";

	//发�?�?��时间 范围 1---23
	public static final int MPM_UNIFIED_TOUCH_START_TIME = 8;

	//发�?结束时间 范围 1---23
	public static final int MPM_UNIFIED_TOUCH_END_TIME = 20;

	//任务紧�?程度 范围 0  普�?  1 紧�?
	//public static final int MPM_UNIFIED_TOUCH_LEVEL = 0;

	//手机号码字段名称
	public static final String MPM_UNIFIED_TOUCH_END_COL = "product_no";

	//表类�?
	public static final String DATA_SOURCE_CYCLE_TYPE = "source_cycle_type";
	//按月分表
	public static final short DATA_SOURCE_CYCLE_TYPE_MONTH = 1;
	//按天分表
	public static final short DATA_SOURCE_CYCLE_TYPE_DAY = 2;
	//不分�?
	public static final short DATA_SOURCE_CYCLE_TYPE_NO = 3;

	//调用web service日志状�?
	//调用外部服务
	public static final short MPM_CALLWS_LOG_TYPE_OUT = 0;
	//服务被外部调�?
	public static final short MPM_CALLWS_LOG_TYPE_IN = 1;

	//web服务调用状�?
	//调用�?
	public static final short MPM_CALLWS_LOG_STATUS_CALLING = 0;
	//调用成功
	public static final short MPM_CALLWS_LOG_STATUS_SUCCESS = 1;
	//调用失败
	public static final short MPM_CALLWS_LOG_STATUS_FAILURE = 2;
	//调用URL为空
	public static final short MPM_CALLWS_LOG_STATUS_NULL = 3;

	//内容知识管理
	public static final String UNIFIED_URL = "http://10.1.251.240:7009";

	//免打扰类型分�?
	public static final String DIM_AVOID_BOTHER_TYPE_CLASS = "dim_avoid_bother_type_class";
	//渠道免打�?
	public static final short DIM_AVOID_BOTHER_TYPE_CLASS_CHANELL = 0;
	//客户免打�?
	public static final short DIM_AVOID_BOTHER_TYPE_CLASS_CUST = 1;
	public static final short DIM_AVOID_BOTHER_TYPE_ALL_CHANELL_ID = 0;

	//格式化短信的标识
	public static final short MTL_FMT_MESS_FLAG_NO = 0;
	public static final short MTL_FMT_MESS_FLAG_YES = 1;

	public static final String MPM_INTERFACE_RETURN_CODE = "mpm_interface_return_code";

	//活动类型是否进行权限控制标识含义
	public static final short MTL_DRV_ACCESS_FLAG_YES = 1; //接收权限控制，被授权人才有权�?
	public static final short MTL_DRV_ACCESS_FLAG_NO = 0; //不接收控制，�?��人都有权�?
	//活动类型授权对象标识
	public static final short MTL_DRV_AUTH_FLAG_USER = 1; //权限授予用户
	public static final short MTL_DRV_AUTH_FLAG_GROUP = 2; //权限授予用户�?
	//活动类型授权�?
	public static final short MTL_DRV_AUTH_TOKEN_YES = 1; //授予其权�?
	public static final short MTL_DRV_AUTH_TOKEN_NO = 0; //对齐屏蔽权限

	//解决程序分支，liugao add 2010-10-22
	public static final String IF_SHOW_TRUE = "1"; //显示
	public static final String IF_SHOW_FLASE = "0"; //不显�?

	//营销监控指标前缀
	public static final String INDEX_HEADER = "index_";

	//普�?字段
	public static final short DCP_NO_KEY = 0;
	//主键
	public static final short DCP_P_KEY = 1;
	//外键
	public static final short DCP_F_KEY = 2;

	//主键生成方式为uuid
	public static final short DCP_PG_UUID = 1;
	//主键生成方式为日�?时间(毫秒)
	public static final short DCP_PG_DATET = 2;
	//主键生成方式为自增长
	public static final short DCP_PG_SELFG = 3;
	//主键生成方式为外键方�?
	public static final short DCP_PG_FKEY = 9;
	//主键生成方式为默认�?
	public static final short DCP_PG_DEFAULT = 8;
	//主键生成方式为前台传�?
	public static final short DCP_PG_SUBMIT = 7;

	//全删全增修改方式 delete insert
	public static final short DCP_TB_DELINSERT = 2;
	//正常修改方式update
	public static final short DCP_TB_UPDATE = 1;

	public static final String MCD_PROPERTY_NAME = "ASIAINFO_PROPERTIES";

	/** 营销基本信息传递接口*/
	public static final String AIBI_MCD_CAMPSEGINFO_CODE = "AIBI_MCD_CAMPSEGINFO";

	/** 营销基本信息往综合网关传递接口*/
	public static final String AIBI_MCD_CAMPSEGINFO_TO_GATEWAY = "AIBI_MCD_CAMPSEGINFO_TO_GATEWAY";

	/** 状态变更调用接口**/
	public static final String AIBI_MCD_CHANGE_CAMPSEG_STATUS_CODE = "AIBI_MCD_CHANGE_CAMPSEG_STATUS";

	/** 黑名单受限客户查询接口*/
	public static final String AIBI_MCD_BLACK_LIMIT_CUST = "AIBI_MCD_BLACK_LIMIT_CUST";

	/** 调用CI的code代码�?*/
	public static final String CALLWS_CI_CODE = "AIBI_CI_CUST";

	/** 调用CM的code 客户群代码*/
	public static final String CALLWS_CUSTOMERS_CM_CODE = "AIBI_CM_CUSTOMERS_CUST";

	/** 调用COC的code 客户群代码*/
	public static final String CALLWS_CREATE_CAMPAIGN_CODE = "AIBI_CRM_CREATE_CAMPAIGN";
	/** 调用CI的客户群列表代码�?*/
	public static final String AIBI_CI_CUST_LIST_CODE = "AIBI_CI_CUST_LIST";
	/** 调用CI的客户群创建接口代码�?*/
	public static final String AIBI_CI_CUST_CREATE_CODE = "AIBI_CI_CUST_CREATE";
	/** 调用CI的客户群创建接口回调方法 */
	public static final String AIBI_CI_CUST_CREATE_CALLBACK_CODE = "AIBI_CI_CUST_CREATE_CALLBACK";
	/** 调用CI的客户群规则展示接口代码�?*/
	public static final String AIBI_CI_CUST_RULE_CODE = "AIBI_CI_CUST_RULE";
	/** 营销时机创建*/
	public static final String AIBI_MCD_CHANCE_CREATE_CODE = "AIBI_MCD_CHANCE_CREATE";
	/** 营销时机显示*/
	public static final String AIBI_MCD_CHANCE_SHOW_CODE = "AIBI_MCD_CHANCE_SHOW";
	/**复杂事件规则创建*/
	public static final String AIBI_MCD_CEP_CREATE_CODE = "AIBI_CEP_CREATE_CODE";
	/**复杂事件规则保存*/
	public static final String AIBI_MCD_CEP_SAVE_CODE = "AIBI_CEP_SAVE_CODE";
	/**复杂事件更新清单表*/
	public static final String AIBI_CEP_IMP_CODE = "AIBI_CEP_IMP_CODE";
	/**复杂事件获取规则参数*/
	public static final String AIBI_MCD_CEP_FUNCTUON_INFO = "AIBI_MCD_CEP_FUNCTUON_INFO";
	/**复杂事件规则创建回调页面*/
	public static final String AIBI_MCD_CEP_CREATE_CODE_CALLBACK = "AIBI_CEP_CREATE_CODE_CALLBACK";
	/**复杂事件 根据事件id获取复杂事件信息*/
	public static final String AIBI_MCD_GET_CEP_EVENT_INFO = "AIBI_CEP_GET_EVENT_INFO";
	/**广告选择创建*/
	public static final String AIBI_MCD_AD_POSITION_CREATE_CODE = "AIBI_AD_POSITION_CREATE_CODE";
	
	public static final String SEND_XML_CALL_BJ = "SEND_XML_CALL_BJ";

	public static final String SEND_XML_WEB_BJ = "SEND_XML_WEB_BJ";
	/** 调用CI的code代码�?*/
	public static final String CALLWS_IOP_CODE = "AIBI_IOP_CUST";

	public static final String ZJCU_CRM = "SEND_CRM";

	public static final String AIBI_MCD_TO_UNITOUCH_SMS = "AIBI_MCD_TO_UNITOUCH_SMS";

	/** 用于审批流转的客户群数条件id */
	public static final String CUSTGROUP_NUM_COND_ID = "20000000";

	//是否�?��部门领导审批
	public static final String NEED_HOD_APPROVE = "30000000";
	public static final String NEED_CCO_APPROVE = "40000000";

	//判断周期执行时的类型-日执�?
	public static final short DISTRIBUTE_DAYILY = 1;

	//判断周期执行时的类型-月执�?
	public static final short DISTRIBUTE_MONTHLY = 2;

	//判断周期执行时的类型-周执�?
	public static final short DISTRIBUTE_WEEKLY = 3;

	//绿色通道的驱动类�?
	public static final int GREEN_PATH = 1;

	//VGOP 产品信息字典类型
	public interface SI_DIC_CODE_TYPE {

		//业务平台
		public static final short SVC_PLAT = 4;

		//信息格式
		public static final short INFO_FORMAT = 6;

		//信息类型
		public static final short INFO_TYPE = 8;

	}

	//互动营销规则配置页面
	public static final short MPM_INTERACTIVE_PAGE_ID = 7;

	//常规营销汇�?日志
	public static final short MCD_NORMAL_GATHER_LOG = 1;
	//常规营销清单日志
	public static final short MCD_NORMAL_INVENTORY_LOG = 2;
	//实时营销清单日志
	public static final short MCD_INSTANT_INVENTORY_LOG = 3;
	//交互日志
	public static final short MCD_INTERACTIVE_LOG = 4;

	//文件接口：日志反馈
	public static final short MCD_FILE_INTERFACE_LOG = 5;

	//省份配置key
	public static final String KEY_PROVINCE = "PROVINCE";

	public static final String DCP_OUTPUT_PATH = "/mpm/dcp/output/";
	//默认的活动场景类型�?
	public static final String DEFAULT_SCENE_TYPE = "0";
	//客户端软件推荐下载场景类�?
	public static final String RJXZ_SCENE_TYPE = "602";
	//互动软件dimtableID
	public static final String DIMTABLEID_SOFTWARE = "mcd_interactive_software_info";
	//互动软件dimtableID
	public static final String DIMTABLEID_SOFTWARE_VERSION = "mcd_interactive_software_version";

	//活动实际含义1：活动基本信�?2：规�?3：波�?
	//主活�?
	public static final short CAMP_CLASS_BASIC = 1;
	//客户�?
	public static final short CAMP_CLASS_CUSTGROUP = 2;
	//波次
	public static final short CAMP_CLASS_WAVE = 3;

	/** 任务执行状�?           **/
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

	public static final String DB_TYPE_ORACLE = "ORACLE";
	public static final String DB_TYPE_DB2 = "DB2";
	public static final String DB_TYPE_MYSQL = "MYSQL";
	public static final String DB_TYPE_GP = "POSTGRESQL";
	public static final String DB_TYPE_GBASE = "GBASE";
	//客户群清单模板表名
	public static final String MTL_DUSER_YYYYMMDDHHMMSSSSS = "MTL_DUSER_YYYYMMDDHHMMSSSSS";
	
	//任务基础客户清单表
	public static final String MTL_DUSER_T_PREFIX = "MTL_DUSER_T_";
	//任务派单客户清单表
	public static final String MTL_DUSER_O_PREFIX = "MTL_DUSER_O_";
	//活动客户清单表
	public static final String MTL_DUSER_A_PREFIX = "MTL_DUSER_A_";
	//活动客户清单表
	public static final String MTL_DUSER_PREFIX = "MTL_DUSER_";
	
	//客户群清单模板表名  浙江
	public static final String MTL_CUSER_YYYYMMDDHHMMSSSSS = "MTL_CUSER_YYYYMMDDHHMMSSSSS";
	//客户群清单表名 前缀   浙江
	public static final String MTL_DUSER_I_PREFIX = "MTL_DUSER_I_";

	//活动优先级
	public static final String CAMPSEG_PRI_EMERGENCY = "32767";
	public static final String CAMPSEG_PRI_HIGH = "32766";
	public static final String CAMPSEG_PRI_NORMAL = "32765";
	public static final String CAMPSEG_PRI_LOW = "32764";
	//派单接口服务ID
	public static final String MPM_CAMPSEG_SERVICE = "mpmCampsegService";

	//渠道确认状态页面前台下拉列表展现值
	//待处理
	public static final String CAMPSEG_CONFIRM_STATE_WAIT = "1";
	//已处理
	public static final String CAMPSEG_CONFIRM_STATE_FINISHED = "2";
	//全部
	public static final String CAMPSEG_CONFIRM_STATE_ALL = "3";

	public static final int EVAL_DAYTYPE_DAY = 0;
	public static final int EVAL_DAYTYPE_DAYTOTAL = 1;

	public static final int EVAL_DOWNTYPE_CONTACT = 1;
	public static final int EVAL_DOWNTYPE_REPONSE = 2;
	public static final int EVAL_DOWNTYPE_ORDER = 3;

	public static final int EVAL_DIMENSION_CITY = 2;
	public static final int EVAL_DIMENSION_COUNTY = 3;
	public static final int EVAL_DIMENSION_DEPT = 4;
	public static final int EVAL_DIMENSION_CHANNEL = 5;
	public static final int EVAL_DIMENSION_BUSSI = 6;
	public static final String EVAL_DIMEVALUE_CITY = "地市";
	public static final String EVAL_DIMEVALUE_COUNTY = "区县";
	public static final String EVAL_DIMEVALUE_DEPT = "片区";
	public static final String EVAL_DIMEVALUE_CHANNEL = "渠道";
	public static final String EVAL_DIMEVALUE_BUSSI = "业务";

	public static final int ORDER_CYCLE_DAY = 1;
	public static final int ORDER_CYCLE_MONTH = 2;

	/** 网厅配置页面 */
	public static final String AIBI_WEB_HALL_CONFIG = "AIBI_WEB_HALL_CONFIG";
	/** 变量来源 */
	public static final String VAR_SOURCE_CONSTANT = "0";
	public static final String VAR_SOURCE_CHANCE = "1";
	public static final String VAR_SOURCE_EVENT_C3 = "2";
	public static final String VAR_SOURCE_EVENT_CEP = "3";
	public static final String VAR_SOURCE_CHANCEMATCHING = "4";

	/** C3的ftp名字 */
	public static final String AIBI_NBS_FTP_CONFIG = "nbs";

	/** 系统ID：COC*/
	public static final String SYSTEM_COC = "COC";

	/**营销案管理:list,绑定的分业 session id*/
	public static final String CAMPLIST_PAGECOLLECTION = "CAMPLIST_PAGECOLLECTION";

	/**目标分析管理:list,绑定的分业 session id*/
	public static final String TARGET_PAGECOLLECTION = "TARGET_PAGECOLLECTION";

	public static final String CAMPSEGLIST_PAGECOLLECTION = "CAMPSEGLIST_PAGECOLLECTION";

	/**派单任务*/
	public static final String CAMPSEGTASKLIST_PAGECOLLECTION = "CAMPSEGTASKLIST_PAGECOLLECTION";
	/**活动模版列表* */
	public static final String CAMPSEGTMPLIST_PAGECOLLECTION = "CAMPSEGTMPLIST_PAGECOLLECTION";

	public static final String MCD_CAMPSEG_SEDNOD_PHONE_SERVICE = "MCD_CAMPSEG_SEDNOD_PHONE_SERVICE";
	/**场景营销活动-查询*/
	public static final String SCENECAMPSEGLIST_PAGECOLLECTION = "SCENECAMPSEGLIST_PAGECOLLECTION";
	/**
	 * BOSS提醒中心的提醒类型
	 */
	public static final String POLICY_REMIND_TYPE_ZERO = "R0";
	public static final String POLICY_REMIND_TYPE_FIVE = "R5";
	/**
	 * 策略处理器实例缓存KEY
	 */
	public static final String POLICY_HANDLER_PREFIX = "POLICY_HANDLER_";//+服务ID
	public static final String POLICY_JDYX_ID = "1"; //夹带营销策略对应的外部事件源ID

	/** oa确认 */
	public static final String OA_CONFIRM = "OA_CONFIRM";
	/** oa查询 */
	public static final String OA_QUERY = "OA_QUERY";

	/** mcd调用CRM的http方式实时派单接口*/
	public static final String AIBI_MCD_CRM_SENDORDER_CODE = "AIBI_MCD_CRM_SENDORDER";
	/** 调用HUAWEI的code 客户群代码*/
	public static final String CALLWS_CUSTOMERS_HUAWEI_CODE = "AIBI_HUAWEI_CUSTOMERS_CUST";

	/**
	 * 客户群类型 来自与其他活动反馈
	 */
	public static final String CUSTGROUP_TYPE_CAMPSEGFEEDBACK = "QTHDFK";

	public static final String MEMCACHE_TYPE_LOCAL = "local";//本机
	public static final String MEMCACHE_TYPE_DISTRIBUTED = "distributed";//分布式

	/**
	 * 场景营销
	 */
	public static final String DIM_CAMP_SCENE_SERVICE = "dimCampSceneService";
	
	/**
	 * MCD_ZJ 分页参数
	 */
	public static final int pageNum = 1;
	public final static int PAGE_SIZE = 10;
	
	/**
	 * 新建策略页面 选择政策翻页每页显示5条数据
	 */
	public final static int SMALL_PAGE_SIZE_LABEL = 5;
	/**
	 * 新建策略页面 选择政策翻页每页显示10条数据
	 */
	public final static int MIDDLE_PAGE_SIZE_LABEL = 10;
	
	//客户群生成周期
	public static final String CUST_INFO_GRPUPDATEFREQ = "grpUpdateFreq";
	//1代表一次性
	public static final int CUST_INFO_GRPUPDATEFREQ_ONE = 1;
	//2代表月周期
	public static final int CUST_INFO_GRPUPDATEFREQ_MONTH = 2;
	//3代表日周期
	public static final int CUST_INFO_GRPUPDATEFREQ_DAY = 3;
	
	//1策略与渠道对应关系--客户接触方式  一次性
	public static final int MTL_CHANNLE_DEF_CONTACETTYPE_ONE = 1;
	//2策略与渠道对应关系--客户接触方式  周期性
	public static final int MTL_CHANNLE_DEF_CONTACETTYPE_CYCLE= 2;
	
	public static final String IMCD_MTL_GROUPINFO_SERVICE = "mcdMtlGroupInfoService";
	public static final String IMCD_mcd_custgroup_attr_list_SERVICE = "mtlGroupAttrRelService";

	/**
	 * 客户群生成周期 add by jinl
	 */
	public static final String  mcd_custgroup_def_UPDATE_CYCLE_1 = "一次性";
	public static final String  mcd_custgroup_def_UPDATE_CYCLE_2 = "月周期";
	public static final String  mcd_custgroup_def_UPDATE_CYCLE_3 = "日周期";
	
	public static final String MPM_ZJMARKET_APPROVEWS_CLIENT = "mpmZjmarketApproveWsClient";
	
//	客户群校验时随机查询的数据条数
	public static final int RANDOM_CUSTOM_GROUP_NUM = 5;
	
	/** 掉cep保存Lac-ci的方法 */
	public static final String  AIBI_CEP_SAVE_LACCI = "AIBI_CEP_SAVE_LACCI";
	
	/**
	 * 首页优先级 add by lixq10 2016年3月17日10:25:09
	 */
	public static final String PRIORITY_SERVICE_BEAN_ID = "campsegPriorityService";

}
