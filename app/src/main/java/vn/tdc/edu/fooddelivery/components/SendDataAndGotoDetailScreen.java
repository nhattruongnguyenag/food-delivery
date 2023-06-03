package vn.tdc.edu.fooddelivery.components;

import android.app.Activity;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.fragments.user.ProductDetailFragment;
import vn.tdc.edu.fooddelivery.models.ProductModel_Test;

public class SendDataAndGotoDetailScreen {
    //Send data and change screen to detail screen!
    public static void send(Activity activity, ProductModel_Test cart, ArrayList<ProductModel_Test> arrayList) {
        ((AbstractActivity)activity).setFragment(ProductDetailFragment.class, R.id.frameLayout, true)
                .setDetailProduct(cart).
                setArrayList(null)
                .setArrayList(arrayList);
    }
}
