package com.balyshyn.app.root;

import com.balyshyn.app.domain.dto.BugReportResult;
import com.balyshyn.app.service.csv.CsvDataProcessor;
import com.balyshyn.app.utils.asciiart.ASCIIArt;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


/**
 * @author leonard Daume
 */
@Slf4j
public class GuiceApplication implements Application {

	@Inject
	private Config config;
	@Inject
	private ASCIIArt art;

	@Inject
	private CsvDataProcessor<BugReportResult> csvDataProcessor;


	@Override
	public void start() {
		printLogo();
		log.info("# Application Configuration of user: {}", System.getProperty("user.name"));
		log.info("{} is started with config.", this);
		csvDataProcessor.process();
		log.info("Application Lifecycle is finished.");
	}

	@Override
	public String toString() {
		final MoreObjects.ToStringHelper toStringHelper = MoreObjects.toStringHelper(this)
						.add(AppConfigConsts.APPLICATION_NAME, config.getString(AppConfigConsts.APPLICATION_NAME))
						.add("system-info", systemProperties());
		return toStringHelper.toString();
	}

	private Map<String, ConfigValue> systemProperties() {
		Map<String, ConfigValue> sysInfo = Maps.newHashMap();
		sysInfo.put("userName", config.getValue("user.name"));
		sysInfo.put("javaRuntime", config.getValue("java.runtime.name"));
		sysInfo.put("osName", config.getValue("os.name"));
		sysInfo.put("startCommand", config.getValue("sun.java.command"));
		return sysInfo;
	}

	private void printLogo() {
		final String logo = art.printTextLogo("A.Balyshyn", 115, 15);
		log.info("\n{}", logo);
	}
}
