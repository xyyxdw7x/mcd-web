package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlChannelDefDao;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDef;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefCall;

/**
 * Created on Dec 1, 2006 11:20:05 AM
 *
 * <p>Title: </p>
 * <p>Description: 活动波次营销渠道定义表操作DAO实现类</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
@Repository("mtlChannelDefDao")
public class MtlChannelDefDaoImpl extends JdbcDaoBase implements IMtlChannelDefDao {
	public void saveMtlChannelDef(MtlChannelDef def) throws Exception {
		//TODO: this.getHibernateTemplate().save(def);
	}
	
	public void deleteMtlChannelDef(String campsegId) throws Exception {/*
		String sql = "from MtlChannelDef mcd where mcd.id.campsegId='" + campsegId + "'";
		this.getHibernateTemplate().deleteAll(this.getHibernateTemplate().find(sql));
		this.getHibernateTemplate().flush();*/
	}
	
    /**
     * 保存渠道对应表——外呼
     * @param mtlChannelDefCall
     */
	@Override
	public void saveMtlChannelDefCall(MtlChannelDefCall mtlChannelDefCall) {
		//TODO: this.getHibernateTemplate().save(mtlChannelDefCall);
	}
	
	/**
	 * 取策略包下子策略所用营销渠道
	 * 2015-8-15 9:36:24
	 * @author gaowj3
	 * @param campsegId 策略ID
	 * @return
	 */
	@Override
	public List findChildChannelIdList(String campsegId) {
		StringBuffer sql = new StringBuffer("  select distinct mcd.channel_id from mtl_channel_def mcd where mcd.campseg_id in (  "); 	
		sql.append(" select mcs.campseg_id from mtl_camp_seginfo mcs where mcs.campseg_pid =  ?  ") ; 	
		sql.append(" )") ;
		List list= this.getJdbcTemplate().queryForList(sql.toString(), new Object[] { campsegId});
		return list;
	}
    /**
     * 根绝策略ID查询渠道信息
     * @param campsegId
     * @return
     */
    @Override
    public List getMtlChannelDefs(String campsegId) {
        StringBuffer sql = new StringBuffer("  select mcd.targer_user_nums,dmc.channel_name from mtl_channel_def mcd  ");   
        sql.append(" left join dim_mtl_channel dmc on mcd.channel_id = dmc.channel_id   ") ;    
        sql.append(" where mcd.campseg_id = ? ") ;
        List parameterList = new ArrayList();
        parameterList.add(campsegId);
        List list = this.getJdbcTemplate().queryForList(sql.toString(),parameterList.toArray());
        return list;
    }
    
    /**
     * 删除外呼渠道
     * @param campsegId
     * @param channelId
     */
	@Override
	public void deleteMtlChannelDefCall(String campsegId, String channelId) {
		//TODO: jt.update("delete from mtl_channel_def_call where campseg_id = ? and channel_id = ?", new Object[]{campsegId,channelId});
	}
	
}
