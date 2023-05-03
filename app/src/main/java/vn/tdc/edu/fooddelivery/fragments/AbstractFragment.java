package vn.tdc.edu.fooddelivery.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.activities.MainActivity;
import vn.tdc.edu.fooddelivery.api.UploadAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.models.FileModel;

public abstract class AbstractFragment extends Fragment {
    protected String selectedImage;
    protected ActivityResultLauncher<Intent> startActivityForResult;
    protected final int REQ_CAMERA = 99;
    protected final int REQ_READ_EXTERNAL_STORAGE = 100;
    protected CharSequence[] options = {"Camera", "Gallery", "Cancel"};

    protected boolean checkPermissions(String permission) {
        int check = getActivity().checkSelfPermission(permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }

    protected void pickImageAction() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().getIntent().putExtra("req", "Gallery");
        startActivityForResult.launch(intent);
    }

    protected void takePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().getIntent().putExtra("req", "Camera");
        startActivityForResult.launch(intent);
    }

    public Uri getImageUri(Context context, Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "myImage", "");
        return Uri.parse(path);
    }

    protected Action action;

    interface Action {
        void callAction(String fileName);
    }

    public void uploadFileToServer(Action action) {
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
                    if (action != null) {
                        action.callAction(fileModel.getFileName());
                    }
                }
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
            }
        });
    }
}
