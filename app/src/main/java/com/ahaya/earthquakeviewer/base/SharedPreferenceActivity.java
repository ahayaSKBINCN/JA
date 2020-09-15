package com.ahaya.earthquakeviewer.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;


public class SharedPreferenceActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    /**
     * @deprecated：注意 PreferenceManager类已经废弃；
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isTrue",true);
        //提交更改
        editor.apply();

        //editor.commit();立即处理更改 确认返回结果 成功---true 失败---false；
       Map<String,?> allPreference =   preferences.getAll();
       boolean isTrue = preferences.getBoolean("isTrue",false);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //TODO:检查共享偏好和key参数，并根据需要更改UI或行为；

    }
}
