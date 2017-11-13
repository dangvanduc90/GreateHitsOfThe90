package com.greatehits90.greatehitsofthe90.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.greatehits90.greatehitsofthe90.R;

import java.util.Objects;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static com.greatehits90.greatehitsofthe90.common.Ultils.INTENT_STATUS;
import static com.greatehits90.greatehitsofthe90.common.Ultils.INTENT_STATUS_PLAY;
import static com.greatehits90.greatehitsofthe90.common.Ultils.MY_NOTIFICATION_ID;
import static com.greatehits90.greatehitsofthe90.common.Ultils.URL_STREAM;

public class StreamingService extends Service {

    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String status = intent.getStringExtra(INTENT_STATUS);

        if (Objects.equals(status, INTENT_STATUS_PLAY)) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    Notification builder = new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setAutoCancel(false)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                            .setColor(Color.BLUE)
                            .setContentTitle("Bao thuc")
                            .setContentText("Chuyen de thuc hanh")
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setVisibility(VISIBILITY_PUBLIC)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setFullScreenIntent(null, true)
//                            .addAction(R.drawable.ic_launcher_background, "Action", null)
//                            .addAction(R.drawable.ic_launcher_background, "Dismiss", null)
                            .build();
                    NotificationManager notificationService  =
                            (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationService.notify(MY_NOTIFICATION_ID, builder);
                }

                mediaPlayer = MediaPlayer.create(this, Uri.parse(URL_STREAM));
                mediaPlayer.start();
            } catch (Exception e) {
                Log.d("error1", e.getMessage());
            }
        } else {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        return START_REDELIVER_INTENT;
    }


}
