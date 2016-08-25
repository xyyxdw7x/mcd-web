package com.asiainfo.biapp.mcd.common.constants;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.biframe.utils.config.Configure;

/**
 * Created on 2005-6-8
 *
 * <p>Title: </p>
 * <p>Description:营销管理系统常量定义�?/p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */

public class MpmCONST {

    /**
     * MCD_ZJ 分页参数
     */
    public static final int pageNum = 1;
    public final static int PAGE_SIZE = 10;  
    
	public static final String MPM_CAMPSEG_STAT_HDSP = "40";//活动审批�? （浙江--审批中）
	public static final String MPM_CAMPSEG_STAT_CHZT = "20";//策划状�?  （浙江--草稿）

	public static final String REALTIME_DRV_TYPE = "-8";
	public static final String NORMAL_DRV_TYPE = "-12";
	//活动场景类型
	public static final String SCENE_TYPE = "scene_type";
	public static final String SCENE_TYPE_REALTIME = "scene_type11";
	public static final String SCENE_TYPE_NORMAL = "scene_type12";
	
	public static final String MEMCACHE_TYPE_DISTRIBUTED = "distributed";//分布式
	
	/**复杂事件规则创建*/
	public static final String AIBI_MCD_CEP_CREATE_CODE = "AIBI_CEP_CREATE_CODE";
	/**复杂事件规则创建回调页面*/
	public static final String AIBI_MCD_CEP_CREATE_CODE_CALLBACK = "AIBI_CEP_CREATE_CODE_CALLBACK";

	/**
	 * 新建策略页面 选择政策翻页每页显示5条数据
	 */
	public final static int SMALL_PAGE_SIZE_LABEL = 5;
}
