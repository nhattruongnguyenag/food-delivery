package vn.tdc.edu.fooddelivery.enums;

public enum Role {
    ADMIN("admin"),
    SHIPPER("shipper"),
    CUSTOMER("customer");

    private String role;

    Role(String name) {
        this.role = name;
    }

    public String getName() {
        return role;
    }
}
