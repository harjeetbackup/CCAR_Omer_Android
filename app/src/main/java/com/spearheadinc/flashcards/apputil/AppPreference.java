package com.spearheadinc.flashcards.apputil;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spearheadinc.flashcards.omer.retrofit.ItemsBean;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
    public  void putList( ArrayList<ItemsBean> list) {
        Gson gson = new Gson();
        mEditor.putString("OMAR_CARDS", gson.toJson(list));
        mEditor.apply();
    }
    public  ArrayList<ItemsBean> getList() {
        Type typeOfT = TypeToken.getParameterized(ArrayList.class, ItemsBean.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(mPreferences.getString("OMAR_CARDS", null), typeOfT);
    }

    public void setCurrentTime(String currentDate){
        mEditor.putString("CurrentDate", currentDate);
        mEditor.apply();
    }
    public String getCurrentTime(){
        String currentDate = mPreferences.getString("CurrentDate","");
        String date =  currentDate;
        return date;
    }
    public void setOmarDate(ArrayList date) {
        mEditor.putString("date", String.valueOf(date));
        mEditor.apply();
    }


    public String getOmarDate() {
        String omarDate = mPreferences.getString("date","");
        String dat =  omarDate;
        return dat;
    }
    public void setCheckboxState(final boolean isChecked){
        mEditor.putBoolean("checked",isChecked);
        mEditor.commit();
    }
    public boolean getCheckboxSState(){
        boolean checkbox = mPreferences.getBoolean("checked",false);
        boolean check = checkbox;
        return check;
    }

    public void setSunCheckboxState(final boolean isChecked){
        mEditor.putBoolean("sunChecked",isChecked);
        mEditor.commit();
    }
    public boolean getSunCheckboxSState(){
        boolean checkbox = mPreferences.getBoolean("sunChecked",false);
        boolean check = checkbox;
        return check;
    }
    public void setSunsetTime(String currentDate){
        mEditor.putString("SunsetTime", currentDate);
        mEditor.apply();
    }
    public String getSunsetTime(){
        String currentDate = mPreferences.getString("SunsetTime","");
        String date =  currentDate;
        return date;
    }
    public void setTimePikerHour(int hour){
        mEditor.putInt("CurrentHour", hour);
        mEditor.apply();
    }
    public int getTimePikerHour(){
        int currentHour = mPreferences.getInt("CurrentHour",0);
        int hour =  currentHour;
        return hour;
    }

    public void setTimePikerMinute(int minute){
        mEditor.putInt("CurrentMinute", minute);
        mEditor.apply();
    }
    public int getTimePikerMinute(){
        int currentMinute = mPreferences.getInt("CurrentMinute",0);
        int minute =  currentMinute;
        return minute;
    }
}
