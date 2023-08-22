package com.hst.simplephotoedior.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hst.simplephotoedior.adapter.FontAdapter;
import com.hst.simplephotoedior.adapter.StickerAdapter;
import com.hst.simplephotoedior.adapter.PopupAdapter;
import com.hst.simplephotoedior.model.FontDetail;
import com.hst.simplephotoedior.model.Sticker;
import com.hst.simplephotoedior.model.Frame;
import com.hst.simplephotoedior.R;
import com.hst.simplephotoedior.sticker.DrawableSticker;
import com.hst.simplephotoedior.sticker.StickerView;
import com.hst.simplephotoedior.sticker.TextSticker;
import com.hst.simplephotoedior.util.CustomDialog;
import com.hst.simplephotoedior.util.Constances;
import com.hst.simplephotoedior.util.PaletteBar;
import com.hst.simplephotoedior.util.PathUtills;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CreatePhotoActivity extends AppCompatActivity {

    Context context;
    public ImageView ivframe, ivImage, ivCamera;
    //AdView adView;
    ArrayList<Frame> frameLists;
    LinearLayout image, frame, flip, stickers, text, save, border, ll_frame_background, ll_image;
    PopupWindow mPopupWindow, mPopupWindowpw;
    RelativeLayout rl_main, rl_main1;
    TextView tvHorizonal, tvVertical;
    public static CreatePhotoActivity instance = null;
    float scalediff;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    public static final int RequestPermissionCode = 1;
    String[] permissionsRequired = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int PERMISSION_CALLBACK_CONSTANT = 200;
    Uri photouri;
    String mCurrentPhotoPath;
    EditText et_text_sticker;
    TextSticker txtsticker;
    int textStickerColor = R.color.black;
    StickerView sticker_view;
    String[] fontList = new String[]{"ArchivoNarrow.otf", "ABeeZee.otf", "After_Shok.ttf", "AbrilFatface.otf", "Acknowledgement.otf", "Acme.ttf", "AlfaSlabOne.ttf", "AlmendraDisplay.otf", "Almendra.otf", "alpha_echo.ttf", "Amadeus.ttf", "AMERSN.ttf", "ANUDI.ttf", "AquilineTwo.ttf", "Arbutus.ttf", "AlexBrush.ttf", "Alisandra.ttf", "Allura.ttf", "Amarillo.ttf", "BEARPAW.ttf", "bigelowrules.ttf", "BLACKR.ttf", "BOYCOTT.ttf", "BebasNeue.ttf", "BLKCHCRY.TTF", "Carousel.ttf", "Caslon_Calligraphic.ttf", "CroissantOne.ttf", "Carnevalee-Freakshow.ttf", "CAROBTN.TTF", "CaviarDreams.ttf", "Cocogoose.ttf", "diplomata.ttf", "deftone stylus.ttf", "Dosis.ttf", "FONTL.TTF", "Hugtophia.ttf", "ICE_AGE.ttf", "Kingthings_Calligraphica.ttf", "Love Like This.ttf", "MADE Canvas.otf", "Merci-Heart-Brush.ttf", "Metropolis.otf", "Montserrat.otf", "MontserratAlternates.otf", "norwester.otf", "ostrich.ttf", "squealer.ttf", "Titillium.otf", "Ubuntu.ttf"};
    Sticker[] stickerlist;
    public ArrayList<FontDetail> arrayList;
    int count = 0, textcolor;
    public Bitmap bitmapsave;
    public boolean isForShareGlobal;
    public Date currentTime;
    public String filePath = "";
    public boolean showingFirst = true;
    public boolean showingsecond = true;
    EditText etcount;
    String abc;

    LinearLayout facbook_ad_banner;
    File imagesDir;
    public CreatePhotoActivity() {
        instance = CreatePhotoActivity.this;
    }

    public static synchronized CreatePhotoActivity getInstance() {
        if (instance == null) {
            instance = new CreatePhotoActivity();
        }
        return instance;
    }

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_photo);

        context = CreatePhotoActivity.this;

        ivframe = findViewById(R.id.iv_frame);
        ivImage = findViewById(R.id.iv_image);
        image = findViewById(R.id.image);
        frame = findViewById(R.id.frame);
        flip = findViewById(R.id.flip);
        stickers = findViewById(R.id.sticker);
        text = findViewById(R.id.text);
        save = findViewById(R.id.save);
        rl_main = findViewById(R.id.rl_main);
        rl_main1 = findViewById(R.id.rl_main1);
        ivCamera = findViewById(R.id.ivCamera);
        et_text_sticker = findViewById(R.id.et_text_sticker);
        et_text_sticker.setImeOptions(EditorInfo.IME_ACTION_DONE);
        sticker_view = findViewById(R.id.sticker_view);
        ll_frame_background = findViewById(R.id.ll_frame_background);
        border = findViewById(R.id.border);
        facbook_ad_banner = findViewById(R.id.facbook_ad_banner);

        arrayList = new ArrayList<>();
        frameLists = new ArrayList<>();
        if(Constances.adType.equals("Ad Mob"))
        {
            loadAdMobAdervertise();

        }
        else {
            loadfacebookAd();
        }

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        for (int i = 0; i < fontList.length; i++) {

            arrayList.add(new FontDetail(fontList[i], fontList[i]));
        }


        stickerlist = new Sticker[]{

                new Sticker(R.drawable.s1), new Sticker(R.drawable.s2), new Sticker(R.drawable.s3),
                new Sticker(R.drawable.s4), new Sticker(R.drawable.sfive), new Sticker(R.drawable.s6), new Sticker(R.drawable.s7),
                new Sticker(R.drawable.s8), new Sticker(R.drawable.s9), new Sticker(R.drawable.s10), new Sticker(R.drawable.s11),
                new Sticker(R.drawable.s12), new Sticker(R.drawable.s13), new Sticker(R.drawable.s14), new Sticker(R.drawable.s15),
                new Sticker(R.drawable.s16), new Sticker(R.drawable.s17), new Sticker(R.drawable.s18), new Sticker(R.drawable.s19),
                new Sticker(R.drawable.s20), new Sticker(R.drawable.s21), new Sticker(R.drawable.s22), new Sticker(R.drawable.s23),
                new Sticker(R.drawable.s24), new Sticker(R.drawable.s25), new Sticker(R.drawable.s26), new Sticker(R.drawable.s27),
                new Sticker(R.drawable.s28), new Sticker(R.drawable.s29), new Sticker(R.drawable.s30), new Sticker(R.drawable.s31),
                new Sticker(R.drawable.s32), new Sticker(R.drawable.s33), new Sticker(R.drawable.s34), new Sticker(R.drawable.s35),
                new Sticker(R.drawable.s36), new Sticker(R.drawable.s37), new Sticker(R.drawable.s38), new Sticker(R.drawable.s39),
                new Sticker(R.drawable.s40), new Sticker(R.drawable.s41), new Sticker(R.drawable.s42), new Sticker(R.drawable.s43),
                new Sticker(R.drawable.s44), new Sticker(R.drawable.s45), new Sticker(R.drawable.s46), new Sticker(R.drawable.s47),
                new Sticker(R.drawable.s48), new Sticker(R.drawable.s49), new Sticker(R.drawable.s50), new Sticker(R.drawable.s51),
                new Sticker(R.drawable.s52), new Sticker(R.drawable.s53), new Sticker(R.drawable.s54), new Sticker(R.drawable.s55),
                new Sticker(R.drawable.s56), new Sticker(R.drawable.s57),
        };


        stickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStickers();

            }
        });


        border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBorder();


                //  ivImage.setLayoutParams(parameter);
                // ll_frame_background.setBackgroundColor(getResources().getColor(R.color.color20));
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_text_sticker.setVisibility(View.VISIBLE);
                et_text_sticker.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        et_text_sticker.post(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.showSoftInput(et_text_sticker, InputMethodManager.SHOW_IMPLICIT);
                            }
                        });
                    }
                });
                et_text_sticker.requestFocus();
                et_text_sticker.getText().clear();
                et_text_sticker.setTextColor(getResources().getColor(R.color.hint));
                et_text_sticker.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/" + "Acme.ttf"));
                et_text_sticker.setHint("your text for sticker");


            }
        });


        et_text_sticker.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you wants to Edit Sticker");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            textStickerEditPopUp();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (et_text_sticker.getText().toString().equals("") || et_text_sticker.getText().toString().equals(null)) {
                                CustomDialog.AlertMessage(context, "Please enter text");
                            } else {
                                et_text_sticker.setVisibility(View.GONE);
                                txtsticker = new TextSticker(context);

                                et_text_sticker.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(et_text_sticker.getWindowToken(), 0);
                                    }
                                });


                                txtsticker.setText("");
                                txtsticker.getText();
                                txtsticker.setText(et_text_sticker.getText().toString());
                                txtsticker.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/" + Constances.FontStyle));
                                txtsticker.setTextColor(getResources().getColor(textStickerColor));
                                textStickerColor = R.color.black;
                                txtsticker.resizeText();
                                sticker_view.addSticker(txtsticker);

                            }

                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                    pbutton.setTextColor(Color.BLACK);
                    pbutton.setBackgroundColor(Color.WHITE);

                    Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                    nbutton.setTextColor(Color.BLACK);
                    nbutton.setBackgroundColor(Color.WHITE);

                }
                return false;
            }
        });


        //   Glide.with(context).load("file://" + getIntent().getStringExtra("images")).into(ivImage);
        Glide.with(context).load(getIntent().getIntExtra("frame", 0)).into(ivframe);

        //  Constances.FrameId=getIntent().getIntExtra("frameId",0);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(700, 700);
        ivImage.setLayoutParams(layoutParams);

        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFrames();
            }
        });


        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /* Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

               startActivityForResult(intent, 7);*/


                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    File photoThumbnailFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {

                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photouri = FileProvider.getUriForFile(context,
                                getString(R.string.file_provider_authority),
                                photoFile);

//                       Uri photoThumbnailURI = FileProvider.getUriForFile(context,
//                               getString(R.string.file_provider_authority),
//                                photoThumbnailFile);

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photouri);
                        startActivityForResult(takePictureIntent, 1);

                    }
                }
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, GalleryAdapter.class);
                startActivity(intent);

                if(Constances.adType.equals("Ad Mob")){
                    displayAdMob();
                }
                else {
                    displayInterstitialFacbookAd();
                }



            }
        });

        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeRotation();
            }
        });
        try {
            ivImage.setImageDrawable(getResources().getDrawable(R.drawable.white_bg));

        } catch (OutOfMemoryError e) {

        }


        sticker_view.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull com.hst.simplephotoedior.sticker.Sticker sticker) {


            }

            @Override
            public void onStickerClicked(@NonNull com.hst.simplephotoedior.sticker.Sticker sticker1) {
                sticker_view.hideIcons(false);
                sticker_view.setLocked(false);
            }

            @Override
            public void onStickerDeleted(@NonNull com.hst.simplephotoedior.sticker.Sticker sticker) {

            }

            @Override
            public void onStickerDragFinished(@NonNull com.hst.simplephotoedior.sticker.Sticker sticker) {

            }

            @Override
            public void onStickerTouchedDown(@NonNull com.hst.simplephotoedior.sticker.Sticker sticker) {
                sticker_view.hideIcons(true);


            }

            @Override
            public void onStickerZoomFinished(@NonNull com.hst.simplephotoedior.sticker.Sticker sticker) {

            }

            @Override
            public void onStickerFlipped(@NonNull com.hst.simplephotoedior.sticker.Sticker sticker) {

            }

            @Override
            public void onStickerDoubleTapped(@NonNull com.hst.simplephotoedior.sticker.Sticker sticker) {
                sticker_view.hideIcons(false);
                sticker_view.setLocked(false);
                et_text_sticker.setVisibility(View.VISIBLE);
                sticker = (TextSticker) sticker_view.getCurrentSticker();
                et_text_sticker.setText(((TextSticker) sticker).getText());
                et_text_sticker.setTextColor(((TextSticker) sticker).getTextColor());
                sticker_view.remove(sticker);
            }
        });

        ivImage.setOnTouchListener(new View.OnTouchListener() {

            RelativeLayout.LayoutParams parms;
            int startwidth;
            int startheight;
            float dx = 0, dy = 0, x = 0, y = 0;
            float angle = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final ImageView view = (ImageView) v;

                ((BitmapDrawable) view.getDrawable()).setAntiAlias(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:

                        parms = (RelativeLayout.LayoutParams) ivImage.getLayoutParams();
                        startwidth = parms.width;
                        startheight = parms.height;
                        dx = event.getRawX() - parms.leftMargin;
                        dy = event.getRawY() - parms.topMargin;
                        mode = DRAG;
                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                        if (oldDist > 10f) {
                            mode = ZOOM;
                        }

                        d = rotation(event);

                        break;
                    case MotionEvent.ACTION_UP:

                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;

                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {

                            x = event.getRawX();
                            y = event.getRawY();

                            parms.leftMargin = (int) (x - dx);
                            parms.topMargin = (int) (y - dy);

                            parms.rightMargin = 0;
                            parms.bottomMargin = 0;
                            parms.rightMargin = parms.leftMargin + (5 * parms.width);
                            parms.bottomMargin = parms.topMargin + (10 * parms.height);

                            view.setLayoutParams(parms);

                        } else if (mode == ZOOM) {

                            if (event.getPointerCount() == 2) {

                                newRot = rotation(event);
                                float r = newRot - d;
                                angle = r;

                                x = event.getRawX();
                                y = event.getRawY();

                                float newDist = spacing(event);
                                if (newDist > 10f) {
                                    float scale = newDist / oldDist * view.getScaleX();

                                    if (scale > 0.6) {
                                        scalediff = scale;
                                        view.setScaleX(scale);
                                        view.setScaleY(scale);

                                    }
                                }

                                view.animate().rotationBy(angle).setDuration(0).setInterpolator(new LinearInterpolator()).start();

                                x = event.getRawX();
                                y = event.getRawY();

                                parms.leftMargin = (int) ((x - dx) + scalediff);
                                parms.topMargin = (int) ((y - dy) + scalediff);

                                parms.rightMargin = 0;
                                parms.bottomMargin = 0;
                                parms.rightMargin = parms.leftMargin + (5 * parms.width);
                                parms.bottomMargin = parms.topMargin + (10 * parms.height);

                                view.setLayoutParams(parms);


                            }
                        }
                        break;
                }

                return true;

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sticker_view.hideIcons(true);
                Intent i = new Intent(context, ImagePreviewActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finishAffinity();
                bitmapsave = viewToBitmap(rl_main1);
                Constances.Image = bitmapsave;

                new NetworkAccess().execute();
                startActivity(i);
            }


        });

    }


    public void loadAdMobAdervertise() {
    }

    public void loadfacebookAd() {

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(context, HomeActivity.class);
        startActivityForResult(i, 1);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Glide.with(context).load(Constances.frameLists.get(Constances.FrameId1).getFrame()).into(ivframe);
        // cameraPermission();
        if (Constances.imageSet) {
            Log.e("File", "" + "file://" + getIntent().getStringExtra("images"));
            Log.e("AppCon", "" + getApplicationContext());
            // Log.e("File",""+"file://" + getIntent().getStringExtra("images"));
            Glide.with(context).load("file://" + getIntent().getStringExtra("images")).into(ivImage);
            Glide.with(context).load(Constances.frameLists.get(Constances.FrameId1).getFrame()).into(ivframe);

            if (Constances.cameraImage) {
                ivImage.setImageBitmap(Constances.bmp);
            }
            Constances.imageSet = false;
        } else {
            Glide.with(context).load(Constances.frameLists.get(Constances.FrameId1).getFrame()).into(ivframe);
        }


    }


    // Glide.with(context).load("file://" + getIntent().getStringExtra("images")).into(ivImage);


    public void textStickerEditPopUp() {


        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        final View customView = inflater.inflate(R.layout.edit_text_sticker_popup, null);


        mPopupWindowpw = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );


