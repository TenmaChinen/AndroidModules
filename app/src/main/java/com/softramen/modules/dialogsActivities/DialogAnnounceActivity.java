package com.softramen.modules.dialogsActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LifecycleOwner;
import com.softramen.dialogsCustom.DialogAnnounce;
import com.softramen.dialogsCustom.utils.DialogConstants;
import com.softramen.modules.R;
import com.softramen.modules.databinding.ActivityDialogAnnounceBinding;

public class DialogAnnounceActivity extends AppCompatActivity {
	private final String TAG = "ANNOUNCE_ACTIVITY";

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_dialog_announce );

		final ActivityDialogAnnounceBinding bind;
		bind = DataBindingUtil.setContentView( this , R.layout.activity_dialog_announce );
		bind.setClickListener( onClickListener );
		showAnnounceDialog();
	}

	private final View.OnClickListener onClickListener = v -> {
		showAnnounceDialog();
	};

	private void showAnnounceDialog() {
		final String message = "Announcement\nMessage";
		final DialogAnnounce dialogAnnounce = DialogAnnounce.newInstance( message );
		final FragmentManager fragmentManager = getSupportFragmentManager();
		final LifecycleOwner lifecycleOwner = this;
		fragmentManager.setFragmentResultListener( DialogAnnounce.REQUEST_CODE, lifecycleOwner, fragmentResultListener );
		dialogAnnounce.show( fragmentManager , "DialogAnnounce" );
	}

	private final FragmentResultListener fragmentResultListener = new FragmentResultListener() {
	    @Override
	    public void onFragmentResult( @NonNull final String requestCode , @NonNull final Bundle result ) {
	        if ( requestCode.equals( DialogAnnounce.REQUEST_CODE ) ) {
	            final int methodCode = result.getInt( DialogConstants.METHOD_CODE );
	            switch ( methodCode ) {
					case DialogConstants.ON_FINISH_ANNOUNCE:
	                    Log.d( TAG , "ON_FINISH_ANNOUNCE" );
	                    break;
	                case DialogConstants.ON_CANCEL:
	                    Log.d( TAG , "ON_CANCEL" );
	            }
	        }
	    }
	};

}