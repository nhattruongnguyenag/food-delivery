package vn.tdc.edu.fooddelivery.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activity.AbstractActivity;

public class CategoryActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        createActionBar();
    }
}