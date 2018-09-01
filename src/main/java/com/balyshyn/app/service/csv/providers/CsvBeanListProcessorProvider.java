package com.balyshyn.app.service.csv.providers;

import com.balyshyn.app.domain.dto.BugInfo;
import com.google.inject.Provider;
import com.univocity.parsers.common.processor.BeanListProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsvBeanListProcessorProvider implements Provider<BeanListProcessor<BugInfo>> {
	@Override
	public BeanListProcessor<BugInfo> get() {
		return new BeanListProcessor<>(BugInfo.class);
	}
}
