package vn.tdc.edu.fooddelivery.models;

import com.google.gson.annotations.SerializedName;

public class FileModel {
    @SerializedName("result")
    public String result;

    public FileModel() {
    }

    public FileModel(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
