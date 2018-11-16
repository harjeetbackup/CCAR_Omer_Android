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
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.spearheadinc.flashcards.apputil.Alarm;
import com.spearheadinc.flashcards.omer.R;

import java.util.HashMap;
import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Map<String, String> data = new HashMap<>();
        data.put("title", context.getResources().getString(R.string.Notify));
        data.put("body", "This is notification Message");

        //It creates the notification
        Alarm.createNotification(context, data);

        // Once above notification is created,
        // then it sets new alarm interval of omar upcoming date.
        AsyncTask asyncTask = new AsyncTask(context);
        asyncTask.execute();

    }


    private class AsyncTask extends android.os.AsyncTask< String, String, String>{
        private Context context;

        public AsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            Alarm.readOmarDateToSetAlarm(context);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }




}
