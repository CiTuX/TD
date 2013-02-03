package ch.citux.twitchdroid.data.model;

public abstract class Base {

    private String error;
    private int errorResId;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public void setErrorResId(int errorResId) {
        this.errorResId = errorResId;
    }

    public boolean hasError() {
        return error != null || errorResId != 0;
    }
}
