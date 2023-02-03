package com.softramen.optionmenu;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OptionMenuAdapter extends BaseAdapter {
	private final String TAG = "OPTION_MENU_ADAPTER";
	private final String[] stringArray;
	private final LayoutInflater layoutInflater;
	private final OptionMenu.OptionMenuAttrs attrs;
	private int selectedPosition = -1;

	public OptionMenuAdapter( final Context context , final String[] stringArray , final OptionMenu.OptionMenuAttrs attrs ) {
		layoutInflater = LayoutInflater.from( context );
		this.stringArray = stringArray;
		this.attrs = attrs;
	}

	@Override
	public int getCount() {
		return stringArray.length;
	}

	@Override
	public Object getItem( final int position ) {
		return stringArray[ position ];
	}

	@Override
	public long getItemId( final int position ) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		// Only One Type Of View is Returned for OptionMenu
		return 1;
	}

	@Override
	public int getItemViewType( final int position ) {
		// Only one Type of View which Type ID = 0
		return 0;
	}

	@Override
	public View getView( final int position , View convertView , final ViewGroup parent ) {
		Log.d( TAG , "getView > position : " + position );
		if ( convertView == null ) {
			convertView = layoutInflater.inflate( R.layout.option_menu_closed_item , parent , false );
			convertView.setBackgroundColor( attrs.textBackgroundColor );
			final TextView tvOptionMenuLabel = convertView.findViewById( R.id.tv_option_menu_label );
			final ImageView ivOptionMenuArrow = convertView.findViewById( R.id.iv_option_menu_arrow );
			tvOptionMenuLabel.setText( stringArray[ position ] );
			tvOptionMenuLabel.setTextSize( TypedValue.COMPLEX_UNIT_PX , attrs.textSize );
			tvOptionMenuLabel.setTextColor( attrs.textColor );
			tvOptionMenuLabel.setGravity( attrs.textGravity );
			// tvOptionMenuLabel.setPadding( attrs.textPadding , attrs.textPadding , attrs.textPadding , attrs.textPadding );

			ivOptionMenuArrow.setMaxHeight( attrs.textSize / 2 );
			ivOptionMenuArrow.setColorFilter( attrs.textColor );
		}

		if ( position != selectedPosition ) {
			selectedPosition = position;
		}

		return convertView;
	}

	@Override
	public View getDropDownView( final int position , View convertView , final ViewGroup parent ) {
		if ( convertView == null ) {
			convertView = layoutInflater.inflate( R.layout.option_menu_opened_item , parent , false );
			convertView.setBackgroundColor( attrs.dropdownBackgroundColor );
			final TextView tvOptionMenuLabel = convertView.findViewById( R.id.tv_option_menu_label );
			tvOptionMenuLabel.setText( stringArray[ position ] );
			tvOptionMenuLabel.setTextSize( TypedValue.COMPLEX_UNIT_PX , attrs.textSize );
			tvOptionMenuLabel.setTextColor( attrs.textColor );
			tvOptionMenuLabel.setGravity( attrs.textGravity );
			final int pad = attrs.dropDownTextPadding;
			tvOptionMenuLabel.setPadding( pad , pad , pad , pad );
		}

		if ( position == selectedPosition ) {
			convertView.setBackgroundColor( attrs.selectedBackgroundColor );
		}
		return convertView;
	}
}
