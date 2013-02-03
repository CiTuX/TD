package ch.citux.twitchdroid.data.model;

public class StreamToken extends Base {

    private String token;
    private double sleep_time;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getSleep_time() {
        return sleep_time;
    }

    public void setSleep_time(double sleep_time) {
        this.sleep_time = sleep_time;
    }
}
