package com.ahaya.earthquakeviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    EarthquakeListFragment earthquakeListFragment;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction ft = fm.beginTransaction();
            earthquakeListFragment = new EarthquakeListFragment();
            ft.add(R.id.main_activity_linear, earthquakeListFragment, TAG_LIST_FRAGMENT);
            ft.commitNow();
        } else {
            earthquakeListFragment = (EarthquakeListFragment) fm.findFragmentByTag(TAG_LIST_FRAGMENT);
        }
        //TODO 暂时使用模拟数据
        Date now = Calendar.getInstance().getTime();
        btn = findViewById(R.id.button_go_to_compass);

        LinkToCompass(this, btn);

        List<Earthquake> dummyQuakes = new ArrayList<>(0);
        dummyQuakes.add(new Earthquake("0", now, "San Jose", null, 7.3, null));
        dummyQuakes.add(new Earthquake("1", now, "LA", null, 6.5, null));
        earthquakeListFragment.setEarthquakes(dummyQuakes);
    }


    private void LinkToCompass(final Context context, Button btn) {
        if (null != btn) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,CompassActivity.class);
                    Log.println(Log.WARN,"MSG","CLICK_HANDLED");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
        }
    }

}