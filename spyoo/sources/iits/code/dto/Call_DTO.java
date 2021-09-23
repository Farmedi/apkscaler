package iits.code.dto;

public class Call_DTO {
    private int direction = -1;
    private int duration = 0;
    private long id = 0;
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

    public void setDuration(int duration2) {
        this.duration = duration2;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }
}
