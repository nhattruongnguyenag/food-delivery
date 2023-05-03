package vn.tdc.edu.fooddelivery.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.admin.UserManagementActivity;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;

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

    protected void switchActivity(Class<?> targetActivity, String action) {
        Intent intent = new Intent(this, targetActivity);
        intent.setAction(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public <T> T setFragment(Class<T> tClass, int layout) {
        T fragment = null;
        try {
            fragment = tClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(layout, (Fragment) fragment)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

        return fragment;
    }


}
