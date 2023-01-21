package com.softramen.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private final String TAG = "PREFERENCES_MANAGER" ;

    private static final String KEY_PREFERENCES = "com.softramen.$APP_NAME$.KEY_PREFERENCES";
    private static final String KEY_MUTE = "com.softramen.$APP_NAME$.KEY_MUTE";

    private static PreferencesManager instance;
    private final SharedPreferences sharedPreferences;

    private PreferencesManager( Context context ) {
        sharedPreferences = context.getSharedPreferences( KEY_PREFERENCES , Context.MODE_PRIVATE );
    }

    public static synchronized void init( final Context context ) {
        if ( instance == null ) {
            instance = new PreferencesManager( context );
        }
    }

    public static synchronized PreferencesManager getInstance() {
        return instance;
    }

    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }

    public void setSoundState( boolean mute ) {
        sharedPreferences.edit().putBoolean( KEY_MUTE , mute ).apply();
    }

    public boolean getSoundState() {
        return sharedPreferences.getBoolean( KEY_MUTE , false );
    }
}
