package com.asiainfo.biapp.mcd.home.service.impl;


import com.asiainfo.biapp.mcd.home.echartbean.Constants;
import com.asiainfo.biapp.mcd.home.dao.ICepKeywordsDao;
//import com.asiainfo.biapp.mcd.bean.zhejiang.PageResult;
import com.asiainfo.biapp.mcd.home.echartbean.EchartsUtil;
import com.asiainfo.biapp.mcd.home.service.ICepKeywordsService;

import javax.annotation.Resource;

//import com.asiainfo.biapp.mcd.model.CepKeyword;
//import com.asiainfo.biapp.mcd.model.CepKeywordsPkg;
//import com.asiainfo.biapp.mcd.util.CommonUtil;
//import com.asiainfo.biframe.exception.ServiceException;
//import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
@Service("CepKeywordsService")
public class CepKeywordsServiceImpl implements ICepKeywordsService {

	private static Logger log = LogManager.getLogger();
	
	@Resource(name = "CepKeywordsDao")
	private ICepKeywordsDao dao;
	
	private static final String RANGE_ALL = "A";
	private static final String RANGE_MONTH = "M";
	private static final String RANGE_DAY = "D";
	
	private static final String VERTI_TEND = "tend";
	private static final String VERTI_PUT = "put";
	private static final String VERTI_AREA = "area";
	
	private static final String TAB_T_CAMP = "t_cam";
	private static final String TAB_T_SUC = "t_suc";
	private static final String TAB_CAMP_CVT = "cam_cvt";
	
//	private static final String TABLE_YEAR = "mtl_eval_info_plan_m";
//	private static final String TABLE_MONTH = "mtl_eval_info_plan_d";
	
	private static final String C_999 = "999";

