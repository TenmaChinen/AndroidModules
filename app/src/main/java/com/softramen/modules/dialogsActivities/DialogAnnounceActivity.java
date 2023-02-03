package com.softramen.modules.dialogsActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.softramen.CustomDialogs.DialogAnnounce;
import com.softramen.modules.R;
import com.softramen.modules.databinding.ActivityDialogAnnounceBinding;

public class DialogAnnounceActivity extends AppCompatActivity {
	private final String TAG = "ANNOUNCE_ACTIVITY";

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_dialog_announce );

		final ActivityDialogAnnounceBinding activityDialogAnnounceBinding;
		activityDialogAnnounceBinding = DataBindingUtil.setContentView( this , R.layout.activity_dialog_announce );
		activityDialogAnnounceBinding.setClickListener( onClickListener );
		showAnnounceDialog();
	}

	private final View.OnClickListener onClickListener = v -> {
		showAnnounceDialog();
	};

	private void showAnnounceDialog() {
		final String announce = "Announce Dialog";
		final DialogAnnounce dialogAnnounce = new DialogAnnounce( announce );
		dialogAnnounce.setCallback( new DialogAnnounce.Callback() {
			@Override
			public void onAnnounceFinish() {
				Log.d( TAG , "onAnnounceFinish" );
			}
		} );
		dialogAnnounce.show( getSupportFragmentManager() , "DialogAnnounce" );
	}


}