package vn.tdc.edu.fooddelivery.fragments.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.adapters.OrderDetailRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;
import vn.tdc.edu.fooddelivery.fragments.admin.OrdersListFragment;
import vn.tdc.edu.fooddelivery.models.OrderModel;
import vn.tdc.edu.fooddelivery.utils.CommonUtils;
import vn.tdc.edu.fooddelivery.utils.FormatCurentcy;

public class OrderDetailsFragment extends AbstractFragment {
    private TextView tvCustomerName;
    private TextView tvAddress;
    private TextView tvCreateDate;
    private TextView tvTotal;
    private RecyclerView recyclerView;
    private OrderDetailRecyclerViewAdapter adapter;

    private OrderModel orderModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        tvCustomerName = view.findViewById(R.id.tvCustomerName);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvCreateDate = view.findViewById(R.id.tvCreateDate);
        tvTotal = view.findViewById(R.id.tvTotal);
        recyclerView = view.findViewById(R.id.recyclerView);

        Bundle bundleReceive = getArguments();

        if (bundleReceive != null) {
            orderModel = (OrderModel) bundleReceive.getSerializable(OrdersListFragment.ORDER_MODEL);
        }

        if (orderModel != null && orderModel.getItems() != null) {
            tvCustomerName.setText(orderModel.getCustomer().getFullName());
            tvCreateDate.setText(CommonUtils.convertDateToString(orderModel.getCreatedAt()));
            tvAddress.setText(CommonUtils.createIndentedText(orderModel.getAddress(),105,0));
            tvTotal.setText(FormatCurentcy.formatVietnamCurrency(orderModel.getTotal()));

            adapter = new OrderDetailRecyclerViewAdapter((AppCompatActivity) getActivity(), R.layout.recycler_order_management, orderModel.getItems());

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}