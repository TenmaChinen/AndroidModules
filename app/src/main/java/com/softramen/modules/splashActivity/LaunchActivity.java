package com.softramen.modules.splashActivity;

import android.os.Bundle;
import com.softramen.modules.fadeRecyclerView.FadeRecyclerViewActivity;
import com.softramen.splashActivity.SplashActivity;

public class LaunchActivity extends SplashActivity {

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setActivityToLaunch( FadeRecyclerViewActivity.class );
	}
}