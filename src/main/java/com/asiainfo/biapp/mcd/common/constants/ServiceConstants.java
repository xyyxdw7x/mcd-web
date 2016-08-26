package com.asiainfo.biapp.mcd.common.constants;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.biframe.utils.config.Configure;

/**
 * 
 * Title: ServiceConstants.java  <br>
 * Description: 业务常量类,定义业务相关的常量类信息<br>
 *              如：标签状态、标签使用类型等<br>
 * Copyright: (C) Copyright 1993-2020 AsiaInfo Holdings, Inc<br>
 * Company: 亚信联创科技（中国）有限公司<br>
 * 
 * @author chengjia 2013-5-7 上午10:57:43
 * @version 1.0
 */
public class ServiceConstants {

	/**
	 * 元素类型
	 */
	/** 元素类型，运算符 */
	public static final int ELEMENT_TYPE_OPERATOR = 1;
	/** 元素类型，标签ID */
	public static final int ELEMENT_TYPE_LABEL_ID = 2;
	/** 元素类型，括号 */
	public static final int ELEMENT_TYPE_BRACKET = 3;
	/** 元素类型，产品ID */
	public static final int ELEMENT_TYPE_PRODUCT_ID = 4;
	/** 元素类型，清单表名 */
	public static final int ELEMENT_TYPE_LIST_ID = 5;
	/** 元素类型，客户群规则 */
	public static final int ELEMENT_TYPE_CUSTOM_RULES = 6;

	/**
	 * 标签类型
	 */
	/** 标签类型，1=标志型，用0、1来作为值的； */
	public static final int LABEL_TYPE_SIGN = 1;
	/** 标签类型，2=得分型，用0到1的小数作为值； */
	public static final int LABEL_TYPE_SCORE = 2;
	/** 标签类型，3=属性型，单独列，每个值不同的。 */
	public static final int LABEL_TYPE_ATTR = 3;
	/** 标签类型，4=指标型，存具体的指标值； */
	public static final int LABEL_TYPE_KPI = 4;
	/** 标签类型，5=枚举型，列的值有对应的维表，下拉展示； */
	public static final int LABEL_TYPE_ENUM = 5;
	/** 标签类型，6=日期型，字符串类型的日期值。 */
	public static final int LABEL_TYPE_DATE = 6;
	/** 标签类型，7=模糊型，存字符串，like查询 */
	public static final int LABEL_TYPE_TEXT = 7;
	/** 标签类型，8=组合型，对应多个列，数据是纵表存储。 */
	public static final int LABEL_TYPE_VERT = 8;
	/** 标签类型，9=按位与标签 */
	public static final int LABEL_TYPE_BIT = 9;
	/** 标签类型，10=组合型 */
	public static final int LABEL_TYPE_COM = 10;
	/** 标签类型，1=标志型，用0、1来作为值的； */
	public static final String LABEL_TYPE_SIGN_STR = "标识型";
	/** 标签类型，2=得分型，用0到1的小数作为值； */
	public static final String LABEL_TYPE_SCORE_STR= "得分型";
	/** 标签类型，3=属性型，单独列，每个值不同的。 */
	public static final String LABEL_TYPE_ATTR_STR = "属性型";
	/** 标签类型，4=指标型，存具体的指标值； */
	public static final String LABEL_TYPE_KPI_STR = "指标型";
	/** 标签类型，5=枚举型，列的值有对应的维表，下拉展示； */
	public static final String LABEL_TYPE_ENUM_STR = "枚举型";
	/** 标签类型，6=日期型，字符串类型的日期值。 */
	public static final String LABEL_TYPE_DATE_STR = "日期型";
	/** 标签类型，7=文本型，存字符串，like查询 */
	public static final String LABEL_TYPE_TEXT_STR = "文本型";
	/** 标签类型，8=组合型，对应多个列，数据是纵表存储。 */
	public static final String LABEL_TYPE_VERT_STR = "组合型";
	/** 标签类型，9=按位与标签。 */
	public static final String LABEL_TYPE_BIT_STR = "按位与标签";
	/**标签类型，10=组合型   */
	public static final String LABEL_TYPE_COM_STR = "组合型 ";
	/**
	 * 标签周期类型
	 */
	/** 标签周期类型，1=日周期  */
	public static final int LABEL_CYCLE_TYPE_D = 1;
	/** 标签周期类型，2=月周期  */
	public static final int LABEL_CYCLE_TYPE_M = 2;
	/** 标签周期类型，日周期:1 */
	public static final String DAY_CYCLE_LABEL = "日周期";
	/** 标签周期类型，月周期:2 */
	public static final String MONTH_CYCLE_LABEL = "月周期";
	
	/**
	 * 标签对应的元数据列类型:column_data_type_id
	 */
	/** 元数据列类型，数字类型:1 */
	public static final int COLUMN_DATA_TYPE_NUM = 1;
	/** 元数据列类型，字符串类型:2 */
	public static final int COLUMN_DATA_TYPE_VARCHAR = 2;
	/** 元数据列类型，数字类型:number */
	public static final String COLUMN_TYPE_NUMBER = "number";
	/** 元数据列类型，字符串类型:char */
	public static final String COLUMN_TYPE_CHAR = "char";
	/** 元数据列类型，小数类型:decimal */
	public static final String COLUMN_TYPE_DECIMAL = "decimal";
	/** 元数据列类型，数字类型:integer */
	public static final String COLUMN_TYPE_INTEGER = "integer";

	/**
	 * 客户群创建类型
	 */
	/** 客户群创建类型：1 标签创建 */
	public static final int CUSTOMER_CREATE_TYPE_BY_LABEL = 1;
	/** 客户群创建类型：2 标签微分 */
	public static final int CUSTOMER_CREATE_TYPE_BY_LABEL_DIFFERENTIAL = 2;
	/** 客户群创建类型：3 标签分析 */
	public static final int CUSTOMER_CREATE_TYPE_BY_LABEL_ANALYSIS = 3;
	/** 客户群创建类型：4 客户群运算 */
	public static final int CUSTOMER_CREATE_TYPE_BY_CUSTOMER_CALCULATE = 4;
	/** 客户群创建类型：5 客户群分析 */
	public static final int CUSTOMER_CREATE_TYPE_BY_CUSTOMER_ANALYSIS = 5;
	/** 客户群创建类型：6 客户群微分 */
	public static final int CUSTOMER_CREATE_TYPE_BY_CUSTOMER_DIFFERENTIAL = 6;
	/** 客户群创建类型：7 文件导入创建 */
	public static final int CUSTOMER_CREATE_TYPE_BY_FILE_IMPORT = 7;
	/** 客户群创建类型：8 产品创建 */
	public static final int CUSTOMER_CREATE_TYPE_BY_PRODUCT = 8;
	/** 客户群创建类型：9 营销策略创建 */
	public static final int CUSTOMER_CREATE_TYPE_BY_PRODUCT_TACTIC = 9;
	/** 客户群创建类型：10 指标微分（来自自助分析平台） */
	public static final int CUSTOMER_CREATE_TYPE_BY_SA = 10;
	/** 客户群创建类型：11 外部系统推送的（来自自助分析平台） */
	public static final int CUSTOMER_CREATE_TYPE_BY_OTHER_SYS = 11;
	/** 客户群创建类型：12 外部表导入创建 */
	public static final int CUSTOMER_CREATE_TYPE_BY_TABLE_IMPORT = 12;
	
	/**
	 * 客户群创建日志编码
	 */
	/** 客户群创建日志编码：所有创建客户群日志父类型*/
	public static final String COC_CUSTOMER_MANAGE_ADD = "COC_CUSTOMER_MANAGE_ADD";
	/** 客户群创建日志编码： 标签创建客户群日志类型*/
	public static final String COC_CUSTOMER_MANAGE_ADD_LABEL = "COC_CUSTOMER_MANAGE_ADD_LABEL";
	/** 客户群创建日志编码： 导入创建客户群日志类型*/
	public static final String COC_CUSTOMER_MANAGE_ADD_IMPORT = "COC_CUSTOMER_MANAGE_ADD_IMPORT";
	/** 客户群创建日志编码： 产品创建客户群日志类型*/
	public static final String COC_CUSTOMER_MANAGE_ADD_PRODUCT = "COC_CUSTOMER_MANAGE_ADD_PRODUCT";
	
