package com.softramen.introView.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import com.softramen.introView.IntroTarget;

public class Rect extends Shape {
	private final String TAG = "INTRO_RECT";
	private RectF adjustedRect;

	public Rect( final IntroTarget introTarget ) {
		super( introTarget );
		calculateAdjustedRect();
	}

	public Rect( final IntroTarget introTarget , final FocusType focusType ) {
		super( introTarget , focusType );
		calculateAdjustedRect();
	}

	public Rect( final IntroTarget introTarget , final FocusType focusType , final FocusGravity focusGravity , final int padding ) {
		super( introTarget , focusType , focusGravity , padding );
		calculateAdjustedRect();
	}

	@Override
	public void draw( final Canvas canvas , final Paint eraser , final int radius ) {
		canvas.drawRoundRect( adjustedRect , radius , radius , eraser );
	}

	private void calculateAdjustedRect() {
		final RectF rect = new RectF();
		rect.set( introTarget.getRect() );
		rect.left -= padding;
		rect.top -= padding;
		rect.right += padding;
		rect.bottom += padding;

		adjustedRect = rect;
	}

	@Override
	public void reCalculateAll() {
		calculateAdjustedRect();
	}

	@Override
	public Point getPoint() {
		return introTarget.getPoint();
	}

	@Override
	public int getHeight() {
		return ( int ) adjustedRect.height();
	}

	@Override
	public boolean isTouchOnFocus( final double x , final double y ) {
		return adjustedRect.contains( ( float ) x , ( float ) y );
	}
}
