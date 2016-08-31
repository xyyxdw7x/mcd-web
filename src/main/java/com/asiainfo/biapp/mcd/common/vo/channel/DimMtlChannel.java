package com.asiainfo.biapp.mcd.common.vo.channel;

/**
 * DimMtlChannel entity.
 *
 * @author MyEclipse Persistence Tools
 */

public class DimMtlChannel implements java.io.Serializable {

	// Fields

	private String channelId;

	private Short channeltypeId;

	private String channelName;

	private String campId;
	//	private String cityId;
	private String createUser; //渠道创建人,空代表共有渠道
	// Constructors

	public String getCampId() {
		return campId;
	}

	public void setCampId(String campId) {
		this.campId = campId;
	}

	/** default constructor */
	public DimMtlChannel() {
	}

	/** minimal constructor */
	public DimMtlChannel(String channelId, String channelName) {
		this.channelId = channelId;
		this.channelName = channelName;
	}

	/** full constructor */
	public DimMtlChannel(String channelId, Short channeltypeId, String channelName) {
		this.channelId = channelId;
		this.channeltypeId = channeltypeId;
		this.channelName = channelName;
	}

	// Property accessors

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Short getChanneltypeId() {
		return channeltypeId;
	}

	public void setChanneltypeId(Short channeltypeId) {
		this.channeltypeId = channeltypeId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	//	public String getCityId() {
	//		return cityId;
	//	}
	//
	//	public void setCityId(String cityId) {
	//		this.cityId = cityId;
	//	}

}
