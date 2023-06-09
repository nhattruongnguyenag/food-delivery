package vn.tdc.edu.fooddelivery.activities.user;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.activities.LoginActivity;
import vn.tdc.edu.fooddelivery.activities.admin.CategoryManagementActivity;
import vn.tdc.edu.fooddelivery.activities.admin.OrderManagementActivity;
import vn.tdc.edu.fooddelivery.activities.admin.ProductManagementActivity;
import vn.tdc.edu.fooddelivery.activities.admin.RoleManagementActivity;
import vn.tdc.edu.fooddelivery.activities.admin.UserManagementActivity;
import vn.tdc.edu.fooddelivery.components.ConfirmDialog;
import vn.tdc.edu.fooddelivery.enums.Role;
import vn.tdc.edu.fooddelivery.fragments.SearchFragment;
import vn.tdc.edu.fooddelivery.fragments.user.CartFragment;
import vn.tdc.edu.fooddelivery.fragments.user.HomeFragment;
import vn.tdc.edu.fooddelivery.fragments.user.NotificationFragment;
import vn.tdc.edu.fooddelivery.fragments.user.OrderFragment;
import vn.tdc.edu.fooddelivery.fragments.user.ProfileFragment;
import vn.tdc.edu.fooddelivery.models.UserModel;
import vn.tdc.edu.fooddelivery.utils.Authentication;
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
    private NavigationView navigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private View navigationHeader;
    private ShapeableImageView userImage;
    private TextView tvUserName;
    private TextView tvUserEmail;
    private Fragment prevFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        searchView = findViewById(R.id.searchView);
        navigationHeader = navigation.getHeaderView(0);
        userImage = navigationHeader.findViewById(R.id.userImage);
        tvUserName = navigationHeader.findViewById(R.id.tvUserName);
        tvUserEmail = navigationHeader.findViewById(R.id.tvUserEmail);
        setSupportActionBar(toolbar);
        setToggleActionNavigationView();
        setMenuByUserRole();
        setUserLoginInfo();

        // -------------------CHU DINH HANH----------------//
        bottomNavigation = findViewById(R.id.bottomNavigation);
        MainActivity.setMainActivitySave(MainActivity.this);
        catchDataCartIconNotify();
        catchDataNotifyIcon();
        // -------------------------End------------------------//

        prevFragment = setFragment(HomeFragment.class, R.id.frameLayout, false);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigation.setCheckedItem(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        prevFragment = setFragment(HomeFragment.class, R.id.frameLayout, false);
                        break;
                    case R.id.menu_cart:
                        prevFragment = setFragment(CartFragment.class, R.id.frameLayout, false);
                        break;
                    case R.id.menu_order:
                        prevFragment = setFragment(OrderFragment.class, R.id.frameLayout, false);
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
                clearSearchView();
                return true;
            }
        });

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomNavigation.setSelectedItemId(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        prevFragment = setFragment(HomeFragment.class, R.id.frameLayout, false);
                        drawerLayout.close();
                        break;
                    case R.id.menu_cart:
                        prevFragment = setFragment(CartFragment.class, R.id.frameLayout, false);
                        drawerLayout.close();
                        break;
                    case R.id.menu_order:
                        prevFragment = setFragment(OrderFragment.class, R.id.frameLayout, false);
                        drawerLayout.close();
                        break;
                    case R.id.menu_notification:
                        prevFragment = setFragment(NotificationFragment.class, R.id.frameLayout, false);
                        drawerLayout.close();
                        break;
                    case R.id.menu_profile:
                        prevFragment = setFragment(ProfileFragment.class, R.id.frameLayout, false);
                        drawerLayout.close();
                        break;
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
                    case R.id.nav_role_management:
                        switchActivity(RoleManagementActivity.class, "Quản lý vai trò");
                        break;
                    case R.id.nav_logout:
                        ConfirmDialog confirmDialog = new ConfirmDialog(MainActivity.this);
                        confirmDialog.setTitle("Đăng xuất");
                        confirmDialog.setMessage("Đăng xuất khỏi tài khoản của bạn ?");
                        confirmDialog.setOnDialogComfirmAction(new ConfirmDialog.DialogComfirmAction() {
                            @Override
                            public void cancel() {
                                confirmDialog.dismiss();
                            }

                            @Override
                            public void ok() {
                                confirmDialog.dismiss();
                                if (Authentication.logout()) {
                                    switchActivity(LoginActivity.class, "Logout");
                                    finish();
                                };
                            }
                        });

                        confirmDialog.show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setFragment(SearchFragment.class, R.id.frameLayout, false);
                } else {
                    Log.d("TAG", "onFocusChange: bo cham");
                }
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

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
               if (Authentication.isUpdated) {
                   setUserLoginInfo();
                   Authentication.isUpdated = false;
               }
            }
        });
    }

    public static void clearSearchView() {
        searchView.setQuery("", false);
        searchView.clearFocus();
    }

    private void setNavigationView() {

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUserLoginInfo() {
        Glide.with(this).load(Authentication.getUserLogin().getImageUrl())
                .into(userImage);
        tvUserName.setText(Authentication.getUserLogin().getFullName());
        tvUserEmail.setText(Authentication.getUserLogin().getEmail());
    }

    private void setMenuByUserRole() {
        navigation.getMenu().clear();
        if (Authentication.getUserLogin().getRolesString().contains(Role.ADMIN.getName())) {
            navigation.inflateMenu(R.menu.navigation_menu_admin);
        } else if (Authentication.getUserLogin().getRolesString().contains(Role.SHIPPER.getName())) {
            navigation.inflateMenu(R.menu.navigation_menu_shipper);
        } else {
            navigation.inflateMenu(R.menu.navigation_menu_customer);
            navigation.setCheckedItem(R.id.menu_home);
        }
    }

    private void setToggleActionNavigationView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
//        createNum(FileUtils.cartList.size(), 2);
    }

    public void catchDataNotifyIcon() {
//        createNum(FileUtils.arrayListNotifications.size(), 3);
    }

    public static void CreateNumberBuyButtonEventClick() {
//            createNum(FileUtils.cartList.size(), 2);
    }

    public void clearAllSelectNavigation() {

    }


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
}