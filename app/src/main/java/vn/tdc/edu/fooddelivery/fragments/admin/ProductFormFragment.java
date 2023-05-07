package vn.tdc.edu.fooddelivery.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.api.CategoryAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.components.MultiSelectDialog;
import vn.tdc.edu.fooddelivery.models.CategoryModel;

public class ProductFormFragment extends Fragment implements View.OnClickListener {
    private List<CategoryModel> categoriesList;
    private TextView tvCategories;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_product_form, container, false);
        tvCategories = view.findViewById(R.id.edCategories);
        tvCategories.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.edCategories) {
            showMultiSelectCategoryDialog();
        }
    }

    private void showMultiSelectCategoryDialog() {
        if (categoriesList == null) {
            categoriesList = new ArrayList<>();
        }

        Call<List<CategoryModel>> call = RetrofitBuilder.getClient().create(CategoryAPI.class).getCategories();

        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.body() != null) {
                    categoriesList.clear();
                    categoriesList.addAll(response.body());
                }

                MultiSelectDialog<CategoryModel> multiSelectDialog = new MultiSelectDialog(getContext(), categoriesList);
                multiSelectDialog.setTitle("Chọn danh mục");
                multiSelectDialog.setOnActionClickListener(new MultiSelectDialog.Action() {
                    @Override
                    public void cancel() {
                    }
                    @Override
                    public void ok(List<Integer> listObjectSelected) {

                    }
                });
                multiSelectDialog.show();
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });
    }
}