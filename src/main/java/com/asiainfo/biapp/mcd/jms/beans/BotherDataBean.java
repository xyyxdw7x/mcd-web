package com.asiainfo.biapp.mcd.jms.beans;

/**
 * @author dengshu
 *
 */
public class BotherDataBean {
	//控制级别1-系统�?-活动�?
	private int control_level;// NOPMD by wanglei on 13-6-11 4:18
	//营销活动编号（活动级接触控制为必填项�?
	private String activity_code;// NOPMD by wanglei on 13-6-11 4:18
	//用户号码
	private String user_code;// NOPMD by wanglei on 13-6-11 4:18
	//免打扰控制类�?1-�??免打�?2-取消免打�?
	private int control_type;// NOPMD by wanglei on 13-6-11 4:18
	//sql执行类型
	private int exeSqlType;// NOPMD by wanglei on 13-6-11 4:18
	//插入活动级别免打�?
	public static final int SQL_INSERT_BOTHER_ACTIVITY = 0;
	//插入全局免打�?
	public static final int SQL_INSERT_BOTHER = 1;
	//删除活动免打�?
	public static final int SQL_DELETE_BOTHER_ACTIVITY = 2;
	//删除全局免打�?
	public static final int SQL_DELETE_BOTHER = 3;
	//1-�??免打�?
	public static final int BOTHER_CONFIRM = 1;
	//2-取消免打�?
	public static final int BOTHER_CANCEL = 2;
	//-系统�?
	public static final int LEVEL_SYSTEM = 1;
	//2-活动�?
	public static final int LEVEL_ACTIVITY = 2;
	
	public int getExeSqlType() {
		return exeSqlType;
	}
	public void setExeSqlType(int exeSqlType) {
		this.exeSqlType = exeSqlType;
	}
	public int getControl_level() {// NOPMD by wanglei on 13-6-11 4:18
		return control_level;
	}
	public void setControl_level(int controlLevel) {// NOPMD by wanglei on 13-6-11 4:18
		control_level = controlLevel;
	}
	public String getActivity_code() { // NOPMD by wanglei on 13-6-11 4:18
		return activity_code;
	}
	public void setActivity_code(String activityCode) {// NOPMD by wanglei on 13-6-11 4:18
		activity_code = activityCode;
	}
	public String getUser_code() {// NOPMD by wanglei on 13-6-11 4:18
		return user_code;
	}
	public void setUser_code(String userCode) {// NOPMD by wanglei on 13-6-11 4:18
		user_code = userCode;
	}
	public int getControl_type() {// NOPMD by wanglei on 13-6-11 4:18
		return control_type;
	}
	public void setControl_type(int controlType) {// NOPMD by wanglei on 13-6-11 4:18
		control_type = controlType;
	}
	
	
}
