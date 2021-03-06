package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.asiainfo.biapp.mcd.common.plan.dao.IMtlStcPlanDao;
import com.asiainfo.biapp.mcd.common.plan.vo.PlanBean;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.service.IMtlStcPlanManagementService;
import com.asiainfo.biapp.mcd.tactics.vo.McdPlanChannelList;
import org.apache.commons.lang3.StringUtils;
@Service("mtlStcPlanManagementService")
public class MtlStcPlanManagementServiceImpl implements IMtlStcPlanManagementService  {
	@Resource(name="mtlStcPlanDao")
	private IMtlStcPlanDao mtlStcPlanDao;
	public IMtlStcPlanDao getMtlStcPlanDao() {
		return mtlStcPlanDao;
	}
	public void setMtlStcPlanDao(IMtlStcPlanDao mtlStcPlanDao) {
		this.mtlStcPlanDao = mtlStcPlanDao;
	}
	@Override
	public int searchPlanCount(String keyWords, String typeId,String cityId) {
		return mtlStcPlanDao.searchPlanCount(keyWords, typeId,cityId);
	}
	@Override
	public List<PlanBean> searchPlan(String keyWords,String typeId,String cityId,Pager pager) {
		List<PlanBean> resultList = mtlStcPlanDao.searchPlan(keyWords,typeId,cityId,pager);
		String planIds = "";
		for(int i = 0;i<resultList.size();i++){
			planIds += "'"+ resultList.get(i).getPlanId()+"',";
		}
		//		关联channel信息
		if(CollectionUtils.isNotEmpty(resultList)){
			List<McdPlanChannelList> mtlStcPlanChannels = mtlStcPlanDao.getChannelIds(planIds.substring(0, planIds.length()-1));
			
			for(int i = 0;i<resultList.size();i++){
				PlanBean bean = resultList.get(i);
				String planId = bean.getPlanId();
				String channelIds = "";
				String planChannelId = "";
				for(int j = 0;j<mtlStcPlanChannels.size();j++){
					McdPlanChannelList mtlStcPlanChannel = mtlStcPlanChannels.get(j);
					if(planId.equals(mtlStcPlanChannel.getPlanId())){
						planChannelId = mtlStcPlanChannel.getId();
						channelIds += mtlStcPlanChannel.getChannelId() + ",";
						bean.setChannelId(channelIds.substring(0, channelIds.length()-1));
						bean.setPlanChannelId(planChannelId);
						bean.setChannelTypeId(channelIds.substring(0, channelIds.length()-1));
						bean.setAdivId(mtlStcPlanChannel.getAdivId());
						bean.setAdivResourceId(mtlStcPlanChannel.getAdivResourceId());
						bean.setAdivResourceName(mtlStcPlanChannel.getAdivResourceName());
						bean.setAdivResourceDesc(mtlStcPlanChannel.getAdivResourceDesc());
						bean.setAdivContentURL(mtlStcPlanChannel.getAdivContentURL());
						bean.setAdivContentToRUL(mtlStcPlanChannel.getAdivContentToRUL());
						bean.setEditURL(mtlStcPlanChannel.getEditURL());
						bean.setHandleURL(mtlStcPlanChannel.getHandleURL());
					}
				}
			}
		}
		
		return resultList;
	}
	@Override
	public int getMtlStcPlanByCondationCount(String keyWords, String typeId,String channelTypeId,String planTypeId,String cityId,String isDoubleSelect) {
		return mtlStcPlanDao.getMtlStcPlanByCondationCount(keyWords, typeId, channelTypeId,planTypeId,cityId,isDoubleSelect);
	}

	
	@Override
	public List<PlanBean> getMtlStcPlanByCondation(String keyWords,String typeId, String channelTypeId,String planTypeId,String cityId,String isDoubleSelect,Pager pager) {
		List<PlanBean> resultList = mtlStcPlanDao.getMtlStcPlanByCondation(keyWords, typeId, channelTypeId,planTypeId,cityId,isDoubleSelect,pager);
		String planIds = "";
		for(int i = 0;i<resultList.size();i++){
			planIds += "'"+ resultList.get(i).getPlanId()+"',";
		}
		//		关联channel信息
		if(StringUtils.isNotEmpty(planIds)){
			List<McdPlanChannelList> mtlStcPlanChannels = mtlStcPlanDao.getChannelIds(planIds.substring(0, planIds.length()-1));
			for(int i = 0;i<resultList.size();i++){
				PlanBean bean = resultList.get(i);
				String planId = bean.getPlanId();
				String channelIds = "";
				String planChannelId = "";
				for(int j = 0;j<mtlStcPlanChannels.size();j++){
					McdPlanChannelList mtlStcPlanChannel = mtlStcPlanChannels.get(j);
					if(planId.equals(mtlStcPlanChannel.getPlanId())){
						planChannelId = mtlStcPlanChannel.getId();
						if(channelIds.indexOf(mtlStcPlanChannel.getChannelId())<0){
							if(cityId.equals("577")){ // 当不是温州的时候，不显示微信温州渠道
								channelIds += mtlStcPlanChannel.getChannelId() + ",";
							}else{
								if(!"912".equals(mtlStcPlanChannel.getChannelId())){
									channelIds += mtlStcPlanChannel.getChannelId() + ",";
								}
							}
							if(StringUtils.isNotEmpty(channelIds)){
								bean.setChannelId(channelIds.substring(0, channelIds.length()-1));
								bean.setPlanChannelId(planChannelId);
								bean.setChannelTypeId(channelIds.substring(0, channelIds.length()-1));
								bean.setAdivId(mtlStcPlanChannel.getAdivId());
								bean.setAdivResourceId(mtlStcPlanChannel.getAdivResourceId());
								bean.setAdivResourceName(mtlStcPlanChannel.getAdivResourceName());
								bean.setAdivResourceDesc(mtlStcPlanChannel.getAdivResourceDesc());
								bean.setAdivContentURL(mtlStcPlanChannel.getAdivContentURL());
								bean.setAdivContentToRUL(mtlStcPlanChannel.getAdivContentToRUL());
								if(StringUtils.isNotEmpty(mtlStcPlanChannel.getEditURL())){
									bean.setEditURL(mtlStcPlanChannel.getEditURL());
								}
								if(StringUtils.isNotEmpty(mtlStcPlanChannel.getHandleURL())){
									bean.setHandleURL(mtlStcPlanChannel.getHandleURL());
								}
								if(StringUtils.isNotEmpty(mtlStcPlanChannel.getSmsContent())){
									bean.setSmsContent(mtlStcPlanChannel.getSmsContent());
								}
								if(StringUtils.isNotEmpty(mtlStcPlanChannel.getAwardMount())&&!mtlStcPlanChannel.getAwardMount().equals("null")){
									bean.setAwardMount(mtlStcPlanChannel.getAwardMount());
								}
								if(StringUtils.isNotEmpty(mtlStcPlanChannel.getExecContent())){
									bean.setExecContent(mtlStcPlanChannel.getExecContent());
								}
							}
						}
					}
				}
			}
		}
		//匹配是否已经使用
		if(StringUtils.isNotEmpty(planIds)){
			List<Map<String,Object>> isUseredList = mtlStcPlanDao.checkIsUserd(planIds.substring(0, planIds.length()-1), cityId);
			if(CollectionUtils.isEmpty(isUseredList)){
				for(int i = 0;i<resultList.size();i++){
					PlanBean bean = resultList.get(i);
					bean.setIsUsed("0");
				}
			}else{
				for(int i = 0;i<resultList.size();i++){
					PlanBean bean = resultList.get(i);
					for(int k = 0;k<isUseredList.size();k++){
						String planId = bean.getPlanId();
						String tempPlanId = String.valueOf(isUseredList.get(k).get("PLAN_ID"));
						if(planId.equals(tempPlanId)){
							bean.setIsUsed("1");
						}else{
							bean.setIsUsed("0");
						}
					}
				}
			}
				
		}
		
		return resultList;
	}

	
}
