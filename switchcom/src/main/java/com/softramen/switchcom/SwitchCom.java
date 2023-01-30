package com.softramen.switchcom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

public class SwitchCom extends SwitchCompat {
	private final String TAG = "SWITCH_C";
	private int thumbColorActive, thumbColorInactive;
	private int trackColorActive, trackColorInactive;
	private int switchHeight;


	public SwitchCom( @NonNull final Context context ) {
		super( context , null );
	}

	public SwitchCom( @NonNull final Context context , @Nullable final AttributeSet attributeSet ) {
		super( context , attributeSet );
		setAttributes( attributeSet );
	}

	private void setAttributes( final AttributeSet attributeSet ) {

		try {
			final TypedArray typedArray = this.getContext().obtainStyledAttributes( attributeSet , R.styleable.SwitchCom );
			thumbColorActive = typedArray.getColor( R.styleable.SwitchCom_thumbColorActive , 0xFFA8A8A8 );
			trackColorActive = typedArray.getColor( R.styleable.SwitchCom_trackColorActive , 0xFF707070 );
			thumbColorInactive = typedArray.getColor( R.styleable.SwitchCom_thumbColorInactive , 0xFFB8B8B8 );
			trackColorInactive = typedArray.getColor( R.styleable.SwitchCom_trackColorInactive , 0xFF636363 );
			switchHeight = typedArray.getDimensionPixelSize( R.styleable.SwitchCom_switchHeight , 40 );
			typedArray.recycle();
		}
		catch ( final Exception error ) {
			Log.d( TAG , "Error : " + error );
		}

		/*   T H U M B   D R A W A B L E   */

		final StateListDrawable thumbStateListDrawable = new StateListDrawable();

		final ShapeDrawable thumbShapeDrawable = new ShapeDrawable( new OvalShape() );
		final int thumbSize = switchHeight;
		thumbShapeDrawable.setIntrinsicWidth( thumbSize );
		thumbShapeDrawable.setIntrinsicHeight( thumbSize );
		thumbShapeDrawable.getPaint().setColor( Color.WHITE );

		thumbStateListDrawable.addState( new int[]{ android.R.attr.state_checked } , thumbShapeDrawable );
		thumbStateListDrawable.addState( new int[]{ -android.R.attr.state_checked } , thumbShapeDrawable );

		/*   T R A C K   D R A W A B L E   */

		final StateListDrawable trackStateListDrawable = new StateListDrawable();
		final TrackDrawable trackDrawable = new TrackDrawable();

		trackStateListDrawable.addState( new int[]{ android.R.attr.state_checked } , trackDrawable );
		trackStateListDrawable.addState( new int[]{ -android.R.attr.state_checked } , trackDrawable );
		// super.setSwitchMinWidth( 700 );

		/* STATES COLOR */

		final int[][] states = new int[][]{ new int[]{ android.R.attr.state_checked } , new int[]{ -android.R.attr.state_checked } };
		final int[] thumbColors = new int[]{ thumbColorActive , thumbColorInactive };

		final ColorStateList thumbTintList = new ColorStateList( states , thumbColors );
		thumbStateListDrawable.setTintList( thumbTintList );
		// super.getThumbDrawable().setTintList( thumbTintList );
		super.setThumbDrawable( thumbStateListDrawable );

		final int[] trackColors = new int[]{ trackColorActive , trackColorInactive };
		final ColorStateList trackTintList = new ColorStateList( states , trackColors );
		// super.getTrackDrawable().setTintList( trackTintList );
		trackStateListDrawable.setTintList( trackTintList );
		super.setTrackDrawable( trackStateListDrawable );
	}

	@Override
	public boolean onTouchEvent( final MotionEvent motionEvent ) {
		final int action = motionEvent.getActionMasked();
		if ( action == MotionEvent.ACTION_UP ) {
			final float x = motionEvent.getX();
			final float y = motionEvent.getY();
			if ( x < 0 || x > getMeasuredWidth() || y < 0 || y > getMeasuredHeight() ) {
				return true;
			}
			return performClick();
		}
		return true;
	}

	@Override
	public boolean performClick() {
		return super.performClick();
	}
}
