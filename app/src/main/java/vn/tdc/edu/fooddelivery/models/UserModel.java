package vn.tdc.edu.fooddelivery.models;

import com.google.gson.annotations.SerializedName;

public class UserModel extends BaseModel {
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("image")
    private String image;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("status")
    private Byte status;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
