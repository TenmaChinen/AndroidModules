package com.softramen.CustomDialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.softramen.CustomDialogs.utils.AnimationListener;
import com.softramen.CustomDialogs.utils.Constants;
import com.softramen.customdialogs.R;

public class DialogAnnounce extends DialogFragment {
	private final String TAG = "ANNOUNCE_DIALOG";

	public static final String REQUEST_CODE = "DIALOG_ANNOUNCE", METHOD_CODE = "METHOD_CODE";
	public static final int ON_FINISH_ANNOUNCE = 0, ON_CANCEL = 1;

	private static final String ARG_MESSAGE = "ARG_MESSAGE";

	private Animation windowEnterAnimation, windowExitAnimation;
	private boolean onStartExecuted = false;
	private int callbackId = ON_CANCEL;
	private TextView tvAnnounce;
	private Animation animation;
	private View inflatedView;
	private String message;

	// Use this to ensure fragment's state is properly preserved across configuration changes
	public static DialogAnnounce newInstance( final String message ) {
		final DialogAnnounce dialogAnnounce = new DialogAnnounce();
		final Bundle args = new Bundle();
		args.putString( ARG_MESSAGE , message );
		dialogAnnounce.setArguments( args );
		return dialogAnnounce;
	}

	@Override
	public void onCreate( @Nullable final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setStyle( DialogFragment.STYLE_NO_FRAME , R.style.DialogBaseStyle );

		windowEnterAnimation = AnimationUtils.loadAnimation( getContext() , android.R.anim.fade_in );
		windowExitAnimation = AnimationUtils.loadAnimation( getContext() , android.R.anim.fade_out );

		animation = AnimationUtils.loadAnimation( getContext() , R.anim.fade_in_fade_out_animation );
		animation.setAnimationListener( new AnimationListener() {
			@Override
			public void onAnimationEnd( final Animation animation ) {
				callbackId = ON_FINISH_ANNOUNCE;
				tvAnnounce.setVisibility( View.GONE );
				startDismissAnimation();
			}
		} );

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
		return dialog;
	}

	@Nullable
	@Override
	public View onCreateView( @NonNull final LayoutInflater inflater , @Nullable final ViewGroup container , @Nullable final Bundle savedInstanceState ) {
		inflatedView = inflater.inflate( R.layout.dialog_announce , container , false );
		inflatedView.setOnClickListener( v -> dismiss() );
		tvAnnounce = inflatedView.findViewById( R.id.tv_announce );
		tvAnnounce.setText( message );
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
			// window.setLayout( -1 , -1 );
			window.setLayout( LayoutParams.MATCH_PARENT , Constants.Dialog.HEIGHT ); // Avoids cover AdMob Banners
			inflatedView.startAnimation( windowEnterAnimation );
		}
		tvAnnounce.setAnimation( animation );
	}

	@Override
	public void onCancel( @NonNull final DialogInterface dialogInterface ) {
		// This method will call dismiss method
		// super.onCancel( dialogInterface );
		startDismissAnimation();
	}

	@Override
	public void onDismiss( @NonNull final DialogInterface dialog ) {
		animation.cancel();
		tvAnnounce.clearAnimation();
		animation.setAnimationListener( null );
		sendResults();
		super.onDismiss( dialog );
	}

	private void startDismissAnimation() {
		// The post prevents from dismissing while the DialogFragment is still drawing
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