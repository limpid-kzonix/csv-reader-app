package com.balyshyn.app.root;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.LogbackException;
import ch.qos.logback.core.spi.FilterReply;
import org.assertj.core.util.Lists;

import java.util.List;

public class WarningsAppender extends AppenderBase<ILoggingEvent> implements TestAppender {

	private List<ILoggingEvent> loggingEvents = Lists.newArrayList();

	@Override
	protected void append(ILoggingEvent eventObject) {
		addToList(eventObject);
	}

	@Override
	public void doAppend(ILoggingEvent eventObject) throws LogbackException {
		addToList(eventObject);
	}

	@Override
	public FilterReply getFilterChainDecision(ILoggingEvent event) {
		return FilterReply.NEUTRAL;
	}

	@Override
	public List<ILoggingEvent> getLoggingEvents() {
		return Lists.newArrayList(loggingEvents);
	}

	private void addToList(ILoggingEvent eventObject) {
		if (eventObject.getLevel().equals(Level.WARN))
			loggingEvents.add(eventObject);
	}
}
