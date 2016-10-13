package com.asiainfo.biapp.mcd.home.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.home.dao.ISaleSituationDao;
import com.asiainfo.biapp.mcd.home.vo.CampChannel;
import com.asiainfo.biapp.mcd.home.vo.MySale;
import com.asiainfo.biapp.mcd.home.vo.RecommendCamp;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;

@Repository(value="saleSituationDao")
public class SaleSituationDaoImpl extends JdbcDaoBase implements ISaleSituationDao {
	private static Logger log = LogManager.getLogger();
	
	// 查询总营销数
	@Override
	public long getTotalSale(String cityId) throws Exception {
		long num = 0;
		String monthFirstDay = QuotaUtils.getDayDate("yyyyMM")+"01";
		String today = QuotaUtils.getDayDate("yyyyMMdd");
		StringBuffer sql = new StringBuffer();

		sql.append("select sum(dd) from (");
		sql.append(" select t1.city_id as city_id,sum(t1.CAMP_USER_NUM) as dd from mtl_eval_info_plan_m t1 ");
		sql.append(" group by t1.CITY_ID");
		sql.append(" union all ");
		sql.append(" select t2.city_id as city_id,sum(t2.CAMP_USER_NUM) as dd from mtl_eval_info_plan_d t2 ");
		sql.append(" where t2.stat_date>=? and t2.stat_date<=? ");
		sql.append(" group by t2.city_id )");
		if(StringUtils.isNotEmpty(cityId) && !"999".equals(cityId)){
			sql.append(" where city_id='"+cityId+"'");
		}

		log.debug("getTotalSale执行sql=" + sql);
		try {
			num = this.getJdbcTemplate().query(sql.toString(),new String[]{monthFirstDay,today}, this.longResultSetExtractor);
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.info("查询数据异常！！！");
            throw new Exception("");
		}
		return num;
	}
	
