package com.ahaya.earthquakeviewer.sample;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

public class DownloadsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        String extraNotificationFileIds =
                DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS;
        String extraFileId = DownloadManager.EXTRA_DOWNLOAD_ID;
        String action = intent.getAction();

        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            long reference = intent.getLongExtra(extraFileId, -1);
            //TODO: 对下载文件的操作;
             long myDownloadReference = 77777;
             if(myDownloadReference == reference){
             DownloadManager.Query myDownloadQuery = new DownloadManager.Query();
             myDownloadQuery.setFilterById(reference);

                 Cursor myDownload = downloadManager.query(myDownloadQuery);
                 if(myDownload.moveToFirst()){
                     int fileIdIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_ID);

                     long fileId = myDownload.getLong(fileIdIdx);
                     Uri fileUri = downloadManager.getUriForDownloadedFile(fileId);
                     //TODO：操作下载文件
                 }
                 myDownload.close();
             }

        } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(action)) {
            long[] references = intent.getLongArrayExtra(extraNotificationFileIds);
            for (long reference : references) {
                //TODO:响应用户选择文件下载的通知；

//                if (myDownloadReference == reference) {
//                }
            }
        }
    }
}
