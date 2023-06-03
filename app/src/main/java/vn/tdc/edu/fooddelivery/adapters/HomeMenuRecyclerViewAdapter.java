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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.user.MainActivity;
import vn.tdc.edu.fooddelivery.components.CreateStart;
import vn.tdc.edu.fooddelivery.components.SendDataAndGotoDetailScreen;
import vn.tdc.edu.fooddelivery.models.ProductModel_Test;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class HomeMenuRecyclerViewAdapter extends RecyclerView.Adapter<HomeMenuRecyclerViewAdapter.MyViewHolder> {

    private Activity activity;
    private int layout_ID;
    private ArrayList<ProductModel_Test> arrayList;
    private onRecyclerViewOnClickListener _onRecyclerViewOnClickListener;
    private int flag = 1;
    private MainActivity mainActivity = null;

    public HomeMenuRecyclerViewAdapter(Activity activity, int layout_ID, ArrayList<ProductModel_Test> arrayList) {
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
        ProductModel_Test cart = arrayList.get(position);
        holder.txt_name.setText(cart.getName());
        holder.txt_price.setText(String.valueOf(cart.getPrice()) + " đ");
        holder.txt_qtyStart.setText("(" + String.valueOf(cart.getRate()) + ")");
        holder.linearLayout.removeAllViews();
        //-------------------------Start add star-------------------------//
        CreateStart.renderStart(holder.linearLayout, cart, activity);
        //-------------------------End add start-------------------------//
        holder.imageView.setImageDrawable(activity.getResources().getDrawable(cart.getImg(), activity.getTheme()));
        //----------------------------Star event click to detail screen-------------------//
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_onRecyclerViewOnClickListener != null) {
                    flag = 2;
                    _onRecyclerViewOnClickListener.onItemRecyclerViewOnClickListener(position, holder.itemView);
                    SendDataAndGotoDetailScreen.send(activity, cart, arrayList);
                }
            }
        };
        if (flag == 1) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendDataAndGotoDetailScreen.send(activity, cart, arrayList);
                }
            });
        }
        //----------------------------End event click to detail screen-------------------//
        //------------------------------Start catch click event---------------------------//
        buttonBuyEventClick(holder, cart);
        //------------------------------End catch click event---------------------------//
    }

    public void buttonBuyEventClick(MyViewHolder holder, ProductModel_Test cart) {
        //Btn buy event
        holder.btn_buy.setOnClickListener(new View.OnClickListener() {
            int index = -1;

            @Override
            public void onClick(View v) {
                if (FileUtils.cartList == null) {
                    FileUtils.cartList = new ArrayList<>();
                } else {
                    for (int i = 0; i < FileUtils.cartList.size(); i++) {
                        if (FileUtils.cartList.get(i).get_id() == (cart.get_id())) {
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
                //-----------------Create notify cart number--------------------//
                MainActivity.CreateNumberBuyButtonEventClick();
            }
        });
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
        public LinearLayout linearLayout;
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
