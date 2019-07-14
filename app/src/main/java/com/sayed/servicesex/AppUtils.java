package com.sayed.servicesex;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class AppUtils {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel createNotificationChannel(String channelId, String channelName) {
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        return notificationChannel;
    }
}
