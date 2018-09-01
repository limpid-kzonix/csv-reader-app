package com.balyshyn.app.service.csv.impl;

import com.balyshyn.app.domain.dto.BugReportResult;
import com.balyshyn.app.root.AppConfigConsts;
import com.balyshyn.app.service.csv.CsvFileWriter;
import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CsvBugReportResultFileWriter implements CsvFileWriter<Map<String, BugReportResult>> {
	@Override
	public Optional<File> writeFile(File csvFile, Map<String, BugReportResult> source, Config config) {
		final String[][] strings = fillMatrix(source, new String[ 9 ][ source.size() + 1 ]);
		final String resultStringToWrite = prepareStringToWrite(config.getString(AppConfigConsts.CSV_ITEM_SEPARATOR),
						strings);
		File resultFile;
		if (csvFile.isFile()) {
			resultFile = new File(csvFile.getParent() + getResultFileName(config));
		} else if (csvFile.isDirectory()) {
			resultFile = new File(csvFile.getAbsolutePath() + getResultFileName(config));
		} else {
			return Optional.empty();
		}
		try {
			if (resultFile.createNewFile()) {
				log.info("Created new file for writing result: {}", resultFile.getAbsolutePath());
			} else {
				log.info("Application will use existing file to write result. File location {}", resultFile.getAbsolutePath());
			}
		} catch (IOException e) {
			log.error("Can`t create file for writing result. Cause: {}", e.getMessage());
		}
		try (FileWriter fileWriter = new FileWriter(resultFile)) {
			if (resultFile.canWrite())
				fileWriter.write(resultStringToWrite);
			else {
				log.warn("Can`t write result to file: {}", resultFile.getAbsolutePath());
			}
		} catch (IOException e) {
			log.error("Error during writing process. Cause: {}", e.getMessage());
		}

		return Optional.of(resultFile);
	}

	private String getResultFileName(Config config) {
		return config.getString("file.separator") + config.getString(AppConfigConsts.CSV_RES_FILENAME);
	}

	private String prepareStringToWrite(String separator, String[][] strings) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String[] s : strings) {
			for (String value : s) {
				stringBuilder.append(value).append(separator);
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("\n");
		}
		return stringBuilder.toString();
	}

	private String[][] fillMatrix(Map<String, BugReportResult> source, String[][] mtrx) {
		int i = 1;
		fillRowFirstCellTitle(mtrx);
		for (Map.Entry<String, BugReportResult> entry : source.entrySet()) {
			mtrx[ 0 ][ i ] = String.valueOf(entry.getKey());
			mtrx[ 1 ][ i ] = String.valueOf(entry.getValue().getBlocker());
			mtrx[ 2 ][ i ] = String.valueOf(entry.getValue().getCritical());
			mtrx[ 3 ][ i ] = String.valueOf(entry.getValue().getMajor());
			mtrx[ 4 ][ i ] = String.valueOf(entry.getValue().getMedium());
			mtrx[ 5 ][ i ] = String.valueOf(entry.getValue().getMinor());
			mtrx[ 6 ][ i ] = String.valueOf(entry.getValue().getNormal());
			mtrx[ 7 ][ i ] = String.valueOf(entry.getValue().getTotal());
			mtrx[ 8 ][ i ] = String.valueOf(entry.getValue().getUnresolved());
			i++;
		}
		return mtrx;
	}

	private void fillRowFirstCellTitle(String[][] strings) {
		strings[ 0 ][ 0 ] = "";
		strings[ 1 ][ 0 ] = "Blocker";
		strings[ 2 ][ 0 ] = "Critical";
		strings[ 3 ][ 0 ] = "Major";
		strings[ 4 ][ 0 ] = "Medium";
		strings[ 5 ][ 0 ] = "Minor";
		strings[ 6 ][ 0 ] = "Normal";
		strings[ 7 ][ 0 ] = "Total";
		strings[ 8 ][ 0 ] = "Unresolved";
	}
}
