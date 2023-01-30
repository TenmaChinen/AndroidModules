package com.softramen.switchcom;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;

public class TrackDrawable extends Drawable {
	private final ShapeDrawable shapeDrawable;
	private ColorStateList colorStateList = null;

	public TrackDrawable() {
		final float[] outerRadius = new float[ 8 ];
		Arrays.fill( outerRadius , 100 );
		final RoundRectShape roundRectShape = new RoundRectShape( outerRadius , new RectF() , null );
		this.shapeDrawable = new ShapeDrawable( roundRectShape );
	}

	@Override
	public void draw( @NonNull final Canvas canvas ) {
		shapeDrawable.draw( canvas );
	}


	@Override
	public void setAlpha( final int alpha ) {
		shapeDrawable.setAlpha( alpha );
	}

	@Override
	public void setColorFilter( @Nullable final ColorFilter colorFilter ) {
		shapeDrawable.setColorFilter( colorFilter );
	}

	@Override
	public int getOpacity() {
		return shapeDrawable.getOpacity();
	}

	@Override
	public void setTintList( @Nullable final ColorStateList colorStateList ) {
		if ( this.colorStateList == null ) {
			this.colorStateList = colorStateList;
		}
	}

	@Override
	public boolean setState( final int[] stateSet ) {
		if ( colorStateList != null ) {
			final int color = colorStateList.getColorForState( stateSet , 0 );
			shapeDrawable.setTint( color );
		}
		return super.setState( stateSet );
	}

	@Override
	public void setBounds( final int left , final int top , final int right , final int bottom ) {
		super.setBounds( left , top , right , bottom );
		final int padY = ( int ) ( ( bottom - top ) * 0.1 );
		shapeDrawable.setBounds( left , top + padY , right , bottom - padY );
	}
}
