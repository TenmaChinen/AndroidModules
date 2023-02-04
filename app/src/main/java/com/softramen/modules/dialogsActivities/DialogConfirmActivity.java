package com.softramen.modules.dialogsActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import com.softramen.dialogsCustom.DialogConfirm;
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
		final FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.setFragmentResultListener( DialogConfirm.REQUEST_CODE , this , fragmentResultListener );
		dialogConfirm.show( fragmentManager , "DialogConfirm" );
	}

	private final FragmentResultListener fragmentResultListener = new FragmentResultListener() {
		@Override
		public void onFragmentResult( @NonNull final String requestCode , @NonNull final Bundle result ) {
			if ( requestCode.equals( DialogConfirm.REQUEST_CODE ) ) {
				final int methodCode = result.getInt( DialogConfirm.METHOD_CODE );
				Log.d( TAG , "onFragmentResult > Result : " + methodCode );
				switch ( methodCode ) {
					case DialogConfirm.ON_CLICK_POSITIVE:
						Log.d( TAG , "onClickPositive" );
						break;
					case DialogConfirm.ON_CLICK_NEGATIVE:
						Log.d( TAG , "onClickNegative" );
						break;
					case DialogConfirm.ON_CANCEL:
						Log.d( TAG , "onCancel" );
				}
			}
		}
	};
}