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

import com.softramen.CustomDialogs.DialogRetry;
import com.softramen.modules.R;
import com.softramen.modules.databinding.ActivityRetryDialogBinding;

public class DialogRetryActivity extends AppCompatActivity {

    private final String TAG = "DIALOG_RETRY_ACTIVITY" ;

    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_retry_dialog );

        final ActivityRetryDialogBinding binding = DataBindingUtil.setContentView( this , R.layout.activity_retry_dialog );
        binding.setClickListener( onClickListener );
    }

    private final View.OnClickListener onClickListener = v -> {
        showAnnounceDialog();
    };

    public void showAnnounceDialog() {
        final DialogRetry dialogRetry = new DialogRetry();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final LifecycleOwner lifecycleOwner = this;
        fragmentManager.setFragmentResultListener( DialogRetry.REQUEST_CODE, lifecycleOwner, fragmentResultListener  );
        dialogRetry.show( getSupportFragmentManager() , "DialogRetry" );
    }

    private final FragmentResultListener fragmentResultListener = new FragmentResultListener() {
        @Override
        public void onFragmentResult( @NonNull final String requestCode , @NonNull final Bundle result ) {
            if ( requestCode.equals( DialogRetry.REQUEST_CODE ) ) {
                final int methodCode = result.getInt( DialogRetry.METHOD_CODE );
                switch ( methodCode ) {
                    case DialogRetry.ON_CLICK_RETRY:
                        Log.d( TAG , "ON_CLICK_RETRY" );
                        break;
                    case DialogRetry.ON_CANCEL:
                        Log.d( TAG , "ON_CANCEL" );
                }
            }
        }
    };
}