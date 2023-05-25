package com.hst.simplephotoedior.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.hst.simplephotoedior.fragment.CreationFragment;
import com.hst.simplephotoedior.R;
import com.hst.simplephotoedior.util.Constances;


public class CreationActivity extends AppCompatActivity {
    ImageView ivcancel;
    Context context;
    LinearLayout facbook_ad_banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_creations);
        ivcancel = findViewById(R.id.ivbtnclose);
        context = CreationActivity.this;
        facbook_ad_banner = findViewById(R.id.facbook_ad_banner);
        if(Constances.adType.equals("Ad Mob"))
        {
           // loadAdMobAdervertise();

        }
        else {
           // loadfacebookAd();
        }

        ivcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, HomeActivity.class);
                //finishAffinity();
                context.startActivity(i);
            }
        });
        loadFragment(new CreationFragment());

        getSupportFragmentManager();
    }


    public void loadAdMobAdervertise() {


    }

    public void loadfacebookAd() {


    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}
