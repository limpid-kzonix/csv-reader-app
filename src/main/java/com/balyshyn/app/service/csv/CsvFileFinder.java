package com.balyshyn.app.service.csv;

import java.io.File;
import java.util.Optional;

public interface CsvFileFinder {

	Optional<File> find(String path, String fileNamePattern);

}
