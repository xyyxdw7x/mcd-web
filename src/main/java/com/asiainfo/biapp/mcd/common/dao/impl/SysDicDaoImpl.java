package com.asiainfo.biapp.mcd.common.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.dao.ISysDicDao;

@Repository("sysDicDao")
public class SysDicDaoImpl extends JdbcDaoBase implements ISysDicDao {

	protected final Log log = LogFactory.getLog(getClass());
	
	@Override
	public List<Map<String, Object>> queryAllData(String proFile) throws Exception {
		log.info("queryAllData proFile="+proFile);
		String sql="select dic_key,dic_display_value,dic_data_value,dic_data_pvalue from  MCD_SYS_DIC "
				+ "where dic_profile=? and dic_is_show=1  order by DIC_KEY asc ,dic_display_order asc";
		Object[] args=new Object[]{proFile};
		int[] types=new int[]{Types.VARCHAR};
		log.info("sql="+sql);
		List<Map<String, Object>> list=this.getJdbcTemplate().queryForList(sql,args,types);
		log.info("list size="+list.size());
		return list;
	}

}
