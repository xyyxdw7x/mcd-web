package com.asiainfo.biapp.mcd.jms.beans;

/**
 * 接触控制处理结果
 * @author zhangyb5
 *
 */
public class ContactControlResult {
	private boolean match;//是否匹配
	private int contactType;//接触类型：1=用户级，2=活动级
	private int persistType;//持久化类型：1=新增；2=更新

	public boolean isMatch() {
		return match;
	}

	public void setMatch(boolean match) {
		this.match = match;
	}

	public int getContactType() {
		return contactType;
	}

	public void setContactType(int contactType) {
		this.contactType = contactType;
	}

	public int getPersistType() {
		return persistType;
	}

	public void setPersistType(int persistType) {
		this.persistType = persistType;
	}

}
