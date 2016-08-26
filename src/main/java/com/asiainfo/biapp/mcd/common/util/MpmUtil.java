package com.asiainfo.biapp.mcd.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.biframe.utils.string.StringUtil;

public class MpmUtil {
	private static Logger log = LogManager.getLogger();

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
}