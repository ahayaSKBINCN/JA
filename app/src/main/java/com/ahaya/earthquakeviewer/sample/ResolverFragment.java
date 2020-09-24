package com.ahaya.earthquakeviewer.sample;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ResolverFragment extends Fragment {

    ContentResolver contentResolver;
    String[] result_columns = new String[] {
            HoardContract.KEY_ID,
            HoardContract.KEY_GOLD_HOARD_ACCESSIBLE_COLUMN,
            HoardContract.KEY_GOLD_HOARD_COLUMN
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //获取Content Resolver
        contentResolver = getContext().getContentResolver();
    }

    /**
     * 使用Content Resolver 查询 Content Provider；
     * @return Cursor
     */
    public Cursor queryByContentResolver () {
        //指定结果列投影（projection参数）,返回满足要求所需的最小列数；


        //指定用于现在结果的where 子句
        String where = HoardContract.KEY_GOLD_HOARD_ACCESSIBLE_COLUMN
                + "=?";
        String[] whereArgs = {"1"};
        //SQL排序
        String order = null;
        //返回指定的行
        Cursor resultCursor = contentResolver.query(HoardContentProvider.CONTENT_URI,result_columns,where,whereArgs,order);
        return resultCursor;
    }

    public Cursor queryCertionProvider (int rowID){
        Uri rowAddress = ContentUris.withAppendedId(HoardContentProvider.CONTENT_URI,rowID);
        //一下这些为null，因为我们请求的是单行；
        String where = null;
        String[] whereArgs= null;
        String order = null;

        //返回指定行
        return contentResolver.query(rowAddress,result_columns,where,whereArgs,order);
    }

}
