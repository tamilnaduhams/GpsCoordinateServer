package ca.dait.gps.data;

import ca.dait.gps.entities.Coordinate;
import ca.dait.gps.entities.Directory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.TypeHandler;

/**
 * Created by darinamos on 2016-11-19.
 */
public interface CoordinateService {

    public Directory getDirectory(Long directoryId);
    public Directory[] getRootDirectories();
    public Directory[] getDirectories(Long parentDirectoryId);

    public Coordinate[] getCoordinates(Long directoryId);

    public void createDirectory(Directory directory);
    public void createCoordinate(Coordinate directory);

}
