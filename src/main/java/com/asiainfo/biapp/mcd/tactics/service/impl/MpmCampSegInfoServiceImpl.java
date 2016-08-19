package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.util.MpmLocaleUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCampSeginfoPlanOrderService;
import com.asiainfo.biapp.mcd.tactics.vo.LkgStaff;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampsegPolicyRelation;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfoPlanOrder;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampsegCiCustgroup;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDef;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefCall;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefCallId;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefId;
import com.asiainfo.biframe.utils.config.Configure;
import com.asiainfo.biframe.utils.spring.SystemServiceLocator;
import com.asiainfo.biframe.utils.string.StringUtil;

/**
 *
 * Created on 2007-11-9 下午02:00:59
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author
 * @version 1.0
 */
@Repository("mpmCampSegInfoService")
public class MpmCampSegInfoServiceImpl implements IMpmCampSegInfoService {
    private static Logger log = LogManager.getLogger();
    @Resource(name="mpmCampSegInfoDao")
    private IMpmCampSegInfoDao campSegInfoDao;
    
	@Override
	public List searchIMcdCampsegInfo(MtlCampSeginfo segInfo, Pager pager) {
		List list = campSegInfoDao.searchIMcdCampsegInfo(segInfo,pager);
		return list;
	}

    public IMpmCampSegInfoDao getCampSegInfoDao() {
        return campSegInfoDao;
    }

