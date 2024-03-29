package com.distribuida.appbooks.clients;

import com.distribuida.appbooks.dtos.AuthorDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "authorsRestClient")
public interface AuthorsRestClient {
    @GET
    List<AuthorDto> findAll();

    @GET
    @Path("/{id}")
    AuthorDto getById(@PathParam("id") Integer id);
}
