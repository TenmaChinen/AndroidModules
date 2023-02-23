package com.softramen.introView.animation;

import android.animation.Animator;
import androidx.annotation.NonNull;

public interface AnimatorListener extends Animator.AnimatorListener {
	@Override
	default void onAnimationStart( @NonNull final Animator animation ) {}
	@Override
	default void onAnimationEnd( @NonNull final Animator animation ) {}
	@Override
	default void onAnimationCancel( @NonNull final Animator animation ) {}
	@Override
	default void onAnimationRepeat( @NonNull final Animator animation ) {}
}
