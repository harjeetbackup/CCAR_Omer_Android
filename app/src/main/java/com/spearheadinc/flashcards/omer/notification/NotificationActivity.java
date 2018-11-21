package com.spearheadinc.flashcards.omer.notification;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;

import com.spearheadinc.flashcards.apputil.Alarm;
import com.spearheadinc.flashcards.apputil.AppPreference;
import com.spearheadinc.flashcards.omer.R;
import com.spearheadinc.flashcards.sunrisesunset.SunriseSunsetCalculator;
import com.spearheadinc.flashcards.sunrisesunset.dto.Location;

import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity {
    private TimePicker mTimePiker;
    private CheckBox mLocalCheckBox, mSunsetCheckBox;
    private LocationManager mLocationManager;
    private android.location.Location mLocation;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mTimePiker = findViewById(R.id.timePiker);
        mLocalCheckBox = findViewById(R.id.localCheckBox);
        mSunsetCheckBox = findViewById(R.id.SunsetCheckBox);

    }

    public void onCheckBoxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.localCheckBox:
                if (checked) {
                    mTimePiker.setVisibility(View.VISIBLE);
                    mSunsetCheckBox.setChecked(false);
                    AppPreference.getInstance(NotificationActivity.this).setCurrentTime(mTimePiker.getCurrentHour() + ":" + mTimePiker.getCurrentMinute());
                    Alarm.readOmarDateToSetAlarm(NotificationActivity.this);

                } else {
                    mTimePiker.setVisibility(View.GONE);
                }
                break;
            case R.id.SunsetCheckBox:
                if (checked) {
                    mLocalCheckBox.setChecked(false);
                    mTimePiker.setVisibility(View.GONE);

                }
        }
    }

}

