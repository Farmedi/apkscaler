package iits.code.dto;

public class URL_DTO {
    private int bookMark = 0;
    private long id = 0;
    private long time = 0;
    private String title = "";
    private String url = "";

    public void setID(long id2) {
        this.id = id2;
    }

    public long getID() {
        return this.id;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public long getTime() {
        return this.time;
    }

    public void setURL(String url2) {
        this.url = url2;
    }

    public String getURL() {
        return this.url;
    }

    public void setBookMark(int bookMark2) {
        this.bookMark = bookMark2;
    }

    public int getBookMark() {
        return this.bookMark;
    }
}
