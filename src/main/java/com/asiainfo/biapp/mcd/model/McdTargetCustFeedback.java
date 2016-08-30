package com.asiainfo.biapp.mcd.model;

/**
 * 目标客户群来源于其他活动反馈的数据
 * @author lixiangqian 20141024
 *
 */
public class McdTargetCustFeedback implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *内部标示
	 */
	private String id;

	/**
	 * 当前活动id
	 */
	private String campsegId;

	/**
	 * 其他活动id
	 */
	private String campsegOtherId;

	/**
	 * 选择的反馈状态条件
	 */
	private String feedCondition;

	public McdTargetCustFeedback(String id, String campsegId, String campsegOtherId, String feedCondition) {
		super();
		this.id = id;
		this.campsegId = campsegId;
		this.campsegOtherId = campsegOtherId;
		this.feedCondition = feedCondition;
	}

	public McdTargetCustFeedback() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 当前活动id
	 * @return
	 */
	public String getCampsegId() {
		return campsegId;
	}

	/**
	 * 当前活动id
	 * @param campsegId 
	 */
	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}

	/**
	 *  其他活动id
	 * @return
	 */
	public String getCampsegOtherId() {
		return campsegOtherId;
	}

	/**
	 *  其他活动id
	 * @param campsegOtherId
	 */
	public void setCampsegOtherId(String campsegOtherId) {
		this.campsegOtherId = campsegOtherId;
	}

	/**
	 * 选择的反馈状态条件
	 * @return
	 */
	public String getFeedCondition() {
		return feedCondition;
	}

	/**
	 * 选择的反馈状态条件
	 * @param feedCondition
	 */
	public void setFeedCondition(String feedCondition) {
		this.feedCondition = feedCondition;
	}

	@Override
	public String toString() {
		return "McdTargetCustFeedback [id=" + id + ", campsegId=" + campsegId + ", campsegOtherId=" + campsegOtherId
				+ ", feedCondition=" + feedCondition + "]";
	}
}
