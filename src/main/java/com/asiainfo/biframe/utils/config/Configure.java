package com.asiainfo.biframe.utils.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.biframe.utils.string.StringUtil;

public class Configure {
	public static final String URL_PATH = "URL_PATH";
	private static Logger log = LogManager.getLogger(Configure.class);
	private static final Configure CONFIGURE = new Configure();
	private static final String DEFAULT_CONFIG_TYPE = "ASIAINFO_PROPERTIES";
	private static Map modifiedTimeMap = new HashMap();
	private static Map fileNameMap = new HashMap();
	private static Map absPathMap = new HashMap();
	private static Map configMap = new HashMap();

	private Configure() {
	}

	public static Configure getInstance() {
		return CONFIGURE;
	}

	/**
	 * @deprecated Method setConfFileName is deprecated
	 */
	@Deprecated
	public void setConfFileName(String fileName) throws Exception {
		initProperties("ASIAINFO_PROPERTIES", fileName);
	}

	public void addConfFileName(String configType, String fileName) throws Exception {
		initProperties(configType, fileName);
	}

	public String getProperty(String strKey) {
		try {
			return getProperty("ASIAINFO_PROPERTIES", strKey);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	public String getProperty(String configType, String strKey) throws Exception {
		if (StringUtil.isEmpty(configType)) {
			throw new Exception("----Configure--err-------:configType is null");
		}
		try {
			if (configMap.get(configType) == null) {
				throw new Exception((new StringBuilder()).append("----Configure--err-------:configType[")
						.append(configType).append("]is not initialized").toString());
			}
			File fileObj = new File((String) fileNameMap.get(configType));
			if (fileObj.lastModified() > ((Long) modifiedTimeMap.get(configType)).longValue()) {
				initProperties(configType, (String) fileNameMap.get(configType));
			}
			Properties properties = (Properties) configMap.get(configType);
			return StringUtil.obj2Str(properties.getProperty(strKey));
		} catch (Exception excep) {
			log.error("", excep);
		}
		return "";
	}

	public String getWebUrl(String key) throws Exception {
		return getProperty("URL_PATH", key);
	}

	public Map getWebUrlMap() {
		Map map = new HashMap();
		Properties props = (Properties) configMap.get("URL_PATH");
		Enumeration enums = props.propertyNames();
		String name = null;
		for (; enums.hasMoreElements(); map.put(name, props.getProperty(name, ""))) {
			name = (String) enums.nextElement();
		}

		return map;
	}

	private synchronized boolean initProperties(String configType, String fileName) throws Exception {
		if (StringUtil.isEmpty(configType)) {
			throw new Exception("----Configure--err-------:configType is null");
		}
		if (StringUtil.isEmpty(fileName)) {
			throw new Exception("----Configure--err-------:fileName is null");
		}
		Properties props = new Properties();
		File fileObj = new File(fileName);
		String absPathStr = fileObj.getAbsolutePath();
		log.debug((new StringBuilder()).append("fileName:").append(fileName).append("\r\n Absolute Path:")
				.append(absPathStr).toString());
		if (!fileObj.exists()) {
			throw new Exception((new StringBuilder()).append("parameter file not found:").append(fileName)
					.append("\r\nAbsolute Path:").append(absPathStr).toString());
		} else {
			FileInputStream fis = new FileInputStream(fileName);
			props.load(fis);
			fis.close();
			modifiedTimeMap.put(configType, Long.valueOf(fileObj.lastModified()));
			fileNameMap.put(configType, fileName);
			absPathMap.put(configType, absPathStr);
			Properties pro = (Properties) configMap.get(configType);
			if (pro != null) {//覆盖原有的配置项
				pro.putAll(props);
			} else {
				pro = props;
			}
			configMap.put(configType, pro);
			return true;
		}
	}

	public String getAbsPath() {
		return getAbsPath("ASIAINFO_PROPERTIES");
	}

	public String getAbsPath(String configType) {
		return (String) absPathMap.get(configType);
	}

	public Properties getPropFile(String configType) {
		Properties properties = (Properties) configMap.get(configType);
		return properties;
	}

}
