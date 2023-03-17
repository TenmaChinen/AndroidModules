package com.softramen.modules.introWidget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.softramen.modules.R;
import com.softramen.modules.databinding.ActivityIntroWidgetBinding;

public class introWidgetActivity extends AppCompatActivity {
	private final String TAG = "INTRO_WIDGET_ACTIVITY";

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_intro_widget );

		final Activity activity = this;

		final ActivityIntroWidgetBinding bind;
		bind = DataBindingUtil.setContentView( this , R.layout.activity_intro_widget );
		bind.setClickListener( this::onClick );

		final IntroManager introManagerA = new IntroManager( activity );
		introManagerA.showIntroSequence( activity, bind.textView, bind.button, bind.imageView );
	}

	public void onClick( final View view ) {
		final int viewId = view.getId();
		if ( viewId == R.id.text_view ) {
			Log.d( TAG , "MainActivity > onClick > TextView" );
		} else if ( viewId == R.id.button ) {
			Log.d( TAG , "MainActivity > onClick > Button" );
		} else if ( viewId == R.id.image_view ) {
			Log.d( TAG , "MainActivity > onClick > ImageView" );
		}
	}
}