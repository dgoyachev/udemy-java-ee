package academy.learnprogramming.rest;

import academy.learnprogramming.service.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;

@Provider
@Authz
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    @Inject
    private SecurityUtil securityUtil;
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //Grab token from the header of the request using the AUTHORIZATION constant
        //Throw an exception with a message if there's no token
        //Parse the token
        //If parsing succeeds, proceed
        //Otherwise we throw an exception with message

        String authString = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authString == null || !authString.startsWith(SecurityUtil.BEARER)) {
            //Beautify this exception
            throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
        }

        String token = authString.substring(SecurityUtil.BEARER.length()).trim();

        try {
            Key key = securityUtil.getSecurityKey(); //Some Security Key
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            SecurityContext originalContext = requestContext.getSecurityContext();

            System.err.println("original context " + originalContext);

            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return new Principal() {
                        @Override
                        public String getName() {
                            return claimsJws.getBody().getSubject();
                        }
                    };
                }

                @Override
                public boolean isUserInRole(String role) {
                    return originalContext.isUserInRole(role);
                }

                @Override
                public boolean isSecure() {
                    return originalContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return originalContext.getAuthenticationScheme();
                }
            });

            System.err.println("new context " + requestContext.getSecurityContext());
            System.err.println(requestContext.getSecurityContext().getUserPrincipal().getName());

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}