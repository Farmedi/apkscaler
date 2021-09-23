package iits.code.dto;

public class GPS_DTO {
    private String altitude;
    private long id;
    private String latitude;
    private String longitude;
    private long time;

    public GPS_DTO() {
        this.id = 0;
        this.time = 0;
        this.latitude = "";
        this.longitude = "";
        this.altitude = "";
    }

    public GPS_DTO(GPS_DTO gps) {
        this.id = gps.id;
        this.time = gps.time;
        this.latitude = gps.latitude;
        this.longitude = gps.longitude;
        this.altitude = gps.altitude;
    }

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

    public void setLatitude(String latitude2) {
        this.latitude = latitude2;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLongitude(String longitude2) {
        this.longitude = longitude2;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setAltitude(String altitude2) {
        this.altitude = altitude2;
    }

    public String getAltitude() {
        return this.altitude;
    }
}
