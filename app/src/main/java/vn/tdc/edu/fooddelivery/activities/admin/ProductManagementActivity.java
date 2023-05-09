package vn.tdc.edu.fooddelivery.activities.admin;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.fragments.admin.ProductsListFragment;
import vn.tdc.edu.fooddelivery.models.ProductModel;

public class ProductManagementActivity extends AbstractActivity {
    private List<ProductModel> listProducts;

    private RecyclerView recyclerViewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_management);
        createActionBar();

        setFragment(ProductsListFragment.class, R.id.frameLayout, false);
    }
}