package ca.dait.gps.entities;

import ca.dait.gps.ServerConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by darinamos on 2016-11-19.
 */
public class Directory {
    private final Long id;
    private final Long parentId;
    private final String name;

    @JsonCreator
    public Directory(@JsonProperty("parentId") Long parentId, @JsonProperty("name") String name){
        this.id = null;
        this.parentId = (parentId == null) ? ServerConstants.ROOT_DIRECTORY : parentId;
        this.name = name;
    }

    public Directory(Long id, Long parentId, String name){
        this.id = id;
        this.parentId = (parentId == null) ? ServerConstants.ROOT_DIRECTORY : parentId;
        this.name = name;
    }

    public String toString(){
        return "[" + this.id + "," + this.parentId + "," + this.name + "]";
    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }
}
