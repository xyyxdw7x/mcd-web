package com.asiainfo.biapp.mcd.tactics.vo;

/**
 * MtlChannelDefId generated by MyEclipse - Hibernate Tools
 */

public class MtlChannelDefId implements java.io.Serializable {

	// Fields    

	private Short usersegId;

	private String campsegId;

	private Integer channelNo;

	// Constructors

	/** default constructor */
	public MtlChannelDefId() {
	}

	/** full constructor */
	public MtlChannelDefId(Short usersegId, String campsegId, Integer channelNo) {
		this.usersegId = usersegId;
		this.campsegId = campsegId;
		this.channelNo = channelNo;
	}

	// Property accessors

	public Short getUsersegId() {
		return this.usersegId;
	}

	public void setUsersegId(Short usersegId) {
		this.usersegId = usersegId;
	}

	public String getCampsegId() {
		return this.campsegId;
	}

	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}

	public Integer getChannelNo() {
		return this.channelNo;
	}

	public void setChannelNo(Integer channelNo) {
		this.channelNo = channelNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MtlChannelDefId))
			return false;
		MtlChannelDefId castOther = (MtlChannelDefId) other;

		return ((this.getUsersegId() == castOther.getUsersegId()) || (this.getUsersegId() != null && castOther.getUsersegId() != null && this.getUsersegId().equals(castOther.getUsersegId())))
				&& ((this.getCampsegId() == castOther.getCampsegId()) || (this.getCampsegId() != null && castOther.getCampsegId() != null && this.getCampsegId().equals(castOther.getCampsegId())))
				&& ((this.getChannelNo() == castOther.getChannelNo()) || (this.getChannelNo() != null && castOther.getChannelNo() != null && this.getChannelNo().equals(castOther.getChannelNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUsersegId() == null ? 0 : this.getUsersegId().hashCode());
		result = 37 * result + (getCampsegId() == null ? 0 : this.getCampsegId().hashCode());
		result = 37 * result + (getChannelNo() == null ? 0 : this.getChannelNo().hashCode());
		return result;
	}

}
