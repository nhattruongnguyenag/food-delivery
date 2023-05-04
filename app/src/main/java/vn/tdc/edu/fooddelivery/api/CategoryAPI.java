package vn.tdc.edu.fooddelivery.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import vn.tdc.edu.fooddelivery.models.CategoryModel;

public interface CategoryAPI {
    @GET("api/categories")
    Call<List<CategoryModel>> getCategories();

    @POST("api/categories")
    Call<CategoryModel> saveCategory(@Body CategoryModel categoryModel);

    @PUT("api/categories")
    Call<CategoryModel> updateCategory(@Body CategoryModel categoryModel);

    @DELETE("api/categories")
    Call<CategoryModel> deleteCategory(@Field("categoryId") Integer categoryId);
}
