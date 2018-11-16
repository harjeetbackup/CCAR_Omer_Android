package com.location.callbacks;

public interface AppLocationListener {

    void locationReceived(String maxAddress, String pin, String state, String city, String subCity, String countryCode, double latitude, double longitude);
    void locationFailed();
}
