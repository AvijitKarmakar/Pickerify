package com.ui.pickerifysample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ui.pickerify.ListAdapter;
import com.ui.pickerifysample.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setDataForPickerifyListView();
    }

    private void setDataForPickerifyListView() {
        dataList = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            dataList.add(String.valueOf(i));
        }

        binding.pickerListView.setData(dataList);
    }

}
