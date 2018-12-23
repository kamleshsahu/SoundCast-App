package com.kamlesh.soundcastapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.kamlesh.soundcastapp.Model.DownloadModel.Result;
import com.kamlesh.soundcastapp.MusicPlayer;
import com.kamlesh.soundcastapp.R;

import java.util.List;


/**
 * Created by RAJA on 18-07-2018.
 */

public class AdapterList extends RecyclerView.Adapter<AdapterList.AdapterAllHolder> {
    private Context context;
    private List<Result> data;
    private Activity activity;
    public AdapterList(Context context, Activity activity, List<Result> data){
        this.context=context;
        this.data=data;
        this.activity=activity;

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
                   .load(data.get(position).getThumbnail_file().getUrl())
                   //   .override(100, 200) // resizes the image to 100x200 pixels but does not respect aspect ratio
                   .into(holder.icon);
       }catch (Exception e){
           e.printStackTrace();
       }

       holder.item.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent=new Intent(activity, MusicPlayer.class);

               intent.putExtra("currposition",position);
               context.startActivity(intent);
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