	/**
	 * 标签分析 类型编码
	 */
	/** 标签分析 所有标签分析日志父类型*/
	public static final String COC_LABEL_ANALYSIS = "COC_LABEL_ANALYSIS";
	/** 标签分析 分析页面 */
	public static final String COC_LABEL_ANALYSIS_LINK = "COC_LABEL_ANALYSIS_LINK";
	/** 标签分析 对比分析 */
	public static final String COC_LABEL_CONTRAST_ANALYSIS = "COC_LABEL_CONTRAST_ANALYSIS";
	/** 标签分析 关联分析 */
	public static final String COC_LABEL_REL_ANALYSIS = "COC_LABEL_REL_ANALYSIS";
	/** 标签分析 微分分析 */
	public static final String COC_LABEL_DIFFERENTIAL = "COC_LABEL_DIFFERENTIAL";
	
	/**
	 * 客户群分析 类型编码
	 */
	/** 客户群分析 所有客户群分析日志父类型*/
	public static final String COC_CUSTOMER_ANALYSIS = "COC_CUSTOMER_ANALYSIS";
	/** 客户群分析 分析页面 */
	public static final String COC_CUSTOMER_CONTRAST_ANALYSIS = "COC_CUSTOMER_CONTRAST_ANALYSIS";
	/** 客户群分析 对比分析 */
	public static final String COC_CUSTOMER_REL_ANALYSIS = "COC_CUSTOMER_REL_ANALYSIS";
	/** 客户群分析 关联分析 */
	public static final String COC_INDEX_DIFFERENTIAL = "COC_INDEX_DIFFERENTIAL";
	/** 客户群分析 微分分析 */
	public static final String COC_CUSTOMER_LABEL_DIFFERENTIAL = "COC_CUSTOMER_LABEL_DIFFERENTIAL";
	
	/**
	 * 数据探索 类型编码
	 */
	/**数据探索 所有数据探索日志父类型*/
	public static final String COC_DATA_EXPLORE = "COC_DATA_EXPLORE";
	/** 数据探索 标签控 */
	public static final String COC_INDEX_LABEL_DATA_EXPLORE = "COC_INDEX_LABEL_DATA_EXPLORE";
	/** 数据探索 觅产品 */
	public static final String COC_INDEX_PRODUCT_DATA_EXPLORE = "COC_INDEX_PRODUCT_DATA_EXPLORE";
	/** 数据探索  标签分析*/
	public static final String COC_LABEL_ANALYSIS_DATA_EXPLORE = "COC_LABEL_ANALYSIS_DATA_EXPLORE";
	
	/**
	 * 策略匹配 类型编码
	 */
	/** 客户群营销策略匹配 */
	public static final String COC_CUSTOMER_MATCH_ADD = "COC_CUSTOMER_MATCH_ADD";
	/** 客户群营销策略保存 */
	public static final String COC_CUSTOMER_MARKETING_ADD = "COC_CUSTOMER_MARKETING_ADD";
	
	/**
	 * 客户群策划营销活动 类型编码
	 */
	/** 客户群策划营销活动  */
	public static final String COC_MARKETING_CAMPAIGN_LINK = "COC_MARKETING_CAMPAIGN_LINK";
	
	/**
	 * 登陆 类型编码
	 */
	/** 登陆  */
	public static final String COC_LOGIN = "COC_LOGIN";
	
	
	/**
	 * 标签使用类型
	 */
	/**标签使用类型:1 查看标签详情 */
	public static final int LABEL_USE_TYPE_QUERY_LABELINFO = 1;
	/**标签使用类型:2 创建客户群 */
	public static final int LABEL_USE_TYPE_CREATE_CUSTOMER = 2;
	/**标签使用类型:3 用作标签分析主体 */
	public static final int LABEL_USE_TYPE_MAIN_ANALYSIS = 3;
	/**标签使用类型:4 用作微分群主体 */
	public static final int LABEL_USE_TYPE_MAIN_DIFFERENTIAL = 4;
	/**标签使用类型:5 用于关联分析 */
	public static final int LABEL_USE_TYPE_REL_ANALYSIS = 5;
	/**标签使用类型:6 用于对比分析 */
	public static final int LABEL_USE_TYPE_CONTRAST_ANALYSIS = 6;
	/**标签使用类型:7 用于标签微分群 */
	public static final int LABEL_USE_TYPE_LABEL_DIFFERENTIAL = 7;

	/**
	 * 客户群使用类型
	 */
	/**客户群使用类型:1 查看详情 */
	public static final int CUSTOM_USE_TYPE_QUERY_LABELINFO = 1;
	/**客户群使用类型:2 创建客户群 */
	public static final int CUSTOM_USE_TYPE_CREATE_CUSTOMER = 2;
	/**客户群使用类型:3 用作客户群分析主体 */
	public static final int CUSTOM_USE_TYPE_MAIN_ANALYSIS = 3;
	/**客户群使用类型:4 用作微分群主体 */
	public static final int CUSTOM_USE_TYPE_MAIN_DIFFERENTIAL = 4;
	
	/**
	 * 1客户群，2模板
	 */
	/** 1客户群 */
	public static final int LABEL_RULE_FROM_COSTOMER = 1;
	/** 2模板 */
	public static final int LABEL_RULE_FROM_TEMPLATE = 2;
	
	/**
	 * 1模板，0客户群
	 */
	/** 1模板 */
	public static final int USE_TEMPLATE = 1;
	/** 0客户群 */
	public static final int USE_COSTOMER = 0;

	/**
	 * 列后缀：得分类型标签使用
	 */
	public static final String COLUMN_POSTFIX = "_SCORE";

	/**
	 * 标签规则是否取“非” 0：取非，1：不取非
	 */
	/** 标签规则“非”的值 */
	public static final int LABEL_RULE_FLAG_NO = 0;
	/** 标签规则“是”的值 */
	public static final int LABEL_RULE_FLAG_YES = 1;
	//为得分值预留
	/** 标签规则“非”的值 */
	public static final int LABEL_RULE_FLAG_NO_FOR_SCORE = 2;
	/** 标签规则“是”的值 */
	public static final int LABEL_RULE_FLAG_YES_FOR_SCORE = 3;

	/**
	 * 客户群状态
	 */
	/** 客户群状态：0、已删除 */
	public static final int CICUSTOMGROUPINFO_STATUS_DELETE = 0;
	/** 客户群状态：1、有效 */
	public static final int CICUSTOMGROUPINFO_STATUS_VALIDATE = 1;
	/** 客户群是否私有:  1、私有*/
	public static final int CICUSTOMGROUPINFO_IS_PRIVATE = 1;
	/** 客户群是否私有:  0、公有*/
	public static final int CICUSTOMGROUPINFO_IS_PUBLIC = 0;
	/** 客户群标签是否下线：	0、没有下线或者停用*/
	public static final int CICUSTOMGROUPINFO_IS_ONLINE = 0;
	/** 客户群标签是否下线：	1、下线或者停用*/
	public static final int CICUSTOMGROUPINFO_IS_OVERLINE = 1;

	/**
	 * 模板状态
	 */
	/** 模板状态：0、已删除 */
	public static final int TEMPLATEINFO_STATUS_DELETE = 0;
	/** 模板状态：1、有效 */
	public static final int TEMPLATEINFO_STATUS_VALIDATE = 1;
	/** 模板状态：2、失效 */
	public static final int TEMPLATEINFO_STATUS_INVALIDATE = 2;
	/** 模板是否私有:  1、私有*/
	public static final int TEMPLATEINFO_IS_PRIVATE = 1;
	/** 模板是否私有:  0、公有*/
	public static final int TEMPLATEINFO_IS_PUBLIC = 0;


	/**
	 * 客户群清单数据状态
	 */
	/** 客户群清单数据状态 ：0、统计失败 */
	public static final int CUSTOM_LIST_STATUS_FAILED = 0;
	/** 客户群清单数据状态：1、待创建 */
	public static final int CUSTOM_LIST_STATUS_WAIT = 1;
	/** 客户群清单数据状态：2、 创建中 */
	public static final int CUSTOM_LIST_STATUS_CREATING = 2;
	/** 客户群清单数据状态：3、 统计成功 */
	public static final int CUSTOM_LIST_STATUS_SUCCESS = 3;

