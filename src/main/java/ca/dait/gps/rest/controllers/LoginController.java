package ca.dait.gps.rest.controllers;

import ca.dait.gps.GpsConstants;
import ca.dait.gps.auth.StatelessSessionService;
import ca.dait.gps.auth.UserCredentials;
import ca.dait.gps.data.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(method = RequestMethod.POST)
    public void login(@RequestParam("username") String username,
                      @RequestParam("password") String password,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        UserCredentials credentials = this.userCredentialsService.getByUsername(username);

        if(this.encoder.matches(password, credentials.getCredentials().toString())){
            this.statelessSessionService.saveAuthorization(credentials, request, response);
        }
        else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication Failed: Bad Credentials");
        }
    }
}
