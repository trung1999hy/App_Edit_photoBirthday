package com.hst.simplephotoedior.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hst.simplephotoedior.adapter.ImageAdapter;
import com.hst.simplephotoedior.R;


public class GalleryActivity extends AppCompatActivity {
    int int_position;
    private GridView gridView;
    ImageAdapter adapter;
    ImageView ivBack, ivCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_images);
        gridView = (GridView) findViewById(R.id.gv_folder);

        ivBack = findViewById(R.id.ivBack);
        ivCamera = findViewById(R.id.ivCamera);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
              //  finish();
            }
        });


        int_position = getIntent().getIntExtra("value", 0);
        adapter = new ImageAdapter(this, GalleryAdapter.al_images, int_position);
        gridView.setAdapter(adapter);
    }
}
