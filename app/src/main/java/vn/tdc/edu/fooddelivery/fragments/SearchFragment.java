package vn.tdc.edu.fooddelivery.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.activities.MainActivity;
import vn.tdc.edu.fooddelivery.adapters.MySearchRecyclerViewAdapter2_Hanh;
import vn.tdc.edu.fooddelivery.models.CartModel_Hanh;

public class SearchFragment extends AbstractFragment implements MySearchRecyclerViewAdapter2_Hanh.UserClicListenter {
    private int typeView = 1;
    public static RecyclerView recyclerView;
    private MySearchRecyclerViewAdapter2_Hanh myRecycleViewAdapter;
    private ArrayList<CartModel_Hanh> arrayList = new ArrayList<>();
    private ImageButton btn_select_type_view_search_screen;
    private SearchView searchView;
    private boolean flag = false;
    View fragmentLayout = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.seartch_layout, container, false);
        //----------------------------------------------------
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
        //----------------------------------------------------
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
                    myRecycleViewAdapter = new MySearchRecyclerViewAdapter2_Hanh((Activity) fragmentLayout.getContext(), R.layout.search_layout_item3, myRecycleViewAdapter.cartArrayListOnChange, this::selectedUser);
                } else {
                    myRecycleViewAdapter = new MySearchRecyclerViewAdapter2_Hanh((Activity) fragmentLayout.getContext(), R.layout.search_layout_item3, arrayList, this::selectedUser);
                }
                recyclerView.setAdapter(myRecycleViewAdapter);
                break;
            case 2:
                //Setup
                GridLayoutManager layoutManager2 = new GridLayoutManager((Activity) fragmentLayout.getContext(), 2);
                recyclerView.setLayoutManager(layoutManager2);
                if (myRecycleViewAdapter.cartArrayListOnChange != null) {
                    myRecycleViewAdapter = new MySearchRecyclerViewAdapter2_Hanh((Activity) fragmentLayout.getContext(), R.layout.search_layout_item1, myRecycleViewAdapter.cartArrayListOnChange, this::selectedUser);
                } else {
                    myRecycleViewAdapter = new MySearchRecyclerViewAdapter2_Hanh((Activity) fragmentLayout.getContext(), R.layout.search_layout_item1, arrayList, this::selectedUser);
                }
                recyclerView.setAdapter(myRecycleViewAdapter);
                break;
        }
        flag = true;
    }


    public void anhXa() {
        MainActivity.searchImgBtn.setVisibility(View.INVISIBLE);
        MainActivity.searchView.setVisibility(View.VISIBLE);
        btn_select_type_view_search_screen = fragmentLayout.findViewById(R.id.btn_select_type_view_search_screen);
        recyclerView = fragmentLayout.findViewById(R.id.recyclerView_search_screen);
    }

    public void createFakeData() {
        CartModel_Hanh cart = new CartModel_Hanh("ca chien1", 12, 9999, R.drawable.anh_nhopng, 1, "");
        CartModel_Hanh cart2 = new CartModel_Hanh("ca chien2", 12, 9999, R.drawable.food4png, 1, "");
        CartModel_Hanh cart3 = new CartModel_Hanh("ca chien3", 12, 9999, R.drawable.img, 1, "");
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


    public void sendDataForDetailScreen(CartModel_Hanh cart, ArrayList<CartModel_Hanh> arrayList) {
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
        //-----------------Clear old data----------------------
        MainActivity.searchView.setQuery("", false);
        MainActivity.searchView.clearFocus();
        //------------------Bin data agin----------------------
//        binData();
        //----------------------------End----------------------
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
            myRecycleViewAdapter = new MySearchRecyclerViewAdapter2_Hanh((Activity) fragmentLayout.getContext(), R.layout.search_layout_item3, arrayList, this::selectedUser);
        } else {
            myRecycleViewAdapter = new MySearchRecyclerViewAdapter2_Hanh((Activity) fragmentLayout.getContext(), R.layout.search_layout_item1, arrayList, this::selectedUser);
        }
        recyclerView.setAdapter(myRecycleViewAdapter);
    }


    @Override
    public void selectedUser(CartModel_Hanh userModel) {
        Toast.makeText(fragmentLayout.getContext(), "" + userModel.getName(), Toast.LENGTH_SHORT).show();
    }
}
