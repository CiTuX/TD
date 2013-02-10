package ch.citux.twitchdroid.data.model;

import ch.citux.twitchdroid.TDApplication;

import java.io.Serializable;

public abstract class Base implements Serializable {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setErrorResId(int errorResId) {
        this.error = TDApplication.getContext().getString(errorResId);
    }

    public boolean hasError() {
        return error != null;
    }
}
