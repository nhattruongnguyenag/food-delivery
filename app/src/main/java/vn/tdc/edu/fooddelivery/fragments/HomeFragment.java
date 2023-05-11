package vn.tdc.edu.fooddelivery.fragments;

import android.app.Activity;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.adapters.MyHomeMenuRecyclerViewAdapter_Hanh;
import vn.tdc.edu.fooddelivery.adapters.MySearchRecyclerViewAdapter_Hanh;
import vn.tdc.edu.fooddelivery.models.CartModel_Hanh;

public class HomeFragment extends AbstractFragment {

    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView_category;
    private RecyclerView recyclerView_menu;
    private MyHomeMenuRecyclerViewAdapter_Hanh myAdapterMenu;
    private MySearchRecyclerViewAdapter_Hanh myAdapterSearch;
    private ArrayList<CartModel_Hanh> arrayList = new ArrayList<>();
    private View fragmentLayout = null;
    private SearchView searchView;
    private TextView txtMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        fragmentLayout = inflater.inflate(R.layout.fragment_home, container, false);
        //---------------------Start oncreate home---------------------
        fakeData();
        anhXa();
        ActionViewFlipper();
        recyclerView_menu.setFocusable(false);
        recyclerView_menu.setNestedScrollingEnabled(false);
        ClickEventMenu();
        ClickEventCategory();
        //--------------------Star Chuyen man hinh boi fragment--------------------
        return fragmentLayout;
    }


    public void fakeData() {
        CartModel_Hanh cart1 = new CartModel_Hanh("Com heo1", 1, 2000, R.drawable.anh1, 5, "adawdaw");
        CartModel_Hanh cart2 = new CartModel_Hanh("Com heo2", 12, 2000, R.drawable.anh1, 2, "awdawdwa");
        CartModel_Hanh cart3 = new CartModel_Hanh("Com heo3", 2, 2000, R.drawable.anh1, 1, "d");
        arrayList.add(cart1);
        arrayList.add(cart2);
        arrayList.add(cart3);
    }

    public void anhXa() {
        txtMenu = fragmentLayout.findViewById(R.id.txt_menu_home_screen);
        recyclerView_menu = fragmentLayout.findViewById(R.id.recyclerView_menu_home_screen);
        recyclerView_category = fragmentLayout.findViewById(R.id.recyclerView_category_home_screen);
        viewFlipper = fragmentLayout.findViewById(R.id.viewFlipper_home_screen);
        //Setup category recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentLayout.getContext());
        layoutManager.setOrientation(layoutManager.HORIZONTAL);
        recyclerView_category.setLayoutManager(layoutManager);
        myAdapterSearch = new MySearchRecyclerViewAdapter_Hanh((Activity) fragmentLayout.getContext(), R.layout.home_layout_category_item, arrayList);
        recyclerView_category.setAdapter(myAdapterSearch);
        //Setup menu recyclerView
        grifViewList(arrayList);
    }

    public void grifViewList(ArrayList<CartModel_Hanh> arrayListx) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(fragmentLayout.getContext(), 2);
        recyclerView_menu.setLayoutManager(gridLayoutManager);
        myAdapterMenu = new MyHomeMenuRecyclerViewAdapter_Hanh((Activity) fragmentLayout.getContext(), R.layout.home_layout_menu_item, arrayListx);
        recyclerView_menu.setAdapter(myAdapterMenu);
    }

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

    //Catch event click
    public void ClickEventMenu() {
        myAdapterMenu.set_OnRecyclerViewOnClickListener(new MyHomeMenuRecyclerViewAdapter_Hanh.onRecyclerViewOnClickListener() {
            @Override
            public void onItemRecyclerViewOnClickListener(int p, View CardView) {
                Toast.makeText(fragmentLayout.getContext(), "MENU" + p, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Chuyen man hinh
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

    //Catch event click
    public void ClickEventCategory() {
        myAdapterSearch.setonRecyclerViewOnClickListener(new MySearchRecyclerViewAdapter_Hanh.onRecyclerViewOnClickListener() {
            @Override
            public void onItemRecyclerViewOnClickListener(int p, View CardView) {
                ArrayList<CartModel_Hanh> category1 = new ArrayList<>();
                ArrayList<CartModel_Hanh> category2 = new ArrayList<>();
                ArrayList<CartModel_Hanh> category3 = new ArrayList<>();
                CartModel_Hanh cart = new CartModel_Hanh("com heo 1-1", 12, 2000, R.drawable.anh1, 4, "dwadwadwa");
                CartModel_Hanh cart2 = new CartModel_Hanh("com heo 1-2", 12, 2000, R.drawable.anh1, 4, "dwadwadwa");
                CartModel_Hanh cart3 = new CartModel_Hanh("com heo 1-3", 12, 2000, R.drawable.anh1, 4, "dwadwadwa");
                category1.add(cart);
                category1.add(cart);
                category1.add(cart);
                category1.add(cart);
                category1.add(cart);
                category2.add(cart2);
                category2.add(cart2);
                category2.add(cart2);
                category2.add(cart2);
                category2.add(cart2);
                category3.add(cart3);
                category3.add(cart3);
                category3.add(cart3);
                category3.add(cart3);
                category3.add(cart3);

                if (arrayList.get(p).getName().equalsIgnoreCase("Com heo1")) {
                    grifViewList(category1);
                }
                if (arrayList.get(p).getName().equalsIgnoreCase("Com heo2")) {
                    grifViewList(category2);
                }
                if (arrayList.get(p).getName().equalsIgnoreCase("Com heo3")) {
                    grifViewList(category3);
                }
                txtMenu.setText(arrayList.get(p).getName());
            }
        });
    }

}