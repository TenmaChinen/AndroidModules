package com.softramen.modules.optionMenu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.softramen.modules.R;
import com.softramen.optionMenu.OptionMenu;

public class OptionMenuActivity extends AppCompatActivity {
	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_option_menu );

		final String[] stringArray = { "Option A" , "Option B" , "Option C" };
		final OptionMenu optionMenu = findViewById( R.id.option_menu );
		optionMenu.setItems( stringArray );
	}
}