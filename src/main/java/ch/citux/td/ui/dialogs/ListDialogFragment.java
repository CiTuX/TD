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

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListAdapter;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.DialogFragment;


public class ListDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    private static final String FRAGMENT_TAG = "ListDialogFragment";

    private DialogInterface.OnClickListener mOnClickListener;
    private OnCancelListener mOnCancelListener;
    private ListAdapter mAdapter;
    private String mTitle;


    private static ListDialogFragment newInstance(String title,
                                                  ListAdapter adapter,
                                                  DialogInterface.OnClickListener onClickListener,
                                                  OnCancelListener onCancelListener) {

        ListDialogFragment dialogFragment = new ListDialogFragment();
        dialogFragment.mOnClickListener = onClickListener;
        dialogFragment.mOnCancelListener = onCancelListener;
        dialogFragment.mAdapter = adapter;
        dialogFragment.mTitle = title;

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
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setAdapter(mAdapter, mOnClickListener)
                .setBlockDismiss(true)
                .setCancelable(false)
                .setOnCancelListener(this)
                .setNeutralButton(getActivity().getString(android.R.string.ok), mOnClickListener);
        setCancelable(false);
        return b.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mOnCancelListener != null) {
            mOnCancelListener.onCancel();
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    public interface OnCancelListener {
        public void onCancel();
    }

    public static class ListDialogFragmentBuilder {
        private FragmentActivity mActivity;
        private String mTitle;
        private ListAdapter mAdapter;
        private DialogInterface.OnClickListener mOnClickListener;
        private OnCancelListener mOnCancelListener;

        public ListDialogFragmentBuilder(FragmentActivity activity) {
            mActivity = activity;
            mTitle = "";
        }

        public ListDialogFragmentBuilder setTitle(int resId) {
            mTitle = mActivity.getString(resId);
            return this;
        }

        public ListDialogFragmentBuilder setTitle(String text) {
            mTitle = text;
            return this;
        }

        public ListDialogFragmentBuilder setAdapter(ListAdapter adapter) {
            mAdapter = adapter;
            return this;
        }

        public ListDialogFragmentBuilder setOnClickListener(DialogInterface.OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
            return this;
        }

        public ListDialogFragmentBuilder setOnCancelListener(OnCancelListener onCancelListener) {
            mOnCancelListener = onCancelListener;
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

            ListDialogFragment.newInstance(mTitle, mAdapter, mOnClickListener, mOnCancelListener)
                    .show(fragmentManager, FRAGMENT_TAG);
        }
    }
}
