package com.ahaya.earthquakeviewer.starpicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ahaya.earthquakeviewer.R;

public class StarSignPicker extends AppCompatActivity {
    public static final String EXTRA_SIGN_NAME= "SIGN_NAME";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_sign_picker);
        StarSignPickerAdapter starSignPickerAdapter = new StarSignPickerAdapter();
        starSignPickerAdapter.setOnAdapterItemClick(
                new StarSignPickerAdapter.IAdapterItemClickListener() {
                    @Override
                    public void onItemClicked(String selectedItem) {
                        //Construct the result URI;
                        Intent outData = new Intent();
                        outData.putExtra(EXTRA_SIGN_NAME,selectedItem);
                        setResult(Activity.RESULT_OK,outData);
                        finish();
                    }
                }
        );
        RecyclerView recyclerView = findViewById(R.id.star_recycler_view);
        recyclerView.setAdapter(starSignPickerAdapter);
    }
}
