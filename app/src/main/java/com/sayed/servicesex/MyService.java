package com.sayed.servicesex;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

    private MediaPlayer player;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MY SERVICE","ON CREATE");
        setStartForegroundInAndroid8();

        startRingToon();
    }

    //Start Ringtoon
    private void startRingToon() {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        // This will play the ringtone continuously until we stop the service.
        player.setLooping(true);
        // It will start the player
        player.start();
    }

    //foreground service - for oreo limitation
    private void setStartForegroundInAndroid8() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(AppUtils.createNotificationChannel("channelIdBackground", "channelNameBackground"));

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            String message = getString(R.string.message_service_running);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "channelIdBackground")
                    .setSmallIcon(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.mipmap.ic_launcher : R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentIntent(contentIntent)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            startForeground(2, mBuilder.build());
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MY SERVICE","ON DESTROY");
        player.stop();
    }


}
