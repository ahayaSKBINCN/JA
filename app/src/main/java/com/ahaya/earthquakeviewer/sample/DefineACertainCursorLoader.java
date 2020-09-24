package com.ahaya.earthquakeviewer.sample;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

public class DefineACertainCursorLoader {

    private Context context;
    public DefineACertainCursorLoader (Context context){
        this.context = context;
    }
    LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            // 以Cursor Loader 的形式构造一个新的查询，使用id参数够着并返回不同的Loader
            String[] projection = null;
            String where = null;
            String[] whereArgs = null;
            String sortOrder = null;
            //查询URI
            Uri queryUri = HoardContentProvider.CONTENT_URI;
            //创建一个新的Cursor Loader
            return new CursorLoader(context,queryUri,projection,where,whereArgs,sortOrder);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        }
    };


}
