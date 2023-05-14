package com.example.socialmediaapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialmediaapp.Adapter.NotificationAdapter;
import com.example.socialmediaapp.Model.NotificationModel;
import com.example.socialmediaapp.R;

import java.util.ArrayList;

public class Notification2Fragment extends Fragment {
    RecyclerView listNotificationRcv;
    ArrayList<NotificationModel> modelArrayList;
    NotificationAdapter adapter;
    public Notification2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification2, container, false);
        listNotificationRcv = view.findViewById(R.id.list_notification);
        modelArrayList = new ArrayList<>();
        modelArrayList.add(new NotificationModel(R.drawable.avatar,"<b>Hòa Ngô</b> nhắc đến bạn trong 1 cuộc trò chuyện.","Just now"));
        modelArrayList.add(new NotificationModel(R.drawable.user,"<b>Trọng Cảnh</b> đã thích bài viết của bạn.","Just now"));
        adapter = new NotificationAdapter(modelArrayList,getContext());
        listNotificationRcv.setAdapter(adapter);
        return view;
    }
}