package com.balyshyn.app.module;

import com.balyshyn.app.domain.dto.BugInfo;
import com.balyshyn.app.domain.dto.BugReportResult;
import com.balyshyn.app.root.Application;
import com.balyshyn.app.root.ApplicationConfigProvider;
import com.balyshyn.app.root.GuiceApplication;
import com.balyshyn.app.service.csv.*;
import com.balyshyn.app.service.csv.impl.CsvBugReportFileFinder;
import com.balyshyn.app.service.csv.impl.CsvBugReportProcessor;
import com.balyshyn.app.service.csv.impl.CsvBugReportReader;
import com.balyshyn.app.service.csv.impl.CsvBugReportResultFileWriter;
import com.balyshyn.app.service.csv.providers.CsvBeanListProcessorProvider;
import com.balyshyn.app.service.csv.providers.CsvBugReportParserProvider;
import com.balyshyn.app.utils.asciiart.ASCIIArt;
import com.balyshyn.app.utils.asciiart.ASCIIArtImpl;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.typesafe.config.Config;
import com.univocity.parsers.common.processor.BeanListProcessor;
import software.reinvent.commons.log.Slf4jTypeListener;

import java.util.Map;

import static com.google.inject.matcher.Matchers.any;

public class AppModule extends AbstractModule {


	@Override
	protected void configure() {
		bindListener(any(), new Slf4jTypeListener());
		bind(Config.class).toProvider(ApplicationConfigProvider.class).asEagerSingleton();
		bind(ASCIIArt.class).to(ASCIIArtImpl.class);
		bind(Application.class).to(GuiceApplication.class);
		bind(CsvFileFinder.class).to(CsvBugReportFileFinder.class);
		bind(new TypeLiteral<CsvFileReader<BugInfo>>() { }).to(CsvBugReportReader.class);
		bind(new TypeLiteral<CsvDataProcessor<BugReportResult>>() { }).to(CsvBugReportProcessor.class);
		bind(new TypeLiteral<BeanListProcessor<BugInfo>>() { }).toProvider(CsvBeanListProcessorProvider.class);
		bind(new TypeLiteral<CsvDataParser<BugInfo>>() { }).toProvider(CsvBugReportParserProvider.class);
		bind(new TypeLiteral<CsvFileWriter<Map<String, BugReportResult>>>() { }).to(CsvBugReportResultFileWriter.class);
	}
}
