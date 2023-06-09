package com.hst.simplephotoedior.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hst.simplephotoedior.activity.CreatePhotoActivity;
import com.hst.simplephotoedior.model.FontDetail;
import com.hst.simplephotoedior.R;

import java.util.ArrayList;


public class FontAdapter extends RecyclerView.Adapter<FontAdapter.FontListForShayriDetailViewHolder> {

    Context context;
    int resource;
    ArrayList<FontDetail> arrayList;

    public FontAdapter(Context context, int resource, ArrayList<FontDetail> arrayList) {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;

    }

    @Override
    public FontListForShayriDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resource, parent, false);
        return new FontListForShayriDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FontListForShayriDetailViewHolder holder, final int position) {

        String[] fontlist = arrayList.get(position).getFontName().split("\\.");

        holder.tvFont.setText(fontlist[0].replace("-", " "));
        try {
            holder.tvFont.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/" + arrayList.get(position).getFontName()));
        } catch (Exception e) {
            Log.e("Error", "Error");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePhotoActivity.getInstance().SetFontToText(arrayList.get(position).getFontName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FontListForShayriDetailViewHolder extends RecyclerView.ViewHolder {

        TextView tvFont;

        public FontListForShayriDetailViewHolder(View itemView) {
            super(itemView);
            tvFont = (TextView) itemView.findViewById(R.id.tvFontName);
        }
    }
}