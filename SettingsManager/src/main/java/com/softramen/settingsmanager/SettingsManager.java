package com.softramen.settingsmanager;

import com.softramen.settingsManager.R;
import java.util.LinkedHashMap;
import java.util.Map;

public class SettingsManager {

	private static SettingsManager instance;

	private final Map<Integer, SettingsItem> settingsItemMap;

	private SettingsManager() {
		settingsItemMap = createSettingsItem();
	}

	public static synchronized void init() {
		// May pass context to manage Shared Preferences as well.
		if ( instance == null ) {
			instance = new SettingsManager();
		}
	}

	private Map<Integer, SettingsItem> createSettingsItem() {
		// TODO : Load Default States by SharedPreferences
		final Map<Integer, SettingsItem> settingsItemMap = new LinkedHashMap<>();
		final String dot = "\\.";
		settingsItemMap.put( R.id.setting_a , new SettingsItem( "Setting A" , false ) );
		settingsItemMap.put( R.id.setting_b , new SettingsItem( "Setting B" , "A.B.C".split( dot ) , 0 ) );
		settingsItemMap.put( R.id.setting_c , new SettingsItem( "Setting C" , false ) );
		settingsItemMap.put( R.id.setting_d , new SettingsItem( "Setting D" , "X.Y.Z".split( dot ) , 1 ) );
		return settingsItemMap;
	}

	public static synchronized SettingsManager getInstance() {
		return instance;
	}

	public Map<Integer, SettingsItem> getSettingsItemMap() {
		return settingsItemMap;
	}
}
