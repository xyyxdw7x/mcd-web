package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlStcPlanDao;
import com.asiainfo.biapp.mcd.tactics.service.IMtlStcPlanManagementService;
import com.asiainfo.biapp.mcd.tactics.vo.MtlStcPlanBean;
import com.asiainfo.biapp.mcd.tactics.vo.MtlStcPlanChannel;
import com.asiainfo.biframe.utils.string.StringUtil;
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
	public List<MtlStcPlanBean> searchPlan(String keyWords,String typeId,String cityId,Pager pager) {
		List<MtlStcPlanBean> resultList = mtlStcPlanDao.searchPlan(keyWords,typeId,cityId,pager);
		String planIds = "";
		for(int i = 0;i<resultList.size();i++){
			planIds += "'"+ resultList.get(i).getPlanId()+"',";
		}
		//		关联channel信息
		if(CollectionUtils.isNotEmpty(resultList)){
			List<MtlStcPlanChannel> mtlStcPlanChannels = mtlStcPlanDao.getChannelIds(planIds.substring(0, planIds.length()-1));
			
			for(int i = 0;i<resultList.size();i++){
				MtlStcPlanBean bean = resultList.get(i);
				String planId = bean.getPlanId();
				String channelIds = "";
				String planChannelId = "";
				for(int j = 0;j<mtlStcPlanChannels.size();j++){
					MtlStcPlanChannel mtlStcPlanChannel = mtlStcPlanChannels.get(j);
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
	public List<MtlStcPlanBean> getMtlStcPlanByCondation(String keyWords,String typeId, String channelTypeId,String planTypeId,String cityId,String isDoubleSelect,Pager pager) {
		List<MtlStcPlanBean> resultList = mtlStcPlanDao.getMtlStcPlanByCondation(keyWords, typeId, channelTypeId,planTypeId,cityId,isDoubleSelect,pager);
		String planIds = "";
		for(int i = 0;i<resultList.size();i++){
			planIds += "'"+ resultList.get(i).getPlanId()+"',";
		}
		//		关联channel信息
		if(StringUtil.isNotEmpty(planIds)){
			List<MtlStcPlanChannel> mtlStcPlanChannels = mtlStcPlanDao.getChannelIds(planIds.substring(0, planIds.length()-1));
			for(int i = 0;i<resultList.size();i++){
				MtlStcPlanBean bean = resultList.get(i);
				String planId = bean.getPlanId();
				String channelIds = "";
				String planChannelId = "";
				for(int j = 0;j<mtlStcPlanChannels.size();j++){
					MtlStcPlanChannel mtlStcPlanChannel = mtlStcPlanChannels.get(j);
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
							if(StringUtil.isNotEmpty(channelIds)){
								bean.setChannelId(channelIds.substring(0, channelIds.length()-1));
								bean.setPlanChannelId(planChannelId);
								bean.setChannelTypeId(channelIds.substring(0, channelIds.length()-1));
								bean.setAdivId(mtlStcPlanChannel.getAdivId());
								bean.setAdivResourceId(mtlStcPlanChannel.getAdivResourceId());
								bean.setAdivResourceName(mtlStcPlanChannel.getAdivResourceName());
								bean.setAdivResourceDesc(mtlStcPlanChannel.getAdivResourceDesc());
								bean.setAdivContentURL(mtlStcPlanChannel.getAdivContentURL());
								bean.setAdivContentToRUL(mtlStcPlanChannel.getAdivContentToRUL());
								if(StringUtil.isNotEmpty(mtlStcPlanChannel.getEditURL())){
									bean.setEditURL(mtlStcPlanChannel.getEditURL());
								}
								if(StringUtil.isNotEmpty(mtlStcPlanChannel.getHandleURL())){
									bean.setHandleURL(mtlStcPlanChannel.getHandleURL());
								}
								if(StringUtil.isNotEmpty(mtlStcPlanChannel.getSmsContent())){
									bean.setSmsContent(mtlStcPlanChannel.getSmsContent());
								}
								if(StringUtil.isNotEmpty(mtlStcPlanChannel.getAwardMount())&&!mtlStcPlanChannel.getAwardMount().equals("null")){
									bean.setAwardMount(mtlStcPlanChannel.getAwardMount());
								}
								if(StringUtil.isNotEmpty(mtlStcPlanChannel.getExecContent())){
									bean.setExecContent(mtlStcPlanChannel.getExecContent());
								}
							}
						}
					}
				}
			}
		}
		//匹配是否已经使用
		if(StringUtil.isNotEmpty(planIds)){
			List<Map> isUseredList = mtlStcPlanDao.checkIsUserd(planIds.substring(0, planIds.length()-1), cityId);
			if(CollectionUtils.isEmpty(isUseredList)){
				for(int i = 0;i<resultList.size();i++){
					MtlStcPlanBean bean = resultList.get(i);
					bean.setIsUserd("0");
				}
			}else{
				for(int i = 0;i<resultList.size();i++){
					MtlStcPlanBean bean = resultList.get(i);
					for(int k = 0;k<isUseredList.size();k++){
						String planId = bean.getPlanId();
						String tempPlanId = String.valueOf(isUseredList.get(k).get("PLAN_ID"));
						if(planId.equals(tempPlanId)){
							bean.setIsUserd("1");
						}else{
							bean.setIsUserd("0");
						}
					}
				}
			}
				
		}
		
		return resultList;
	}

	
}
