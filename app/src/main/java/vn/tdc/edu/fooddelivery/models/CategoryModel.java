package vn.tdc.edu.fooddelivery.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import vn.tdc.edu.fooddelivery.constant.SystemConstant;

public class CategoryModel extends BaseModel {
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;

    @SerializedName("numberOfProduct")
    private Integer numberOfProduct;

    public String getImageUrl() {
        Log.d("image-url", "url " + SystemConstant.IMAGES_BASE_URL + name);
        return SystemConstant.IMAGES_BASE_URL + image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return image;
    }

    public void setImageName(String image) {
        this.image = image;
    }

    public Integer getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(Integer numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }
}
