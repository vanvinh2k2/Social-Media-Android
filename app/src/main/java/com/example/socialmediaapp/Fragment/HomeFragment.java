package com.example.socialmediaapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialmediaapp.Adapter.DasboardAdapter;
import com.example.socialmediaapp.Adapter.StoryAdapter;
import com.example.socialmediaapp.Model.DasboardModel;
import com.example.socialmediaapp.Model.StoryModel;
import com.example.socialmediaapp.Model.User;
import com.example.socialmediaapp.R;
import com.example.socialmediaapp.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    StoryAdapter adapterStory;
    DasboardAdapter adapterDasboard;
    ArrayList<StoryModel> modelList;
    ArrayList<DasboardModel> dasboardModels;
    RecyclerView storyRcv,dasboardRcv;
    FirebaseDatabase database;
    FragmentHomeBinding binding;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);

        DatabaseReference reference = database.getReference();
        reference.child("user").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    Picasso.with(getContext())
                            .load(user.getUserPhoto())
                            .placeholder(R.drawable.user)
                            .into(binding.profileUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        modelList = new ArrayList<>();
        modelList.add(new StoryModel(R.drawable.anh, R.drawable.video, R.drawable.user,"Trọng Cảnh"));
        modelList.add(new StoryModel(R.drawable.avatar, R.drawable.video, R.drawable.user,"Văn Thành"));
        adapterStory = new StoryAdapter(modelList,getActivity());
        binding.listStory.setAdapter(adapterStory);

        dasboardModels = new ArrayList<>();
        dasboardModels.add(new DasboardModel(R.drawable.user,R.drawable.anh_moi,R.drawable.ic_bookmark,
                "Phan Viết Trọng Cảnh","As strong as a horse","428","721","157"));
        dasboardModels.add(new DasboardModel(R.drawable.avatar,R.drawable.doi_hoa_tim,R.drawable.ic_bookmark,
                "Ngô Văn Hòa","Always happy","500","298","41"));
        dasboardModels.add(new DasboardModel(R.drawable.avatar,R.drawable.doi_hoa_tim,R.drawable.ic_bookmark,
                "Ngô Văn Hòa","Always happy","500","298","41"));
        adapterDasboard = new DasboardAdapter(dasboardModels,getContext());
        binding.listImage.setAdapter(adapterDasboard);
        return binding.getRoot();
    }
}