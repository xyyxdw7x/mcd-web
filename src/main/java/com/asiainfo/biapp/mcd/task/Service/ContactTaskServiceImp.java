package com.asiainfo.biapp.mcd.task.Service;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.task.dao.MtlContactRecentLogDao;
@Repository("contactTaskService")
public class ContactTaskServiceImp implements  ContactTaskService{

	private MtlContactRecentLogDao mtlContactRecentLogDao;

	public void setMtlContactRecentLogDao(MtlContactRecentLogDao mtlContactRecentLogDao) {
		this.mtlContactRecentLogDao = mtlContactRecentLogDao;
	}

	@Override
	public void execTaskDayContact() {
		String day = QuotaUtils.getBeforeDate(100, "yyyyMMdd");
		mtlContactRecentLogDao.updateInMemBatchDeleteContact(day);
		mtlContactRecentLogDao.updateInMemBatchDeleteContactActivity(day);
	}
}
