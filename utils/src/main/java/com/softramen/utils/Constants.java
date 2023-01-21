package com.softramen.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Constants {
    private static final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();

    static class Screen {
        public static final int WIDTH = displayMetrics.widthPixels;
        public static final int HEIGHT = displayMetrics.heightPixels;
    }

    static class InGame {
        public static final int HEIGHT = ( int ) ( Screen.HEIGHT * 0.9 );
        public static final int MARGIN_X = ( int ) ( Screen.WIDTH * 0.1 );
        public static final int MARGIN_Y = ( int ) ( InGame.HEIGHT * 0.1 );
    }
}