package vn.tdc.edu.fooddelivery.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.user.MainActivity;
import vn.tdc.edu.fooddelivery.models.AddCarstModel;
import vn.tdc.edu.fooddelivery.models.CarstModel;
import vn.tdc.edu.fooddelivery.models.ProductModel;
import vn.tdc.edu.fooddelivery.utils.FormatCurentcy;
import vn.tdc.edu.fooddelivery.fragments.user.CartFragment;
import vn.tdc.edu.fooddelivery.utils.FileUtils;


public class CartRecycleViewAdapter extends RecyclerView.Adapter<CartRecycleViewAdapter.MyViewHolder> {
    //B2: Definitions variable of this interface
    private onRecyclerViewOnClickListener _onRecyclerViewOnClickListener;
    private Activity activity;
    private int layout_id;
    private List<CarstModel> arrayList;
    private CartFragment cartScreenActivity = new CartFragment();
    private MainActivity mainActivity;


    public CartRecycleViewAdapter(Activity activity, int layout_id, List<CarstModel> arrayList) {
        this.activity = activity;
        this.layout_id = layout_id;
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
        CarstModel cart = arrayList.get(position);
        holder.txt_name.setText(String.valueOf(cart.getProduct().getName()));
        holder.txt_total.setText(FormatCurentcy.format(cart.getProduct().getPrice() + "") + " x " + FormatCurentcy.format(cart.getQuantity() + "") + " = " + FormatCurentcy.format((cart.getQuantity() * cart.getProduct().getPrice()) + "") + " VND ");
        holder.txt_qty.setText(String.valueOf(cart.getQuantity()));
        Glide.with(activity).load(cart.getProduct().getImageUrl())
                .into(holder.img);
        //B3: Event click
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_onRecyclerViewOnClickListener != null) {
                    _onRecyclerViewOnClickListener.onItemRecyclerViewOnClickListener(position, holder.itemView);
                }
            }
        };

        btnPlustClickEvent(holder, cart);
        btnSubClickEvent(holder, cart);
        deleteButtonEventClick(holder, cart);
    }

    public void deleteButtonEventClick(MyViewHolder holder, CarstModel cart) {
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = 1;
                CartFragment cartFragment = new CartFragment();
                AddCarstModel carstModel = new AddCarstModel();
                carstModel.setId(cart.getCart_id());
                carstModel.setUser_id(cart.getUser().getId());
                carstModel.setQuantity(cart.getQuantity());
                carstModel.setProduct_id(cart.getProduct().getId());
                Log.d("TAG", "onClick: xoa");
                cartFragment.deleteCarst(carstModel);
            }
        });
    }


    public void CreateNumberBuyButtonEventClick() {
        if (mainActivity == null) {
            mainActivity = new MainActivity();
        }
//        if (FileUtils.cartList != null) {
//            mainActivity.createNum(FileUtils.cartList.size(), 2);
//        }
    }

    public void btnPlustClickEvent(MyViewHolder holder, CarstModel cart) {
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: cong" + cart.getQuantity());
                AddCarstModel carstItem = new AddCarstModel();
                carstItem.setUser_id(cart.getUser().getId());
                carstItem.setProduct_id(cart.getProduct().getId());
                carstItem.setQuantity(1);
                CartFragment cartFragment = new CartFragment();
                cartFragment.updateCart(carstItem);
            }
        });
    }


    public void btnSubClickEvent(MyViewHolder holder, CarstModel cart) {
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: tru" + cart.getQuantity());
                AddCarstModel carstItem = new AddCarstModel();
                carstItem.setUser_id(cart.getUser().getId());
                carstItem.setProduct_id(cart.getProduct().getId());
                carstItem.setQuantity(-1);
                CartFragment cartFragment = new CartFragment();
                cartFragment.updateCart(carstItem);
            }
        });
    }


    public void updateArrayListCart() {
        CreateNumberBuyButtonEventClick();
        CartFragment.myRecycleViewAdapter.notifyDataSetChanged();
        cartScreenActivity.setActivityCart(activity);
        cartScreenActivity.CalculateAndAssign(arrayList);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //Definitions getType
    @Override
    public int getItemViewType(int position) {
        return layout_id;
    }

    //Customize your viewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;
        private TextView txt_name;
        private TextView txt_total;
        private TextView txt_qty;
        private Button btn_delete;
        private ImageButton plus;
        private ImageButton sub;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.img_cart_screen);
            txt_name = v.findViewById(R.id.txt_name_cart_Screen);
            txt_total = v.findViewById(R.id.txt_total_cart_screen);
            txt_qty = v.findViewById(R.id.txt_qty_cart_screen);
            btn_delete = v.findViewById(R.id.btn_delete_cart_screen);
            plus = v.findViewById(R.id.btn_plus_cart_screen);
            sub = v.findViewById(R.id.btn_sub_cart_screen);
            //Catch event
            img.setOnClickListener(this);
            txt_name.setOnClickListener(this);
            txt_total.setOnClickListener(this);
            txt_qty.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    //B1: Definition interface
    public interface onRecyclerViewOnClickListener {
        //p lay vi tri , cardView de lay layout_item ma da tao ra doi tuong thoi tiet do
        public void onItemRecyclerViewOnClickListener(int p, View CardView);
    }

    //B4: Setter for variable just definitions on top
    public void set_onRecyclerViewOnClickListener(onRecyclerViewOnClickListener _onRecyclerViewOnClickListener) {
        this._onRecyclerViewOnClickListener = _onRecyclerViewOnClickListener;
    }
}
