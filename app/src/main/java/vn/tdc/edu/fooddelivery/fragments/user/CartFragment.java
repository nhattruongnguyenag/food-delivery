package vn.tdc.edu.fooddelivery.fragments.user;

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
import vn.tdc.edu.fooddelivery.adapters.CartRecycleViewAdapter;
import vn.tdc.edu.fooddelivery.utils.FormatCurentcy;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;
import vn.tdc.edu.fooddelivery.models.ProductModel_Test;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class CartFragment extends AbstractFragment {
    public static CartRecycleViewAdapter myRecycleViewAdapter;
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
        //-----------------------Processing-----------------//
        CreateData();
        AnhXa();
        ClickEvent();
        CalculateAndAssign(FileUtils.cartList);
        clickBtnBuyEvent();
        //Catch event click into Add btn

        //-------------------------End---------------------//
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



    //-----------------------Calculate total and assign to TextViewTotal------------------------//
    public void CalculateAndAssign(ArrayList<ProductModel_Test> cartArrayList) {
        int sum = 0;
        for (int i = 0; i < cartArrayList.size(); i++) {
            sum += cartArrayList.get(i).getPrice() * cartArrayList.get(i).getQty();
        }
        String value = FormatCurentcy.format(sum + "");
        if (total_cart_screen == null) {
            total_cart_screen = activityCart.findViewById(R.id.total_cart_screen);
        }


        if (sum == 0) {
            btnBuy.setVisibility(View.INVISIBLE);
            total_cart_screen.setText("0 đồng ");
            //--------------------Create animation gif!----------------------------//
            createAnimation();
        } else {
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
        myRecycleViewAdapter.set_onRecyclerViewOnClickListener(new CartRecycleViewAdapter.onRecyclerViewOnClickListener() {
            @Override
            public void onItemRecyclerViewOnClickListener(int p, View CardView) {
                //MARK actions
                Toast.makeText(fragmentLayout.getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //--------------------------------Create fake data-----------------------//
    public void CreateData() {
        if (FileUtils.cartList == null) {
            FileUtils.cartList = new ArrayList<>();
        }
    }

    //----------------------------------------Anh xa----------------------------//
    public void AnhXa() {
        btnBuy = fragmentLayout.findViewById(R.id.btn_order_btn_cart_screen);
        linearLayoutWrapper = fragmentLayout.findViewById(R.id.linearLayout_wrapper_cart_Screen);
        total_cart_screen = fragmentLayout.findViewById(R.id.total_cart_screen);
        order_btn_cart_screen = fragmentLayout.findViewById(R.id.btn_order_btn_cart_screen);
        recyclerView = fragmentLayout.findViewById(R.id.recyclerView_card_screen);
        //Setup
        setUpCart();
    }

    public void setUpCart() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentLayout.getContext());
        layoutManager.setOrientation(layoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        myRecycleViewAdapter = new CartRecycleViewAdapter((Activity) fragmentLayout.getContext(), R.layout.cart_layout_item, FileUtils.cartList);
        recyclerView.setAdapter(myRecycleViewAdapter);
        myRecycleViewAdapter.notifyDataSetChanged();
    }


}