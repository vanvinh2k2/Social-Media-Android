package com.example.socialmediaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmediaapp.Model.DasboardModel;
import com.example.socialmediaapp.R;

import java.util.List;

public class DasboardAdapter extends RecyclerView.Adapter<DasboardAdapter.HolderDasboard>{
    List<DasboardModel> dasboardModelList;
    Context context;

    public DasboardAdapter(List<DasboardModel> dasboardModelList, Context context) {
        this.dasboardModelList = dasboardModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderDasboard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dasboard,parent,false);
        return new HolderDasboard(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDasboard holder, int position) {
        DasboardModel dasboardModel = dasboardModelList.get(position);
        holder.name.setText(dasboardModel.getName());
        holder.about.setText(dasboardModel.getAbout());
        holder.profile.setImageResource(dasboardModel.getProfile());
        holder.comment.setText(dasboardModel.getComment());
        holder.like.setText(dasboardModel.getLike());
        holder.share.setText(dasboardModel.getShare());
        holder.save.setImageResource(dasboardModel.getSave());
        holder.postImage.setImageResource(dasboardModel.getPostImage());
    }

    @Override
    public int getItemCount() {
        return dasboardModelList.size();
    }

    class HolderDasboard extends RecyclerView.ViewHolder{
        TextView name,about,like,comment,share;
        ImageView profile,postImage,save;
        public HolderDasboard(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            about = itemView.findViewById(R.id.about);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.chat);
            share = itemView.findViewById(R.id.share);
            profile = itemView.findViewById(R.id.profileStory_image);
            postImage = itemView.findViewById(R.id.storyType_image);
            save = itemView.findViewById(R.id.save);
        }
    }
}
