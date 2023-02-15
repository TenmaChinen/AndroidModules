package com.softramen.modules.splashActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.softramen.modules.R;
import com.softramen.modules.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

	private Context context;
	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_second );

		context = this;

		final ActivitySecondBinding activitySecondBinding;
		activitySecondBinding = DataBindingUtil.setContentView( this , R.layout.activity_second );
		activitySecondBinding.setClickListener( onClickListener );

		final Handler handler = new Handler();
		handler.postDelayed( this::restartSplashActivity , 1000 );
	}

	private final View.OnClickListener onClickListener = v -> {
		final int viewId = v.getId();
		if ( viewId == R.id.button ) {
			restartSplashActivity();
		}
	};

	private void restartSplashActivity() {
		final Intent intent = new Intent( context , LaunchActivity.class );
		intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
		ContextCompat.startActivity( context , intent , null );
		finish();
		overridePendingTransition( android.R.anim.fade_in , android.R.anim.fade_out );
	}
}