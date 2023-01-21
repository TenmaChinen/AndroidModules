package com.softramen.customdialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CustomDialog extends DialogFragment {
    private final String TAG = "CUSTOM_DIALOG";
    private String message;

    private Callback callback;
    private boolean onStartExecuted = false;

    public CustomDialog() {
        // Default constructor required for DialogFragment ( Sometimes )
    }

    public CustomDialog( final String message ) {
        this.message = message;
    }

    public void setCallback( final Callback callback ) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( @Nullable Bundle savedInstanceState ) {
        final Dialog dialog = super.onCreateDialog( savedInstanceState );
        dialog.setCancelable( false );
        dialog.setCanceledOnTouchOutside( false );
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        final View view = inflater.inflate( R.layout.dialog_custom , container , false );
        final TextView tvMessage = view.findViewById( R.id.tv_message );
        final TextView btnPositive = view.findViewById( R.id.btn_positive );
        final TextView btnNegative = view.findViewById( R.id.btn_negative );

        tvMessage.setText( message );

        btnPositive.setOnClickListener( v -> {
            if ( callback != null ) callback.onPositiveButtonClicked();
            dismiss();
        } );
        btnNegative.setOnClickListener( v -> {
            if ( callback != null ) callback.onNegativeButtonClicked();
            dismiss();
        } );
        return view;
    }

    @Override
    public void onStart() {
        if ( onStartExecuted ) return;
        onStartExecuted = true;

        final Dialog dialog = getDialog();
        final int width = ( int ) ( Resources.getSystem().getDisplayMetrics().widthPixels * 0.8 );
        // final int height = (int)(Resources.getSystem().getDisplayMetrics().heightPixels * 0.6);
        if ( dialog != null ) {
            final Window window = dialog.getWindow();
            window.setBackgroundDrawableResource( R.drawable.dialog_round_background );
            window.setLayout( width , -2 );
            window.setWindowAnimations( R.style.CustomDialogStyle );
        }
        super.onStart();
    }


    @Override
    public void onCancel( @NonNull DialogInterface dialog ) {
        super.onCancel( dialog );
        if ( callback != null ) {
            callback.onCancel();
        }
    }

    public interface Callback {
        default void onPositiveButtonClicked() {

        }

        default void onNegativeButtonClicked() {

        }

        default void onCancel() {

        }
    }
}