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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.softramen.CustomDialogs.utils.AnimationListener;
import com.softramen.CustomDialogs.utils.Constants;
import com.softramen.customdialogs.R;

public class DialogRetry extends DialogFragment {

	public static final String REQUEST_CODE = "DIALOG_RETRY", METHOD_CODE = "METHOD_CODE";
	public static final int ON_CLICK_RETRY = 0, ON_CANCEL = 1;

	private Animation windowEnterAnimation, windowExitAnimation;
	private boolean onStartExecuted = false;
	private int callbackId = ON_CANCEL;
	private View inflatedView;
	private ImageView btnRetry;

	@Override
	public void onCreate( @Nullable final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setStyle( DialogFragment.STYLE_NO_FRAME , R.style.DialogBaseStyle );
		windowEnterAnimation = AnimationUtils.loadAnimation( getContext() , android.R.anim.fade_in );
		windowExitAnimation = AnimationUtils.loadAnimation( getContext() , android.R.anim.fade_out );
	}

	@NonNull
	@Override
	public Dialog onCreateDialog( @Nullable final Bundle savedInstanceState ) {
		final Dialog dialog = super.onCreateDialog( savedInstanceState );
		dialog.setCancelable( false );
		dialog.setCanceledOnTouchOutside( false );

		// Enables to touch behind activity things ( Warning : This causes back button to also finish activity without waiting dialog )
		final Window window = dialog.getWindow();
		window.setFlags( LayoutParams.FLAG_NOT_FOCUSABLE , LayoutParams.FLAG_NOT_FOCUSABLE );
		// To force disable default DialogFragment animations
		window.setWindowAnimations( R.style.DialogNullWindowAnimationStyle );
		window.setGravity( Gravity.BOTTOM );

		return dialog;
	}

	@Nullable
	@Override
	public View onCreateView( @NonNull final LayoutInflater inflater , @Nullable final ViewGroup container , @Nullable final Bundle savedInstanceState ) {
		inflatedView = inflater.inflate( R.layout.dialog_retry , container , false );
		btnRetry = inflatedView.findViewById( R.id.btn_retry );

		final View.OnClickListener clickListener = view -> {
			btnRetry.setOnClickListener( null );
			final int viewId = view.getId();
			if ( viewId == R.id.btn_retry ) callbackId = ON_CLICK_RETRY;
			startDismissAnimation();
		};

		btnRetry.setOnClickListener( clickListener );
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

		final Animation popAnimation = AnimationUtils.loadAnimation( getContext() , R.anim.pop_animation );
		popAnimation.setStartOffset( 100 );
		btnRetry.startAnimation( popAnimation );
	}

	@Override
	public void onCancel( @NonNull final DialogInterface dialogInterface ) {
		// This method will call dismiss method
		// super.onCancel( dialogInterface );
		startDismissAnimation();
	}

	@Override
	public void onDismiss( @NonNull final DialogInterface dialog ) {
		sendResults();
		super.onDismiss( dialog );
	}


	private void startDismissAnimation() {
		windowExitAnimation.setAnimationListener( ( AnimationListener ) animation -> inflatedView.post( this::dismiss ) );
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