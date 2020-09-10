package com.ahaya.earthquakeviewer.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahaya.earthquakeviewer.R;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeListFragment extends Fragment {
    private ArrayList<Earthquake> mEarthquakes =
            new ArrayList<>();
    private RecyclerView recyclerView;
    private EarthquakeRecyclerViewAdapter earthquakeRecyclerViewAdapter
            =new EarthquakeRecyclerViewAdapter(mEarthquakes);

//    private EarthquakeModel  model;

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
//        model = new ViewModelProvider(this, new ViewModelProvider.Factory() {
//            @NonNull
//            @Override
//            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//                try {
//                    return modelClass.newInstance();
//                } catch (IllegalAccessException e) {
//                    Log.e("","IllegalAccessException",e);
//                } catch (java.lang.InstantiationException e) {
//                    Log.e("","InstantiationException",e);
//                }
//                return null;
//            }
//        }).get(EarthquakeModel.class);


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
