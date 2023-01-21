package com.softramen.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class SoundManager {

    private final int MAX_STREAMS = 2;
    private final int PRIORITY = 1;
    private final int RATE = 1;
    private final int LOOP = 0;

    private float leftSound, rightSound;
    private boolean muted;

    private final SoundPool soundPool;
    private final int ERROR_SOUND, PRESS_SOUND;

    private static SoundManager instance;

    private SoundManager( Context context ) {
        leftSound = 1.0f;
        rightSound = 1.0f;
        muted = PreferencesManager.getInstance().getSoundState();

        // Deprecated in API 21
        // soundPool = new SoundPool( MAX_STREAMS, AudioManager.STREAM_MUSIC, 0 );
        AudioAttributes audioAttributes;

        audioAttributes = new AudioAttributes.Builder()
                .setContentType( AudioAttributes.CONTENT_TYPE_MUSIC )
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams( MAX_STREAMS )
                .setAudioAttributes( audioAttributes )
                .build();

        ERROR_SOUND = soundPool.load( context , R.raw.error , PRIORITY );
        PRESS_SOUND = soundPool.load( context , R.raw.press , PRIORITY );

        // TODO : CHECK SHARED PREFERENCES HERE TO MUTE OR UNMUTE
    }

    public static synchronized void init( final Context context ) {
        if ( instance == null ) {
            instance = new SoundManager( context );
        }
    }

    public static synchronized SoundManager getInstance() {
        return instance;
    }

    public void playError() {
        playSound( ERROR_SOUND );
    }

    public void playPress() {
        playSound( PRESS_SOUND );
    }

    public void setMusicState( boolean muted ) {
        this.muted = muted;
    }

    private void playSound( int soundId ) {
        if ( !isMuted() ) {
            soundPool.play( soundId , leftSound , rightSound , PRIORITY , LOOP , RATE );
        }
    }

    public void toggleSound() {
        muted = !muted;
        PreferencesManager.getInstance().setSoundState( muted );
    }

    public boolean isMuted() {
        return muted;
    }
}
