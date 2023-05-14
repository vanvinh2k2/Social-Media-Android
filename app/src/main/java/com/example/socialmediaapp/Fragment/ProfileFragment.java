package com.example.socialmediaapp.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socialmediaapp.Adapter.FollowAdapter;
import com.example.socialmediaapp.Model.Follow;
import com.example.socialmediaapp.Model.User;
import com.example.socialmediaapp.R;
import com.example.socialmediaapp.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    FollowAdapter adapter;
    ArrayList<Follow> arrayList;
    FragmentProfileBinding binding;
    private final int KEY_PICK_COVER = 1;
    private final int KEY_PICK_USER = 2;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("user").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    Picasso.with(getActivity())
                            .load(user.getCoverPhoto())
                            .placeholder(R.drawable.background)
                            .into(binding.backgroundImage);
                    Picasso.with(getActivity())
                            .load(user.getUserPhoto())
                            .placeholder(R.drawable.user)
                            .into(binding.profileImageUser);
                    binding.nameProfile.setText(user.getName());
                    binding.about.setText(user.getProfession());
                    binding.countFollow.setText(user.getFollowerCount()+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        arrayList = new ArrayList<>();
        adapter = new FollowAdapter(arrayList,getContext());
        binding.listFriend.setAdapter(adapter);
        database.getReference().child("user")
                .child(mAuth.getUid())
                .child("follows")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Follow follow = dataSnapshot.getValue(Follow.class);
                            //arrayList.clear();
                            arrayList.add(follow);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        binding.cameraCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, KEY_PICK_COVER);
            }
        });
        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, KEY_PICK_USER);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final StorageReference reference = storage.getReference();

        if(requestCode == KEY_PICK_COVER && data != null) {
            Uri uri = data.getData();
            binding.backgroundImage.setImageURI(uri);
            reference.child("cover_photo").child(FirebaseAuth.getInstance().getUid()).putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if (taskSnapshot.getMetadata() != null) {
                                if (taskSnapshot.getMetadata().getReference() != null) {
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String download = uri.toString();
                                            Toast.makeText(getContext(), "Cover photo saved", Toast.LENGTH_SHORT).show();
                                            database.getReference().child("user").child(mAuth.getUid()).child("coverPhoto").setValue(download);
                                        }
                                    });
                                }
                            }
                        }
                    });
        }
        else if(requestCode == KEY_PICK_USER && data != null) {
            Uri uri = data.getData();
            binding.profileImageUser.setImageURI(uri);
            reference.child("user_photo").child(FirebaseAuth.getInstance().getUid()).putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if (taskSnapshot.getMetadata() != null) {
                                if (taskSnapshot.getMetadata().getReference() != null) {
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String download = uri.toString();
                                            Toast.makeText(getContext(), "User photo saved", Toast.LENGTH_SHORT).show();
                                            database.getReference().child("user").child(mAuth.getUid()).child("userPhoto").setValue(download);
                                        }
                                    });
                                }
                            }
                        }
                    });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}