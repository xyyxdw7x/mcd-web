package com.asiainfo.biapp.mcd.common.constants;


public enum TasKStatus {

	TASK_STATUS_UNDO((short) 50, "等待中"), 
	TASK_STATUS_RUNNING((short) 51, "发送中"), 
	TASK_STATUS_PAUSE((short) 59, "暂停"),
	TASK_STATUS_LOADED((short) 70, "等待中"),
	TASK_STATUS_AUTOMATIC_PAUSE((short) 71, "暂停（配额不足）"),
	TASK_STATUS_DAY_END((short) 79, "已完成");
	
	private final short value;
	private final String name;
	
	public short getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	TasKStatus(short value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public static String getName(short value) {
        for (TasKStatus t : TasKStatus.values()) {
            if (t.getValue() == value) {
                return t.name;
            }
        }
        return null;
    }
	
	public static void main(String[] args) {
		System.out.println(TasKStatus.getName((short)59));
	}

}
