package com.example.make_it_easy;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment()).commit();
        findViewById(R.id.fab).setOnClickListener(v ->   openFragment(new AddFragment()));
        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    openFragment(new HomeFragment());
                    return true;
                case R.id.progress_date:
                    openFragment(new ProgressDateFragment());
                    return true;
                case R.id.Placeholder:
                    openFragment(new AddFragment());
                    return true;
                case R.id.project:
                    openFragment(new ProjectFragment());
                    return true;
                case R.id.account:
                    openFragment(new AccountFragment());
                    return true;
            }
            return false;
        });

    }




    public void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();

    }
}