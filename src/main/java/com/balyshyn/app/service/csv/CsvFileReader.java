package com.balyshyn.app.service.csv;

import java.io.File;
import java.util.List;

public interface CsvFileReader<T> {

	List<T> readFile(File csvFile);
}