	/**
	 * 客户群清单文件生成状态
	 */
	/** 客户群清单文件生成状态 ：1、未创建 */
	public static final int CUSTOM_LIST_FILE_STATUS_WAIT = 1;
	/** 客户群清单文件生成状态：2、创建中 */
	public static final int CUSTOM_LIST_FILE_STATUS_CREATING = 2;
	/** 客户群清单文件生成状态：3、 创建成功 */
	public static final int CUSTOM_LIST_FILE_STATUS_SUCCESS = 3;
	/** 客户群清单文件生成状态：4、 创建失败 */
	public static final int CUSTOM_LIST_FILE_STATUS_FAILED = 4;
	
	/**
	 * 客户群数据状态
	 */
	/** 客户群数据状态：0、 创建失败 */
	public static final int CUSTOM_DATA_STATUS_FAILED = 0;
	/** 客户群数据状态：1、待 创建 */
	public static final int CUSTOM_DATA_STATUS_WAIT = 1;
	/** 客户群数据状态：2、 创建中 */
	public static final int CUSTOM_DATA_STATUS_CREATING = 2;
	/** 客户群数据状态：3、 创建成功 */
	public static final int CUSTOM_DATA_STATUS_SUCCESS = 3;
	/** 客户群数据状态：4、 预约状态 */
	public static final int CUSTOM_DATA_STATUS_ORDER = 4;
	
	
	/**
	 * 客户群对比分析结果标示
	 */
	/** 客户群与标签交集比 */
	public static final int CUSTOM_LABEL_JJ_ANALYSIS_RESULT = 1;
	/** 客户群与标签差集比 */
	public static final int CUSTOM_LABEL_CJ_ANALYSIS_RESULT = 2;
	/** 标签与客户群差集比 */
	public static final int LABEL_CUSTOM_CJ_ANALYSIS_RESULT = 3;

	/**
	 * 客户群运算的规则的 ELEMENT_TYPE
	 */
	/** 运算符 类型 */
	public static final int CUSTOM_CALC_ELEMENT_TYPE_OPT = 0;
	/** 客户群Id 类型 */
	public static final int CUSTOM_CALC_ELEMENT_TYPE_CUTOMERID = 1;
	/** 运算符 union */
	public static final String CUSTOM_CALC_ELEMENT_TYPE_OPT_UNION = "OR";
	/** 运算符 交集 */
	public static final String CUSTOM_CALC_ELEMENT_TYPE_OPT_INTERSECT = "AND";
	/** 运算符 差 */
	public static final String CUSTOM_CALC_ELEMENT_TYPE_OPT_EXCEPT = "NOT";
	
	/**
	 * 标签运算运算符规则
	 */
	/** 运算符 and */
	public static final String CALCULATE_ELEMENT_TYPE_OPT_AND = "and";
	/** 运算符 or */
	public static final String CALCULATE_ELEMENT_TYPE_OPT_OR = "or";
	/** 运算符 剔除 */
	public static final String CALCULATE_ELEMENT_TYPE_OPT_EXCEPT = "-";
	/** 运算符 剔除 */
	public static final String CUSTOM_CALC_ELEMENT_TYPE_OPT_REMOVE = "REMOVE";

	/**
	 * 客户群生成周期:1,一次性;2,月周期;3,日周期;4,无周期
	 */
	/** 客户群生成周期:1,一次性; */
	public static final int CUSTOM_CYCLE_TYPE_ONE = 1;
	/** 客户群生成周期:2,月周期; */
	public static final int CUSTOM_CYCLE_TYPE_M = 2;
	/** 客户群生成周期:3,日周期 */
	public static final int CUSTOM_CYCLE_TYPE_D = 3;
	/** 客户群生成周期:4,无周期 */
	public static final int CUSTOM_CYCLE_TYPE_N = 4;

	public static final String CUSTOM_CYCLE_ONE = "一次性";

	public static final String CUSTOM_CYCLE_M = "月周期";

	public static final String CUSTOM_CYCLE_D = "日周期";

	/**
	 * 标签数据状态维表数据
	 */
	/** 标签数据状态: 1、未生效 */
	public static final int LABEL_DATA_STATUS_ID_NOT_EFFECT = 1;
	/** 标签数据状态: 2、已生效 */
	public static final int LABEL_DATA_STATUS_ID_EFFECT = 2;
	/** 标签数据状态: 3、已失效 */
	public static final int LABEL_DATA_STATUS_ID_FAILURE = 3;
	/** 标签数据状态: 4、冷冻期 */
	public static final int LABEL_DATA_STATUS_ID_FREEZED = 4;
	/** 标签数据状态: 5、已下线 */
	public static final int LABEL_DATA_STATUS_ID_UNDER = 5;
	/** 标签数据状态: 6、已删除 */
	public static final int LABEL_DATA_STATUS_ID_DELETED = 6;

	/**
	 * 客户群清单表字段，也是宽表关联字段
	 */
	public static String MAINTABLE_KEYCOLUMN = Configure.getInstance().getProperty("RELATED_COLUMN");

	/**
	 * 客户群分析类别
	 */

	public static final String CUSTOM_ASSOCIATION_ANALYSIS = "";

	/**
	 * 预警常量字段
	 */
	/** coc产品Id */
	public static final String COC_PRODUCT_ID = "COC";

	/**
	 * 预警类型
	 */
	/**标签预警*/
	public static final String ALARM_THRESHOLD_TYPE_LABEL = "LabelAlarm";
	/**客户群预警*/
	public static final String ALARM_THRESHOLD_TYPE_CUSTOMERS = "CustomersAlarm";

	/**有效的预警*/
	public static final int ALARM_THRESHOLD_FLAG_ACTIVE = 0;
	/**无效的预警*/
	public static final int ALARM_THRESHOLD_FLAG_INVALID = 1;

	/** 
	 * 预警列字段
	 */
	/**客户数量*/
	public static final String ALARM_COLUMN_ID_CUSTOM_NUM = "0";
	/**环比增长量*/
	public static final String ALARM_COLUMN_ID_RING_NUM = "1";
	/**占比值*/
	public static final String ALARM_COLUMN_ID_PROPORTION = "2";

	/**用于转换预警列id和预警列名称*/
	public static final Map<String, String> alarmColumnIdToNameMap = new HashMap<String, String>();
	static {
		alarmColumnIdToNameMap.put(ALARM_COLUMN_ID_CUSTOM_NUM, "基础");
		alarmColumnIdToNameMap.put(ALARM_COLUMN_ID_RING_NUM, "环比");
		alarmColumnIdToNameMap.put(ALARM_COLUMN_ID_PROPORTION, "占比");
	}

	/**
	 * 告警记录check_flag
	 */
	/**未确认*/
	public static final int ALARM_RECORD_UNCONFIRM_FLAG = 0;
	/**已确认*/
	public static final int ALARM_RECORD_CONFIRM_FLAG = 1;

	/** 一级标签父标签Id */
	public static final int LABEL_PARENT_ID_NULL = -1;

	/**
	 * 客户群日期范围查询
	 */
	/** 一天以内 */
	public static final String CUSTOM_ONE_D = "1";
	/** 一月以内 */
	public static final String CUSTOM_ONE_M = "2";
	/** 三月以内 */
	public static final String CUSTOM_THREE_M = "3";
	/** 一周以内 */
	public static final String CUSTOM_ONE_7D = "4";

	/**
	 * 标签需要统计用户数
	 */
	public static final int STAT_USER_NUM_YES = 1;

	/**
	 * 产品相关常量
	 */
	/** 产品类别中第一类别的父标签id */
	public static final int PRODUCT_CATEGORY_PARENT_ID_NULL = -1;
	/** 产品中第一级别的产品父标签id */
	public static final int PRODUCT_PARENT_ID_NULL = -1;

	/**
	 * 系统公告相关常量
	 */
	/**  系统公告: 0、删除状态 */
	public static final int SYS_ANNOUNCEMENT_STATUE_DELETE = 0;
	/** 系统公告: 1、有效状态 */
	public static final int SYS_ANNOUNCEMENT_STATUE_VALID = 1;
	/** 系统公告: 2、无效状态 */
	public static final int SYS_ANNOUNCEMENT_STATUE_INVALID = 2;
	
