package ca.dait.gps.rest.controllers;

import ca.dait.gps.ServerConstants;
import ca.dait.gps.auth.UserCredentials;
import ca.dait.gps.data.CoordinateService;
import ca.dait.gps.entities.Coordinate;
import ca.dait.gps.entities.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by darinamos on 2016-11-14.
 */

@RestController
@RequestMapping(ServerConstants.BASE_URL + "/gps")
public class CoordinateControllers {

    @Autowired
    private CoordinateService coordinateService;

    @RequestMapping(value="directory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getRootDirectory(){
        return new Result(this.coordinateService.getDirectory(ServerConstants.ROOT_DIRECTORY),
                          this.coordinateService.getRootDirectories(),
                          this.coordinateService.getCoordinates(ServerConstants.ROOT_DIRECTORY));
    }

    @RequestMapping(value = "directory/{parentDirectoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getDirectory(@PathVariable("parentDirectoryId") Long parentDirectoryId){
        return new Result(this.coordinateService.getDirectory(parentDirectoryId),
                          this.coordinateService.getDirectories(parentDirectoryId),
                          this.coordinateService.getCoordinates(parentDirectoryId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value="directory", method = RequestMethod.POST)
    public void createDirectory(@RequestBody Directory directory){//
        this.coordinateService.createDirectory(directory);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value="coordinate", method = RequestMethod.POST)
    public void createCoordinate(@RequestBody Coordinate coordinate){//
        this.coordinateService.createCoordinate(coordinate);
    }

    public static class Result{
        private final Directory directory;
        private final Directory subDirectories[];
        private final Coordinate coordinates[];

        public Result(Directory directory, Directory subDirectories[], Coordinate coordinates[]){
            this.directory = directory;
            this.subDirectories = subDirectories;
            this.coordinates = coordinates;
        }

        public Directory getDirectory(){
            return directory;
        }

        public Directory[] getSubDirectories() {
            return subDirectories;
        }

        public Coordinate[] getCoordinates() {
            return coordinates;
        }
    }

}
