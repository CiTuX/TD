package ch.citux.td.data.dto;

public class TwitchAccessToken {

    private String token;
    private String sig;
    private boolean mobile_restricted;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public boolean isMobile_restricted() {
        return mobile_restricted;
    }

    public void setMobile_restricted(boolean mobile_restricted) {
        this.mobile_restricted = mobile_restricted;
    }
}
