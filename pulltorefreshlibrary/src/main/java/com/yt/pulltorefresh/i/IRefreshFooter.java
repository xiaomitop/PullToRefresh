package com.yt.pulltorefresh.i;

import com.yt.pulltorefresh.i.callback.OnClickToLoadMoreListener;

/**
 * 功能：
 * 作者：yangtao
 * 创建时间：2016/10/28 09:48
 */
public interface IRefreshFooter {
    /**
     * 正在刷新的时候调用
     */
    void refreshing();

    /**
     * 刷新成功的时候调用
     */
    void complete();

    /**
     * 显示加载更多
     */
    void onWaitToLoadMore();

    /**
     * @return true 表示正在刷新
     */
    boolean isLoading();

    /**
     * 点击加载更多回调接口
     */
    void setClickToLoadMoreListener(OnClickToLoadMoreListener clickToLoadMoreListener);
}
