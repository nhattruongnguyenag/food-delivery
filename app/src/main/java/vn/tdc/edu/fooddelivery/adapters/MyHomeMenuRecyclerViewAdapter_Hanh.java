package vn.tdc.edu.fooddelivery.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.activities.MainActivity;
import vn.tdc.edu.fooddelivery.fragments.ProductDetailFragment;
import vn.tdc.edu.fooddelivery.models.CartModel_Hanh;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class MyHomeMenuRecyclerViewAdapter_Hanh extends RecyclerView.Adapter<MyHomeMenuRecyclerViewAdapter_Hanh.MyViewHolder> {

    private Activity activity;
    private int layout_ID;
    private ArrayList<CartModel_Hanh> arrayList;
    private onRecyclerViewOnClickListener _onRecyclerViewOnClickListener;
    private int flag = 1;
    private MainActivity mainActivity = null;

    public MyHomeMenuRecyclerViewAdapter_Hanh(Activity activity, int layout_ID, ArrayList<CartModel_Hanh> arrayList) {
        this.activity = activity;
        this.layout_ID = layout_ID;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        CardView cardView = (CardView) layoutInflater.inflate(viewType, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartModel_Hanh cart = arrayList.get(position);
        holder.txt_name.setText(cart.getName());
        holder.txt_price.setText(String.valueOf(cart.getPrice()) + " đ");
        holder.txt_qtyStart.setText("(" + String.valueOf(cart.getRate()) + ")");
        holder.linearLayout.removeAllViews();
        //-------Start add star
        for (int i = 0; i < cart.getRate(); i++) {
            ImageView imageView = new ImageView(activity);
            imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_baseline_star_24, activity.getTheme()));
            holder.linearLayout.addView(imageView);
        }
        //-------End add star
        holder.imageView.setImageDrawable(activity.getResources().getDrawable(cart.getImg(), activity.getTheme()));
        //holder.imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_launcher_background, activity.getTheme()));
        //B3: Event click
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_onRecyclerViewOnClickListener != null) {
                    flag = 2;
                    _onRecyclerViewOnClickListener.onItemRecyclerViewOnClickListener(position, holder.itemView);
                    sendDataForDetailScreen(cart, arrayList);
                }
            }
        };
        if (flag == 1) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendDataForDetailScreen(cart, arrayList);
                }
            });
        }
        //Catch click event
        buttonBuyEventClick(holder, cart);
    }

    public void buttonBuyEventClick(MyViewHolder holder, CartModel_Hanh cart) {
        //Btn buy event
        holder.btn_buy.setOnClickListener(new View.OnClickListener() {
            int index = -1;

            @Override
            public void onClick(View v) {
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
                Toast.makeText(activity, "Đã thêm sản phẩm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                CreateNumberBuyButtonEventClick();
            }
        });
    }

    public void CreateNumberBuyButtonEventClick() {
        if (mainActivity == null) {
            mainActivity = new MainActivity();
        }
        if (FileUtils.cartList != null) {
            mainActivity.createNum(FileUtils.cartList.size(), 2);
        }
    }

    public void sendDataForDetailScreen(CartModel_Hanh cart, ArrayList<CartModel_Hanh> arrayList) {
        ((AbstractActivity) activity).setFragment(ProductDetailFragment.class, R.id.frameLayout, true)
                .setDetailProduct(cart).
                setArrayList(null)
                .setArrayList(arrayList);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layout_ID;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private LinearLayout linearLayout;
        private TextView txt_name;
        private TextView txt_price;
        private TextView txt_qtyStart;
        private Button btn_buy;
        private View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View v) {
            super(v);
            linearLayout = v.findViewById(R.id.linearlayout_rating_wrapper);
            imageView = v.findViewById(R.id.img_menu_home_screen);
            txt_name = v.findViewById(R.id.txt_name_menu_home_screen);
            txt_price = v.findViewById(R.id.txt_price_menu_home_screen);
            txt_qtyStart = v.findViewById(R.id.txt_qty_start_menu_home_screen);
            btn_buy = v.findViewById(R.id.btn_buy_menu_home_screen);
            //Catch event click
            imageView.setOnClickListener(this);
            txt_name.setOnClickListener(this);
            txt_price.setOnClickListener(this);
            txt_qtyStart.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    //B1: definitions interface
    public interface onRecyclerViewOnClickListener {
        public void onItemRecyclerViewOnClickListener(int p, View CardView);
    }
    //B4: Setter

    public void set_OnRecyclerViewOnClickListener(onRecyclerViewOnClickListener _OnRecyclerViewOnClickListener) {
        this._onRecyclerViewOnClickListener = _OnRecyclerViewOnClickListener;
    }
}
