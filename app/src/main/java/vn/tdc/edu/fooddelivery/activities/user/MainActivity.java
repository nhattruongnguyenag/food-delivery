package vn.tdc.edu.fooddelivery.activities.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

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
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class MainActivity extends AbstractActivity {
    // ------------------CHU DINH HANH----------------//
    private static BottomNavigationView bottomNavigation;
    private static Activity mainActivitySave;

    public static SearchView searchView;

    public static Activity getMainActivitySave() {
        return mainActivitySave;
    }

    public static void setMainActivitySave(Activity mainActivitySave) {
        MainActivity.mainActivitySave = mainActivitySave;

    }

    // --------------------------End--------------------------//
    NavigationView navigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    // private SearchView searchView;

    private Fragment prevFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        // -------------------CHU DINH HANH----------------//
        bottomNavigation = findViewById(R.id.bottomNavigation);
        MainActivity.setMainActivitySave(MainActivity.this);
        catchDataCartIconNotify();
        catchDataNotifyIcon();
        // -------------------------End------------------------//
        navigation = findViewById(R.id.navigation);
        // Show the navigation view and display the button for navigation view toggle in
        // tool bar
        toolbar = findViewById(R.id.toolbar);
        // ---------Search--------------------------//
        searchView = findViewById(R.id.searchView);
        // ---------Search--------------------------//
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
                        switchActivity(ProductManagementActivity.class, "Quản lý hàng hoá");
                        break;
                    case R.id.nav_order_management:
                        switchActivity(OrderManagementActivity.class, "Quản lý đơn hàng");
                        break;
                    case R.id.nav_user_management:
                        switchActivity(UserManagementActivity.class, "Quản lý người dùng");
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

        // searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener()
        // {
        // @Override
        // public void onFocusChange(View view, boolean b) {
        // setToolbarButtonToClearFocusSearchView();
        // setFragment(SearchFragment.class,R.id.frameLayout,false);
        // }
        // });

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
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawerOpen,
                R.string.drawerClose);
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
                setFragment(prevFragment.getClass(), R.id.frameLayout, false);
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

    // -------------------------------CHU DINH
    // HANH----------------------------------------//

    public void catchDataCartIconNotify() {
        if (FileUtils.cartList == null) {
            FileUtils.cartList = new ArrayList<>();
        }
        // Them vo o day

        createNum(FileUtils.cartList.size(), 2);
    }

    public void catchDataNotifyIcon() {
        if (FileUtils.arrayListNotifications == null) {
            FileUtils.arrayListNotifications = new ArrayList<>();
        }
        // Them vo o day
        createNum(FileUtils.arrayListNotifications.size(), 3);
    }

    public static void CreateNumberBuyButtonEventClick() {
        if (FileUtils.cartList != null) {
            createNum(FileUtils.cartList.size(), 2);
        }
    }

    public void clearAllSelectNavigation() {

    }

    // -------------------------------------Create number notify in navigation bar
    // bottom--------------------------------------------//
    public static void createNum(int number, int menu) {
        Context context = MainActivity.getMainActivitySave();
        bottomNavigation
                .setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
                    @Override
                    public void onNavigationItemReselected(@NonNull MenuItem item) {

                    }
                });
        BadgeDrawable badgeExplorer = null;
        switch (menu) {
            case 1:
                badgeExplorer = bottomNavigation.getOrCreateBadge(R.id.menu_home);
                break;
            case 2:
                badgeExplorer = bottomNavigation.getOrCreateBadge(R.id.menu_cart);
                break;
            case 3:
                badgeExplorer = bottomNavigation.getOrCreateBadge(R.id.menu_notification);
                break;
            case 4:
                badgeExplorer = bottomNavigation.getOrCreateBadge(R.id.menu_profile);
                break;
        }
        if (number >= 1) {
            badgeExplorer.setVisible(true);
            badgeExplorer.setVerticalOffset(dpToPx(context, 1));
            badgeExplorer.setNumber(number);
            // MARK
            badgeExplorer.setBackgroundColor(getMainActivitySave().getColor(R.color.red));
            badgeExplorer.setBadgeTextColor(getMainActivitySave().getColor(R.color.white));
        } else {
            badgeExplorer.setVisible(false);
            badgeExplorer.setBackgroundColor(getMainActivitySave().getColor(R.color.white));
            badgeExplorer.setBadgeTextColor(getMainActivitySave().getColor(R.color.white));
        }
    }

    public static int dpToPx(Context context, int dp) {
        Resources resource = context.getResources();
        return Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ((Resources) resource).getDisplayMetrics()));
    }
    // -----------------------------------------------End--------------------------------------------------------//
}