package com.ui.pickerify;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ui.pickerify.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by USER on 12-01-2017.
 */

public class ListAdapter extends BaseAdapter {

    private int height, centerPos;
    private List<String> dataList;
    private Context context;
    private SparseIntArray positionsNoClickables;

    ListAdapter(Context context, List<String> dataList, int visibleItemCount) {
        this.context = context;
        this.dataList = dataList;
        positionsNoClickables = new SparseIntArray(4);
        setItems(dataList, visibleItemCount);
        setPositonsNoClickables();
    }

    public void setHeight(int height) {
        this.height = height;
        notifyDataSetChanged();
    }

    void handleSelectEvent(int position) {
        this.centerPos = position;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item, parent, false);
            convertView = binding.getRoot();
            convertView.setMinimumHeight(height);
            convertView.setTag(binding);
        } else {
            convertView.setMinimumHeight(height);
            binding = (ListItemBinding) convertView.getTag();
        }

        String num = (String) getItem(position);

        binding.itemTxt.setText(num);

        if (position == centerPos) {
            binding.itemTxt.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        } else {
            binding.itemTxt.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
        }

        return convertView;
    }

    private void setItems(List<String> rawItems, int visibleItem) {
        List<String> emptyRows = Arrays.asList("", "");
        List<String> items = new ArrayList<>();
        items.addAll(emptyRows);
        items.addAll(rawItems);
        items.addAll(emptyRows);

        this.dataList = items;

        centerPos = (visibleItem / 2);
    }

    private void setPositonsNoClickables() {
        positionsNoClickables.put(0, 0);
        positionsNoClickables.put(1, 1);
        positionsNoClickables.put(dataList.size() - 2, dataList.size() - 2);
        positionsNoClickables.put(dataList.size() - 1, dataList.size() - 1);
    }

    @Override
    public boolean isEnabled(int position) {
        boolean isClickable = positionsNoClickables.get(position, -1) == -1;
        return isClickable && super.isEnabled(position);
    }

}
