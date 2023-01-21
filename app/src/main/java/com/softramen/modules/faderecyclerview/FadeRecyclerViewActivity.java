package com.softramen.modules.faderecyclerview;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softramen.modules.R;
import com.softramen.recyclerviews.FadeRecyclerView;

public class FadeRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fade_recycler_view );

        final Context context = this;

        final String[] stringItems = { "A" , "B" , "C" , "D" , "E" };
        final FadeRecyclerView recyclerView = findViewById( R.id.fade_recyler_view );
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter( stringItems );
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( context );

        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter( recyclerViewAdapter );
    }
}