package com.pinting.core.logback;

import java.io.FileNotFoundException;

import javax.servlet.ServletContext;

public class LogbackWebConfigurer {
	public static final String CONFIG_LOCATION_PARAM = "logbackConfigLocation";

	public static void initLogging(ServletContext servletContext) {
		String location = servletContext
				.getInitParameter("logbackConfigLocation");
		if (location != null) {
			servletContext.log("Initializing logback from [" + location + "]");
			try {
				LogbackConfigurer.initLogging(location);
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException(
						"Invalid 'logbackConfigLocation' parameter: "
								+ e.getMessage());
			}
		}
	}
}
