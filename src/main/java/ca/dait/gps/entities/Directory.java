package ca.dait.gps.entities;

/**
 * Created by darinamos on 2016-11-19.
 */
public class Directory {
    private final Long id;
    private final String name;
    private final String description;

    public Directory(Long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String toString(){
        return "[" + this.id + "," + this.name + ", " + this.description + "]";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
