package com.ahaya.earthquakeviewer.sample;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.FileNotFoundException;

public class HoardContentProvider extends ContentProvider {
    private HoardDBOpenHelper hoardDBOpenHelper;
    private static final int ALLOWS = 1;
    private static final int SINGLE_ROW = 2;

    private static final UriMatcher matcher;
    public static final Uri CONTENT_URI =Uri.parse("");

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI("com.ahaya.provider.JA", "lairs", ALLOWS);
        matcher.addURI("com.ahaya.provider.JA", "lairs/#", SINGLE_ROW);
    }


    @Override
    public boolean onCreate() {
        //construct the underlying database.
        //defer opening the database until you need to perform a query or transaction;
        hoardDBOpenHelper = new HoardDBOpenHelper(getContext(),
                HoardDBOpenHelper.DATABASE_NAME,
                null,
                HoardDBOpenHelper.DATABASE_VERSION);

        return true;

    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull Uri uri,
            @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder) {

        return query(uri, projection, selection, selectionArgs, sortOrder, null);
    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull Uri uri,
            @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder,
            @Nullable CancellationSignal cancellationSignal) {

        //open the database
        SQLiteDatabase db;
        try {
            db = hoardDBOpenHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = hoardDBOpenHelper.getReadableDatabase();
        }
        //replace these with valid sql statements if necessary.
        String groupBy = null;
        String having = null;
        //use an SQLite Query Builder to simplify construction the database query;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        //if this is a row query, limit the result set to the passed in row.
        switch (matcher.match(uri)) {
            case SINGLE_ROW:
                String rowID = uri.getLastPathSegment();
                queryBuilder.appendWhere(HoardContract.KEY_ID + "=" + rowID);
            default:
                break;
        }
        //specify the table on which to perform the query. this can be a specific table or a join as required;
        queryBuilder.setTables(HoardDBOpenHelper.DATABASE_TABLE);

        //specify a limit to the number of returned results, if any;
        String limit = null;
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {

            @Override
            public void onCancel() {
                //TODO 当查询操作被取消时做出响应
            }
        });

        //Execute the query;
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder, limit, cancellationSignal);
        //return the result cursor;
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        //TODO: return the mime-type of a query;
        return null;
    }

    @Nullable
    @Override
    public Uri insert(
            @NonNull Uri uri,
            @Nullable ContentValues values) {
        //TODO:insert the content values and return a uri to the record;
        return null;
    }

    @Override
    public int delete(
            @NonNull Uri uri,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {
        SQLiteDatabase db = hoardDBOpenHelper.getWritableDatabase();

        switch (matcher.match(uri)) {
            case SINGLE_ROW:
                String rowID = uri.getLastPathSegment();
                selection = HoardContract.KEY_ID + "=" + rowID +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
            default:
                break;
        }
        //要返回已删除的条目的数量，就必须指定where子句。 要删除所有行，则传入1；
        if (selection == null)
            selection = "1";
        //执行删除操作
        int deleteCount = db.delete(HoardDBOpenHelper.DATABASE_TABLE, selection, selectionArgs);
        //通知所有观察者有关数据的变化
        getContext().getContentResolver().notifyChange(uri, null);
        //返回删除的条目
        return deleteCount;
    }

    @Override
    public int update(
            @NonNull Uri uri,
            @Nullable ContentValues values,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {
        SQLiteDatabase db = hoardDBOpenHelper.getWritableDatabase();
        switch (matcher.match(uri)) {
            case SINGLE_ROW:
                String rowID = uri.getLastPathSegment();
                selection = HoardContract.KEY_ID + "=" + rowID
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
            default:
                break;
        }
        int updateCount = db.update(HoardDBOpenHelper.DATABASE_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        return super.openFile(uri, mode);
    }
}
