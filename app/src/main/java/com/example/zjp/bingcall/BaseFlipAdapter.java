package com.example.zjp.bingcall;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.yalantis.flipviewpager.R.id;
import com.yalantis.flipviewpager.R.layout;
import com.yalantis.flipviewpager.utils.FlipSettings;
import com.yalantis.flipviewpager.view.FlipViewPager;
import com.yalantis.flipviewpager.view.FlipViewPager.OnChangePageListener;
import java.util.List;

public abstract class BaseFlipAdapter<T> extends BaseAdapter {
    private List<T> items;
    private FlipSettings settings;
    private LayoutInflater inflater;
    private int mypage,myposition;
    private FlipViewPager flipViewPager;

    public BaseFlipAdapter(Context context, List<T> items, FlipSettings settings) {
        this.items = items;
        this.settings = settings;
        this.inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.items.size() % 2 != 0?this.items.size() / 2 + 1:this.items.size() / 2;
    }

    public T getItem(int position) {
        return this.items.get(position);
    }

    public long getItemId(int position) {
        return (long)position;
    }

    public int getMypage() {
        return mypage;
    }

    public int getMyposition() {
        return myposition;
    }

    public FlipViewPager getFlipViewPager() {
        return flipViewPager;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        Object item1 = this.getItem(position * 2);
        Object item2 = this.items.size() > position * 2 + 1?this.getItem(position * 2 + 1):null;
        if(convertView == null) {
            convertView = this.inflater.inflate(layout.flipper, (ViewGroup)null);
        }

        BaseFlipAdapter.ViewHolder viewHolder;
        if(convertView.getTag() != null) {
            viewHolder = (BaseFlipAdapter.ViewHolder)convertView.getTag();
        } else {
            viewHolder = new BaseFlipAdapter.ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.mFlipViewPager = (FlipViewPager)convertView.findViewById(id.flip_view);
        }

        viewHolder.mFlipViewPager.setOnChangePageListener(new FlipViewPager.OnChangePageListener() {
            public void onFlipped(int page) {
                BaseFlipAdapter.this.settings.savePageState(position, page);
                Log.d("haha", position + " " + page+" ");
                mypage=page;
                myposition=position;
            }
        });
        Log.d("haha22", " "+viewHolder.mFlipViewPager);
        if(viewHolder.mFlipViewPager.getAdapter() == null) {
            viewHolder.mFlipViewPager.setAdapter(new BaseFlipAdapter.MergeAdapter(item1, item2), this.settings.getDefaultPage(), position, this.items.size());
        } else {
            BaseFlipAdapter.MergeAdapter adapter = (BaseFlipAdapter.MergeAdapter)viewHolder.mFlipViewPager.getAdapter();
            adapter.updateData(item1, item2);
            viewHolder.mFlipViewPager.setAdapter(adapter, this.settings.getPageForPosition(position).intValue(), position, this.items.size());
        }
        this.flipViewPager=viewHolder.mFlipViewPager;
        return convertView;
    }

    public abstract View getPage(int var1, View var2, ViewGroup var3, T var4, T var5);

    public abstract int getPagesCount();

    private class MergeAdapter extends BaseAdapter {
        private T item1;
        private T item2;

        public MergeAdapter(T item1, T item2) {
            this.item1 = item1;
            this.item2 = item2;
        }

        public void updateData(T item1, T item2) {
            this.item1 = item1;
            this.item2 = item2;
        }

        public int getCount() {
            return this.item2 == null?BaseFlipAdapter.this.getPagesCount() - 1:BaseFlipAdapter.this.getPagesCount();
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long)position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return BaseFlipAdapter.this.getPage(position, convertView, parent, this.item1, this.item2);
        }
    }

    class ViewHolder {
        FlipViewPager mFlipViewPager;

        ViewHolder() {
        }
    }
}
