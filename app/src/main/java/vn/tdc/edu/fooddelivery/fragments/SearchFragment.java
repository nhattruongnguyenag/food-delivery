package vn.tdc.edu.fooddelivery.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.activities.user.MainActivity;
import vn.tdc.edu.fooddelivery.adapters.SearchRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.fragments.user.ProductDetailFragment;
import vn.tdc.edu.fooddelivery.models.ProductModel_Test;

public class SearchFragment extends AbstractFragment implements SearchRecyclerViewAdapter.UserClicListenter {
    private int typeView = 1;
    public static RecyclerView recyclerView;
    private SearchRecyclerViewAdapter myRecycleViewAdapter;
    private ArrayList<ProductModel_Test> arrayList = new ArrayList<>();
    private ImageButton btn_select_type_view_search_screen;
    private SearchView searchView;
    private boolean flag = false;
    View fragmentLayout = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.seartch_layout, container, false);
        //---------------------------Processing-------------------------//
        anhXa();
        chooseTypeView();
        createFakeData();
        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);
        btn_select_type_view_search_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventClickTypeView();
            }
        });
        searchActivity();
        //--------------------------End--------------------------//
        return fragmentLayout;
    }


    public void EventClickTypeView() {
        if (typeView % 2 == 0) {
            typeView = 1;
            btn_select_type_view_search_screen.setBackgroundResource((R.drawable.ic_baseline_grid_on_24));
        } else {
            typeView = 2;
            btn_select_type_view_search_screen.setBackgroundResource((R.drawable.ic_baseline_view_list_24));
        }
        chooseTypeView();
    }

    public void chooseTypeView() {
        switch (typeView) {
            case 1:
                //Setup
                StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager1);
                if (myRecycleViewAdapter.cartArrayListOnChange != null) {
                    myRecycleViewAdapter = new SearchRecyclerViewAdapter((Activity) fragmentLayout.getContext(), R.layout.search_layout_item3, myRecycleViewAdapter.cartArrayListOnChange, this::selectedUser);
                } else {
                    myRecycleViewAdapter = new SearchRecyclerViewAdapter((Activity) fragmentLayout.getContext(), R.layout.search_layout_item3, arrayList, this::selectedUser);
                }
                recyclerView.setAdapter(myRecycleViewAdapter);
                break;
            case 2:
                //Setup
                GridLayoutManager layoutManager2 = new GridLayoutManager((Activity) fragmentLayout.getContext(), 2);
                recyclerView.setLayoutManager(layoutManager2);
                if (myRecycleViewAdapter.cartArrayListOnChange != null) {
                    myRecycleViewAdapter = new SearchRecyclerViewAdapter((Activity) fragmentLayout.getContext(), R.layout.search_layout_item1, myRecycleViewAdapter.cartArrayListOnChange, this::selectedUser);
                } else {
                    myRecycleViewAdapter = new SearchRecyclerViewAdapter((Activity) fragmentLayout.getContext(), R.layout.search_layout_item1, arrayList, this::selectedUser);
                }
                recyclerView.setAdapter(myRecycleViewAdapter);
                break;
        }
        flag = true;
    }


    public void anhXa() {
        btn_select_type_view_search_screen = fragmentLayout.findViewById(R.id.btn_select_type_view_search_screen);
        recyclerView = fragmentLayout.findViewById(R.id.recyclerView_search_screen);
    }

    public void createFakeData() {
        ProductModel_Test cart = new ProductModel_Test(10,1,"ca chien1", 12, 9999, R.drawable.anh_nhopng, 1, "");
        ProductModel_Test cart2 = new ProductModel_Test(11,2,"ca chien2", 12, 9999, R.drawable.food4png, 1, "");
        ProductModel_Test cart3 = new ProductModel_Test(12,3,"ca chien3", 12, 9999, R.drawable.anh1, 1, "");
        arrayList.add(cart2);
        arrayList.add(cart3);
        arrayList.add(cart);
        arrayList.add(cart);
        arrayList.add(cart);
        arrayList.add(cart);
        arrayList.add(cart);
        arrayList.add(cart);
        arrayList.add(cart);
        arrayList.add(cart);
        arrayList.add(cart);
        arrayList.add(cart);
        arrayList.add(cart);
    }


    public void sendDataForDetailScreen(ProductModel_Test cart, ArrayList<ProductModel_Test> arrayList) {
        ((AbstractActivity) fragmentLayout.getContext()).setFragment(ProductDetailFragment.class, R.id.frameLayout, true)
                .setDetailProduct(cart).
                setArrayList(null)
                .setArrayList(arrayList);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void searchActivity() {
        //-----------------Clear old data----------------------//
        MainActivity.searchView.setQuery("", false);
        MainActivity.searchView.clearFocus();
        //------------------Bin data again----------------------//
        binData();
        //----------------------------End----------------------//
        MainActivity.searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myRecycleViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (flag == true) {
                    binData();
                    flag = false;
                }
                myRecycleViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void binData() {
        if (typeView == 1) {
            myRecycleViewAdapter = new SearchRecyclerViewAdapter((Activity) fragmentLayout.getContext(), R.layout.search_layout_item3, arrayList, this::selectedUser);
        } else {
            myRecycleViewAdapter = new SearchRecyclerViewAdapter((Activity) fragmentLayout.getContext(), R.layout.search_layout_item1, arrayList, this::selectedUser);
        }
        recyclerView.setAdapter(myRecycleViewAdapter);
    }


    @Override
    public void selectedUser(ProductModel_Test userModel) {
        int index =  arrayList.indexOf(userModel);
        ProductModel_Test cart = arrayList.get(index);
        sendDataForDetailScreen(cart,arrayList);
    }
}