package com.asiainfo.biapp.mcd.kpi.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class KpiUtil {
	
	private static final Logger log = LogManager.getLogger(KpiUtil.class);
	
	/**
	 * 营销评估 数据库地市 渠道 全部默认值为999
	 */
	public static final String DEFAULT_DATABASE_ID = "999";
	public static final String DATE_TYPE_DAY = "DAY";
	public static final String DATE_TYPE_MONTH = "MONTH";
	public static final String CITY_ALL = "";
	public static final String CHANNEL_ALL = "-1";
	public static final String CHANNEL_ALL_1 = "999";
	/**
	 * List转Map
	 * @param list
	 * @param key Map主键
	 * @param valueKey Map值的key
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> listToMap(List list,String key ,String valueKey){
		Map remap = Maps.newHashMap();
		if(null != list){
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String keystr =  map.get(key).toString();
				String valuestr =  map.get(valueKey).toString();
				remap.put(keystr, valuestr);
			}
		}
		return remap;
	}
	/**
	 * 处理成千分位
	 * @param number
	 * @param pattern   ",###,###"||",###,###.00"
	 * @return
	 */
	public static String parseMoney(String number,String pattern){
		
		if(StringUtils.isEmpty(pattern)){
			pattern = ",###,###";
		}
		 BigDecimal bd=new BigDecimal(number);
		 DecimalFormat df=new DecimalFormat(pattern);
		 return df.format(bd);
	}
	/**
	 * 小数乘以100处理成百分数 带百分号
	 * @param number
	 * @return
	 */
	public static String parsePercent(String number){
		 	double result=Double.valueOf(number);
	        DecimalFormat df = new DecimalFormat("0.00%");
	        return df.format(result);
	}
	/**
	 * 小数乘以100处理成百分数 不带百分号
	 * @param number
	 * @return
	 */
	public static String parsePercentNoUnit(String number){
		 	double result=Double.valueOf(number)*100;
	        DecimalFormat df = new DecimalFormat("0.00");
	        return df.format(result);
	}
	/**
	 * 取得当前月份前6个月的月份
	 * @param month
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getLast6Month(String month) {
		List list = Lists.newArrayList();
		try {
			Date date = DateUtils.parseDate(month, new String[] { "yyyy-MM" });
			for (int i = 0; i < 6; i++) {
				Date date1 = DateUtils.addMonths(date, -i);
				String datestr = DateFormatUtils.format(date1, "yyyyMM");
				list.add(datestr);
			}
		} catch (ParseException e) {
			log.info("getLast6Month error ---" + e);
		}
		return list;
	}
	
	public static void main(String[] args) throws Exception {
		String   aString = parseMoney("1123456789",null);
		System.out.println(aString);
		String vvvvString =parsePercent("0.12514");
		System.out.println(vvvvString);
		String vvvvString111 =parsePercentNoUnit("0.12514");
		System.out.println(vvvvString111);
		
		Date date =   DateUtils.parseDate("2015-12", new String[]{"yyyy-MM"});
		System.out.println(date);
		System.out.println(getLast6Month("2015-03"));
		
	}
}
