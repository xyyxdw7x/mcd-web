package com.asiainfo.biapp.mcd.model;

/**
 * DimAvoidBother generated by MyEclipse - Hibernate Tools
 */

public class DimAvoidBotherId implements java.io.Serializable {

	private Short avoidBotherId;
	private Short avoidBotherType;
	

	// Constructors

	/** default constructor */
	public DimAvoidBotherId() {
	}

	/** minimal constructor */
	public DimAvoidBotherId(Short avoidBotherId,Short avoidBotherType) {
		this.avoidBotherId = avoidBotherId;
		this.avoidBotherType = avoidBotherType;
	}

	public Short getAvoidBotherId() {
		return avoidBotherId;
	}

	public void setAvoidBotherId(Short avoidBotherId) {
		this.avoidBotherId = avoidBotherId;
	}

	public Short getAvoidBotherType() {
		return avoidBotherType;
	}

	public void setAvoidBotherType(Short avoidBotherType) {
		this.avoidBotherType = avoidBotherType;
	}



	

}