package vn.tdc.edu.fooddelivery.models;

import java.io.Serializable;

public class ProductModel_Test implements Serializable {
    private  int _id;
    private int category_id;
    private String name;
    private int qty;
    private int price;
    private int img;
    private int rate;
    private String description;

    public ProductModel_Test(int id,int category_id, String name, int qty, int price, int img, int rate, String description) {
        this._id = id;
        this.category_id = category_id;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.img = img;
        this.rate = rate;
        this.description = description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Cart{" +
                " id=" + _id +
                " category_id=" + category_id +
                "name='" + name + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", img=" + img +
                ", rate=" + rate +
                ", description='" + description + '\'' +
                '}';
    }
}
