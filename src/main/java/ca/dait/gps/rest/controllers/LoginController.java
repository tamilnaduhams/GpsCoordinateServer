package ca.dait.gps.rest.controllers;

import ca.dait.gps.GpsConstants;
import ca.dait.gps.auth.StatelessSessionService;
import ca.dait.gps.auth.UserCredentials;
import ca.dait.gps.data.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by darinamos on 2016-11-24.
 */
@RestController
@RequestMapping(GpsConstants.BASE_URL + "/login")
public class LoginController {

    @Autowired
    private UserCredentialsService userCredentialsService;
    @Autowired
    private StatelessSessionService statelessSessionService;
    @Autowired
    private PasswordEncoder encoder;

    @RequestMapping(method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public void login(@RequestBody UserCredentials input,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        UserCredentials actual = this.userCredentialsService.getByUsername(input.getPrincipal());

        if(this.encoder.matches(input.getCredentials(), actual.getCredentials())){
            this.statelessSessionService.saveAuthorization(actual, request, response);
        }
        else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication Failed: Bad Credentials");
        }
    }
}
