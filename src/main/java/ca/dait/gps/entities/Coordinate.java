package ca.dait.gps.entities;

import ca.dait.gps.ServerConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by darinamos on 2016-11-15.
 */
public class Coordinate {
    private final Long id;
    private final Long directoryId;
    private final String name;
    private final Float lat;
    private final Float lon;

    @JsonCreator
    public Coordinate(@JsonProperty("directoryId") Long directoryId,
                      @JsonProperty("name") String name,
                      @JsonProperty("lat") Float lat,
                      @JsonProperty("lon") Float lon){

        this.id = null;
        this.directoryId = (directoryId == null) ? ServerConstants.ROOT_DIRECTORY : directoryId;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }

    public Coordinate(Long id, Long directoryId, String name, Float lat, Float lon){
        this.id = null;
        this.directoryId = (directoryId == null) ? ServerConstants.ROOT_DIRECTORY : directoryId;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }

    public String toString(){
        return "[" + String.valueOf(this.directoryId) + "," + this.name + ", " + this.lat + "," + this.lon + "]";
    }

    public Long getDirectoryId(){
        return this.directoryId;
    }

    public String getName() { return name; }

    public float getLon() {
        return lon;
    }

    public float getLat(){
        return lat;
    }
}
