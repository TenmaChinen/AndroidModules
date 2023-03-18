package com.softramen.introView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ContextThemeWrapper;
import com.softramen.introView.animations.AnimatorFactory;
import com.softramen.introView.animations.IntroListener;
import com.softramen.introView.shapes.Circle;
import com.softramen.introView.shapes.FocusGravity;
import com.softramen.introView.shapes.FocusType;
import com.softramen.introView.shapes.Rect;
import com.softramen.introView.shapes.Shape;
import com.softramen.introView.shapes.ShapeType;
import com.softramen.introView.utils.Constants;
import com.softramen.introView.utils.Utils;

public class IntroWidget extends FrameLayout {
	private final String TAG = "INTRO_WIDGET";
	private int maskColor = Constants.DEFAULT_MASK_COLOR;

	// Prevents from drawing IntroWidget until isReady
	private boolean isReady = false;
	private long startDelayMillis = Constants.DEFAULT_START_DELAY_MILLIS;

	// Show/Dismiss IntroWidget with fade in/out animations if this is enabled.
	private boolean isFadeAnimationEnabled = true;
	private final long fadeAnimationDuration = Constants.DEFAULT_FADE_DURATION;

	// targetShape focusType on introTarget and clear circle to focusType
	private FocusGravity focusGravity = FocusGravity.CENTER;
	private FocusType focusType = FocusType.ALL;
	private final Paint eraser = new Paint();
	private IntroTarget introTarget;
	private Shape targetShape;

	// Used to delay IntroWidget
	private final Handler handler = new Handler();
	private Bitmap focusBitmap;

	private int focusPadding = Constants.DEFAULT_PADDING;
	private int layoutWidth = LayoutParams.MATCH_PARENT;
	private int layoutHeight = LayoutParams.MATCH_PARENT;
	private int layoutMargin = Constants.DEFAULT_LAYOUT_MARGIN;
	private int layoutGravity = Gravity.NO_GRAVITY;

	private TextView tvInfo;
	private View dotView;

	private boolean isShowOnlyOnce = true;
	private boolean isDotViewEnabled = Constants.DEFAULT_DOT_VIEW_ENABLED;
	private boolean dismissOnTouch = Constants.DEFAULT_DISMISS_ON_TOUCH;

	private IntroPreferenceManager introPreferenceManager;
	private String introId;

	private boolean isLayoutCompleted = false;
	private IntroListener introListener = null;
	private boolean isPerformClick = false;
	private boolean isDismissed = false;
	private boolean isInfoEnabled = false;


	private ShapeType targetShapeType = ShapeType.CIRCLE;

	public IntroWidget( final Context context ) {
		super( context );
		init( context );
	}

	public IntroWidget( final Context context , final AttributeSet attributeSet ) {
		super( context , attributeSet );
		init( context );
	}

	public IntroWidget( final Context context , final AttributeSet attributeSet , final int defStyleAttr ) {
		super( context , attributeSet , defStyleAttr );
		init( context );
	}

	public IntroWidget( final Context context , final AttributeSet attributeSet , final int defStyleAttr , final int defStyleRes ) {
		super( context , attributeSet , defStyleAttr , defStyleRes );
		init( context );
	}

