package com.example.socialmediaapp.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmediaapp.Model.NotificationModel;
import com.example.socialmediaapp.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.HolderNotification>{
    List<NotificationModel> notificationModelList;
    Context context;

    public NotificationAdapter(List<NotificationModel> notificationModelList, Context context) {
        this.notificationModelList = notificationModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification,parent,false);
        return new HolderNotification(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNotification holder, int position) {
        NotificationModel model = notificationModelList.get(position);
        holder.profile.setImageResource(model.getProfile());
        holder.notification.setText(Html.fromHtml(model.getNotification()));
        holder.time.setText(model.getTime());
    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    class HolderNotification extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView notification,time;
        public HolderNotification(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile_imageUser);
            notification = itemView.findViewById(R.id.notificationApp);
            time = itemView.findViewById(R.id.timeNotification);
        }
    }
}
