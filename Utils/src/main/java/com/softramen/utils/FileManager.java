package com.softramen.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

public class FileManager {
	private final String TAG = "FILE_MANAGER";

	private final AssetManager assetManager;

	public FileManager( final Context context ) {
		assetManager = context.getAssets();
	}

	public String getString( final String filePath ) {

		byte[] buffer;
		try {
			final InputStream inputStream = assetManager.open( filePath );
			buffer = new byte[ inputStream.available() ];
			inputStream.read( buffer );
			inputStream.close();
			return new String( buffer );
		}
		catch ( final IOException ioException ) {
			Log.d( TAG , "getString >  ioException : " + ioException );
			return null;
		}
	}

	public Bitmap[] getBitmaps( final String[] filesPath ) {
		final Bitmap[] bitmaps = new Bitmap[ filesPath.length ];

		for ( int idx = 0 ; idx < filesPath.length ; idx++ ) {
			try {
				final InputStream inputStream = assetManager.open( filesPath[ idx ] );
				bitmaps[ idx ] = BitmapFactory.decodeStream( inputStream );
			}
			catch ( final IOException ioException ) {
				Log.d( TAG , "getBitmaps > ioException : " + ioException );
			}
		}
		return bitmaps;
	}
}
