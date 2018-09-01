package com.balyshyn.app.service.csv;

import java.util.Map;

public interface CsvDataProcessor<T> {
	Map<String, T> process();
}