	/**  用户系统公告: 0、删除状态 */
	public static final int USER_ANNOUNCEMENT_STATUE_DELETE = 0;
	/** 用户系统公告: 1、已读状态 */
	public static final int USER_ANNOUNCEMENT_STATUE_READ = 1;
	/** 用户系统公告: 2、未读状态 */
	public static final int USER_ANNOUNCEMENT_STATUE_NOT_READ = 2;

	/**
	 * 产品状态
	 */
	/** 产品状态: 1、未生效 */
	public static final int PRODUCT_STATUS_NOT_EFFECT = 1;
	/** 产品状态: 2、已生效 */
	public static final int PRODUCT_STATUS_EFFECT = 2;
	/** 产品状态: 3、已失效 */
	public static final int PRODUCT_STATUS_FAILURE = 3;
	/** 产品状态: 4、冷冻期 */
	public static final int PRODUCT_STATUS_FREEZED = 4;
	/** 产品状态: 5、已下线 */
	public static final int PRODUCT_STATUS_UNDER = 5;
	/** 产品状态: 6、已删除 */
	public static final int PRODUCT_STATUS_DELETED = 6;

	/**
	 * 营销策略状态
	 */
	/** 0、已删除*/
	public static String CUSTOM_PRODUCT_TACTICS_REL_DEL = "0";
	/** 1、有效*/
	public static String CUSTOM_PRODUCT_TACTICS_REL_VAL = "1";

	/**
	 * 营销策略与产品与客户群关系状态
	 */
	/** 0、已删除*/
	public static short MARKET_TACTICS_STATUS_DEL = 0;
	/** 1、有效*/
	public static short MARKET_TACTICS_STATUS_VAL = 1;

	/**
	 * 父节点为空
	 */
	public static final int PARENT_ID_NULL = -1;

	/**
	 * 数据库排序类型
	 */
	//正向排序
	public static final int DB_ORDER_ASC = 1;
	//倒序排序
	public static final int DB_ORDER_DESC = 2;
	//不需要排序
	public static final int DB_ORDER_NO = 3;
	/**
	 * 客户群推送状态
	 */
	/**
	 * 推送，1 = 等待推送
	 */
	public static final int PUSH_CUSOMER_STATUS_WAIT = 1;
	/**
	 * 推送，2 = 推送ing
	 */
	public static final int PUSH_CUSOMER_STATUS_PUSHING = 2;
	/**
	 * 推送，3 = 推送成功
	 */
	public static final int PUSH_CUSOMER_STATUS_SUCCESS = 3;
	/**
	 * 推送，0 = 推送失败
	 */
	public static final int PUSH_CUSOMER_STATUS_FAILED = 0;
	/**
	 * 公用删除和有效状态
	 */
	/** 0、已删除*/
	public static short PUBLIC_STATUS_DEL = 0;
	/** 1、有效*/
	public static short PUBLIC_STATUS_VAL = 1;
	/**
	 *  首页通知刷新周期
	 */
	public static int CI_NOTICE_REFRESH_INTERVAL = 5;
	static {
		String interval = Configure.getInstance().getProperty("CI_NOTICE_REFRESH_INTERVAL");
		if (StringUtils.isNotEmpty(interval)) {
			CI_NOTICE_REFRESH_INTERVAL = Integer.valueOf(interval);
		}
	}
	/**
	 * 系统公告读取时间
	 */
	public static int CI_SYS_INTERVAL = 7;
	/**
	 * 个人通知读取状态
	 */
	/** 2、已读状态**/
	public static final int PERSON_NOTICE_READ_STATUS_YES = 2;
	/** 1、未读状态**/
	public static final int PERSON_NOTICE_READ_STATUS_NO = 1;
	/**
	 * 个人通知类型
	 */
	/**标签发布通知*/
	public static int PERSON_NOTICE_TYPE_LABEL_PUBLISH = 1;
	/**标签预警通知*/
	public static int PERSON_NOTICE_TYPE_PUBLISH_LABEL_ALARM = 2;
	/**客户群生成通知*/
	public static int PERSON_NOTICE_TYPE_PUBLISH_CUSTOMERS_GENERATE = 3;
	/**客户群预警通知*/
	public static int PERSON_NOTICE_TYPE_PUBLISH_CUSTOMERS_ALARM = 4;
	/**客户群策略匹配完成通知*/
	public static int PERSON_NOTICE_TYPE_PUBLISH_CUSTOMERS_MATCH_PRODUCT = 5;
	/**客户群推送*/
	public static int PERSON_NOTICE_TYPE_PUBLISH_CUSTOMERS_PUBLISH = 6;
	/**标签审批通知*/
	public static int PERSON_NOTICE_TYPE_LABEL_APPROVE = 7;
	/**客户群文件生成通知*/
	public static int PERSON_NOTICE_TYPE_CUSTOM_FILE_CREATE = 8;
	/**自助取数推送到CI，生成客户群通知*/
	public static int PERSON_NOTICE_TYPE_OTHERSY_PUBLISH_CUSTOMERS_GENERATE = 9;
	/**清单下载审批通知*/
	public static int PERSON_NOTICE_TYPE_LIST_DOWNLOAD_APPROVE = 10;
	/** 标签导入通知*/
	public static int PERSON_NOTICE_TYPE_UPLOAD_LABEL_FILE = 11;
	/**增加意见反馈通知*/
	public static int PERSON_NOTICE_TYPE_FEEDBACK_ADD = 12;
	/**意见反馈回复通知*/
	public static int PERSON_NOTICE_TYPE_FEEDBACK_REPLY = 13;
	/**意见反馈取消通知*/
	public static int PERSON_NOTICE_TYPE_FEEDBACK_CANCLE = 14;
	
	/**标签发布通知*/
	public static String PERSON_NOTICE_TYPE_STRING_LABEL_PUBLISH = "标签发布通知";
	/**标签预警通知*/
	public static String PERSON_NOTICE_TYPE_STRING_PUBLISH_LABEL_ALARM = "标签预警通知";
	/**客户群生成通知*/
	public static String PERSON_NOTICE_TYPE_STRING_PUBLISH_CUSTOMERS_GENERATE = "客户群生成";
	/**客户群预警通知*/
	public static String PERSON_NOTICE_TYPE_STRING_PUBLISH_CUSTOMERS_ALARM = "客户群预警通知";
	/**客户群策略匹配完成通知*/
	public static String PERSON_NOTICE_TYPE_STRING_PUBLISH_CUSTOMERS_MATCH_PRODUCT = "客户群策略匹配完成通知";
	/**客户群生成通知*/
	public static String PERSON_NOTICE_TYPE_STRING_CREATE_CUSTOMERS_FILE_GENERATE = "客户群文件生成通知";
	/**客户群推送*/
	public static String PERSON_NOTICE_TYPE_STRING_PUBLISH_CUSTOMERS_PUBLISH = "客户群推送通知";
	/**标签审批通知*/
	public static String PERSON_NOTICE_TYPE_STRING_LABEL_APPROVE = "标签审批通知";
	/**自助取数推送到CI，生成客户群通知*/
	public static String PERSON_NOTICE_TYPE_STRING_OTHERSY_PUBLISH_CUSTOMERS_GENERATE = "外部系统推送通知";
	/**清单下载审批通知*/
	public static String PERSON_NOTICE_TYPE_STRING_LIST_DOWNLOAD_APPROVE = "清单下载审批通知";
	/** 标签导入通知*/
	public static String PERSON_NOTICE_TYPE_STRING_UPLOAD_LABEL_FILE = "标签导入通知";
	/**增加意见反馈通知*/
	public static String PERSON_NOTICE_TYPE_STRING_FEEDBACK_ADD = "意见反馈新增通知";
	/**意见反馈回复通知*/
	public static String PERSON_NOTICE_TYPE_STRING_FEEDBACK_REPLY = "意见反馈回复通知";
	/**意见反馈取消通知*/
	public static String PERSON_NOTICE_TYPE_STRING_FEEDBACK_CANCLE = "意见反馈取消通知";
	/**
	 * 个人通知成功失败
	 */
	public static final int PERSON_NOTICE_SUCCESS = 1;
	public static final int PERSON_NOTICE_FAILURE = 0;
	/**
	 * 通知类型  系统通知与个人通知
	 */
	public static final int NOTICE_SYS = 1;
	public static final int NOTICE_PERSON = 2;
	/**
	 * 个人通知状态
	 */
	/** 1、有效**/
	public static final int PERSON_NOTICE_STATUS_VALID = 1;
	/** 2、失效**/
	public static final int PERSON_NOTICE_STATUS_INVALID = 2;
	//关联分析关联圆的半径--占比40%以下
	public static final int REL_ANALYSIS_REDIUS_40 = 50;
	//关联分析关联圆的半径--占比70%以下，40%以上
	public static final int REL_ANALYSIS_REDIUS_70 = 50;
	//关联分析关联圆的半径--占比100%以下，70%以上
	public static final int REL_ANALYSIS_REDIUS_100 = 50;

