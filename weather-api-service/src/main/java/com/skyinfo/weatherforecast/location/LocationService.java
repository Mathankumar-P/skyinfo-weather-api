package com.skyinfo.weatherforecast.location;

import ch.qos.logback.core.util.Loader;
import com.skyinfo.weatherforecast.common.Location;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LocationService {

    private  LocationRepository repository;
    public LocationService(LocationRepository repo){
        super();
        this.repository=repo;
    }
    public Location add(Location location){
    return repository.save(location);
    }

    public List<Location> list(){
        return repository.findUntrashed();
    }
    public Location get(String code){
        return repository.findByCode(code);
    }

    public Location update(Location locationInRequest) throws LocationNotFoundException {
        String code = locationInRequest.getCode();
        Location location = repository.findByCode(code);
        if(location==null)
            throw new LocationNotFoundException ("No location found with the given code  : : " + code);
        location.setCityName(locationInRequest.getCityName());
        location.setCountyName(locationInRequest.getCountyName());
        location.setRegionName(locationInRequest.getRegionName());
        location.setCountryCode(locationInRequest.getCountryCode());
        location.setEnabled(locationInRequest.isEnabled());
        return repository.save(location);
    }

    public void delete(String code) throws LocationNotFoundException {
        Location location = repository.findByCode(code);
        if(location==null)
            throw new LocationNotFoundException("No location found with given code : : "+ code);
        repository.trashedByCode(code);
    }
}
