package com.ui.pickerify;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.ui.pickerify.databinding.ListViewLayoutBinding;

import java.util.List;

/**
 * Created by USER on 22-01-2017.
 */

public class PickerifyListView extends FrameLayout implements AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener {

    private static final int DEF_VISIBLE_ITEM_SIZE = 5;
    private ListView listView;
    private ListAdapter listAdapter;
    private int height, width;

    public PickerifyListView(Context context) {
        this(context, null);
    }

    public PickerifyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PickerifyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateListViewLayout();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PickerifyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateListViewLayout();
    }

    private void inflateListViewLayout() {
        ListViewLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.list_view_layout, this, false);
        View view = binding.getRoot();

        listView = (ListView) view.findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);

        addView(view);
    }

    public void setData(List<String> dataList) {
        listAdapter = new ListAdapter(getContext(), dataList, DEF_VISIBLE_ITEM_SIZE);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        listAdapter.setHeight(height / DEF_VISIBLE_ITEM_SIZE);
        setMeasuredDimension(width, height);
    }

}
