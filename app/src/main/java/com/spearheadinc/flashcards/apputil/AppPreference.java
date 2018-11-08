package com.spearheadinc.flashcards.apputil;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;


    private static AppPreference mUser;


    private AppPreference(Context context) {
        try {
            mPreferences = context.getSharedPreferences("userPref.xml", Context.MODE_PRIVATE);
            mEditor = mPreferences.edit();
        } catch (NumberFormatException e) {

        }

    }

    public static final AppPreference getInstance(Context context) {
        if(mUser == null) {
            try {
                mUser = new AppPreference(context);
            } catch (Exception e) {

            }
        }

        return mUser;
    }


    public void setLatitude(double latitude) {
        mEditor.putLong("latitude", (long)latitude);
        mEditor.apply();
    }


    public double getLatitude() {
        long latitude = mPreferences.getLong("latitude", 0);
        double lat = (double) latitude;
        return lat;
    }


    public void setLongitude(double longitude) {
        mEditor.putLong("longitude", (long)longitude);
        mEditor.apply();
    }


    public double getLongitude() {
        long longitude = mPreferences.getLong("longitude", 0);
        double lon = (double) longitude;
        return lon;
    }

}
