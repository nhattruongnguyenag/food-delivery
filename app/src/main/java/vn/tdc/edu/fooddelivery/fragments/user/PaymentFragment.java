package vn.tdc.edu.fooddelivery.fragments.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.api.CartsAPI;
import vn.tdc.edu.fooddelivery.api.OrderAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.models.CartsModel;
import vn.tdc.edu.fooddelivery.models.OrderModel;
import vn.tdc.edu.fooddelivery.models.UserModel;
import vn.tdc.edu.fooddelivery.utils.Authentication;
import vn.tdc.edu.fooddelivery.utils.FormatCurentcy;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;


public class PaymentFragment extends AbstractFragment {
    private EditText address;
    private EditText phone;
    private Button btnBuy;
    private TextView txtPrice;
    private View fragmentLayout = null;

    private CartFragment carstFragment = new CartFragment();
    private List<CartsModel> listOrders;

    UserModel userModel = Authentication.getUserLogin();
    int userID = userModel.getId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.payment_layout, container, false);
        //--------------------------Processing-------------------
        if (listOrders == null) {
            {
                listOrders = new ArrayList<>();
            }
        }
        anhXa();
        clearDataInputFile();
        buyBtnClick();
        return fragmentLayout;
    }

    private void clearDataInputFile() {
        address.setText("");
        phone.setText("");
        address.setError(null);
        phone.setError(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderListFromAPI();
    }

    private void getOrderListFromAPI() {
        Call<List<CartsModel>> call = RetrofitBuilder.getClient().create(CartsAPI.class).findCartsOfUser(userID);
        call.enqueue(new Callback<List<CartsModel>>() {
            @Override
            public void onResponse(Call<List<CartsModel>> call, Response<List<CartsModel>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    listOrders.clear();
                    listOrders.addAll(response.body());
                    CalculateAndAssign(listOrders);
                    Log.d("api-call", "Fetch product data successfully");
                } else {
                    Log.d("api-call", "Fetch product data fail");
                }
            }

            @Override
            public void onFailure(Call<List<CartsModel>> call, Throwable t) {
                CalculateAndAssign(listOrders);
                Log.d("api-call", "Fetch product data fail");
            }
        });
    }


    private void buyBtnClick() {
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alidatePhoneAction() && alidateAddressAction()) {
                    OrderModel orderModel = new OrderModel();
                    orderModel.setAddress(String.valueOf(address.getText()));
                    orderModel.setUserId(userID);
                    orderModel.setPhone(phone.getText().toString().trim());
                    createOrder(orderModel);
                    showNotification(fragmentLayout.getContext(), "THÔNG BÁO TỪ HỆ THỐNG", "Ấn vào biểu tượng để xem thêm..", "Đơn hàng của bạn đã đặt thành công chúng tôi sẽ sơm giao" +
                            " đến sớm nhất, vui lòng để điện thoai ở trạng thái chờ chúng tôi sẽ gọi điện cho ban sớm");
                    ((AbstractActivity) getActivity()).showMessageDialog("Đặt hàng thành công");
                    setFragment(HomeFragment.class, R.id.frameLayout, false);
                } else {
                    Toast.makeText(fragmentLayout.getContext(), "loi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean alidatePhoneAction() {
        boolean ok = true;
        if (phone.getText().toString().trim() == "" || phone.getText().toString().trim().isEmpty()) {
            phone.setError("Hãy nhập số điện thoại của bạn");
            ok = false;
        } else {
            if (!isValidPhoneNumber(phone.getText().toString().trim())) {
                phone.setError("Số điện thoại của bạn không đúng định dạng");
                ok = false;
            }
        }
        return ok;
    }


    private boolean alidateAddressAction() {
        boolean ok = true;
        if (address.getText().toString().trim() == "" || address.getText().toString().trim().isEmpty()) {
            address.setError("Yêu cầu nhập địa chỉ");
            ok = false;
        }
        return ok;
    }


    public boolean isValidPhoneNumber(String phoneNumber) {
        // Biểu thức chính quy để kiểm tra số điện thoại Việt Nam
        String regex = "^(\\+?84|0)\\d{9,10}$";

        // Kiểm tra phoneNumber với biểu thức chính quy
        return phoneNumber.matches(regex);
    }


    //-----------------------End date picket-----------------------------//
    public void anhXa() {
        //Action
        address = fragmentLayout.findViewById(R.id.edt_address_receive_item_address_payment_screen);
        txtPrice = fragmentLayout.findViewById(R.id.txt_price_paymetn_screen);
        phone = fragmentLayout.findViewById(R.id.edt_date_receive_item_payment_screen);
        //layout
        btnBuy = fragmentLayout.findViewById(R.id.btn_buy_payment_screen);
    }


    public void CalculateAndAssign(List<CartsModel> orderItemModels) {
        int sum = 0;
        for (int i = 0; i < orderItemModels.size(); i++) {
            sum += orderItemModels.get(i).getProduct().getPrice() * orderItemModels.get(i).getQuantity();
        }
        String value = FormatCurentcy.format(sum + "");
        txtPrice.setText(value + " đồng ");
    }


    //-----------------------------Notifications----------------------------//
    @SuppressLint("MissingPermission")
    public static void showNotification(Context context, String title, String _short, String _length) {
        // Hiển thị thông báo
        vn.tdc.edu.fooddelivery.components.Notification.showNotificationUpgrade(context, title, _short, _length);
    }

    //---------------------------------Get location---------------------------------//


    private void createOrder(OrderModel orderModel) {

        Call<List<OrderModel>> call = RetrofitBuilder.getClient().create(OrderAPI.class).create(orderModel);
        call.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.d("TAG", "onResponse: tao order thanh cong");
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                Log.d("TAG", "onResponse: tao order khong thanh cong");
            }
        });
    }

}