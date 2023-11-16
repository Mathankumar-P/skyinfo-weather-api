package com.skyinfo.weatherforecast.location;

import com.skyinfo.weatherforecast.common.Location;
import com.skyinfo.weatherforecast.common.RealtimeWeather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class LocationRepositoryTest {
    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testSuccess(){
        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountyName("United States");
        Location saved = locationRepository.save(location);

        assertThat(saved).isNotNull();
        assertThat(saved.getCode()).isEqualTo("NYC_USA");
    }

    @Test
    public void testListLocations(){
        List<Location> locations = locationRepository.findUntrashed();
        assertThat(locations ).isNotEmpty();
        locations.forEach(System.out::println);
    }
    @Test
    public void testGetNotFound(){
        String code = "ABC";
        Location location = locationRepository.findByCode(code);
        assertThat(location).isNull();
    }
    @Test
    public void testGetFound(){
        String code = "NYC_USA";
        Location location = locationRepository.findByCode(code);
        assertThat(location).isNotNull();
        assertThat(location.getCode()).isEqualTo(code);
    }

    @Test
    public  void testTrashedSucess(){
        String code = "CHE_IN";
        locationRepository.trashedByCode(code);
        Location location = locationRepository .findByCode(code);
        assertThat(location).isNull();
    }

    @Test
    public void testAddRealTimeWeatherData(){
        String code = "NYC_USA";
        Location location = locationRepository.findByCode(code);
        RealtimeWeather realtime = location.getRealtimeWeather();
        if(realtime==null){
            realtime = new RealtimeWeather();
            realtime.setLocation(location);
            location.setRealtimeWeather(realtime);
        }
        realtime.setTemperature(20);
        realtime.setHumidity(60);
        realtime.setPrecipitation(70);
        realtime.setWindSpeed(10);
        realtime.setStatus("Sunny");
        realtime.setLastUpdated(new Date());
        Location savedLocation = locationRepository.save(location);
    }
}
