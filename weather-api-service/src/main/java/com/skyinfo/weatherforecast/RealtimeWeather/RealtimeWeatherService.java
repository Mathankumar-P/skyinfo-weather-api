package com.skyinfo.weatherforecast.RealtimeWeather;

import com.skyinfo.weatherforecast.common.Location;
import com.skyinfo.weatherforecast.common.RealtimeWeather;
import com.skyinfo.weatherforecast.geolocation.GeoLocationException;
import com.skyinfo.weatherforecast.location.LocationNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RealtimeWeatherService {
    private RealtimeWeatherRepository repository;
    public RealtimeWeatherService(RealtimeWeatherRepository repository) {
        super();
        this.repository = repository;
    }

    public RealtimeWeather getLocation (Location location) throws LocationNotFoundException {
        String countryCode = location.getCountryCode();
        String cityName = location.getCityName();
        RealtimeWeather realtimeWeather = repository.findByCountryCodeAndCity(countryCode,cityName);
        if(realtimeWeather == null)
            throw new LocationNotFoundException("No Location found with given country code and city name");

        return  realtimeWeather;
    }

}
