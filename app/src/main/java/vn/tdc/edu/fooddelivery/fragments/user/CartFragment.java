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
import vn.tdc.edu.fooddelivery.activities.user.MainActivity;
import vn.tdc.edu.fooddelivery.adapters.CartRecycleViewAdapter;
import vn.tdc.edu.fooddelivery.api.CartsAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.components.ConfirmDialog;
import vn.tdc.edu.fooddelivery.models.ItemCartsModel;
import vn.tdc.edu.fooddelivery.models.CartsModel;
import vn.tdc.edu.fooddelivery.models.UserModel;
import vn.tdc.edu.fooddelivery.utils.Authentication;
import vn.tdc.edu.fooddelivery.utils.FormatCurentcy;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;

public class CartFragment extends AbstractFragment {

    public MainActivity mainActivity = new MainActivity();
    public static CartRecycleViewAdapter myRecycleViewAdapter;
    private static RecyclerView recyclerView;
    private static TextView total_cart_screen;
    private Button order_btn_cart_screen;
    private static View fragmentLayout = null;
    private Activity activityCart;
    private static LinearLayout linearLayoutWrapper;
    private static Button btnBuy;

    private static List<CartsModel> listOrders;
    UserModel userModel = Authentication.getUserLogin();
    private final int userID = userModel.getId();

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
        btnBuy.setVisibility(View.INVISIBLE);
        setUpCart(listOrders);
        clickBtnBuyEvent();
        //-------------------------End---------------------//
        return fragmentLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderListFromAPI();
    }

    public void getOrderListFromAPI() {
        Call<List<CartsModel>> call = RetrofitBuilder.getClient().create(CartsAPI.class).findCartsOfUser(userID);
        call.enqueue(new Callback<List<CartsModel>>() {
            @Override
            public void onResponse(Call<List<CartsModel>> call, Response<List<CartsModel>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    if (listOrders == null) {
                        listOrders = new ArrayList<>();
                    }
                    try {
                        listOrders.clear();
                        listOrders.addAll(response.body());
                        asignDataToBin();
                    } catch (Exception exception) {
                    }
                    mainActivity.catchDataCartIconNotify(listOrders.size());
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


    public void asignDataToBin() {
        setUpCart(listOrders);
        CalculateAndAssign(listOrders);
    }


    public void updateCart(ItemCartsModel carstModel, CartsModel carst, Activity activity) {
        Call<List<CartsModel>> call = RetrofitBuilder.getClient().create(CartsAPI.class).updateAndCreate(carstModel);
        call.enqueue(new Callback<List<CartsModel>>() {
            @Override
            public void onResponse(Call<List<CartsModel>> call, Response<List<CartsModel>> response) {
                //have a bug
            }

            @Override
            public void onFailure(Call<List<CartsModel>> call, Throwable t) {
                if (carst != null) {
                    int index = listOrders.indexOf(carst);
                    listOrders.get(index).setQuantity(carstModel.getQuantity() + carst.getQuantity());
                    CalculateAndAssign(listOrders);
                    myRecycleViewAdapter.notifyDataSetChanged();
                } else {
                    getOrderListFromAPI();
                    showMessageDialog("Thêm sản phẩm vào giỏ hàng thành công", activity);
                }
            }
        });
    }

    public void showMessageDialog(String message, Activity activity) {
        androidx.appcompat.app.AlertDialog alert = new androidx.appcompat.app.AlertDialog.Builder(activity)
                .setTitle("Message")
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show();
    }


    public void deleteCarst(ItemCartsModel carstModel, CartsModel carstModelDelete) {
        ConfirmDialog confirmDialog = new ConfirmDialog(fragmentLayout.getContext());
        confirmDialog.setTitle("Xóa sản phẩm ");
        confirmDialog.setMessage("Xóa sản phẩm ra khỏi giỏ hàng của bạn ?");
        confirmDialog.setOnDialogComfirmAction(new ConfirmDialog.DialogComfirmAction() {
            @Override
            public void cancel() {
                confirmDialog.dismiss();
            }

            @Override
            public void ok() {
                deleteProduceInCartsActions(carstModel, carstModelDelete);
                confirmDialog.dismiss();
            }
        });
        confirmDialog.show();
    }

    public void deleteProduceInCartsActions(ItemCartsModel carstModel, CartsModel carstModelDelete) {
        Call<CartsModel> call = RetrofitBuilder.getClient().create(CartsAPI.class).delete(carstModel.getId());
        call.enqueue(new Callback<CartsModel>() {
            @Override
            public void onResponse(Call<CartsModel> call, Response<CartsModel> response) {
                //Have bug here
            }

            @Override
            public void onFailure(Call<CartsModel> call, Throwable t) {
                listOrders.remove(carstModelDelete);
                myRecycleViewAdapter.notifyDataSetChanged();
                CalculateAndAssign(listOrders);
                showMessageDialog("Đã xóa thành công sản phẩm ");
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


    public void CalculateAndAssign(List<CartsModel> orderItemModels) {
        int sum = 0;
        for (int i = 0; i < orderItemModels.size(); i++) {
            sum += orderItemModels.get(i).getProduct().getPrice() * orderItemModels.get(i).getQuantity();
        }
        String value = FormatCurentcy.format(sum + "");
        if (total_cart_screen == null) {
            total_cart_screen = activityCart.findViewById(R.id.total_cart_screen);
        }

        if (sum == 0) {
            btnBuy.setVisibility(View.INVISIBLE);
            total_cart_screen.setText("0 đồng ");
            createAnimation();
        } else {
            btnBuy.setVisibility(View.VISIBLE);
            linearLayoutWrapper.removeAllViews();
            btnBuy.setVisibility(View.VISIBLE);
            total_cart_screen.setText(value + " đồng ");
        }
        mainActivity.catchDataCartIconNotify(listOrders.size());
    }

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

    public void AnhXa() {
        btnBuy = fragmentLayout.findViewById(R.id.btn_order_btn_cart_screen);
        linearLayoutWrapper = fragmentLayout.findViewById(R.id.linearLayout_wrapper_cart_Screen);
        total_cart_screen = fragmentLayout.findViewById(R.id.total_cart_screen);
        order_btn_cart_screen = fragmentLayout.findViewById(R.id.btn_order_btn_cart_screen);
        recyclerView = fragmentLayout.findViewById(R.id.recyclerView_card_screen);
    }

    public void setUpCart(List<CartsModel> orderModel) {
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