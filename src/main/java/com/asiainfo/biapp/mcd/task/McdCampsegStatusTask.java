package com.asiainfo.biapp.mcd.task;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.biapp.mcd.task.Service.CampStatuTaskService;

public class McdCampsegStatusTask   implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3676374959211287308L;
	@Resource(name="campStatuTaskService")
	private CampStatuTaskService campStatuTaskService;
	
	private final Log log = LogFactory.getLog(getClass());

	public void doTask(){
		
		try {
			log.info("=======进入campStatus定时任务=========");
			this.campStatuTaskService.doCampTask();
		} catch (Exception e) {
			log.info("McdCampsegStatusTask执行出现错误...，错误原因："+ e.getMessage());
			e.printStackTrace();
		}
	}
}
