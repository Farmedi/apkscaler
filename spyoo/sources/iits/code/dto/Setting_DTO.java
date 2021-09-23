package iits.code.dto;

public class Setting_DTO {
    private boolean flagCall = false;
    private boolean flagContact = false;
    private boolean flagGPS = false;
    private boolean flagSMS = false;
    private boolean flagSpyCall = false;
    private boolean flagURL = false;
    private int gpsInterval = 0;
    private int horizontal = 0;
    private String link = "";
    private int reportTime = 0;
    private boolean runMode = false;
    private String secretKey = "";
    private boolean sendSetting = false;
    private int silent;
    private String spyCallNumber = "";
    private int vertical = 0;
    private int vibrate;

    public Setting_DTO() {
        setSilent(0);
        setVibrate(0);
    }

    public void setFlagSMS(boolean flagSMS2) {
        this.flagSMS = flagSMS2;
    }

    public boolean getFlagSMS() {
        return this.flagSMS;
    }

    public void setFlagCall(boolean flagCall2) {
        this.flagCall = flagCall2;
    }

    public boolean getFlagCall() {
        return this.flagCall;
    }

    public void setFlagGPS(boolean flagGPS2) {
        this.flagGPS = flagGPS2;
    }

    public boolean getFlagGPS() {
        return this.flagGPS;
    }

    public void setSendSetting(boolean sendSetting2) {
        this.sendSetting = sendSetting2;
    }

    public boolean getSendSetting() {
        return this.sendSetting;
    }

    public void setGPSInterval(int gpsInterval2) {
        this.gpsInterval = gpsInterval2;
    }

    public int getGPSInterval() {
        return this.gpsInterval;
    }

    public void setSecretKey(String secretKey2) {
        this.secretKey = secretKey2;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setRunMode(boolean runmode) {
        this.runMode = runmode;
    }

    public boolean getRunMode() {
        return this.runMode;
    }

    public void setFlagURL(boolean flagURL2) {
        this.flagURL = flagURL2;
    }

    public boolean getFlagURL() {
        return this.flagURL;
    }

    public void setFlagSpyCall(boolean flagSpyCall2) {
        this.flagSpyCall = flagSpyCall2;
    }

    public boolean getFlagSpyCall() {
        return this.flagSpyCall;
    }

    public void setFlagContact(boolean flagContact2) {
        this.flagContact = flagContact2;
    }

    public boolean getFlagContact() {
        return this.flagContact;
    }

    public void setReportTime(int reportTime2) {
        this.reportTime = reportTime2;
    }

    public int getReportTime() {
        return this.reportTime;
    }

    public void setVertical(int vertical2) {
        this.vertical = vertical2;
    }

    public int getVertical() {
        return this.vertical;
    }

    public void setHorizontal(int horizontal2) {
        this.horizontal = horizontal2;
    }

    public int getHorizontal() {
        return this.horizontal;
    }

    public void setSpyCallNumber(String spyCallNumber2) {
        this.spyCallNumber = spyCallNumber2;
    }

    public String getSpyCallNumber() {
        return this.spyCallNumber;
    }

    public void setLink(String link2) {
        this.link = link2;
    }

    public String getLink() {
        return this.link;
    }

    public void setSilent(int silent2) {
        this.silent = silent2;
    }

    public int getSilent() {
        return this.silent;
    }

    public void setVibrate(int vibrate2) {
        this.vibrate = vibrate2;
    }

    public int getVibrate() {
        return this.vibrate;
    }
}
