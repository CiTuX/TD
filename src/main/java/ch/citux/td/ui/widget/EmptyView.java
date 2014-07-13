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
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ch.citux.td.R;

public class EmptyView extends FrameLayout {

    private ProgressBar progress;
    private TextView text;

    public EmptyView(Context context) {
        super(context);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.list_empty, this);
        progress = (ProgressBar) findViewById(R.id.progress);
        text = (TextView) findViewById(R.id.text);
    }

    public void showText() {
        progress.setVisibility(GONE);
        text.setVisibility(VISIBLE);
    }

    public void showProgress() {
        progress.setVisibility(VISIBLE);
        text.setVisibility(GONE);
    }

    public void setText(String message) {
        text.setText(message);
        showText();
    }

    public void setText(int messageId) {
        text.setText(messageId);
        showText();
    }
}
