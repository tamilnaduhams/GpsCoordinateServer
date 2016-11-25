package ca.dait.gps.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by darinamos on 2016-11-23.
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private final StatelessSessionService statelessSessionService;

    public StatelessAuthenticationFilter(StatelessSessionService statelessSessionService){
        this.statelessSessionService = statelessSessionService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            UserCredentials user = this.statelessSessionService.getAuthorization(
                                                (HttpServletRequest)request, (HttpServletResponse)response);
            if(user != null){
                SecurityContextHolder.getContext().setAuthentication(user);
            }
            chain.doFilter(request, response);
        }
        catch(BadCredentialsException e){
            this.statelessSessionService.clearAuthorization((HttpServletRequest)request, (HttpServletResponse)response);
            ((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication Failed: " + e.getMessage());
        }

    }

}
