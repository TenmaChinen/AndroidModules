package com.softramen.modules.dialogs_activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.softramen.customdialogs.DialogClippedOverlay;
import com.softramen.modules.R;

public class DialogClippedOverlayActivity extends AppCompatActivity {
    private final String TAG = "DIALOG_CLIPPED_ACTIVITY";

    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dialog_clipped_overlay );
        onClick( null );
    }

    public void onClick( final View view ) {
        final String message = "Are you sure?";
        final DialogClippedOverlay dialogClippedOverlay = new DialogClippedOverlay( message );
        dialogClippedOverlay.setCallback( customDialogCallback );
        dialogClippedOverlay.show( getSupportFragmentManager() , "CustomDialog" );
    }

    private final DialogClippedOverlay.Callback customDialogCallback = new DialogClippedOverlay.Callback() {
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