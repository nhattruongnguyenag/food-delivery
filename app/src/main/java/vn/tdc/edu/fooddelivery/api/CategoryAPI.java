package vn.tdc.edu.fooddelivery.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.tdc.edu.fooddelivery.models.CategoryModel;

public interface CategoryAPI {
    @GET("api/categories")
    Call<List<CategoryModel>> getCategories();

    @POST("api/categories")
    Call<CategoryModel> saveCategory(@Body CategoryModel categoryModel);

}
