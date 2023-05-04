package vn.tdc.edu.fooddelivery.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.api.CategoryAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.models.CategoryModel;
import vn.tdc.edu.fooddelivery.utils.ImageUploadUtils;

public class CategoryFormFragment extends AbstractFragment implements View.OnClickListener {
    private ShapeableImageView imgCategory;
    private FloatingActionButton btnUploadImage;
    private EditText edName;
    private Button btnAddOrUpdate;

    private CategoryModel categoryModel;

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_category_form, container, false);
        imgCategory = view.findViewById(R.id.imgCategory);
        btnUploadImage = view.findViewById(R.id.btnUploadImage);
        edName = view.findViewById(R.id.edName);
        btnAddOrUpdate = view.findViewById(R.id.btnAddOrUpdate);

        imgCategory.setOnClickListener(this);
        btnUploadImage.setOnClickListener(this);
        btnAddOrUpdate.setOnClickListener(this);

        if (categoryModel != null && categoryModel.getId() != null) {
            btnAddOrUpdate.setText("Cập nhật danh mục");
            edName.setText(categoryModel.getName());
            Glide.with(getActivity()).load(categoryModel.getImage())
                    .into(imgCategory);
            return view;
        }

        ImageUploadUtils.getInstance().registerForUploadImageActivityResult(this,imgCategory);

        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnUploadImage) {
            ImageUploadUtils.getInstance().showChoosingImageOptionsDialog((AbstractActivity) getActivity(), imgCategory);
        }

        if (view.getId() == R.id.btnAddOrUpdate) {
            ImageUploadUtils.getInstance().handleUploadFileToServer(new ImageUploadUtils.Action() {
                @Override
                public void onSucess(String fileName) {
                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.setImage(fileName);
                    categoryModel.setName(edName.getText().toString());
                    categoryModel.setNumberOfProduct(0);

                    Call<CategoryModel> call = RetrofitBuilder.getClient().create(CategoryAPI.class).saveCategory(categoryModel);

                    call.enqueue(new Callback<CategoryModel>() {
                        @Override
                        public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                            ((AbstractActivity) getActivity()).showMessageDialog("Thêm danh mục thành công");
                            ((AbstractActivity) getActivity()).setFragment(CategoriesListFragment.class, R.id.frameLayout, true);
                        }

                        @Override
                        public void onFailure(Call<CategoryModel> call, Throwable t) {
                            ((AbstractActivity) getActivity()).showMessageDialog("Thêm danh mục thất bại");
                        }
                    });
                }

                @Override
                public void onFailed() {
                }
            });
        }
    }
}