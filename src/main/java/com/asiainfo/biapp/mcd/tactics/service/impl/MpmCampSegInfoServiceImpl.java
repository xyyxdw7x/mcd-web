package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.amqp.CepUtil;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.dao.custgroup.IMcdMtlGroupInfoDao;
import com.asiainfo.biapp.mcd.common.dao.plan.MtlStcPlanDao;
import com.asiainfo.biapp.mcd.common.service.custgroup.CustGroupInfoService;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.DateTool;
import com.asiainfo.biapp.mcd.common.util.MpmConfigure;
import com.asiainfo.biapp.mcd.common.util.MpmLocaleUtil;
import com.asiainfo.biapp.mcd.common.util.MpmUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.custgroup.MtlGroupInfo;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlan;
import com.asiainfo.biapp.mcd.custgroup.dao.CreateCustGroupTabDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMcdCampsegTaskDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlChannelDefDao;
import com.asiainfo.biapp.mcd.tactics.dao.MtlCampsegCustgroupDao;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.service.IMcdCampsegTaskService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampsegStat;
import com.asiainfo.biapp.mcd.tactics.vo.LkgStaff;
import com.asiainfo.biapp.mcd.tactics.vo.McdApproveLog;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampsegTask;
import com.asiainfo.biapp.mcd.tactics.vo.McdTempletForm;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCallwsUrl;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampsegCustgroup;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDef;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefCall;
import com.asiainfo.biapp.mcd.tactics.vo.MtlStcPlanChannel;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.utils.config.Configure;
import com.asiainfo.biframe.utils.date.DateUtil;
import com.asiainfo.biframe.utils.spring.SystemServiceLocator;
import com.asiainfo.biframe.utils.string.DES;
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
@Service("mpmCampSegInfoService")
public class MpmCampSegInfoServiceImpl implements IMpmCampSegInfoService {
    private static Logger log = LogManager.getLogger();
    @Resource(name="mpmCampSegInfoDao")
    private IMpmCampSegInfoDao campSegInfoDao;
    @Resource(name="mtlChannelDefDao")
  	private IMtlChannelDefDao mtlChannelDefDao;//活动渠道Dao
    @Resource(name="mtlStcPlanDao")
    private MtlStcPlanDao stcPlanDao;
    @Resource(name="mtlCampsegCustgroupDao")
    private MtlCampsegCustgroupDao mtlCampsegCustgroupDao; 
    @Resource(name="mpmUserPrivilegeService")
    IMpmUserPrivilegeService mpmUserPrivilegeService;
	@Resource(name = "createCustGroupTab")
	private CreateCustGroupTabDao createCustGroupTab;
	@Resource(name = "mtlCallWsUrlService")
	private IMtlCallWsUrlService callwsUrlService;
	@Resource(name = "mcdCampsegTaskDao")
	private IMcdCampsegTaskDao mcdCampsegTaskDao;
	@Resource(name = "mcdCampsegTaskService")
	private IMcdCampsegTaskService mcdCampsegTaskService;
	@Resource(name = "custGroupInfoService")
	private CustGroupInfoService custGroupInfoService;
	@Resource(name = "mcdMtlGroupInfoDao")
    private IMcdMtlGroupInfoDao mcdMtlGroupInfoDao;
	
    public IMtlChannelDefDao getMtlChannelDefDao() {
		return mtlChannelDefDao;
	}
	public void setMtlChannelDefDao(IMtlChannelDefDao mtlChannelDefDao) {
		this.mtlChannelDefDao = mtlChannelDefDao;
	}

	public IMpmUserPrivilegeService getMcdZjPrivilegeService() {
		return mpmUserPrivilegeService;
	}
	public void setMcdZjPrivilegeService(IMpmUserPrivilegeService mcdZjPrivilegeService) {
		this.mpmUserPrivilegeService = mcdZjPrivilegeService;
	}

	private Map<String,String> oaMap; //经分与OA帐号对应关系
    
   	public Map<String, String> getOaMap() {
		return oaMap;
	}
	public void setOaMap(Map<String, String> oaMap) {
		this.oaMap = oaMap;
	}
	
    public IMpmCampSegInfoDao getCampSegInfoDao() {
        return campSegInfoDao;
    }
    public void setCampSegInfoDao(IMpmCampSegInfoDao campSegInfoDao) {
        this.campSegInfoDao = campSegInfoDao;
    }
    /**
     * gaowj3
     * JDBC查询策略管理
     * @param campsegId
     * @return
     */
    @Override
    public List searchIMcdCampsegInfo(MtlCampSeginfo segInfo, Pager pager) {
        List list = campSegInfoDao.searchIMcdCampsegInfo(segInfo,pager);
        return list;
    }
    
