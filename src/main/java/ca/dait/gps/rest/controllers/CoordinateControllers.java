package ca.dait.gps.rest.controllers;

import ca.dait.gps.GpsConstants;
import ca.dait.gps.data.CoordinateService;
import ca.dait.gps.entities.Coordinate;
import ca.dait.gps.entities.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by darinamos on 2016-11-14.
 */

@RestController
@RequestMapping(GpsConstants.BASE_URL + "/coordinates")
public class CoordinateControllers {

    @Autowired
    private CoordinateService coordinateService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getRootDirectory(){
        return new Result(this.coordinateService.getRootDirectories(),
                          this.coordinateService.getRootCoordinates());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getDirectory(@PathVariable("id") Long id){
        return new Result(this.coordinateService.getSubDirectories(id),
                          this.coordinateService.getDirectoryCoordinates(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void postDirectory(){
        System.err.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }

    public static class Result{
        private Directory directories[];
        private Coordinate coordinates[];

        public Result(Directory directories[], Coordinate coordinates[]){
            this.directories = directories;
            this.coordinates = coordinates;
        }

        public Directory[] getDirectories() {
            return directories;
        }

        public Coordinate[] getCoordinates() {
            return coordinates;
        }
    }

}
