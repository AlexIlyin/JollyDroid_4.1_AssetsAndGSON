package com.alexilyin.android.a41_assetsandgson;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int PREFERRED_CARD_WIDTH = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float displayWidthDp = metrics.widthPixels / metrics.density;
        int columns = Math.round(displayWidthDp / PREFERRED_CARD_WIDTH);

        // add data to DB
        new AssetsLoader(this).loadToCacheDB();


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL));


        Cursor cursor = CacheDBContract.Asset.get(this);
        cursor.moveToFirst();
        recyclerView.setAdapter(new MyCardAdapter(this, cursor));

        // for testing purpose
//        ImageView test = (ImageView) findViewById(R.id.test_image_view);
//        cursor.moveToFirst();
//        test.setImageBitmap(CacheDBContract.Asset.getImage(cursor));
    }


}
