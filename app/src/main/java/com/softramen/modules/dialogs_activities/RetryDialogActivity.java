package com.softramen.modules.dialogs_activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.softramen.customdialogs.RetryDialog;
import com.softramen.modules.R;
import com.softramen.modules.databinding.ActivityRetryDialogBinding;

public class RetryDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_retry_dialog );

        final ActivityRetryDialogBinding binding = DataBindingUtil.setContentView( this , R.layout.activity_retry_dialog );
        binding.setClickListener( onClickListener );
    }

    private final View.OnClickListener onClickListener = v -> {
        showAnnounceDialog();
    };

    public void showAnnounceDialog() {
        final RetryDialog retryDialog = new RetryDialog();
        retryDialog.setCallback( retryDialogCallback );
        retryDialog.show( getSupportFragmentManager() , "RetryDialog" );
    }

    private final RetryDialog.Callback retryDialogCallback = new RetryDialog.Callback() {
        @Override
        public void onClickRetryButton() {
            RetryDialog.Callback.super.onClickRetryButton();
        }

        @Override
        public void onCancel() {
            RetryDialog.Callback.super.onCancel();
        }
    };
}