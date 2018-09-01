package com.balyshyn.app.service.csv.providers;

import com.balyshyn.app.domain.dto.BugInfo;
import com.balyshyn.app.service.csv.CsvDataParser;
import com.balyshyn.app.service.csv.impl.CsvBugReportParser;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.univocity.parsers.common.processor.BeanListProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsvBugReportParserProvider implements Provider<CsvDataParser<BugInfo>> {

	private final Provider<BeanListProcessor<BugInfo>> beanListProcessorProvider;

	@Inject
	private CsvBugReportParserProvider(Provider<BeanListProcessor<BugInfo>> beanListProcessorProvider) {
		this.beanListProcessorProvider = beanListProcessorProvider;
	}

	@Override
	public CsvDataParser<BugInfo> get() {
		return new CsvBugReportParser(beanListProcessorProvider.get());
	}
}
