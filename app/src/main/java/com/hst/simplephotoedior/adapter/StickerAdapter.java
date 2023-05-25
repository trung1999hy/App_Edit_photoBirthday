package com.hst.simplephotoedior.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hst.simplephotoedior.activity.CreatePhotoActivity;
import com.hst.simplephotoedior.model.Sticker;
import com.hst.simplephotoedior.R;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

    private Context context;
    private Sticker[] stickerlist;
    LayoutInflater layoutInflater;

    public StickerAdapter(Context context, int framelist_raw_item, Sticker[] stickers, String popup) {
        this.context = context;
        this.stickerlist = stickers;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.stickerlist_raw_item, parent, false);
        return new StickerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(stickerlist[position].getImgId()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePhotoActivity.getInstance().setEmoji(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stickerlist.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_framlist_raw);

        }
    }
}
