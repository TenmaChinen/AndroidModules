package com.softramen.introView.utils;

import android.content.res.Resources;

public class Utils {
	private static final float DENSITY = Resources
			.getSystem()
			.getDisplayMetrics().density;

	public static int pxToDp( final int px ) {
		return ( int ) ( px / DENSITY );
	}

	public static int dpToPx( final int dp ) {
		return ( int ) ( dp * DENSITY );
	}
}
