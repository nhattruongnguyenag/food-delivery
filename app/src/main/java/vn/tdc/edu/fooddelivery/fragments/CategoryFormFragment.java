package vn.tdc.edu.fooddelivery.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.MainActivity;
import vn.tdc.edu.fooddelivery.api.CategoryAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.models.CategoryModel;
import vn.tdc.edu.fooddelivery.models.ProductModel;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        }

        startActivityForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        Intent data = result.getData();
                        if (result.getResultCode() == AppCompatActivity.RESULT_OK && data != null) {
                            switch (getActivity().getIntent().getStringExtra("req")) {
                                case "Camera":
                                    Bitmap image = (Bitmap) data.getExtras().get("data");
                                    selectedImage = FileUtils.getPath(getActivity(), getImageUri(getActivity(), image));
                                    imgCategory.setImageBitmap(image);
                                    break;
                                case "Gallery":
                                    Uri imageUri = data.getData();
                                    selectedImage = FileUtils.getPath(getActivity(), imageUri);
                                    Picasso.get().load(imageUri).into(imgCategory);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
        );

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnUploadImage) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select Image");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (options[which].equals("Camera")) {
                        if (checkPermissions(Manifest.permission.CAMERA)) {
                            takePhotoAction();
                        } else {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQ_CAMERA);
                        }
                    } else if (options[which].equals("Gallery")) {
                        if (checkPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            pickImageAction();
                        } else {
                            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_READ_EXTERNAL_STORAGE);
                        }
                    } else {
                        dialog.dismiss();
                    }
                }
            });

            builder.show();
        } else if (view.getId() == R.id.btnAddOrUpdate) {
            uploadFileToServer(new Action() {
                @Override
                public void callAction(String fileName) {

                    CategoryModel categoryModel = new CategoryModel();

                    categoryModel.setImage(fileName);
                    categoryModel.setName(edName.getText().toString());
                    categoryModel.setNumberOfProduct(0);

                    Call<CategoryModel> call = RetrofitBuilder.getClient().create(CategoryAPI.class).saveProduct(categoryModel);

                    call.enqueue(new Callback<CategoryModel>() {
                        @Override
                        public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                                    .setTitle("Message")
                                    .setMessage("Thêm danh mục thành công")
                                    .setPositiveButton("Ok", null);
                            alert.show();
                        }

                        @Override
                        public void onFailure(Call<CategoryModel> call, Throwable t) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                                    .setTitle("Message")
                                    .setMessage("Thêm danh mục thất bại")
                                    .setPositiveButton("Ok", null);
                            alert.show();
                        }
                    });
                }
            });
        }
    }
}