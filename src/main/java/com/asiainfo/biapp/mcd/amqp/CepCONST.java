package com.asiainfo.biapp.mcd.amqp;

public class CepCONST {

	//前端与外部系统交互交换机
	public static final String EXCHANGE_DIRECT_CTRLDATA = "controldata.direct";
	//CEP监听队列名称
	public static final String ROUTE_EPL_EXTERNAL_REQ = "com.ailk.bdx.cep.epl_external_req";

	//MCD收取CEP反馈数据
	public static final String EXCHANGE_DIRECT_EVENTDATA = "eventdata.direct";
	//MCD接收CEP反馈数据路由前缀
	public static final String ROUTE_EPL_RES_PRFIFX = "com.ailk.bdx.cep.esper.result_";

	//获取需要监听的活动
	public static final String QUERY_CEP_EVENT_INIT_SQL_ORACLE = "select campseg_id, cep_event_id from mtl_camp_seginfo where cep_event_id is not null  and  campseg_stat_id in (54,59)";
	
	public static final String QUERY_CEP_EVENT_INIT_SQL = "select campseg_id, cep_event_id from mtl_camp_seginfo where cep_event_id is not null and  campseg_stat_id in (54,59)";
	public static final String QUERY_MCD_CAMPSEG_TASK_SQL = "select count(1) from mcd_campseg_task_{year} where exec_time >= ? and exec_time <= ? and campseg_id = ?";
	public static final String QUERY_MCD_CAMPSEG_TASK_SQL_ORACLE = "select count(1) from mcd_campseg_task_{year} where exec_time >= to_date('{beginDate}','yyyy-mm-dd hh24:mi:ss') and exec_time <= to_date('{endDate}','yyyy-mm-dd hh24:mi:ss') and campseg_id = ?";
}
