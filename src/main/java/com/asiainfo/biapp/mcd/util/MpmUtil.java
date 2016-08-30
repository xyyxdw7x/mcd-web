package com.asiainfo.biapp.mcd.util;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.jdbc.core.JdbcTemplate;

import com.asiainfo.biapp.mcd.bean.SystemVarInfoBean;
import com.asiainfo.biapp.mcd.bo.MetaDataElement;
import com.asiainfo.biapp.mcd.constants.MpmCONST;
import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.jms.nbs.NbsJmsPublishUtil;
import com.asiainfo.biapp.mcd.jms.util.ConvertUtils;
import com.asiainfo.biapp.mcd.jms.util.JmsConstant;
import com.asiainfo.biapp.mcd.jms.util.SimpleCache;
import com.asiainfo.biapp.mcd.jms.util.SpringContext;
import com.asiainfo.biapp.mcd.model.McdCvDefine;
import com.asiainfo.biapp.mcd.service.IMcdViewService;
import com.asiainfo.biapp.mcd.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.sqlparse.SqlParseCONST;
import com.asiainfo.biframe.pagecomponent.bean.TreeNode;
import com.asiainfo.biframe.privilege.ICity;
import com.asiainfo.biframe.privilege.IUserCompany;
import com.asiainfo.biframe.unilog.base.util.StringFunc;
import com.asiainfo.biframe.utils.config.Configure;
import com.asiainfo.biframe.utils.date.DateUtil;
import com.asiainfo.biframe.utils.number.NumberUtil;
import com.asiainfo.biframe.utils.spring.SystemServiceLocator;
import com.asiainfo.biframe.utils.string.StringUtil;

public class MpmUtil {
	private static Logger log = LogManager.getLogger();

	/** 当前语言显示的日期格式 */
	public static final String DATE_LOCALE_FORMAT = MpmLocaleUtil.getMessage("mcd.dateFormat");
	/** 当前语言显示的日期格式 */
	public static final String DATE_LOCALE_FORMAT_YM = MpmLocaleUtil.getMessage("mcd.dateFormatYM");
	/** 当前语言显示的时间格式 */
	public static final String TIME_LOCALE_FORMAT = MpmLocaleUtil.getMessage("mcd.timeFormat");

	/** 当前语言ext显示的时间格式 */
	public static final String DATE_EXT_LOCALE_FORMAT = MpmLocaleUtil.getMessage("mcd.dateExtFormat");

	/** 数据库保存的日期格式 */
	public static final String DATE_DB_FORMAT = "yyyy-MM-dd";
	public static final String DATE_DB_FORMAT_YM = "yyyy-MM";

	/** 数据库保存的数据格式 */
	public static final String NUMBER_DB_FORMAT = "#.##";

	public static final DateFormat DATE_US_FORMATER = new SimpleDateFormat(DATE_LOCALE_FORMAT, Locale.US);
	public static final DateFormat DATE_US_FORMATER_YM = new SimpleDateFormat(DATE_LOCALE_FORMAT_YM, Locale.US);

	public static final DecimalFormat decimalFormat = new DecimalFormat(MpmLocaleUtil.getMessage("mcd.decimalFormat"));
	public static final DecimalFormat integerFormat = new DecimalFormat(MpmLocaleUtil.getMessage("mcd.integerFormat"));
	public static final DecimalFormat dbDecimalFormat = new DecimalFormat(NUMBER_DB_FORMAT);

	public static final DecimalFormat phoneNumberFormat = new DecimalFormat("0000");

	/** 单引号转码； */
	public static final String SINGLE_QUOTES = "&apos;";

	public static final boolean DATE_FORMAT_IS_DEFAULT = DATE_DB_FORMAT.equals(DATE_LOCALE_FORMAT);

	public static final int[] NUM_DATA = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	public static String lastTabTime = "";//记录最新的数据表时间戳后缀
	public static String lastCampsegTaskId = "";//记录最新的活动或任务ID值

	// 8.7
	public static Long getIfLongIsNull(Long longNum) {
		if (longNum == null) {
			return Long.valueOf(0);
		} else {
			return longNum;
		}
	}

	public static String getNotNullString(String str) {
		if (StringUtil.isEmpty(str)) {
			return "--";
		}
		return str;
	}

	public static String getIfStringIsNull(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * 得到计算sql结果大小
	 *
	 * @param tables
	 * @param condition
	 * @param tail
	 * @return
	 */
	public static String getCountTotalSql(String table, String strPrimaryKey, String condition, String tail) {
		if (table == null) {
			return "";
		}
		String sql = "select count(" + strPrimaryKey + ") from ";
		sql += table + "    ";
		if (condition != null && !condition.equals("")) {
			sql += "where " + condition;
		}
		if (tail != null && !tail.equals("")) {
			sql += tail;
		}
		return sql;
	}

	/**
	 * 根据一个select 语句，得到该select语句计算总数的sql
	 *
	 * @param sql
	 *            String
	 * @param primaryKey
	 *            TODO
	 * @return String
	 */
	public static String getCountTotalBySQL(String sql, String primaryKey) {
		StringBuffer countSQL = new StringBuffer();
		if (StringUtil.isEmpty(primaryKey)) {
			primaryKey = "*";
		}
		countSQL.append("select count(" + primaryKey + ") from ( ");
		countSQL.append(sql);
		countSQL.append(" ) tt");
		return countSQL.toString();
	}

	/**
	 * 把传入的毫秒值转换为 yyyymmddhhmmss 格式的字符串
	 *
	 * @param longMills
	 * @return
	 */
	public synchronized static String convertLongMillsToYYYYMMDDHHMMSS2(long longMills) {
		Calendar caldTmp = Calendar.getInstance();
		if (longMills > 0) {
			caldTmp.setTimeInMillis(longMills);
		} else {
			caldTmp.setTimeInMillis(System.currentTimeMillis());
		}
		StringBuffer res = new StringBuffer().append(caldTmp.get(Calendar.YEAR));
		String tmpStr = String.valueOf(caldTmp.get(Calendar.MONTH) + 1);
		tmpStr = (tmpStr.length() < 2) ? "0" + tmpStr : tmpStr;
		res.append(tmpStr);
		tmpStr = String.valueOf(caldTmp.get(Calendar.DAY_OF_MONTH));
		tmpStr = (tmpStr.length() < 2) ? "0" + tmpStr : tmpStr;
		res.append(tmpStr);
		res.append(caldTmp.get(Calendar.HOUR_OF_DAY));
		tmpStr = String.valueOf(caldTmp.get(Calendar.MINUTE));
		tmpStr = (tmpStr.length() < 2) ? "0" + tmpStr : tmpStr;
		res.append(tmpStr);
		tmpStr = String.valueOf(caldTmp.get(Calendar.SECOND));
		tmpStr = (tmpStr.length() < 2) ? "0" + tmpStr : tmpStr;
		res.append(tmpStr);
		return res.toString();
	}

	/**
	 *
	 * @param sql
	 * @param column
	 * @param strPrimaryKey
	 * @param curpage
	 * @param pagesize
	 * @return
	 */
	public static String getPagedSql(String sql, String column, String strPrimaryKey, int curpage, int pagesize)
			throws Exception {
		String strDBType = getDBType();
		StringBuffer buffer = null;
		buffer = new StringBuffer();
		if ("ORACLE".equalsIgnoreCase(strDBType)) {
			buffer.append("select * from ( ");
			buffer.append("select ").append(column).append(" rownum as my_rownum from( ");
			buffer.append(sql).append(") ");
			int pageAll = pagesize * curpage + pagesize;
			buffer.append((new StringBuilder()).append("where rownum <= ").append(pageAll).append(") a ").toString());
			buffer.append((new StringBuilder()).append("where a.my_rownum > ").append(pagesize * curpage).toString());
		} else if ("DB2".equalsIgnoreCase(strDBType)) {
			buffer.append("select * from ( ");
			buffer.append("select ")
					.append(column)
					.append((new StringBuilder()).append("  rownumber() over (order by ").append(strPrimaryKey)
							.append(") as my_rownum from( ").toString());
			buffer.append(sql).append(") as temp ");
			buffer.append((new StringBuilder()).append("fetch first ").append(pagesize * curpage + pagesize)
					.append(" rows only) as a ").toString());
			buffer.append((new StringBuilder()).append("where a.my_rownum > ").append(pagesize * curpage).toString());
		} else if ("TERA".equalsIgnoreCase(strDBType)) {
			buffer.append(sql);
			int orderByIndex = buffer.toString().toLowerCase().lastIndexOf("order by");
			if (orderByIndex > 0) {
				String orderBy = buffer.substring(orderByIndex);
				buffer.insert(orderByIndex, " QUALIFY row_number() OVER( ");
				buffer.append(" ) ");
				buffer.append((new StringBuilder()).append(" between ").append(pagesize * curpage).append(" and ")
						.append(pagesize * curpage + pagesize).toString());
				buffer.append(orderBy);
			} else {
				buffer.append((new StringBuilder()).append(" QUALIFY sum(1) over (rows unbounded preceding) between ")
						.append(pagesize * curpage).append(" and ").append(pagesize * curpage + pagesize).toString());
			}
		} else if ("mysql".equalsIgnoreCase(strDBType)) {
			String columnTemp = column.substring(0, column.length() - 1);
			buffer.append("SELECT ").append(columnTemp).append("  FROM ( ");
			buffer.append(sql).append(" )  AS tempTable ORDER BY ").append(strPrimaryKey);
			boolean a = strPrimaryKey.contains("desc");
			if (!strPrimaryKey.contains("DESC") && !strPrimaryKey.contains("desc")) {
				buffer.append(" DESC");
			}
			buffer.append(" limit ").append(pagesize * curpage).append(",").append(pagesize * curpage + pagesize);
		}
		return buffer.toString();
	}

	/**
	 * 根据字符串初始化一个Long对象，如果字符串空则返回null对象
	 *
	 * @param str
	 * @return
	 */
	public static Long getLong(String str) {
		if (str == null) {
			return null;
		} else if (str.trim().equals("")) {
			return null;
		} else {
			return Long.valueOf(str.trim());
		}
	}

	public static Short getShort(String str) {
		if (str == null) {
			return null;
		} else {
			return Short.valueOf(str);
		}
	}

	/**
	 * 根据字符串初始化一个Date对象，如果Date空则返回null对象
	 *
	 * @param d
	 * @return
	 */
	public static Date getDate(Date d) {
		if (d == null) {
			return null;
		} else {
			return d;
		}

	}

	/**
	 * 根据字符串初始化一个Double对象，如果字符串空则返回null对象
	 *
	 * @param str
	 * @return
	 */
	public static Double getDouble(String str) {
		if (str == null) {
			return null;
		} else if (str.trim().equals("")) {
			return null;
		} else {
			return Double.valueOf(str);
		}
	}

	/**
	 * 根据年月 获得一个日期实�?
	 *
	 * @param str
	 * @return
	 */
	public static Float getFloat(String str) {
		if (str == null) {
			return null;
		} else {
			return Float.valueOf(str);
		}

	}

	public static Date makeDate(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		return cal.getTime();
	}

	/**
	 * 初始化vml图的数据时，使用此方法处理属性为null的情�?
	 *
	 * @param o
	 * @return
	 */
	public static double getDataArrayValue(Object o) {
		if (o == null) {
			return 0;// Double.MIN_VALUE;
		}
		double value = 0;// Double.MIN_VALUE;
		try {
			value = Double.valueOf(o.toString()).doubleValue();
		} catch (Exception e) {

		}
		return value;
	}

	public static String getLocalizedPhoneNumber(String mobilePhone) {
		// String strDBType= getDBType();
		String sql = mobilePhone + " ";

		return sql;
	}

	public static String getDBType() {
		String strDBType = Configure.getInstance().getProperty("MPM_DBTYPE");
		return strDBType.toUpperCase();
	}

	// 反回上一个月，格式为200507
	public static String getMonth(String act) {
		FastDateFormat dFormat = FastDateFormat.getInstance("yyyy-MM");
		String currentDate = dFormat.format(new java.util.Date());
		String year = currentDate.split("-")[0];
		String month = currentDate.split("-")[1];
		if ("curr".equalsIgnoreCase(act)) {
			return year + month;
		}
		if ("pre".equalsIgnoreCase(act)) {
			if (Integer.parseInt(month) == 1) {
				year = String.valueOf(Integer.parseInt(year) - 1);
				month = "12";
			} else {
				month = String.valueOf(Integer.parseInt(month) - 1);
			}
		}

		if ("next".equalsIgnoreCase(act)) {
			if (Integer.parseInt(month) == 12) {
				year = String.valueOf(Integer.parseInt(year) + 1);
				month = "1";
			} else {
				month = String.valueOf(Integer.parseInt(month) + 1);
			}
		}
		if (month.length() == 1) {
			month = "0" + month;
		}
		return year + month;
	}

	/**
	 * 取上个月的月�?如果当前日期大于10�?则取上月,否则取上上月)
	 *
	 * @return yyyyMM
	 */
	public static String getCeilOfCurrMonth() {
		String YYYYMM_TABLE_CREATE_DAY_Str = Configure.getInstance().getProperty("YYYYMM_TABLE_CREATE_DAY");
		int YYYYMM_TABLE_CREATE_DAY_INT = 6;
		if (YYYYMM_TABLE_CREATE_DAY_Str != null && YYYYMM_TABLE_CREATE_DAY_Str.length() > 0) {
			try {
				YYYYMM_TABLE_CREATE_DAY_INT = Integer.parseInt(YYYYMM_TABLE_CREATE_DAY_Str);
			} catch (Exception e) {

			}
		}
		String today = DateUtil.getToday();
		String res = today;
		String day = today.substring(8, 10);
		int intDay = Integer.parseInt(day);
		// 如果当天是月份的10日前，取�?个月的帐�?
		if (intDay < YYYYMM_TABLE_CREATE_DAY_INT) {
			res = DateUtil.date2String(DateUtil.getOffsetDate(today, -2, "month"), DateUtil.YYYY_MM_DD);
		}
		// 如果当天是月份的10日以后，取上个月的账�?
		else {
			res = DateUtil.date2String(DateUtil.getOffsetDate(today, -1, "month"), DateUtil.YYYY_MM_DD);
		}
		res = res.substring(0, 7).replaceAll("-", "");
		return res;
	}

	/**
	 * 取上个月的月�?如果当前日期大于10�?则取上月,否则取上上月)
	 *
	 * @param date
	 *            String yyyy-MM-dd
	 * @return yyyyMM
	 */
	public static String getCeilOfMonthByDate(String date) {
		String YYYYMM_TABLE_CREATE_DAY_Str = Configure.getInstance().getProperty("YYYYMM_TABLE_CREATE_DAY");
		int YYYYMM_TABLE_CREATE_DAY_INT = 6;
		if (YYYYMM_TABLE_CREATE_DAY_Str != null && YYYYMM_TABLE_CREATE_DAY_Str.length() > 0) {
			try {
				YYYYMM_TABLE_CREATE_DAY_INT = Integer.parseInt(YYYYMM_TABLE_CREATE_DAY_Str);
			} catch (Exception e) {

			}
		}
		// String today = DateUtil.getToday();
		String res = date;
		String day = date.substring(8, 10);
		int intDay = Integer.parseInt(day);
		// 如果当天是月份的10日前，取�?个月的帐�?
		if (intDay < YYYYMM_TABLE_CREATE_DAY_INT) {
			res = DateUtil.date2String(DateUtil.getOffsetDate(date, -2, "month"), DateUtil.YYYY_MM_DD);
		}
		// 如果当天是月份的10日以后，取上个月的账�?
		else {
			res = DateUtil.date2String(DateUtil.getOffsetDate(date, -1, "month"), DateUtil.YYYY_MM_DD);
		}
		res = res.substring(0, 7).replaceAll("-", "");
		return res;
	}

	public static void download(String strFileName, HttpServletResponse response) {
		try {
			// String dst_fname = URLEncoder.encode(strFileName,"UTF-8");
			String arrFileName[] = StringUtils.split(strFileName, File.separator);
			String dst_fname = arrFileName[arrFileName.length - 1];
			//			 response.setContentType("application/octet-stream; charset=iso-8859-1");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=\"" + dst_fname + "\"");

			//			out = response.getWriter();
			//			fis = new FileInputStream(strFileName);
			//			response.setContentLength(fis.available());
			//			while ((byteRead = fis.read()) != -1) {
			//				out.write(byteRead);
			//			}
			//			out.flush();
			//			if (fis != null) {
			//				fis.close();
			//			}
			File file = new File(strFileName);
			if (!file.exists()) {
				//response.sendError(404, "File not found!");
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("下载失败：文件未找到！！！");
				return;
			}
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();

			file.delete();

		} catch (Exception e) {
			log.debug(e);
		}

	}

	public static String getSqlDateTime(String dateTime) throws Exception {
		return getSql2Date(dateTime, "yyyy-MM-dd");

	}

	public static String getSql2Date(String strDateStr, String splitStr) // NOPMD
			throws Exception {
		String strDBType = getDBType();
		return getSql2Date(strDBType, strDateStr, splitStr);
	}

	public static String getSqlDateTime() throws Exception {
		return getSql2DateTimeNow();
	}

	public static String getSql2DateTimeNow() throws MpmException {
		String strType = getDBType();
		String strRet = "";
		if (strType.equalsIgnoreCase("MYSQL")) {
			strRet = "now()";
		} else if (strType.equalsIgnoreCase("ORACLE")) {
			strRet = "sysdate";
		} else if (strType.equalsIgnoreCase("ACESS")) {
			strRet = "date()+time()";
		} else if (strType.equalsIgnoreCase("SQLSERVER")) {
			strRet = "getdate()";
		} else if (strType.equalsIgnoreCase("POSTGRESQL")) {
			strRet = "current_timestamp";
		} else if (strType.equalsIgnoreCase("DB2")) {
			strRet = "current timestamp";
		} else if (strType.equalsIgnoreCase("TERA")) {
			strRet = "cast((date (format 'yyyy-mm-dd' )) as char(10)) ||' '|| time";
		} else if (strType.equalsIgnoreCase("SYBASE")) {
			strRet = "getdate()";
		} else {
			throw new MpmException("can't get the current date of the function definition");
		}
		return strRet;
	}

	public static void printArray(Object[] arrs, String objName) {
		int len = arrs.length;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			sb.append(objName + "[" + i + "]=" + arrs[i] + ", ");
		}
		log.debug(sb.toString());
	}

