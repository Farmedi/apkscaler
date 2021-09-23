package iits.code.dto;

public class SMS_DTO {
    private int direction = -1;
    private long id = 0;
    private String message = "";
    private String name = "";
    private String number = "";
    private long time = 0;

    public void setID(long id2) {
        this.id = id2;
    }

    public long getID() {
        return this.id;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public long getTime() {
        return this.time;
    }

    public void setDirection(int direction2) {
        this.direction = direction2;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setNumber(String number2) {
        this.number = number2;
    }

    public String getNumber() {
        return this.number;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }
}
