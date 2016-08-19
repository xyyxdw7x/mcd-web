package com.asiainfo.biapp.mcd.common.util;

import java.sql.Clob;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Title:DBAdapter.java
 * </p>
 * <p>
 * Description:数据库适配器，提供大量工具方法，用来屏蔽数据库之间的差异。
 * <p>
 * 本类通过spring配置，必须指定dbType参数，支持setter注入。 <br>
 * 参数值从MYSQL、ORACLE、ACCESS、SQLSERVER、DB2、SYBASE中选择。
 * </p>
 * <p>
 * 时间格式语法 Java时间格式语法： 使用一个 time pattern 字符串指定时间格式。 在这种方式下，所有的 ASCII
 * 字母被保留为模式字母，定义如下：
 * 
 * 符号 含义 表示 示例 ------ ------- ------------ ------- G 年代标志符 (Text) AD y 年
 * (Number) 1996 M 月 (Text & Number) July & 07 d 日 (Number) 10 h 时 在上午或下午 (1~12)
 * (Number) 12 H 时 在一天中 (0~23) (Number) 0 m 分 (Number) 30 s 秒 (Number) 55 S 毫秒
 * (Number) 978 E 星期 (Text) Tuesday D 一年中的第几天 (Number) 189 F 一月中第几个星期几 (Number)
 * 2 (2nd Wed in July) w 一年中第几个星期 (Number) 27 W 一月中第几个星期 (Number) 2 a 上午 / 下午
 * 标记符 (Text) PM k 时 在一天中 (1~24) (Number) 24 K 时 在上午或下午 (0~11) (Number) 0 z 时区
 * (Text) Pacific Standard Time ' 文本转义符 (Delimiter) '' 单引号 (Literal)
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) Asiainfo 2002
 * </p>
 * <p>
 * Author Chao Wu wuchao@asiainfo.com
 * </p>
 * <p>
 * Version 1.0
 * </p>
 * <p>
 * Date Mar 25, 2009 3:23:32 PM
 * </p>
 */

public class DataBaseAdapter {
	private static Log log = LogFactory.getLog(DataBaseAdapter.class);

	// 日期字符串格式
	/**
	 * 格式：yyyy-MM-dd
	 */
	public static String DATA_FORMAT1 = "yyyy-MM-dd";
	/**
	 * 格式：yyyy/MM/dd
	 */
	public static String DATA_FORMAT2 = "yyyy/MM/dd";
	/**
	 * 格式：yyyyMMdd
	 */
	public static String DATA_FORMAT3 = "yyyyMMdd";
	// 时间单位常量
	/**
	 * 秒
	 */
	public static String SECOND = "SECOND";
	/**
	 * 分
	 */
	public static String MINUTE = "MINUTE";
	/**
	 * 小时
	 */
	public static String HOUR = "HOUR";
	/**
	 * 天
	 */
	public static String DAY = "DAY";
	/**
	 * 月
	 */
	public static String MONTH = "MONTH";
	/**
	 * 年
	 */
	public static String YEAR = "YEAR";

	/**
	 * 指定数据库类型，参数值从MYSQL、ORACLE、ACCESS、SQLSERVER、DB2、SYBASE中选择
	 */
	private static String dbType;

	/**
	 * ORACLE
	 */
	public static final String DBMS_ORACLE = "ORACLE";
	/**
	 * ODBC
	 */
	public static final String DBMS_ODBC = "ODBC";
	/**
	 * ACESS
	 */
	public static final String DBMS_ACESS = "ACESS";
	/**
	 * MYSQL
	 */
	public static final String DBMS_MYSQL = "MYSQL";
	/**
	 * DB2
	 */
	public static final String DBMS_DB2 = "DB2";
	/**
	 * SQLSERVER
	 */
	public static final String DBMS_SQLSERVER = "SQLSERVER";
	/**
	 * TERA
	 */
	public static final String DBMS_TERA = "TERA";
	/**
	 * SYBASE
	 */
	public static final String DBMS_SYBASE = "SYBASE";

	/**
	 * 设置数据库类型 spring注入使用。
	 * 
	 * @param dbType
	 */
	public void setDbType(String dbType) {
		DataBaseAdapter.dbType = dbType;
	}

	/**
	 * 设置数据库类型
	 * 
	 * @param dbType
	 */
	public static void setDataBaseType(String dbType) {
		DataBaseAdapter.dbType = dbType;
	}

	/**
	 * 得到数据库类型
	 * 
	 * @return
	 * @throws RuntimeException
	 */
	public static String getDbType() throws RuntimeException {
		if (dbType == null || dbType.trim() == "")
			throw new RuntimeException("类DBAdapter的属性dbType必须注入，不能为空");

		// MYSQL、ORACLE、ACCESS、SQLSERVER、DB2、SYBASE
		if (dbType.indexOf("MYSQL") >= 0) {
			dbType = DBMS_MYSQL;
		} else if (dbType.indexOf("ORACLE") >= 0) {
			dbType = DBMS_ORACLE;
		} else if (dbType.indexOf("ACCESS") >= 0) {
			dbType = DBMS_ACESS;
		} else if (dbType.indexOf("SQL SERVER") >= 0) {
			dbType = DBMS_SQLSERVER;
		} else if (dbType.indexOf("DB2") >= 0) {
			dbType = DBMS_DB2;
		} else if (dbType.indexOf("TERA") >= 0) {
			dbType = DBMS_TERA;
		} else if (dbType.indexOf("SYBASE") >= 0) {
			dbType = DBMS_SYBASE;
		} else {
			throw new RuntimeException("不支持的数据库类型！");
		}
		return dbType;
	}

