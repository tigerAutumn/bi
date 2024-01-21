package com.pinting.core.logback;

import java.io.FileNotFoundException;
import java.net.URL;

import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LogbackConfigurer {
	public static final String CLASSPATH_URL_PREFIX = "classpath:";
	public static final String XML_FILE_EXTENSION = ".xml";

	public static void initLogging(String location)
			throws FileNotFoundException {

		LoggerContext logContext = (LoggerContext) LoggerFactory
				.getILoggerFactory();
		logContext.reset();

		JoranConfigurator jConfig = new JoranConfigurator();
		jConfig.setContext(logContext);

		String resolvedLocation = SystemPropertyUtils
				.resolvePlaceholders(location);
		URL url = ResourceUtils.getURL(resolvedLocation);
		try {
			jConfig.doConfigure(url);
		} catch (JoranException e) {
			throw new IllegalArgumentException("Initializing 'logback' error: "
					+ e.getMessage());
		}
	}
}
