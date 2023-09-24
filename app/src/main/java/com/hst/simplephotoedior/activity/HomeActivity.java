package com.hst.simplephotoedior.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hst.simplephotoedior.MyApplication;
import com.hst.simplephotoedior.adapter.FrameAdapter;
import com.hst.simplephotoedior.model.Frame;
import com.hst.simplephotoedior.R;
import com.hst.simplephotoedior.util.Constances;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    private DrawerLayout drawerLayout;
    NavigationView mNavigationView;
    ImageView nav_btn;
    RecyclerView recyclerView;
    public static HomeActivity instance = null;
    Timer timer;
    TimerTask hourlyTask;
    LinearLayout facbook_ad_banner;

    private  LinearLayout pointView;
    private TextView pointTxt;;


    public HomeActivity() {
        instance = HomeActivity.this;
    }

    public static synchronized HomeActivity getInstance() {
        if (instance == null) {
            instance = new HomeActivity();
        }
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = HomeActivity.this;
        drawerLayout = findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navbar);
        nav_btn = findViewById(R.id.nav_btn);
        recyclerView = findViewById(R.id.rv_framelist);
        facbook_ad_banner = findViewById(R.id.facbook_ad_banner);
        pointView = findViewById(R.id.pointView);
        pointTxt = findViewById(R.id.pointWallet);
        ArrayList<Frame> frameLists = new ArrayList<>();
        if(Constances.adType.equals("Ad Mob"))
        {
            loadAdMobAdervertise();

        }
        else {
            loadfacebookAd();
        }

        load_mInterstitialAd();
        final int frames[] = {
                R.drawable.frm_animal01, R.drawable.frm_animal02, R.drawable.frm_animal03, R.drawable.frm_animal04,
                R.drawable.frm_animal05, R.drawable.frm_animal06, R.drawable.frm_animal07,
                R.drawable.frm_animal08, R.drawable.frm_animal09, R.drawable.frm_10, R.drawable.frm_11,
                R.drawable.frm_12, R.drawable.frm_13, R.drawable.frm_14, R.drawable.frm_15, R.drawable.frm_16,
                R.drawable.frm_17, R.drawable.frm_18, R.drawable.frm_19, R.drawable.frm_20, R.drawable.frm_21,
                R.drawable.frm_22, R.drawable.frm_23, R.drawable.frm_24
        };

        for (int i = 0; i < frames.length; i++) {
            Frame modelclassFrameList = new Frame();
            modelclassFrameList.setFrame(frames[i]);
            if (i == 2 || i == 5 || i==7) {
                modelclassFrameList.setNeedPoint(true);
            }
            frameLists.add(modelclassFrameList);
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        FrameAdapter adapterCategory = new FrameAdapter(context, frameLists);
        recyclerView.setAdapter(adapterCategory);

        nav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        pointView.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, PurchaseInAppActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        pointTxt.setText(MyApplication.getInstance().getValueCoin() + "");
    }

    public void loadAdMobAdervertise() {

    }

    public void loadfacebookAd() {

    }

    public void load_mInterstitialAd() {
        //// AdMob Ad-------------------------





    }

    public  void fullADInit(final Activity activity) {


    }


    public void displayAdMob() {

    }

    public void displayInterstitialFacbookAd() {

    }



    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
        final AlertDialog alertDialog = builder.create();
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();

                    }
                })
                .setNegativeButton("Rate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Uri uri = Uri.parse("market://details?id=" + getPackageName());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(Constances.Rateapp)));
                        }
                    }
                })
                .setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                })
                .show();
    }

    public void stopTask() {
        if (hourlyTask != null) {
            Log.d("TIMER", "timer canceled");
            hourlyTask.cancel();
        }
    }

    public void StartTimer() {
        timer = new Timer();
        hourlyTask = new TimerTask() {
            @Override
            public void run() {
                if (!Constances.isFirstTimeOpen) {
                    Constances.AllowToOpenAdvertise = true;
                    stopTask();
                } else {
                    Constances.isFirstTimeOpen = false;
                }
            }
        };


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {

            drawerLayout.openDrawer(Gravity.LEFT);

        }/* else if (id == R.id.contactus) {

            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", Constances.emailid, null));
            intent.putExtra(Intent.EXTRA_SUBJECT, 0);
            intent.putExtra(Intent.EXTRA_TEXT, 0);
            startActivity(Intent.createChooser(intent, "Choose an Email client"));

        }*/ /*else if (id == R.id.shareapp) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, Constances.shareapp_url);
            intent.setType("text/plain");
            this.startActivity(intent);


        }*/ else if (id == R.id.mycreation) {
            Intent intent = new Intent(context, CreationActivity.class);
            startActivity(intent);

            if(Constances.adType.equals("Ad Mob")){
                displayAdMob();
            }
            else {
                displayInterstitialFacbookAd();
            }


        } /*else if (id == R.id.moreapp) {

            try {
                Uri uri = Uri.parse(Constances.Moreapp);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Constances.Moreapp)));
            }


        } */else if (id == R.id.rateapp) {

            try {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Constances.Rateapp)));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
