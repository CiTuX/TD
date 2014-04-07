package ch.citux.td.data.model;

import java.util.ArrayList;

public class Channel extends Base {

    private int id;
    private ArrayList<Logo> logos;
    private String name;
    private Status status;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo(String size) {
        if (logos != null) {
            for (Logo logo : logos) {
                if (logo.getSize().equals(size)) {
                    return logo.getUrl();
                }
            }
        }
        return null;
    }

    public ArrayList<Logo> getLogos() {
        return logos;
    }

    public void setLogos(ArrayList<Logo> logos) {
        this.logos = logos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
