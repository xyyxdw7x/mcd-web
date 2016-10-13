package com.asiainfo.biapp.mcd.home.dao;

import java.util.List;
import java.util.Map;

public interface ICepKeywordsDao {

	List<Map<String, Object>> queryData4Charts(String string, Object[] objects);

}
