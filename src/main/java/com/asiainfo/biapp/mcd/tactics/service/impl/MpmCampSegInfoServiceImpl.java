package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.io.File;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * Created on 2007-11-9 下午02:00:59
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author
 * @version 1.0
 */
@Repository("mpmCampSegInfoService")
public class MpmCampSegInfoServiceImpl implements IMpmCampSegInfoService {
    private static Logger log = LogManager.getLogger();
    @Resource(name="mpmCampSegInfoDao")
    private IMpmCampSegInfoDao campSegInfoDao;
    
	@Override
	public List searchIMcdCampsegInfo(MtlCampSeginfo segInfo, Pager pager) {
		List list = campSegInfoDao.searchIMcdCampsegInfo(segInfo,pager);
		return list;
	}

    public IMpmCampSegInfoDao getCampSegInfoDao() {
        return campSegInfoDao;
    }

    public void setCampSegInfoDao(IMpmCampSegInfoDao campSegInfoDao) {
        this.campSegInfoDao = campSegInfoDao;
    }
	
}
