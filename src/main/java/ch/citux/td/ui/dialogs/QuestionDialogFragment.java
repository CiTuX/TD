/*
 * Copyright 2013-2014 Paul Stöhr
 *
 * This file is part of TD.
 *
 * TD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.citux.td.ui.dialogs;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public final class QuestionDialogFragment extends DialogFragment {

    private static final String FRAGMENT_TAG = "QuestionDialogFragment";

    private static final String BUNDLE_TITLE = "title";
    private static final String BUNDLE_MESSAGE = "message";
    private static final String BUNDLE_POSITIVE_BUTTON_TEXT = "positiveButtonText";
    private static final String BUNDLE_NEGATIVE_BUTTON_TEXT = "negativeButtonText";

    private OnClickListener mPositiveOnClickListener;
    private OnClickListener mNegativeOnClickListener;

    private static QuestionDialogFragment newInstance(String title, String message,
                                                      String positiveButtonText, OnClickListener positiveOnClickListener,
                                                      String negativeButtonText, OnClickListener negativeOnClickListener) {
        QuestionDialogFragment dialogFragment = new QuestionDialogFragment();

        Bundle args = new Bundle();
        args.putString(BUNDLE_TITLE, title);
        args.putString(BUNDLE_MESSAGE, message);
        args.putString(BUNDLE_POSITIVE_BUTTON_TEXT, positiveButtonText);
        args.putString(BUNDLE_NEGATIVE_BUTTON_TEXT, negativeButtonText);
        dialogFragment.setArguments(args);

        dialogFragment.mPositiveOnClickListener = positiveOnClickListener;
        dialogFragment.mNegativeOnClickListener = negativeOnClickListener;
        return dialogFragment;
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        Builder b = new Builder(getActivity());
        b.setTitle(args.getString(BUNDLE_TITLE));
        b.setMessage(args.getString(BUNDLE_MESSAGE));
        b.setIcon(android.R.drawable.ic_dialog_alert);
        setCancelable(true);
        b.setPositiveButton(args.getString(BUNDLE_POSITIVE_BUTTON_TEXT), mPositiveOnClickListener);
        b.setNegativeButton(args.getString(BUNDLE_NEGATIVE_BUTTON_TEXT), mNegativeOnClickListener);
        return b.create();
    }

    public static class QuestionDialogFragmentBuilder {

        private FragmentActivity mActivity;
        private String mTitle;
        private String mMessage;
        private String mPositiveButtonText;
        private OnClickListener mPositiveButtonOnClickListener;
        private String mNegativeButtonText;
        private OnClickListener mNegativeButtonOnClickListener;

        public QuestionDialogFragmentBuilder(FragmentActivity activity) {
            mActivity = activity;

            mPositiveButtonText = activity.getString(android.R.string.yes);
            mNegativeButtonText = activity.getString(android.R.string.no);
        }

        public QuestionDialogFragmentBuilder setTitle(int resId) {
            mTitle = mActivity.getString(resId);
            return this;
        }

        public QuestionDialogFragmentBuilder setTitle(String text) {
            mTitle = text;
            return this;
        }

        public QuestionDialogFragmentBuilder setMessage(int resId) {
            mMessage = mActivity.getString(resId);
            return this;
        }

        public QuestionDialogFragmentBuilder setMessage(String text) {
            mMessage = text;
            return this;
        }

        public QuestionDialogFragmentBuilder setPositiveButton(int resId,
                                                               OnClickListener onClickListener) {
            return setPositiveButton(mActivity.getString(resId), onClickListener);
        }

        public QuestionDialogFragmentBuilder setPositiveButton(String text,
                                                               OnClickListener onClickListener) {
            mPositiveButtonText = text;
            mPositiveButtonOnClickListener = onClickListener;
            return this;
        }

        public QuestionDialogFragmentBuilder setNegativeButton(int resId,
                                                               OnClickListener onClickListener) {
            return setNegativeButton(mActivity.getString(resId), onClickListener);
        }

        public QuestionDialogFragmentBuilder setNegativeButton(String text,
                                                               OnClickListener onClickListener) {
            mNegativeButtonText = text;
            mNegativeButtonOnClickListener = onClickListener;
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

            QuestionDialogFragment.newInstance(mTitle, mMessage, mPositiveButtonText,
                    mPositiveButtonOnClickListener, mNegativeButtonText,
                    mNegativeButtonOnClickListener).show(fragmentManager, FRAGMENT_TAG);
        }

    }
}
