package com.asiainfo.biapp.mcd.common.custgroup.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.custgroup.dao.ICustGroupAttrRelDao;
import com.asiainfo.biapp.mcd.custgroup.vo.McdCustgroupAttrList;
import com.asiainfo.biapp.mcd.tactics.vo.RuleTimeTermLable;
@Repository("custGroupAttrRelDao")
public class CustGroupAttrRelDaoImpl extends JdbcDaoBase  implements ICustGroupAttrRelDao{
	@Override
	public List<McdCustgroupAttrList> initTermLable(String custGroupId) {
		List<Map<String, Object>> list = null;
		List<McdCustgroupAttrList> mtlGroupAttrRelList = new ArrayList<McdCustgroupAttrList>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("SELECT  * FROM mcd_custgroup_attr_list WHERE CUSTOM_GROUP_ID=?")
				   .append(" and list_table_name=(select max(list_table_name) from mcd_custgroup_attr_list WHERE CUSTOM_GROUP_ID=?)");
			list = this.getJdbcTemplate().queryForList(sbuffer.toString(), new Object[]{custGroupId,custGroupId});
			for (Map<String,Object> map : list) {
				McdCustgroupAttrList mtlGroupAttrRel = new McdCustgroupAttrList();
				mtlGroupAttrRel.setAttrCol((String) map.get("ATTR_COL"));
				mtlGroupAttrRel.setAttrColName((String) map.get("ATTR_COL_NAME"));
				mtlGroupAttrRel.setAttrColLength((String) map.get("ATTR_COL_LENGTH"));
				mtlGroupAttrRel.setAttrColType((String) map.get("ATTR_COL_TYPE"));
				mtlGroupAttrRel.setCustomGroupId((String) map.get("CUSTOM_GROUP_ID"));
				mtlGroupAttrRel.setCustomSourceId((String) map.get("CUSTOM_SOURCE_ID"));
				mtlGroupAttrRel.setListTableName((String) map.get("LIST_TABLE_NAME"));
				mtlGroupAttrRelList.add(mtlGroupAttrRel);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return mtlGroupAttrRelList;
	}
	
	@Override
	public List<RuleTimeTermLable> getFunctionNameById(String functionId){
		List<Map<String, Object>> list = null;
		List<RuleTimeTermLable> resultList = new ArrayList<RuleTimeTermLable>();
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("select * from dim_function_map_zj where function_id=? ");
		list = this.getJdbcTemplate().queryForList(sbuffer.toString(),new Object[]{functionId});
		for (Map<String,Object> map : list) {
			RuleTimeTermLable temp = new RuleTimeTermLable();
			temp.setFunctionId((String) map.get("function_id"));
			temp.setFunctionNameDesc((String) map.get("function_name"));
			resultList.add(temp);
		}
		return resultList;
	}

}
