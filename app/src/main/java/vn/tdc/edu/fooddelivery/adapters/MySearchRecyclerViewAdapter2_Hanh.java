package vn.tdc.edu.fooddelivery.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.models.CartModel_Hanh;

public class MySearchRecyclerViewAdapter2_Hanh extends RecyclerView.Adapter<MySearchRecyclerViewAdapter2_Hanh.MyViewHolder> implements Filterable {
    private Activity activity;
    private int layout_id;
    public ArrayList<CartModel_Hanh> arrayList;
    private ArrayList<CartModel_Hanh> arrayListOld;
    public static ArrayList<CartModel_Hanh> cartArrayListOnChange;
    public UserClicListenter userClicListenter;

    public void setArrayList(ArrayList<CartModel_Hanh> arrayList) {
        this.arrayList = arrayList;
    }

    public MySearchRecyclerViewAdapter2_Hanh(Activity activity, int layout_id, ArrayList<CartModel_Hanh> arrayList, UserClicListenter userClicListenter) {
        this.activity = activity;
        this.layout_id = layout_id;
        this.arrayList = arrayList;
        arrayListOld = arrayList;
        this.userClicListenter = userClicListenter;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
        holder.img.setImageDrawable(activity.getResources().getDrawable(cart.getImg(), activity.getTheme()));
        //B3: Event click
        //B3: Event click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userClicListenter.selectedUser(cart);
            }
        });
    }


    //Definitions getType
    @Override
    public int getItemViewType(int position) {
        return layout_id;
    }


    //Customize your viewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView txt_name;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.img_search_item_type);
            txt_name = v.findViewById(R.id.txt_name_search_item_type);
        }
    }

    // Phan thuc hien tim kiem va xuat len man hinh
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.values = arrayListOld;
                    filterResults.count = arrayListOld.size();
                } else {
                    String searchStr = constraint.toString().toLowerCase();
                    List<CartModel_Hanh> userModels = new ArrayList<>();
                    for (CartModel_Hanh userModel : arrayListOld) {
                        if (userModel.getName().toLowerCase().contains(searchStr)) {
                            userModels.add(userModel);
                        }
                    }
                    filterResults.values = userModels;
                    filterResults.count = userModels.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults Results) {
                arrayList = (ArrayList<CartModel_Hanh>) Results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface UserClicListenter {
        void selectedUser(CartModel_Hanh userModel);
    }

}
