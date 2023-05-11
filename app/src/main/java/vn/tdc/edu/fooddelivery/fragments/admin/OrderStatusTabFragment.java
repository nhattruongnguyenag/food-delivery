package vn.tdc.edu.fooddelivery.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.adapters.OrderFragmentStateAdapter;
import vn.tdc.edu.fooddelivery.adapters.UserFragmentStateAdapter;
import vn.tdc.edu.fooddelivery.models.RoleModel;

public class OrderStatusTabFragment extends Fragment {
    private TabLayout tabLayout;
    private CharSequence[] listStatus;
    private int LIST_STATUS_LENGTH = 4;
    private ViewPager2 viewPager2;
    private OrderFragmentStateAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_status_tab, container, false);

        if (listStatus == null) {
            listStatus = new CharSequence[LIST_STATUS_LENGTH];
            listStatus[0] = "Đơn hàng mới";
            listStatus[1] = "Đang giao hàng";
            listStatus[2] = "Thành công";
            listStatus[3] = "Thất bại";
        }

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.page);

        adapter = new OrderFragmentStateAdapter(OrderStatusTabFragment.this);
        adapter.setListStatus(listStatus);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                adapter.setListStatus(listStatus);
                tab.setText(listStatus[position]);
            }
        }).attach();

        return view;
    }
}