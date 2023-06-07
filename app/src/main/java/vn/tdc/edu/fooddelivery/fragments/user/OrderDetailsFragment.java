package vn.tdc.edu.fooddelivery.fragments.user;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.parser.ColorParser;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.adapters.OrderDetailRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.api.OrderAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.enums.OrderStatus;
import vn.tdc.edu.fooddelivery.enums.Role;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;
import vn.tdc.edu.fooddelivery.fragments.admin.OrdersListFragment;
import vn.tdc.edu.fooddelivery.models.OrderRequest;
import vn.tdc.edu.fooddelivery.models.OrderModel;
import vn.tdc.edu.fooddelivery.utils.Authentication;
import vn.tdc.edu.fooddelivery.utils.CommonUtils;
import vn.tdc.edu.fooddelivery.utils.FormatCurentcy;

public class OrderDetailsFragment extends AbstractFragment implements View.OnClickListener {
    private TextView tvCustomerName;
    private TextView tvAddress;
    private TextView tvCreateDate;
    private TextView tvTotal;
    private TextView tvShipperName;
    private TextView tvOrderStatus;
    private LinearLayout layoutBtnAction;
    private LinearLayout layoutShipperInfo;
    private RecyclerView recyclerView;
    private Button btnSuccess;
    private Button btnCancel;
    private OrderDetailRecyclerViewAdapter adapter;
    private OrderModel orderModel;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        tvCustomerName = view.findViewById(R.id.tvCustomerName);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvCreateDate = view.findViewById(R.id.tvCreateDate);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvShipperName = view.findViewById(R.id.tvShipperName);
        tvOrderStatus = view.findViewById(R.id.tvOrderStatus);
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutBtnAction = view.findViewById(R.id.layoutBtnAction);
        layoutShipperInfo = view.findViewById(R.id.layoutShipperInfo);
        btnSuccess = view.findViewById(R.id.btnSuccess);
        btnCancel = view.findViewById(R.id.btnCancel);

        btnSuccess.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        Bundle bundleReceive = getArguments();

        if (bundleReceive != null) {
            orderModel = (OrderModel) bundleReceive.getSerializable(OrdersListFragment.ORDER_MODEL);
        }

        if (orderModel != null && orderModel.getItems() != null) {
            tvCustomerName.setText(orderModel.getCustomer().getFullName());
            tvCreateDate.setText(CommonUtils.convertDateToString(orderModel.getCreatedAt()));
            tvAddress.setText(CommonUtils.createIndentedText(orderModel.getAddress(), 105, 0));
            tvTotal.setText(FormatCurentcy.formatVietnamCurrency(orderModel.getTotal()));

            if (orderModel.getStatus() != OrderStatus.DANG_GIAO_HANG.getStatus()) {
                layoutBtnAction.setVisibility(View.GONE);
            }

            if (orderModel.getStatus() == OrderStatus.CHUA_XU_LY.getStatus()) {
                layoutShipperInfo.setVisibility(View.GONE);
            } else {
                if (orderModel.getShipper() != null) {
                    tvShipperName.setText(orderModel.getShipper().getFullName());
                } else {
                    tvShipperName.setText("");
                }
            }

            if (orderModel.getStatus() == OrderStatus.CHUA_XU_LY.getStatus()) {
                tvOrderStatus.setText(OrderStatus.CHUA_XU_LY.getName());
                tvOrderStatus.setTextColor(Color.parseColor("#f1c602"));
            } else if (orderModel.getStatus() == OrderStatus.DANG_GIAO_HANG.getStatus()) {
                tvOrderStatus.setText(OrderStatus.DANG_GIAO_HANG.getName());
                tvOrderStatus.setTextColor(Color.parseColor("#FF00B0FF"));
            } else if (orderModel.getStatus() == OrderStatus.THANH_CONG.getStatus()) {
                tvOrderStatus.setText(OrderStatus.THANH_CONG.getName());
                tvOrderStatus.setTextColor(Color.parseColor("#008132"));
            } else if (orderModel.getStatus() == OrderStatus.THAT_BAI.getStatus()) {
                tvOrderStatus.setText(OrderStatus.THAT_BAI.getName());
                tvOrderStatus.setTextColor(Color.parseColor("#EB4A3E"));
            }

            adapter = new OrderDetailRecyclerViewAdapter((AppCompatActivity) getActivity(), R.layout.recycler_order_management, orderModel.getItems());

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSuccess || view.getId() == R.id.btnCancel) {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setId(orderModel.getId());
            if (view.getId() == R.id.btnSuccess) {
                orderRequest.setStatus(OrderStatus.THANH_CONG.getStatus());
            } else {
                orderRequest.setStatus(OrderStatus.THAT_BAI.getStatus());
            }

            updateOrderStatus(orderRequest);
        }
    }

    private void updateOrderStatus(OrderRequest orderRequest) {
        Call<OrderModel> call = RetrofitBuilder.getClient().create(OrderAPI.class).update(orderRequest);
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                if (response.code() == HttpURLConnection.HTTP_OK || response.code() == HttpURLConnection.HTTP_CREATED) {
                    if (response.body().getStatus() == OrderStatus.THANH_CONG.getStatus()) {
                        ((AbstractActivity) getActivity()).showMessageDialog("Đơn hàng đã giao thành công");
                    } else if (response.body().getStatus() == OrderStatus.THAT_BAI.getStatus()) {
                        ((AbstractActivity) getActivity()).showMessageDialog("Huỷ đơn hàng thành công");
                    }
                    getActivity().onBackPressed();
                } else {
                    ((AbstractActivity) getActivity()).showMessageDialog("Thao tác không thành công");
                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                ((AbstractActivity) getActivity()).showMessageDialog("Thao tác không thành công");
            }
        });
    }
}