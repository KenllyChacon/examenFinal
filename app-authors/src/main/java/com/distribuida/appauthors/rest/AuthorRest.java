package com.distribuida.appauthors.rest;


import com.distribuida.appauthors.db.Author;
import com.distribuida.appauthors.repo.AuthorRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(info = @Info(title = "Author API", version = "1.0"))
@Transactional
public class AuthorRest {

    @Inject
    AuthorRepository rep;

    @APIResponse(responseCode = "200", description = "Lista de autores",
            content = @Content())
    @GET
    public List<Author> findAll() {
        return rep.findAll();
    }

    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Autor por ID",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON)
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Autor no encontrado.")
    })
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {
        var author = rep.findById(id);
        if (author == null) {
            throw new NotFoundException("Autor no encontrado");
        }

        return Response.ok(author).build();
    }

    @Operation(summary = "Crear un nuevo autor")
    @APIResponse(responseCode = "201", description = "Autor creado")
    @POST
    public Response create(Author p) {
        rep.create(p);

        return Response.status(Response.Status.CREATED.getStatusCode(), "author created").build();
    }

    @Operation(summary = "Actualizar un autor por ID")
    @APIResponse(responseCode = "200", description = "Autor actualizado")
    @APIResponse(responseCode = "404", description = "Autor no encontrado")
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Author authorObj) {
        Author author = rep.findById(id);
        if (author == null) {
            throw new NotFoundException("Autor no encontrado");
        }

        author.setFirstName(authorObj.getFirstName());
        author.setLastName(authorObj.getLastName());

        return Response.ok().build();
    }


    @Operation(summary = "Eliminar un autor por ID")
    @APIResponse(responseCode = "200", description = "Autor eliminado")
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        try {
            rep.delete(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity("No se pudo eliminar el autor").build();
        }
    }

}
