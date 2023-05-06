package vn.tdc.edu.fooddelivery.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.tdc.edu.fooddelivery.models.ProductModel;

public interface ProductAPI {
    @GET("api/products")
    Call<List<ProductModel>> getProducts();
}
