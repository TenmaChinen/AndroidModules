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
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.softramen.CustomDialogs.utils.AnimationListener;
import com.softramen.CustomDialogs.utils.Constants;
import com.softramen.customdialogs.R;
import com.softramen.settingsmanager.SettingsItem;
import com.softramen.settingsmanager.SettingsListAdapter;
import com.softramen.settingsmanager.SettingsManager;
import org.jetbrains.annotations.Nullable;
import java.util.Map;


public class DialogSettings extends DialogFragment {
	private final String TAG = "DIALOG_SETTINGS";

	public static final String REQUEST_CODE = "DIALOG_SETTINGS", METHOD_CODE = "METHOD_CODE";
	public static final int ON_CLICK_SAVE = 0, ON_CANCEL = 1;

	private final SettingsManager settingsManager = SettingsManager.getInstance();
	private Animation windowEnterAnimation, windowExitAnimation;
	private boolean onStartExecuted = false;
	private int callbackId = ON_CANCEL;
	private View inflatedView;

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

		final Window window = dialog.getWindow();
		window.setFlags( LayoutParams.FLAG_NOT_FOCUSABLE , LayoutParams.FLAG_NOT_FOCUSABLE );
		window.setWindowAnimations( R.style.DialogNullWindowAnimationStyle );
		window.setGravity( Gravity.BOTTOM );
		return dialog;
	}

	@Nullable
	@Override
	public View onCreateView( @NonNull final LayoutInflater inflater , @Nullable final ViewGroup container , @Nullable final Bundle savedInstanceState ) {
		inflatedView = inflater.inflate( R.layout.dialog_settings , container , false );
		final ListView listView = inflatedView.findViewById( R.id.list_view );
		final TextView btnSave = inflatedView.findViewById( R.id.btn_save );
		final TextView btnCancel = inflatedView.findViewById( R.id.btn_cancel );

		final Map<Integer, SettingsItem> settingsItemMap = settingsManager.getSettingsItemMap();
		final SettingsListAdapter settingsListAdapter = new SettingsListAdapter( inflater.getContext() , settingsItemMap );
		listView.setAdapter( settingsListAdapter );

		final View.OnClickListener clickListener = view -> {
			btnSave.setOnClickListener( null );
			btnCancel.setOnClickListener( null );
			final int viewId = view.getId();
			if ( viewId == R.id.btn_save ) {
				settingsListAdapter.updateSettingsChanges();
				callbackId = ON_CLICK_SAVE;
			}
			else if ( viewId == R.id.btn_cancel ) callbackId = ON_CANCEL;
			startDismissAnimation();
		};

		btnSave.setOnClickListener( clickListener );
		btnCancel.setOnClickListener( clickListener );
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