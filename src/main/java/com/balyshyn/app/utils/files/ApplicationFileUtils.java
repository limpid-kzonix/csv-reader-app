package com.balyshyn.app.utils.files;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ApplicationFileUtils {

	private ApplicationFileUtils() {
	}

	public static List<File> findFiles(File directory, String fileSeparator, String fileExtension) {
		List<File> files = scanDirectories(directory);
		return files.stream().filter(file -> {
			final String[] split = file.getAbsolutePath().split(fileSeparator);
			final String fileName = split[ split.length - 1 ];
			return fileName.contains(fileExtension);
		}).collect(Collectors.toList());
	}

	public static Optional<File> findFileByFileName(File directory, String fileSeparator, String fileNamePattern) {
		return scanDirectoriesAndFindFile(directory, fileSeparator, fileNamePattern);
	}

	private static Optional<File> scanDirectoriesAndFindFile(File directory, String fileSeparator,
	                                                         String fileNamePattern) {
		log.info("Finding process is starting in directory '{}'", directory.getAbsolutePath());
		if (!directory.exists()) {
			return Optional.empty();
		}
		final File[] contentOfDirectory = directory.listFiles();
		if (contentOfDirectory == null || contentOfDirectory.length == 0) {
			return Optional.empty();
		}
		for (File file : contentOfDirectory) {
			if (file.exists() && file.isFile()) {
				if (checkFile(fileSeparator, fileNamePattern, file)) return Optional.of(file);
			} else if (file.exists() && file.isDirectory()) {
				final Optional<File> maybeFile = findIsSubDirectory(fileSeparator, fileNamePattern, file);
				if (maybeFile.isPresent()) return maybeFile;
			}
		}
		return Optional.empty();
	}

	private static Optional<File> findIsSubDirectory(String fileSeparator, String fileNamePattern, File file) {
		File subDirectory = new File(file.getAbsolutePath());
		return scanDirectoriesAndFindFile(subDirectory, fileSeparator, fileNamePattern);
	}

	private static boolean checkFile(String fileSeparator, String fileNamePattern, File file) {
		final String[] split = file.getAbsolutePath().split(fileSeparator);
		final String fileName = split[ split.length - 1 ];
		return getFileInfo(fileName).getName().equals(getFileInfo(fileNamePattern).getName())
						|| getFileInfo(fileName).getName().equalsIgnoreCase(getFileInfo(fileNamePattern).getName())
						|| fileName.equals(fileNamePattern)
						|| fileName.equalsIgnoreCase(fileNamePattern);
	}

	private static FileInfo getFileInfo(String fileNamePattern) {
		final String[] fileName = fileNamePattern.split("\\.");
		if (fileName.length == 1 || fileName.length == 0) {
			return new FileInfo(fileNamePattern, "");
		}
		final String fileExtension = fileName[ fileName.length - 1 ];
		final String fileNameWithoutExtension = fileNamePattern.substring(0, fileNamePattern.indexOf(fileExtension));
		return new FileInfo(fileNameWithoutExtension, fileExtension);
	}

	private static List<File> scanDirectories(File directory) {
		log.info("Finding process is starting in directory '{}'", directory.getAbsolutePath());
		List<File> resultListOfFiles = Lists.newArrayList();
		if (!directory.exists()) {
			return Collections.emptyList();
		}
		final File[] contentOfDirectory = directory.listFiles();
		if (contentOfDirectory == null || contentOfDirectory.length == 0) {
			return Collections.emptyList();
		}
		for (File item : contentOfDirectory) {
			if (item.exists() && item.isFile()) {
				resultListOfFiles.add(item);
			} else if (item.exists() && item.isDirectory()) {
				File subDirectory = new File(item.getAbsolutePath());
				final List<File> files = scanDirectories(subDirectory);
				resultListOfFiles.addAll(files);
			}
		}
		return resultListOfFiles;
	}

	@Getter
	@AllArgsConstructor
	private static class FileInfo {

		private String name;
		private String extension;
	}


}
