package com.asiainfo.biapp.mcd.bull.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.bull.dao.IBullMonitorDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMcdCampsegTaskDao;
import com.asiainfo.biapp.mcd.bull.dao.ISendType4CitysDao;
import com.asiainfo.biapp.mcd.bull.dao.ITask4BullDao;
import com.asiainfo.biapp.mcd.bull.dao.IUserDeptDao;
import com.asiainfo.biapp.mcd.bull.service.IBullMonitorService;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampTask;
import com.asiainfo.biapp.mcd.bull.vo.BullMonitor;
import com.asiainfo.biapp.mcd.bull.vo.UserDept;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;
import org.apache.commons.lang3.StringUtils;

@Service("bullMonitorService")
public class BullMonitorServiceImp implements IBullMonitorService {
	@Autowired
	private IBullMonitorDao bullMonitorDao;
	@Autowired
	private IUserDeptDao userDeptDao;
	@Autowired
	private ITask4BullDao task4BullDao;
	@Autowired
	private ISendType4CitysDao sendType4CitysDao;
	@Autowired
	private IMcdCampsegTaskDao mcdCampsegTaskDao;

	
	public IMcdCampsegTaskDao getMcdCampsegTaskDao() {
		return mcdCampsegTaskDao;
	}

	public void setMcdCampsegTaskDao(IMcdCampsegTaskDao mcdCampsegTaskDao) {
		this.mcdCampsegTaskDao = mcdCampsegTaskDao;
	}

	public void setSendType4CitysDao(ISendType4CitysDao sendType4CitysDao) {
		this.sendType4CitysDao = sendType4CitysDao;
	}

	public void setTask4BullDao(ITask4BullDao task4BullDao) {
		this.task4BullDao = task4BullDao;
	}

	public void setUserDeptDao(IUserDeptDao userDeptDao) {
		this.userDeptDao = userDeptDao;
	}

	public void setBullMonitorDao(IBullMonitorDao bullMonitorDao) {
		this.bullMonitorDao = bullMonitorDao;
	}

	@Override
	public List<BullMonitor> getBullMonitorListByDeptId(String cityId,String deptId) {

		List<BullMonitor> list = new ArrayList<BullMonitor>();
		List<Map<String, Object>> list4Map = null;
		if(!StringUtils.isEmpty(deptId)){
			list4Map = bullMonitorDao.getBullMonitorListByDept(cityId, deptId);
		}else{
			list4Map = bullMonitorDao.getBullMonitorList(cityId);
		}

		if (list4Map != null && list4Map.size() > 0) {
			for (int i = 0; i < list4Map.size(); i++) {
				Map<String, Object> map = list4Map.get(i);
				BullMonitor temp = new BullMonitor();
				try {
					QuotaUtils.map2Bean(map, temp);
				} catch (Exception e) {
					e.printStackTrace();
				}

				list.add(temp);
			}
		}

		return list;
	}

	@Override
	public List<UserDept> getDeptsAll(String cityId) {

		List<UserDept> list = new ArrayList<UserDept>();
		List<Map<String, Object>> list4Map = userDeptDao.getCityDeptes(cityId);

		if (list4Map != null && list4Map.size() > 0) {
			for (int i = 0; i < list4Map.size(); i++) {
				Map<String, Object> map = list4Map.get(i);
				UserDept temp = new UserDept();
				try {
					QuotaUtils.map2Bean(map, temp);
				} catch (Exception e) {
					e.printStackTrace();
				}

				list.add(temp);
			}
		}

		return list;
	}

	@Override
	public void batchModifyCampPri(String[] ids) {
		List<McdCampDef> list = new ArrayList<McdCampDef>();

		for (int i = 0; i < ids.length; i++) {
			McdCampDef temp = new McdCampDef();
			temp.setPriId((short) (i + 1));
			temp.setCampId(ids[i]);
			list.add(temp);
		}
		task4BullDao.updateCampPri(list);
	}

	@Override
	public void updateSendType(String cityId, String sendtype) {
		sendType4CitysDao.updateType(cityId, sendtype);
	}

	@Override
	public void btachSetTaskStatus(String[] ids, short targetStaus) {
		//支持实时事件    add by lixq10  begin
		List<Map<String, Object>> listCampsegMsg = mcdCampsegTaskDao.getCampsegMsgByTaskIds(ids);
		if(CollectionUtils.isNotEmpty(listCampsegMsg)){
			for (Map<String, Object> map : listCampsegMsg) {
				String cepEventId = (String) map.get("cep_event_id");
				if(StringUtils.isNotEmpty(cepEventId)){
					try {
						if(targetStaus == 51){ //开始
							//CepUtil.restartCepEvent(cepEventId);
						}else if(targetStaus == 59){//暂停
							//CepUtil.stopCepEvent(cepEventId);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}	
		}
		//end
		
		List<McdCampTask> list = new ArrayList<McdCampTask>();
		for (int i = 0; i < ids.length; i++) {
			McdCampTask temp = new McdCampTask();
			temp.setTaskId(ids[i]);
			temp.setExecStatus(targetStaus);
			list.add(temp);
		}
		task4BullDao.batchUpdate(list);
	}
    @Override
	public int getSendType(String cityId){
		return sendType4CitysDao.getCitySendType(cityId);
	}

	@Override
	public void addCitySendType(String cityId, int sendType) {
		sendType4CitysDao.add(cityId, sendType);
	}
	@Override
	public void batchUpdatePauseComment(String pauseComment,String campIds){
		if(pauseComment==null){
			pauseComment="";
		}
		if(!StringUtils.isEmpty(campIds)){
			String[] ids = campIds.split(",");
			String inClause = this.getInClause(ids);
			bullMonitorDao.batchUpdatePauseComment(inClause,pauseComment);
		}
			
		
	}
	private String getInClause(String[] campIds){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<campIds.length;i++){
			
			sb.append("'").append(campIds[i]).append("'");
			if(i!=campIds.length-1){
				sb.append(",");
			}
			
		}
		return sb.toString();
	}
}
