package com.shareshipping.utils.settingsManager;

public interface ISettingsManager {

	public abstract String getStringProperty(String key, String defaultRet);

	public abstract int getIntProperty(String key, int defaultRet);

	public abstract void parseSettings(String file);

}
