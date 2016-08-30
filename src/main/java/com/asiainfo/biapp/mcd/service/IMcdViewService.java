package com.asiainfo.biapp.mcd.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.model.DimAttrClass;
import com.asiainfo.biapp.mcd.model.DimCtrlType;
import com.asiainfo.biapp.mcd.model.McdCvColDefine;
import com.asiainfo.biapp.mcd.model.McdCvDefine;

public interface IMcdViewService {
	public Map<String, Object> search(McdCvDefine condBean, Integer curpage, Integer pagesize) throws MpmException;

	public McdCvDefine findCvDefineById(String id);

	public DimAttrClass findDimAttrClassById(String id);

	public void deleteCV(String id);

	public void deleteCVClassById(String id);

	public void deleteAttrColById(String attrId);

	public void deleteAttrColByMetaId(String id, String cviewId, String attrId);

	public void saveCV(McdCvDefine mcdCvDefine);

	public void saveCVClass(DimAttrClass dimAttrClass);

	public List<McdCvColDefine> findByParentId(String parentId);

	public List<McdCvColDefine> findAllByParentAttrId(String pattrId);

	public List<DimAttrClass> findDimAttrClassByParentId(String parentId, String cviewId);

	public List<DimAttrClass> findDimAttrClassByCviewId(String cviewId);

	public void updateCV(McdCvDefine mcdCvDefine) throws Exception;

	public void updateCVClass(DimAttrClass dimAttrClass) throws Exception;

	public McdCvColDefine findCvColDefineByAttrId(String id);

	public List<McdCvColDefine> findCvColDefineByAttrIdAndCViewId(String attrMetaId, String cViewId);

	public List<McdCvColDefine> findByAttrMetaIdAndDimattrClassId(String attrMetaId, String cViewId, String attrClassId);

	public McdCvColDefine findMcdCvColDefine(String id);

	public void saveOrUpdateCVColDefine(McdCvColDefine mcdCvColDefine);

	public DimCtrlType findDimCtrlType(String ctrlTypeId);

	public McdCvColDefine findByViewIdAndAttrMetaId(String viewId, String attrMetaId);

	public McdCvColDefine findByViewIdAndAttrMetaId(String viewId, String attrMetaId, String attrClassId);

	public boolean ifCViewNameExist(String cviewName);

	public boolean ifCViewClassNameExist(String cviewClassName, String cviewId);

	public List<McdCvColDefine> findColDefByCviewIdAndClassId(String cviewId, String attrClassId, boolean isAsc);

	public McdCvDefine findMcdCvDefine(String id);

	public List<McdCvDefine> findAllCviews();

	public List<McdCvColDefine> getColDefByCviewId(String cviewId);
}
