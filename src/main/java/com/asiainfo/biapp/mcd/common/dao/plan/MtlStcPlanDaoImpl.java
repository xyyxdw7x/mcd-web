package com.asiainfo.biapp.mcd.common.dao.plan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanType;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlan;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlanBean;
import com.asiainfo.biapp.mcd.tactics.vo.MtlStcPlanChannel;
import com.asiainfo.biframe.utils.config.Configure;
import com.asiainfo.biframe.utils.string.StringUtil;

/**
 * Created on 4:02:56 PM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
@Repository("mtlStcPlanDao")
public class MtlStcPlanDaoImpl extends JdbcDaoBase implements MtlStcPlanDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<DimPlanType> initDimPlanType() throws Exception {
		String sql = "select * from DIM_PLAN_TYPE d order by d.sort_Num";
		//String hql = "from DimPlanType";
		return this.getJdbcTemplate().query(sql,new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				DimPlanType tmp = new DimPlanType();
				tmp.setChannelType(rs.getString("channel_type"));
				tmp.setSortNum(rs.getString("sort_num"));
				tmp.setTypeId(rs.getString("type_id"));
				tmp.setTypeName(rs.getString("type_name"));
				tmp.setTypePid(rs.getString("type_pid"));
				return tmp;
			}
		});
	}
	
	@Override
	public int searchPlanCount(String keyWords, String typeId,String cityId) {
		int count = 0;
		List parameterList = new ArrayList();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT COUNT(*)")
				  .append(" FROM MTL_STC_PLAN A ")
				  .append(" LEFT JOIN DIM_PLAN_SRV_TYPE B ON A.PLAN_SRV_TYPE = B.PLAN_TYPE_ID")
				  .append(" LEFT JOIN DIM_PLAN_TYPE D ON A.PLAN_TYPE = D.TYPE_ID")
				  .append(" WHERE 1=1");
			if(StringUtil.isNotEmpty(keyWords)){  //关键字查询
				if(keyWords.equals("%")){
					buffer.append(" and (A.PLAN_NAME like ").append("'%\\%%' escape '\\'").append(" OR A.PLAN_PNAME like ").append("'%\\%%' escape '\\')");
				}else{
					buffer.append(" and (A.PLAN_NAME like ? OR A.PLAN_PNAME like ?)");
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			if(StringUtil.isNotEmpty(typeId)){   //查询政策类别
				buffer.append(" and D.TYPE_ID = ?");
				parameterList.add(typeId);
			}

			if(StringUtil.isNotEmpty(cityId) && !cityId.equals(Configure.getInstance().getProperty("CENTER_CITYID"))){  //地市限制
				buffer.append(" and (instr('|'||A.CITY_ID||'|','|"+cityId+"|',1,1)>0 or instr('|'||A.CITY_ID||'|','|999|',1,1)>0)");
			}
			buffer.append(" and B.PLAN_TYPE_ID ='2'");
			count = this.getJdbcTemplate().queryForObject(buffer.toString(),parameterList.toArray(),Integer.class);
		}catch (Exception e) {
		}
		return count;
	}
	
//	根据planId从mtl_stc_plan_channel表中查询多个channelId
	@Override
	public List<MtlStcPlanChannel> getChannelIds(String planIds){
		StringBuffer buffer = new StringBuffer();
		List<Map<String, Object>> list = null;
		List<MtlStcPlanChannel> resultList = new ArrayList<MtlStcPlanChannel>();
		try {
				buffer.append("select mtl_stc_plan_channel.*,mtl_stc_plan_resource.adiv_id,dim_mtl_adiv_resouce.adiv_resource_id,dim_mtl_adiv_resouce.adiv_resource_name,")
				  .append(" dim_mtl_adiv_resouce.adiv_resource_desc,dim_mtl_adiv_resouce.adiv_content_url,dim_mtl_adiv_resouce.adiv_content_to_url")
				  .append(" from mtl_stc_plan_channel")
				  .append(" left join mtl_stc_plan_resource on mtl_stc_plan_channel.plan_id=mtl_stc_plan_resource.plan_id")
				  .append(" left join dim_mtl_adiv_resouce on mtl_stc_plan_resource.chn_resource_id=dim_mtl_adiv_resouce.adiv_resource_id")
				  .append(" where mtl_stc_plan_channel.plan_id in ("+planIds+")");
			list = this.getJdbcTemplate().queryForList(buffer.toString());
			for (Map map : list) {
				MtlStcPlanChannel mtlStcPlanChannel = new MtlStcPlanChannel();
				mtlStcPlanChannel.setId((String) map.get("ID"));
				mtlStcPlanChannel.setChannelId((String) map.get("CHANNEL_ID"));
				mtlStcPlanChannel.setPlanId((String) map.get("PLAN_ID"));
				mtlStcPlanChannel.setAdivId((String) map.get("adiv_id"));
				mtlStcPlanChannel.setAdivResourceId((String) map.get("adiv_resource_id"));
				mtlStcPlanChannel.setAdivResourceName((String) map.get("adiv_resource_name"));
				mtlStcPlanChannel.setAdivResourceDesc((String) map.get("adiv_resource_desc"));
				mtlStcPlanChannel.setAdivContentURL((String) map.get("adiv_content_url"));
				mtlStcPlanChannel.setAdivContentToRUL((String) map.get("adiv_content_to_url"));
				mtlStcPlanChannel.setEditURL((String) map.get("edit_url"));
				mtlStcPlanChannel.setHandleURL((String) map.get("handle_url"));			
				mtlStcPlanChannel.setSmsContent((String) map.get("sms_content"));
				mtlStcPlanChannel.setAwardMount(map.get("award_mount")+"");
				mtlStcPlanChannel.setExecContent((String) map.get("exec_content"));
				
				resultList.add(mtlStcPlanChannel);
			}
		} catch (Exception e) {
		}
		return resultList;
	}

	@Override
	public int getMtlStcPlanByCondationCount(String keyWords, String typeId,String channelTypeId,String planTypeId,String cityId,String isDoubleSelect) {
		int count = 0;
		List parameterList = new ArrayList();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT COUNT(*)")
				  .append(" FROM MTL_STC_PLAN A ")
				  .append(" LEFT JOIN DIM_PLAN_SRV_TYPE B ON A.PLAN_SRV_TYPE = B.PLAN_TYPE_ID")
				  .append(" LEFT JOIN DIM_PLAN_TYPE D ON A.PLAN_TYPE = D.TYPE_ID")
				  .append(" WHERE 1=1");
			if(StringUtil.isNotEmpty(keyWords)){  //关键字查询
				if(keyWords.equals("%")){
					buffer.append(" and (A.PLAN_NAME like ").append("'%\\%%' escape '\\'").append(" OR A.PLAN_ID like ").append("'%\\%%' escape '\\')");
				}else{
					buffer.append(" and (A.PLAN_NAME like ? OR A.PLAN_ID like ?)");
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			if(StringUtil.isNotEmpty(typeId)){   //查询政策类别
				buffer.append(" and D.TYPE_ID =?");
				parameterList.add(typeId);
			}
			if(StringUtil.isNotEmpty(planTypeId)){ //查询粒度
				buffer.append(" and B.PLAN_TYPE_ID =?");
				parameterList.add(planTypeId);
			}
	
			if(StringUtil.isNotEmpty(cityId) && !cityId.equals("999")){  //地市限制
				buffer.append(" and (instr('|'||A.CITY_ID||'|','|"+cityId+"|',1,1)>0 or instr('|'||A.CITY_ID||'|','|999|',1,1)>0)");
			}
			if(StringUtil.isNotEmpty(channelTypeId)){ //渠道类型查询
				buffer.append(" and A.PLAN_ID in ( select PLAN_ID from MTL_STC_PLAN_CHANNEL WHERE CHANNEL_ID = ?)");
				parameterList.add(channelTypeId);
			}
			
			if("1".equals(isDoubleSelect) && StringUtil.isEmpty(channelTypeId)){   //多产品默认选择三个三个渠道
				buffer.append(" and A.PLAN_ID in ( select unique PLAN_ID from MTL_STC_PLAN_CHANNEL WHERE CHANNEL_ID in ('902','903','906'))");
			}
			
			buffer.append(" AND SYSDATE BETWEEN nvl2(A.PLAN_STARTDATE,A.PLAN_STARTDATE,TO_DATE('19000101','YYYYMMDD'))  AND nvl2(A.PLAN_ENDDATE,A.PLAN_ENDDATE,TO_DATE('21000101','YYYYMMDD'))");
			count = this.getJdbcTemplate().queryForObject(buffer.toString(),parameterList.toArray(),Integer.class);
		}catch (Exception e) {
		}
		return count;
	}
	
	@Override
	public List<MtlStcPlanBean> searchPlan(String keyWords,String typeId,String cityId,Pager pager) {
		List<Map<String, Object>> list = null;
		List parameterList = new ArrayList();
		List<MtlStcPlanBean> resultList = new ArrayList<MtlStcPlanBean>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT A.PLAN_ID,A.PLAN_NAME,A.PLAN_STARTDATE,A.PLAN_ENDDATE,A.PLAN_DESC,A.ID,A.STATUS,A.CREATE_USERID,A.CREATE_DATE,A.PLAN_PNAME CAMP_NAME,A.PLAN_PID CAMP_ID,A.FEE_DESC,A.MATERIAL_DESC," +
				   "A.CITY_ID,A.PLAN_TYPE,A.CHANNELS,A.LEVEL_ID,A.CAMPSEG_TYPE_ID,B.PLAN_TYPE_ID,B.PLAN_TYPE_NAME,")
				  .append(" D.TYPE_ID,D.TYPE_NAME,")
				  .append(" (select to_char(wm_concat(c.city_name)) from dim_pub_city c where '|' || A.city_id || '|' like '%|' || c.city_id || '|%' ) city_name,")
				  .append(" (CASE WHEN A.PLAN_ID IN (SELECT F.PLAN_ID FROM  mtl_camp_seginfo F WHERE F.CAMPSEG_STAT_ID NOT IN ('90', '20', '91') AND F.city_id = '"+cityId+"' )")
				  .append(" THEN 1 ELSE 0 END) IS_USERD")
				  .append(" FROM MTL_STC_PLAN A ")
				  .append(" LEFT JOIN DIM_PLAN_SRV_TYPE B ON A.PLAN_SRV_TYPE = B.PLAN_TYPE_ID")
				  .append(" LEFT JOIN DIM_PLAN_TYPE D ON A.PLAN_TYPE = D.TYPE_ID")
				  .append(" WHERE 1=1");
			if(StringUtil.isNotEmpty(keyWords)){  //关键字查询
				if(keyWords.equals("%")){
					buffer.append(" and (A.PLAN_NAME like ").append("'%\\%%' escape '\\'").append(" OR A.PLAN_PNAME like ").append("'%\\%%' escape '\\')");
				}else{
					buffer.append(" and (A.PLAN_NAME like ? OR A.PLAN_PNAME like ?)");
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			if(StringUtil.isNotEmpty(typeId)){   //查询政策类别
				buffer.append(" and D.TYPE_ID = ?");
				parameterList.add(typeId);
			}
		
			if(StringUtil.isNotEmpty(cityId) && !cityId.equals(Configure.getInstance().getProperty("CENTER_CITYID"))){  //地市限制
				buffer.append(" and (instr('|'||A.CITY_ID||'|','|"+cityId+"|',1,1)>0 or instr('|'||A.CITY_ID||'|','|999|',1,1)>0)");
			}
			buffer.append(" and B.PLAN_TYPE_ID ='2'");
			buffer.append(" ORDER BY A.PLAN_STARTDATE DESC,A.PLAN_ID DESC");
			String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(),pager.getPageSize());
			list = this.getJdbcTemplate().queryForList(sqlExt.toString(),parameterList.toArray());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Map map : list) {
				MtlStcPlanBean bean = new MtlStcPlanBean();
				bean.setPlanId((String) map.get("PLAN_ID"));
				bean.setPlanName((String) map.get("PLAN_NAME"));
				if(map.get("PLAN_STARTDATE") != null){
					bean.setPlanStartdate(format.parse(String.valueOf(map.get("PLAN_STARTDATE"))));
				}
				if(map.get("PLAN_ENDDATE") != null){
					bean.setPlanEnddate(format.parse(String.valueOf(map.get("PLAN_ENDDATE"))));
				}
				bean.setPlanDesc((String) map.get("PLAN_DESC"));
				bean.setId((String) map.get("ID"));
				bean.setStatus((String) map.get("STATUS"));
				bean.setCreateUserid((String) map.get("CREATE_USERID"));
				if(map.get("CREATE_DATE") != null){
					bean.setCreateDate(format.parse(String.valueOf(map.get("CREATE_DATE"))));
				}
				bean.setCityId((String) map.get("CITY_ID"));
				bean.setPlanType((String) map.get("PLAN_TYPE"));
				bean.setChannelId((String) map.get("CHANNELS"));
				bean.setLevelId((String) map.get("LEVEL_ID"));
				bean.setLevelName((String) map.get("LEVEL_NAME"));
				bean.setCampCode((String) map.get("CAMP_CODE"));
				bean.setCampName((String) map.get("CAMP_NAME"));
				if(StringUtil.isEmpty((String) map.get("CAMP_NAME"))){
					bean.setCampName("---");
				}
				bean.setReward((String) map.get("REWARD"));
				bean.setPlanChannelId((String) map.get("PLAN_CHANNEL_ID"));
				bean.setChannelId((String) map.get("CHANNEL_ID"));
				bean.setTypeId((String) map.get("TYPE_ID"));
				bean.setTypeName((String) map.get("TYPE_NAME"));
				bean.setChannelName((String) map.get("CHANNEL_NAME"));
				bean.setCampsegTypeId(String.valueOf(map.get("CAMPSEG_TYPE_ID")));
				bean.setFeeDesc((String) map.get("FEE_DESC"));
				bean.setMaterialDesc((String) map.get("MATERIAL_DESC"));
				
				bean.setPlanPid((String) map.get("PLAN_PID"));//营销案编号
				bean.setIsUserd(String.valueOf(map.get("IS_USERD")));//是否已匹配  1：是；  0：否
				if(StringUtil.isNotEmpty((String) map.get("PLAN_TYPE_NAME"))){
					bean.setPlanTypeName((String) map.get("PLAN_TYPE_NAME"));//政策粒度
				}else{
					bean.setPlanTypeName("其他");//政策粒度
				}
				bean.setCityName((String) map.get("city_name")); //地市名称
				resultList.add(bean);
			}
		} catch (Exception e) {
		}
		
		return resultList;
	}
	@Override
	public List checkIsUserd(String planIds,String cityId){
		List<Map<String, Object>> list = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT unique F.PLAN_ID FROM mtl_camp_seginfo F")
				  .append(" WHERE F.CAMPSEG_STAT_ID NOT IN ('90', '20', '91')")
				  .append(" AND F.city_id = '"+cityId+"' ")
				  .append(" and plan_id in ("+planIds+")");
			list = this.getJdbcTemplate().queryForList(buffer.toString());
		} catch (Exception e) {
		}
		return list;
	}
	@Override
	public List<MtlStcPlanBean> getMtlStcPlanByCondation(String keyWords,String typeId, String channelTypeId,String planTypeId,String cityId,String isDoubleSelect,Pager pager) {
		List<Map<String, Object>> list = null;
		List<MtlStcPlanBean> resultList = new ArrayList<MtlStcPlanBean>();
		List parameterList = new ArrayList();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT A.PLAN_ID,A.PLAN_NAME,A.PLAN_STARTDATE,A.PLAN_ENDDATE,A.PLAN_DESC,A.ID,A.STATUS,A.CREATE_USERID,A.CREATE_DATE,A.FEE_DESC,A.MATERIAL_DESC," +
				   "A.CITY_ID,A.PLAN_TYPE,A.CHANNELS,A.LEVEL_ID,A.CAMPSEG_TYPE_ID,A.PLAN_PID,A.PLAN_PNAME,A.PLAN_SRV_TYPE,B.PLAN_TYPE_ID,B.PLAN_TYPE_NAME,")
				  .append(" D.TYPE_ID,D.TYPE_NAME,")
				  .append(" (select to_char(wm_concat(c.city_name)) from dim_pub_city c where '|' || A.city_id || '|' like '%|' || c.city_id || '|%' ) city_name")
				  .append(" FROM MTL_STC_PLAN A ")
				  .append(" LEFT JOIN DIM_PLAN_SRV_TYPE B ON A.PLAN_SRV_TYPE = B.PLAN_TYPE_ID")
				  .append(" LEFT JOIN DIM_PLAN_TYPE D ON A.PLAN_TYPE = D.TYPE_ID")
			 	  .append(" WHERE 1=1 ");
			if(StringUtil.isNotEmpty(keyWords)){  //关键字查询
				if(keyWords.equals("%")){
					buffer.append(" and (A.PLAN_NAME like ").append("'%\\%%' escape '\\'").append(" OR A.PLAN_ID like ").append("'%\\%%' escape '\\')");
				}else{
					buffer.append(" and (A.PLAN_NAME like ? OR A.PLAN_ID like ?)");
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			if(StringUtil.isNotEmpty(typeId)){   //查询政策类别
				buffer.append(" and D.TYPE_ID =?");
				parameterList.add(typeId);
			}
			if(StringUtil.isNotEmpty(planTypeId)){ //查询粒度
				buffer.append(" and B.PLAN_TYPE_ID =?");
				parameterList.add(planTypeId);
			}
			if(StringUtil.isNotEmpty(cityId) && !cityId.equals("999")){  //地市限制
				buffer.append(" and (instr('|'||A.CITY_ID||'|','|"+cityId+"|',1,1)>0 or instr('|'||A.CITY_ID||'|','|999|',1,1)>0)");
			}
			if(StringUtil.isNotEmpty(channelTypeId)){ //渠道类型查询
				buffer.append(" and A.PLAN_ID in ( select unique PLAN_ID from MTL_STC_PLAN_CHANNEL WHERE CHANNEL_ID = ?)");
				parameterList.add(channelTypeId);
			}
			
			if("1".equals(isDoubleSelect) && StringUtil.isEmpty(channelTypeId)){   //多产品默认选择三个三个渠道
				buffer.append(" and A.PLAN_ID in ( select unique PLAN_ID from MTL_STC_PLAN_CHANNEL WHERE CHANNEL_ID in ('902','903','906'))");
			}
			
			buffer.append(" AND SYSDATE BETWEEN nvl2(A.PLAN_STARTDATE,A.PLAN_STARTDATE,TO_DATE('19000101','YYYYMMDD'))  AND nvl2(A.PLAN_ENDDATE,A.PLAN_ENDDATE,TO_DATE('21000101','YYYYMMDD'))");
			buffer.append(" ORDER BY A.PLAN_STARTDATE DESC,A.PLAN_ID DESC");
			String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(),pager.getPageSize());
			list = this.getJdbcTemplate().queryForList(sqlExt.toString(), parameterList.toArray());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for (Map map : list) {
				MtlStcPlanBean bean = new MtlStcPlanBean();
				bean.setPlanId((String) map.get("PLAN_ID"));
				bean.setPlanName((String) map.get("PLAN_NAME"));
				if(map.get("PLAN_STARTDATE") != null){
					bean.setPlanStartdate(format.parse(String.valueOf(map.get("PLAN_STARTDATE"))));
				}
				if(map.get("PLAN_ENDDATE") != null){
					bean.setPlanEnddate(format.parse(String.valueOf(map.get("PLAN_ENDDATE"))));
				}
				bean.setPlanDesc((String) map.get("PLAN_DESC"));
				bean.setId((String) map.get("ID"));
				bean.setStatus((String) map.get("STATUS"));
				bean.setCreateUserid((String) map.get("CREATE_USERID"));
				if(map.get("CREATE_DATE") != null){
					bean.setCreateDate(format.parse(String.valueOf(map.get("CREATE_DATE"))));
				}
				bean.setCityId((String) map.get("CITY_ID"));
				bean.setPlanType((String) map.get("PLAN_TYPE"));
				bean.setLevelId((String) map.get("LEVEL_ID"));
				bean.setLevelName((String) map.get("LEVEL_NAME"));
				bean.setCampCode((String) map.get("CAMP_CODE"));
				bean.setCampName((String) map.get("CAMP_NAME"));
				bean.setReward((String) map.get("REWARD"));
				bean.setTypeId((String) map.get("TYPE_ID"));
				bean.setTypeName((String) map.get("TYPE_NAME"));
				bean.setChannelName((String) map.get("CHANNEL_NAME"));
				bean.setCampsegTypeId(String.valueOf(map.get("CAMPSEG_TYPE_ID")));
				bean.setPlanPname((String) map.get("PLAN_PNAME"));
				bean.setPlanSrvType((String) map.get("PLAN_SRV_TYPE"));
				bean.setFeeDesc((String) map.get("FEE_DESC"));
				bean.setMaterialDesc((String) map.get("MATERIAL_DESC"));
				
				bean.setPlanPid((String) map.get("PLAN_PID"));//营销案编号
				bean.setIsUserd(String.valueOf(map.get("IS_USERD")));//是否已匹配  1：是；  0：否
				if(StringUtil.isNotEmpty((String) map.get("PLAN_TYPE_NAME"))){
					bean.setPlanTypeName((String) map.get("PLAN_TYPE_NAME"));//政策粒度
				}else{
					bean.setPlanTypeName("其他");//政策粒度
				}
				if("省公司".equals((String) map.get("city_name"))){
					bean.setCityName("全省"); //地市名称
				}else{
					bean.setCityName((String) map.get("city_name")); //地市名称
				}
				
				resultList.add(bean);
			}
		} catch (Exception e) {
		}
		
		return resultList;
	}
    /**
     * 根据渠道ID获取渠道信息
     * @param planId
     * @return
     */
    @Override
    public MtlStcPlan getMtlStcPlanByPlanId(String planId) {
        String sql = " select * from mtl_stc_plan A where A.PLAN_ID=?";
        Object[] args=new Object[]{planId};
        int[] argTypes=new int[]{Types.VARCHAR};
        List<MtlStcPlan> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<MtlStcPlan>(MtlStcPlan.class));
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    /**
     * 根据渠道类型ID获取渠道
     * @param planType
     * @return
     */
    @Override
    public DimPlanType getPlanTypeById(String planTypeId) {
        String sql = " select * from DIM_PLAN_TYPE A where A.TYPE_ID=?";
        Object[] args=new Object[]{planTypeId};
        int[] argTypes=new int[]{Types.VARCHAR};
        List<DimPlanType> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<DimPlanType>(DimPlanType.class));
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }
}
