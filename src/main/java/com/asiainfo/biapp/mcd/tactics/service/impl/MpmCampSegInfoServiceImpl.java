package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.text.ParseException;
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

import javax.annotation.Resource;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.framework.privilege.service.IUserPrivilege;
import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.util.DESBase64Util;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.custgroup.dao.ICustGroupInfoDao;
import com.asiainfo.biapp.mcd.common.custgroup.service.ICustGroupInfoService;
import com.asiainfo.biapp.mcd.common.custgroup.vo.McdCustgroupDef;
import com.asiainfo.biapp.mcd.common.plan.dao.IMtlStcPlanDao;
import com.asiainfo.biapp.mcd.common.plan.vo.McdPlanDef;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.DateTool;
import com.asiainfo.biapp.mcd.common.util.MpmConfigure;
import com.asiainfo.biapp.mcd.common.util.MpmUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.dao.IMcdCampsegTaskDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlChannelDefDao;
import com.asiainfo.biapp.mcd.tactics.dao.MtlCampsegCustgroupDao;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.service.IMcdCampsegTaskService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampStatus;
import com.asiainfo.biapp.mcd.tactics.vo.McdApproveLog;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampTask;
import com.asiainfo.biapp.mcd.tactics.vo.McdTempletForm;
import com.asiainfo.biapp.mcd.tactics.vo.McdSysInterfaceDef;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampCustgroupList;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampChannelList;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefCall;
import com.asiainfo.biapp.mcd.tactics.vo.McdPlanChannelList;

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
    @Autowired 
    private IUserPrivilege userPrivilege;
    @Resource(name="mpmCampSegInfoDao")
    private IMpmCampSegInfoDao campSegInfoDao;
    @Resource(name="mtlChannelDefDao")
  	private IMtlChannelDefDao mtlChannelDefDao;//活动渠道Dao
    @Resource(name="mtlStcPlanDao")
    private IMtlStcPlanDao stcPlanDao;
    @Resource(name="mtlCampsegCustgroupDao")
    private MtlCampsegCustgroupDao mtlCampsegCustgroupDao; 
	@Resource(name = "mtlCallWsUrlService")
	private IMtlCallWsUrlService callwsUrlService;
	@Resource(name = "mcdCampsegTaskDao")
	private IMcdCampsegTaskDao mcdCampsegTaskDao;
	@Resource(name = "mcdCampsegTaskService")
	private IMcdCampsegTaskService mcdCampsegTaskService;
	@Resource(name = "custGroupInfoService")
	private ICustGroupInfoService custGroupInfoService;
	@Resource(name = "custGroupInfoDao")
    private ICustGroupInfoDao custGroupInfoDao;
	
    public IMtlChannelDefDao getMtlChannelDefDao() {
		return mtlChannelDefDao;
	}
	public void setMtlChannelDefDao(IMtlChannelDefDao mtlChannelDefDao) {
		this.mtlChannelDefDao = mtlChannelDefDao;
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
    public List<Map<String,Object>> searchIMcdCampsegInfo(McdCampDef segInfo, Pager pager) {
        List<Map<String,Object>> list = campSegInfoDao.searchIMcdCampsegInfo(segInfo,pager);
        return list;
    }
    
    @Override
	public String updateCampSegWaveInfoZJ(List<McdCampDef> seginfoList)throws MpmException {
		String approveFlag = "0";  //不走审批
		try {
			String campsegId = null;
			String campsegPid = null;
			String isApprove = "false";
			//在保存和其他表的对应关系
			for(int j = 0;j<seginfoList.size();j++){
				McdCampDef segInfo = seginfoList.get(j);
				isApprove = segInfo.getIsApprove();
				boolean isFatherNode = segInfo.getIsFatherNode();
				campsegId = segInfo.getCampId();
				String custgroupId = segInfo.getCustgroupId();//关联表
				String channelId = segInfo.getChannelId(); //渠道类型ID&渠道ID(关联表)
				
				List<McdCampChannelList> mtlChannelDefList = segInfo.getMtlChannelDefList();   //渠道执行信息
			
				User user = userPrivilege.queryUserById(segInfo.getCreateUserId());
				if (user != null) {
					segInfo.setCityId(user.getCityId());
					String deptMsg = user.getDepartmentId();
					segInfo.setDeptId(Integer.parseInt(deptMsg.split("&&")[0]));
				}
				
				segInfo.setCreateTime(new Date()); // 活动创建时间
				segInfo.setStatId(Short.valueOf(MpmCONST.MPM_CAMPSEG_STAT_CHZT)); // 活动初始状态
				segInfo.setApproveFlowId(null);
				segInfo.setApproveResultDesc("测试");
				if(isFatherNode){
					campSegInfoDao.saveCampSegInfo(segInfo);   //不用删除旧策略  后台调用savaOrUpdate
					campsegPid = campsegId;
				}else{
					segInfo.setPid(campsegPid);
					campSegInfoDao.saveCampSegInfo(segInfo);
					log.debug("****************新生成的波次活动ID:{}", campsegId);
					
					//2、保存客户群与策略的关系/保存时机与策略关系
					/*String basicEventTemplateId = segInfo.getBasicEventTemplateId();
					String bussinessLableTemplateId = segInfo.getBussinessLableTemplateId();*/
					//修改时,先删除客户群与策略的关系/保存时机与策略关系
					mtlCampsegCustgroupDao.deleteByCampsegId(campsegId);
					saveCampsegCustGroupZJ(campsegId, custgroupId, user.getId(),segInfo,"0");//基础客户群必须保存
					/*if(StringUtils.isNotEmpty(basicEventTemplateId)){  //选择时机
						saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"1");//保存基础标签  ARPU
					}
					if(StringUtils.isNotEmpty(bussinessLableTemplateId)){
						saveCampsegCustGroupZJ(campsegId, custgroupId, user.getUserid(),segInfo,"2");//保存业务标签 
					}*/
					
					//先删除渠道信息表
					mtlChannelDefDao.deleteMtlChannelDef(campsegId);
					//3、保存渠道
					if (CollectionUtils.isNotEmpty(mtlChannelDefList) && !isFatherNode) {
						for(int i = 0;i<mtlChannelDefList.size();i++){
							McdCampChannelList mtlChannelDef = mtlChannelDefList.get(i);
							mtlChannelDef.setCampId(campsegId);
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
			throw new MpmException("审批失败");
		}
		return approveFlag;
	}
    
	@Override
	public void updateCampsegInfo(McdCampDef segInfo){
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
    public List<McdDimCampStatus> getDimCampsegStatList() {
        List<McdDimCampStatus> list = campSegInfoDao.getDimCampsegStatList();
        return list;
    }
	@Override
	public String saveCampSegWaveInfoZJ(List<McdCampDef> seginfoList)throws MpmException {
		String approveFlag = "0";  //不走审批
		try {
			String campsegId = null;
			String campsegPid = null;
			String isApprove = "false";
			for(int j = 0;j<seginfoList.size();j++){
				McdCampDef segInfo = seginfoList.get(j);
				isApprove = segInfo.getIsApprove();
				boolean isFatherNode = segInfo.getIsFatherNode();
				campsegId = segInfo.getCampId();
				String custgroupId = segInfo.getCustgroupId();//关联表
				String channelId = segInfo.getChannelId(); //渠道类型ID&渠道ID(关联表)
				
				List<McdCampChannelList> mtlChannelDefList = segInfo.getMtlChannelDefList();   //渠道执行信息
					
				segInfo.setCreateTime(new Date()); // 活动创建时间
				segInfo.setStatId(Short.valueOf(MpmCONST.MPM_CAMPSEG_STAT_CHZT)); // 活动初始状态
				segInfo.setApproveFlowId(null);
				if(isFatherNode){
					campsegId = (String) campSegInfoDao.saveCampSegInfo(segInfo);//Duang Duang Duang 保存策略！！！
					campsegPid = campsegId;
				}else{
					segInfo.setPid(campsegPid);
					segInfo.setCampId(MpmUtil.generateCampsegAndTaskNo());
					campsegId = (String) campSegInfoDao.saveCampSegInfo(segInfo);//Duang Duang Duang 保存策略！！！
					log.debug("****************新生成的波次活动ID:{}", campsegId);
					//1、保存客户群与策略的关系 
					saveCampsegCustGroupZJ(campsegId, custgroupId, "chenyg",segInfo,"0");//基础客户群必须保存
					
					//2、保存渠道     ------------------无需判断是否已经存在
					if (CollectionUtils.isNotEmpty(mtlChannelDefList) && !isFatherNode) {
						for(int i = 0;i<mtlChannelDefList.size();i++){
							McdCampChannelList mtlChannelDef = mtlChannelDefList.get(i);
							mtlChannelDef.setCampId(campsegId);
							mtlChannelDefDao.save(mtlChannelDef);
						}
					}
					
					if(segInfo.getMtlChannelDefCall() != null){
						MtlChannelDefCall mtlChannelDefCall = segInfo.getMtlChannelDefCall();
						mtlChannelDefCall.setCampsegId(campsegId);
						mtlChannelDefCall.setChannelId(channelId);
						mtlChannelDefDao.save(mtlChannelDefCall);
					}

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
			throw new MpmException(e.getMessage());
		}
		return approveFlag;
	}
    /**
     * 根据渠道ID获取渠道
     * @param planId
     * @return
     */
    @Override
    public McdPlanDef getMtlStcPlanByPlanId(String planId) {
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
    public List<McdCampCustgroupList> getCustGroupSelectList(String campsegId) throws MpmException {
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
    public List<Map<String,Object>> getMtlChannelDefs(String campsegId) {
        List<Map<String,Object>> list = mtlChannelDefDao.getMtlChannelDefs(campsegId);
        return list;
    }
    
	@Override
	public void saveCampsegCustGroupZJ(String campsegId, String custGroupIdStr, String userId,McdCampDef segInfo,String flag) throws MpmException {
			McdCampCustgroupList mtlCampsegCustGroup = new McdCampCustgroupList();
			mtlCampsegCustGroup.setCustgroupId(segInfo.getCustgroupId());
			mtlCampsegCustGroup.setCampId(campsegId);
			mtlCampsegCustGroup.setCustgroupName("test");
			mtlCampsegCustGroup.setCustgroupType("CG"); //客户群类型
		
			try {
				mtlCampsegCustGroup.setCampsegCustgroupId(DataBaseAdapter.generaterPrimaryKey());
			} catch (Exception e) {
				e.printStackTrace();
			}
			mtlCampsegCustgroupDao.save(mtlCampsegCustGroup);
	}
	public McdCampDef getBaseCampsegInfo(String campsegId) throws Exception {
		McdCampDef campsegInfo = null;
		if (StringUtils.isNotEmpty(campsegId)) {
			campsegInfo = this.getCampSegInfo(campsegId);
			if (!"0".equals(campsegInfo.getPid()) && StringUtils.isNotEmpty(campsegInfo.getPid())) {
				campsegInfo = this.getBaseCampsegInfo(campsegInfo.getPid());
			}
		}
		return campsegInfo;
	}
	public McdCampDef getCampSegInfo(String campSegId) throws MpmException {
		McdCampDef obj = null;
		try {
			obj = campSegInfoDao.getCampSegInfo(campSegId);
		} catch (Exception e) {
			log.error("", e);
			throw new MpmException(e.getMessage());
		}
		return obj;
	}
	@Override
	public String submitApprovalXml(String campsegId) {
		StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlStr.append("<campsegInfo>");

        try {
        	//策略包基本信息
			McdCampDef mtlCampSeginfo = campSegInfoDao.getCampSegInfo(campsegId);
			//策略包下子策略基本信息
			List<McdCampDef> mtlCampSeginfoList = this.getChildCampSeginfo(campsegId);
			//取渠道信息
			List<Map<String,Object>> approveChannelIdList = mtlChannelDefDao.findChildChannelIdList(campsegId);
			Set<String> channeIdSet = new HashSet<String>();
			for(int i = 0;i< approveChannelIdList.size();i++){
				Map<String,Object> map = (Map<String,Object>) approveChannelIdList.get(i);
				String channelId = map.get("channel_id").toString();
				channeIdSet.add(channelId);
			}
			String channeIds = channeIdSet.toString().replaceAll(" ","");


	    	//策略基本信息
	    	xmlStr.append("<campsegId>" + mtlCampSeginfo.getCampId()+ "</campsegId>");//营销策略编号 
	    	xmlStr.append("<campsegName>" + mtlCampSeginfo.getCampName()+ "</campsegName>");//策略名称 
	    	xmlStr.append("<campsegType>" + mtlCampSeginfo.getTypeId()+ "</campsegType>");//营销类别	
	    	xmlStr.append("<startDate>" + mtlCampSeginfo.getStartDate()+ "</startDate>");//策略开始时间
	    	xmlStr.append("<endDate>" + mtlCampSeginfo.getEndDate() + "</endDate>");//策略结束时间
	    	xmlStr.append("<createTime>" + DateFormatUtils.format(mtlCampSeginfo.getCreateTime(), "yyyy-MM-dd HH:mm:ss")  + "</createTime>");//创建时间
	    	
	    	User user  = userPrivilege.queryUserById(mtlCampSeginfo.getCreateUserId());
	    	String userName =  user != null ? user.getName() : "";
	    	xmlStr.append("<createUser>" +userName + "</createUser>");//创建人
	    	//有些帐号经分帐号和OA帐号不同 提交审批需要OA帐号
	    	String createUserId=mtlCampSeginfo.getCreateUserId();
	    	if(oaMap!=null){
	    		if(oaMap.get(createUserId)!=null){
		    		createUserId=oaMap.get(createUserId);
		    	}
	    	}
	    	xmlStr.append("<createUserId>" + createUserId + "</createUserId>");//创建人ID
	    	xmlStr.append("<cityId>" + mtlCampSeginfo.getCityId() + "</cityId>");//创建人地市id
	    	xmlStr.append("<approveChannelId>" + channeIds.substring(1,channeIds.length()-1) + "</approveChannelId>");//审批渠道类型（判断采用哪个流程）：渠道ID
	    	xmlStr.append("<approveFlowId>" + mtlCampSeginfo.getApproveFlowId() + "</approveFlowId>");//审批流程ID
	    	String campsegInfoViewUrl = MpmConfigure.getInstance().getProperty("CAMPSEGINFO_VIEWURL");
			String token = DESBase64Util.encodeInfo(mtlCampSeginfo.getCampId());
	    	xmlStr.append("<campsegInfoViewUrl>" + campsegInfoViewUrl+mtlCampSeginfo.getCampId() + "&token=" +token+ "</campsegInfoViewUrl>");////营销策略信息URL
	    	xmlStr.append("</campsegInfo>");
					
			String results = "";
			String request_id ="";
			String assign_id = "";
			String result_desc = "";
			
			log.info("开始提交审批 ！");
			McdSysInterfaceDef url = callwsUrlService.getCallwsURL("APPREVEINFO_BYIDS");
		
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
			
			McdCampDef segInfo = campSegInfoDao.getCampSegInfo(campsegId);
			if("1".equals(results)){
				segInfo.setApproveFlowId(assign_id);
				segInfo.setStatId(Short.valueOf(MpmCONST.MPM_CAMPSEG_STAT_HDSP));
				for(McdCampDef childSeg : mtlCampSeginfoList){
					childSeg.setApproveFlowId(assign_id);
					childSeg.setStatId(Short.valueOf(MpmCONST.MPM_CAMPSEG_STAT_HDSP));
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
	public List<McdCampDef> getChildCampSeginfo(String campsegId) {
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
                McdCampDef segInfo = campSegInfoDao.getCampSegInfo(v_campSegId);
                //删除营销活动
                campSegInfoDao.deleteCampSegInfo(segInfo);
            }
        } catch (Exception e) {
            log.error("", e);
            throw new MpmException("删除营销活动" + campSegId
                    + "失败");
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
		custGroupInfoDao.addCreateCustGroupTabInMem(MpmUtil.getSqlCreateAsTableInSqlFire(tabName, tabNameModel)); 
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
        List<String> rList = null;
        try {
            rList = new ArrayList<String>();
            rList.add(campSegId);           
            McdCampDef segInfo = campSegInfoDao.getCampSegInfo(campSegId);
            boolean cepEventFlag = false;
            if (StringUtils.isNotEmpty(segInfo.getCepEventId())) {
                cepEventFlag = true;
            }
            if (MpmCONST.MPM_CAMPSEG_STAT_HDZZ.equals(type)) {//终止
                if ("zhejiang".equalsIgnoreCase(MpmConfigure.getInstance().getProperty("PROVINCE"))) {
                    mcdCampsegTaskService.updateCampTaskStat(campSegId,MpmCONST.TASK_STATUS_STOP);
                    List<McdCampTask> mcdCampsegTakList = mcdCampsegTaskService.findByCampsegIdAndChannelId(campSegId,MpmCONST.CHANNEL_TYPE_SMS);
                    for(McdCampTask mcdCampsegTask : mcdCampsegTakList){
                        String taskSendoddTabName = mcdCampsegTask.getTaskSendoddTabName();
                        if(taskSendoddTabName != null && "".equals(taskSendoddTabName)){
                            mcdCampsegTaskService.dropTaskSendoddTabNameInMem(taskSendoddTabName);
                        }
                    }
                }
                if (cepEventFlag) {
                    /*CepMessageReceiverThread thread = CepReceiveThreadCache.getInstance().get(segInfo.getCampsegId());
                    if (thread != null) {*/
                	//TODO CEP需要重构    
                	//CepUtil.finishCepEvent(segInfo.getCepEventId());
                    /*  thread.stopThread();
                        CepReceiveThreadCache.getInstance().remove(segInfo.getCampsegId());
                    }*/
                }
                campSegInfoDao.updateCampStat(rList, type);
            } else if (MpmCONST.MPM_CAMPSEG_STAT_DDCG.equals(type)) {//启动
                String status = type;
                //浙江版，策略与业务状态可多选，关联删除
                if ("zhejiang".equalsIgnoreCase(MpmConfigure.getInstance().getProperty("PROVINCE"))) {
                    McdCampDef mtlCampSeginfo = campSegInfoDao.getCampSegInfo(campSegId);
                    int startDate = mtlCampSeginfo == null ? 0 : Integer.parseInt(mtlCampSeginfo.getStartDate().replaceAll("-",""));
                    int endDate = mtlCampSeginfo == null ? 0 : Integer.parseInt(mtlCampSeginfo.getEndDate().replaceAll("-",""));
                    int newDate = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
                    if(startDate > newDate){//未到策略开始时间
                        status = MpmCONST.MPM_CAMPSEG_STAT_ZXZT;
                    }else if(newDate >= startDate && newDate <= endDate){//到策略开始时间，未到策略结束时间
                        status = MpmCONST.MPM_CAMPSEG_STAT_DDCG;
                    }else{//已到策略结束时间
                        status = MpmCONST.MPM_CAMPSEG_STAT_HDWC;
                    }
                    mcdCampsegTaskService.updateCampTaskStat(campSegId,MpmCONST.TASK_STATUS_RUNNING);

                    if (cepEventFlag) {
                        
                    	//TODO CEP需要重构
                    	//CepUtil.restartCepEvent(segInfo.getCepEventId());
                    }
                    campSegInfoDao.updateCampStat(rList, status);
                }

            } else if (MpmCONST.MPM_CAMPSEG_STAT_PAUSE.equals(type)) {//暂停
                if ("zhejiang".equalsIgnoreCase(MpmConfigure.getInstance().getProperty("PROVINCE"))) {
                    mcdCampsegTaskService.updateCampTaskStat(campSegId,MpmCONST.TASK_STATUS_PAUSE);
                }
                
                if (cepEventFlag) {
                	//TODO CEP需要重构
                    //CepUtil.stopCepEvent(segInfo.getCepEventId());
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
    public List<Map<String,Object>> getExecContentList(String campsegId) {
        return campSegInfoDao.getExecContentList(campsegId);
    }
    /**
     * 获取营销用语变量
     * @param campsegId
     * @return
     */
    @Override
    public List<Map<String,Object>> getExecContentVariableList(String campsegId) {
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
	public List<McdCampDef> getCampSeginfoListByCampsegId(String campsegId) throws MpmException {
		List<McdCampDef> allTreeList = new ArrayList<McdCampDef>();
		try {
			if (StringUtils.isNotEmpty(campsegId)) {
				allTreeList = new ArrayList<McdCampDef>();
				McdCampDef seginfo = this.getCampSegInfo(campsegId);
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
	public List<McdCampDef> getSubCampsegInfoTreeList(String campsegId) throws MpmException {
		List<McdCampDef> resultList = new ArrayList<McdCampDef>();
		try {
			List<McdCampDef> childList = campSegInfoDao.getSubCampsegInfo(campsegId);
			if (CollectionUtils.isNotEmpty(childList)) {
				resultList.addAll(childList);
				for (McdCampDef mcs : childList) {
					List<McdCampDef> cList = getSubCampsegInfoTreeList(mcs.getCampId());
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
    public McdDimCampStatus getDimCampsegStat(String dimCampsegStatID) {
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
    public List<Map<String,Object>> getTargetCustomerbase(String campsegId) throws Exception {
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
    public List<Map<String,Object>> getChannelsByCampIds(String campsegIds) {
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
    public List<Map<String,Object>> getCampChannelDetail(String campsegId, String channelId, String startDate, String endDate) {
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
    public List<Map<String,Object>> getCampsChannelSituation(String campsegIds, String channelId, String statDate) {
        String[] campsegs = campsegIds.split(",");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<campsegs.length;i++){
            Map<String, Object> map =campSegInfoDao.getCampChannelSituation(campsegs[i],channelId,statDate);
            if(map!=null){
                list.add(map);
            }
        }
        return list;
    }
    
    public List<McdPlanChannelList> getStcPlanChannel(String planId){
		List<McdPlanChannelList> mtlStcPlanChannels = stcPlanDao.getChannelIds(planId);
		List<McdPlanChannelList> result = new ArrayList<McdPlanChannelList>();
		Map<String, McdPlanChannelList> map = new HashMap<String, McdPlanChannelList>();
		
		for(int j = 0;j<mtlStcPlanChannels.size();j++){
			McdPlanChannelList mtlStcPlanChannel = mtlStcPlanChannels.get(j);
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

        String flag ="0";
        String processingResults = "";
        //用来判断策略包最终是否审批通过
        boolean isapprove = true;
        //短信渠道是否通过
        StringBuffer xmlStr = new StringBuffer();
        try{
             Document dom=DocumentHelper.parseText(approveInfoXML); 
             Element root=dom.getRootElement();
             
             @SuppressWarnings("unchecked")
			List<Element> elementList=root.elements("ASSIGNMENT_INFO"); 
    
             for(int i=0;i<elementList.size();i++){
                 Element element= (org.dom4j.Element) elementList.get(i);
                 assing_id = element.element("ASSIGN_ID").getText(); 
                 approve_flag = element.element("APPROVE_FLAG").getText(); 
                 approve_desc = null != element.element("APPROVE_DESC") ? element.element("APPROVE_DESC").getText():null; 
                 channel_id = element.element("CHANNEL_ID").getText(); 
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
                }
             }
             
                String campsegStatId = null;
                List<Map<String,Object>> list = campSegInfoDao.getCampSegInfoByApproveFlowId(assing_id);
                Map<String,Object> campSegInfoMap = null;
                if(list != null && list.size() > 0){
                    campSegInfoMap = (Map<String,Object>)list.get(0);
                }
                //只有策略包邮开始时间与结束时间，根据开始时间与结束时间破判断所有策略状态
                int startDate = campSegInfoMap.get("start_date") == null ? 0 : Integer.parseInt(campSegInfoMap.get("start_date").toString().replaceAll("-",""));
                int endDate = campSegInfoMap.get("end_date") == null ? 0 : Integer.parseInt(campSegInfoMap.get("end_date").toString().replaceAll("-",""));
                int newDate = Integer.parseInt(DateTool.getStringDate(new Date(), "yyyyMMdd"));
                String pidSampsegId = campSegInfoMap.get("campseg_id") == null ? "" : campSegInfoMap.get("campseg_id").toString();
             
             //根据工单 编号获取子策略（规则）
            List<Map<String,Object>> mtlCampSeginfoList = campSegInfoDao.getChildCampSeginfoByAssingId(assing_id);
            for(int i=0 ;i<mtlCampSeginfoList.size();i++){
                Map<String,Object> mtlCampSeginfoMap = (Map<String,Object>)mtlCampSeginfoList.get(i);
                String childCampseg_id = mtlCampSeginfoMap.get("campseg_id").toString();
                //查找子策略下有的所有渠道有几种审批状态
                List<Map<String,Object>> mtlChannelDefApproveFlowList = mtlChannelDefDao.getMtlChannelDefApproveFlowList(childCampseg_id);
                //当策略下有的所有渠道只有一种审批状态时，该策略状态即为渠道审批状态
                if(mtlChannelDefApproveFlowList != null && mtlChannelDefApproveFlowList.size() == 1){
                    Map<String,Object> approveFlowMap = (Map<String,Object>)mtlChannelDefApproveFlowList.get(0);
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
                    McdCampDef segInfo = this.getCampSegInfo(childCampseg_id);
                    segInfo.setApproveResult(approveResult);
                    //segInfo.setApproveResultDesc(approve_result_desc);
                    segInfo.setStatId(Short.parseShort(campsegStatId));
                    campSegInfoDao.updateCampSegInfo(segInfo);                   
                }else if(mtlChannelDefApproveFlowList != null && mtlChannelDefApproveFlowList.size() > 1){
                //当策略下有的所有渠道有两种审批状态时，该策略状态一定为不通过，因为状态只有通过与不通过两种
                    short approveResult = MpmCONST.MPM_SEG_APPROVE_RESULT_NOTPASSED;
                    McdCampDef segInfo = this.getCampSegInfo(childCampseg_id);
                    segInfo.setApproveResult(approveResult);
                    segInfo.setStatId(Short.parseShort(campsegStatId));
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
                    List<McdCampDef> childMtlCampSeginfoList = this.getChildCampSeginfo(pidSampsegId);
                    boolean isSMSTest = false;
                    for(McdCampDef childMtl : childMtlCampSeginfoList){
                        //查找活动下的所有渠道
                        List<McdCampChannelList> mtlChannelDefList = this.getChannelByCampsegId(childMtl.getCampId());
                    
                        for(McdCampChannelList mtlChannelDef : mtlChannelDefList){
                            //是周期性且是短信渠道    设计图画错，是否是周期性都要创建自动任务：mtlChannelDef.getContactType().intValue() == MpmCONST.MTL_CHANNLE_DEF_CONTACETTYPE_CYCLE && 
                            if(Integer.parseInt(mtlChannelDef.getChannelId()) ==MpmCONST.CHANNEL_TYPE_SMS_INT){
                                //短信测试流程，暂时不写
                                
                            }else{
                                McdCampTask task = new McdCampTask();
                                McdCampDef mtlCampSeginfo = campSegInfoDao.getCampSegInfo(pidSampsegId);
                                task.setCampsegId(childMtl.getCampId());
                                //生成清单表（任务的基础清客户单表和派单客户清单表
                                Date startTime = DateTool.getDate(mtlCampSeginfo.getStartDate());
                                task.setTaskStartTime(startTime);
                                task.setExecStatus(MpmCONST.TASK_STATUS_INIT);
                                task.setChannelId(mtlChannelDef.getChannelId());
                                //把从策略获取表的过程个去掉   edit by lixq10  2016年6月3日10:43:33
                                task.setRetry(0);
                                task.setBotherAvoidNum(0);
                                task.setContactControlNum(0);
                                task.setCycleType((short)mtlChannelDef.getContactType().intValue());
                                mcdCampsegTaskDao.saveTask(task);
                                
                                String taskId = task.getTaskId();
                                short execStatus = MpmCONST.TASK_STATUS_INIT;
                                try {
                                    String custgroupId = campSegInfoDao.getMtlCampsegCustGroupId(childMtl.getCampId());
                                    String dataDate = "";
                                    if(custgroupId != null){
                                        List<Map<String,Object>> mtlCustomList = custGroupInfoDao.getMtlCustomListInfo(custgroupId);
                                        if(mtlCustomList != null && mtlCustomList.size() > 0){
                                            Map<String,Object> mtlCustomMap = (Map<String,Object>)mtlCustomList.get(0);
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
                                    e.printStackTrace();
                                }
                                    
                            }   
                        }
                    }
                    //有短信渠道且为测试中，更改所有策略状态
                    if(isSMSTest){
                        int i = 0 ;
                        for(McdCampDef childMtl : childMtlCampSeginfoList){
                            if(i == 0){
                                campSegInfoDao.updateCampsegInfoState(childMtl.getPid(), MpmCONST.MPM_CAMPSEG_STAT_HDCS);
                                i = 1;
                            }
                            campSegInfoDao.updateCampsegInfoState(childMtl.getCampId(), MpmCONST.MPM_CAMPSEG_STAT_HDCS);
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
            xmlStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xmlStr.append("<MARKET_ASSIGNMENT_INFO><ASSIGNMENT_INFO>");
            xmlStr.append("<ASSIGN_ID>"+ assing_id +"</ASSIGN_ID>");
            xmlStr.append("<PROCESS_FLAG>"+ flag +"</PROCESS_FLAG>");
            xmlStr.append("<PROCESS_DESC>"+ processingResults +"</PROCESS_DESC>");
            xmlStr.append("</ASSIGNMENT_INFO></MARKET_ASSIGNMENT_INFO>");
        }
        return xmlStr.toString();
    }
    
    /**
     * 查找活动下的所有渠道
     * @param campsegId
     * @return
     */
    private List<McdCampChannelList> getChannelByCampsegId(String campsegId) {
        return mtlChannelDefDao.getChannelByCampsegId(campsegId);
    }
     
    @Override
	public String createCustGroupTabAsCustTable(String tabPrefix,String custGroupId) {
		List<Map<String,Object>> list = custGroupInfoDao.getMtlCustomListInfo(custGroupId);
		String tabNameModel = (String) list.get(0).get("LIST_TABLE_NAME");
		String tabName = tabPrefix + MpmUtil.convertLongMillsToYYYYMMDDHHMMSSSSS();
		String province = MpmConfigure.getInstance().getProperty("PROVINCE");
		//查询客户群的周期性
		McdCustgroupDef groupInfo = custGroupInfoDao.getCustGroupInfoById(custGroupId);
		int updateCycle = groupInfo.getUpdateCycle();
		if(StringUtils.isNotEmpty(province) && province.equals("zhejiang")){  //浙江Oracle sqlfire同时创建表
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
				McdCustgroupDef groupInfo = custGroupInfoDao.getCustGroupInfoById(custGroupId);
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
		List<Map<String,Object>> columnList = this.getSqlFireTableColumns(tableModle);
		StringBuffer buffer = new StringBuffer();
		for(int i = 0;i<columnList.size();i++){
			Map<String,Object> tmap = (Map<String, Object>) columnList.get(i);
			String columnName = (String)tmap.get("column_name");
			String dataType = (String)tmap.get("data_type");
			String dataLength = String.valueOf(tmap.get("data_length"));
			if(StringUtils.isNotEmpty(dataLength)){
				buffer.append(" ").append(columnName).append(" ").append(dataType).append("(").append(dataLength).append("),");
			}else{
				buffer.append(" ").append(columnName).append(" ").append(dataType);
			}
		}
		
		StringBuilder strRet = new StringBuilder();
		String tabSpace = MpmConfigure.getInstance().getProperty("MPM_SQLFIRE_TABLESPACE");
		String isUseSqlfire = MpmConfigure.getInstance().getProperty("MPM_IS_USE_SQLFIRE");
		if(StringUtils.isNotEmpty(isUseSqlfire) && isUseSqlfire.equals("false")){  //不使用sqlfire数据库
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
 private List<Map<String,Object>> getSqlFireTableColumns(String tableName){
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
	@Override
	public String saveCampInfo(User user, List<McdCampDef> campSegInfoList) throws Exception {
		String approveFlag = "0";  //不走审批
		String isApprove = "false";
		String campPid="";
		for(McdCampDef campInfo:campSegInfoList){
			McdCampDef tmp = this.saveBefore(user,campInfo);
			String currentCampId;
			List<McdCampChannelList> channelList =null;			
            if(tmp.getPid()!="0"){//子策略
            	isApprove = tmp.getIsApprove();
            	currentCampId = MpmUtil.generateCampsegAndTaskNo();
            	tmp.setCampId(currentCampId);
            	String custGroupId =tmp.getCustgroupId();
            	this.saveCampCustRel(currentCampId, custGroupId);//Duang Duang Duang 保存策略和客户群关系表！！！
            	channelList = tmp.getMtlChannelDefList();
            	for(McdCampChannelList campChannel:channelList){
            		mtlChannelDefDao.save(campChannel); //Duang Duang Duang 保存策略和渠道关系表！！！
            	}
			}else{//父策略
				campPid = tmp.getPid();
			}
			campSegInfoDao.saveCampSegInfo(tmp);//Duang Duang Duang 保存策略！！！
			
			if("true".equals(isApprove)){
				String approveStr = this.submitApprovalXml(campPid);
				if("提交审批成功".equals(approveStr)){
					approveFlag = "1"; //走审批，审批成功
				}else{
					approveFlag = "2"; //走审批，审批失败
				}
		}
		}
		return approveFlag;
	}
	private McdCampDef saveBefore(User user, McdCampDef campInfo){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		campInfo.setStatId(Short.parseShort(MpmCONST.MPM_CAMPSEG_STAT_CHZT));//策略状态
		campInfo.setCreateUserId(user.getId()); // 活动策划人
		campInfo.setCreateUserName(user.getName());
		campInfo.setCityId(user.getCityId()); // 策划人所属城市
		campInfo.setDeptId(Integer.parseInt(user.getDepartmentId()));
		try {
			campInfo.setCreateTime(format.parse(format.format(new Date())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return campInfo;
	}
	
	private void saveCampCustRel(String campId, String custGroupId)  {
			McdCampCustgroupList mtlCampsegCustGroup = new McdCampCustgroupList();
			mtlCampsegCustGroup.setCustgroupId(custGroupId);
			mtlCampsegCustGroup.setCampId(campId);
			try {
				mtlCampsegCustgroupDao.save(mtlCampsegCustGroup);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
    
}
