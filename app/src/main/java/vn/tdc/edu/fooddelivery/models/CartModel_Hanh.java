package vn.tdc.edu.fooddelivery.models;

import java.io.Serializable;

public class CartModel_Hanh implements Serializable {
    private String name;
    private int qty;
    private int price;
    private int img;
    private int rate;
    private String description;

    public CartModel_Hanh(String name, int qty, int price, int img, int rate, String description) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.img = img;
        this.rate = rate;
        this.description = description;
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
                "name='" + name + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", img=" + img +
                ", rate=" + rate +
                ", description='" + description + '\'' +
                '}';
    }
}
