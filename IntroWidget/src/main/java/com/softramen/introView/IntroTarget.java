package com.softramen.introView;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;

public class IntroTarget {
	private final View targetView;
	private final FrameLayout introLayout;

	public IntroTarget( final View targetView, final FrameLayout introLayout ) {
		this.targetView = targetView;
		this.introLayout = introLayout;
	}

	public Point getPoint() {
		final int[] location = getLocationInWindow();
		return new Point( location[ 0 ] + ( targetView.getWidth() / 2 ) , location[ 1 ] + ( targetView.getHeight() / 2 ) );
	}

	public Rect getRect() {
		final int[] location = getLocationInWindow();
		return new Rect( location[ 0 ] , location[ 1 ] , location[ 0 ] + targetView.getWidth() , location[ 1 ] + targetView.getHeight() );
	}

	private int[] getLocationInWindow(){
		final float yOffset = introLayout.getY();
		final int[] location = new int[ 2 ];
		targetView.getLocationInWindow( location );
		location[1] -= yOffset;
		return location;
	}

	public View getTargetView() {
		return targetView;
	}
}
