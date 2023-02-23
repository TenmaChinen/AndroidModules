package com.softramen.modules.introWidget;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import com.softramen.introView.IntroConfig;
import com.softramen.introView.IntroWidget;
import com.softramen.introView.shape.FocusGravity;
import com.softramen.introView.shape.FocusType;
import com.softramen.introView.shape.ShapeType;
import com.softramen.modules.R;

public class IntroManager {

	private final IntroConfig introConfig;
	private final Activity activity;

	public IntroManager( final Activity activity ) {
		this.activity = activity;
		introConfig = setIntroConfig();
	}

	private IntroConfig setIntroConfig() {
		final IntroConfig introConfig = new IntroConfig();
		introConfig.setFocusGravity( FocusGravity.CENTER );
		// introConfig.setTextInfoColor( Color.WHITE );
		// introConfig.setTextInfoSize( 18 );
		introConfig.setFocusType( FocusType.NORMAL );
		introConfig.setFadeAnimationEnabled( true );
		// introConfig.setDotViewEnabled( true );
		introConfig.setDelayMillis( 200 );
		introConfig.setPadding( 50 );
		return introConfig;
	}

	public void showIntroSequence( final IntroWidget.Builder... args ) {
		for ( int idx = 0 ; idx < args.length ; idx++ ) {
			final IntroWidget.Builder builders = args[ idx ];
			if ( idx < args.length - 1 ) {
				final int nextIdx = idx + 1;
				builders.setListener( introViewId -> args[ nextIdx ].show() );
			}
		}
		final Handler handler = new Handler();
		handler.postDelayed( () -> args[ 0 ].show() , 500 );

	}

	public IntroWidget.Builder makeIntro( final View target , final String message , final ShapeType shapeType ) {
		// return new IntroWidget.Builder( activity )
		return new IntroWidget.Builder( activity , R.style.IntroViewThemeCustom )
				.setTarget( target )
				// .performClick( true )
				.setShape( shapeType )
				.setUsageId( String.valueOf( System.nanoTime() ) )
				.setInfoText( message )
				.setConfiguration( introConfig );
	}
}
