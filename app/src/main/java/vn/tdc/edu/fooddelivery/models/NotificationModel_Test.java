package vn.tdc.edu.fooddelivery.models;

public class NotificationModel_Test {
    private int img;
    private String title;
    private int time;
    private String content;

    public NotificationModel_Test(int img, String title, int time, String content) {
        this.img = img;
        this.title = title;
        this.time = time;
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "img=" + img +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                '}';
    }
}
