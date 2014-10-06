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
package ch.citux.td.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

public class GridView extends org.holoeverywhere.widget.GridView implements AbsListView.OnScrollListener {

    private OnLastItemVisibleListener onLastItemVisibleListener;
    private int itemCount;
    private boolean notified;

    public GridView(Context context) {
        super(context);
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnLastItemVisibleListener(OnLastItemVisibleListener onLastItemVisibleListener) {
        this.onLastItemVisibleListener = onLastItemVisibleListener;
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount > 0 && totalItemCount == firstVisibleItem + visibleItemCount && onLastItemVisibleListener != null) {
            if (!notified || itemCount != totalItemCount) {
                itemCount = totalItemCount;
                onLastItemVisibleListener.onLastItemVisible();
                notified = true;
            }
        }
    }

    public interface OnLastItemVisibleListener {
        public void onLastItemVisible();
    }
}
