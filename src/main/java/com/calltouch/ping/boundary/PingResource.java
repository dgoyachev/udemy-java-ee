package com.calltouch.ping.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("ping")
public class PingResource {

    @GET
    public String ping() {
        return "Ping! Enjoy Java EE 8!";
    }
}