package com.skyinfo.weatherforecast.RealtimeWeather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skyinfo.weatherforecast.common.Location;
import jakarta.persistence.*;

import java.util.Date;
public class RealtimeWeatherDTO {

    private String location;
    private int temperature;
    private int humidity;
    private int precipitation;
    @JsonProperty("wind_speed")
    private int windSpeed;

    private String status;

    private Date lastUpdated;



    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
