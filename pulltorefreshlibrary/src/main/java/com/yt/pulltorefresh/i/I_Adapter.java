package com.yt.pulltorefresh.i;


import com.yt.pulltorefresh.recyclerview.BaseAdapterHelper;

/**
 * ACCommonAdapter
 * 扩展的Adapter接口规范
 */
public interface I_Adapter<T> {

    void bindData(BaseAdapterHelper helper, int position, T item);
}