	//关联分析关联圆距离主圆的距离圆心计算基数--占比40%以下
	public static final double REL_ANALYSIS_DISTANCE_40 = 0.4;
	//关联分析关联圆距离主圆的距离圆心计算基数--占比70%以下，40%以上
	public static final double REL_ANALYSIS_DISTANCE_70 = 0.6;
	//关联分析关联圆距离主圆的距离圆心计算基数--占比100%以下，70%以上
	public static final double REL_ANALYSIS_DISTANCE_100 = 0.8;
	/**
	 * 资源审批流程ID
	 */
	/**客户标签审批流程**/
	public static final String LABEL_APPROVE_PROCESS = "1";
	/**
	 * 资源审批查询待审批资源时是否需要判断部门权限
	 */
	/**不需要判断部门权限，即当前用户可以审批全部部门资源**/
	public static final String NO_NEED_TO_JUDGE_DEPT = "0";
	/**需要判断部门权限，即当前用户可以审批部分部门资源**/
	public static final String NEED_TO_JUDGE_DEPT = "1";
	/**
	 * 资源审批历史表中审批结果
	 */
	/**资源审批通过**/
	public static final int APPROVE_PASS = 1;
	/**资源审批通过**/
	public static final int APPROVE_NOT_PASS = 0;
	/**
	 * 资源审批级别表中，审批角色类型ID
	 */
	/**审批角色类型ID=1,为审批角色，处于实际审批过程中**/
	public static final int APPROVE_ROLE_TYPE_IN_PROCESS = 1;
	/**审批角色类型ID=2,为审批最终处理角色，处于实际审批过程外**/
	public static final int APPROVE_ROLE_TYPE_OUT_OF_PROCESS = 2;
	/**
	 * 根据flag从数据库中获取对应的资源审批状态ID
	 */
	/**普通用户添加，从数据库中获取资源相对应的草稿状态ID**/
	public static final int GENERAL_USER_ADD = 1;
	/**普通用户提交审批，从数据库中获取资源相对应的第一级待审批状态ID**/
	public static final int GENERAL_USER_SUBMINT = 2;
	/**等待最终处理，从数据库中获取资源相对应的最终的待处理状态ID**/
	public static final int WAIT_FINAL_PROCESSING = 3;
	/**最终处理完成，从数据库中获取资源相对应的最终状态ID**/
	public static final int THE_FINAL_APPROVE_STATUS = 4;
	/**
	 * 获取某已审批角色对应的待审批和已处理的状态
	 */
	/**等待对应审批人审批**/
	public static final int WAITTINT_TO_BE_PROCESSED = 1;
	/**对应审批人已处理**/
	public static final int APPROVER_HAS_PROCESSED = 2;
	/**
	 * 标签管理操作列类型(用于在标签历史信息表记录所做的操作)
	 */
	/** 标签操作类型: 1、新增(保存)*/
	public static final int LABEL_OPERATE_TYPE_ID_ADD = 1;
	/** 标签操作类型: 2、修改*/
	public static final int LABEL_OPERATE_TYPE_ID_MODIFY = 2;
	/** 标签操作类型: 3、删除*/
	public static final int LABEL_OPERATE_TYPE_ID_DELETE = 3;
	/** 标签操作类型: 4、提交审批*/
	public static final int LABEL_OPERATE_TYPE_ID_APPROVE = 4;
	/** 标签操作类型: 5、保存并提交审批*/
	public static final int LABEL_OPERATE_TYPE_ID_ADDAPPROVE = 5;
	/** 标签操作类型: 6、停用*/
	public static final int LABEL_OPERATE_TYPE_ID_STOPUSE = 6;
	/** 标签操作类型: 7、下线*/
	public static final int LABEL_OPERATE_TYPE_ID_OFFLINE = 7;
	/** 标签操作类型: 8、启用(针对停用状态的重新上线)*/
	public static final int LABEL_OPERATE_TYPE_ID_ONLINE = 8;
	/** 标签操作类型: 9、发布*/
	public static final int LABEL_OPERATE_TYPE_ID_RELEASE = 9;

	//vip等级跟节点值
	public static final int VIP_LEVEL_ID_ROOT_VAL = -1;
	//品牌跟节点值
	public static final int BRAND_ID_ROOT_VAL = -1;
	//城市跟节点值
	public static final int CITY_ID_ROOT_VAL = -1;

	/**
	 * 营销策略匹配
	 */
	/** 返回拖拽产品的页面*/
	public static final String RETURN_PAGE_MARKETINGSTRATEGY = "marketingStrategy";
	/** 返回选择产品的页面*/
	public static final String RETURN_PAGE_PRODUCTVIEW = "customProductView";
	/** 系统匹配*/
	public static final String SYS_MATCH = "sysMatch";

	/** 手动匹配*/
	public static final String CUSTOM_MATCH = "customMatch";
	/** 未匹配*/
	public static final int NO_SYS_MATCH = 1;
	/** 匹配中*/
	public static final int SYS_MATCH_ING = 2;
	/** 已匹配*/
	public static final int FINISH_SYS_MATCH = 3;

	/**
	 * 客户群导入 结果
	 */
	/** 导入成功*/
	public static final String CUSTOM_IMPORT_SUCCESS = "CUSTOM_IMPORT_SUCCESS";
	/** 导入失败*/
	public static final String CUSTOM_IMPORT_FAIL = "CUSTOM_IMPORT_FAIL";

	public static final String CUSTOM_IMPORT_TOTAL = "CUSTOM_IMPORT_TOTAL";
	/**
	 * 预警确认标识
	 */
	public static final int ALARM_CHECKFLAG_NO = 0;
	public static final int ALARM_CHECKFLAG_YES = 1;
	/**
	 * 月标签统计表表名后缀
	 */
	public static final String LABEL_STAT_CYCLE_MM = "MM";
	/**
	 * 日标签统计表表名后缀
	 */
	public static final String LABEL_STAT_CYCLE_DM = "DM";

	/**  最新数据日期是否统计过标识  */
	/** 已经统计过为1 */
	public static final int LABEL_NEWEST_DATE_IS_STAT_YES = 1;
	/** 未统计过为0 */
	public static final int LABEL_NEWEST_DATE_IS_STAT_NO = 0;

	/** 宽表标签周期类型*/
	public static final long LABEL_INIT_CYCLE_M = 1;
	public static final long LABEL_INIT_CYCLE_D = 2;

	/** 无法找到文件导入路径*/
	public static final String UNKNOWM_PATH = "-1";
	
	/** 为地市很多的省份使用，某个地市的趋势图的月份数量 */
	public static final int MORE_CITY_TREND_MONTH = 12;
	/** 为地市很多的省份使用，某个地市的趋势图的日期数量 */
	public static final int MORE_CITY_TREND_DAY = 30;
	
	/**
	 * 客户群属性关系表 字段名称前缀
	 */
	public static final String GROUP_ATTRIBUTE_COLUMN_NAME = "ATTR_COL_";
	
	/**
	 * 客户群来源
	 */
	/** create by import */
	public static final String CUSTOM_RESOURCE_ID_CI_IMPORT = "CI_IMPORT";
	
