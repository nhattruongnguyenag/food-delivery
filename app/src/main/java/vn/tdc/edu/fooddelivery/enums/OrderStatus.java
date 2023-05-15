package vn.tdc.edu.fooddelivery.enums;

public enum OrderStatus {
    CHUA_XU_LY(0),
    DANG_GIAO_HANG(1),
    THANH_CONG(2),
    THAT_BAI(3);

    private int status;

    OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
