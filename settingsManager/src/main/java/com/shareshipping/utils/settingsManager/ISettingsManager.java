package com.shareshipping.utils.settingsManager;

import java.util.Optional;

public interface ISettingsManager {

	public abstract String getStringProperty(String key, String defaultRet);

	public abstract int getIntProperty(String key, int defaultRet);

	public abstract void parseSettings(String file);

	public abstract <T> Optional<T> getOptionalProperty(String key, Class<T> clazz);
}