	/**
	 * 客户群属性来源
	 */
	/** by import */
	public static final int ATTR_SOURCE_ID_BY_IMPORT = 1;
	/** by label */
	public static final int ATTR_SOURCE_ID_BY_LABEL = 2;
	/** by custom */
	public static final int ATTR_SOURCE_ID_BY_CUSTOM = 3;
	
	
	/**最新模板*/
	public static final int TEMPLATE_LATEST_DATE=15;
	/**最新客户群*/
	public static final int CUSTOMERS_LATEST_DATE=15;
	/**最新标签*/
	public static final int LABEL_LATEST_DATE=15;
	
	
	/**
	 *  生成客户群信息ID是否需要拼接cityId
	 */
	public static boolean CUSTOM_GROUP_INFO_ID_NEED_CITYID = false;
	static {
		String needCityId = Configure.getInstance().getProperty("CUSTOM_GROUP_INFO_ID_NEED_CITYID");
		if (StringUtils.isNotEmpty(needCityId) && "true".equalsIgnoreCase(needCityId)) {
			CUSTOM_GROUP_INFO_ID_NEED_CITYID = true;
		}else{
			CUSTOM_GROUP_INFO_ID_NEED_CITYID = false;
		}
	}
	
	/**
	 *  生成客户群信息ID需要拼接几位数字编码
	 */
	public static int CUSTOM_GROUP_INFO_ID_NUM_CODE_LENGTH = 8;
	static {
		String numCodeLength = Configure.getInstance().getProperty("CUSTOM_GROUP_INFO_ID_NUM_CODE_LENGTH");
		if (StringUtils.isNotEmpty(numCodeLength)) {
			CUSTOM_GROUP_INFO_ID_NUM_CODE_LENGTH = Integer.valueOf(numCodeLength);
		}
	}
	
	/**
	 * 购物车session使用
	 */
	/**标签**/
	public static String LABEL_INFO_CALCULATIONS_TYPEID="1";
	/**客户群**/
	public static String CUSTOM_GROUP_INFO_CALCULATIONS_TYPEID="2";
	/**模板**/
	public static String TEMPLATE_INFO_CALCULATIONS_TYPEID="3";
	/**修改客户群标示**/
	public static String EDIT_CUSTOM_FLAG="1";
	/**保存客户群标示**/
	public static String SAVE_CUSTOM_FLAG="0";
	
	/**
	 * 标签、客户群、客户群模板 显示热门共享、系统热门显示条数设置
	 */
	/**热门共享模板显示条数**/
	public static int SHOW_HOT_TEMPLATE_NUM = 5;
	/**系统热门标签显示条数**/
	public static int SHOW_RECOMMEND_LABEL_NUM = 99;
	/**系统客户群显示条数**/
	public static int SHOW_RECOMMEND_CUSTOMS_NUM = 99;
	/**系统热门客户群模板显示条数**/
	public static int SHOW_RECOMMEND_TEMPLATE_NUM = 99;
	/**系统推荐显示标签条数**/
	public static int SHOW_SYS_RECOMMEND_LABEL = 20;
	/**系统推荐显示客户群条数**/
	public static int SHOW_SYS_RECOMMEND_CUSTOM = 20;
	
	/**
	 * 是否是最热共享排序  1是 ，0不是
	 */
	public static int IS_HOT_LIST = 1;
	public static int NO_HOT_LIST = 0;
	
	/**
	 * 指标型标签选择哪种方式：1、范围；2、精确值；3、空值
	 */
	/** 指标型标签选择哪种方式：1、范围； **/
	public static int QUERY_WAY_FOR_KPI_LABEL_RANGE = 1;
	/** 指标型标签选择哪种方式：2、精确值； **/
	public static int QUERY_WAY_FOR_KPI_LABEL_EXACT = 2;
	/** 指标型标签选择哪种方式：3、空值 **/
	public static int QUERY_WAY_FOR_KPI_LABEL_NULL = 3;
	
	/**
	 * 首页30秒会操作cookie名称
	 */
	public static String COOKIE_NAME_FOR_INDEX_PAGE = "INDEX_NUMS_COOKIE";
	/**
	 * 全景标签视图标签双击提示
	 */
	public static String COOKIE_NAME_FOR_INDEX_LABEL_TIP = "INDEX_LABEL_TIP_NUMS_COOKIE";
	/**
	 * 标签计算中心30秒会操作cookie名称
	 */
	public static String COOKIE_NAME_FOR_CALCULATE_PAGE = "CALCULATE_NUMS_COOKIE";
	/**
	 * 我的客户群导入操作cookie名称
	 */
	public static String COOKIE_NAME_FOR_IMPORT = "IMPORT_NUMS_COOKIE";
	/**
	 * 新旧版本切换cookie名称
	 */
	public static String COOKIE_NAME_FOR_VERSION = "VERSION_FLAG_COOKIE";
	/**
	 * 是否显示新旧版本切换的按钮
	 */
	public static String COOKIE_NAME_FOR_SHOW_VERSION = "VERSION_SHOW_FLAG_COOKIE";
	
	/**
	 * 系统推荐
	 */
	public static int IS_SYS_RECOM = 1;
	/**
	 * 非系统推荐
	 */
	public static int IS_NOT_SYS_RECOM = 0;
	/**
	 * 全部场景ID
	 */
	public static String ALL_SCENE_ID = "0";
	/**
	 * 场景有效状态
	 */
	public static final int SCENE_STATUS_VALIDATE = 1;
	
	/**
	 * 场景删除状态
	 */
	public static final int SCENE_STATUS_DELETE = 0;
	
	/**
	 * 客户群查询类型
	 */
	//用户收藏的客户群
	public static final String CUSTOM_GROUP_QUERY_TYPE_USER_ATTENTION = "userAttention";
	
	/**
	后台调用查询客户群的方法，这时不能通过PrivilegeServiceUtil.getUserId()获得userId
	 * 
	 */
	public static final String CUSTOM_GROUP_QUERY_TYPE_CALL_BY_BACK = "callByBackStage";
	
	/**
	 * 客户群规则中包含本地市清单
	 */
	public static final int IS_CONTAIN_LOCAL_LIST = 1;
	/**
	 * 客户群规则中不包含本地市清单
	 */
	public static final int IS_NOT_CONTAIN_LOCAL_LIST = 0;
	/**
	 * 跳转到我的收藏 标签列表 1 客户群列表 2
	 */
	public static final int ATTENTION_LABEL_ID = 1;
	public static final int ATTENTION_CUSTOM_ID = 2;
	/**
	 * 发送方式-系统通知
	 */
	public static final String NOTICE_PERSON_SEND_SYS = "1";
	/**
	 * 发送方式-短信
	 */
	public static final String NOTICE_PERSON_SEND_SMS= "2";
	/**
	 * 发送方式-邮件
	 */
	public static final String NOTICE_PERSON_SEND_MAIL= "3";
	/**
	 * 标签地图点击 一级标签 二级标签 三级标签
	 */
	/**点击一级标签*/
	public static final int FIRST_LABEL_LEVEL= 1;
	/**点击二级标签*/
	public static final int SECOND_LABEL_LEVEL= 2;
	/**点击三级标签*/
	public static final int THIRD_LABEL_LEVEL= 3;
	
	/**
	 * 客户群推送状态
	 */
	/** 已推送 */
	public static final int CUSTOM_IS_PUSHED = 1;
	/** 未推送 */
	public static final int CUSTOM_NOT_PUSHED = 0;
	/**
	 * 系统公告读取状态
	 */
	/** 2、已读状态**/
	public static final int SYS_NOTICE_READ_STATUS_YES = 1;
	/** 1、未读状态**/
	public static final int SYS_NOTICE_READ_STATUS_NO = 0;
	
	/**
	 * 标签状态
	 */
	/** 标签更新状态 ：0、失败 */
	public static final int COC_LABEL_FAIL_STATUS = 0;
	/** 标签更新状态：1、成功 */
	public static final int COC_LABEL_SUCCESS_STATUS = 1;
	
	/**
	 * 清单策略
	 */
	/** 预约策略  清单策略一：按统一的最新数据执行，没有数据，延迟生成清单  **/
	public static final String LIST_TABLE_TACTICS_ID_ONE = "1";
	/** 保守策略  清单策略三：按最晚（用户选定的日期或者月份）执行跑清单  **/
	public static final String LIST_TABLE_TACTICS_ID_THREE = "2";
	
	/**
	 * 验证标签数据以确定清单如何处理
	 */
	/** 等待创建 **/
	public static final String VALIDATE_RESULT_WAIT = "1";
	/** 立即执行 **/
	public static final String VALIDATE_RESULT_GO ="2";
	/** 等待周期job执行 **/
	public static final String VALIDATE_RESULT_NEW ="3";
	
