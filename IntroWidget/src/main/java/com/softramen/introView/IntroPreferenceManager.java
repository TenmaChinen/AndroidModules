package com.softramen.introView;

import android.content.Context;
import android.content.SharedPreferences;

public class IntroPreferenceManager {

	private static final String KEY_PREFERENCES = "com.softramen.introView.PREFERENCES";

	private final SharedPreferences sharedPreferences;

	public IntroPreferenceManager( final Context context ) {
		sharedPreferences = context.getSharedPreferences( KEY_PREFERENCES , Context.MODE_PRIVATE );
	}

	public boolean isDisplayed( final String id ) {
		return sharedPreferences.getBoolean( id , false );
	}

	public void setDisplayed( final String id ) {
		sharedPreferences.edit().putBoolean( id , true ).apply();
	}

	public void reset( final String id ) {
		if ( sharedPreferences.contains( id ) ) {
			sharedPreferences.edit().putBoolean( id , false ).apply();
		}
	}

	public void resetAll() {
		sharedPreferences.edit().clear().apply();
	}
}
