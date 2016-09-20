package com.asiainfo.biapp.mcd.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.lang3.StringUtils;


public class MpmConfigure {

	private MpmConfigure() {
	}

	public static MpmConfigure getInstance() {
		return configure;
	}

	/**
	 * @deprecated Method setConfFileName is deprecated
	 */

	@Deprecated
	public void setConfFileName(String file) throws Exception {
		String province = MpmConfigure.getInstance().getProperty("PROVINCE");
		log.info("province------------------" + province);
		initProperties("MPM_PROPERTIES", file, province);
	}

	public String getProperty(String strKey) {
		try {
			return getProperty("MPM_PROPERTIES", strKey);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	public String getProperty(String configType, String strKey) throws Exception {
		String province = MpmConfigure.getInstance().getProperty("PROVINCE");
		//String province = AppConfigService.PROFILE_ACTIVE;
		if (StringUtils.isEmpty(configType)) {
			throw new Exception("----Configure--err-------:configType is null");
		}
		try {
			if (configMap.get(configType) == null) {
				throw new Exception((new StringBuilder()).append("----Configure--err-------:configType[")
						.append(configType).append("]is not initialized").toString());
			}
			File defaultFileObj = new File((String) fileMap.get(configType + "_default"));
			File provinceFileObj = new File((String) fileMap.get(configType + "_" + province));
			if (defaultFileObj.lastModified() > ((Long) modifiedTimeMap.get(configType + "_default")).longValue()
					|| provinceFileObj.lastModified() > ((Long) modifiedTimeMap.get(configType + "_" + province))
							.longValue()) {
				initProperties(configType, (String) fileNameMap.get(configType), province);
			}
			Properties properties = (Properties) configMap.get(configType);
			return properties.getProperty(strKey) == null ? "" : properties.getProperty(strKey).toString().trim();
		} catch (Exception excep) {
			log.error("", excep);
		}
		return "";
	}

	private synchronized boolean initProperties(String configType, String file, String province) throws Exception {
		if (StringUtils.isEmpty(configType)) {
			throw new Exception("----Configure--err-------:configType is null");
		}
		if (StringUtils.isEmpty(file)) {
			throw new Exception("----Configure--err-------:fileName is null");
		}
		Properties properties = new Properties();
		String defaultFile = file.replace("{PROVINCE}", "default");
		File defaultFileObj = new File(defaultFile);
		String defaultPath = defaultFileObj.getAbsolutePath();
		if (!defaultFileObj.exists()) {
			throw new Exception("Parameter file not found:\r\nAbsolute Path:" + defaultPath);
		} else {
			log.debug("Load default properties fileName:\r\n Absolute Path:" + defaultPath);
			FileInputStream fis = new FileInputStream(defaultFile);
			properties.load(fis);
			File privincePathObj = null;
			String provincePath = "";
			if (StringUtils.isNotEmpty(province)) { //把默认配置文件和各个省配置文件合并
				String provinceFile = file.replace("{PROVINCE}", province.toLowerCase());
				privincePathObj = new File(provinceFile);
				if (privincePathObj.exists()) {
					provincePath = privincePathObj.getAbsolutePath();
					log.debug("Load province properties fileName:\r\n Absolute Path:" + provincePath);
					FileInputStream fisp = new FileInputStream(provinceFile);
					Properties propsProvince = new Properties();
					propsProvince.load(fisp);
					fisp.close();
					Set<Entry<Object, Object>> entries = propsProvince.entrySet();
					if (entries != null) {
						Iterator<Entry<Object, Object>> iterator = entries.iterator();
						while (iterator.hasNext()) {
							Map.Entry<Object,Object> entry = (Map.Entry<Object,Object>) iterator.next();
							Object key = entry.getKey();
							Object value = entry.getValue();
							if (!properties.containsKey(key.toString())) {
								properties.put(key, value);
							} else {
								properties.remove(key);
								properties.put(key, value);
							}
						}
					}
				}
			}
			fis.close();

			modifiedTimeMap.put(configType + "_default", Long.valueOf(defaultFileObj.lastModified()));
			modifiedTimeMap.put(configType + "_" + province, Long.valueOf(privincePathObj.lastModified()));
			fileMap.put(configType + "_" + province, provincePath);
			fileMap.put(configType + "_default", defaultPath);
			fileNameMap.put(configType, file);
			configMap.put(configType, properties);
			return true;
		}
	}

	public String getAbsPath() {
		return getAbsPath(DEFAULT_CONFIG_TYPE + "_default");
	}

	public String getAbsPath(String configType) {
		return (String) fileMap.get(configType + "_default");
	}

	private static final Logger log = LogManager.getLogger();
	private static MpmConfigure configure = new MpmConfigure();
	private static final String DEFAULT_CONFIG_TYPE = "MPM_PROPERTIES";
	private static Map<String, Long> modifiedTimeMap = new HashMap<String, Long>();
	private static Map<String, String> fileMap = new HashMap<String, String>();
	private static Map<String, String> fileNameMap = new HashMap<String, String>();
	private static Map<String, Properties> configMap = new HashMap<String, Properties>();

}
