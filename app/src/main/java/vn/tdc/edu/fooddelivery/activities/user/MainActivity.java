package vn.tdc.edu.fooddelivery.activities.user;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.activities.admin.CategoryManagementActivity;
import vn.tdc.edu.fooddelivery.activities.admin.OrderManagementActivity;
import vn.tdc.edu.fooddelivery.activities.admin.ProductManagementActivity;
import vn.tdc.edu.fooddelivery.activities.admin.UserManagementActivity;
import vn.tdc.edu.fooddelivery.fragments.SearchFragment;
import vn.tdc.edu.fooddelivery.fragments.user.CartFragment;
import vn.tdc.edu.fooddelivery.fragments.user.HomeFragment;
import vn.tdc.edu.fooddelivery.fragments.user.NotificationFragment;
import vn.tdc.edu.fooddelivery.fragments.user.ProfileFragment;

public class MainActivity extends AbstractActivity {
    NavigationView navigation;
    BottomNavigationView bottomNavigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    private SearchView searchView;

    private Fragment prevFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        navigation = findViewById(R.id.navigation);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Show the navigation view and display the button for navigation view toggle in tool bar
        toolbar = findViewById(R.id.toolbar);
        searchView = findViewById(R.id.searchView);
        setSupportActionBar(toolbar);

        setNavigationView();

        prevFragment = setFragment(HomeFragment.class, R.id.frameLayout, false);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        prevFragment = setFragment(HomeFragment.class, R.id.frameLayout, false);
                        break;
                    case R.id.menu_cart:
                        prevFragment = setFragment(CartFragment.class, R.id.frameLayout, false);
                        break;
                    case R.id.menu_notification:
                        prevFragment = setFragment(NotificationFragment.class, R.id.frameLayout, false);
                        break;
                    case R.id.menu_profile:
                        prevFragment = setFragment(ProfileFragment.class, R.id.frameLayout, false);
                        break;
                    default:
                        prevFragment = setFragment(HomeFragment.class, R.id.frameLayout, false);
                        break;
                }
                return true;
            }
        });

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_product_management:
                        switchActivity( ProductManagementActivity.class, "Quản lý hàng hoá");
                        break;
                    case R.id.nav_order_management:
                        switchActivity(OrderManagementActivity.class, "Quản lý đơn hàng");
                        break;
                    case R.id.nav_user_management:
                        switchActivity( UserManagementActivity.class, "Quản lý người dùng");
                        break;
                    case R.id.nav_category_management:
                        switchActivity(CategoryManagementActivity.class, "Quản lý danh mục sản phẩm");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                setToolbarButtonToClearFocusSearchView();
                setFragment(SearchFragment.class,R.id.frameLayout,false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("searchView", "On submit");
                setToolbarButtonToOpenNavigationView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("searchView", "On text change");
                return true;
            }
        });
    }

    private void setNavigationView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        setToolbarButtonToOpenNavigationView();
    }

    private void setToolbarButtonToOpenNavigationView() {
        searchView.clearFocus();
        toolbar.setNavigationIcon(R.drawable.ic_navigation);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });
    }

    private void setToolbarButtonToClearFocusSearchView() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.clearFocus();
                setToolbarButtonToOpenNavigationView();
                setFragment(prevFragment.getClass(),R.id.frameLayout,false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        drawerLayout.close();
    }
}