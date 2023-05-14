package com.example.socialmediaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmediaapp.Model.StoryModel;
import com.example.socialmediaapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder> {
    List<StoryModel> modelList;
    Context context;

    public StoryAdapter(List<StoryModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story_design,parent,false);
        return new StoryHolder(view);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder holder, int position) {
        StoryModel model = modelList.get(position);
        holder.nametxt.setText(model.getName());
        holder.profileimg.setImageResource(model.getProfile());
        holder.storyTypeimg.setImageResource(model.getStoryType());
        holder.storyimg.setImageResource(model.getStory());
    }

    class StoryHolder extends RecyclerView.ViewHolder{
        ImageView profileimg,storyimg,storyTypeimg;
        TextView nametxt;
        public StoryHolder(@NonNull View item) {
            super(item);
            nametxt = item.findViewById(R.id.nameStory_text);
            profileimg = item.findViewById(R.id.profileStory_image);
            storyimg = item.findViewById(R.id.story_image);
            storyTypeimg = item.findViewById(R.id.storyType_image);
        }
    }
}
