package vn.tdc.edu.fooddelivery.enums;

public enum OrderStatus {
    CHUA_XU_LY(1),
    DANG_GIAO_HANG(2),
    THANH_CONG(3),
    THAT_BAI(4);

    private int status;

    OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