	/**
	 * 客户群规则所需日期或月份是否回退
	 */
	/** 需要日回退 **/
	public static final String BACK_FOR_DAY = "1";
	/** 需要月回退 **/
	public static final String BACK_FOR_MONTH ="2";
	/** 日月都要回退 **/
	public static final String BACK_FOR_ALL ="3";
	/** 日月都不要回退 **/
	public static final String NO_BACK_FOR_ALL ="4";
	
	/**
	 * 个人通知消息状态，isSuccess由Boolean类型改为int类型，代替之前的TRUE与FALSE值
	 */
	public static final int PERSON_NOTICE_FALSE = 0;
	public static final int PERSON_NOTICE_TRUE = 1;
	
	/**
	 * 初次跑客户群清单是否失败
	 */
	/** 第一次清单跑失败 */
	public static final int CUSTOM_GROUP_IS_FIRST_FAILED = 1;
	/** 第一次清单跑成功 */
	public static final int CUSTOM_GROUP_IS_FIRST_SUCCESS = 0;
	
	/**
	 * 跑定时客户群清单是否失败
	 */
	/** 跑定时客户群清单失败 */
	public static final int CUSTOM_GROUP_CUSTOM_LIST_CREATE_FAILED = 1;
	/** 跑定时客户群清单成功 */
	public static final int CUSTOM_GROUP_CUSTOM_LIST_CREATE_SUCCESS = 0;

	/**
	 * is_show_tip个人通知与系统公告是否在主页提示
	 */
	/** 1、显示**/
	public static final int NOTICE_IS_SHOW_TIP = 1;
	/** 0、不显示**/
	public static final int NOTICE_NOT_SHOW_TIP = 0;
	
	/**
	 * 最近收藏标签客户群数量
	 */
	public static final int LABEL_CUSTOM_ATTENTION_LIST_NUM = 5;
	/**
	 * 元数据表中标签对应的宽表类型
	 */
	/** 1、用户签**/
	public static final int MDA_SYS_TABLE_TYPE_USER = 1;
	/** 2、产品签**/
	public static final int MDA_SYS_TABLE_TYPE_PRODUCT = 2;
	/** 3、纵表签**/
	public static final int MDA_SYS_TABLE_TYPE_VERTICAL = 3;
	
	/**
	 * 重新跑的标签是否已发公告,0,未发送；1,已发送
	 */
	public static final int LABEL_HAS_SEND_NOTICE_YES = 1;
	public static final int LABEL_HAS_SEND_NOTICE_NOT = 0;
	
	/**
	 * 公告常量定义表
	 */
	//公告类型
	/**标签发布**/
	public static final int SYSANNOUNCEMENT_TYPE_LABEL_PUBLISH = 1;
	/**新功能上线**/
	public static final int SYSANNOUNCEMENT_TYPE_NEW_FUNCTION = 2;
	/**标签下线**/
	public static final int SYSANNOUNCEMENT_TYPE_LABEL_OFFLINE = 3;
	/**其他**/
	public static final int SYSANNOUNCEMENT_TYPE_OTHER = 4;
	
	//公告优先级
	/**重要**/
	public static final int SYSANNOUNCEMENT_PRIORITY_HIGH = 0;
	/**普通**/
	public static final int SYSANNOUNCEMENT_PRIORITY_COMMON = 1;
	
	//客户群属性关系表-是否纵表属性
	/**是纵表属性**/
	public static final int CUSTOM_LABEL_IS_VERTICAL_ATTR_YES = 1 ;
	/**不是纵表属性**/
	public static final int CUSTOM_LABEL_IS_VERTICAL_ATTR_NO = 0 ;
	
	
	//客户群是否包含清单
	/**包含**/
	public static final int CUSTOM_IS_HAS_LIST = 1;
	/**不包含**/
	public static final int CUSTOM_NO_HAS_LIST = 0;
	
	/**标签是否统计客户数**/
	/**可用标签，非分类标签**/
	public static final int IS_STAT_USER_NUM = 1;
	/**分类标签**/
	public static final int IS_NOT_STAT_USER_NUM = 0;
	
	/**
	 * 服务端是否执行清单
	 */
	/**执行客户群跑清单**/
	public static final int SERVER_IS_EXE_TASK_YES=1;
	/**不执行客户群跑清单**/
	public static final int SERVER_IS_EXE_TASK_NO=0;
	
	public static final  int LABEL_EXACT_OR_ATTR_VALUE_LENGTH = 20;
	
	/**
	 * CI_GROUP_ATTR_REL 表增加STATUS      SMALLINT 2,修改为没有属性;1,已失效;0,有效  字段，用作客户群属性修改功能。
	 * 客户群属性状态
	 */
	public static final int CI_GROUP_ATTR_REL_STATUS_VALID = 0;
	public static final int CI_GROUP_ATTR_REL_STATUS_INVALID = 1;
	public static final int CI_GROUP_ATTR_REL_STATUS_NO_ATTR = 2;
	
	/** 0、已删除*/
	public static int STATUS_DEL = 0;
	/** 1、有效*/
	public static int STATUS_VAL = 1;
	  
	/**日期类标签是否偏移量**/
	
	/** 1:需要 **/
	public static int IS_NEED_OFFSET_YES = 1;
	/** 0：不需要 **/
	public static int IS_NEED_OFFSET_NO = 0;
	
	/**
	 * 标签创建方式
	 */
	/** 配置标签*/
	public static final int CONFIG_LABEL = 1;
	/** 导入标签*/
	public static final int IMPORT_LABEL = 2;
	
	/**
	 * 
	 * 标签导入文件列值索引
	 */
	/** 标签名称*/
	public static final int LABEL_NAME = 0;
	/** 标签类型*/
	public static final int LABEL_TYPE = 1;
    /** 更新周期*/
	public static final int UPDATE_CYCLE = 2;
	/** 业务口径*/
	public static final int BUSINESS_CALIBER = 3;
	/** 依赖的指标*/
	public static final int DEPEND_INDEX = 4;
	/** 具体规则*/
	public static final int COUNT_RULES = 5;
	/** 规则描述*/
	public static final int COUNT_RULES_DESC = 6;
	/** 所属分类*/
	public static final int PARENT_NAME = 7;
	/** 属性标签维表*/
	public static final int DIM_TRANS_ID = 8;
	/** 标签所属宽表*/
	public static final int TABLE_NAME = 9;
	/** 数据类型*/
	public static final int DATA_TYPE = 10;
	/** 过滤权限*/
	public static final int NEED_AUTHORITY = 11;
	/** 标签文件列数*/
	//public static final int LABEL_FILE_COLUMN_LENGTH = 12;
	/**应用场景**/
	public static final int SCENE_IDS = 12;
	/**单位  */
	public static final int UNIT_DESC = 13;
	
	/** 标签扩展信息是否统计用户数 1是0否 */
	public static final int IS_STAT_USER_NUM_YES = 1;
	public static final int IS_STAT_USER_NUM_NO = 0;
	
	/** 标签扩展信息是否叶子节点 1是0否 */
	public static final int IS_LEAF_YES = 1;
	public static final int IS_LEAF_NO = 0;
	
	/**
	 * 标签配置里标签code的生成方式
	 */
	/** 新的生成方式 字符串类型：1 */
	public static final String LABEL_CODE_NEW = "1";
	/** 旧的生成方式 字符串类型 ：2 */
	public static final String LABEL_CODE_OLD = "2";
	
	/**
	 * 0、1对应的字符串
	 */
	/** 1对应的中文字符 */
	public static final String IS_STR = "是";
	/** 0对应的中文字符 */
	public static final String NOT_STR = "否";
	
	/**
	 * 0、1
	 */
	/** 1 */
	public static final int IS_NUM = 1;
	/** 0 */
	public static final int NOT_NUM = 0;
	
	/**
	 * 枚举型标签是否需要权限
	 */
	/** 需要权限控制 */
	public static final int IS_NEED_AUTHORITY = 1;
	/** 不需要权限控制 */
	public static final int NOT_NEED_AUTHORITY = 0;
	
	
	/**
	 * 标签规则表主键
	 */
	public static final String COUNT_RULES_CODE = "R_00001";
	/*public static final String PRODUCT_COUNT_RULES_CODE = "R_00001";
	public static final String P_COUNT_RULES_CODE = "R_00000";*/
	
