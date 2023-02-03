package com.softramen.CustomDialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.softramen.CustomDialogs.utils.AnimationListener;
import com.softramen.CustomDialogs.utils.Constants;
import com.softramen.customdialogs.R;
import org.jetbrains.annotations.Nullable;


public class DialogConfirm extends DialogFragment {
	private final String TAG = "CUSTOM_DIALOG";

	private static final String ARG_MESSAGE = "ARG_MESSAGE";

	private Animation windowEnterAnimation, windowExitAnimation;
	private View inflatedView;
	private int callbackId = -1;
	private String message;

	private Callback callback;

	// No Need to Define Constructor
	/// public DialogConfirm() {}

	// Use this to ensure fragment's state is properly preserved across configuration changes
	public static DialogConfirm newInstance( final String message ) {
		final DialogConfirm dialogConfirm = new DialogConfirm();
		final Bundle args = new Bundle();
		args.putString( ARG_MESSAGE , message );
		dialogConfirm.setArguments( args );
		return dialogConfirm;
	}

	@Override
	public void onCreate( @Nullable final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setStyle( DialogFragment.STYLE_NO_FRAME , R.style.DialogBaseStyle );

		// Retrieve arguments here
		if ( getArguments() != null ) {
			final Bundle args = getArguments();
			message = args.getString( ARG_MESSAGE );
		}

		windowEnterAnimation = AnimationUtils.loadAnimation( getContext() , R.anim.dialog_window_fade_in );
		windowExitAnimation = AnimationUtils.loadAnimation( getContext() , R.anim.dialog_window_fade_out );
	}

	@NonNull
	@Override
	public Dialog onCreateDialog( @Nullable final Bundle savedInstanceState ) {
		final Dialog dialog = super.onCreateDialog( savedInstanceState );
		dialog.setCancelable( false );
		dialog.setCanceledOnTouchOutside( false );

		final Window window = dialog.getWindow();

		// Enables to touch behind activity things ( Warning : This causes back button to also finish activity without waiting dialog )
		window.setFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE );
		// To force disable default DialogFragment animations
		window.setWindowAnimations( R.style.DialogNullWindowAnimationStyle );
		window.setGravity( Gravity.BOTTOM );
		return dialog;
	}

	@Nullable
	@Override
	public View onCreateView( @NonNull final LayoutInflater inflater , @Nullable final ViewGroup container , @Nullable final Bundle savedInstanceState ) {
		inflatedView = inflater.inflate( R.layout.dialog_confirm , container , false );
		final TextView tvMessage = inflatedView.findViewById( R.id.tv_message );
		final TextView btnPositive = inflatedView.findViewById( R.id.btn_positive );
		final TextView btnNegative = inflatedView.findViewById( R.id.btn_negative );

		tvMessage.setText( message );

		final View.OnClickListener clickListener = view -> {
			btnPositive.setOnClickListener( null );
			btnNegative.setOnClickListener( null );
			callbackId = view.getId();
			startDismissAnimation();
		};

		btnPositive.setOnClickListener( clickListener );
		btnNegative.setOnClickListener( clickListener );
		return inflatedView;
	}

	@Override
	public void onStart() {
		super.onStart();
		final Dialog dialog = getDialog();
		if ( dialog != null ) {
			final Window window = dialog.getWindow();
			window.setLayout( LayoutParams.MATCH_PARENT , Constants.Dialog.HEIGHT ); // Avoids cover AdMob Banners
			inflatedView.startAnimation( windowEnterAnimation );
		}
	}

	@Override
	public void onCancel( @NonNull final DialogInterface dialogInterface ) {
		// This method will call dismiss method
		// super.onCancel( dialogInterface );
		startDismissAnimation();
	}

	@Override
	public void onDismiss( @NonNull final DialogInterface dialog ) {
		super.onDismiss( dialog );
		if ( callback != null ) {
			if ( callbackId == R.id.btn_positive ) callback.onClickPositive();
			else if ( callbackId == R.id.btn_negative ) callback.onClickNegative();
			else if ( callbackId == -1 ) {
				callback.onCancel();
			}
		}
	}

	private void startDismissAnimation() {
		windowExitAnimation.setAnimationListener( ( AnimationListener ) animation -> dismiss() );
		inflatedView.startAnimation( windowExitAnimation );
	}

	public void setCallback( final Callback callback ) {
		this.callback = callback;
	}

	public interface Callback {
		default void onClickPositive() {}
		default void onClickNegative() {}
		default void onCancel() {}
	}
}