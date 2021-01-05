package com.felixseifert.kth.networkprogramming.project.places.resource;

import com.felixseifert.kth.networkprogramming.project.places.model.Role;
import com.felixseifert.kth.networkprogramming.project.places.model.VisitedPlace;
import com.felixseifert.kth.networkprogramming.project.places.service.VisitedPlaceService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1/places")
@RolesAllowed(Role.USER)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VisitedPlaceResource {

    @Inject
    VisitedPlaceService visitedPlaceService;

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    public List<VisitedPlace> getAllVisitedPlaces() {
        return visitedPlaceService.getVisitedPlacesByUserId(getUserIdFromToken());
    }

    @GET
    @Path("/{id}")
    public VisitedPlace getVisitedPlaceById(@PathParam("id") Long id) {
        return visitedPlaceService.getVisitedPlaceByIdAndUserId(id, getUserIdFromToken());
    }

    @POST
    public VisitedPlace postVisitedPlace(VisitedPlace visitedPlace) {
        return visitedPlaceService.postVisitedPlace(visitedPlace, getUserIdFromToken());
    }

    @PUT
    @Path("/{id}")
    public VisitedPlace putVisitedPlace(@PathParam("id") Long id, VisitedPlace visitedPlace) {
        return visitedPlaceService.putVisitedPlace(id, visitedPlace, getUserIdFromToken());
    }

    @DELETE
    @Path("{id}")
    public void deleteVisitedPlace(@PathParam("id") Long id) {
        visitedPlaceService.deleteVisitedPlace(id, getUserIdFromToken());
    }

    private String getUserIdFromToken() {
        return jsonWebToken.getClaim("sub");
    }
}
