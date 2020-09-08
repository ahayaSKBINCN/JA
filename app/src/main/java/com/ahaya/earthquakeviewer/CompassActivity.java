package com.ahaya.earthquakeviewer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ahaya.earthquakeviewer.Sample.CompassView;


public class CompassActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        CompassView compassView = findViewById(R.id.compassView);
        //根据需要，通过调用setBearing 方法更新方向；
    }
}
