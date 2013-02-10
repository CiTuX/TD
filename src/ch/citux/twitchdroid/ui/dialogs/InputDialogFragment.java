
package ch.citux.twitchdroid.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import ch.citux.twitchdroid.R;


public class InputDialogFragment extends DialogFragment implements TextView.OnEditorActionListener, DialogInterface.OnClickListener, View.OnClickListener {

    public interface OnCancelListener {
        public void onCancel();
    }

    public interface OnDoneListener {
        void onFinishInputDialog(String inputText);
    }

    private static final String FRAGMENT_TAG = "InputDialogFragment";

    private static final String BUNDLE_TITLE = "title";
    private static final String BUNDLE_HINT = "hint";
    private static final String BUNDLE_TEXT = "text";

    private OnClickListener mOnClickListener;
    private OnCancelListener mOnCancelListener;
    private OnDoneListener mOnDoneListener;
    private EditText txtInput;

    private static InputDialogFragment newInstance(String title,
                                                   String hint,
                                                   String text,
                                                   OnClickListener onClickListener,
                                                   OnCancelListener onCancelListener,
                                                   OnDoneListener onDoneListener) {

        InputDialogFragment dialogFragment = new InputDialogFragment();
        dialogFragment.mOnClickListener = onClickListener;
        dialogFragment.mOnCancelListener = onCancelListener;
        dialogFragment.mOnDoneListener = onDoneListener;

        Bundle args = new Bundle();
        args.putString(BUNDLE_TITLE, title);
        args.putString(BUNDLE_HINT, hint);
        args.putString(BUNDLE_TEXT, text);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        final String text = args.getString(BUNDLE_TEXT);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_input, null);
        txtInput = (EditText) view.findViewById(R.id.txtInput);
        txtInput.setHint(args.getString(BUNDLE_HINT));
        txtInput.setText(text);
        txtInput.setOnEditorActionListener(this);
        txtInput.post(new Runnable() {
            @Override
            public void run() {
                txtInput.setSelection(text.length());
            }
        });
        view.findViewById(R.id.btnClear).setOnClickListener(this);
        builder.setView(view);
        builder.setTitle(args.getString(BUNDLE_TITLE));
        builder.setNeutralButton(getActivity().getString(android.R.string.ok), this);
        builder.setCancelable(true);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(dialog, which);
        } else {
            mOnDoneListener.onFinishInputDialog(txtInput.getText().toString());
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        txtInput.setText("");
        txtInput.requestFocus();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (mOnDoneListener != null) {
            if (EditorInfo.IME_ACTION_DONE == actionId) {
                // Return input text to activity
                mOnDoneListener.onFinishInputDialog(txtInput.getText().toString());
                this.dismiss();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mOnCancelListener != null) {
            mOnCancelListener.onCancel();
        }
    }

    public void onClearText(View view) {
        txtInput.setText("");
    }

    public static class InputDialogFragmentBuilder {
        private FragmentActivity mActivity;
        private String mTitle;
        private String mHint;
        private String mText;
        private OnClickListener mOnClickListener;
        private OnCancelListener mOnCancelListener;
        private OnDoneListener mOnDoneListener;

        public InputDialogFragmentBuilder(FragmentActivity activity) {
            mActivity = activity;
        }

        public InputDialogFragmentBuilder setTitle(int resId) {
            mTitle = mActivity.getString(resId);
            return this;
        }

        public InputDialogFragmentBuilder setTitle(String text) {
            mTitle = text;
            return this;
        }

        public InputDialogFragmentBuilder setHint(int resId) {
            mHint = mActivity.getString(resId);
            return this;
        }

        public InputDialogFragmentBuilder setHint(String text) {
            mHint = text;
            return this;
        }

        public InputDialogFragmentBuilder setText(int resId) {
            mText = mActivity.getString(resId);
            return this;
        }

        public InputDialogFragmentBuilder setText(String text) {
            mText = text;
            return this;
        }

        public InputDialogFragmentBuilder setOnClickListener(OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
            return this;
        }

        public InputDialogFragmentBuilder setOnCancelListener(OnCancelListener onCancelListener) {
            mOnCancelListener = onCancelListener;
            return this;
        }

        public InputDialogFragmentBuilder setOnDoneListener(OnDoneListener mOnDoneListener) {
            this.mOnDoneListener = mOnDoneListener;
            return this;
        }

        public void show() {
            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Fragment prev = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
            if (prev != null) {
                fragmentTransaction.remove(prev);
            }
            fragmentTransaction.addToBackStack(null);

            InputDialogFragment.newInstance(mTitle, mHint, mText, mOnClickListener, mOnCancelListener, mOnDoneListener)
                    .show(fragmentManager, FRAGMENT_TAG);
        }
    }

    public static void dismiss(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment prev = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.commit();
    }
}
