package com.skyinfo.weatherforecast.RealtimeWeather;

import com.skyinfo.weatherforecast.common.Location;
import com.skyinfo.weatherforecast.common.RealtimeWeather;
import com.skyinfo.weatherforecast.geolocation.GeoLocationException;
import com.skyinfo.weatherforecast.location.LocationNotFoundException;
import com.skyinfo.weatherforecast.location.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RealtimeWeatherService {
    private RealtimeWeatherRepository repository;
    private LocationRepository locationRepository;
    public RealtimeWeatherService(RealtimeWeatherRepository repository , LocationRepository locationRepository) {
        super();
        this.repository = repository;
        this.locationRepository = locationRepository;
    }

    public RealtimeWeather getLocation (Location location) throws LocationNotFoundException {
        String countryCode = location.getCountryCode();
        String cityName = location.getCityName();
        RealtimeWeather realtimeWeather = repository.findByCountryCodeAndCity(countryCode,cityName);
        if(realtimeWeather == null)
            throw new LocationNotFoundException("No Location found with given country code and city name");
        System.out.println();
        return  realtimeWeather;
    }

    public RealtimeWeather getLocationById(String locationCode) throws LocationNotFoundException {
        RealtimeWeather weather = repository.findByLocationCode(locationCode);
        if(weather==null) throw new LocationNotFoundException("No Location Found with the Given Code : "+locationCode);
        return weather;
    }

    public RealtimeWeather updateWeather(String locationCode, RealtimeWeather realtimeWeather) throws LocationNotFoundException {
        Location location = locationRepository.findByCode(locationCode);
        if(location==null) throw new LocationNotFoundException("No Locations found for the given location Code : " +locationCode);
        realtimeWeather.setLocation(location);
        realtimeWeather.setLastUpdated(new Date());
        if(location.getRealtimeWeather()==null) {
            location.setRealtimeWeather(realtimeWeather);
            Location updatedLocation = locationRepository.save(location);
        }
        return repository.save(realtimeWeather);
    }
}
