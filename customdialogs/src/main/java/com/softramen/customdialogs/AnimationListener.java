package com.softramen.customdialogs;

import android.view.animation.Animation;

public interface AnimationListener extends Animation.AnimationListener {
    default void onAnimationStart( Animation animation ) {

    }

    void onAnimationEnd( Animation animation );

    default void onAnimationRepeat( Animation animation ) {

    }
}
