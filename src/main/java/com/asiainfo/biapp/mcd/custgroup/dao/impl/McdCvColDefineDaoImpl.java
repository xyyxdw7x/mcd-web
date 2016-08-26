package com.asiainfo.biapp.mcd.custgroup.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.custgroup.dao.IMcdCvColDefineDao;
import com.asiainfo.biapp.mcd.custgroup.model.McdCvColDefine;
import com.asiainfo.biframe.utils.string.StringUtil;

/**
 * The Class McdCvColDefineDaoImpl.
 */
@Repository("mcdCvColDefineDao")
public class McdCvColDefineDaoImpl extends JdbcDaoBase  implements IMcdCvColDefineDao {
	@Override
	public List<McdCvColDefine> initCvColDefine(String pAttrClassId,String keyWords) {
		List<Map<String,Object>> list = null;
		List<McdCvColDefine> resultList = new ArrayList<McdCvColDefine>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT MCD_CV_COL_DEFINE.ATTR_ID,MCD_CV_COL_DEFINE.ATTR_ALIAS,MCD_CV_COL_DEFINE.CVIEW_ID,MCD_CV_COL_DEFINE.ATTR_CLASS_ID,MCD_CV_COL_DEFINE.CTRL_TYPE_ID,DIM_ATTR_CLASS.ATTR_CLASS_NAME,")
				  .append(" MCD_CV_COL_DEFINE.ATTR_META_ID,MDA_SYS_TABLE_COLUMN.LABEL_ID,MDA_SYS_TABLE_COLUMN.COLUMN_DATA_TYPE_ID,DIM_MTL_LABEL_INFO.BUSI_CALIBER,DIM_MTL_LABEL_INFO.UPDATE_CYCLE,DIM_MTL_LABEL_INFO.DATA_DATE")
				  .append(" FROM MCD_CV_COL_DEFINE ")
				  .append(" LEFT JOIN DIM_ATTR_CLASS ON MCD_CV_COL_DEFINE.ATTR_CLASS_ID = DIM_ATTR_CLASS.ATTR_CLASS_ID")
				  .append(" LEFT JOIN MDA_SYS_TABLE_COLUMN ON MCD_CV_COL_DEFINE.ATTR_META_ID = MDA_SYS_TABLE_COLUMN.COLUMN_ID")
				  .append(" LEFT JOIN DIM_MTL_LABEL_INFO ON DIM_MTL_LABEL_INFO.LABEL_ID = MDA_SYS_TABLE_COLUMN.LABEL_ID")
				  .append(" WHERE DIM_ATTR_CLASS.P_ATTR_CLASS_ID =?");
			if(StringUtil.isNotEmpty(keyWords)){
				  buffer.append(" AND MCD_CV_COL_DEFINE.ATTR_ALIAS like '%?%'");
			}
			if(StringUtil.isNotEmpty(keyWords)){
				list = this.getJdbcTemplate().queryForList(buffer.toString(),new Object[] { pAttrClassId,keyWords });
			}else {
				list = this.getJdbcTemplate().queryForList(buffer.toString(),new Object[] { pAttrClassId });
			}
			for (Map map : list) {
				McdCvColDefine mcdCvColDefine = new McdCvColDefine();
				mcdCvColDefine.setAttrId((String) map.get("ATTR_ID"));
				mcdCvColDefine.setAttrAlias((String) map.get("ATTR_ALIAS"));
				mcdCvColDefine.setAttrClassId((String) map.get("ATTR_CLASS_ID"));
				mcdCvColDefine.setcViewId((String) map.get("CVIEW_ID"));
				mcdCvColDefine.setCtrlTypeId((String) map.get("CTRL_TYPE_ID"));
				mcdCvColDefine.setAttrClassName((String) map.get("ATTR_CLASS_NAME"));
				mcdCvColDefine.setAttrMetaId((String) map.get("ATTR_META_ID"));
				mcdCvColDefine.setColumnDataType(Integer.parseInt(String.valueOf(map.get("COLUMN_DATA_TYPE_ID"))));
				mcdCvColDefine.setLableId(0);
				
				if(null != map.get("LABEL_ID")){
					mcdCvColDefine.setLableId(Integer.parseInt(String.valueOf(map.get("LABEL_ID"))));
				}
				mcdCvColDefine.setBusiCuliber((String) map.get("BUSI_CALIBER"));
				mcdCvColDefine.setUpdateCycle(1);
				if(null != map.get("UPDATE_CYCLE")){
					mcdCvColDefine.setUpdateCycle(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))));
				}
				mcdCvColDefine.setDataDate((String) map.get("DATA_DATE"));
				
				//前台模板统一使用一下名字
				mcdCvColDefine.setTypeId((String) map.get("ATTR_ID"));
				mcdCvColDefine.setTypeName((String) map.get("ATTR_ALIAS"));
				resultList.add(mcdCvColDefine);
			}
		} catch (Exception e) {
		}
		return resultList;
	}
	
}
