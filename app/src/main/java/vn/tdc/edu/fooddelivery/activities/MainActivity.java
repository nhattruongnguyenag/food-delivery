package vn.tdc.edu.fooddelivery.activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.admin.CategoryManagementActivity;
import vn.tdc.edu.fooddelivery.activities.admin.OrderManagementActivity;
import vn.tdc.edu.fooddelivery.activities.admin.ProductManagementActivity;
import vn.tdc.edu.fooddelivery.activities.admin.UserManagementActivity;
import vn.tdc.edu.fooddelivery.fragments.CartFragment;
import vn.tdc.edu.fooddelivery.fragments.HomeFragment;
import vn.tdc.edu.fooddelivery.fragments.NotificationFragment;
import vn.tdc.edu.fooddelivery.fragments.ProfileFragment;
import vn.tdc.edu.fooddelivery.fragments.SearchFragment;
import vn.tdc.edu.fooddelivery.models.CartModel_Hanh;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class MainActivity extends AbstractActivity {
    NavigationView navigation;
    private static BottomNavigationView bottomNavigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    public static ImageButton searchImgBtn;
    public static SearchView searchView;


    //----------------------------------
    private static Activity mainActivitySave;

    public static Activity getMainActivitySave() {
        return mainActivitySave;
    }
    //------------------------------------------

    public static void setMainActivitySave(Activity mainActivitySave) {
        MainActivity.mainActivitySave = mainActivitySave;
    }
    //----------------------------------

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //---------------------Cart-----------------------
        MainActivity.setMainActivitySave(MainActivity.this);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        //-----------------------Catch data cart when start--------------------------
        catchDataCartIconNotify();
        //-------------------------------------------------
        //-----------------------Catch data notify when start--------------------------
        catchDataNotifyIcon();
        //-------------------------------------------------
        anhXaSearch();
        setVisible();
        navigation = findViewById(R.id.navigation);
        // Show the navigation view and display the button for navigation view toggle in tool bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_navigation);

        setFragment(HomeFragment.class, R.id.frameLayout, false);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setVisible();
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        setFragment(HomeFragment.class, R.id.frameLayout, false);
                        break;
                    case R.id.menu_cart:
                        setFragment(CartFragment.class, R.id.frameLayout, false);
                        break;
                    case R.id.menu_notification:
                        setFragment(NotificationFragment.class, R.id.frameLayout, false);
                        break;
                    case R.id.menu_profile:
                        setFragment(ProfileFragment.class, R.id.frameLayout, false);
                        break;
                    default:
                        setFragment(HomeFragment.class, R.id.frameLayout, false);
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
        catchsearchImgBtnEvent();
    }

    private void anhXaSearch() {
        searchView = findViewById(R.id.search_area_fragment1);
        searchImgBtn = findViewById(R.id.search_area_fragment2);
    }

    private void setVisible() {
        searchView.setVisibility(View.INVISIBLE);
        searchImgBtn.setVisibility(View.VISIBLE);
    }


    public void catchsearchImgBtnEvent() {
        searchImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(SearchFragment.class, R.id.frameLayout, false);
            }
        });
    }

    public void catchDataCartIconNotify() {
        if (FileUtils.cartList == null) {
            FileUtils.cartList = new ArrayList<>();
        }
        FileUtils.cartList.add(new CartModel_Hanh("hanh", 12, 3, R.drawable.anh1, 1, ""));

        createNum(FileUtils.cartList.size(), 2);
    }

    public void catchDataNotifyIcon() {
        if (FileUtils.arrayListNotifications == null) {
            FileUtils.arrayListNotifications = new ArrayList<>();
        }
        createNum(FileUtils.arrayListNotifications.size(), 3);
    }

    public void createNum(int number, int menu) {
        Context context = MainActivity.getMainActivitySave();
        bottomNavigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
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
            //MARK
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
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ((Resources) resource).getDisplayMetrics()));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}