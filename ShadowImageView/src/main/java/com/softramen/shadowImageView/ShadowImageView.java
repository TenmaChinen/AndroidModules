package com.softramen.shadowImageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class ShadowImageView extends AppCompatImageView {
	private final Paint shadowPaint = new Paint();
	private final Rect srcBounds = new Rect();
	private int shadowColor, shadowRadius;
	private Bitmap alphaBitmap = null;

	public ShadowImageView( final Context context ) {
		super( context );
		init( null , 0 );
	}
	public ShadowImageView( final Context context , @Nullable final AttributeSet attrs ) {
		super( context , attrs );
		init( attrs , 0 );
	}
	public ShadowImageView( final Context context , @Nullable final AttributeSet attrs , final int defStyleAttr ) {
		super( context , attrs , defStyleAttr );
		init( attrs , defStyleAttr );
	}

	private void init( final AttributeSet attributeSet , final int defStyleAttr ) {
		final TypedArray typedArray;
		typedArray = getContext().obtainStyledAttributes( attributeSet , R.styleable.ShadowImageView , defStyleAttr , 0 );
		shadowColor = typedArray.getColor( R.styleable.ShadowImageView_shadowColor , 0x99000000 );
		shadowRadius = ( int ) typedArray.getDimension( R.styleable.ShadowImageView_shadowRadius , 25 );
		typedArray.recycle();
		initPaint();
		adjustPadding();
		setLayerType( LAYER_TYPE_SOFTWARE , null );

		// Ensure image matrix is updated after onMeasure and being visible to get resulting source image bounds
		getViewTreeObserver().addOnGlobalLayoutListener( new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				final Drawable drawable = getDrawable();
				if ( drawable != null && getVisibility() == VISIBLE ) {
					loadSrcBounds( drawable );
					alphaBitmap = createShadowBitmap( drawable );
					getViewTreeObserver().removeOnGlobalLayoutListener( this );
				}
			}
		} );
	}

	private void initPaint() {
		shadowPaint.setColor( shadowColor );
		shadowPaint.setMaskFilter( new BlurMaskFilter( shadowRadius , Blur.NORMAL ) );
		shadowPaint.setStyle( Paint.Style.FILL );
		shadowPaint.setAntiAlias( true );
		final PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode( Mode.DST_OVER );
		shadowPaint.setXfermode( porterDuffXfermode );
	}

	private void adjustPadding() {
		final int padBottom = Math.max( shadowRadius , getPaddingBottom() );
		final int padTop = Math.max( shadowRadius , getPaddingTop() );
		final int padLeft = Math.max( shadowRadius , getPaddingLeft() );
		final int padRight = Math.max( shadowRadius , getPaddingRight() );
		this.setPadding( padLeft , padTop , padRight , padBottom );
	}

	@Override
	protected void onDraw( final Canvas canvas ) {
		final Drawable drawable = getDrawable();
		if ( drawable == null || alphaBitmap == null ) {
			super.onDraw( canvas );
			return;
		}

		canvas.saveLayer( null , null , Canvas.ALL_SAVE_FLAG );
		super.onDraw( canvas );
		canvas.drawBitmap( alphaBitmap , srcBounds.left , srcBounds.top , shadowPaint );
		canvas.restore();
	}

	private void loadSrcBounds( final Drawable srcDrawable ) {
		final Matrix matrix = getImageMatrix();
		final Rect bounds = srcDrawable.getBounds();
		final RectF boundsF = new RectF( bounds );
		matrix.mapRect( boundsF );
		boundsF.round( srcBounds );

		// Shadow Bounds adjustment when user sets padding greater than shadowRadius
		srcBounds.top += Math.abs( shadowRadius - getPaddingTop() );
		srcBounds.bottom += Math.abs( shadowRadius - getPaddingBottom() );
		srcBounds.left += Math.abs( shadowRadius - getPaddingLeft() );
		srcBounds.right += Math.abs( shadowRadius - getPaddingRight() );
	}

	private Bitmap createShadowBitmap( final Drawable drawable ) {
		// Create bitmap shadow slightly bigger than source image to let space for blur
		final int pad = shadowRadius;
		final Rect shadowBounds = new Rect( pad , pad , srcBounds.width() + pad , srcBounds.height() + pad );
		final Drawable newDrawable = drawable.getConstantState().newDrawable();
		final Bitmap bitmap = Bitmap.createBitmap( shadowBounds.right , shadowBounds.bottom , Bitmap.Config.ARGB_8888 );
		final Canvas canvas = new Canvas( bitmap );
		newDrawable.setBounds( shadowBounds );
		newDrawable.draw( canvas );
		return bitmap.extractAlpha();
	}
}
