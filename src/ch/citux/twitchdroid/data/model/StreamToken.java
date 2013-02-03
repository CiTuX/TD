package ch.citux.twitchdroid.data.model;

public class StreamToken {

    private String token;
    private long sleep_time;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getSleep_time() {
        return sleep_time;
    }

    public void setSleep_time(long sleep_time) {
        this.sleep_time = sleep_time;
    }
}
