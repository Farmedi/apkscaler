package iits.code.dto;

public class Account_DTO {
    private String password = "";
    private boolean rememberLogin = false;
    private String serialMachine = "";
    private long userid = 0;
    private String username = "";

    public void setUserid(long userid2) {
        this.userid = userid2;
    }

    public long getUserid() {
        return this.userid;
    }

    public void setUsername(String username2) {
        this.username = username2;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setSerialMachine(String serialMachine2) {
        this.serialMachine = serialMachine2;
    }

    public String getSerialMachine() {
        return this.serialMachine;
    }

    public void setRememberLogin(boolean rememberLogin2) {
        this.rememberLogin = rememberLogin2;
    }

    public boolean getRememberLogin() {
        return this.rememberLogin;
    }
}
