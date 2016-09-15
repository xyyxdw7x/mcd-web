package com.asiainfo.biapp.mcd.common.vo.channel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * DimMtlChannel entity.
 *
 * @author MyEclipse Persistence Tools
 */
@Table(name="mcd_dim_channel")
public class McdDimChannel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -81148059180044511L;
	
	@Column(name="channel_id")
	private String channelId;
	@Column(name="channel_name")
	private String channelName;
	@Column(name="display_order")
    private Integer displayOrder;
	
	private String campId;
//  IMCD_ZJ 为了统一前台模板使用  起别名
    private String typeId;
    private String typeName;
    

	public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCampId() {
		return campId;
	}

	public void setCampId(String campId) {
		this.campId = campId;
	}
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}


	
	
	/** default constructor */
	public McdDimChannel() {
	}

	/** minimal constructor */
	public McdDimChannel(String channelId, String channelName) {
		this.channelId = channelId;
		this.channelName = channelName;
	}


}
