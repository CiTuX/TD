package ch.citux.twitchdroid.data.worker;

public interface TwitchDroidCallback<Result> {

    public void onResponse(Result response);

    public void onError(String title, String message);

}
