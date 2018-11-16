package com.spearheadinc.flashcards.omer.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.spearheadinc.flashcards.apputil.AppPreference;
import com.spearheadinc.flashcards.omer.FlashCards;
import com.spearheadinc.flashcards.omer.R;
import com.spearheadinc.flashcards.omer.retrofit.ItemsBean;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat mdformat = new SimpleDateFormat("");
    private TimePicker mTimePiker;
    private Button mAlarmSetBtn;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    final static int RQS_1 = 1;
    private String time;
    private String date;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mTimePiker = findViewById(R.id.timePiker);
        mAlarmSetBtn = findViewById(R.id.setAlarmBtn);

//         hour = mTimePiker.getHour();
//        minute = mTimePiker.getMinute();
//        second = 0;
//        time =  new Time(hour,minute,0);

         mTimePiker.is24HourView();
        mTimePiker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                time =  mTimePiker.getCurrentHour() + ":" + mTimePiker.getCurrentMinute() ;
            }


        });


        mAlarmSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                ArrayList<ItemsBean> omarList = AppPreference.getInstance(NotificationActivity.this).getList();
                for (int i = 0; i < omarList.size(); i++) {
                    String dateResponse = omarList.get(i).getDate();

                    getTimeDate(date, cal);
                    Map<String, String> data = new HashMap<>();
                    data.put("title", getResources().getString(R.string.Notify));
                    data.put("body", "This is notification Message");
                    AlarmReceiver.createNotification(NotificationActivity.this, data);
                    setAlarm(cal);
                }
            }

        });

    }

    private void getTimeDate(String dateResponse, Calendar calendar) {

    }

    private void setAlarm(Calendar targetCal) {
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }
}

