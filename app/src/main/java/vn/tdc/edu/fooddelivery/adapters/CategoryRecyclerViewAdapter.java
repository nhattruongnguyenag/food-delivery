package vn.tdc.edu.fooddelivery.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.models.CategoryModel;
import vn.tdc.edu.fooddelivery.models.ProductModel;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryItemHolder> {
    private Activity activity;
    private int layout;
    private List<CategoryModel> listCategories;

    private  OnRecylerViewItemClickListener recylerViewItemClickListener;

    public void setRecylerViewItemClickListener(OnRecylerViewItemClickListener recylerViewItemClickListener) {
        this.recylerViewItemClickListener = recylerViewItemClickListener;
    }

    public CategoryRecyclerViewAdapter(@NonNull Activity activity, int layout, @NonNull List<CategoryModel> listCategories) {
        this.activity = activity;
        this.layout = layout;
        this.listCategories = listCategories;
    }

    @NonNull
    @Override
    public CategoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        CardView view = (CardView) inflater.inflate(layout, parent, false);
        return new CategoryItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemHolder holder, int position) {
        CategoryModel categoryModel = listCategories.get(position);
        Glide.with(activity).load(categoryModel.getImage())
                .into(holder.imgCategory);
        holder.tvName.setText(categoryModel.getName());
        holder.tvNumberOfProduct.setText(categoryModel.getNumberOfProduct() + "(sp)");

        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recylerViewItemClickListener != null) {
                    if (view.getId() == R.id.btnEdit) {
                        holder.btnEdit.setSelected(!holder.btnEdit.isSelected());
                        recylerViewItemClickListener.onButtonEditClickListener(position, listCategories.get(position));
                    } else if (view.getId() == R.id.btnDelete) {
                        holder.btnDelete.setSelected(!holder.btnEdit.isSelected());
                        recylerViewItemClickListener.onButtonDeleteClickListener(position, listCategories.get(position));
                    }
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layout;
    }

    public interface OnRecylerViewItemClickListener {
        public void onButtonEditClickListener(int position, CategoryModel categoryModel);

        public void onButtonDeleteClickListener(int position, CategoryModel categoryModel);
    }

    public static class CategoryItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgCategory;
        private TextView tvName;
        private TextView tvNumberOfProduct;
        private View.OnClickListener onClickListener;

        private ImageButton btnEdit;
        private ImageButton btnDelete;

        public CategoryItemHolder(@NonNull View itemView) {
            super(itemView);
            this.imgCategory = itemView.findViewById(R.id.imgCategory);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvNumberOfProduct = itemView.findViewById(R.id.tvNumberOfProduct);
            this.btnEdit = itemView.findViewById(R.id.btnEdit);
            this.btnDelete = itemView.findViewById(R.id.btnDelete);

            // Event processing
            btnEdit.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
        }
    }
}
