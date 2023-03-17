package com.softramen.backgroundViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import java.util.Random;

public class StarsView extends View {
	private final Random random = new Random();
	private final Bitmap bmpBackground, bmpStar;
	private final Bitmap[] bmpStarArray;

	public StarsView( final Context context , @Nullable final AttributeSet attrs ) {
		super( context , attrs );
		bmpBackground = getBackgroundShape();
		bmpStar = BitmapFactory.decodeResource( context.getResources() , R.drawable.drawable_star );
		bmpStarArray = createBitmapStars();
	}


	@Override
	protected void onDraw( final Canvas canvas ) {
		super.onDraw( canvas );
		canvas.drawBitmap( bmpBackground , 0 , 0 , null );
		for ( final Bitmap bmpStar : bmpStarArray ) {
			canvas.drawBitmap( bmpStar , getRandomPosX() , getRandomPosY() , null );
		}
	}

	private int getRandomPosX() {
		return ( int ) ( Math.random() * Screen.WIDTH );
	}

	private int getRandomPosY() {
		return ( int ) ( Math.abs( random.nextGaussian() ) * Screen.HEIGHT * 0.35 );
	}

	private Bitmap[] createBitmapStars() {
		final Bitmap[] bitmapArray = new Bitmap[ 40 ];
		for ( int idx = 0 ; idx < bitmapArray.length ; idx++ ) {
			final int size = getRandomSize();
			bitmapArray[ idx ] = Bitmap.createScaledBitmap( bmpStar , size , size , false );
		}
		return bitmapArray;
	}

	private int getRandomSize() {
		return ( int ) ( 10 + Math.random() * 0.06 * Screen.WIDTH );
	}

	private Bitmap getBackgroundShape() {
		final Drawable drawable = ResourcesCompat.getDrawable( getResources() , R.drawable.drawable_night_background , null );
		final Bitmap bitmap = Bitmap.createBitmap( Screen.WIDTH , Screen.HEIGHT , Bitmap.Config.ARGB_4444 );
		final Canvas canvas = new Canvas( bitmap );
		if ( drawable != null ) {
			drawable.setBounds( 0 , 0 , Screen.WIDTH , Screen.HEIGHT );
			drawable.draw( canvas );
		}
		return bitmap;
	}
}
