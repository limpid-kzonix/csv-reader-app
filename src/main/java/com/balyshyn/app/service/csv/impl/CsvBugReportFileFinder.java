package com.balyshyn.app.service.csv.impl;

import com.balyshyn.app.service.csv.CsvFileFinder;
import com.balyshyn.app.utils.files.ApplicationFileUtils;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Optional;

@Slf4j
@Singleton
public class CsvBugReportFileFinder implements CsvFileFinder {

	@Override
	public Optional<File> find(String path, String fileNamePattern) {
		log.info("Start searching file {}", fileNamePattern);
		final File directory = new File(path);
		if (StringUtils.isNotBlank(path) && directory.exists()) {
			return ApplicationFileUtils.findFileByFileName(directory, System.getProperty("file.separator"),
							fileNamePattern);
		} else {
			return ApplicationFileUtils.findFileByFileName(new File(System.getProperty("user.dir")),
							System.getProperty("file.separator"), fileNamePattern);
		}
	}
}
