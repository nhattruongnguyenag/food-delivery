package vn.tdc.edu.fooddelivery.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.tdc.edu.fooddelivery.models.CategoryModel;

public interface CategoryAPI {
    @POST("api/categories")
    Call<CategoryModel> saveProduct(@Body CategoryModel categoryModel);
}
