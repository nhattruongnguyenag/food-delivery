package vn.tdc.edu.fooddelivery.utils;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.enums.Role;
import vn.tdc.edu.fooddelivery.models.UserModel;

public class Authentication {

    public static boolean login(UserModel userModel) {
        return true;
    }

    public static UserModel getUserLogin() {
        UserModel userModel = new UserModel();
        userModel.setId(4);
        userModel.setImageName("user_image_default.png");
        userModel.setFullName("Nguyen Van A (NV)");
        userModel.setEmail("nguyenvana@gmail.com");
        ArrayList<String> role = new ArrayList<>();
        role.add(Role.ADMIN.getName());
        userModel.setRoleCodes(role);
        return userModel;
    }

    public static boolean logout() {
        return true;
    }
}
