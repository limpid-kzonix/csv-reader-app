package com.balyshyn.app.service.csv;

import java.io.File;
import java.util.List;

public interface CsvDataParser<T> {
	List<T> parse(File file);
}
