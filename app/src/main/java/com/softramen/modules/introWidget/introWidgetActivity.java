package com.softramen.modules.introWidget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.softramen.introView.IntroWidget;
import com.softramen.introView.shape.ShapeType;
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

		final TextView textView = bind.textView;
		final Button button = bind.button;
		final ImageView imageView = bind.imageView;

		final IntroManager introManager = new IntroManager( activity );
		final IntroWidget.Builder introText = introManager.makeIntro( textView , "This is the TextView" , ShapeType.RECTANGLE );
		final IntroWidget.Builder introButton = introManager.makeIntro( button , "This is the Button" , ShapeType.RECTANGLE );
		final IntroWidget.Builder introImage = introManager.makeIntro( imageView , "This is the ImageView" , ShapeType.CIRCLE );
		introManager.showIntroSequence( introText , introButton , introImage );
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