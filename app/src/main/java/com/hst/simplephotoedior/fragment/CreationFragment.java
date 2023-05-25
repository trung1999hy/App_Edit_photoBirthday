package com.hst.simplephotoedior.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hst.simplephotoedior.activity.HomeActivity;
import com.hst.simplephotoedior.adapter.CreationAdapter;
import com.hst.simplephotoedior.model.DownloadImage;
import com.hst.simplephotoedior.R;
import com.hst.simplephotoedior.util.Constances;

import java.io.File;
import java.util.ArrayList;

public class CreationFragment extends Fragment {
    View view;
    Context context;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout rl_Creation_list_empty_list;
    TextView tvStart;

    ArrayList<DownloadImage> filenames = new ArrayList<DownloadImage>();
    String path = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES).toString() + File.separator + Constances.FolderName;

    CreationAdapter adapterMyCreations;
    ArrayList<DownloadImage> dataCombined = null; //You are passing this null


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_creations_list, container, false);
        context = container.getContext();
        recyclerView = view.findViewById(R.id.rv_downloadimages);
        rl_Creation_list_empty_list = view.findViewById(R.id.rl_Creation_list_empty_list);
        tvStart = view.findViewById(R.id.tvStart);

        getMyVideoList();

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
            }
        });
        // Toast.makeText(context, "" + adapterMyCreations.getItemCount(), Toast.LENGTH_SHORT).show();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void getMyVideoList() {
        filenames.clear();
        File directory = new File(path);
        File[] files = directory.listFiles();
        ArrayList<DownloadImage> dataCombined = new ArrayList<DownloadImage>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {

                String file_name = files[i].getName();
                String file_path = files[i].getPath();
                Bitmap thumb = ThumbnailUtils.createVideoThumbnail(file_path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                // you can store name to arraylist and use it later
                filenames.add(new DownloadImage(file_name, file_path, thumb));
            }

            if (files.length != 0) {
                /*tv_more_download_nodata.setVisibility(View.GONE);
                rv_more_download.setVisibility(View.VISIBLE);*/
            } else {
                /*tv_more_download_nodata.setVisibility(View.VISIBLE);
                rv_more_download.setVisibility(View.GONE);*/
            }


            dataCombined.addAll(filenames);

            if (dataCombined.size() != 0) {

                recyclerView.setVisibility(View.VISIBLE);
                adapterMyCreations = new CreationAdapter(context, dataCombined);
                recyclerView.setAdapter(adapterMyCreations);
            } else {


                rl_Creation_list_empty_list.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }

        } else {

            rl_Creation_list_empty_list.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            /*tv_more_download_nodata.setVisibility(View.VISIBLE);
            rv_more_download.setVisibility(View.GONE);*/
        }
    }


}
