package com.softramen.introView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
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

	private int maskColor = Constants.DEFAULT_MASK_COLOR;

	// IntroWidget will start showing after delayMillis seconds passed
	private long delayMillis = Constants.DEFAULT_DELAY_MILLIS;

	// No draw IntroWidget until isReady field set to true
	private boolean isReady = false;

	// Show/Dismiss IntroWidget with fade in/out animations if this is enabled.
	private boolean isFadeAnimationEnabled = true;

	private final long fadeAnimationDuration = Constants.DEFAULT_FADE_DURATION;

	// targetShape focusType on introTarget and clear circle to focusType
	private Shape targetShape;


	// FocusType Type
	private FocusType focusType = FocusType.ALL;
	private FocusGravity focusGravity = FocusGravity.CENTER;
	private IntroTarget introTarget;
	private Paint eraser;

	// Used to delay IntroWidget
	private Handler handler;
	private Bitmap focusBitmap;

	private int focusPadding = Constants.DEFAULT_PADDING;
	private int layoutWidth;
	private int layoutHeight;

	private TextView tvInfo;
	private View dotView;

	private boolean dismissOnTouch = false;
	private boolean isInfoEnabled = false;
	private boolean isDotViewEnabled = false;

	// To show Intro only the first time
	private PreferenceManager preferenceManager;

	// Check using this Id whether user learned or not.
	private String introViewId;

	// When layout completed set this true -> Otherwise onGlobalLayoutListener stuck on loop.
	private boolean isLayoutCompleted = false;

	// Notify user when IntroWidget is dismissed
	private IntroListener introListener;

	// To perform click on View after user touch dismiss the introView
	private boolean isPerformClick = false;

	// Disallow this IntroWidget from showing up more than once at a time
	private boolean isIdempotent = false;
	private boolean isDismissed = false;

	private ShapeType targetShapeType = ShapeType.CIRCLE;
	private boolean usesCustomShape = false;

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

		handler = new Handler();
		preferenceManager = new PreferenceManager( context );

		eraser = new Paint();
		eraser.setColor( 0xFFFFFFFF );
		eraser.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.CLEAR ) );
		eraser.setFlags( Paint.ANTI_ALIAS_FLAG );

		inflate( getContext() , R.layout.layout_intro , this );

		tvInfo = this.findViewById( R.id.tv_info );

		dotView = this.findViewById( R.id.iv_dot );
		dotView.measure( MeasureSpec.UNSPECIFIED , MeasureSpec.UNSPECIFIED );

		getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				targetShape.reCalculateAll();
				if ( targetShape != null && targetShape.getPoint().y != 0 && !isLayoutCompleted ) {
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

	private void createFocusBitmap( final int layoutWidth , final int layoutHeight ) {
		focusBitmap = Bitmap.createBitmap( layoutWidth , layoutHeight , Config.ARGB_4444 );
		final Canvas canvas = new Canvas( focusBitmap );
		canvas.drawColor( maskColor );
		targetShape.draw( canvas , eraser , focusPadding );
	}

	@Override
	protected void onMeasure( final int widthMeasureSpec , final int heightMeasureSpec ) {
		super.onMeasure( widthMeasureSpec , heightMeasureSpec );
		layoutWidth = getMeasuredWidth();
		layoutHeight = getMeasuredHeight();
		createFocusBitmap( layoutWidth , layoutHeight );
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

	// Show Intro ViewGroup fade In
	private void show( final Activity activity ) {

		if ( preferenceManager.isDisplayed( introViewId ) ) return;

		final Window window = activity.getWindow();
		final ViewGroup decorView = ( ViewGroup ) window.getDecorView();
		decorView.addView( this );

		setReady();
		handler.postDelayed( () -> {
			if ( isFadeAnimationEnabled ) {
				AnimatorFactory.animateFadeIn( IntroWidget.this , fadeAnimationDuration );

			} else {
				setVisibility( VISIBLE );
			}
		} , delayMillis );

		if ( isIdempotent ) {
			preferenceManager.setDisplayed( introViewId );
		}
	}

	// Dismiss Intro View
	public void dismissIntroLayout() {
		isDismissed = true;
		if ( !isIdempotent ) {
			preferenceManager.setDisplayed( introViewId );
		}

		if ( isFadeAnimationEnabled ) {
			AnimatorFactory.animateFadeOut( this , fadeAnimationDuration , animation -> afterDismissIntroLayout());
		} else {
			afterDismissIntroLayout();
		}
	}

	private void afterDismissIntroLayout() {
		setVisibility( GONE );
		removeIntroLayout();
		if ( introListener != null ) introListener.onUserClicked( introViewId );
	}

	private void removeIntroLayout() {
		if ( getParent() != null ) ( ( ViewGroup ) getParent() ).removeView( this );
	}

	// Locate Info TextView above/below the TargetShape.

	private void setInfoLayout() {

		handler.post( () -> {
			isLayoutCompleted = true;

			final Point targetPoint = targetShape.getPoint();
			final int offsetY = ( int ) ( targetShape.getHeight() / 1.5f );
			final int infoPosY;

			if ( targetPoint.y < layoutHeight / 2 ) {
				infoPosY = targetPoint.y + offsetY;
			} else {
				infoPosY = targetPoint.y - offsetY - tvInfo.getMeasuredHeight();
			}
			tvInfo.setY( infoPosY );
			tvInfo.postInvalidate();
			tvInfo.setVisibility( VISIBLE );
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

	private void setDelay( final int delayMillis ) {
		this.delayMillis = delayMillis;
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

	private void setShape( final Shape shape ) {
		this.targetShape = shape;
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

	private void setTextInfoSize( final int textViewInfoSize ) {
		this.tvInfo.setTextSize( TypedValue.COMPLEX_UNIT_SP , textViewInfoSize );
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

	private void setIdempotent( final boolean idempotent ) {
		this.isIdempotent = idempotent;
	}

	private void enableDotView( final boolean enable ) {
		this.isDotViewEnabled = enable;
	}

	public void setConfiguration( final IntroConfig config ) {

		if ( config != null ) {
			if ( config.isTextInfoColorSet() ) setColorTextInfo( config.getTextInfoColor() );
			if ( config.isTextInfoSizeSet() ) setTextInfoSize( config.getTextInfoSize() );
			if ( config.isTextInfoBackgroundColorSet() ) setTextInfoBackgroundColor( config.getTextInfoBackgroundColor() );
			if ( config.isTextInfoStyleSet() ) setTextInfoStyle( config.getTextInfoStyle() );

			this.isFadeAnimationEnabled = config.isFadeAnimationEnabled();
			this.isDotViewEnabled = config.isDotViewEnabled();
			this.dismissOnTouch = config.isDismissOnTouch();
			this.focusGravity = config.getFocusGravity();
			this.delayMillis = config.getDelayMillis();
			this.focusPadding = config.getFocusPadding();
			this.maskColor = config.getMaskColor();
			this.focusType = config.getFocusType();
		}
	}

	private void setUsageId( final String introLayoutId ) {
		this.introViewId = introLayoutId;
	}

	private void setListener( final IntroListener introListener ) {
		this.introListener = introListener;
	}

	private void setPerformClick( final boolean isPerformClick ) {
		this.isPerformClick = isPerformClick;
	}


	public static class Builder {

		private final IntroWidget introWidget;
		private final Activity activity;

		public Builder( final Activity activity ) {
			this( activity , R.style.IntroWidgetTheme );
		}

		public Builder( final Activity activity , final int resourceTheme ) {
			this.activity = activity;
			final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper( activity , resourceTheme );
			introWidget = new IntroWidget( contextThemeWrapper );
		}

		public Builder setMaskColor( final int maskColor ) {
			introWidget.setMaskColor( maskColor );
			return this;
		}

		public Builder setDelayMillis( final int delayMillis ) {
			introWidget.setDelay( delayMillis );
			return this;
		}

		public Builder enableFadeAnimation( final boolean isFadeAnimationEnabled ) {
			introWidget.enableFadeAnimation( isFadeAnimationEnabled );
			return this;
		}

		public Builder setShape( final ShapeType shape ) {
			introWidget.setTargetShapeType( shape );
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
			introWidget.setTarget( new IntroTarget( view ) );
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

		public Builder setTextViewInfoSize( final int textSize ) {
			introWidget.setTextInfoSize( textSize );
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

		public Builder setIdempotent( final boolean idempotent ) {
			introWidget.setIdempotent( idempotent );
			return this;
		}

		public Builder setListener( final IntroListener introListener ) {
			introWidget.setListener( introListener );
			return this;
		}

		public Builder setCustomShape( final Shape shape ) {
			introWidget.usesCustomShape = true;
			introWidget.setShape( shape );
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
			if ( introWidget.usesCustomShape ) return introWidget;

			final Shape shape;
			if ( introWidget.targetShapeType == ShapeType.CIRCLE ) {
				shape = new Circle( introWidget.introTarget , introWidget.focusType , introWidget.focusGravity , introWidget.focusPadding );
			} else {
				shape = new Rect( introWidget.introTarget , introWidget.focusType , introWidget.focusGravity , introWidget.focusPadding );
			}

			introWidget.setShape( shape );
			return introWidget;
		}


		public void show() {
			build().show( activity );
		}
	}
}
