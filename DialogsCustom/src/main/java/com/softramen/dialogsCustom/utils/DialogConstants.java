package com.softramen.dialogsCustom.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DialogConstants {
	private static final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    public static final int HEIGHT = ( int ) ( displayMetrics.heightPixels * 0.85 );
    public static final int disabledTint = 0xFFB1B1B1;
    public static final String METHOD_CODE = "METHOD_CODE";
    public static final int ON_CANCEL=0, ON_CLICK_POSITIVE = 1, ON_CLICK_NEGATIVE = 2, ON_CLICK_LEVEL_SELECTOR = 3;
    public static final int ON_CLICK_NEXT_LEVEL=4, ON_CLICK_RESTART=5, ON_CLICK_RETRY = 6, ON_CLICK_SAVE=7, ON_FINISH_ANNOUNCE=8;
}