package com.ahaya.earthquakeviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    EarthquakeListFragment earthquakeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        if(savedInstanceState == null){
            FragmentTransaction ft = fm.beginTransaction();
            earthquakeListFragment = new EarthquakeListFragment();
            ft.add(R.id.main_activity_frame,earthquakeListFragment,TAG_LIST_FRAGMENT);
            ft.commitNow();
        }else{
            earthquakeListFragment = (EarthquakeListFragment) fm.findFragmentByTag(TAG_LIST_FRAGMENT);
        }
        //TODO 暂时使用模拟数据
        Date now = Calendar.getInstance().getTime();
        List<Earthquake> dummyQuakes = new ArrayList<>(0);
        dummyQuakes.add(new Earthquake("0",now,"San Jose",null,7.3,null));
        dummyQuakes.add(new Earthquake("1",now,"LA",null,6.5,null));
        earthquakeListFragment.setEarthquakes(dummyQuakes);

    }
}