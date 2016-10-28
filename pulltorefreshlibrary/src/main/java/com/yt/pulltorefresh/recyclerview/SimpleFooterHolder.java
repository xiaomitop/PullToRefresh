package com.yt.pulltorefresh.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yt.pulltorefresh.i.IRefreshFooter;
import com.yt.pulltorefresh.utils.State;

/**
 * 功能：
 * 作者：yangtao
 * 创建时间：2016/10/27 17:19
 */
public class SimpleFooterHolder extends RecyclerView.ViewHolder {
    private IRefreshFooter iRefreshFooter;

    public SimpleFooterHolder(View itemView) {
        super(itemView);
        iRefreshFooter = (IRefreshFooter) itemView;
    }

    //根据传过来的status控制哪个状态可见
    public void setData(State status) {
        switch (status) {
            case RESET:
                iRefreshFooter.reset();
                break;
            case LOADING:
                iRefreshFooter.refreshing();
                break;
            case COMPLETE:
                iRefreshFooter.complete();
                break;
            default:
                break;
        }
    }
}

