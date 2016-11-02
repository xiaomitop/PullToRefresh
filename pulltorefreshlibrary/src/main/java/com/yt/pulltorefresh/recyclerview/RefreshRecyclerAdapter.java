package com.yt.pulltorefresh.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import com.yt.pulltorefresh.i.I_Adapter;
import com.yt.pulltorefresh.i.I_Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * 作者：yangtao
 * 创建时间：2016/10/28 16:34
 */
public abstract class RefreshRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder>
        implements I_Data<T>, I_Adapter<T> {
    private final int NORMALLAYOUT = 0;
    private final int FOOTERLAYOUT = 1;
    protected final Context context;
    protected final List<T> data;
    protected final int layoutResId;
    private View footerView;

    public abstract int getItemLayoutId();

    public RefreshRecyclerAdapter(Context context, List<T> data) {
        this.data = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.context = context;
        this.layoutResId = getItemLayoutId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMALLAYOUT) {
            final BaseAdapterHelper helper = BaseAdapterHelper.get(context, null, parent, layoutResId, -1);
            return new RecyclerViewHolder(helper.getView(), helper);
        } else {
            return new SimpleFooterHolder(footerView);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof SimpleFooterHolder) {
            return;
        }
        BaseAdapterHelper helper = ((RecyclerViewHolder) holder).adapterHelper;
        helper.setAssociatedObject(getItem(position));
        bindData(helper, position, getItem(position));
    }

    public T getItem(int position) {
        return position >= data.size() ? null : data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == data.size())
            return FOOTERLAYOUT;
        else
            return NORMALLAYOUT;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        BaseAdapterHelper adapterHelper;

        public RecyclerViewHolder(View itemView, BaseAdapterHelper adapterHelper) {
            super(itemView);
            this.adapterHelper = adapterHelper;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener) {
                        onItemClickListener.onItemClick(RecyclerViewHolder.this, v, getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (null != onItemLongClickListener) {
                        onItemLongClickListener.onItemLongClick(RecyclerViewHolder.this, v, getAdapterPosition());
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ViewHolder viewHolder, View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(ViewHolder viewHolder, View view, int position);
    }


    public void addFooterView(View view){
        footerView = view;
    }

    @Override
    public void add(T elem) {
        data.add(elem);
        notifyItemInserted(data.size());
    }

    @Override
    public void addAll(List<T> elem) {
        data.addAll(elem);
        notifyItemRangeInserted(data.size() - elem.size(), elem.size());
    }

    @Override
    public void set(T oldElem, T newElem) {
        set(data.indexOf(oldElem), newElem);
    }

    @Override
    public void set(int index, T elem) {
        data.set(index, elem);
        notifyItemChanged(index);
    }

    @Override
    public void remove(T elem) {
        final int position = data.indexOf(elem);
        data.remove(elem);
        notifyItemRemoved(position);
    }

    @Override
    public void remove(int index) {
        data.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public void replaceAll(List<T> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }

    @Override
    public boolean contains(T elem) {
        return data.contains(elem);
    }

    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }
}
