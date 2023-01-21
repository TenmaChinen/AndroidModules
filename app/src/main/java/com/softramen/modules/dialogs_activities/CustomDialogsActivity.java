package com.softramen.modules.dialogs_activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.softramen.customdialogs.CustomDialog;
import com.softramen.modules.R;

public class CustomDialogsActivity extends AppCompatActivity {

    private final String TAG = "MAIN_ACTIVITY" ;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_custom_dialogs );
    }

    public void onClick( View view ) {
        final int viewId = view.getId();
        if ( viewId == R.id.btn_show ) {

            final String message = "Are you sure?";
            final CustomDialog customDialog = new CustomDialog( message );
            customDialog.setCallback( customDialogCallback );
            customDialog.show( getSupportFragmentManager() , "CustomDialog" );
        }
    }

    private final CustomDialog.Callback customDialogCallback = new CustomDialog.Callback() {
        @Override
        public void onPositiveButtonClicked() {
            Log.d( TAG , "onPositiveButtonClicked" );
        }

        @Override
        public void onNegativeButtonClicked() {
            Log.d( TAG , "onNegativeButtonClicked" );
        }

        @Override
        public void onCancel() {
            Log.d( TAG , "onCancel" );
        }
    };
}