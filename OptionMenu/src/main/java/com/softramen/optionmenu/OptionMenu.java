package com.softramen.optionMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.SpinnerAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

public class OptionMenu extends AppCompatSpinner {
	private final String TAG = "OPTION_MENU";

	public OptionMenuAttrs optionMenuAttrs;
	private OptionMenuAdapter optionMenuAdapter;

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

	@Override
	public void setPadding( final int left , final int top , final int right , final int bottom ) {
		super.setPadding( left , top , right , bottom );
		optionMenuAttrs.setPadding( left , top , right , bottom );
	}

	@Override
	public void setSelection( final int position , final boolean animate ) {
		super.setSelection( position , animate );
		if ( optionMenuAdapter != null ) {
			optionMenuAdapter.setSelectedPosition( position );
		}
	}

	@Override
	public void setSelection( final int position ) {
		super.setSelection( position );
		// This method is called automatically without listener when item is selected
		if ( optionMenuAdapter != null ) {
			optionMenuAdapter.setSelectedPosition( position );
		}
	}

	public void setDropdownPadding( final int padding ) {
		optionMenuAttrs.setDropdownPadding( padding );
	}

	private void setDefaults() {
		super.setBackgroundColor( Color.TRANSPARENT );
	}

	private void setAttributes( final AttributeSet attributeSet ) {

		try {
			final TypedArray typedArray;
			typedArray = getContext().obtainStyledAttributes( attributeSet , R.styleable.OptionMenu );
			optionMenuAttrs = new OptionMenuAttrs( typedArray , this );
			typedArray.recycle();
		}
		catch ( final Exception error ) {
			Log.e( TAG , "setAttributes > Error : " + error );
		}
	}

	public void setItems( final String[] stringArray ) {
		optionMenuAdapter = new OptionMenuAdapter( getContext() , stringArray , optionMenuAttrs );
		super.setAdapter( optionMenuAdapter );

		post( () -> {
			final View view = getChildAt( 0 );
			setDropDownVerticalOffset( view.getMeasuredHeight() + view.getPaddingBottom() );
		} );
	}


	public void setTextSize( final int unit , final float size ) {
		optionMenuAttrs.textSize = ( int ) TypedValue.applyDimension( unit , size , getResources().getDisplayMetrics() );
	}

	public void setTextStyle( final int textStyle ) {
		optionMenuAttrs.textStyle = textStyle;
	}

	public static class OptionMenuAttrs {
		protected final int textColor, textBackgroundColor, dropdownBackgroundColor, selectedBackgroundColor;
		protected final int textGravity, dropDownTextGravity;

		protected int padLeft, padTop, padRight, padBottom, dropdownPadding;
		protected int textStyle, textSize;

		public OptionMenuAttrs( final TypedArray typedArray , final AppCompatSpinner appCompatSpinner ) {
			textColor = typedArray.getColor( R.styleable.OptionMenu_textColor , Color.WHITE );
			textSize = typedArray.getDimensionPixelSize( R.styleable.OptionMenu_textSize , 14 );
			textBackgroundColor = typedArray.getColor( R.styleable.OptionMenu_backgroundColor , Color.TRANSPARENT );
			dropdownBackgroundColor = typedArray.getColor( R.styleable.OptionMenu_dropDownBackgroundColor , 0xFF474247 );
			selectedBackgroundColor = typedArray.getColor( R.styleable.OptionMenu_selectedBackgroundColor , 0xFF2E292E );
			textStyle = typedArray.getInt( R.styleable.OptionMenu_textStyle , Typeface.NORMAL );
			textGravity = typedArray.getInt( R.styleable.OptionMenu_textGravity , Gravity.CENTER );
			dropDownTextGravity = typedArray.getInt( R.styleable.OptionMenu_dropDownTextGravity , Gravity.CENTER );
			dropdownPadding = typedArray.getDimensionPixelSize( R.styleable.OptionMenu_dropDownPadding , 10 );

			padLeft = appCompatSpinner.getPaddingLeft();
			padTop = appCompatSpinner.getPaddingTop();
			padRight = appCompatSpinner.getPaddingRight();
			padBottom = appCompatSpinner.getPaddingBottom();
		}

		public void setPadding( final int left , final int top , final int right , final int bottom ) {
			padLeft = left;
			padTop = top;
			padRight = right;
			padBottom = bottom;
		}

		public void setDropdownPadding( final int dropdownPadding ) {
			this.dropdownPadding = dropdownPadding;
		}
	}
}