    public void setCampSegInfoDao(IMpmCampSegInfoDao campSegInfoDao) {
        this.campSegInfoDao = campSegInfoDao;
    }

	
	@Override
	public String saveCampSegWaveInfoZJ(List<MtlCampSeginfo> seginfoList)throws MpmException {
		String approveFlag = "0";  //不走审批
		try {
			String campsegId = null;
			String campsegPid = null;
			String province = Configure.getInstance().getProperty("PROVINCE");
			String isApprove = "false";
			for(int j = 0;j<seginfoList.size();j++){
				MtlCampSeginfo segInfo = seginfoList.get(j);
				isApprove = segInfo.getIsApprove();
				boolean isFatherNode = segInfo.isFatherNode();
				campsegId = segInfo.getCampsegId();
				Integer contactType = segInfo.getWaveContactType();//关联表
				Integer contactCount = segInfo.getWaveContactCount();//关联表
				Integer campClass = segInfo.getCampClass();
				String custgroupId = segInfo.getCustgroupId();//关联表
				Integer isRelativeExecTime = segInfo.getIsRelativeExecTime();
				String absoluteDates = segInfo.getAbsoluteDates(); //关联表
				String channelId = segInfo.getChannelId(); //渠道类型ID&渠道ID(关联表)
				String siteCategoryIdClassId = segInfo.getSiteCategoryIdClassId(); //(云南的)活动规则的渠道执行为综合网关时的内容站点、网站分类ID的关联
				String channelCampContent = segInfo.getChannelCampContent(); //(关联表)
				String attachements = segInfo.getFilePath();
				String custGroupAttrId = segInfo.getCustGroupAttrId();
				List<McdCampsegPolicyRelation> policyList = segInfo.getPolicyList();
				int splitCampSegInfo = segInfo.getSplitCampSegInfo();
				String[] campsegIds = null;
				String[] channelIds = null;
				String[] channeltypeIds = null;
				
				List<MtlChannelDef> mtlChannelDefList = segInfo.getMtlChannelDefList();   //渠道执行信息
				int updateCycle = 0;
				if(StringUtil.isNotEmpty(segInfo.getUpdatecycle())){
					updateCycle = Integer.parseInt(segInfo.getUpdatecycle());
				}

					
				//1、保存活动信息(波次条件、父活动编号、活动实际含义、推荐业务、营销时机)
				IMpmUserPrivilegeService userService = (IMpmUserPrivilegeService) SystemServiceLocator.getInstance().getService("mcdZjPrivilegeService");
				LkgStaff user = (LkgStaff)userService.getUser(segInfo.getCreateUserid());
				if (user != null) {
					segInfo.setCityId(user.getCityid());
					String deptMsg = user.getDepId();
					segInfo.setDeptId(Integer.parseInt(deptMsg.split("&&")[0]));
				}
				segInfo.setCreateTime(new Date()); // 活动创建时间
				segInfo.setCampsegStatId(Short.valueOf(MpmCONST.MPM_CAMPSEG_STAT_CHZT)); // 活动初始状态
				segInfo.setApproveFlowid(null);
				segInfo.setSelectTempletId("XXXXXX");
				if(isFatherNode){
					campsegId = (String) campSegInfoDao.saveCampSegInfo(segInfo);
					campsegPid = campsegId;
				}else{
					segInfo.setCampsegPid(campsegPid);
					campsegId = (String) campSegInfoDao.saveCampSegInfo(segInfo);
					log.debug("****************新生成的波次活动ID:{}", campsegId);
					//2、保存客户群与策略的关系/保存时机与策略关系  
					String basicEventTemplateId = segInfo.getBasicEventTemplateId();
					String bussinessLableTemplateId = segInfo.getBussinessLableTemplateId();
					saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"0");//基础客户群必须保存
					if(StringUtil.isNotEmpty(basicEventTemplateId)){  //选择时机
						saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"1");//保存基础标签  ARPU
					}
					if(StringUtil.isNotEmpty(bussinessLableTemplateId)){
						saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"2");//保存业务标签 
					}
					
					//3、保存渠道     ------------------无需判断是否已经存在
					if (CollectionUtils.isNotEmpty(mtlChannelDefList) && !isFatherNode) {
						for(int i = 0;i<mtlChannelDefList.size();i++){
							MtlChannelDef mtlChannelDef = mtlChannelDefList.get(i);
							MtlChannelDefId mtlChannelDefId = new MtlChannelDefId();
							mtlChannelDefId.setCampsegId(campsegId);
							mtlChannelDefId.setChannelNo(i);
							mtlChannelDefId.setUsersegId((short) 0);
							mtlChannelDef.setId(mtlChannelDefId);
							mtlChannelDef.setChanneltypeId(Integer.parseInt(mtlChannelDef.getChannelId()));							
							mtlChannelDefDao.saveMtlChannelDef(mtlChannelDef);
						}
					}
					
					if(segInfo.getMtlChannelDefCall() != null){
						
						MtlChannelDefCall mtlChannelDefCall = segInfo.getMtlChannelDefCall();
						MtlChannelDefCallId mtlChannelDefCallId = new MtlChannelDefCallId();
						mtlChannelDefCallId.setCampsegId(campsegId);
						mtlChannelDefCallId.setChannelId(channelId);
						mtlChannelDefCall.setId(mtlChannelDefCallId);
						mtlChannelDefDao.saveMtlChannelDefCall(mtlChannelDefCall);
					}
	
					
					IMpmInteractiveRuleService mpmInteractiveRuleService = (IMpmInteractiveRuleService) SystemServiceLocator.getInstance().getService("mpmInteractiveRuleService");
					//先删除该活动原来的事件规则，再新增
					mpmInteractiveRuleService.deleteEventRuleByActivityCode(campsegId, "2");
					if (StringUtil.isNotEmpty(segInfo.getEventActiveTempletId())) {
						MtlCampSeginfo baseCampsegInfo = getBaseCampsegInfo(campsegId);
						segInfo.setStartDate(baseCampsegInfo.getStartDate());
						segInfo.setEndDate(baseCampsegInfo.getEndDate());
						McdRuleTempletAction mrt = new McdRuleTempletAction();
						mrt.setUserId(segInfo.getCreateUserid());
						this.saveInteractiveRule(segInfo, mrt.getEventRule(segInfo.getEventActiveTempletId()));
					}
					
					//在保存和其他表的对应关系
					IMpmCampSegInfoService service = (IMpmCampSegInfoService) SystemServiceLocator.getInstance().getService(MpmCONST.CAMPAIGN_SEG_INFO_SERVICE);
					//保存产品订购或者剔除关系
					IMtlCampSeginfoPlanOrderService planOrderService = (IMtlCampSeginfoPlanOrderService) SystemServiceLocator.getInstance().getService(MpmCONST.MTL_CAMPSEG_INFO_PLAN_ORDER_SERVICE);
					MtlCampSeginfoPlanOrder campSeginfoPlanOrder = new MtlCampSeginfoPlanOrder();
					campSeginfoPlanOrder.setCampsegId(campsegId);
					campSeginfoPlanOrder.setOrderPlanIds(segInfo.getOrderPlanIds());
					campSeginfoPlanOrder.setExcludePlanIds(segInfo.getExcludePlanIds());
					planOrderService.save(campSeginfoPlanOrder);
					
					
				}
			}
			
			if("true".equals(isApprove)){
					IMpmCampSegInfoService approveService = (IMpmCampSegInfoService) SystemServiceLocator.getInstance().getService(MpmCONST.CAMPAIGN_SEG_INFO_SERVICE);
					String approveStr = approveService.submitApprovalXml(campsegPid);
					if("提交审批成功".equals(approveStr)){
						approveFlag = "1"; //走审批，审批成功
					}else{
						approveFlag = "2"; //走审批，审批失败
					}
			}
		} catch (Exception e) {
			log.error("", e);
			throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.bchdxxsb"));
		}
		return approveFlag;
	}
	@Override
	public void saveCampsegCustGroupZJ(String campsegId, String custGroupIdStr, String userId,MtlCampSeginfo segInfo,String flag) throws MpmException {
		if(flag.equals("0")){	//基础客户群
			MtlCampsegCiCustgroup mtlCampsegCustGroup = new MtlCampsegCiCustgroup();
			mtlCampsegCustGroup.setCustgroupId(segInfo.getCustgroupId());
			mtlCampsegCustGroup.setCampsegId(campsegId);
			mtlCampsegCustGroup.setCustgroupName("test");
			mtlCampsegCustGroup.setCustgroupType("CG"); //客户群类型
			mtlCampsegCustGroup.setCustgroupNumber(segInfo.getCustgroupNumber());
			mtlCampsegCustGroup.setCustBaseDay(segInfo.getCustBaseDay());
			if (StringUtils.isEmpty(segInfo.getCustBaseDay())) {
				mtlCampsegCustGroup.setCustBaseMonth(segInfo.getCustBaseMonth());
			}
			mtlCampsegCiCustDao.save(mtlCampsegCustGroup);
		}else{
			//时机  或者基础标签
			MtlCampsegCiCustgroup mtlCampsegCustGroup1 = new MtlCampsegCiCustgroup();
			mtlCampsegCustGroup1.setCampsegId(campsegId);
			mtlCampsegCustGroup1.setCustgroupName("test");
			mtlCampsegCustGroup1.setCustgroupType("CGT"); //客户群类型
			mtlCampsegCustGroup1.setCustgroupNumber(segInfo.getCustgroupNumber());
			mtlCampsegCustGroup1.setCustBaseDay(segInfo.getCustBaseDay());
			if (StringUtils.isEmpty(segInfo.getCustBaseDay())) {
				mtlCampsegCustGroup1.setCustBaseMonth(segInfo.getCustBaseMonth());
			}
			if(flag.equalsIgnoreCase("1")){  //保存基础标签
				mtlCampsegCustGroup1.setAttrClassId("888");
				mtlCampsegCustGroup1.setCustgroupId(segInfo.getBasicEventTemplateId());
				mtlCampsegCiCustDao.save(mtlCampsegCustGroup1);
			}else if(flag.equalsIgnoreCase("2")){
				mtlCampsegCustGroup1.setAttrClassId("999");
				mtlCampsegCustGroup1.setCustgroupId(segInfo.getBussinessLableTemplateId());
				mtlCampsegCiCustDao.save(mtlCampsegCustGroup1);
			}
		}
	}
	
}
