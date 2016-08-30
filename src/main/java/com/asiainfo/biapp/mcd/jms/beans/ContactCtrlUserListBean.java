package com.asiainfo.biapp.mcd.jms.beans;

import java.util.List;

/**
 * 接触控制用户数据
 * @author zhangyb5
 *
 */
public class ContactCtrlUserListBean {
	private String activity_code;// NOPMD by wanglei on 13-6-11 4:18
	private String channel_type;// NOPMD by wanglei on 13-6-11 4:18
	private String business_channel_type;// NOPMD by wanglei on 13-6-11 4:18
	private List<ContactCtrlUserBean> user_list;// NOPMD by wanglei on 13-6-11 4:18

	public ContactCtrlUserListBean() {
		super();
	}

	public ContactCtrlUserListBean(String activity_code, String channel_type, String business_channel_type,
			List<ContactCtrlUserBean> user_list) {
		super();
		this.activity_code = activity_code;
		this.channel_type = channel_type;
		this.business_channel_type = business_channel_type;
		this.user_list = user_list;
	}

	public String getActivity_code() {// NOPMD by wanglei on 13-6-11 4:18
		return activity_code;
	}

	public void setActivity_code(String activity_code) {// NOPMD by wanglei on 13-6-11 4:18
		this.activity_code = activity_code;
	}

	public String getChannel_type() {// NOPMD by wanglei on 13-6-11 4:18
		return channel_type;
	}

	public void setChannel_type(String channel_type) {// NOPMD by wanglei on 13-6-11 4:18
		this.channel_type = channel_type;
	}

	public String getBusiness_channel_type() {// NOPMD by wanglei on 13-6-11 4:18
		return business_channel_type;
	}

	public void setBusiness_channel_type(String business_channel_type) {// NOPMD by wanglei on 13-6-11 4:18
		this.business_channel_type = business_channel_type;
	}

	public List<ContactCtrlUserBean> getUser_list() {// NOPMD by wanglei on 13-6-11 4:18
		return user_list;
	}

	public void setUser_list(List<ContactCtrlUserBean> user_list) {// NOPMD by wanglei on 13-6-11 4:18
		this.user_list = user_list;
	}

}
