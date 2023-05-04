package vn.tdc.edu.fooddelivery.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.admin.UserManagementActivity;
import vn.tdc.edu.fooddelivery.api.UploadAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;
import vn.tdc.edu.fooddelivery.models.FileModel;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public abstract class AbstractActivity extends AppCompatActivity {
    private Toolbar toolbar;
    public ActivityResultLauncher<Intent> startActivityForResult;
    protected final int REQ_CAMERA = 99;
    protected final int REQ_READ_EXTERNAL_STORAGE = 100;
    protected CharSequence[] options = {"Camera", "Gallery", "Cancel"};

    private BackwardHandling action;

    public interface BackwardHandling {
        void doAction(String fileName);
    }


    protected void createActionBar() {
        setTitle(getIntent().getAction());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void showMessageDialog(String message) {
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show();
    }

    protected void switchActivity(Class<?> targetActivity, String action) {
        Intent intent = new Intent(this, targetActivity);
        intent.setAction(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public <T> T setFragment(Class<T> tClass, int layout, boolean addToBackStack) {
        T fragment = null;
        try {
            fragment = tClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction()
                    .replace(layout, (Fragment) fragment)
                    .setReorderingAllowed(true);
            if (addToBackStack) {
                transaction.addToBackStack(null);
            }

            transaction.commit();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

        return fragment;
    }

    private boolean checkPermissions(String permission) {
        int check = checkSelfPermission(permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }

    public void handleChoosingImage(ImageView imageView) {;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn hình ảnh");
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
    }

    public void handleUploadImage(String selectedImage, BackwardHandling action) {
        if (action != null) {
            uploadFileToServer(selectedImage, action);
        }
    }
    private void takePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getIntent().putExtra("req", "Camera");
        startActivityForResult.launch(intent);
    }

    private void pickImageAction() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getIntent().putExtra("req", "Gallery");
        startActivityForResult.launch(intent);
    }

    public void uploadFileToServer(String selectedImage, BackwardHandling action) {
        File file = new File(Uri.parse((selectedImage)).getPath());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        UploadAPI uploadAPI = RetrofitBuilder.getClient().create(UploadAPI.class);

        Call<FileModel> call = uploadAPI.callUploadApi(filePart);

        call.enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                FileModel fileModel = response.body();

                if (action != null) {
                    String fileName = fileModel.getFileName() == null ? "image_upload_default.png" : fileModel.getFileName();
                    action.doAction(fileName);
                }
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length == grantResults.length) {
            for (int grant : grantResults) {
                if (grant == PackageManager.PERMISSION_DENIED) {
                    return;
                }
            }

            if (requestCode == REQ_CAMERA) {
                takePhotoAction();
            } else if (requestCode == REQ_READ_EXTERNAL_STORAGE) {
                pickImageAction();
            }
        }
    }
}
