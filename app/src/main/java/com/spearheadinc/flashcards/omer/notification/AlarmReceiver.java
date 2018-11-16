package com.spearheadinc.flashcards.omer.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.spearheadinc.flashcards.omer.R;

import java.util.HashMap;
import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Map<String, String> data = new HashMap<>();
        data.put("title", context.getResources().getString(R.string.Notify));
        data.put("body", "This is notification Message");

        createNotification(context, data);
    }


    public static void createNotification (Context context, Map<String, String> getData) {

        String body = getData.get("body");
        String title = getData.get("title");

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        CharSequence name="APP ss";
        String desc="this is notific";
        int imp=NotificationManager.IMPORTANCE_HIGH;
        final String ChannelID="my_channel_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(ChannelID, name, imp);
            mChannel.setDescription(desc);
            mChannel.setLightColor(Color.CYAN);
            mChannel.canShowBadge();
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent intent = new Intent(context , NotificationActivity.class );
        intent.putExtra("from", "pushNotification");
        for (Map.Entry<String, String> entry : getData.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            intent.putExtra(key, val);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent resultIntent = PendingIntent.getActivity( context , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.icon_facebook40);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( context, ChannelID)
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        notificationManager.notify(0, mNotificationBuilder.build());
    }

}
