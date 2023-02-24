package com.softramen.modules.introWidget;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import com.softramen.introView.IntroConfig;
import com.softramen.introView.IntroWidget;
import com.softramen.introView.shapes.FocusGravity;
import com.softramen.introView.shapes.FocusType;
import com.softramen.introView.shapes.ShapeType;

public class IntroManager {

	private final IntroConfig introConfig;
	private final Activity activity;

	public IntroManager( final Activity activity ) {
		this.activity = activity;
		introConfig = setIntroConfig();
	}

	private IntroConfig setIntroConfig() {
		final IntroConfig introConfig = new IntroConfig();

		// Text Info Config
		introConfig.setTextInfoSize( 18 );
		introConfig.setTextInfoStyle( Typeface.BOLD );
		// introConfig.setTextInfoColor( Color.WHITE );
		// introConfig.setTextInfoBackgroundColor( Color.YELLOW );

		// Focus Config
		introConfig.setFocusGravity( FocusGravity.CENTER );
		introConfig.setFocusType( FocusType.NORMAL );
		introConfig.setFocusPadding( 50 );

		// Others
		// introConfig.setDotViewEnabled( true );
		introConfig.setFadeAnimationEnabled( true );
		introConfig.setDelayMillis( 200 );

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
		return new IntroWidget.Builder( activity )
		// return new IntroWidget.Builder( activity , R.style.IntroViewThemeCustom )
				.setTarget( target )
				// .performClick( true )
				.setShape( shapeType )
				.setUsageId( String.valueOf( System.nanoTime() ) )
				.setInfoText( message )
				.setConfig( introConfig );
	}
}
