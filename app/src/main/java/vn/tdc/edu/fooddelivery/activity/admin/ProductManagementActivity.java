package vn.tdc.edu.fooddelivery.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activity.AbstractActivity;

public class ProductManagementActivity extends AbstractActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_management);
        createActionBar();
    }
}