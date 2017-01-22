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
import android.widget.TextView;

import com.ui.pickerify.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by USER on 12-01-2017.
 */

public class ListAdapter extends BaseAdapter {

    private int height, visibleItemCount, centerPos;
    private List<String> dataList;
    private Context context;

    ListAdapter(Context context, List<String> dataList, int visibleItemCount) {
        this.context = context;
        this.dataList = dataList;
        this.visibleItemCount = visibleItemCount;
        setItems(dataList, visibleItemCount);
    }

    public void setHeight(int height) {
        this.height = height;
        notifyDataSetChanged();
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

        return convertView;
    }

    private void setItems(List<String> rawItems, int visibleItem) {
        List<String> emptyRows = Arrays.asList("", "");
        List<String> items = new ArrayList<>();
        items.addAll(emptyRows);
        items.addAll(rawItems);
        items.addAll(emptyRows);

        this.dataList = items;

        centerPos = (visibleItem / 2) + 1;
    }

}
