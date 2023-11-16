package com.skyinfo.weatherforecast.geolocation;

public class GeoLocationException extends Exception{
    public GeoLocationException(String message) {
        super(message);
    }

    public GeoLocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
