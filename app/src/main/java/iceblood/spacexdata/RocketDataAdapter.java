package iceblood.spacexdata;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Titan'ik on 08.02.2018.
 */

public class RocketDataAdapter extends RecyclerView.Adapter<RocketDataAdapter.ViewHolder> {
    private List<RocketData> rocketDataList;
    private Context context;
    Listener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView rocketName;
        TextView launchDate;
        TextView details;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            rocketName = (TextView) itemView.findViewById(R.id.rocket_name);
            launchDate = (TextView) itemView.findViewById(R.id.launch_date);
            details = (TextView) itemView.findViewById(R.id.details);
            imageView = (ImageView) itemView.findViewById(R.id.imageView2);
        }
    }

    public RocketDataAdapter(Context context) {
        this.context = context;
        this.rocketDataList = new ArrayList<>();
    }

    public void setRocketDataList(List<RocketData> rocketDataList ){
        this.rocketDataList = rocketDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_rocket, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.rocketName.setText(rocketDataList.get(position).getRocketName());
        holder.launchDate.setText(rocketDataList.get(position).getLaunchData());
        holder.details.setText(rocketDataList.get(position).getDetails());

        Glide.with(context)
                .load(rocketDataList.get(position).getUrlMissionPatch())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                //.placeholder(R.)
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    listener.onItemClick(rocketDataList.get(position).getVideoLink());
            }
        });


    }

    @Override
    public int getItemCount() {
        return rocketDataList.size();
    }

    public interface Listener{
        void onItemClick(String url);
    }
}

