package ca.dait.gps.auth;

import ca.dait.gps.data.ConfigService;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darinamos on 2016-11-24.
 */
@Service
public final class StatelessSessionService{

    private static final String DELETE_COOKIE_VAL = "DEL"; //garbage data for deleting a cookie.

    private final String cookieName;
    private final String path;
    private final boolean isSecure;
    private final boolean isHttpOnly;
    private final long cookieTimeout;
    private final long cookieRefresh;
    private final String timestamp;
    private final String issuer;
    private final String username;
    private final String authorities;


    private final JWTSigner signer;
    private final JWTVerifier verifier;

    @Autowired
    public StatelessSessionService(ConfigService configService,
                                   @Value("${site.auth.cookie.name}") String cookieName,
                                   @Value("${site.auth.cookie.name}") String path,
                                   @Value("${site.auth.cookie.secure}") boolean isSecure,
                                   @Value("${site.auth.cookie.httpOnly}") boolean isHttpOnly,
                                   @Value("${site.auth.cookie.timeout}") long cookieTimeout,
                                   @Value("${site.auth.cookie.refresh}") long cookieRefresh,
                                   @Value("${site.auth.cookie.name}")  String timestamp,
                                   @Value("${site.auth.jwt.issuer}") String issuer,
                                   @Value("${site.auth.jwt.username}") String username,
                                   @Value("${site.auth.jwt.authorities}") String authorities){

        String secret = configService.getSecret();
        this.signer = new JWTSigner(secret);
        this.verifier = new JWTVerifier(secret);
        this.cookieName = cookieName;
        this.path = path;
        this.isSecure = isSecure;
        this.isHttpOnly = isHttpOnly;
        this.cookieTimeout = cookieTimeout;
        this.cookieRefresh = cookieRefresh;
        this.timestamp = timestamp;
        this.issuer = issuer;
        this.username = username;
        this.authorities = authorities;
    }

    public void saveAuthorization(UserCredentials user,
                                  HttpServletRequest request, final HttpServletResponse response){

        String value =  createToken(user, request);
        Cookie cookie = new Cookie(this.cookieName, value);
        cookie.setDomain(request.getServerName());
        cookie.setSecure(this.isSecure);
        cookie.setHttpOnly(this.isHttpOnly);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public UserCredentials getAuthorization(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = getCookie(this.cookieName, request);
        if(cookie != null){
            UserCredentials user = this.getCredentials(cookie.getValue());
            if(!user.isAuthenticated()){
                //If the cookie has timed out, remove the cookie and return null.
                //This lets the user continue as an unauthorized user.
                //The service being executed could be available to unauthorized users.
                this.clearAuthorization(request, response);
                return null;
            }
            if(user.isRefreshRequired()){
                this.saveAuthorization(user, request, response);
            }
            return user;
        }
        return null;
    }

    public void clearAuthorization(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie(this.cookieName, null);
        cookie.setDomain(request.getServerName());
        cookie.setSecure(this.isSecure);
        cookie.setHttpOnly(this.isHttpOnly);
        cookie.setMaxAge(0);
        cookie.setPath(this.path);
        response.addCookie(cookie);
    }

    private String createToken(UserCredentials user, HttpServletRequest request){
        Map<String, Object> claims = new HashMap<>();
        claims.put(this.timestamp, System.currentTimeMillis());
        claims.put(this.issuer, request.getServerName());
        claims.put(this.username, user.getPrincipal());
        claims.put(this.authorities, user.getAuthoritiesRaw());
        return this.signer.sign(claims);
    }

    private UserCredentials getCredentials(String token) {
        try {
            final Map<String, Object> claims = this.verifier.verify(token);
            long timestamp = (Long)claims.get(this.timestamp);
            long age = System.currentTimeMillis() - timestamp;

            UserCredentials user = new UserCredentials((String)claims.get(this.username),
                                       (List<String>)claims.get(this.authorities));
            user.setAuthenticated(age < this.cookieTimeout);
            user.setRefreshRequired(age > this.cookieRefresh);
            return user;

        } catch (JWTVerifyException e) {
            throw new BadCredentialsException("Bad session token");
        } catch (NoSuchAlgorithmException | IOException | SignatureException | InvalidKeyException e) {
            throw new BadCredentialsException("Unexpected error occured parsing session token");
        }
    }

    private static Cookie getCookie(String name, HttpServletRequest request){
        Cookie cookies[] = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : request.getCookies()){
                //Make sure the cookie doesn't match the DEL value.
                if(name.equals(cookie.getName()) && !DELETE_COOKIE_VAL.equals(cookie.getValue()));
                return cookie;
            }
        }
        return null;
    }

}