//
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindowpw.setElevation(5.0f);
        }
        mPopupWindowpw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindowpw.setOutsideTouchable(true);
        mPopupWindowpw.showAtLocation(rl_main, Gravity.BOTTOM, 0, 0);


        final TextView dialogTitle = (TextView) customView.findViewById(R.id.cp_accent_title);
        final LinearLayout ll_text_color = (LinearLayout) customView.findViewById(R.id.ll_text_color);
        final LinearLayout ll_text_style = (LinearLayout) customView.findViewById(R.id.ll_text_style);
        final Button btn_Ok = (Button) customView.findViewById(R.id.btn_Ok);


        ll_text_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textStickerColorPopUp();

            }
        });


        ll_text_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFontList();
            }
        });
        dialogTitle.setText("Edit Sticker");


        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (et_text_sticker.getText().toString().equals("") || et_text_sticker.getText().toString().equals(null)) {
                    CustomDialog.AlertMessage(context, "Please enter text");
                    mPopupWindowpw.dismiss();


                } else {
                    et_text_sticker.setVisibility(View.GONE);
                    txtsticker = new TextSticker(context);
                    txtsticker.setText("");
                    et_text_sticker.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(et_text_sticker.getWindowToken(), 0);

                        }
                    });
                    // Toast.makeText(context, et_text_sticker.getText().toString(), Toast.LENGTH_LONG).show();
                    txtsticker.setText(et_text_sticker.getText().toString());
                    txtsticker.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/" + Constances.FontStyle));
                    txtsticker.setTextColor(textcolor);
                    textStickerColor = R.color.black;
                    txtsticker.resizeText();
                    sticker_view.addSticker(txtsticker);
                    mPopupWindowpw.dismiss();


                }


            }


        });


    }


    public void textStickerColorPopUp() {


        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View customView = inflater.inflate(R.layout.select_color_popup, null);

        PaletteBar paletteBar = customView.findViewById(R.id.paletteBar);
        paletteBar.setListener(new PaletteBar.PaletteBarListener() {
            @Override
            public void onColorSelected(int color) {
                et_text_sticker.setTextColor(color);
                textcolor = color;

            }
        });

        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);


        final TextView dialogTitle = (TextView) customView.findViewById(R.id.cp_accent_title);
        final Button btnOk = (Button) customView.findViewById(R.id.btnOk);
        final Button btnCancel = (Button) customView.findViewById(R.id.btnCancel);
