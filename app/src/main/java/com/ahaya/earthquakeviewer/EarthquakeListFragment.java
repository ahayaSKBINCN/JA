package com.ahaya.earthquakeviewer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeListFragment extends Fragment {
    private ArrayList<Earthquake> mEarthquakes =
            new ArrayList<>();
    private RecyclerView recyclerView;
    private EarthquakeRecyclerViewAdapter earthquakeRecyclerViewAdapter
            =new EarthquakeRecyclerViewAdapter(mEarthquakes);

    public EarthquakeListFragment(){

    }
    public void setEarthquakes(List<Earthquake> earthquakes){
        for(Earthquake earthquake: earthquakes){
            if(!this.mEarthquakes.contains(earthquake)){
                //插入数据
                mEarthquakes.add(earthquake);
                //向recyclerview 发出通知
                earthquakeRecyclerViewAdapter.notifyItemInserted(mEarthquakes.indexOf(earthquake));
            }
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_earthquake_list, container, false);
        recyclerView = view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //为RecyclerView设置适配器（adapter）
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(earthquakeRecyclerViewAdapter);
    }
}
