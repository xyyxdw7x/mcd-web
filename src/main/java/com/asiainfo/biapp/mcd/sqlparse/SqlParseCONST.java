package com.asiainfo.biapp.mcd.sqlparse;

import java.util.Locale;

import com.asiainfo.biframe.utils.config.Configure;

public class SqlParseCONST {

	/**
	 * 解析SQL时用的字符
	 * 
	 */
	public static final char PARSE_SQL_CONDITION_LEFT_BRACKET = '(';
	public static final char PARSE_SQL_CONDITION_RIGHT_BRACKET = ')';
	public static final char PARSE_SQL_CONDITION_WAVE = '~';
	public static final char PARSE_SQL_CONDITION_OPEN_CURLY_BRACE = '{';
	public static final char PARSE_SQL_CONDITION_CLOSE_CURLY_BRACE = '}';
	public static final char PARSE_SQL_CONDITION_RULE_FLAG = '^';
	public static final char PARSE_SQL_CONDITION_DBLINK_FLAG = '@';
	public static final char PARSE_SQL_CONDITION_OPEN_BRACKET = '[';
	public static final char PARSE_SQL_CONDITION_CLOSE_BRACKET = ']';
	public static final String PARSE_SQL_CONDITION_AND = " and ";
	public static final String PARSE_SQL_CONDITION_OR = " or ";
	public static final String PARSE_SQL_CONDITION_IN = " in ";
	public static final String PARSE_SQL_CONDITION_EQ = " = ";
	public static final String PARSE_SQL_CONDITION_SQLPARAMSVALUE_CASCADE_REP = "?0";

	public static final String PARSE_SQL_CONDITION_SQLPARAMSVALUE_DATE_REP = "?1";
	public static final String PARSE_SQL_CONDITION_SQLPARAMSVALUE_RULE_REP = "?2";
	public static final String PARSE_SQL_CONDITION_SQLPARAMSVALUE_SPLIT_FLAG = "\\|";
	public static final String PARSE_SQL_CONDITION_SQLPARAMSVALUE_VALUE_SPLIT_FLAG = "#";
	public static final String PARSE_SQL_CONDITION_POS_N = "N";//解析sql条件时，内部处理标识
	public static final String PARSE_SQL_CONDITION_POS_W = "W";//解析sql条件时，外部处理标识
	//处理级联条件时用于解析单个条件的正则表达式
	public static final String PARSE_SQL_CASCADECON_MATCH_EXP = "\\(\\s*~(\\w+)~\\s*(in|=|not\\sin)\\s*(\\(?['?[\\w|\\.|\\-|\\s|:|\\*]*'?,?]*\\)?)\\s*\\)";
	//处理行为规则条件时用于替换子SQL的正则表达式
	public static final String PARSE_SQL_RULECON_MATCH_EXP = "\\(\\s*(~?\\w+.?\\w*~?)\\s*in\\s*\\?2\\s*\\)";
	//处理行为规则条件时用于替换子SQL的正则表达式
	public static final String PARSE_SQL_CITYCON_MATCH_EXP = "\\s*(and)\\s*~(\\w+)~\\s*(=|in|not\\sin)\\s*\\(?(['?[\\w|\\.|\\-|\\s|:|\\*]*'?,?]*)\\)?\\s*";
	//数据库模式符号：用于后台TCL程序替换
	public static final String PARSE_SQL_CONDITION_DBSCHEME_FLAG = "~SCHEMA~";
	/**
	 * 条件中含有的类型
	 * 
	 */
	public static final String PARSE_SQL_CONDITION_TYPE_CON = "1";//普通条件
	public static final String PARSE_SQL_CONDITION_TYPE_CONNECT = "2";//条件连接符
	public static final String PARSE_SQL_CONDITION_TYPE_CURLY_BRACE = "3";//花括号-用户添加的括号
	public static final String PARSE_SQL_CONDITION_TYPE_RULE = "4";//行为规则类型的条件
	public static final String PARSE_SQL_CONDITION_TYPE_BOOKATTR = "5";//订购类型属性条件
	public static final String PARSE_SQL_CONDITION_TYPE_COMPLEX = "6";//含有计算（+-×/）指标的负责条件
	public static final String PARSE_SQL_CONDITION_TYPE_VALUE = "7";//数值信息

	public static final String CTRL_LISTBOX = "ctrl_listbox";
	public static final String CTRL_TREECHECK = "ctrl_treeCheck";
	public static final String CTRL_DATE = "ctrl_date";
	public static final String CTRL_INPUT = "ctrl_input";
	public static final String CTRL_TAG = "ctrl_tag";
	public static final String CTRL_RANGE = "ctrl_range";
	public static final String CTRL_CHILDLIST = "ctrl_childList";
	public static final String CTRL_LISTTREECHECKHREF = "ctrl_listTreeCheckHref";

