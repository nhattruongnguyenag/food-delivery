package vn.tdc.edu.fooddelivery.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import vn.tdc.edu.fooddelivery.ProductModel;

public interface ProductAPI {
    @POST("api/products")
    Call<ProductModel> createProduct(
            @Body ProductModel productModel);
}
