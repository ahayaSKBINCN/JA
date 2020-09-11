package com.ahaya.earthquakeviewer.base;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ahaya.earthquakeviewer.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.lifecycle.ViewModelProvider.*;

public class EarthquakeListFragment extends Fragment {
    private ArrayList<Earthquake> mEarthquakes =
            new ArrayList<>();
    private RecyclerView recyclerView;
    private EarthquakeRecyclerViewAdapter earthquakeRecyclerViewAdapter
            =new EarthquakeRecyclerViewAdapter(mEarthquakes);

    private EarthquakeModel earthquakeModel;
    private SwipeRefreshLayout refreshLayout;

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
        refreshLayout.setRefreshing(false);
    }

    public interface OnListFragmentInteractionListener{
        void onListFragmentRefreshRequested();
    }

    private OnListFragmentInteractionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnListFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        refreshLayout = view.findViewById(R.id.refresh_control);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //为RecyclerView设置适配器（adapter）
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(earthquakeRecyclerViewAdapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateEarthquakes();
            }
        });
    }

    protected void updateEarthquakes() {
        if(mListener != null){
            mListener.onListFragmentRefreshRequested();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final String TAG = "ONACTIVITYCREATED_EARTHQUAKE_FRAGMENT";


        earthquakeModel = new ViewModelProvider(this, AndroidViewModelFactory.getInstance((Application) getContext().getApplicationContext() )).get(EarthquakeModel.class);
        earthquakeModel.getEarthquakes()
                .observe(this, new Observer<List<Earthquake>>() {
                    @Override
                    public void onChanged(List<Earthquake> earthquakes) {
                        if(earthquakes !=null)
                            setEarthquakes(earthquakes);
                    }
                });
    }
}
