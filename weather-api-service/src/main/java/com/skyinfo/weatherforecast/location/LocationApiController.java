package com.skyinfo.weatherforecast.location;

import com.skyinfo.weatherforecast.common.Location;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/locations")
public class LocationApiController {
    private  LocationService service;
    public LocationApiController(LocationService service){
        super();
        this.service=service;
    }
    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody  @Valid Location  location){
        Location savedLocation = service.add(location);
        System.out.println(location);
        URI uri = URI.create("/v1/location"+savedLocation.getCode());
        return ResponseEntity.created(uri).body(savedLocation);
    }

    @GetMapping
    public ResponseEntity<?> listLocations(){
        List<Location> list = service.list();
        if(list.isEmpty()) return  ResponseEntity.noContent().build();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getLocation(@PathVariable String code){
        Location location = service.get(code);
        if(location==null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(location);
    }

    @PutMapping
    public ResponseEntity<?> updateLocation(@RequestBody  @Valid Location  location)  {
        System.out.println("Yeah");
       try{
           Location updatedLocation = service.update(location);
       return ResponseEntity.ok(updatedLocation);
       }
       catch (LocationNotFoundException e){
           return ResponseEntity.notFound().build();
       }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteLocation(@PathVariable String code){
        try{
            service.delete(code);
            return ResponseEntity.noContent().build();
        } catch (LocationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