	/**
	 * 获得各种数据库的TimeStamp函数。格式 '2002-10-20 12:00:00'
	 * 
	 * @param strDateStr
	 *            日期 。格式： 2002-10-20
	 * @param strH
	 *            小时 格式：00
	 * @param strM
	 *            分钟 格式：00
	 * @param strS
	 *            秒 格式：00
	 * @return
	 * @throws RuntimeException
	 */
	public static String getTimeStamp(String strDateStr, String strH,
			String strM, String strS) throws RuntimeException {
		if (null == strDateStr || strDateStr.length() < 1) {
			return null;
		}
		if (strDateStr.indexOf("0000-00-00") >= 0) {
			return "null";
		}
		String strType = getDbType(); // Must call
		String strRet = "";
		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = "'" + strDateStr + " " + strH + ":" + strM + ":" + strS
					+ "'";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "timestamp('" + strDateStr + " " + strH + ":" + strM + ":"
					+ strS + "')";
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "to_date('" + strDateStr + " " + strH + ":" + strM + ":"
					+ strS + "','YYYY-mm-dd hh24:mi:ss')";
		} else if (strType.equalsIgnoreCase(DBMS_ACESS)) {
			strRet = "'" + strDateStr + "'";
		} else if (strType.equalsIgnoreCase(DBMS_SQLSERVER)) {
			strRet = "CONVERT(Datetime,'" + strDateStr + " " + strH + ":"
					+ strM + ":" + strS + "',20)";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = strDateStr + " (FORMAT 'YYYY-MM-DD')";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "cast('" + strDateStr + " " + strH + ":" + strM + ":"
					+ strS + "' as Datetime)";
		} else {
			throw new RuntimeException("不能取得当前日期的函数定义");
		}
		return strRet;

	}

	/**
	 * 获得各种数据库的TimeStamp函数。格式 '2002-10-20'，时分秒补00
	 * 
	 * @param strDateStr
	 *            格式 '2002-10-20'
	 * @return
	 * @throws RuntimeException
	 */
	public static String getTimeStamp(String strDateStr)
			throws RuntimeException {
		if (strDateStr != null && strDateStr.trim().length() > 0) {
			strDateStr = strDateStr.trim();
			Pattern p = Pattern
					.compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}$"); // YYYY-MM-DD
																							// HH:MM:SS
			if (p.matcher(strDateStr).matches()) {
				String[] strArr = strDateStr.split("\\s+");
				String[] hmsArr = strArr[1].split(":");
				return getTimeStamp(strArr[0], hmsArr[0], hmsArr[1], hmsArr[2]);
			}

			p = Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}$");
			if (p.matcher(strDateStr).matches()) {
				return getTimeStamp(strDateStr, "00", "00", "00");
			}
		}
		return "";
	}

	/**
	 * 获得各种数据库的TimeStamp函数名
	 */
	public static String getTimeStampName() {
		String strType = getDbType(); // Must call
		String result = "";
		if (strType.equalsIgnoreCase(DBMS_DB2)) {
			result = "timestamp";
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			result = "to_date";
		}
		return result;
	}

	/**
	 * 获得各种数据库的TimeStamp函数。
	 * 
	 * @param date
	 *            日期
	 * @return
	 * @throws RuntimeException
	 */
	public static String getTimeStamp(java.util.Date date) {
		java.text.SimpleDateFormat dFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		java.text.SimpleDateFormat dFormat2 = new java.text.SimpleDateFormat(
				"HH");
		java.text.SimpleDateFormat dFormat3 = new java.text.SimpleDateFormat(
				"mm");
		java.text.SimpleDateFormat dFormat4 = new java.text.SimpleDateFormat(
				"ss");
		return getTimeStamp(dFormat.format(date), dFormat2.format(date),
				dFormat3.format(date), dFormat4.format(date));
	}

	/**
	 * 取得各种数据库的日期函数(Data) 字符串格式 yyyy-MM-dd
	 * 
	 * @param strDateStr
	 *            字符串格式 yyyy-MM-dd
	 * @return 数据库日期函数
	 * @throws RuntimeException
	 */
	public static String getDate(String strDateStr) {
		if (null == strDateStr || strDateStr.length() < 1) {
			return null;
		}
		if (strDateStr.indexOf("0000-00-00") >= 0) {
			return "null";
		}
		String strType = getDbType(); // Must call
		String strRet = "";
		int i = strDateStr.indexOf(" ");
		if (i > 0)
			strDateStr = strDateStr.substring(0, i);
		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = "'" + strDateStr + "'";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "date('" + strDateStr + "')";
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "to_date('" + strDateStr + "','YYYY-mm-dd')";
		} else if (strType.equalsIgnoreCase(DBMS_ACESS)) {
			strRet = "'" + strDateStr + "'";
		} else if (strType.equalsIgnoreCase(DBMS_SQLSERVER)) {
			strRet = "convert(varchar(10), convert(datetime,'" + strDateStr
					+ "'), 111)";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = "cast('" + strDateStr + "' as date FORMAT 'YYYY-MM-DD')";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "cast('" + strDateStr + "' as Date)";
		} else {
			throw new RuntimeException("不能取得当前日期的函数定义");
		}
		return strRet;
	}

	/**
	 * 获取不同数据库日期格式 日期字符串来自DBAdapter.DATA_FORMAT1 DBAdapter.DATA_FORMAT2
	 * DBAdapter.DATA_FORMAT3 中的一种。
	 * 
	 * @param strDateStr
	 *            日期字符串
	 * @param format
	 *            字符串格式
	 * @return
	 * @throws RuntimeException
	 */
	public static String getDate(String strDateStr, String format) {
		if (format.equals(DataBaseAdapter.DATA_FORMAT1))
			return getDate(strDateStr);
		else if (format.equals(DataBaseAdapter.DATA_FORMAT2))
			return getDate2(strDateStr);
		else
			return getDate3(strDateStr);
	}

	/**
	 * 获取不同数据库日期格式为YYYY/mm/dd的日期转换函数字串
	 * 
	 * @param strDateStr
	 * @return
	 * @throws RuntimeException
	 */
	private static String getDate2(String strDateStr) throws RuntimeException {
		if (null == strDateStr || strDateStr.length() < 1) {
			return null;
		}
		if (strDateStr.indexOf("000000") >= 0) {
			return "null";
		}
		String strType = getDbType(); // Must call
		String strRet = "";
		int i = strDateStr.indexOf(" ");
		if (i > 0)
			strDateStr = strDateStr.substring(0, i);
		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = "'" + strDateStr + "'";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "date('" + strDateStr + "')";
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "to_date('" + strDateStr + "','YYYY/mm/dd')";
		} else if (strType.equalsIgnoreCase(DBMS_ACESS)) {
			strRet = "'" + strDateStr + "'";
		} else if (strType.equalsIgnoreCase(DBMS_SQLSERVER)) {
			strRet = "CONVERT(Datetime,'" + strDateStr + "',20)";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = "cast('" + strDateStr + "' as date FORMAT 'YYYY/MM/DD')";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "cast('" + strDateStr + "' as Date)";
		} else {
			throw new RuntimeException("不能取得当前日期的函数定义");
		}
		return strRet;
	}

	/**
	 * 获取不同数据库日期格式为YYYYmm的日期转换函数字串
	 * 
	 * @param strDateStr
	 * @return
	 * @throws RuntimeException
	 */
	private static String getDate3(String strDateStr) throws RuntimeException {
		if (null == strDateStr || strDateStr.length() < 1) {
			return null;
		}
		if (strDateStr.indexOf("000000") >= 0) {
			return "null";
		}
		String strType = getDbType(); // Must call
		String strRet = "";
		int i = strDateStr.indexOf(" ");
		if (i > 0)
			strDateStr = strDateStr.substring(0, i);
		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = "'" + strDateStr + "'";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "date('" + strDateStr + "')";
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "to_date('" + strDateStr + "','YYYYmmdd')";
		} else if (strType.equalsIgnoreCase(DBMS_ACESS)) {
			strRet = "'" + strDateStr + "'";
		} else if (strType.equalsIgnoreCase(DBMS_SQLSERVER)) {
			strRet = "convert(varchar, convert(datetime, '" + strDateStr
					+ "'), 112)";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = strDateStr + " (FORMAT 'YYYYMMDD')";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "cast('" + strDateStr + "' as Date)";
		} else {
			throw new RuntimeException("不能取得当前日期的函数定义");
		}
		return strRet;
	}

	/**
	 * 取得DBMS生成类似日期格式YYYY-MM-DD HH24:MI:SS的函数。
	 * 
	 * @param strDateColName
	 *            日期类型的参数值
	 * @return 函数
	 * @throws RuntimeException
	 *             不支持此类DBMS
	 */
	public static String getFullDate(String strDateColName)
			throws RuntimeException {
		String strType = getDbType(); // Must call
		String strRet = "";
		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = "Date_Format(" + strDateColName + ",'%Y-%m-%d %H:%i:%s')";
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "to_char(" + strDateColName + ",'YYYY-mm-dd hh24:mi:ss')";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "ts_fmt(" + strDateColName + ",'yyyy-mm-dd hh:mi:ss')";
		} else if (strType.equalsIgnoreCase(DBMS_ACESS)) {
			strRet = strDateColName;
		} else if (strType.equalsIgnoreCase(DBMS_SQLSERVER)) {
			strRet = "CONVERT(Varchar," + strDateColName + ",20)";
			strRet = "CONVERT(Varchar," + strDateColName + ",120)";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = strDateColName + " (FORMAT 'YYYY-MM-DD')";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "convert(char(10)," + strDateColName
					+ ",23) || ' ' || convert(char(8)," + strDateColName
					+ ",108)";
		} else {
			throw new RuntimeException("不能取得当前日期的函数定义");
		}
		return strRet;

	}

	/**
	 * 取得DBMS当前日期的函数
	 * 
	 * @return 当前日期函数
	 * @throws RuntimeException
	 *             不支持此类DBMS
	 */
	public static String getNow() throws RuntimeException {
		String strType = getDbType(); // Must call
		String strRet = "";
		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = "now()";
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "sysdate";
		} else if (strType.equalsIgnoreCase(DBMS_ACESS)) {
			strRet = "date()+time()";
		} else if (strType.equalsIgnoreCase(DBMS_SQLSERVER)) {
			strRet = "getdate()";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "current timestamp";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = "cast((date (format 'yyyy-mm-dd' )) as char(10)) ||' '|| time";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "getdate()";
		} else {
			throw new RuntimeException("不能取得当前日期的函数定义");
		}
		return strRet;
	}

	/**
	 * 取得DBMS生成限制返回前N条记录条的SQL
	 * 
	 * @param limitnum
	 *            限制返回条数
	 * @return 函数
	 * @throws RuntimeException
	 *             不支持此类DBMS
	 */
	public static String getSqlLimit(String strSql, int limitnum) {
		String strType = getDbType(); // Must call
		String strRet = "";
		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = strSql + " limit " + limitnum;
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			limitnum++;

			strRet = "select * from (" + strSql + ") where ROWNUM<" + limitnum;
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = strSql + "fetch first " + limitnum + " rows only";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "select top " + limitnum + " * from(" + strSql + ") a";
		} else if (strType.equalsIgnoreCase(DBMS_SQLSERVER)) {
			strRet = "select top " + limitnum + " * from(" + strSql + ") a";
		}
		// end
		else if (DBMS_TERA.equalsIgnoreCase(strType)) {
			StringBuffer buffer = new StringBuffer(strSql.length() + 100);
			buffer.append(strSql);
			int orderByIndex = buffer.toString().toLowerCase().lastIndexOf(
					"order by");
			if (orderByIndex > 0) {

				String orderBy = buffer.substring(orderByIndex);

				buffer.insert(orderByIndex, " QUALIFY row_number() OVER( ");
				buffer.append(" ) ");
				buffer.append(" between 1 and " + limitnum);
				buffer.append(orderBy);
			} else {
				buffer
						.append(" QUALIFY sum(1) over (rows unbounded preceding) between 1 and "
								+ limitnum);
			}
			strRet = buffer.toString();
		} else {
			throw new RuntimeException("不能取得函数定义");
		}
		return strRet;

	}

	/**
	 * 取得各种数据库的分页SQL
	 * 
	 * @param sql
	 *            原始SQL
	 * @param currPage
	 *            当前页（取值从1开始，即第一页currpage=1）
	 * @param pageSize
	 *            每页的记录数
	 * @return
	 * @throws RuntimeException
	 */
	public static String getPagedSql(String srcSql, int currPage, int pageSize) {
//		String dbType = getDbType(); // Must call
		String dbType = "ORACLE";
		int begin = (currPage - 1) * pageSize;
		int end = begin + pageSize;
		String strRet = srcSql;
		if (dbType.equals(DBMS_MYSQL)) {
			strRet = srcSql + " limit " + begin + "," + pageSize;
		} else if (dbType.equals(DBMS_ORACLE)) {
			end++;
			strRet = "SELECT * FROM (SELECT T.*, ROWNUM RN FROM ("
					+ srcSql + ") T WHERE ROWNUM < " + end + "  ) WHERE RN >"
					+ begin;
		} else if (dbType.equals(DBMS_DB2)) {
			StringBuffer rownumber = new StringBuffer(" rownumber() over(");
			int orderByIndex = srcSql.toLowerCase().indexOf("order by");

			if (orderByIndex > 0) {
				String[] tempStr = srcSql.substring(orderByIndex).split("\\.");
				for (int i = 0; i < tempStr.length - 1; i++) {
					int dotIndex = tempStr[i].lastIndexOf(",");
					if (dotIndex < 0)
						dotIndex = tempStr[i].lastIndexOf(" ");
					String result = tempStr[i].substring(0, dotIndex + 1);
					rownumber.append(result).append(" temp_.");
				}
				rownumber.append(tempStr[tempStr.length - 1]);
			}

			rownumber.append(") as row_,");

			StringBuffer pagingSelect = new StringBuffer(srcSql.length() + 100)
					.append("select * from ( ").append(" select ").append(
							rownumber.toString()).append("temp_.* from (")
					.append(srcSql).append(" ) as temp_")
					.append(" ) as temp2_").append(
							" where row_  between " + begin + "+1 and " + end);
			strRet = pagingSelect.toString();
		} else if (DBMS_TERA.equalsIgnoreCase(dbType)) {
			StringBuffer buffer = new StringBuffer(srcSql.length() + 100);
			buffer.append(srcSql);
			int orderByIndex = buffer.toString().toLowerCase().lastIndexOf(
					"order by");
			if (orderByIndex > 0) {
				String orderBy = buffer.substring(orderByIndex);
				buffer.insert(orderByIndex, " QUALIFY row_number() OVER( ");
				buffer.append(" ) ");
				buffer.append(" between " + begin + " and " + end);
				buffer.append(orderBy);
			} else {
				buffer
						.append(" QUALIFY sum(1) over (rows unbounded preceding) between "
								+ begin + " and " + end);
			}
			strRet = buffer.toString();
		}
		return strRet;
	}

	/**
	 * 根据数据库类型构造不同的分页SQL
	 * 
	 * @param sql
	 *            原始sql
	 * @param column
	 *            需要取的字段
	 * @param strPrimaryKey
	 *            主键
	 * @param curpage
	 *            当前页(取值从0开始，即第一页currpage=0)
	 * @param pagesize
	 *            每页的记录数
	 * @return
	 * @throws RuntimeException
	 */
	public static String getPagedSql(String sql, String column,
			String strPrimaryKey, int curpage, int pagesize)
			throws RuntimeException {
		String strDBType = getDbType();
		StringBuffer buffer = null;
		buffer = new StringBuffer();
		if (DBMS_ORACLE.equalsIgnoreCase(strDBType)) {
			buffer.append("select * from ( ");
			buffer.append("select ").append(column).append(
					" rownum as my_rownum from( ");
			buffer.append(sql).append(") ");
			int pageAll = pagesize * curpage + pagesize;
			buffer.append("where rownum <= " + pageAll + ") a ");
			buffer.append("where a.my_rownum > " + pagesize * curpage);
		} else if (DBMS_DB2.equalsIgnoreCase(strDBType)) {
			buffer.append("select * from ( ");
			buffer.append("select ").append(column).append(
					"  rownumber() over (order by " + strPrimaryKey
							+ ") as my_rownum from( ");
			buffer.append(sql).append(") as temp ");
			buffer.append("fetch first " + (pagesize * curpage + pagesize)
					+ " rows only) as a ");
			buffer.append("where a.my_rownum > " + pagesize * curpage);
		} else if (DBMS_TERA.equalsIgnoreCase(strDBType)) {
			buffer.append(sql);
			int orderByIndex = buffer.toString().toLowerCase().lastIndexOf(
					"order by");
			if (orderByIndex > 0) {
				String orderBy = buffer.substring(orderByIndex);

				buffer.insert(orderByIndex, " QUALIFY row_number() OVER( ");
				buffer.append(" ) ");
				buffer.append(" between " + pagesize * curpage + " and "
						+ (pagesize * curpage + pagesize));
				buffer.append(orderBy);
			} else {
				buffer
						.append(" QUALIFY sum(1) over (rows unbounded preceding) between "
								+ pagesize
								* curpage
								+ " and "
								+ (pagesize * curpage + pagesize));
			}
		}
		return buffer.toString();
	}

	/**
	 * 取得各种数据库子串的函数。
	 * 
	 * @param strColName
	 *            字段名称
	 * @param pos
	 *            起始位置
	 * @param len
	 *            子串长度
	 * @return 函数
	 * @throws RuntimeException
	 *             不支持此类DBMS
	 */
	public static String getSubString(String strColName, int pos, int len)
			throws RuntimeException {
		String strType = getDbType(); // Must call
		String strRet = "";
		if (strType.equalsIgnoreCase(DBMS_MYSQL)
				|| strType.equalsIgnoreCase(DBMS_SYBASE)) {
			if (len == -1) {
				strRet = "substring(" + strColName + "," + pos + ")";
			} else {
				strRet = "substring(" + strColName + "," + pos + "," + len
						+ ")";
			}
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)
				|| strType.equalsIgnoreCase(DBMS_DB2)) {
			if (len == -1) {
				strRet = "substr(" + strColName + "," + pos + ")";
			} else {
				strRet = "substr(" + strColName + "," + pos + "," + len + ")";
			}
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			if (len == -1) {
				strRet = "substring(" + strColName + " form " + pos + ")";
			} else {
				strRet = "substring(" + strColName + " from " + pos + " for "
						+ len + ")";
			}
		} else {
			throw new RuntimeException("不能取得函数定义");
		}
		return strRet;
	}

	/**
	 * 取得各种数据库子串的函数。
	 * 
	 * @param strColName
	 *            字段名称
	 * @param pos
	 *            起始位置
	 * @return 函数
	 * @throws RuntimeException
	 *             不支持此类DBMS
	 */
	public static String getSubString(String strColName, int pos)
			throws RuntimeException {
		return getSubString(strColName, pos, -1);
	}

	/**
	 * 当前时间加减一段时间，做减法需要interv参数前面带"-"
	 * 
	 * @param interv
	 *            时间段长度
	 * @param unit
	 *            时间段单位：SECOND,MINUTE,HOUR,DAY,MONTH,YEAR
	 * @throws RuntimeException
	 * @return String
	 */
	public static String getAddDate(String interv, String unit)
			throws RuntimeException {
		String strRet = "";
		String strType = getDbType(); // Must call
		unit = unit.trim().toUpperCase();
		interv = interv.trim();
		if (!interv.startsWith("-"))
			interv = "+" + interv;
		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = "ADDDATE(now(),INTERVAL " + interv + " " + unit + ")";
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			if (unit.compareTo("MINUTE") == 0)
				strRet = "(sysdate" + interv + "/(24*60))";
			else if (unit.compareTo("SECOND") == 0)
				strRet = "(sysdate" + interv + "/(24*60*60))";
			else if (unit.compareTo("HOUR") == 0)
				strRet = "(sysdate" + interv + "/24)";
			else if (unit.compareTo("DAY") == 0)
				strRet = "(sysdate" + interv + ")";
			else if (unit.compareTo("MONTH") == 0)
				strRet = "(add_months(sysdate," + interv + "))";
			else if (unit.compareTo("YEAR") == 0)
				strRet = "(add_months(sysdate,(" + interv + "*12)))";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "(current timestamp " + interv + " " + unit + ")";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			if (unit.compareTo("MINUTE") == 0)
				strRet = "dateadd(mi," + interv + ",getdate())";
			else if (unit.compareTo("SECOND") == 0)
				strRet = "dateadd(ss," + interv + ",getdate())";
			else if (unit.compareTo("HOUR") == 0)
				strRet = "dateadd(hh," + interv + ",getdate())";
			else if (unit.compareTo("DAY") == 0)
				strRet = "dateadd(dd," + interv + ",getdate())";
			else if (unit.compareTo("MONTH") == 0)
				strRet = "dateadd(mm," + interv + ",getdate())";
			else if (unit.compareTo("YEAR") == 0)
				strRet = "dateadd(yy," + interv + ",getdate())";
		} else {
			throw new RuntimeException("不能取得当前日期的函数定义");
		}

		return strRet;
	}

	/**
	 * 返回各种数据库中当前日期加N个月份后再转换为字符的函数 added by wwl 2004-12-26
	 * 
	 * @param month
	 * @return
	 * @throws RuntimeException
	 */
	public static String getDateAddMonth(String monthNum)
			throws RuntimeException {
		String strType = getDbType(); // Must call
		String strRet = "";

		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = "DATE_ADD(curdate(),INTERVAL " + monthNum + " month)";
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "to_char(add_months(sysdate," + monthNum
					+ "),'YYYY-mm-dd')";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "char((current date + " + monthNum + " month))";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "dateadd(mm," + monthNum + ",getdate())";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = "add_months(date," + monthNum + ")";
		} else {
			throw new RuntimeException("不能取得当前日期的函数定义");
		}
		return strRet;
	}

	/**
	 * 把表中字段的值有int转换为char类型的值
	 * 
	 * @param strColName
	 *            字段名
	 * @return
	 * @throws RuntimeException
	 */
	public static String getIntToChar(String strColName)
			throws RuntimeException {
		String strType = getDbType(); // Must call
		String strRet = "";

		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = strColName;
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "cast(" + strColName + " as varchar2(12))";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "char(" + strColName + ")";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "convert(char," + strColName + ")";
		} else if (strType.equalsIgnoreCase(DBMS_SQLSERVER)) {
			strRet = "cast(" + strColName + " as varchar(12))";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = "cast(" + strColName + " as varchar(12))";
		} else {
			throw new RuntimeException("不能取得函数定义");
		}
		return strRet;
	}

	/**
	 * 把表中字段的值有char转换为int类型的值
	 * 
	 * @param strColName
	 *            字段名
	 * @return
	 * @throws RuntimeException
	 */
	public static String getCharToInt(String strColName)
			throws RuntimeException {
		String strType = getDbType(); // Must call
		String strRet = "";

		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = strColName;
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)
				|| strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = "cast(" + strColName + " as integer)";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "int(" + strColName + ")";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "convert(int," + strColName + ")";
		} else if (strType.equalsIgnoreCase(DBMS_SQLSERVER)) {
			strRet = "cast(" + strColName + " as integer)";
		} else {
			throw new RuntimeException("不能取得函数定义");
		}
		return strRet;
	}

	/**
	 * 把表中字段的值有char转换为double类型的值
	 * 
	 * @param strColName
	 *            字段名
	 * @return
	 * @throws RuntimeException
	 */
	public static String getCharToDouble(String strColName) {
		String strType = getDbType(); // Must call
		String strRet = "";

		if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = strColName;
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "cast(" + strColName + " as numeric)";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "double(" + strColName + ")";
		} else {
			throw new RuntimeException("不能取得函数定义");
		}
		return strRet;
	}

	/**
	 * 取各种数据库中实现round功能的函数
	 * 
	 * @param str1
	 *            需要格式化的值或字段名
	 * @param str2
	 *            格式化保留的小数位数
	 * @return
	 * @throws RuntimeException
	 */
	public static String getRound(String str1, String str2)
			throws RuntimeException {
		String strType = getDbType(); // Must call
		String strRet = "";

		if (strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = " cast ((" + str1 + ") as decimal(10," + str2 + ")) ";
		} else {
			strRet = " round(" + str1 + "," + str2 + ") ";
		}
		return strRet;
	}

	/**
	 * 取得不等于的表示 teradata 不支持!=
	 * 
	 * @return
	 */
	public static String getNotEqual() throws RuntimeException {
		String strType = getDbType(); // Must call
		String strRet = "";

		if (strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = "<>";
		} else {
			strRet = "!=";
		}
		return strRet;
	}

	/**
	 * 如果前面的arg的值为null那么返回的值为后面的value
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 * @throws RuntimeException
	 */
	public static String getNvl(String str1, String str2)
			throws RuntimeException {
		String strType = getDbType();
		String strRet = "";

		if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "value(" + str1 + "," + str2 + ")";
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "nvl(" + str1 + "," + str2 + ")";
		} else if (strType.equalsIgnoreCase(DBMS_MYSQL)) {
			strRet = "ifnull(" + str1 + "," + str2 + ")";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "isnull(" + str1 + "," + str2 + ")";
		} else if (strType.equalsIgnoreCase(DBMS_SQLSERVER)) {
			strRet = "isnull(" + str1 + "," + str2 + ")";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			strRet = "COALESCE(" + str1 + "," + str2 + ")";
		} else {
			throw new RuntimeException("不能取得函数定义");
		}
		return strRet;
	}

	/**
	 * 根据数据库类型获取根据模板表结构创建新表的SQL
	 * 
	 * @param newtable
	 * @param templettable
	 * @return
	 * @throws RuntimeException
	 */
	public static String getCreateAsTableSql(String newtable,
			String templettable) throws RuntimeException {
		String ss = "";
		String strDBType = getDbType();
		if (DBMS_ORACLE.equalsIgnoreCase(strDBType)) {
			ss = "create table " + newtable + " as select * from "
					+ templettable + " where 1=2";
		} else if (DBMS_DB2.equalsIgnoreCase(strDBType)) {
			ss = "create table " + newtable + " like " + templettable;
		} else if (DBMS_TERA.equalsIgnoreCase(strDBType)) {
			ss = "create table " + newtable + " as " + templettable
					+ " with no data";
		}
		return ss;
	}

	/**
	 * 在指定表空间创建表
	 * 
	 * @param tableDDLSql
	 *            完整的建表SQL语句
	 * @param tableSpace
	 *            表空间名
	 * @return
	 * @throws RuntimeException
	 */
	public static String getCreateTableInTableSpaceSql(String tableDDLSql,
			String tableSpace) throws RuntimeException {
		if (tableSpace == null || tableSpace.length() < 1)
			return tableDDLSql;
		String strDBType = getDbType();
		if (DBMS_ORACLE.equalsIgnoreCase(strDBType)) {
			tableDDLSql += " tablespace " + tableSpace;
		} else if (DBMS_DB2.equalsIgnoreCase(strDBType)) {
			tableDDLSql += " in " + tableSpace;
		}
		return tableDDLSql;
	}

	/**
	 * 在指定表空间创建索引
	 * 
	 * @param createIndexSql
	 *            完整的创建表索引的SQL
	 * @param tableSpace
	 * @return
	 * @throws RuntimeException
	 */
	public static String getCreateIndexInTableSpaceSql(String createIndexSql,
			String tableSpace) throws RuntimeException {
		if (tableSpace == null || tableSpace.length() < 1)
			return createIndexSql;
		String strDBType = getDbType();
		if (DBMS_ORACLE.equalsIgnoreCase(strDBType)) {
			createIndexSql += " using index tablespace " + tableSpace;
		}
		return createIndexSql;
	}

	/**
	 * 根据数据库类型获取判断数据库是否存在特定名称表的SQL
	 * 
	 * @param tableName
	 * @return
	 * @throws RuntimeException
	 */
	public static String getCheckTableIsExistSql(String tableName)
			throws RuntimeException {
		String strSql = "";
		String strDBType = getDbType();
		if (strDBType.equals(DBMS_DB2)) {
			strSql = "select * from syscat.tables where tabname='"
					+ tableName.toUpperCase() + "'";
		} else if (strDBType.equals(DBMS_ORACLE)) {
			strSql = "select * from TAB where tname='"
					+ tableName.toUpperCase() + "'";
		} else if (strDBType.equals(DBMS_TERA)) {
			strSql = "select * from dbc.tables where tablename='"
					+ tableName.toUpperCase() + "'";
		}
		return strSql;
	}

	/**
	 * clob字段转字符串
	 * 
	 * @param clob
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String clobToString(Clob clob) {
		String s = "";
		StringBuffer content = new StringBuffer();
		try {
			java.io.BufferedReader br = new java.io.BufferedReader(clob
					.getCharacterStream());
			s = br.readLine();
			while (s != null) {
				content.append(s);
				s = br.readLine();
			}
		} catch (Exception e) {
			log.error("convert clob to string error", e);
		} finally {
			return content.toString();
		}
	}

	/**
	 * 树结构SQL查询封装.暂时只支持db2和oracle
	 * <p>
	 * 例如DBAdapter.queryTree("sys_menu_item", "menuitemid", "parentid", 47, 1,
	 * "desc", "menuitemtitle")
	 * <p>
	 * 表示查询sys_menu_item表，取menuitemid\parentid和menuitemtitle字段，menuitemid的起始值是47
	 * ，从该节点向叶子节点查询，查询结果按照menuitemid进行降序排列。
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param idName
	 *            子编码
	 * @param pidName
	 *            父编码
	 * @param startId
	 *            查询的起始值
	 * @param orientation
	 *            查询方向。0表示从根节点查向本节点,1表示从本节点查向叶子节点
	 * @param orderBy
	 *            对idName的排序方式。"asc"表示升序，"desc"表示降序
	 * @param args
	 *            查询的扩展字段名称，否则默认查询结果只包括idName和pidName字段
	 * @return 封装好的sql字符串
	 */
	public static String queryTree(String tableName, String idName,
			String pidName, Object startId, int orientation, String orderBy,
			String... args) throws RuntimeException {
		String strSql = null;
		String strDBType = getDbType();
		String order = null;
		// 获取起始值
		Object start = null;
		if (startId == null) { // 起始值为空
			throw new RuntimeException("起始查询条件不能为空，请参考本方法使用文档");
		} else if (startId instanceof String) { // 起始值不空，是字符类型
			start = (idName + "='" + startId + "' ");
		} else if (startId instanceof Number) { // 起始值不空，是数字类型
			start = (idName + "=" + startId + " ");
		} else {
			throw new RuntimeException("参数传递错误，请参考本方法使用文档");
		}
		if (orderBy != null && orderBy.trim() != null) {
			if (orderBy.trim().equalsIgnoreCase("asc"))
				order = "asc";
			else if (orderBy.trim().equalsIgnoreCase("desc"))
				order = "desc";
			else
				throw new RuntimeException("参数传递错误，请参考本方法使用文档");
		}

		// 拼装select子句
		String selectRet = idName + "," + pidName + " ";
		for (String arg : args) {
			selectRet += ("," + arg + " ");
		}
		// 拼装一个中间语句
		String tempRet = "";
		for (String t : selectRet.split(",")) {
			tempRet += ("," + "CHILD." + t.trim());
		}

		tempRet = tempRet.substring(1);

		if (strDBType.equals(DBMS_DB2)) {
			if (orientation == 1) {
				strSql = "WITH TEMP(" + selectRet + ") AS " + "(SELECT "
						+ selectRet + "  FROM " + tableName + " WHERE " + start
						+ "UNION ALL " + "SELECT " + tempRet
						+ "  FROM TEMP PARENT,  " + tableName
						+ " CHILD WHERE  PARENT." + idName + "=CHILD."
						+ pidName + ")" + "SELECT  " + selectRet
						+ "  FROM TEMP order by " + idName + " " + order;
			}
			if (orientation == 0) {
				strSql = "WITH TEMP(" + selectRet + ") AS " + "(SELECT "
						+ selectRet + "  FROM " + tableName + " WHERE " + start
						+ "UNION ALL " + "SELECT " + tempRet
						+ "  FROM TEMP PARENT,  " + tableName
						+ " CHILD WHERE  CHILD." + idName + "=PARENT."
						+ pidName + ")" + "SELECT  " + selectRet
						+ "  FROM TEMP order by " + idName + " " + order;
			}
		} else if (strDBType.equals(DBMS_ORACLE)) {
			if (orientation == 1) {
				strSql = "select " + selectRet + " from " + tableName
						+ " start with " + start + " connect by prior "
						+ idName + "=" + pidName + " order by " + idName + " "
						+ order;
			}
			if (orientation == 0) {
				strSql = "select " + selectRet + " from " + tableName
						+ " start with " + start + " connect by prior "
						+ pidName + "=" + idName + " order by " + idName + " "
						+ order;
			}
		} else {
			throw new RuntimeException("不支持的该数据库类型的树查询！");
		}
		return strSql;
	}

	/**
	 * 树结构SQL查询封装.暂时只支持db2和oracle
	 * <p>
	 * 例如DBAdapter.queryTree("sys_menu_item", "menuitemid", "parentid", 47, 1,
	 * "desc", "menuitemtitle")
	 * <p>
	 * 表示查询sys_menu_item表，取menuitemid\parentid和menuitemtitle字段，menuitemid的起始值是47
	 * ，从该节点向叶子节点查询，查询结果按照menuitemid进行降序排列。
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param idName
	 *            子编码
	 * @param pidName
	 *            父编码
	 * @param startId
	 *            查询的起始值
	 * @param orientation
	 *            查询方向。0表示从根节点查向本节点,1表示从本节点查向叶子节点
	 * @param orderBy
	 *            对idName的排序方式。"asc"表示升序，"desc"表示降序
	 * @param args
	 *            查询的扩展字段名称，否则默认查询结果只包括idName和pidName字段
	 * @return 封装好的sql字符串
	 */
	public static String getTreeSql(String tableName, String idName,
			String pidName, Object startId, int orientation, String orderBy,
			String... args) throws RuntimeException {
		String strSql = null;
		String strDBType = getDbType();
		String order = null;
		// 获取起始值
		Object start = null;
		if (startId == null) { // 起始值为空
			throw new RuntimeException("起始查询条件不能为空，请参考本方法使用文档");
		} else if (startId instanceof String) { // 起始值不空，是字符类型
			start = (idName + "='" + startId + "' ");
		} else if (startId instanceof Number) { // 起始值不空，是数字类型
			start = (idName + "=" + startId + " ");
		} else {
			throw new RuntimeException("参数传递错误，请参考本方法使用文档");
		}
		if (orderBy != null && orderBy.trim() != null) {
			if (orderBy.trim().equalsIgnoreCase("asc"))
				order = "asc";
			else if (orderBy.trim().equalsIgnoreCase("desc"))
				order = "desc";
			else
				throw new RuntimeException("参数传递错误，请参考本方法使用文档");
		}

		// 拼装select子句
		String selectRet = idName + "," + pidName + " ";
		for (String arg : args) {
			selectRet += ("," + arg + " ");
		}
		// 拼装一个中间语句
		String tempRet = "";
		for (String t : selectRet.split(",")) {
			tempRet += ("," + "CHILD." + t.trim());
		}

		tempRet = tempRet.substring(1);

		if (strDBType.equals(DBMS_DB2)) {
			if (orientation == 1) {
				strSql = "WITH TEMP(" + selectRet + ") AS " + "(SELECT "
						+ selectRet + "  FROM " + tableName + " WHERE " + start
						+ "UNION ALL " + "SELECT " + tempRet
						+ "  FROM TEMP PARENT,  " + tableName
						+ " CHILD WHERE  PARENT." + idName + "=CHILD."
						+ pidName + ")" + "SELECT  " + selectRet
						+ "  FROM TEMP order by " + idName + " " + order;
			}
			if (orientation == 0) {
				strSql = "WITH TEMP(" + selectRet + ") AS " + "(SELECT "
						+ selectRet + "  FROM " + tableName + " WHERE " + start
						+ "UNION ALL " + "SELECT " + tempRet
						+ "  FROM TEMP PARENT,  " + tableName
						+ " CHILD WHERE  CHILD." + idName + "=PARENT."
						+ pidName + ")" + "SELECT  " + selectRet
						+ "  FROM TEMP order by " + idName + " " + order;
			}
		} else if (strDBType.equals(DBMS_ORACLE)) {
			if (orientation == 1) {
				strSql = "select " + selectRet + " from " + tableName
						+ " start with " + start + " connect by prior "
						+ idName + "=" + pidName + " order by " + idName + " "
						+ order;
			}
			if (orientation == 0) {
				strSql = "select " + selectRet + " from " + tableName
						+ " start with " + start + " connect by prior "
						+ pidName + "=" + idName + " order by " + idName + " "
						+ order;
			}
		} else {
			throw new RuntimeException("不支持的该数据库类型的树查询！");
		}
		return strSql;
	}

	/**
	 * 获取字段长度
	 * 
	 * @param strColName
	 * @return
	 * @author chengjia
	 */
	public static String getStringLen(String strColName) {
		String strType = getDbType(); // Must call
		String strRet = "";
		if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "length(" + strColName + ")";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "length(" + strColName + ")";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			// strRet = "length(" + strColName + ")";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "datalength(" + strColName + ")";
		} else {
			throw new RuntimeException("不能取得函数定义");
		}
		return strRet;
	}

	/**
	 * 取字符在字符串中的位置,当前只支持DB2 和ORACLE
	 * 
	 * @param strColName
	 * @param charName
	 * @return
	 * @throws RuntimeException
	 * @author chengjia
	 */
	public static String getPosString(String strColName, String charName)
			throws RuntimeException {
		String strType = getDbType(); // Must call
		String strRet = "";

		if (strType.equalsIgnoreCase(DBMS_ORACLE)) {
			strRet = "instr(" + strColName + ",'" + charName + "')";
		} else if (strType.equalsIgnoreCase(DBMS_DB2)) {
			strRet = "locate('" + charName + "'," + strColName + ")";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {
			// strRet = "substring(" + strColName + " form " + pos + ")";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {
			strRet = "charindex('" + charName + "'," + strColName + ")";
		} else {
			throw new RuntimeException("不能取得函数定义");
		}
		return strRet;
	}

	/**
	 * 获取各种数据库子字符串的方法
	 * 
	 * @param strColName
	 *            --字段名称
	 * @param pos
	 *            --起始位置，用数据库函数得到
	 * @param len
	 *            --字符串长度，用数据库函数得到
	 * @return
	 * @throws RuntimeException
	 * @author chengjia
	 */
	public static String getSubString(String strColName, String pos, String len)
			throws RuntimeException {
		String strType = getDbType(); // Must call
		String strRet = "";
		if (strType.equalsIgnoreCase(DBMS_MYSQL)
				|| strType.equalsIgnoreCase(DBMS_SYBASE)) {

			strRet = "substring(" + strColName + "," + pos + "," + len + ")";
		} else if (strType.equalsIgnoreCase(DBMS_ORACLE)
				|| strType.equalsIgnoreCase(DBMS_DB2)) {

			strRet = "substr(" + strColName + "," + pos + "," + len + ")";
		} else if (strType.equalsIgnoreCase(DBMS_TERA)) {

			strRet = "substring(" + strColName + " from " + pos + " for " + len
					+ ")";
		} else if (strType.equalsIgnoreCase(DBMS_SYBASE)) {

			strRet = "substr(" + strColName + "," + pos + "," + len + ")";
		} else {
			throw new RuntimeException("不能取得函数定义");
		}
		return strRet;
	}

	/**
	 * @Title: generaterPrimaryKey
	 * @Description: 以UUID的方式生成主键
	 * @return String : 主键值
	 */
	public static synchronized String generaterPrimaryKey() throws Exception {
		UUID uuid = UUID.randomUUID();
		if (null == uuid || "".equals(uuid.toString())) {
			throw new NullPointerException("uuid is null");
		}
		String primaryKey = String.valueOf(uuid);
		if (null != primaryKey && primaryKey.contains("-")) {
			primaryKey = primaryKey.replaceAll("-", "");
		}
		return primaryKey;
	}
}
