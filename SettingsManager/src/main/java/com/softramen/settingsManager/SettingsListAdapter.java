package com.softramen.settingsManager;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Space;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.softramen.optionMenu.OptionMenu;
import com.softramen.settingsManager.SettingsItem.Type;
import com.softramen.switchCom.SwitchCom;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SettingsListAdapter extends BaseAdapter {
	private final LayoutInflater layoutInflater;
	private final Map<Integer, Object> changesMap = new HashMap<>();
	private final Map<Integer, SettingsItem> settingsItemMap;
	private final int[] mapKeys;
	private int textSizePixels = 80;

	public SettingsListAdapter( @NonNull final Context context , @NonNull final Map<Integer, SettingsItem> settingsItemMap ) {
		layoutInflater = LayoutInflater.from( context );
		this.settingsItemMap = settingsItemMap;
		mapKeys = getMapKeys( settingsItemMap );
	}

	private int[] getMapKeys( final Map<Integer, SettingsItem> settingsItemMap ) {
		int idx = 0;
		final int[] mapKeys = new int[ settingsItemMap.size() ];
		for ( final int key : settingsItemMap.keySet() ) {
			mapKeys[ idx++ ] = key;
		}
		return mapKeys;
	}

	public void updateSettingsChanges() {
		for ( final Entry<Integer, Object> entrySet : changesMap.entrySet() ) {
			final int key = entrySet.getKey();
			final SettingsItem settingsItem = settingsItemMap.get( key );
			if ( settingsItem != null ) {
				switch ( settingsItem.getItemType() ) {
					case Type.SPINNER:
						settingsItem.setOptionPosition( ( Integer ) entrySet.getValue() );
						break;
					case Type.SWITCH:
						settingsItem.setState( ( Boolean ) entrySet.getValue() );
				}
			}
		}
	}

	@Override
	public int getCount() {
		return settingsItemMap.size();
	}

	@Override
	public SettingsItem getItem( final int position ) {
		return settingsItemMap.get( mapKeys[ position ] );
	}

	@Override
	public long getItemId( final int position ) {
		return position;
	}

	@Override
	public int getItemViewType( final int position ) {
		return getItem( position ).getItemType();
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@NonNull
	@Override
	public View getView( final int itemPosition , @Nullable View convertView , @NonNull final ViewGroup parent ) {

		if ( convertView == null ) {
			final SettingsItem listItem = getItem( itemPosition );

			final TextView tvLabel;
			switch ( getItemViewType( itemPosition ) ) {

				case Type.SWITCH:
					convertView = layoutInflater.inflate( R.layout.item_settings_list_switch , parent , false );
					tvLabel = convertView.findViewById( R.id.tv_label_list_item );
					tvLabel.setTextSize( TypedValue.COMPLEX_UNIT_PX, textSizePixels );
					tvLabel.setText( listItem.getLabel() );
					final SwitchCom switchCom = convertView.findViewById( R.id.switch_settings_list_item );
					final int switchPad = 25;
					switchCom.setPadding( switchPad,switchPad,switchPad,switchPad);
					switchCom.setSwitchHeight( ( int ) (textSizePixels / 1.5f) );
					switchCom.setChecked( listItem.getState() );
					switchCom.setOnCheckedChangeListener( ( compoundButton , state ) -> {
						final int settingId = mapKeys[ itemPosition ];
						if ( state != listItem.getState() ) changesMap.put( settingId , state );
						else changesMap.remove( settingId );
					} );
					break;
				case Type.SPINNER:
					convertView = layoutInflater.inflate( R.layout.item_settings_list_spinner , parent , false );
					tvLabel = convertView.findViewById( R.id.tv_label_list_item );
					tvLabel.setTextSize( TypedValue.COMPLEX_UNIT_PX, textSizePixels );
					tvLabel.setText( listItem.getLabel() );
					final OptionMenu optionMenu = convertView.findViewById( R.id.spinner_settings_list_item );
					optionMenu.setPadding( 50,50,50,50 );
					optionMenu.setItems( listItem.getOptions() );
					optionMenu.setTextSize( TypedValue.COMPLEX_UNIT_PX, textSizePixels );
					optionMenu.setSelection( listItem.getOptionPosition() );
					optionMenu.setOnItemSelectedListener( ( OnItemSelectedListener ) ( adapterView , view , optionPosition , l ) -> {
						final int settingId = mapKeys[ itemPosition ];
						if ( optionPosition != listItem.getOptionPosition() ) changesMap.put( settingId , optionPosition );
						else changesMap.remove( settingId );
					} );
					break;
				default:
					convertView = new Space( parent.getContext() );
			}
		}
		convertView.setPadding( 0 , 15 , 0 , 15 );
		return convertView;
	}
}
