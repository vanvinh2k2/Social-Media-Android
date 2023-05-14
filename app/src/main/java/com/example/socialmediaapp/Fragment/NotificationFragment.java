package com.example.socialmediaapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialmediaapp.Adapter.ViewPagerAdapter;
import com.example.socialmediaapp.R;
import com.google.android.material.tabs.TabLayout;

public class NotificationFragment extends Fragment {
    ViewPager pager;
    TabLayout layout;
    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contain, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, contain, false);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new Notification2Fragment());
        transaction.commit();
        pager = view.findViewById(R.id.container);
        layout = view.findViewById(R.id.title_content);
        pager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        layout.setupWithViewPager(pager);
        return view;
    }
}