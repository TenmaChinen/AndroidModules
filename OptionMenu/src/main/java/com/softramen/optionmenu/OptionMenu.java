package com.softramen.optionmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.SpinnerAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

public class OptionMenu extends AppCompatSpinner {
	private final String TAG = "OPTION_MENU";

	public OptionMenuAttrs optionMenuAttrs;

	public OptionMenu( @NonNull final Context context ) {
		super( context , null );
	}

	public OptionMenu( @NonNull final Context context , final int mode ) {
		super( context , mode );
		// setDefaults();
	}

	public OptionMenu( @NonNull final Context context , @Nullable final AttributeSet attributeSet ) {
		super( context , attributeSet );
		setDefaults();
		setAttributes( attributeSet );
	}

	@Override
	public void setAdapter( final SpinnerAdapter adapter ) {
		// super.setAdapter();
	}

	@Override
	public void setBackgroundColor( final int color ) {
		// super.setBackgroundColor( color );
	}

	private void setDefaults() {
		super.setBackgroundColor( Color.TRANSPARENT );
	}

	private void setAttributes( final AttributeSet attributeSet ) {

		try {
			final TypedArray typedArray;
			typedArray = getContext().obtainStyledAttributes( attributeSet , R.styleable.OptionMenu );
			optionMenuAttrs = new OptionMenuAttrs( typedArray );
			typedArray.recycle();
		}
		catch ( final Exception error ) {
			Log.d( TAG , "Error : " + error );
		}
	}

	@Override
	protected void onMeasure( final int widthMeasureSpec , final int heightMeasureSpec ) {
		super.onMeasure( widthMeasureSpec , heightMeasureSpec );
	}
	public void setItems( final String[] stringArray ) {
		final OptionMenuAdapter optionMenuAdapter = new OptionMenuAdapter( getContext() , stringArray , optionMenuAttrs );
		super.setAdapter( optionMenuAdapter );

		post( () -> {
			final View view = getChildAt( 0 );
			setDropDownVerticalOffset( view.getMeasuredHeight() );
		} );
	}


	static class OptionMenuAttrs {
		protected final int textColor, textSize, textBackgroundColor, dropdownBackgroundColor, selectedBackgroundColor;
		protected final int textGravity, dropDownTextGravity;
		protected final int textPadding, dropDownTextPadding;

		public OptionMenuAttrs( final TypedArray typedArray ) {
			textColor = typedArray.getColor( R.styleable.OptionMenu_textColor , Color.WHITE );
			textSize = typedArray.getDimensionPixelSize( R.styleable.OptionMenu_textSize , 14 );
			textBackgroundColor = typedArray.getColor( R.styleable.OptionMenu_backgroundColor , Color.TRANSPARENT );
			dropdownBackgroundColor = typedArray.getColor( R.styleable.OptionMenu_dropDownBackgroundColor , 0xFF151515 );
			selectedBackgroundColor = typedArray.getColor( R.styleable.OptionMenu_selectedBackgroundColor , 0xFF202020 );
			textGravity = typedArray.getInt( R.styleable.OptionMenu_textGravity , Gravity.CENTER );
			dropDownTextGravity = typedArray.getInt( R.styleable.OptionMenu_dropDownTextGravity , Gravity.CENTER );
			textPadding = typedArray.getInt( R.styleable.OptionMenu_textPadding , 0 );
			dropDownTextPadding = typedArray.getInt( R.styleable.OptionMenu_dropDownTextPadding , 10 );
		}
	}
}
