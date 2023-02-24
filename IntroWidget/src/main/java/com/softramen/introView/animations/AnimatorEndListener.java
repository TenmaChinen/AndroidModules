package com.softramen.introView.animations;

import android.animation.Animator;
import androidx.annotation.NonNull;

public interface AnimatorEndListener extends Animator.AnimatorListener {
	@Override
	default void onAnimationStart( @NonNull final Animator animation ){}
	@Override
	void onAnimationEnd( @NonNull final Animator animation );
	@Override
	default void onAnimationCancel( @NonNull final Animator animation ) {}
	@Override
	default void onAnimationRepeat( @NonNull final Animator animation ) {}
}
