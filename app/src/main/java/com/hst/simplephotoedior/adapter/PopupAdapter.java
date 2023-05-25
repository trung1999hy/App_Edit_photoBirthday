package com.hst.simplephotoedior.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hst.simplephotoedior.activity.CreatePhotoActivity;
import com.hst.simplephotoedior.model.Frame;
import com.hst.simplephotoedior.R;
import com.hst.simplephotoedior.util.Constances;


import java.util.ArrayList;

public class PopupAdapter extends RecyclerView.Adapter<PopupAdapter.FrameListViewHolder>{

    ArrayList<Frame> LocalFrame;
    Context context;
    public  int resource;
    String type;


    public PopupAdapter(Context context, int resource, ArrayList<Frame> LocalFrame, String type) {
        this.context = context;
        this.resource = resource;
        this.LocalFrame = LocalFrame;
        this.type=type;
    }

    @NonNull
    @Override
    public PopupAdapter.FrameListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resource, parent, false);
        return new PopupAdapter.FrameListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopupAdapter.FrameListViewHolder holder, final int position) {

        holder.iv_framlist_raw.setImageResource(LocalFrame.get(position).getFrame());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type.equals("popup"))
                {
                    Constances.FrameId=position;
                    CreatePhotoActivity.getInstance().setFrame(position);
                }
                 }
        });
    }

    @Override
    public int getItemCount() {
        return LocalFrame.size();
    }

    public class FrameListViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_framlist_raw;

        public FrameListViewHolder(View itemView) {
            super(itemView);
            iv_framlist_raw = (ImageView) itemView.findViewById(R.id.iv_framlist_raw);
        }
    }



}
