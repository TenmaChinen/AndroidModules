package com.softramen.modules.introWidget;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import com.softramen.introView.IntroConfig;
import com.softramen.introView.IntroWidget;
import com.softramen.introView.shapes.FocusGravity;
import com.softramen.introView.shapes.FocusType;
import com.softramen.introView.shapes.ShapeType;

public class IntroManager {
	private final String TAG = "INTRO_MANAGER";

	private final IntroConfig introConfig;
	private final Activity activity;

	public IntroManager( final Activity activity ) {
		this.activity = activity;
		introConfig = setIntroConfig();
	}

	private IntroConfig setIntroConfig() {
		final IntroConfig introConfig = new IntroConfig();

		// TEXT INFO CONFIG
		introConfig.setTextInfoSize( TypedValue.COMPLEX_UNIT_SP , 20 );
		introConfig.setTextInfoStyle( Typeface.BOLD );
		// introConfig.setTextInfoColor( Color.WHITE );
		// introConfig.setTextInfoBackgroundColor( Color.YELLOW );

		// FOCUS CONFIG
		introConfig.setFocusGravity( FocusGravity.CENTER );
		introConfig.setFocusType( FocusType.NORMAL );
		introConfig.setFocusPadding( 50 );

		// OTHERS
		// introConfig.setDotViewEnabled( true );
		introConfig.setFadeAnimationEnabled( true );
		introConfig.setStartDelayMillis( 200 );
		introConfig.setDismissOnTouch( false );

		return introConfig;
	}

	public void showIntroSequence( final IntroWidget... args ) {
		if ( args[ args.length - 1 ].isAlreadyIntroduced() ) return;

		for ( int idx = 0 ; idx < args.length ; idx++ ) {
			final IntroWidget introWidget = args[ idx ];
			if ( idx < args.length - 1 ) {
				final int nextIdx = idx + 1;
				introWidget.setListener( introViewId -> args[ nextIdx ].show( activity ) );
			}
		}
		final Handler handler = new Handler();
		handler.postDelayed( () -> args[ 0 ].show( activity ) , 500 );
	}

	public IntroWidget makeIntro( final View target , final String message , final ShapeType shapeType , final String id , final boolean performClick ) {
		return new IntroWidget.Builder( activity )
				// return new IntroWidget.Builder( activity , R.style.IntroViewThemeCustom )
				.setTarget( target )
				.performClick( performClick )
				.setTargetShapeType( shapeType )
				.setUsageId( ( id == null ) ? String.valueOf( System.nanoTime() ) : id )
				.setInfoText( message )
				.setConfig( introConfig )
				.build();
	}
}
