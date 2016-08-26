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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository("custGroupInfoDao")
public class CustGroupInfoDaoImpl extends JdbcDaoBase  implements CustGroupInfoDao{
	private static Logger log = LogManager.getLogger(CustGroupInfoDaoImpl.class);
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
	@Override
	@SuppressWarnings("unchecked")
	public List searchCustom(String contentType, Pager pager, String userId, String keywords) {
		
		StringBuffer buffer = new StringBuffer();

		List plist = new ArrayList();

		if("ALL-CUSTOM".equals(contentType) || "MY-CUSTOM".equals(contentType)) {
			buffer.append("select tbase.*, info.data_date,info.list_table_name from (");
			buffer.append(" select * from (");
			buffer.append(" select ROWNUM as rowno,t.custom_group_id,t.custom_group_name,t.custom_group_desc,t.create_user_id,t.create_time,t.rule_desc,t.custom_num,");
			buffer.append(" case t.custom_status_id when 0 then '无效' when 1 then '有效'  when 3 then '提取处理中'  when 4 then '提取失败' when 9 then '客户群导入失败' end as custom_status,t.effective_time,t.fail_time,t.create_user_name,");
			buffer.append(" case t.update_cycle when 1 then '一次性' when 2 then '月' when 3 then '日' end as update_cycle_name");
			buffer.append(" from MTL_GROUP_INFO t where t.custom_status_id <>'2' ");

			if("MY-CUSTOM".equals(contentType)) {
				buffer.append(" and (t.create_user_id = ?  or t.custom_group_id in (select custom_group_id from mtl_group_push_info where create_push_target_id = '"+userId+"'))");
				plist.add(userId);
			}
			if(keywords != null && !"".equals(keywords)) {
				buffer.append(" and (t.custom_group_id like '%" + keywords + "%' or t.custom_group_name like '%" + keywords + "%')");
			}

			buffer.append(" order by t.create_time desc) ");
			buffer.append(" ) tbase left join (");
			buffer.append(" select * from (");
			buffer.append(" SELECT ROW_NUMBER() OVER(PARTITION BY custom_group_id ORDER BY data_time DESC) rn,t.* ");
			buffer.append(" FROM MTL_CUSTOM_LIST_INFO t ) where rn = 1");
			buffer.append(" ) info on tbase.custom_group_id = info.custom_group_id");
		} else {//异常客户群：过期的、客户群状态为9的，客户群数量为0的

			buffer.append(" select re.* from (");
			buffer.append(" select tab.*, case tab.update_cycle ");
			buffer.append(" when 2 then case when trunc(tab.data_time,'mon')<trunc(add_months(sysdate,-1),'mon') then 1 else 0 end");
			buffer.append(" when 3 then case when (sysdate - interval '24' hour) > add_months(tab.data_time,1) then 1 else 0 end");
			buffer.append(" else 0 end as invalid_flag,");
			buffer.append(" case tab.update_cycle when 2 then '月' when 3 then '日' when 1 then '一次性' end as update_cycle_name");
			buffer.append(" from (");
			buffer.append(" select cinfo.*, dinfo.data_time,dinfo.data_date from (");
			buffer.append(" select * from (");
			buffer.append(" SELECT ROW_NUMBER() OVER(PARTITION BY custom_group_id ORDER BY data_time DESC) rn,t1.* ");
			buffer.append(" FROM MTL_CUSTOM_LIST_INFO t1");
			buffer.append(" ) t2 where t2.rn = 1) dinfo");
			buffer.append(" left join MTL_GROUP_INFO cinfo on dinfo.custom_group_id = cinfo.custom_group_id");
			buffer.append(" where (cinfo.create_user_id =  '"+userId+"' or  cinfo.custom_group_id in (select custom_group_id from mtl_group_push_info where create_push_target_id = '"+userId+"'))");
            buffer.append(" and cinfo.custom_group_id in (select distinct mcc.custgroup_id from mtl_camp_seginfo mcs,MTL_CAMPSEG_CUSTGROUP mcc where mcs.campseg_id = mcc.campseg_id and mcc.custgroup_type = 'CG'and mcs.campseg_stat_id in ('50','54','59'))");
			    buffer.append(") tab) re ");

			buffer.append(" where ((re.invalid_flag = 1 and re.custom_status_id <>2)  or re.custom_status_id='9' or re.custom_num='0') ");// re.custom_status_id <>2：客户群状态不等于删除状态
			
			if(keywords != null && !"".equals(keywords)) {
				buffer.append(" and (re.custom_group_id like '%" + keywords + "%' or re.custom_group_name like '%" + keywords + "%')");
			}
			buffer.append(" order by re.create_time desc");
		}
		
		log.info("执行sql="+buffer);
		
		String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(),pager.getPageSize());
		List list = this.getJdbcTemplate().queryForList(sqlExt.toString(), plist.toArray());
		List listSize = this.getJdbcTemplate().queryForList(buffer.toString(), plist.toArray());
		pager.setTotalSize(listSize.size());  // 总记录数
		
