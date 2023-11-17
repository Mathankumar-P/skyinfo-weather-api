package com.skyinfo.weatherforecast.geolocation;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import com.skyinfo.weatherforecast.common.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeoLocationService {
    private static final Logger logger = LoggerFactory.getLogger(GeoLocationService.class);
    private String dbPath = "E:\\REST-Api-Masterclass\\skyinfo-weather-api\\weather-api-service\\src\\main\\resources\\ip2localdb\\IP2LOCATION-LITE-DB3.BIN";
    private IP2Location ipLocator = new IP2Location();

    public GeoLocationService(){
        try {
            ipLocator.Open(dbPath);
        }catch (IOException ex){
            logger.error(ex.getMessage(), ex);
        }
    }

    public Location getLocationService (String ipAddress) throws GeoLocationException {
        try {
            IPResult result = ipLocator.IPQuery(ipAddress);

            if(result.getStatus().equals("Ok")){
                throw new GeoLocationException("GeoLocationFailed with status code "+ result.getStatus() );
            }
            logger.info(String.valueOf(result));
            return new Location(result.getCity(), result.getRegion(), result.getCountryLong(), result.getCountryShort());
        }catch (IOException ex){
            throw  new GeoLocationException("Error Querying IP Database" , ex);
        }
    }
}
