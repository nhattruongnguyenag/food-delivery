package vn.tdc.edu.fooddelivery.utils;

import android.app.Activity;

import vn.tdc.edu.fooddelivery.dal.DatabaseLayer;
import vn.tdc.edu.fooddelivery.models.UserModel;

public class Authentication {
    private static Activity context;

    private static DatabaseLayer dal;
    public static boolean login(UserModel userModel) {
        return dal.savePerson(userModel);
    }

    public static void setContext(Activity context) {
        Authentication.context = context;
        if (dal == null) {
            dal = new DatabaseLayer(context);
        }
    }

    public static UserModel getUserLogin() {
        return dal.getPerson();
    }

    public static boolean logout() {
        return dal.deletePerson(dal.getPerson());
    }
}
