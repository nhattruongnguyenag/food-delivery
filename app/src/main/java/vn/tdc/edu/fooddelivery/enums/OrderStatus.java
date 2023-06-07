package vn.tdc.edu.fooddelivery.enums;

public enum OrderStatus {
    CHUA_XU_LY(1, "Chưa xử lý"),
    DANG_GIAO_HANG(2,"Đang giao hàng"),
    THANH_CONG(3, "Giao thành công"),
    THAT_BAI(4, "Huỷ");

    private int status;

    private String name;

    OrderStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
