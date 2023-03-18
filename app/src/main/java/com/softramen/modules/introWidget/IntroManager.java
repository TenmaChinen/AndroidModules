package com.softramen.modules.introWidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.provider.CalendarContract.Colors;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import com.softramen.introView.IntroConfig;
import com.softramen.introView.IntroPreferenceManager;
import com.softramen.introView.IntroWidget;
import com.softramen.introView.shapes.FocusGravity;
import com.softramen.introView.shapes.FocusType;
import com.softramen.introView.shapes.ShapeType;
import com.softramen.modules.Screen;

public class IntroManager {
	private final String TAG = "INTRO_MANAGER";

	private final Handler handler = new Handler();
	private final IntroConfig introConfig;
	private final Context context;
	private final IntroPreferenceManager introPreferenceManager;

	public IntroManager( final Context context ) {
		this.context = context;
		introConfig = setIntroConfig();
		introPreferenceManager = new IntroPreferenceManager( context );
		// introPreferenceManager.resetAll();
	}

	private IntroConfig setIntroConfig() {
		final IntroConfig introConfig = new IntroConfig();

		// TEXT INFO CONFIG
		introConfig.setTextInfoSize( TypedValue.COMPLEX_UNIT_SP , 20 );
		introConfig.setTextInfoStyle( Typeface.BOLD );
		// introConfig.setTextInfoAlignment( View.TEXT_ALIGNMENT_TEXT_END );
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

		// OVERLAY SETTINGS
		introConfig.setMaskColor( Color.argb( 100,200,10,10 ));
		introConfig.setLayoutMargin( ( int ) ( Screen.HEIGHT * 0.2 ) );
		introConfig.setLayoutGravity( Gravity.TOP );

		return introConfig;
	}

	public void showIntroSequence( final Activity activity , final View textView , final View button , final View imageView ) {
		final String introGroupId = "IntroGroupId";
		// This pattern centralized in some Singleton class to not read everytime
		// if ( introPreferenceManager.isDisplayed( introGroupId ) ) return;

		final IntroWidget introA = makeIntro( textView , "TextView\nIntro" , ShapeType.RECTANGLE , "TextView" , false );
		final IntroWidget introB = makeIntro( button , "Button\nIntro" , ShapeType.RECTANGLE , "Button" , false );
		final IntroWidget introC = makeIntro( imageView , "ImageView\nIntro" , ShapeType.CIRCLE , "ImageView" , false );

		introA.setListener( ( id ) -> introB.show( activity ) );
		introB.setListener( ( id ) -> introC.show( activity ) );
		introC.setListener( ( id ) -> introPreferenceManager.setDisplayed( introGroupId ) ); // Use this to avoid overhead once intro is fully learned

		handler.postDelayed( () -> introA.show( activity ) , 500 );
	}

	public IntroWidget makeIntro( final View target , final String message , final ShapeType shapeType , final String id , final boolean performClick ) {
		return new IntroWidget.Builder( context )
				// return new IntroWidget.Builder( activity , R.style.IntroViewThemeCustom )
				.setTarget( target )
				.performClick( performClick )
				.setTargetShapeType( shapeType )
				.setUsageId( id )
				.setShowOnlyOnce( false )
				.setInfoText( message )
				.setConfig( introConfig )
				.build();
	}
}
