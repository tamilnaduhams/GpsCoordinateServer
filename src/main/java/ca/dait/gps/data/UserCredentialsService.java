package ca.dait.gps.data;

import ca.dait.gps.auth.UserCredentials;

/**
 * Created by darinamos on 2016-11-23.
 */
public interface UserCredentialsService{

    public UserCredentials getByUsername(String username);

}
