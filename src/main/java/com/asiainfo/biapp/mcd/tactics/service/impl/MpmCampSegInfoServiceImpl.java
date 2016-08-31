package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.amqp.CepUtil;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.dao.plan.MtlStcPlanDao;
import com.asiainfo.biapp.mcd.common.util.MpmConfigure;
import com.asiainfo.biapp.mcd.common.util.MpmLocaleUtil;
import com.asiainfo.biapp.mcd.common.util.MpmUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlan;
import com.asiainfo.biapp.mcd.custgroup.dao.CreateCustGroupTabDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMcdCampsegTaskDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlCampsegCiCustDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlChannelDefDao;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.service.IMcdCampsegTaskService;
//import com.asiainfo.biapp.mcd.tactics.service.IMcdCampsegTaskService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.LkgStaff;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampsegTask;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCallwsUrl;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampsegCiCustgroup;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDef;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefCall;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefCallId;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefId;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.utils.config.Configure;
import com.asiainfo.biframe.utils.date.DateUtil;
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
    //业务类型
    @Resource(name="mtlStcPlanDao")
    private MtlStcPlanDao stcPlanDao;
    @Resource(name="mtlCampsegCiCustDao")
    private IMtlCampsegCiCustDao mtlCampsegCiCustDao; 
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
	
    public IMtlChannelDefDao getMtlChannelDefDao() {
		return mtlChannelDefDao;
	}
	public void setMtlChannelDefDao(IMtlChannelDefDao mtlChannelDefDao) {
		this.mtlChannelDefDao = mtlChannelDefDao;
	}
	public IMtlCampsegCiCustDao getMtlCampsegCiCustDao() {
		return mtlCampsegCiCustDao;
	}
	public void setMtlCampsegCiCustDao(IMtlCampsegCiCustDao mtlCampsegCiCustDao) {
		this.mtlCampsegCiCustDao = mtlCampsegCiCustDao;
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

					
				//1、保存活动信息(波次条件、父活动编号、活动实际含义、推荐业务、营销时机)
				LkgStaff user = (LkgStaff)mpmUserPrivilegeService.getUser(segInfo.getCreateUserid());
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
					/*String basicEventTemplateId = segInfo.getBasicEventTemplateId();
					String bussinessLableTemplateId = segInfo.getBussinessLableTemplateId();*/
					saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"0");//基础客户群必须保存
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
}
