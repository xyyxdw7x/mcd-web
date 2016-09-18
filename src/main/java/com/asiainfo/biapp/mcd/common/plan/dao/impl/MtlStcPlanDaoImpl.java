package com.asiainfo.biapp.mcd.common.plan.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.common.plan.dao.IMtlStcPlanDao;
import com.asiainfo.biapp.mcd.common.plan.vo.DimPlanSrvType;
import com.asiainfo.biapp.mcd.common.plan.vo.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.plan.vo.McdPlanDef;
import com.asiainfo.biapp.mcd.common.plan.vo.MtlStcPlanBean;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.MpmConfigure;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.McdPlanChannelList;

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
public class MtlStcPlanDaoImpl extends JdbcDaoBase implements IMtlStcPlanDao {
	protected final Log log = LogFactory.getLog(getClass());
	@Override
	public List<McdDimPlanType> initDimPlanType() throws Exception {
		String sql = "select * from mcd_dim_plan_type d order by d.sort_Num";
		return this.getJdbcTemplate().query(sql,new RowMapper<McdDimPlanType>() {
			@Override
			public McdDimPlanType mapRow(ResultSet rs, int index) throws SQLException {
				McdDimPlanType tmp = new McdDimPlanType();
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
		List<String> parameterList = new ArrayList<String>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT COUNT(*)")
				  .append(" FROM mcd_plan_def A ")
				  .append(" LEFT JOIN DIM_PLAN_SRV_TYPE B ON A.PLAN_SRV_TYPE = B.PLAN_TYPE_ID")
				  .append(" LEFT JOIN mcd_dim_plan_type D ON A.PLAN_TYPE = D.TYPE_ID")
				  .append(" WHERE 1=1");
			if(StringUtils.isNotEmpty(keyWords)){  //关键字查询
				if(keyWords.equals("%")){
					buffer.append(" and (A.PLAN_NAME like ").append("'%\\%%' escape '\\'").append(" OR A.PLAN_PNAME like ").append("'%\\%%' escape '\\')");
				}else{
					buffer.append(" and (A.PLAN_NAME like ? OR A.PLAN_PNAME like ?)");
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			if(StringUtils.isNotEmpty(typeId)){   //查询政策类别
				buffer.append(" and D.TYPE_ID = ?");
				parameterList.add(typeId);
			}

			if(StringUtils.isNotEmpty(cityId) && !cityId.equals(MpmConfigure.getInstance().getProperty("CENTER_CITYID"))){  //地市限制
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
	public List<McdPlanChannelList> getChannelIds(String planIds){
		StringBuffer buffer = new StringBuffer();
		List<Map<String, Object>> list = null;
		List<McdPlanChannelList> resultList = new ArrayList<McdPlanChannelList>();
		try {
				buffer.append("select mcd_plan_channel_list.*,mcd_plan_resource_list.adiv_id,mcd_dim_adiv_resouce.adiv_resource_id,mcd_dim_adiv_resouce.adiv_resource_name,")
				  .append(" mcd_dim_adiv_resouce.adiv_resource_desc,mcd_dim_adiv_resouce.adiv_content_url,mcd_dim_adiv_resouce.adiv_content_to_url")
				  .append(" from mcd_plan_channel_list")
				  .append(" left join mcd_plan_resource_list on mcd_plan_channel_list.plan_id=mcd_plan_resource_list.plan_id")
				  .append(" left join mcd_dim_adiv_resouce on mcd_plan_resource_list.chn_resource_id=mcd_dim_adiv_resouce.adiv_resource_id")
				  .append(" where mcd_plan_channel_list.plan_id in ("+planIds+")");
			list = this.getJdbcTemplate().queryForList(buffer.toString());
			for (Map<String, Object> map : list) {
				McdPlanChannelList mtlStcPlanChannel = new McdPlanChannelList();
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
		List<String> parameterList = new ArrayList<String>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT COUNT(*)")
				  .append(" FROM mcd_plan_def A ")
				  .append(" LEFT JOIN DIM_PLAN_SRV_TYPE B ON A.PLAN_SRV_TYPE = B.PLAN_TYPE_ID")
				  .append(" LEFT JOIN mcd_dim_plan_type D ON A.PLAN_TYPE = D.TYPE_ID")
				  .append(" WHERE 1=1");
			if(StringUtils.isNotEmpty(keyWords)){  //关键字查询
				if(keyWords.equals("%")){
					buffer.append(" and (A.PLAN_NAME like ").append("'%\\%%' escape '\\'").append(" OR A.PLAN_ID like ").append("'%\\%%' escape '\\')");
				}else{
					buffer.append(" and (A.PLAN_NAME like ? OR A.PLAN_ID like ?)");
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			if(StringUtils.isNotEmpty(typeId)){   //查询政策类别
				buffer.append(" and D.TYPE_ID =?");
				parameterList.add(typeId);
			}
			if(StringUtils.isNotEmpty(planTypeId)){ //查询粒度
				buffer.append(" and B.PLAN_TYPE_ID =?");
				parameterList.add(planTypeId);
			}
	
			if(StringUtils.isNotEmpty(cityId) && !cityId.equals("999")){  //地市限制
				buffer.append(" and (instr('|'||A.CITY_ID||'|','|"+cityId+"|',1,1)>0 or instr('|'||A.CITY_ID||'|','|999|',1,1)>0)");
			}
			if(StringUtils.isNotEmpty(channelTypeId)){ //渠道类型查询
				buffer.append(" and A.PLAN_ID in ( select PLAN_ID from mcd_plan_channel_list WHERE CHANNEL_ID = ?)");
				parameterList.add(channelTypeId);
			}
			
			if("1".equals(isDoubleSelect) && StringUtils.isEmpty(channelTypeId)){   //多产品默认选择三个三个渠道
				buffer.append(" and A.PLAN_ID in ( select unique PLAN_ID from mcd_plan_channel_list WHERE CHANNEL_ID in ('902','903','906'))");
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
		List<String> parameterList = new ArrayList<String>();
		List<MtlStcPlanBean> resultList = new ArrayList<MtlStcPlanBean>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT A.PLAN_ID,A.PLAN_NAME,A.PLAN_STARTDATE,A.PLAN_ENDDATE,A.PLAN_DESC,A.ID,A.STATUS,A.CREATE_USERID,A.CREATE_DATE,A.PLAN_PNAME CAMP_NAME,A.PLAN_PID CAMP_ID,A.FEE_DESC,A.MATERIAL_DESC," +
				   "A.CITY_ID,A.PLAN_TYPE,A.CHANNELS,A.LEVEL_ID,A.CAMPSEG_TYPE_ID,B.PLAN_TYPE_ID,B.PLAN_TYPE_NAME,")
				  .append(" D.TYPE_ID,D.TYPE_NAME,")
				  .append(" (select to_char(wm_concat(c.city_name)) from mcd_dim_city c where '|' || A.city_id || '|' like '%|' || c.city_id || '|%' ) city_name,")
				  .append(" (CASE WHEN A.PLAN_ID IN (SELECT F.PLAN_ID FROM  mcd_camp_def F WHERE F.CAMPSEG_STAT_ID NOT IN ('90', '20', '91') AND F.city_id = '"+cityId+"' )")
				  .append(" THEN 1 ELSE 0 END) IS_USERD")
				  .append(" FROM mcd_plan_def A ")
				  .append(" LEFT JOIN DIM_PLAN_SRV_TYPE B ON A.PLAN_SRV_TYPE = B.PLAN_TYPE_ID")
				  .append(" LEFT JOIN mcd_dim_plan_type D ON A.PLAN_TYPE = D.TYPE_ID")
				  .append(" WHERE 1=1");
			if(StringUtils.isNotEmpty(keyWords)){  //关键字查询
				if(keyWords.equals("%")){
					buffer.append(" and (A.PLAN_NAME like ").append("'%\\%%' escape '\\'").append(" OR A.PLAN_PNAME like ").append("'%\\%%' escape '\\')");
				}else{
					buffer.append(" and (A.PLAN_NAME like ? OR A.PLAN_PNAME like ?)");
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			if(StringUtils.isNotEmpty(typeId)){   //查询政策类别
				buffer.append(" and D.TYPE_ID = ?");
				parameterList.add(typeId);
			}
		
			if(StringUtils.isNotEmpty(cityId) && !cityId.equals(MpmConfigure.getInstance().getProperty("CENTER_CITYID"))){  //地市限制
				buffer.append(" and (instr('|'||A.CITY_ID||'|','|"+cityId+"|',1,1)>0 or instr('|'||A.CITY_ID||'|','|999|',1,1)>0)");
			}
			buffer.append(" and B.PLAN_TYPE_ID ='2'");
			buffer.append(" ORDER BY A.PLAN_STARTDATE DESC,A.PLAN_ID DESC");
			String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(),pager.getPageSize());
			list = this.getJdbcTemplate().queryForList(sqlExt.toString(),parameterList.toArray());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Map<String,Object> map : list) {
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
				if(StringUtils.isEmpty((String) map.get("CAMP_NAME"))){
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
				if(StringUtils.isNotEmpty((String) map.get("PLAN_TYPE_NAME"))){
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
	public List<Map<String,Object>> checkIsUserd(String planIds,String cityId){
		List<Map<String, Object>> list = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT unique F.PLAN_ID FROM mcd_camp_def F")
				  .append(" WHERE F.CAMPSEG_STAT_ID NOT IN ('90', '20', '91')")
				  .append(" AND F.city_id = '"+cityId+"' ")
				  .append(" and plan_id in ("+planIds+")");
			list = this.getJdbcTemplate().queryForList(buffer.toString());
		} catch (Exception e) {
		}
		return list;
	}
	
	@Override
	public List<DimPlanSrvType> getGradeList() throws MpmException {
		List<Map<String, Object>> list = null;
		List<DimPlanSrvType> dimPlanSrvTypeList = new ArrayList<DimPlanSrvType>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("select * from DIM_PLAN_SRV_TYPE");
			list = this.getJdbcTemplate().queryForList(sbuffer.toString());
			for (Map<String, Object> map : list) {
				DimPlanSrvType dimPlanSrvType = new DimPlanSrvType();
				dimPlanSrvType.setTypeId((String) map.get("PLAN_TYPE_ID"));
				dimPlanSrvType.setTypeName((String) map.get("PLAN_TYPE_NAME"));
				dimPlanSrvTypeList.add(dimPlanSrvType);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return dimPlanSrvTypeList;
	}
	
	@Override
	public List<MtlStcPlanBean> getMtlStcPlanByCondation(String keyWords,String typeId, String channelTypeId,String planTypeId,String cityId,String isDoubleSelect,Pager pager) {
		List<Map<String, Object>> list = null;
		List<MtlStcPlanBean> resultList = new ArrayList<MtlStcPlanBean>();
		List<String> parameterList = new ArrayList<String>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT A.PLAN_ID,A.PLAN_NAME,A.PLAN_STARTDATE,A.PLAN_ENDDATE,A.PLAN_DESC,A.ID,A.STATUS,A.CREATE_USERID,A.CREATE_DATE,A.FEE_DESC,A.MATERIAL_DESC," +
				   "A.CITY_ID,A.PLAN_TYPE,A.CHANNELS,A.LEVEL_ID,A.CAMPSEG_TYPE_ID,A.PLAN_PID,A.PLAN_PNAME,A.PLAN_SRV_TYPE,B.PLAN_TYPE_ID,B.PLAN_TYPE_NAME,")
				  .append(" D.TYPE_ID,D.TYPE_NAME,")
				  .append(" (select to_char(wm_concat(c.city_name)) from mcd_dim_city c where c.city_id like '%A.city_id%' ) city_name")
				  .append(" FROM mcd_plan_def A ")
				  .append(" LEFT JOIN DIM_PLAN_SRV_TYPE B ON A.PLAN_SRV_TYPE = B.PLAN_TYPE_ID")
				  .append(" LEFT JOIN mcd_dim_plan_type D ON A.PLAN_TYPE = D.TYPE_ID")
			 	  .append(" WHERE 1=1 ");
			if(StringUtils.isNotEmpty(keyWords)){  //关键字查询
				if(keyWords.equals("%")){
					buffer.append(" and (A.PLAN_NAME like ").append("'%\\%%' escape '\\'").append(" OR A.PLAN_ID like ").append("'%\\%%' escape '\\')");
				}else{
					buffer.append(" and (A.PLAN_NAME like ? OR A.PLAN_ID like ?)");
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			if(StringUtils.isNotEmpty(typeId)){   //查询政策类别
				buffer.append(" and D.TYPE_ID =?");
				parameterList.add(typeId);
			}
			if(StringUtils.isNotEmpty(planTypeId)){ //查询粒度
				buffer.append(" and B.PLAN_TYPE_ID =?");
				parameterList.add(planTypeId);
			}
			if(StringUtils.isNotEmpty(cityId) && !cityId.equals("999")){ //地市人员只能看本地市的产品
				buffer.append(" and instr(A.CITY_ID,'"+cityId+"',1,1)>0 ");//CITY_ID字段中包含当前地市
			}
			if(StringUtils.isNotEmpty(channelTypeId)){ //渠道类型查询
				buffer.append(" and A.PLAN_ID in ( select unique PLAN_ID from mcd_plan_channel_list WHERE CHANNEL_ID = ?)");
				parameterList.add(channelTypeId);
			}
			
			if("1".equals(isDoubleSelect) && StringUtils.isEmpty(channelTypeId)){   //多产品默认选择三个三个渠道
				buffer.append(" and A.PLAN_ID in ( select unique PLAN_ID from mcd_plan_channel_list WHERE CHANNEL_ID in ('902','903','906'))");
			}
			
			buffer.append(" AND SYSDATE BETWEEN nvl2(A.PLAN_STARTDATE,A.PLAN_STARTDATE,TO_DATE('19000101','YYYYMMDD'))  AND nvl2(A.PLAN_ENDDATE,A.PLAN_ENDDATE,TO_DATE('21000101','YYYYMMDD'))");
			buffer.append(" ORDER BY A.PLAN_STARTDATE DESC,A.PLAN_ID DESC");
			String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(),pager.getPageSize());
			log.info("执行sql="+sqlExt);
			list = this.getJdbcTemplate().queryForList(sqlExt, parameterList.toArray());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for (Map<String,Object> map : list) {
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
				if(StringUtils.isNotEmpty((String) map.get("PLAN_TYPE_NAME"))){
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
    public McdPlanDef getMtlStcPlanByPlanId(String planId) {
        String sql = " select * from mcd_plan_def A where A.PLAN_ID=?";
        Object[] args=new Object[]{planId};
        int[] argTypes=new int[]{Types.VARCHAR};
        log.info("执行sql="+sql);
        List<McdPlanDef> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<McdPlanDef>(McdPlanDef.class));
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
    public McdDimPlanType getPlanTypeById(String planTypeId) {
        String sql = " select * from mcd_dim_plan_type A where A.TYPE_ID=?";
        Object[] args=new Object[]{planTypeId};
        int[] argTypes=new int[]{Types.VARCHAR};
        log.info("执行sql="+sql);
        List<McdDimPlanType> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<McdDimPlanType>(McdDimPlanType.class));
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    
	public McdPlanDef getMtlStcPlanByPlanID(String planID){
		final String sql="select * from mcd_plan_def where plan_id=?";
		log.info("执行sql="+sql);
		List<McdPlanDef> list=this.getJdbcTemplate().query(sql,new Object[]{planID}, 
				new VoPropertyRowMapper<McdPlanDef>(McdPlanDef.class));
		if(list!=null&&list.size()>0){
			return (McdPlanDef) list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<McdDimPlanType> getChildrens(String pid){
		String sql = "select TYPE_ID,TYPE_NAME,TYPE_PID from MCD_DIM_PLAN_TYPE where TYPE_PID='"+pid+"'";
		List<McdDimPlanType> subTypes=this.getJdbcTemplate().query(sql, new VoPropertyRowMapper<McdDimPlanType>(McdDimPlanType.class));
		return subTypes;
	}
	
	@Override
	public List<McdDimPlanType> getAll(){
		String sql = "select TYPE_ID,TYPE_NAME,TYPE_PID from MCD_DIM_PLAN_TYPE";
		List<McdDimPlanType> subTypes=this.getJdbcTemplate().query(sql, new VoPropertyRowMapper<McdDimPlanType>(McdDimPlanType.class));
		return subTypes;
	}


	@Override
	public List<Map<String,Object>> execQuerySql(String sql, List<Object> params) {
		log.info("执行sql="+sql);
		List<Map<String,Object>> res = null;
		if(params==null ||params.size()==0){
			res = this.getJdbcTemplate().queryForList(sql);	
		}else{	
			res = this.getJdbcTemplate().queryForList(sql, params);		
		}
		return res;
	}
	@Override
	public int execQuerySqlCount(String sql, List<Object> params) {
		log.info("执行sql="+sql);
		int count = 0;
		if(params==null ||params.size()==0){
			count = this.getJdbcTemplate().queryForObject(sql,Integer.class);	
		}else{	
			count = this.getJdbcTemplate().queryForObject(sql, params.toArray(),Integer.class);		
		}
		return count;
	}
	
	
}
