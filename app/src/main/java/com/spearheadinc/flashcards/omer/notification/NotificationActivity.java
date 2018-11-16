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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.spearheadinc.flashcards.apputil.Alarm;
import com.spearheadinc.flashcards.apputil.AppPreference;
import com.spearheadinc.flashcards.omer.FlashCards;
import com.spearheadinc.flashcards.omer.R;
import com.spearheadinc.flashcards.omer.retrofit.ItemsBean;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.field.MillisDurationField;

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
    private TimePicker mTimePiker;
    private Button mAlarmSetBtn, mTestBtn;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mTimePiker = findViewById(R.id.timePiker);
        mAlarmSetBtn = findViewById(R.id.setAlarmBtn);
        mTestBtn = findViewById(R.id.test);


         mTimePiker.is24HourView();

        mAlarmSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreference.getInstance(NotificationActivity.this).setCurrentDate(mTimePiker.getCurrentHour() + ":" + mTimePiker.getCurrentMinute());

                Alarm.readOmarDateToSetAlarm(NotificationActivity.this);
            }

        });

    }




}

