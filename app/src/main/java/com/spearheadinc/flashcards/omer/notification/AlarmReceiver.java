package com.spearheadinc.flashcards.omer.notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
