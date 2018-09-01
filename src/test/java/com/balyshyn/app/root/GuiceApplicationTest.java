package com.balyshyn.app.root;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.balyshyn.app.ConfigTestUtils;
import com.balyshyn.app.module.AppModule;
import com.google.inject.Inject;
import com.typesafe.config.Config;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;

import java.util.List;

@RunWith(JukitoRunner.class)
@UseModules(value = {AppModule.class, ApplicationTestModule.class})
public class GuiceApplicationTest {

	@Inject
	private Application application;

	@Inject
	private Config config;

	private Logger rootLogger;

	@Inject
	private TestAppender mockedAppender;

	@Before
	public void setUp() {
		rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		rootLogger.addAppender(mockedAppender);
	}

	@After
	public void tearDown() {
		rootLogger.detachAppender(mockedAppender);
	}

	@Test
	public void happyDayStart() {
		application.start();
	}

	@Test
	public void startWithHelloWorldLog() throws NoSuchFieldException, IllegalAccessException {
		final String backedUppedConfigValue = getConfigValue(AppConfigConsts.APPLICATION_NAME);
		addConfigEntry(AppConfigConsts.APPLICATION_NAME, "Test");

		application.start();
		final List<ILoggingEvent> loggingEvents = mockedAppender.getLoggingEvents();
		addConfigEntry(AppConfigConsts.APPLICATION_NAME, backedUppedConfigValue);
	}

	private void addConfigEntry(String path, String backedUppedConfigValue) throws NoSuchFieldException,
					IllegalAccessException {
		ConfigTestUtils.addConfigEntry(path, backedUppedConfigValue, config);
	}

	private String getConfigValue(String path) {
		String backedUppedConfigValue = null;
		if (config.hasPath(path)) {
			backedUppedConfigValue = config.getString(path);
		}
		return backedUppedConfigValue;
	}
}