	public static void printDouArray(double[] arrs, String objName) {
		int len = arrs.length;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			sb.append(objName + "[" + i + "]=" + arrs[i] + ", ");
		}
		log.debug(sb.toString());
	}

	public static String mpmDiyFormat(double yValue) {
		String tmpStr = NumberUtil.formatNumber(Double.valueOf(yValue), null, 2, 0);
		int pos = tmpStr.indexOf(".");
		if (pos > 0) {
			String tmpStr2 = tmpStr.substring(pos + 1);
			if (Integer.parseInt(tmpStr2) == 0) {
				tmpStr = tmpStr.substring(0, pos);
			}
		}
		return tmpStr;
	}

	/**
	 * 取根据模板表创建一个新表的SQL（会在sql后面增加表空间指定）
	 *
	 * @param newtable
	 * @param templettable
	 * @return
	 * @throws Exception
	 */
	public static String getCreateAsTableSql(String newtable, String templettable) throws Exception {
		String tableSpace = Configure.getInstance().getProperty("MPM_TABLESPACE");
		String sql = getCreateAsTableSql(newtable, templettable, tableSpace);
		// added by wwl 2007-3-23 辽宁需要指定建表的表空间，因为连接用户对默认的表空间没有权�?
		// String province = Configure.getInstance().getProperty("PROVINCE");
		// if (province != null && province.equalsIgnoreCase("liaoning")) {
		// sql = StringFunc.replace2(sql, newtable, newtable +
		// " tablespace tbs_dm ");
		// }
		return sql;
	}

	public static String getCreateAsTableSqlNoTbs(String newtable, String templettable) throws Exception {
		String ss = "";
		String strDBType = getDBType();
		if ("ORACLE".equalsIgnoreCase(strDBType) || "POSTGRESQL".equalsIgnoreCase(strDBType)) {
			ss = (new StringBuilder()).append("create table ").append(newtable).append(" as select * from ")
					.append(templettable).append(" where 1=2").toString();
		} else if ("DB2".equalsIgnoreCase(strDBType)) {
			ss = (new StringBuilder()).append("create table ").append(newtable).append(" like ").append(templettable)
					.toString();
		} else if ("TERA".equalsIgnoreCase(strDBType)) {
			ss = (new StringBuilder()).append("create table ").append(newtable).append(" as ").append(templettable)
					.append(" with no data").toString();
		}
		return ss;
	}

	public static String getCreateAsTableSql(String newtable, String templettable, String tableSpace) throws Exception {
		String ss = getCreateAsTableSql(newtable, templettable);
		if (tableSpace == null || tableSpace.length() < 1) {
			return ss;
		}
		String strDBType = getDBType();
		if ("ORACLE".equalsIgnoreCase(strDBType) || "POSTGRESQL".equalsIgnoreCase(strDBType)) {
			ss = ss.replaceAll(newtable,
					(new StringBuilder()).append(newtable).append(" tablespace ").append(tableSpace).toString());
		} else if ("DB2".equalsIgnoreCase(strDBType)) {
			ss = (new StringBuilder()).append(ss).append(" in ").append(tableSpace).toString();
		}
		return ss;
	}

	public static String getCreateTableInTableSpaceSql(String tableDDLSql, String tableSpace) // NOPMD by Administrator on 11-8-26 8:56
			throws Exception {
		if (tableSpace == null || tableSpace.length() < 1) {
			return tableDDLSql;
		}
		String strDBType = getDBType();
		if ("ORACLE".equalsIgnoreCase(strDBType) || "POSTGRESQL".equalsIgnoreCase(strDBType)) {
			tableDDLSql = (new StringBuilder()).append(tableDDLSql).append(" tablespace ").append(tableSpace)
					.toString();
		} else if ("DB2".equalsIgnoreCase(strDBType)) {
			tableDDLSql = (new StringBuilder()).append(tableDDLSql).append(" in ").append(tableSpace).toString();
		}
		return tableDDLSql;
	}

	/**
	 * 取创建一个表的SQL（会在sql后面增加表空间指定）
	 *
	 * @param tableDDLSql
	 * @return
	 * @throws Exception
	 */
	public static String getCreateTableSql(String tableDDLSql) throws Exception {
		String tableSpace = Configure.getInstance().getProperty("MPM_TABLESPACE");
		String sql = getCreateTableInTableSpaceSql(tableDDLSql, tableSpace);
		return sql;
	}

	/**
	 * 取创建一个索引的SQL（会在sql后面增加表空间指定）
	 *
	 * @param indexDDLSql
	 * @return
	 * @throws Exception
	 */
	public static String getCreateIndexSql(String indexDDLSql) throws Exception {
		String tableSpace = Configure.getInstance().getProperty("MPM_INDEX_TABLESPACE");
		String sql = getCreateIndexInTableSpaceSql(indexDDLSql, tableSpace);
		return sql;
	}

	public static String getCreateIndexInTableSpaceSql(String createIndexSql, String tableSpace) // NOPMD by Administrator on 11-8-26 8:56
			throws Exception {
		if (tableSpace == null || tableSpace.length() < 1) {
			return createIndexSql;
		}
		String strDBType = getDBType();
		if ("ORACLE".equalsIgnoreCase(strDBType)) {
			createIndexSql = (new StringBuilder()).append(createIndexSql).append(" using index tablespace ")
					.append(tableSpace).toString();
		} else if (!"DB2".equalsIgnoreCase(strDBType)) {
			;
		}
		return createIndexSql;
	}

	/**
	 * 转换Unicode字符到GB字符
	 *
	 * @param strIn
	 * @return
	 */
	public static String unicodeToGB(String strIn) {

		String strOut = null;
		if (StringUtil.isEmpty(strIn)) {
			return strIn;
		}
		try {
			// MPM_CHARSETENCODING
			String strMpmCharsetEncoding = Configure.getInstance().getProperty("MPM_CHARSETENCODING").toUpperCase();
			if (strMpmCharsetEncoding.trim().equalsIgnoreCase("GBK") || StringUtil.isEmpty(strMpmCharsetEncoding)) {
				strOut = strIn;
			} else {
				byte[] b = strIn.getBytes("GBK");
				strOut = new String(b, strMpmCharsetEncoding);// ISO8859_1
			}
		} catch (Exception e) {
			strOut = strIn;
		}
		return strOut;
	}

	/**
	 * 转换GB代码到Unicode
	 *
	 * @param strIn
	 * @return
	 */
	public static String GBToUnicode(String strIn) {
		String strOut = null;
		if (StringUtil.isEmpty(strIn)) {
			return strIn;
		}

		try {
			// MPM_CHARSETENCODING
			String strMpmCharsetEncoding = Configure.getInstance().getProperty("MPM_CHARSETENCODING").toUpperCase();
			if (strMpmCharsetEncoding.trim().equalsIgnoreCase("GBK") || StringUtil.isEmpty(strMpmCharsetEncoding)) {
				strOut = strIn;
			} else {
				byte[] b = strIn.getBytes(strMpmCharsetEncoding);// ISO8859_1
				strOut = new String(b, "GBK");
			}
		} catch (Exception e) {
			strOut = strIn;
		}
		return strOut;
	}

	/**
	 * 把sql中的'转换�?'
	 *
	 * @param sql
	 * @return
	 */
	public static String formatSql(String sql) {
		if (StringUtil.isEmpty(sql)) {
			return "";
		}
		sql = StringFunc.replace2(sql, "'", "''");
		return sql;
	}

	/**
	 * 取营销管理子系统中存储导出接口文件的目�?
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getCampExportSendPath() throws Exception {
		// 判断导出接口文件目录是否存在
		String filepath = Configure.getInstance().getProperty("SYS_COMMON_UPLOAD_PATH");
		if (!filepath.endsWith(File.separator)) {
			filepath += File.separator;
		}
		filepath += MpmCONST.MPM_STORE_SUB_PATH + File.separator + MpmCONST.MPM_STORE_SEND_PATH;
		java.io.File rootFolder = new java.io.File(filepath);
		if (!rootFolder.exists()) {
			rootFolder.mkdirs();
		}
		return filepath;
	}

	/**
	 * 取营销管理子系统中存储导出接口文件检验文件的目录
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getCampExportChkPath() throws Exception {
		// 判断导出接口文件目录是否存在
		String filepath = Configure.getInstance().getProperty("SYS_COMMON_UPLOAD_PATH");
		if (!filepath.endsWith(File.separator)) {
			filepath += File.separator;
		}
		filepath += MpmCONST.MPM_STORE_SUB_PATH + File.separator + MpmCONST.MPM_STORE_CHK_PATH;
		java.io.File rootFolder = new java.io.File(filepath);
		if (!rootFolder.exists()) {
			rootFolder.mkdirs();
		}
		return filepath;
	}

	/**
	 * 判断某个数组中是否包含某个指定的�?
	 *
	 * @param arr
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static boolean containStr(String[] arr, String str) throws Exception {
		boolean res = false;
		if (ArrayUtils.isEmpty(arr)) {
			return res;
		}
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].trim().equals(str)) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**
	 * 获取一个字符串真正的字节长�? String.length()只把1个汉字作�?个字节长度来统计，其�?个汉字占2个字节长�?
	 *
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static int getStrRealLength(String str) throws Exception {
		if (StringUtil.isEmpty(str)) {
			return 0;
		}
		// str = str.trim();
		char chr;
		int len = 0;
		for (int i = 0; i < str.length(); i++) {
			chr = str.charAt(i);
			if (chr <= '~') {
				len++;
			} else {
				len += 2;
			}
		}
		return len;
	}

	/**
	 * 取当前分公司/部门的上N级分公司/部门编号
	 *
	 * @param currDeptId
	 * @return
	 * @throws Exception
	 */
	public static String getDeptTopNDeptId(String currDeptId, int topN) throws Exception {
		IMpmUserPrivilegeService mpmUserPrivilegeService = null;

		try {
			mpmUserPrivilegeService = (IMpmUserPrivilegeService) SystemServiceLocator.getInstance().getService(
					MpmCONST.MPM_USER_PRIVILEGE_SERVICE);
		} catch (Exception e) {
			log.error("", e);
		}
		IUserCompany dept = null;
		int index = 0;
		String deptId = null;
		do {
			dept = mpmUserPrivilegeService.getUserCompanyById(currDeptId);
			if (dept != null && index < topN) {
				currDeptId = String.valueOf(dept.getParentid());
				index++;
			} else {
				deptId = currDeptId;
				break;
			}
		} while (dept != null && index <= topN);
		return deptId;
	}

	/**
	 * 判断审批触发条件的设定值与实际值是否符合操作条�?
	 *
	 * @param realValue
	 * @param operator
	 * @param condValue
	 * @return
	 * @throws Exception
	 */
	public static boolean matchApproveTriggerCondValue(double realValue, String operator, double condValue)
			throws Exception {
		boolean match = false;
		if (operator.equals(">") && realValue > condValue) {
			match = true;
		} else if (operator.equals(">=") && realValue >= condValue) {
			match = true;
		} else if (operator.equals("<") && realValue < condValue) {
			match = true;
		} else if (operator.equals("<=") && realValue <= condValue) {
			match = true;
		} else if (operator.equals("=") && realValue == condValue) {
			match = true;
		}
		return match;
	}

	/**
	 * 根据客户群类型获取客户群创建步骤的流程编�?
	 *
	 * @param custGroupType
	 * @return
	 * @throws Exception
	 */
	public static String getFlowIdByCustGroupType(short custGroupType) throws Exception {
		String flowId = MpmCONST.MPM_FLOW_ADD_CUST_GROUP_DEFINE;
		if (custGroupType == MpmCONST.MPM_CUST_GROUP_TYPE_LIST_FROM_DEFINE) {
			flowId = MpmCONST.MPM_FLOW_ADD_CUST_GROUP_FROM_DEFINE;
		} else if (custGroupType == MpmCONST.MPM_CUST_GROUP_TYPE_LIST_FROM_FILE) {
			flowId = MpmCONST.MPM_FLOW_ADD_CUST_GROUP_FROM_FILE;
		} else if (custGroupType == MpmCONST.MPM_CUST_GROUP_TYPE_LIST_FROM_OPERATE) {
			flowId = MpmCONST.MPM_FLOW_ADD_CUST_GROUP_FROM_OPERATE;
		} else if (custGroupType == MpmCONST.MPM_CUST_GROUP_TYPE_LIST_FROM_EXECFEEDBACK) {
			flowId = MpmCONST.MPM_FLOW_ADD_CUST_GROUP_FROM_EXECFEEDBACK;
		}
		return flowId;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public static String writeExcel(String[] srcFileName, String destFileName, String dateF) {
		HSSFWorkbook wbwrite = null, wbread = null;
		HSSFSheet wSheet = null, rSheet = null;
		HSSFRow wRow = null, rRow = null;
		HSSFCellStyle style = null, style1 = null;
		HSSFFont font = null, font1 = null;
		POIFSFileSystem fsInr = null, fsInw = null;
		FileInputStream fisr = null, fisw = null;
		double[][] counts = new double[1][29];
		int count = 2;
		double xjsum = 0;
		try {
			fisw = new FileInputStream(destFileName);
			if (fisw == null) {
				log.debug(MpmLocaleUtil.getMessage("mcd.java.gjwj") + destFileName
						+ MpmLocaleUtil.getMessage("mcd.java.hqFileInpu"));
				return null;
			}
			fsInw = new POIFSFileSystem(fisw);
			if (fsInw == null) {
				log.debug(MpmLocaleUtil.getMessage("mcd.java.gjwj") + destFileName
						+ MpmLocaleUtil.getMessage("mcd.java.hqPOIFSFil"));
				return null;
			}
			wbwrite = new HSSFWorkbook(fsInw);
			fisw.close();
			wSheet = wbwrite.getSheetAt(0);
			font = wbwrite.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			for (int i = 0; i < srcFileName.length; i++) {
				if (StringUtil.isEmpty(srcFileName[i])) {
					continue;
				}
				log.debug("=======================srcFileName=" + srcFileName[i]);
				fisr = new FileInputStream(srcFileName[i]);
				if (fisr == null) {
					log.debug(MpmLocaleUtil.getMessage("mcd.java.gjwj") + srcFileName[i]
							+ MpmLocaleUtil.getMessage("mcd.java.hqFileInpu"));
					return null;
				}
				fsInr = new POIFSFileSystem(fisr);
				if (fsInr == null) {
					log.debug(MpmLocaleUtil.getMessage("mcd.java.gjwj") + srcFileName[i]
							+ MpmLocaleUtil.getMessage("mcd.java.hqPOIFSFil"));
					return null;
				}
				wbread = new HSSFWorkbook(fsInr);
				fisr.close();
				rSheet = wbread.getSheetAt(0);
				for (int a = 2; a >= 2; a++) {
					rRow = rSheet.getRow(a);
					if (rRow == null) {
						break;
					}
					if (rRow.getCell((short) 2) == null || rRow.getCell((short) 2).getStringCellValue() == null
							|| rRow.getCell((short) 2).getStringCellValue().trim().length() <= 0) {
						break;
					}
					wRow = wSheet.getRow(count);
					if (wRow == null) {
						wRow = wSheet.createRow(count);
					}
					if (wRow.getCell((short) 0) == null) {
						wRow.createCell((short) 0);
					}
					if (rRow.getCell((short) 0) != null
							&& rRow.getCell((short) 0).getStringCellValue().trim().length() > 0) {
						wRow.getCell((short) 0).setCellStyle(wSheet.getRow(1).getCell((short) 0).getCellStyle());
						wRow.getCell((short) 0).setCellType(HSSFCell.CELL_TYPE_STRING);
						wRow.getCell((short) 0).setCellValue(rRow.getCell((short) 0).getStringCellValue());
					} else {
					}
					if (wRow.getCell((short) 1) == null) {
						wRow.createCell((short) 1);
					}
					if (rRow.getCell((short) 1) != null
							&& rRow.getCell((short) 1).getStringCellValue().trim().length() > 0) {
						wRow.getCell((short) 1).setCellStyle(wSheet.getRow(1).getCell((short) 1).getCellStyle());
						wRow.getCell((short) 1).setCellType(HSSFCell.CELL_TYPE_STRING);
						wRow.getCell((short) 1).setCellValue(rRow.getCell((short) 1).getStringCellValue());
					} else {
					}
					if (wRow.getCell((short) 2) == null) {
						wRow.createCell((short) 2);
					}
					wRow.getCell((short) 2).setCellStyle(wSheet.getRow(1).getCell((short) 2).getCellStyle());
					wRow.getCell((short) 2).setCellType(HSSFCell.CELL_TYPE_STRING);
					rRow.getCell((short) 2).setCellType(HSSFCell.CELL_TYPE_STRING);
					wRow.getCell((short) 2).setCellValue(rRow.getCell((short) 2).getStringCellValue());
					if (wRow.getCell((short) 3) == null) {// 放号
						wRow.createCell((short) 3);
					}
					wRow.getCell((short) 3).setCellStyle(wSheet.getRow(1).getCell((short) 3).getCellStyle());
					style = wRow.getCell((short) 3).getCellStyle();
					style.setFont(font);

					wRow.getCell((short) 3).setCellValue(rRow.getCell((short) 3).getNumericCellValue());
					counts[0][3] = counts[0][3] + rRow.getCell((short) 3).getNumericCellValue();
					if (wRow.getCell((short) 4) == null) {
						wRow.createCell((short) 4);
					}
					wRow.getCell((short) 4).setCellStyle(wSheet.getRow(1).getCell((short) 4).getCellStyle());
					wRow.getCell((short) 4).setCellValue(rRow.getCell((short) 4).getNumericCellValue());
					counts[0][4] = counts[0][4] + rRow.getCell((short) 4).getNumericCellValue();
					if (wRow.getCell((short) 5) == null) {
						wRow.createCell((short) 5);
					}
					wRow.getCell((short) 5).setCellStyle(wSheet.getRow(1).getCell((short) 5).getCellStyle());
					wRow.getCell((short) 5).setCellValue(rRow.getCell((short) 5).getNumericCellValue());
					counts[0][5] = counts[0][5] + rRow.getCell((short) 5).getNumericCellValue();
					if (wRow.getCell((short) 6) == null) {
						wRow.createCell((short) 6);
					}
					wRow.getCell((short) 6).setCellStyle(wSheet.getRow(1).getCell((short) 6).getCellStyle());
					wRow.getCell((short) 6).setCellValue(rRow.getCell((short) 6).getNumericCellValue());
					counts[0][6] = counts[0][6] + rRow.getCell((short) 6).getNumericCellValue();
					if (wRow.getCell((short) 7) == null) {
						wRow.createCell((short) 7);
					}
					wRow.getCell((short) 7).setCellStyle(wSheet.getRow(1).getCell((short) 7).getCellStyle());
					wRow.getCell((short) 7).setCellValue(rRow.getCell((short) 7).getNumericCellValue());
					counts[0][7] = counts[0][7] + rRow.getCell((short) 7).getNumericCellValue();
					if (wRow.getCell((short) 8) == null) {
						wRow.createCell((short) 8);
					}
					wRow.getCell((short) 8).setCellStyle(wSheet.getRow(1).getCell((short) 8).getCellStyle());
					wRow.getCell((short) 8).setCellValue(rRow.getCell((short) 8).getNumericCellValue());
					counts[0][8] = counts[0][8] + rRow.getCell((short) 8).getNumericCellValue();
					if (wRow.getCell((short) 9) == null) { // 小计
						wRow.createCell((short) 9);
					}
					xjsum = rRow.getCell((short) 3).getNumericCellValue()
							+ rRow.getCell((short) 4).getNumericCellValue()
							+ rRow.getCell((short) 5).getNumericCellValue()
							+ rRow.getCell((short) 6).getNumericCellValue()
							+ rRow.getCell((short) 7).getNumericCellValue()
							+ rRow.getCell((short) 8).getNumericCellValue();
					wRow.getCell((short) 9).setCellStyle(wSheet.getRow(1).getCell((short) 9).getCellStyle());
					wRow.getCell((short) 9).setCellValue(xjsum);
					counts[0][9] = counts[0][9] + xjsum;
					if (wRow.getCell((short) 10) == null) { // 数据业务
						wRow.createCell((short) 10);
					}
					wRow.getCell((short) 10).setCellStyle(wSheet.getRow(1).getCell((short) 10).getCellStyle());
					wRow.getCell((short) 10).setCellValue(rRow.getCell((short) 9).getNumericCellValue());
					counts[0][10] = counts[0][10] + rRow.getCell((short) 9).getNumericCellValue();
					if (wRow.getCell((short) 11) == null) {
						wRow.createCell((short) 11);
					}
					wRow.getCell((short) 11).setCellStyle(wSheet.getRow(1).getCell((short) 11).getCellStyle());
					wRow.getCell((short) 11).setCellValue(rRow.getCell((short) 10).getNumericCellValue());
					counts[0][11] = counts[0][11] + rRow.getCell((short) 10).getNumericCellValue();
					if (wRow.getCell((short) 12) == null) {
						wRow.createCell((short) 12);
					}
					wRow.getCell((short) 12).setCellStyle(wSheet.getRow(1).getCell((short) 12).getCellStyle());
					wRow.getCell((short) 12).setCellValue(rRow.getCell((short) 11).getNumericCellValue());
					counts[0][12] = counts[0][12] + rRow.getCell((short) 11).getNumericCellValue();
					if (wRow.getCell((short) 13) == null) {
						wRow.createCell((short) 13);
					}
					wRow.getCell((short) 13).setCellStyle(wSheet.getRow(1).getCell((short) 13).getCellStyle());
					wRow.getCell((short) 13).setCellValue(rRow.getCell((short) 12).getNumericCellValue());
					counts[0][13] = counts[0][13] + rRow.getCell((short) 12).getNumericCellValue();
					if (wRow.getCell((short) 14) == null) {
						wRow.createCell((short) 14);
					}
					wRow.getCell((short) 14).setCellStyle(wSheet.getRow(1).getCell((short) 14).getCellStyle());
					wRow.getCell((short) 14).setCellValue(rRow.getCell((short) 13).getNumericCellValue());
					counts[0][14] = counts[0][14] + rRow.getCell((short) 13).getNumericCellValue();
					if (wRow.getCell((short) 15) == null) {// 小计
						wRow.createCell((short) 15);
					}
					xjsum = rRow.getCell((short) 9).getNumericCellValue()
							+ rRow.getCell((short) 10).getNumericCellValue()
							+ rRow.getCell((short) 11).getNumericCellValue()
							+ rRow.getCell((short) 12).getNumericCellValue()
							+ rRow.getCell((short) 13).getNumericCellValue();
					wRow.getCell((short) 15).setCellStyle(wSheet.getRow(1).getCell((short) 15).getCellStyle());
					wRow.getCell((short) 15).setCellValue(xjsum);
					counts[0][15] = counts[0][15] + xjsum;
					if (wRow.getCell((short) 16) == null) {// 充值送礼
						wRow.createCell((short) 16);
					}
					wRow.getCell((short) 16).setCellStyle(wSheet.getRow(1).getCell((short) 16).getCellStyle());
					wRow.getCell((short) 16).setCellValue(rRow.getCell((short) 14).getNumericCellValue());
					counts[0][16] = counts[0][16] + rRow.getCell((short) 14).getNumericCellValue();
					if (wRow.getCell((short) 17) == null) {
						wRow.createCell((short) 17);
					}
					wRow.getCell((short) 17).setCellStyle(wSheet.getRow(1).getCell((short) 17).getCellStyle());
					wRow.getCell((short) 17).setCellValue(rRow.getCell((short) 15).getNumericCellValue());
					counts[0][17] = counts[0][17] + rRow.getCell((short) 15).getNumericCellValue();
					if (wRow.getCell((short) 18) == null) {
						wRow.createCell((short) 18);
					}
					wRow.getCell((short) 18).setCellStyle(wSheet.getRow(1).getCell((short) 18).getCellStyle());
					wRow.getCell((short) 18).setCellValue(rRow.getCell((short) 16).getNumericCellValue());
					counts[0][18] = counts[0][18] + rRow.getCell((short) 16).getNumericCellValue();
					if (wRow.getCell((short) 19) == null) {
						wRow.createCell((short) 19);
					}
					wRow.getCell((short) 19).setCellStyle(wSheet.getRow(1).getCell((short) 19).getCellStyle());
					wRow.getCell((short) 19).setCellValue(rRow.getCell((short) 17).getNumericCellValue());
					counts[0][19] = counts[0][19] + rRow.getCell((short) 17).getNumericCellValue();
					if (wRow.getCell((short) 20) == null) {
						wRow.createCell((short) 20);
					}
					wRow.getCell((short) 20).setCellStyle(wSheet.getRow(1).getCell((short) 20).getCellStyle());
					wRow.getCell((short) 20).setCellValue(rRow.getCell((short) 18).getNumericCellValue());
					counts[0][20] = counts[0][20] + rRow.getCell((short) 18).getNumericCellValue();
					if (wRow.getCell((short) 21) == null) { // 小计
						wRow.createCell((short) 21);
					}
					xjsum = rRow.getCell((short) 14).getNumericCellValue()
							+ rRow.getCell((short) 15).getNumericCellValue()
							+ rRow.getCell((short) 16).getNumericCellValue()
							+ rRow.getCell((short) 17).getNumericCellValue()
							+ rRow.getCell((short) 18).getNumericCellValue();
					wRow.getCell((short) 21).setCellStyle(wSheet.getRow(1).getCell((short) 21).getCellStyle());
					wRow.getCell((short) 21).setCellValue(xjsum);
					counts[0][21] = counts[0][21] + xjsum;
					if (wRow.getCell((short) 22) == null) {// 终端销售总数
						wRow.createCell((short) 22);
					}
					wRow.getCell((short) 22).setCellStyle(wSheet.getRow(1).getCell((short) 22).getCellStyle());
					wRow.getCell((short) 22).setCellValue(rRow.getCell((short) 19).getNumericCellValue());
					counts[0][22] = counts[0][22] + rRow.getCell((short) 19).getNumericCellValue();
					if (wRow.getCell((short) 23) == null) {// 其它
						wRow.createCell((short) 23);
					}
					wRow.getCell((short) 23).setCellStyle(wSheet.getRow(1).getCell((short) 23).getCellStyle());
					wRow.getCell((short) 23).setCellValue(rRow.getCell((short) 20).getNumericCellValue());
					counts[0][23] = counts[0][23] + rRow.getCell((short) 20).getNumericCellValue();
					if (wRow.getCell((short) 24) == null) {
						wRow.createCell((short) 24);
					}
					wRow.getCell((short) 24).setCellStyle(wSheet.getRow(1).getCell((short) 24).getCellStyle());
					wRow.getCell((short) 24).setCellValue(rRow.getCell((short) 21).getNumericCellValue());
					counts[0][24] = counts[0][24] + rRow.getCell((short) 21).getNumericCellValue();
					if (wRow.getCell((short) 25) == null) {
						wRow.createCell((short) 25);
					}
					wRow.getCell((short) 25).setCellStyle(wSheet.getRow(1).getCell((short) 25).getCellStyle());
					wRow.getCell((short) 25).setCellValue(rRow.getCell((short) 22).getNumericCellValue());
					counts[0][25] = counts[0][25] + rRow.getCell((short) 22).getNumericCellValue();
					if (wRow.getCell((short) 26) == null) {
						wRow.createCell((short) 26);
					}
					wRow.getCell((short) 26).setCellStyle(wSheet.getRow(1).getCell((short) 26).getCellStyle());
					wRow.getCell((short) 26).setCellValue(rRow.getCell((short) 23).getNumericCellValue());
					counts[0][26] = counts[0][26] + rRow.getCell((short) 23).getNumericCellValue();
					if (wRow.getCell((short) 27) == null) {
						wRow.createCell((short) 27);
					}
					wRow.getCell((short) 27).setCellStyle(wSheet.getRow(1).getCell((short) 27).getCellStyle());
					wRow.getCell((short) 27).setCellValue(rRow.getCell((short) 24).getNumericCellValue());
					counts[0][27] = counts[0][27] + rRow.getCell((short) 24).getNumericCellValue();
					if (wRow.getCell((short) 28) == null) {// 小计
						wRow.createCell((short) 28);
					}
					xjsum = rRow.getCell((short) 20).getNumericCellValue()
							+ rRow.getCell((short) 21).getNumericCellValue()
							+ rRow.getCell((short) 22).getNumericCellValue()
							+ rRow.getCell((short) 23).getNumericCellValue()
							+ rRow.getCell((short) 24).getNumericCellValue();
					wRow.getCell((short) 28).setCellStyle(wSheet.getRow(1).getCell((short) 28).getCellStyle());
					wRow.getCell((short) 28).setCellValue(xjsum);
					counts[0][28] = counts[0][28] + xjsum;
					count++;
				}
			}
			wRow = wSheet.getRow(count); // 合计
			if (wRow == null) {
				wRow = wSheet.createRow(count);
			}
			for (short i = 0; i < 29; i++) {
				if (wRow.getCell(i) == null) {
					wRow.createCell(i);
				}
				if (i == 0) {
					style = wRow.getCell(i).getCellStyle();
					style.setFillForegroundColor(HSSFColor.RED.index);
					style.setFillBackgroundColor(HSSFColor.RED.index);
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					style.setFont(font);
					wRow.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
					wRow.getCell(i).setCellValue(MpmLocaleUtil.getMessage("mcd.java.hj"));
					continue;
				}
				if (i <= 2) {
					continue;
				}
				style = wRow.getCell(i).getCellStyle();
				style.setFillForegroundColor(HSSFColor.ORANGE.index);
				style.setFillBackgroundColor(HSSFColor.ORANGE.index);
				style.setFont(font);
				wRow.getCell(i).setCellStyle(style);
				wRow.getCell(i).setCellValue(counts[0][i]);
			}
		} catch (FileNotFoundException ex) {
			log.error("", ex);
		} catch (IOException ex2) {
			log.error("", ex2);
		} catch (Exception e) {
			log.error("", e);
		} finally {
		}
		FileOutputStream fos = null;// 另存文件
		BufferedOutputStream bos = null;
		try {
			Calendar cd = Calendar.getInstance();
			FastDateFormat df = FastDateFormat.getInstance("yyyyMMddHHmmss");
			destFileName = destFileName.replaceAll("report_template.xls", MpmLocaleUtil.getMessage("mcd.java.fgssbhz")
					+ dateF + "_" + df.format(Calendar.getInstance().getTime()) + ".xls");
			fos = new FileOutputStream(destFileName, false);
			bos = new BufferedOutputStream(fos);
			wbwrite.write(bos);
			bos.flush();
			fos.close();
			bos.close();
		} catch (FileNotFoundException ex3) {
			log.error("", ex3);
			return null;
		} catch (IOException ex5) {
			log.error("", ex5);
			return null;
		}
		return destFileName;
	}

	/**
	 * 把传入的毫秒值转换为 yyyy-mm-dd hh:mm:ss 格式的字符串
	 *
	 * @param longMills
	 * @return
	 */
	public static String convertLongMillsToStrTime(long longMills) {
		Calendar caldTmp = Calendar.getInstance();
		caldTmp.setTimeInMillis(longMills);
		StringBuffer res = new StringBuffer().append(caldTmp.get(Calendar.YEAR)).append("-")
				.append((caldTmp.get(Calendar.MONTH) + 1)).append("-").append(caldTmp.get(Calendar.DAY_OF_MONTH))
				.append(" ").append(caldTmp.get(Calendar.HOUR_OF_DAY)).append(":").append(caldTmp.get(Calendar.MINUTE))
				.append(":").append(caldTmp.get(Calendar.SECOND));
		return res.toString();
	}

	/**
	 * 把传入的毫秒值转换为 yyyymmddhhmmss 格式的字符串
	 *
	 * @param longMills
	 * @return
	 */
	public synchronized static String convertLongMillsToYYYYMMDDHHMMSS(long longMills) {
		Calendar caldTmp = Calendar.getInstance();
		if (longMills > 0) {
			caldTmp.setTimeInMillis(longMills);
		} else {
			caldTmp.setTimeInMillis(System.currentTimeMillis());
		}
		StringBuffer res = new StringBuffer().append(caldTmp.get(Calendar.YEAR));
		String tmpStr = String.valueOf(caldTmp.get(Calendar.MONTH) + 1);
		tmpStr = (tmpStr.length() < 2) ? "0" + tmpStr : tmpStr;
		res.append(tmpStr);
		tmpStr = String.valueOf(caldTmp.get(Calendar.DAY_OF_MONTH));
		tmpStr = (tmpStr.length() < 2) ? "0" + tmpStr : tmpStr;
		res.append(tmpStr);
		res.append(caldTmp.get(Calendar.HOUR_OF_DAY));
		tmpStr = String.valueOf(caldTmp.get(Calendar.MINUTE));
		tmpStr = (tmpStr.length() < 2) ? "0" + tmpStr : tmpStr;
		res.append(tmpStr);
		tmpStr = String.valueOf(caldTmp.get(Calendar.SECOND));
		tmpStr = (tmpStr.length() < 2) ? "0" + tmpStr : tmpStr;
		res.append(tmpStr);
		tmpStr = formatNumber(caldTmp.get(Calendar.MILLISECOND), "000");
		res.append(tmpStr);
		return res.toString();
	}

	/**
	 * 获取绝对唯一的时间戳
	 * @return
	 */
	public synchronized static String convertLongMillsToYYYYMMDDHHMMSSSSS() {
		FastDateFormat df = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
		String dateStr = df.format(new Date());
		if (StringUtil.isNotEmpty(lastTabTime)) {
			while (Long.parseLong(dateStr) == Long.parseLong(lastTabTime)) {
				dateStr = df.format(new Date());
			}
		}
		lastTabTime = dateStr;
		return lastTabTime;
	}

	/**
	 * 根据指定长度获取随机字符串（yyyyMMddHHmmssSSS）
	 * @param length（指定长度）
	 * @return
	 */
	public synchronized static String getRandomTimeStr(int length) {
		FastDateFormat df = FastDateFormat.getInstance("yyyyMMddHHmmss");
		String dateStr = df.format(new Date());
		if (length > 0) {
			if (dateStr.length() < length) {
				dateStr += genFixLenNumStr(length - dateStr.length());
			} else if (dateStr.length() > length) {
				dateStr = dateStr.substring(0, length);
			}
		}
		return dateStr;
	}

	/**
	 * 根据时间戳生成活动和任务编号，默认17位，可通过省份的mpm.properties配置CAMPSEG_TASK_NO_LENGTH的值定制长度
	 * @param longMills
	 * @return YYYYMMDDHHMMSSXXX
	 */
	public synchronized static String generateCampsegAndTaskNo() {
		FastDateFormat df = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
		String dateStr = getFixLenStr(df.format(new Date()));
		if (StringUtil.isNotEmpty(lastCampsegTaskId)) {
			while (Long.parseLong(dateStr) == Long.parseLong(lastCampsegTaskId)) {
				dateStr = getFixLenStr(df.format(new Date()));
			}
		}
		lastCampsegTaskId = dateStr;
		return lastCampsegTaskId;
	}

	public static String getFixLenStr(String data) {
		String dateStr = data;
		String noLen = MpmConfigure.getInstance().getProperty("CAMPSEG_TASK_NO_LENGTH");
		if (StringUtil.isNotEmpty(noLen)) {
			int len = Integer.valueOf(noLen);
			if (data.length() < len) {
				dateStr += genFixLenNumStr(len - data.length());
			} else if (data.length() > len) {
				dateStr = data.substring(0, len);
			}
		}
		return dateStr;
	}

	/**
	 * 活动营销测试时的任务编号
	 * @return
	 */
	public synchronized static String generateTestTaskNo() {
		String dateStr = "00000000000000000";//默认17位0
		String noLen = MpmConfigure.getInstance().getProperty("CAMPSEG_TASK_NO_LENGTH");
		if (StringUtil.isNotEmpty(noLen)) {
			int len = Integer.valueOf(noLen);
			if (dateStr.length() < len) {
				for (int i = 0; i < (len - dateStr.length()); i++) {
					dateStr += "0";
				}
			} else if (dateStr.length() > len) {
				dateStr = dateStr.substring(0, len);
			}
		}
		return dateStr;
	}

	/**
	 * 生成指定长度的随机数字
	 * @param len
	 * @return
	 */
	public static String genFixLenNumStr(int len) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			int index = (int) (Math.random() * 10);
			sb.append(NUM_DATA[index]);
		}
		return sb.toString();
	}

	/**
	 * 判断字符串是不是由数字组�?
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 计算百分�?
	 *
	 * @param count
	 *            除数
	 * @param sum
	 *            被除�?
	 * @param fStr
	 *            百分比格�?0表示没有小数�?0.0表示一位小数点，依此类�?
	 * @return percentStr 百分�?�?5%
	 */
	public static String percentStr(int count, int sum, String fStr) {
		if (count == 0 || sum == 0) {
			return "0%";
		}
		DecimalFormat myformat = null;
		myformat = (DecimalFormat) NumberFormat.getPercentInstance();
		if (StringUtil.isEmpty(fStr)) {
			myformat.applyPattern("0%");
		} else {
			myformat.applyPattern(fStr); // 0表示没有小数�?0.0表示一位小数点
		}
		double rat = (double) count / (double) sum;
		return myformat.format(rat);
	}

	/**
	 * 格式化字符串应用SQL中in的搜索，自动加入单引�?'
	 *
	 * @param strInString
	 *            需要转换的字符�?
	 * @return
	 */
	public static String formatSqlInString(String strInString) {
		String strResult = strInString;
		String[] arrayStr = null;
		if (strResult != null && strResult.indexOf("'") == -1) {
			strResult = "";
			arrayStr = strInString.split("\\,");
			if (arrayStr.length > 0) {
				for (int i = 0; i < arrayStr.length; i++) {
					strResult += "'" + arrayStr[i] + "'";
					if (i != arrayStr.length - 1) {
						strResult += ",";
					}
				}

			}
		}

		return strResult;

	}

	public static String getToday(String strType) {
		FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");
		String strDateStr = sdf.format(new java.util.Date());
		if (strDateStr.indexOf("0000-00-00") >= 0) {
			return "null";
		}
		String strRet = "";
		int i = strDateStr.indexOf(" ");
		if (i > 0) {
			strDateStr = strDateStr.substring(0, i);
		}
		if (strType.equalsIgnoreCase("MYSQL")) {
			strRet = (new StringBuilder("'")).append(strDateStr).append("'").toString();
		} else if (strType.equalsIgnoreCase("DB2")) {
			strRet = (new StringBuilder("date('")).append(strDateStr).append("')").toString();
		} else if (strType.equalsIgnoreCase("ORACLE")) {
			strRet = (new StringBuilder("to_date('")).append(strDateStr).append("','YYYY-mm-dd')").toString();
		} else if (strType.equalsIgnoreCase("ACESS")) {
			strRet = (new StringBuilder("'")).append(strDateStr).append("'").toString();
		} else if (strType.equalsIgnoreCase("SQLSERVER")) {
			strRet = (new StringBuilder("convert(varchar(10), convert(datetime,'")).append(strDateStr)
					.append("'), 111)").toString();
		} else if (strType.equalsIgnoreCase("TERA")) {
			strRet = (new StringBuilder("cast('")).append(strDateStr).append("' as date FORMAT 'YYYY-MM-DD')")
					.toString();
		} else if (strType.equalsIgnoreCase("SYBASE")) {
			strRet = (new StringBuilder("cast('")).append(strDateStr).append("' as Date)").toString();
		}
		return strRet;
	}

	/**
	 * float转化为百分比
	 *
	 * @param value
	 *            �?
	 * @param fStr
	 *            百分比格�?0表示没有小数�?0.0表示一位小数点，依此类�?
	 * @return convertPercentStr 百分�?�?5%
	 */
	public static String convertPercentStr(Object obj, String fStr) {
		DecimalFormat myformat = null;
		myformat = (DecimalFormat) NumberFormat.getPercentInstance();
		if (StringUtil.isEmpty(fStr)) {
			myformat.applyPattern("0%");
		} else {
			myformat.applyPattern(fStr); // 0表示没有小数�?0.0表示一位小数点
		}
		float value = Float.parseFloat(obj.toString()) / 100;
		return myformat.format(value);
	}

	/**
	 * 传入数据库的日期类型转换为yyyy-MM-dd格式 传入的参数按照本地语言规定的数据格式；
	 *
	 * @param date
	 * @return
	 */
	public static String parseInDate(String date) {
		if (DATE_FORMAT_IS_DEFAULT) {
			return date;
		}
		String parsed = "";
		try {
			parsed = DateUtil.date2String((DATE_US_FORMATER.parse(date)), DATE_DB_FORMAT);
		} catch (Exception e) {
			log.debug("--------date parse to in error, orign date is " + date);
			parsed = date;
		}

		return parsed;
	}

	/**
	 * 将数据库中的日期类型转换为本地语言规定的数据格式； 数据库中的日期格式为yyyy-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static String parseOutDate(String date) {
		if (DATE_FORMAT_IS_DEFAULT) {
			return date;
		}
		String parsed = "";
		try {
			parsed = DATE_US_FORMATER.format(DateUtil.string2Date(date, DATE_DB_FORMAT));
		} catch (Exception e) {
			log.debug("--------date parse to out error, orign date is " + date);
			parsed = date;
		}
		return parsed;
	}

	/**
	 * 将数据库中的日期类型转换为本地语言规定的数据格式； 数据库中的日期格式为yyyy-MM
	 *
	 * @param date
	 * @return
	 */
	public static String parseOutDateYM(String date) {
		if (DATE_FORMAT_IS_DEFAULT) {
			return date;
		}
		String parsed = "";
		try {
			parsed = DATE_US_FORMATER_YM.format(DateUtil.string2Date(date, DATE_DB_FORMAT_YM));
		} catch (Exception e) {
			log.debug("--------date parse to out error, orign date is " + date);
			parsed = date;
		}
		return parsed;
	}

	/**
	 * 将数据类型转换为本地语言规定的数据格式；
	 *
	 * @param date
	 * @return
	 */
	public static String parseOutNumber(Object date) {
		if (date == null) {
			return "--";
		}
		String parsed = "";
		if (date instanceof String) {
			date = Double.valueOf(Double.parseDouble((String) date));
		}
		try {
			parsed = decimalFormat.format(date);
		} catch (Exception e) {
			log.debug("--------date parse to out error, orign date is " + date);
		}
		return parsed;
	}

	public static String getSqlRound(String str1, String str2) throws Exception {
		String strType = getDBType();
		String strRet = "";
		if (strType.equalsIgnoreCase("TERA")) {
			strRet = (new StringBuilder()).append(" cast ((").append(str1).append(") as decimal(10,").append(str2)
					.append(")) ").toString();
		} else {
			strRet = (new StringBuilder()).append(" round(").append(str1).append(",").append(str2).append(") ")
					.toString();
		}
		return strRet;
	}

	public static String getSqlNvl(String str1, String str2) throws Exception {
		String strType = getDBType();
		String strRet = "";
		if (strType.equalsIgnoreCase("DB2")) {
			strRet = (new StringBuilder()).append("value(").append(str1).append(",").append(str2).append(")")
					.toString();
		} else if (strType.equalsIgnoreCase("ORACLE") || strType.equalsIgnoreCase("POSTGRESQL")) {
			strRet = (new StringBuilder()).append("nvl(").append(str1).append(",").append(str2).append(")").toString();
		} else if (strType.equalsIgnoreCase("MYSQL")) {
			strRet = (new StringBuilder()).append("ifnull(").append(str1).append(",").append(str2).append(")")
					.toString();
		} else if (strType.equalsIgnoreCase("SYBASE")) {
			strRet = (new StringBuilder()).append("isnull(").append(str1).append(",").append(str2).append(")")
					.toString();
		} else if (strType.equalsIgnoreCase("SQLSERVER")) {
			strRet = (new StringBuilder()).append("isnull(").append(str1).append(",").append(str2).append(")")
					.toString();
		} else if (strType.equalsIgnoreCase("TERA")) {
			strRet = (new StringBuilder()).append("COALESCE(").append(str1).append(",").append(str2).append(")")
					.toString();
		} else {
			throw new Exception("function definition can not be achieved");
		}
		return strRet;
	}

	/**
	 * 将数据类型转换为本地语言规定的数据格式；
	 *
	 * @param date
	 * @return
	 */
	public static String parseOutInteger(Object date) {
		if (date == null) {
			return "--";
		}
		String parsed = "";
		if (date instanceof String) {
			date = Double.valueOf(Double.parseDouble((String) date));
		}
		try {
			parsed = integerFormat.format(date);
		} catch (Exception e) {
			log.debug("--------date parse to out error, orign date is " + date);
		}
		return parsed;
	}

	/**
	 * 将数据加一个括号；
	 *
	 * @param data
	 * @return
	 */
	public static String addBracket(String data) {
		if (null == data) {
			return "0";
		}

		return " (" + data + ")";
	}

	/**
	 * 将数据类型转换为数据库存储的数据格式；
	 *
	 * @param date
	 * @return
	 */
	public static String parseInNumber(String date) {
		if (date == null) {
			return null;
		}
		String parsed = null;
		try {
			parsed = dbDecimalFormat.format(decimalFormat.parse(date));
		} catch (Exception e) {
			log.debug("--------date parse to out error, orign date is " + date);
			return null;
		}
		return parsed;
	}

	/**
	 * 将日期类型转换为本地语言规定的数据格式；
	 *
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		if (date == null) {
			return "";
		}
		String parsed = "";
		try {
			if (DATE_FORMAT_IS_DEFAULT) {
				parsed = DateUtil.date2String(date, DATE_LOCALE_FORMAT);
			} else {
				parsed = DATE_US_FORMATER.format(date);
			}
		} catch (Exception e) {
			log.debug("--------date parse to out error, orign date is " + date);
			parsed = "";
		}
		return parsed;
	}

	/**
	 * 替换字符串中的单引号；
	 *
	 * @param replaced
	 * @return
	 */
	public static String replaceSingleQuotes(String replaced) {
		if (StringUtil.isEmpty(replaced)) {
			return null;
		}

		replaced = replaced.replace("'", SINGLE_QUOTES);

		return replaced;
	}

	public static void main(String[] args) {
		long s = System.currentTimeMillis();
		log.debug(MpmUtil.convertLongMillsToStrTime(s));
		log.debug(MpmUtil.convertLongMillsToYYYYMMDDHHMMSS(s));

		log.debug(MpmUtil.getLastSendContentForBJ("2014011716115574", "32132"));

	}

	// 获取当前进程的id added by caolifeng
	public static int getPid() {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		String name = runtime.getName(); // format: "pid@hostname"
		try {
			return Integer.parseInt(name.substring(0, name.indexOf('@')));
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 获取CI客户群日期
	 */
	public static String getDateForCustGroup(int flag) {

		Calendar calendar = Calendar.getInstance();
		String currentDate = DateUtil.date2String(calendar.getTime(), DateUtil.YYYY_MM_DD);
		if (flag == MpmCONST.DISTRIBUTE_DAYILY) {// 周期派单之日派单
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			currentDate = DateUtil.date2String(calendar.getTime(), DateUtil.YYYY_MM_DD);
		} else if (flag == MpmCONST.DISTRIBUTE_MONTHLY) {// 周期派单之月派单
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			currentDate = DateUtil.date2String(calendar.getTime(), DateUtil.YYYY_MM_DD);
		} else if (flag == MpmCONST.DISTRIBUTE_WEEKLY) {// 周期派单之周派单
			int dayofweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			if (dayofweek == 0) {
				dayofweek = 7;
			}
			calendar.add(Calendar.DATE, -dayofweek - 7);
			currentDate = DateUtil.date2String(calendar.getTime(), DateUtil.YYYY_MM_DD);
		} else {
			// 非周期派单先按月走
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			currentDate = DateUtil.date2String(calendar.getTime(), DateUtil.YYYY_MM_DD);
		}

		return currentDate;

	}

	// 获取上月一号日期
	public static String getLastMonth() {
		String currentDate = getDateForCustGroup(MpmCONST.DISTRIBUTE_MONTHLY);
		return currentDate;
	}

	// 获取昨天日期
	public static String getYesterday() {
		String currentDate = getDateForCustGroup(MpmCONST.DISTRIBUTE_DAYILY);
		return currentDate;
	}

	// 获取上周第一天日期
	public static String getLastWeek() {
		String currentDate = getDateForCustGroup(MpmCONST.DISTRIBUTE_WEEKLY);
		return currentDate;
	}

	public static final String escapeHTML(String s) {
		if (s == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\u20ac'://
				sb.append("&euro;");
				break;
			case ' ':
				sb.append("&nbsp;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 替换字符串操作
	 *
	 * @param source
	 * @param oldString
	 * @param newString
	 * @return
	 */
	public static String replace2(String source, String oldString, String newString) {
		if (StringUtil.isEmpty(source)) {
			return "";
		}
		StringBuffer output = new StringBuffer();
		int lengthOfSource = source.length();
		int lengthOfOld = oldString.length();
		int posStart;
		int pos;
		for (posStart = 0; (pos = source.indexOf(oldString, posStart)) >= 0; posStart = pos + lengthOfOld) {
			output.append(source.substring(posStart, pos));
			output.append(newString);
		}

		if (posStart < lengthOfSource) {
			output.append(source.substring(posStart));
		}
		return output.toString();
	}

	public static String getSql2DateWithFormat(String sDateStr, String format) throws Exception {
		String dbType = getDBType();
		String strRet = sDateStr;
		if (dbType.equalsIgnoreCase(MpmCONST.DB_TYPE_DB2)) {
			strRet = "date('" + sDateStr + "')";
		} else if (dbType.equalsIgnoreCase(MpmCONST.DB_TYPE_ORACLE)) {
			strRet = "to_date('" + sDateStr + "','" + format + "')";
		}
		return strRet;
	}

	/**
	 *
	 * @param strDateStr
	 * @param splitStr
	 * @return
	 * @throws Exception
	 */
	public static String getSql2Date(String dbType, String sDateStr, String splitStr) throws Exception {
		String strDateStr = sDateStr;
		if (StringUtil.isEmpty(strDateStr)) {
			return null;
		}
		if (strDateStr.indexOf("0000-00-00") >= 0) {
			return "null";
		}
		String strRet = "";
		int i = strDateStr.indexOf(" ");
		if (i > 0) {
			strDateStr = strDateStr.substring(0, i);
		}

		if (dbType.equalsIgnoreCase("MYSQL") || dbType.equalsIgnoreCase("GBASE")) {
			strRet = "'" + strDateStr + "'";
		} else if (dbType.equalsIgnoreCase("DB2")) {
			strRet = "date('" + strDateStr + "')";
		} else if (dbType.equalsIgnoreCase("ORACLE") || dbType.equalsIgnoreCase("POSTGRESQL")) {
			if ("-".equals(splitStr)) {
				strRet = "to_date('" + strDateStr + "','YYYY-mm-dd')";
			} else {
				strRet = "to_date('" + strDateStr + "','YYYY/mm/dd')";
			}
		} else if (dbType.equalsIgnoreCase("ACESS")) {
			strRet = "'" + strDateStr + "'";
		} else if (dbType.equalsIgnoreCase("SQLSERVER")) {
			if ("-".equals(splitStr)) {
				strRet = "convert(varchar(10), convert(datetime,'" + strDateStr + "'), 111)";
			} else {
				strRet = "CONVERT(Datetime,'" + strDateStr + "',20)";
			}
		} else if (dbType.equalsIgnoreCase("TERA")) {
			if ("-".equals(splitStr)) {
				strRet = "cast('" + strDateStr + "' as date FORMAT 'YYYY-MM-DD')";
			} else {
				strRet = "cast('" + strDateStr + "' as date FORMAT 'YYYY/MM/DD')";
			}
		} else if (dbType.equalsIgnoreCase("SYBASE")) {
			strRet = "cast('" + strDateStr + "' as Date)";
		} else {
			throw new Exception("can't get the current date of the function definition");
		}
		return strRet;
	}

	/**
	 *
	 * @param strDateStr
	 * @param splitStr
	 * @return
	 * @throws Exception
	 */
	public static String getSql2DateCol(String dbType, String colName, String splitStr) throws Exception {
		String colNameStr = colName;
		if (StringUtil.isEmpty(colNameStr)) {
			return null;
		}
		String strRet = "";

		if (dbType.equalsIgnoreCase("MYSQL") || dbType.equalsIgnoreCase("GBASE")) {
			strRet = colNameStr;
		} else if (dbType.equalsIgnoreCase("DB2")) {
			strRet = "date(" + colNameStr + ")";
		} else if (dbType.equalsIgnoreCase("ORACLE") || dbType.equalsIgnoreCase("POSTGRESQL")) {
			if ("-".equals(splitStr)) {
				strRet = "to_date(" + colNameStr + ",'YYYY-mm-dd')";
			} else {
				strRet = "to_date(" + colNameStr + ",'YYYY/mm/dd')";
			}
		} else if (dbType.equalsIgnoreCase("ACESS")) {
			strRet = colNameStr;
		} else if (dbType.equalsIgnoreCase("SQLSERVER")) {
			if ("-".equals(splitStr)) {
				strRet = "convert(varchar(10), convert(datetime," + colNameStr + "), 111)";
			} else {
				strRet = "CONVERT(Datetime," + colNameStr + ",20)";
			}
		} else if (dbType.equalsIgnoreCase("TERA")) {
			if ("-".equals(splitStr)) {
				strRet = "cast(" + colNameStr + " as date FORMAT 'YYYY-MM-DD')";
			} else {
				strRet = "cast(" + colNameStr + " as date FORMAT 'YYYY/MM/DD')";
			}
		} else if (dbType.equalsIgnoreCase("SYBASE")) {
			strRet = "cast(" + colNameStr + " as Date)";
		} else {
			throw new Exception("can't get the current date of the function definition");
		}
		return strRet;
	}

	public static MetaDataElement getElementFromCacheByAttrId(String cviewId, String strType, String attrMetaId) {
		return getElementsMapFromCache(cviewId, strType).get(attrMetaId);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, MetaDataElement> getElementsMapFromCache(String cviewId, String strType) {
		Map<String, MetaDataElement> map = (Map<String, MetaDataElement>) SimpleCache.getInstance().get(
				strType + cviewId);
		return map;
	}

	/**
	 * 将已经拼接好的连接串根据数据库类型转换成实际的连接串[处理用||连接的字段]
	 * @param dbType
	 * @param concatedCols 已连接好的字段
	 * @return
	 * @throws Exception
	 */
	public static String getRealConcatedColumns(String dbType, String concatedCols) throws Exception {
		String realConcatStr = "";
		if (StringUtil.isNotEmpty(concatedCols)) {
			realConcatStr = getConcatSqlStr(dbType, (Object[]) concatedCols.split("\\|\\|"));
		}
		return realConcatStr;
	}

	/**
	 * 返回字符串连接的函数
	 * @param values
	 * @return
	 */
	public static String getConcatSqlStr(String dbType, Object... values) {
		//		String dbType = getDBType();
		StringBuffer sb = new StringBuffer();

		if (dbType.equalsIgnoreCase("db2") || dbType.equalsIgnoreCase("oracle")
				|| dbType.equalsIgnoreCase("POSTGRESQL")) {
			sb.append(jionStr("||", values));
			return sb.toString();
		}

		if (dbType.equalsIgnoreCase("SQL Server")) {
			sb.append(jionStr("+", values));
			return sb.toString();
		}

		if (dbType.equalsIgnoreCase("MySQL") || dbType.equalsIgnoreCase("gbase")) {
			sb.append("CONCAT(");
			sb.append(jionStr(",", values));
			sb.append(")");
			return sb.toString();
		}

		return null;
	}

	/**
	 * 连接成字符串
	 * @param sep
	 * @param values value1,value2,value3...
	 * @return value1<B>sep</B>value2<B>sep</B>value3...
	 */
	public static String jionStr(String sep, Object... values) {
		StringBuffer sb = new StringBuffer();
		if (values.length > 0) {
			int index = 0;
			for (Object col : values) {
				if (index != 0) {
					sb.append(sep);
				}
				sb.append(col);
				index++;
			}
		}
		return sb.toString();
	}

	/**
	 * 根据视图ID获取通用主键字段
	 * @param cviewId
	 * @return
	 * @throws Exception
	 * @author zhangyb5
	 */
	public static String getKeyColumn(String cviewId) throws Exception {
		String mainKeyColumn = "";
		IMcdViewService cvs = SpringContext.getBean("mcdViewService", IMcdViewService.class);
		McdCvDefine ccd = null;
		if (cvs != null && StringUtil.isNotEmpty(cviewId)) {
			ccd = cvs.findCvDefineById(cviewId);
			mainKeyColumn = ccd.getRelaPrimaryKey();
		}
		return mainKeyColumn;
	}

	/**
	 * 将日期按照特定格式转换为字符串
	 * @param date 传入的日期
	 * @param format 指定的日期格式
	 * @return
	 * @throws Exception
	 * @author zhangyb5
	 */
	public static String getFormatedDate(Date date, String format) throws Exception {
		String dateStr = "";
		String dateFormat = SqlParseCONST.DATE_FORMAT_YYYYMM;//默认日期格式
		if (StringUtil.isNotEmpty(format)) {
			if (SqlParseCONST.DATE_FORMAT_YYMM.equalsIgnoreCase(format)) {
				dateFormat = SqlParseCONST.DATE_FORMAT_YYMM;
			} else if (SqlParseCONST.DATE_FORMAT_YYYY.equalsIgnoreCase(format)) {
				dateFormat = SqlParseCONST.DATE_FORMAT_YYYY;
			} else if (SqlParseCONST.DATE_FORMAT_YYYYMMDD.equalsIgnoreCase(format)) {
				dateFormat = SqlParseCONST.DATE_FORMAT_YYYYMMDD;
			} else if (SqlParseCONST.DATE_FORMAT_YYYY_MM_DD.equalsIgnoreCase(format)) {
				dateFormat = SqlParseCONST.DATE_FORMAT_YYYY_MM_DD;
			} else if (SqlParseCONST.DATE_FORMAT_YYYY_MM.equalsIgnoreCase(format)) {
				dateFormat = SqlParseCONST.DATE_FORMAT_YYYY_MM;
			} else if (SqlParseCONST.DATE_FORMAT_YYYYMM.equalsIgnoreCase(format)) {
				dateFormat = SqlParseCONST.DATE_FORMAT_YYYYMM;
			} else {
				dateFormat = format;
			}

		} else {
			log.warn("Date format is empty!Use the default date format: " + dateFormat);
		}
		FastDateFormat sd = FastDateFormat.getInstance(dateFormat);
		dateStr = sd.format(date);
		return dateStr;
	}

	@SuppressWarnings("unchecked")
	public static List<TreeNode> getTreeFromCache(Locale locale, String cviewId, String strType) {
		List<TreeNode> lst = (List<TreeNode>) SimpleCache.getInstance().get(strType + cviewId);
		return lst;
	}

	public static void setTreeToCache(String cviewId, String strType, List<TreeNode> lst) {
		SimpleCache.getInstance().put(strType + cviewId, lst, -1);
	}

	public static void setElementsToCache(String cviewId, String strType, Map<String, MetaDataElement> map) {
		SimpleCache.getInstance().put(strType + cviewId, map, -1);
	}

	public static Date getDate(String date) throws Exception {
		Date d = null;
		DateFormat sd = new SimpleDateFormat(SqlParseCONST.DATE_FORMAT_YYYYMMDD);
		String dateStr = "";
		if (StringUtil.isNotEmpty(date) && date.length() == 6) {//YYYYMM
			dateStr = date + "01";//补充日
		} else if (StringUtil.isNotEmpty(date) && date.length() == 4) {//YYYY
			dateStr = date + "0101";//补充月日
		} else {
			dateStr = date;
		}
		if (StringUtil.isNotEmpty(dateStr)) {
			d = sd.parse(dateStr);
		}
		return d;
	}

	/**
	 * 将日期转换为特定格式的字符串
	 * @param dateStr 传入的日期
	 * @param srcFormat 指定传入的日期格式
	 * @param targetFormat 指定格式化的日期格式
	 * @return
	 * @throws Exception
	 * @author zwj
	 */
	public static String getFormatedDate(String dateStr, String srcFormat, String targetFormat) throws Exception {
		String FormatedDateStr = "";
		try {
			Date date = getDate(dateStr, srcFormat);
			FormatedDateStr = getFormatedDate(date, targetFormat);
		} catch (Exception e) {
			//FormatedDateStr = dateStr;
			log.error("format error dateStr:" + dateStr + " srcFormat:" + srcFormat + " targetFormat:" + targetFormat);
		}
		return FormatedDateStr;
	}

	public static Date getDate(String date, String format) throws Exception {
		Date d = null;
		DateFormat sd = new SimpleDateFormat(format);
		if (StringUtil.isNotEmpty(date)) {
			d = sd.parse(date);
		} else {
			throw new Exception("The data date is empty!Please check it!");
		}
		return d;
	}

	@SuppressWarnings("unchecked")
	public static MetaDataElement getElementByNodeId(List<TreeNode> metaCacheList, String nodeText, String cviewId)
			throws Exception {
		MetaDataElement bo = new MetaDataElement();
		//add by SiQinfu,统一视图主表名称MAP
		Map<String, String> mainTableNameMap = (Map<String, String>) SimpleCache.getInstance().get(
				SqlParseCONST.MAIN_TABLE_NAME);
		String mainTableName = mainTableNameMap.get(cviewId);
		for (TreeNode node : metaCacheList) {
			if (node.isLeaf()) {
				JSONObject o = JSONObject.fromObject(node.getParam1());
				MetaDataElement meta = (MetaDataElement) JSONObject.toBean(o, MetaDataElement.class);
				if (meta != null && mainTableName.equalsIgnoreCase(meta.getMdaTableId())
						&& meta.getMdaColumnName().equalsIgnoreCase(nodeText)) {
					bo = meta;
					break;
				}
			}
		}
		return bo;
	}

	/**
	 * 根据模板ID和缓存类型获取该原数据对应的JSON数组数据，用于简单模式界面展示
	 */
	public static JSONArray getJsonArrayDataOfConFromCache(String templetId, String type) {
		return (JSONArray) SimpleCache.getInstance().get(type + templetId);
	}

	/**
	 * 缓存业务特征和筛选模板对应的JSON数组数据
	 * @param templetId
	 * @param type
	 * @param json
	 */
	public static void setJsonArrayDataOfConToCache(String templetId, String type, JSONArray jsons) {
		if (jsons != null) {
			String expireTime = Configure.getInstance().getProperty("CACHE_METADATA_JSON_EXPIRETIME");
			int time = Integer
					.valueOf((expireTime != null && !"".equals(expireTime.trim())) ? expireTime.trim() : "-1")
					.intValue();
			SimpleCache.getInstance().put(type + templetId, jsons, time * 60);
		} else {
			log.warn(templetId + "'s jsons is null");
		}
	}

	/**
	 * 设置缓存中数据为空
	 */
	public static void removeJsonArrayDataOfConFromCache(String templetId, String type) {
		SimpleCache.getInstance().put(type + templetId, null);
	}

	/**
	 * 将list按照给定长度分组
	 * @param list
	 * @param rowSize 大于0的整数
	 * @return
	 */
	public static <T> List<List<T>> splitList(List<T> list, int rowSize) {
		List<List<T>> lists = new ArrayList<List<T>>();
		if (list != null && !list.isEmpty() && rowSize > 0) {
			int remainder = list.size() % rowSize;
			int intPart = list.size() / rowSize;
			for (int i = 0; i < intPart; i++) {
				lists.add(list.subList(i * rowSize, (i + 1) * rowSize));
			}
			if (remainder > 0) {
				lists.add(list.subList(intPart * rowSize, intPart * rowSize + remainder));
			}
		}
		return lists;
	}

	public static String getCommonMonthFormat() {
		return Configure.getInstance().getProperty("COMMON_MONTH_FORMAT").equals("") ? SqlParseCONST.DATE_FORMAT_YYYY_MM
				: Configure.getInstance().getProperty("COMMON_MONTH_FORMAT");
	}

	public static String getCommonTimeFormat() {
		return Configure.getInstance().getProperty("COMMON_TIME_FORMAT").equals("") ? "yyyy-MM-dd HH:mm:ss" : Configure
				.getInstance().getProperty("COMMON_TIME_FORMAT");
	}

	public static String getCommonDateFormat() {
		return Configure.getInstance().getProperty("COMMON_DATE_FORMAT").equals("") ? SqlParseCONST.DATE_FORMAT_YYYY_MM_DD
				: Configure.getInstance().getProperty("COMMON_DATE_FORMAT");
	}

	/**
	 * 将Object类型的对象根据其实际类型转换为字符串
	 * @param obj
	 * @return
	 */
	public static String object2String(Object obj) {
		String data = "";
		if (obj instanceof Date) {
			if (obj != null) {
				data = FastDateFormat.getInstance(getCommonDateFormat()).format((Date) obj);
			}
		} else if (obj instanceof BigDecimal) {
			data = obj != null ? ((BigDecimal) obj).doubleValue() + "" : "0";
		} else {
			data = obj != null ? obj.toString() : "0";
		}
		return data;
	}

	/**
	 * 将Object类型的对象根据其实际类型转换为字符串
	 * @param obj
	 * @param type 1-字符串；2-整数；3-浮点数；4-日期；5-时间
	 * @return
	 */
	public static String object2String(Object obj, String type) {
		String data = "";
		if (obj instanceof Date) {
			if (obj != null) {
				if ("5".equals(type)) {
					data = FastDateFormat.getInstance(getCommonTimeFormat()).format((Date) obj);
				} else {
					data = FastDateFormat.getInstance(getCommonDateFormat()).format((Date) obj);
				}
			}
		} else if (obj instanceof BigDecimal) {
			if ("3".equals(type)) {
				data = obj != null ? ((BigDecimal) obj).doubleValue() + "" : "";
			} else {
				data = obj != null ? ((BigDecimal) obj).longValue() + "" : "";
			}
		} else {
			data = obj != null ? obj.toString() : "";
		}
		return data;
	}

	

	/**
	 * 手机号码段格式化（补零）
	 * @param phoneNumber
	 * @return
	 */
	public static String phoneNumberZeroAdd(short phoneNumber) {
		return phoneNumberFormat.format(phoneNumber);
	}

	/**
	 * 格式化（补零）
	 * @param number
	 * @param format
	 * @return
	 */
	public static String formatNumber(long number, String format) {
		DecimalFormat numberFormat = new DecimalFormat(format);
		return numberFormat.format(number);
	}

	public static String formatPhoneNo(short num) {
		String str = String.valueOf(num);
		int n = str.length();
		StringBuilder sb = new StringBuilder();
		switch (n) {
		case 1:
			sb.append("000").append(str);
			break;
		case 2:
			sb.append("00").append(str);
			break;
		case 3:
			sb.append("0").append(str);
			break;
		case 4:
			sb.append(str);
			break;
		default:
			sb.append(str);
			break;
		}
		return sb.toString();

	}

	/**
	 * 获得根据模板表创建表的SQL语句
	 * @param newTab
	 * @param tmpTable
	 * @return
	 */
	public static String getSqlCreateAsTable(String newTab, String tmpTable) {
		StringBuilder strRet = new StringBuilder();
		String tabSpace = Configure.getInstance().getProperty("MPM_TABLESPACE");
		String dbType = getDBType();
		if (dbType.equalsIgnoreCase(MpmCONST.DB_TYPE_ORACLE) || dbType.equalsIgnoreCase(MpmCONST.DB_TYPE_GP)) {
			if (StringUtil.isNotEmpty(tabSpace)) {//如果包含表空间，则添加
				strRet.append("create table ").append(tabSpace).append(".").append(newTab)
						.append(" as select * from ").append(tabSpace).append(".").append(tmpTable).append(" where 1=2 ");
			} else {
				strRet.append("create table ").append(newTab).append(" NOLOGGING as select * from ").append(tmpTable)
						.append(" where 1=2 ");
			}
		} else if (dbType.equalsIgnoreCase(MpmCONST.DB_TYPE_DB2)) {
			if (StringUtil.isNotEmpty(tabSpace)) {//如果包含表空间，则添加
				strRet.append("create table ").append(newTab).append(" like ").append(tmpTable).append(" in ")
						.append(tabSpace).append(" NOT LOGGED INITIALLY ");
			} else {
				strRet.append("create table ").append(newTab).append(" like ").append(tmpTable)
						.append(" NOT LOGGED INITIALLY ");
			}
		} else if (dbType.equalsIgnoreCase(MpmCONST.DB_TYPE_MYSQL)) {//不需指定表空间
			strRet.append("create table ").append(newTab).append(" as select * from ").append(tmpTable)
					.append(" where 1=2 ");
		}
		return strRet.toString();
	}
	
	/**
	 * 在sqlFire库中根据模板表创建客户群清单表  add by lixq10
	 * @param newTab
	 * @param tmpTable
	 * @return
	 */
	public static String getSqlCreateAsTableInSqlFire(String newTab, String tmpTable) {
		StringBuilder strRet = new StringBuilder();
		String tabSpace = MpmConfigure.getInstance().getProperty("MPM_SQLFIRE_TABLESPACE");
		String isUseSqlfire = MpmConfigure.getInstance().getProperty("MPM_IS_USE_SQLFIRE");
		if(StringUtil.isNotEmpty(isUseSqlfire) && isUseSqlfire.equals("false")){  //不使用sqlfire数据库
			strRet.append("create table ").append(tabSpace).append(".").append(newTab).append(" NOLOGGING as select * from ").append(tabSpace).append(".").append(tmpTable).append(" where 1=2");
		}else{
			strRet.append("create table ").append(tabSpace).append(".").append(newTab).append(" as select * from ").append(tabSpace).append(".").append(tmpTable).append(" where 1=2").append(" WITH NO DATA");
		}
		log.warn("在sqlfire库中创建清单表sql语句："+strRet.toString());
		return strRet.toString();
	}

	/**
	 * 获取字符串的编码类型
	 * @param str
	 * @return
	 * @author zhangyb5
	 */
	public static String getChartSetName(String str) {
		if (null != str) {
			String encode = "ISO-8859-1";
			try {
				if (str.equals(new String(str.getBytes(encode), encode))) {
					String s = encode;
					return s;
				}
			} catch (Exception exception) {
			}
			encode = "GB2312";
			try {
				if (str.equals(new String(str.getBytes(encode), encode))) {
					String s1 = encode;
					return s1;
				}
			} catch (Exception exception1) {
			}
			encode = "UTF-8";
			try {
				if (str.equals(new String(str.getBytes(encode), encode))) {
					String s2 = encode;
					return s2;
				}
			} catch (Exception exception2) {
			}
			encode = "GBK";
			try {
				if (str.equals(new String(str.getBytes(encode), encode))) {
					String s3 = encode;
					return s3;
				}
			} catch (Exception exception3) {
			}
		}

		return "";
	}

	public static String getSql2DateNow() {
		String strType = getDBType();
		String strRet = "";
		if (strType.equalsIgnoreCase("MYSQL")) {
			strRet = "now()";
		} else if (strType.equalsIgnoreCase("ORACLE")) {
			strRet = "sysdate";
		} else if (strType.equalsIgnoreCase("ACESS")) {
			strRet = "date()";
		} else if (strType.equalsIgnoreCase("SQLSERVER")) {
			strRet = "getdate()";
		} else if (strType.equalsIgnoreCase("DB2")) {
			strRet = "current date";
		} else if (strType.equalsIgnoreCase("POSTGRESQL")) {
			strRet = "current_date";
		} else if (strType.equalsIgnoreCase("TERA")) {
			strRet = "cast((date (format 'yyyy-mm-dd' )) as char(10)) ||' '|| time";
		} else if (strType.equalsIgnoreCase("SYBASE")) {
			strRet = "getdate()";
		}
		return strRet;
	}

	public static String getSql2DateYesterday() {
		String strType = getDBType();
		String strRet = "";
		if (strType.equalsIgnoreCase("MYSQL")) {
			strRet = "date_sub(now(),interval 1 day)";
		} else if (strType.equalsIgnoreCase("ORACLE")) {
			strRet = "sysdate -1";
		} else if (strType.equalsIgnoreCase("DB2")) {
			strRet = "current date - 1 day";
		} else if (strType.equalsIgnoreCase("POSTGRESQL")) {
			strRet = "current_date - interval '1 day'";
		}
		return strRet;
	}

	public static String[] splitStr(String str, String del) {
		Vector vect = StringUtil.getVector(str, del);
		int num = vect.size();
		String strArray[] = new String[num];
		for (int i = 0; i < num; i++) {
			strArray[i] = (String) vect.elementAt(i);
		}
		return strArray;
	}

	public static String getTwoDbDateOptByDays(String date1, String date2, String opt) {
		StringBuffer sb = new StringBuffer("(");
		if (MpmCONST.DB_TYPE_ORACLE.equalsIgnoreCase(getDBType())) {
			sb.append("trunc(").append(date1).append(")").append(opt).append("trunc(").append(date2).append(")");
		} else if (MpmCONST.DB_TYPE_DB2.equalsIgnoreCase(getDBType())) {
			sb.append("days(").append(date1).append(")").append(opt).append("days(").append(date2).append(")");
		} else if (MpmCONST.DB_TYPE_MYSQL.equalsIgnoreCase(getDBType())) {
			sb.append("to_days(").append(date1).append(")").append(opt).append("to_days(").append(date2).append(")");
		} else {
			sb.append(date1).append(opt).append(date2);
		}
		sb.append(")");
		return sb.toString();
	}

	//判断是否有效手机号码
	public static boolean isMobileNumber(String mobiles) {
		Pattern p = Pattern.compile("^((13[4-9])|(15[0|1|2|7|8|9])|(18[2|3|4|7|8]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static String getMonthFromDateStr(String dateStr) {
		if (StringUtils.isNotEmpty(dateStr)) {
			return dateStr.substring(0, 7);
		} else {
			return dateStr;
		}
	}

	public static String getCityNameByCityId(String cityId) {
		IMpmUserPrivilegeService pService = null;
		try {
			pService = (IMpmUserPrivilegeService) SystemServiceLocator.getInstance().getService(
					MpmCONST.MPM_USER_PRIVILEGE_SERVICE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ICity city = pService.getCityById(cityId);
		if (city != null) {
			return city.getCityName();
		} else {
			return cityId;
		}
	}

	/*
	 ' ============================================
	' 按指定长度分割字符串,通常用于短信
	 ' ============================================
	*/
	public static String[] strSplit(String msg, int num) {
		int len = msg.length();
		if (len <= num) {
			return new String[] { msg };
		}
		int count = len / (num - 1);
		count += len > (num - 1) * count ? 1 : 0; // 这里应该值得注意
		String[] result = new String[count];
		int pos = 0;
		int splitLen = num - 1;
		for (int i = 0; i < count; i++) {
			if (i == count - 1) {
				splitLen = len - pos;
			}
			result[i] = msg.substring(pos, pos + splitLen);
			pos += splitLen;

		}
		return result;
	}

	public static String convertPercentNumber(Double num) {
		if (num != null && num != 0) {
			return convertPercentStr(num * 100, "0.00%");
		}
		return num.toString();
	}

	/**
	 * 获取替换变量后的营销用语
	 * @param actId
	 * @param productNo
	 * @param cepEventData
	 * @return
	 */
	public static String getLastSendContent(String actId, String productNo, JSONObject cepEventData)
			throws MpmException {

		String lastSendContent = "";
		try {
			if ("1".equals(MpmConfigure.getInstance().getProperty("ENABLE_REPLACE_STR"))) {//如果启用变量替换，则执行变量替换逻辑获取替换后的营销用语
				String campContent = NbsJmsPublishUtil.getCampContentByActId(actId);
				//获取营销用语变量
				String[] campContentVars = NbsJmsPublishUtil.parseReplaceCharacter(campContent);
				lastSendContent = campContent;
				if (ArrayUtils.isNotEmpty(campContentVars)) {
					for (String var : campContentVars) {
						Object2ObjectOpenHashMap<String, SystemVarInfoBean> varDefMap = (Object2ObjectOpenHashMap<String, SystemVarInfoBean>) SimpleCache
								.getInstance().get(JmsConstant.CACHE_KEY_TABLE_VAR_DATA);
						SystemVarInfoBean vb = varDefMap.get(var);
						if (vb != null) {
							if (MpmCONST.VAR_SOURCE_CONSTANT.equals(vb.getVarSource())) {//常量，目前仅日期类型
								lastSendContent = lastSendContent.replace("${" + var + "}", getConstantVarValue(var));
							} else if (MpmCONST.VAR_SOURCE_CHANCE.equals(vb.getVarSource())) {//时机
								lastSendContent = lastSendContent.replace("${" + var + "}",
										getChanceVarValue(vb.getColumnName(), productNo));
								//							} else if (MpmCONST.VAR_SOURCE_EVENT_C3.equals(vb.getVarSource()) && null != c3EventData) {//事件
								//								lastSendContent = lastSendContent.replace("${" + var + "}",
								//										getC3EventVarValue(vb, productNo, c3EventData));
							}
						}
					}
				}

				/**
				 * 复杂事件携带营销用语
				 */
				if (cepEventData != null) {
					String[] cepContentVars = (String[]) SimpleCache.getInstance().get(
							"CAMPSEG_CEP_REPLACE_VARS_" + actId);
					if (ArrayUtils.isEmpty(cepContentVars)) {
						cepContentVars = NbsJmsPublishUtil.parseCepReplaceCharacter(lastSendContent);
						SimpleCache.getInstance()
								.put("CAMPSEG_CEP_REPLACE_VARS_" + actId, cepContentVars, 24 * 60 * 60);
					}
					//log.debug("cepContentVars:"+printArrs(cepContentVars));
					//从用户json中取值
					if (ArrayUtils.isNotEmpty(cepContentVars)) {
						//从数据配置表取值
						Object2ObjectOpenHashMap<String, Object2ObjectOpenHashMap<String, String>> varCepMap = (Object2ObjectOpenHashMap<String, Object2ObjectOpenHashMap<String, String>>) SimpleCache
								.getInstance().get(JmsConstant.CACHE_KEY_CEP_REPLACE_VARS_TAB_DATA);
						Object2ObjectOpenHashMap<String, String[]> cepCacheVarMadData = (Object2ObjectOpenHashMap<String, String[]>) SimpleCache
								.getInstance().get(JmsConstant.CACHE_KEY_CEP_REPLACE_VARS_MDA_DATA);
						//log.debug("varCepMap:"+varCepMap);
						//log.debug("cepCacheVarMadData:"+cepCacheVarMadData);
						for (String var : cepContentVars) {
							// log.debug("var:"+var);
							String rex = "\\#\\{" + var + "\\}";
							if (cepCacheVarMadData.containsKey(var)) {
								String[] mad_tab = cepCacheVarMadData.get(var);//数组构成String[mda_id,table_name]
								String mda_id = mad_tab[0];
								String table_name = mad_tab[1];
								String val_flag = cepEventData.get(mda_id).toString();
								Object2ObjectOpenHashMap<String, String> Vmap = varCepMap.get(table_name);
								//log.debug(Vmap);
								String value = Vmap.get(val_flag);
								log.debug("mda_id:" + mda_id + "  table_name:" + table_name + "   val_flag=" + val_flag
										+ " value:" + value);
								lastSendContent = lastSendContent.replaceAll(rex, value);
							} else {
								lastSendContent = lastSendContent.replaceAll(rex, cepEventData.get(var).toString());
							}
						}

					}
				}
			}

			//			log.info(lastSendContent);

		} catch (Exception e) {
			log.error("获取变量替换后的营销用语发生异常：", e);
			throw new MpmException("获取变量替换后的营销用语发生异常（actId=" + actId + ";productNo=" + productNo + ";cepEventData="
					+ cepEventData + "）", e);
		}
		return lastSendContent;
	}

	/**
	 * 获取时机替换付的值
	 * @param phoneNo
	 * @param varId
	 * @return
	 */
	public static String getChanceReplaceValue(byte v1, short v2, short v3, String varId) {
		try {
			Object2ObjectOpenHashMap<String, SystemVarInfoBean> varDefMap = (Object2ObjectOpenHashMap<String, SystemVarInfoBean>) SimpleCache
					.getInstance().get(JmsConstant.CACHE_KEY_TABLE_VAR_DATA);
			SystemVarInfoBean vb = varDefMap.get(varId);
			if (vb != null) {
				if (MpmCONST.VAR_SOURCE_CONSTANT.equals(vb.getVarSource())) {//常量，目前仅日期类型
					return getConstantVarValue(varId);
				} else if (MpmCONST.VAR_SOURCE_CHANCE.equals(vb.getVarSource())) {//时机
					return getChanceVarValue(vb.getColumnName(), v1, v2, v3);
				} else if (MpmCONST.VAR_SOURCE_CHANCEMATCHING.equals(vb.getVarSource())) {
					return getChanceMatchingValue(v1, v2, v3, varId);
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	/**
	 * 获取缓存中计算出来的时机标签
	 * @param varId
	 * @param phoneNo
	 * @return
	 */
	public static String getChanceMatchingValue(byte v1, short v2, short v3, String varId) {
		Byte2ObjectOpenHashMap<Short2ObjectOpenHashMap<Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>>>> h1 = (Byte2ObjectOpenHashMap<Short2ObjectOpenHashMap<Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>>>>) SimpleCache
				.getInstance().get(JmsConstant.CACHE_KEY_CHANCE_MATCHING_DATA);
		if (h1 != null) {
			Short2ObjectOpenHashMap<Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>>> h2 = h1.get(v1);
			if (h2 != null) {
				Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>> h3 = h2.get(v2);
				if (h3 != null) {
					Object2ObjectOpenHashMap<String, String> map = h3.get(v3);
					if (map != null) {
						return map.get(varId);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取替换变量后的营销用语(CM客户群)
	 * @param actId
	 * @param productNo
	 * @param c3EventData
	 * @return
	 */
	public static String getLastSendContentForCM(String actId, String productNo,
			Object2ObjectOpenHashMap<String, String> h4) throws MpmException {

		String lastSendContent = "";
		try {
			String campContent = NbsJmsPublishUtil.getCampContentByActId(actId);
			//获取营销用语变量
			String[] campContentVars = NbsJmsPublishUtil.parseReplaceCharacterForCM(campContent);
			if (ArrayUtils.isNotEmpty(campContentVars)) {
				lastSendContent = campContent;
				for (String var : campContentVars) {
					lastSendContent = lastSendContent.replace("&{" + var + "}", h4.get(var));
				}
			}

		} catch (Exception e) {
			log.error("获取变量替换后的营销用语发生异常：", e);
			throw new MpmException("获取变量替换后的营销用语发生异常（actId=" + actId + ";productNo=" + productNo + "）", e);
		}
		return lastSendContent;
	}

	/**
	 * 北京格式化短信-获取替换变量后的营销用语   如“尊敬的1%，截止到2%，您的余额为3%，请您及时充值！
	 * @param actId
	 * @param productNo
	 * @param c3EventData
	 * @return
	 */
	public static String getLastSendContentForBJ(String actId, String productNo) throws MpmException {

		String lastSendContent = "";
		try {
			String campContent = NbsJmsPublishUtil.getCampContentByActId(actId);
			//获取营销用语变量
			String[] campContentVars = NbsJmsPublishUtil.parseReplaceCharacterForBJ(campContent);
			if (ArrayUtils.isNotEmpty(campContentVars)) {
				lastSendContent = campContent;
				for (int i = 0; i < campContentVars.length; i++) {
					lastSendContent = lastSendContent.replaceFirst("(\\&\\{(\\w+)\\})", "%" + (i + 1));

				}
			}

		} catch (Exception e) {
			log.error("获取格式化短信的营销用语发生异常：", e);
			throw new MpmException("获取格式化短信的营销用语发生异常（actId=" + actId + ";productNo=" + productNo + "）", e);
		}
		return lastSendContent;
	}

	/**
	 * 获取常量值
	 * @param varName
	 * @return
	 * @throws Exception
	 */
	public static String getConstantVarValue(String varName) throws Exception {
		String dateFormat = "";
		Calendar calendar = Calendar.getInstance();
		if ("currentDay".equals(varName)) {//当前日期
			dateFormat = SqlParseCONST.DATE_FORMAT_YYYY_MM_DD_ZH;
		} else if ("currentMonth".equals(varName)) {
			dateFormat = SqlParseCONST.DATE_FORMAT_YYYY_MM_ZH;
		} else if ("preDay".equals(varName)) {
			calendar.add(Calendar.DATE, -1);
			dateFormat = SqlParseCONST.DATE_FORMAT_YYYY_MM_DD_ZH;
		} else if ("preMonth".equals(varName)) {
			calendar.add(Calendar.MONTH, -1);
			dateFormat = SqlParseCONST.DATE_FORMAT_YYYY_MM_ZH;
		}
		return MpmUtil.getFormatedDate(calendar.getTime(), dateFormat);
	}

	/**
	 * 获取时机变量值
	 * @param columnName--变量对应的时机表的字段名称
	 * @param productNo
	 * @return
	 * @throws Exception
	 */
	public static String getChanceVarValue(String columnName, String productNo) throws Exception {
		String varValue = "";
		Byte2ObjectOpenHashMap<Short2ObjectOpenHashMap<Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>>>> chanceData = (Byte2ObjectOpenHashMap<Short2ObjectOpenHashMap<Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>>>>) SimpleCache
				.getInstance().get(JmsConstant.CACHE_KEY_OPPORTUNITY_DATA);
		if (null != chanceData) {
			byte v1 = ConvertUtils.parseByte(productNo.substring(1, 3));
			short v2 = ConvertUtils.parseShort(productNo.substring(3, 7));
			short v3 = ConvertUtils.parseShort(productNo.substring(7));
			Object2ObjectOpenHashMap<String, String> attrValue = getExtendAttrValue(v1, v2, v3, chanceData);
			if (null != attrValue) {
				varValue = attrValue.get(columnName.toUpperCase());
			}
		}
		return varValue;
	}

	/**
	 * 获取时机变量值
	 * @param columnName--变量对应的时机表的字段名称
	 * @param productNo
	 * @return
	 * @throws Exception
	 */
	public static String getChanceVarValue(String columnName, byte v1, short v2, short v3) throws Exception {
		String varValue = "";
		Byte2ObjectOpenHashMap<Short2ObjectOpenHashMap<Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>>>> chanceData = (Byte2ObjectOpenHashMap<Short2ObjectOpenHashMap<Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>>>>) SimpleCache
				.getInstance().get(JmsConstant.CACHE_KEY_OPPORTUNITY_DATA);
		if (null != chanceData) {
			Object2ObjectOpenHashMap<String, String> attrValue = getExtendAttrValue(v1, v2, v3, chanceData);
			if (null != attrValue) {
				varValue = attrValue.get(columnName.toUpperCase());
			}
		}
		return varValue;
	}

	/**
	 * 获取C3事件变量值
	 * @param varInfoBean--变量定义实体
	 * @param productNo
	 * @param c3EventData
	 * @return
	 * @throws Exception
	 */
	public static String getC3EventVarValue(SystemVarInfoBean varInfoBean, String productNo, JSONObject c3EventData)
			throws Exception {
		String varValue = "";
		if (null != c3EventData) {
			varValue = c3EventData.getString(varInfoBean.getColumnName().toLowerCase());//获取事件数据
			if (StringUtil.isNotEmpty(varInfoBean.getRelTable())) {//需要从关系表中获取数据
				Object2ObjectOpenHashMap<String, Object2ObjectOpenHashMap<String, String>> eventData = (Object2ObjectOpenHashMap<String, Object2ObjectOpenHashMap<String, String>>) SimpleCache
						.getInstance().get(JmsConstant.CACHE_KEY_EVENT_DATA);
				if (null != eventData) {
					Object2ObjectOpenHashMap<String, String> dataMap = eventData.get(varInfoBean.getRelTable());
					if (null != dataMap) {
						varValue = dataMap.get(varValue);//获取事件规则对应的关系表数据
					}
				}
			}
		}
		return varValue;
	}

	/**
	 * 根据分段手机号码匹配对应的扩展属性（与手机号对应的其他属性值Map）
	 * @param v1
	 * @param v2
	 * @param v3
	 * @param h1
	 * @return
	 */
	public static Object2ObjectOpenHashMap<String, String> getExtendAttrValue(
			byte v1,
			short v2,
			short v3,
			Byte2ObjectOpenHashMap<Short2ObjectOpenHashMap<Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>>>> h1) {
		Object2ObjectOpenHashMap<String, String> h4 = null;
		if (h1 != null) {
			Short2ObjectOpenHashMap<Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>>> h2 = h1.get(v1);
			if (h2 != null) {
				Short2ObjectOpenHashMap<Object2ObjectOpenHashMap<String, String>> h3 = h2.get(v2);
				if (h3 != null) {
					h4 = h3.get(v3);
				}
			}
		}
		return h4;
	}

	public static void dropTable(String tabName) throws Exception {
		if (StringUtil.isNotEmpty(tabName)) {
			try {
				JdbcTemplate jdbcTemplate = SpringContext.getBean("jdbcTemplate", JdbcTemplate.class);
				jdbcTemplate.execute(new StringBuilder("drop table ").append(tabName).toString());
			} catch (Exception e) {
				log.warn("drop table error:" + e.getMessage());
			}
		}
	}

	/**
	 * 清理表数据
	 * @param tabName
	 * @throws Exception
	 */
	public static void clearTabData(String tabName) throws Exception {
		String dbType = MpmUtil.getDBType();
		JdbcTemplate jdbcTemplate = SpringContext.getBean("jdbcTemplate", JdbcTemplate.class);
		if (StringUtil.isNotEmpty(tabName)) {
			try {
				StringBuffer b = null;
				if (MpmCONST.DB_TYPE_DB2.equals(dbType)) {
					b = new StringBuffer();
					b.append("alter table ").append(tabName).append(" activate NOT LOGGED INITIALLY WITH EMPTY TABLE");
					jdbcTemplate.execute(b.toString());
					//jdbcTemplate.execute("alter table " + tabName + " activate NOT LOGGED INITIALLY");
				} else if (MpmCONST.DB_TYPE_ORACLE.equals(dbType) || MpmCONST.DB_TYPE_MYSQL.equals(dbType)) {
					b = new StringBuffer("truncate table ").append(tabName);
					jdbcTemplate.execute(b.toString());
				} else {
					b = new StringBuffer("delete from ").append(tabName);
					jdbcTemplate.execute(b.toString());
				}
			} catch (Exception e) {
				log.error("Clear data of {} error:", tabName, e);
			}
		}
	}

	/**
	 * 清理表数据
	 * @param tabName
	 * @throws Exception
	 */
	public static void clearTabData(JdbcTemplate jdbcTemplate, String tabName) throws Exception {
		String dbType = MpmUtil.getDBType();
		if (StringUtil.isNotEmpty(tabName)) {
			try {
				String sql = "";
				if (MpmCONST.DB_TYPE_DB2.equals(dbType)) {
					sql = new StringBuffer("alter table ").append(tabName)
							.append(" activate NOT LOGGED INITIALLY WITH EMPTY TABLE").toString();
					jdbcTemplate.execute(sql);
				} else if (MpmCONST.DB_TYPE_ORACLE.equals(dbType) || MpmCONST.DB_TYPE_MYSQL.equals(dbType)) {
					sql = new StringBuffer("truncate table ").append(tabName).toString();
					jdbcTemplate.execute(sql);
				} else {
					sql = new StringBuffer("delete from ").append(tabName).toString();
					jdbcTemplate.execute(sql);
				}

			} catch (Exception e) {
				log.error("Clear data of " + tabName + " error:", e);
			}
		}
	}

	public static boolean isExistsBasePageExtendJs(ServletContext application, String province) {
		boolean flag = false;
		String basePage_extendPath = application.getRealPath("/mpm/assets/js/province/" + province
				+ "/basePage-extend.js");
		File basePage_extendFile = new File(basePage_extendPath);
		if (basePage_extendFile.exists()) {
			flag = true;
			return flag;
		} else {
			return flag;
		}

	}

	public static boolean isExistsExtendJSP(ServletContext application, String province, String JspName) {
		boolean flag = false;
		String basePage_extendPath = application.getRealPath("/mpm/province/" + province + "/" + JspName
				+ "_extend.jsp");
		File basePage_extendFile = new File(basePage_extendPath);
		if (basePage_extendFile.exists()) {
			flag = true;
			return flag;
		} else {
			return flag;
		}

	}

	public static boolean isExistsSceneExtendJSP(ServletContext application, String province, String JspName) {
		boolean flag = false;
		String basePage_extendPath = application.getRealPath("/mpm/sceneCamp/province/" + province + "/" + JspName
				+ "_extend.jsp");
		File basePage_extendFile = new File(basePage_extendPath);
		if (basePage_extendFile.exists()) {
			flag = true;
			return flag;
		} else {
			return flag;
		}

	}

	/**
	 *
	 * @param strDateStr
	 * @param splitStr
	 * @return
	 * @throws Exception
	 */
	public static String getSql2Date2(String dbType, String sDateStr, String splitStr) throws Exception {
		String strDateStr = sDateStr;
		if (StringUtil.isEmpty(sDateStr)) {
			return null;
		}
		if (strDateStr.indexOf("0000-00-00") >= 0) {
			return "null";
		}
		String strRet = "";

		if (dbType.equalsIgnoreCase("MYSQL") || dbType.equalsIgnoreCase("GBASE")) {
			strRet = "'" + strDateStr + "'";
		} else if (dbType.equalsIgnoreCase("DB2")) {
			strRet = "TIMESTAMP('" + strDateStr + "')";
		} else if (dbType.equalsIgnoreCase("ORACLE") || dbType.equalsIgnoreCase("POSTGRESQL")) {
			if ("-".equals(splitStr)) {
				strRet = "to_date('" + strDateStr + "','YYYY-mm-dd HH24:mi:ss')";
			} else {
				strRet = "to_date('" + strDateStr + "','YYYY/mm/dd HH24:mi:ss')";
			}
		} else if (dbType.equalsIgnoreCase("ACESS")) {
			strRet = "'" + strDateStr + "'";
		} else if (dbType.equalsIgnoreCase("SQLSERVER")) {
			if ("-".equals(splitStr)) {
				strRet = "convert(varchar(10), convert(datetime,'" + strDateStr + "'), 111)";
			} else {
				strRet = "CONVERT(Datetime,'" + strDateStr + "',20)";
			}
		} else if (dbType.equalsIgnoreCase("TERA")) {
			if ("-".equals(splitStr)) {
				strRet = "cast('" + strDateStr + "' as date FORMAT 'YYYY-MM-DD HH24:mi:ss')";
			} else {
				strRet = "cast('" + strDateStr + "' as date FORMAT 'YYYY/MM/DD HH24:mi:ss')";
			}
		} else if (dbType.equalsIgnoreCase("SYBASE")) {
			strRet = "cast('" + strDateStr + "' as Date)";
		} else {
			throw new Exception("can't get the current date of the function definition");
		}
		return strRet;
	}

	/**
	 * 将传入的SQL转换为符合VoltDB语法要求的SQL
	 * 1.将in替换为(column = v1) or (column = v2)....
	 * 2.去除sql中的常条件，如1=1等
	 * 3.去除select ~ from部分，VoltDB不支持子查询
	 * @param sql
	 * @return
	 */
	public static String convertToVoltdbSql(String sql, boolean isDeleteSelect) {
		String result = sql;
		//去除常条件
		String resultTmp = result.replaceAll("[and]*\\s+1=1\\s*[and]*", "");
		//替换in语句
		Pattern pattern1 = Pattern.compile("(\\s+[\\w|\\.]+\\s+[in|not in]+\\s*\\([\\w|\\d|,|'|\\s]+\\)\\s*)",
				Pattern.CASE_INSENSITIVE);
		Pattern pattern2 = Pattern.compile("\\s+([\\w|\\.]+)\\s+([in|not in]+)\\s*\\(([\\w|\\d|,|'|\\s]+)\\)\\s*",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher1 = pattern1.matcher(resultTmp);
		StringBuilder sb = new StringBuilder();
		result = resultTmp;
		while (matcher1.find()) {
			String condition = matcher1.group();
			Matcher matcher2 = pattern2.matcher(condition);
			matcher2.find();
			String column = matcher2.group(1);
			String opt = matcher2.group(2);
			String value = matcher2.group(3);
			sb.append("(");
			for (String val : value.split(",")) {
				if (sb.length() > 1) {
					if ("in".equalsIgnoreCase(opt)) {
						sb.append(" or ");
					} else {
						sb.append(" and ");
					}
				}
				if ("in".equalsIgnoreCase(opt)) {
					sb.append(column).append(" = ").append(val);
				} else {
					sb.append(column).append(" != ").append(val);
				}
			}
			sb.append(")");
			result = result.replace(condition, sb.toString());
			sb.delete(0, sb.length());
		}
		//去除select
		if (isDeleteSelect) {
			Pattern pattern3 = Pattern.compile("(select\\s+[\\w|\\d|\\.|,\\s]+\\s+from)", Pattern.CASE_INSENSITIVE);
			Matcher matcher3 = pattern3.matcher(result);
			if (matcher3.find()) {
				result = result.replace(matcher3.group(), " ");
			}
		}
		return result;
	}

	/**
	 * html转义
	 * @param str
	 * @return
	 */
	public static String escape4Html(String str) {
		if (StringUtils.isNotEmpty(str)) {
			if (str.indexOf("\"") != -1) {
				str = str.replaceAll("\"", "&quot;");
			}
			if (str.indexOf("\'") != -1) {
				str = str.replaceAll("\'", "&apos;");
			}
		}
		return str;
	}

	/**
	 * 拼接数组数据
	 */
	public static String array2String(Object[] objs) {
		StringBuilder sb = new StringBuilder();
		if (objs != null) {
			for (Object obj : objs) {
				sb.append(object2String(obj)).append("|");
			}
		}
		return sb.toString();
	}

	/**
	 * 将指定数字转换为指定长度的字符串，不够补零，超出则直接返回给定的数字
	 */
	public static String formatNum(int num, int length) {
		StringBuilder sb = new StringBuilder();
		sb.append(num);
		int delta = length - sb.length();
		if (delta > 0) {
			for (int i = 0; i < delta; i++) {
				sb.insert(0, 0);
			}
		}
		return sb.toString();
	}

	/**
	 * 按日生成当前的文件序号：用于生成的文件需要添加序号的情况
	 * @param busiType：业务类型，区分其他业务
	 * @param length：指定序号长度，不足补零
	 * @return
	 * @throws Exception 
	 */
	public static synchronized String genCurrentFileNumByDay(String busiType, int length) throws Exception {
		String numStr = "";
		String filePath = Configure.getInstance().getProperty("SYS_COMMON_LOG_PATH");
		if (!filePath.endsWith(File.separator)) {
			filePath = filePath + File.separator;
		}
		filePath += "fileNum" + File.separator + busiType;
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		File[] files = file.listFiles();
		String currentDay = DateUtil.date2String(new Date(), "yyyyMMdd");
		if (files != null && files.length > 0) {
			files[0].delete();
			if (files[0].getName().startsWith(currentDay)) {
				numStr = files[0].getName();
				String nexNumStr = formatNum(Integer.valueOf(numStr.split("-")[1]) + 1, 3);
				new File(filePath + File.separator + currentDay + "-" + nexNumStr).createNewFile();
			}
		}
		if (StringUtil.isEmpty(numStr)) {
			new File(filePath + File.separator + currentDay + "-001").createNewFile();
			numStr = currentDay + "-000";
		}
		return numStr;
	}

	/**
	 * 获取用户归属的二级部门或分公司ID、如果当前用户已经归属二级部门或顶级部门，则直接返回否则递归查询上级部门
	 * @param deptId
	 * @return
	 */
	public static String getSecondDeptId(int deptId) {
		String deptIdStr = String.valueOf(deptId);
		IMpmUserPrivilegeService pService = SpringContext.getBean("mpmUserPrivilegeService",
				IMpmUserPrivilegeService.class);
		IUserCompany uc = pService.getUserCompanyById(String.valueOf(deptId));
		if (!(null == uc.getParentid() || uc.getParentid().intValue() == 1 || uc.getParentid().intValue() == -1 || uc
				.getParentid().intValue() == 0)) {
			deptIdStr = getSecondDeptId(uc.getParentid());
		}
		return deptIdStr;
	}
}