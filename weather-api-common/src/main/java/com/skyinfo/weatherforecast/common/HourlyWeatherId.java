package com.skyinfo.weatherforecast.common;

import jakarta.persistence.Embeddable;

@Embeddable
public class HourlyWeatherId {
    private int hourOfDay;
    private Location location;

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
