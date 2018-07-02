package com.shareshipping.utils.settingsManager.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

	public static String getResourcePath() {
		try {
			URI resourcePathFile = System.class.getResource("/RESOURCE_PATH").toURI();
			String resourcePath = Files.readAllLines(Paths.get(resourcePathFile)).get(0);
			URI rootURI = new File("").toURI();
			URI resourceURI = new File(resourcePath).toURI();
			URI relativeResourceURI = rootURI.relativize(resourceURI);
			return relativeResourceURI.getPath();
		} catch (Exception e) {
			return null;
		}
	}

	public static String getResourcePath(String folder) {

		return (getResourcePath() != null) ? getResourcePath() + folder : null;
	}

	public static File[] listFiles(String folderPath, FilenameFilter pattern) {
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles(pattern);
		return listOfFiles;

	}

}
