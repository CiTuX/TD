package ch.citux.twitchdroid.data.model;

public class StreamQuality extends Base {

    private String key;
    private String name;
    private int value;

    public StreamQuality(String key, int value) {
        this.value = value;
        this.name = key;
        this.key = key;
    }

    public StreamQuality(String key, String name, int value) {
        this.key = key;
        this.name = name;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
