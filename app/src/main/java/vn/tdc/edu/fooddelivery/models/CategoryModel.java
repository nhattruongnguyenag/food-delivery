package vn.tdc.edu.fooddelivery.models;

import com.google.gson.annotations.SerializedName;

public class CategoryModel extends BaseModel{
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
