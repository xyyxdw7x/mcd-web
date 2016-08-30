package com.asiainfo.biapp.mcd.jms.beans;

public class ContactCtrlUserBean {
	private String user_code;// NOPMD by wanglei on 13-6-11 4:18
	private int user_contact_flag;// NOPMD by wanglei on 13-6-11 4:18
	private int user_contact_count;// NOPMD by wanglei on 13-6-11 4:18

	public ContactCtrlUserBean() {
		super();
	}

	public ContactCtrlUserBean(String user_code, int user_contact_flag, int user_contact_count) {
		super();
		this.user_code = user_code;
		this.user_contact_flag = user_contact_flag;
		this.user_contact_count = user_contact_count;
	}

	public String getUser_code() {// NOPMD by wanglei on 13-6-11 4:18
		return user_code;
	}

	public void setUser_code(String user_code) {// NOPMD by wanglei on 13-6-11 4:18
		this.user_code = user_code;
	}

	public int getUser_contact_flag() {// NOPMD by wanglei on 13-6-11 4:18
		return user_contact_flag;
	}

	public void setUser_contact_flag(int user_contact_flag) {// NOPMD by wanglei on 13-6-11 4:18
		this.user_contact_flag = user_contact_flag;
	}

	public int getUser_contact_count() {// NOPMD by wanglei on 13-6-11 4:18
		return user_contact_count;
	}

	public void setUser_contact_count(int user_contact_count) {// NOPMD by wanglei on 13-6-11 4:18
		this.user_contact_count = user_contact_count;
	}

}
