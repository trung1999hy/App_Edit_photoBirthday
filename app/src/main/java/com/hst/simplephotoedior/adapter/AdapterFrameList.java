package com.hst.simplephotoedior.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hst.simplephotoedior.MyApplication;
import com.hst.simplephotoedior.activity.ActivityCreatePhoto;
import com.hst.simplephotoedior.ModelClass.ModelclassFrameList;
import com.hst.simplephotoedior.R;
import com.hst.simplephotoedior.Utils.Constances;


import java.util.ArrayList;
import java.util.List;

public class AdapterFrameList extends RecyclerView.Adapter<AdapterFrameList.ViewHolder> {

    private Context context;
    private ArrayList<ModelclassFrameList> frameLists;
    LayoutInflater layoutInflater;

    public AdapterFrameList(Context context, ArrayList<ModelclassFrameList> frameLists) {
        this.context = context;
        this.frameLists = frameLists;
       Constances.frameLists = frameLists;

        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rv_frame_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.imageView.setImageResource(frameLists.get(position).getFrame());
        if (frameLists.get(position).isNeedPoint()) {
            holder.pointView.setVisibility(View.VISIBLE);
        }
        else {
            holder.pointView.setVisibility(View.GONE);
        }
        final int frame = frameLists.get(position).getFrame();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameLists.get(position).isNeedPoint() && MyApplication.getInstance().getValueCoin() >= 2) {
                    MyApplication.getInstance().setValueCoin(MyApplication.getInstance().getValueCoin() - 2);
                }
                else {
                    if (frameLists.get(position).isNeedPoint()) {
                        Toast.makeText(context,
                                context.getString(R.string.need_coin),
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                }

                Constances.FrameId1 = position;
                Intent intent = new Intent(context, ActivityCreatePhoto.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //   intent.putExtra("frameId", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return frameLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;

        RelativeLayout pointView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv);
            imageView = itemView.findViewById(R.id.iv1);
            pointView = itemView.findViewById(R.id.point_layout);
        }
    }
}
