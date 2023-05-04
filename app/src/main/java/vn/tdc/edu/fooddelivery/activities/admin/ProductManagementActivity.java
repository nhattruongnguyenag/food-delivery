package vn.tdc.edu.fooddelivery.activities.admin;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.adapters.ProductRecyvlerViewAdapter;
import vn.tdc.edu.fooddelivery.models.ProductModel;

public class ProductManagementActivity extends AbstractActivity {
    private List<ProductModel> listProducts;

    private RecyclerView recyclerViewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_management);
        createActionBar();


//        listProducts = new ArrayList<>();
//
//        for (int i = 0; i < 50; i++) {
//            ProductModel productModel = new ProductModel();
//            productModel.setName("Cơm chiên dương châu");
//            productModel.setUnit("Phần");
//            productModel.setQuantity(50);
//            productModel.setPrice(45000L);
//            listProducts.add(productModel);
//        }
//
//        recyclerViewProduct = findViewById(R.id.recyclerViewProduct);
//
//        ProductRecyvlerViewAdapter adapter = new ProductRecyvlerViewAdapter(this, R.layout.recycler_product, listProducts);
//
//        // Create layout manager
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerViewProduct.setLayoutManager(layoutManager);
//        recyclerViewProduct.setAdapter(adapter);
    }
}