package com.softramen.introView.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import com.softramen.introView.IntroTarget;
import com.softramen.introView.utils.Constants;

public abstract class Shape {

	protected FocusGravity focusGravity;
	protected IntroTarget introTarget;
	protected FocusType focusType;
	protected int padding;

	public Shape( final IntroTarget introTarget ) {
		this( introTarget , FocusType.MINIMUM );
	}

	public Shape( final IntroTarget introTarget , final FocusType focusType ) {
		this( introTarget , focusType , FocusGravity.CENTER , Constants.DEFAULT_PADDING );
	}

	public Shape( final IntroTarget introTarget , final FocusType focusType , final FocusGravity focusGravity , final int padding ) {
		this.introTarget = introTarget;
		this.focusType = focusType;
		this.focusGravity = focusGravity;
		this.padding = padding;
	}

	public abstract void draw( Canvas canvas , Paint eraser , int padding );

	protected Point getFocusPoint() {
		final Rect rect = introTarget.getRect();
		final Point point = introTarget.getPoint();
		if ( focusGravity == FocusGravity.LEFT ) {
			final int xLeft = rect.left + ( point.x - rect.left ) / 2;
			return new Point( xLeft , point.y );
		} else if ( focusGravity == FocusGravity.RIGHT ) {
			final int xRight = point.x + ( rect.right - point.x ) / 2;
			return new Point( xRight , point.y );
		} else return point;
	}

	public abstract void reCalculateAll();

	public abstract Point getPoint();

	public abstract int getHeight();

	// Determines if a click is on the shape
	public abstract boolean isTouchOnFocus( double x , double t );
}
