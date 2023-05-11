package vn.tdc.edu.fooddelivery.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.MainActivity;
import vn.tdc.edu.fooddelivery.adapters.MyDetailRecyclerViewAdapter_Hanh;
import vn.tdc.edu.fooddelivery.models.CartModel_Hanh;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class ProductDetailFragment extends AbstractFragment {
    private RecyclerView recyclerView;
    private MyDetailRecyclerViewAdapter_Hanh myRecycleViewAdapter;
    private ArrayList<CartModel_Hanh> arrayList = new ArrayList<>();
    private CartModel_Hanh DetailProduct;
    private Button buttonHeart;
    private Button buttonCart;
    private View fragmentLayout = null;
    private RatingBar ratingBar;
    private ImageButton ratingImgBtn;
    //------------------Start Main product area------------------
    private TextView txt_NameProductMain;
    private TextView txt_PriceProductMain;
    private TextView txt_DescriptionProductMain;
    private ImageView img_MainProduct;
    private TextView txt_start;
    private LinearLayout startLayoutWrapper;
    //------------------End Main product area------------------
    private CartFragment cartFragment = new CartFragment();
    //-----------------Change number notify in bottomBar----
    private MainActivity mainActivity;

    public ArrayList<CartModel_Hanh> getArrayList() {
        return arrayList;
    }

    public ProductDetailFragment setArrayList(ArrayList<CartModel_Hanh> arrayList) {
        this.arrayList = arrayList;
        return this;
    }

    public CartModel_Hanh getDetailProduct() {
        return DetailProduct;
    }

    public ProductDetailFragment setDetailProduct(CartModel_Hanh detailProduct) {
        DetailProduct = detailProduct;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.fragment_product, container, false);
        //----------Start-------------------------
        anhXa();
        RatingEvent();
        createDataForMainProduct();
        ClickEvent();
        //        Catch event click button back
        buttonHeart();
        //        Catch event click button back
        buttonCart();
        //----------End---------------------------
        return fragmentLayout;
    }


    public void buttonCart() {
        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonBuyEventClick(DetailProduct);
                Toast.makeText(fragmentLayout.getContext(), "cart", Toast.LENGTH_SHORT).show();
                CreateNumberBuyButtonEventClick();
            }
        });
    }

    public void CreateNumberBuyButtonEventClick() {
        if (mainActivity == null) {
            mainActivity = new MainActivity();
        }
        if (FileUtils.cartList != null) {
            if (FileUtils.cartList.size() != 0) {
                mainActivity.createNum(FileUtils.cartList.size(), 2);
            }
        }
    }

    public void buttonBuyEventClick(CartModel_Hanh cart) {
        int index = -1;
        if (FileUtils.cartList == null) {
            FileUtils.cartList = new ArrayList<>();
        } else {
            for (int i = 0; i < FileUtils.cartList.size(); i++) {
                if (FileUtils.cartList.get(i).getName().equals(cart.getName())) {
                    index = i;
                }
            }
        }
        if (index != -1) {
            CartModel_Hanh cartOld = FileUtils.cartList.get(index);
            FileUtils.cartList.get(index).setQty(cartOld.getQty() + 1);
        } else {
            cart.setQty(1);
            FileUtils.cartList.add(cart);
        }
        Toast.makeText(fragmentLayout.getContext(), "Đã thêm sản phẩm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
    }




    public void buttonHeart() {
        buttonHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(fragmentLayout.getContext(), "heart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void RatingEvent() {
        ratingImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRating();
            }
        });
    }

    public void dialogRating() {
        Dialog dialog = new Dialog(fragmentLayout.getContext());
        dialog.setContentView(R.layout.rating_layout);
        dialog.show();

        //Anh xa
        Button button = (Button) dialog.findViewById(R.id.btn_Ratting_layout_item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar_item_layout);
                Toast.makeText(fragmentLayout.getContext(), "" + ratingBar.getRating(), Toast.LENGTH_SHORT).show();
                dialog.hide();
            }
        });
    }


    public void anhXa() {
        //--------------Main object-------------------------
        img_MainProduct = fragmentLayout.findViewById(R.id.img_detail_screen);
        txt_NameProductMain = fragmentLayout.findViewById(R.id.txt_name_detail_screen);
        txt_PriceProductMain = fragmentLayout.findViewById(R.id.txt_price_detail_screen);
        txt_DescriptionProductMain = fragmentLayout.findViewById(R.id.txt_description_detail_screen);
        txt_start = fragmentLayout.findViewById(R.id.txt_start_detail_screen);
        startLayoutWrapper = fragmentLayout.findViewById(R.id.linearLayout_start_layout_wrapper_detail_screen);
        //---------------Rating---------------------------
        ratingImgBtn = fragmentLayout.findViewById(R.id.imgBtn_rating_detail_screen);
        //--------------End------------------------------
        buttonCart = fragmentLayout.findViewById(R.id.btn_cart_detail_screen);
        buttonHeart = fragmentLayout.findViewById(R.id.btn_heart_detail_screen);
        recyclerView = fragmentLayout.findViewById(R.id.recyclerView_detail_screen);
        //Setup
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentLayout.getContext());
        layoutManager.setOrientation(layoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        myRecycleViewAdapter = new MyDetailRecyclerViewAdapter_Hanh((Activity) fragmentLayout.getContext(), R.layout.detail_layout_item, arrayList);
        recyclerView.setAdapter(myRecycleViewAdapter);
    }

    public void createDataForMainProduct() {
        img_MainProduct.setBackgroundResource(DetailProduct.getImg());
        if (DetailProduct.getDescription().trim().isEmpty() == true) {
            txt_DescriptionProductMain.setText("Xin lỗi, sản phẩm này chưa được cập nhât chi tiết chúng" +
                    " tôi sẽ cập nhật trong thời gian sớm nhất có thể!");
        } else {
            txt_DescriptionProductMain.setText(DetailProduct.getDescription());
        }
        txt_NameProductMain.setText(DetailProduct.getName());
        txt_PriceProductMain.setText((cartFragment.formatCurrentcy(String.valueOf(DetailProduct.getPrice()))) + " đồng");
        txt_start.setText(String.valueOf(DetailProduct.getRate()));
        //----------------------Star Start printf-----------------
        for (int i = 0; i < DetailProduct.getRate(); i++) {
            ImageView imageView = new ImageView(fragmentLayout.getContext());
            imageView.setBackgroundResource(R.drawable.ic_baseline_star_24);
            startLayoutWrapper.addView(imageView);
        }
        //----------------------End Start printf------------------
    }

    //Catch event click
    public void ClickEvent() {
        myRecycleViewAdapter.set_OnRecyclerViewOnClickListener(new MyDetailRecyclerViewAdapter_Hanh.onRecyclerViewOnClickListener() {
            @Override
            public void onItemRecyclerViewOnClickListener(int p, View CardView) {
                Toast.makeText(fragmentLayout.getContext(), "" + p, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
