package com.asiainfo.biapp.mcd.kpi.dao.impl;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.kpi.dao.IKpiDao;
import com.asiainfo.biapp.mcd.kpi.util.KpiUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository("kpiDao")
public class KpiDaoImpl extends JdbcDaoBase  implements IKpiDao {
	private static final Logger log = LogManager.getLogger(KpiDaoImpl.class);

	@SuppressWarnings("rawtypes")
	public Map<String,String> getCityInfos(){
		List list = Lists.newArrayList();
		StringBuffer sql = new StringBuffer("");
		sql.append(" select  CITY_ID as id , CITY_NAME as name from DIM_PUB_CITY ");
		sql.append(" union all ");
		sql.append(" select  COUNTY_ID as id , COUNTY_NAME as name from DIM_PUB_COUNTY ");
		log.info("getCityInfos sql---getCityInfos" +sql.toString()  );
		list = this.getJdbcTemplate().queryForList(sql.toString());
		log.info("getCityInfos  return List size=" + list.size());
		Map<String,String> map = KpiUtil.listToMap(list, "id", "name");
		map.put("999", "省公司");
		return map;
	}
	
	public List<Map<String,Object>> getChildCityInfosByPid(String cityId){
		List<Map<String,Object>> relist = Lists.newArrayList();
		try {
			StringBuffer sql = new StringBuffer("");
			if ("999".equals(cityId)) {
				sql.append(" select  CITY_ID as id , CITY_NAME as name from DIM_PUB_CITY where CITY_ID !='0' ");
				sql.append(" order by CITY_ID asc ");
			}else {
				sql.append(" select  COUNTY_ID as id , COUNTY_NAME as name from DIM_PUB_COUNTY ");
				sql.append(" where CITY_ID ='").append(cityId).append("' ");
				sql.append(" order by COUNTY_ID asc ");
			}
			log.info("getChildCityInfosByPid sql---getChildCityInfosByPid" +sql.toString()  );
			relist = this.getJdbcTemplate().queryForList(sql.toString());
			log.info("getChildCityInfosByPid  return List size=" + relist.size());
		} catch (Exception e) {
			log.info("getChildCityInfosByPid error : " + e );
		}

		return relist;
	}
	
	public List<Map<String, Object>> getChannelInfos(){
		List<Map<String, Object>> list = Lists.newArrayList();
		StringBuffer sql = new StringBuffer("");
		sql.append("select CHANNELTYPE_ID as id, CHANNEL_NAME as name from mcd_dim_channel");
		log.info("getChannelInfos sql--getChannelInfos-" +sql.toString() );
		 list = this.getJdbcTemplate().queryForList(sql.toString());
		 log.info("getChannelInfos  return List size=" + list.size());
		return list;
	}

