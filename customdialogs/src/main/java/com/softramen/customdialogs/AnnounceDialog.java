package com.softramen.customdialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AnnounceDialog extends DialogFragment {
    private final String TAG = "ANNOUNCE_DIALOG";

    private Callback callback;
    private String announce;
    private TextView tvAnnounce;
    private Animation animation;
    private boolean onStartExecuted = false;

    public AnnounceDialog() {
        // Default constructor required for DialogFragment ( Sometimes )
    }

    public AnnounceDialog( final String announce ) {
        this.announce = announce;
    }

    public interface Callback {
        void onAnnounceFinish();
    }

    public void setCallback( final Callback callback ) {
        this.callback = callback;
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.d( TAG , "onCreate" );

        animation = AnimationUtils.loadAnimation( getContext() , R.anim.fade_in_fade_out_animation );
        animation.setAnimationListener( new AnimationListener() {

            @Override
            public void onAnimationEnd( Animation animation ) {
                if ( callback != null ) {
                    Log.d( TAG , "onAnimationEnd" );
                    dismiss();
                }
            }
        } );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( @Nullable Bundle savedInstanceState ) {
        Log.d( TAG , "onCreateDialog" );
        final Dialog dialog = super.onCreateDialog( savedInstanceState );
        dialog.setCancelable( false );
        dialog.setCanceledOnTouchOutside( false );
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        Log.d( TAG , "onCreateView" );

        final View view = inflater.inflate( R.layout.dialog_announce , container , false );
        view.setOnClickListener( v -> dismiss() );

        tvAnnounce = view.findViewById( R.id.tv_announce );
        tvAnnounce.setText( announce );
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if ( onStartExecuted ) return;
        onStartExecuted = true;

        Log.d( TAG , "onStart" );
        final Dialog dialog = getDialog();
        if ( dialog != null ) {
            final Window window = dialog.getWindow();
            window.setLayout( -1 , -1 );
            window.setBackgroundDrawable( null );
        }
        tvAnnounce.setAnimation( animation );
    }

    @Override
    public void onDismiss( @NonNull DialogInterface dialogInterface ) {
        Log.d( TAG , "onDismiss" );
        super.onDismiss( dialogInterface );
        cancelAnimation();
        if ( callback != null ) {
            callback.onAnnounceFinish();
        }
    }

    private void cancelAnimation() {
        animation.cancel();
        tvAnnounce.clearAnimation();
        animation.setAnimationListener( null );
    }
}