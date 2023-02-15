package com.softramen.modules.optionMenu;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import androidx.appcompat.app.AppCompatActivity;
import com.softramen.modules.R;
import com.softramen.optionMenu.OptionMenu;


public class OptionMenuActivity extends AppCompatActivity {
	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_option_menu );

		final OptionMenu optionMenuA = findViewById( R.id.option_menu_a );
		optionMenuA.setItems( new String[]{ "Option A" , "Option B" , "Option C" } );
		optionMenuA.setTextStyle( Typeface.BOLD_ITALIC );

		final OptionMenu optionMenuB = findViewById( R.id.option_menu_b );
		optionMenuB.setTextSize( TypedValue.COMPLEX_UNIT_SP, 30 );
		optionMenuB.setItems( new String[]{ "Option A" , "Option B" , "Option C" } );
	}
}