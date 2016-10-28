package com.yt.pulltorefresh.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yt.pulltorefresh.R;
import com.yt.pulltorefresh.i.IRefreshFooter;

/**
 * 功能：
 * 作者：yangtao
 * 创建时间：2016/10/28 10:05
 */
public class SimpleFooterView extends FrameLayout implements IRefreshFooter {

    private RelativeLayout relativeLayout;
    private TextView text;
    private ProgressBar progressBar;
    private View successIcon;


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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimpleFooterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.simple_footer, this);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        text = (TextView) findViewById(R.id.text);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        successIcon = findViewById(R.id.successIcon);
    }

    @Override
    public void reset() {
        if (relativeLayout != null && relativeLayout.getVisibility() == View.VISIBLE){
            relativeLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void refreshing() {
        if (relativeLayout != null && relativeLayout.getVisibility() == View.GONE){
            relativeLayout.setVisibility(View.VISIBLE);
        }
        if (successIcon != null && successIcon.getVisibility() == View.VISIBLE) {
            successIcon.setVisibility(INVISIBLE);
        }
        if (progressBar != null && progressBar.getVisibility() == View.INVISIBLE){
            progressBar.setVisibility(VISIBLE);
        }
        text.setText(getResources().getText(R.string.header_refreshing));
    }

    @Override
    public void complete() {
        if (relativeLayout != null && relativeLayout.getVisibility() == View.GONE){
            relativeLayout.setVisibility(View.VISIBLE);
        }
        if (successIcon != null && successIcon.getVisibility() == View.INVISIBLE) {
            successIcon.setVisibility(VISIBLE);
        }
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(INVISIBLE);
        }
        text.setText(getResources().getText(R.string.header_completed));
    }
}