/*
        final RecyclerView rvList = (RecyclerView) customView.findViewById(R.id.rvList);
*/


        dialogTitle.setText("Text Color");


      /*  GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 7);
        rvList.setLayoutManager(linearLayoutManager);*/


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();

            }
        });


        mPopupWindow.showAtLocation(rl_main, Gravity.BOTTOM, 0, 0);
    }


    public void openStickers() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.frame_pop_up, null);

        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        mPopupWindow.setAnimationStyle(R.style.animation_for_popup);
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        mPopupWindow.setOutsideTouchable(true);


        RecyclerView rvList = customView.findViewById(R.id.rvList);


        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        StickerAdapter adapterStickersList = new StickerAdapter(context, R.layout.framelist_raw_item, stickerlist, "popup");
        rvList.setAdapter(adapterStickersList);


        mPopupWindow.showAtLocation(rl_main, Gravity.BOTTOM, 150, 300);
    }


    public void openFontList() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        View customView = inflater.inflate(R.layout.popup_for_select_font, null);


        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );


        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);


        RecyclerView rvList = customView.findViewById(R.id.rvList);
        Button btnOk = customView.findViewById(R.id.btnOk);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvList.setLayoutManager(linearLayoutManager);

        FontAdapter adapterFontList = new FontAdapter(context, R.layout.font_list_row, arrayList);
        rvList.setAdapter(adapterFontList);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();

            }
        });

        mPopupWindow.showAtLocation(rl_main, Gravity.BOTTOM, 0, 0);

    }


    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }


    // Method (Photo Rotation)...................
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

  /*  public void setTextStickerColor(int color) {
        //Toast.makeText(context,"color"+color,Toast.LENGTH_LONG).show();
        et_text_sticker.setTextColor(getResources().getColor(color));
        textStickerColor = color;
    }*/

    public void setEmoji(int position) {

        sticker_view.addSticker(new DrawableSticker(getResources().getDrawable(stickerlist[position].getImgId())));
        mPopupWindow.dismiss();
    }

    // Method (Font Style to Text)...................
    public void SetFontToText(String FontName) {

        Constances.ischangetypeface = true;

        et_text_sticker.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/" + FontName));
        Constances.FontStyle = FontName;
    }


    public class NetworkAccess extends AsyncTask<Void, Void, Exception> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // call some loader
        }

        @Override
        protected Exception doInBackground(Void... params) {
            // Do background task
            try {
                Uri result=saveImage(context, Constances.Image,Constances.FolderName,currentTime+".jpg");
                Constances.filePath = new File(PathUtills.getPath(CreatePhotoActivity.this, result));

                scanFile(context, result);

            } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                Toast.makeText(context, "Fail to save image", Toast.LENGTH_SHORT).show();
                return e;
            } catch (IOException e) {
//                    e.printStackTrace();
//                Toast.makeText(context, "Fail to save image", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
                return e;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);
            // dismiss loader
            // update ui


            if (isForShareGlobal) {
                //ShareImage();
//                progressBar.setVisibility(View.GONE);
                //dialog.dismiss();
            }

        }
    }
    private Uri saveImage(Context context, Bitmap bitmap, @NonNull String folderName, @NonNull String fileName) throws IOException {
        OutputStream fos = null;
        File imageFile = null;
        Uri imageUri = null;
        imagesDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString() + File.separator + folderName);

        if (!imagesDir.exists())
            imagesDir.mkdir();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/*");
                contentValues.put(
                        MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + folderName);
                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                if (imageUri == null)
                    throw new IOException("Failed to create new MediaStore record.");

                fos = resolver.openOutputStream(imageUri);
            } else {
                imagesDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).toString() + File.separator + folderName);

                if (!imagesDir.exists())
                    imagesDir.mkdir();

                imageFile = new File(imagesDir, fileName + ".jpg");
                fos = new FileOutputStream(imageFile);
            }


            if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos))
                throw new IOException("Failed to save bitmap.");
            fos.flush();
        } finally {
            if (fos != null)
                fos.close();
        }

        if (imageFile != null) {//pre Q
            MediaScannerConnection.scanFile(context, new String[]{imageFile.toString()}, null, null);
            imageUri = Uri.fromFile(imageFile);
        }
        return imageUri;
    }

    private static void scanFile(Context context, Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        context.sendBroadcast(scanIntent);

    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void setBorder() {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.border_size_popup, null);

        ImageView plusbtn, minusbtn;
        plusbtn = customView.findViewById(R.id.ivPlus);
        minusbtn = customView.findViewById(R.id.ivMinus);
        etcount = customView.findViewById(R.id.etBordersize);
        PaletteBar paletteBar = customView.findViewById(R.id.paletteBar);
        final LinearLayout ll_selectborder = customView.findViewById(R.id.ll_selectborder);


        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        mPopupWindow.setAnimationStyle(R.style.animation_for_popup);
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }


        paletteBar.setListener(new PaletteBar.PaletteBarListener() {
            @Override
            public void onColorSelected(int color) {
                ll_frame_background.setBackgroundColor(color);

            }
        });

        etcount.setCursorVisible(false);


        plusbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean iscolor = true;


                if (iscolor) {


                    ll_selectborder.setBackgroundColor(Color.BLUE);
                    count++;

                    if (count <= 25) {


                        ll_selectborder.setBackgroundColor(Color.parseColor("#000000"));
                        etcount.setText(String.valueOf(count));
                        int margin = Integer.parseInt(etcount.getText().toString());
                        RelativeLayout.LayoutParams parameter = (RelativeLayout.LayoutParams) rl_main.getLayoutParams();
                        parameter.setMargins(margin, margin, margin, margin); // left, top, right, bottom
                        rl_main.setLayoutParams(parameter);
                        abc = etcount.getText().toString();


                    } else {
                        count = 25;
                        ll_selectborder.setBackgroundColor(Color.parseColor("#000000"));

                    }
                }


            }
        });

        minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean iscolor = true;

                if (iscolor) {
                    ll_selectborder.setBackgroundColor(Color.BLUE);
                    count--;
                    if (count >= 0) {

                        ll_selectborder.setBackgroundColor(Color.parseColor("#000000"));
                        etcount.setText(String.valueOf(count));
                        int margin = Integer.parseInt(etcount.getText().toString());
                        RelativeLayout.LayoutParams parameter = (RelativeLayout.LayoutParams) rl_main.getLayoutParams();
                        parameter.setMargins(margin, margin, margin, margin); // left, top, right, bottom
                        rl_main.setLayoutParams(parameter);
                        abc = etcount.getText().toString();

                    } else {
                        count = 0;
                        ll_selectborder.setBackgroundColor(Color.parseColor("#000000"));
                    }

                }


            }
        });
        etcount.setText(abc);

        mPopupWindow.setOutsideTouchable(true);

        mPopupWindow.showAtLocation(rl_main, Gravity.BOTTOM, 160, 320);
    }


    public void changeRotation() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.fliprotation_popup, null);

        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        mPopupWindow.setAnimationStyle(R.style.animation_for_popup);
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        tvHorizonal = customView.findViewById(R.id.tvHorizontal);
        tvVertical = customView.findViewById(R.id.tvVertical);

        tvHorizonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showingFirst) {
                    ivImage.setRotationY(180f);
                    showingFirst = false;
                } else {
                    ivImage.setRotationY(0f);
                    showingFirst = true;
                }

                mPopupWindow.dismiss();
            }

        });

        tvVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (showingsecond) {
                    ivImage.setRotationX(180f);
                    showingsecond = false;
                } else {
                    ivImage.setRotationX(0f);
                    showingsecond = true;
                }
                mPopupWindow.dismiss();


            }
        });
        mPopupWindow.setOutsideTouchable(true);

        mPopupWindow.showAtLocation(rl_main, Gravity.BOTTOM, 150, 300);
    }


    public void openFrames() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.frame_pop_up, null);

        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        mPopupWindow.setAnimationStyle(R.style.animation_for_popup);
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        mPopupWindow.setOutsideTouchable(true);


        RecyclerView rvList = customView.findViewById(R.id.rvList);


        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        PopupAdapter adepterFrameList = new PopupAdapter(context, R.layout.framelist_raw_item, Constances.frameLists, "popup");
        rvList.setAdapter(adepterFrameList);


        mPopupWindow.showAtLocation(rl_main, Gravity.BOTTOM, 150, 300);
    }

    public void setFrame(int imgId) {
        Constances.FrameId = imgId;
        Glide.with(context).load(Constances.frameLists.get(imgId).getFrame()).into(ivframe);
        mPopupWindow.dismiss();
    }


    public void EnableRuntimePermission() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreatePhotoActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            Log.e("ABC", "1");
            //  cameraPermission();
        }


        if (ActivityCompat.shouldShowRequestPermissionRationale(CreatePhotoActivity.this, permissionsRequired[0])) {
            //Show Information about why you need the permission

            Log.e("ABC", "4");
            AlertDialog.Builder builder = new AlertDialog.Builder(CreatePhotoActivity.this);
            builder.setTitle("Need Permissions");
            builder.setMessage("This app needs Write External permissions.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(CreatePhotoActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(Color.BLACK);
            pbutton.setBackgroundColor(Color.WHITE);
        } else {
            //just request the permission
            Log.e("ABC", "5");
            ActivityCompat.requestPermissions(CreatePhotoActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
        }
    }

/*
    void cameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityCreatePhoto.this,
                Manifest.permission.CAMERA)) {
           /// Log.e("ABC", "2");
            ///Toast.makeText(context, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {
            Log.e("ABC", "3");

            ActivityCompat.requestPermissions(ActivityCreatePhoto.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);


        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();

            Bitmap bitmap = null;
            try {
                Constances.cameraImage = true;
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photouri);
                Constances.bmp = bitmap;
                ivImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    //Toast.makeText(ActivityCreateFrame.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    // Toast.makeText(ActivityCreateFrame.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void displayAdMob() {

    }

    public void displayInterstitialFacbookAd() {


    }

}
