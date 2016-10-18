package com.asiainfo.biapp.mcd.common.plan.vo;

import javax.persistence.Column;

/**
 * 产品推荐排序属性 
 * 对应的表是MCD_STC_PLAN_ORDER_ATTR
 */
public class McdStcPlanOrderAttr implements java.io.Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5415976853187473872L;
	/**
	 * 产品编号
	 */
	@Column(name="PLAN_ID")
	private String planId;
	/**
	 * 地市ID
	 */
	@Column(name="CITY_ID")
	private String cityId;
	/**
	 * 渠道ID
	 */
	@Column(name="CHANNEL_ID")
	private String channelId;
	/**
	 * 酬金 2位有效小数
	 */
	@Column(name="PLAN_REWARD")
	private double planReward;
	/**
	 * 酬金级别  1:高 0:低'
	 */
	@Column(name="PLAN_REWARD_LEVEL")
	private int planRewardLevel;
	/**
	 * 是否考核指标
	 */
	@Column(name="IS_CHECK")
	private short isCheck;
	/**
	 * 最终排序
	 */
	@Column(name="ORDER_NUM")
	private int orderNum;
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public double getPlanReward() {
		return planReward;
	}
	public void setPlanReward(double planReward) {
		this.planReward = planReward;
	}
	public int getPlanRewardLevel() {
		return planRewardLevel;
	}
	public void setPlanRewardLevel(int planRewardLevel) {
		this.planRewardLevel = planRewardLevel;
	}
	public short getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(short isCheck) {
		this.isCheck = isCheck;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channelId == null) ? 0 : channelId.hashCode());
		result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
		result = prime * result + isCheck;
		result = prime * result + orderNum;
		result = prime * result + ((planId == null) ? 0 : planId.hashCode());
		long temp;
		temp = Double.doubleToLongBits(planReward);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + planRewardLevel;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		McdStcPlanOrderAttr other = (McdStcPlanOrderAttr) obj;
		if (channelId == null) {
			if (other.channelId != null)
				return false;
		} else if (!channelId.equals(other.channelId))
			return false;
		if (cityId == null) {
			if (other.cityId != null)
				return false;
		} else if (!cityId.equals(other.cityId))
			return false;
		if (isCheck != other.isCheck)
			return false;
		if (orderNum != other.orderNum)
			return false;
		if (planId == null) {
			if (other.planId != null)
				return false;
		} else if (!planId.equals(other.planId))
			return false;
		if (Double.doubleToLongBits(planReward) != Double.doubleToLongBits(other.planReward))
			return false;
		if (planRewardLevel != other.planRewardLevel)
			return false;
		return true;
	}
	public McdStcPlanOrderAttr() {
		super();
	}

}