package com.asiainfo.biapp.mcd.common.service.plan;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.dao.plan.McdDimPlanTypeDao;
import com.asiainfo.biapp.mcd.common.vo.plan.McdDimPlanType;
@Service("mcdDimPlanTypeService")
public class McdDimPlanTypeServiceImpl implements McdDimPlanTypeService{
	@Resource(name="mcdDimPlanTypeDao")
	private McdDimPlanTypeDao mcdDimPlanTypeDao;
	@Override
	public List<McdDimPlanType> getTreeList(){
		 List<McdDimPlanType> allTypes = mcdDimPlanTypeDao.getAll();
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
