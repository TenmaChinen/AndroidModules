package com.softramen.introView;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class IntroTarget {
	private final View targetView;

	public IntroTarget( final View targetView ) {
		this.targetView = targetView;
	}

	public Point getPoint() {
		final int[] location = new int[ 2 ];
		targetView.getLocationInWindow( location );
		return new Point( location[ 0 ] + ( targetView.getWidth() / 2 ) , location[ 1 ] + ( targetView.getHeight() / 2 ) );
	}

	public Rect getRect() {
		final int[] location = new int[ 2 ];
		targetView.getLocationInWindow( location );
		return new Rect( location[ 0 ] , location[ 1 ] , location[ 0 ] + targetView.getWidth() , location[ 1 ] + targetView.getHeight() );
	}

	public View getTargetView() {
		return targetView;
	}
}
