package com.skyinfo.weatherforecast.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
@Table(name="locations")
public class Location {
    @Id
    @Column(length = 12,nullable = false, unique = true)
    @NotNull(message = "Location name cannot be null")
    @Length(min=3, max = 12,  message = "Location code must have 3-12 characters")
    private String code;
    @Column(length = 128, nullable = false)
    @JsonProperty("city_name")
    @NotBlank(message = "City name cannot be blank")
    @NotNull(message = "City name cannot be null")
    private String cityName;
    @Column(length = 128)
    @JsonProperty("region_name")
    @NotNull(message = "Region  name cannot be Null")
    @NotNull(message = "Region name cannot be null")
    private String regionName;
    @Column(length = 64, nullable = false)
    @JsonProperty("country_name")
    @NotBlank(message = "Country name cannot be blank")
    @NotNull(message = "Country name cannot be null")
    private String countyName;
    @Column(length = 2, nullable = false)
    @JsonProperty("country_code")
    @NotNull(message = "Country code cannot be null")
    @Length(min=2, max =2, message = "Country Code must have 2 characters")
    private String countryCode;
    private boolean enabled;
    @JsonIgnore
    private boolean trashed;

    @OneToOne(mappedBy ="location" , cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private RealtimeWeather realtimeWeather;

    public Location(){
    }
    public Location(String cityName, String regionName, String countyName, String countryCode) {
        this.cityName = cityName;
        this.regionName = regionName;
        this.countyName = countyName;
        this.countryCode = countryCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isTrashed() {
        return trashed;
    }

    public void setTrashed(boolean trashed) {
        this.trashed = trashed;
    }

    public RealtimeWeather getRealtimeWeather() {
        return realtimeWeather;
    }

    public void setRealtimeWeather(RealtimeWeather realtimeWeather) {
        this.realtimeWeather = realtimeWeather;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return enabled == location.enabled && trashed == location.trashed && Objects.equals(code, location.code) && Objects.equals(cityName, location.cityName) && Objects.equals(regionName, location.regionName) && Objects.equals(countyName, location.countyName) && Objects.equals(countryCode, location.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, cityName, regionName, countyName, countryCode, enabled, trashed);
    }

    @Override
    public String toString() {
        return "Location{" +
                "code='" + code + '\'' +
                ", cityName='" + cityName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", countyName='" + countyName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", enabled=" + enabled +
                ", trashed=" + trashed +
                '}';
    }
}
