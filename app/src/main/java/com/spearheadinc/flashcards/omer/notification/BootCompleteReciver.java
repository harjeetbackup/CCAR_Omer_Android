package com.spearheadinc.flashcards.omer.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.spearheadinc.flashcards.apputil.Alarm;

public class BootCompleteReciver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        SchduleUpcoingAlarmTask asyncTask = new SchduleUpcoingAlarmTask(context);
        asyncTask.execute();




    }
    public class SchduleUpcoingAlarmTask extends android.os.AsyncTask<String,String,String>{
        private Context context;

        public SchduleUpcoingAlarmTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            Alarm.readOmarDateToSetAlarm(context);
            return null;
        }
    }
}
