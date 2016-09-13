package com.wu.my.android_letterindexviewrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wu.my.android_letterindexviewrecyclerview.R;
import com.wu.my.android_letterindexviewrecyclerview.bean.UserBean;
import com.wu.my.android_letterindexviewrecyclerview.helper.MyIndexerAdapter;

import java.util.List;

/**
 * Created by My on 2016/8/2.
 */
public class MyAdapter extends MyIndexerAdapter<UserBean> implements SectionIndexer {

    public MyAdapter(Context context, List<UserBean> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recyclerview_main, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;
        mHolder.textView_item_username.setText(list.get(position).getUsername());
        mHolder.textView_item_pinyin.setText(list.get(position).getPinyin());
        Glide.with(context).load(list.get(position).getIconUrl())
                .placeholder(R.mipmap.ic_stub)
                .error(R.mipmap.ic_empty)
                .into(mHolder.imageView_item_icon);
        //根据给定position来获取所在的分组
        int section = getSectionForPosition(position);
        int position_first = getPositionForSection(section);
        //给该分组的第一个元素赋值第一个字母，分组其他的则不赋值
        if (position == position_first) {
            mHolder.textView_item_firstletter.setVisibility(View.VISIBLE);
            mHolder.textView_item_firstletter.setText(list.get(position).getFirstLetter());
        } else {
            mHolder.textView_item_firstletter.setVisibility(View.GONE);
        }
    }
    @Override
    public Object[] getSections() {
        return new Object[0];
    }
    /**
     * @param sectionIndex
     * @return 根据元素获取分组，并且获取该组中的第一个元素
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < list.size(); i++) {
            String firstLetter = list.get(i).getFirstLetter();
            int char_firstLetter = firstLetter.charAt(0);
            if (char_firstLetter == sectionIndex) {
                return i;
            }
        }
        return -1;
    }
    /**
     * @param position
     * @return 根据元素的position来获取元素所在的分组
     */
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getFirstLetter().charAt(0);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_item_firstletter;
        private TextView textView_item_username;
        private TextView textView_item_pinyin;
        private ImageView imageView_item_icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView_item_icon = (ImageView) itemView.findViewById(R.id.imageView_item_icon);
            textView_item_firstletter = (TextView) itemView.findViewById(R.id
                    .textView_item_firstletter);
            textView_item_username = (TextView) itemView.findViewById(R.id.textView_item_username);
            textView_item_pinyin = (TextView) itemView.findViewById(R.id.textView_item_pinyin);
        }
    }
}
