package com.yt.pulltorefresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yt.pulltorefresh.R;
import com.yt.pulltorefresh.i.IRefreshFooter;
import com.yt.pulltorefresh.i.callback.OnClickToLoadMoreListener;

/**
 * 功能：
 * 作者：yangtao
 * 创建时间：2016/10/28 10:05
 */
public class SimpleFooterView extends FrameLayout implements IRefreshFooter, View.OnClickListener {
    private boolean isLoading = false;
    private TextView text;
    private ProgressBar progressBar;
    private OnClickToLoadMoreListener clickToLoadMoreListener;

    public SimpleFooterView(Context context) {
        super(context);
        init(context);
    }

    public SimpleFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.simple_footer, this);
        text = (TextView) findViewById(R.id.text);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.setOnClickListener(this);
    }

    @Override
    public void refreshing() {
        isLoading = true;
        if (progressBar != null && progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(VISIBLE);
        }
        text.setText(getResources().getText(R.string.header_refreshing));
    }

    @Override
    public void complete() {
        onWaitToLoadMore();
    }

    @Override
    public void onWaitToLoadMore() {
        isLoading = false;
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(INVISIBLE);
        }
        text.setText(getResources().getText(R.string.click_to_load_more));
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public void setClickToLoadMoreListener(OnClickToLoadMoreListener clickToLoadMoreListener) {
        this.clickToLoadMoreListener = clickToLoadMoreListener;
    }

    @Override
    public void onClick(View view) {
        if (text.getText().toString().equals(getResources().getText(R.string.click_to_load_more))) {
            if (clickToLoadMoreListener != null) {
                clickToLoadMoreListener.clickToLoadMore();
            }
        }
    }
}
