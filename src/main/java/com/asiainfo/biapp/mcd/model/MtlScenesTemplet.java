package com.asiainfo.biapp.mcd.model;

/**
 * MtlScenesTemplet generated by MyEclipse Persistence Tools
 */

public class MtlScenesTemplet implements java.io.Serializable {

	// Fields    

	private String scenesId;

	private Short campDrvId;

	private String scenesName;
	
	private Integer dcpPageId;

	// Constructors

	/** default constructor */
	public MtlScenesTemplet() {
	}

	/** full constructor */
	public MtlScenesTemplet(Short campDrvId, String scenesName) {
		this.campDrvId = campDrvId;
		this.scenesName = scenesName;
	}

	// Property accessors

	public String getScenesId() {
		return this.scenesId;
	}

	public Integer getDcpPageId() {
		return dcpPageId;
	}

	public void setDcpPageId(Integer dcpPageId) {
		this.dcpPageId = dcpPageId;
	}

	public void setScenesId(String scenesId) {
		this.scenesId = scenesId;
	}

	public Short getCampDrvId() {
		return this.campDrvId;
	}

	public void setCampDrvId(Short campDrvId) {
		this.campDrvId = campDrvId;
	}

	public String getScenesName() {
		return this.scenesName;
	}

	public void setScenesName(String scenesName) {
		this.scenesName = scenesName;
	}

}
