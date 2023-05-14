package com.example.socialmediaapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socialmediaapp.Adapter.UserAdapter;
import com.example.socialmediaapp.Model.User;
import com.example.socialmediaapp.R;
import com.example.socialmediaapp.databinding.FragmentSearchBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    List<User> list;
    UserAdapter adapter;
    FragmentSearchBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater(), container, false);
        list = new ArrayList<>();
        adapter = new UserAdapter(list, getContext());
        binding.listSearch.setAdapter(adapter);

        database.getReference().child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    user.setUserId(dataSnapshot.getKey());
                    //Toast.makeText(getActivity(), user.getUserId()+"and "+mAuth.getUid()., Toast.LENGTH_SHORT).show();
                    if(!dataSnapshot.getKey().equals(mAuth.getUid().toString())){
                        list.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}