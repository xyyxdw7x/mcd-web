package com.asiainfo.biapp.mcd.model.index;

public class SaleSituation {
   private long totalNum;/*总营销数*/
   private long totalSuccessNum;/*总成功数*/
   
   private long saleNumMon;
   private long saleSuccessNumMon;
   
   private long saleNumDay;
   private long saleSuccessNumDay;
   
public long getTotalNum() {
	return totalNum;
}
public void setTotalNum(long totalNum) {
	this.totalNum = totalNum;
}
public long getTotalSuccessNum() {
	return totalSuccessNum;
}
public void setTotalSuccessNum(long totalSuccessNum) {
	this.totalSuccessNum = totalSuccessNum;
}
public long getSaleNumMon() {
	return saleNumMon;
}
public void setSaleNumMon(long saleNumMon) {
	this.saleNumMon = saleNumMon;
}
public long getSaleSuccessNumMon() {
	return saleSuccessNumMon;
}
public void setSaleSuccessNumMon(long saleSuccessNumMon) {
	this.saleSuccessNumMon = saleSuccessNumMon;
}
public long getSaleNumDay() {
	return saleNumDay;
}
public void setSaleNumDay(long saleNumDay) {
	this.saleNumDay = saleNumDay;
}
public long getSaleSuccessNumDay() {
	return saleSuccessNumDay;
}
public void setSaleSuccessNumDay(long saleSuccessNumDay) {
	this.saleSuccessNumDay = saleSuccessNumDay;
}
}
