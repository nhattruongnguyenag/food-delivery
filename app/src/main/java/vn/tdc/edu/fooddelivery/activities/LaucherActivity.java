package vn.tdc.edu.fooddelivery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.fragments.user.HomeFragment;

public class LaucherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_laucher);

        new Handler().postDelayed(new Runnable() {
            @Override
            // check ktra đã đăng nhập vào thẳng vào Home
            public void run() {
                Intent intent = new Intent(LaucherActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}