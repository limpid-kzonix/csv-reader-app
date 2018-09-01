package com.balyshyn.app.service.csv.impl;

import com.balyshyn.app.domain.dto.BugInfo;
import com.balyshyn.app.domain.dto.BugReportResult;
import com.balyshyn.app.root.AppConfigConsts;
import com.balyshyn.app.service.csv.CsvDataProcessor;
import com.balyshyn.app.service.csv.CsvFileFinder;
import com.balyshyn.app.service.csv.CsvFileReader;
import com.balyshyn.app.service.csv.CsvFileWriter;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class CsvBugReportProcessor implements CsvDataProcessor<BugReportResult> {

	private final Config config;
	private final CsvFileFinder csvFileFinder;
	private final CsvFileReader<BugInfo> csvFileReader;
	private final CsvFileWriter<Map<String, BugReportResult>> csvFileWriter;

	@Inject
	public CsvBugReportProcessor(Config config, CsvFileFinder csvFileFinder, CsvFileReader<BugInfo> csvFileReader,
	                             CsvFileWriter<Map<String, BugReportResult>> csvFileWriter) {
		this.config = config;
		this.csvFileFinder = csvFileFinder;
		this.csvFileReader = csvFileReader;
		this.csvFileWriter = csvFileWriter;
	}

	private void fillEmptyResult(Map<String, BugReportResult> reportResultMap) {
		final Set<String> difference = Sets.difference(AppConfigConsts.LABELS, reportResultMap.keySet()).immutableCopy();
		difference.forEach(s -> reportResultMap.putIfAbsent(s, new BugReportResult()));
	}

	@Override
	public Map<String, BugReportResult> process() {
		final Optional<File> file = csvFileFinder.find(config.getString(AppConfigConsts.CSV_FILEPATH),
						config.getString(AppConfigConsts.CSV_FILENAME));
		if (!file.isPresent()) {
			log.warn("File {} in directory {} is not found",
							config.getString(AppConfigConsts.CSV_FILENAME), config.getString(AppConfigConsts.CSV_FILEPATH));

			return Maps.newHashMap();
		}
		final List<BugInfo> bugInfoList = csvFileReader.readFile(file.get());
		if (bugInfoList.isEmpty()) {
			log.warn("Inappropriate file structure or file is empty ( {} ).", file.get().getAbsolutePath());
		}
		Map<String, BugReportResult> reportResultMap =
						bugInfoList.stream().collect(Collectors.groupingBy(CsvBugReportProcessorUtils.groupByLabel(),
										Collectors.collectingAndThen(Collectors.toList(), CsvBugReportProcessorUtils.mapToResult())));
		if (!reportResultMap.isEmpty()) {
			fillEmptyResult(reportResultMap);
			csvFileWriter.writeFile(file.get(), reportResultMap, config);
			log.info("Recording was successful");
		} else {
			log.warn("Recording of result was failed");
		}
		return reportResultMap;
	}


}
