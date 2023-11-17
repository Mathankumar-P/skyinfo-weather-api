package com.skyinfo.weatherforecast.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "realtime_weather")
public class RealtimeWeather {

    @Id @Column(name ="location_code")
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private String locationCode;
    @Range(min=-50, max=50 , message = "Temperature must be in the range of -50 to 50")
    private int temperature;
    @Range(min=0, max=100 , message = "Humidity Must Be In The Range of 0 to 100")
    private int humidity;
    @Range(min=0, max=100 , message = "Precipitation Must Be In The Range of 0 to 100")
    private int precipitation;
    @JsonProperty("wind_speed")
    @Range(min=0, max=200 , message = "Wind Speed Must Be In The Range of 0 to 100")
    private int windSpeed;
    @Column(length = 50)
    @NotNull(message = "Status Must Not Be Empty")
    @NotBlank(message = "Status Must Not Be Blank")
    @Length(min=3, max =50, message ="Status must be in 3-50 characters")
    private String status;

    @JsonProperty("last_updated")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-mm-dd 'T' HH:mm:ss 'z' ")
    @JsonIgnore
    private Date lastUpdated;

    @OneToOne
    @JoinColumn(name = "location_code")
    @MapsId
    private Location location;

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.setLocationCode(location.getCode());
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealtimeWeather weather = (RealtimeWeather) o;
        return Objects.equals(locationCode, weather.locationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationCode);
    }
}
