package com.softramen.modules.dialogsActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.softramen.CustomDialogs.DialogConfirm;
import com.softramen.modules.R;

public class DialogConfirmActivity extends AppCompatActivity {

	private final String TAG = "DIALOG_CONFIRM_ACTIVITY";

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_dialog_confirm );
		showDialogConfirm();
	}

	public void onClick( final View view ) {
		final int viewId = view.getId();
		if ( viewId == R.id.btn_show ) {
			showDialogConfirm();
		}
	}

	private void showDialogConfirm() {
		final String message = "Are you sure?";
		final DialogConfirm dialogConfirm = DialogConfirm.newInstance( message );
		dialogConfirm.setCallback( customDialogCallback );
		dialogConfirm.show( getSupportFragmentManager() , "DialogConfirm" );
	}

	private final DialogConfirm.Callback customDialogCallback = new DialogConfirm.Callback() {
		@Override
		public void onClickPositive() {
			Log.d( TAG , "onPositiveButtonClicked" );
		}

		@Override
		public void onClickNegative() {
			Log.d( TAG , "onNegativeButtonClicked" );
		}

		@Override
		public void onCancel() {
			Log.d( TAG , "onCancel" );
		}
	};
}