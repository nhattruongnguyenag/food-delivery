package vn.tdc.edu.fooddelivery.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.google.android.material.tabs.TabLayout;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.adapters.OrderManagementItemRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.models.OrderModel;

public class OrdersListFragment extends Fragment implements OrderManagementItemRecyclerViewAdapter.OnRecylerViewItemClickListener {
    private OrderManagementItemRecyclerViewAdapter adapter;
    private List<OrderModel> listOrders;
    private Spinner spOrderStatus;
    private RecyclerView recyclerViewOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);

//        spOrderStatus = view.findViewById(R.id.spOrderStatus);
        TabLayout tabLayout = view.findViewById(R.id.tabOrderStatus);
        tabLayout.addTab(tabLayout.newTab().setText("Chưa xử lý"));
        tabLayout.addTab(tabLayout.newTab().setText("Đang giao hàng"));
        tabLayout.addTab(tabLayout.newTab().setText("Thành công"));
        tabLayout.addTab(tabLayout.newTab().setText("Thất bại"));
        recyclerViewOrder = view.findViewById(R.id.recyclerViewOrder);

        listOrders = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            OrderModel orderModel = new OrderModel();
            orderModel.setId(1);
            orderModel.setCustomerFullName("Nguyễn Văn A");
            orderModel.setPhone("0706600000");
            orderModel.setAddress("53, Võ Văn Ngân, Phường Linh Chiểu, Tp. Thủ Đức, Tp. HCM");
            orderModel.setTotal(500000L);
            orderModel.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            listOrders.add(orderModel);
        }

        adapter = new OrderManagementItemRecyclerViewAdapter(getActivity(), R.layout.recycler_order_management,listOrders);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewOrder.setLayoutManager(layoutManager);
        recyclerViewOrder.setAdapter(adapter);

        adapter.setOnRecylerViewItemClickListener(this);

        return view;
    }

    @Override
    public void onButtonOrderDetailClickListener(int position) {
        Log.d("recyclerTest", "Order detais clicked at " + position);
    }

    @Override
    public void onButtonPhoneClickListener(int position) {
        Log.d("recyclerTest", "Phone clicked at " + position);
    }

    @Override
    public void onButtonAcceptClickListener(int position) {
        Log.d("recyclerTest", "Accept clicked at " + position);
    }

    @Override
    public void onButtonDeleteClickListener(int position) {
        Log.d("recyclerTest", "Delete clicked at " + position);
    }
}