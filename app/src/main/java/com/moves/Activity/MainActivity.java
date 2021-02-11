package com.moves.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.moves.Fragment.FavoriteFragment;
import com.moves.Fragment.MoveFragment;
import com.moves.Fragment.TrendingFragment;
import com.moves.Fragment.TvFragment;
import com.moves.R;

public class MainActivity extends AppCompatActivity {

    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                    new MoveFragment()).commit();
        }

    }

    private void bottomNavigation() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Move", R.drawable.ic_movie);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Tv", R.drawable.ic_show);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Trending", R.drawable.ic_trend);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Favorite", R.drawable.ic_favorite);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#758bff"));
        bottomNavigation.setAccentColor(Color.parseColor("#ffffff"));
        bottomNavigation.setInactiveColor(Color.parseColor("#f2f2f2"));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        replaceFragment(new MoveFragment());
                        break;
                    case 1:
                        replaceFragment(new TvFragment());
                        break;
                    case 2:
                        replaceFragment(new TrendingFragment());
                        break;
                    case 3:
                        replaceFragment(new FavoriteFragment());
                        break;
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }
}