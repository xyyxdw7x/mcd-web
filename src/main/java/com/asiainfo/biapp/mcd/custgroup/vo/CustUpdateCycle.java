package com.asiainfo.biapp.mcd.custgroup.vo;

/**
 * 客户群周期类型
 * @author zhuyq3
 *
 */
public enum CustUpdateCycle {

	ON_OFF((short) 1, "一次性"), 
	MONTH((short) 2, "月周期"), 
	DAY((short) 3, "日周期");
	
	private final short value;
	private final String name;
	
	public short getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	CustUpdateCycle(short value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public static String getName(short value) {
        for (CustUpdateCycle t : CustUpdateCycle.values()) {
            if (t.getValue() == value) {
                return t.name;
            }
        }
        return null;
    }
	
	public static void main(String[] args) {
		System.out.println(CustUpdateCycle.getName((short)2));
		System.out.println(CustUpdateCycle.getName((short)3));
		System.out.println(CustUpdateCycle.getName((short)1));
	}

}
