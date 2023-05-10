package vn.tdc.edu.fooddelivery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.user.MainActivity;

public class LaucherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_laucher);

        Intent intent = new Intent(LaucherActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}