		return list;
	}
	
	public String isCustomDeletable(String customGrpId,String userId) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select mcc.CUSTGROUP_ID");
		buffer.append(" from mtl_camp_seginfo mcs");
		buffer.append(" left join MTL_CAMPSEG_CUSTGROUP mcc");
		buffer.append(" on mcs.campseg_id = mcc.campseg_id");
		buffer.append(" where mcc.CUSTGROUP_TYPE='CG' and mcc.CUSTGROUP_ID = ?");
		buffer.append(" and mcs.CAMPSEG_STAT_ID NOT IN (91)");
		buffer.append(" union select custom_group_id from MTL_GROUP_INFO where create_user_id<>'"+userId+"' and custom_group_id ='"+customGrpId+"' ");
		buffer.append(" union select custom_group_id from mtl_group_push_info where create_push_target_id<>'"+userId+"' and custom_group_id ='"+customGrpId+"' ");
		List params = new ArrayList();
		params.add(customGrpId);
		List data = this.getJdbcTemplate().queryForList(buffer.toString(), params.toArray());
		
		return data.size()>0?"0":"1";
	}
	
	@Override
	public List searchCustomDetail(String customGrpId) {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("select t1.*,");
		buffer.append(" case t1.update_cycle when 2 then '月' when 3 then '日' else '一次性' end as update_cycle_name,");
		buffer.append(" case  t1.custom_status_id ")
		          .append(" when 0 then '无效' ")
		          .append("  when 1 then '有效' ")
		          .append("  when 3 then '提取处理中' ")
		          .append("  when 4 then '提取失败' ")
		          .append("  when 9 then '客户群导入失败' ")
		          .append(" end as custom_status, ");
		buffer.append(" t2.data_date from (");
		buffer.append(" select * from MTL_GROUP_INFO cus where cus.custom_group_id = ?) t1 left join (");
		buffer.append(" select * from (");
		buffer.append(" SELECT ROW_NUMBER() OVER(PARTITION BY custom_group_id ORDER BY data_time DESC) rn,t.* ");
		buffer.append(" FROM MTL_CUSTOM_LIST_INFO t ) info where info.rn = 1");
		buffer.append(" ) t2 on t1.custom_group_id = t2.custom_group_id");
		
		log.info("searchCustomDetail执行sql="+buffer);
		
		List params = new ArrayList();
		params.add(customGrpId);
		
		return this.getJdbcTemplate().queryForList(buffer.toString(), params.toArray());
	}
	
	@Override
	public String getExistQueueCfgId(String group_into_id, String queue_id) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT cfg_id from MTL_STC_10086_QUEUE where GROUP_INTO_ID=? and QUEUE_ID=? ");
		List params = new ArrayList();
		params.add(group_into_id);
		params.add(queue_id);
		List list = this.getJdbcTemplate().queryForList(sb.toString(),params.toArray());
		if(null!=list && list.size()!=0){
			return ((Map)list.get(0)).get("cfg_id")+"";
		}else {
			return null;
		}
	}
	@Override
	public String getMaxQueueCfgId() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT max(CFG_ID)+1 cfg_id from I_SYNC_data_CFG ");
		List list = this.getJdbcTemplate().queryForList(sb.toString());
		if(null!=list && list.size()!=0){
			return ((Map)list.get(0)).get("cfg_id")+"";
		}else {
			return null;
		}
	}
	
	@Override
	public int insertQueue(String group_into_id, String group_cycle, String queue_id,
						   String data_date, String cfg_id,String group_table_name) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(" MERGE INTO MTL_STC_10086_QUEUE A ");
		sb.append(" USING (SELECT '"+group_into_id+"' GROUP_INTO_ID, '"+group_cycle+"' GROUP_CYCLE,'"+queue_id+"' QUEUE_ID,'"+data_date+"' data_date,'"+cfg_id+"' CFG_ID, '"+group_table_name+"' GROUP_TABLE_NAME FROM DUAL) B");
		sb.append(" ON (A.QUEUE_ID = B.QUEUE_ID AND A.GROUP_INTO_ID = B.GROUP_INTO_ID) ");
		sb.append(" WHEN MATCHED THEN ");
		sb.append("  UPDATE SET A.START_DATE  = SYSDATE, A.GROUP_CYCLE = B.GROUP_CYCLE, A.DATA_DATE = B.DATA_DATE,A.END_DATE=sysdate+1 ");
		sb.append(" WHEN NOT MATCHED THEN ");
		if("O".equals(group_cycle)){
			sb.append("  INSERT(A.GROUP_INTO_ID,A.GROUP_CYCLE,A.GROUP_TABLE_NAME,A.QUEUE_ID,A.START_DATE,A.STATE,A.DATA_DATE,A.CFG_ID,A.END_DATE) ");
			sb.append("  VALUES(B.GROUP_INTO_ID,B.GROUP_CYCLE,B.GROUP_TABLE_NAME,B.QUEUE_ID,SYSDATE,1,B.DATA_DATE,B.CFG_ID,sysdate+1) ");
		}else{
			sb.append("  INSERT(A.GROUP_INTO_ID,A.GROUP_CYCLE,A.GROUP_TABLE_NAME,A.QUEUE_ID,A.START_DATE,A.STATE,A.DATA_DATE,A.CFG_ID) ");
			sb.append("  VALUES(B.GROUP_INTO_ID,B.GROUP_CYCLE,B.GROUP_TABLE_NAME,B.QUEUE_ID,SYSDATE,1,B.DATA_DATE,B.CFG_ID) ");
		}
		return this.getJdbcTemplate().update(sb.toString());

	}
	
	@Override
	public void deleteCustom(String customGrpId) {
		
		StringBuffer buffer = new StringBuffer("update mtl_group_info t set t.custom_status_id = 2 where custom_group_id = ?");
		
		List params = new ArrayList();
		params.add(customGrpId);
		
		this.getJdbcTemplate().update(buffer.toString(), params.toArray());
	}
	@Override
	public List queryQueueInfo() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select * from dim_pub_10086_queue");
		List list = this.getJdbcTemplate().queryForList(sql.toString());
		return list;
	}
}
