package com.hst.simplephotoedior.util;

import android.graphics.Bitmap;


import com.hst.simplephotoedior.model.DownloadImage;
import com.hst.simplephotoedior.model.Frame;

import java.io.File;
import java.util.ArrayList;

public class Constances {

    public static String emailid = "test@gmail.com";
    public static String shareapp_url = "https://play.google.com/store/apps/details?id=com.hst.animalphotomaker";
    public static String Rateapp = "http://play.google.com/store/apps/details?id=com.hst.animalphotomaker";
    public static String Moreapp = "https://play.google.com/store/apps/details?id=com.hst.animalphotomaker";

    public static String FontStyle="ABeeZee.otf";
    public static boolean ischangetypeface = false;
    public static boolean imageSet=false,cameraImage = false;
    public static  int  FrameId,FrameId1;
    public static Bitmap Image,bmp;
    public static String FolderName = "Animal PhotoMaker";
    public static File filePath;
    public static boolean AllowToOpenAdvertise = false;
    public static boolean isFirstTimeOpen = true;
    public static  ArrayList<Frame> frameLists;
    public static  ArrayList<DownloadImage> modelclassDownloadedImages;

//    public static String adType = "facebook";
    public static String adType = "Ad Mob";

}
