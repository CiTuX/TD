package ch.citux.twitchdroid.ui;

import android.os.Bundle;
import ch.citux.twitchdroid.R;
import ch.citux.twitchdroid.data.requestmanager.TwitchRequestManager;
import ch.citux.twitchdroid.ui.dialogs.ErrorDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.foxykeep.datadroid.requestmanager.Request;

import java.util.ArrayList;

public class TDActivity extends SherlockFragmentActivity {

    private static final String SAVED_STATE_REQUEST_LIST = "savedStateRequestList";

    protected TwitchRequestManager requestManager;
    protected ArrayList<Request> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestManager = TwitchRequestManager.from(this);
        if (savedInstanceState != null) {
            requestList = savedInstanceState.getParcelableArrayList(SAVED_STATE_REQUEST_LIST);
        } else {
            requestList = new ArrayList<Request>();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(SAVED_STATE_REQUEST_LIST, requestList);
    }

    protected void showBadDataErrorDialog() {
        new ErrorDialogFragment.ErrorDialogFragmentBuilder(this).setTitle(R.string.dialog_error_data_error_title)
                .setMessage(R.string.dialog_error_data_error_message).show();
    }

}
