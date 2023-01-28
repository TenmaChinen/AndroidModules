package com.softramen.modules.dialogs_activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.softramen.customdialogs.AnnounceDialog;
import com.softramen.modules.R;
import com.softramen.modules.databinding.ActivityAnnounceDialogBinding;

public class AnnounceDialogActivity extends AppCompatActivity {
    private final String TAG = "ANNOUNCE_ACTIVITY";

    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_announce_dialog );

        final ActivityAnnounceDialogBinding binding = DataBindingUtil.setContentView( this , R.layout.activity_announce_dialog );
        binding.setClickListener( onClickListener );

        showAnnounceDialog();
    }

    private final View.OnClickListener onClickListener = v -> {
        showAnnounceDialog();
    };

    private void showAnnounceDialog() {
        final String announce = "Announce Dialog";
        final AnnounceDialog announceDialog = new AnnounceDialog( announce );
        announceDialog.setCallback( new AnnounceDialog.Callback() {
            @Override
            public void onAnnounceFinish() {
                Log.d( TAG , "onAnnounceFinish" );
            }
        } );
        announceDialog.show( getSupportFragmentManager() , "AnnounceDialog" );
    }


}