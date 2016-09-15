package  com.asiainfo.biapp.mcd.common.service;

import java.util.List;

import com.asiainfo.biapp.mcd.custgroup.vo.McdCvColDefine;

public interface IMpmCommonService {


	
	/**
	 * add by lixq10  IMCD_ZJ 新建策略页面视图预定义配置
	 * pAttrClassId:classId
	 * keyWords:g关键字  只有关键字查询的时候才传递参数值
	 * @return
	 */
	public List<McdCvColDefine> initCvColDefine(String pAttrClassId,String keyWords);
	
	public void insertCustGroupDataBySqlldr(String custGroupId, String tableName, String customGroupName, String date)throws Exception;
	

	
}
