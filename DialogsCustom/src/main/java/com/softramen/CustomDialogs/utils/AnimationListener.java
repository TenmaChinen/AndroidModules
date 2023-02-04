package com.softramen.CustomDialogs.utils;

import android.view.animation.Animation;

public interface AnimationListener extends Animation.AnimationListener {
	default void onAnimationStart( final Animation animation ) {

	}

	void onAnimationEnd( Animation animation );

	default void onAnimationRepeat( final Animation animation ) {

	}
}
