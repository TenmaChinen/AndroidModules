package com.softramen.introView;

import com.softramen.introView.shape.FocusGravity;
import com.softramen.introView.shape.FocusType;
import com.softramen.introView.utils.Constants;

public class IntroConfig {
	private boolean isFadeAnimationEnabled = Constants.DEFAULT_FADE_ANIMATION_ENABLED;
	private boolean isDotViewEnabled = Constants.DEFAULT_DOT_VIEW_ENABLED;
	private boolean dismissOnTouch = Constants.DEFAULT_DISMISS_ON_TOUCH;
	private FocusType focusType = Constants.DEFAULT_FOCUS_TYPE;
	private long delayMillis = Constants.DEFAULT_DELAY_MILLIS;
	private FocusGravity focusGravity = FocusGravity.CENTER;
	private int maskColor = Constants.DEFAULT_MASK_COLOR;
	private int padding = Constants.DEFAULT_PADDING;

	private int textInfoColor = Constants.NONE;
	private int textInfoSize = Constants.NONE;

	// G E T T E R S
	public int getMaskColor() {
		return maskColor;
	}

	public long getDelayMillis() {
		return delayMillis;
	}

	public FocusType getFocusType() {
		return focusType;
	}

	public FocusGravity getFocusGravity() {
		return focusGravity;
	}

	public int getPadding() {
		return padding;
	}

	public int getTextInfoColor() {
		return textInfoColor;
	}

	public int getTextInfoSize() {
		return textInfoSize;
	}

	// S E T T E R S

	public void setMaskColor( final int maskColor ) {
		this.maskColor = maskColor;
	}
	public void setDelayMillis( final long delayMillis ) {
		this.delayMillis = delayMillis;
	}

	public void setFadeAnimationEnabled( final boolean fadeAnimationEnabled ) {
		isFadeAnimationEnabled = fadeAnimationEnabled;
	}
	public void setFocusType( final FocusType focusType ) {
		this.focusType = focusType;
	}

	public void setFocusGravity( final FocusGravity focusGravity ) {
		this.focusGravity = focusGravity;
	}

	public void setPadding( final int padding ) {
		this.padding = padding;
	}

	public void setDismissOnTouch( final boolean dismissOnTouch ) {
		this.dismissOnTouch = dismissOnTouch;
	}

	public void setDotViewEnabled( final boolean dotViewEnabled ) {
		isDotViewEnabled = dotViewEnabled;
	}

	public void setTextInfoColor( final int textInfoColor ) {
		this.textInfoColor = textInfoColor;
	}

	public void setTextInfoSize( final int textInfoSize ) {
		this.textInfoSize = textInfoSize;
	}

	// Checkers

	public boolean isDismissOnTouch() {
		return dismissOnTouch;
	}

	public boolean isDotViewEnabled() {
		return isDotViewEnabled;
	}

	public boolean isFadeAnimationEnabled() {
		return isFadeAnimationEnabled;
	}

	public boolean isTextInfoColorSet() {
		return textInfoColor != Constants.NONE;
	}

	public boolean isTextInfoSizeSet() {
		return textInfoSize != Constants.NONE;
	}
}
