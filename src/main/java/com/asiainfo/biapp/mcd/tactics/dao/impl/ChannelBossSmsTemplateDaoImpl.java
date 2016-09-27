package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.tactics.dao.ChannelBossSmsTemplateDao;
import com.asiainfo.biapp.mcd.tactics.vo.ChannelBossSmsTemplate;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimSmsbossTemplateType;

/**
 * 
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-12-21 上午9:27:59
 * @version 1.0
 */
@Repository("channelBossSmsTemplateDao")
public class ChannelBossSmsTemplateDaoImpl  extends JdbcDaoBase implements ChannelBossSmsTemplateDao {
	private static Logger log = LogManager.getLogger();

	@Override
	public List<ChannelBossSmsTemplate> initMtlChannelBossSmsTemplate() {
		List<ChannelBossSmsTemplate> result = new ArrayList<ChannelBossSmsTemplate>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("SELECT  * FROM MTL_CHANNEL_BOSS_SMS_TEMPLATE T ORDER BY T.TEMPLATE_TYPE_ID, T.Template_Id");
			result = this.getJdbcTemplate().query(sbuffer.toString(),new VoPropertyRowMapper<ChannelBossSmsTemplate>(ChannelBossSmsTemplate.class));
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}
	
	@Override
	public List<ChannelBossSmsTemplate> getBossSmsTemplateByType(String type) {
		List<ChannelBossSmsTemplate> result = new ArrayList<ChannelBossSmsTemplate>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("SELECT  * FROM MTL_CHANNEL_BOSS_SMS_TEMPLATE where TEMPLATE_TYPE_ID='"+type+"'");
			result = this.getJdbcTemplate().query(sbuffer.toString(),new VoPropertyRowMapper<ChannelBossSmsTemplate>(ChannelBossSmsTemplate.class));
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}
	
	@Override
	public List<McdDimSmsbossTemplateType> getBossSmsTemplateTypes() {
		List<McdDimSmsbossTemplateType> result = new ArrayList<McdDimSmsbossTemplateType>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("SELECT  * FROM mcd_dim_smsboss_template_type t order by t.sort_num ");
			result = this.getJdbcTemplate().query(sbuffer.toString(),new VoPropertyRowMapper<McdDimSmsbossTemplateType>(McdDimSmsbossTemplateType.class));
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}


}