	// 查询总成功数据
	@Override
	public long getTotalSuccess(String cityId) {
		String monthFirstDay = QuotaUtils.getDayDate("yyyyMM")+"01";
		String today = QuotaUtils.getDayDate("yyyyMMdd");
		long num = 0;
		StringBuffer sql = new StringBuffer();

		sql.append("select sum(dd) from (");
		sql.append(" select CITY_ID,sum(CAMP_SUCC_NUM) dd from mtl_eval_info_plan_m t ");
		sql.append(" group by city_id");
		sql.append(" union all ");
		sql.append(" select CITY_ID,sum(CAMP_SUCC_NUM) dd  from mtl_eval_info_plan_d t  ");
		sql.append(" where t.stat_date>=? and  t.stat_date<=? group by city_id)");

		if(StringUtils.isNotEmpty(cityId) && !"999".equals(cityId)){
			sql.append(" where city_id='"+cityId+"'");
		}

		log.debug("getTotalSuccess执行sql=" + sql);
		try {
			num = this.getJdbcTemplate().query(sql.toString(),new String[]{monthFirstDay,today}, this.longResultSetExtractor);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return num;
	}

	// 本月总营销数：从日表累计截止到昨天的总额
	@Override
	public long getTotalSaleMonth(String cityId) {
		Long num = 0l;
		String monthFirstDay= QuotaUtils.getDayDate("yyyyMM")+"01";
		String today = QuotaUtils.getDayDate("yyyyMMdd");
		if (today.equals(monthFirstDay)) {// 今天是一号
			return 0;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("select sum(cam_num) ");
		sql.append(" from (select city_id, sum(CAMP_USER_NUM) cam_num from mtl_eval_info_plan_d");
		sql.append(" where STAT_DATE>=?  and STAT_DATE<? group by city_id )");

		if(StringUtils.isNotEmpty(cityId) && !"999".equals(cityId)){
			sql.append(" where city_id='"+cityId+"'");
		}
		log.debug("getTotalSaleMonth执行sql=" + sql);
		try {
			num = this.getJdbcTemplate().query(sql.toString(),new String[]{monthFirstDay,today}, this.longResultSetExtractor);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		if (null != num) {
			return num;
		} else {
			return 0l;
		}
	}

	// 本月总营销数：从日表累计截止到昨天的总额
	@Override
	public long getTotalSucessMonth(String cityId) {
		long num = 0;
		String monthFirstDay=QuotaUtils.getDayDate("yyyyMM")+"01";
		String today = QuotaUtils.getDayDate("yyyyMMdd");
		if (today.equals(monthFirstDay)) {// 今天是一号
			return 0;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("select sum(cam_succ)");
		sql.append(" from (select city_id, sum(CAMP_SUCC_NUM) cam_succ from mtl_eval_info_plan_d");
		sql.append("  where STAT_DATE>=?  and STAT_DATE<? group by city_id )");
		if(StringUtils.isNotEmpty(cityId) && !"999".equals(cityId)){
			sql.append(" where city_id='"+cityId+"'");
		}
		log.debug("getTotalSucessMonth执行sql=" + sql);
		try {
			num = this.getJdbcTemplate().query(sql.toString(),new String[]{monthFirstDay,today}, this.longResultSetExtractor);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return num;
	}

	// 今日总营销数：实际就是昨日的营销数
	@Override
	public long getTotalSaleDay(String cityId) {
		long num = 0;
		StringBuffer sql = new StringBuffer();
		String yesterDay=QuotaUtils.getYesterday("yyyyMMdd");

		sql.append("select sum(cam_num) from (");
		sql.append(" select city_id,sum(CAMP_USER_NUM) cam_num from mtl_eval_info_plan_d where STAT_DATE = '").append(yesterDay).append("' ");
		sql.append(" group by city_id )");

		if(StringUtils.isNotEmpty(cityId) && !"999".equals(cityId)){
			sql.append(" where city_id='" + cityId + "'");
		}

		log.debug("getTotalSaleDay执行sql=" + sql);
		try {
			num = this.getJdbcTemplate().query(sql.toString(), this.longResultSetExtractor);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return num;

	}

	// 今日成功营销数：查询最新的就行
	@Override
	public long getTotalSuccessDay(String cityId) {
		long num = 0;
		StringBuffer sql = new StringBuffer();
		String yesterDay=QuotaUtils.getYesterday("yyyyMMdd");
		sql.append("select sum(cam_succ) from (");
		sql.append(" select city_id,sum(CAMP_SUCC_NUM) cam_succ from mtl_eval_info_plan_d where STAT_DATE = '").append(yesterDay).append("' ");
		sql.append(" group by city_id) ");

		if(StringUtils.isNotEmpty(cityId) && !"999".equals(cityId)){
			sql.append(" where city_id='"+cityId+"'");
		}
		log.debug("getTotalSuccessDay执行sql=" + sql);
		try {
			num = this.getJdbcTemplate().query(sql.toString(), this.longResultSetExtractor);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return num;
	}

	// ---------------------------------------------------以下是推荐优秀策略的--------------------------------------

	/**
	 *  查询某个地市的推荐策略(分页查询)
	 */
	@Override
	public Pager getRecommendCamp( int pageNum, String city_id) {
		List<RecommendCamp> list = null;
		Pager page = new Pager();
		page.setPageNum(pageNum);
		page.setPageSize(20);
		page.setTotalSize(this.getCampsNum());

		StringBuffer srcSql = new StringBuffer();
		String[] paramArr = new String[0];

		srcSql.append("SELECT A.STAT_DATE,A.CAMPSEG_ID,A.CAMPSEG_NAME,A.CITY_ID,A.CAMP_USER_NUM_TOTAL,A.CAMP_SUCC_NUM_TOTAL,A.CAMP_SUCC_RATE_TOTAL,A.PV_CONVERT_RATE_TOTAL,A.ORDER_SCORE from ");
		srcSql.append(" (select A.STAT_DATE,A.CAMPSEG_ID,B.CAMPSEG_NAME,A.CITY_ID,A.CAMP_USER_NUM_TOTAL,A.CAMP_SUCC_NUM_TOTAL,A.CAMP_SUCC_RATE_TOTAL,A.PV_CONVERT_RATE_TOTAL,A.ORDER_SCORE,row_number() over(partition by A.CAMPSEG_ID ORDER BY A.STAT_DATE DESC) rm ");
		srcSql.append(" from MTL_CAMPSEG_SORT A, mcd_camp_def B,DIM_PLAN_RECM C where A.CAMPSEG_ID = B.CAMPSEG_ID ");
		
		if (StringUtils.isNotEmpty(city_id)) {
			srcSql.append("and A.city_id=? ");
			paramArr = new String[1];
			paramArr[0] = city_id;
		}
		
		srcSql.append(" and A.CAMPSEG_TYPE=1 AND ");
		srcSql.append(" A.CITY_ID=C.CITY_ID AND A.CAMP_SUCC_RATE_TOTAL>C.RATE AND A.CAMP_SUCC_NUM_TOTAL>C.USER_NUM AND A.STAT_DATE=(select max(STAT_DATE) from MTL_CAMPSEG_SORT )  ) A where rm=1 ORDER BY A.CAMP_SUCC_RATE_TOTAL*0.75+A.PV_CONVERT_RATE_TOTAL*0.25 DESC");

		String sqlStr = DataBaseAdapter.getPagedSql(srcSql.toString(), pageNum, 20);

		log.info("getRecommendCamp 执行sql=" + sqlStr);
		try {
			JdbcTemplate jdbcTemplete = this.getJdbcTemplate();
			list = jdbcTemplete.query(sqlStr, paramArr, new ResultSetExtractor<List<RecommendCamp>>() {
				@Override
				public List<RecommendCamp> extractData(ResultSet rs) throws SQLException, DataAccessException {  
					List<RecommendCamp> list = new ArrayList<RecommendCamp>();
					if (null != rs) {
						while (rs.next()) {
							RecommendCamp rc = new RecommendCamp();
							rc.setCampsegId(rs.getString("CAMPSEG_ID"));
							rc.setCampSuccNum(rs.getLong("CAMP_SUCC_NUM_TOTAL"));
							rc.setCampSuccRate(rs.getDouble("CAMP_SUCC_RATE_TOTAL"));
							rc.setCampsegName(rs.getString("CAMPSEG_NAME"));
							rc.setCampUserNum(rs.getLong("CAMP_USER_NUM_TOTAL"));
							rc.setPvConvertRate(rs.getDouble("PV_CONVERT_RATE_TOTAL"));
							rc.setStatDate(rs.getString("STAT_DATE"));
							rc.setCityId(rs.getString("CITY_ID"));
							list.add(rc);
						}
					}
					
					return list;
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		page.setResult(list);
		return page;
	}

	/**
	 *  查询策略的渠道
	 */
	@Override
	public List<CampChannel> getCampChannel(String campsegId) {
		List<CampChannel> list = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select a.channel_id,b.channel_name from mcd_camp_channel_list a,mcd_dim_channel b,mcd_camp_def c")
				.append(" WHERE c.campseg_pid='").append(campsegId)
				.append("' and a.channel_id = b.channel_id and a.campseg_id=c.campseg_id");

		log.debug("getCampChannel执行sql=" + sql);
		try {
			list = this.getJdbcTemplate().query(sql.toString(),
					new RowMapper<CampChannel>() {

						@Override
						public CampChannel mapRow(ResultSet rs, int index)
								throws SQLException {
							CampChannel cc = new CampChannel();
							cc.setChannelId(rs.getString("channel_id"));
							cc.setChannelTypeName(rs.getString("channel_name"));
							return cc;
						}
					});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;

	}

	private long getCampsNum() {
		long total = 0;
		StringBuffer srcSql = new StringBuffer();
		srcSql.append("select count(*) from MTL_CAMPSEG_SORT ");
		log.debug("getCampsNum执行sql=" + srcSql);
		try {
			total = this.getJdbcTemplate().query(srcSql.toString(), this.longResultSetExtractor);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * 我的营销策略
	 */
	@Override
	public Pager getMySale(String userId, int pageNum,int pageSize) {
		/*
		 * select
		 * camp.campseg_id,camp.campseg_name,camp.campseg_stat_id,mm.month_succ_num
		 * ,nn.camp_succ_num from mcd_camp_def camp,
		 * 
		 * (select sum(CAMP_SUCC_NUM) month_succ_num, campseg_id from (select *
		 * from MTL_EVAL_INFO_CAMPSEG_D where channel_id = '-1'and stat_date >
		 * '2016-03-01' and stat_date < '2016-03-18') aa group by campseg_id)
		 * mm,
		 * 
		 * (select tt.campseg_id, tt.camp_succ_num from MTL_EVAL_INFO_CAMPSEG_D
		 * tt where tt.stat_date = '2016-03-18') nn
		 * 
		 * where camp.campseg_id = mm.campseg_id and camp.campseg_id =
		 * nn.campseg_id;
		 */

		/*
		 * select
		 * camp.campseg_id,camp.campseg_name,camp.campseg_stat_id,mm.month_succ_num
		 * ,nn.camp_succ_num from mcd_camp_def camp
		 * 
		 * left outer join
		 * 
		 * ( select sum(CAMP_SUCC_NUM) month_succ_num, campseg_id from (select *
		 * from MTL_EVAL_INFO_CAMPSEG_D where channel_id = '-1' and stat_date >
		 * '2016-03-01' and stat_date < '2016-03-18') group by campseg_id ) mm
		 * 
		 * on camp.campseg_id = mm.campseg_id
		 * 
		 * left outer join ( select tt.campseg_id, tt.camp_succ_num from
		 * MTL_EVAL_INFO_CAMPSEG_D tt where tt.stat_date = '2016-03-18' ) nn
		 * 
		 * on camp.campseg_id = nn.campseg_id
		 * 
		 * where camp.CREATE_USERID='777'
		 */
		StringBuffer sql = new StringBuffer();

		sql.append("select camp.campseg_id,camp.campseg_name,camp.campseg_stat_id,aa.campseg_stat_name,mm.month_succ_num,nn.camp_succ_num ")
				.append(" from mcd_camp_def camp ");

		sql.append(" left outer join  ");
		sql.append("(")
				.append("select sum(CAMP_SUCC_NUM) month_succ_num, campseg_id from ")
				.append(" (select * from MTL_EVAL_INFO_CAMPSEG_D where channel_id = '-1' and stat_date >= ? and stat_date <= ?) ")
				.append(" group by campseg_id ").append(" )  mm");
		sql.append(" on camp.campseg_id = mm.campseg_id ");

		sql.append(" left outer join  ");
		sql.append("(")
				.append(" select tt.campseg_id, tt.camp_succ_num from MTL_EVAL_INFO_CAMPSEG_D tt  where tt.stat_date = ?")
				.append(") nn");
		sql.append(" on camp.campseg_id = nn.campseg_id ");
		
		sql.append(" left outer join  (select t.campseg_stat_siteid,t.campseg_stat_id,t.campseg_stat_name from mcd_dim_camp_status t  where t.campseg_stat_visible=0 ) aa")
		.append(" on aa.campseg_stat_id=camp.campseg_stat_id");
         
		sql.append(" where camp.CREATE_USERID=? and camp.campseg_pid='0' ");
		sql.append(" ORDER BY aa.campseg_stat_siteid ASC,camp.create_time desc ");

		String sqlStr = DataBaseAdapter.getPagedSql(sql.toString(), pageNum, pageSize);

		log.debug("getMySale执行sql=" + sqlStr);

		List<MySale> list = null;
		String today = QuotaUtils.getDayDate("yyyyMMdd");
		String monthFirstDay = QuotaUtils.getDayDate("yyyyMM") + "01";
		String yesterday = QuotaUtils.getYesterday("yyyyMMdd");

		try {
			list = this.getJdbcTemplate().query(sqlStr,new String[] { monthFirstDay, today, yesterday, userId },
					new RowMapper<MySale>() {

						@Override
						public MySale mapRow(ResultSet rs, int index)throws SQLException {
							MySale ms = new MySale();
							ms.setCampsegId(rs.getString("campseg_id"));
							ms.setCampsegStatName(rs.getString("campseg_stat_name"));
							ms.setCampsegName(rs.getString("campseg_name"));
							ms.setCampsegStatId(rs.getString("campseg_stat_id"));
							ms.setMonthSuccessNum(rs.getLong("month_succ_num"));
							ms.setYesterdaySuccessNum(rs
									.getLong("camp_succ_num"));
							return ms;
						}
					});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		Pager page = new Pager();
		page.setPageSize(pageSize);
		page.setPageNum(pageNum);
		page.setTotalSize(getMyTotalCamp(userId));
		page.setResult(list);
		return page;
	}

	private long getMyTotalCamp(String userId) {
		long num = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from mcd_camp_def where  CREATE_USERID = ?");
		log.debug("getMyTotalCamp执行sql=" + sql);
		try {
			num = this.getJdbcTemplate().query(sql.toString(),new String[] { userId }, this.longResultSetExtractor);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return num;

	}
}
