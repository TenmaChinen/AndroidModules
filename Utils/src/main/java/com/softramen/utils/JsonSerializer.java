package com.softramen.utils;

import android.util.Log;
import org.json.JSONArray;

public class JsonSerializer {

	private final static String TAG = "JSON_SERIALIZER";

	public static int[][] jsonMatrixToIntMatrix( final JSONArray jsonMatrix ) {
		JSONArray jsonArray;
		int[][] intMatrix = null;
		try {
			final int rows = jsonMatrix.length();
			final int cols = jsonMatrix.getJSONArray( 0 ).length();
			intMatrix = new int[ rows ][ cols ];
			for ( int row = 0 ; row < rows ; row++ ) {
				jsonArray = jsonMatrix.getJSONArray( row );
				for ( int col = 0 ; col < cols ; col++ ) {
					intMatrix[ row ][ col ] = jsonArray.getInt( col );
				}
			}
		}
		catch ( Exception e ) {
			Log.d( TAG , "ERROR : Json Matrix To Int Matrix" );
		}
		return intMatrix;
	}

	public static int[] jsonArrayToIntArray( final JSONArray jsonArray ) {
		int[] intArray = null;
		try {
			intArray = new int[ jsonArray.length() ];
			for ( int idx = 0 ; idx < intArray.length ; idx++ ) {
				intArray[ idx ] = jsonArray.getInt( idx );
			}
		}
		catch ( Exception e ) {
			Log.d( TAG , "ERROR : Json Array To Int Array" );
		}
		return intArray;
	}
}