	@Override
	public String composite(String range, String verti, String tab, String cityId) {

		try {
//			String _GROUP_TAB = "mcd_dim_city";
//			String _GROUP_COL_ = "CITY_ID";
//			String _GROUP_COL_NAME_ = "CITY_NAME";
			if (!C_999.equals(cityId)) {
//				_GROUP_TAB = "DIM_PUB_COUNTY";
//				_GROUP_COL_ = "COUNTY_ID";
//				_GROUP_COL_NAME_ = "COUNTY_NAME";
			}

			String chartType = Constants.ECHARTS_CHARTTYPE.LINE;
//			String tabName = TABLE_YEAR;
			String legend = "";
			StringBuffer sql = new StringBuffer("SELECT ");
			String orderCol = " T.STAT_DATE ";


			if (RANGE_ALL.equals(range.toUpperCase())) {
				sql.append(orderCol);
				sql.append(", ");
				legend = "总";
			} else if (RANGE_MONTH.equals(range.toUpperCase())) {
//				tabName = TABLE_MONTH;
//			orderCol = " T.STAT_DATE ";
				sql.append(orderCol);
				sql.append(", ");
				legend = "本月";
			} else if (RANGE_DAY.equals(range.toUpperCase())) {
//				tabName = TABLE_MONTH;
//			orderCol = " T.STAT_DATE ";
				sql.append(orderCol);
				sql.append(", ");
				legend = "今日";
			}
			legend += "营销";
			if (VERTI_TEND.equals(verti.toLowerCase())) {

				String trend_select_head = "";

				if (TAB_T_CAMP.equals(tab.toLowerCase())) {
					trend_select_head = " sum(cam_num) cam_num ";
					legend += "人数";
				}
				if (TAB_T_SUC.equals(tab.toLowerCase())) {
					trend_select_head = " sum(cam_succ) cam_succ ";
					legend += "成功数";
				}
				if (TAB_CAMP_CVT.equals(tab.toLowerCase())) {
//					sql.append(" SUM(T.PV_CONVERT_RATE) * 100 ");
					trend_select_head = " CASE WHEN sum(cam_num) = 0 THEN 0 else round(sum(cam_succ) / sum(cam_num),4)*100 END cam_rate ";
					legend += "转化率(%)";
				}

				String trend_select_city = "";
				if (!C_999.equals(cityId)) {
					trend_select_city = " AND T.CITY_ID = '"+cityId+"' ";
				}


				StringBuffer sb = new StringBuffer();
				if (RANGE_ALL.equals(range.toUpperCase())) {
					sb.append("select stat_date,"+trend_select_head);
					sb.append(" 	from (select stat_date,");
					sb.append("               city_id,");
					sb.append("               sum(CAMP_SUCC_NUM) cam_succ,");
					sb.append("               sum(CAMP_USER_NUM) cam_num");
					sb.append("          from mtl_eval_info_plan_m t");
					sb.append("         where 1=1 ");
					sb.append(trend_select_city);
					sb.append("         group by stat_date, city_id");
					sb.append("        union all");
					sb.append("        select substr(stat_date, 1, 6) stat_date,");
					sb.append("               city_id,");
					sb.append("               sum(CAMP_SUCC_NUM) cam_succ,");
					sb.append("               sum(CAMP_USER_NUM) cam_num");
					sb.append("          from mtl_eval_info_plan_d t");
					sb.append("         where  1=1 ");
					sb.append("           and stat_date like '"+EchartsUtil.getCurrentMonth()+"%' ");
					sb.append(trend_select_city);
					sb.append("         group by substr(stat_date, 1, 6), city_id)");
					sb.append(" 	group by stat_date");
					sb.append(" order by stat_date");


				} else if (RANGE_MONTH.equals(range.toUpperCase())) {
					sb.append("select stat_date,"+trend_select_head);
					sb.append(" from (select stat_date,");
					sb.append("                 city_id,");
					sb.append("                 sum(CAMP_SUCC_NUM) cam_succ,");
					sb.append("                 sum(CAMP_USER_NUM) cam_num");
					sb.append("            from mtl_eval_info_plan_d t");
					sb.append("           where 1=1 ");
					sb.append("             and stat_date like '"+EchartsUtil.getCurrentMonth()+"%' ");
					sb.append(trend_select_city);
					sb.append("           group by stat_date, city_id)");
					sb.append("   group by stat_date");
					sb.append("   order by stat_date");

				}


				log.info("首页下钻:趋势图表sql:"+sb.toString());
				return EchartsUtil.convert2Charts(dao.queryData4Charts(sb.toString(), new Object[] {}), chartType, legend);

			} else if (VERTI_PUT.equals(verti.toLowerCase())) {
				String chartType_bar = Constants.ECHARTS_CHARTTYPE.HORIZONTAL_BAR;
				chartType = Constants.ECHARTS_CHARTTYPE.PIE_EMPTY;


				String put_select_head = "";

				if (TAB_T_CAMP.equals(tab.toLowerCase())) {
					put_select_head = " sum(cam_num) cam_num ";
					legend += "人数";
				}
				if (TAB_T_SUC.equals(tab.toLowerCase())) {
					put_select_head = " sum(cam_succ) cam_succ ";
					legend += "成功数";
				}
				if (TAB_CAMP_CVT.equals(tab.toLowerCase())) {
//					sql.append(" SUM(T.PV_CONVERT_RATE) * 100 ");
					put_select_head = " CASE WHEN sum(cam_num) = 0 THEN 0 else round(sum(cam_succ) / sum(cam_num),4)*100 END cam_rate ";
					legend += "转化率(%)";
				}

				String put_select_city = "";
				if (!C_999.equals(cityId)) {
					put_select_city = " AND T.CITY_ID = '"+cityId+"' ";
				}

				StringBuffer sb = new StringBuffer();


				if (RANGE_ALL.equals(range.toUpperCase())) {
					sb.append("select channel_name,"+put_select_head);
					sb.append(" from (select stat_date,");
					sb.append("               city_id,");
					sb.append("               channel_id,");
					sb.append("               sum(CAMP_SUCC_NUM) cam_succ,");
					sb.append("               sum(CAMP_USER_NUM) cam_num");
					sb.append("          from mtl_eval_info_plan_m t");
					sb.append("         where 1=1 ");
					sb.append(put_select_city);
					sb.append("         group by stat_date, city_id,channel_id");
					sb.append("        union all");
					sb.append("        select substr(stat_date, 1, 6) stat_date,");
					sb.append("               city_id,");
					sb.append("               channel_id,");
					sb.append("               sum(CAMP_SUCC_NUM) cam_succ,");
					sb.append("               sum(CAMP_USER_NUM) cam_num");
					sb.append("          from mtl_eval_info_plan_d t");
					sb.append("         where 1=1 ");
					sb.append("           and stat_date like '"+EchartsUtil.getCurrentMonth()+"%' ");
					sb.append(put_select_city);
					sb.append("         group by substr(stat_date, 1, 6), city_id,channel_id) a");
					sb.append(" join mcd_dim_channel b on a.channel_id=b.channel_id");
					sb.append(" group by channel_name ");

				} else if (RANGE_MONTH.equals(range.toUpperCase())) {
					sb.append("select channel_name,"+put_select_head);
					sb.append(" from (select  city_id,");
					sb.append("               channel_id,");
					sb.append("               sum(CAMP_SUCC_NUM) cam_succ,");
					sb.append("               sum(CAMP_USER_NUM) cam_num");
					sb.append("          from mtl_eval_info_plan_d t");
					sb.append("         where 1=1 ");
					sb.append("           and stat_date like '"+EchartsUtil.getCurrentMonth()+"%' ");
					sb.append(put_select_city);
					sb.append("        group by city_id, channel_id) a ");
					sb.append("		join mcd_dim_channel b ");
					sb.append("     on a.channel_id=b.channel_id ");
					sb.append(" group by channel_name ");
				}

				//成功率不是饼图
				if (TAB_CAMP_CVT.equals(tab.toLowerCase())) {
					log.info("首页下钻第二个:投放图表sql:成功率:"+sb.toString());
					return EchartsUtil.convert2Charts(dao.queryData4Charts(sb.toString(), new Object[] {}), chartType_bar, legend);
				}else{
					log.info("首页下钻第二个:投放图表sql:"+sb.toString());
					return EchartsUtil.convert2Charts(dao.queryData4Charts(sb.toString(), new Object[] {}), chartType, legend);
				}

			} else if (VERTI_AREA.equals(verti.toLowerCase())) {
				chartType = Constants.ECHARTS_CHARTTYPE.HORIZONTAL_BAR;
				String area_select_head = "";
				String area_select_head_mon = "";

				if (TAB_T_CAMP.equals(tab.toLowerCase())) {
					area_select_head = " sum(cam_num) cam_num ";
					area_select_head_mon = " sum(CAMP_USER_NUM) cam_num ";
					legend += "人数";
				}
				if (TAB_T_SUC.equals(tab.toLowerCase())) {
					area_select_head = " sum(cam_succ) cam_succ ";
					area_select_head_mon = " sum(CAMP_SUCC_NUM) cam_succ ";
					legend += "成功数";
				}
				if (TAB_CAMP_CVT.equals(tab.toLowerCase())) {
//					sql.append(" SUM(T.PV_CONVERT_RATE) * 100 ");
					area_select_head = " CASE WHEN sum(cam_num) = 0 THEN 0 else round(sum(cam_succ) / sum(cam_num),4)*100 END cam_rate ";
					area_select_head_mon = " CASE WHEN sum(CAMP_USER_NUM) = 0 THEN 0 else round(sum(CAMP_SUCC_NUM) / sum(CAMP_USER_NUM),4)*100 END cam_rate ";
					legend += "转化率(%)";
				}

				String area_select_city = "";
				if (!C_999.equals(cityId)) {
					area_select_city = " AND T.CITY_ID = '"+cityId+"' ";
				}

				StringBuffer sb = new StringBuffer();


				if (RANGE_MONTH.equals(range.toUpperCase())) {

					if (C_999.equals(cityId)) {
						sb.append("select city_name,"+area_select_head_mon);
						sb.append(" from mtl_eval_info_plan_d t");
						sb.append(" join mcd_dim_city b on t.city_id=b.city_id");
						sb.append(" where 1=1 ");
						sb.append(" and stat_date like '"+EchartsUtil.getCurrentMonth()+"%' ");
						sb.append(" group by b.city_name,b.CITY_ID order by b.city_id desc");

					}else{
						sb.append("select county_name,"+area_select_head_mon);
						sb.append(" from mtl_eval_info_plan_d t");
						sb.append(" join mcd_dim_city b on t.city_id=b.city_id");
						sb.append(" join dim_pub_county c on t.county_id=c.county_id");
						sb.append(" where 1=1 ");
						sb.append(area_select_city);
						sb.append("   and t.stat_date like '"+EchartsUtil.getCurrentMonth()+"%' ");
						sb.append(" group by c.county_name ,c.county_id order by c.county_id desc");
					}

				} else if (RANGE_ALL.equals(range.toUpperCase())) {
					if (C_999.equals(cityId)) {
						sb.append("select city_name,"+area_select_head);
						sb.append("  from (select city_name,b.city_id,");
						sb.append("               sum(CAMP_SUCC_NUM) cam_succ,");
						sb.append("               sum(CAMP_USER_NUM) cam_num");
						sb.append("          from mtl_eval_info_plan_m t");
						sb.append("          join mcd_dim_city b on t.city_id = b.city_id");
						sb.append("         where 1=1 ");
						sb.append("         group by b.city_name,b.city_id");
						sb.append("        union all");
						sb.append("        select city_name,b.city_id,");
						sb.append("               sum(CAMP_SUCC_NUM) cam_succ,");
						sb.append("               sum(CAMP_USER_NUM) cam_num");
						sb.append("          from mtl_eval_info_plan_d t");
						sb.append("          join mcd_dim_city b on t.city_id = b.city_id");
						sb.append("         where 1=1 ");
						sb.append("   		  and stat_date like '"+EchartsUtil.getCurrentMonth()+"%' ");
						sb.append("         group by b.city_name,b.city_id)");
						sb.append(" group by city_name,city_id order by city_id desc");
					}else{
						sb.append("select county_name,"+area_select_head);
						sb.append("  from (select county_name,c.county_id,");
						sb.append("               sum(CAMP_SUCC_NUM) cam_succ,");
						sb.append("               sum(CAMP_USER_NUM) cam_num");
						sb.append("          from mtl_eval_info_plan_m t");
						sb.append("          join mcd_dim_city b on t.city_id = b.city_id");
						sb.append("          join dim_pub_county c on t.county_id = c.county_id");
						sb.append("         where 1=1 ");
						sb.append(area_select_city);
						sb.append("           group by c.county_name,c.county_id");
						sb.append("        union all");
						sb.append("        select county_name,c.county_id,");
						sb.append("               sum(CAMP_SUCC_NUM) cam_succ,");
						sb.append("               sum(CAMP_USER_NUM) cam_num");
						sb.append("          from mtl_eval_info_plan_d t");
						sb.append("          join mcd_dim_city b on t.city_id = b.city_id");
						sb.append("          join dim_pub_county c on t.county_id = c.county_id");
						sb.append("         where 1=1 ");
						sb.append(area_select_city);
						sb.append("   		  and stat_date like '"+EchartsUtil.getCurrentMonth()+"%' ");
						sb.append("         group by c.county_name,c.county_id )");
						sb.append(" group by county_name,county_id order by county_id desc");
					}
				}

				log.info("首页下钻第三个:区域图表sql:"+sb.toString());

				return EchartsUtil.convert2Charts(dao.queryData4Charts(sb.toString(), new Object[] {}), chartType, legend);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
