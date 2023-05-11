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
import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.models.CartModel_Hanh;

public class MySearchRecyclerViewAdapter_Hanh extends RecyclerView.Adapter<MySearchRecyclerViewAdapter_Hanh.MyViewHolder> implements Filterable {
    //B2: Definitions variable of this interface
//    private SearchActivity searchActivity = new SearchActivity();
    private onRecyclerViewOnClickListener onRecyclerViewOnClickListener;
    private Activity activity;
    private int layout_id;
    public ArrayList<CartModel_Hanh> arrayList;
    private ArrayList<CartModel_Hanh> arrayListOld;
    public static ArrayList<CartModel_Hanh> cartArrayListOnChange;

    public void setArrayList(ArrayList<CartModel_Hanh> arrayList) {
        this.arrayList = arrayList;
    }

    public MySearchRecyclerViewAdapter_Hanh(Activity activity, int layout_id, ArrayList<CartModel_Hanh> arrayList) {
        this.activity = activity;
        this.layout_id = layout_id;
        this.arrayList = arrayList;
        arrayListOld = arrayList;
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
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRecyclerViewOnClickListener != null) {
                    onRecyclerViewOnClickListener.onItemRecyclerViewOnClickListener(position, holder.itemView);
                }
            }
        };
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
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.img_search_item_type);
            txt_name = v.findViewById(R.id.txt_name_search_item_type);
            //Catch event
            img.setOnClickListener(this);
            txt_name.setOnClickListener(this);
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
    public void setonRecyclerViewOnClickListener(onRecyclerViewOnClickListener onRecyclerViewOnClickListener) {
        this.onRecyclerViewOnClickListener = onRecyclerViewOnClickListener;
    }


    // Phan thuc hien tim kiem va xuat len man hinh
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                // Lúc đầu khi ta chưa điền giá trị thì nó có giá trị bằng giá trị của tất cả các giá trị có trong mảng gốc
                if (strSearch.isEmpty()) {
                    arrayList = arrayListOld;
                } else {
                    ArrayList<CartModel_Hanh> list = new ArrayList<>();
                    for (CartModel_Hanh _cart : arrayListOld) {
                        if (_cart.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(_cart);
                        }
                    }
                    arrayList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                //Update for click event
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrayList = (ArrayList<CartModel_Hanh>) filterResults.values;
//                cartArrayListOnChange = arrayList;
                notifyDataSetChanged();
            }
        };
    }

}
