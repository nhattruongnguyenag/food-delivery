package vn.tdc.edu.fooddelivery.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import vn.tdc.edu.fooddelivery.utils.ImageUploadUtils;

public abstract class AbstractActivity extends AppCompatActivity {
    private Toolbar toolbar;

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
                    .replace(layout, (Fragment) fragment);
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

    public void openFeedbackDialog(int gravity, boolean cancelable) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_confirm_dialog_popup);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(cancelable);
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

            if (requestCode == ImageUploadUtils.REQ_CAMERA) {
                ImageUploadUtils.getInstance().takePhotoAction(this);
            } else if (requestCode == ImageUploadUtils.REQ_READ_EXTERNAL_STORAGE) {
                ImageUploadUtils.getInstance().takePhotoAction(this);
            }
        }
    }
}
