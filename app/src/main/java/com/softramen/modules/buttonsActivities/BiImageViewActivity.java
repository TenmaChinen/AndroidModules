package com.softramen.modules.buttonsActivities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.softramen.buttonsCustom.BiImageView;
import com.softramen.modules.R;

public class BiImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_layered_image_view );

        final BiImageView biImageView = findViewById( R.id.layered_image_view_a );
        biImageView.setTextImage( com.softramen.buttonsCustom.R.drawable.btn_hard );
    }
}