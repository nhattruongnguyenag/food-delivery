package vn.tdc.edu.fooddelivery.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.fragments.ProductDetailFragment;
import vn.tdc.edu.fooddelivery.models.CartModel_Hanh;

public class MyDetailRecyclerViewAdapter_Hanh extends RecyclerView.Adapter<MyDetailRecyclerViewAdapter_Hanh.MyViewHolder> {
    private Activity activity;
    private int layout_ID;
    private ArrayList<CartModel_Hanh> arrayList;
    private onRecyclerViewOnClickListener _onRecyclerViewOnClickListener;
    //Doi tuong do du lieu vao

    public MyDetailRecyclerViewAdapter_Hanh(Activity activity, int layout_ID, ArrayList<CartModel_Hanh> arrayList) {
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
        holder.imageViewListItem.setImageDrawable(activity.getResources().getDrawable(cart.getImg(), activity.getTheme()));
        //B3: Event click
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_onRecyclerViewOnClickListener != null) {
                    _onRecyclerViewOnClickListener.onItemRecyclerViewOnClickListener(position, holder.itemView);
                    sendDataForDetailScreen(cart, arrayList);
                }
            }
        };
    }

    public void sendDataForDetailScreen(CartModel_Hanh cart, ArrayList<CartModel_Hanh> arrayList) {
        ((AbstractActivity) activity).setFragment(ProductDetailFragment.class, R.id.frameLayout, true)
                .setDetailProduct(cart)
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
        private ImageView imageViewListItem;
        private View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewListItem = itemView.findViewById(R.id.img_suggestive_detail_screen);
            //Catch event click
            imageViewListItem.setOnClickListener(this);
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
