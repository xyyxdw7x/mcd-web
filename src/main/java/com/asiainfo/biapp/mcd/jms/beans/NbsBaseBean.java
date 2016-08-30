package com.asiainfo.biapp.mcd.jms.beans;

import java.io.Serializable;

/**
 * 基本信息
 * @author zhangyb5
 *
 */
public class NbsBaseBean implements Serializable {
	private static final long serialVersionUID = 6109840834661356957L;
	private String activity_code;//营销活动编号
	private String event_start_time;//营销活动开始时间（日期）
	private String event_end_time;//营销活动结束时间（日期）
	private int event_id;//事件编号
	private int event_type;//事件类型
	private long control_field;//控制字段。预留64位，从低位到高位，每一位表示后续一个参数的控制情况，0：表示“对应参数无需控制”，1：表示“对应参数需要控制”；该字段用于对后续参数的核查
	private long control_file;//文件控制字段。预留64位，下面的字段从低位到高位，每一位表示后续一个参数的控制情况，0：表示“对应参数直接匹配”，1：表示“对应参数通过文件匹配”；1.0支持范围是位置（site_id）和用户账户（user_account）的文件匹配 ，该字段用于从第8个字段起对后续每个对应参数的控制
	private String user_account;//用户号码

	public NbsBaseBean() {

	}

	public NbsBaseBean(String activity_code, String event_start_time, String event_end_time, int event_id,
			int event_type, int control_field, String user_account, long control_file) {
		super();
		this.activity_code = activity_code;
		this.event_start_time = event_start_time;
		this.event_end_time = event_end_time;
		this.event_id = event_id;
		this.event_type = event_type;
		this.control_field = control_field;
		this.user_account = user_account;
		this.control_file = control_file;
	}

	public long getControl_file() {
		return control_file;
	}

	public void setControl_file(long control_file) {
		this.control_file = control_file;
	}

	public String getActivity_code() {
		return activity_code;
	}

	public void setActivity_code(String activity_code) {
		this.activity_code = activity_code;
	}

	public String getEvent_start_time() {
		return event_start_time;
	}

	public void setEvent_start_time(String event_start_time) {
		this.event_start_time = event_start_time;
	}

	public String getEvent_end_time() {
		return event_end_time;
	}

	public void setEvent_end_time(String event_end_time) {
		this.event_end_time = event_end_time;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public int getEvent_type() {
		return event_type;
	}

	public void setEvent_type(int event_type) {
		this.event_type = event_type;
	}

	public long getControl_field() {
		return control_field;
	}

	public void setControl_field(long control_field) {
		this.control_field = control_field;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

}
