package vn.tdc.edu.fooddelivery.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import vn.tdc.edu.fooddelivery.models.CategoryModel_Hanh;

public interface CategoryAPI {
    @GET("api/categories")
    Call<List<CategoryModel_Hanh>> getCategories();

    @POST("api/categories")
    Call<CategoryModel_Hanh> saveCategory(@Body CategoryModel_Hanh categoryModel);

    @PUT("api/categories")
    Call<CategoryModel_Hanh> updateCategory(@Body CategoryModel_Hanh categoryModel);

    @DELETE("api/categories")
    Call<CategoryModel_Hanh> deleteCategory(@Field("categoryId") Integer categoryId);
}
