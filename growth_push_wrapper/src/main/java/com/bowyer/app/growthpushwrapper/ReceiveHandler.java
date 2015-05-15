package com.bowyer.app.growthpushwrapper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;


public class ReceiveHandler implements com.growthpush.handler.ReceiveHandler {

    private Intent mIntent;

    public ReceiveHandler(Intent intent) {
        mIntent = intent;
    }

    public void onReceive(Context context, Intent intent) {
        showNotification(context, intent);
    }

    public void showNotification(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify("GrowthPush" + context.getPackageName(), 1,
                generateNotification(context, intent.getExtras()));
    }

    public Notification generateNotification(Context context, Bundle extras) {
        PackageManager packageManager = context.getPackageManager();

        int icon = 0;
        String title = "";
        try {
            ApplicationInfo applicationInfo = packageManager
                    .getApplicationInfo(context.getPackageName(), 0);
            icon = packageManager.getApplicationInfo(context.getPackageName(), 0).icon;
            title = packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
        }

        String message = extras.getString("message");
        boolean sound = false;
        if (extras.containsKey("sound")) {
            sound = Boolean.valueOf(extras.getString("sound"));
        }

        mIntent.putExtras(extras);

        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(title);
        builder.setSmallIcon(icon);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setContentIntent(pendingIntent);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);

        if (sound && PermissionUtils.permitted(context, "android.permission.VIBRATE")) {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE
                    | Notification.DEFAULT_LIGHTS);
        }

        return builder.build();

    }


}
