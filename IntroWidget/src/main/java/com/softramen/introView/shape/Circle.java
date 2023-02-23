package com.softramen.introView.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import com.softramen.introView.IntroTarget;

public class Circle extends Shape {
	private int radius;

	private Point circlePoint;

	public Circle( final IntroTarget introTarget , final FocusType focusType , final FocusGravity focusGravity , final int padding ) {
		super( introTarget , focusType , focusGravity , padding );
		circlePoint = getFocusPoint();
		calculateRadius( padding );
	}

	@Override
	public void draw( final Canvas canvas , final Paint eraser , final int padding ) {
		calculateRadius( padding );
		circlePoint = getFocusPoint();
		canvas.drawCircle( circlePoint.x , circlePoint.y , radius , eraser );
	}

	@Override
	public void reCalculateAll() {
		calculateRadius( padding );
		circlePoint = getFocusPoint();
	}

	private void calculateRadius( final int padding ) {
		final int side;

		if ( focusType == FocusType.MINIMUM ) side = Math.min( introTarget
				.getRect()
				.width() / 2 , introTarget
				.getRect()
				.height() / 2 );
		else if ( focusType == FocusType.ALL ) side = Math.max( introTarget
				.getRect()
				.width() / 2 , introTarget
				.getRect()
				.height() / 2 );
		else {
			final int minSide = Math.min( introTarget
					.getRect()
					.width() / 2 , introTarget
					.getRect()
					.height() / 2 );
			final int maxSide = Math.max( introTarget
					.getRect()
					.width() / 2 , introTarget
					.getRect()
					.height() / 2 );
			side = ( minSide + maxSide ) / 2;
		}

		radius = side + padding;
	}

	private int getRadius() {
		return radius;
	}

	@Override
	public Point getPoint() {
		return circlePoint;
	}

	@Override
	public int getHeight() {
		return 2 * getRadius();
	}

	@Override
	public boolean isTouchOnFocus( final double x , final double y ) {
		final int xV = getPoint().x;
		final int yV = getPoint().y;

		final double dx = Math.pow( x - xV , 2 );
		final double dy = Math.pow( y - yV , 2 );
		return ( dx + dy ) <= Math.pow( radius , 2 );
	}
}
