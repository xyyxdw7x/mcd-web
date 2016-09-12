package com.asiainfo.biapp.mcd.bull.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.model.McdCampsegTask;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;

public interface Task4BullDao {

	public void updateCampPri(final List<McdCampDef> campsegs);

	public void batchUpdate(final List<McdCampsegTask> list);

}
