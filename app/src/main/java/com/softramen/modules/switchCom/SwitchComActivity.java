package com.softramen.modules.switchCom;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import androidx.appcompat.app.AppCompatActivity;
import com.softramen.switchcom.SwitchCom;
import com.softramen.modules.R;

public class SwitchComActivity extends AppCompatActivity {
	private final String TAG = "SWITCH_COM_ACTIVITY";

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_switch_com );

		final SwitchCom switchComA = findViewById( R.id.switch_com_a );
		final SwitchCom switchComB = findViewById( R.id.switch_com_b );

		switchComA.setTag( "Switch A" );
		switchComB.setTag( "Switch B" );

		switchComA.setOnCheckedChangeListener( onCheckedChangeListener );
		switchComB.setOnCheckedChangeListener( onCheckedChangeListener );

		switchComA.setChecked( true );
		Log.d( TAG , "onCreate > isChecked : " + switchComA.isChecked() );
	}

	private final OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged( final CompoundButton buttonView , final boolean isChecked ) {
			//			Log.d( TAG , "onCheckedChanged > isChecked : " + isChecked + " | view ID : " + buttonView.getId() );
			Log.d( TAG , "onCheckedChanged > isChecked : " + isChecked + " | view TAG : " + buttonView.getTag() );
		}
	};
}