package iits.code.dto;

public class Link_DTO {
    private long id;
    private String name;
    private String url;

    public Link_DTO() {
        setID(0);
        setName("");
        setURL("");
    }

    public void setID(long id2) {
        this.id = id2;
    }

    public long getID() {
        return this.id;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }

    public void setURL(String url2) {
        this.url = url2;
    }

    public String getURL() {
        return this.url;
    }
}