    @Override
	public String updateCampSegWaveInfoZJ(List<MtlCampSeginfo> seginfoList)throws MpmException {
		String approveFlag = "0";  //不走审批
		try {
			String campsegId = null;
			String campsegPid = null;
			String province = Configure.getInstance().getProperty("PROVINCE");
			String isApprove = "false";
			//在保存和其他表的对应关系
			for(int j = 0;j<seginfoList.size();j++){
				MtlCampSeginfo segInfo = seginfoList.get(j);
				isApprove = segInfo.getIsApprove();
				boolean isFatherNode = segInfo.getIsFatherNode();
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
//				List<McdCampsegPolicyRelation> policyList = segInfo.getPolicyList();
				int splitCampSegInfo = segInfo.getSplitCampSegInfo();
				String[] campsegIds = null;
				String[] channelIds = null;
				String[] channeltypeIds = null;
				
				List<MtlChannelDef> mtlChannelDefList = segInfo.getMtlChannelDefList();   //渠道执行信息
				int updateCycle = 0;
				if(StringUtil.isNotEmpty(segInfo.getUpdatecycle())){
					updateCycle = Integer.parseInt(segInfo.getUpdatecycle());
				}
			
				LkgStaff user = (LkgStaff)mpmUserPrivilegeService.getUser(segInfo.getCreateUserid());
				if (user != null) {
					segInfo.setCityId(user.getCityid());
					String deptMsg = user.getDepId();
					segInfo.setDeptId(Integer.parseInt(deptMsg.split("&&")[0]));
				}
				
				segInfo.setCreateTime(new Date()); // 活动创建时间
				segInfo.setCampsegStatId(Short.valueOf(MpmCONST.MPM_CAMPSEG_STAT_CHZT)); // 活动初始状态
				segInfo.setApproveFlowid(null);
				segInfo.setApproveResultDesc("测试");
				segInfo.setSelectTempletId("XXXXXX");
				if(isFatherNode){
					campSegInfoDao.saveCampSegInfo(segInfo);   //不用删除旧策略  后台调用savaOrUpdate
					campsegPid = campsegId;
				}else{
					segInfo.setCampsegPid(campsegPid);
					campSegInfoDao.saveCampSegInfo(segInfo);
					log.debug("****************新生成的波次活动ID:{}", campsegId);
					
					//2、保存客户群与策略的关系/保存时机与策略关系
					/*String basicEventTemplateId = segInfo.getBasicEventTemplateId();
					String bussinessLableTemplateId = segInfo.getBussinessLableTemplateId();*/
					//修改时,先删除客户群与策略的关系/保存时机与策略关系
					mtlCampsegCustgroupDao.deleteByCampsegId(campsegId);
					saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"0");//基础客户群必须保存
					/*if(StringUtil.isNotEmpty(basicEventTemplateId)){  //选择时机
						saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"1");//保存基础标签  ARPU
					}
					if(StringUtil.isNotEmpty(bussinessLableTemplateId)){
						saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"2");//保存业务标签 
					}*/
					
					//先删除渠道信息表
					mtlChannelDefDao.deleteMtlChannelDef(campsegId);
					//3、保存渠道
					if (CollectionUtils.isNotEmpty(mtlChannelDefList) && !isFatherNode) {
						for(int i = 0;i<mtlChannelDefList.size();i++){
							MtlChannelDef mtlChannelDef = mtlChannelDefList.get(i);
							mtlChannelDef.setCampsegId(campsegId);
							mtlChannelDef.setChannelNo(i);
							mtlChannelDef.setUsersegId((short) 0);
							mtlChannelDef.setChanneltypeId(Integer.parseInt(mtlChannelDef.getChannelId()));
							mtlChannelDefDao.save(mtlChannelDef);
						}
					}
					mtlChannelDefDao.deleteMtlChannelDefCall(campsegId,channelId);
					if(segInfo.getMtlChannelDefCall() != null){
						MtlChannelDefCall mtlChannelDefCall = segInfo.getMtlChannelDefCall();
						/*MtlChannelDefCallId mtlChannelDefCallId = new MtlChannelDefCallId();
						mtlChannelDefCallId.setCampsegId(campsegId);
						mtlChannelDefCallId.setChannelId(channelId);
						mtlChannelDefCall.setId(mtlChannelDefCallId);*/
						mtlChannelDefCall.setCampsegId(campsegId);
						mtlChannelDefCall.setChannelId(channelId);
						mtlChannelDefDao.save(mtlChannelDefCall);
					}
					
					/*//保存产品订购或者剔除关系     
					IMtlCampSeginfoPlanOrderService planOrderService = (IMtlCampSeginfoPlanOrderService) SystemServiceLocator.getInstance().getService(
							MpmCONST.MTL_CAMPSEG_INFO_PLAN_ORDER_SERVICE);
					//修改时删除产品对应关系数据
					planOrderService.updatePlanOrderByCampsegId(campsegId,segInfo.getOrderPlanIds(),segInfo.getExcludePlanIds());*/			
					this.updateCampsegInfo(segInfo);
				}
			}
			if("true".equals(isApprove)){
					String approveStr = this.submitApprovalXml(campsegPid);
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
	public void updateCampsegInfo(MtlCampSeginfo segInfo){
		try {
			campSegInfoDao.updateCampsegInfo(segInfo);
		} catch (Exception e) {
			log.error("",e);
		}
	}
    /**
     * gaowj3
     * JDBC查询业务状态
     * @return
     */
    @Override
    public List<DimCampDrvType> getDimCampSceneList() {
        List<DimCampDrvType> list = campSegInfoDao.getDimCampSceneList();
        return list;
    }
    /**
     * gaowj3
     * JDBC查询策略状态
     * @return
     */
    @Override
    public List getDimCampsegStatList() {
        List list = campSegInfoDao.getDimCampsegStatList();
        return list;
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
				boolean isFatherNode = segInfo.getIsFatherNode();
				campsegId = segInfo.getCampsegId();
//				Integer contactType = segInfo.getWaveContactType();//关联表
//				Integer contactCount = segInfo.getWaveContactCount();//关联表
//				Integer campClass = segInfo.getCampClass();
				String custgroupId = segInfo.getCustgroupId();//关联表
//				Integer isRelativeExecTime = segInfo.getIsRelativeExecTime();
//				String absoluteDates = segInfo.getAbsoluteDates(); //关联表
				String channelId = segInfo.getChannelId(); //渠道类型ID&渠道ID(关联表)
//				String siteCategoryIdClassId = segInfo.getSiteCategoryIdClassId(); //(云南的)活动规则的渠道执行为综合网关时的内容站点、网站分类ID的关联
//				String channelCampContent = segInfo.getChannelCampContent(); //(关联表)
//				String attachements = segInfo.getFilePath();
//				String custGroupAttrId = segInfo.getCustGroupAttrId();
//				List<McdCampsegPolicyRelation> policyList = segInfo.getPolicyList();
//				int splitCampSegInfo = segInfo.getSplitCampSegInfo();
//				String[] campsegIds = null;
//				String[] channelIds = null;
//				String[] channeltypeIds = null;
				
				List<MtlChannelDef> mtlChannelDefList = segInfo.getMtlChannelDefList();   //渠道执行信息
/*				int updateCycle = 0;
				if(StringUtil.isNotEmpty(segInfo.getUpdatecycle())){
					updateCycle = Integer.parseInt(segInfo.getUpdatecycle());
				}*/

					
				//1、保存活动信息(波次条件、父活动编号、活动实际含义、推荐业务、营销时机)
//				TODO LkgStaff user = (LkgStaff)mpmUserPrivilegeService.getUser(segInfo.getCreateUserid());
				/*if (user != null) {
					segInfo.setCityId(user.getCityid());
					String deptMsg = user.getDepId();
					segInfo.setDeptId(Integer.parseInt(deptMsg.split("&&")[0]));
				}*/
				segInfo.setCreateTime(new Date()); // 活动创建时间
				segInfo.setCampsegStatId(Short.valueOf(MpmCONST.MPM_CAMPSEG_STAT_CHZT)); // 活动初始状态
				segInfo.setApproveFlowid(null);
				segInfo.setSelectTempletId("XXXXXX");
				if(isFatherNode){
					campsegId = (String) campSegInfoDao.saveCampSegInfo(segInfo);//Duang Duang Duang 保存策略！！！
					campsegPid = campsegId;
				}else{
					segInfo.setCampsegPid(campsegPid);
					segInfo.setCampsegId(MpmUtil.generateCampsegAndTaskNo());
					campsegId = (String) campSegInfoDao.saveCampSegInfo(segInfo);//Duang Duang Duang 保存策略！！！
					log.debug("****************新生成的波次活动ID:{}", campsegId);
					//2、保存客户群与策略的关系 
					/*String basicEventTemplateId = segInfo.getBasicEventTemplateId();
					String bussinessLableTemplateId = segInfo.getBussinessLableTemplateId();*/
//					TODO:saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"0");//基础客户群必须保存
					saveCampsegCustGroupZJ(campsegId, custgroupId, "chenyg",segInfo,"0");//基础客户群必须保存
					/*if(StringUtil.isNotEmpty(basicEventTemplateId)){  //选择时机
						saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"1");//保存基础标签  ARPU
					}
					if(StringUtil.isNotEmpty(bussinessLableTemplateId)){
						saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"2");//保存业务标签 
					}*/
					
					//3、保存渠道     ------------------无需判断是否已经存在
					if (CollectionUtils.isNotEmpty(mtlChannelDefList) && !isFatherNode) {
						for(int i = 0;i<mtlChannelDefList.size();i++){
							MtlChannelDef mtlChannelDef = mtlChannelDefList.get(i);
//							MtlChannelDefId mtlChannelDefId = new MtlChannelDefId();
//							mtlChannelDefId.setCampsegId(campsegId);
//							mtlChannelDefId.setChannelNo(i);
//							mtlChannelDefId.setUsersegId((short) 0);
//							mtlChannelDef.setId(mtlChannelDefId);
							mtlChannelDef.setCampsegId(campsegId);
							mtlChannelDef.setChannelNo(i);
							mtlChannelDef.setUsersegId((short) 0);
							mtlChannelDef.setChanneltypeId(Integer.parseInt(mtlChannelDef.getChannelId()));							
							mtlChannelDefDao.save(mtlChannelDef);
						}
					}
					
					if(segInfo.getMtlChannelDefCall() != null){
						MtlChannelDefCall mtlChannelDefCall = segInfo.getMtlChannelDefCall();
						mtlChannelDefCall.setCampsegId(campsegId);
						mtlChannelDefCall.setChannelId(channelId);
						mtlChannelDefDao.save(mtlChannelDefCall);
					}
	
					
					/*IMpmInteractiveRuleService mpmInteractiveRuleService = (IMpmInteractiveRuleService) SystemServiceLocator.getInstance().getService("mpmInteractiveRuleService");
					//先删除该活动原来的事件规则，再新增
					mpmInteractiveRuleService.deleteEventRuleByActivityCode(campsegId, "2");
					if (StringUtil.isNotEmpty(segInfo.getEventActiveTempletId())) {
						MtlCampSeginfo baseCampsegInfo = getBaseCampsegInfo(campsegId);
						segInfo.setStartDate(baseCampsegInfo.getStartDate());
						segInfo.setEndDate(baseCampsegInfo.getEndDate());
						McdRuleTempletAction mrt = new McdRuleTempletAction();
						mrt.setUserId(segInfo.getCreateUserid());
						this.saveInteractiveRule(segInfo, mrt.getEventRule(segInfo.getEventActiveTempletId()));
					}*/
					
					/*
					//在保存和其他表的对应关系
					IMpmCampSegInfoService service = (IMpmCampSegInfoService) SystemServiceLocator.getInstance().getService(MpmCONST.CAMPAIGN_SEG_INFO_SERVICE);
					//保存产品订购或者剔除关系
					IMtlCampSeginfoPlanOrderService planOrderService = (IMtlCampSeginfoPlanOrderService) SystemServiceLocator.getInstance().getService(MpmCONST.MTL_CAMPSEG_INFO_PLAN_ORDER_SERVICE);
					MtlCampSeginfoPlanOrder campSeginfoPlanOrder = new MtlCampSeginfoPlanOrder();
					campSeginfoPlanOrder.setCampsegId(campsegId);
					campSeginfoPlanOrder.setOrderPlanIds(segInfo.getOrderPlanIds());
					campSeginfoPlanOrder.setExcludePlanIds(segInfo.getExcludePlanIds());
					planOrderService.save(campSeginfoPlanOrder);*/
					
					
				}
			}
			
			if("true".equals(isApprove)){
					String approveStr = this.submitApprovalXml(campsegPid);
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
    /**
     * 根据渠道ID获取渠道
     * @param planId
     * @return
     */
    @Override
    public MtlStcPlan getMtlStcPlanByPlanId(String planId) {
        try {
            return stcPlanDao.getMtlStcPlanByPlanId(planId);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }
    /**
     * 取客户群选择（目标客户群”及“对比客户群”信息）
     * @param campsegId
     * @return
     * @throws MpmException
     */
    public List getCustGroupSelectList(String campsegId) throws MpmException {
        // TODO 自动生成方法存根
        try {
            return campSegInfoDao.getCustGroupSelectList(campsegId);
        } catch (Exception e) {
            log.error("", e);
            throw new MpmException("获取目标客户群错误");
        }
    }
    /**
     * 根绝策略ID查询渠道信息
     */
    @Override
    public List getMtlChannelDefs(String campsegId) {
        List list = mtlChannelDefDao.getMtlChannelDefs(campsegId);
        return list;
    }
    
	@Override
	public void saveCampsegCustGroupZJ(String campsegId, String custGroupIdStr, String userId,MtlCampSeginfo segInfo,String flag) throws MpmException {
//		if(flag.equals("0")){	//基础客户群
			MtlCampsegCustgroup mtlCampsegCustGroup = new MtlCampsegCustgroup();
			mtlCampsegCustGroup.setCustgroupId(segInfo.getCustgroupId());
			mtlCampsegCustGroup.setCampsegId(campsegId);
			mtlCampsegCustGroup.setCustgroupName("test");
			mtlCampsegCustGroup.setCustgroupType("CG"); //客户群类型
			mtlCampsegCustGroup.setCustgroupNumber(segInfo.getCustgroupNumber());
			mtlCampsegCustGroup.setCustBaseDay(segInfo.getCustBaseDay());
			if (StringUtils.isEmpty(segInfo.getCustBaseDay())) {
				mtlCampsegCustGroup.setCustBaseMonth(segInfo.getCustBaseMonth());
			}
			try {
				mtlCampsegCustGroup.setCampsegCustgroupId(DataBaseAdapter.generaterPrimaryKey());
			} catch (Exception e) {
				e.printStackTrace();
			}
			mtlCampsegCustgroupDao.save(mtlCampsegCustGroup);
		/*}else{
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
		}*/
	}
	public MtlCampSeginfo getBaseCampsegInfo(String campsegId) throws Exception {
		MtlCampSeginfo campsegInfo = null;
		if (StringUtil.isNotEmpty(campsegId)) {
			campsegInfo = this.getCampSegInfo(campsegId);
			if (!"0".equals(campsegInfo.getCampsegPid()) && StringUtil.isNotEmpty(campsegInfo.getCampsegPid())) {
				campsegInfo = this.getBaseCampsegInfo(campsegInfo.getCampsegPid());
			}
		}
		return campsegInfo;
	}
	public MtlCampSeginfo getCampSegInfo(String campSegId) throws MpmException {
		MtlCampSeginfo obj = null;
		try {
			obj = campSegInfoDao.getCampSegInfo(campSegId);
		} catch (Exception e) {
			log.error("", e);
			throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.qhd") + campSegId+ MpmLocaleUtil.getMessage("mcd.java.xxsb"));
		}
		return obj;
	}
	@Override
	public String submitApprovalXml(String campsegId) {
		StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlStr.append("<campsegInfo>");

        try {
        	//策略包基本信息
			MtlCampSeginfo mtlCampSeginfo = campSegInfoDao.getCampSegInfo(campsegId);
			//策略包下子策略基本信息
			List<MtlCampSeginfo> mtlCampSeginfoList = this.getChildCampSeginfo(campsegId);
			//取渠道信息
			List approveChannelIdList = mtlChannelDefDao.findChildChannelIdList(campsegId);
			Set channeIdSet = new HashSet();
			for(int i = 0;i< approveChannelIdList.size();i++){
				Map map = (Map) approveChannelIdList.get(i);
				String channelId = map.get("channel_id").toString();
				channeIdSet.add(channelId);
			}
			String channeIds = channeIdSet.toString().replaceAll(" ","");


	    	//策略基本信息
	    	xmlStr.append("<campsegId>" + mtlCampSeginfo.getCampsegId()+ "</campsegId>");//营销策略编号 
	    	xmlStr.append("<campsegName>" + mtlCampSeginfo.getCampsegName()+ "</campsegName>");//策略名称 
	    	xmlStr.append("<campDrvName>" + mtlCampSeginfo.getCampDrvName()+ "</campDrvName>");//业务类型 
	    	xmlStr.append("<campsegType>" + mtlCampSeginfo.getCampsegTypeId()+ "</campsegType>");//营销类别	
	    	xmlStr.append("<startDate>" + mtlCampSeginfo.getStartDate()+ "</startDate>");//策略开始时间
	    	xmlStr.append("<endDate>" + mtlCampSeginfo.getEndDate() + "</endDate>");//策略结束时间
	    	xmlStr.append("<createTime>" + DateUtil.date2String(mtlCampSeginfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss")  + "</createTime>");//创建时间
	    	
	    	IUser user  = mpmUserPrivilegeService.getUser(mtlCampSeginfo.getCreateUserid());
	    	String userName =  user != null ? user.getUsername() : "";
	    	xmlStr.append("<createUser>" +userName + "</createUser>");//创建人
	    	//有些帐号经分帐号和OA帐号不同 提交审批需要OA帐号
	    	String createUserId=mtlCampSeginfo.getCreateUserid();
	    	if(oaMap!=null){
	    		if(oaMap.get(createUserId)!=null){
		    		createUserId=oaMap.get(createUserId);
		    	}
	    	}
	    	xmlStr.append("<createUserId>" + createUserId + "</createUserId>");//创建人ID
	    	xmlStr.append("<cityId>" + mtlCampSeginfo.getCityId() + "</cityId>");//创建人地市id
	    	xmlStr.append("<approveChannelId>" + channeIds.substring(1,channeIds.length()-1) + "</approveChannelId>");//审批渠道类型（判断采用哪个流程）：渠道ID
	    	xmlStr.append("<approveFlowId>" + mtlCampSeginfo.getApproveFlowid() + "</approveFlowId>");//审批流程ID
	    	String campsegInfoViewUrl = MpmConfigure.getInstance().getProperty("CAMPSEGINFO_VIEWURL");
			final String webPath = "http://" + Configure.getInstance().getProperty("HOST_ADDRESS") + ":"
					+ Configure.getInstance().getProperty("HOST_PORT")
					+ Configure.getInstance().getProperty("CONTEXT_PATH");
	    	String token = DES.encrypt(mtlCampSeginfo.getCampsegId());
	    	xmlStr.append("<campsegInfoViewUrl>" + campsegInfoViewUrl+mtlCampSeginfo.getCampsegId() + "&token=" +token+ "</campsegInfoViewUrl>");////营销策略信息URL
	    	xmlStr.append("</campsegInfo>");
					
			String results = "";
			String request_id ="";
			String assign_id = "";
			String result_desc = "";
			
			log.info("开始提交审批 ！");
			MtlCallwsUrl url = callwsUrlService.getCallwsURL("APPREVEINFO_BYIDS");
		
			QName name=new QName("http://impl.biz.web.tz","commitApproveInfo");
			org.apache.axis.client.Service service = new  org.apache.axis.client.Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url.getCallwsUrl()));
			call.setOperationName(name);
			call.setTimeout(50000);//超时时间5秒
			log.info(xmlStr.toString());
			String childxml = call.invoke(new Object[] { xmlStr.toString() }).toString();

			log.info("提交审批返回responed xml " + childxml);
			 Document dom=DocumentHelper.parseText(childxml); 
	   		 Element root=dom.getRootElement();  
	   		results = root.element("result") == null ? "" : root.element("result").getText(); 
   			request_id = root.element("request_id") == null ? "" : root.element("request_id").getText(); 
   			assign_id = root.element("assign_id") == null ? "" : root.element("assign_id").getText(); 
   			result_desc = root.element("result_desc") == null ? "" : root.element("result_desc").getText(); 	
			
			log.info("提交审批后返回值 result: " + results + " , request_id: " + request_id + " , assign_id: " + assign_id + " , result_desc: " + result_desc);
			
			MtlCampSeginfo segInfo = campSegInfoDao.getCampSegInfo(campsegId);
			if("1".equals(results)){
				segInfo.setApproveFlowid(assign_id);
				segInfo.setCampsegStatId(Short.valueOf(MpmCONST.MPM_CAMPSEG_STAT_HDSP));
				for(MtlCampSeginfo childSeg : mtlCampSeginfoList){
					childSeg.setApproveFlowid(assign_id);
					childSeg.setCampsegStatId(Short.valueOf(MpmCONST.MPM_CAMPSEG_STAT_HDSP));
					campSegInfoDao.updateCampSegInfo(childSeg);
				}
			}else{
				log.error("提交审批流程失败，失败原因：", result_desc);
				String ApproveResult = "提交失败，失败原因："+result_desc;
				return ApproveResult;
			}
			campSegInfoDao.updateCampSegInfo(segInfo);
			
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "提交失败，失败原因：" + e.getMessage();
		}

        
		return "提交审批成功";
	}
	
	@Override
	public List<MtlCampSeginfo> getChildCampSeginfo(String campsegId) {
		try {
			return campSegInfoDao.getChildCampSeginfo(campsegId);
		} catch (Exception e) {
			return null;
		}
	}
	
    /**
     * 根据编号删除策略信息
     * @param campSegId
     * @throws MpmException
     */
    public void deleteCampSegInfo(String campSegId) throws MpmException {
        try {
            List<String> rList = new ArrayList<String>();
            gettListAllCampSegByParentId(campSegId, rList);
            rList.add(campSegId);
            for (String v_campSegId : rList) {
                MtlCampSeginfo segInfo = campSegInfoDao.getCampSegInfo(v_campSegId);
                //删除营销活动
                campSegInfoDao.deleteCampSegInfo(segInfo);
            }
        } catch (Exception e) {
            log.error("", e);
            throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.schd") + campSegId
                    + MpmLocaleUtil.getMessage("mcd.java.xxsb"));
        }
    }
    
    /**
     * 根据营销活动父节点查询父节点下所有节点 营销活动编码
     * 2013-6-4 16:38:32
     * @author Mazh
     * @param campSegId
     * @param rList
     */
    public void gettListAllCampSegByParentId(String pcampSegId, List<String> rList) {
        try {
            List<String> list = campSegInfoDao.gettListAllCampSegByParentId(pcampSegId, rList);

            if (list != null && list.size() != 0) {
                for (String campSegId : list) {
                    gettListAllCampSegByParentId(campSegId, rList);
                }
            }

        } catch (Exception e) {
            log.error("", e);
        }

    }
	@Override
	public String createCustGroupTabAsCustTable1(String tabPrefix,String custGroupId) {
		String tabNameModel="mtl_cuser_XXXXXXXX";
		String tabName = tabPrefix + custGroupId; //浙江Oracle sqlfire同时创建表
		createCustGroupTab.addCreateCustGroupTabInMem(MpmUtil.getSqlCreateAsTableInSqlFire(tabName, tabNameModel)); 
		return tabName;
	}
	/**
	 * 修改策略完成时间（延期）
	 */
	@Override
	public void updateCampsegEndDate(String campsegId, String endDate) throws Exception{
	    this.campSegInfoDao.updateCampSegInfoEndDate(campsegId,endDate);
	        
	}
	   /**
     * 撤销工单
     * @param campsegId  策略ID
     * @param ampsegStatId  撤消后的住哪个台
     * @param approve_desc  处理结果描述
     */
    @Override
    public void cancelAssignment(String campsegId, short ampsegStatId,
            String approve_desc) {
        campSegInfoDao.cancelAssignment(campsegId,ampsegStatId,approve_desc);
        
    }
    /**
     * 保存暂停/停止原因
     * @param campsegId  父策略ID
     * @param pauseComment  暂停原因
     */
    @Override
    public void updatMtlCampSeginfoPauseComment(String campsegId, String pauseComment) {
        campSegInfoDao.updatMtlCampSeginfoPauseComment(campsegId,pauseComment);
        mcdCampsegTaskDao.updateTaskPauseComment(campsegId,pauseComment);
    }
    /**
     * add by gaowj3 20150721
     * @Title: updateCampStat
     * @Description: 更改策略状态（浙江版）
     * @param @param campSegId
     * @param @return
     * @param @throws Exception    
     * @return List 
     * @throws
     */
    @Override
    public void updateCampStat(String campSegId, String type) {
        List rList = null;
        try {
            MtlCampSeginfo baseSeginfo = campSegInfoDao.getCampSegInfo(campSegId);
            rList = new ArrayList();
            rList.add(campSegId);           
            MtlCampSeginfo segInfo = campSegInfoDao.getCampSegInfo(campSegId);
            boolean cepEventFlag = false;
            if (StringUtil.isNotEmpty(segInfo.getCepEventId())) {
                cepEventFlag = true;
            }
            if (MpmCONST.MPM_CAMPSEG_STAT_HDZZ.equals(type)) {//终止
                if ("zhejiang".equalsIgnoreCase(Configure.getInstance().getProperty("PROVINCE"))) {
                    mcdCampsegTaskService.updateCampTaskStat(campSegId,MpmCONST.TASK_STATUS_STOP);
                    List<McdCampsegTask> mcdCampsegTakList = mcdCampsegTaskService.findByCampsegIdAndChannelId(campSegId,MpmCONST.CHANNEL_TYPE_SMS);
                    for(McdCampsegTask mcdCampsegTask : mcdCampsegTakList){
                        String taskSendoddTabName = mcdCampsegTask.getTaskSendoddTabName();
                        if(taskSendoddTabName != null && "".equals(taskSendoddTabName)){
                            mcdCampsegTaskService.dropTaskSendoddTabNameInMem(taskSendoddTabName);
                        }
                    }
                }
                if (cepEventFlag) {
                    /*CepMessageReceiverThread thread = CepReceiveThreadCache.getInstance().get(segInfo.getCampsegId());
                    if (thread != null) {*/
                        CepUtil.finishCepEvent(segInfo.getCepEventId());
                    /*  thread.stopThread();
                        CepReceiveThreadCache.getInstance().remove(segInfo.getCampsegId());
                    }*/
                }
                campSegInfoDao.updateCampStat(rList, type);
            } else if (MpmCONST.MPM_CAMPSEG_STAT_DDCG.equals(type)) {//启动
                String status = type;
                //浙江版，策略与业务状态可多选，关联删除
                if ("zhejiang".equalsIgnoreCase(Configure.getInstance().getProperty("PROVINCE"))) {
                    MtlCampSeginfo mtlCampSeginfo = campSegInfoDao.getCampSegInfo(campSegId);
                    int startDate = mtlCampSeginfo == null ? 0 : Integer.parseInt(mtlCampSeginfo.getStartDate().replaceAll("-",""));
                    int endDate = mtlCampSeginfo == null ? 0 : Integer.parseInt(mtlCampSeginfo.getEndDate().replaceAll("-",""));
                    int newDate = Integer.parseInt(DateUtil.date2String(new Date(), "yyyyMMdd"));
                    if(startDate > newDate){//未到策略开始时间
                        status = MpmCONST.MPM_CAMPSEG_STAT_ZXZT;
                    }else if(newDate >= startDate && newDate <= endDate){//到策略开始时间，未到策略结束时间
                        status = MpmCONST.MPM_CAMPSEG_STAT_DDCG;
                    }else{//已到策略结束时间
                        status = MpmCONST.MPM_CAMPSEG_STAT_HDWC;
                    }
                    mcdCampsegTaskService.updateCampTaskStat(campSegId,MpmCONST.TASK_STATUS_RUNNING);

                    if (cepEventFlag) {
                        CepUtil.restartCepEvent(segInfo.getCepEventId());
                    }
                    campSegInfoDao.updateCampStat(rList, status);
                }

            } else if (MpmCONST.MPM_CAMPSEG_STAT_PAUSE.equals(type)) {//暂停
                if ("zhejiang".equalsIgnoreCase(Configure.getInstance().getProperty("PROVINCE"))) {
                    mcdCampsegTaskService.updateCampTaskStat(campSegId,MpmCONST.TASK_STATUS_PAUSE);
                }
                
                if (cepEventFlag) {
                    CepUtil.stopCepEvent(segInfo.getCepEventId());
                }
                campSegInfoDao.updateCampStat(rList, type);

            }


        } catch (Exception e) {
            rList = null;
            log.error("策略状态变更失败：", e);
        }
    
        
    }
    /**
     * 获取有营销用语的渠道的营销用语
     * @param campsegId
     * @return
     */
    @Override
    public List getExecContentList(String campsegId) {
        return campSegInfoDao.getExecContentList(campsegId);
    }
    /**
     * 获取营销用语变量
     * @param campsegId
     * @return
     */
    @Override
    public List getExecContentVariableList(String campsegId) {
        return campSegInfoDao.getExecContentVariableList(campsegId);

    }
    /**
     * 保存营销用语
     * @param campsegId
     * @param channelId
     * @param execContent
     * @param ifHasVariate 
     */
    @Override
    public void saveExecContent(String campsegId, String channelId, String execContent, String ifHasVariate) {
        campSegInfoDao.saveExecContent(campsegId,channelId,execContent,ifHasVariate);
    }
    
    /*
	 * 属性递归查询(包括自己节点)
	 *
	 */
	@Override
	public List<MtlCampSeginfo> getCampSeginfoListByCampsegId(String campsegId) throws MpmException {
		List<MtlCampSeginfo> allTreeList = new ArrayList();
		try {
			if (StringUtils.isNotEmpty(campsegId)) {
				allTreeList = new ArrayList<MtlCampSeginfo>();
				MtlCampSeginfo seginfo = this.getCampSegInfo(campsegId);
				if (seginfo != null) {
					allTreeList.add(seginfo);
				}
				allTreeList.addAll(getSubCampsegInfoTreeList(campsegId));
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return allTreeList;
	}
	
	/**
	 * 获取指定活动ID下的所有子节点（向下递归，不包括自身）
	 * @param savedCampsegId
	 * @return
	 */
	public List<MtlCampSeginfo> getSubCampsegInfoTreeList(String campsegId) throws MpmException {
		List<MtlCampSeginfo> resultList = new ArrayList<MtlCampSeginfo>();
		try {
			List<MtlCampSeginfo> childList = campSegInfoDao.getSubCampsegInfo(campsegId);
			if (CollectionUtils.isNotEmpty(childList)) {
				resultList.addAll(childList);
				for (MtlCampSeginfo mcs : childList) {
					List<MtlCampSeginfo> cList = getSubCampsegInfoTreeList(mcs.getCampsegId());
					if (CollectionUtils.isNotEmpty(cList)) {
						resultList.addAll(cList);
					}
				}
			}
		} catch (Exception e) {
			log.error("getSubCampsegInfoTreeList error:", e);
		}
		return resultList;
	}
	
	@Override
	public boolean deleteLableByCampsegId(String campsegId) {
		return mtlCampsegCustgroupDao.deleteLableByCampsegId(campsegId);
	}
	   /**
     * 根据营销状态ID获取营销状态
     * @param string
     * @return
     */
    @Override
    public DimCampsegStat getDimCampsegStat(String dimCampsegStatID) {
        return campSegInfoDao.getDimCampsegStat(dimCampsegStatID);
    }
    /**
     * add by jinl 20150717
     * @Title: getTargetCustomerbase
     * @Description: 获取"目标客户群"信息
     * @param @param campsegId
     * @param @return
     * @param @throws Exception    
     * @return List 
     * @throws
     */
    @Override
    public List getTargetCustomerbase(String campsegId) throws Exception {
        return custGroupInfoService.getTargetCustomerbase(campsegId);
    }
    /**
     * 获取细分规则信息（时机）
     */
    @Override
    public List<Map<String, Object>> getrule(String campsegId) {
        return campSegInfoDao.getrule(campsegId);
    }
    /**
     * 查询本地审批日志
     * @param approveFlowid
     * @return
     */
    @Override
    public McdApproveLog getLogByFlowId(String flowId) {
        McdApproveLog mcdApproveLog = null;
        try {
            mcdApproveLog = campSegInfoDao.getLogByFlowId(flowId);
        } catch (Exception e) {
            log.error(e);
        }
        return mcdApproveLog;
    }
    /**
     * 根据策略id获得策略的所有渠道
     * @param campsegId
     * @return
     */
    @Override
    public List getChannelsByCampIds(String campsegIds) {
        return campSegInfoDao.getChannelsByCampIds(campsegIds);

    }
    /**
     * 查询指定策略指定渠道在指定时间段内的营销情况
     * @param campsegId
     * @param channelId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List getCampChannelDetail(String campsegId, String channelId, String startDate, String endDate) {
        return campSegInfoDao.getCampChannelDetail(campsegId,channelId,startDate,endDate);

    }
    /**
     * 查询某策略某个指定渠道的所有子策略某天的执行情况   
     * @param campsegIds
     * @param channelId
     * @param statDate
     * @return
     */
    @Override
    public List getCampsChannelSituation(String campsegIds, String channelId, String statDate) {
        String[] campsegs = campsegIds.split(",");
        List list = new ArrayList();
        for(int i=0;i<campsegs.length;i++){
            Map map =campSegInfoDao.getCampChannelSituation(campsegs[i],channelId,statDate);
            if(map!=null){
                list.add(map);
            }
        }
        return list;
    }
    
    public List<MtlStcPlanChannel> getStcPlanChannel(String planId){
		List<MtlStcPlanChannel> mtlStcPlanChannels = stcPlanDao.getChannelIds(planId);
		List<MtlStcPlanChannel> result = new ArrayList<MtlStcPlanChannel>();
		String channelIds = "";
		String planChannelId = "";
		Map<String, MtlStcPlanChannel> map = new HashMap<String, MtlStcPlanChannel>();
		
		for(int j = 0;j<mtlStcPlanChannels.size();j++){
			MtlStcPlanChannel mtlStcPlanChannel = mtlStcPlanChannels.get(j);
			map.put(mtlStcPlanChannel.getId(), mtlStcPlanChannel);
		}
		
		Set<String> keySet = map.keySet();  
        Iterator<String> it = keySet.iterator();  
        while (it.hasNext())  
        {  
        	String i = it.next();  
            result.add(map.get(i));
        }  
		return result;
	}
    
    
    /**
     * 浙江处理策略审批结束（通过或退回）后的信息
     * @param campId
     * @param approveFlag
     * @throws MpmException
     */
    @Override
    public String processApproveInfo(String approveInfoXML) {
        //String campsegId, short approveResult
        log.info("=====MpmApproveWsService----processApproveInfo param:"+approveInfoXML);
         String assing_id = null;
         String approve_flag = null;
         String approve_desc = null;
         String channel_id = null;
         String isSystemApprove = null;

        String flag ="0";
        String processingResults = "";
        //用来判断策略包最终是否审批通过
        boolean isapprove = true;
        //短信渠道是否通过
        boolean approveSMS = false;

        try{
             Document dom=DocumentHelper.parseText(approveInfoXML); 
             Element root=dom.getRootElement();  
             List<Element> elementList=root.elements("ASSIGNMENT_INFO"); 
    
             for(int i=0;i<elementList.size();i++){
                 Element element= (org.dom4j.Element) elementList.get(i);
                 assing_id = element.element("ASSIGN_ID").getText(); 
                 approve_flag = element.element("APPROVE_FLAG").getText(); 
                 approve_desc = null != element.element("APPROVE_DESC") ? element.element("APPROVE_DESC").getText():null; 
                 channel_id = element.element("CHANNEL_ID").getText(); 
                 isSystemApprove = element.element("IS_SYSTEM_APPROVE") == null ? "1" : "0";  //0:人工（soupUI）审批，1:审批系统审批(默认)
                short approveResult;
                if("1".equals(approve_flag)){//通过
                    approveResult = MpmCONST.MPM_SEG_APPROVE_RESULT_PASSED;
                    
                } else{//驳回
                    approveResult = MpmCONST.MPM_SEG_APPROVE_RESULT_NOTPASSED;
                }
                //根据工单编号，修改所有子策略（规则）下某渠道的审批状态
                mtlChannelDefDao.updateMtlChannelDefApproveResult(assing_id,approve_desc,channel_id,approveResult);
                
                //因外呼渠道换表存了，故更改外呼渠道的审批状态                
                mtlChannelDefDao.updateMtlChannelDefCallApproveResult(assing_id,approve_desc,channel_id,approveResult);
                if("0".equals(approve_flag)){
                    isapprove = false;
                 }
                
                //------------------------------------新增功能，生成营销任务记录------------------------------------------------------------------
                int channelId = channel_id == null ? 0 : Integer.parseInt(channel_id);
                if(channelId == MpmCONST.CHANNEL_TYPE_SMS_INT){
                    approveSMS = true;
                }
             }
             
                String campsegStatId = null;
                List list = campSegInfoDao.getCampSegInfoByApproveFlowId(assing_id);
                Map campSegInfoMap = null;
                if(list != null && list.size() > 0){
                    campSegInfoMap = (Map)list.get(0);
                }
                //只有策略包邮开始时间与结束时间，根据开始时间与结束时间破判断所有策略状态
                int startDate = campSegInfoMap.get("start_date") == null ? 0 : Integer.parseInt(campSegInfoMap.get("start_date").toString().replaceAll("-",""));
                int endDate = campSegInfoMap.get("end_date") == null ? 0 : Integer.parseInt(campSegInfoMap.get("end_date").toString().replaceAll("-",""));
                int newDate = Integer.parseInt(DateTool.getStringDate(new Date(), "yyyyMMdd"));
                String pidSampsegId = campSegInfoMap.get("campseg_id") == null ? "" : campSegInfoMap.get("campseg_id").toString();
             
             //根据工单 编号获取子策略（规则）
            List mtlCampSeginfoList = campSegInfoDao.getChildCampSeginfoByAssingId(assing_id);
            for(int i=0 ;i<mtlCampSeginfoList.size();i++){
                Map mtlCampSeginfoMap = (Map)mtlCampSeginfoList.get(i);
                String childCampseg_id = mtlCampSeginfoMap.get("campseg_id").toString();
                //查找子策略下有的所有渠道有几种审批状态
                List mtlChannelDefApproveFlowList = mtlChannelDefDao.getMtlChannelDefApproveFlowList(childCampseg_id);
                //当策略下有的所有渠道只有一种审批状态时，该策略状态即为渠道审批状态
                if(mtlChannelDefApproveFlowList != null && mtlChannelDefApproveFlowList.size() == 1){
                    Map approveFlowMap = (Map)mtlChannelDefApproveFlowList.get(0);
                    String channelApproveResult = approveFlowMap.get("approve_result").toString();
                    //String approve_result_desc = approveFlowMap.get("approve_result_desc").toString();
                    short approveResult;
                    if("1".equals(channelApproveResult)){//通过
                        approveResult = MpmCONST.MPM_SEG_APPROVE_RESULT_PASSED;
                        if(startDate > newDate){//未到策略开始时间
                            campsegStatId = MpmCONST.MPM_CAMPSEG_STAT_ZXZT;
                        }else if(newDate >= startDate && newDate <= endDate){//到策略开始时间，未到策略结束时间
                            campsegStatId = MpmCONST.MPM_CAMPSEG_STAT_DDCG;
                        }else{//已到策略结束时间
                            campsegStatId = MpmCONST.MPM_CAMPSEG_STAT_HDWC;
                        }
                        
                    } else{//驳回
                        approveResult = MpmCONST.MPM_SEG_APPROVE_RESULT_NOTPASSED;
                        campsegStatId = MpmCONST.MPM_CAMPSEG_STAT_SPYG;
                    }
                    MtlCampSeginfo segInfo = this.getCampSegInfo(childCampseg_id);
                    segInfo.setApproveResult(approveResult);
                    //segInfo.setApproveResultDesc(approve_result_desc);
                    segInfo.setCampsegStatId(Short.parseShort(campsegStatId));
                    campSegInfoDao.updateCampSegInfo(segInfo);                   
                }else if(mtlChannelDefApproveFlowList != null && mtlChannelDefApproveFlowList.size() > 1){
                //当策略下有的所有渠道有两种审批状态时，该策略状态一定为不通过，因为状态只有通过与不通过两种
                    Map approveFlowMap = (Map)mtlChannelDefApproveFlowList.get(0);
                    String channelApproveResult = approveFlowMap.get("approve_result").toString();
                    //String approve_result_desc = approveFlowMap.get("approve_result_desc").toString();
                    short approveResult = MpmCONST.MPM_SEG_APPROVE_RESULT_NOTPASSED;
                    MtlCampSeginfo segInfo = this.getCampSegInfo(childCampseg_id);
                    segInfo.setApproveResult(approveResult);
                    //segInfo.setApproveResultDesc(approve_result_desc);
                    segInfo.setCampsegStatId(Short.parseShort(campsegStatId));
                    campsegStatId = MpmCONST.MPM_CAMPSEG_STAT_SPYG;
                    campSegInfoDao.updateCampSegInfo(segInfo);   
                }
            }
        
            if(isapprove){
                if(startDate > newDate){//未到策略开始时间
                    campsegStatId = MpmCONST.MPM_CAMPSEG_STAT_ZXZT;
                }else if(newDate >= startDate && newDate <= endDate){//到策略开始时间，未到策略结束时间
                    campsegStatId = MpmCONST.MPM_CAMPSEG_STAT_DDCG;//所谓执行成功在浙江改为待执行
                }else{//已到策略结束时间
                    campsegStatId = MpmCONST.MPM_CAMPSEG_STAT_HDWC;
                }
                short approveResult = MpmCONST.MPM_SEG_APPROVE_RESULT_PASSED;
                campSegInfoDao.updateCampsegApproveStatusZJ(assing_id, "", approveResult,campsegStatId);
                
                //策略包整体审核通过，且子策略有短信渠道,且没有超期
                if(newDate < endDate){
                    List<MtlCampSeginfo> childMtlCampSeginfoList = this.getChildCampSeginfo(pidSampsegId);
                    boolean isSMSTest = false;
                    for(MtlCampSeginfo childMtl : childMtlCampSeginfoList){
                        //查找活动下的所有渠道
                        List<MtlChannelDef> mtlChannelDefList = this.getChannelByCampsegId(childMtl.getCampsegId());
                        boolean isSMS = false;
                    
                        for(MtlChannelDef mtlChannelDef : mtlChannelDefList){
                            //是周期性且是短信渠道    设计图画错，是否是周期性都要创建自动任务：mtlChannelDef.getContactType().intValue() == MpmCONST.MTL_CHANNLE_DEF_CONTACETTYPE_CYCLE && 
                            if(mtlChannelDef.getChanneltypeId().intValue() ==MpmCONST.CHANNEL_TYPE_SMS_INT){
                                //短信测试流程，暂时不写
                                isSMS = true;                    
                                
                            }else{
                                McdCampsegTask task = new McdCampsegTask();
                                MtlCampSeginfo mtlCampSeginfo = campSegInfoDao.getCampSegInfo(pidSampsegId);
                                task.setCampsegId(childMtl.getCampsegId());
                                //生成清单表（任务的基础清客户单表和派单客户清单表
                                Date startTime = DateTool.getDate(mtlCampSeginfo.getStartDate());
                                task.setTaskStartTime(startTime);
                                task.setExecStatus(MpmCONST.TASK_STATUS_INIT);
                                task.setChannelId(mtlChannelDef.getChannelId());
                                //把从策略获取表的过程个去掉   edit by lixq10  2016年6月3日10:43:33
//                              String custListTabName = childMtl.getInitCustListTab();
//                              task.setCustListTabName(custListTabName);
//                              String currentTime = MpmUtil.convertLongMillsToYYYYMMDDHHMMSS(new Date().getTime());
//                              task.setTaskSendoddTabName(MpmCONST.MTL_DUSER_O_PREFIX + currentTime);
//                              mcdCampsegTaskDao.createTaskSendoddTab(task.getTaskSendoddTabName());
                                task.setRetry(0);
                                task.setBotherAvoidNum(0);
                                task.setContactControlNum(0);
                                task.setCycleType((short)mtlChannelDef.getContactType().intValue());
//                              int intGroupNum = mcdCampsegTaskDao.getSqlFireTableNum(custListTabName);
//                              task.setIntGroupNum(intGroupNum);
                                mcdCampsegTaskDao.saveTask(task);
                                
                                String taskId = task.getTaskId();
                                short execStatus = MpmCONST.TASK_STATUS_INIT;
                                try {
                                    String custgroupId = campSegInfoDao.getMtlCampsegCustGroupId(childMtl.getCampsegId());
                                    String dataDate = "";
                                    if(custgroupId != null){
                                        List mtlCustomList = mcdMtlGroupInfoDao.getMtlCustomListInfo(custgroupId);
                                        if(mtlCustomList != null && mtlCustomList.size() > 0){
                                            Map mtlCustomMap = (Map)mtlCustomList.get(0);
                                            dataDate = mtlCustomMap.get("data_date") == null ? "" : mtlCustomMap.get("data_date").toString();
                                        }
                                    }
                                    int tableNum = 0;
                                    if(!"".equals(dataDate)){
//                                      tableNum = mcdMtlGroupInfoDao.getCustomListInfoNum(custListTabName,dataDate);
                                    }
                                    Date planExecTime = new Date();
                                    mcdCampsegTaskDao.insertMcdCampsegTaskDate(taskId,dataDate,execStatus,tableNum,planExecTime);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                    
                            }   
                        }
                    }
                    //有短信渠道且为测试中，更改所有策略状态
                    if(isSMSTest){
                        int i = 0 ;
                        for(MtlCampSeginfo childMtl : childMtlCampSeginfoList){
                            if(i == 0){
                                campSegInfoDao.updateCampsegInfoState(childMtl.getCampsegPid(), MpmCONST.MPM_CAMPSEG_STAT_HDCS);
                                i = 1;
                            }
                            campSegInfoDao.updateCampsegInfoState(childMtl.getCampsegId(), MpmCONST.MPM_CAMPSEG_STAT_HDCS);
                        }
                    }
                    
                    
                }
                
                
            }else{
                short approveResult = MpmCONST.MPM_SEG_APPROVE_RESULT_NOTPASSED ;
                campsegStatId = MpmCONST.MPM_CAMPSEG_STAT_SPYG;
                campSegInfoDao.updateCampsegApproveStatusZJ(assing_id, "", approveResult,campsegStatId);
            }
            
            processingResults = "处理成功";
            
            //当审批完成后，从审批系统调用审批日志  add by lixq10 2016年7月1日19:40:16 ------begin  add gaowj3 因为本地核心版本审批另做，这暂时去掉
            
//            //先从本地库去日志信息，如果该日志信息已经存在，则直接使用，否则再次调用
//            IMcdApproveLogService mcdApproveLogService = (IMcdApproveLogService) SystemServiceLocator.getInstance().getService("mcdApproveService");
//            McdApproveLog mcdApproveLogLocal = mcdApproveLogService.getLogByFlowId(assing_id);
//            
//            //当是人工审批（soupUI）审批时
//            McdApproveLog mcdApproveLog = new McdApproveLog();
//            mcdApproveLog.setApproveFlowId(assing_id);
//            if("0".equals(isSystemApprove)){
//                String manualApproveLog = this.assembleXmlLog(campSegInfoMap);
//                mcdApproveLog.setApproveResult(manualApproveLog);
//            }else{  //审批系统审批
//                String approveLog = this.getApproveLog(pidSampsegId);
//                mcdApproveLog.setApproveResult(approveLog);
//            }
//            
//            IMcdApproveLogService mcdApproveService = (IMcdApproveLogService) SystemServiceLocator.getInstance().getService("mcdApproveService");
//            if(mcdApproveLogLocal != null && null != mcdApproveLogLocal.getApproveResult()){
//                mcdApproveService.updateLogByFlowId(mcdApproveLog);
//            }else{
//                mcdApproveService.saveApproveLog(mcdApproveLog);
//            }
            //add by lixq10 2016年7月1日19:40:16 ------end
        }catch(Exception e){
            flag ="1";
            processingResults = "处理失败，原因：" + e.getMessage();
            log.error("",e);
        }finally{
            StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlStr.append("<MARKET_ASSIGNMENT_INFO><ASSIGNMENT_INFO>");
            xmlStr.append("<ASSIGN_ID>"+ assing_id +"</ASSIGN_ID>");
            xmlStr.append("<PROCESS_FLAG>"+ flag +"</PROCESS_FLAG>");
            xmlStr.append("<PROCESS_DESC>"+ processingResults +"</PROCESS_DESC>");
            xmlStr.append("</ASSIGNMENT_INFO></MARKET_ASSIGNMENT_INFO>");
            return xmlStr.toString();
        }
    }
    
    /**
     * 查找活动下的所有渠道
     * @param campsegId
     * @return
     */
    private List<MtlChannelDef> getChannelByCampsegId(String campsegId) {
        return mtlChannelDefDao.getChannelByCampsegId(campsegId);
    }
    /**
     * 当是soupUI审批系统的时候，拼装审批日志xml文件，保存至数据库
     * @param list
     * @return
     */
    private String assembleXmlLog(Map campSegInfoMap){
        //针对soapUI审批的策略，只是针对父策略，只需要拼一个参数即可
        int startDate = campSegInfoMap.get("start_date") == null ? 0 : Integer.parseInt(campSegInfoMap.get("start_date").toString().replaceAll("-",""));
        int endDate = campSegInfoMap.get("end_date") == null ? 0 : Integer.parseInt(campSegInfoMap.get("end_date").toString().replaceAll("-",""));
        int newDate = Integer.parseInt(DateTool.getStringDate(new Date(), "yyyyMMdd"));
        String pidSampsegId = campSegInfoMap.get("campseg_id") == null ? "" : campSegInfoMap.get("campseg_id").toString();
        String approveFlowId = campSegInfoMap.get("APPROVE_FLOW_ID") == null ? "" : campSegInfoMap.get("APPROVE_FLOW_ID").toString();
        String creator = campSegInfoMap.get("CREATE_USERNAME") == null ? "" : campSegInfoMap.get("CREATE_USERNAME").toString();
        
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
              .append("<MARKET_APPROVE_INFO>")
              .append("<ASSIGN_ID>").append(approveFlowId).append("</ASSIGN_ID>")
              .append("<CREATOR>").append(creator).append("</CREATOR>")
              .append("<ASSIGN_NAME>系统管理员审批</ASSIGN_NAME>")
              .append("<APPROVE_INFO>")
              .append("<NODE_NO>").append(UUID.randomUUID()).append("</NODE_NO>")
              .append("<NODE_NAME>系统管理员审批</NODE_NAME>")
              .append("<APPROVE_RESULT>同意</APPROVE_RESULT>")
              .append("<APPROVE_VIEW>111</APPROVE_VIEW>")
              .append("<APPROVALER_NAME>系统管理员审批</APPROVALER_NAME>")
              .append("<APPROVE_DATE>").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("</APPROVE_DATE>")
              .append("<IF_CURRENT_NODE>N</IF_CURRENT_NODE>")
              .append("</APPROVE_INFO>")
              .append("</MARKET_APPROVE_INFO>");
        return buffer.toString();
    }
    
    @Override
	public String createCustGroupTabAsCustTable(String tabPrefix,String custGroupId) {
		List<Map> list = mcdMtlGroupInfoDao.getMtlCustomListInfo(custGroupId);
		String tabNameModel = (String) list.get(0).get("LIST_TABLE_NAME");
		String tabName = tabPrefix + MpmUtil.convertLongMillsToYYYYMMDDHHMMSSSSS();
		String province = Configure.getInstance().getProperty("PROVINCE");
		//查询客户群的周期性
		MtlGroupInfo groupInfo = mcdMtlGroupInfoDao.getCustGroupInfoById(custGroupId);
		int updateCycle = groupInfo.getUpdateCycle();
		if(StringUtil.isNotEmpty(province) && province.equals("zhejiang")){  //浙江Oracle sqlfire同时创建表
//			创建分区   edit by lixq10 2016年6月2日21:13:06
			try {
				campSegInfoDao.excSqlInMcdAdInMem(this.getCreateDuserSQLForSqlfire(tabName, tabNameModel,updateCycle));
				//MCD表创建同义词
				custGroupInfoService.createSynonymTableMcdBySqlFire(tabName);

			} catch (Exception e) {
				log.error(e);
			}
		}else{
			campSegInfoDao.excSqlInMcd(this.getCreateDuserSQLForSqlfire(tabName, tabNameModel,updateCycle));
		}
		return tabName;
	}
    
    @Override
	public void createDuserIndex(String tableName,String custGroupId){
		try {
			if(tableName.indexOf("_") != -1){
				String ss[] = tableName.split("_");
				
				//查询客户群的周期性
				MtlGroupInfo groupInfo = mcdMtlGroupInfoDao.getCustGroupInfoById(custGroupId);
				int updateCycle = groupInfo.getUpdateCycle();
				String sql = "";
				if("2".equals(updateCycle) || "3".equals(updateCycle)){
					sql = "create index INDEX_"+(ss[ss.length-1])+"_0 ON "+tableName+"(PRODUCT_NO) LOCAL";
				}else{
					sql = "create index INDEX_"+(ss[ss.length-1])+"_0 ON "+tableName+"(PRODUCT_NO) ";
				}
				campSegInfoDao.excSqlInMcdAdInMem(sql);
				
			}
		} catch (Exception e) {
			log.error(e);
		}
		
	}
    
    /**
	 * 创建mcd_ad分区
	 * @param tableName
	 * @param tableModle
	 * @return
	 */
	private String getCreateDuserSQLForSqlfire(String tableName,String tableModle,int updateCycle){
		String result = "";
		List columnList = this.getSqlFireTableColumns(tableModle);
		StringBuffer buffer = new StringBuffer();
		for(int i = 0;i<columnList.size();i++){
			Map<String,Object> tmap = (Map<String, Object>) columnList.get(i);
			String columnName = (String)tmap.get("column_name");
			String dataType = (String)tmap.get("data_type");
			String dataLength = String.valueOf(tmap.get("data_length"));
			if(StringUtil.isNotEmpty(dataLength)){
				buffer.append(" ").append(columnName).append(" ").append(dataType).append("(").append(dataLength).append("),");
			}else{
				buffer.append(" ").append(columnName).append(" ").append(dataType);
			}
		}
		
		StringBuilder strRet = new StringBuilder();
		String tabSpace = MpmConfigure.getInstance().getProperty("MPM_SQLFIRE_TABLESPACE");
		String isUseSqlfire = MpmConfigure.getInstance().getProperty("MPM_IS_USE_SQLFIRE");
		if(StringUtil.isNotEmpty(isUseSqlfire) && isUseSqlfire.equals("false")){  //不使用sqlfire数据库
			strRet.append("create table ").append(tabSpace).append(".").append(tableName).append(" ");
		}else{
			strRet.append("create table ").append(tabSpace).append(".").append(tableName).append(" ");
		}
		
		//创建分区
		StringBuilder buffer1 = new StringBuilder();
		if(updateCycle == 3){  // 日周期
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			buffer1.append(" partition by range (DATA_DATE) (")
			  .append(" partition p_"+df.format(new Date())+" values less than ("+df.format(new Date())+"),")
			  .append(" partition p_max values less than (maxvalue))");
		}else if(updateCycle == 2){  //月周期
			SimpleDateFormat df1 = new SimpleDateFormat("yyyyMM");
			buffer1.append(" partition by range (DATA_DATE) (")
			  .append(" partition p_"+df1.format(new Date())+" values less than ("+df1.format(new Date())+"),")
			  .append(" partition p_max values less than (maxvalue))");
		}
		
		result = strRet.toString()+" ("+buffer.toString().substring(0, buffer.toString().length()-1)+") "+buffer1.toString();
		log.info("**************创建MCD_AD库分区表："+result);
		return result;
	}
	   /**
  * 根据表名读取表结构
  * @param tableName
  * @return
  */
 private List getSqlFireTableColumns(String tableName){
     return campSegInfoDao.getSqlFireTableColumnsInMem(tableName);
 }
 
	@Override
	public int excuteCustGroupCount(String customgroupid,McdTempletForm bussinessLableTemplate,McdTempletForm basicEventTemplate,Locale local,String orderProductNo,String excludeProductNo){
		int custCount = 0;
		try {
			/*String bussinessLableSql = null;
			if(null != bussinessLableTemplate){
				bussinessLableSql = this.getSql(bussinessLableTemplate, local);
			}
			String basicEventSql = null;
			if(null != basicEventTemplate){
				basicEventSql = this.getSql(basicEventTemplate, local);
			}*/
			custCount = custGroupInfoService.getCustInfoCount(customgroupid,null,null,orderProductNo,excludeProductNo);
		} catch (Exception e) {
			log.error("",e);
		}
		
		return custCount;
	}
	
	@Override
	public void insertCustGroupNewWay(String customgroupid,McdTempletForm bussinessLableTemplate,McdTempletForm basicEventTemplate,Locale local,String orderProductNo,String excludeProductNo,String tableName,boolean removeRepeatFlag) {
		try {
			/*if(null != bussinessLableTemplate){
				bussinessLableSql = this.getSql(bussinessLableTemplate, local);
			}
			String basicEventSql = null;
			if(null != basicEventTemplate){
				basicEventSql = this.getSql(basicEventTemplate, local);
			}*/
			custGroupInfoService.insertCustGroupNewWay(customgroupid,null,null,orderProductNo,excludeProductNo,tableName,removeRepeatFlag);
		} catch (Exception e) {
			log.error("",e);
		}
	}
    
}
