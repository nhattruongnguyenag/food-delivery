package vn.tdc.edu.fooddelivery.fragments;

import android.app.Activity;
import android.os.Bundle;
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

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.adapters.MyCartRecycleViewAdapter_Hanh;
import vn.tdc.edu.fooddelivery.models.CartModel_Hanh;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class CartFragment extends AbstractFragment {
    public static MyCartRecycleViewAdapter_Hanh myRecycleViewAdapter;
    private RecyclerView recyclerView;
    private TextView total_cart_screen;
    private Button order_btn_cart_screen;
    private static View fragmentLayout = null;
    private Activity activityCart;
    private static LinearLayout linearLayoutWrapper;
    private static Button btnBuy;

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
        //----------------------------------------------
        CreateData();
        AnhXa();
        ClickEvent();
        CalculateAndAssign(FileUtils.cartList);
        clickBtnBuyEvent();
        //Catch event click into Add btn

        //----------------------------------------------
        return fragmentLayout;
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

    //Calculate total and assign to TextViewTotal
    public void CalculateAndAssign(ArrayList<CartModel_Hanh> cartArrayList) {
        int sum = 0;
        for (int i = 0; i < cartArrayList.size(); i++) {
            sum += cartArrayList.get(i).getPrice() * cartArrayList.get(i).getQty();
        }
        String value = formatCurrentcy(sum + "");
        if (total_cart_screen == null) {
            total_cart_screen = activityCart.findViewById(R.id.total_cart_screen);
        }


        if (sum == 0) {
            btnBuy.setVisibility(View.INVISIBLE);
            //-------------------------------------
            //Create animation gif!
            total_cart_screen.setText("0 đồng ");
            createAnimation();
        } else {
            //-----------------------------------
            linearLayoutWrapper.removeAllViews();
            //-----------------------------------
            btnBuy.setVisibility(View.VISIBLE);
            total_cart_screen.setText(value + " đồng ");
        }
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

    //Catch event click
    public void ClickEvent() {
        myRecycleViewAdapter.set_onRecyclerViewOnClickListener(new MyCartRecycleViewAdapter_Hanh.onRecyclerViewOnClickListener() {
            @Override
            public void onItemRecyclerViewOnClickListener(int p, View CardView) {
                Toast.makeText(fragmentLayout.getContext(), "" + p, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Create fake data
    public void CreateData() {
        if (FileUtils.cartList == null) {
            FileUtils.cartList = new ArrayList<>();
        }
    }

    //Anh xa
    public void AnhXa() {
        btnBuy = fragmentLayout.findViewById(R.id.btn_order_btn_cart_screen);
        linearLayoutWrapper = fragmentLayout.findViewById(R.id.linearLayout_wrapper_cart_Screen);
        total_cart_screen = fragmentLayout.findViewById(R.id.total_cart_screen);
        order_btn_cart_screen = fragmentLayout.findViewById(R.id.btn_order_btn_cart_screen);
        recyclerView = fragmentLayout.findViewById(R.id.recyclerView_card_screen);
        //Setup
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentLayout.getContext());
        layoutManager.setOrientation(layoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        myRecycleViewAdapter = new MyCartRecycleViewAdapter_Hanh((Activity) fragmentLayout.getContext(), R.layout.cart_layout_item, FileUtils.cartList);
        recyclerView.setAdapter(myRecycleViewAdapter);
        myRecycleViewAdapter.notifyDataSetChanged();
    }

    public String formatCurrentcy(String value) {
        int count = (value.length()) / 3;
        double flag = ((value.length()) / (3 * 1.0));
        String total = "";
        String temp = "";
        if (flag > 1) {
            for (int i = 1; i <= count; i++) {
                int node = (value.length()) - (i * 3);
                temp = value.substring(node, node + 3);
                if ((i == count) && (value.length() % 3 == 0)) {
                    total = temp + total;
                } else {
                    total = "," + temp + total;
                    if ((i == count) && (value.length() % 3 != 0)) {
                        temp = value.substring(0, value.length() - count * 3);
                        total = temp + total;
                    }
                }

            }
        } else {
            total = value;
        }
        return total;
    }

}