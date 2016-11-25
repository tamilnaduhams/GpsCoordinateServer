package ca.dait.gps.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by darinamos on 2016-11-24.
 */
public class UserCredentials implements Authentication {

    private final String username;
    private final Collection<GrantedAuthority> authorities;
    private final transient String password;
    private transient boolean authenticated = false;
    private transient boolean refreshRequired = false;

    @JsonCreator
    public UserCredentials(@JsonProperty("username") String username, @JsonProperty("password") String password){
        this.username = username;
        this.password = password;
        this.authorities = Collections.EMPTY_SET;
    }

    public UserCredentials(String username, String password, String authorities){
        this.username = username;
        this.password = password;
        this.authorities = new HashSet<>();
        for(String authority : authorities.split(",")){
            this.authorities.add(new SimpleGrantedAuthority(authority));
        }
    }

    public UserCredentials(String username, List<String> authorities){
        this.username = username;
        this.password = null;
        this.authorities = new HashSet<>();
        for(String authority : authorities){
            this.authorities.add(new SimpleGrantedAuthority(authority));
        }
    }

    @Override
    public String getPrincipal() {
        return username;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getCredentials() {
        return password;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String[] getAuthoritiesRaw() {
        int i = 0;
        String authoritiesRaw[] = new String[this.authorities.size()];
        for(GrantedAuthority auth : this.authorities){
            authoritiesRaw[i++] = auth.getAuthority();
        }
        return authoritiesRaw;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    public boolean isRefreshRequired() {
        return refreshRequired;
    }

    public void setRefreshRequired(boolean refreshRequired) throws IllegalArgumentException {
        this.refreshRequired = refreshRequired;
    }
}
