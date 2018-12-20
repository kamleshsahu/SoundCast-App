package com.kamlesh.soundcastapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.kamlesh.soundcastapp.Model.Apidata;
import com.kamlesh.soundcastapp.Model.Result;
import com.kamlesh.soundcastapp.R;

import java.util.List;


/**
 * Created by RAJA on 18-07-2018.
 */

public class AdapterList extends RecyclerView.Adapter<AdapterList.AdapterAllHolder> {
    private Context context;
    private List<Result> data;
    public AdapterList(Context context,List<Result> data){
        this.context=context;
        this.data=data;

    }

    @Override
    public AdapterAllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.listview_item_travelwithactivity,parent,false);
        return new AdapterAllHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterAllHolder holder, final int position) {

       holder.title.setText(data.get(position).getTitle());


       try {
           Glide.with(context)
                   .load(data.get(position).getThumbnail())
                   //   .override(100, 200) // resizes the image to 100x200 pixels but does not respect aspect ratio
                   .into(holder.icon);
       }catch (Exception e){
           e.printStackTrace();
       }

       holder.item.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });

    }

    @Override
    public int getItemCount() {

        return data.size();
    }



    public class AdapterAllHolder extends RecyclerView.ViewHolder {

        TextView title,time;
        ImageView icon;LinearLayout item;

        public AdapterAllHolder(View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.itemContainer);
            time=(TextView) itemView.findViewById(R.id.time);
            icon=itemView.findViewById(R.id.icon);
            title=itemView.findViewById(R.id.title);
        }

    }

}
