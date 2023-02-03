package com.softramen.CustomDialogs.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Constants {
	public static class Screen {
		private static final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
		public static final int WIDTH = displayMetrics.widthPixels;
		public static final int HEIGHT = displayMetrics.heightPixels;
	}

	public static class Dialog {
		public static final int HEIGHT = ( int ) ( Screen.HEIGHT * 0.85 );
	}
}