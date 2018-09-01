package com.balyshyn.app.service.csv;

import com.typesafe.config.Config;

import java.io.File;
import java.util.Optional;

public interface CsvFileWriter<T> {

	Optional<File> writeFile(File csvFile, T source, Config config);
}
