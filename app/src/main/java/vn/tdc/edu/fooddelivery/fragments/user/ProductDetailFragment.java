package vn.tdc.edu.fooddelivery.fragments.user;

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
import vn.tdc.edu.fooddelivery.activities.user.MainActivity;
import vn.tdc.edu.fooddelivery.adapters.DetailRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.components.CreateStart;
import vn.tdc.edu.fooddelivery.utils.FormartCurentcy;
import vn.tdc.edu.fooddelivery.components.ToaslCustomize;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;
import vn.tdc.edu.fooddelivery.models.ProductModel_Test;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class ProductDetailFragment extends AbstractFragment {
    private ToaslCustomize _customeToasl;
    private RecyclerView recyclerView;
    private DetailRecyclerViewAdapter myRecycleViewAdapter;
    private ArrayList<ProductModel_Test> arrayList = new ArrayList<>();
    private ProductModel_Test DetailProduct;
    private ImageButton buttonHeart;
    private ImageButton buttonCart;
    private View fragmentLayout = null;
    private RatingBar ratingBar;
    private ImageButton ratingImgBtn;
    //------------------Start Main product area------------------//
    private TextView txt_NameProductMain;
    private TextView txt_PriceProductMain;
    private TextView txt_DescriptionProductMain;
    private ImageView img_MainProduct;
    private TextView txt_start;
    private LinearLayout startLayoutWrapper;
    //------------------End Main product area------------------//
    private CartFragment cartFragment = new CartFragment();
    //-----------------Change number notify in bottomBar----//
    private MainActivity mainActivity;

    public ArrayList<ProductModel_Test> getArrayList() {
        return arrayList;
    }

    public ProductDetailFragment setArrayList(ArrayList<ProductModel_Test> arrayList) {
        this.arrayList = arrayList;
        return this;
    }

    public ProductModel_Test getDetailProduct() {
        return DetailProduct;
    }

    public ProductDetailFragment setDetailProduct(ProductModel_Test detailProduct) {
        DetailProduct = detailProduct;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.fragment_product, container, false);
        //--------------------------Start-------------------------//
        anhXa();
        RatingEvent();
        createDataForMainProduct();
        ClickEvent();
        //-------------Catch event click button back-----------------//
        buttonHeart();
        //-----------------Catch event click button back--------------//
        buttonCart();
        //-----------------------------End---------------------------//
        return fragmentLayout;
    }


    public void buttonCart() {
        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //--------------Toasl Custome-------//
                LayoutInflater layoutInflater = getLayoutInflater();
                _customeToasl = new ToaslCustomize();
                _customeToasl.customeToasl(fragmentLayout, layoutInflater);
                //--------------End--------------//
                buttonBuyEventClick(DetailProduct);
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

    public void buttonBuyEventClick(ProductModel_Test cart) {
        int index = -1;
        if (FileUtils.cartList == null) {
            FileUtils.cartList = new ArrayList<>();
        } else {
            for (int i = 0; i < FileUtils.cartList.size(); i++) {
                if (FileUtils.cartList.get(i).get_id() == cart.get_id()) {
                    index = i;
                }
            }
        }
        if (index != -1) {
            ProductModel_Test cartOld = FileUtils.cartList.get(index);
            FileUtils.cartList.get(index).setQty(cartOld.getQty() + 1);
        } else {
            cart.setQty(1);
            FileUtils.cartList.add(cart);
        }
    }


    public void buttonHeart() {
        buttonHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        setUp();
    }

    public void setUp() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentLayout.getContext());
        layoutManager.setOrientation(layoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        myRecycleViewAdapter = new DetailRecyclerViewAdapter((Activity) fragmentLayout.getContext(), R.layout.detail_layout_item, arrayList);
        recyclerView.setAdapter(myRecycleViewAdapter);
    }

    public void createDataForMainProduct() {
        img_MainProduct.setBackgroundResource(DetailProduct.getImg());
        if (DetailProduct.getDescription().trim().isEmpty() == true) {
            txt_DescriptionProductMain.setText("" +
                    "Xin lỗi, sản phẩm này chưa được cập nhât chi tiết chúng" +
                    " tôi sẽ cập nhật trong thời gian sớm nhất có thể!" +
                    "");
        } else {
            txt_DescriptionProductMain.setText(DetailProduct.getDescription());
        }
        txt_NameProductMain.setText(DetailProduct.getName());
        txt_PriceProductMain.setText((FormartCurentcy.format(String.valueOf(DetailProduct.getPrice()))) + " đồng");
        txt_start.setText(String.valueOf(DetailProduct.getRate()));
        //----------------------Star Start printf-----------------//
//        for (int i = 0; i < DetailProduct.getRate(); i++) {
//            ImageView imageView = new ImageView(fragmentLayout.getContext());
//            imageView.setBackgroundResource(R.drawable.ic_baseline_star_24);
//            startLayoutWrapper.addView(imageView);
//        }
        CreateStart.renderStart(startLayoutWrapper, DetailProduct, (Activity) fragmentLayout.getContext());
        //----------------------End Start printf------------------//
    }

    //----------------------------Catch event click-----------------//
    public void ClickEvent() {
        myRecycleViewAdapter.set_OnRecyclerViewOnClickListener(new DetailRecyclerViewAdapter.onRecyclerViewOnClickListener() {
            @Override
            public void onItemRecyclerViewOnClickListener(int p, View CardView) {
                //MARK actions
            }
        });
    }

    //----------------------------Customize toasl------------------------------//

}
