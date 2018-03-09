package com.shareshipping.utils.settingsManager.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.shareshipping.utils.settingsManager.ISettingsManager;

public class SettingsManager implements ISettingsManager {

	private HashMap<String, String> config = Maps.newHashMap();

	public void parseSettings(String file) {

		String path = getResourcePath(file);

		FileInputStream fis;
		try {
			fis = new FileInputStream(path);

			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

			String line;
			while ((line = reader.readLine()) != null) {
				if (StringUtils.isNotBlank(line) && !StringUtils.startsWith(line, "#")) {
					Pattern p = Pattern.compile("([^\\s|=]+)\\s*=\\s*([^\\s|=]+)\\s*");
					Matcher m = p.matcher(line);
					if (m.matches()) {
						String key = m.group(1);
						String value = m.group(2);

						config.put(key, value);

					}
				}
			}

			reader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getResourcePath() {
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

	public String getResourcePath(String folder) {

		return (getResourcePath() != null) ? getResourcePath() + folder : null;
	}

	private boolean isValidInt(String value) {

		return value.matches("^\\d+$");
	}

	public String getStringProperty(String key, String defaultRet) {
		return (config.get(key) == null) ? defaultRet : config.get(key);
	}

	public int getIntProperty(String key, int defaultRet) {
		String value = getStringProperty(key, String.valueOf(defaultRet));

		if (isValidInt(value)) {

			return Integer.parseInt(value);
		}

		return defaultRet;
	}

}
