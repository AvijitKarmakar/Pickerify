package com.ui.pickerifysample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ui.pickerify.ListAdapter;
import com.ui.pickerify.PickerifyListView;
import com.ui.pickerifysample.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PickerifyListView.PickerifyListViewItemClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setDataForPickerifyListView();
        binding.pickerListView.setOnItemClickItemPickerUI(this);
    }

    private void setDataForPickerifyListView() {
        List<String> dataList = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            dataList.add(String.valueOf(i));
        }

        binding.pickerListView.setData(dataList);
    }

    @Override
    public void onItemClickPickerifyListView(int position, String valueResult) {
        Toast.makeText(this, "Selected Value:" + valueResult, Toast.LENGTH_SHORT).show();
    }

}
