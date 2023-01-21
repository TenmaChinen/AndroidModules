package com.softramen.customdialogs;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Screen {
    private static final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    public static final int WIDTH = displayMetrics.widthPixels;
    public static final int HEIGHT = displayMetrics.heightPixels;
}