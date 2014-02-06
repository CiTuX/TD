package ch.citux.td.data.model;

public class Logo extends Base {

    public static final String HUGE = "-600x600";
    public static final String LARGE = "-300x300";
    public static final String MEDIUM = "-150x150";
    public static final String SMALL = "-70x70";
    public static final String TINY = "-50x50";

    public static final String[] SIZES = {HUGE, LARGE, MEDIUM, SMALL, TINY};

    private String url;
    private String size;

    public Logo(String url, String size) {
        this.url = url;
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
