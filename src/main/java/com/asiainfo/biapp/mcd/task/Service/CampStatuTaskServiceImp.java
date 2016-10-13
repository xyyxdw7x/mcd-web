package com.asiainfo.biapp.mcd.task.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.task.dao.CampStatuTaskDao;
import com.google.common.collect.Maps;
@Service("campStatuTaskService")
public class CampStatuTaskServiceImp implements CampStatuTaskService {
	@Resource(name="campStatuTaskDao")
	private CampStatuTaskDao campStatuTaskDao;
	@Resource(name="mpmCampSegInfoDao")
	private IMpmCampSegInfoDao mpmCampSegInfoDao;
	
	private final Log log = LogFactory.getLog(getClass());
	
	@Override
	public void doCampTask() {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>进入doCampTask方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		campStatuTaskDao.updateOutDateCustStatus();//失效日期已到的客户群修改其状态为0
		
		//查询（策略状态 为执行中(54)和暂停(59)）过期的任务
		List<Map<String, Object>> camps = campStatuTaskDao.queryOutDateCamps();
		
		if(CollectionUtils.isNotEmpty(camps)){
			log.info("camps size="+camps.size());
			//-----过期任务变完成，并删除调度表中数据，然后客户群表
			Map<String,String> map = this.group(camps);
			String campIds = map.get("campIds");//,分隔的策略id
			String taskIds = map.get("taskIds");//,分隔的任务id
			
			if(!"".equals(taskIds)){
				log.info(" taskIds size="+taskIds);
				//过期的任务变完成、过期的策略变完成
				campStatuTaskDao.batchupdateTaskStatu(taskIds, McdCONST.TASK_STATUS_END);
				//更新周期性任务表(MTL_CAMPSEG_TASK_DATE)中过期的任务的状态为完成状态
				campStatuTaskDao.updateTaskDate(taskIds, McdCONST.TASK_STATUS_END); 
				//删除调度表(MTL_SMS_CHANNEL_SCHEDULE)中过期的调度任务
				campStatuTaskDao.delOutDateScedule(taskIds);
				
				// 将过期的任务的状态变成完成后还得将任务的客户群表删除
				List<String> sqllist = this.getSqls(camps);
				String[] sqls;
				if(sqllist!=null && sqllist.size()>0){
					sqls = this.list2Array(sqllist);
					campStatuTaskDao.updateInMemDelCustGroupTab(sqls);
				}
				log.info("task execute end");
			}
			//过期策略变完成
			campStatuTaskDao.batchupdateCampStatu(campIds, Short.parseShort(McdCONST.MPM_CAMPSEG_STAT_HDWC));
			log.info("batchupdateCampStatu end  campIds="+campIds);
			
			//定时任务---策略完成时增加实时事件支持   -----add by lixq10  begin
			/*for(int i = 0;i<camps.size();i++){
				String campsegId = (String)camps.get(i).get("CAMPSEG_ID");
				McdCampDef mtlCampsegInfo = null;
				try {
					mtlCampsegInfo = mpmCampSegInfoDao.getCampSegInfo(campsegId);
					if(null != mtlCampsegInfo && StringUtil.isNotEmpty(mtlCampsegInfo.getCepEventId())){
						CepUtil.finishCepEvent(mtlCampsegInfo.getCepEventId());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}*/
		}
		//待执行策略变执行中
		campStatuTaskDao.updateStartedCampStatu(Short.parseShort(McdCONST.MPM_CAMPSEG_STAT_ZXZT),Short.parseShort(McdCONST.MPM_CAMPSEG_STAT_DDCG));
			
	}
	
	

	private Map<String,String> group(List<Map<String, Object>> camps) {
		Map<String,String> map = Maps.newHashMap();
		StringBuffer  campIds =  new StringBuffer("");
		StringBuffer  taskIds =  new StringBuffer("");

		for(int i=0;i<camps.size();i++){
			Map<String, Object> tmp = camps.get(i);
			String campId = tmp.get("CAMPSEG_ID").toString();
			String taskId = tmp.get("TASK_ID")==null?"":tmp.get("TASK_ID").toString();
			campIds.append(campId);
			if(i!=camps.size()-1){
				campIds.append(",");
			}
			if(!"".equals(taskId)){
				taskIds.append(taskId).append(",");
			}
		}
	
		map.put("campIds", campIds.toString());
		if(taskIds.length()>0){
			map.put("taskIds",taskIds.substring(0, taskIds.length()-1) );
		}else{
			map.put("taskIds","");
		}
	
		return map;
	}
	
	private String[] list2Array(List<String> list){
		String[] arry = null;
		if(list!=null && list.size()>0){
			arry = new String[list.size()];
			for(int i=0;i<list.size();i++){
				arry[i] = list.get(i);
			}
		}
	
		return arry;
	}

	private List<String> getSqls(List<Map<String, Object>> tasks){
		List<String> sqls = new ArrayList<String>();
		for(int i=0;i<tasks.size();i++){
			Map<String, Object> map = tasks.get(i);
			String tmpTableName =map.get("TASK_SENDODD_TAB_NAME")==null?"":map.get("TASK_SENDODD_TAB_NAME").toString();
			if(!"".equals(tmpTableName)){
				if(campStatuTaskDao.queryInMemTableExists(tmpTableName)){
					String tempSql = "drop table "+ tmpTableName;
					sqls.add(tempSql);
				}
			}
		}
		return sqls;
	}
}
