package com.softramen.recyclerviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


public class FadeRecyclerView extends RecyclerView {
	private float topFadingPercent, bottomFadingPercent, leftFadingPercent, rightFadingPercent;

	public FadeRecyclerView( @NonNull final Context context ) {
		super( context );
		setDefaults();
	}

	public FadeRecyclerView( @NonNull final Context context , @Nullable final AttributeSet attrs ) {
		super( context , attrs );
		setDefaults();
		setAttributes( attrs );
	}

	private void setDefaults() {
		setClipToPadding( false );
		setFadingEdgeLength( 100 );
		setVerticalFadingEdgeEnabled( true );
	}

	private void setAttributes( final AttributeSet attributeSet ) {
		final int topFadingEdgeLengthId = R.styleable.FadeRecyclerView_topFadingPercent;
		final int bottomFadingEdgeLengthId = R.styleable.FadeRecyclerView_bottomFadingPercent;
		final int leftFadingEdgeLengthId = R.styleable.FadeRecyclerView_leftFadingPercent;
		final int rightFadingEdgeLengthId = R.styleable.FadeRecyclerView_rightFadingPercent;

		if ( attributeSet != null ) {
			final TypedArray typedArray;
			typedArray = getContext().obtainStyledAttributes( attributeSet , R.styleable.FadeRecyclerView );
			final float defaultFadingLength = 0.5f;

			topFadingPercent = typedArray.getFloat( topFadingEdgeLengthId , defaultFadingLength );
			bottomFadingPercent = typedArray.getFloat( bottomFadingEdgeLengthId , defaultFadingLength );
			leftFadingPercent = typedArray.getFloat( leftFadingEdgeLengthId , defaultFadingLength );
			rightFadingPercent = typedArray.getFloat( rightFadingEdgeLengthId , defaultFadingLength );
			typedArray.recycle();
		}
	}

	@Override
	protected float getTopFadingEdgeStrength() {
		return topFadingPercent;
	}

	@Override
	protected float getBottomFadingEdgeStrength() {
		return bottomFadingPercent;
	}

	@Override
	protected float getLeftFadingEdgeStrength() {
		return leftFadingPercent;
	}

	@Override
	protected float getRightFadingEdgeStrength() {
		return rightFadingPercent;
	}


	@Override
	protected boolean isPaddingOffsetRequired() {
		return true;
	}

	@Override
	protected int getTopPaddingOffset() {
		return -getPaddingTop();
	}

	@Override
	protected int getBottomPaddingOffset() {
		return getPaddingBottom();
	}
}
