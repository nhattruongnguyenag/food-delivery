package vn.tdc.edu.fooddelivery.activities.admin;

import android.os.Bundle;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.fragments.admin.OrderStatusTabFragment;
import vn.tdc.edu.fooddelivery.fragments.admin.OrdersListFragment;

public class OrderManagementActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_order_management);
        createActionBar();

        setFragment(OrderStatusTabFragment.class, R.id.frameLayout, false);
    }
}