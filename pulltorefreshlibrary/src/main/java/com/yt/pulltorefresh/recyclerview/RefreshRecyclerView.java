package com.yt.pulltorefresh.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yt.pulltorefresh.i.IRefreshFooter;
import com.yt.pulltorefresh.i.callback.OnClickToLoadMoreListener;
import com.yt.pulltorefresh.i.callback.OnLoadingListener;
import com.yt.pulltorefresh.utils.LayoutManagerType;
import com.yt.pulltorefresh.view.SimpleFooterView;

/**
 * 功能：
 * 作者：yangtao
 * 创建时间：2016/11/1 11:03
 */
public class RefreshRecyclerView extends RecyclerView {
    private Context context;
    //是否自动刷新
    private boolean isAutoLoadMore = false;
    private RefreshRecyclerAdapter refreshRecyclerAdapter;
    private IRefreshFooter footerView;
    private OnLoadingListener loadingListener;

    public RefreshRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setAdapter(RefreshRecyclerAdapter adapter) {
        refreshRecyclerAdapter = adapter;
        if (footerView == null) {
            footerView = new SimpleFooterView(context);
        }
        footerView.setClickToLoadMoreListener(new OnClickToLoadMoreListener() {
            @Override
            public void clickToLoadMore() {
                footerView.refreshing();
                if (loadingListener != null) {
                    loadingListener.loading();
                }
            }
        });
        refreshRecyclerAdapter.addFooterView((View) footerView);
        super.setAdapter(refreshRecyclerAdapter);
    }

    private void init(Context context) {
        this.context = context;
        //setInternalOnScrollListener();
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layout;
            final int spanCount = gridLayoutManager.getSpanCount();
            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //如果是最后一个item，则设置占据spanCount列，否则占据1列
                    boolean isFooter = position == refreshRecyclerAdapter.getItemCount() - 1;
                    return isFooter ? spanCount : 1;
                }
            });
            super.setLayoutManager(gridLayoutManager);
        }
    }



    private LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.STAGGERED_GRID;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if ((visibleItemCount > 0 && !footerView.isLoading()
                && state == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - 1)) {
            Log.e("ssdsd", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            if (isAutoLoadMore) { // 如果mAutoLoadMore被设置为true
                footerView.refreshing();
                if (loadingListener != null) {
                    loadingListener.loading();
                }
            } else {
                footerView.onWaitToLoadMore(); // 显示”点击加载更多“
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void setOnLoadingListener(OnLoadingListener loadingListener) {
        this.loadingListener = loadingListener;
    }

    public void complete(){
        footerView.complete();
    }
}
