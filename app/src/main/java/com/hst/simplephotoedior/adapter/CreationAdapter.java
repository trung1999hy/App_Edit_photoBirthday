package com.hst.simplephotoedior.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hst.simplephotoedior.activity.PreviewAcivity;
import com.hst.simplephotoedior.model.DownloadImage;
import com.hst.simplephotoedior.R;
import com.hst.simplephotoedior.util.Constances;

import java.util.ArrayList;

public class CreationAdapter extends RecyclerView.Adapter<CreationAdapter.ViewHolder> {


    ArrayList<DownloadImage> modelclassDownloadedImages;

    Context context;
    boolean favorite = false;

    public CreationAdapter(Context context, ArrayList<DownloadImage> modelclassDownloadedImages) {
        this.modelclassDownloadedImages = modelclassDownloadedImages;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_downloadedlist_item, parent, false);
        CreationAdapter.ViewHolder vh = new CreationAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        final String imgname;
        final String imageurl;
        final Bitmap thumb_image;

        imgname = modelclassDownloadedImages.get(position).getName();
        //  thumb_image=downloadedVideosModels.get(i).getThumb();
        if (imgname.endsWith(".jpg")) {
            imageurl = modelclassDownloadedImages.get(position).getImagepath();
            Constances.modelclassDownloadedImages = modelclassDownloadedImages;

            //holder.images.setImageBitmap(modelclassDownloadedImages.get(position).getThumbimage());
            Glide.with(context).load(modelclassDownloadedImages.get(position).getImagepath()).apply(new RequestOptions().placeholder(R.drawable.imageholder).override(200, 200)).into(holder.images);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PreviewAcivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("position", position);
                    // intent.putExtra("imageUrl",imageurl);
                    context.startActivity(intent);
                }
            });
        } else {

            //Glide.with(context).load(modelclassDownloadedImages.get(position).getImagepath()).apply(new RequestOptions().placeholder(R.drawable.imageholder).override(200, 200)).into(holder.images);
        }
    }

    @Override
    public int getItemCount() {
            return  modelclassDownloadedImages == null ? 0 : modelclassDownloadedImages.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView images;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            images = (ImageView) itemView.findViewById(R.id.iv1);
            cardView = itemView.findViewById(R.id.cv);


        }
    }
}
