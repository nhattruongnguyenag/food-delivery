package vn.tdc.edu.fooddelivery.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.tdc.edu.fooddelivery.constant.SystemConstant;

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
    @SerializedName("roleIds")
    private List<Integer> roleIds;

    @SerializedName("roleCodes")
    private List<String> roleCodes;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageName() {
        return image;
    }

    public void setImageUrl(String image) {this.image = image;}

    public void setImageName(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return SystemConstant.IMAGES_BASE_URL + image;
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

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    @NonNull
    @Override
    public String toString() {
        return fullName;
    }
}
