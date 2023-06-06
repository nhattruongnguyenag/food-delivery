package vn.tdc.edu.fooddelivery.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.user.MainActivity;
import vn.tdc.edu.fooddelivery.utils.FormartCurentcy;
import vn.tdc.edu.fooddelivery.fragments.user.CartFragment;
import vn.tdc.edu.fooddelivery.models.ProductModel_Test;
import vn.tdc.edu.fooddelivery.utils.FileUtils;


public class CartRecycleViewAdapter extends RecyclerView.Adapter<CartRecycleViewAdapter.MyViewHolder> {
    //B2: Definitions variable of this interface
    private onRecyclerViewOnClickListener _onRecyclerViewOnClickListener;
    private Activity activity;
    private int layout_id;
    private ArrayList<ProductModel_Test> arrayList;
    private CartFragment cartScreenActivity = new CartFragment();
    private MainActivity mainActivity;


    public CartRecycleViewAdapter(Activity activity, int layout_id, ArrayList<ProductModel_Test> arrayList) {
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
        ProductModel_Test cart = arrayList.get(position);
        holder.txt_name.setText(String.valueOf(cart.getName()));
        holder.txt_total.setText(FormartCurentcy.format(cart.getPrice() + "") + " x " + FormartCurentcy.format(cart.getQty() + "") + " = " +  FormartCurentcy.format((cart.getQty() * cart.getPrice()) + "") + " VND ");
        holder.txt_qty.setText(String.valueOf(cart.getQty()));
        holder.img.setImageDrawable(activity.getResources().getDrawable(cart.getImg(), activity.getTheme()));
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

    public void deleteButtonEventClick(MyViewHolder holder, ProductModel_Test cart) {
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialog(cart);
            }
        });
    }


    public void createAlertDialog(ProductModel_Test cart) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(R.drawable.ic_baseline_warning_24);
        builder.setTitle("Bạn có muốn xóa sản phẩm này chứ!");
        builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileUtils.cartList.remove(cart);
                updateArrayListCart();
            }
        });
        builder.show();
    }

    public void CreateNumberBuyButtonEventClick() {
        if (mainActivity == null) {
            mainActivity = new MainActivity();
        }
        if (FileUtils.cartList != null) {
            mainActivity.createNum(FileUtils.cartList.size(), 2);
        }
    }

    public void btnPlustClickEvent(MyViewHolder holder, ProductModel_Test cart) {
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeQtyProductInCart(1, cart);
            }
        });
    }


    public void btnSubClickEvent(MyViewHolder holder, ProductModel_Test cart) {
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeQtyProductInCart(2, cart);
            }
        });
    }

    public void changeQtyProductInCart(int type, ProductModel_Test cart) {
        int index = -1;
        if (FileUtils.cartList == null) {
            FileUtils.cartList = new ArrayList<>();
        } else {
            for (int i = 0; i < FileUtils.cartList.size(); i++) {
                if (FileUtils.cartList.get(i).get_id() ==  cart.get_id()) {
                    index = i;
                }
            }
        }
        if (index != -1) {
            ProductModel_Test cartOld = FileUtils.cartList.get(index);
            if (type == 1) {
                FileUtils.cartList.get(index).setQty(cartOld.getQty() + 1);
            } else {
                FileUtils.cartList.get(index).setQty(cartOld.getQty() - 1);
                if (FileUtils.cartList.get(index).getQty() < 1) {
                    FileUtils.cartList.remove(index);
                }
            }
            //Update data array
            updateArrayListCart();
        } else {
            Toast.makeText(activity, "Thay doi so luong that bai!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateArrayListCart() {
        CreateNumberBuyButtonEventClick();
        CartFragment.myRecycleViewAdapter.notifyDataSetChanged();
        cartScreenActivity.setActivityCart(activity);
        cartScreenActivity.CalculateAndAssign(FileUtils.cartList);
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
