package vn.tdc.edu.fooddelivery.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import vn.tdc.edu.fooddelivery.R;
public class UsersListFragment extends Fragment {
    private RecyclerView recyclerViewUser;
    private TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Khách hàng"));
        tabLayout.addTab(tabLayout.newTab().setText("Nhân viên giao hàng"));
        tabLayout.addTab(tabLayout.newTab().setText("Quản trị viên"));

        recyclerViewUser = view.findViewById(R.id.recyclerViewUser);


        return  view;
    }
}