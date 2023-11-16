package com.skyinfo.weatherforecast.RealtimeWeather;

import com.skyinfo.weatherforecast.CommonUtility;
import com.skyinfo.weatherforecast.common.Location;
import com.skyinfo.weatherforecast.common.RealtimeWeather;
import com.skyinfo.weatherforecast.geolocation.GeoLocationException;
import com.skyinfo.weatherforecast.geolocation.GeoLocationService;
import com.skyinfo.weatherforecast.location.LocationNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/realtime")
public class RealtimeWeatherApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeWeatherApiController.class);
    private GeoLocationService geoLocationService;
    private RealtimeWeatherService realtimeWeatherService;

    private ModelMapper modelMapper;

    public RealtimeWeatherApiController(GeoLocationService geoLocationService, RealtimeWeatherService realtimeWeatherService, ModelMapper modelMapper) {
        this.geoLocationService = geoLocationService;
        this.realtimeWeatherService = realtimeWeatherService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<?> getRealtimeWeatherByIPAddress(HttpServletRequest request){
        String ipAddress = CommonUtility.getIPAddress(request);
        try {
            Location locationFromIp = geoLocationService.getLocationService(ipAddress);
            RealtimeWeather weather = realtimeWeatherService.getLocation(locationFromIp);
            return ResponseEntity.ok(weather);
        } catch (GeoLocationException e) {
            LOGGER.error(e.getMessage(),e);
            return ResponseEntity.badRequest().build();
        } catch (LocationNotFoundException e){
            LOGGER.error(e.getMessage(),e);
            return ResponseEntity.notFound().build();
        }

    }
}
