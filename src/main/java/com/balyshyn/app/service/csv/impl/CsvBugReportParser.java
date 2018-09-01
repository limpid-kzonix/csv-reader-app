package com.balyshyn.app.service.csv.impl;

import com.balyshyn.app.domain.dto.BugInfo;
import com.balyshyn.app.service.csv.CsvDataParser;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

@Slf4j
public class CsvBugReportParser implements CsvDataParser<BugInfo> {

	private final CsvParser parser;
	private final BeanListProcessor<BugInfo> beanListProcessor;

	public CsvBugReportParser(BeanListProcessor<BugInfo> beanListProcessor) {
		this.parser = new CsvParser(prepareSettings(beanListProcessor));
		this.beanListProcessor = beanListProcessor;
	}

	private CsvParserSettings prepareSettings(BeanListProcessor<BugInfo> beanListProcessor) {
		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.getFormat().setLineSeparator("\n");
		parserSettings.setProcessor(beanListProcessor);
		parserSettings.setHeaderExtractionEnabled(true);
		return parserSettings;
	}

	@Override
	public List<BugInfo> parse(File file) {
		parser.parse(file);
		return beanListProcessor.getBeans();
	}
}
