package com.ahaya.earthquakeviewer.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ahaya.earthquakeviewer.R;

public class LifeformDetectedReceiver extends BroadcastReceiver {
    public static final String NEW_LIFEFORM_ACTION
            = "com.ahaya.earthquakeviewer.sample.action.NEW_LIFEFORM_ACTION";
    public static final String EXTRA_LIFEFORM_NAME
            = "EXTRA_LIFEFORM_NAME";
    public static final String EXTRA_LATITUDE
            = "EXTRA_LATITUDE";
    public static final String EXTRA_LONGITUDE
            = "EXTRA_LONGITUDE";
    public static final String FACE_HUGGER
            = "facehugger";

    private static final int NOTIFICATION_ID = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        // 从Intent中获取详情；
        String type = intent.getStringExtra(EXTRA_LIFEFORM_NAME);
        double lat = intent.getDoubleExtra(EXTRA_LATITUDE,  Double.NaN);
        double lng = intent.getDoubleExtra(EXTRA_LONGITUDE, Double.NaN);

        if(type.equals(FACE_HUGGER)){
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Face Hugger Detected")
                    .setContentText(Double.isNaN(lat) || Double.isNaN(lng)?"Location Unknown":"Located at "+lat+", "+lng);
            notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

        }




    }
}
