package com.asiainfo.biapp.mcd.model;

/**
 * MtlChannelDefId generated by MyEclipse - Hibernate Tools
 */

public class MtlChannelDefCallId implements java.io.Serializable {

	// Fields    
	private String campsegId;

	private String channelId;

	// Constructors

	/** default constructor */
	public MtlChannelDefCallId() {
	}

	/** full constructor */
	public MtlChannelDefCallId(String campsegId, String channelId) {
		this.campsegId = campsegId;
		this.channelId = channelId;
	}

	// Property accessors

	public String getCampsegId() {
		return this.campsegId;
	}

	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MtlChannelDefCallId))
			return false;
		MtlChannelDefCallId castOther = (MtlChannelDefCallId) other;

		return ((this.getCampsegId() == castOther.getCampsegId()) || (this.getCampsegId() != null && castOther.getCampsegId() != null && this.getCampsegId().equals(castOther.getCampsegId())))
				&& ((this.getChannelId() == castOther.getChannelId()) || (this.getChannelId() != null && castOther.getChannelId() != null && this.getChannelId().equals(castOther.getChannelId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCampsegId() == null ? 0 : this.getCampsegId().hashCode());
		result = 37 * result + (getChannelId() == null ? 0 : this.getChannelId().hashCode());
		return result;
	}

}
