package vn.tdc.edu.fooddelivery.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.models.ProductModel;

public class NotificationFragment extends AbstractFragment {
    private List<ProductModel> listProducts;
    private ProductModel productModel;

    public List<ProductModel> getListProducts() {
        return listProducts;
    }

    public NotificationFragment setListProducts(List<ProductModel> listProducts) {
        this.listProducts = listProducts;
        return this;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public NotificationFragment setProductModel(ProductModel productModel) {
        this.productModel = productModel;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.fragment_notification, container, false);
        return fragmentLayout;
    }
}