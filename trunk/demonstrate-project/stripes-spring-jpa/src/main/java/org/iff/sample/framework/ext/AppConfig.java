package org.iff.sample.framework.ext;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppConfig {

	private static final Log log = LogFactory.getLog(AppConfig.class);

	//
	public static final String MAIL_ERROR_EXCLUDE_EXCEPTION = "mail.error.exclude.exception";
	//
	public static final String APP_DEV = "APP_DEV";

	private static Configuration config;

	private AppConfig() {
	}

	public static Configuration getConfig() {
		if (config == null) {
			try {
				config = new PropertiesConfiguration("app.properties");
				if (config == null) {
					log.warn("Can not load configure file: app.properties!");
				}
				config = config == null ? new PropertiesConfiguration()
						: config;
			} catch (Exception e) {
				log.error("Can not load configure file: app.properties!", e);
			}
		}
		return config;
	}

	public static String getProperty(String key, String defaultValue) {
		return getConfig().getString(key, defaultValue);
	}

	public static String getProperty(String key) {
		return getConfig().getString(key);
	}

}
