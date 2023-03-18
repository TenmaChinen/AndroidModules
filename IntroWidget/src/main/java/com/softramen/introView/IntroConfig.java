package com.softramen.introView;

import android.view.Gravity;
import android.widget.FrameLayout.LayoutParams;
import com.softramen.introView.shapes.FocusGravity;
import com.softramen.introView.shapes.FocusType;
import com.softramen.introView.utils.Constants;

public class IntroConfig {
	private boolean isFadeAnimationEnabled = Constants.DEFAULT_FADE_ANIMATION_ENABLED;
	private boolean isDotViewEnabled = Constants.DEFAULT_DOT_VIEW_ENABLED;
	private long startDelayMillis = Constants.DEFAULT_START_DELAY_MILLIS;
	private boolean dismissOnTouch = Constants.DEFAULT_DISMISS_ON_TOUCH;
	private FocusType focusType = Constants.DEFAULT_FOCUS_TYPE;
	private int layoutMargin = Constants.DEFAULT_LAYOUT_MARGIN;
	private FocusGravity focusGravity = FocusGravity.CENTER;
	private int maskColor = Constants.DEFAULT_MASK_COLOR;
	private int focusPadding = Constants.DEFAULT_PADDING;
	private int layoutGravity = Gravity.NO_GRAVITY;

	private int textInfoColor = Constants.NONE;
	private int textInfoSize = Constants.NONE;
	private int textInfoSizeUnit = Constants.NONE;
	private int textInfoBackgroundColor = Constants.NONE;
	private int textInfoStyle = Constants.NONE;
	private int textInfoAlignment = Constants.NONE;

	// G E T T E R S
	public int getMaskColor() {
		return maskColor;
	}

	public long getStartDelayMillis() {
		return startDelayMillis;
	}

	public FocusType getFocusType() {
		return focusType;
	}

	public FocusGravity getFocusGravity() {
		return focusGravity;
	}

	public int getFocusPadding() {
		return focusPadding;
	}

	public int getTextInfoColor() {
		return textInfoColor;
	}

	public int getTextInfoSize() {
		return textInfoSize;
	}

	public int getTextInfoSizeUnit() {
		return textInfoSizeUnit;
	}

	public int getTextInfoBackgroundColor() {
		return textInfoBackgroundColor;
	}

	public int getTextInfoAlignment() {
		return textInfoAlignment;
	}

	public int getTextInfoStyle() {
		return textInfoStyle;
	}

	public int getLayoutMargin() {
		return layoutMargin;
	}

	public int getLayoutGravity() {
		return layoutGravity;
	}

	// S E T T E R S

	public void setMaskColor( final int maskColor ) {
		this.maskColor = maskColor;
	}
	public void setStartDelayMillis( final long startDelayMillis ) {
		this.startDelayMillis = startDelayMillis;
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

	public void setFocusPadding( final int padding ) {
		this.focusPadding = padding;
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

	public void setTextInfoSize( final int unit , final int textInfoSize ) {
		this.textInfoSize = textInfoSize;
		this.textInfoSizeUnit = unit;
	}

	public void setTextInfoBackgroundColor( final int textInfoBackgroundColor ) {
		this.textInfoBackgroundColor = textInfoBackgroundColor;
	}

	public void setTextInfoAlignment( final int textInfoAlignment ) {
		this.textInfoAlignment = textInfoAlignment;
	}

	public void setTextInfoStyle( final int textInfoStyle ) {
		this.textInfoStyle = textInfoStyle;
	}

	public void setLayoutMargin( final int layoutMargin ) {
		this.layoutMargin = layoutMargin;
	}

	public void setLayoutGravity( final int layoutGravity ) {
		this.layoutGravity = layoutGravity;
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

	public boolean isTextInfoSizeUnitSet() {
		return textInfoSizeUnit != Constants.NONE;
	}

	public boolean isTextInfoBackgroundColorSet() {
		return textInfoBackgroundColor != Constants.NONE;
	}

	public boolean isTextInfoStyleSet() {
		return textInfoStyle != Constants.NONE;
	}

	public boolean isTextInfoAlignmentSet() {
		return textInfoAlignment != Constants.NONE;
	}
}
