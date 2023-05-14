package com.example.socialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.socialmediaapp.Fragment.AddFragment;
import com.example.socialmediaapp.Fragment.AddPostFragment;
import com.example.socialmediaapp.Fragment.HomeFragment;
import com.example.socialmediaapp.Fragment.NotificationFragment;
import com.example.socialmediaapp.Fragment.ProfileFragment;
import com.example.socialmediaapp.Fragment.SearchFragment;
import com.example.socialmediaapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("My profile");
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        binding.toolbar.setVisibility(View.INVISIBLE);
        transaction.replace(R.id.contentLayout,new HomeFragment());
        transaction.commit();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(item.getItemId()==R.id.home){
                    binding.toolbar.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.contentLayout,new HomeFragment());
                    transaction.commit();
                    //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    item.setChecked(true);
                }
                if(item.getItemId()==R.id.notificationApp){
                    binding.toolbar.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.contentLayout,new NotificationFragment());
                    transaction.commit();
                    //Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();
                    item.setChecked(true);
                }
                if(item.getItemId()==R.id.add){
                    binding.toolbar.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.contentLayout,new AddPostFragment());
                    transaction.commit();
                    //Toast.makeText(MainActivity.this, "Add", Toast.LENGTH_SHORT).show();
                    item.setChecked(true);
                }
                if(item.getItemId()==R.id.search){
                    binding.toolbar.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.contentLayout,new SearchFragment());
                    transaction.commit();
                    //Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                    item.setChecked(true);
                }
                if(item.getItemId()==R.id.user){
                    binding.toolbar.setVisibility(View.VISIBLE);
                    transaction.replace(R.id.contentLayout,new ProfileFragment());
                    transaction.commit();
                    //Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    item.setChecked(true);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.setting){
            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            //Toast.makeText(this, "You have selected settings", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}