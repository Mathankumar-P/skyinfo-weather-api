package com.skyinfo.weatherforecast.realtime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyinfo.weatherforecast.RealtimeWeather.RealtimeWeatherApiController;
import com.skyinfo.weatherforecast.RealtimeWeather.RealtimeWeatherService;
import com.skyinfo.weatherforecast.common.Location;
import com.skyinfo.weatherforecast.common.RealtimeWeather;
import com.skyinfo.weatherforecast.geolocation.GeoLocationException;
import com.skyinfo.weatherforecast.geolocation.GeoLocationService;
import com.skyinfo.weatherforecast.location.LocationNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import java.util.Date;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(RealtimeWeatherApiController.class)
public class RealTimeApiControllerTests {
    private static final String END_PATH = "/v1/realtime";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    RealtimeWeatherService realtimeWeatherService;
    @MockBean
    GeoLocationService geoLocationService;

    @Test
    public void testShouldReturnStatus400BadRequest() throws Exception {
        Mockito.when(geoLocationService.getLocationService(Mockito.anyString())).thenThrow(GeoLocationException.class);
        mockMvc.perform(get(END_PATH)).andExpect(status().isBadRequest()).andDo(print());
    }
   @Test
    public void testShouldReturnStatus404NotFound() throws Exception {
        Location location = new Location();
        Mockito.when(geoLocationService.getLocationService(Mockito.anyString())).thenReturn(location);
        Mockito.when(realtimeWeatherService.getLocation(location)).thenThrow(LocationNotFoundException.class);
        mockMvc.perform(get(END_PATH)).andExpect(status().isNotFound()).andDo(print());
    }
//    @Test
//    public void testShouldReturnStatus200Ok() throws Exception {
//        Location location = new Location();
//        location.setCode("SFCA_USA");
//        location.setCityName("San Francisco");
//        location.setRegionName("California");
//        location.setCountyName("United States of America");
//        location.setCountryCode("US");
//
//        RealtimeWeather realtimeWeather = new RealtimeWeather();
//        realtimeWeather.setTemperature(22);
//        realtimeWeather.setHumidity(44);
//        realtimeWeather.setWindSpeed(33);
//        realtimeWeather.setPrecipitation(88);
//        realtimeWeather.setLastUpdated(new Date());
//        realtimeWeather.setLocation(location);
//
//
//        Mockito.when(geoLocationService.getLocationService(Mockito.anyString())).thenReturn(location);
//        Mockito.when(realtimeWeatherService.getLocation(location)).thenReturn(realtimeWeather);
//        String expectedLocation = location.getCityName()+ ", "+location.getRegionName()+", "+location.getCountyName();
//        mockMvc.perform(get(END_PATH)).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
//                .andExpect((ResultMatcher) jsonPath("$.location", is(expectedLocation))).andDo(print());
//    }

    @Test
    public void getLocationByIdShouldReturn404NotFound() throws Exception {
        String locationCode = "Abc_CS";
        Mockito.when(realtimeWeatherService.getLocationById(locationCode)).thenThrow(LocationNotFoundException.class);
        String URI =END_PATH+"/"+locationCode;
        mockMvc.perform(get(URI)).
                andExpect(status().isNotFound()).
                andDo(print());
    }

    @Test
    public void testGetLocationByIdShouldReturn200Ok() throws Exception{
        Location location = new Location();
        location.setCode("SFCA_USA");
        location.setCityName("San Francisco");
        location.setRegionName("California");
        location.setCountyName("United States of America");
        location.setCountryCode("US");

        RealtimeWeather realtimeWeather = new RealtimeWeather();
        realtimeWeather.setTemperature(22);
        realtimeWeather.setHumidity(44);
        realtimeWeather.setWindSpeed(33);
        realtimeWeather.setPrecipitation(88);
        realtimeWeather.setLastUpdated(new Date());
        realtimeWeather.setLocation(location);

        Mockito.when(realtimeWeatherService.getLocationById(location.getCode())).thenReturn(realtimeWeather);

        String expectedLocation = location.getCityName()+", "+  location.getRegionName()+", "+location.getCountyName();
        String URI = END_PATH+"/"+location.getCode();
        mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    public void testUpdateShouldReturn400BadRequest() throws Exception {
        String locationCode = "Abc_CS";
        String URI =END_PATH+"/"+locationCode;

        RealtimeWeather realtimeWeather = new RealtimeWeather();
        realtimeWeather.setTemperature(123);
        realtimeWeather.setHumidity(111);
        realtimeWeather.setWindSpeed(-22);
        realtimeWeather.setPrecipitation(1111);
        realtimeWeather.setStatus("Cl");

        String bodyContent = mapper.writeValueAsString(realtimeWeather);
        mockMvc.perform(put(URI).contentType("application/json").content(bodyContent)).
                andExpect(status().isBadRequest()).
                andDo(print());
    }

    @Test
    public void updateShouldReturn404NotFound() throws Exception {
        String locationCode = "Abc_CS";
        String URI =END_PATH+"/"+locationCode;

        RealtimeWeather realtimeWeather = new RealtimeWeather();
        realtimeWeather.setTemperature(13);
        realtimeWeather.setHumidity(11);
        realtimeWeather.setWindSpeed(22);
        realtimeWeather.setPrecipitation(11);
        realtimeWeather.setStatus("Cloudy");

        Mockito.when(realtimeWeatherService.updateWeather(locationCode,realtimeWeather)).thenThrow(LocationNotFoundException.class);
        String bodyContent = mapper.writeValueAsString(realtimeWeather);
        mockMvc.perform(put(URI).contentType("application/json").content(bodyContent)).
                andExpect(status().isNotFound()).
                andDo(print());
    }

@Test
    public void updateShouldReturn200OK ()throws Exception {
        String locationCode = "SFCA_USA";
        String URI =END_PATH+"/"+locationCode;

        Location location = new Location();
        location.setCode("SFCA_USA");
        location.setCityName("San Francisco");
        location.setRegionName("California");
        location.setCountyName("United States of America");
        location.setCountryCode("US");

        RealtimeWeather realtimeWeather = new RealtimeWeather();
        realtimeWeather.setTemperature(13);
        realtimeWeather.setHumidity(11);
        realtimeWeather.setWindSpeed(22);
        realtimeWeather.setPrecipitation(11);
        realtimeWeather.setStatus("Cloudy");
        realtimeWeather.setLastUpdated(new Date());
        realtimeWeather.setLocation(location);
        location.setRealtimeWeather(realtimeWeather);

        Mockito.when(realtimeWeatherService.updateWeather(locationCode,realtimeWeather)).thenThrow(LocationNotFoundException.class);
        String bodyContent = mapper.writeValueAsString(realtimeWeather);
        String expectedLocation = location.getCityName()+", "+  location.getRegionName()+", "+location.getCountyName();
        mockMvc.perform(put(URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }
}
