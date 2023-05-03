package vn.tdc.edu.fooddelivery.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import vn.tdc.edu.fooddelivery.models.CategoryModel;
import vn.tdc.edu.fooddelivery.models.FileModel;

public interface UploadAPI {
    @Multipart
    @POST("api/upload-image")
    Call<FileModel> callUploadApi(@Part MultipartBody.Part image);
}
