package com.ahaya.earthquakeviewer.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ahaya.earthquakeviewer.dao.EarthquakeDao;

import com.ahaya.earthquakeviewer.database.EarthquakeDatabaseAccessor;
import com.ahaya.earthquakeviewer.entity.Earthquake;

public class EarthquakeProvider extends ContentProvider {

    public static final String TAG = EarthquakeProvider.class.getName();
    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String AUTHORITY = "com.ahaya.provider.earthquake_provider";
    public static final String TABLE_NAME = "Earthquake";
    public static final int ID_EARTHQUAKE_DATA = 1;
    public static final int ID_EARTHQUAKE_DATA_ITEM = 2;


    static {
        uriMatcher.addURI(
                AUTHORITY,
                TABLE_NAME,
                ID_EARTHQUAKE_DATA
        );
        uriMatcher.addURI(
                AUTHORITY,
                TABLE_NAME + "/*",
                ID_EARTHQUAKE_DATA_ITEM
        );
    }

    private EarthquakeDao earthquakeDao;


    @Override
    public boolean onCreate() {
        earthquakeDao = EarthquakeDatabaseAccessor.getInstance(this.getContext()).earthquakeDao();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull Uri uri,
            @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder) {
        Log.d(TAG, "query");
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case ID_EARTHQUAKE_DATA:
                cursor = earthquakeDao.findAll();
                if (getContext() != null) {
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                }
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(
            @NonNull Uri uri,
            @Nullable ContentValues values) {
        Log.d(TAG,"insert");
        switch (uriMatcher.match(uri)){
            case ID_EARTHQUAKE_DATA:
                if(getContext()!=null){
                    long id = earthquakeDao.insertEarthquake(Earthquake.fromContentValue(values));
                    if(id !=0){
                        getContext().getContentResolver().notifyChange(uri,null);
                        return ContentUris.withAppendedId(uri,id);
                    }
                }
            case ID_EARTHQUAKE_DATA_ITEM:
                throw new IllegalArgumentException("Invalid URI: Insert failed " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri );
        }
    }

    @Override
    public int delete(
            @NonNull Uri uri,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {
        Log.d(TAG,"delete");
        switch (uriMatcher.match(uri)){
            case ID_EARTHQUAKE_DATA:
                throw new IllegalArgumentException
                        ("Invalid uri: cannot delete");
            case ID_EARTHQUAKE_DATA_ITEM:
                if(getContext()!=null){
                    int count = earthquakeDao.deleteEarthquake(ContentUris.parseId(uri));
                    return count;
                }
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
    }

    @Override
    public int update(
            @NonNull Uri uri,
            @Nullable ContentValues values,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {
        Log.d(TAG,"update");
        switch (uriMatcher.match(uri)){
            case ID_EARTHQUAKE_DATA:
                if(getContext()!=null){
                    int count = earthquakeDao.update(Earthquake.fromContentValue(values));
                    if(count !=0){
                        getContext().getContentResolver().notifyChange(uri,null);
                        return count;
                    }
                }
            case ID_EARTHQUAKE_DATA_ITEM:
                throw new IllegalArgumentException("Invalid URI:  cannot update");
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
