package ch.citux.twitchdroid.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import ch.citux.twitchdroid.R;

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

    private void showText() {
        progress.setVisibility(GONE);
        text.setVisibility(VISIBLE);
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
