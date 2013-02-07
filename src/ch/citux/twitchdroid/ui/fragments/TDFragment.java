package ch.citux.twitchdroid.ui.fragments;

import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.worker.TDCallback;
import ch.citux.twitchdroid.util.Log;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public abstract class TDFragment<Result> extends SherlockListFragment implements TDCallback<Result> {

    private MenuItem refreshItem;
    private boolean optionsMenuCreated;
    private boolean hasRefresh;
    private boolean isLoading;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (hasRefresh) {
            inflater.inflate(R.menu.refresh, menu);
            refreshItem = menu.findItem(R.id.menu_refresh);
        }
        optionsMenuCreated = true;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (hasRefresh && item.getItemId() == R.id.menu_refresh) {
            if (!isLoading) {
                refreshData();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void addRefreshAction() {
        if (optionsMenuCreated) {
            Log.e(this, "addRefreshAction was called too late...");
        }
        setHasOptionsMenu(true);
        hasRefresh = true;
    }

    public void startLoading() {
        isLoading = true;
        if (refreshItem != null) {
            refreshItem.setActionView(R.layout.refresh_action);
        }
    }

    public void stopLoading() {
        isLoading = false;
        if (refreshItem != null) {
            refreshItem.setActionView(null);
        }
    }

    @Override
    public void onError(String title, String message) {
    }

    protected void loadData() {
    }

    protected final void refreshData() {
        loadData();
    }

}
