package com.yt.pulltorefresh.i;

/**
 * 功能：
 * 作者：yangtao
 * 创建时间：2016/10/28 09:48
 */
public interface IRefreshFooter {
    /**
     * 松手，头部隐藏后会回调这个方法
     */
    void reset();

    /**
     * 正在刷新的时候调用
     */
    void refreshing();

    /**
     * 刷新成功的时候调用
     */
    void complete();
}
