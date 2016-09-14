package com.asiainfo.biapp.mcd.common.service.plan;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.dao.plan.MtlStcPlanDao;
import com.asiainfo.biapp.mcd.common.vo.plan.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.vo.plan.McdPlanDef;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;

/**
 * 渠道相关Service
 * @author AsiaInfo-jie
 *
 */
@Service("mtlStcPlanService")
public class MtlStcPlanServiceImpl implements IMtlStcPlanService {

    @Resource(name = "mtlStcPlanDao")
    private MtlStcPlanDao mtlStcPlanDao;
 
    /**
     * 根据渠道类型ID获取渠道
     * @param planType
     * @return
     */
    @Override
    public McdDimPlanType getPlanTypeById(String planTypeId) {
        return mtlStcPlanDao.getPlanTypeById(planTypeId);
    }
    
	@Override
	public List<McdDimPlanType> initDimPlanType() {
		try {
			return mtlStcPlanDao.initDimPlanType();
		} catch (Exception e) {
			throw new MpmException("mcd.java.cshzclbsb");
		}
	}
    
	public McdPlanDef getMtlStcPlanByPlanID(String planID){
		return mtlStcPlanDao. getMtlStcPlanByPlanID(planID);
	}
	
	@Override
	public List<McdDimPlanType> getTreeList(){
		 List<McdDimPlanType> allTypes = mtlStcPlanDao.getAll();
		 List<McdDimPlanType> topTypes =  new ArrayList<McdDimPlanType>();
		 for(McdDimPlanType node:allTypes){
			 if("0".equals(node.getTypePid())){
				 topTypes.add(node);
				 this.setSubTypeList(allTypes, node);
			 }
			
		 }
		return topTypes;
	}
	
	private void setSubTypeList(List<McdDimPlanType> allTypes,McdDimPlanType node){
		
		List<McdDimPlanType> childTypes = this.getChildrens(allTypes, node);
		if(childTypes!=null && childTypes.size()>0){
			node.setSubTypes(childTypes);
			for(McdDimPlanType tmp:childTypes){
				setSubTypeList(allTypes, tmp);
			}
		}else{
			node.setSubTypes(childTypes);
		}
	}
	
	private List<McdDimPlanType> getChildrens(List<McdDimPlanType> allTypes,McdDimPlanType node){
		List<McdDimPlanType> subTypes=new ArrayList<>();
		for (int i = 0,size=allTypes.size(); i < size; i++) {
			McdDimPlanType menu=allTypes.get(i);
			if(node.getTypeId().equals(menu.getTypePid())){
				subTypes.add(menu);
			}
		}
		return subTypes;
	}
}
