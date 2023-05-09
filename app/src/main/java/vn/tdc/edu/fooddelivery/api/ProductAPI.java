package vn.tdc.edu.fooddelivery.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import vn.tdc.edu.fooddelivery.models.CategoryModel;
import vn.tdc.edu.fooddelivery.models.ProductModel;

public interface ProductAPI {
    @GET("api/products")
    Call<List<ProductModel>> getProducts();

    @POST("api/products")
    Call<ProductModel> saveProduct(@Body ProductModel productModel);

    @PUT("api/products")
    Call<ProductModel> updateProduct(@Body ProductModel productModel);
    @HTTP(method = "DELETE", path = "api/products", hasBody = true)
    Call<ProductModel> deleteProduct(@Body ProductModel productModel);
}
