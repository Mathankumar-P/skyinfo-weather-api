package com.skyinfo.weatherforecast.realtime;

import com.skyinfo.weatherforecast.RealtimeWeather.RealtimeWeatherRepository;
import com.skyinfo.weatherforecast.common.Location;
import com.skyinfo.weatherforecast.common.RealtimeWeather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RealTimeRepositoryTest {
    @Autowired
    RealtimeWeatherRepository repo ;

    @Test
    public void test (){
        String code = "NYC_USA";
        RealtimeWeather realtime = repo.findById(code).get();
        realtime.setTemperature(20);
        realtime.setHumidity(22);
        realtime.setPrecipitation(2);
        realtime.setWindSpeed(40);
        realtime.setStatus("Sunny");
        realtime.setLastUpdated(new Date());
        repo.save(realtime);
    }

    @Test
    public void testFindbyCountryCodeAndCityNotFound(){
        String countryCode = "JP";
        String cityName = "Tokyo";
        RealtimeWeather realtimeWeather =repo.findByCountryCodeAndCity(countryCode,cityName);
        assertThat(realtimeWeather).isNull();
    }

    @Test
    public void testFindbyCountryCodeAndCityFound(){
        String countryCode = "US";
        String cityName = "New York";
        RealtimeWeather realtimeWeather =repo.findByCountryCodeAndCity(countryCode,cityName);
        assertThat(realtimeWeather).isNotNull();
        assertThat(realtimeWeather.getLocation().getCityName().equals(cityName));
    }

    @Test
    public void testFindbyLocationNotFound(){
        String locationCode = "Xyz";
        RealtimeWeather weather = repo.findByLocationCode(locationCode);
        assertThat(weather).isNull();

    }

    @Test
    public void testFindByLocationNotFound(){
        String code = "ERD_TN";
        RealtimeWeather weather = repo.findByLocationCode(code);
        assertThat(weather).isNull();
    }

    @Test
    public void testFindByLocationFound(){
        String code = "NYC_USA";
        RealtimeWeather weather = repo.findByLocationCode(code);
        assertThat(weather).isNotNull();
    }
}
