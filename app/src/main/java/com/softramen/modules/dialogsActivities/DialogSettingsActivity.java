package com.softramen.modules.dialogsActivities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LifecycleOwner;
import com.softramen.dialogsCustom.DialogSettings;
import com.softramen.dialogsCustom.utils.DialogConstants;
import com.softramen.modules.R;
import com.softramen.modules.databinding.ActivityDialogSettingsBinding;
import com.softramen.settingsManager.SettingsItem;
import com.softramen.settingsManager.SettingsItem.Type;
import com.softramen.settingsManager.SettingsManager;

public class DialogSettingsActivity extends AppCompatActivity {

	private final String TAG = "DIALOG_SETTINGS_ACT";
	private Context context;

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_dialog_settings );

		context = this;

		final ActivityDialogSettingsBinding activityDialogSettingsBinding;
		activityDialogSettingsBinding = DataBindingUtil.setContentView( this , R.layout.activity_dialog_settings );
		activityDialogSettingsBinding.setClickListener( onClickListener );

		SettingsManager.init();

		showDialogSettings();
	}

	private final View.OnClickListener onClickListener = v -> {
		final int viewId = v.getId();
		if ( viewId == R.id.button ) {
			showDialogSettings();
		}
	};

	private void showDialogSettings() {

		final DialogSettings dialogSettings = new DialogSettings();
		final FragmentManager fragmentManager = getSupportFragmentManager();
		final LifecycleOwner lifecycleOwner = this;
		fragmentManager.setFragmentResultListener( DialogSettings.REQUEST_CODE , lifecycleOwner , fragmentResultListener );
		dialogSettings.show( getSupportFragmentManager() , "DialogSettings" );
	}

	private final FragmentResultListener fragmentResultListener = new FragmentResultListener() {
		@Override
		public void onFragmentResult( @NonNull final String requestCode , @NonNull final Bundle result ) {
			if ( requestCode.equals( DialogSettings.REQUEST_CODE ) ) {
				final int methodCode = result.getInt( DialogConstants.METHOD_CODE );
				switch ( methodCode ) {
					case DialogConstants.ON_CLICK_SAVE:
						Log.d( TAG , "onClickSave" );
						// TODO : Update things here if needed
						// Toast.makeText( context , "Changes Saved" , Toast.LENGTH_SHORT ).show();
						final SettingsManager settingsManager = SettingsManager.getInstance();
						for ( final SettingsItem settingsItem : settingsManager.getSettingsItemMap().values() ) {
							switch ( settingsItem.getItemType() ) {
								case Type.SPINNER:
									Log.d( TAG , "onClickSave > Label : " + settingsItem.getLabel() + " | Option : " + settingsItem.getOptionText() );
									break;
								case Type.SWITCH:
									Log.d( TAG , "onClickSave > Label : " + settingsItem.getLabel() + " | Value : " + settingsItem.getState() );
							}
						}
						break;
					case DialogConstants.ON_CANCEL:
						Log.d( TAG , "onCancel" );
						Toast.makeText( context , "Changes not saved" , Toast.LENGTH_SHORT ).show();
				}
			}
		}
	};
}