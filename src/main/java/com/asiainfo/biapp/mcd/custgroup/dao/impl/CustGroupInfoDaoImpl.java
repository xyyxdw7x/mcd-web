package com.asiainfo.biapp.mcd.custgroup.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.dao.CustGroupInfoDao;
import com.asiainfo.biapp.mcd.tactics.vo.MtlGroupInfo;
import com.asiainfo.biframe.utils.string.StringUtil;
@Repository("custGroupInfoDao")
public class CustGroupInfoDaoImpl extends JdbcDaoBase  implements CustGroupInfoDao{
	@Override
	public int getMoreMyCustomCount(String currentUserId,String keyWords) {
		int count = 0;
		List parameterList = new ArrayList();
		try {
			StringBuffer buffer = new StringBuffer();
			parameterList.add(currentUserId);
			parameterList.add(currentUserId);
			buffer.append("SELECT COUNT(*) FROM (select MTL_GROUP_INFO.*,mtl_custom_list_info.data_time from MTL_GROUP_INFO left join (")
		 	   .append(" select A.Custom_Group_Id,B.data_time from (")
		 	   .append(" select max(list_table_name) list_table_name, max(data_date ),Custom_Group_Id from mtl_custom_list_info group by Custom_Group_Id")
		 	   .append("  ) A left join mtl_custom_list_info B on A.list_table_name = B.list_table_name)")
		 	   .append(" mtl_custom_list_info on MTL_GROUP_INFO.Custom_Group_Id = mtl_custom_list_info.custom_group_id order by MTL_GROUP_INFO.create_time desc) WHERE (CREATE_USER_ID = ? or custom_group_id in ( select custom_group_id from mtl_group_push_info where create_push_target_id = ?))")
			   .append(" and custom_status_id not in (2,9)")
		 	   .append("  and (to_char(Fail_Time,'yyyy-MM-dd')>to_char(trunc(sysdate),'yyyy-MM-dd') or Fail_Time is null)"); //失效时间计算
			if(StringUtil.isNotEmpty(keyWords)){
				if(keyWords.equals("%")){
					buffer.append(" AND (CUSTOM_GROUP_NAME LIKE ").append("'%\\%%' escape '\\'").append(" OR CUSTOM_GROUP_ID LIKE ").append("'%\\%%' escape '\\')");
				}else{
					buffer.append(" AND (CUSTOM_GROUP_NAME LIKE ?");
					buffer.append(" OR CUSTOM_GROUP_ID LIKE ?)");
					
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			count = this.getJdbcTemplate().queryForObject(buffer.toString(),parameterList.toArray(),Integer.class);
		} catch (Exception e) {
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MtlGroupInfo> getMoreMyCustom(String currentUserId,String keyWords, Pager pager) {
		List<Map<String,Object>> list = null;
		List<MtlGroupInfo> custGroupList = new ArrayList<MtlGroupInfo>();
		List parameterList = new ArrayList();
		try {
			StringBuffer sbuffer = new StringBuffer();
			parameterList.add(currentUserId);
			parameterList.add(currentUserId);
			
			sbuffer.append("SELECT * FROM (select MTL_GROUP_INFO.*,mtl_custom_list_info.data_time,mtl_custom_list_info.max_data_time from MTL_GROUP_INFO left join (")
		 	   .append(" select A.Custom_Group_Id,A.max_data_time,B.data_time from (")
		 	   .append(" select max(list_table_name) list_table_name, max(data_date ) max_data_time,Custom_Group_Id from mtl_custom_list_info group by Custom_Group_Id")
		 	   .append("  ) A left join mtl_custom_list_info B on A.list_table_name = B.list_table_name)")
		 	   .append(" mtl_custom_list_info on MTL_GROUP_INFO.Custom_Group_Id = mtl_custom_list_info.custom_group_id order by mtl_custom_list_info.max_data_time desc) WHERE (CREATE_USER_ID = ? or custom_group_id in ( select custom_group_id from mtl_group_push_info where create_push_target_id = ?))")
		 	   .append(" and custom_status_id not in (2,9)")
		 	   .append("  and (to_char(Fail_Time,'yyyy-MM-dd')>to_char(trunc(sysdate),'yyyy-MM-dd') or Fail_Time is null)"); //失效时间计算
			if(StringUtil.isNotEmpty(keyWords)){
				if(keyWords.equals("%")){
					sbuffer.append(" AND (CUSTOM_GROUP_NAME LIKE ").append("'%\\%%' escape '\\'").append(" OR CUSTOM_GROUP_ID LIKE ").append("'%\\%%' escape '\\')");
				}else{
					sbuffer.append(" AND (CUSTOM_GROUP_NAME LIKE ?");
					sbuffer.append(" OR CUSTOM_GROUP_ID LIKE ?)");
					
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			String sqlExt = DataBaseAdapter.getPagedSql(sbuffer.toString(), pager.getPageNum(),pager.getPageSize());
			list = this.getJdbcTemplate().queryForList(sqlExt.toString(),parameterList.toArray());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for (Map map : list) {
				MtlGroupInfo custGroupInfo = new MtlGroupInfo();
				custGroupInfo.setCustomGroupId((String) map.get("CUSTOM_GROUP_ID"));
				custGroupInfo.setCustomGroupName((String) map.get("CUSTOM_GROUP_NAME"));    
				custGroupInfo.setCreateUserId((String) map.get("CREATE_USER_ID"));
				custGroupInfo.setDataDate(String.valueOf(map.get("max_data_time")));
				if(null != map.get("CUSTOM_NUM")){
					custGroupInfo.setCustomNum(Integer.parseInt(String.valueOf(map.get("CUSTOM_NUM"))));
				}
				if(null != map.get("CUSTOM_STATUS_ID")){
					custGroupInfo.setCustomStatusId(Integer.parseInt(String.valueOf(map.get("CUSTOM_STATUS_ID"))));
				}
				if(null != map.get("UPDATE_CYCLE")){
					custGroupInfo.setUpdateCycle(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))));
					if(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))) == 1){
						custGroupInfo.setCustGroupUpdateCycle("一次性");
					}else if(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))) == 2){
						custGroupInfo.setCustGroupUpdateCycle("月");
					}else if(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))) == 3){
						custGroupInfo.setCustGroupUpdateCycle("日");
					}
				}
				custGroupInfo.setCreateUserName(String.valueOf(map.get("create_user_name")));
				custGroupList.add(custGroupInfo);
			}
		} catch (Exception e) {
		}
		return custGroupList;
	}
	
	@Override
	public List<MtlGroupInfo> getMyCustGroup(String currentUserId) {
		List<Map<String, Object>> list = null;
		List<MtlGroupInfo> custGroupList = new ArrayList<MtlGroupInfo>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("SELECT * FROM (select MTL_GROUP_INFO.*,mtl_custom_list_info.data_time,mtl_custom_list_info.max_data_time  from MTL_GROUP_INFO left join (")
			 	   .append(" select A.Custom_Group_Id,A.max_data_time,B.data_time from (")
			 	   .append(" select max(list_table_name) list_table_name, max(data_date ) max_data_time,Custom_Group_Id from mtl_custom_list_info group by Custom_Group_Id")
			 	   .append("  ) A left join mtl_custom_list_info B on A.list_table_name = B.list_table_name)")
			 	   .append(" mtl_custom_list_info on MTL_GROUP_INFO.Custom_Group_Id = mtl_custom_list_info.custom_group_id order by mtl_custom_list_info.max_data_time desc) WHERE CREATE_USER_ID = ? or custom_group_id in ( select custom_group_id from mtl_group_push_info where create_push_target_id = ?)");
			list = this.getJdbcTemplate().queryForList(sbuffer.toString(),new String[] { currentUserId,currentUserId });
			for (Map map : list) {
				MtlGroupInfo custGroupInfo = new MtlGroupInfo();
				custGroupInfo.setCustomGroupId((String) map.get("CUSTOM_GROUP_ID"));
				custGroupInfo.setCustomGroupName((String) map.get("CUSTOM_GROUP_NAME"));    
				custGroupInfo.setCreateUserId((String) map.get("CREATE_USER_ID"));
				if(null != map.get("CUSTOM_NUM")){
					custGroupInfo.setCustomNum(Integer.parseInt(String.valueOf(map.get("CUSTOM_NUM"))));
				}
				if(null != map.get("CUSTOM_STATUS_ID")){
					custGroupInfo.setCustomStatusId(Integer.parseInt(String.valueOf(map.get("CUSTOM_STATUS_ID"))));
				}
				if(null != map.get("UPDATE_CYCLE")){
					custGroupInfo.setUpdateCycle(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))));
				}
				
//				前台模板统一使用  添加模板
				custGroupInfo.setTypeId((String) map.get("CUSTOM_GROUP_ID"));
				custGroupInfo.setTypeName((String) map.get("CUSTOM_GROUP_NAME"));
				
				custGroupList.add(custGroupInfo);
			}
		} catch (Exception e) {
		}
		return custGroupList;
	}

}