	/**
	 * 客户群的数据周期
	 */
	/** 客户群的数据周期：1 数据时以月为单位的*/
	public static final int CUSTOMER_GROUP_DATA_CYCLE_MONTH = 1;
	/** 客户群的数据周期：2数据时以 日为单位的 */
	public static final int CUSTOMER_GROUP_DATA_CYCLE_DAY = 2;
	/**
	 * 组合标签模板索引
	 */
	/** 父标签或者纵表列所属的标签*/
	public static final int VERT_PARENT_NAME = 4;
	/** 属性标签维表*/
	public static final int VERT_DIM_TRANS_ID = 5;
	/** 纵表表名*/
	public static final int VERT_TABLE_NAME = 6;
	/** 数据类型*/
	public static final int VERT_DATA_TYPE = 7;
	/** 过滤权限*/
	public static final int VERT_NEED_AUTHORITY = 8;
	/**是否是纵表列**/
	public static final int VERT_CLOUMN = 9;
	/**纵表列字段名**/
	public static final int VERT_CLOUMN_NAME = 10;
	/**纵表标签应用场景**/
	public static final int VERT_SCENE_IDS = 11;
	/**纵表列单位  */
    public static final int VERT_UNIT_DESC = 12;
	
	/**
	 * 
	 * 标签分类导入文件列值索引
	 */
	/** 标签名称*/
	public static final int LABEL_CLASS_NAME = 0;
	/** 所属分类*/
	public static final int PARENT_CLASS_NAME = 1;
	/** 标签文件列数*/
	public static final int LABEL_CLASS_FILE_COLUMN_LENGTH = 2;
	
	/**
	 * cookie 生效时间
	 */
	/** 1年 */
	public static final int COOKIE_EFFECTIVE_ONE_YEAR = 60*60*24*365;
	
	/**
	 *购物车在session中存放的名称
	 */
	/** 具体规则存放的名称 */
	public static final String SHOP_CART_RULE = "sessionModelList";
	/** 标签和客户群规则的数量名称 */
	public static final String SHOP_CART_RULE_NUM = "calcElementNum";
	
	/**标签配置中，宽表纵表选择方式：新建：2 **/
	public static final String LABEL_CREATE_TABLE_TYPE_NEW = "2";
	/**
	 * 意见反馈
	 */
	/** 未处理	提出者在创建反馈意见后，管理者在未进行处理前*/
	public static int FEEDBACK_DEAL_STATUS_ADD = 1;
	/** 处理中	管理者对反馈进行处理（将工作分发给相关人员进行解决）*/
	public static int FEEDBACK_DEAL_STATUS_REPLY = 2;
	/** 已处理	解决完毕后，管理者确认反馈*/
	public static int FEEDBACK_DEAL_STATUS_FINISH = 3;
	/** 已取消	提出者在反馈处于“未处理”的时候可以进行取消，取消后，*/
	public static int FEEDBACK_DEAL_STATUS_CANCEL = 4;
	/** 已关闭	提出者可以对“已处理”的反馈进行关闭*/
	public static int FEEDBACK_DEAL_STATUS_CLOSE = 5;
	
	/** 反馈标识：用于条件查询时，用于“本人创建”和“本人处理”**/
	/** =1“本人创建”**/
	public static int FEEDBACK_SIGN_CREDATE = 1;
	/** =2“本人处理”**/
	public static int FEEDBACK_SIGN_DEAL = 2;
	
	/** 反馈类型**/
	/** =1 标签**/
	public static int FEEDBACK_TYPE_LABEL = 1;
	/** =2 客户群**/
	public static int FEEDBACK_TYPE_CUSTOM = 2;
	/** =3 系统功能**/
	public static int FEEDBACK_TYPE_SYSTEM = 3;
	
	/** 反馈状态 */
	/** =0 已删除 **/
	public static int FEEDBACK_STATUS_DEL = 0;
	/** =1 有效 **/
	public static int FEEDBACK_STATUS_VAL = 1;
	/** 回复人类型	*/
	/**	=1 提出人**/
	public static int FEEDBACK_REPLY_TYPE_RAISE = 1;
	/**	=2 回复人**/
	public static int FEEDBACK_REPLY_TYPE_REPLY = 2;
	
	/** 客户群标签推荐类型 **/
	/** 标签推荐 **/
	public static int RECOM_SHOW_TYPE_LABEL = 1;
	/** 客户群推荐 **/
	public static int RECOM_SHOW_TYPE_CUSTOM = 2;
	/** 客户群标签推荐状态 **/
	/** 有效推荐 **/
	public static int RECOM_STATUS_VALIDATE = 1;
	/** 无效推荐 **/
	public static int RECOM_STATUS_INVALIDATE = 0;
	
	/** 是否显示固定搜索框 **/
	/** 显示 **/
	public static String FIXED_SEARCH_YES = "true";
	/** 不显示 **/
	public static String FIXED_SEARCH_NO = "false";
	
	/** 客户群标签关系实体默认排序 **/
	public static int SORT_NUM = 99;
	/** 客户群标签关系实体默认得分 **/
	public static double AVG_SCORE = 0.0;
	
	/** 客户群标签关系实体状态 **/
	/** 有效 **/
	public static int  SCENE_REL_STATUS_VALIDATE= 1;
	/** 失效 **/
	public static int SCENE_REL_STATUS_INVALIDATE = 0;
	/** 是否显示切换版本按钮 **/
	/** 显示 **/
	public static String  CHANGE_VERSION_SHOW_YES= "true";
	/** 不显示 **/
	public static String CHANGE_VERSION_SHOW_NO = "false";
	/** 新旧版本 **/
	/** 新版本**/
	public static String  CHANGE_VERSION_YES= "true";
	/** 旧版本 **/
	public static String CHANGE_VERSION_NO = "false";
	/**
	 * 操作成功 失败
	 */
	public static final int OPERA_CUSTOM_GROUP_SUSSESS = 1;
	public static final int OPERA_CUSTOM_GROUP_FAILURE = 0;
	/**
	 * 外部接口传来字符串 元素
	 */
	/** 标签ID前加 # */
	public static final String EXTERNAL_LABEL_SYMBOLS = "#";
	/** 清单表名前加$ */
	public static final String EXTERNAL_CUSTOMLIST_SYMBOLS = "$";
	public static final String EXTERNAL_LIKE_SYMBOLS = "like";
	/** 客户群数据日期长度 数据日期为YYYY-MM*/
	public static final int CUSTOMER_DATADATE_LENGTH_MONTH = 7;
	/** 客户群数据日期长度 数据日期为YYYY-MM-DD*/
	public static final int CUSTOMER_DATADATE_LENGTH_DAY = 10;
	
	/**
	 * 外部系统 vgop
	 */
	public static final String SYS_VGOP = "vgop";
	/**
	 * 外部系统标签分类List
	 */
	public static final String EXTERNAL_LABEL_CATEGORY_LIST = "_LABEL_CATEGORY_LIST";
	/**
	 * 外部系统分类与标签的对应关系List
	 */
	public static final String EXTERNAL_SYS_LABEL_REL_LIST = "_SYS_LABEL_REL_LIST";
	/**
	 * 外部系统全部的标签体系List
	 */
	public static final String EXTERNAL_ALL_LABEL_LIST = "_ALL_LABEL_LIST";
	
	/**
     * 案例日期范围查询
     */
    /** 一天以内 */
    public static final String CASE_ONE_D = "1";
    /** 一月以内 */
    public static final String CASE_ONE_M = "2";
    /** 三月以内 */
    public static final String CASE_THREE_M = "3";
    /** 一周以内 */
    public static final String CASE_ONE_7D = "4";
    
    /**标签与案例关系  */
    public static final int CASE_LABEL_TYPE = 1;
    /**客户群与案例关系  */
    public static final int CASE_CUS_TYPE = 2;
    
    /**案例上线  */
    public static final int CASE_ONLINE_STATE = 1;
    /**案例下线  */
    public static final int CASE_OFFLINE_STATE = 2;
    
    /**推送成功  */
    public static final int PUSH_STATUS_SUCCESS = 1;
    /**推送失败  */
    public static final int PUSH_STATUS_FAIL = 2;
	
	
}
