package com.ahaya.earthquakeviewer.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 1、由于headless fragment 没有与之关联的视图 所以无法通过布局创建；
 * 2、fragment的实例仅处于活动状态时被保留，这意味着这种方式只能用于不在回退堆栈中的fragment；
 * 3、headless fragment 不能存储任何父Activity的任何引用--或包含对父Activity的任何对象，这可能会导致内存泄露
 *    ---由于引用而无法对销毁的Activity进行回收操作；
 */
public class HeadlessFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //跨配置更改时，保留Fragment；
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }
}
