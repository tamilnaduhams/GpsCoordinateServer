package ca.dait.gps.entities;

/**
 * Created by darinamos on 2016-11-15.
 */
public class Coordinate {
    private final String name;
    private final String description;
    private final Float lat;
    private final Float lon;

    public Coordinate(String name, String description, Float lat, Float lon){
        this.name = name;
        this.description = description;
        this.lon = lon;
        this.lat = lat;
    }

    public String toString(){
        return "[" + this.name + ", " + this.description + ", " + this.lat + "," + this.lon + "]";
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public float getLon() {
        return lon;
    }

    public float getLat(){
        return lat;
    }
}
