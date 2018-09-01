package com.balyshyn.app.service.csv.impl;

import com.balyshyn.app.domain.dto.BugInfo;
import com.balyshyn.app.domain.dto.BugReportResult;
import com.balyshyn.app.root.AppConfigConsts;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

class CsvBugReportProcessorUtils {

	private CsvBugReportProcessorUtils() {}

	static Function<List<BugInfo>, BugReportResult> mapToResult() {
		return list -> {
			final BugReportResult bugReportResult = new BugReportResult();
			list.forEach(bugInfo -> {
				switch (bugInfo.getPriority()) {
					case MAJOR:
						bugReportResult.incrementMajor();
						break;
					case MINOR:
						bugReportResult.incrementMinor();
						break;
					case BLOCKER:
						bugReportResult.incrementBlocker();
						break;
					case CRITICAL:
						bugReportResult.incrementCritical();
						break;
					case MEDIUM:
						bugReportResult.incrementMedium();
						break;
					case NORMAL:
						bugReportResult.incrementNormal();
						break;
					default:
						break;
				}
				if (!bugInfo.getStatus().contains("Closed - Complete")) {
					bugReportResult.incrementUnresolved();
				}
			});
			return bugReportResult;
		};
	}

	static Function<BugInfo, String> groupByLabel() {
		return element -> {
			final String[] split = element.getLabel().split(",");
			final Set<String> inputLabels = Arrays.stream(split).map(String::trim).collect(Collectors.toSet());
			inputLabels.retainAll(AppConfigConsts.LABELS);
			final String[] strings = inputLabels.toArray(new String[ 0 ]);
			return strings[ 0 ];
		};
	}
}
