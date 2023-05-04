package vn.tdc.edu.fooddelivery.fragments;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.adapters.CategoryRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.api.CategoryAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.models.CategoryModel;

public class CategoriesListFragment extends AbstractFragment implements View.OnClickListener {
    private Button btnAdd;
    private RecyclerView recyclerViewCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragment_categories_list, container, false);

        recyclerViewCategory = view.findViewById(R.id.recyclerViewCategory);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        List<CategoryModel> categoryModels = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            CategoryModel categoryModel = new CategoryModel();
//            categoryModel.setId(1);
//            categoryModel.setImage("https://www.freepnglogos.com/uploads/android-logo-png/android-logo-0.png");
//            categoryModel.setName("Nước ép");
//            categoryModel.setNumberOfProduct(12);
//            categoryModels.add(categoryModel);
//        }

        CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter((AbstractActivity) getActivity(), R.layout.recycler_category, categoryModels);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setAdapter(adapter);

        Call<List<CategoryModel>> call = RetrofitBuilder.getClient().create(CategoryAPI.class).getCategories();

        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                categoryModels.clear();
                categoryModels.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });

        adapter.setRecylerViewItemClickListener(new CategoryRecyclerViewAdapter.OnRecylerViewItemClickListener() {
            @Override
            public void onButtonEditClickListener(int position, CategoryModel categoryModel) {
                ((AbstractActivity) getActivity()).setFragment(CategoryFormFragment.class, R.id.frameLayout, true)
                        .setCategoryModel(categoryModels.get(position));
            }

            @Override
            public void onButtonDeleteClickListener(int position, CategoryModel categoryModel) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAdd) {
            ((AbstractActivity) getActivity()).setFragment(CategoryFormFragment.class, R.id.frameLayout, true);
        }
    }
}