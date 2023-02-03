package com.softramen.CustomDialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.softramen.customdialogs.R;

public class RetryDialog extends DialogFragment {
	private final String TAG = "RETRY_DIALOG";

	private Callback callback;

	public RetryDialog() {
		// Default constructor required for DialogFragment ( Sometimes )
	}

	public void setCallback( final Callback callback ) {
		this.callback = callback;
	}

	@Nullable
	@Override
	public View onCreateView( @NonNull final LayoutInflater inflater , @Nullable final ViewGroup container , @Nullable final Bundle savedInstanceState ) {
		Log.d( TAG , "onCreateView" );
		final View view = inflater.inflate( R.layout.dialog_retry , container , false );
		final ImageView btnRetry = view.findViewById( R.id.btn_retry );
		btnRetry.setOnClickListener( v -> {
			if ( callback != null ) callback.onClickRetryButton();
			dismiss();
		} );
		return view;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog( @Nullable final Bundle savedInstanceState ) {
		final Dialog dialog = super.onCreateDialog( savedInstanceState );
		dialog.setCancelable( false );
		dialog.setCanceledOnTouchOutside( false );
		return dialog;
	}

	@Override
	public void onCancel( @NonNull final DialogInterface dialog ) {
		super.onCancel( dialog );
		if ( callback != null ) {
			callback.onCancel();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d( TAG , "onStart" );
		final Dialog dialog = getDialog();
		if ( dialog != null ) {
			dialog.getWindow().setBackgroundDrawable( null );
		}
		final ImageView btnRetry = getView().findViewById( R.id.btn_retry );
		final Animation popAnimation = AnimationUtils.loadAnimation( getContext() , R.anim.pop_animation );
		popAnimation.setStartOffset( 100 );
		btnRetry.startAnimation( popAnimation );
	}

	public interface Callback {
		default void onClickRetryButton() {

		}

		default void onCancel() {

		}
	}


	@Override
	public void onAttach( @NonNull final Context context ) {
		super.onAttach( context );
		final View view = getView();
	}
}