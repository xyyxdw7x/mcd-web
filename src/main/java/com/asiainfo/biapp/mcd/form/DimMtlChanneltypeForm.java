package com.asiainfo.biapp.mcd.form;


/**
 * 
 * Created on 2008-3-7
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author qixf
 * @version 1.0
 */
public class DimMtlChanneltypeForm extends SysBaseForm {
	private Short channeltypeId;

	private String channeltypeName;

	private Short activeFlag;

	/**
	 * @return activeFlag
	 */
	public Short getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag ~o activeFlag
	 */
	public void setActiveFlag(Short activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return channeltypeId
	 */
	public Short getChanneltypeId() {
		return channeltypeId;
	}

	/**
	 * @param channeltypeId ~o channeltypeId
	 */
	public void setChanneltypeId(Short channeltypeId) {
		this.channeltypeId = channeltypeId;
	}

	/**
	 * @return channeltypeName
	 */
	public String getChanneltypeName() {
		return channeltypeName;
	}

	/**
	 * @param channeltypeName ~o channeltypeName
	 */
	public void setChanneltypeName(String channeltypeName) {
		this.channeltypeName = channeltypeName;
	}

	public DimMtlChanneltypeForm() {

	}
}
