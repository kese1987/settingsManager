package com.shareshipping.utils.settingsManager;

import static com.google.inject.Scopes.SINGLETON;

import com.google.inject.AbstractModule;
import com.shareshipping.utils.settingsManager.impl.SettingsManager;

public class SettingsManagerModule extends AbstractModule {
	@Override
	public void configure() {
		bind(ISettingsManager.class).to(SettingsManager.class).in(SINGLETON);
	}
}
