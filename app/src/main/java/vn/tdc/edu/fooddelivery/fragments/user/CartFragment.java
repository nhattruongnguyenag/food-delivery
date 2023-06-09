package vn.tdc.edu.fooddelivery.fragments.user;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.adapters.CartRecycleViewAdapter;
import vn.tdc.edu.fooddelivery.api.CartsAPI;
import vn.tdc.edu.fooddelivery.api.CategoryAPI;
import vn.tdc.edu.fooddelivery.api.NotificationAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.models.AddCarstModel;
import vn.tdc.edu.fooddelivery.models.CarstModel;
import vn.tdc.edu.fooddelivery.models.CategoryModel;
import vn.tdc.edu.fooddelivery.models.NotificationModel;
import vn.tdc.edu.fooddelivery.utils.FormatCurentcy;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;

public class CartFragment extends AbstractFragment {

    public static CartRecycleViewAdapter myRecycleViewAdapter;
    private RecyclerView recyclerView;
    private TextView total_cart_screen;
    private Button order_btn_cart_screen;
    private static View fragmentLayout = null;
    private Activity activityCart;
    private static LinearLayout linearLayoutWrapper;
    private static Button btnBuy;

    private static List<CarstModel> listOrders;
    private final int userID = 1;

    public Activity getActivityCart() {
        return activityCart;
    }


    public void setActivityCart(Activity activityCart) {
        this.activityCart = activityCart;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.fragment_cart, container, false);
        //-----------------------Processing-----------------//
        if (listOrders == null) {
            listOrders = new ArrayList<>();
        }
        AnhXa();
        ClickEvent();
        clickBtnBuyEvent();
        //Catch event click into Add btn
        //-------------------------End---------------------//
        return fragmentLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderListFromAPI();
    }

    private void getOrderListFromAPI() {
        Call<List<CarstModel>> call = RetrofitBuilder.getClient().create(CartsAPI.class).findCartsOfUser(userID);
        call.enqueue(new Callback<List<CarstModel>>() {
            @Override
            public void onResponse(Call<List<CarstModel>> call, Response<List<CarstModel>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    listOrders.clear();
                    listOrders.addAll(response.body());
                    setUpCart(listOrders);
                    CalculateAndAssign(listOrders);
                    Log.d("api-call", "Fetch product data successfully");
                } else {
                    Log.d("api-call", "Fetch product data fail");
                }
            }

            @Override
            public void onFailure(Call<List<CarstModel>> call, Throwable t) {
                CalculateAndAssign(listOrders);
                Log.d("api-call", "Fetch product data fail");
            }
        });
    }


    public void updateCart(AddCarstModel carstModel) {
        AnhXa();
        Call<List<CarstModel>> call = RetrofitBuilder.getClient().create(CartsAPI.class).updateAndCreate(carstModel);
        call.enqueue(new Callback<List<CarstModel>>() {
            @Override
            public void onResponse(Call<List<CarstModel>> call, Response<List<CarstModel>> response) {
                getOrderListFromAPI();
            }

            @Override
            public void onFailure(Call<List<CarstModel>> call, Throwable t) {
                getOrderListFromAPI();
            }
        });
    }


    public void deleteCarst(AddCarstModel carstModel) {

        Call<CarstModel> call = RetrofitBuilder.getClient().create(CartsAPI.class).delete(carstModel.getUser_id());
        call.enqueue(new Callback<CarstModel>() {
            @Override
            public void onResponse(Call<CarstModel> call, Response<CarstModel> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.d("TAG", "onResponse: xoa thanh cong");
                }
            }

            @Override
            public void onFailure(Call<CarstModel> call, Throwable t) {
                Log.d("TAG", "onResponse: xoa khong thanh cong");
            }
        });
    }


    public void clickBtnBuyEvent() {
        if (btnBuy != null) {
            order_btn_cart_screen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AbstractActivity) fragmentLayout.getContext()).setFragment(PaymentFragment.class, R.id.frameLayout, true);
                }
            });
        }
    }


    //-----------------------Calculate total and assign to TextViewTotal------------------------//
    public void CalculateAndAssign(List<CarstModel> orderItemModels) {
        int sum = 0;
        for (int i = 0; i < orderItemModels.size(); i++) {
            sum += orderItemModels.get(i).getProduct().getPrice() * orderItemModels.get(i).getQuantity();
        }
        String value = FormatCurentcy.format(sum + "");
        if (total_cart_screen == null) {
            total_cart_screen = activityCart.findViewById(R.id.total_cart_screen);
        }

        if (sum == 0) {
            Log.d("TAG", "CalculateAndAssign: rong");
            btnBuy.setVisibility(View.INVISIBLE);
            total_cart_screen.setText("0 đồng ");
            //--------------------Create animation gif!----------------------------//
            createAnimation();
        } else {
            Log.d("TAG", "CalculateAndAssign: khong rong");
            //-----------------------------------
            linearLayoutWrapper.removeAllViews();
            //-----------------------------------
            btnBuy.setVisibility(View.VISIBLE);
            total_cart_screen.setText(value + " đồng ");
        }
    }

    //------------------------------Create animation---------------------//
    public void createAnimation() {
        LottieAnimationView anim = new LottieAnimationView(fragmentLayout.getContext());
        anim.setAnimation(R.raw.nothing_gif2);
        TranslateAnimation ta = new TranslateAnimation(0, 0, Animation.RELATIVE_TO_SELF, 100);
        ta.setDuration(1000);
        ta.setFillAfter(true);
        anim.startAnimation(ta);
        anim.playAnimation();
        anim.loop(true);
        linearLayoutWrapper.addView(anim);
    }

    //--------------------------------Catch event click-------------------------------//
    public void ClickEvent() {
//        myRecycleViewAdapter.set_onRecyclerViewOnClickListener(new CartRecycleViewAdapter.onRecyclerViewOnClickListener() {
//            @Override
//            public void onItemRecyclerViewOnClickListener(int p, View CardView) {
//                //MARK actions
//                Toast.makeText(fragmentLayout.getContext(), "click", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    //--------------------------------Create fake data-----------------------//


    //----------------------------------------Anh xa----------------------------//
    public void AnhXa() {
        btnBuy = fragmentLayout.findViewById(R.id.btn_order_btn_cart_screen);
        linearLayoutWrapper = fragmentLayout.findViewById(R.id.linearLayout_wrapper_cart_Screen);
        total_cart_screen = fragmentLayout.findViewById(R.id.total_cart_screen);
        order_btn_cart_screen = fragmentLayout.findViewById(R.id.btn_order_btn_cart_screen);
        recyclerView = fragmentLayout.findViewById(R.id.recyclerView_card_screen);
    }

    public void setUpCart(List<CarstModel> orderModel) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentLayout.getContext());
        layoutManager.setOrientation(layoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        myRecycleViewAdapter = new CartRecycleViewAdapter((Activity) fragmentLayout.getContext(), R.layout.cart_layout_item, orderModel);
        recyclerView.setAdapter(myRecycleViewAdapter);
        myRecycleViewAdapter.notifyDataSetChanged();
    }

    public void showMessageDialog(String message) {
        androidx.appcompat.app.AlertDialog alert = new androidx.appcompat.app.AlertDialog.Builder(fragmentLayout.getContext())
                .setTitle("Message")
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show();
    }
}