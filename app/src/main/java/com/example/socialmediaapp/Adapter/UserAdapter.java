package com.example.socialmediaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmediaapp.Model.Follow;
import com.example.socialmediaapp.Model.User;
import com.example.socialmediaapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.HolderUser>{
    List<User> list;
    Context context;

    public UserAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
        return new HolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUser holder, int position) {
        User user = list.get(position);
        Picasso.with(context).load(user.getUserPhoto()).into(holder.someone);
        holder.name.setText(user.getName());
        holder.profession.setText(user.getProfession());

        FirebaseDatabase.getInstance().getReference()
                        .child("user")
                        .child(user.getUserId())
                        .child("follows")
                        .child(FirebaseAuth.getInstance().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()) {
                                    holder.follow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.activity_follow));
                                    holder.follow.setText("Following");
                                    holder.follow.setTextColor(context.getResources().getColor(R.color.gray));
                                    holder.follow.setEnabled(false);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Follow follow = new Follow();
                follow.setFollowBy(FirebaseAuth.getInstance().getUid());
                follow.setFollowAt(new Date().getTime());
                FirebaseDatabase.getInstance().getReference()
                        .child("user")
                        .child(user.getUserId())
                        .child("follows")
                        .child(FirebaseAuth.getInstance().getUid())
                        .setValue(follow).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("user")
                                        .child(user.getUserId())
                                        .child("followerCount")
                                        .setValue(user.getFollowerCount()+1)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                holder.follow.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.activity_follow));
                                                holder.follow.setText("Following");
                                                holder.follow.setTextColor(context.getResources().getColor(R.color.gray));
                                                holder.follow.setEnabled(false);
                                                Toast.makeText(context, "You followed "+user.getName(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HolderUser extends RecyclerView.ViewHolder{
        TextView name,profession;
        Button follow;
        ImageView someone;
        public HolderUser(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameUser);
            profession = itemView.findViewById(R.id.about);
            follow = itemView.findViewById(R.id.follow);
            someone = itemView.findViewById(R.id.profile_imageUser);
        }
    }
}
