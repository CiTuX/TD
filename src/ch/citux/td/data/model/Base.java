package ch.citux.td.data.model;

import java.io.Serializable;

import ch.citux.td.TDApplication;

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
