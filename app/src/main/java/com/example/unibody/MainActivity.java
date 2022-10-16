package com.example.unibody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.unibody.chat.fragment.ChatFragment;
import com.example.unibody.finder.fragment.FinderFragment;
import com.example.unibody.me.fragment.MeFragment;
import com.example.unibody.share.fragment.ShareFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.finder);

        ChatFragment chatFragment = new ChatFragment();
        FinderFragment finderFragment = new FinderFragment();
        MeFragment meFragment = new MeFragment();
        ShareFragment shareFragment = new ShareFragment();

        replaceFragment(finderFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.chat:
                        replaceFragment(chatFragment);
                        return true;
                    case R.id.finder:
                        replaceFragment(finderFragment);
                        return true;
                    case R.id.share:
                        replaceFragment(shareFragment);
                        return true;
                    case R.id.me:
                        replaceFragment(meFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment,fragment);
        transaction.commit();
    }
}