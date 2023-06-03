package vn.tdc.edu.fooddelivery.fragments.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.adapters.OrderManagementItemRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.api.OrderAPI;
import vn.tdc.edu.fooddelivery.api.UserAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.components.ConfirmDialog;
import vn.tdc.edu.fooddelivery.enums.OrderStatus;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;
import vn.tdc.edu.fooddelivery.models.OrderModel;
import vn.tdc.edu.fooddelivery.models.UserModel;

public class OrdersListFragment extends AbstractFragment implements OrderManagementItemRecyclerViewAdapter.OnRecylerViewItemClickListener {
    private OrderManagementItemRecyclerViewAdapter adapter;
    private List<OrderModel> listOrders;
    private Integer status;

    public List<OrderModel> getListOrders() {
        return listOrders;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private RecyclerView recyclerViewOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);

        recyclerViewOrder = view.findViewById(R.id.recyclerViewOrder);

        getOrderListFromAPI(status);

        return view;
    }

    private void deleteUser(OrderModel orderModel) {
        Call<OrderModel> call = RetrofitBuilder.getClient().create(OrderAPI.class).delete(orderModel);
        call.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    ((AbstractActivity) getActivity()).showMessageDialog("Xoá đơn hàng thành công");
                    listOrders.remove(orderModel);
                    adapter.notifyDataSetChanged();
                } else {
                    ((AbstractActivity) getActivity()).showMessageDialog("Xoá đơn hàng thất bại");
                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                ((AbstractActivity) getActivity()).showMessageDialog("Xoá đơn hàng thất bại");
            }
        });
    }

    private void getOrderListFromAPI(Integer status) {
        Call<List<OrderModel>> call = RetrofitBuilder.getClient().create(OrderAPI.class).getAll(status);
        call.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    if (listOrders == null) {
                        listOrders = new ArrayList<>();
                    }

                    listOrders.clear();
                    listOrders.addAll(response.body());

                    adapter = new OrderManagementItemRecyclerViewAdapter(getActivity(), R.layout.recycler_order_management, listOrders);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerViewOrder.setLayoutManager(layoutManager);
                    recyclerViewOrder.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                    adapter.setOnRecylerViewItemClickListener(OrdersListFragment.this);
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {

            }
        });
    }

//    private void

    @Override
    public void onButtonOrderDetailClickListener(int position) {
        Log.d("recyclerTest", "Order detais clicked at " + position);
    }

    @Override
    public void onButtonPhoneClickListener(int position) {
        String phone = "tel:" + listOrders.get(position).getPhone();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));
        getActivity().startActivity(intent);
    }

    @Override
    public void onButtonAcceptClickListener(int position) {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(listOrders.get(position).getId());
        orderModel.setStatus(OrderStatus.DANG_GIAO_HANG.getStatus());

    }

    @Override
    public void onButtonDeleteClickListener(int position) {
        ConfirmDialog confirmDialog = new ConfirmDialog(getActivity());
        confirmDialog.setTitle("Xác nhận");
        confirmDialog.setMessage("Dữ liệu đã xoá không thể hoàn tác.\nBạn có muốn tiếp tục không?");
        confirmDialog.setOnDialogComfirmAction(new ConfirmDialog.DialogComfirmAction() {
            @Override
            public void cancel() {
                confirmDialog.dismiss();
            }

            @Override
            public void ok() {
                deleteUser(listOrders.get(position));
                confirmDialog.dismiss();
            }
        });

        confirmDialog.show();
    }
}