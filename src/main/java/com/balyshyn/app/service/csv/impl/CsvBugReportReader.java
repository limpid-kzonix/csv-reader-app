package com.balyshyn.app.service.csv.impl;

import com.balyshyn.app.domain.dto.BugInfo;
import com.balyshyn.app.service.csv.CsvDataParser;
import com.balyshyn.app.service.csv.CsvFileReader;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.File;
import java.util.List;

@Singleton
public class CsvBugReportReader implements CsvFileReader<BugInfo> {

	private final Provider<CsvDataParser<BugInfo>> parserProvider;

	@Inject
	public CsvBugReportReader(Provider<CsvDataParser<BugInfo>> parserProvider) {
		this.parserProvider = parserProvider;
	}

	@Override
	public List<BugInfo> readFile(File csvFile) {
		return parserProvider.get().parse(csvFile);
	}
}
