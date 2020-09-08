package com.ahaya.earthquakeviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ahaya.earthquakeviewer.base.Earthquake;
import com.ahaya.earthquakeviewer.base.EarthquakeListFragment;
import com.ahaya.earthquakeviewer.compass.CompassActivity;
import com.ahaya.earthquakeviewer.starpicker.StarSignPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    private static final int PICK_STARSIGN = 1;
    EarthquakeListFragment earthquakeListFragment;
    Button linkToCompass;
    Button linkToStarSignPicker;
    TextView textView;



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
        linkToCompass = findViewById(R.id.button_go_to_compass);
        LinkToCompass(this, linkToCompass);
        linkToStarSignPicker = findViewById(R.id.pick_star_btn);
        LinkToStarPicker(linkToStarSignPicker);
        textView = findViewById(R.id.selected_starSign_textView);

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
                    Intent intent = new Intent(context, CompassActivity.class);
                    Log.println(Log.WARN,"MSG","CLICK_HANDLED");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void LinkToStarPicker(Button btn) {
        if(null  != btn) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("starsigns://"));
                    startActivityForResult(intent, PICK_STARSIGN);
                    //Intent.setExtra
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_STARSIGN) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                String selectedStarSign = data.getStringExtra(StarSignPicker.EXTRA_SIGN_NAME);
               textView.setText(selectedStarSign);
            }
        }
    }
}