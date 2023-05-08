package vn.tdc.edu.fooddelivery.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import vn.tdc.edu.fooddelivery.models.CategoryModel;

public interface CategoryAPI {
    @GET("api/categories")
    Call<List<CategoryModel>> getCategories(@Query("sort") String sortBy);

    @POST("api/categories")
    Call<CategoryModel> saveCategory(@Body CategoryModel categoryModel);

    @PUT("api/categories")
    Call<CategoryModel> updateCategory(@Body CategoryModel categoryModel);

    @HTTP(method = "DELETE", path = "api/categories", hasBody = true)
    Call<CategoryModel> deleteCategory(@Body CategoryModel categoryModel);
}