package vn.tdc.edu.fooddelivery.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class ProductModel extends BaseModel {
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("price")
    private Long price;
    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private String unit;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
