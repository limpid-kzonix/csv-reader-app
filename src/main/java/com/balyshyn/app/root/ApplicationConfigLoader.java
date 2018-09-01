package com.balyshyn.app.root;

import com.balyshyn.app.utils.files.ApplicationFileUtils;
import com.google.common.base.Charsets;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import software.reinvent.commons.config.ConfigLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
public class ApplicationConfigLoader {

	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String CONF_DIRECTORY = "conf";

	private ApplicationConfigLoader() {
	}

	static Config load() {
		Config config = ConfigFactory.parseProperties(System.getProperties());
		config = withProvidedValues(config);
		config = withUserValues(config);
		config = withExternalAdditionalValues(config);
		return config.withFallback(ConfigFactory.load()).resolve();
	}

	private static Config withProvidedValues(final Config config) {
		final String providedConfig = System.getProperty("provided.config");
		if (isNotBlank(providedConfig)) {
			return config.withFallback(ConfigFactory.parseFile(new File(providedConfig)));
		}
		return config;
	}

	private static Config withUserValues(final Config config) {
		try {
			final String userConfigFile = defaultString(System.getProperty("user.name")) + ".conf";
			final String pathToUserConfig = FILE_SEPARATOR + userConfigFile;
			if (isNotBlank(userConfigFile) && ConfigLoader.class.getResource(pathToUserConfig) != null) {
				final String confString = IOUtils.toString(ConfigLoader.class.getResourceAsStream(pathToUserConfig),
								Charsets.UTF_8);
				final Config parsedConfig = ConfigFactory.parseString(confString);
				return config.withFallback(parsedConfig);
			}

		} catch (Exception e) {
			log.error("Could not load user config.", e);
		}
		return config;
	}

	private static Config withExternalAdditionalValues(final Config config) {
		final String rootPath = System.getProperty("user.dir") + FILE_SEPARATOR + CONF_DIRECTORY;
		File directory = new File(rootPath);
		if (!directory.exists()) {
			return config;
		}
		List<File> configFiles = ApplicationFileUtils.findFiles(directory, FILE_SEPARATOR, ".conf");
		return configFiles.stream()
						.filter(file -> isNotBlank(file.getAbsolutePath()))
						.map(readConfigFile(config)).reduce(Config::withFallback).orElse(config);
	}

	private static Function<File, Config> readConfigFile(Config config) {
		return file -> {
			try {
				final byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
				final String confString = IOUtils.toString(bytes, Charsets.UTF_8.toString());
				log.info("User specific configuration uploaded - {} \n", confString);
				return ConfigFactory.parseString(confString);
			} catch (IOException e) {
				log.error("Can`t read file {} as stream", file.getAbsoluteFile());
				return config;
			}
		};
	}


}
