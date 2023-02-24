package com.softramen.introView.animations;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import androidx.annotation.NonNull;

public class AnimatorFactory {

	public static void animateFadeIn( final View view , final long duration ) {
		final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat( view , View.ALPHA , 0f , 1f );
		objectAnimator.setDuration( duration );
		objectAnimator.addListener( ( AnimatorStartListener ) animation -> view.setVisibility( View.VISIBLE ) );
		objectAnimator.start();
	}

	public static void animateFadeOut( final View view , final long duration , final AnimatorEndListener animatorEndListener ) {
		final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat( view , View.ALPHA , 1 , 0 );
		objectAnimator.setDuration( duration );
		objectAnimator.addListener( animatorEndListener );
		objectAnimator.start();
	}

	public static void performAnimation( final View view ) {
		final AnimatorSet animatorSet = new AnimatorSet();
		final ValueAnimator scaleX = ObjectAnimator.ofFloat( view , View.SCALE_X , 0.6f );
		scaleX.setRepeatCount( ValueAnimator.INFINITE );
		scaleX.setRepeatMode( ValueAnimator.REVERSE );
		scaleX.setDuration( 1000 );

		final ValueAnimator scaleY = ObjectAnimator.ofFloat( view , View.SCALE_Y , 0.6f );
		scaleY.setRepeatCount( ValueAnimator.INFINITE );
		scaleY.setRepeatMode( ValueAnimator.REVERSE );
		scaleY.setDuration( 1000 );

		animatorSet.playTogether( scaleX , scaleY );
		animatorSet.start();
	}
}
