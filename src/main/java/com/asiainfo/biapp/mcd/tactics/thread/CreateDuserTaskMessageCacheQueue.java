package com.asiainfo.biapp.mcd.tactics.thread;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CreateDuserTaskMessageCacheQueue {
	/** 保证单例 */
	static class EventBeanCacheQueueHolder {
		//待创建D表的任务队列
		static ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<String>();
	}
	
	//待创建D表的任务队列
	public static ConcurrentLinkedQueue<String> getMessageQueue(){
		return EventBeanCacheQueueHolder.messageQueue;
	}
	
	//待创建D表的任务队列
	public static boolean  putMessageQueue(String str){
		return getMessageQueue().offer(str);
	}
	
}
