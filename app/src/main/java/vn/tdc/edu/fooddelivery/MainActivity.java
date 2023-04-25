package vn.tdc.edu.fooddelivery;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.api.UploadAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.models.FileModel;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class MainActivity extends AppCompatActivity {
    private ImageView imageViewUpload;
    private CardView cardViewUploadImage;
    private Button btnUpload;
    private String selectedImage;
    private ActivityResultLauncher<Intent> startActivityForResult;
    private final int REQ_CAMERA = 99;
    private final int REQ_READ_EXTERNAL_STORAGE = 100;
    private CharSequence[] options = {"Camera", "Gallery", "Cancel"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewUpload = findViewById(R.id.imageViewUpload);
        cardViewUploadImage = findViewById(R.id.cardViewUploadImage);
        btnUpload = findViewById(R.id.btnUpload);

        startActivityForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        Intent data = result.getData();
                        if (result.getResultCode() == RESULT_OK && data != null) {
                            switch (getIntent().getStringExtra("req")) {
                                case "Camera":
                                    Bitmap image = (Bitmap) data.getExtras().get("data");
                                    selectedImage = FileUtils.getPath(MainActivity.this, getImageUri(MainActivity.this, image));
                                    imageViewUpload.setImageBitmap(image);
                                    break;
                                case "Gallery":
                                    Uri imageUri = data.getData();
                                    selectedImage = FileUtils.getPath(MainActivity.this, imageUri);
                                    Picasso.get().load(imageUri).into(imageViewUpload);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
        );

        imageViewUpload.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
        });

        btnUpload.setOnClickListener(view -> {
            uploadFileToServer();
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

    private boolean checkPermissions(String permission) {
        int check = checkSelfPermission(permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }

    private void pickImageAction() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getIntent().putExtra("req", "Gallery");
        startActivityForResult.launch(intent);
    }

    private void takePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getIntent().putExtra("req", "Camera");
        startActivityForResult.launch(intent);
    }

    public Uri getImageUri(Context context, Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "myImage", "");
        return Uri.parse(path);
    }

    public void uploadFileToServer() {

        File file = new File(Uri.parse(selectedImage).getPath());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        UploadAPI uploadAPI = RetrofitBuilder.getClient().create(UploadAPI.class);

        Call<FileModel> call = uploadAPI.callUploadApi(filePart);
        call.enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                FileModel fileModel = response.body();
                Toast.makeText(MainActivity.this, fileModel.getResult(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}