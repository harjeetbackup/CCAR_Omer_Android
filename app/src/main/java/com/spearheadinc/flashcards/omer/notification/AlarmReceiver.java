package com.spearheadinc.flashcards.omer.notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.spearheadinc.flashcards.apputil.Alarm;
import com.spearheadinc.flashcards.apputil.AppPreference;
import com.spearheadinc.flashcards.omer.R;
import com.spearheadinc.flashcards.omer.retrofit.ItemsBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {
    private   String mTitle;

    @Override
    public void onReceive(Context context, Intent intent) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        ArrayList<ItemsBean> omarList = AppPreference.getInstance(context).getList();
        for (int i= 0; i<omarList.size();i++ ){
            if (formattedDate.equals(omarList.get(i).getDate())){
                mTitle = omarList.get(i).getTitle();
            }
        }
        Map<String, String> data = new HashMap<>();
        data.put("title", mTitle);
        data.put("body", "click here to see card of the day");

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
