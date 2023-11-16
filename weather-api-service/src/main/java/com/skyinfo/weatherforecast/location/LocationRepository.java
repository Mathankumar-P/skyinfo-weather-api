package com.skyinfo.weatherforecast.location;

import com.skyinfo.weatherforecast.common.Location;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository  extends CrudRepository<Location, String> {

    @Query("SELECT l FROM Location l WHERE l.trashed = false")
    public List<Location>findUntrashed();
    @Query("SELECT l FROM Location l WHERE l.code = ?1")
    public Location findByCode(String code);
    @Modifying
    @Query ("UPDATE  Location  set trashed = true where code = ?1")
    public void trashedByCode(String code);
}
