package vn.tdc.edu.fooddelivery.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.activities.MainActivity;
import vn.tdc.edu.fooddelivery.api.UploadAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.models.FileModel;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public abstract class AbstractFragment extends Fragment {
    protected String selectedImage;
    protected final int REQ_CAMERA = 99;
    protected final int REQ_READ_EXTERNAL_STORAGE = 100;
    protected CharSequence[] options = {"Camera", "Gallery", "Cancel"};

    protected void pickImageAction() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().getIntent().putExtra("req", "Gallery");
        ((AbstractActivity) getActivity()).startActivityForResult.launch(intent);
    }

    protected void takePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().getIntent().putExtra("req", "Camera");
        ((AbstractActivity) getActivity()).startActivityForResult.launch(intent);
    }

    public Uri getImageUri(Context context, Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "myImage", "");
        return Uri.parse(path);
    }

    private String fileNameResponse;

    public void uploadFileToServer() {
        File file = new File(Uri.parse(selectedImage).getPath());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        UploadAPI uploadAPI = RetrofitBuilder.getClient().create(UploadAPI.class);

        Call<FileModel> call = uploadAPI.callUploadApi(filePart);
        call.enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                FileModel fileModel = response.body();

                if (fileModel != null) {
                    fileNameResponse = fileModel.getFileName();
                }
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                fileNameResponse = null;
            }
        });
    }

    public void setOnHandleStartActivityResult(ImageView imageView) {
        ((AbstractActivity) getActivity()).startActivityForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        Intent data = result.getData();
                        if (result.getResultCode() == AppCompatActivity.RESULT_OK && data != null) {
                            switch (getActivity().getIntent().getStringExtra("req")) {
                                case "Camera":
                                    Bitmap image = (Bitmap) data.getExtras().get("data");
                                    selectedImage = FileUtils.getPath(getActivity(), getImageUri(getActivity(), image));
                                    imageView.setImageBitmap(image);
                                    break;
                                case "Gallery":
                                    Uri imageUri = data.getData();
                                    selectedImage = FileUtils.getPath(getActivity(), imageUri);
                                    Picasso.get().load(imageUri).into(imageView);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
        );
    }


}
