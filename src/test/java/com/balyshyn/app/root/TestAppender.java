package com.balyshyn.app.root;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;

import java.util.List;

public interface TestAppender extends Appender<ILoggingEvent> {
	List<ILoggingEvent> getLoggingEvents();
}
