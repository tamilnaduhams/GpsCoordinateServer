package ca.dait.gps.data;

import ca.dait.gps.entities.Coordinate;
import ca.dait.gps.entities.Directory;

/**
 * Created by darinamos on 2016-11-19.
 */
public interface CoordinateService {

    public Directory[] getRootDirectories();
    public Directory[] getSubDirectories(Long id);

    public Coordinate[] getRootCoordinates();
    public Coordinate[] getDirectoryCoordinates(Long id);

}