	private static final String C_999 = "999";
	public List<Map<String, Object>> getDayUseEffectList(String date, String cityId) {
		List<Map<String, Object>> list = Lists.newArrayList();
		try {
			String _GROUP_TAB = "DIM_PUB_CITY";
			String _GROUP_COL_ = "CITY_ID";
			String _GROUP_COL_NAME_ = "CITY_NAME";
			if (StringUtils.isNotEmpty(cityId) && !C_999.equals(cityId)) {
				_GROUP_TAB = "DIM_PUB_COUNTY";
				_GROUP_COL_ = "COUNTY_ID";
				_GROUP_COL_NAME_ = "COUNTY_NAME";
			}
			StringBuffer sb = new StringBuffer("SELECT ");
			sb.append(" T1.");
			sb.append(_GROUP_COL_NAME_);
			sb.append(" AREA_NAME, T2.AREA_ID, T2.VAL_CAMP VAL_CAMP, T2.VAL_SUC VAL_SUC,");
			sb.append(" T2.RATE RATE FROM ");
			sb.append("(SELECT T.");
			sb.append(_GROUP_COL_);
			sb.append(" AREA_ID, SUM(T.CAMP_USER_NUM) AS VAL_CAMP, SUM(T.CAMP_SUCC_NUM) AS VAL_SUC, CASE WHEN SUM(T.CAMP_USER_NUM)= 0 THEN 0 ELSE  SUM(T.CAMP_SUCC_NUM) / SUM(T.CAMP_USER_NUM) END AS RATE FROM ");
			sb.append(" MTL_EVAL_INFO_PLAN_D T WHERE 1 = 1 ");

			if (StringUtils.isNotEmpty(cityId) && !C_999.equals(cityId)) {
				sb.append(" AND T.CITY_ID = ");
				sb.append(cityId);
			}
			if (StringUtils.isNotEmpty(date)) {
				sb.append(" AND T.STAT_DATE = '");
				sb.append(date);
				sb.append("'");
			}
			sb.append(" GROUP BY T.");
			sb.append(_GROUP_COL_);
			sb.append(" ORDER BY T.");
			sb.append(_GROUP_COL_);
			sb.append(") T2, ");
			sb.append(_GROUP_TAB);
			sb.append(" T1");
			sb.append(" WHERE T2.AREA_ID");
			sb.append(" = T1.");
			sb.append(_GROUP_COL_);
			sb.append(" ORDER BY T2.AREA_ID ");

			list =  this.getJdbcTemplate().queryForList(sb.toString(), new Object[] { });
			log.info("getDayUseEffectList params--date:" + date +";cityId="+cityId );
			
			log.info("getDayUseEffectList params--sql:getDayUseEffectList=" + sb.toString() );
			log.info("getDayUseEffectList sql---" +sb.toString() +"----return List size=" +list.size() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * OverView页面月使用效果表格
	 */
	public List<Map<String, Object>> getMonthUseEffectList(String date, String cityId) {
		List<Map<String, Object>> list = Lists.newArrayList();
		try {
			String _GROUP_TAB = "DIM_PUB_CITY";
			String _GROUP_COL_ = "CITY_ID";
			String _GROUP_COL_NAME_ = "CITY_NAME";
			if (!C_999.equals(cityId)) {
				_GROUP_TAB = "DIM_PUB_COUNTY";
				_GROUP_COL_ = "COUNTY_ID";
				_GROUP_COL_NAME_ = "COUNTY_NAME";
			}
			
			StringBuffer sb = new StringBuffer("SELECT T1.");
			sb.append(_GROUP_COL_NAME_);
			sb.append(" AREA_NAME, T2.CITY_ID");
			sb.append(", T2.VAL_USER USER_NUM, T2.VAL_CAMP VAL_CAMP, T2.VAL_SUC VAL_SUC, T2.RATE RATE FROM");
			sb.append(" (SELECT T.");
			sb.append(_GROUP_COL_);
			sb.append(" CITY_ID, SUM(T.TARGET_USER_NUM) AS VAL_USER, SUM(T.CAMP_USER_NUM) AS VAL_CAMP, SUM(T.CAMP_SUCC_NUM) AS VAL_SUC, SUM(T.CAMP_SUCC_NUM) / SUM(T.CAMP_USER_NUM) AS RATE FROM ");
			sb.append("mtl_eval_info_plan_m T WHERE 1 = 1 ");
			if (StringUtils.isNotEmpty(cityId) && !C_999.equals(cityId)) {
				sb.append(" AND T.CITY_ID = ");
				sb.append(cityId);
			}
			if (StringUtils.isNotEmpty(date)) {
				sb.append(" AND T.STAT_DATE = '");
				sb.append(date);
				sb.append("'");
			}
			sb.append(" GROUP BY T.");
			sb.append(_GROUP_COL_);
			sb.append(" ORDER BY T.");
			sb.append(_GROUP_COL_);
			sb.append(") T2, ");
			sb.append(_GROUP_TAB);
			sb.append(" T1 WHERE T2.CITY_ID");
			sb.append(" = T1.");
			sb.append(_GROUP_COL_);
			sb.append(" ORDER BY T2.CITY_ID ");

			log.info("getMonthUseEffectList params--date:" + date +";cityId="+cityId );
			log.info("getMonthUseEffectList sql---getMonthUseEffectList=" + sb.toString());
			list = this.getJdbcTemplate().queryForList(sb.toString(), new Object[]{ });
			log.info("getMonthUseEffectList  return List size=" + list.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 *  近6个月营销成功用户及成功率趋势
	 * @return
	 */
	public List<Map<String, Object>> getMonthsHisEffectList(List<String> months, String cityId) throws Exception {
		List<Map<String, Object>> list = Lists.newArrayList();
		StringBuffer sb = new StringBuffer("SELECT T.STAT_DATE, SUM(T.CAMP_USER_NUM) CAMPNUM,");
		sb.append(" SUM(T.CAMP_SUCC_NUM) SUCNUM, CASE WHEN SUM(T.CAMP_USER_NUM)=0 THEN 0 ELSE SUM(T.CAMP_SUCC_NUM) / SUM(T.CAMP_USER_NUM) END SUCRATE ");
		sb.append(" FROM MTL_EVAL_INFO_PLAN_M T WHERE 1=1 ");
		List<String> params =  Lists.newArrayList();
		String datesql = " AND T.STAT_DATE IN ( ";
		for (int i = 0; i < months.size(); i++) {
			datesql += " ?, ";
			params.add(months.get(i));
		}
		datesql = datesql.substring(0, datesql.lastIndexOf(","));
		datesql += " ) ";
		sb.append(datesql);
		if (StringUtils.isNotEmpty(cityId) && !C_999.equals(cityId)) {
			sb.append(" AND CITY_ID = ?");
			params.add(cityId);
		}
		sb.append("  GROUP BY  STAT_DATE ORDER BY  STAT_DATE");
		list =  this.getJdbcTemplate().queryForList(sb.toString(), params.toArray());
		log.info("getMonthsHisEffectList  return List size=" + list.size());
		log.info("getMonthsHisEffectList  sql--getMonthsHisEffectList=" + sb.toString());
		return list;
	}
	
	/**
	 *  某月营销成功用户渠道分布
	 * @return
	 */
	public List<Map<String, Object>> getSuccesChanneltDisList(String month, String cityId) {
		List<Map<String, Object>> list = Lists.newArrayList();
		try {
			StringBuffer sqlStr = new StringBuffer("SELECT T1.CHANNEL_NAME CHANNEL_NAME, T2.VAL VAL FROM (SELECT T.CHANNEL_ID, SUM(T.CAMP_SUCC_NUM) AS VAL FROM mtl_eval_info_plan_m T WHERE 1 = 1");
			if (StringUtils.isNotEmpty(cityId) && !C_999.equals(cityId)) {
				sqlStr.append(" AND T.CITY_ID = ");
				sqlStr.append(cityId);
			}
			if (StringUtils.isNotEmpty(month)) {
				sqlStr.append(" AND T.STAT_DATE = ");
				sqlStr.append(month);
			}
			sqlStr.append("   GROUP BY T.CHANNEL_ID ORDER BY T.CHANNEL_ID) T2, MCD_DIM_CHANNEL T1 WHERE T2.CHANNEL_ID = T1.CHANNEL_ID");
			 list = this.getJdbcTemplate().queryForList(sqlStr.toString(), new Object[]{ });
			 log.info("getMonthUseEffectList  return List size=" + list.size());
			 log.info("getMonthUseEffectList  sql=getSuccesChanneltDisList=" + sqlStr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 *  某月某渠道执行情况
	 * @param channelId
	 * @return
	 */
	public List<Map<String, Object>> getChannelExec1List(String month, String cityId, String channelId) {
		List<Map<String, Object>> list = Lists.newArrayList();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT T.STAT_DATE, SUM(T.CAMP_USER_NUM) CAMPNUM, SUM(T.TARGET_USER_NUM) TARNUM, ");
			sb.append(" SUM(T.BOTHER_AVOID_USER_NUM) BOTHERAVOIDNUM, SUM(T.FRE_CTL_USER_NUM) FRENUM, ");
			sb.append(" SUM(T.NO_TOUCH_USER_NUM) NOTOUCHNUM, SUM(T.CAMP_SUCC_NUM) SUCNUM, SUM(T.CAMP_FAIL_USER_NUM) FAILNUM, ");
			sb.append(" CASE WHEN SUM(T.CAMP_USER_NUM)=0 THEN 0 ELSE  SUM(T.CAMP_SUCC_NUM) / SUM(T.CAMP_USER_NUM)  END SUCRATE, ");
			sb.append(" CASE WHEN SUM(T.ORDER_USER_NUM)=0 THEN 0 ELSE SUM(T.CAMP_SUCC_NUM) / SUM(T.ORDER_USER_NUM) END CVRATE ");
			sb.append(" FROM MTL_EVAL_INFO_SUM_M T WHERE 1 = 1  ");
			if (StringUtils.isNotEmpty(month)) {
				sb.append(" AND T.STAT_DATE = '");
				sb.append(month);
				sb.append("'");
			}
			if (StringUtils.isNotEmpty(channelId) && !"-1".equals(channelId)) {
				sb.append(" AND T.CHANNEL_ID = ");
				sb.append(channelId);
			}
			if (StringUtils.isNotEmpty(cityId) && !C_999.equals(cityId)) {
				sb.append(" AND T.CITY_ID = ");
				sb.append(cityId);
			}
			sb.append(" GROUP BY T.STAT_DATE ORDER BY T.STAT_DATE");
			
			list =  this.getJdbcTemplate().queryForList(sb.toString(), new Object[] { });
			log.info("getMonthUseEffectList  return List size=" + list.size());
			log.info("getMonthUseEffectList  sql=getChannelExec1List=" +sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 *  某月某渠道成功用户趋势图
	 * @param channelId
	 * @return
	 */
	public List<Map<String, Object>> getChannelExec2List(List<String> months ,String cityId ,String channelId) {
		List<Map<String, Object>> list = Lists.newArrayList();
		try {
			StringBuffer sb = new StringBuffer("SELECT T.STAT_DATE, SUM(T.CAMP_SUCC_NUM) SUCNUM, SUM(CAMP_SUCC_NUM) / SUM(CAMP_USER_NUM) SUCRATE FROM mtl_eval_info_plan_m T WHERE 1=1 ");
			List<String> params =  Lists.newArrayList();
			String datesql = " AND T.STAT_DATE IN ( ";
			for (int i = 0; i < months.size(); i++) {
				datesql += " ?, ";
				params.add(months.get(i));
			}
			datesql = datesql.substring(0, datesql.lastIndexOf(","));
			datesql += " ) ";
			sb.append(datesql);
			if (StringUtils.isNotEmpty(channelId) && !"-1".equals(channelId)) {
				sb.append(" AND T.CHANNEL_ID = ?");
				params.add(channelId);
			}
			if (StringUtils.isNotEmpty(cityId) && !C_999.equals(cityId)) {
				sb.append(" AND T.CITY_ID = ?");
				params.add(cityId);
			}
			sb.append(" GROUP BY T.STAT_DATE ORDER BY T.STAT_DATE");
			list =  this.getJdbcTemplate().queryForList(sb.toString(), params.toArray());
			log.info("getMonthsHisEffectList  return List size=" + list.size());
			log.info("getMonthUseEffectList  sql=getChannelExec2List=" +sb.toString());
			
		} catch (Exception e) {
			log.info("getMonthsHisEffectList error : " + e );
		}
		return list;
	}
	/**
	 *  某月某渠道成功率趋势图
	 * @param channelId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String,Object>> getChannelExec3List(List months ,String cityId ,String channelId){
		List<Map<String,Object>> list = Lists.newArrayList();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select * from  MTL_EVAL_INFO_SUM_M where 1=1 ");
			List<Object> params =  Lists.newArrayList();
			String datesql = " and stat_date in ( ";
			for (int i = 0; i < months.size(); i++) {
				datesql += " ? , ";
				params.add(months.get(i));
			}
			datesql = datesql.substring(0, datesql.lastIndexOf(","));
			datesql += " ) ";
			sql.append(datesql);
			sql.append(" and CITY_ID = ? ");
			sql.append(" and COUNTY_ID = '").append(KpiUtil.DEFAULT_DATABASE_ID).append("' ");
			sql.append(" and CHANNEL_ID = ?  order by  stat_date asc");
			params.add(cityId);
			params.add(channelId);
			log.info("getMonthsHisEffectList params--:" + params );
			log.info("getMonthsHisEffectList sql---getChannelExec3List=" +sql.toString()  );
			list =  this.getJdbcTemplate().queryForList(sql.toString(),params.toArray());
			log.info("getMonthsHisEffectList return size=" + list.size());
		} catch (Exception e) {
			log.info("getMonthsHisEffectList error : " + e );
		}
		
		return list;
	}
	
	/**营销政策效果tabList查询
	 * @param dateType 周期类型
	 * @param date 日期或者月份
	 * @param cityId 地市Id
	 * @param channelId 渠道ID
	  * @param tactics 政策编号名称like
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getPolicyEffectList(String dateType,String date,String cityId,String countryId,String channelId ,String tactics,Pager pager){
		
		List list = Lists.newArrayList();
		try {
			List params = Lists.newArrayList();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("  A.PLAN_ID, ");
			sql.append("  SUM(A.TARGET_USER_NUM) TARGET_USER_NUM, ");
			sql.append("  SUM(A.CAMP_USER_NUM) CAMP_USER_NUM, ");
			sql.append("  SUM(A.CAMP_SUCC_NUM) CAMP_SUCC_NUM, ");
			sql.append("  SUM(A.TOTAL_ORDER_NUM) TOTAL_ORDER_NUM, ");
			sql.append("  SUM(A.CAMP_USER_NUMS) PV_NUM, ");
			sql.append("  SUM(A.CK_NUM) CK_NUM, ");
			sql.append("  CASE WHEN SUM(A.CAMP_USER_NUM)=0 THEN 0 ELSE SUM(A.CAMP_SUCC_NUM)/SUM(A.CAMP_USER_NUM) END CAMP_SUCC_RATE, ");
			sql.append("  CASE WHEN SUM(A.TOTAL_ORDER_NUM)=0 THEN 0 ELSE SUM(A.CAMP_SUCC_NUM)/SUM(A.TOTAL_ORDER_NUM) END USER_COVERAGE_RATE, ");
			sql.append("  B.PLAN_NAME ");
			sql.append(" FROM ");

			if(KpiUtil.DATE_TYPE_DAY.equalsIgnoreCase(dateType)){//日
				sql.append(" mtl_eval_info_plan_d");
			}else {//月
				sql.append(" mtl_eval_info_plan_m");
			}

			sql.append(" A INNER JOIN mtl_stc_plan B ON A.PLAN_ID = B.PLAN_ID ");
			sql.append(" WHERE 1 = 1 AND A.stat_date = ? ");
			params.add(date);

			if(StringUtils.isNotEmpty(cityId)&& !C_999.equals(cityId)){
				sql.append(" and A.CITY_ID = ?  ");
				params.add(cityId);
			}
			if(StringUtils.isNotEmpty(channelId) && !"-1".equals(channelId)){//
				sql.append(" and a.channel_id = ?  ");
				params.add(channelId);
			}

			if(StringUtils.isNotEmpty(tactics)){
				sql.append(" and ( B.PLAN_ID like   '%'|| ? ||'%' or B.PLAN_NAME LIKE '%'|| ? ||'%')   ");//政策ID
				params.add(tactics);
				params.add(tactics);
			}
			sql.append(" GROUP BY a.PLAN_ID,b.PLAN_NAME ");
			sql.append(" ORDER BY CASE WHEN SUM(A.CAMP_USER_NUM) = 0 THEN 0 ELSE SUM(A.CAMP_SUCC_NUM) / SUM(A.CAMP_USER_NUM) END DESC, SUM(A.CAMP_SUCC_NUM) DESC");

			String sqlExt = DataBaseAdapter.getPagedSql(sql.toString(), pager.getPageNum(),pager.getPageSize());
			log.info("getPolicyEffectList params: dateType="+dateType +";date="+date+";cityId=" + cityId +";channelId="+channelId +";countryId="+countryId+";likeName"
					+ ";pager="+pager);
			log.info("getPolicyEffectList sqlExt page query sql = getPolicyEffectList:" + sqlExt.toString() );
			log.info("getPolicyEffectList sql list query sql =getPolicyEffectList:" + sql.toString());
			list = this.getJdbcTemplate().queryForList(sqlExt.toString(), params.toArray());
			List listSize = this.getJdbcTemplate().queryForList(sql.toString(), params.toArray());
			pager.setTotalSize(listSize.size());  // 总记录数
			log.info("getPolicyEffectList qlExt  query  teturn 总条数ListSize=" + + listSize.size());
			log.info("getPolicyEffectList sql  query  teturn 分页返回条数ListSize=" + + list.size());
		} catch (Exception e) {
			log.info("getPolicyEffectList error " + e);
		}

		return list;
	}
	/**营销渠道效果效果tabList查询
	 * @param dateType 周期类型
	 * @param date 日期或者月份
	 * @return
	 */
	public List<Map<String,Object>> getChannelEffectList(String dateType,String date ,String cityId, String tactics, Pager pager){
		List<Map<String,Object>> list = Lists.newArrayList();
		try {
			StringBuffer sql = new StringBuffer();
			List<String> params = Lists.newArrayList();
			sql.append("");
			sql.append("SELECT");
			sql.append("  A.CHANNEL_ID,");
			sql.append("  SUM(A.CAMP_USER_NUM) CAMP_USER_NUM,");
			sql.append("  SUM(A.CAMP_SUCC_NUM) CAMP_SUCC_NUM,");
			sql.append("  SUM(A.TOTAL_ORDER_NUM) TOTAL_ORDER_NUM,");
			sql.append("  CASE WHEN SUM(A.CAMP_USER_NUM) = 0");
			sql.append("    THEN 0");
			sql.append("  ELSE SUM(A.CAMP_SUCC_NUM) / SUM(A.CAMP_USER_NUM) END CAMP_SUCC_RATE,");
			sql.append("  CASE WHEN SUM(A.TOTAL_ORDER_NUM) = 0");
			sql.append("    THEN 0");
			sql.append("  ELSE SUM(A.CAMP_SUCC_NUM) / SUM(A.TOTAL_ORDER_NUM) END USER_COVERAGE_RATE,");
			sql.append("  B.CHANNEL_NAME AS CHANNEL_NAME");
			sql.append(" FROM ");
			if(KpiUtil.DATE_TYPE_DAY.equalsIgnoreCase(dateType)){//日
				sql.append(" MTL_EVAL_INFO_PLAN_D ");
			}else {//月
				sql.append(" MTL_EVAL_INFO_PLAN_M ");
			}
			sql.append(" A INNER JOIN mcd_dim_channel B ON A.CHANNEL_ID = B.CHANNELTYPE_ID");
			sql.append("  INNER JOIN MTL_STC_PLAN C ON A.PLAN_ID = C.PLAN_ID ");
			sql.append(" WHERE 1 = 1 AND A.STAT_DATE = ? ");
			params.add(date);
			if(StringUtils.isNotEmpty(cityId) && !C_999.equals(cityId)){
				sql.append(" and a.city_id = ?  ");//city
				params.add(cityId);
			}
			if(StringUtils.isNotEmpty(tactics)){
				sql.append(" and ( C.PLAN_ID like   '%'|| ? ||'%' or C.PLAN_NAME LIKE '%'|| ? ||'%')   ");//政策ID
				params.add(tactics);
				params.add(tactics);
			}
			sql.append("GROUP BY A.CHANNEL_ID,B.CHANNEL_NAME ");


			String sqlExt = DataBaseAdapter.getPagedSql(sql.toString(), pager.getPageNum(),pager.getPageSize());
			log.info("getChannelEffectList params: "+params + ";pager="+pager);
			log.info("getChannelEffectList sqlExt page query sql = " + sqlExt.toString());
			log.info("getChannelEffectList sql list query sql = " + sql.toString());
			list = this.getJdbcTemplate().queryForList(sqlExt.toString(), params.toArray());
			List<Map<String,Object>> listSize = this.getJdbcTemplate().queryForList(sql.toString(), params.toArray());
			pager.setTotalSize(listSize.size());  // 总记录数
			log.info("getChannelEffectList qlExt  query  teturn 总条数ListSize=" + + listSize.size());
			log.info("getChannelEffectList sql  query  teturn 分页返回条数ListSize=" + + list.size());
		} catch (Exception e) {
			log.info("getChannelEffectList error " + e);
		}

		return list;
	}
	/**
	 * 一线人员效果 tabList 查询
	 * @param dateType
	 * @param date
	 * @param cityId
	 * @param channelId
	 * @param pager
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getFrontLineEffectList(String dateType,String date ,String cityId,String countryId,String channelId,String org ,Pager pager){
		List list = Lists.newArrayList();
		try {
			List params = Lists.newArrayList();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT");
			sql.append("  A.ORG_ID,");
			sql.append("  SUM(A.CAMP_USER_NUM) CAMP_USER_NUM,");
			sql.append("  SUM(A.CAMP_SUCC_NUM) CAMP_SUCC_NUM,");
			sql.append("  CASE WHEN SUM(A.CAMP_USER_NUM) = 0");
			sql.append("    THEN 0");
			sql.append("  ELSE SUM(A.CAMP_SUCC_NUM) / SUM(A.CAMP_USER_NUM) END CAMP_SUCC_RATE,");
			sql.append("  B.CHANNEL_NAME ORG_NAME");
			sql.append(" FROM ");
			if(KpiUtil.DATE_TYPE_DAY.equalsIgnoreCase(dateType)){//日
				sql.append(" MTL_EVAL_INFO_CHN_ORG_D ");
			}else {//月
				sql.append(" MTL_EVAL_INFO_CHN_ORG_M ");
			}
			sql.append(" A INNER JOIN DW_CHNL_DEPT B ON CAST(B.CHANNEL_ID AS VARCHAR2(100)) = A.ORG_ID");
			sql.append(" WHERE 1 = 1 AND A.STAT_DATE = ? ");
			params.add(date);
			if(StringUtils.isNotEmpty(cityId) && !C_999.equals(cityId)){
				sql.append(" and b.city_id = ?  ");//city
				params.add(cityId);
			}
			if(StringUtils.isNotEmpty(countryId) && !"-1".equals(countryId)){
				sql.append(" and b.county_id = ?  ");//city
				params.add(countryId);
			}
			if(StringUtils.isNotEmpty(channelId) && !"-1".equals(channelId)){
				sql.append(" and a.channel_id = ?  ");
				params.add(channelId);
			}
			if(StringUtils.isNotEmpty(org)){
				sql.append(" and ( a.org_id like   '%'|| ? ||'%' or b.channel_name LIKE '%'|| ? ||'%')   ");//组织id 组织name
				params.add(org);
				params.add(org);
			}
			sql.append(" GROUP BY A.ORG_ID, B.CHANNEL_NAME ");
			sql.append(" ORDER BY CASE WHEN SUM(A.CAMP_USER_NUM) = 0 THEN 0 ELSE SUM(A.CAMP_SUCC_NUM) / SUM(A.CAMP_USER_NUM) END DESC, SUM(A.CAMP_SUCC_NUM) DESC");

			String sqlExt = DataBaseAdapter.getPagedSql(sql.toString(), pager.getPageNum(),pager.getPageSize());
			log.info("getFrontLineEffectList params: =" +params+ ";pager="+pager);
			log.info("getFrontLineEffectList sqlExt page query sql = " + sqlExt.toString() );
			log.info("getFrontLineEffectList sql list query sql = " + sql.toString());
			list = this.getJdbcTemplate().queryForList(sqlExt.toString(), params.toArray());
			List listSize = this.getJdbcTemplate().queryForList(sql.toString(), params.toArray());
			pager.setTotalSize(listSize.size());  // 总记录数
			log.info("getFrontLineEffectList qlExt  query  teturn 总条数ListSize=" + + listSize.size());
			log.info("getFrontLineEffectList sql  query  teturn 分页返回条数ListSize=" + + list.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("getFrontLineEffectList error " + e);
		}

		return list;
	}
	/**
	 * 一线人员效果 详情页查询
	 * @param dateType
	 * @param date
	 * @param orgId
	 * @param channelId
	 * @return
	 */
	public List<Map<String,Object>> getFrontLineEffectDetailList(String dateType,String date ,String orgId ,String channelId  ,String user,Pager pager){
		List<Map<String,Object>> list = Lists.newArrayList();
		try {
			List<String> params = Lists.newArrayList();
			String tableName = "";
			if(KpiUtil.DATE_TYPE_DAY.equalsIgnoreCase(dateType)){
				tableName = "MTL_EVAL_INFO_CHN_USER_D" ;//渠道效果人员评估（每日）表名
			}else {
				tableName = "MTL_EVAL_INFO_CHN_USER_M" ;//渠道效果人员评估（每日）表名
			}
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT");
			sql.append("  A.USER_ID,");
			sql.append("  SUM(A.CAMP_USER_NUM) CAMP_USER_NUM,");
			sql.append("  SUM(A.CAMP_SUCC_NUM) CAMP_SUCC_NUM,");
			sql.append("  CASE WHEN SUM(A.CAMP_USER_NUM) = 0");
			sql.append("    THEN 0");
			sql.append("  ELSE SUM(A.CAMP_SUCC_NUM) / SUM(A.CAMP_USER_NUM) END   CAMP_SUCC_RATE,");
			sql.append("  B.OP_NAME USER_NAME");
			sql.append(" FROM  ");
			sql.append(tableName);
			sql.append(" A INNER JOIN dwd_sys_oper B");
			sql.append("    ON A.USER_ID = cast(B.LOGIN_NAME AS VARCHAR2(100)) OR A.USER_ID = cast(B.OP_ID AS VARCHAR2(100))");
			sql.append(" WHERE 1=1 and A.stat_date = ? ");
			params.add(date);

			if(StringUtils.isNotEmpty(channelId) && !"-1".equals(channelId)){
				sql.append(" and A.CHANNEL_ID =? ");
				params.add(channelId);
			}

			if(StringUtils.isNotEmpty(orgId)){
				sql.append(" and A.ORG_ID =? ");
				params.add(orgId);
			}
			if(StringUtils.isNotEmpty(user)){
				sql.append(" and (  cast(B.op_id as varchar2(100))  like   '%'|| ? ||'%' 	 OR B.OP_NAME LIKE '%'|| ? ||'%')   ");//员工id 员工name
				params.add(user);
				params.add(user);
			}
			sql.append(" GROUP BY A.USER_ID,B.OP_NAME");

			String sqlExt = DataBaseAdapter.getPagedSql(sql.toString(), pager.getPageNum(),pager.getPageSize());
			log.info("getFrontLineEffectDetailList params: ="+params+ ";pager="+pager);
			log.info("getFrontLineEffectDetailList sqlExt page query sql = " + sqlExt.toString() );
			log.info("getFrontLineEffectDetailList sql list query sql = " + sql.toString());
			list = this.getJdbcTemplate().queryForList(sqlExt.toString(), params.toArray());
			List<Map<String,Object>> listSize = this.getJdbcTemplate().queryForList(sql.toString(), params.toArray());
			pager.setTotalSize(listSize.size());  // 总记录数
			log.info("getFrontLineEffectDetailList qlExt  query  teturn 总条数ListSize=" + + listSize.size());
			log.info("getFrontLineEffectDetailList sql  query  teturn 分页返回条数ListSize=" + + list.size());
		} catch (Exception e) {
			log.info("getFrontLineEffectDetailList error " + e);
		}

		return list;
	}
	
	
	//营销政策详情页 方法 begin
	public List<Map<String,Object>> getPolicySuccessChannelList(String dateType,String date,String planId ,String cityId ){
		List<Map<String,Object>> list = Lists.newArrayList();
		try {
			List<String> params = Lists.newArrayList();
			StringBuffer sql = new StringBuffer();
			if(KpiUtil.DATE_TYPE_DAY.equalsIgnoreCase(dateType)){//日
				sql.append(" select A.* ,B.CHANNEL_NAME as channel_name from mtl_eval_info_plan_d  A ");
			}else {//月
				sql.append(" select A.* ,B.CHANNEL_NAME  as channel_name from mtl_eval_info_plan_m  A ");
			}
			sql.append(" INNER JOIN mcd_dim_channel B ON A.CHANNEL_ID = B.CHANNELTYPE_ID ");
			sql.append(" where A.stat_date =? ");
			sql.append(" and A.PLAN_ID =? ");
			sql.append(" and A.CITY_ID =? ");
			sql.append(" and A.COUNTY_ID =  '").append(KpiUtil.DEFAULT_DATABASE_ID).append("' ");
			sql.append(" and A.CHANNEL_ID != '").append(KpiUtil.DEFAULT_DATABASE_ID).append("' ");
			params.add(date);
			params.add(planId);
			params.add(cityId);
			log.info("getPolicySuccessChannelList params="+params + ":sql="+sql.toString());
			list = this.getJdbcTemplate().queryForList(sql.toString(), params.toArray());
		} catch (Exception e) {
			log.info("getPolicySuccessChannelList error " + e);
		}

		return list;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getPolicyInfo(String planId,String cityId ,Pager pager){
		List list = Lists.newArrayList();
		List cityList = this.getChildCityInfosByPid(cityId);
		try {
			List params = Lists.newArrayList();
			StringBuffer sql = new StringBuffer();
			sql.append(" select A.* ,B.campseg_id, B.campseg_name , C.CUSTGROUP_NAME from mtl_stc_plan  A ");
			sql.append(" LEFT JOIN  mtl_camp_seginfo B ON A.PLAN_ID = B.PLAN_ID ");
			sql.append(" LEFT JOIN MTL_CAMPSEG_CUSTGROUP C ON  B.CAMPSEG_ID = C.CAMPSEG_ID ");
			sql.append(" where A.PLAN_ID = ? ");
			params.add(planId);
			String datesql = " and B.CITY_ID in ( ";
			for (int i = 0; i < cityList.size(); i++) {
				datesql += " ? , ";
				Map map = (Map) cityList.get(i);
				params.add(map.get("ID").toString());
			}
			datesql = datesql.substring(0, datesql.lastIndexOf(","));
			datesql += " ) ";
			sql.append(datesql);
			String sqlExt = DataBaseAdapter.getPagedSql(sql.toString(), pager.getPageNum(),pager.getPageSize());
			log.info("getPolicyInfo params: ="+params+ ";pager="+pager);
			log.info("getPolicyInfo sqlExt page query sql = " + sqlExt.toString() );
			log.info("getPolicyInfo sql list query sql = " + sql.toString()+ ";总条数=" );
			list = this.getJdbcTemplate().queryForList(sqlExt.toString(), params.toArray());
			List listSize = this.getJdbcTemplate().queryForList(sql.toString(), params.toArray());
			log.info("getPolicyInfo qlExt  query  teturn 总条数ListSize=" + + listSize.size());
			log.info("getPolicyInfo sql  query  teturn 分页返回条数ListSize=" + + list.size());
			pager.setTotalSize(listSize.size());  // 总记录数
		} catch (Exception e) {
			log.info("getPolicyInfo error " + e);
		}
		return list;
	}
	
	/**
	 * 获取指标定义
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getKpiDefineInfos(List kpiIds){
		List list = Lists.newArrayList();
		try {
			List params = Lists.newArrayList();
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from  DIM_EVAL_INDEX_INFO  where 1=1 ");
			String datesql = " and INDEX_ID in ( ";
			for (int i = 0; i < kpiIds.size(); i++) {
				datesql += " ? , ";
				params.add(kpiIds.get(i));
			}
			datesql = datesql.substring(0, datesql.lastIndexOf(","));
			datesql += " ) ";
			sql.append(datesql);
			log.info("getKpiDefineInfos params="+params + " sql="+ sql.toString());
			list = this.getJdbcTemplate().queryForList(sql.toString(), params.toArray());
		} catch (Exception e) {
			log.info("getKpiDefineInfos error " + e);
		}
		return list;
	}
	
	/**
	 * 查询全网用户数
	 * @param dateType
	 * @param date
	 * @param cityId
	 * @return
	 */
	public Map<String,Object> getAllUserNum(String dateType ,String date ,String cityId){
		Map<String,Object> map = Maps.newHashMap();
		try {
			List<String> params = Lists.newArrayList();
			StringBuffer sql = new StringBuffer();
			if(KpiUtil.DATE_TYPE_DAY.equalsIgnoreCase(dateType)){//日
				sql.append(" select *  from mtl_eval_info_plan_user_num_d   ");
			}else {//月
				sql.append(" select *  from mtl_eval_info_plan_user_num_m  ");
			}
			sql.append(" where stat_date = ? ");
			sql.append(" and city_id = ? ");
			params.add(date);
			params.add(cityId);
			log.info("getAllUserNum params="+params + ":sql="+sql.toString());
			List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql.toString(), params.toArray());
			log.info("getAllUserNum  return List size=" + list.size());
			if(list.size()>0){
				map = (Map<String,Object>) list.get(0);
			}
		} catch (Exception e) {
			log.info("getAllUserNum error " + e);
		}
		return map;
	}
	
	/**
	 * 查询政策效果
	 * @param dateType
	 * @param date
	 * @param planId
	 * @param cityId
	 * @param countryId
	 * @param channelId
	 * @return
	 */
	public List<Map<String,Object>> getPolicyExecInfo(String dateType , String date ,String planId,  String cityId ,
			String countryId,String channelId){
		List<Map<String,Object>> list = Lists.newArrayList();
		try {
			List<String> params = Lists.newArrayList();
			StringBuffer sql = new StringBuffer();
			if(KpiUtil.DATE_TYPE_DAY.equalsIgnoreCase(dateType)){//日
				sql.append(" select *  from mtl_eval_info_plan_d   ");
			}else {//月
				sql.append(" select *  from mtl_eval_info_plan_m  ");
			}
			sql.append(" where stat_date = ? ");
			sql.append(" and plan_id = ? ");
			params.add(date);
			params.add(planId);
			if(StringUtils.isNotEmpty(cityId) && !C_999.equals(cityId)){
				sql.append(" and city_id = ? ");
				params.add(cityId);
			}
			if(StringUtils.isNotEmpty(channelId) && !"-1".equals(channelId)){
				sql.append(" and channel_id = ? ");
				params.add(channelId);
			}


			list = this.getJdbcTemplate().queryForList(sql.toString(), params.toArray());
			log.info("getPolicyExecInfo params="+params + ":sql="+sql.toString());
		} catch (Exception e) {
			log.info("getPolicyExecInfo error " + e);
		}
		return list;
	}
	
	public static  String getRandomString(int count){
		double d= Math.random()*count;
		BigDecimal   b   =   new   BigDecimal(d);  
		double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		return String.valueOf(f1);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map<String, Object>> getMonthsHisEffectList(List months, String cityId,
			String countryId, String channelId) {
		return null;
	}
}
