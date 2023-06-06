package vn.tdc.edu.fooddelivery.fragments.user;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.user.MainActivity;
import vn.tdc.edu.fooddelivery.adapters.HomeMenuRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.adapters.HomeCategoryRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.components.ToaslCustomize;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;
import vn.tdc.edu.fooddelivery.models.CategoryModel_Test;
import vn.tdc.edu.fooddelivery.models.ProductModel_Test;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class HomeFragment extends AbstractFragment {

    private static LayoutInflater layoutInflater = null;
    private static View fragmentLayout = null;
    private ToaslCustomize toaslCustomize;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView_category;
    private RecyclerView recyclerView_menu;
    private HomeMenuRecyclerViewAdapter myAdapterMenu;
    private HomeCategoryRecyclerViewAdapter categoryAdapter;
    private SearchView searchView;
    private TextView txtMenu;
    private NestedScrollView nestedScrollView;
    private FloatingActionButton fab;
    private CategoryModel_Test categoryHasChoose;
    private int selectedRow = -1;
    private TextView previousCartViewItemClicked;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.fragment_home, container, false);
        layoutInflater = getLayoutInflater();
        //----------------------------Processing---------------------//
        fakeDataCartegory();
        fakeDataForMenu();
        //---------------------------------------------------------
        anhXa();
        setInvisibleFab();
        ActionViewFlipper();
        ClickEventMenu();
        ClickEventCategory();
        //Event scroll up
        ClickEventFab();
        catchEventScrollNestedScrollView();

        //--------------------------------End---------------------------//
        return fragmentLayout;
    }

    public void catchEventScrollNestedScrollView() {
        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= 5) {
                    setVisibleFab();
                } else {
                    setInvisibleFab();
                }
            }
        });
    }

    //-----------------------------Set disable for fab-----------------//
    public void setInvisibleFab() {
        fab.setVisibility(View.INVISIBLE);
    }

    public void setVisibleFab() {
        fab.setVisibility(View.VISIBLE);
    }
    //-----------------------------Set disable for fab-----------------//

    private void ClickEventFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nestedScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        nestedScrollView.smoothScrollTo(0, 0); // Cuộn lên trên
                    }
                });
            }
        });
    }


    public void setUpSystem() {
        recyclerView_menu.setFocusable(false);
        recyclerView_menu.setNestedScrollingEnabled(false);
    }

    //--------------------------------Fake data------------------------------//
    public void fakeDataCartegory() {
        if (FileUtils.categoryList.size() == 0) {
            FileUtils.categoryList.add(new CategoryModel_Test(1, "Com heo1", R.drawable.anh1));
            FileUtils.categoryList.add(new CategoryModel_Test(2, "Com heo2", R.drawable.anh1));
            FileUtils.categoryList.add(new CategoryModel_Test(3, "Com heo3", R.drawable.anh1));
        }
    }

    public void fakeDataForMenu() {
        if (FileUtils.product.size() == 0) {
            FileUtils.product.add(new ProductModel_Test(1, 1, "menu_1", 12, 900000, R.drawable.anh1, 4, "dwadwadwa"));
            FileUtils.product.add(new ProductModel_Test(2, 2, "menu_2", 12, 900000, R.drawable.anh1, 4, "dwadwadwa"));
            FileUtils.product.add(new ProductModel_Test(3, 1, "menu_1", 12, 900000, R.drawable.anh1, 4, "dwadwadwa"));
            FileUtils.product.add(new ProductModel_Test(4, 3, "menu_3", 12, 900000, R.drawable.anh1, 4, "dwadwadwa"));
            FileUtils.product.add(new ProductModel_Test(5, 1, "menu_1", 12, 900000, R.drawable.anh1, 4, "dwadwadwa"));
            FileUtils.product.add(new ProductModel_Test(6, 2, "menu_2", 12, 900000, R.drawable.anh1, 4, "dwadwadwa"));
            FileUtils.product.add(new ProductModel_Test(7, 1, "menu_1", 12, 900000, R.drawable.anh1, 4, "dwadwadwa"));
            FileUtils.product.add(new ProductModel_Test(8, 1, "menu_1", 12, 900000, R.drawable.anh1, 4, "dwadwadwa"));
            FileUtils.product.add(new ProductModel_Test(9, 1, "menu_1", 12, 900000, R.drawable.anh1, 4, "dwadwadwa"));
        }
    }

    public void anhXa() {
        nestedScrollView = fragmentLayout.findViewById(R.id.nestedScrollView);
        fab = fragmentLayout.findViewById(R.id.fab_btn);
        txtMenu = fragmentLayout.findViewById(R.id.txt_menu_home_screen);
        recyclerView_menu = fragmentLayout.findViewById(R.id.recyclerView_menu_home_screen);
        recyclerView_category = fragmentLayout.findViewById(R.id.recyclerView_category_home_screen);
        viewFlipper = fragmentLayout.findViewById(R.id.viewFlipper_home_screen);
        //---------------------Setup category recyclerView------------------------//
        binDataCategory();
        //-------------------------Setup menu recyclerView-------------------------//
        binDataMenu();
    }

    public void binDataCategory() {
        categoryAdapter = new HomeCategoryRecyclerViewAdapter((Activity) fragmentLayout.getContext(), R.layout.home_layout_category_item, FileUtils.categoryList);
        recyclerView_category.setAdapter(categoryAdapter);
    }


    public void binDataMenu() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentLayout.getContext());
        layoutManager.setOrientation(layoutManager.HORIZONTAL);
        recyclerView_category.setLayoutManager(layoutManager);
        //Setup menu recyclerView
        grifViewList(FileUtils.product);
    }

    public void grifViewList(ArrayList<ProductModel_Test> arrayList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(fragmentLayout.getContext(), 2);
        recyclerView_menu.setLayoutManager(gridLayoutManager);
        myAdapterMenu = new HomeMenuRecyclerViewAdapter((Activity) fragmentLayout.getContext(), R.layout.home_layout_menu_item, arrayList);
        recyclerView_menu.setAdapter(myAdapterMenu);
    }

    //----------------------------------Banner------------------------//
    private void ActionViewFlipper() {
        ArrayList<Integer> mangQuangCao = new ArrayList<Integer>();
        mangQuangCao.add(R.drawable.anh1);
        mangQuangCao.add(R.drawable.anh2);
        mangQuangCao.add(R.drawable.anh3);
        mangQuangCao.add(R.drawable.anh4);
        mangQuangCao.add(R.drawable.anh5);
        mangQuangCao.add(R.drawable.anh6);

        for (int i = 0; i < mangQuangCao.size(); i++) {
            ImageView imageView = new ImageView(fragmentLayout.getContext().getApplicationContext());
            Glide.with(fragmentLayout.getContext().getApplicationContext()).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(fragmentLayout.getContext().getApplicationContext(), R.anim.slide_in);
        Animation animation_out = AnimationUtils.loadAnimation(fragmentLayout.getContext().getApplicationContext(), R.anim.slide_out);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }

    //----------------------Catch event click----------------------------//
    public void ClickEventMenu() {
        myAdapterMenu.set_OnRecyclerViewOnClickListener(new HomeMenuRecyclerViewAdapter.onRecyclerViewOnClickListener() {
            @Override
            public void onItemRecyclerViewOnClickListener(int p, View CardView) {

            }
        });
    }


    public void addToCardAndNotify(ProductModel_Test cart) {
        toaslCustomize = new ToaslCustomize();
        toaslCustomize.customeToasl(fragmentLayout, layoutInflater);
        int index = -1;
        if (FileUtils.cartList == null) {
            FileUtils.cartList = new ArrayList<>();
        } else {
            for (int i = 0; i < FileUtils.cartList.size(); i++) {
                if (FileUtils.cartList.get(i).get_id() == (cart.get_id())) {
                    index = i;
                }
            }
        }
        if (index != -1) {
            ProductModel_Test cartOld = FileUtils.cartList.get(index);
            FileUtils.cartList.get(index).setQty(cartOld.getQty() + 1);
        } else {
            cart.setQty(1);
            FileUtils.cartList.add(cart);
        }
        //------------------Toasl--------------------------------------//

        //-----------------Create notify cart number--------------------//
        MainActivity.CreateNumberBuyButtonEventClick();
        //--------------------Toasl----------------------------------------//
    }


    //-------------------------------Chuyen man hinh---------------------------------//
    public <T> T setFragment(Class<T> tClass, int layout, boolean addToBackStack) {
        T fragment = null;
        try {
            fragment = tClass.newInstance();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction()
                    .replace(layout, (Fragment) fragment)
                    .setReorderingAllowed(true);
            if (addToBackStack) {
                transaction.addToBackStack(null);
            }

            transaction.commit();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (java.lang.InstantiationException e) {
            throw new RuntimeException(e);
        }

        return fragment;
    }

    //--------------------------Catch event click category---------------------------//
    public void ClickEventCategory() {
        categoryAdapter.setonRecyclerViewOnClickListener(new HomeCategoryRecyclerViewAdapter.onRecyclerViewOnClickListener() {

            @Override
            public void onItemRecyclerViewOnClickListener(int p, View CardView) {
                final ArrayList<ProductModel_Test> arrayFilter = new ArrayList<>();
                categoryHasChoose = FileUtils.categoryList.get(p);
                for (int i = 0; i < FileUtils.product.size(); i++) {
                    if (FileUtils.product.get(i).getCategory_id() == categoryHasChoose.getIdCategory()) {
                        arrayFilter.add(FileUtils.product.get(i));
                    }
                }

                //Add into menu
                grifViewList(arrayFilter);
                //Bin color for category clicked
                fillColorForCategoryClicked(p, CardView);
                txtMenu.setText(FileUtils.categoryList.get(p).getName());
            }
        });
    }

    public void fillColorForCategoryClicked(int p, View CardView) {
        if (selectedRow == -1 || p == selectedRow) {
            selectedRow = p;
            TextView cardViewBg = CardView.findViewById(R.id.txt_name_search_item_type);
            previousCartViewItemClicked = cardViewBg;
            cardViewBg.setTextColor(getResources().getColor(R.color.green, fragmentLayout.getContext().getTheme()));
        } else {
            selectedRow = p;
            previousCartViewItemClicked.setTextColor(getResources().getColor(R.color.black, fragmentLayout.getContext().getTheme()));
            TextView cardViewBg = CardView.findViewById(R.id.txt_name_search_item_type);
            cardViewBg.setTextColor(getResources().getColor(R.color.green, fragmentLayout.getContext().getTheme()));
            previousCartViewItemClicked = cardViewBg;
        }
    }
}