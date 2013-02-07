package ch.citux.twitchdroid.data.worker;

import ch.citux.twitchdroid.ui.fragments.TDFragment;

public abstract class TDBasicCallback<Result> implements TDCallback<Result> {

    private Object caller;

    protected TDBasicCallback(Object caller) {
        this.caller = caller;
    }

    @Override
    public void startLoading() {
        if (caller instanceof TDFragment) {
            ((TDFragment) caller).startLoading();
        }
    }

    @Override
    public void stopLoading() {
        if (caller instanceof TDFragment) {
            ((TDFragment) caller).stopLoading();
        }
    }

    @Override
    public void onError(String title, String message) {
        if (caller instanceof TDFragment) {
            ((TDFragment) caller).onError(title, message);
        }
    }
}
