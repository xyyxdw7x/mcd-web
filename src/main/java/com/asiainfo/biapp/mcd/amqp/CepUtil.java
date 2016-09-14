package com.asiainfo.biapp.mcd.amqp;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;

import com.asiainfo.biapp.mcd.jms.util.SimpleCache;
import com.asiainfo.biapp.mcd.jms.util.SpringContext;
import com.asiainfo.biapp.mcd.kafka.cep.CepKafKaProducer;
import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

public class CepUtil {
	private static final Logger log = LogManager.getLogger();

	private static final long expireTime = 2*60*60;
	private static final long expireTime5 = 5*60;
	private static final String QUERY_CAMPSEG_ID_BY_EVENT_ID = "select campseg_id from mcd_camp_def where cep_event_id = ? and campseg_stat_id = 54";
	private static final String QUERY_SEND_CONTENT = "select CHANNEL_CAMP_CONTENT FROM mcd_camp_channel_list  WHERE CAMPSEG_ID=?";
	/**
	 * 消息通知cep复杂事件开始执行
	 * @param cepEventId
	 */
	public static void registCepEvent(String cepEventId) throws Exception {
		sendCepEplMessage(cepEventId, "1");
	}

	public static void restartCepEvent(String cepEventId) throws Exception {
		sendCepEplMessage(cepEventId, "4");
	}

	public static void stopCepEvent(String cepEventId) throws Exception {
		sendCepEplMessage(cepEventId, "2");
	}

	public static void finishCepEvent(String cepEventId) throws Exception {
		sendCepEplMessage(cepEventId, "3");
	}

	private static void sendCepEplMessage(String cepEventId, String ctrlType) {
		/*
		 * json字符串通知CEP {“epl_id":"XXXXX","ctrl_type","XXXX"}
		 * ctrl_type 对应值 1,注册，2.暂停，3，终止，4，重启
		 * */
		CepKafKaProducer cepKafKaProducer = null;
		try {
			cepKafKaProducer = new CepKafKaProducer();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("epl_id", cepEventId);
			jsonObj.put("ctrl_type", ctrlType);
			cepKafKaProducer.send(CepCONST.ROUTE_EPL_EXTERNAL_REQ, jsonObj.toString());
		} catch (Exception e) {
			log.error("", e);
		}finally{
			if(cepKafKaProducer !=null ){
				cepKafKaProducer.close();
			}
		}
	}
	
	public static String[] getActivityCodeByCepId(String cepEventId) {
		
		String[] activityCode = (String[])SimpleCache.getInstance().get("CEP_EVENT_ID"+cepEventId);
		if(activityCode == null){
			JdbcTemplate jt = SpringContext.getBean("jdbcTemplate", JdbcTemplate.class);
			List list = jt.queryForList(QUERY_CAMPSEG_ID_BY_EVENT_ID, new Object[]{cepEventId});
			if(CollectionUtils.isNotEmpty(list)){
				activityCode = new String[list.size()];
				for(int i=0; i<list.size(); i++){
					activityCode[i] = (String)((Map)list.get(i)).get("campseg_id");
				}
				SimpleCache.getInstance().put("CEP_EVENT_ID"+cepEventId, activityCode, expireTime);
			} else {
				activityCode = new String[0];
			}
		}
		return activityCode;
	}

	public static void refreshCepActivityCode(String cepEventId) {
		String[] activityCode = null;
		JdbcTemplate jt = SpringContext.getBean("jdbcTemplate", JdbcTemplate.class);
		List list = jt.queryForList(QUERY_CAMPSEG_ID_BY_EVENT_ID, new Object[]{cepEventId});
		if(CollectionUtils.isNotEmpty(list)){
			activityCode = new String[list.size()];
			for(int i=0; i<list.size(); i++){
				activityCode[i] = (String)((Map)list.get(i)).get("campseg_id");
			}
			SimpleCache.getInstance().put("CEP_EVENT_ID"+cepEventId, activityCode, expireTime);
		} else {
			activityCode = new String[0];
		}
		SimpleCache.getInstance().put("CEP_EVENT_ID"+cepEventId, activityCode, expireTime);
	}
	public static String getSendContent(String campsegId) {
		String sendContent = (String)SimpleCache.getInstance().get("SEND_CONTENT"+campsegId);
		if(StringUtils.isEmpty(sendContent)) {
			JdbcTemplate jt = SpringContext.getBean("jdbcTemplate", JdbcTemplate.class);
			List list = jt.queryForList(QUERY_SEND_CONTENT, new Object[]{campsegId});
			if(CollectionUtils.isNotEmpty(list)){
				sendContent = (String)((Map)list.get(0)).get("CHANNEL_CAMP_CONTENT");
			}
			SimpleCache.getInstance().put("SEND_CONTENT"+campsegId, sendContent, expireTime);
		}
		return sendContent;
	}
	public static String getPlanId(String campsegId) {
		String planId = (String)SimpleCache.getInstance().get("PLAN_ID"+campsegId);
		if(planId == null){
			IMpmCampSegInfoDao seginfoDao = SpringContext.getBean("mpmCampSegInfoDao", IMpmCampSegInfoDao.class);
			try {
				planId = seginfoDao.getCampSegInfo(campsegId).getPlanId();
				if(planId == null) {
					planId = "";
				}
			} catch (Exception e) {
				log.error("",e);
				planId = "";
			}
			SimpleCache.getInstance().put("PLAN_ID"+campsegId,planId,expireTime);
		}
		return planId;
	}
	
}
