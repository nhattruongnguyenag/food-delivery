package vn.tdc.edu.fooddelivery.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.fragment.AbstractFragment;
import vn.tdc.edu.fooddelivery.fragment.CartFragment;
import vn.tdc.edu.fooddelivery.fragment.HomeFragment;
import vn.tdc.edu.fooddelivery.fragment.NotificationFragment;
import vn.tdc.edu.fooddelivery.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    NavigationView navigation;
    BottomNavigationView bottomNavigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    private AbstractFragment fragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        navigation = findViewById(R.id.navigation);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.search_bar_background, getTheme());

        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawerOpen, R.string.drawerClose);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_navigation);

        setFragment(HomeFragment.class);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        setFragment(HomeFragment.class);
                        break;
                    case R.id.menu_cart:
                        setFragment(CartFragment.class);
                        break;
                    case R.id.menu_notification:
                        setFragment(NotificationFragment.class);
                        break;
                    case R.id.menu_profile:
                        setFragment(ProfileFragment.class);
                        break;
                    default:
                        setFragment(HomeFragment.class);
                        break;
                }
                return true;
            }
        });

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        });
    }

    private void setFragment(Class tClass) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, tClass, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}