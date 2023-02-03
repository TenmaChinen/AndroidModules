package com.softramen.utils;

import android.graphics.Bitmap;

public class BitmapManager {
	private final String TAG = "BITMAP_MANAGER";

	public static Bitmap[] scaleBitmaps( final Bitmap[] bitmaps , final int width , final int height ) {
		final Bitmap[] scaledBitmaps = new Bitmap[ bitmaps.length ];
		for ( int idx = 0 ; idx < bitmaps.length ; idx++ ) {
			scaledBitmaps[ idx ] = scaleBitmap( bitmaps[ idx ] , width , height );
		}

		return scaledBitmaps;
	}

	public static Bitmap scaleBitmap( final Bitmap bitmap , final int width , final int height ) {
		return Bitmap.createScaledBitmap( bitmap , width , height , false );
	}

	public static Bitmap scaleTileBitmap( final Bitmap tileBitmap , final int tileSize ) {
		return scaleBitmap( tileBitmap , tileSize , tileSize );
	}

	public static Bitmap[] scaleTilesBitmap( final Bitmap[] tilesBitmap , final int tileSize ) {
		return scaleBitmaps( tilesBitmap , tileSize , tileSize );
	}
}