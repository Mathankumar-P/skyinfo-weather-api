package com.skyinfo.weatherforecast.location;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyinfo.weatherforecast.RealtimeWeather.RealtimeWeatherRepository;
import com.skyinfo.weatherforecast.common.Location;
import com.skyinfo.weatherforecast.common.RealtimeWeather;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationApiController.class)
public class LocationApiControllerTests {
    private static final String END_POINT_PATH = "/v1/locations";

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper mapper;
    @MockBean LocationService service;
    @MockBean
    RealtimeWeatherRepository repository;

    @Test
    public void testAddShouldReturn400BadRequest() throws Exception {
        Location location = new Location();
        String bodyContent = mapper.writeValueAsString(location);
        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void shouldReturn201Created() throws Exception{
        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountyName("United States");
        location.setEnabled(true);
        Mockito.when(service.add(location)).thenReturn(location);
        String bodyContent = mapper.writeValueAsString(location);
        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }
    @Test
    public void testShouldReturn204NoContent() throws Exception {
        Mockito.when(service.list()).thenReturn(Collections.emptyList());
        mockMvc.perform(get(END_POINT_PATH)).andExpect(status().isNoContent()).andDo(print());
    }
    @Test
    public void testShouldReturn200Ok() throws Exception {
        Location location1 = new Location();
        location1.setCode("NYC_USA");
        location1.setCityName("New York");
        location1.setRegionName("New York");
        location1.setCountryCode("US");
        location1.setCountyName("United States");
        location1.setEnabled(true);
        Location location2 = new Location();
        location1.setCode("WDC_USA");
        location1.setCityName("Washington DC");
        location1.setRegionName("Washington ");
        location1.setCountryCode("US");
        location1.setCountyName("United States");
        location1.setEnabled(true);

        Mockito.when(service.list()).thenReturn(List.of(location1, location2));
        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    public void testValidateRequestBodyLocationCodeNotNull () throws Exception {
        Location location1 = new Location();
        location1.setCityName("New York");
        location1.setRegionName("New York");
        location1.setCountryCode("US");
        location1.setCountyName("United States");
        location1.setEnabled(true);

        String bodyContent = mapper.writeValueAsString(location1);
        mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH).contentType("application/json").content(bodyContent)).
                andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }
    @Test
    public void testValidateRequestBodyLocationCodeLength () throws Exception {
        Location location1 = new Location();
        location1.setCode("12");
        location1.setCityName("New York");
        location1.setRegionName("New York");
        location1.setCountryCode("US");
        location1.setCountyName("United States");
        location1.setEnabled(true);

        String bodyContent = mapper.writeValueAsString(location1);
        mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH).contentType("application/json").content(bodyContent)).
                andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    public void testGetShouldReturn405MethodNotAllowed () throws Exception {
        String requestUri= END_POINT_PATH+"/ABC";
        mockMvc.perform(post(requestUri))
                .andExpect(status().isMethodNotAllowed())
                        .andDo(print());
    }

    @Test
    public void testGetShouldReturn200OK() throws Exception {
        String code = "ERD_TN";
        String requestURI = END_POINT_PATH + "/" + code;

        Location location = new Location();
        location.setCode("LACA_USA");
        location.setCityName("Los Angeles");
        location.setRegionName("California");
        location.setCountryCode("US");
        location.setCountyName("United States of America");
        location.setEnabled(true);

        Mockito.when(service.get(code)).thenReturn(location);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }
    @Test
    public void testShouldReturn404Notfound() throws Exception {
        Location location = new Location();
        location.setCode("LACA_USA");
        location.setCityName("Los Angeles");
        location.setRegionName("California");
        location.setCountryCode("US");
        location.setCountyName("United States of America");
        location.setEnabled(true);
        Mockito.when(service.update(location)).thenThrow( new LocationNotFoundException("No Location found"));
        String bodyContent = mapper.writeValueAsString(location);
        mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH).contentType("application/json").content(bodyContent)).
                andExpect(status().isNotFound())
                .andDo(print());
    }
    @Test
    public void testShouldReturn400BadRequest() throws Exception {
        Location location = new Location();
        location.setCityName("Los Angeles");
        location.setRegionName("California");
        location.setCountryCode("US");
        location.setCountyName("United States of America");
        location.setEnabled(true);
        String bodyContent = mapper.writeValueAsString(location);
        mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH).contentType("application/json").content(bodyContent)).
                andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testUpdateShouldReturn200Ok() throws Exception {
        Location location1 = new Location();
        location1.setCode("NYC_USA");
        location1.setCityName("New York");
        location1.setRegionName("New York");
        location1.setCountryCode("US");
        location1.setCountyName("United States");
        location1.setEnabled(true);
        Mockito.when(service.update(location1)).thenReturn(location1);
        String bodyContent = mapper.writeValueAsString(location1);
        mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    public void testDeleteShouldReturn404NotFound() throws Exception {
        String code = "ERD_TN";
        String requestURI = END_POINT_PATH + "/" + code;
        Mockito.doThrow(LocationNotFoundException.class).when(service).delete(code);
        mockMvc.perform(delete(requestURI)).andExpect(status().isNotFound()).andDo(print());
    }
@Test
    public void testDeleteShouldReturn204NoContent() throws Exception {
        String code = "ERD_TN";
        String requestURI = END_POINT_PATH + "/" + code;
        Mockito.doNothing().when(service).delete(code);
        mockMvc.perform(delete(requestURI)).andExpect(status().isNoContent()).andDo(print());
    }


}