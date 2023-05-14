package com.example.socialmediaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmediaapp.Model.Follow;
import com.example.socialmediaapp.Model.User;
import com.example.socialmediaapp.R;
import com.example.socialmediaapp.databinding.ItemFriendBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.HolderFriend>{
    List<Follow> list;
    Context context;

    public FollowAdapter(List<Follow> friendModels, Context context) {
        this.list = friendModels;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderFriend onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend,parent,false);
        return new HolderFriend(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderFriend holder, int position) {
        Follow model = list.get(position);
        FirebaseDatabase.getInstance().getReference()
                .child("user")
                .child(model.getFollowBy())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        Picasso.with(context)
                                .load(user.getUserPhoto())
                                .placeholder(R.drawable.user)
                                .into(holder.binding.profileImageUser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HolderFriend extends RecyclerView.ViewHolder{
        ItemFriendBinding binding;
        public HolderFriend(@NonNull View itemView) {
            super(itemView);
            binding = ItemFriendBinding.bind(itemView);
        }
    }
}
