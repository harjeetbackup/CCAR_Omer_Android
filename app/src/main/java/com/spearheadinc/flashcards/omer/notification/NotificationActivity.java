package com.spearheadinc.flashcards.omer.notification;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.spearheadinc.flashcards.apputil.Alarm;
import com.spearheadinc.flashcards.apputil.AppPreference;
import com.spearheadinc.flashcards.omer.R;

import org.shredzone.commons.suncalc.SunTimes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {
    private TimePicker mTimePiker;
    private CheckBox mLocalCheckBox, mSunsetCheckBox;
    private RelativeLayout mBackBtn;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        AppPreference.getInstance(NotificationActivity.this).getCheckboxSState();
        setContentView(R.layout.activity_notification);
        mTimePiker = findViewById(R.id.timePiker);
        mLocalCheckBox = findViewById(R.id.localCheckBox);
        mSunsetCheckBox = findViewById(R.id.SunsetCheckBox);
        mBackBtn = findViewById(R.id.relative_back);

        String lastUpdatedTime = AppPreference.getInstance(NotificationActivity.this).getCurrentTime();

        boolean check = AppPreference.getInstance(NotificationActivity.this).getCheckboxSState();
        mLocalCheckBox.setChecked(false);
        if (check) {
            mLocalCheckBox.setChecked(true);
            mSunsetCheckBox.setChecked(false);
            mTimePiker.setVisibility(View.VISIBLE);
        } else {

            mLocalCheckBox.setChecked(false);
//           mSunsetCheckBox.setChecked(true);
            mTimePiker.setVisibility(View.GONE);

        }

        boolean check1 = AppPreference.getInstance(NotificationActivity.this).getSunCheckboxSState();
        mSunsetCheckBox.setChecked(false);
        if (check1) {
            mSunsetCheckBox.setChecked(true);
            mLocalCheckBox.setChecked(false);
            mTimePiker.setVisibility(View.GONE);
        } else {
            mSunsetCheckBox.setChecked(false);
//           mLocalCheckBox.setChecked(true);
        }

        String[] time = lastUpdatedTime.split(":");
        int hour = Integer.parseInt(time[0].trim());
        int min = Integer.parseInt(time[1].trim());
        mTimePiker.setHour(hour);
        mTimePiker.setMinute(min);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onCheckBoxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.localCheckBox:
                if (checked) {
                    mTimePiker.setVisibility(View.VISIBLE);
                    mSunsetCheckBox.setChecked(false);
                    AppPreference.getInstance(NotificationActivity.this).setCheckboxState(true);

                    mTimePiker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                        @Override
                        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                            hourOfDay = mTimePiker.getCurrentHour();
                            minute = mTimePiker.getCurrentMinute();
                            AppPreference.getInstance(NotificationActivity.this).setCurrentTime(hourOfDay + ":" + minute);
                            Alarm.readOmarDateToSetAlarm(NotificationActivity.this);

                        }
                    });

                }
                if (!checked) {
                    AppPreference.getInstance(NotificationActivity.this).setCheckboxState(false);
                    mTimePiker.setVisibility(View.GONE);
                    AppPreference.getInstance(NotificationActivity.this).getCurrentTime();
                    deleteAlarm();

                }
                break;
            case R.id.SunsetCheckBox:
                if (checked) {
                    mLocalCheckBox.setChecked(false);
                    mTimePiker.setVisibility(View.GONE);
                    AppPreference.getInstance(NotificationActivity.this).setSunCheckboxState(true);
                    Date date = new Date();
                    SunTimes times = SunTimes.compute()
                            .on(date)
                            .at(AppPreference.getInstance(NotificationActivity.this).getLatitude(),
                                    AppPreference.getInstance(NotificationActivity.this).getLongitude())
                            .execute();
                    Date sunsetTime = times.getSet();
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    String sunDownTime = dateFormat.format(sunsetTime);
                    AppPreference.getInstance(NotificationActivity.this).setSunsetTime(sunDownTime);
                    Alarm.readSunsetTimeForAlarm(NotificationActivity.this);
                    Log.i("Time", "sunset time");
                }
                if (!checked) {
                    AppPreference.getInstance(NotificationActivity.this).setSunCheckboxState(false);
//                    removeSunsetAlarm();
                }
        }

    }

//    private void removeSunsetAlarm() {
//        Alarm.removeSunsetAlarm(NotificationActivity.this);
//    }

    private void deleteAlarm() {
        Alarm.removeAlarmByUser(NotificationActivity.this);

    }

}

