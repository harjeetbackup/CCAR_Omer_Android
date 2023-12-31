package com.spearheadinc.flashcards.apputil;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.spearheadinc.flashcards.omer.DeckView;
import com.spearheadinc.flashcards.omer.R;
import com.spearheadinc.flashcards.omer.notification.AlarmReceiver;
import com.spearheadinc.flashcards.omer.retrofit.ItemsBean;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Alarm {

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

        Intent intent = new Intent(context , DeckView.class);
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
                R.drawable.icon);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( context, ChannelID)
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent)
                .setOnlyAlertOnce(true);

        notificationManager.notify(0, mNotificationBuilder.build());
    }


    public static void setAlarm(Context context, long timeInMillies) {
        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int requestCode = 1;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillies, pendingIntent);

    }


    public static void readOmarDateToSetAlarm(Context context) {
        Calendar currentTime = Calendar.getInstance();
        ArrayList<ItemsBean> omarList = AppPreference.getInstance(context).getList();
        String savedTime = AppPreference.getInstance(context).getCurrentTime();

        if (omarList != null) {
            for (ItemsBean bean : omarList) {
                String dateStr = bean.getDate() + "-" + savedTime;
                try {
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd-HH:mm").parse(dateStr);
                    long omarMillis = date1.getTime();
                    long currentTimeInMillies = currentTime.getTimeInMillis();
                    if (currentTimeInMillies<= omarMillis) {
                        setAlarm(context, omarMillis);
                        break;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static long getCurrentDateInMillsec(long millsec) {
        DateFormat simple = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        Date result = new Date(millsec);
        String h =  simple.format(result);
        Date date1 = null;
        long omarMillis = 0;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd-HH:mm").parse(h);
            omarMillis = date1.getTime() ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return omarMillis;
    }
    public static void readSunsetTimeForAlarm(Context context){
        Calendar currentTime = Calendar.getInstance();
        ArrayList<ItemsBean> omarList = AppPreference.getInstance(context).getList();
        String savedTime = AppPreference.getInstance(context).getSunsetTime();

        if (omarList != null) {
            for (ItemsBean bean : omarList) {
                String dateStr = bean.getDate() + "-" + savedTime;
                try {
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd-HH:mm").parse(dateStr);
                    long omarMillis = date1.getTime();
                    if (getCurrentDateInMillsec(currentTime.getTimeInMillis()) == omarMillis) {
                        setAlarm(context, omarMillis);
                        break;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static  void removeAlarmByUser(Context context ){
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = 1;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }
    public static void removeSunsetAlarm(Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = 1;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
