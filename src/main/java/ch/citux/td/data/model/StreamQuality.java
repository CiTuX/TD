package ch.citux.td.data.model;

public class StreamQuality extends Base {

    private String name;
    private int value;

    public StreamQuality(String key, int value) {
        this.value = value;
        this.name = key;
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
