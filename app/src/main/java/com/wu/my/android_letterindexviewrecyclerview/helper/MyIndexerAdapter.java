package com.wu.my.android_letterindexviewrecyclerview.helper;

/***
 *自定义recyclerviewAdapter适配器工具类
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class MyIndexerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<T> list;
    public Context context;
    public LayoutInflater inflater;

    public MyIndexerAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateMyViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindMyViewHolder(holder, position);
    }

    public abstract RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindMyViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * @param position
     * @param data     添加单条数据
     */
    public void addItem(int position, T data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * @param position
     * @param _list    批量的添加的数据
     */
    public void addItems(int position, List<T> _list) {
        list.addAll(_list);
        notifyItemRangeInserted(position, _list.size());
    }

    /**
     * @param position 删除单条数据
     */
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * @param _list
     * @param position 批量的删除数据
     */
    public void removeItems(List<T> _list, int position) {
        list.removeAll(_list);
        notifyItemRangeRemoved(position, _list.size());
    }

    /**
     * @param position
     * @param data     更新数据
     */
    public void updateItem(int position, T data) {
        list.remove(position);
        list.add(position, data);
        notifyItemChanged(position);
    }

    /**
     * @param _list
     * @param isClear 实现数据的重回新加载
     */
    public void reloadRecyclerView(List<T> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
}
