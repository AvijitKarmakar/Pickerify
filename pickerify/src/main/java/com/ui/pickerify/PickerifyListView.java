package com.ui.pickerify;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.ui.pickerify.databinding.ListViewLayoutBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 22-01-2017.
 */

public class PickerifyListView extends FrameLayout implements AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener {

    private static final int DEF_VISIBLE_ITEM_SIZE = 5;
    private ListViewLayoutBinding binding;
    private ListAdapter listAdapter;
    private int height, width, position, scrollTop, scrollBottom, firstItem, lastPositionNotified;
    private boolean onItemClicked = true;
    private PickerifyListViewItemClickListener pickerifyListViewItemClickListener;
    private List<String> dataList;

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
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.list_view_layout, this, false);

        binding.listView.setOnItemClickListener(this);
        binding.listView.setOnScrollListener(this);

        addView(binding.getRoot());
    }

    public void setData(List<String> dataList) {
        this.dataList = dataList;
        listAdapter = new ListAdapter(getContext(), dataList, DEF_VISIBLE_ITEM_SIZE);
        binding.listView.setAdapter(listAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onItemClicked = false;
        setNewPositionCenter(position);
    }

    private void setNewPositionCenter(int position) {
        listAdapter.setHeight(height);
        listAdapter.handleSelectEvent(position);
        selectListItem(position);
    }

    private void selectListItem(int position) {
        selectListItem(position - 2, true);
    }

    private void selectListItem(int pos, final boolean notify) {
        this.position = pos;
        binding.listView.smoothScrollToPositionFromTop(position, 0, 500);
        if (notify) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //We need to give the adapter time to draw the views
                    if (pickerifyListViewItemClickListener == null) {
                        throw new IllegalStateException(
                                "You must assign a valid PickerUIListView.PickerUIItemClickListener first!");
                    }
                    pickerifyListViewItemClickListener
                            .onItemClickPickerifyListView(position, dataList.get(position));
                }
            }, 200);

        }
    }

    public void setOnItemClickItemPickerUI(PickerifyListViewItemClickListener pickerifyListViewItemClickListener) {
        this.pickerifyListViewItemClickListener = pickerifyListViewItemClickListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (scrollState == 0) {
            onItemClicked = true;
            getItemInListCenter();
            if (scrollTop < -(height / 5)) {
                listAdapter.handleSelectEvent(firstItem + 1 + 2);
                selectListItem(firstItem + 1 + 2);
            } else if (scrollBottom < (height / 5)) {
                selectListItem(firstItem + 2);
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (onItemClicked) {
            View v = binding.listView.getChildAt(0);
            scrollTop = (v == null) ? 0 : v.getTop();
            scrollBottom = (v == null) ? 0 : v.getBottom();
            firstItem = firstVisibleItem;
            getItemInListCenter();
        }
        onItemClicked = true;
    }

    public int getItemInListCenter() {
        int position = binding.listView.pointToPosition(getWidth() / 2, getHeight() / 2);
        if (position != -1) {
            if (position != lastPositionNotified) {
                lastPositionNotified = position;
                listAdapter.handleSelectEvent(position);
            }
        }
        return position + 4;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        listAdapter.setHeight(height / DEF_VISIBLE_ITEM_SIZE);
        setMeasuredDimension(width, height);
    }

    public interface PickerifyListViewItemClickListener {
        void onItemClickPickerifyListView(int position, String valueResult);
    }

}
