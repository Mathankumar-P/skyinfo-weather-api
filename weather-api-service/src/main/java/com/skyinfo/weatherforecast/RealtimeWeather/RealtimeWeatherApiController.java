package com.skyinfo.weatherforecast.RealtimeWeather;

import com.skyinfo.weatherforecast.CommonUtility;
import com.skyinfo.weatherforecast.common.Location;
import com.skyinfo.weatherforecast.common.RealtimeWeather;
import com.skyinfo.weatherforecast.geolocation.GeoLocationException;
import com.skyinfo.weatherforecast.geolocation.GeoLocationService;
import com.skyinfo.weatherforecast.location.LocationNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            RealtimeWeatherDTO dto = modelMapper.map(weather, RealtimeWeatherDTO.class);
            return ResponseEntity.ok(dto);
        } catch (GeoLocationException e) {
            LOGGER.error(e.getMessage(),e);
            return ResponseEntity.badRequest().build();
        } catch (LocationNotFoundException e){
            LOGGER.error(e.getMessage(),e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getWeatherById (@PathVariable String code)throws  LocationNotFoundException{
        try {
            RealtimeWeather weather = realtimeWeatherService.getLocationById(code);
            return  ResponseEntity.ok(entityToDto(weather));
        } catch (LocationNotFoundException e){
            LOGGER.error(e.getMessage(),e);
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{code}")
    public ResponseEntity<?> updateWeather(@PathVariable String code, @RequestBody @Valid RealtimeWeather weather){
        weather.setLocationCode(code);
        try {
            weather =  realtimeWeatherService.updateWeather(code,weather);
            return  ResponseEntity.ok(entityToDto(weather));
        } catch (LocationNotFoundException e) {
           return ResponseEntity.notFound().build();
        }
    }

    private RealtimeWeatherDTO entityToDto(RealtimeWeather realtimeWeather){
        return modelMapper.map(realtimeWeather, RealtimeWeatherDTO.class);
    }

}
