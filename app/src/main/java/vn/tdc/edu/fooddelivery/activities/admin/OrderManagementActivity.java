package vn.tdc.edu.fooddelivery.activities.admin;

import android.os.Bundle;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.adapters.CategoryRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.models.CategoryModel;

public class OrderManagementActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_order_management);
        createActionBar();
    }
}