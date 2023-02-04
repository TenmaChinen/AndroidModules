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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.softramen.CustomDialogs.utils.AnimationListener;
import com.softramen.CustomDialogs.utils.Constants;
import com.softramen.customdialogs.R;
import org.jetbrains.annotations.Nullable;


public class DialogConfirm extends DialogFragment {
	private final String TAG = "DIALOG_CONFIRM";

	public static final String REQUEST_CODE = "DIALOG_CONFIRM", METHOD_CODE = "METHOD_CODE";
	public static final int ON_CLICK_POSITIVE = 0, ON_CLICK_NEGATIVE = 1, ON_CANCEL = 2;

	private static final String ARG_MESSAGE = "ARG_MESSAGE";

	private Animation windowEnterAnimation, windowExitAnimation;
	private boolean onStartExecuted = false;
	private int callbackId = ON_CANCEL;
	private View inflatedView;
	private String message;

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

		windowEnterAnimation = AnimationUtils.loadAnimation( getContext() , android.R.anim.fade_in );
		windowExitAnimation = AnimationUtils.loadAnimation( getContext() , android.R.anim.fade_out );

		// Retrieve arguments here
		if ( getArguments() != null ) {
			final Bundle args = getArguments();
			message = args.getString( ARG_MESSAGE );
		}

	}

	@NonNull
	@Override
	public Dialog onCreateDialog( @Nullable final Bundle savedInstanceState ) {
		final Dialog dialog = super.onCreateDialog( savedInstanceState );
		dialog.setCancelable( false );
		dialog.setCanceledOnTouchOutside( false );

		final Window window = dialog.getWindow();

		// Enables to touch behind activity things ( Warning : This causes back button to also finish activity without waiting dialog )
		// window.setFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE );
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
			final int viewId = view.getId();
			if ( viewId == R.id.btn_positive ) callbackId = ON_CLICK_POSITIVE;
			else if ( viewId == R.id.btn_negative ) callbackId = ON_CLICK_NEGATIVE;
			startDismissAnimation();
		};

		btnPositive.setOnClickListener( clickListener );
		btnNegative.setOnClickListener( clickListener );
		return inflatedView;
	}

	@Override
	public void onStart() {
		super.onStart();
		if ( onStartExecuted ) return;
		onStartExecuted = true;

		final Dialog dialog = getDialog();
		if ( dialog != null ) {
			final Window window = dialog.getWindow();
			window.setLayout( LayoutParams.MATCH_PARENT , Constants.Dialog.HEIGHT ); // Avoids cover AdMob Banners
			inflatedView.startAnimation( windowEnterAnimation );
		}
	}

	@Override
	public void onCancel( @NonNull final DialogInterface dialogInterface ) {
		// super.onCancel( dialogInterface ); This calls dismiss method
		startDismissAnimation();
	}

	@Override
	public void onDismiss( @NonNull final DialogInterface dialog ) {
		sendResults();
		super.onDismiss( dialog );
	}

	private void startDismissAnimation() {
		windowExitAnimation.setAnimationListener( ( AnimationListener ) animation -> {
			inflatedView.post( this::dismiss );
		} );
		inflatedView.startAnimation( windowExitAnimation );
	}

	private void sendResults() {
		final FragmentActivity fragmentActivity = getActivity();
		if ( fragmentActivity != null ) {
			final Bundle results = new Bundle();
			results.putInt( METHOD_CODE , callbackId );
			final FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
			fragmentManager.setFragmentResult( REQUEST_CODE , results );
		}
	}
}