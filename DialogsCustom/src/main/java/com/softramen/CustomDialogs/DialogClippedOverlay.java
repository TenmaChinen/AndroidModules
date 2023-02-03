package com.softramen.CustomDialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.softramen.CustomDialogs.utils.Constants;
import com.softramen.customdialogs.R;

public class DialogClippedOverlay extends DialogFragment {
	private final String TAG = "CUSTOM_DIALOG";

	private boolean onStartExecuted = false;
	private Callback callback;
	private String message;

	public DialogClippedOverlay() {
		// Default constructor required for DialogFragment ( Sometimes )
	}

	public DialogClippedOverlay( final String message ) {
		this.message = message;
	}

	public void setCallback( final Callback callback ) {
		this.callback = callback;
	}

	@Override
	public void onCreate( @Nullable final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setStyle( DialogFragment.STYLE_NO_FRAME , R.style.DialogBaseStyle );
	}

	@Nullable
	@Override
	public View onCreateView( @NonNull final LayoutInflater inflater , @Nullable final ViewGroup container , @Nullable final Bundle savedInstanceState ) {
		final View view = inflater.inflate( R.layout.dialog_gradient , container , false );
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

	@NonNull
	@Override
	public Dialog onCreateDialog( @Nullable final Bundle savedInstanceState ) {
		final Dialog dialog = super.onCreateDialog( savedInstanceState );
		dialog.setCancelable( false );
		dialog.setCanceledOnTouchOutside( false );
		dialog.getWindow().setBackgroundDrawableResource( R.drawable.dialog_gradient_overlay );
		return dialog;
	}

	@Override
	public void onStart() {
		super.onStart();
		if ( onStartExecuted ) return;
		onStartExecuted = true;

		final Dialog dialog = getDialog();
		if ( dialog != null ) {
			final Window window = dialog.getWindow();
			window.setLayout( LayoutParams.MATCH_PARENT , Constants.Dialog.HEIGHT );
			window.setGravity( Gravity.BOTTOM );
		}
	}

	@Override
	public void onCancel( @NonNull final DialogInterface dialog ) {
		super.onCancel( dialog );
		if ( callback != null ) {
			callback.onCancel();
		}
	}

	public interface Callback {
		default void onPositiveButtonClicked() {}
		default void onNegativeButtonClicked() {}
		default void onCancel() {}
	}
}