	private void init( final Context context ) {
		setWillNotDraw( false );
		setVisibility( INVISIBLE );

		introPreferenceManager = new IntroPreferenceManager( context );

		eraser.setColor( 0xFFFFFFFF );
		eraser.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.CLEAR ) );
		eraser.setFlags( Paint.ANTI_ALIAS_FLAG );

		inflate( getContext() , R.layout.layout_intro , this );

		tvInfo = this.findViewById( R.id.tv_info );
		dotView = this.findViewById( R.id.iv_dot );
		dotView.measure( MeasureSpec.UNSPECIFIED , MeasureSpec.UNSPECIFIED );

		createTargetShape();

		getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				targetShape.reCalculateAll();
				if ( targetShape.getPoint().y != 0 && !isLayoutCompleted ) {
					createFocusBitmap();
					if ( isInfoEnabled ) setInfoLayout();
					if ( isDotViewEnabled ) setDotViewLayout();
					removeOnGlobalLayoutListener( IntroWidget.this , this );
				}
			}
		} );
	}

	private static void removeOnGlobalLayoutListener( final View view , final ViewTreeObserver.OnGlobalLayoutListener listener ) {
		final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
		viewTreeObserver.removeOnGlobalLayoutListener( listener );
	}

	private void createFocusBitmap() {
		focusBitmap = Bitmap.createBitmap( layoutWidth , layoutHeight , Config.ARGB_4444 );
		final Canvas canvas = new Canvas( focusBitmap );
		canvas.drawColor( maskColor );
		targetShape.draw( canvas , eraser , focusPadding );
	}

	private void createTargetShape() {
		handler.post( () -> {
			final Shape targetShape;

			if ( targetShapeType == ShapeType.CIRCLE ) {
				targetShape = new Circle( introTarget , focusType , focusGravity , focusPadding );
			} else {
				targetShape = new Rect( introTarget , focusType , focusGravity , focusPadding );
			}
			setTargetShape( targetShape );
		} );
	}

	@Override
	protected void onMeasure( final int widthMeasureSpec , final int heightMeasureSpec ) {
		super.onMeasure( widthMeasureSpec , heightMeasureSpec );
		layoutWidth = getMeasuredWidth();
		layoutHeight = getMeasuredHeight();
	}

	@Override
	protected void onDraw( final Canvas canvas ) {
		super.onDraw( canvas );
		if ( !isReady ) return;
		canvas.drawBitmap( focusBitmap , 0 , 0 , null );
	}

	@Override
	public boolean onTouchEvent( final MotionEvent motionEvent ) {
		if ( isDismissed ) return super.onTouchEvent( motionEvent );

		final float posX = motionEvent.getX();
		final float posY = motionEvent.getY();

		final boolean isTouchOnFocus = targetShape.isTouchOnFocus( posX , posY );
		final View targetView = introTarget.getTargetView();

		switch ( motionEvent.getAction() ) {

			case MotionEvent.ACTION_DOWN:
				if ( isTouchOnFocus && isPerformClick ) {
					targetView.setPressed( true );
					targetView.invalidate();
				}
				return true;

			case MotionEvent.ACTION_UP:
				if ( isTouchOnFocus || dismissOnTouch ) dismissIntroLayout();
				if ( isTouchOnFocus && isPerformClick ) {
					targetView.performClick();
					targetView.setPressed( true );
					targetView.invalidate();
					targetView.setPressed( false );
					targetView.invalidate();
				}
				return true;

			default:
				break;
		}

		return super.onTouchEvent( motionEvent );
	}

	private void dismissIntroLayout() {
		isDismissed = true;

		if ( isShowOnlyOnce ) introPreferenceManager.setDisplayed( introId );

		if ( isFadeAnimationEnabled ) {
			AnimatorFactory.animateFadeOut( this , fadeAnimationDuration , animation -> afterDismissIntroLayout() );
		} else {
			afterDismissIntroLayout();
		}
	}

	private void afterDismissIntroLayout() {
		setVisibility( GONE );
		removeIntroLayout();
		if ( introListener != null ) introListener.onUserClicked( introId );
	}

	private void removeIntroLayout() {
		if ( getParent() != null ) ( ( ViewGroup ) getParent() ).removeView( this );
	}

	public void show( final Activity activity ) {
		if ( isDisplayed() ) return;
		final Window window = activity.getWindow();
		final ViewGroup decorView = ( ViewGroup ) window.getDecorView();
		initLayoutParams();
		decorView.addView( this );
		showIntro();
	}

	public void show( final Dialog dialog ) {
		if ( isDisplayed() ) return;
		final Window window = dialog.getWindow();
		final ViewGroup decorView = ( ViewGroup ) window.getDecorView();
		initLayoutParams();
		decorView.addView( this );
		showIntro();
	}

	private void initLayoutParams() {
		final int screenHeight = this.getResources().getDisplayMetrics().heightPixels;
		layoutHeight = screenHeight - layoutMargin;

		final LayoutParams layoutParams = new LayoutParams( layoutWidth , layoutHeight );
		if ( layoutGravity == Gravity.BOTTOM ) {
			this.setY( layoutMargin );
		}
		this.setLayoutParams( layoutParams );
	}

	private void showIntro() {
		setReady();
		handler.postDelayed( () -> {
			if ( isFadeAnimationEnabled ) {
				AnimatorFactory.animateFadeIn( IntroWidget.this , fadeAnimationDuration );
			} else {
				setVisibility( VISIBLE );
			}
		} , startDelayMillis );
	}

	private boolean isDisplayed() {
		if ( isAlreadyIntroduced() ) {
			if ( introListener != null ) introListener.onUserClicked( introId );
			return true;
		}
		return false;
	}

	public boolean isAlreadyIntroduced() {
		return introPreferenceManager.isDisplayed( introId );
	}

	// Locate Info TextView above/below the TargetShape.

	private void setInfoLayout() {

		handler.post( () -> {

			final Point targetPoint = targetShape.getPoint();
			final int offsetY = ( int ) ( targetShape.getHeight() / 1.5f );
			final int infoPosY;

			if ( targetPoint.y < layoutHeight / 2 ) {
				infoPosY = targetPoint.y + offsetY;
			} else {
				infoPosY = targetPoint.y - offsetY - tvInfo.getMeasuredHeight();
			}
			tvInfo.setMaxWidth( ( int ) ( layoutWidth * 0.8 ) );
			tvInfo.setY( infoPosY );
			tvInfo.postInvalidate();
			tvInfo.setVisibility( VISIBLE );

			isLayoutCompleted = true;
		} );
	}

	private void setDotViewLayout() {
		handler.post( () -> {
			final LayoutParams dotViewLayoutParams = new LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT );
			dotViewLayoutParams.height = Utils.dpToPx( Constants.DEFAULT_DOT_SIZE );
			dotViewLayoutParams.width = Utils.dpToPx( Constants.DEFAULT_DOT_SIZE );
			final Point point = targetShape.getPoint();
			dotViewLayoutParams.setMargins( point.x - ( dotViewLayoutParams.width / 2 ) , point.y - ( dotViewLayoutParams.height / 2 ) , 0 , 0 );
			dotView.setLayoutParams( dotViewLayoutParams );
			dotView.postInvalidate();
			dotView.setVisibility( VISIBLE );
			AnimatorFactory.performAnimation( dotView );
		} );
	}

	// S E T T E R S

	private void setMaskColor( final int maskColor ) {
		this.maskColor = maskColor;
	}

	private void setStartDelay( final int delayMillis ) {
		this.startDelayMillis = delayMillis;
	}

	private void enableFadeAnimation( final boolean isFadeAnimationEnabled ) {
		this.isFadeAnimationEnabled = isFadeAnimationEnabled;
	}

	private void setTargetShapeType( final ShapeType shapeType ) {
		this.targetShapeType = shapeType;
	}

	private void setReady() {
		this.isReady = true;
	}

	private void setTarget( final IntroTarget introTarget ) {
		this.introTarget = introTarget;
	}

	private void setFocusType( final FocusType focusType ) {
		this.focusType = focusType;
	}

	private void setTargetShape( final Shape targetShape ) {
		this.targetShape = targetShape;
	}

	private void setFocusPadding( final int circlePadding ) {
		this.focusPadding = circlePadding;
	}

	private void setDismissOnTouch( final boolean dismissOnTouch ) {
		this.dismissOnTouch = dismissOnTouch;
	}

	private void setFocusGravity( final FocusGravity focusGravity ) {
		this.focusGravity = focusGravity;
	}

	private void setTvInfo( final CharSequence tvInfo ) {
		this.tvInfo.setText( tvInfo );
	}

	private void setColorTextInfo( final int colorTextViewInfo ) {
		tvInfo.setTextColor( colorTextViewInfo );
	}

	private void setTextInfoSize( final int unit , final int textViewInfoSize ) {
		this.tvInfo.setTextSize( unit , textViewInfoSize );
	}

	private void setTextInfoAlignment( final int textAlignment ) {
		this.tvInfo.setTextAlignment( textAlignment );
	}

	private void setTextInfoStyle( final int fontStyle ) {
		this.tvInfo.setTypeface( this.tvInfo.getTypeface() , fontStyle );
	}

	private void setTextInfoBackgroundColor( final int color ) {
		final GradientDrawable gradientDrawable;
		gradientDrawable = ( GradientDrawable ) AppCompatResources.getDrawable( getContext() , R.drawable.info_tv_background );
		if ( gradientDrawable != null ) {
			gradientDrawable.setColor( color );
			tvInfo.setBackground( gradientDrawable );
		}
	}

	private void enableInfoDialog() {
		this.isInfoEnabled = true;
	}

	private void setShowOnlyOnce( final boolean showOnlyOnce ) {
		this.isShowOnlyOnce = showOnlyOnce;
		if ( !showOnlyOnce ) {
			introPreferenceManager.reset( introId );
		}
	}

	private void enableDotView( final boolean enable ) {
		this.isDotViewEnabled = enable;
	}

	private void setUsageId( final String introLayoutId ) {
		this.introId = introLayoutId;
	}

	public void setListener( final IntroListener introListener ) {
		this.introListener = introListener;
	}

	private void setPerformClick( final boolean isPerformClick ) {
		this.isPerformClick = isPerformClick;
	}

	private void setConfiguration( final IntroConfig config ) {
		if ( config != null ) {
			if ( config.isTextInfoColorSet() ) setColorTextInfo( config.getTextInfoColor() );
			if ( config.isTextInfoSizeSet() ) setTextInfoSize( config.getTextInfoSizeUnit() , config.getTextInfoSize() );
			if ( config.isTextInfoBackgroundColorSet() ) setTextInfoBackgroundColor( config.getTextInfoBackgroundColor() );
			if ( config.isTextInfoAlignmentSet() ) setTextInfoAlignment( config.getTextInfoAlignment() );
			if ( config.isTextInfoStyleSet() ) setTextInfoStyle( config.getTextInfoStyle() );

			this.isFadeAnimationEnabled = config.isFadeAnimationEnabled();
			this.isDotViewEnabled = config.isDotViewEnabled();
			this.dismissOnTouch = config.isDismissOnTouch();
			this.focusGravity = config.getFocusGravity();
			this.startDelayMillis = config.getStartDelayMillis();
			this.focusPadding = config.getFocusPadding();
			this.maskColor = config.getMaskColor();
			this.focusType = config.getFocusType();
			this.layoutMargin = config.getLayoutMargin();
			this.layoutGravity = config.getLayoutGravity();
		}
	}

	// B U I L D E R

	public static class Builder {
		private final IntroWidget introWidget;

		public Builder( final Context context ) {
			this( context , R.style.IntroWidgetTheme );
		}

		public Builder( final Context context , final int resourceTheme ) {
			final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper( context , resourceTheme );
			introWidget = new IntroWidget( contextThemeWrapper );
		}

		public Builder setMaskColor( final int maskColor ) {
			introWidget.setMaskColor( maskColor );
			return this;
		}

		public Builder setDelayMillis( final int delayMillis ) {
			introWidget.setStartDelay( delayMillis );
			return this;
		}

		public Builder enableFadeAnimation( final boolean isFadeAnimationEnabled ) {
			introWidget.enableFadeAnimation( isFadeAnimationEnabled );
			return this;
		}

		public Builder setTargetShapeType( final ShapeType shapeType ) {
			introWidget.setTargetShapeType( shapeType );
			return this;
		}

		public Builder setFocusType( final FocusType focusType ) {
			introWidget.setFocusType( focusType );
			return this;
		}

		public Builder setFocusGravity( final FocusGravity focusGravity ) {
			introWidget.setFocusGravity( focusGravity );
			return this;
		}

		public Builder setTarget( final View view ) {
			final IntroTarget introTarget = new IntroTarget( view , introWidget );
			introWidget.setTarget( introTarget );
			return this;
		}

		public Builder setTargetPadding( final int padding ) {
			introWidget.setFocusPadding( padding );
			return this;
		}

		public Builder setTextColor( final int textColor ) {
			introWidget.setColorTextInfo( textColor );
			return this;
		}

		public Builder setInfoText( final CharSequence infoText ) {
			introWidget.enableInfoDialog();
			introWidget.setTvInfo( infoText );
			return this;
		}

		public Builder setTextViewInfoSize( final int unit , final int textSize ) {
			introWidget.setTextInfoSize( unit , textSize );
			return this;
		}

		public Builder setTextViewInfoAlignment( final int textAlignment ) {
			introWidget.setTextInfoAlignment( textAlignment );
			return this;
		}

		public Builder dismissOnTouch( final boolean dismissOnTouch ) {
			introWidget.setDismissOnTouch( dismissOnTouch );
			return this;
		}

		public Builder setUsageId( final String introLayoutId ) {
			introWidget.setUsageId( introLayoutId );
			return this;
		}

		public Builder enableDotView( final boolean enable ) {
			introWidget.enableDotView( enable );
			return this;
		}

		public Builder setShowOnlyOnce( final boolean showOnlyOnce ) {
			introWidget.setShowOnlyOnce( showOnlyOnce );
			return this;
		}

		public Builder setListener( final IntroListener introListener ) {
			introWidget.setListener( introListener );
			return this;
		}

		public Builder performClick( final boolean isPerformClick ) {
			introWidget.setPerformClick( isPerformClick );
			return this;
		}

		public Builder setConfig( final IntroConfig configuration ) {
			introWidget.setConfiguration( configuration );
			return this;
		}

		public IntroWidget build() {
			return introWidget;
		}

		public void show( final Activity activity ) {
			final IntroWidget introWidget = build();
			introWidget.show( activity );
		}

		public void show( final Dialog dialog ) {
			final IntroWidget introWidget = build();
			introWidget.show( dialog );
		}
	}
}
