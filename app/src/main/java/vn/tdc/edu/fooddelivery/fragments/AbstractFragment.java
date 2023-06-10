package vn.tdc.edu.fooddelivery.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.api.UploadAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.models.FileModel;
import vn.tdc.edu.fooddelivery.utils.FileUtils;
import vn.tdc.edu.fooddelivery.utils.ImageUploadUtils;

public abstract class AbstractFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public <T> T setFragment(Class<T> tClass, int layout, boolean addCurrentFragmentToBackStack) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        AbstractFragment fragment = (AbstractFragment) fragmentManager.findFragmentByTag(tClass.getSimpleName() + "");
        Log.d("fragment-manager", fragmentManager.getFragments().size() + "");
        try {
            if (fragment != null) {
                transaction.remove(fragment).commit();
            }

            fragment = (AbstractFragment) tClass.newInstance();

            transaction.replace(layout, fragment, tClass.getSimpleName() + "");

            if (fragmentManager.findFragmentByTag(tClass.getSimpleName() + "") == null) {
                transaction.addToBackStack(null);
            }
            transaction.commit();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (java.lang.InstantiationException e) {
            throw new RuntimeException(e);
        }
        return (T) fragment;
    }
}