	/**
	 * 元数据字段的数据类型
	 */
	public static final String MDA_COLUMN_DATA_TYPE_STRING = "1";//字符串
	public static final String MDA_COLUMN_DATA_TYPE_INT = "2";//整数
	public static final String MDA_COLUMN_DATA_TYPE_FLOAT = "3";//浮点数
	public static final String MDA_COLUMN_DATA_TYPE_DATE = "4";//日期
	public static final String MDA_COLUMN_DATA_TYPE_TIME = "5";//时间
	/**
	 * 数据类型对应维表组件值
	 */
	public static final String MDA_COLUMN_DATA_TYPE_DIM_STRING = "char";//字符

	/**
	 * 元数据类型
	 */
	public static final int MDA_COLUMN_TYPE_DIM = 1;//维度
	public static final int MDA_COLUMN_TYPE_INDICATOR = 2;//指标
	public static final int MDA_COLUMN_TYPE_TAG = 3;//标签
	public static final int MDA_COLUMN_TYPE_OTHERS = 0;//其他
	public static final int MDA_COLUMN_TYPE_EVENT = 4;//事件

	/**
	 * 主表主键
	 */
	public static final String MAINTABLE_KEYCOLUMN = "MAINTABLE_KEYCOLUMN";//字符串
	/**
	 * 主表主键的描述
	 */
	public static final String MAINTABLE_KEYCOLUMN_DESCS = "MAINTABLE_KEYCOLUMN_DESCS";//字符串
	/**
	 * 表连接方式-外连接
	 */
	public static final String TABLE_CONNECT_WAY_OUTTER = "OUTTER ";//字符串
	/**
	 * 表连接方式-内连接
	 */
	public static final String TABLE_CONNECT_WAY_INNER = "INNER";//字符串

	/**
	 * 日期格式
	 */
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String DATE_FORMAT_YYYYMM = "yyyyMM";
	public static final String DATE_FORMAT_YYYY = "yyyy";
	public static final String DATE_FORMAT_YYMM = "yyMM";
	public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATE_FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String WITHOUT_CHECK_CVIEW_CITYID = "-1";//视图表CITYID为此值不用校验

	public static final String DATE_FORMAT_YYYY_MM_ZH = "yyyy年MM月";
	public static final String DATE_FORMAT_YYYY_MM_DD_ZH = "yyyy年MM月dd日";

	//SQL解析器的配置名称
	public static final String SQL_PARSE_HANDLERS_HOOK = "SQL_PARSE_HANDLERS_HOOK";

	public static final String MCD_CHANCE_VIEW_ID = "999";//时机
	public static final String MCD_EVENT_VIEW_ID = "888";//事件

	public static final String CACAHE_COMMON_META_ELEMENTS = "CACAHE_COMMON_META_ELEMENTS";

	/**
	 * 统一视图主表名称
	 */
	public static final String MAIN_TABLE_NAME = "main_table_name";
	public static final String CACAHE_COMMON_META_TREE_CLASS = "CACAHE_COMMON_META_TREE_CLASS";
	public static final Locale LOCALE = new Locale(Configure.getInstance().getProperty("LOCALE_LANGUAGE_DEFAULT"),
			Configure.getInstance().getProperty("LOCALE_COUNTRY_DEFAULT"), "");
	public static final String CACAHE_COMMON_USUAL = "KMV_CACAHE_COMMON_USUAL";
	public static final String CACAHE_COMMON_DEFAULT_CVIEW_ID = "CACAHE_COMMON_DEFAULT_CVIEW_ID";
	public static final String CACAHE_COMMON_ALL_CVIEW = "CACAHE_COMMON_ALL_CVIEW";

	public static final String ENCODE_TYPE_UTF = "UTF-8";
	/** 缓存业务特征和模板对应的用于界面展示的JSON数组数据*/
	public static final String CACHE_TEMPLETDATA_JSONARRAY = "CACHE_TEMPLETDATA_JSONARRAY";
	public static final int ACTIVE_TEMPLET_CREATE_TYPE_QUERY_CONDITIONS = 6;
	public static final int ACTIVE_TEMPLET_CREATE_TYPE_SPECIALIZED_ANALYSIS = 7;

	/** 时机字段与事件字段的名称前缀 **/
	public static final String CAMP_CHANCE_PREFIX = "CHANCE_";//时机
	public static final String CAMP_EVENT_PREFIX = "EVENT_";//事件
}
