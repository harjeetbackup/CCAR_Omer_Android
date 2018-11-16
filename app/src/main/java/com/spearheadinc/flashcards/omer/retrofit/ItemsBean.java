package com.spearheadinc.flashcards.omer.retrofit;

import java.util.Calendar;

public class ItemsBean  {
    /**
     * date : 2019-04-21
     * category : omer
     * hebrew : א׳ בעומר
     * title : 1st day of the Omer
     */

    private String date;
    private String category;
    private String hebrew;
    private String title;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHebrew() {
        return hebrew;
    }

    public void setHebrew(String hebrew) {
        this.hebrew = hebrew;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
