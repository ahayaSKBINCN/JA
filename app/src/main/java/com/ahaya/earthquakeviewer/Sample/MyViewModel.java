package com.ahaya.earthquakeviewer.Sample;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用ViewModel 必须先从Activity 和Fragment中创建新的实例：
 * ViewModelProvider provider = new ViewModelProvider(this, AndroidViewModelFactory.getInstance((Application) Objects.requireNonNull(getContext()).getApplicationContext() ));
 * 然后使用get方法指定使用的类：
 * ViewModel model = provider.get(Model::class);
 * 之后使用observe添加一个Observer：
 * model.getData().observe(this,new Observer<List<String>>(){
 * @Override
 * public void onChanged(@Nullable List<String> data) {
 *     更新UI；
 * }
 * })
 */
public class MyViewModel extends AndroidViewModel {
    public MyViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<List<String>> data = null;
    private static final String TAG = "MY_VIEW_MODEL";

    public LiveData<List<String>> getData () {
        if(data == null) {
            data = new MutableLiveData<List<String>>();
            loadData();
        }
        return data;
    }

    @SuppressLint("StaticFieldLeak")
    private void loadData() {
        new AsyncTask<Void,Void,List<String>>() {

            @Override
            protected List<String> doInBackground(Void... voids) {
                ArrayList<String> result = new ArrayList<>(0);
                //TODO:在后台线程中获取数据
                return result;
            }

            @Override
            protected void onPostExecute(List<String> strings) {
                //更新LiveData中的数据值
                if(data!=null) data.setValue(strings);
            }
        }.execute();
    }
}
