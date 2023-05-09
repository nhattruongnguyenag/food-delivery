package vn.tdc.edu.fooddelivery.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    public static String convertDateToString(Date date) {
        if (date != null) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(date);
        }
        return "";
    }
}
