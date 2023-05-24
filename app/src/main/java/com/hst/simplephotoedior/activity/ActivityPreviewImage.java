package com.hst.simplephotoedior.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;


import com.hst.simplephotoedior.R;
import com.hst.simplephotoedior.Utils.Constances;

import java.io.File;
import java.util.Date;

public class ActivityPreviewImage extends AppCompatActivity {
    //AdView adView;
    ImageView ivcancel, ivPreview;
    LinearLayout ivHome, ivShare, ivDelete,facbook_ad_banner;
    Context context;
    String[] permissionsRequired = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int PERMISSION_CALLBACK_CONSTANT = 200;
    ProgressDialog dialog;
    public Bitmap bitmapsave;
    public boolean isForShareGlobal;
    public Date currentTime;
    public String filePath = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);
        ivcancel = findViewById(R.id.ivcancel);
        ivHome = findViewById(R.id.ivHome);
        ivShare = findViewById(R.id.ivShare);
        ivDelete = findViewById(R.id.ivDelete);
        ivPreview = findViewById(R.id.ivPreview);
        context = ActivityPreviewImage.this;

        ivPreview.setImageBitmap(Constances.Image);
        if(Constances.adType.equals("Ad Mob"))
        {
         //   loadAdMobAdervertise();

        }
        else {
         //   loadfacebookAd();
        }
        ivcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityHome.class);
                //finishAffinity();
                context.startActivity(intent);
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    SaveCustomView(ivPreview, true);
                } else {
                    checkPermission();
                }
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog diaBox = AskOption();
                diaBox.show();
            }
        });


    }


    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(context, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionsRequired[1])) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions((Activity) context, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        onBackPressed();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions((Activity) context, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }
        }
    }


    public void SaveCustomView(View view, boolean isForShare) {
        dialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        dialog.setMessage("Please Wait... Image is saving...");

        dialog.setCancelable(false);
        dialog.show();
//        view.setDrawingCacheEnabled(true);
//        bitmapsave = view.getDrawingCache();
        bitmapsave = viewToBitmap(view);

        isForShareGlobal = isForShare;


        if (isForShareGlobal) {
            ShareImage();
//                progressBar.setVisibility(View.GONE);
            dialog.dismiss();
        }
    }


    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code


                        File file = Constances.filePath;
                        file.delete();
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(Constances.filePath)));
                        dialog.dismiss();


                        // ivPreview.setImageBitmap(null);
                        onBackPressed();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    public void loadAdMobAdervertise() {


    }

    public void loadfacebookAd() {

    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public void ShareImage() {
        Intent share = new Intent(Intent.ACTION_SEND);
        Uri uri = FileProvider.getUriForFile(context, getString(R.string.file_provider_authority), Constances.filePath);
        share.setType("image/*");
//      share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share via"));
    }
}
