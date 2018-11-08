package com.location.callbacks;

/**
 * Created by ashwanisingh on 02/03/18.
 */

public interface AppLocationListener {

    void locationReceived(String maxAddress, String pin, String state, String city, String subCity, String countryCode, double latitude, double longitude);
    void locationFailed();
}
