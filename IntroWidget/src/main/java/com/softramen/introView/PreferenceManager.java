package com.softramen.introView;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

	private static final String PREFERENCES_NAME = "com.softramen.introView.PREFERENCES_NAME";

	private final SharedPreferences sharedPreferences;

	public PreferenceManager( final Context context ) {
		sharedPreferences = context.getSharedPreferences( PREFERENCES_NAME , Context.MODE_PRIVATE );
	}

	public boolean isDisplayed( final String id ) {
		return sharedPreferences.getBoolean( id , false );
	}

	public void setDisplayed( final String id ) {
		sharedPreferences
				.edit()
				.putBoolean( id , true )
				.apply();
	}

	public void reset( final String id ) {
		sharedPreferences
				.edit()
				.putBoolean( id , false )
				.apply();
	}

	public void resetAll() {
		sharedPreferences
				.edit()
				.clear()
				.apply();
	}
}
