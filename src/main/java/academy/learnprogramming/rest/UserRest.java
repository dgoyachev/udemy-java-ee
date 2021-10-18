package academy.learnprogramming.rest;

import academy.learnprogramming.entity.User;
import academy.learnprogramming.service.SecurityUtil;
import academy.learnprogramming.service.TodoService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Path("user")
public class UserRest {

    @Inject
    private SecurityUtil securityUtil;

    @Context
    private UriInfo uriInfo;

    @Inject
    private TodoService todoService;

    private static final Logger logger = LoggerFactory.getLogger(UserRest.class);

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@NotNull @FormParam("email") String email,
                          @NotNull @FormParam("password") String password) {

        logger.info("UserRest::login() email: {}, password: {}", email, password);
        //Authenticate user
        //Generate token
        //Return token in Response header to client
        boolean authenticated = securityUtil.authenticateUser(email, password);
        if (!authenticated) {
            throw new SecurityException("Email or password not valid");
        }

        String token = generateToken(email);

        return Response.ok()
                .header(HttpHeaders.AUTHORIZATION, SecurityUtil.BEARER + " " + token)
                .build();
    }

    private String generateToken(String email) {
        Key securityKey = securityUtil.getSecurityKey();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setIssuer(uriInfo.getBaseUri().toString())
                .setAudience(uriInfo.getAbsolutePath().toString())
                .setExpiration(securityUtil.toDate(LocalDateTime.now().plusMinutes(15)))
                .signWith(SignatureAlgorithm.HS512, securityKey).compact();
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(@NotNull User user) {
        logger.info("UserRest::saveUser() user: {}", user);
        todoService.saveUser(user);
        return Response.ok(user).build();
    }
}