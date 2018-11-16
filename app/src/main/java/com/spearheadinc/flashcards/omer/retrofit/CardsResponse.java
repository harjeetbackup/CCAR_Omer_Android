package com.spearheadinc.flashcards.omer.retrofit;

import java.util.ArrayList;

public class CardsResponse {


    /**
     * items : [{"date":"2019-04-21","category":"omer","hebrew":"א׳ בעומר","title":"1st day of the Omer"},{"title":"2nd day of the Omer","hebrew":"ב׳ בעומר","category":"omer","date":"2019-04-22"},{"date":"2019-04-23","category":"omer","hebrew":"ג׳ בעומר","title":"3rd day of the Omer"},{"hebrew":"ד׳ בעומר","title":"4th day of the Omer","date":"2019-04-24","category":"omer"},{"title":"5th day of the Omer","hebrew":"ה׳ בעומר","category":"omer","date":"2019-04-25"},{"date":"2019-04-26","category":"omer","hebrew":"ו׳ בעומר","title":"6th day of the Omer"},{"hebrew":"ז׳ בעומר","title":"7th day of the Omer","date":"2019-04-27","category":"omer"},{"hebrew":"ח׳ בעומר","title":"8th day of the Omer","date":"2019-04-28","category":"omer"},{"hebrew":"ט׳ בעומר","title":"9th day of the Omer","date":"2019-04-29","category":"omer"},{"date":"2019-04-30","category":"omer","hebrew":"י׳ בעומר","title":"10th day of the Omer"},{"category":"omer","date":"2019-05-01","title":"11th day of the Omer","hebrew":"י״א בעומר"},{"date":"2019-05-02","category":"omer","hebrew":"י״ב בעומר","title":"12th day of the Omer"},{"title":"13th day of the Omer","hebrew":"י״ג בעומר","category":"omer","date":"2019-05-03"},{"title":"14th day of the Omer","hebrew":"י״ד בעומר","category":"omer","date":"2019-05-04"},{"title":"15th day of the Omer","hebrew":"ט״ו בעומר","category":"omer","date":"2019-05-05"},{"category":"omer","date":"2019-05-06","title":"16th day of the Omer","hebrew":"ט״ז בעומר"},{"hebrew":"י״ז בעומר","title":"17th day of the Omer","date":"2019-05-07","category":"omer"},{"title":"18th day of the Omer","hebrew":"י״ח בעומר","category":"omer","date":"2019-05-08"},{"title":"19th day of the Omer","hebrew":"י״ט בעומר","category":"omer","date":"2019-05-09"},{"category":"omer","date":"2019-05-10","title":"20th day of the Omer","hebrew":"כ׳ בעומר"},{"hebrew":"כ״א בעומר","title":"21st day of the Omer","date":"2019-05-11","category":"omer"},{"date":"2019-05-12","category":"omer","hebrew":"כ״ב בעומר","title":"22nd day of the Omer"},{"hebrew":"כ״ג בעומר","title":"23rd day of the Omer","date":"2019-05-13","category":"omer"},{"date":"2019-05-14","category":"omer","hebrew":"כ״ד בעומר","title":"24th day of the Omer"},{"category":"omer","date":"2019-05-15","title":"25th day of the Omer","hebrew":"כ״ה בעומר"},{"category":"omer","date":"2019-05-16","title":"26th day of the Omer","hebrew":"כ״ו בעומר"},{"date":"2019-05-17","category":"omer","hebrew":"כ״ז בעומר","title":"27th day of the Omer"},{"title":"28th day of the Omer","hebrew":"כ״ח בעומר","category":"omer","date":"2019-05-18"},{"hebrew":"כ״ט בעומר","title":"29th day of the Omer","date":"2019-05-19","category":"omer"},{"title":"30th day of the Omer","hebrew":"ל׳ בעומר","category":"omer","date":"2019-05-20"},{"title":"31st day of the Omer","hebrew":"ל״א בעומר","category":"omer","date":"2019-05-21"},{"title":"32nd day of the Omer","hebrew":"ל״ב בעומר","category":"omer","date":"2019-05-22"},{"date":"2019-05-23","category":"omer","hebrew":"ל״ג בעומר","title":"33rd day of the Omer"},{"hebrew":"ל״ד בעומר","title":"34th day of the Omer","date":"2019-05-24","category":"omer"},{"hebrew":"ל״ה בעומר","title":"35th day of the Omer","date":"2019-05-25","category":"omer"},{"title":"36th day of the Omer","hebrew":"ל״ו בעומר","category":"omer","date":"2019-05-26"},{"date":"2019-05-27","category":"omer","hebrew":"ל״ז בעומר","title":"37th day of the Omer"},{"date":"2019-05-28","category":"omer","hebrew":"ל״ח בעומר","title":"38th day of the Omer"},{"date":"2019-05-29","category":"omer","hebrew":"ל״ט בעומר","title":"39th day of the Omer"},{"title":"40th day of the Omer","hebrew":"מ׳ בעומר","category":"omer","date":"2019-05-30"},{"category":"omer","date":"2019-05-31","title":"41st day of the Omer","hebrew":"מ״א בעומר"},{"hebrew":"מ״ב בעומר","title":"42nd day of the Omer","date":"2019-06-01","category":"omer"},{"date":"2019-06-02","category":"omer","hebrew":"מ״ג בעומר","title":"43rd day of the Omer"},{"hebrew":"מ״ד בעומר","title":"44th day of the Omer","date":"2019-06-03","category":"omer"},{"category":"omer","date":"2019-06-04","title":"45th day of the Omer","hebrew":"מ״ה בעומר"},{"category":"omer","date":"2019-06-05","title":"46th day of the Omer","hebrew":"מ״ו בעומר"},{"category":"omer","date":"2019-06-06","title":"47th day of the Omer","hebrew":"מ״ז בעומר"},{"hebrew":"מ״ח בעומר","title":"48th day of the Omer","date":"2019-06-07","category":"omer"},{"title":"49th day of the Omer","hebrew":"מ״ט בעומר","category":"omer","date":"2019-06-08"}]
     * location : {"geo":"none"}
     * title : Hebcal 2019
     * link : https://www.hebcal.com/hebcal/?v=1;maj=off;min=off;mod=off;nx=off;year=2019;month=x;ss=off;mf=off;c=off;geo=none;s=off;o=on
     * date : 2018-11-06T13:21:12-00:00
     */

    private LocationBean location;
    private String title;
    private String link;
    private String date;
    private ArrayList<ItemsBean> items;

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ItemsBean> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemsBean> items) {
        this.items = items;
    }

    public static class LocationBean {
        /**
         * geo : none
         */

        private String geo;

        public String getGeo() {
            return geo;
        }

        public void setGeo(String geo) {
            this.geo = geo;
        }
    }
}
