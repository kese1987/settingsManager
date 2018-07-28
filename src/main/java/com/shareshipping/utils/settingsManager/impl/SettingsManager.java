package com.shareshipping.utils.settingsManager.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.shareshipping.utils.settingsManager.ISettingsManager;

public class SettingsManager implements ISettingsManager {

	private HashMap<String, String> config = Maps.newHashMap();

	@Override
	public void parseSettings(String file) {

		String path = Utils.getResourcePath(file);

		try (FileInputStream fis = new FileInputStream(path)) {

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
			e.printStackTrace();
		}
	}

	private boolean isValidInt(String value) {

		return value.matches("^\\d+$");
	}

	@Override
	public String getStringProperty(String key, String defaultRet) {
		return (config.get(key) == null) ? defaultRet : config.get(key);
	}

	@Override
	public int getIntProperty(String key, int defaultRet) {
		String value = getStringProperty(key, String.valueOf(defaultRet));

		if (isValidInt(value)) {

			return Integer.parseInt(value);
		}

		return defaultRet;
	}

	@Override
	public <T> Optional<T> getOptionalProperty(String key, Class<T> clazz) {
		String stringProperty = config.get(key);
		try {
			T castedObject = clazz.cast(stringProperty);
			//in case of null object return an empty optional 
			return Optional.ofNullable(castedObject);
		} catch (ClassCastException e) {
			//in case of exceptio
			return Optional.empty();
		}
	}

}
